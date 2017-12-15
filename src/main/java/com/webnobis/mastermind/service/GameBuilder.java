package com.webnobis.mastermind.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.xml.XmlGame;
import com.webnobis.mastermind.model.xml.XmlGameWithSolution;
import com.webnobis.mastermind.model.xml.XmlSolution;

public class GameBuilder {

	private final Supplier<IntStream> ints;

	GameBuilder(Supplier<IntStream> ints) {
		this.ints = ints;
	}

	public GameBuilder() {
		this(() -> new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes()).ints());
	}

	public GameWithSolution build(int min, int max, int size) {
		return new XmlGameWithSolution(new XmlGame(null, null), new XmlSolution(createValues(min, max, size)));
	}

	private List<Integer> createValues(int min, int max, int size) {
		return ints.get()
				.filter(i -> i >= min)
				.filter(i -> i <= max)
				.limit(size)
				.boxed()
				.collect(Collectors.toList());
	}

}
