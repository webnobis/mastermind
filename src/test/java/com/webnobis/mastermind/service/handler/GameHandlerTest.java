package com.webnobis.mastermind.service.handler;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.store.GameStore;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.http.Status;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JMockit.class)
public class GameHandlerTest {

	private static final String ID = "the id";

	private static final String TEXT1 = "game as text";

	private static final String TEXT2 = "finished game as text";

	@Mocked
	private Function<Game, String> gameTransformer;

	@Mocked
	private Game game;

	@Mocked
	private GameWithSolution gameWithSolution;

	@Mocked
	private GameStore gameStore;

	private Handler handler;

	@Before
	public void setUp() throws Exception {
		handler = new GameHandler(gameStore, gameTransformer);

		new Expectations() {
			{
				gameStore.find(ID);
				returns(gameWithSolution);
			}
			{
				gameWithSolution.getGame();
				returns(game);
			}
		};
	}

	@Test
	public void testHandle() throws Exception {
		new Expectations() {
			{
				gameTransformer.apply(game);
				returns(TEXT1);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID)));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(TEXT1, result.getBodyText());
	}

	@Test
	public void testHandleFinish() throws Exception {
		new Expectations() {
			{
				gameTransformer.apply(game);
				returns(TEXT2);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID)));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(TEXT2, result.getBodyText());
	}

	@Test
	public void testHandleFailed() throws Exception {
		IllegalStateException e = new IllegalStateException();
		new Expectations() {
			{
				gameTransformer.apply(game);
				result = e;
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.pathBinding(Collections.singletonMap(Constants.ID_TOKEN, ID)));

		assertEquals(e, result.exception(IllegalStateException.class));
	}

}
