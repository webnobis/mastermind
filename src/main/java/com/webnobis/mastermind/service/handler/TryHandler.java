package com.webnobis.mastermind.service.handler;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.http.TypedData;

public class TryHandler extends AbstractFindHandler {

	private final Function<String, Try> tryTransformer;

	private final BiFunction<Solution, Try, TryWithAssessment> assessmentService;

	private final BiFunction<GameWithSolution, TryWithAssessment, GameWithSolution> gameUpdateService;

	public TryHandler(GameStore gameStore, Function<String, Try> tryTransformer, BiFunction<Solution, Try, TryWithAssessment> assessmentService,
			BiFunction<GameWithSolution, TryWithAssessment, GameWithSolution> gameUpdateService) {
		super(gameStore);
		this.tryTransformer = tryTransformer;
		this.assessmentService = assessmentService;
		this.gameUpdateService = gameUpdateService;
	}

	@Override
	protected void handle(Context ctx, GameWithSolution gameWithSolution) throws Exception {
		ctx.getRequest()
				.getBody()
				.map(TypedData::getText)
				.map(tryTransformer::apply)
				.map(theTry -> assessmentService.apply(gameWithSolution.getSolution(), theTry))
				.map(tryWithAssessment -> gameUpdateService.apply(gameWithSolution, tryWithAssessment))
				.map(gameStore::store)
				.then(id -> ctx.redirect(Constants.REDIRECT_CODE, id));
	}

}
