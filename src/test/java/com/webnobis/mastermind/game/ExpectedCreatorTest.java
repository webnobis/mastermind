package com.webnobis.mastermind.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ExpectedCreatorTest {

	@DataPoints
	public static final int[] INTEGER = { Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Theory
	public void testCreateInteger(int size, int from, int to) {
		Supplier<List<Integer>> supplier = ExpectedCreator.create(size, from, to);
		if (from < to && size > 0) {
			List<Integer> list = supplier.get();
			assertEquals(size, list.size());
			SortedSet<Integer> set = new TreeSet<>(list);
			assertFalse(set.first() < from);
			assertFalse(set.last() > to);
		} else {
			try {
				supplier.get();
				fail(IllegalArgumentException.class.getName() + " expected");
			} catch (IllegalArgumentException e) {
			}
		}
	}

}
