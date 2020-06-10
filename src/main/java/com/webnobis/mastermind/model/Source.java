package com.webnobis.mastermind.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Source<T> {

	static final int LIMIT = 20;

	@XmlElement(name = "source")
	private final List<T> sources;

	// only for JAXB
	Source() {
		this(new ArrayList<>());
	}

	Source(List<T> sources) {
		this.sources = Objects.requireNonNull(sources, "sources is null");
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
