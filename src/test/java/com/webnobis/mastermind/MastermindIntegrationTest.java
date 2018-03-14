package com.webnobis.mastermind;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXB;

import static com.webnobis.mastermind.service.Constants.ALIVE_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_BUILDER_PATH;
import static com.webnobis.mastermind.service.Constants.GAME_PATH;
import static com.webnobis.mastermind.service.Constants.RESIGN_PATH;
import static com.webnobis.mastermind.service.Constants.TRY_PATH;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.webnobis.mastermind.model.transformer.GameConfigTransformer;
import com.webnobis.mastermind.model.xml.XmlGameConfig;

import ratpack.http.Status;
import ratpack.test.embed.EmbeddedApp;
import ratpack.test.http.TestHttpClient;

public class MastermindIntegrationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFinishedGame() throws Exception {
		EmbeddedApp.fromServer(Mastermind.buildRatpackServer()).test(client -> {
/*			{
				String result = client
						.request(GAME_BUILDER, req -> req
								.post()
								.body(body -> body
										.stream(out -> JAXB.marshal(new XmlGameConfig(1, 9, 4), out))								)
						).getBody()
						.getText(StandardCharsets.UTF_8);
				System.out.println(result);
			}*/
			{
				assertEquals(Status.OK.getCode(), client.get(ALIVE_PATH).getStatusCode());
			}
			{
				String game = client.requestSpec(req -> req
						.body(body -> body
								.stream(out -> JAXB.marshal(new XmlGameConfig(1, 9, 4), out))
								.type("application/xml")))
						.postText(GAME_BUILDER_PATH);
				System.out.println(game);
			}
		});
	}

}
