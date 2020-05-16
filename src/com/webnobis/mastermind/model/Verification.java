package com.webnobis.mastermind.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Verification {

	private final static Comparator<Status> comparator = (status1, status2) -> Status.CORRECT_PLACE.equals(status1) ? -1 : 1;

	@XmlElementWrapper(name="results")
	@XmlElement(type=ArrayList.class)
	private final List<Status> results;

	public Verification(List<Status> results) {
		this.results = Objects.requireNonNull(results).stream().sorted(comparator).collect(Collectors.toList());
	}

	public List<Status> getResults() {
		return results;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0} [{1}]", this.getClass().getSimpleName(), results);
	}

}
