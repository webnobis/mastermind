package com.webnobis.mastermind.service.handler;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.service.GameCreator;
import com.webnobis.mastermind.service.Transformer;
import com.webnobis.mastermind.service.TryChecker;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.TypedData;

public class GameHandler implements Handler {

	private final String contentType;

	private final Transformer<String, Try> tryTransformer;

	private final TryChecker tryChecker;

	private final GameStore gameStore;

	private final Transformer<Game, String> gameTransformer;

	public GameHandler(String contentType, Transformer<String, Try> tryTransformer, TryChecker tryChecker, GameStore gameStore, Transformer<Game, String> gameTransformer) {
		this.contentType = contentType;
		this.tryTransformer = tryTransformer;
		this.tryChecker = tryChecker;
		this.gameStore = gameStore;
		this.gameTransformer = gameTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.getRequest()
				.getBody()
				.map(TypedData::getText)
				.map(tryTransformer::transform)
				.map(tryChecker::check)
				.map(checkedTry -> GameCreator.create(gameStore.find(checkedTry.getId()), checkedTry))
				.map(gameTransformer::transform)
				.then(text -> ctx.getResponse()
						.contentType(contentType)
						.send(text));
	}

}
