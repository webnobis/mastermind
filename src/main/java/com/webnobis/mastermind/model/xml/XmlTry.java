package com.webnobis.mastermind.model.xml;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Try;

@XmlRootElement
public class XmlTry implements Try {

	@XmlElement
	private final List<Integer> ideas;

	public XmlTry(List<Integer> ideas) {
		this.ideas = Optional.ofNullable(ideas)
				.map(Collections::unmodifiableList)
				.orElse(Collections.emptyList());
	}

	@OnlyForXmlBinding
	XmlTry() {
		this(null);
	}

	@Override
	public List<Integer> getIdeas() {
		return ideas;
	}

}
