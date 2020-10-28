package com.webnobis.mastermind.view.old;

import java.util.List;

import com.webnobis.mastermind.view.ColorType;

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
