package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Solution extends AbstractPart<Integer> {

	public Solution(String id, List<Integer> parts) {
		super(id, parts);
	}

	@OnlyForXmlBinding
	Solution() {
		this(null, null);
	}

	@Override
	public String toString() {
		return "Solution [getId()=" + getId() + ", getParts()=" + getParts() + "]";
	}

}
