package com.webnobis.mastermind.view;

import java.text.MessageFormat;

import com.webnobis.mastermind.Mastermind;
import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.service.AssessmentService;
import com.webnobis.mastermind.service.PlayService;
import com.webnobis.mastermind.view.menu.InfoMenu;
import com.webnobis.mastermind.view.menu.PlayMenu;
import com.webnobis.mastermind.view.node.PlayNode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Dialog of the Mastermind game, calls the service and containing the menu and
 * play area.
 * 
 * @author steffen
 * 
 * @see PlayService#PlayService(java.util.function.IntFunction,
 *      java.util.function.BiFunction)
 * @see PlayNode#PlayNode(javafx.stage.Window)
 * @see PlayMenu#PlayMenu(javafx.stage.Window, PlayService, Updateable)
 */
public class MastermindDialog extends Application {

	/**
	 * Title
	 */
	public static final String TITLE = MessageFormat.format("{0} {1}", Mastermind.class.getSimpleName(),
			Constants.VERSION.getValue());

	@Override
	public void start(Stage stage) throws Exception {
		PlayService<ColorType> playService = new PlayService<>(ColorTypeSourceGenerator::generate,
				AssessmentService::assess);

		PlayNode playNode = new PlayNode(stage);
		PlayMenu playMenu = new PlayMenu(stage, playService, playNode);
		VBox pane = new VBox(new MenuBar(playMenu.getMenuItem(), new InfoMenu().getMenuItem()), playNode.getPane());

		stage.setTitle(TITLE);
		stage.centerOnScreen();
		stage.setScene(new Scene(pane));
		stage.show();
	}

}
