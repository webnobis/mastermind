package com.webnobis.mastermind.service.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.service.GameCreator;
import com.webnobis.mastermind.service.MissingParameterException;
import com.webnobis.mastermind.service.Transformer;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class GameCreatorHandler implements Handler {

	public static final String MIN_PARAM = "min";

	public static final String MAX_PARAM = "max";

	public static final String SIZE_PARAM = "size";

	private static final List<String> keys = Arrays.asList(MIN_PARAM, MAX_PARAM, SIZE_PARAM);

	private final String contentType;

	private final GameCreator gameCreator;

	private final GameStore gameStore;

	private final Transformer<Game, String> gameTransformer;

	public GameCreatorHandler(String contentType, GameCreator gameCreator, GameStore gameStore, Transformer<Game, String> gameTransformer) {
		this.contentType = contentType;
		this.gameCreator = gameCreator;
		this.gameStore = gameStore;
		this.gameTransformer = gameTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		Map<String, Integer> parameters = ctx.getRequest().getQueryParams().entrySet().stream()
				.filter(entry -> keys.contains(entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> Integer.parseInt(entry.getValue())));
		if (parameters.size() < keys.size()) {
			throw new MissingParameterException(String.format("Request contains only query parameters %s, expected are %s.", parameters.keySet(), keys));
		}

		Game game = gameStore.store(gameCreator.create(parameters.get(MIN_PARAM), parameters.get(MAX_PARAM), parameters.get(SIZE_PARAM)));
		ctx.getResponse()
				.contentType(contentType)
				.send(gameTransformer.transform(game));
	}

}
