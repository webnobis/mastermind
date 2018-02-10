package com.webnobis.mastermind.service.handler;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.net.HostAndPort;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JMockit.class)
public class TryHandlerTest {

	private static final String TEXT = "try as text";

	private static final String ADDRESS = "server:9999";

	private static final String CALL_URI = "/the/call/path/";

	private static final String ID = "the id";

	private static final String REDIRECT_URI = "http://" + ADDRESS + CALL_URI + ID;

	private static final String HEADER_KEY = "location";

	@Mocked
	private Function<String, Try> tryTransformer;

	@Mocked
	private BiFunction<Solution, Try, TryWithAssessment> assessmentService;

	@Mocked
	private BiFunction<GameWithSolution, TryWithAssessment, GameWithSolution> gameUpdateService;

	@Mocked
	private Try theTry;

	@Mocked
	private TryWithAssessment tryWithAssessment;

	@Mocked
	private GameWithSolution gameWithSolution1;

	@Mocked
	private GameWithSolution gameWithSolution2;

	@Mocked
	private GameStore gameStore;

	private Handler handler;

	@Before
	public void setUp() throws Exception {
		handler = new TryHandler(gameStore, tryTransformer, assessmentService, gameUpdateService);

		new Expectations() {
			{
				gameStore.find(ID);
				returns(gameWithSolution1);
			}
			{
				tryTransformer.apply(TEXT);
				returns(theTry);
			}
			{
				assessmentService.apply(withInstanceOf(Solution.class), theTry);
				returns(tryWithAssessment);
			}
		};
	}

	@Test
	public void testHandle() throws Exception {
		new Expectations() {
			{
				gameUpdateService.apply(gameWithSolution1, tryWithAssessment);
				returns(gameWithSolution2);
			}
			{
				gameStore.store(gameWithSolution2);
				returns(ID);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID))
				.body(TEXT, Constants.CONTENT_TYPE)
				.localAddress(HostAndPort.fromString(ADDRESS))
				.uri(CALL_URI));

		assertEquals(Constants.REDIRECT_CODE, result.getStatus().getCode());
		assertEquals(REDIRECT_URI, result.getHeaders().asMultiValueMap().get(HEADER_KEY));
	}

	@Test
	public void testHandleFailed() throws Exception {
		IllegalStateException e = new IllegalStateException();
		new Expectations() {
			{
				gameUpdateService.apply(gameWithSolution1, tryWithAssessment);
				result = e;
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID))
				.body(TEXT, Constants.CONTENT_TYPE));

		assertEquals(e, result.exception(IllegalStateException.class));
	}

}
