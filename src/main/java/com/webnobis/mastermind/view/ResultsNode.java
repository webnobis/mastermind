package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Objects;

import com.webnobis.mastermind.model.Result;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class ResultsNode implements Updateable<List<Result<ColorType>>>, Paneable<Pane> {

	private final Window parent;

	private final VBox pane;

	public ResultsNode(Window parent) {
		this.parent = Objects.requireNonNull(parent);
		pane = new VBox();
		pane.setPadding(new Insets(Constants.PADDING.getIntValue()));
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void update(List<Result<ColorType>> results) {
		if (Objects.requireNonNull(results).isEmpty()) {
			pane.getChildren().clear();
			parent.sizeToScene();
		} else if (results.size() > pane.getChildren().size()) {
			results.subList(pane.getChildren().size(), results.size()).stream().map(ResultNode::new)
					.map(ResultNode::getPane).forEach(pane.getChildren()::add);
			parent.sizeToScene();
		}
	}

}
