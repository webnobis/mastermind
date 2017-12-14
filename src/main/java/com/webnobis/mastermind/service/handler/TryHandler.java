package com.webnobis.mastermind.service.handler;

import java.util.Optional;
import java.util.function.Function;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.TypedData;

public class TryHandler implements Handler {

	private final Function<String, Try> tryTransformer;

	private final Function<Try, TryWithAssessment> assessmentService;

	private final GameStore gameStore;

	private final Function<Game, String> gameTransformer;

	private final Function<GameWithSolution, String> gameWithSolutionTransformer;

	public TryHandler(Function<String, Try> tryTransformer, Function<Try, TryWithAssessment> assessmentService, GameStore gameStore, Function<Game, String> gameTransformer,
			Function<GameWithSolution, String> gameWithSolutionTransformer) {
		this.tryTransformer = tryTransformer;
		this.assessmentService = assessmentService;
		this.gameStore = gameStore;
		this.gameTransformer = gameTransformer;
		this.gameWithSolutionTransformer = gameWithSolutionTransformer;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.getRequest()
				.getBody()
				.map(TypedData::getText)
				.map(tryTransformer::apply)
				.map(assessmentService::apply)
				.then(assessedTry -> Optional.ofNullable(ctx.getPathTokens().get(Constants.ID_TOKEN))
						.map(gameStore::find)
						.map(this::transform)
						.ifPresent(text -> ctx.getResponse()
								.contentType(Constants.CONTENT_TYPE)
								.send(text)));
	}

	private String transform(GameWithSolution gameWithSolution) {
		if (gameWithSolution.getGame().isFinish()) {
			return gameWithSolutionTransformer.apply(gameWithSolution);
		} else {
			return gameTransformer.apply(gameWithSolution.getGame());
		}
	}

}
