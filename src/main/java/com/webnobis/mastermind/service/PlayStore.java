package com.webnobis.mastermind.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.Play;

public interface PlayStore {

	@SuppressWarnings("unchecked")
	static <T> Play<T> load(Path file) {
		try {
			return JAXB.unmarshal(Files.newBufferedReader(file, StandardCharsets.UTF_8), Play.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	static void store(Play<?> play, Path file) {
		try {
			JAXB.marshal(play, Files.newOutputStream(file));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
