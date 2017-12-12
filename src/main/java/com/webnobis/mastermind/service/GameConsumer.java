package com.webnobis.mastermind.service;

import com.webnobis.mastermind.model.Game;

@FunctionalInterface
public interface GameConsumer {
	
	void consume(Game game);

}
