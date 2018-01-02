package com.webnobis.mastermind;

import java.util.function.Supplier;

import com.webnobis.mastermind.service.GameServer;

import ratpack.server.RatpackServer;

public class Mastermind {

	static Supplier<RatpackServer> ratpackServerBuilder = Mastermind::buildRatpackServer;

	public static void main(String[] args) throws Exception {
		RatpackServer server = ratpackServerBuilder.get();
		try {
			server.start();
		} finally {
			server.stop();
		}
	}

	static RatpackServer buildRatpackServer() {
		return GameServer.build().buildServer();
	}

}