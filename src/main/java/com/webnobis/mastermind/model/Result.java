package com.webnobis.mastermind.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Result<T> extends Source<T> {

	private final List<ResultType> results;

	private Result(List<T> sources, List<ResultType> results) {
		super(sources);
		this.results = results;
	}

	public static <T> Result<T> of(Source<T> source, ResultType... resultTypes) {
		List<T> sources = Objects.requireNonNull(source, "source is null").getSources();
		return new Result<>(sources, Arrays.stream(resultTypes).limit(sources.size()).collect(Collectors.toList()));
	}

	public List<ResultType> getResults() {
		return results;
	}

	@Override
	public String toString() {
		return "Result [results=" + results + ", sources=" + getSources() + "]";
	}

}
