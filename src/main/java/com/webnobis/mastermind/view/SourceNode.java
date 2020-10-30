package com.webnobis.mastermind.view;

import java.util.Objects;

import com.webnobis.mastermind.model.Source;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SourceNode implements Paneable<Pane> {

	private final GridPane pane;

	public SourceNode(Source<ColorType> source) {
		pane = new GridPane();
		pane.setHgap(Constants.PADDING.getIntValue());
		pane.addRow(0,
				Objects.requireNonNull(source).getSources().stream().map(PinNode::new).toArray(i -> new PinNode[i]));
	}

	@Override
	public Pane getPane() {
		return pane;
	}

}
