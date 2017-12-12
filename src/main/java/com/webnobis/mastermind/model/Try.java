package com.webnobis.mastermind.model;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Try {

	@XmlElement
	private final List<Integer> parts;

	public Try(List<Integer> parts) {
		this.parts = Objects.requireNonNull(parts, "parts is null");
	}

	@OnlyForXmlBinding
	Try() {
		this(null);
	}

	public List<Integer> getParts() {
		return parts;
	}

	@Override
	public String toString() {
		return "Try [parts=" + parts + "]";
	}
	
}
