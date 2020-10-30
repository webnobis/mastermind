package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

/**
 * Assessment service, builds the assessment logic of the Mastermind game
 * 
 * @author steffen
 *
 */
public interface AssessmentService {

	/**
	 * Assess the try source depending on the solution source.<br>
	 * Each equal position results in {@link ResultType#EXACT}.<br>
	 * All others in solution containing try elements resulting in
	 * {@link ResultType#PRESENT}.
	 * 
	 * @param <T>            type of elements
	 * @param solutionSource comparison solution source
	 * @param trySource      try source
	 * @return result, containing the try source and all got result types
	 * @see ResultType
	 */
	static <T> Result<T> assess(Source<T> solutionSource, Source<T> trySource) {
		Objects.requireNonNull(solutionSource, "solutionSource is null");
		Objects.requireNonNull(trySource, "trySource is null");

		List<T> presents = new ArrayList<>(
				trySource.getSources().stream().limit(solutionSource.getSources().size()).collect(Collectors.toList()));
		List<ResultType> results = new ArrayList<>();
		IntStream.range(0, solutionSource.getSources().size()).limit(trySource.getSources().size()).forEach(i -> {
			T tSolution = solutionSource.getSources().get(i);
			T tTry = trySource.getSources().get(i);
			if (Objects.equals(tSolution, tTry)) {
				results.add(ResultType.EXACT);
				presents.remove(tTry);
			}
		});
		presents.retainAll(solutionSource.getSources());
		Stream.generate(() -> ResultType.PRESENT).limit(presents.size()).forEach(results::add);

		return Result.of(trySource, results.toArray(ResultType[]::new));
	}

}
