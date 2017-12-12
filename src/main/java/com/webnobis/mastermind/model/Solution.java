package com.webnobis.mastermind.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Solution extends Try {

	public Solution(List<Integer> parts) {
		super(parts);
	}

}
