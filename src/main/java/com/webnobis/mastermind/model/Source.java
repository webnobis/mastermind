package com.webnobis.mastermind.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Source<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int LIMIT = 20;

	private final List<T> sources;

	Source(List<T> sources) {
		this.sources = sources;
	}

	@SafeVarargs
	public static <T extends Serializable> Source<T> of(T... sources) {
		return new Source<>(Arrays.stream(sources).limit(LIMIT).collect(Collectors.toList()));
	}

	public List<T> getSources() {
		return sources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sources == null) ? 0 : sources.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Source other = (Source) obj;
		if (sources == null) {
			if (other.sources != null) {
				return false;
			}
		} else if (!sources.equals(other.sources)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Source [sources=" + sources + "]";
	}

}
