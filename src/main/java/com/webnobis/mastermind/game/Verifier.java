package com.webnobis.mastermind.game;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FunctionalInterface
public interface Verifier<E> {
	
	List<E> expected();
	
	default List<Result> verifyEasy(List<E> test) {
		Set<Integer> correct = IntStream.range(0, expected().size())
				.limit(test.size())
			.filter(i -> expected().get(i).equals(test.get(i)))
			.boxed()
			.collect(Collectors.toSet());
		
		List<E> others = IntStream.range(0, expected().size())
				.filter(i -> !correct.contains(i))
				.mapToObj(expected()::get)
				.collect(Collectors.toList());
		
		return IntStream.range(0, test.size())
				.mapToObj(i -> {
					if (correct.contains(i)) {
						return Result.CORRECT_PLACE;
					} else if (others.remove(test.get(i))) {
						return Result.CONTAINED;
					} else {
						return Result.MISSING;
					}
				}).collect(Collectors.toList());
	}
	
	default List<Result> verifyHard(List<E> test) {
		return verifyEasy(test).stream()
				.filter(result -> Result.CORRECT_PLACE.equals(result) || Result.CONTAINED.equals(result))
				.sorted()
				.collect(Collectors.toList());
	}

}
