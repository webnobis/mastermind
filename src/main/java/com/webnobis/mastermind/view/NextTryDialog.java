package com.webnobis.mastermind.view;

import java.util.EnumSet;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public interface NextTryDialog {

	/*
	 * Links alle Farben, dann nach rechts per Drag-and-Drop ablegen
	 */
	static Source<ColorType> create(Window parent, Play<ColorType> play) {
			TilePane palettePane = new TilePane(10, 10);
		palettePane.setPrefRows(2);
		EnumSet.complementOf(EnumSet.of(ColorType.BLACK, ColorType.WHITE, ColorType.HOLE)).stream()
		.map(ColorTypePin::create).forEach(palettePane.getChildren()::add);
		
		GridPane nextTryPane = new GridPane();
		nextTryPane.addRow(0, Stream.generate(() -> new Label("-")).limit(play.getCols()).toArray(i -> new Node[i]));
		
		BorderPane mainPane = new BorderPane();
		mainPane.setLeft(palettePane);
		mainPane.setCenter(nextTryPane);
		
		Dialog<Source<ColorType>> tryDialog = new Dialog<>();
		tryDialog.initOwner(parent);
		
		DialogPane dialogPane = tryDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("Nächster Versuch");
		
		tryDialog.showAndWait();
		return Source.of(ColorType.GREEN);
	}

}
