package com.webnobis.mastermind.service;

@FunctionalInterface
public interface GameConsumerRegistry {

	void register(String id, GameConsumer gameConsumer);

	default void deregister(String id) {
		register(id, null);
	}

}
