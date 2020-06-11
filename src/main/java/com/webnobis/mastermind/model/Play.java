package com.webnobis.mastermind.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A play of the Mastermind game
 * 
 * @author steffen
 *
 * @param <T> type of findings, such as numbers, colors, dogs and cats, or
 *            others
 */
@XmlType(propOrder = { "id", "type", "cols", "rows", "source", "finish", "solved", "results" })
@XmlRootElement
public class Play<T> {

	@XmlAttribute
	private final String id;

	@XmlAttribute
	private final Class<T> type;

	@XmlAttribute
	private final int cols;

	@XmlAttribute
	private final int rows;

	@XmlElementWrapper(name = "results")
	@XmlElement(name = "try")
	private final List<Result<T>> results;

	@XmlElement
	private final Source<T> source;

	/*
	 * Only for JAXB
	 */
	Play() {
		this.id = null;
		this.type = null;
		this.cols = 0;
		this.rows = 0;
		this.results = new ArrayList<>();
		this.source = null;
	}

	/*
	 * Only used directly from tests
	 * 
	 * @see Play#of(Class, int)
	 * 
	 * @see Play#of(Class, int, int)
	 */
	Play(String id, Class<T> type, int cols, int rows, List<Result<T>> results, Source<T> source) {
		this.id = Objects.requireNonNull(id, "id is null");
		this.type = Objects.requireNonNull(type, "type is null");
		this.cols = cols;
		this.rows = rows;
		this.results = Objects.requireNonNull(results, "results is null");
		this.source = source;
	}

	/**
	 * A new typed play with generated id, columns, unlimited try rows, empty
	 * results and without the solution source
	 * 
	 * @param <T>  type of findings
	 * @param type type class
	 * @param cols columns, it's the count of searched findings
	 * @return new Play
	 * @see UUID#randomUUID()
	 */
	public static <T> Play<T> of(Class<T> type, int cols) {
		return of(type, cols, -1);
	}

	/**
	 * A new typed play with generated id, columns, maximum try rows, empty results
	 * and without the solution source
	 * 
	 * @param <T>  type of findings
	 * @param type type class
	 * @param cols columns, it's the count of searched findings
	 * @param rows maximum try rows, until the Play is finish, in-depending the
	 *             solution was found
	 * @return new Play
	 * @see UUID#randomUUID()
	 */
	public static <T> Play<T> of(Class<T> type, int cols, int rows) {
		return new Play<>(UUID.randomUUID().toString(), type, cols, rows, Collections.emptyList(), null);
	}

	/**
	 * Clones the play with the added result at the end of all results
	 * 
	 * @param result result
	 * @return the cloned play
	 */
	public Play<T> withAddedResult(Result<T> result) {
		return Optional.ofNullable(result).map(Stream::of)
				.map(resultStream -> Stream.concat(results.stream(), resultStream).collect(Collectors.toList()))
				.map(resultList -> new Play<>(id, type, cols, rows, resultList, source)).orElse(this);
	}

	/**
	 * Clones the play with the set solution source
	 * 
	 * @param solution source
	 * @return the cloned play
	 */
	public Play<T> withSource(Source<T> source) {
		return new Play<>(id, type, cols, rows, results, source);
	}

	/**
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return type
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * 
	 * @return columns, the count of searched findings
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * 
	 * @return maximum try rows, until the play is finish or -1 if unlimited
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 
	 * @return history of try results
	 */
	public List<Result<T>> getResults() {
		return results;
	}

	/**
	 * 
	 * @return solution source, otherwise null
	 */
	public Source<T> getSource() {
		return source;
	}

	/**
	 * True, if solved or maximum try rows are reached, if not unlimited tries
	 * 
	 * @return finish
	 * @see Play#isSolved()
	 * @see Play#getRows()
	 */
	@XmlElement(name = "finish")
	public boolean isFinish() {
		return isSolved() || (rows > -1 && results.size() >= rows);
	}

	/**
	 * True, if results contains at least a row with column count result types of
	 * "exact"
	 * 
	 * @return solved
	 * @see Play#getCols()
	 * @see Play#getResults()
	 * @see ResultType#EXACT
	 */
	@XmlElement(name = "solved")
	public boolean isSolved() {
		return results.stream().map(Result::getResults).filter(list -> list.size() == getCols())
				.anyMatch(list -> list.stream().allMatch(ResultType.EXACT::equals));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return Objects.equals(id, Play.class.cast(obj).id);
	}

	@Override
	public String toString() {
		return "Play [id=" + id + "]";
	}

}
