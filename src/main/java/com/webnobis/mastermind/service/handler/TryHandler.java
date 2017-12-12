package com.webnobis.mastermind.service.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.TypedData;

public class TryHandler implements Handler {

	public TryHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.getRequest()
		.getBody()
		.map(TypedData::getText)
		;

	}

}
