package com.webnobis.mastermind.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.xml.XmlTry;

import old.GameStore;

public class GameFixture {

	private static final Long MISSING = Long.MIN_VALUE;

	private static final List<Long> EXPECTED = Arrays.asList(1L, 2L, 3L, 4L, 1L);

	private final Game<Long> game;

	private GameFixture(Game<Long> game) {
		this.game = game;
		GameStore.get().store(game);
	}

	public static GameFixture create(boolean easyVerify) {
		return new GameFixture(Game.create(EXPECTED, easyVerify));
	}

	public Game<Long> getGame() {
		return game;
	}

	public GameFixture nextTry(List<Result> results) {
		Map<Integer, Long> tmp = IntStream.range(0, game.getVerifier().expected().size())
				.limit(results.size())
				.boxed()
				.collect(Collectors.toMap(i -> i, game.getVerifier().expected()::get));

		List<Long> nextTry = IntStream.range(0, game.getVerifier().expected().size())
				.limit(results.size())
				.mapToObj(i -> {
					switch (results.get(i)) {
					case CORRECT_PLACE:
						return tmp.remove(i);
					case CONTAINED:
						Long value = tmp.get(i);
						return tmp.entrySet().stream()
								.filter(e -> !value.equals(e.getValue()))
								.findFirst()
								.map(e -> tmp.remove(e.getKey()))
								.orElse(MISSING);
					default:
						return MISSING;
					}
				}).collect(Collectors.toList());

		game.nextTry(nextTry);
		return this;
	}

	public GameFixture finish() {
		game.nextTry(game.getVerifier().expected());
		return this;
	}

	public XmlTry<Long> getLastTryAsXml() {
		if (game.tries() > 0) {
			return new XmlTry<>(game.getId(), game.getTries().get(game.getTries().size() - 1));
		} else {
			throw new IllegalStateException("no tries available");
		}
	}

}
