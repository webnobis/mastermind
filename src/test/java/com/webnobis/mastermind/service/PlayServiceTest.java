package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import javax.xml.bind.JAXB;

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

	private static final int ROWS = 2;

	private static final IntFunction<Source<Integer>> SOLUTION_GENERATOR = cols -> {
		assertSame(COLS, cols);
		return SOURCE;
	};

	private static final BiFunction<Source<Integer>, Source<Integer>, Result<Integer>> ASSESSMENT_SERVICE = (source,
			trySource) -> Result.of(trySource, ResultType.PRESENT);

	private Path tmpFile;

	private PlayService<Integer> playService;

	@BeforeEach
	void setUp() throws Exception {
		tmpFile = Files.createTempFile(PlayServiceTest.class.getSimpleName(), ".xml");

		playService = new PlayService<>(SOLUTION_GENERATOR, ASSESSMENT_SERVICE);
	}

	@AfterEach
	void tearDown() throws Exception {
		Optional.ofNullable(tmpFile).filter(Files::exists).ifPresent(t -> {
			try {
				Files.delete(t);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}

	@Test
	void testNewUnlimitedPlay() throws IOException {
		Play<Integer> play = playService.newPlay(COLS);
		assertNotNull(play);
		assertNotEquals(play, playService.newPlay(COLS));
		assertNotNull(play.getId());
		assertSame(COLS, play.getCols());
		assertSame(-1, play.getRows());
		assertTrue(play.getResults().isEmpty());
		assertNull(play.getSource());
		assertFalse(play.isFinish());
		assertFalse(play.isSolved());
		assertTrue(play.isUnlimited());
	}

	@Test
	void testNewLimitedPlay() throws IOException {
		Play<Integer> play = playService.newPlay(COLS, ROWS);
		assertNotNull(play);
		assertNotNull(play.getId());
		assertSame(COLS, play.getCols());
		assertSame(ROWS, play.getRows());
		assertTrue(play.getResults().isEmpty());
		assertNull(play.getSource());
		assertFalse(play.isFinish());
		assertFalse(play.isSolved());
		assertFalse(play.isUnlimited());
	}

	@Test
	void testGetPlay() {
		JAXB.marshal(Play.of(COLS, ROWS, Source.of(1, 2, 3)), tmpFile.toFile());

		Play<Integer> play = playService.getPlay(tmpFile);
		assertNotNull(play);
		assertSame(COLS, play.getCols());
		assertSame(ROWS, play.getRows());
	}

	@Test
	void testNextTry() {
		String id = playService.newPlay(COLS).getId();
		assertNotNull(id);

		Source<Integer> source = Source.of(3);
		Play<Integer> play = playService.nextTry(id, source);
		assertNotNull(play);
		assertEquals(id, play.getId());

		assertSame(1, play.getResults().size());
		Result<Integer> result = play.getResults().iterator().next();
		assertNotNull(result);
		assertEquals(ResultType.PRESENT, result.getResults().iterator().next());
	}

	@Test
	void testQuitPlay() {
		Play<?> newPlay = playService.newPlay(COLS, ROWS);
		assertNotNull(newPlay);
		assertFalse(newPlay.isFinish());
		assertFalse(newPlay.isSolved());
		assertFalse(newPlay.isUnlimited());
		assertNull(newPlay.getSource());

		Play<Integer> play = playService.quitPlay(newPlay.getId());
		assertNotNull(play);
		assertEquals(newPlay.getId(), play.getId());
		assertTrue(play.isFinish());
		assertFalse(play.isSolved());
		assertFalse(play.isUnlimited());
		assertNotNull(play.getSource());
		assertTrue(play.getResults().isEmpty());
	}

	@Test
	void testRemovePlay() {
		Play<?> newPlay = playService.newPlay(COLS, ROWS);
		assertNotNull(newPlay);
		JAXB.marshal(newPlay, tmpFile.toFile());
		assertTrue(Files.exists(tmpFile));

		playService.removePlay(tmpFile);

		assertFalse(Files.exists(tmpFile));
	}

	@Test
	void testStorePlay() {
		Play<?> newPlay = playService.newPlay(COLS, ROWS);
		assertNotNull(newPlay);

		playService.storePlay(newPlay.getId(), tmpFile);

		assertTrue(Files.exists(tmpFile));
		Play<?> play = JAXB.unmarshal(tmpFile.toFile(), Play.class);
		assertNotNull(play);
		assertEquals(newPlay.getId(), play.getId());
	}

}
