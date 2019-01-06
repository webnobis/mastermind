package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class VerifiedTrying<E extends Enum<E>> extends Trying<E> {
	
	@XmlElement
	private final Verification verification;

	public VerifiedTrying(int index, List<E> positions, Verification verification) {
		super(index, positions);
		this.verification = verification;
	}

	public Verification getVerification() {
		return verification;
	}

}
