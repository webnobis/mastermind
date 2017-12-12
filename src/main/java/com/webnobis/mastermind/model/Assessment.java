package com.webnobis.mastermind.model;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assessment {
	
	@XmlElement
	private final List<Verification> verifications;

	public Assessment(List<Verification> verifications) {
		this.verifications = Objects.requireNonNull(verifications, "verifications is null");
	}

	@OnlyForXmlBinding
	Assessment() {
		this(null);
	}

	public List<Verification> getVerifications() {
		return verifications;
	}

	@Override
	public String toString() {
		return "Assessment [verifications=" + verifications + "]";
	}

}
