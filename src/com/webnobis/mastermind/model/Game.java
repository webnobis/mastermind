package com.webnobis.mastermind.model;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.webnobis.com/mastermind/game")
public class Game<E extends Trying<T>, T> {

	@XmlAttribute
	private final String id;
	
	@XmlAttribute
	private final Class<T> type;

	@XmlElement
	private final Solution<T> solution;

	@XmlElementWrapper(name = "tryings")
	@XmlElement(type = TreeSet.class)
	private final SortedSet<E> tryings;

	@XmlAttribute
	private final boolean finish;

	// only JAXB
	Game() {
		this(null, null, null, false);
	}

	public Game(String id, Solution<T> solution, SortedSet<E> tryings, boolean finish) {
		this.id = Objects.requireNonNull(id);
		this.solution = Objects.requireNonNull(solution);
		this.type = getType(solution);
		this.tryings = Objects.requireNonNull(tryings);
		this.finish = finish;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getType(Solution<T> solution) {
		return solution.getPositions().stream().findAny().map(t -> (Class<T>)t.getClass()).orElseThrow(() -> new IllegalArgumentException("solution needs at least one position"));
	}

	public String getId() {
		return id;
	}

	public Class<T> getType() {
		return type;
	}

	public Solution<T> getSolution() {
		return solution;
	}

	public SortedSet<E> getTryings() {
		return tryings;
	}

	public boolean isFinish() {
		return finish;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return id.equals(((Game<?, ?>) obj).id);
	}

	@Override
	public String toString() {
		return MessageFormat.format("Game [id={0}]", id);
	}

}
