package com.webnobis.mastermind.service;

import com.webnobis.mastermind.service.handler.GameHandler;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.server.RatpackServer;

public class MastermindService {

	public static void main(String[] args) throws Exception {
		RatpackServer server = RatpackServer.of(spec -> spec
				.registryOf(registry -> registry
						.add(new GameHandler())
						.addLazy(GameStore.class, () -> GameStore.create()))
				.handlers(chain -> chain
						.post("game", GameHandler.class)
						.get("hello", ctx -> ctx.render("Hello from MastermindService"))));
		server.start();
		try {
			System.in.read();
		} finally {
			System.out.println("stop");
			server.stop();
		}
	}

}
