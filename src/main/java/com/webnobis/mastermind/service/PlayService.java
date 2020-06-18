package com.webnobis.mastermind.service;

import java.nio.file.Path;
import java.util.Objects;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;

public class PlayService {
	
	private final Path rootPath;

	public PlayService(Path rootPath) {
		this.rootPath = Objects.requireNonNull(rootPath, "rootPath is null");
	}

	public <T> Play<T> newPlay(Class<T> type, int cols) {
		Play<T> play = Play.of(type, cols);
		
		return play;
	}

	public <T> Play<T> newPlay(Class<T> type, int cols, int rows) {
		Play<T> play = Play.of(type, cols, rows);
		return play;
	}

	public <T> Play<T> getPlay(String id) {
		return null;
	}

	public <T> Play<T> nextTry(String id, Source<T> source) {
		Play<T> play = Play.of(type, cols, rows);
		return play;
	}

	public <T> Play<T> quitPlay(String id) {
		return null;
	}

	public boolean removePlay(String id) {
		return false;
	}

}
