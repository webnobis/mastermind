package com.webnobis.mastermind.view.node;

import java.util.EnumSet;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Paneable;

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

/**
 * Palette usable color type pin nodes (all except {@link ColorType#BLACK} and
 * {@link ColorType#WHITE})
 * 
 * @author steffen
 *
 */
public class PinPaletteNode implements Paneable<Pane> {

	private final TilePane pane;

	/**
	 * Node of all usable color type pin nodes, each to drop copy of its color type.
	 * 
	 * @see PinNode#startDragAndDrop(TransferMode...)
	 * @see ClipboardContent#putString(String)
	 * @see Dragboard#setContent(java.util.Map)
	 */
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
