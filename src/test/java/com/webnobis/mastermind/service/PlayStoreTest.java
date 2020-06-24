package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class PlayStoreTest {

	private static final Play<Integer> PLAY = Play.of(42, Source.of(17, 6, -19))
			.withAddedResult(Result.of(Source.of(31), ResultType.PRESENT, ResultType.EXACT));

	private Path tmpFile;

	@BeforeEach
	void setUp() throws Exception {
		tmpFile = Files.createTempFile(PlayStoreTest.class.getSimpleName(), ".xml");
	}

	@AfterEach
	void tearDown() throws Exception {
		Files.delete(tmpFile);
	}

	@Test
	void testStoreAndLoad() throws IOException {
		assertFalse(Files.readAllBytes(tmpFile).length > 0);

		PlayStore.store(PLAY, tmpFile);

		assertTrue(Files.readAllBytes(tmpFile).length > 0);
		assertEquals(PLAY, PlayStore.load(tmpFile));
	}

}
