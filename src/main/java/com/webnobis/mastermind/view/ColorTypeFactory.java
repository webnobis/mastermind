package com.webnobis.mastermind.view;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.shape.Circle;

public interface ColorTypeFactory {
	
	static Node create(ColorType colorType) {
		CornerRadii radii = new CornerRadii(colorType.getDiameter(), true);
		BackgroundFill fill = Optional.ofNullable(colorType.getPaint()).map(paint -> new BackgroundFill(paint, radii, new Insets(4.0))).orElse(null);
		Background background = new Background(fill);
		Circle circle = new Circle(colorType.getDiameter(), colorType.getPaint());
		Group group = new Group(circle);
		return group;
	}

}
