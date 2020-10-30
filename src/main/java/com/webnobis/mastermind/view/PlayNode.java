package com.webnobis.mastermind.view;

import java.util.Objects;

import com.webnobis.mastermind.model.Play;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class PlayNode implements Updateable<Play<ColorType>>, Paneable<Pane> {

	private final Window parent;

	private final StateNode stateNode;

	private final ResultsNode resultsNode;

	private final BorderPane pane;

	public PlayNode(Window parent) {
		this.parent = Objects.requireNonNull(parent);
		stateNode = new StateNode();
		resultsNode = new ResultsNode();

		pane = new BorderPane();
		pane.setTop(stateNode.getPane());
		pane.setCenter(new SplitPane());
		pane.setBottom(resultsNode.getPane());
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void update(Play<ColorType> play) {
		stateNode.update(play);
		resultsNode.update(play.getResults());
		parent.sizeToScene();
	}

}
