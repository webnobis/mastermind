package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Status;

public interface VerifyService {

	static <T> List<List<Status>> verifyAll(List<List<T>> nextTry, List<T> solution) {
		return Objects.requireNonNull(nextTry).stream()
				.map(tryItem -> verify(tryItem, solution))
				.collect(Collectors.toList());
	}

	static <T> List<Status> verify(List<T> nextTry, List<T> solution) {
		List<T> tmpSolution = new ArrayList<>(Objects.requireNonNull(solution));
		return IntStream.range(0, Objects.requireNonNull(nextTry).size()).mapToObj(i -> {
			T nextTryPart = nextTry.get(i);
			if (i < solution.size() && Optional.ofNullable(solution.get(i))
					.map(value -> value.equals(nextTryPart))
					.orElse(nextTryPart == null)) {
				tmpSolution.remove(nextTryPart);
				return Status.CORRECT_PLACE;
			} else {
				return (tmpSolution.remove(nextTryPart)) ? Status.CONTAINED : Status.MISSING;
			}
		}).collect(Collectors.toList());
	}

	static <T> boolean verifyFinish(List<List<T>> nextTry, List<T> solution) {
		return Objects.requireNonNull(nextTry).stream()
				.anyMatch(tryItem -> Objects.equals(tryItem, solution));
	}

}
