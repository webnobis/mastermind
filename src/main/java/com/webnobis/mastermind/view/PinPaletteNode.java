package com.webnobis.mastermind.view;

import java.util.EnumSet;

import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class PinPaletteNode implements Paneable<Pane> {
	
	private final TilePane pane;

	public PinPaletteNode() {
		pane = new TilePane(Constants.PADDING.getIntValue(), Constants.PADDING.getIntValue());
		pane.setPrefRows(2);
		pane.setPadding(new Insets(Constants.PADDING.getIntValue()));
		pane.setBorder(new Border(
				new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
		EnumSet.complementOf(EnumSet.of(ColorType.BLACK, ColorType.WHITE)).stream().map(PinNode::new)
				.map(PinPaletteNode::startDragAndDrop).forEach(pane.getChildren()::add);
	}

	private static PinNode startDragAndDrop(PinNode pinNode) {
		pinNode.setOnDragDetected(event -> {
			Dragboard db = pinNode.startDragAndDrop(TransferMode.COPY);
			ClipboardContent content = new ClipboardContent();
			content.putString(pinNode.getType().name());
			db.setContent(content);
			event.consume();
		});
		return pinNode;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

}
