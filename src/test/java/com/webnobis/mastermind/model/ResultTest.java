package com.webnobis.mastermind.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultTest {

	private Result<Boolean> result;

	@BeforeEach
	void setUp() throws Exception {
		result = Result.of(Source.of());
	}

	@Test
	void testOf() {
		Result<Boolean> newResult = Result.of(Source.of(Boolean.TRUE), ResultType.PRESENT, ResultType.EXACT);

		assertNotNull(newResult);
		List<ResultType> results = newResult.getResults();
		assertNotNull(results);
		assertSame(1, results.size());
		assertEquals(ResultType.PRESENT, results.iterator().next());
	}

	@Test
	void testGetSources() {
		assertNotNull(result.getSources());
		assertTrue(result.getSources().isEmpty());
	}

	@Test
	void testGetResults() {
		assertNotNull(result.getResults());
		assertTrue(result.getResults().isEmpty());
	}

}
