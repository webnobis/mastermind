package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Status;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

public class StepPosition<T> extends Label {

	public StepPosition(T type) {
		super(type.toString());
		super.setAlignment(Pos.CENTER);
		if (Color.class.isAssignableFrom(type.getClass())) {
			PhongMaterial material = new PhongMaterial();
			material.setDiffuseColor(Color.class.cast(type));
	        material.setSpecularColor(Color.BLACK);
	        material.setSpecularPower(100);
			Shape3D shape = new Sphere(20.0);
			shape.setMaterial(material);
			super.setGraphic(shape);
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
