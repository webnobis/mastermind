package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class AssessmentServiceTest {

	@Mocked
	private Solution solution;

	@Mocked
	private Try theTry;

	@Mocked
	private Assessment assessment;

	@Test
	public void testAssessMatchingSize() {
		new Expectations() {
			{
				solution.getValues();
				returns(Arrays.asList(null, 9, 9, 9, null));
			}
			{
				theTry.getIdeas();
				returns(Arrays.asList(8, null, 9, 7, null));
			}
			{
				assessment.getAssessments();
				returns(Arrays.asList(Result.CORRECT_PLACE, Result.CORRECT_PLACE, Result.CONTAINED));
			}
		};

		TryWithAssessment result = AssessmentService.assess(solution, theTry);
		assertNotNull(result);
		assertEquals(theTry.getIdeas(), result.getTry().getIdeas());
		assertEquals(assessment.getAssessments(), result.getAssessment().getAssessments());
	}

	@Test
	public void testAssessNoMatchingSize() {
		new Expectations() {
			{
				solution.getValues();
				returns(Arrays.asList(1, 2, 3, 4));
			}
			{
				theTry.getIdeas();
				returns(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(1, 2, 3));
			}
			{
				List<Result> results = Stream.generate(() -> Result.CORRECT_PLACE).limit(4).collect(Collectors.toList());
				assessment.getAssessments();
				returns(results, results.subList(0, 3));
			}
		};

		{
			TryWithAssessment result = AssessmentService.assess(solution, theTry);
			assertNotNull(result);
			assertEquals(theTry.getIdeas(), result.getTry().getIdeas());
			assertEquals(assessment.getAssessments(), result.getAssessment().getAssessments());
		}
		{
			TryWithAssessment result = AssessmentService.assess(solution, theTry);
			assertNotNull(result);
			assertEquals(theTry.getIdeas(), result.getTry().getIdeas());
			assertEquals(assessment.getAssessments(), result.getAssessment().getAssessments());
		}
	}

	@Test
	public void testAssessEmpty() {
		new Expectations() {
			{
				solution.getValues();
				returns(Collections.emptyList());
			}
			{
				theTry.getIdeas();
				returns(Collections.emptyList());
			}
			{
				assessment.getAssessments();
				returns(Collections.emptyList());
			}
		};

		TryWithAssessment result = AssessmentService.assess(solution, theTry);
		assertNotNull(result);
		assertEquals(theTry.getIdeas(), result.getTry().getIdeas());
		assertEquals(assessment.getAssessments(), result.getAssessment().getAssessments());
	}

	@Test(expected = NullPointerException.class)
	public void testAssessSolutionNull() {
		AssessmentService.assess(null, theTry);

		fail("NullPointerException expected");
	}

	@Test(expected = NullPointerException.class)
	public void testAssessTryNull() {
		AssessmentService.assess(solution, null);

		fail("NullPointerException expected");
	}

	@Test(expected = NullPointerException.class)
	public void testAssessSolutionAndTryNull() {
		AssessmentService.assess(null, null);

		fail("NullPointerException expected");
	}

}
