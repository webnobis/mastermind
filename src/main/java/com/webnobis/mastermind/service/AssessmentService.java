package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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

		List<ResultType> results = new ArrayList<>();
		int size = Math.min(trySource.getSources().size(), solutionSource.getSources().size());
		Collection<T> tmpSolutionSource = new ArrayList<>(solutionSource.getSources().subList(0, size));
		Collection<T> tmpTrySources = new ArrayList<>(trySource.getSources().subList(0, size));
		IntStream.range(0, size).forEach(i -> {
			T tSolution = solutionSource.getSources().get(i);
			T tTry = trySource.getSources().get(i);
			if (Objects.equals(tSolution, tTry)) {
				results.add(ResultType.EXACT);
				tmpSolutionSource.remove(tSolution);
				tmpTrySources.remove(tTry);
			}
		});
		tmpTrySources.stream().filter(tmpSolutionSource::remove).map(unused -> ResultType.PRESENT)
				.forEach(results::add);

		return Result.of(trySource, results.toArray(ResultType[]::new));
	}

}
