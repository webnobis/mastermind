package com.webnobis.mastermind.game.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.CharArrayWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Before;
import org.junit.Test;

public class NextTryToXmlTest {

	private String id;

	private List<Boolean> test;

	@Before
	public void setUp() throws Exception {
		id = UUID.randomUUID().toString();
		test = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
	}

	@Test
	public void testNextTryToXml() {
		String xml;
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(new XmlTry<>(id, test), out);
			xml = out.toString();
		}
		assertNotNull(xml);
		assertTrue(xml.contains(XmlTry.class.getAnnotation(XmlRootElement.class).name()));

		validateXmlToObject(xml);
	}

	private void validateXmlToObject(String xml) {
		XmlTry<Boolean> xmlTry = XmlTry.from(xml);
		assertNotNull(xmlTry);
		assertEquals(id, xmlTry.getId());
		assertEquals(test, xmlTry.getTest());
	}

}
