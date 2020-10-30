package com.webnobis.mastermind.view;

import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.skin.LabelSkin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PinNode extends Control implements Updateable<ColorType>, Readable<ColorType> {

	static final double BIG = 30.0;

	static final double SMALL = 12.0;

	private final AtomicReference<ColorType> colorTypeRef;

	public PinNode() {
		this(null);
	}

	public PinNode(ColorType colorType) {
		super();
		super.setSkin(new LabelSkin(new Label()));
		colorTypeRef = new AtomicReference<>();
		update(colorType);
	}

	@Override
	public ColorType getType() {
		return colorTypeRef.get();
	}

	@Override
	public void update(ColorType colorType) {
		colorTypeRef.set(Optional.ofNullable(colorType).orElse(ColorType.HOLE));
		update();
	}

	private void update() {
		double radius = getRadius();
		Optional.ofNullable(getColor()).ifPresentOrElse(color -> {
			super.setBorder(null);
			super.setBackground(createBackground(radius, color));
		}, () -> {
			super.setBorder(createBorder(radius));
			super.setBackground(null);
		});
		super.setMinSize(radius, radius);
		super.setPrefSize(radius, radius);
		super.setMaxSize(radius, radius);
	}

	private double getRadius() {
		return EnumSet.of(ColorType.BLACK, ColorType.WHITE).contains(getType()) ? SMALL : BIG;
	}

	private Color getColor() {
		try {
			return Color.class.cast(Color.class.getDeclaredField(getType().name()).get(null));
		} catch (Exception e) {
			return null; // such as HOLE
		}
	}

	private static Border createBorder(double radius) {
		return new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(radius),
				new BorderWidths(1)));
	}

	private static Background createBackground(double radius, Color color) {
		return new Background(new BackgroundFill(color, new CornerRadii(radius), new Insets(0)));
	}

}
