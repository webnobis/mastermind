package com.webnobis.mastermind.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Result<T extends Serializable> extends Source<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final List<ResultType> results;

	Result(List<T> sources, List<ResultType> results) {
		super(sources);
		this.results = results;
	}

	public static <T extends Serializable> Result<T> of(Source<T> source, ResultType... resultTypes) {
		List<T> sources = Objects.requireNonNull(source, "source is null").getSources();
		return new Result<>(sources, Arrays.stream(resultTypes).limit(sources.size()).collect(Collectors.toList()));
	}

	public List<ResultType> getResults() {
		return results;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Result other = (Result) obj;
		if (results == null) {
			if (other.results != null) {
				return false;
			}
		} else if (!results.equals(other.results)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Result [results=" + results + ", sources=" + getSources() + "]";
	}

}
