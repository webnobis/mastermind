package com.webnobis.mastermind.model.transformer;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.xml.XmlTry;

public class TryTransformerTest {
	
	private Try theTry;
	
	private Path tmpFile;
	
	private String xml;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		theTry = new XmlTry(Arrays.asList(-1, 0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE));
		
		tmpFile = Files.createTempFile(TryTransformerTest.class.getSimpleName(), ".tmp");
		JAXB.marshal(theTry, Files.newBufferedWriter(tmpFile, StandardCharsets.UTF_8));
		xml = Files.readAllLines(tmpFile, StandardCharsets.UTF_8).stream().collect(Collectors.joining());
	}

	@After
	public void tearDown() throws Exception {
		Files.delete(tmpFile);
	}

	@Test
	public void testTransform() {
		assertEquals(theTry, TryTransformer.transform(xml));
	}

}
