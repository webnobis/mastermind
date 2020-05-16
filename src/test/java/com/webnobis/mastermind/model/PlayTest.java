package com.webnobis.mastermind.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
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
		fail("Not yet implemented");
	}

	@Test
	void testIsFinish() {
		fail("Not yet implemented");
	}

	@Test
	void testIsSolved() {
		fail("Not yet implemented");
	}
	
	enum Color {
		BLUE, GREEN, RED;
	}

}
