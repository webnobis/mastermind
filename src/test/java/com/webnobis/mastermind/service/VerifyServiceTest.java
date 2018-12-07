package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.Status;


public class VerifyServiceTest {
	
	private static final List<Integer> SOLUTION = Arrays.asList(1,1,8,8);
	
	private VerifyService<Integer> verifyService;

	@Before
	public void setUp() throws Exception {
		verifyService = new VerifyService<>(SOLUTION);
	}

	@Test
	public void testVerifyMissing() {
		List<Status> result = verifyService.verify(Arrays.asList(0,0,0));
		assertEquals(3, result.size());
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyContained() {
		List<Status> result = verifyService.verify(Arrays.asList(0,0,1,0,0));
		assertEquals(5, result.size());
		assertTrue(result.remove(Status.CONTAINED));
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyCorrectPlace() {
		List<Status> result = verifyService.verify(Arrays.asList(0,0,0,8,0));
		assertEquals(5, result.size());
		assertTrue(result.remove(Status.CORRECT_PLACE));
		assertTrue(result.stream().allMatch(Status.MISSING::equals));
	}

	@Test
	public void testVerifyCorrectPlaceAndContained() {
		List<Status> result = verifyService.verify(Arrays.asList(8,1,8,1));
		assertEquals(4, result.size());
		assertEquals(2L, result.stream().filter(Status.CORRECT_PLACE::equals).count());
		assertEquals(2L, result.stream().filter(Status.CONTAINED::equals).count());
	}

	@Test
	public void testVerifyAllCorrectPlace() {
		List<Status> result = verifyService.verify(Arrays.asList(1,1));
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(Status.CORRECT_PLACE::equals));
	}

	@Test
	public void testVerifyAllContained() {
		List<Status> result = verifyService.verify(Arrays.asList(8,8));
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(Status.CONTAINED::equals));
	}
	
	@Test
	public void testVerifyFinish() {
		assertFalse(verifyService.verifyFinish(Stream.of(SOLUTION, SOLUTION).flatMap(List::stream).collect(Collectors.toList())));
		assertFalse(verifyService.verifyFinish(SOLUTION.subList(0, SOLUTION.size() - 1)));
		assertTrue(verifyService.verifyFinish(SOLUTION));
	}

}
