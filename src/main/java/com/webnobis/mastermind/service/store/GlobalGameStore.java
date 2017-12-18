package com.webnobis.mastermind.service.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.webnobis.mastermind.model.GameWithSolution;

public enum GlobalGameStore implements GameStore {

	INSTANCE;

	private final Map<String, GameWithSolution> cache = new ConcurrentHashMap<>();

	@Override
	public String store(GameWithSolution game) {
		cache.put(game.getId(), game);
		return game.getId();
	}

	@Override
	public GameWithSolution find(String id) {
		return cache.get(id);
	}

	@Override
	public void delete(String id) {
		cache.remove(id);
	}

}
