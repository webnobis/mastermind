package com.webnobis.mastermind.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Game;

public interface GameService {

	static <T> Game<T> newGame(List<T> solution) {
		return new Game<T>(UUID.randomUUID().toString(), solution, Collections.emptyList(), Collections.emptyList(),
				false);
	}

	static <T> Game<T> nextTry(Game<T> game, List<T> nextTry) {
		List<T> solution = Objects.requireNonNull(game).getSolution();
		return new Game<T>(game.getId(), solution,
				Stream.concat(game.getNextTry().stream(), Stream.of(Objects.requireNonNull(nextTry)))
						.collect(Collectors.toList()),
				game.getVerify(), false);
	}

	static <T> Game<T> verify(Game<T> game) {
		List<T> solution = Objects.requireNonNull(game).getSolution();
		List<List<T>> nextTry = game.getNextTry();
		return new Game<T>(game.getId(), solution, nextTry, VerifyService.verifyAll(nextTry, solution),
				VerifyService.verifyFinish(nextTry, solution));
	}

}
