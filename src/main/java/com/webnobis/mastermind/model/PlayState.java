package com.webnobis.mastermind.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlayState<T extends Serializable> {

	private final List<Result<T>> results;

	private final boolean finish;

	private final boolean solved;

	private PlayState(List<Result<T>> results, boolean finish, boolean solved) {
		this.results = results;
		this.finish = finish;
		this.solved = solved;
	}

	public static <T extends Serializable> PlayState<T> of(Play<T> play) {
		Objects.requireNonNull(play, "play is null");
		return new PlayState<>(play.getResults(), play.isFinish(), play.isSolved());
	}

	public List<Result<T>> getResults() {
		return results;
	}

	public boolean isFinish() {
		return finish;
	}

	public boolean isSolved() {
		return solved;
	}

	@Override
	public String toString() {
		return "PlayState [results=" + results + ", finish=" + finish + ", solved=" + solved + "]";
	}
}
