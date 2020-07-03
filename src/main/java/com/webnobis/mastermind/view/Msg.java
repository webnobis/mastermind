package com.webnobis.mastermind.view;

import java.util.ResourceBundle;

public final class Msg {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("msg");

	private Msg() {
	}

	public static String get(String key) {
		return bundle.getString(key);
	}

}
