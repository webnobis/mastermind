package com.webnobis.mastermind.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import javafx.geometry.Insets;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class NextTryNode implements Readable<Map<Integer, ColorType>>, Paneable<Pane> {

	private final Map<Integer, ColorType> colorTypes;

	private final GridPane pane;

	public NextTryNode(int cols) {
		colorTypes = new ConcurrentHashMap<>();
		pane = new GridPane();
		pane.setHgap(10);
		IntStream.range(0, cols).forEach(col -> {
			PinNode pinNode = new PinNode(ColorType.ORANGE);
			Pane pinPane = pinNode.getPane();
			pinPane.setOnDragDropped(event -> {
				Dragboard db = event.getDragboard();
				DataFormat type = new TypeDataFormat<>(ColorType.class);
				if (db.hasContent(type)) {
					ColorType colorType = ColorType.class.cast(db.getContent(type));
					colorTypes.put(col, colorType);
					pinNode.update(colorType);
					event.setDropCompleted(true);
				}
				event.consume();
			});
			pane.add(pinPane, col, 0);
		});
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public Map<Integer, ColorType> getType() {
		return colorTypes;
	}

}
