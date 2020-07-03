package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MsgTest {

	private static final String ID_LABEL = "id.label";

	private static Properties properties;

	@BeforeAll
	static void setUpAll() throws Exception {
		properties = new Properties();
		properties.load(MsgTest.class.getResourceAsStream("/msg.properties"));
	}

	@Test
	void testGet() {
		assertEquals(properties.getProperty(ID_LABEL), Msg.get(ID_LABEL));
	}

}
