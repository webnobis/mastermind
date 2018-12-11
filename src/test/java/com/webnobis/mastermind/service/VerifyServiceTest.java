package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.webnobis.mastermind.model.Status;

public class VerifyServiceTest {

	private static final List<Integer> SOLUTION = Arrays.asList(1, 1, 8, 8);

	@Test
	public void testVerifyMissing() {
		List<Status> result = VerifyService.verify(Arrays.asList(0, 0, 0), SOLUTION);
		assertEquals(3, result.size());
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyContained() {
		List<Status> result = VerifyService.verify(Arrays.asList(0, 0, 1, 0, 0), SOLUTION);
		assertEquals(5, result.size());
		assertTrue(result.remove(Status.CONTAINED));
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyCorrectPlace() {
		List<Status> result = VerifyService.verify(Arrays.asList(0, 0, 0, 8, 0), SOLUTION);
		assertEquals(5, result.size());
		assertTrue(result.remove(Status.CORRECT_PLACE));
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyCorrectPlaceAndContained() {
		List<Status> result = VerifyService.verify(Arrays.asList(8, 1, 8, 1), SOLUTION);
		assertEquals(4, result.size());
		assertEquals(2L, result.stream().filter(Status.CORRECT_PLACE::equals).count());
		assertEquals(2L, result.stream().filter(Status.CONTAINED::equals).count());
	}

	@Test
	public void testVerifyAllCorrectPlace() {
		List<Status> result = VerifyService.verify(Arrays.asList(1, 1), SOLUTION);
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(Status.CORRECT_PLACE::equals));
	}

	@Test
	public void testVerifyAllContained() {
		List<Status> result = VerifyService.verify(Arrays.asList(8, 8), SOLUTION);
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(Status.CONTAINED::equals));
	}

	@Test
	public void testVerifyAll() {
		List<List<Status>> result = VerifyService.verifyAll(Collections.singletonList(Arrays.asList(8, 8)), SOLUTION);
		assertEquals(1, result.size());
		assertEquals(2, result.get(0).size());
		assertTrue(result.stream().flatMap(List::stream).allMatch(Status.CONTAINED::equals));
	}

	@Test
	public void testVerifyFinish() {
		assertFalse(VerifyService.verifyFinish(Collections.singletonList(Stream.of(SOLUTION, SOLUTION)
				.flatMap(List::stream)
				.collect(Collectors.toList())), SOLUTION));
		assertFalse(VerifyService.verifyFinish(Collections.singletonList(SOLUTION.subList(0, SOLUTION.size() - 1)), SOLUTION));
		assertTrue(VerifyService.verifyFinish(Collections.singletonList(SOLUTION), SOLUTION));
	}

}
