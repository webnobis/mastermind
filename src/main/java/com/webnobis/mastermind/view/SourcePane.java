package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Source;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface SourcePane {

	static Pane create(Source<ColorType> source) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		Optional.ofNullable(source).map(Source::getSources).map(List::stream).orElseGet(Stream::empty)
				.map(ColorTypePin::create).forEach(pane.getChildren()::add);
		return pane;
	}

}
