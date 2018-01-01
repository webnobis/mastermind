package com.webnobis.mastermind;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import ratpack.api.UncheckedException;
import ratpack.server.RatpackServer;

@RunWith(JMockit.class)
public class MastermindTest {

	@Mocked
	private RatpackServer server;

	@Test
	public void testMain() throws Exception {
		Mastermind.ratpackServerBuilder = () -> server;

		Mastermind.main(null);

		new Verifications() {
			{
				server.start();
			}
		};
	}

	@Test(expected = UncheckedException.class)
	public void testMainBuildException() throws Exception {
		Mastermind.ratpackServerBuilder = () -> {
			throw new UncheckedException(null);
		};

		Mastermind.main(null);
	}

	@Test(expected = UncheckedException.class)
	public void testMainStartException() throws Exception {
		new Expectations() {
			{
				server.start();
				result = new UncheckedException(null);
			}
		};

		Mastermind.ratpackServerBuilder = () -> server;

		try {
			Mastermind.main(null);
		} finally {
			new Verifications() {
				{
					server.stop();
				}
			};
		}
	}

	@Ignore // TODO successful after implementation
	@Test
	public void testBuildRatpackServer() {
		assertNotNull(Mastermind.buildRatpackServer());
	}

}
