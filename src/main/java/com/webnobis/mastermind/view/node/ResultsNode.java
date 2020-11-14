package com.webnobis.mastermind.view.node;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Paneable;
import com.webnobis.mastermind.view.Updateable;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Results node
 * 
 * @author steffen
 *
 */
public class ResultsNode implements Updateable<List<Result<ColorType>>>, Paneable<Pane> {

	/**
	 * Max result count: 15
	 */
	public static final int MAX_RESULT_COUNT = 15;

	private final VBox pane;

	/**
	 * Node of all results
	 * 
	 * @see ResultNode#ResultNode(Result)
	 */
	public ResultsNode() {
		pane = new VBox(Constants.PADDING.getIntValue());
		pane.setPadding(new Insets(Constants.PADDING.getIntValue()));
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	/**
	 * Adds until {@link #MAX_RESULT_COUNT} result nodes with latest results.
	 */
	@Override
	public void update(List<Result<ColorType>> results) {
		if (Objects.requireNonNull(results).isEmpty()) {
			pane.getChildren().clear();
		} else if (results.size() > pane.getChildren().size()) {
			if (pane.getChildren().size() < MAX_RESULT_COUNT) {
				results.subList(pane.getChildren().size(), results.size()).stream().map(ResultNode::new)
						.map(ResultNode::getPane).forEach(pane.getChildren()::add);
			} else {
				List<Result<ColorType>> subList = results.subList(results.size() - MAX_RESULT_COUNT, results.size());
				IntStream.range(0, subList.size())
						.forEach(i -> pane.getChildren().set(i, new ResultNode(subList.get(i)).getPane()));
			}
		}
	}

}
