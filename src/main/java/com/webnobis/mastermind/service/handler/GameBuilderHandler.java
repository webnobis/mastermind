package com.webnobis.mastermind.service.handler;

import java.util.function.Function;

import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.GameBuilder;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.TypedData;

@Path("")
public class GameBuilderHandler implements Handler {

	private final GameBuilder gameBuilder;

	private final GameStore gameStore;

	private final Function<String, GameConfig> gameConfigTransformer;

	public GameBuilderHandler(GameBuilder gameBuilder, GameStore gameStore, Function<String, GameConfig> gameConfigTransformer) {
		this.gameBuilder = gameBuilder;
		this.gameStore = gameStore;
		this.gameConfigTransformer = gameConfigTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.getRequest()
				.getBody()
				.map(TypedData::getText)
				.map(gameConfigTransformer::apply)
				.map(config -> gameBuilder.build(config.getMin(), config.getMax(), config.getSize()))
				.map(gameStore::store)
				.then(id -> ctx.redirect(Constants.REDIRECT_CODE, id));
	}

}