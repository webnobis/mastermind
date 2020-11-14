package com.webnobis.mastermind.view.node;

import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.view.Readable;
import com.webnobis.mastermind.view.Updateable;

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

/**
 * Pin node, the graphical representation of a color type<br>
 * Each node has a circle, filled with the resulting color, except the
 * {@link ColorType#HOLE}, it's only black bordered.
 * 
 * @author steffen
 *
 */
public class PinNode extends Control implements Updateable<ColorType>, Readable<ColorType> {

	/**
	 * Circle size of all colors, except {@link ColorType#BLACK},
	 * {@link ColorType#WHITE}
	 */
	static final double BIG = 25.0;

	/**
	 * Circle size of {@link ColorType#BLACK} and {@link ColorType#WHITE}
	 */
	static final double SMALL = 15.0;

	private final AtomicReference<ColorType> colorTypeRef;

	/**
	 * Pin node of {@link ColorType#HOLE}
	 */
	public PinNode() {
		this(null);
	}

	/**
	 * Pin node of color type.<br>
	 * If color type is null, {@link ColorType#HOLE} is used.
	 * 
	 * @param colorType color type
	 */
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

	/**
	 * Updates the pin node with the new color type.<br>
	 * If color type is null, {@link ColorType#HOLE} is used.
	 * 
	 * @param colorType color type
	 */
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
		return new Background(new BackgroundFill(color, new CornerRadii(radius), Insets.EMPTY));
	}

}
