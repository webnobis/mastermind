package com.webnobis.mastermind.view;

import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public interface ColorTypePin {

	static final double BIG = 18.0;

	static final double SMALL = 8.0;

	static Sphere create(ColorType colorType) {
		double radius = BIG;
		Color color;
		switch (Objects.requireNonNull(colorType)) {
		case HOLE:
			color = null;
			break;
		case BLACK:
		case WHITE:
			radius = SMALL;
		default:
			try {
				color = Color.class.cast(Color.class.getDeclaredField(colorType.name()).get(null));
			} catch (Exception e) {
				throw new ColorTypeException(e);
			}
		}
		Sphere pin = new Sphere(radius);
//		pin.setDrawMode(DrawMode.FILL);
//		pin.setOpacity(30);
//		pin.setCache(true);
//		pin.setCullFace(CullFace.BACK);
//		pin.setPickOnBounds(true);
		pin.setMaterial(new PhongMaterial(color));
		return pin;
	}

	public class ColorTypeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ColorTypeException(Exception cause) {
			super(cause);
		}

	}

}
