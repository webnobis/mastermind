package com.webnobis.mastermind.service.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.game.Game;

public class GameStoreTest {
	
	private GameStore gameStore;

	@Before
	public void setUp() throws Exception {
		gameStore = GameStore.get();
	}

	@Test
	public void testNewGetRemove() {
		Game<Boolean> game = Game.create(Collections.emptyList(), false);
		
		String id = gameStore.store(game);
		assertNotNull(id);
		assertEquals(id, gameStore.store(game));
		
		Game<Boolean> gameGet = gameStore.get(id);
		assertEquals(game, gameGet);
		
		gameStore.remove(id);
		assertNull(gameStore.get(id));
	}
	
	@Test
	public void testGetAfterTries() {
		Game<IndexOutOfBoundsException> game = Game.create(() -> new IndexOutOfBoundsException(), true);
		gameStore.store(game);
		
		game.nextTry(Collections.emptyList());
		assertEquals(1, game.tries());
		
		Game<Boolean> gameGet = gameStore.get(game.getId());
		assertEquals(game, gameGet);
	}

}
