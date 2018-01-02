package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;

@XmlRootElement
public class XmlGameWithSolution implements GameWithSolution {

	@XmlAttribute
	private final String id;

	@XmlElement(type = XmlGame.class)
	private final Game game;

	@XmlElement(type = XmlSolution.class)
	private final Solution solution;

	public XmlGameWithSolution(String id, Game game, Solution solution) {
		this.id = id;
		this.game = game;
		this.solution = solution;
	}

	@OnlyForXmlBinding
	XmlGameWithSolution() {
		this(null, null, null);
	}

	@Override
	public String getId() {
		return id;
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
