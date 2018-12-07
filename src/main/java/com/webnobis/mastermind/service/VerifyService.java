package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Status;

public class VerifyService<T> {
	
	private final List<T> solution;
	
	public VerifyService(List<T> solution) {
		this.solution = solution;
	}

	public List<Status> verify(List<T> nextTry) {
		List<T> tmpSolution = new ArrayList<>(solution);
		return IntStream.range(0, nextTry.size())
			.mapToObj(i -> buildStatus(i, nextTry.get(i), tmpSolution))
			.collect(Collectors.toList());
		
	}
	
	private Status buildStatus(int i, T nextTry, List<T> tmpSolution) {
		if (i < solution.size() && solution.get(i).equals(nextTry)) {
			tmpSolution.remove(nextTry);
			return Status.CORRECT_PLACE;
		} else {
			return (tmpSolution.remove(nextTry))? Status.CONTAINED: Status.MISSING; 
		}
	}
	
	public boolean verifyFinish(List<T> nextTry) {
		return solution.equals(nextTry);
	}

}
