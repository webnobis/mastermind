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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PlayPane<T> implements Consumer<Play<T>> {

	private final Function<List<T>, Node> sourcesToNode;

	private final Function<List<ResultType>, Node> resultsToNode;

	private final BiFunction<String, Boolean, Node> stateToNode;

	private final BorderPane playPane;

	public PlayPane(Consumer<Pane> paneBuilder, Function<List<T>, Node> sourcesToNode,
			Function<List<ResultType>, Node> resultsToNode, BiFunction<String, Boolean, Node> stateToNode) {
		this.sourcesToNode = sourcesToNode;
		this.resultsToNode = resultsToNode;
		this.stateToNode = stateToNode;

		playPane = new BorderPane();
		playPane.setPadding(new Insets(10));
		paneBuilder.accept(playPane);
	}

	@Override
	public void accept(Play<T> play) {
		playPane.getChildren().clear();

		VBox statePane = new VBox();
		// statePane.setPadding(new Insets(2));
		statePane.setSpacing(4);
		statePane.getChildren().add(new Label("Id:"));
		statePane.getChildren().add(stateToNode.apply("ID: ".concat(play.getId()), null));
		statePane.getChildren().add(new Label("Status:"));
		statePane.getChildren().add(stateToNode.apply("Unbegrenzt", play.isUnlimited()));
		statePane.getChildren().add(stateToNode.apply("Gelöst", play.isSolved()));
		statePane.getChildren().add(stateToNode.apply("Beendet", play.isFinish()));
		playPane.setTop(statePane);

		VBox resultsPane = new VBox();
		// resultsPane.setPadding(new Insets(2));
		resultsPane.setSpacing(4);
		resultsPane.getChildren().add(new Label("Versuche:"));
		play.getResults().forEach(result -> {
			HBox pane = new HBox();
			// pane.setPadding(new Insets(2));
			pane.setSpacing(10);
			pane.getChildren().add(sourcesToNode.apply(result.getSources()));
			pane.getChildren().add(resultsToNode.apply(result.getResults()));
			resultsPane.getChildren().add(pane);
		});
		playPane.setCenter(resultsPane);
		BorderPane.setMargin(resultsPane, new Insets(20, 0, 20, 0));

		Optional.ofNullable(play.getSource()).map(Source::getSources).map(sourcesToNode::apply).ifPresent(node -> {
			HBox pane = new HBox();
			pane.setSpacing(10);
			pane.setAlignment(Pos.CENTER_LEFT);
			pane.getChildren().add(node);
			pane.getChildren().add(new Label("wurde gesucht"));
			playPane.setBottom(pane);
		});
	}

}
