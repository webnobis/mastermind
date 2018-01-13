package com.webnobis.mastermind.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.xml.XmlGame;
import com.webnobis.mastermind.model.xml.XmlGameWithSolution;
import com.webnobis.mastermind.model.xml.XmlSolution;

public class GameBuilder {

	@FunctionalInterface
	private interface IntGenerator {

		IntStream generate(int min, int max, int size);

	}

	private final IntGenerator ints;

	GameBuilder(Supplier<IntStream> ints) {
		this.ints = (min, max, size) -> ints.get();
	}

	public GameBuilder() {
		// long stream before int, because of allow MAX int value
		this.ints = (min, max, size) -> new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes())
				.longs(size, min, max + 1L)
				.boxed()
				.mapToInt(Long::intValue);
	}

	public GameWithSolution build(int min, int max, int size) {
		return new XmlGameWithSolution(UUID.randomUUID().toString(), new XmlGame(null, null), new XmlSolution(createValues(min, max, size)));
	}

	List<Integer> createValues(int min, int max, int size) {
		return ints.generate(min, max, size)
				.filter(i -> i >= min)
				.filter(i -> i <= max)
				.limit(size)
				.boxed()
				.collect(Collectors.toList());
	}

}
