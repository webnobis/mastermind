package com.webnobis.mastermind.service.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.webnobis.mastermind.game.Game;

public interface GameCache<E> {
	
	String store(Game<E> game);
	
	Game<E> get(String id);
	
	void remove(String id);
	
	static <E> GameCache<E> create() {
		return new GameCache<E>() {
			
			private final Map<SoftReference<String>,SoftReference<Game<E>>> cache = new ConcurrentHashMap<>();

			@Override
			public String store(Game<E> game) {
				String id = UUID.randomUUID().toString();
				cache.put(new SoftReference<>(id), new SoftReference<>(game));
				return id;
			}

			@Override
			public Game<E> get(String id) {
				return cache.get(new SoftReference<>(id)).get();
			}

			@Override
			public void remove(String id) {
				cache.remove(new SoftReference<>(id));
			}
			
		};
	}

}
