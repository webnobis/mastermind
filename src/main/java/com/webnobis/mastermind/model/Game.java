package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.service.SolutionFinder;

@XmlRootElement
public class Game {
	
	@XmlAttribute
	private final String id;

	@XmlElement
	private final List<VerifiedTry> verifiedTries;

	@XmlElement(nillable = true)
	private final Solution solution;

	@XmlAttribute
	private final boolean finish;

	public Game(String id, List<VerifiedTry> verifiedTries) {
		this.id = id;
		this.verifiedTries = verifiedTries;
		solution = SolutionFinder.findSolution(verifiedTries);
		finish = solution != null;
	}

	@OnlyForXmlBinding
	Game() {
		this(null,null);
	}

	public List<VerifiedTry> getVerifiedTries() {
		return verifiedTries;
	}

	public Solution getSolution() {
		return solution;
	}

	public boolean isFinish() {
		return finish;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", verifiedTries=" + verifiedTries + ", finish=" + finish + "]";
	}

}
