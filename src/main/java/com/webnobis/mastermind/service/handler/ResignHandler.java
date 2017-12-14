package com.webnobis.mastermind.service.handler;

import java.util.Optional;
import java.util.function.Function;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class ResignHandler implements Handler {

	private final GameStore gameStore;

	private final Function<Solution, String> solutionTransformer;

	public ResignHandler(GameStore gameStore, Function<Solution, String> solutionTransformer) {
		this.gameStore = gameStore;
		this.solutionTransformer = solutionTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		Optional.ofNullable(ctx.getPathTokens().get(Constants.ID_TOKEN))
				.map(gameStore::find)
				.map(GameWithSolution::getSolution)
				.map(solutionTransformer::apply)
				.ifPresent(text -> ctx.getResponse()
						.contentType(Constants.CONTENT_TYPE)
						.send(text));
	}

}
