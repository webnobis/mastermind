package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

class AssessmentServiceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
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

}
