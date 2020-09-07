package com.webnobis.mastermind.view;

import java.util.List;

import com.webnobis.mastermind.model.ResultType;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface ResultsToPane {

	static Pane toColorTypePane(List<ResultType> results) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		results.stream().filter(ResultType.EXACT::equals)
				.forEach(unused -> pane.getChildren().add(ColorTypePin.create(ColorType.BLACK)));
		results.stream().filter(ResultType.PRESENT::equals)
				.forEach(unused -> pane.getChildren().add(ColorTypePin.create(ColorType.WHITE)));
		return pane;
	}

}
