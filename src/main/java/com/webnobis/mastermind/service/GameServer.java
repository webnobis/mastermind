package com.webnobis.mastermind.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webnobis.mastermind.service.handler.AliveHandler;
import com.webnobis.mastermind.service.handler.GameBuilderHandler;
import com.webnobis.mastermind.service.handler.GameHandler;
import com.webnobis.mastermind.service.handler.Path;
import com.webnobis.mastermind.service.handler.ResignHandler;
import com.webnobis.mastermind.service.handler.TryHandler;

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
	}

	public RatpackServer buildServer() {
		try {
			return RatpackServer.of(spec -> spec
					.serverConfig(config -> config
							.port(env.getPort()))
					.handlers(chain -> chain
							.get(getPath(AliveHandler.class), aliveHandler)
							.post(getPath(GameBuilderHandler.class), gameBuilderHandler)
							.get(getPath(GameHandler.class), gameHandler)
							.post(getPath(TryHandler.class), tryHandler)
							.get(getPath(ResignHandler.class), resignHandler)));
		} catch (Exception e) {
			throw new UncheckedException(e);
		}
	}

	private static String getPath(Class<? extends Handler> handler) {
		return Optional.ofNullable(handler.getAnnotation(Path.class))
				.map(Path::value)
				.orElseThrow(() -> new NoSuchElementException("Missing annotation Path at Handler class " + handler.getSimpleName()));
	}

}
