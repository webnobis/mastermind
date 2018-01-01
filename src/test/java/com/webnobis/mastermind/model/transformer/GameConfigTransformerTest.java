package com.webnobis.mastermind.model.transformer;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.xml.XmlGameConfig;

public class GameConfigTransformerTest {
	
	private GameConfig gameConfig;
	
	private Path tmpFile;
	
	private String xml;

	@Before
	public void setUp() throws Exception {
		gameConfig = new XmlGameConfig(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
		
		tmpFile = Files.createTempFile(GameConfigTransformerTest.class.getSimpleName(), ".tmp");
		JAXB.marshal(gameConfig, Files.newBufferedWriter(tmpFile, StandardCharsets.UTF_8));
		xml = Files.readAllLines(tmpFile, StandardCharsets.UTF_8).stream().collect(Collectors.joining());
	}

	@After
	public void tearDown() throws Exception {
		Files.delete(tmpFile);
	}

	@Test
	public void testTransform() {
		assertEquals(gameConfig, GameConfigTransformer.transform(xml));
	}

}
