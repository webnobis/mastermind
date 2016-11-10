package com.webnobis.mastermind.game.xml;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "try")
public class XmlTry<E> {

	@XmlAttribute
	private final String id;

	@XmlElement
	private final List<E> test;

	public XmlTry(String id, List<E> test) {
		this.id = id;
		this.test = test;
	}

	@OnlyForXmlTransformation
	XmlTry() {
		this(null, new ArrayList<>());
	}

	public String getId() {
		return id;
	}

	public List<E> getTest() {
		return test;
	}

	@Override
	public String toString() {
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(this, out);
			out.flush();
			return out.toString();
		}
	}

}
