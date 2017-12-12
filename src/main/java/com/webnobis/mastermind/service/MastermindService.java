package com.webnobis.mastermind.service;

import old.GameHandler;
import ratpack.server.RatpackServer;

@Deprecated
public interface MastermindService {

	static RatpackServer create() throws Exception {
		return RatpackServer.of(spec -> spec
				.registryOf(registry -> registry
						.add(new GameHandler()))
				.handlers(chain -> chain
						.post("game", GameHandler.class)
						.get("hello", ctx -> ctx.render("Hello from MastermindService"))));
	};

	public static void main(String[] args) throws Exception {
		RatpackServer server = MastermindService.create();

		server.start();
		try {
			System.in.read();
		} finally {
			System.out.println("stop");
			server.stop();
		}
	}

}
