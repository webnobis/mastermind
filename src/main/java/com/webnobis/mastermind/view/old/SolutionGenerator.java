package com.webnobis.mastermind.view.old;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;

public interface SolutionGenerator {

	static Source<ColorType> generateColorTypes(int cols) {
		Random random = new SecureRandom(UUID.randomUUID().toString().getBytes());
		List<ColorType> list = Arrays.stream(ColorType.values()).filter(color -> !ColorType.BLACK.equals(color))
				.filter(color -> !ColorType.WHITE.equals(color)).collect(Collectors.toList());
		return Source.of(Stream.generate(() -> list.get(random.nextInt(list.size()))).limit(cols)
				.toArray(i -> new ColorType[i]));
	}

}
