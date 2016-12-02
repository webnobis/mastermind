package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.xml.XmlGame;
import com.webnobis.mastermind.service.handler.GameHandler;

import ratpack.http.Status;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.CloseableApplicationUnderTest;
import ratpack.test.embed.EmbeddedApp;
import ratpack.test.http.TestHttpClient;

public class MastermindServiceTest {

	private GameFixture gameFixture;

	private CloseableApplicationUnderTest testApp;

	@Before
	public void setUp() throws Exception {
		gameFixture = GameFixture.create(true);
		testApp = EmbeddedApp.fromServer(MastermindService.create());
	}

	@After
	public void tearDown() throws Exception {
		testApp.close();
	}

	private void testGame(Optional<List<Result>> results) {
		TestHttpClient client = testApp.getHttpClient();

		ReceivedResponse response = client.request("/game", spec -> spec
				.body(body -> body.text(results.map(gameFixture::nextTry)
						.orElseGet(gameFixture::finish)
						.getLastTryAsXml().toString(), StandardCharsets.UTF_8))
				.post());
		assertNotNull(response);
		assertEquals(Status.OK, response.getStatus());
		assertTrue(response.getBody().getContentType().getType().startsWith(GameHandler.RESPONSE_CONTENT_TYPE));

		XmlGame<Integer> xmlGame = XmlGame.from(gameFixture.getGame());
		assertEquals(xmlGame.toString(), response.getBody().getText());
	}

	@Test
	public void testNextTry() {
		testGame(Optional.of(Arrays.asList(Result.CONTAINED, Result.CORRECT_PLACE, Result.CONTAINED, Result.MISSING)));
	}

	@Test
	public void testFinish() {
		testGame(Optional.empty());
	}

}
