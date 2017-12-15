package com.webnobis.mastermind.model;

import java.util.List;

public interface Game {
	
	GameConfig getConfig();
	
	List<TryWithAssessment> getTries();

	boolean isFinish();

}
