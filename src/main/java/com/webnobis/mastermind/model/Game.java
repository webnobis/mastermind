package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.service.SolutionFinder;

@XmlRootElement
public class Game extends AbstractPart<CheckedTry> {

	@XmlElement(nillable = true)
	private final Solution solution;

	@XmlAttribute
	private final boolean finish;

	public Game(String id, List<CheckedTry> parts) {
		super(id, parts);
		solution = SolutionFinder.findSolution(parts);
		finish = solution != null;
	}

	@OnlyForXmlBinding
	Game() {
		this(null, null);
	}

	public Solution getSolution() {
		return solution;
	}

	public boolean isFinish() {
		return finish;
	}

	@Override
	public String toString() {
		return "Game [getId()=" + getId() + ", isFinish()=" + isFinish() + ", getParts()=" + getParts() + "]";
	}

}
