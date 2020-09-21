package com.webnobis.mastermind.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Optional;

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
	 * @see Files#readString(Path)
	 * @see XmlTransformer#toModel(String, Class)
	 */
	@SuppressWarnings("unchecked")
	static <T> Play<T> load(Path file) {
		return Optional.ofNullable(file).filter(Files::exists).map(playFile -> {
			try {
				return XmlTransformer.toModel(Files.readString(playFile), Play.class);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}).orElse(null);
	}

	/**
	 * Stores a play as XML file.
	 * 
	 * @param play play
	 * @param file file containing the play as XML
	 * @return true, if stored
	 * @throws UncheckedIOException if the file isn't writable
	 * @see Files#writeString(Path, CharSequence, java.nio.file.OpenOption...)
	 * @see XmlTransformer#toXml(Object)
	 */
	static boolean store(Play<?> play, Path file) {
		try {
			Files.writeString(Objects.requireNonNull(file, "file is null"),
					XmlTransformer.toXml(Objects.requireNonNull(play, "play is null")), StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return Files.exists(file);
	}

	/**
	 * Deletes the file.
	 * 
	 * @param file the file
	 * @return true, if deleted
	 * @throws UncheckedIOException if the file couldn't be deleted
	 * @see Files#delete(Path)
	 */
	static boolean delete(Path file) {
		return Optional.ofNullable(file).filter(Files::exists).map(playFile -> {
			try {
				Files.delete(playFile);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			return !Files.exists(playFile);
		}).orElse(false);
	}

}
