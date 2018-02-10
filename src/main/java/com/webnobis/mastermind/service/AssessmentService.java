package com.webnobis.mastermind.service;

import java.util.Collections;
import java.util.Objects;

import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.model.xml.XmlAssessment;
import com.webnobis.mastermind.model.xml.XmlTryWithAssessment;

public abstract class AssessmentService {

	private AssessmentService() {
	}

	public static TryWithAssessment assess(Solution solution, Try theTry) {
		Objects.requireNonNull(solution, "solution is null");
		Objects.requireNonNull(theTry, "theTry is null");

		// TODO correct assess logic

		return new XmlTryWithAssessment(theTry, new XmlAssessment(Collections.emptyList()));
	}

}
