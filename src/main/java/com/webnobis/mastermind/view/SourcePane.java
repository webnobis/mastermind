package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Source;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface SourcePane {

	static Pane create(Source<ColorType> source) {
		return create(Objects.requireNonNull(source, "source is null").getSources());
	}

	static Pane create(List<ColorType> sources) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		pane.setAlignment(Pos.CENTER);
		Optional.ofNullable(sources).map(List::stream).orElseGet(Stream::empty).map(ColorTypePin::create)
				.forEach(pane.getChildren()::add);
		return pane;
	}

}
