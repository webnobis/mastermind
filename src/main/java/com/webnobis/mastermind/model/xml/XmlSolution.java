package com.webnobis.mastermind.model.xml;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Solution;

@XmlRootElement
public class XmlSolution implements Solution {

	@XmlElement
	private final List<Integer> values;

	public XmlSolution(List<Integer> values) {
		this.values = Optional.ofNullable(values)
				.map(Collections::unmodifiableList)
				.orElse(Collections.emptyList());
	}

	@OnlyForXmlBinding
	XmlSolution() {
		this(null);
	}

	@Override
	public List<Integer> getValues() {
		return values;
	}

}
