package com.webnobis.mastermind.service.handler;

import java.io.CharArrayWriter;
import java.util.Objects;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.service.GameConsumer;
import com.webnobis.mastermind.service.GameConsumerRegistry;

import ratpack.websocket.WebSocket;
import ratpack.websocket.WebSocketClose;
import ratpack.websocket.WebSocketHandler;
import ratpack.websocket.WebSocketMessage;

public class GameWebSocketHandler implements WebSocketHandler<GameConsumer> {
	
	private final GameConsumerRegistry gameConsumerRegistry;
	
	private final String id;

	public GameWebSocketHandler(GameConsumerRegistry gameConsumerRegistry, String id) {
		this.gameConsumerRegistry = Objects.requireNonNull(gameConsumerRegistry, "gameConsumerRegistry is null");
		this.id = Objects.requireNonNull(id, "id is null");
	}

	@Override
	public GameConsumer onOpen(WebSocket webSocket) throws Exception {
		GameConsumer gameConsumer = game -> {
			try (CharArrayWriter out = new CharArrayWriter()) {
				JAXB.marshal(game, out);
				out.flush();
				webSocket.send(out.toString());
			}
		};
		gameConsumerRegistry.register(id, gameConsumer);
		return gameConsumer;
	}

	@Override
	public void onClose(WebSocketClose<GameConsumer> close) throws Exception {
		gameConsumerRegistry.deregister(id);
	}

	@Override
	public void onMessage(WebSocketMessage<GameConsumer> frame) throws Exception {
	}

}
