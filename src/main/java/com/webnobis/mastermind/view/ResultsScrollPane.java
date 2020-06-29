package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Result;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public interface ResultsScrollPane {

	static ScrollPane create(List<Result<ColorType>> results) {
		VBox pane = new VBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		Optional.ofNullable(results).map(List::stream).orElseGet(Stream::empty).map(ResultPane::create)
				.forEach(pane.getChildren()::add);
		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setPannable(true);
		scrollPane.setVvalue(1.0); // last entry
		return scrollPane;
	}

}
