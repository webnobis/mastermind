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

	// only for JAXB
	Play() {
		this.id = null;
		this.type = null;
		this.cols = 0;
		this.rows = 0;
		this.results = new ArrayList<>();
		this.source = null;
	}

	Play(String id, Class<T> type, int cols, int rows, List<Result<T>> results, Source<T> source) {
		this.id = Objects.requireNonNull(id, "id is null");
		this.type = Objects.requireNonNull(type, "type is null");
		this.cols = cols;
		this.rows = rows;
		this.results = Objects.requireNonNull(results, "results is null");
		this.source = source;
	}

	public static <T> Play<T> of(Class<T> type, int cols) {
		return of(type, cols, -1);
	}

	public static <T> Play<T> of(Class<T> type, int cols, int rows) {
		return new Play<>(UUID.randomUUID().toString(), type, cols, rows, Collections.emptyList(), null);
	}

	public Play<T> withAddedResult(Result<T> result) {
		return Optional.ofNullable(result).map(Stream::of)
				.map(resultStream -> Stream.concat(results.stream(), resultStream).collect(Collectors.toList()))
				.map(resultList -> new Play<>(id, type, cols, rows, resultList, source)).orElse(this);
	}

	public Play<T> withSource(Source<T> source) {
		return new Play<>(id, type, cols, rows, results, source);
	}

	public String getId() {
		return id;
	}

	public Class<T> getType() {
		return type;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public List<Result<T>> getResults() {
		return results;
	}

	public Source<T> getSource() {
		return source;
	}

	@XmlElement(name = "finish")
	public boolean isFinish() {
		return isSolved() || (rows > -1 && results.size() >= rows);
	}

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
