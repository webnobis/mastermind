package com.webnobis.mastermind.view;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface SourcesToPane {

	static Pane toColorTypePane(List<ColorType> sources) {
		HBox pane = new HBox();
		pane.setPadding(new Insets(2));
		pane.setSpacing(4);
		sources.forEach(colorType -> pane.getChildren().add(ColorTypePin.create(colorType)));
		return pane;
	}

}
