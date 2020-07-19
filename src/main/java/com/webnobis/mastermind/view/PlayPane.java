package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Source;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class PlayPane implements Updateable<BorderPane, Play<ColorType>> {

	private final Updateable<GridPane, Play<?>> stateUpdateable;

	private final GridPane playPane;

	private final BorderPane pane;

	private PlayPane(Play<ColorType> play) {
		stateUpdateable = StatePane.create(play);

		playPane = new GridPane();
		playPane.setPadding(new Insets(5));

		pane = new BorderPane();
		pane.setPadding(new Insets(2));
		pane.setTop(stateUpdateable.getPane());
		pane.setCenter(playPane);
		Optional.ofNullable(play).ifPresent(this::update);
	}

	public static Updateable<BorderPane, Play<ColorType>> create(Play<ColorType> play) {
		return new PlayPane(play);
	}

	@Override
	public BorderPane getPane() {
		return pane;
	}

	@Override
	public void update(Play<ColorType> play) {
		stateUpdateable.update(Objects.requireNonNull(play, "play is null"));

		playPane.getChildren().clear();
		IntStream.range(0, play.getResults().size()).forEach(row -> update(row, play.getResults().get(row)));

		Optional.ofNullable(play.getSource()).filter(unused -> !play.getResults().isEmpty())
				.ifPresent(source -> update(play.getResults().size(), source));
	}

	private void update(int row, Result<ColorType> result) {
		playPane.add(SourcePane.create(result.getSources()), 0, row, 2, 1);
		playPane.add(ResultTypePane.create(result), 2, row);
	}

	private void update(int row, Source<ColorType> source) {
		playPane.add(SourcePane.create(source), 0, row, 2, 1);
		playPane.add(new Label("Lösung"), 2, row);
	}

}
