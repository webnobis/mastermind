package com.webnobis.mastermind.service;

import java.nio.file.Path;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;

public class PlayService {
	
	private final Path rootPath;

	public <T> Play<T> newPlay(Class<T> type, int cols) {
		Play<T> play = Play.of(type, cols);
		return play;
	}

	public <T> Play<T> newPlay(Class<T> type, int cols, int rows) {
		Play<T> play = Play.of(type, cols, rows);
		return play;
	}

	public <T> Play<T> nextTry(String id, Source<T> source) {
		Play<T> play = Play.of(type, cols, rows);
		return play;
	}

	public <T> Play<T> quit(String id) {
		return null;
	}

}
