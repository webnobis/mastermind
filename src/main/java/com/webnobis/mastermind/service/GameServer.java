package com.webnobis.mastermind.service;

import static com.webnobis.mastermind.service.Constants.ALIVE_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_BUILDER_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_PATH;
import static com.webnobis.mastermind.service.Constants.RESIGN_PATH;
import static com.webnobis.mastermind.service.Constants.TRY_PATH;

import com.webnobis.mastermind.service.handler.AliveHandler;
import com.webnobis.mastermind.service.handler.GameBuilderHandler;

import ratpack.api.UncheckedException;
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
		return null;
		//return new GameServer(Env.INSTANCE, new AliveHandler(), new GameBuilderHandler(new GameBuilder(), gameStore, gameConfigTransformer), gameHandler, tryHandler, resignHandler);
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

}
