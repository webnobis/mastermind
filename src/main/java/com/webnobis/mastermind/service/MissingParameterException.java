package com.webnobis.mastermind.service;

public class MissingParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingParameterException(String message) {
		super(message);
	}

}
