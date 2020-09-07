package com.webnobis.mastermind.view;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PlayPane<T> implements Consumer<Play<T>> {

	private final Function<List<T>, Pane> sourcesToPane;

	private final Function<List<ResultType>, Pane> resultsToPane;

	private final BiFunction<String, Boolean, Pane> stateToPane;

	private final BorderPane playPane;

	public PlayPane(Consumer<Pane> paneBuilder, Function<List<T>, Pane> sourcesToPane,
			Function<List<ResultType>, Pane> resultsToPane, BiFunction<String, Boolean, Pane> stateToPane) {
		this.sourcesToPane = sourcesToPane;
		this.resultsToPane = resultsToPane;
		this.stateToPane = stateToPane;

		playPane = new BorderPane();
		playPane.setPadding(new Insets(2));

		paneBuilder.accept(playPane);
	}

	@Override
	public void accept(Play<T> play) {
		playPane.getChildren().clear();

		VBox statePane = new VBox();
		statePane.setPadding(new Insets(2));
		statePane.setSpacing(4);
		statePane.getChildren().add(stateToPane.apply("ID: ".concat(play.getId()), null));
		statePane.getChildren().add(stateToPane.apply("Unbegrenzt", play.isUnlimited()));
		statePane.getChildren().add(stateToPane.apply("Gelöst", play.isSolved()));
		statePane.getChildren().add(stateToPane.apply("Beendet", play.isFinish()));
		playPane.setTop(statePane);

		VBox resultsPane = new VBox();
		resultsPane.setPadding(new Insets(2));
		resultsPane.setSpacing(4);
		play.getResults().forEach(result -> {
			HBox pane = new HBox();
			pane.setPadding(new Insets(2));
			pane.setSpacing(4);
			pane.getChildren().add(sourcesToPane.apply(result.getSources()));
			pane.getChildren().add(resultsToPane.apply(result.getResults()));
			resultsPane.getChildren().add(pane);
		});
		playPane.setCenter(resultsPane);

		Optional.ofNullable(play.getSource()).map(Source::getSources).map(sourcesToPane::apply)
				.ifPresent(playPane::setBottom);
	}

}
