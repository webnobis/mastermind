package com.webnobis.mastermind.service;

import java.io.Serializable;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.PlayState;
import com.webnobis.mastermind.model.Source;

public interface PlayService<T extends Serializable> {

	String newPlay(int cols, int rows);

	PlayState<T> nextTry(String id, Source<T> source);

	Play<T> quit(String id);
	
	static <T extends Serializable> PlayService<T> create() {
		return null;
	}

}
