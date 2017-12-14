package com.webnobis.mastermind.service.store;

import com.webnobis.mastermind.model.GameWithSolution;

public interface GameStore {
	
	void store(GameWithSolution game);
	
	GameWithSolution find(String id);
	
	void delete(String id);

}
