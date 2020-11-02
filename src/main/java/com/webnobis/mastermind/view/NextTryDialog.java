package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.node.NextTryNode;
import com.webnobis.mastermind.view.node.PinPaletteNode;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

/**
 * Next try dialog
 * 
 * @author steffen
 *
 */
public class NextTryDialog {

	/**
	 * Creates the next try dialog.<br>
	 * It shows the color type palette and the next try area.
	 * 
	 * @param cols       cols
	 * @param updateable updateable of resulting source
	 */
	public NextTryDialog(int cols, Updateable<Source<ColorType>> updateable) {
		Objects.requireNonNull(updateable);
		NextTryNode nextTryNode = new NextTryNode(cols);

		Dialog<Source<ColorType>> dialog = new Dialog<>();
		dialog.setTitle("Nächster Versuch");
		dialog.setHeaderText("Bitte Pins nach rechts in die Felder\ndes nächsten Versuchs ziehen:");
		dialog.setResizable(false);
		dialog.setResultConverter(
				buttonType -> ButtonType.OK.equals(buttonType) ? Source.of(nextTryNode.getType()) : null);

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setContent(new HBox(new PinPaletteNode().getPane(), nextTryNode.getPane()));
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		Window window = dialogPane.getScene().getWindow();
		window.sizeToScene();
		window.centerOnScreen();

		dialog.showAndWait();
		Optional.ofNullable(dialog.getResult()).ifPresent(updateable::update);
	}

}
