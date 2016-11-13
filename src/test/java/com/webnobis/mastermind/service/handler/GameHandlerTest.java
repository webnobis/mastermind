package com.webnobis.mastermind.service.handler;

import org.junit.Before;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import ratpack.handling.Context;
import ratpack.handling.Handler;

@RunWith(JMockit.class)
public class GameHandlerTest {
	
	@Mocked
	private Context ctx;
	
	private Handler gameHandler;
	
	@Before
	public void setUp() {
		gameHandler = new GameHandler();
		
		new Expectations() {{
//			ctx.getRequest().getBody().map(data -> data.getText()).then(then);
		}};
	}

}
