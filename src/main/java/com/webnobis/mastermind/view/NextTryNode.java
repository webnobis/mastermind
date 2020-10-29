package com.webnobis.mastermind.view;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javafx.geometry.Insets;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class NextTryNode implements Readable<ColorType[]>, Paneable<Pane> {

	private final List<PinNode> pinNodes;

	private final GridPane pane;

	public NextTryNode(int cols) {
		pinNodes = new CopyOnWriteArrayList<>();
		pane = new GridPane();
		pane.setHgap(PinPaletteNode.PADDING);
		pane.setPadding(new Insets(PinPaletteNode.PADDING));

		pane.addRow(0, Stream.<PinNode>generate(PinNode::new).limit(cols).map(pinNode -> {
			pinNodes.add(pinNode);
			return pinNode;
		}).map(NextTryNode::finishDragAndDrop).toArray(i -> new PinNode[i]));
	}

	private static PinNode finishDragAndDrop(PinNode pinNode) {
		pinNode.setOnDragOver(event -> {
			if (event.getGestureSource() != pinNode && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.COPY);
			}
			event.consume();
		});
		pinNode.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasString()) {
				pinNode.update(ColorType.valueOf(db.getString()));
				event.setDropCompleted(true);
			}
			event.consume();
		});
		return pinNode;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public ColorType[] getType() {
		return pinNodes.stream().map(PinNode::getType).toArray(i -> new ColorType[i]);
	}

}
