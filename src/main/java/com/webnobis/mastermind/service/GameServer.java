package com.webnobis.mastermind.service;

import static com.webnobis.mastermind.service.Constants.ALIVE_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_BUILDER_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_PATH;
import static com.webnobis.mastermind.service.Constants.RESIGN_PATH;
import static com.webnobis.mastermind.service.Constants.TRY_PATH;

import com.webnobis.mastermind.model.transformer.GameAndSolutionTransformer;
import com.webnobis.mastermind.model.transformer.GameConfigTransformer;
import com.webnobis.mastermind.model.transformer.TryTransformer;
import com.webnobis.mastermind.service.handler.AliveHandler;
import com.webnobis.mastermind.service.handler.GameBuilderHandler;
import com.webnobis.mastermind.service.handler.GameHandler;
import com.webnobis.mastermind.service.handler.ResignHandler;
import com.webnobis.mastermind.service.handler.TryHandler;
import com.webnobis.mastermind.service.store.GlobalGameStore;

import ratpack.api.UncheckedException;
import ratpack.error.ClientErrorHandler;
import ratpack.error.ServerErrorHandler;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.server.RatpackServer;

public class GameServer {

	private final Env env;

	private final Handler aliveHandler;

	private final Handler gameBuilderHandler;

	private final Handler gameHandler;

	private final Handler tryHandler;

	private final Handler resignHandler;

	GameServer(Env env, Handler aliveHandler, Handler gameBuilderHandler, Handler gameHandler, Handler tryHandler, Handler resignHandler) {
		this.env = env;
		this.aliveHandler = aliveHandler;
		this.gameBuilderHandler = gameBuilderHandler;
		this.gameHandler = gameHandler;
		this.tryHandler = tryHandler;
		this.resignHandler = resignHandler;
	}

	public static GameServer build() {
		return new GameServer(Env.INSTANCE, new AliveHandler(),
				new GameBuilderHandler(new GameBuilder(), GlobalGameStore.INSTANCE, GameConfigTransformer::transform),
				new GameHandler(GlobalGameStore.INSTANCE, GameAndSolutionTransformer::transform),
				new TryHandler(GlobalGameStore.INSTANCE, TryTransformer::transform, AssessmentService::assess, GameUpdateService::update),
				new ResignHandler(GlobalGameStore.INSTANCE, GameAndSolutionTransformer::transform));
	}

	Env getEnv() {
		return env;
	}

	Handler getAliveHandler() {
		return aliveHandler;
	}

	Handler getGameBuilderHandler() {
		return gameBuilderHandler;
	}

	Handler getGameHandler() {
		return gameHandler;
	}

	Handler getTryHandler() {
		return tryHandler;
	}

	Handler getResignHandler() {
		return resignHandler;
	}

	public RatpackServer buildServer() {
		try {
			return RatpackServer.of(spec -> spec
					.serverConfig(config -> config
							.port(env.getPort()))
					.registryOf(registry -> registry
							.add(new ClientErrorThrowHandler())
							.add(new ServerErrorThrowHandler()))
					.handlers(chain -> chain
							.get(ALIVE_PATH, aliveHandler)
							.post(GAME_BUILDER_PATH, gameBuilderHandler)
							.get(GAME_PATH, gameHandler)
							.post(TRY_PATH, tryHandler)
							.get(RESIGN_PATH, resignHandler)));
		} catch (Exception e) {
			throw new UncheckedException(e);
		}
	}

	class ClientErrorThrowHandler implements ClientErrorHandler {

		@Override
		public void error(Context context, int statusCode) throws Exception {
			throw new IllegalStateException("client error " + statusCode);
		}

	}

	class ServerErrorThrowHandler implements ServerErrorHandler {

		@Override
		public void error(Context context, Throwable throwable) throws Exception {
			throw new IllegalStateException("server error " + throwable.getMessage());
		}

	}

}
