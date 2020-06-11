package com.webnobis.mastermind.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A source as solution or try
 * 
 * @author steffen
 *
 * @param <T>
 */
@XmlRootElement
public class Source<T> {

	/*
	 * Maximum count of source parts: 20
	 * 
	 * @see Source#of(Object...)
	 */
	static final int LIMIT = 20;

	@XmlElement(name = "source")
	private final List<T> sources;

	/*
	 * Only for JAXB
	 */
	Source() {
		this(new ArrayList<>());
	}

	/*
	 * Only used by inherit classes
	 * 
	 * @see Source#of(Object...)
	 */
	Source(List<T> sources) {
		this.sources = Objects.requireNonNull(sources, "sources is null");
	}

	/**
	 * A new typed source as solution or try with up to 20 sources
	 * 
	 * @param <T>     type of sources
	 * @param sources sources
	 * @return new source
	 */
	@SafeVarargs
	public static <T> Source<T> of(T... sources) {
		return new Source<>(Arrays.stream(sources).limit(LIMIT).collect(Collectors.toList()));
	}

	/**
	 * 
	 * @return sources
	 */
	public List<T> getSources() {
		return sources;
	}

	@Override
	public String toString() {
		return "Source [sources=" + sources + "]";
	}

}
