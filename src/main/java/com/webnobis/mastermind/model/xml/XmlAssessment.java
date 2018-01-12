package com.webnobis.mastermind.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Result;

@XmlRootElement(name = "assessment")
public class XmlAssessment implements Assessment {

	@XmlElement
	private final List<Result> assessments = new ArrayList<>();

	public XmlAssessment(List<Result> assessments) {
		Optional.ofNullable(assessments)
				.ifPresent(this.assessments::addAll);
		;
	}

	@OnlyForXmlBinding
	XmlAssessment() {
		this(null);
	}

	@Override
	public List<Result> getAssessments() {
		return assessments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessments == null) ? 0 : assessments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XmlAssessment other = (XmlAssessment) obj;
		if (assessments == null) {
			if (other.assessments != null)
				return false;
		} else if (!assessments.equals(other.assessments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlAssessment [assessments=" + assessments + "]";
	}

}
