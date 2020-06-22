package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBElement;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class XmlTransformerTest {

	private static final Source<Boolean> SOURCE = Source.of(Boolean.TRUE, Boolean.FALSE);

	private static final Play<Integer> PLAY = Play.of(42, Source.of(17, 6, -19))
			.withAddedResult(Result.of(Source.of(31), ResultType.PRESENT, ResultType.EXACT));

	@Test
	void testTransformSource() {
		String xml = XmlTransformer.toXml(SOURCE);
		assertEquals(SOURCE.getSources(), XmlTransformer.toModel(xml, Source.class).getSources());
	}

	@Test
	void testTransformPlay() {
		String xml = XmlTransformer.toXml(PLAY);
		assertEquals(PLAY, XmlTransformer.toModel(xml, Play.class));
	}

	@Test
	void testTransformNoXml() {
		assertThrows(DataBindingException.class, () -> XmlTransformer.toModel("no xml", JAXBElement.class));
	}

	@Test
	void testTransformNoXmlType() {
		assertThrows(DataBindingException.class, () -> XmlTransformer.toXml("no xml"));
	}

}
