package com.webnobis.mastermind.view;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public interface StateToPane {

	static Pane toPane(String label, Boolean flag) {
		return new VBox(Optional.ofNullable(flag).<Node>map(selected -> {
			RadioButton state = new RadioButton(label);
			state.setSelected(flag);
			state.setOnAction(event -> state.setSelected(flag));
			return state;
		}).orElse(new Label(label)));
	}

}
