package com.webnobis.mastermind.view.node;

import java.util.Objects;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Paneable;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Result node
 * 
 * @author steffen
 *
 */
public class ResultNode implements Paneable<Pane> {

	private final GridPane pane;

	/**
	 * Node of bundle of result source and assessment result
	 * 
	 * @param result result
	 */
	public ResultNode(Result<ColorType> result) {
		Objects.requireNonNull(result);
		GridPane resultPane = new GridPane();
		resultPane.setAlignment(Pos.CENTER_LEFT);
		resultPane.setHgap(Constants.PADDING.getIntValue());
		resultPane.addRow(0, result.getResults().stream().map(ResultNode::toColorType).map(PinNode::new)
				.toArray(i -> new PinNode[i]));

		pane = new GridPane();
		pane.setHgap(Constants.PADDING.getIntValue());
		pane.addRow(0, new SourceNode(result).getPane(), resultPane);
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	private static ColorType toColorType(ResultType resultType) {
		switch (resultType) {
		case EXACT:
			return ColorType.BLACK;
		case PRESENT:
			return ColorType.WHITE;
		default:
			return null;
		}
	}

}
