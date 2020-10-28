package com.webnobis.mastermind.view;

import java.util.EnumSet;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class PinPaletteNode implements Paneable<Pane> {

	private final TilePane pane;

	public PinPaletteNode() {
		pane = new TilePane(5, 5);
		pane.setPrefRows(2);
		EnumSet.complementOf(EnumSet.of(ColorType.BLACK, ColorType.WHITE, ColorType.HOLE)).stream().map(PinNode::new)
				.map(pinNode -> {
					Pane pinPane = pinNode.getPane();
					pinPane.setOnDragDetected(event -> {
						Dragboard db = pinPane.startDragAndDrop(TransferMode.COPY);
						ClipboardContent content = new ClipboardContent();
						content.put(new TypeDataFormat<>(ColorType.class), pinNode.getType());
						db.setContent(content);
						event.consume();
					});
					return pinPane;
				}).forEach(pane.getChildren()::add);
	}

	@Override
	public Pane getPane() {
		return pane;
	}

}
