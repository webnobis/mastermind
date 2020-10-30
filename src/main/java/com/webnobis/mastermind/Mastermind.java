package com.webnobis.mastermind;

import com.webnobis.mastermind.service.AssessmentService;
import com.webnobis.mastermind.service.PlayService;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PlayMenu;
import com.webnobis.mastermind.view.PlayNode;
import com.webnobis.mastermind.view.SolutionGenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mastermind extends Application {

	public static void main(String[] args) {
		Application.launch(Mastermind.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		PlayService<ColorType> playService = new PlayService<>(SolutionGenerator::generateColorTypes,
				AssessmentService::assess);

		PlayNode playNode = new PlayNode(stage);
		PlayMenu playMenu = new PlayMenu(stage, playService, playNode);
		VBox pane = new VBox(new MenuBar(playMenu.getMenuItem()), playNode.getPane());

		stage.setTitle("Mastermind 2.0");
		stage.centerOnScreen();
		stage.setScene(new Scene(pane));
		stage.show();
	}

}
