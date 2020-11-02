package com.webnobis.mastermind.view;

import java.util.EnumSet;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.SourceGenerator;

/**
 * Color type source generator
 * 
 * @author steffen
 *
 */
public interface ColorTypeSourceGenerator {

	/**
	 * Creates Source with count of cols random color types.<br>
	 * All color types will be used, except {@link ColorType#BLACK} and
	 * {@link ColorType#WHITE}.
	 * 
	 * @param cols cols
	 * @return Source with count of cols random color types
	 * @see SourceGenerator#generateFromPool(int, java.util.Collection)
	 */
	static Source<ColorType> generate(int cols) {
		return SourceGenerator.generateFromPool(cols,
				EnumSet.complementOf(EnumSet.of(ColorType.BLACK, ColorType.WHITE)));
	}

}
