package com.webnobis.mastermind.service;

import java.util.Collections;

import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.model.xml.XmlAssessment;
import com.webnobis.mastermind.model.xml.XmlTryWithAssessment;

public abstract class AssessmentService {

	private AssessmentService() {
	}

	public static TryWithAssessment assess(Try theTry) {
		// TODO correct assess logic

		return new XmlTryWithAssessment(theTry, new XmlAssessment(Collections.emptyList()));
	}

}
