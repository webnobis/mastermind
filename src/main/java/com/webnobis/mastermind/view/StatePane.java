package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.model.Play;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StatePane implements Updateable<GridPane, Play<?>> {

	private final GridPane pane;

	private final Label idLabel;

	private final Label id;

	private final Label stateLabel;

	private final Label state;

	private Label tryLabel;

	private Label freeTry;

	private StatePane(Play<?> play) {
		pane = new GridPane();
		pane.setPadding(new Insets(5));
		pane.setHgap(5);
		pane.setVgap(5);
		idLabel = new Label(Msg.get("id.label"));
		id = new Label();
		stateLabel = new Label(Msg.get("state.label"));
		state = new Label();
		tryLabel = new Label(Msg.get("try.label"));
		freeTry = new Label();
		pane.add(idLabel, 0, 0);
		pane.add(id, 1, 0, 2, 1);
		pane.add(stateLabel, 0, 1);
		pane.add(state, 1, 1, 2, 1);
		pane.add(tryLabel, 0, 2);
		pane.add(freeTry, 1, 2, 2, 1);
		Optional.ofNullable(play).ifPresent(this::update);
	}

	public static Updateable<GridPane, Play<?>> create(Play<?> play) {
		return new StatePane(play);
	}

	@Override
	public GridPane getPane() {
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
