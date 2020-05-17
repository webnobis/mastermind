package com.webnobis.mastermind.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayTest {

	private static final int ROWS = 3;

	private static final Source<Color> SOURCE = Source.of(Color.BLUE, Color.RED, null);

	private Play<Color> play;

	@BeforeEach
	void setUp() throws Exception {
		play = Play.of(ROWS, SOURCE);
	}

	@Test
	void testGetCols() {
		assertEquals(SOURCE.getSources().size(), play.getCols());
	}

	@Test
	void testGetRows() {
		assertEquals(ROWS, play.getRows());

		assertTrue(Play.of(Source.of()).getRows() < 0);
	}

	@Test
	void testGetResults() {
		assertTrue(play.getResults().isEmpty());

		assertSame(1, play.ofAddedResult(Result.of(Source.of())).getResults().size());
	}

	@Test
	void testGetSource() {
		assertEquals(SOURCE, play.getSource());
	}

	@Test
	void testIsFinish() {
		assertFalse(play.isFinish());

		assertTrue(new Play<>(ROWS,
				Stream.<Result<Color>>generate(() -> Result.of(Source.of())).limit(ROWS).collect(Collectors.toList()),
				Source.of()).isFinish());

		assertTrue(play.ofAddedResult(Result.of(Source.of((Color) null, (Color) null, (Color) null), ResultType.EXACT,
				ResultType.EXACT, ResultType.EXACT)).isFinish());
	}

	@Test
	void testIsSolved() {
		assertFalse(play.isSolved());

		assertFalse(play.ofAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT)).isSolved());
		assertFalse(play.ofAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT, ResultType.PRESENT))
				.isSolved());

		assertTrue(
				play.ofAddedResult(Result.of(SOURCE, ResultType.EXACT, ResultType.EXACT, ResultType.EXACT)).isSolved());
	}

	enum Color {
		BLUE, GREEN, RED;
	}

}
