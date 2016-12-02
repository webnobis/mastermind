package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;
import com.webnobis.mastermind.game.xml.XmlTry;

public class GameFixture {

	private static final int MISSING = 99;

	private static final int CONTAINED = 88;

	private static final List<Integer> expected = Arrays.asList(1, 2, 3, 4, 1);

	private final Game<Integer> game;

	private GameFixture(Game<Integer> game) {
		this.game = game;
	}

	public static GameFixture create(boolean easyVerify) {
		return new GameFixture(Game.create(expected, easyVerify));
	}

	public Game<Integer> getGame() {
		return game;
	}

	public GameFixture nextTry(List<Result> results) {
		List<Integer> tmp = new ArrayList<>(game.getVerifier().expected());
		game.nextTry(IntStream.range(0, game.getVerifier().expected().size()).limit(results.size())
				.map(i -> {
					switch (results.get(i)) {
					case CORRECT_PLACE:
						return tmp.set(i, MISSING);
					case CONTAINED:
						return CONTAINED;
					default:
						return MISSING;
					}
				})
				.boxed()
				.collect(Collectors.toList()).stream()
				.map(i -> {
					if (i == CONTAINED) {
						return tmp.stream()
								.filter(j -> j != MISSING)
								.findFirst()
								.orElseThrow(() -> new IllegalStateException("no more CONTAINED available"));
					} else {
						return i;
					}
				})
				.collect(Collectors.toList()));
		return this;
	}

	public GameFixture finish() {
		game.nextTry(game.getVerifier().expected());
		return this;
	}

	public XmlTry<Integer> getLastTryAsXml() {
		if (game.tries() > 0) {
			return new XmlTry<>(game.getId(), game.getTries().get(game.getTries().size() - 1));
		} else {
			throw new IllegalStateException("no tries available");
		}
	}

}
