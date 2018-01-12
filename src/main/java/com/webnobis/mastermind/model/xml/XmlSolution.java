package com.webnobis.mastermind.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Solution;

@XmlRootElement(name = "solution")
public class XmlSolution implements Solution {

	@XmlElement
	private final List<Integer> values = new ArrayList<>();

	public XmlSolution(List<Integer> values) {
		Optional.ofNullable(values)
				.ifPresent(this.values::addAll);
	}

	@OnlyForXmlBinding
	XmlSolution() {
		this(null);
	}

	@Override
	public List<Integer> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		XmlSolution other = (XmlSolution) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlSolution [values=" + values + "]";
	}

}
