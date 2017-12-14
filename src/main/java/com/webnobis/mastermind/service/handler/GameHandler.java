package com.webnobis.mastermind.service.handler;

import java.util.Optional;
import java.util.function.Function;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class GameHandler implements Handler {

	private final GameStore gameStore;

	private final Function<Game, String> gameTransformer;

	public GameHandler(GameStore gameStore, Function<Game, String> gameTransformer) {
		this.gameStore = gameStore;
		this.gameTransformer = gameTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		Optional.ofNullable(ctx.getPathTokens().get(Constants.ID_TOKEN))
				.map(gameStore::find)
				.map(GameWithSolution::getGame)
				.map(gameTransformer::apply)
				.ifPresent(text -> ctx.getResponse()
						.contentType(Constants.CONTENT_TYPE)
						.send(text));
	}

}
