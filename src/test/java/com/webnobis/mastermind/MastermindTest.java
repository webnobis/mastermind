package com.webnobis.mastermind;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MastermindTest {

	@Test
	public void testBuildGame() {
		assertNotNull(Mastermind.buildGame());
	}

}
