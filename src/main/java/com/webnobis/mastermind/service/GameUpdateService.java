package com.webnobis.mastermind.service;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.model.xml.XmlGame;
import com.webnobis.mastermind.model.xml.XmlGameWithSolution;

public abstract class GameUpdateService {

	private GameUpdateService() {
	}

	public static GameWithSolution update(GameWithSolution gameWithSolution, TryWithAssessment tryWithAssessment) {
		Objects.requireNonNull(gameWithSolution, "gameWithSolution is null");
		Objects.requireNonNull(tryWithAssessment, "tryWithAssessment is null");
		return new XmlGameWithSolution(gameWithSolution.getId(), update(gameWithSolution.getGame(), tryWithAssessment), gameWithSolution.getSolution());
	}

	private static Game update(Game game, TryWithAssessment tryWithAssessment) {
		return new XmlGame(game.getConfig(), Stream.concat(game.getTries().stream(), Stream.of(tryWithAssessment)).collect(Collectors.toList()));
	}

}
