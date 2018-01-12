package com.webnobis.mastermind.model.transformer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.xml.XmlAssessment;
import com.webnobis.mastermind.model.xml.XmlGame;
import com.webnobis.mastermind.model.xml.XmlGameConfig;
import com.webnobis.mastermind.model.xml.XmlGameWithSolution;
import com.webnobis.mastermind.model.xml.XmlSolution;
import com.webnobis.mastermind.model.xml.XmlTry;
import com.webnobis.mastermind.model.xml.XmlTryWithAssessment;

public class GameAndSolutionTransformerTest {

	private Path tmpFile;

	@Before
	public void setUp() throws Exception {
		tmpFile = Files.createTempFile(GameAndSolutionTransformerTest.class.getSimpleName(), ".tmp");
	}

	@After
	public void tearDown() throws Exception {
		Files.delete(tmpFile);
	}

	@Test
	public void testTransformGame() throws IOException {
		Game game = createGame();
		String xml = GameAndSolutionTransformer.transform(game);
		assertEquals(game, toObject(xml, XmlGame.class));
	}

	@Test
	public void testTransformGameWithSolution() throws IOException {
		GameWithSolution gameWithSolution = new XmlGameWithSolution("id 1", createGame(), createSolution());
		String xml = GameAndSolutionTransformer.transform(gameWithSolution);
		assertEquals(gameWithSolution, toObject(xml, XmlGameWithSolution.class));
	}

	@Test
	public void testTransformSolution() throws IOException {
		Solution solution = createSolution();
		String xml = GameAndSolutionTransformer.transform(solution);
		assertEquals(solution, toObject(xml, XmlSolution.class));
	}

	private static Game createGame() {
		return new XmlGame(new XmlGameConfig(-1, 0, 1), IntStream.range(1, 10)
				.mapToObj(i -> new XmlTryWithAssessment(new XmlTry(Collections.singletonList(i)), new XmlAssessment(Collections.singletonList(Result.CONTAINED))))
				.collect(Collectors.toList()));
	}

	private static Solution createSolution() {
		return new XmlSolution(Arrays.asList(1, 2, 3));
	}
	
	private <T> T toObject(String xml, Class<T> c) throws IOException {
		Files.write(tmpFile, xml.getBytes(StandardCharsets.UTF_8));
		return JAXB.unmarshal(Files.newBufferedReader(tmpFile, StandardCharsets.UTF_8), c);
	}

}
