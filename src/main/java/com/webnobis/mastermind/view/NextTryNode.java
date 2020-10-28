package com.webnobis.mastermind.view;

import java.util.stream.Stream;

import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class NextTryNode implements Paneable<Pane> {

	private final GridPane pane;
	
	public NextTryNode(int cols) {
		pane = new GridPane();
		pane.addRow(0,
				Stream.generate(PinNode::new).limit(cols).map(pinNode -> {
					Pane pinPane = pinNode.getPane();
					pinPane.setOnDragDropped(event -> {
						System.out.println(event);
						Dragboard db = event.getDragboard();
						ColorType colorType = ColorType.class.cast(db.getContent(new TypeDataFormat<>(ColorType.class)));
						pinNode.update(colorType);
						event.setDropCompleted(true);
						event.consume();
					});
					return pane;
				}).toArray(i -> new Pane[i]));
	}

	@Override
	public Pane getPane() {
		return pane;
	}

}
