package com.webnobis.mastermind.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayTest {

	private static final String ID = "the id";

	private static final int COLS = 3;

	private static final int ROWS = 2;

	private static final Result<Color> TRY1 = Result.of(Source.of(Color.RED), ResultType.PRESENT);

	private static final Source<Color> SOURCE = Source.of(Color.BLUE, Color.RED, null);

	private Play<Color> play;

	@BeforeEach
	void setUp() throws Exception {
		play = new Play<>(ID, COLS, ROWS, Collections.singletonList(TRY1), SOURCE);
	}

	@Test
	void testOf() {
		Play<Color> newPlay = Play.of(COLS, SOURCE);

		assertNotNull(newPlay);
		assertNotNull(newPlay.getId());
		assertEquals(-1, newPlay.getRows());
		assertNotNull(newPlay.getResults());
		assertTrue(newPlay.getResults().isEmpty());
		assertNotNull(newPlay.getSource());
	}

	@Test
	void testOfNull() {
		assertThrows(NullPointerException.class,
				() -> new Play<Boolean>(null, 1, 1, Collections.emptyList(), Source.of()));
		assertThrows(NullPointerException.class, () -> new Play<Byte>("id1", 1, 1, null, Source.of()));
		assertDoesNotThrow(() -> new Play<Short>("id1", 1, 1, Collections.emptyList(), null));
	}

	@Test
	void testWithAddedResult() {
		Result<Color> result = Result.of(Source.of());
		Play<Color> newPlay = play.withAddedResult(result);

		assertEquals(play.getId(), newPlay.getId());
		assertSame(play.getCols(), newPlay.getCols());
		assertSame(play.getRows(), newPlay.getRows());
		assertEquals(play.getSource(), newPlay.getSource());

		assertSame(2, newPlay.getResults().size());
		assertTrue(newPlay.getResults().contains(result));
	}

	@Test
	void testWithoutSource() {
		assertNull(play.withoutSource().getSource());
	}

	@Test
	void testGetId() {
		assertEquals(ID, play.getId());
	}

	@Test
	void testGetCols() {
		assertSame(COLS, play.getCols());
	}

	@Test
	void testGetRows() {
		assertSame(ROWS, play.getRows());
	}

	@Test
	void testGetResults() {
		List<Result<Color>> results = play.getResults();

		assertNotNull(results);
		assertSame(1, results.size());
		assertEquals(TRY1, results.iterator().next());
	}

	@Test
	void testGetSource() {
		assertEquals(SOURCE, play.getSource());
	}

	@Test
	void testIsUnlimited() {
		assertFalse(play.isUnlimited());

		assertTrue(Play.of(COLS, Source.of()).isUnlimited());
	}

	@Test
	void testIsFinish() {
		assertFalse(new Play<>("id 1", COLS, ROWS, Collections.emptyList(), null).isFinish());
		assertTrue(new Play<>("id 1", COLS, ROWS, Collections.emptyList(), Source.of()).isFinish());

		assertTrue(new Play<>("id 1", COLS, ROWS,
				Stream.generate(() -> Result.of(Source.of())).limit(ROWS).collect(Collectors.toList()), null)
						.isFinish());

		assertTrue(play.withAddedResult(Result.of(Source.of((Color) null, (Color) null, (Color) null), ResultType.EXACT,
				ResultType.EXACT, ResultType.EXACT)).isFinish());
	}

	@Test
	void testIsSolved() {
		assertFalse(play.isSolved());

		assertFalse(play.withAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT)).isSolved());
		assertFalse(play.withAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT, ResultType.PRESENT))
				.isSolved());
		assertTrue(play.withAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT, ResultType.EXACT))
				.isSolved());
	}

	@Test
	void testEquals() {
		assertEquals(play, play);

		assertEquals(play, play.withoutSource());

		assertEquals(play, play.withAddedResult(null));
		assertEquals(play, play.withAddedResult(Result.of(Source.of())));

		assertNotEquals(play, (Play<?>) null);
		assertNotEquals(play, new Object());
		assertNotEquals(Play.of(COLS, Source.of()), Play.of(COLS, Source.of()));
	}

	enum Color {
		BLUE, GREEN, RED;
	}

}
