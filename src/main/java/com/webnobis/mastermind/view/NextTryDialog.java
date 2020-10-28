package com.webnobis.mastermind.view;

import java.util.Map;
import java.util.Objects;

import com.webnobis.mastermind.model.Source;

import javafx.geometry.Insets;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NextTryDialog {

	public NextTryDialog(int cols, Updateable<Source<ColorType>> updateable) {
		Objects.requireNonNull(updateable);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		// depth test disable fixes the not working menu events
		pane.setDepthTest(DepthTest.DISABLE);

		Scene scene = new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight(), true, SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());

		Stage stage = new Stage();
		stage.setTitle("Nächster Versuch");
		stage.centerOnScreen();
		stage.setScene(scene);

		pane.setLeft(new PinPaletteNode().getPane());

		NextTryNode nextTryNode = new NextTryNode(cols);
		pane.setRight(nextTryNode.getPane());

		Button ok = new Button(ButtonType.OK.getText());
		ok.setOnAction(event -> {
			updateable.update(Source.of(toArray(nextTryNode.getType())));
			stage.close();
			event.consume();
		});
		ButtonBar.setButtonData(ok, ButtonData.OK_DONE);
		Button cancel = new Button(ButtonType.CANCEL.getText());
		cancel.setOnAction(event -> {
			stage.close();
			event.consume();
		});
		ButtonBar.setButtonData(cancel, ButtonData.CANCEL_CLOSE);
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.getButtons().addAll(cancel, ok);
		pane.setBottom(buttonBar);

		stage.sizeToScene();
		stage.showAndWait();
	}

	private static ColorType[] toArray(Map<Integer, ColorType> colorTypes) {
		return colorTypes.keySet().stream().sorted().map(colorTypes::get).toArray(i -> new ColorType[i]);
	}

}
