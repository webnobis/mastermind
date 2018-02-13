package com.webnobis.mastermind.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.model.xml.XmlAssessment;
import com.webnobis.mastermind.model.xml.XmlTryWithAssessment;

public abstract class AssessmentService {

	private AssessmentService() {
	}

	public static TryWithAssessment assess(Solution solution, Try theTry) {
		List<Integer> values = Objects.requireNonNull(solution, "solution is null").getValues();
		List<Integer> ideas = Objects.requireNonNull(theTry, "theTry is null").getIdeas();

		Set<Integer> correct = IntStream.range(0, values.size())
				.limit(ideas.size())
				.filter(i -> isEqual(values.get(i), ideas.get(i)))
				.boxed()
				.collect(Collectors.toSet());

		List<Integer> others = IntStream.range(0, values.size())
				.limit(ideas.size())
				.filter(i -> !correct.contains(i))
				.mapToObj(values::get)
				.collect(Collectors.toList());

		List<Result> results = IntStream.range(0, ideas.size())
				.mapToObj(i -> {
					if (correct.contains(i)) {
						return Result.CORRECT_PLACE;
					} else if (others.remove(ideas.get(i))) {
						return Result.CONTAINED;
					} else {
						return null;
					}
				})
				.filter(result -> result != null)
				.sorted()
				.collect(Collectors.toList());

		return new XmlTryWithAssessment(theTry, new XmlAssessment(results));
	}

	private static boolean isEqual(Integer solutionValue, Integer tryValue) {
		return Optional.ofNullable(solutionValue)
				.map(value -> value.equals(tryValue))
				.orElse(tryValue == null);
	}

}
