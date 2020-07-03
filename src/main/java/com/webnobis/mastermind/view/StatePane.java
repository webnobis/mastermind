package com.webnobis.mastermind.view;

import java.util.Optional;

import com.webnobis.mastermind.model.Play;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public interface StatePane {

	static Pane create(Play<?> play) {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(2));
		Optional.ofNullable(play).ifPresent(pl -> {
			Label idLabel = new Label(Msg.get("id.label"));
			TextField id = new TextField(play.getId());
			id.setEditable(false);
			Label stateLabel = new Label(Msg.get("state.label"));
			TextField state = new TextField();
			state.setEditable(false);
			if (pl.isSolved()) {
				state.setText(Msg.get("state.solved"));
				state.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
			} else if (pl.isFinish()) {
				state.setText(Msg.get("state.finish"));
				state.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			} else {
				state.setText(Msg.get("state.default"));
			}
			Label tryLabel = new Label(Msg.get("try.label"));
			TextField freeTry = new TextField();
			freeTry.setEditable(false);
			if (pl.isUnlimited()) {
				freeTry.setText(Msg.get("try.unlimited"));
				freeTry.setBackground(
						new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			} else {
				int freeTryCount = Math.max(0, pl.getRows() - pl.getResults().size());
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
			
			VBox labelPane = new VBox();
			labelPane.setPadding(new Insets(2));
			labelPane.setSpacing(4);
			ObservableList<Node> labelChildren = labelPane.getChildren();
			VBox valuePane = new VBox();
			valuePane.setPadding(new Insets(2));
			valuePane.setSpacing(4);
			ObservableList<Node> valueChildren = valuePane.getChildren();
			labelChildren.add(idLabel);
			valueChildren.add(id);
			labelChildren.add(stateLabel);
			valueChildren.add(state);
			labelChildren.add(tryLabel);
			valueChildren.add(freeTry);
			
			pane.setLeft(labelPane);
			pane.setCenter(valuePane);
		});
		return pane;
	}

}
