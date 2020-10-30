package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Play;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class PlayNode implements Updateable<Play<ColorType>>, Paneable<Pane> {

	private final StateNode stateNode;

	private final ResultsNode resultsNode;

	private final BorderPane pane;

	public PlayNode(Window parent) {
		stateNode = new StateNode();
		resultsNode = new ResultsNode(parent);

		pane = new BorderPane();
		pane.setTop(stateNode.getPane());
		pane.setCenter(resultsNode.getPane());
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void update(Play<ColorType> play) {
		stateNode.update(play);
		resultsNode.update(play.getResults());
	}

}
