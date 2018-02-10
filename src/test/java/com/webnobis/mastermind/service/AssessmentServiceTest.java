package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.Assessment;
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

	@Ignore
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
