package com.webnobis.mastermind.view;

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
	 * Creates Source with count of cols random color types.
	 * 
	 * @param cols cols
	 * @return Source with count of cols random color types
	 * @see SourceGenerator#generateFromEnum(int, Class)
	 */
	static Source<ColorType> generate(int cols) {
		return SourceGenerator.generateFromEnum(cols, ColorType.class);
	}

}
