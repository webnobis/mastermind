package com.webnobis.mastermind.service.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;

@Path("alive")
public class AliveHandler implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.render("yes");
	}

}
