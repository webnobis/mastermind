package com.webnobis.mastermind.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Try;

public interface GameService {

	static <T> Game<T> newGame(List<T> solution) {
		return new Game<T>(UUID.randomUUID().toString(), solution, Collections.emptySortedSet(), false);
	}

	static <T> Game<T> nextTry(Game<T> game, List<T> nextTry) {
		List<T> solution = Objects.requireNonNull(game).getSolution();
		return new Game<T>(game.getId(), solution,
				Stream.concat(game.getTries().stream(), Stream.of(new Try<T>(System.currentTimeMillis(), Objects.requireNonNull(nextTry))))
						.collect(Collectors.toCollection(TreeSet::new)), false);
	}

	static <T> Game<T> verify(Game<T> game) {
		List<T> solution = Objects.requireNonNull(game).getSolution();
		SortedSet<Try<T>> tries = Objects.requireNonNull(game.getTries()).stream()
				.map(nextTry -> VerifyService.verify(nextTry, solution))
				.collect(Collectors.toCollection(TreeSet::new));
		return new Game<T>(game.getId(), solution, tries, tries.stream()
				.anyMatch(nextTry -> VerifyService.verifyFinish(nextTry, solution)));
	}

}
