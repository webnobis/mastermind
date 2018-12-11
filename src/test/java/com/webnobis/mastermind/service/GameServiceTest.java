package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Status;

public class GameServiceTest {
	
	private final static String ID = "id";
	
	private final static List<Boolean> SOLUTION = Arrays.asList(true, false);
	
	private Game<Boolean> gameBefore;

	@Before
	public void setUp() throws Exception {
		gameBefore = new Game<Boolean>(ID, SOLUTION, Collections.singletonList(SOLUTION), Collections.emptyList(), false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewGame() {
		Game<Boolean> game = GameService.newGame(SOLUTION);
		assertNotNull(game);
		assertFalse(game.getId().isEmpty());
		assertEquals(SOLUTION, game.getSolution());
		assertTrue(game.getNextTry().isEmpty());
		assertTrue(game.getVerify().isEmpty());
		assertFalse(game.isFinish());
	}

	@Test
	public void testNextTry() {
		Game<Boolean> game = GameService.nextTry(gameBefore, Collections.singletonList(true));
		assertNotNull(game);
		assertEquals(ID, game.getId());
		assertEquals(SOLUTION, game.getSolution());
		assertEquals(2, game.getNextTry().size());
		assertTrue(game.getNextTry().contains(SOLUTION));
		assertTrue(game.getVerify().isEmpty());
		assertFalse(game.isFinish());
	}
	
	@Test
	public void testVerifyLast() {
		Game<Boolean> game = GameService.verify(gameBefore);
		assertNotNull(game);
		assertEquals(ID, game.getId());
		assertEquals(SOLUTION, game.getSolution());
		assertEquals(1, game.getNextTry().size());
		assertEquals(SOLUTION, game.getNextTry().get(0));
		assertEquals(1, game.getVerify().size());
		assertEquals(SOLUTION.size(), game.getVerify().get(0).size());
		assertEquals(Stream.generate(() -> Status.CORRECT_PLACE).limit(SOLUTION.size()).collect(Collectors.toList()), game.getVerify().get(0));
		assertTrue(game.isFinish());
	}

}
