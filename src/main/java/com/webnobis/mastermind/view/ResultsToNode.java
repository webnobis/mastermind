package com.webnobis.mastermind.view;

import java.util.List;

import com.webnobis.mastermind.model.ResultType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public interface ResultsToNode {

	static Node toResultTypeNode(List<ResultType> results) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.setSpacing(4);
		results.stream().filter(ResultType.EXACT::equals)
				.forEach(unused -> pane.getChildren().add(ColorTypePin.create(ColorType.BLACK)));
		results.stream().filter(ResultType.PRESENT::equals)
				.forEach(unused -> pane.getChildren().add(ColorTypePin.create(ColorType.WHITE)));
		return pane;
	}

}
