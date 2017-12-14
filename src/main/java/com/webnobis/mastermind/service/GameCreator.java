package com.webnobis.mastermind.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.CheckedTry;
import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;

@FunctionalInterface
public interface GameCreator {

	GameWithSolution create(int min, int max, int size);

	static Game create(Game game, CheckedTry checkedTry) {
		return new Game(game.getId(), Stream.concat(game.getParts().stream(), Stream.of(checkedTry)).collect(Collectors.toList()));
	}

}
