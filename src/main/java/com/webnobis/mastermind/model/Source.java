package com.webnobis.mastermind.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Source<T> {

	static final int LIMIT = 20;

	private final List<T> sources;

	Source(List<T> sources) {
		this.sources = sources;
	}

	@SafeVarargs
	public static <T> Source<T> of(T... sources) {
		return new Source<>(Arrays.stream(sources).limit(LIMIT).collect(Collectors.toList()));
	}

	public List<T> getSources() {
		return sources;
	}

	@Override
	public String toString() {
		return "Source [sources=" + sources + "]";
	}

}
