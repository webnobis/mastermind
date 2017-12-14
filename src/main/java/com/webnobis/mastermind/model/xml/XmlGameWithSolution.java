package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;

@XmlRootElement
public class XmlGameWithSolution implements GameWithSolution {

	@XmlElement
	private final Game game;

	@XmlElement
	private final Solution solution;

	public XmlGameWithSolution(Game game, Solution solution) {
		this.game = game;
		this.solution = solution;
	}

	@OnlyForXmlBinding
	XmlGameWithSolution() {
		this(null, null);
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

}
