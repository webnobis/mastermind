package com.webnobis.mastermind.model;

public class GameWithSolution {

	private final Game game;

	private final Solution solution;

	public GameWithSolution(Game game, Solution solution) {
		this.game = game;
		this.solution = solution;
	}

	public Game getGame() {
		return game;
	}

	public Solution getSolution() {
		return solution;
	}

}
