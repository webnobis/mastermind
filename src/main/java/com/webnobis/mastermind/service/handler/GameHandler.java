package com.webnobis.mastermind.service.handler;

import java.util.function.Function;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;

public class GameHandler extends AbstractFindHandler {

	private final Function<Game, String> gameTransformer;

	private final Function<GameWithSolution, String> finishedGameTransformer;

	public GameHandler(GameStore gameStore, Function<Game, String> gameTransformer, Function<GameWithSolution, String> finishedGameTransformer) {
		super(gameStore);
		this.gameTransformer = gameTransformer;
		this.finishedGameTransformer = finishedGameTransformer;
	}

	@Override
	protected void handle(Context ctx, GameWithSolution gameWithSolution) throws Exception {
		ctx.getResponse()
				.contentType(Constants.CONTENT_TYPE)
				.send(transform(gameWithSolution));
	}

	private String transform(GameWithSolution gameWithSolution) {
		if (gameWithSolution.getGame().isFinish()) {
			return finishedGameTransformer.apply(gameWithSolution);
		} else {
			return gameTransformer.apply(gameWithSolution.getGame());
		}
	}

}
