package com.webnobis.mastermind.service.handler;

import java.util.Arrays;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.xml.XmlGame;
import com.webnobis.mastermind.service.store.GameStore;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class NewGameHandler implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		Game<Integer> game = Game.create(Arrays.asList(1,3,5,7), true);
		GameStore store = ctx.get(GameStore.class);
		String id = store.store(game);
		System.out.println(id);
		ctx.getResponse().contentType("application/xml").send(XmlGame.from(game).toString());
	}

}
