package com.webnobis.mastermind.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.game.Result;

public class GameFixture {
	
	private static final int MISSING = 99;
	
	private static final int CONTAINED = 88;
	
	private static final List<Integer> expected = Arrays.asList(1,2,3,4,1);
	
	private final Game<Integer> game;
	
	private GameFixture(Game<Integer> game) {
		this.game = game;
	}
	
	public static GameFixture create(boolean easyVerify) {
		return new GameFixture(Game.create(expected, easyVerify));
	}
	
	public GameFixture nextTry(Result ... results) {
		List<Integer> tmp = new ArrayList<>(game.getVerifier().expected());
		IntStream.range(0, game.getVerifier().expected().size()).limit(results.length)
		.map(i -> {
			switch (results[i]) {
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
		.forEach(i -> {
			if (i == CONTAINED) {
				tmp.stream()
						.filter(j -> j != MISSING)
						.findFirst()
						.map(contained -> tmp.set(i, contained))
						.orElseThrow(() -> new IllegalStateException("no more CONTAINED found"));
			}
		});
		game.nextTry(tmp);
		return this;
	}
	
	public GameFixture finish() {
		game.nextTry(game.getVerifier().expected());
		return this;
	}

}
