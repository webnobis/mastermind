package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Status;
import com.webnobis.mastermind.model.Trying;
import com.webnobis.mastermind.model.Verification;
import com.webnobis.mastermind.model.VerifiedTrying;

public interface VerifyService {

	static <T> VerifiedTrying<T> verify(Trying<T> trying, Solution<T> solution) {
		List<T> tmpSolution = new ArrayList<>(Objects.requireNonNull(solution).getPositions());
		List<T> positions = Objects.requireNonNull(trying).getPositions();
		return new VerifiedTrying<T>(trying, new Verification(IntStream.range(0, positions.size())
				.mapToObj(i -> {
					T nextTestPart = positions.get(i);
					if (i < solution.getPositions().size() && Optional.ofNullable(solution.getPositions().get(i)).map(value -> value.equals(nextTestPart))
							.orElse(nextTestPart == null)) {
						tmpSolution.remove(nextTestPart);
						return Status.CORRECT_PLACE;
					} else {
						return (tmpSolution.remove(nextTestPart)) ? Status.CONTAINED : null;
					}
				}).filter(status -> status != null)
				.collect(Collectors.toList())));
	}

	static <T> boolean isFinish(Collection<? extends Trying<T>> tryings, Solution<T> solution) {
		return Objects.requireNonNull(tryings).stream()
				.map(Trying::getPositions)
				.anyMatch(trying -> Objects.equals(tryings, solution.getPositions()));
	}

}
