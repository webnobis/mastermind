package com.webnobis.mastermind;

import java.util.function.Supplier;

import ratpack.server.RatpackServer;

public class Mastermind {

	static Supplier<RatpackServer> ratpackServerBuilder = Mastermind::buildRatpackServer;

	public static void main(String[] args) throws Exception {
		RatpackServer server = ratpackServerBuilder.get();
		try {
			server.start();
		} catch(Exception e) {
			server.stop();
			throw e;
		}
	}

	static RatpackServer buildRatpackServer() {
		return null;
	}

}
