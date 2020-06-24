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
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class PlayServiceTest {

	private static final Source<Integer> SOURCE = Source.of(42);

	private static final int COLS = 3;

	private static final IntFunction<Source<Integer>> SOLUTION_GENERATOR = cols -> {
		assertSame(COLS, cols);
		return SOURCE;
	};

	private static final BiFunction<Source<Integer>, Source<Integer>, Result<Integer>> ASSESSMENT_SERVICE = (source,
			trySource) -> Result.of(trySource, ResultType.PRESENT);

	private Path tmpFolder;

	private PlayService<Integer> playService;

	private Play<Integer> play;

	@BeforeEach
	void setUp() throws Exception {
		tmpFolder = Files.createTempDirectory(PlayServiceTest.class.getSimpleName());

		playService = new PlayService<>(tmpFolder, SOLUTION_GENERATOR, ASSESSMENT_SERVICE);

		play = playService.newPlay(COLS);
		assertNull(play.getSource());
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
		Play<Integer> play2 = playService.newPlay(COLS, 2);

		assertNotNull(play);
		assertNotNull(play2);
		assertNull(play2.getSource());
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
		Play<Integer> play1 = playService.getPlay(play.getId());

		assertNotNull(play1);
		assertNull(play1.getSource());
		assertEquals(play, play1);
		assertEquals(play.getId(), play1.getId());
	}

	@Test
	void testNextTry() {
		Source<Integer> source = Source.of(42);
		Play<Integer> play1 = playService.nextTry(play.getId(), source);

		assertTrue(play.getResults().isEmpty());

		assertNotNull(play1);
		assertNull(play1.getSource());
		assertEquals(play1.getId(), play1.getId());
		List<Result<Integer>> results = play1.getResults();
		assertSame(1, results.size());
		Result<Integer> result = results.iterator().next();
		assertEquals(source.getSources(), result.getSources());
		assertSame(1, result.getResults().size());
		assertEquals(ResultType.PRESENT, result.getResults().iterator().next());
	}

	@Test
	void testQuitPlay() {
		Play<Integer> play1 = playService.quitPlay(play.getId());

		assertNull(play.getSource());

		assertNotNull(play1);
		assertNotNull(play1.getSource());
		assertEquals(play1.getId(), play1.getId());

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
