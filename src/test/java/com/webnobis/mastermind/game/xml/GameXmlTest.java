package com.webnobis.mastermind.game.xml;

import java.io.CharArrayWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.Verifier;
import com.webnobis.mastermind.game.xml.XmlGame;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GameXmlTest {
	
	@Mocked
	private Verifier<Integer> verifier;
	
	@Mocked
	private Game<Integer> game;

	@Before
	public void setUp() throws Exception {
		new Expectations() {{
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
		}};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGameToXml() {
		String xml;
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(XmlGame.from(game), out);
			xml = out.toString();
		}
		System.out.println(xml);
	}

}
