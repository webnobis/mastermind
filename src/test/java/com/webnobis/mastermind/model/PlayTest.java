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

	private static final Class<Color> TYPE = Color.class;

	private static final int COLS = 3;

	private static final int ROWS = 2;

	private static final Result<Color> TRY1 = Result.of(Source.of(Color.RED), ResultType.PRESENT);

	private static final Source<Color> SOURCE = Source.of(Color.BLUE, Color.RED, null);

	private Play<Color> play;

	@BeforeEach
	void setUp() throws Exception {
		play = new Play<>(ID, TYPE, COLS, ROWS, Collections.singletonList(TRY1), SOURCE);
	}

	@Test
	void testOf() {
		Play<Boolean> newPlay = Play.of(Boolean.class, COLS);

		assertNotNull(newPlay.getId());
		assertEquals(-1, newPlay.getRows());
		assertNotNull(newPlay.getResults());
		assertTrue(newPlay.getResults().isEmpty());
		assertNull(newPlay.getSource());
	}

	@Test
	void testOfNull() {
		assertThrows(NullPointerException.class,
				() -> new Play<Boolean>(null, Boolean.class, 1, 1, Collections.emptyList(), Source.of()));
		assertThrows(NullPointerException.class,
				() -> new Play<Byte>("id1", null, 1, 1, Collections.emptyList(), Source.of()));
		assertThrows(NullPointerException.class, () -> new Play<Byte>("id1", Byte.class, 1, 1, null, Source.of()));
		assertDoesNotThrow(() -> new Play<Short>("id1", Short.class, 1, 1, Collections.emptyList(), null));
	}

	@Test
	void testWithAddedResult() {
		Result<Color> result = Result.of(Source.of());
		Play<Color> newPlay = play.withAddedResult(result);

		assertEquals(play.getId(), newPlay.getId());
		assertEquals(play.getType(), newPlay.getType());
		assertSame(play.getCols(), newPlay.getCols());
		assertSame(play.getRows(), newPlay.getRows());
		assertEquals(play.getSource(), newPlay.getSource());

		assertSame(2, newPlay.getResults().size());
		assertTrue(newPlay.getResults().contains(result));
	}

	@Test
	void testWithSource() {
		assertNull(play.withSource(null).getSource());
	}

	@Test
	void testGetId() {
		assertEquals(ID, play.getId());
	}

	@Test
	void testGetType() {
		assertEquals(TYPE, play.getType());
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
		assertEquals(SOURCE, play.withSource(SOURCE).getSource());
	}

	@Test
	void testIsFinish() {
		assertFalse(play.isFinish());

		assertTrue(new Play<Object>("id 1", Object.class, COLS, ROWS,
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

		assertEquals(play, play.withSource(null));
		assertEquals(play, play.withSource(Source.of()));

		assertEquals(play, play.withAddedResult(null));
		assertEquals(play, play.withAddedResult(Result.of(Source.of())));

		assertNotEquals(play, null);
		assertNotEquals(play, new Object());
		assertNotEquals(Play.of(Boolean.class, COLS), Play.of(Boolean.class, COLS));
	}

	enum Color {
		BLUE, GREEN, RED;
	}

}
