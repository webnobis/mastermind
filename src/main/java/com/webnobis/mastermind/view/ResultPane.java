package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Result;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface ResultPane {

	static Pane create(Result<ColorType> result) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		pane.getChildren().add(SourcePane.create(result.getSources()));
		pane.getChildren().add(ResultTypePane.create(result));
		pane.setPrefWidth(pane.getChildren().stream().mapToDouble(node -> node.minWidth(50)).sum());
		return pane;
	}

}
