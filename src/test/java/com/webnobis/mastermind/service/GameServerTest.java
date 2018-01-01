package com.webnobis.mastermind.service;

import static com.webnobis.mastermind.service.Constants.ALIVE_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_BUILDER_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_PATH;
import static com.webnobis.mastermind.service.Constants.RESIGN_PATH;
import static com.webnobis.mastermind.service.Constants.TRY_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Handler;
import ratpack.http.Status;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.ServerBackedApplicationUnderTest;

@RunWith(JMockit.class)
public class GameServerTest {

	private static final String ALIVE = "alive";

	private static final String GAME_BUILDER = "create";

	private static final String GAME = "game";

	private static final String TRY = "try";

	private static final String RESIGN = "resign";

	private static Handler aliveHandler = ctx -> ctx.render(ALIVE);

	private static Handler gameBuilderHandler = ctx -> ctx.render(GAME_BUILDER);

	private static Handler gameHandler = ctx -> ctx.render(GAME);

	private static Handler tryHandler = ctx -> ctx.render(TRY);

	private static Handler resignHandler = ctx -> ctx.render(RESIGN);

	@Mocked
	private Env env;

	private GameServer server;

	@BeforeClass
	public static void setUpClass() {
		// aliveHandler =
	}

	@Before
	public void setUp() throws Exception {
		new Expectations() {
			{
				env.getPort();
				returns(9999);
			}
		};

		server = new GameServer(env, aliveHandler, gameBuilderHandler, gameHandler, tryHandler, resignHandler);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore // TODO successful after implementation
	@Test
	public void testBuild() {
		assertNotNull(GameServer.build());
	}

	@Test
	public void testBuildServer() throws Exception {
		ServerBackedApplicationUnderTest.of(server.buildServer()).test(client -> {
			validateResponse(client.get(ALIVE_PATH), ALIVE);
			validateResponse(client.post(GAME_BUILDER_PATH), GAME_BUILDER);
			validateResponse(client.get(GAME_PATH.replace(":", "1")), GAME);
			validateResponse(client.post(TRY_PATH.replace(":", "2")), TRY);
			validateResponse(client.get(RESIGN_PATH.replace(":", "3")), RESIGN);
		});
	}

	private static void validateResponse(ReceivedResponse response, String text) {
		assertEquals(Status.OK, response.getStatus());
		assertEquals(text, response.getBody().getText());
	}

}
