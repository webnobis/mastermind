package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Status;
import com.webnobis.mastermind.model.Try;

public interface VerifyService {

	static <T> Try<T> verify(Try<T> nextTry, List<T> solution) {
		List<T> tmpSolution = new ArrayList<>(Objects.requireNonNull(solution));
		List<T> test = Objects.requireNonNull(nextTry).getTestParts();
		return new Try<>(nextTry.getTimestamp(), test, IntStream.range(0, test.size()).mapToObj(i -> {
			T nextTestPart = test.get(i);
			if (i < solution.size() && Optional.ofNullable(solution.get(i))
					.map(value -> value.equals(nextTestPart))
					.orElse(nextTestPart == null)) {
				tmpSolution.remove(nextTestPart);
				return Status.CORRECT_PLACE;
			} else {
				return (tmpSolution.remove(nextTestPart)) ? Status.CONTAINED : Status.MISSING;
			}
		}).collect(Collectors.toList()));
	}

	static <T> boolean verifyFinish(Try<T> nextTry, List<T> solution) {
		return Objects.equals(Objects.requireNonNull(nextTry).getTestParts(), solution);
	}

}
