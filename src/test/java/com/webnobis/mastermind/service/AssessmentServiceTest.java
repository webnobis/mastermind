package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class AssessmentServiceTest {

	@Test
	void testAssess() {
		Source<Integer> solutionSource = Source.of(1, 2, 1, 2);

		{
			Source<Integer> trySource = Source.of(1, 1, 1, 2);
			Result<Integer> result = AssessmentService.assess(solutionSource, trySource);
			assertNotNull(result);
			List<ResultType> results = result.getResults();
			assertSame(3, results.size());
			assertTrue(results.stream().allMatch(ResultType.EXACT::equals));
		}
		{
			Source<Integer> trySource = Source.of(null, 1, 1, 2);
			Result<Integer> result = AssessmentService.assess(solutionSource, trySource);
			assertNotNull(result);
			List<ResultType> results = result.getResults();
			assertSame(3, results.size());
			assertEquals(1L, results.stream().filter(ResultType.PRESENT::equals).count());
			assertEquals(2L, results.stream().filter(ResultType.EXACT::equals).count());
		}
		{
			Source<Integer> trySource = Source.of(1, null, 1, 2);
			Result<Integer> result = AssessmentService.assess(solutionSource, trySource);
			assertNotNull(result);
			List<ResultType> results = result.getResults();
			assertSame(3, results.size());
			assertTrue(results.stream().allMatch(ResultType.EXACT::equals));
		}
		{
			Result<Integer> result = AssessmentService.assess(solutionSource, solutionSource);
			assertNotNull(result);
			List<ResultType> results = result.getResults();
			assertSame(solutionSource.getSources().size(), results.size());
			assertTrue(results.stream().allMatch(ResultType.EXACT::equals));
		}
	}

	@Test
	void testAssessEmpty() {
		Source<Object> solutionSource = Source.of();
		Source<Object> trySource = Source.of();

		Result<Object> result = AssessmentService.assess(solutionSource, trySource);
		assertNotNull(result);
		assertEquals(trySource.getSources(), result.getSources());
		assertTrue(result.getResults().isEmpty());
	}

	@Test
	void testAssessOne() {
		Source<Boolean> solutionSource = Source.of(Boolean.TRUE);
		Source<Boolean> trySource = Source.of(Boolean.FALSE);

		Result<Boolean> result = AssessmentService.assess(solutionSource, trySource);
		assertNotNull(result);
		assertEquals(trySource.getSources(), result.getSources());
		assertTrue(result.getResults().isEmpty());

		result = AssessmentService.assess(solutionSource, solutionSource);
		assertNotNull(result);
		assertEquals(solutionSource.getSources(), result.getSources());
		List<ResultType> results = result.getResults();
		assertSame(1, results.size());
		assertEquals(ResultType.EXACT, results.iterator().next());
	}

	@Test
	void testAssessOneNull() {
		Source<Object> solutionSource = Source.of((Object) null);
		Source<Object> trySourceNoMatch = Source.of(new Object());

		Result<Object> result = AssessmentService.assess(solutionSource, trySourceNoMatch);
		assertNotNull(result);
		assertTrue(result.getResults().isEmpty());

		result = AssessmentService.assess(solutionSource, solutionSource);
		assertNotNull(result);
		List<ResultType> results = result.getResults();
		assertSame(1, results.size());
		assertEquals(ResultType.EXACT, results.iterator().next());
	}

	@Test
	void testAssessTwo() {
		Source<Boolean> solutionSource = Source.of(Boolean.TRUE, Boolean.FALSE);
		Source<Boolean> trySourceNoMatch = Source.of(null, null);
		Source<Boolean> trySourcePresent1 = Source.of(Boolean.FALSE, null);
		Source<Boolean> trySourcePresent2 = Source.of(null, Boolean.TRUE);

		Result<Boolean> result = AssessmentService.assess(solutionSource, trySourceNoMatch);
		assertNotNull(result);
		assertTrue(result.getResults().isEmpty());

		Stream.of(trySourcePresent1, trySourcePresent2)
				.map(trySource -> AssessmentService.assess(solutionSource, trySource)).forEach(r -> {
					assertNotNull(r);
					List<ResultType> results = r.getResults();
					assertSame(1, results.size());
					assertEquals(ResultType.PRESENT, results.iterator().next());
				});

		result = AssessmentService.assess(solutionSource, solutionSource);
		assertNotNull(result);
		assertTrue(result.getResults().stream().allMatch(ResultType.EXACT::equals));
	}

	@Test
	void testAssessMin() {
		Source<Boolean> solutionSource = Source.of(Boolean.TRUE, Boolean.FALSE);
		Source<Boolean> trySourceShorter = Source.of(Boolean.TRUE);
		Source<Boolean> trySourceLonger = Source.of(Boolean.FALSE, null, Boolean.TRUE);

		Result<Boolean> result = AssessmentService.assess(solutionSource, trySourceShorter);
		assertNotNull(result);
		List<ResultType> results = result.getResults();
		assertSame(1, results.size());
		assertEquals(ResultType.EXACT, results.iterator().next());

		result = AssessmentService.assess(solutionSource, trySourceLonger);
		assertNotNull(result);
		results = result.getResults();
		assertSame(1, results.size());
		assertEquals(ResultType.PRESENT, results.iterator().next());
	}

}
