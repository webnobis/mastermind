package com.webnobis.mastermind.view;

public enum Constants {

	PADDING(5), INFO_LABEL(2);
	
	private final int value;

	private Constants(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}
	
}
