package com.webnobis.mastermind.service.file;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.webnobis.mastermind.model.Play;

public interface PlayFileTransformer {

	@SuppressWarnings("unchecked")
	static <T extends Serializable> Play<T> read(Path playFile) {
		try (ObjectInputStream in = new ObjectInputStream(
				Files.newInputStream(Objects.requireNonNull(playFile, "playFile is null")))) {
			return (Play<T>) in.readObject();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	static void write(Path playFile, Play<? extends Serializable> play) {
		try (ObjectOutputStream out = new ObjectOutputStream(
				Files.newOutputStream(Objects.requireNonNull(playFile, "playFile is null")))) {
			out.writeObject(Objects.requireNonNull(play, "play is null"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
