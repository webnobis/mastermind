package com.webnobis.mastermind.model;

import java.util.List;

public class Game<T> {
	
	private final String id;
	
	private final List<T> solution;
	
	private final List<NextTry<T>> nextTry;
	
	private final boolean finish;

	public Game(String id, List<T> solution, List<NextTry<T>> nextTry, boolean finish) {
		this.id = id;
		this.solution = solution;
		this.nextTry = nextTry;
		this.finish = finish;
	}

	public String getId() {
		return id;
	}

	public List<T> getSolution() {
		return solution;
	}

	public List<NextTry<T>> getNextTry() {
		return nextTry;
	}

	public boolean isFinish() {
		return finish;
	}

}
