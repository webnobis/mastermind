package com.webnobis.mastermind.service.handler;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.http.Status;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JMockit.class)
public class ResignHandlerTest {

	private static final String ID = "the id";

	private static final String TEXT = "the solution as text";

	@Mocked
	private Function<Solution, String> solutionTransformer;

	@Mocked
	private Solution solution;

	@Mocked
	private GameWithSolution gameWithSolution;

	@Mocked
	private GameStore gameStore;

	private Handler handler;

	@Before
	public void setUp() throws Exception {
		handler = new ResignHandler(gameStore, solutionTransformer);
	}

	@Test
	public void testHandle() throws Exception {
		new StrictExpectations() {
			{
				gameStore.find(ID);
				returns(gameWithSolution);
			}
			{
				gameWithSolution.getSolution();
				returns(solution);
			}
			{
				solutionTransformer.apply(solution);
				returns(TEXT);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID)));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(TEXT, result.getBodyText());
	}

	@Test
	public void testHandleFailed() throws Exception {
		new StrictExpectations() {
			{
				gameStore.find(ID);
				returns(null);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID)));

		assertEquals(NoSuchElementException.class, result.exception(NoSuchElementException.class).getClass());
	}
}
