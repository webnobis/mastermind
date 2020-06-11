package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class XmlTransformerTest {

	private static final Source<Boolean> SOURCE = Source.of(Boolean.TRUE, Boolean.FALSE);

	private static final Play<Integer> PLAY = Play.of(Integer.class, 42).withSource(Source.of(17, 6, -19))
			.withAddedResult(Result.of(Source.of(31), ResultType.PRESENT, ResultType.EXACT));

	@Test
	void testTransformSource() {
		String xml = XmlTransformer.toXml(SOURCE);

		System.out.println(xml);

		assertEquals(SOURCE.getSources(), XmlTransformer.toModel(xml, Source.class).getSources());
	}

	@Test
	void testTransformPlay() {
		String xml = XmlTransformer.toXml(PLAY);

		System.out.println(xml);

		assertEquals(PLAY, XmlTransformer.toModel(xml, Play.class));
	}

}
