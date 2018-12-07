package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.NextTry;

public interface GameService {

	static <T> Game<T> newGame(List<T> solution) {
		return new Game<T>(UUID.randomUUID().toString(), solution, Collections.emptyList(), false);
	}

	static <T> Game<T> nextTry(Game<T> game, List<T> nextTry) {
		VerifyService<T> verifyService = new VerifyService<>(game.getSolution());
		List<NextTry<T>> currentTry = new ArrayList<>(game.getNextTry());
		currentTry.add(new NextTry<>(nextTry, verifyService.verify(nextTry)));
		return new Game<T>(game.getId(), game.getSolution(), currentTry, verifyService.verifyFinish(nextTry));
	}

}
