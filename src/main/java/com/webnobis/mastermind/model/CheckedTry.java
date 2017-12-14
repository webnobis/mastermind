package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckedTry extends AbstractPart<Result> {

	@XmlElement(name = "try")
	private final Try testTry;

	public CheckedTry(Try testTry, List<Result> parts) {
		super(testTry.getId(), parts);
		this.testTry = testTry;
	}

	public Try getTry() {
		return testTry;
	}

	@Override
	public String toString() {
		return "CheckedTry [getTry()=" + getTry() + ", getId()=" + getId() + ", getParts()=" + getParts() + "]";
	}

}
