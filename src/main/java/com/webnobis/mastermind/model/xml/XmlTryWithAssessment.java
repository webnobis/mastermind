package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;

@XmlRootElement
public class XmlTryWithAssessment implements TryWithAssessment {

	@XmlElement(name = "try", type = XmlTry.class)
	private final Try theTry;

	@XmlElement(type = XmlAssessment.class)
	private final Assessment assessment;

	public XmlTryWithAssessment(Try theTry, Assessment assessment) {
		this.theTry = theTry;
		this.assessment = assessment;
	}

	@OnlyForXmlBinding
	XmlTryWithAssessment() {
		this(null, null);
	}

	@Override
	public Try getTry() {
		return theTry;
	}

	@Override
	public Assessment getAssessment() {
		return assessment;
	}

}
