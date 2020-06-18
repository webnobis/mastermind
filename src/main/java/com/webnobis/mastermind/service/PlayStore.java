package com.webnobis.mastermind.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import com.webnobis.mastermind.model.Play;

/**
 * File based play store
 * 
 * @author steffen
 *
 */
public interface PlayStore {

	/**
	 * Loads a play from file.<br>
	 * Attention, the type is boxed.
	 * 
	 * @param <T>  type of findings
	 * @param file file containing a XML play
	 * @return typed play
	 * @throws UncheckedIOException if the file isn't readable
	 * @see XmlTransformer#toModel(String, Class)
	 */
	@SuppressWarnings("unchecked")
	static <T> Play<T> load(Path file) {
		try {
			return XmlTransformer.toModel(Files.readString(Objects.requireNonNull(file, "file is null")), Play.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Stores a play as XML file.
	 * 
	 * @param play play
	 * @param file file containing the play as XML
	 * @throws UncheckedIOException if the file isn't writable
	 * @see XmlTransformer#toXml(Object)
	 */
	static void store(Play<?> play, Path file) {
		try {
			Files.writeString(Objects.requireNonNull(file, "file is null"),
					XmlTransformer.toXml(Objects.requireNonNull(play, "play is null")), StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
