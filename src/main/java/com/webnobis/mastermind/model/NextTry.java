package com.webnobis.mastermind.model;

import java.util.List;

public class NextTry<T> {
	
	private final List<T> nextTry;
	
	private final List<Status> status;

	public NextTry(List<T> nextTry, List<Status> status) {
		this.nextTry = nextTry;
		this.status = status;
	}

	public List<T> getNextTry() {
		return nextTry;
	}

	public List<Status> getStatus() {
		return status;
	}

}
