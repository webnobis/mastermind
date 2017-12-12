package com.webnobis.mastermind.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VerifiedTry {
	
	@XmlElement(name="try")
	private final Try testTry;
	
	@XmlElement
	private final Assessment assessment;

	public VerifiedTry(Try testTry, Assessment assessment) {
		this.testTry = Objects.requireNonNull(testTry, "testTry is null");
		this.assessment = Objects.requireNonNull(assessment, "assessment is null");
	}

	@OnlyForXmlBinding
	VerifiedTry() {
		this(null,null);
	}

	public Try getTry() {
		return testTry;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	@Override
	public String toString() {
		return "VerifiedTry [testTry=" + testTry + ", assessment=" + assessment + "]";
	}

}
