package com.webnobis.mastermind.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Trying;
import com.webnobis.mastermind.model.VerifiedTrying;

public interface GameService {

	static <T> Game<Trying<T>,T> newGame(Solution<T> solution) {
		return new Game<>(UUID.randomUUID().toString(), solution, Collections.emptySortedSet(), false);
	}

	static <T> Game<Trying<T>,T> nextTrying(Game<Trying<T>,T> game, List<T> trying) {
		SortedSet<Trying<T>> tryings = new TreeSet<>(Objects.requireNonNull(game).getTryings());
		tryings.add(new Trying<>(tryings.size(), trying));
		return new Game<>(game.getId(), game.getSolution(), tryings, false);
	}

	static <T> Game<VerifiedTrying<T>,T> verify(Game<? extends Trying<T>, T> game) {
		Solution<T> solution = Objects.requireNonNull(game).getSolution();
		SortedSet<VerifiedTrying<T>> tryings = game.getTryings().stream()
				.map(trying -> VerifyService.verify(trying, solution))
				.collect(Collectors.toCollection(TreeSet::new));
		return new Game<>(game.getId(), solution, tryings, VerifyService.isFinish(tryings, solution));
	}

}
