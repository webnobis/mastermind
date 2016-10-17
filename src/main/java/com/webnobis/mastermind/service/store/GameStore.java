package com.webnobis.mastermind.service.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.webnobis.mastermind.game.Game;

public interface GameStore {
	
	<E> String store(Game<E> game);
	
	<E> Game<E> get(String id);
	
	void remove(String id);
	
	static GameStore create() {
		return new GameStore() {
			
			private final Map<String,Game<?>> cache = new ConcurrentHashMap<>();

			@Override
			public <E> String store(Game<E> game) {
				cache.put(game.getId(), game);
				return game.getId();
			}

			@SuppressWarnings("unchecked")
			@Override
			public <E> Game<E> get(String id) {
				return (Game<E>) cache.get(id);
			}

			@Override
			public void remove(String id) {
				cache.remove(id);
			}
			
		};
	}

}
