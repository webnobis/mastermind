package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.model.Play;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class StatePane implements Updateable<TilePane, Play<?>> {

	private final TilePane pane;

	private final Label idLabel;

	private final TextField id;

	private final Label stateLabel;

	private final TextField state;

	private Label tryLabel;

	private TextField freeTry;

	private StatePane() {
		pane = new TilePane();
		pane.setPadding(new Insets(2));
		pane.setPrefColumns(2);
		ObservableList<Node> children = pane.getChildren();
		idLabel = new Label(Msg.get("id.label"));
		id = new TextField();
		id.setEditable(false);
		id.setBackground(idLabel.getBackground());
		stateLabel = new Label(Msg.get("state.label"));
		state = new TextField();
		state.setEditable(false);
		tryLabel = new Label(Msg.get("try.label"));
		freeTry = new TextField();
		freeTry.setEditable(false);
		children.add(idLabel);
		children.add(id);
		children.add(stateLabel);
		children.add(state);
		children.add(tryLabel);
		children.add(freeTry);
	}

	public static Updateable<TilePane, Play<?>> create() {
		return create(null);
	}

	public static Updateable<TilePane, Play<?>> create(Play<?> play) {
		Updateable<TilePane, Play<?>> updateable = new StatePane();
		Optional.ofNullable(play).ifPresent(updateable::update);
		return updateable;
	}

	@Override
	public TilePane getPane() {
		return pane;
	}

	@Override
	public void update(Play<?> play) {
		id.setText(Objects.requireNonNull(play, "play is null").getId());
		if (play.isSolved()) {
			state.setText(Msg.get("state.solved"));
			state.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		} else if (play.isFinish()) {
			state.setText(Msg.get("state.finish"));
			state.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		} else {
			state.setText(Msg.get("state.default"));
			state.setBackground(stateLabel.getBackground());
		}
		if (play.isUnlimited()) {
			freeTry.setText(Msg.get("try.unlimited"));
			freeTry.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		} else {
			int freeTryCount = Math.max(0, play.getRows() - play.getResults().size());
			freeTry.setText(String.valueOf(freeTryCount));
			Color tryColor;
			switch (freeTryCount) {
			case 0:
			case 1:
				tryColor = Color.RED;
				break;
			case 2:
			case 3:
				tryColor = Color.ORANGE;
				break;
			default:
				tryColor = Color.YELLOW;
			}
			freeTry.setBackground(new Background(new BackgroundFill(tryColor, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}

}
