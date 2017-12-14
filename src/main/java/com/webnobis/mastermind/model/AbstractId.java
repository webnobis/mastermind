package com.webnobis.mastermind.model;

import javax.xml.bind.annotation.XmlAttribute;

public class AbstractId {

	@XmlAttribute
	private final String id;

	protected AbstractId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
