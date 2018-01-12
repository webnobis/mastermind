package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;

@XmlRootElement(name = "try-with-assessment")
public class XmlTryWithAssessment implements TryWithAssessment {

	@XmlElement(type = XmlTry.class)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessment == null) ? 0 : assessment.hashCode());
		result = prime * result + ((theTry == null) ? 0 : theTry.hashCode());
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
		XmlTryWithAssessment other = (XmlTryWithAssessment) obj;
		if (assessment == null) {
			if (other.assessment != null)
				return false;
		} else if (!assessment.equals(other.assessment))
			return false;
		if (theTry == null) {
			if (other.theTry != null)
				return false;
		} else if (!theTry.equals(other.theTry))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlTryWithAssessment [theTry=" + theTry + ", assessment=" + assessment + "]";
	}

}
