package com.webnobis.mastermind.service.handler;

import java.util.function.Function;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;

public class GameHandler extends AbstractFindHandler {

	private final Function<Game, String> gameTransformer;

	public GameHandler(GameStore gameStore, Function<Game, String> gameTransformer) {
		super(gameStore);
		this.gameTransformer = gameTransformer;
	}

	@Override
	protected void handle(Context ctx, GameWithSolution gameWithSolution) throws Exception {
		ctx.getResponse()
				.contentType(Constants.CONTENT_TYPE)
				.send(gameTransformer.apply(gameWithSolution.getGame()));
	}

}
