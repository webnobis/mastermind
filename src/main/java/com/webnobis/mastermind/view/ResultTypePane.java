package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface ResultTypePane {

	static Pane create(Result<ColorType> result) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		pane.setAlignment(Pos.CENTER_LEFT);
		Optional.ofNullable(result).map(Result::getResults).map(List::stream).orElseGet(Stream::empty)
				.map(resultType -> ResultType.EXACT.equals(resultType) ? ColorType.BLACK
						: ResultType.PRESENT.equals(resultType) ? ColorType.WHITE : ColorType.HOLE)
				.map(ColorTypePin::create).forEach(pane.getChildren()::add);
		return pane;
	}

}
