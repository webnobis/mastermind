package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;

@XmlRootElement(name = "game-with-solution")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((solution == null) ? 0 : solution.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XmlGameWithSolution other = (XmlGameWithSolution) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlGameWithSolution [id=" + id + ", game=" + game + ", solution=" + solution + "]";
	}

}
