package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.GameWithSolution;

public class GameBuilderTest {

	private static final List<Integer> VALUES = Arrays.asList(Integer.MAX_VALUE, 0, Integer.MIN_VALUE);

	private GameBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new GameBuilder(() -> VALUES.stream().mapToInt(Integer::intValue));
	}

	@Test
	public void testBuild() {
		GameWithSolution gameWithSolution = builder.build(Integer.MIN_VALUE, Integer.MAX_VALUE, VALUES.size());
		assertEquals(VALUES, gameWithSolution.getSolution().getValues());
	}

	@Test
	public void testBuildMax() {
		int max = 2;
		GameWithSolution gameWithSolution = builder.build(Integer.MIN_VALUE, Integer.MAX_VALUE, max);
		assertEquals(VALUES.subList(0, max), gameWithSolution.getSolution().getValues());
	}

	@Test
	public void testBuildRange() {
		{
			GameWithSolution gameWithSolution = builder.build(-1, 99, Integer.MAX_VALUE);
			assertEquals(Collections.singletonList(0), gameWithSolution.getSolution().getValues());
		}
		{
			GameWithSolution gameWithSolution = builder.build(0, 0, Integer.MAX_VALUE);
			assertEquals(Collections.singletonList(0), gameWithSolution.getSolution().getValues());
		}
	}

	@Test
	public void testBuildEmpty() {
		{
			GameWithSolution gameWithSolution = builder.build(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
			assertEquals(Collections.emptyList(), gameWithSolution.getSolution().getValues());
		}
		{
			GameWithSolution gameWithSolution = builder.build(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
			assertEquals(Collections.emptyList(), gameWithSolution.getSolution().getValues());
		}
	}

	@Test
	public void testBuildRandom() {
		{
			int min = -9;
			int max = 12;
			int size = 20;
			GameWithSolution gameWithSolution = new GameBuilder().build(min, max, size);
			List<Integer> values = gameWithSolution.getSolution().getValues();
			assertEquals(size, values.size());
			values.forEach(i -> {
				assertFalse(i < min);
				assertFalse(i > max);
			});
		}
		{
			GameWithSolution gameWithSolution = new GameBuilder().build(Integer.MIN_VALUE, Integer.MIN_VALUE, 10);
			gameWithSolution.getSolution().getValues().forEach(i -> assertEquals(Integer.valueOf(Integer.MIN_VALUE), i));
		}
		{
			GameWithSolution gameWithSolution = new GameBuilder().build(Integer.MAX_VALUE, Integer.MAX_VALUE, 10);
			gameWithSolution.getSolution().getValues().forEach(i -> assertEquals(Integer.valueOf(Integer.MAX_VALUE), i));
		}
	}

}
