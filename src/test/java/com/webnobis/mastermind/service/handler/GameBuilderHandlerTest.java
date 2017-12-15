package com.webnobis.mastermind.service.handler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.net.HostAndPort;
import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.xml.XmlGameConfig;
import com.webnobis.mastermind.service.GameBuilder;
import com.webnobis.mastermind.service.store.GameStore;

import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JMockit.class)
public class GameBuilderHandlerTest {

	private static final int MIN = 3;

	private static final int MAX = 4;

	private static final int SIZE = 5;

	private static final String ADDRESS = "server:9999";

	private static final String CALL_URI = "/the/call/path/";

	private static final String ID = "the id";

	private static final String REDIRECT_URI = "http://" + ADDRESS + CALL_URI + ID;

	private static final String HEADER_KEY = "location";

	private static final Function<String, GameConfig> gameConfigTransformer = size -> new XmlGameConfig(MIN, MAX, Integer.parseInt(size));

	@Mocked
	private GameBuilder gameBuilder;

	@Mocked
	private GameWithSolution gameWithSolution;

	@Mocked
	private GameStore gameStore;

	private Handler handler;

	@Before
	public void setUp() throws Exception {
		handler = new GameBuilderHandler(gameBuilder, gameStore, gameConfigTransformer);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandle() throws Exception {
		new StrictExpectations() {
			{
				gameBuilder.build(MIN, MAX, SIZE);
				returns(gameWithSolution);
			}
			{
				gameStore.store(gameWithSolution);
				returns(ID);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.body(String.valueOf(SIZE), "")
				.localAddress(HostAndPort.fromString(ADDRESS))
				.uri(CALL_URI));

		assertEquals(GameBuilderHandler.REDIRECT_TO_ID, result.getStatus().getCode());
		assertEquals(REDIRECT_URI, result.getHeaders().asMultiValueMap().get(HEADER_KEY));
	}

	@Test
	public void testHandleFailed() throws Exception {
		UncheckedIOException e = new UncheckedIOException(new IOException());
		new StrictExpectations() {
			{
				gameBuilder.build(MIN, MAX, SIZE);
				returns(gameWithSolution);
			}
			{
				gameStore.store(gameWithSolution);
				result = e;
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.body(String.valueOf(SIZE), ""));

		assertEquals(e, result.exception(UncheckedIOException.class));
	}

}
