package com.webnobis.mastermind.game;

import java.io.CharArrayWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GameXmlTest {
	
	@Mocked
	private Verifier<String> verifier;
	
	@Mocked
	private Game<String> game;

	@Before
	public void setUp() throws Exception {
		new Expectations() {{
			verifier.expected();
			returns(Arrays.asList("it's", "my", "test", "expectation"));
			
			game.getResults();
			returns(Arrays.asList(Arrays.stream(Result.values()).collect(Collectors.toList())));
			
			game.getTries();
			returns(Arrays.asList("my", "xml", "test"));
			
			game.getVerifier();
			returns(verifier);
			
			game.isEasyVerify();
			returns(true);
		}};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGameToXml() {
		String xml;
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(game, out);
			xml = out.toString();
		}
		System.out.println(xml);
	}

}
