package com.webnobis.mastermind.model;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractPart<T> extends AbstractId {

	@XmlElement
	private final List<T> parts;

	protected AbstractPart(String id, List<T> parts) {
		super(id);
		this.parts = Objects.requireNonNull(parts);
	}

	public List<T> getParts() {
		return parts;
	}

}
