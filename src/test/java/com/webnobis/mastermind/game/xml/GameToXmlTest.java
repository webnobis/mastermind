package com.webnobis.mastermind.game.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.Verifier;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GameToXmlTest {

	@Mocked
	private Verifier<Integer> verifier;

	@Mocked
	private Game<Integer> game;

	@Before
	public void setUp() throws Exception {
		new Expectations() {
			{
				verifier.expected();
				returns(IntStream.range(11, 20).boxed().collect(Collectors.toList()));

				game.getResults();
				returns(Stream.generate(() -> Arrays.stream(Result.values()).collect(Collectors.toList())).limit(2).collect(Collectors.toList()));

				game.getTries();
				returns(Arrays.asList(Stream.of(2, 4, 6, 8, 10).collect(Collectors.toList()), IntStream.range(101, 110).boxed().collect(Collectors.toList())));

				game.getVerifier();
				returns(verifier);

				game.isEasyVerify();
				returns(true);

				game.isFinish();
				returns(true);

				game.tries();
				returns(2);
			}
		};
	}

	@Test
	public void testGameToXml() {
		String xml;
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(XmlGame.from(game), out);
			xml = out.toString();
		}
		assertNotNull(xml);
		assertTrue(xml.contains(XmlGame.class.getAnnotation(XmlRootElement.class).name()));

		validateXmlToObject(xml);
	}

	private void validateXmlToObject(String xml) {
		XmlGame<?> xmlGame;
		try (CharArrayReader in = new CharArrayReader(xml.toCharArray())) {
			xmlGame = JAXB.unmarshal(in, XmlGame.class);
		}
		assertNotNull(xmlGame);
		assertEquals(game.isEasyVerify(), xmlGame.isEasyVerify());
		assertEquals(game.isFinish(), xmlGame.isFinish());
		assertEquals(game.getVerifier().expected(), xmlGame.getExpected());
		xmlGame.getTries().stream()
				.peek(v -> assertEquals(game.getTries().get(v.getTryCount()), v.getTest()))
				.forEach(v -> assertEquals(game.getResults().get(v.getTryCount()), v.getResult()));
	}

}
