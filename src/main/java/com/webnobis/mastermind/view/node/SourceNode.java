package com.webnobis.mastermind.view.node;

import java.util.Objects;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Paneable;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Source node
 * 
 * @author steffen
 *
 */
public class SourceNode implements Paneable<Pane> {

	private final GridPane pane;

	/**
	 * Node of source
	 * 
	 * @param source source
	 */
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
