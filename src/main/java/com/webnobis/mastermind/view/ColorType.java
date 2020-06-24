package com.webnobis.mastermind.view;

import java.util.EnumSet;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum ColorType {

	BLACK, WHITE, YELLOW, ORANGE, RED, GREEN, BLUE, VIOLET, HOLE;

	static final double BIG = 20.0;

	static final double SMALL = 10.0;

	private static final EnumSet<ColorType> blackAndWhite = EnumSet.of(BLACK, WHITE);

	public double getDiameter() {
		return blackAndWhite.contains(this) ? SMALL : BIG;
	}

	public Paint getPaint() {
		if (HOLE.equals(this)) {
			return null;
		}
		try {
			return Paint.class.cast(Color.class.getDeclaredField(this.name()).get(null));
		} catch (Exception e) {
			throw new ColorTypeException(e);
		}
	}

	private class ColorTypeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ColorTypeException(Exception cause) {
			super(cause);
		}

	}

}
