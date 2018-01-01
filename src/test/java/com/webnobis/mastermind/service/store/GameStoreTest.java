package com.webnobis.mastermind.service.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.GameWithSolution;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GameStoreTest {

	private static final String ID1 = "id 2";

	private static final String ID2 = "id 2";

	@Mocked
	private GameWithSolution game1;

	@Mocked
	private GameWithSolution game2;

	@Test
	public void testStoreFind() {
		assertNull(GlobalGameStore.INSTANCE.find(ID1));

		new Expectations() {
			{
				game1.getId();
				returns(ID1);
			}
		};

		assertEquals(ID1, GlobalGameStore.INSTANCE.store(game1));
		assertEquals(game1, GlobalGameStore.INSTANCE.find(ID1));
	}

	@Test
	public void testStoreDeleteFind() {
		new Expectations() {
			{
				game2.getId();
				returns(ID2);
			}
		};

		assertEquals(ID2, GlobalGameStore.INSTANCE.store(game2));
		assertEquals(game2, GlobalGameStore.INSTANCE.find(ID2));

		GlobalGameStore.INSTANCE.delete(ID2);
		assertNull(GlobalGameStore.INSTANCE.find(ID2));
	}

}
