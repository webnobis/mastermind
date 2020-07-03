package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Result;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public interface ResultsPane {

	static Pane create(List<Result<ColorType>> results) {
		VBox pane = new VBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		Optional.ofNullable(results).map(List::stream).orElseGet(Stream::empty).map(ResultPane::create)
				.forEach(pane.getChildren()::add);
		return pane;
	}

}
