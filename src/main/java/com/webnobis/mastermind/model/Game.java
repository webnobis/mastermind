package com.webnobis.mastermind.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game<T> {
	
	@XmlAttribute
	private final String id;
	
	@XmlElement(type=ArrayList.class)
	private final List<T> solution;
	
	@XmlElement(type=ArrayList.class)
	private final List<List<T>> nextTry;
	
	@XmlElement(type=ArrayList.class)
	private final List<List<Status>> verify;
	
	@XmlAttribute
	private final boolean finish;
	
	// only JAXB
	Game() {
		this(null, null, null, null, false);
	}

	public Game(String id, List<T> solution, List<List<T>> nextTry, List<List<Status>> verify, boolean finish) {
		this.id = id;
		this.solution = solution;
		this.nextTry = nextTry;
		this.verify = verify;
		this.finish = finish;
	}

	public String getId() {
		return id;
	}

	public List<T> getSolution() {
		return solution;
	}

	public List<List<T>> getNextTry() {
		return nextTry;
	}

	public List<List<Status>> getVerify() {
		return verify;
	}

	public boolean isFinish() {
		return finish;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Game<?> other = (Game<?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", finish=" + finish + "]";
	}

}
