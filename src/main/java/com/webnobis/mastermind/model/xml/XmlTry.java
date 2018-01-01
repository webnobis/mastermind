package com.webnobis.mastermind.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Try;

@XmlRootElement
public class XmlTry implements Try {

	@XmlElement
	private final List<Integer> ideas = new ArrayList<>();

	public XmlTry(List<Integer> ideas) {
		Optional.ofNullable(ideas)
				.ifPresent(this.ideas::addAll);
	}

	@OnlyForXmlBinding
	XmlTry() {
		this(null);
	}

	@Override
	public List<Integer> getIdeas() {
		return ideas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ideas == null) ? 0 : ideas.hashCode());
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
		XmlTry other = (XmlTry) obj;
		if (ideas == null) {
			if (other.ideas != null)
				return false;
		} else if (!ideas.equals(other.ideas))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlTry [ideas=" + ideas + "]";
	}

}
