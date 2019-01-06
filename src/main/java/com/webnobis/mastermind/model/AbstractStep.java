package com.webnobis.mastermind.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class AbstractStep<E extends Enum<E>> {

	@XmlElement(type=ArrayList.class)
	private final List<E> positions;

	protected AbstractStep(List<E> positions) {
		this.positions = Objects.requireNonNull(positions);
	}

	public List<E> getPositions() {
		return positions;
	}

	@Override
	public int hashCode() {
		return positions.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (AbstractStep.class.isAssignableFrom(obj.getClass())) {
			return Objects.equals(positions, ((AbstractStep<?>) obj).positions);
		}
		return false;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0} [{1}]", this.getClass().getSimpleName(), positions);
	}

}
