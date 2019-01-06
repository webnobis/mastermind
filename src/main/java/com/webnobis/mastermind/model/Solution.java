package com.webnobis.mastermind.model;

import java.util.List;

public class Solution<E extends Enum<E>> extends AbstractStep<E> {

	public Solution(List<E> positions) {
		super(positions);
	}

}
