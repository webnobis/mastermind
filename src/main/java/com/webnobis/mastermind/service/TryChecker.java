package com.webnobis.mastermind.service;

import com.webnobis.mastermind.model.CheckedTry;
import com.webnobis.mastermind.model.Try;

public interface TryChecker {
	
	CheckedTry check(Try testTry);

}
