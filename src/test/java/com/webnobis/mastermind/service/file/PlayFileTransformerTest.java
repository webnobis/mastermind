package com.webnobis.mastermind.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class PlayFileTransformerTest {

	private Path tmpFile;

	@BeforeEach
	void setUp() throws Exception {
		tmpFile = Files.createTempFile(PlayFileTransformerTest.class.getSimpleName(), ".txt");
	}

	@AfterEach
	void tearDown() throws Exception {
		Files.delete(tmpFile);
	}

	@Test
	void testEmptyPlay() throws IOException {
		Play<Boolean> play = Play.of(Source.of(Boolean.TRUE, null, Boolean.FALSE));

		PlayFileTransformer.write(tmpFile, play);
		assertNotNull(Files.readAllBytes(tmpFile));

		assertEquals(play, PlayFileTransformer.read(tmpFile));
	}

	@Test
	void testFilledPlay() throws IOException {
		Play<Integer> play = Play.of(Source.of(IntStream.rangeClosed(1, 100).boxed().toArray(Integer[]::new)));
		for (int i = 0; i < 10; i++) {
			play = play
					.ofAddedResult(Result.of(play.getSource(), ResultType.EXACT, ResultType.EXACT, ResultType.PRESENT));
		}

		PlayFileTransformer.write(tmpFile, play);
		assertNotNull(Files.readAllBytes(tmpFile));

		assertEquals(play, PlayFileTransformer.read(tmpFile));
	}

}
