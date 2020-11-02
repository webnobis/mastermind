package com.webnobis.mastermind.view.node;

import java.util.Objects;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.view.Paneable;
import com.webnobis.mastermind.view.Updateable;

import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Window;

/**
 * Play node, contains the play area
 * 
 * @author steffen
 *
 */
public class PlayNode implements Updateable<Play<ColorType>>, Paneable<Pane> {

	private static final Color BACKGROUND_COLOR = Color.rgb(190, 220, 255);

	private final Window parent;

	private final StateNode stateNode;

	private final ResultsNode resultsNode;

	private final BorderPane pane;

	/**
	 * Node of state and results node
	 * 
	 * @param parent parent
	 * @see StateNode#StateNode()
	 * @see ResultsNode#ResultsNode()
	 */
	public PlayNode(Window parent) {
		this.parent = Objects.requireNonNull(parent);
		stateNode = new StateNode();
		resultsNode = new ResultsNode();

		pane = new BorderPane();
		pane.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
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
