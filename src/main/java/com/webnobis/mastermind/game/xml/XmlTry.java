package com.webnobis.mastermind.game.xml;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import com.webnobis.mastermind.model.OnlyForXmlBinding;

@Deprecated
@XmlRootElement(name = "try")
public class XmlTry<E> {

	@XmlAttribute(required = false)
	private final String id;

	@XmlElementWrapper(name = "next")
	@XmlElement
	private final List<E> test;

	public XmlTry(List<E> test) {
		this(null, test);
	}

	public XmlTry(String id, List<E> test) {
		this.id = id;
		this.test = test;
	}

	@SuppressWarnings("unchecked")
	public static <E> XmlTry<E> from(String xml) {
		try (CharArrayReader in = new CharArrayReader(xml.toCharArray())) {
			return (XmlTry<E>) JAXBContext.newInstance(XmlTry.class).createUnmarshaller().unmarshal(new StreamSource(in));
		} catch (JAXBException e) {
			throw new DataBindingException(e);
		}
	}

	@OnlyForXmlBinding
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
