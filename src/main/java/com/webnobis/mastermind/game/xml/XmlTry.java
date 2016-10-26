package com.webnobis.mastermind.game.xml;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="try")
public class XmlTry<E> {
	
	@XmlElement
	private final List<E> test;

	public XmlTry(List<E> test) {
		this.test = test;
	}
	
	XmlTry() {
		this(Collections.emptyList());
	}

	public List<E> getTest() {
		return test;
	}

}
