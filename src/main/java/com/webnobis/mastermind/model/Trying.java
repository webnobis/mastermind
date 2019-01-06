package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trying<E extends Enum<E>> extends AbstractStep<E> {
	
	@XmlAttribute
	private final int index;

	public Trying(int index, List<E> positions) {
		super(positions);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
