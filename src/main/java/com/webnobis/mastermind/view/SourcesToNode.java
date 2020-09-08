package com.webnobis.mastermind.view;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public interface SourcesToNode {

	static Node toColorTypeNode(List<ColorType> sources) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.setSpacing(4);
		sources.forEach(colorType -> pane.getChildren().add(ColorTypePin.create(colorType)));
		return pane;
	}

}
