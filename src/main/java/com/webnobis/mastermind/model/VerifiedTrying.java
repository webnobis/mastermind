package com.webnobis.mastermind.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class VerifiedTrying<T> extends Trying<T> {
	
	@XmlElement
	private final Verification verification;

	public VerifiedTrying(Trying<T> trying, Verification verification) {
		super(Objects.requireNonNull(trying).getIndex(), trying.getPositions());
		this.verification = verification;
	}

	public Verification getVerification() {
		return verification;
	}

}
