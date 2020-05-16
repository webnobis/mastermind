package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trying<T> extends AbstractStep<T> implements Comparable<Trying<T>> {
	
	@XmlAttribute
	private final int index;

	public Trying(int index, List<T> positions) {
		super(positions);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(Trying<T> other) {
		return (getIndex() < other.getIndex())? -1: (getIndex() > other.getIndex())? 1: 0;
	}

}
