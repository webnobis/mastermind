package com.webnobis.mastermind.view.old;

import java.util.EnumSet;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;

import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public interface NextTryDialog {

	/*
	 * Links alle Farben, dann nach rechts per Drag-and-Drop ablegen
	 */
	static Source<ColorType> create(Play<ColorType> play) {
		DataFormat colorTypeData = new DataFormat(ColorType.class.getSimpleName());
		TilePane palettePane = new TilePane(2, 2);
		palettePane.setPrefRows(2);
		EnumSet.complementOf(EnumSet.of(ColorType.BLACK, ColorType.WHITE, ColorType.HOLE)).stream()
				.map(ColorTypePin::create).map(pin -> {
					pin.setOnDragDetected(event -> {
						Dragboard db = pin.startDragAndDrop(TransferMode.COPY);
						ClipboardContent content = new ClipboardContent();
						content.put(colorTypeData, pin.getColorType());
						db.setContent(content);
						event.consume();
					});
					return pin;
				}).forEach(palettePane.getChildren()::add);

		GridPane nextTryPane = new GridPane();
		nextTryPane.addRow(0,
				Stream.generate(() -> new HBox(new Text("Alle"))).limit(play.getCols()).map(pane -> {
//					pane.setOnDragOver(event -> {
//						Dragboard db = event.getDragboard();
//						ColorType colorType = ColorType.class.cast(db.getContent(colorTypeData));
//						System.out.println(colorType);
//						event.consume();
//					});
					pane.setOnDragDropped(event -> {
						System.out.println(event);
						Dragboard db = event.getDragboard();
						ColorType colorType = ColorType.class.cast(db.getContent(colorTypeData));
						pane.getChildren().set(0, ColorTypePin.create(colorType));
						event.setDropCompleted(true);
						event.consume();
					});
					return pane;
				}).toArray(i -> new Pane[i]));

		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(5));
		mainPane.setLeft(palettePane);
		mainPane.setCenter(nextTryPane);

		Scene scene = new Scene(mainPane, mainPane.getPrefWidth(), mainPane.getPrefHeight(), true,
				SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());

		Stage stage = new Stage();
		stage.setTitle("Nächster Versuch");
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.showAndWait();

//		Dialog<Source<ColorType>> tryDialog = new Dialog<>();
//		tryDialog.initOwner(parent);
//		
//		DialogPane dialogPane = tryDialog.getDialogPane();
//		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//		dialogPane.setContent(mainPane);
//		dialogPane.setHeaderText("Nächster Versuch");
//		
//		tryDialog.showAndWait();
		return Source.of(ColorType.GREEN);
	}

}
