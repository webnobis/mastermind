package com.webnobis.mastermind.model.xml;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Result;

@XmlRootElement
public class XmlAssessment implements Assessment {

	@XmlElement
	private final List<Result> assessments;

	public XmlAssessment(List<Result> assessments) {
		this.assessments = Optional.ofNullable(assessments)
				.map(Collections::unmodifiableList)
				.orElse(Collections.emptyList());
	}

	@OnlyForXmlBinding
	XmlAssessment() {
		this(null);
	}

	@Override
	public List<Result> getAssessments() {
		return assessments;
	}

}
