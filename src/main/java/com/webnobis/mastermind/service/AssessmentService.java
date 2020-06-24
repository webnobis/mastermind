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

public interface AssessmentService {

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
