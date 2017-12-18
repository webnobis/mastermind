package com.webnobis.mastermind.service.handler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.net.HostAndPort;
import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.service.Constants;
import com.webnobis.mastermind.service.GameBuilder;
import com.webnobis.mastermind.service.store.GameStore;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

@RunWith(JMockit.class)
public class GameBuilderHandlerTest {

	private static final int MIN = 3;

	private static final int MAX = 4;

	private static final int SIZE = 5;

	private static final String TEXT = "config as text";

	private static final String ADDRESS = "server:9999";

	private static final String CALL_URI = "/the/call/path/";

	private static final String ID = "the id";

	private static final String REDIRECT_URI = "http://" + ADDRESS + CALL_URI + ID;

	private static final String HEADER_KEY = "location";

	@Mocked
	private Function<String, GameConfig> gameConfigTransformer;

	@Mocked
	private GameConfig gameConfig;

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

		new Expectations() {
			{
				gameConfig.getMin();
				returns(MIN);
				gameConfig.getMax();
				returns(MAX);
				gameConfig.getSize();
				returns(SIZE);
			}
			{
				gameConfigTransformer.apply(TEXT);
				returns(gameConfig);
			}
			{
				gameBuilder.build(MIN, MAX, SIZE);
				returns(gameWithSolution);
			}
		};
	}

	@Test
	public void testHandle() throws Exception {
		new Expectations() {
			{
				gameStore.store(gameWithSolution);
				returns(ID);
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.body(TEXT, Constants.CONTENT_TYPE)
				.localAddress(HostAndPort.fromString(ADDRESS))
				.uri(CALL_URI));

		assertEquals(Constants.REDIRECT_CODE, result.getStatus().getCode());
		assertEquals(REDIRECT_URI, result.getHeaders().asMultiValueMap().get(HEADER_KEY));
	}

	@Test
	public void testHandleFailed() throws Exception {
		UncheckedIOException e = new UncheckedIOException(new IOException());
		new Expectations() {
			{
				gameStore.store(gameWithSolution);
				result = e;
			}
		};

		HandlingResult result = RequestFixture.handle(handler, request -> request
				.body(TEXT, Constants.CONTENT_TYPE));

		assertEquals(e, result.exception(UncheckedIOException.class));
	}

}
