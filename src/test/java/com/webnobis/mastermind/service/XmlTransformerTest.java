package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Source;

class XmlTransformerTest {
	
	private static final Source<Boolean> SOURCE = Source.of(Boolean.TRUE, Boolean.FALSE);

	@Test
	void testTransformSource() {
		String xml = XmlTransformer.toXml(SOURCE);
		
		System.out.println(xml);
		
		assertEquals(SOURCE.getSources(), XmlTransformer.toSource(xml).getSources());
	}

}
