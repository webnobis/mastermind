package com.webnobis.mastermind.model;

import java.util.List;

public interface Game {
	
	List<TryWithAssessment> getTries();

	boolean isFinish();

}
