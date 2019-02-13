package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Status;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class StepPosition<T> extends Label {

	public StepPosition(T type) {
		super(type.toString());
		super.setAlignment(Pos.CENTER);
		if (Color.class.isAssignableFrom(type.getClass())) {
			super.setGraphic(new Circle(10, Color.class.cast(type)));
			super.setTooltip(new Tooltip(super.getText()));
			super.setText("");
		} else if (Status.class.isAssignableFrom(type.getClass())) {
			Paint color;
			switch (Status.class.cast(type)) {
			case CORRECT_PLACE:
				color = Color.RED;
				break;
			case CONTAINED:
				color = Color.BLACK;
				break;
			default:
				color = null;
			}
			super.setGraphic(new Rectangle(7.5, 7.5, color));
			super.setTooltip(new Tooltip(super.getText()));
			super.setText("");
		}
	}

}
