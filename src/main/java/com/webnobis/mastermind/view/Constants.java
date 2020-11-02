package com.webnobis.mastermind.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

/**
 * Constants of the graphical representation of the Mastermind game
 * 
 * @author steffen
 *
 */
public enum Constants {

	PADDING(5), INFO_LABEL(2), VERSION(ClassLoader.getSystemResourceAsStream("version.properties"));

	private final int intValue;

	private final String value;

	private Constants(int value) {
		intValue = value;
		this.value = null;
	}

	private Constants(InputStream in) {
		intValue = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			value = (reader.ready()) ? reader.readLine() : "unknown";
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @return integer value
	 */
	public int getIntValue() {
		return intValue;
	}

}
