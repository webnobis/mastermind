package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

class ColorTypeTest {

	@Test
	void testGetDiameter() {
		EnumSet.allOf(ColorType.class).forEach(colorType -> {
			switch (colorType) {
			case BLACK:
			case WHITE:
				assertEquals(ColorType.SMALL, colorType.getDiameter());
				break;
			default:
				assertEquals(ColorType.BIG, colorType.getDiameter());
			}
		});
	}

	@Test
	void testGetPaint() {
		EnumSet.allOf(ColorType.class).forEach(colorType -> {
			switch (colorType) {
			case HOLE:
				assertNull(colorType.getPaint());
				break;
			default:
				try {
					assertEquals(Color.class.getField(colorType.name()).get(null), colorType.getPaint());
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					fail(e);
				}
			}
		});
	}

}
