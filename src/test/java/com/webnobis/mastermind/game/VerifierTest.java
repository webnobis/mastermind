package com.webnobis.mastermind.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Deprecated
@Ignore
public class VerifierTest {

	private List<E> expected;

	private Verifier<E> verifier;

	@Before
	public void setUp() throws Exception {
		expected = Arrays.asList(E.A, E.B, E.D, E.A);
		verifier = () -> expected;
	}

	private List<List<E>> generateTests() {
		List<List<E>> list = new ArrayList<>();
		for (int a = 0; a < E.values().length; a++) {
			for (int b = 0; b < E.values().length; b++) {
				for (int c = 0; c < E.values().length; c++) {
					for (int d = 0; d < E.values().length; d++) {
						list.add(Arrays.asList(E.values()[a], E.values()[b], E.values()[c], E.values()[d]));
					}
				}
			}
		}
		return list;
	}

	private List<Result> expected(List<E> test) {
		Map<Integer,E> other = new HashMap<>();
		Set<Integer> correct = new HashSet<>();
		for (int i = 0; i < expected.size(); i++) {
			if (expected.get(i).equals(test.get(i))) {
				correct.add(i);
			} else {
				other.put(i, expected.get(i));
			}
		}
		for (Integer i : correct) {
			other.remove(i);
		}
		List<E> contained = new ArrayList<>(other.values());
		List<Result> list = new ArrayList<>();
		for (int i = 0; i < test.size(); i++) {
			if (correct.contains(i)) {
				list.add(Result.CORRECT_PLACE);
			} else if (contained.remove(test.get(i))){
				list.add(Result.CONTAINED);
			} else {
				list.add(Result.MISSING);
			}
		}
		return list;
	}

	@Test
	public void testVerifyEasy() {
		for (List<E> test : generateTests()) {
			assertEquals(expected + " : " + test, expected(test), verifier.verifyEasy(test));
		}
	}

	@Test
	public void testVerifyHard() {
		List<Result> expected;
		for (List<E> test : generateTests()) {
			expected = new ArrayList<>(expected(test));
			for (Iterator<Result> it = expected.iterator(); it.hasNext();) {
				if (Result.MISSING.equals(it.next())) {
					it.remove();
				}
			}
			Collections.sort(expected);
			assertEquals(expected + " : " + test, expected, verifier.verifyHard(test));
		}
	}

	private enum E {

		A, B, C, D;

	}

}
