package com.webnobis.mastermind.service.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class NewGameHandler implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.render("new game");
	}

}
