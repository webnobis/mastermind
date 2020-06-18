package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Source;

class PlayServiceTest {

	private static final int COLS = 3;

	private Path tmpFolder;

	private PlayService playService;

	private Play<Short> play;

	@BeforeEach
	void setUp() throws Exception {
		tmpFolder = Files.createTempDirectory(PlayServiceTest.class.getSimpleName());

		playService = new PlayService(tmpFolder);

		play = playService.newPlay(Short.class, COLS);
	}

	@AfterEach
	void tearDown() throws Exception {
		Files.newDirectoryStream(tmpFolder).forEach(t -> {
			try {
				Files.delete(t);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		Files.delete(tmpFolder);
	}

	@Test
	void testNewPlay() throws IOException {
		Play<Short> play2 = playService.newPlay(Short.class, COLS, 2);

		assertNotNull(play);
		assertNotNull(play2);
		assertNotEquals(play, play2);

		assertSame(2L, Files.walk(tmpFolder).filter(Files::isRegularFile).map(file -> {
			try {
				return Files.readString(file);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}).filter(xml -> xml.contains(play.getId()) || xml.contains(play2.getId())).count());
	}

	@Test
	void testGetPlay() {
		Play<Short> play1 = playService.getPlay(play.getId());

		assertNotNull(play1);
		assertEquals(play, play1);
		assertEquals(play.getId(), play1.getId());
	}

	@Test
	void testNextTry() {
		Source<Short> source = Source.of(Short.MAX_VALUE);
		Play<Short> play1 = playService.nextTry(play.getId(), source);

		assertTrue(play.getResults().isEmpty());

		assertNotNull(play1);
		assertEquals(play1.getId(), play1.getId());
		List<Result<Short>> results = play1.getResults();
		assertSame(1, results.size());
		assertEquals(source.getSources(), results.iterator().next().getSources());
	}

	@Test
	void testQuitPlay() {
		Play<Short> play1 = playService.quitPlay(play.getId());

		assertNull(play.getSource());

		assertNotNull(play1);
		assertEquals(play1.getId(), play1.getId());
		assertNotNull(play1.getSource());

		assertTrue(Stream.of(play.isFinish(), play1.isFinish(), play.isSolved(), play1.isSolved())
				.allMatch(Boolean.FALSE::equals));
	}

	@Test
	void testRemovePlay() throws IOException {
		playService.removePlay(play.getId());

		assertTrue(Files.walk(tmpFolder).filter(Files::isRegularFile).map(file -> {
			try {
				return Files.readString(file);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}).noneMatch(xml -> xml.contains(play.getId())));
	}

}
