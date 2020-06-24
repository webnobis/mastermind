package com.webnobis.mastermind.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import com.webnobis.mastermind.service.AssessmentService;

/**
 * Result of a try
 * 
 * @author steffen
 *
 * @param <T> type of findings
 */
public class Result<T> extends Source<T> {

	@XmlElement(name = "result")
	private final List<ResultType> results;

	/*
	 * Only for JAXB
	 */
	Result() {
		this(new ArrayList<>(), new ArrayList<>());
	}

	private Result(List<T> sources, List<ResultType> results) {
		super(sources);
		this.results = Objects.requireNonNull(results, "results is null");
	}

	/**
	 * A new typed result with source and maximal source count result types
	 * 
	 * @param <T>         type of findings
	 * @param source      source
	 * @param resultTypes optional result types
	 * @return new result
	 */
	@SafeVarargs
	public static <T> Result<T> of(Source<T> source, ResultType... resultTypes) {
		List<T> sources = Objects.requireNonNull(source, "source is null").getSources();
		return new Result<>(sources, Arrays.stream(resultTypes).limit(sources.size()).collect(Collectors.toList()));
	}

	/**
	 * Try results assessment answer
	 * 
	 * @return results
	 * @see AssessmentService#assess(Source)
	 */
	public List<ResultType> getResults() {
		return results;
	}

	@Override
	public String toString() {
		return "Result [results=" + results + ", sources=" + getSources() + "]";
	}

}
