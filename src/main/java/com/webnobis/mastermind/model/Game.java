package com.webnobis.mastermind.model;

import java.util.List;
import java.util.Objects;

public class Game<T> {
	
	private final String id;
	
	private final List<T> solution;
	
	private final List<List<T>> nextTry;
	
	private final List<List<Status>> verify;
	
	private final boolean finish;

	public Game(String id, List<T> solution, List<List<T>> nextTry, List<List<Status>> verify, boolean finish) {
		this.id = Objects.requireNonNull(id);
		this.solution = Objects.requireNonNull(solution);
		this.nextTry = Objects.requireNonNull(nextTry);
		this.verify = Objects.requireNonNull(verify);
		this.finish = finish;
	}

	public String getId() {
		return id;
	}

	public List<T> getSolution() {
		return solution;
	}

	public List<List<T>> getNextTry() {
		return nextTry;
	}

	public List<List<Status>> getVerify() {
		return verify;
	}

	public boolean isFinish() {
		return finish;
	}

}
