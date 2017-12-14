package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Try extends AbstractPart<Integer> {

	public Try(String id, List<Integer> parts) {
		super(id, parts);
	}

	@OnlyForXmlBinding
	Try() {
		this(null, null);
	}

	@Override
	public String toString() {
		return "Try [getId()=" + getId() + ", getParts()=" + getParts() + "]";
	}

}
