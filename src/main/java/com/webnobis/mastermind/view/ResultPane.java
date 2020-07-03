package com.webnobis.mastermind.view;

import java.util.Optional;

import com.webnobis.mastermind.model.Result;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface ResultPane {

	static Pane create(Result<ColorType> result) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		Optional.ofNullable(result).ifPresent(res -> {
			ObservableList<Node> children = pane.getChildren();
			children.add(SourcePane.create(result.getSources()));
			children.add(ResultTypePane.create(result));
//			pane.setPrefWidth(pane.getChildren().stream().mapToDouble(node -> node.minWidth(50)).sum());
		});
		return pane;
	}

}
