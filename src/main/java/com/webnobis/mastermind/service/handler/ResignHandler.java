package com.webnobis.mastermind.service.handler;

import java.util.function.Function;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;

public class ResignHandler extends AbstractFindHandler {

	private final Function<Solution, String> solutionTransformer;

	public ResignHandler(GameStore gameStore, Function<Solution, String> solutionTransformer) {
		super(gameStore);
		this.solutionTransformer = solutionTransformer;
	}

	@Override
	protected void handle(Context ctx, GameWithSolution gameWithSolution) throws Exception {
		ctx.getResponse()
				.contentType(Constants.CONTENT_TYPE)
				.send(solutionTransformer.apply(gameWithSolution.getSolution()));
	}

}
