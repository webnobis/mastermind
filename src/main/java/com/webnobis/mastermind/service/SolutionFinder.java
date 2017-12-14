package com.webnobis.mastermind.service;

import java.util.List;

import com.webnobis.mastermind.model.CheckedTry;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Solution;

public abstract class SolutionFinder {

	private SolutionFinder() {
	}

	public static Solution findSolution(List<CheckedTry> verifiedTries) {
		return verifiedTries.stream()
				.filter(SolutionFinder::isSolution)
				.map(CheckedTry::getTry)
				.findFirst()
				.map(nextTry -> new Solution(nextTry.getId(), nextTry.getParts()))
				.orElse(null);
	}

	private static boolean isSolution(CheckedTry verifiedTry) {
		List<Result> parts = verifiedTry.getParts();
		return parts.size() >= verifiedTry.getTry().getParts().size() &&
				parts.stream().allMatch(Result.CORRECT_PLACE::equals);
	}

}
