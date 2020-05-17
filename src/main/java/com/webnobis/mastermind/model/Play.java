package com.webnobis.mastermind.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Play<T> {

	private final int rows;

	private final List<Result<T>> results;

	private final Source<T> source;

	Play(int rows, List<Result<T>> results, Source<T> source) {
		this.rows = rows;
		this.results = results;
		this.source = source;
	}

	public static <T> Play<T> of(Source<T> source) {
		return of(-1, source);
	}

	public static <T> Play<T> of(int rows, Source<T> source) {
		return new Play<>(rows, Collections.emptyList(), Objects.requireNonNull(source, "source is null"));
	}

	public Play<T> ofAddedResult(Result<T> result) {
		return new Play<>(rows,
				Stream.concat(results.stream(), Stream.of(Objects.requireNonNull(result, "result is null")))
						.collect(Collectors.toList()),
				source);
	}

	public int getCols() {
		return Objects.requireNonNull(source.getSources(), "sources of source is null").size();
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

	public boolean isFinish() {
		return isSolved() || (rows > -1 && results.size() >= rows);
	}

	public boolean isSolved() {
		return results.stream().map(Result::getResults).filter(list -> list.size() == getCols()).anyMatch(list -> list.stream().allMatch(ResultType.EXACT::equals));
	}

	@Override
	public String toString() {
		return "Play [rows=" + rows + ", source=" + source + ", results=" + results.size() + ", finish=" + isFinish()
				+ ", solved=" + isSolved() + "]";
	}

}
