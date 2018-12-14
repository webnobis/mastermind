package com.webnobis.mastermind.persistence;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.Game;

public class GameStore<T> extends AbstractMap<String, Game<T>> {

	static final String FILE_EXT = ".xml";

	private final Path folder;

	public GameStore(Path folder) {
		super();
		this.folder = Objects.requireNonNull(folder);
	}

	private Path buildFile(String key) {
		return folder.resolve(Objects.requireNonNull(key).concat(FILE_EXT));
	}

	@Override
	public Game<T> put(String key, Game<T> value) {
		Game<T> game = get(key);
		JAXB.marshal(Objects.requireNonNull(value), buildFile(key).toFile());
		return game;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Game<T> get(Object key) {
		Path file = buildFile(Objects.requireNonNull(key).toString());
		if (Files.exists(file)) {
			return JAXB.unmarshal(file.toFile(), Game.class);
		} else {
			return null;
		}
	}

	@Override
	public Set<Entry<String, Game<T>>> entrySet() {
		try {
			return Files.walk(folder)
					.filter(Files::isRegularFile)
					.filter(file -> file.toString().endsWith(FILE_EXT))
					.map(Path::getFileName)
					.map(Path::toString)
					.map(name -> name.replace(FILE_EXT, ""))
					.map(this::get)
					.map(game -> new Map.Entry<String, Game<T>>() {

						@Override
						public String getKey() {
							return game.getId();
						}

						@Override
						public Game<T> getValue() {
							return game;
						}

						@Override
						public Game<T> setValue(Game<T> value) {
							throw new UnsupportedOperationException();
						}

					}).collect(Collectors.toUnmodifiableSet());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public Game<T> remove(Object key) {
		Game<T> game = get(key);
		if (game != null) {
			try {
				Files.delete(buildFile(key.toString()));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		return game;
	}

}
