package com.webnobis.mastermind.service.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.xml.XmlGame;
import com.webnobis.mastermind.game.xml.XmlTry;
import com.webnobis.mastermind.service.store.GameStore;

import io.netty.handler.codec.http.HttpResponseStatus;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Status;
import ratpack.http.TypedData;

public class GameHandler implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.getRequest().getBody()
			.map(TypedData::getText)
			.map(xml -> createOrUpdate(xml))
			.onError(IllegalArgumentException.class, e -> ctx.getResponse().status(Status.of(HttpResponseStatus.EXPECTATION_FAILED.code(), e.getMessage())))
			.map(XmlGame::from)
			.map(XmlGame::toString)
			.onError(ctx::error)
			.then(ctx.getResponse().contentType("application/xml")::send);
	}
	
	private static Game<Integer> createOrUpdate(String xml) {
		return Optional.ofNullable(xml)
		.<XmlTry<Integer>>map(XmlTry::from)
		.map(xmlTry -> createOrUpdate(xmlTry))
		.orElseThrow(() -> new IllegalArgumentException("couldn't use for game: " + xml));
	}
	
	private static Game<Integer> createOrUpdate(XmlTry<Integer> xmlTry) {
		Game<Integer> game = Optional.ofNullable(xmlTry.getId())
			.<Game<Integer>>map(GameStore.get()::get)
			.filter(g -> g != null)
			.orElseGet(GameHandler::create);
		
		List<Result> result = game.nextTry(xmlTry.getTest());
		System.out.println(result);
		
		String id = GameStore.get().store(game);
		System.out.println(id);

		return game;
	}
	
	private static Game<Integer> create() {
		return Game.create(Arrays.asList(1,3,5,7), true);
	}

}
