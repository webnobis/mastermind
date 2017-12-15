package com.webnobis.mastermind.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Deprecated
@Ignore
public class GameTest {
	
	private Game<Integer> game;

	@Before
	public void setUp() throws Exception {
		game = Game.create(IntStream.range(Short.MIN_VALUE, Short.MAX_VALUE).boxed().collect(Collectors.toList()), true);
		assertTrue(game.isEasyVerify());
	}

	@Test
	public void testGame() {
		assertFalse(game.isFinish());
		assertEquals(0, game.tries());
		
		List<Integer> test = Arrays.asList(0,0,0);
		game.nextTry(test);
		assertEquals(1, game.tries());
		assertEquals(test, game.getTries().iterator().next());
		assertEquals(Arrays.asList(Result.CONTAINED, Result.MISSING, Result.MISSING), game.getResults().iterator().next());
		assertFalse(game.isFinish());
		
		List<Result> result = game.nextTry(game.getVerifier().expected());
		assertTrue(result.stream().allMatch(Result.CORRECT_PLACE::equals));
		assertTrue(game.isFinish());
	}

}
