package com.webnobis.mastermind.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.webnobis.com/mastermind/game")
public class Game<T extends Trying<E>, E extends Enum<E>> {

	@XmlAttribute
	private final String id;

	@XmlElement
	private final Solution<E> solution;

	@XmlElementWrapper(name = "tryings")
	@XmlElement(type = ArrayList.class)
	private final List<T> tryings;

	@XmlAttribute
	private final boolean finish;

	// only JAXB
	Game() {
		this(null, null, null, false);
	}

	public Game(String id, Solution<E> solution, List<T> tryings, boolean finish) {
		this.id = Objects.requireNonNull(id);
		this.solution = Objects.requireNonNull(solution);
		this.tryings = Objects.requireNonNull(tryings);
		this.finish = finish;
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
