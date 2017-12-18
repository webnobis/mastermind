package com.webnobis.mastermind.service.handler;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public abstract class AbstractFindHandler implements Handler {

	protected final GameStore gameStore;

	protected AbstractFindHandler(GameStore gameStore) {
		this.gameStore = gameStore;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		String id = ctx.getPathTokens().get(Constants.ID_TOKEN);
		handle(ctx, Optional.ofNullable(id)
				.map(gameStore::find)
				.orElseThrow(() -> new NoSuchElementException(String.format("No game with id %s found.", id))));
	}

	protected abstract void handle(Context ctx, GameWithSolution gameWithSolution) throws Exception;

}
