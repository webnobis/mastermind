package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

class ColorTypePinTest {

	@Test
	void testCreate() {
		EnumSet.allOf(ColorType.class).forEach(colorType -> {
			Sphere pin = ColorTypePin.create(colorType);
			assertNotNull(pin);

			switch (colorType) {
			case BLACK:
			case WHITE:
				assertEquals(ColorTypePin.SMALL, pin.getRadius());
				break;
			default:
				assertEquals(ColorTypePin.BIG, pin.getRadius());
			}

			Color color = PhongMaterial.class.cast(pin.getMaterial()).getDiffuseColor();
			switch (colorType) {
			case HOLE:
				assertNull(color);
				break;
			default:
				assertNotNull(color);
			}
		});
	}

}
