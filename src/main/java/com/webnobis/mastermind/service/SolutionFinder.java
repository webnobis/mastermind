package com.webnobis.mastermind.service;

import java.util.List;

import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Verification;
import com.webnobis.mastermind.model.VerifiedTry;

public abstract class SolutionFinder {

	private SolutionFinder() {
	}

	public static Solution findSolution(List<VerifiedTry> verifiedTries) {
		return verifiedTries.stream()
				.filter(SolutionFinder::isSolution)
				.map(VerifiedTry::getTry)
				.map(nextTry -> new Solution(nextTry.getParts()))
				.findFirst()
				.orElse(null);
	}

	private static boolean isSolution(VerifiedTry verifiedTry) {
		List<Verification> verifications = verifiedTry.getAssessment().getVerifications();
		return verifications.size() >= verifiedTry.getTry().getParts().size() &&
				verifications.stream().allMatch(Verification.CORRECT_PLACE::equals);
	}

}
