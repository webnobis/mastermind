package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.webnobis.mastermind.view.old.ColorTypePin.ColorTypeException;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class PinNode implements Updateable<ColorType, Pane> {

	static final double BIG = 18.0;

	static final double SMALL = 8.0;

	private final AtomicReference<ColorType> colorTypeRef;

	private final Pane pane;

	public PinNode() {
		this(null);
	}

	public PinNode(ColorType colorType) {
		colorTypeRef = new AtomicReference<>();
		pane = new HBox(new Pane());
		update(colorType);
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public ColorType getType() {
		return colorTypeRef.get();
	}

	@Override
	public void update(ColorType colorType) {
		colorTypeRef.set(Optional.ofNullable(colorType).orElse(ColorType.HOLE));
		pane.getChildren().set(0, create(getType()));
	}

	private static Sphere create(ColorType colorType) {
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
		return pin;
	}
}
