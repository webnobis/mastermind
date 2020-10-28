package com.webnobis.mastermind.view.old;

import java.util.Objects;

import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PinNode;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public interface ColorTypePin {

	static final double BIG = 18.0;

	static final double SMALL = 8.0;

	static PinNode create(ColorType colorType) {
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
//		pin.setDrawMode(DrawMode.LINE);
//		pin.setOpacity(30);
//		pin.setCache(true);
//		pin.setCullFace(CullFace.NONE);
//		pin.setPickOnBounds(true);
		pin.setMaterial(new PhongMaterial(color));
		return new PinNode(pin, colorType);
	}

	public class ColorTypeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ColorTypeException(Exception cause) {
			super(cause);
		}

	}

}
