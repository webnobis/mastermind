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
import com.webnobis.mastermind.model.Trying;

public class GameStore<T> extends AbstractMap<String, Game<? extends Trying<T>,T>> {

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
	public Game<? extends Trying<T>,T> put(String key, Game<? extends Trying<T>,T> value) {
		Game<? extends Trying<T>,T> game = get(key);
		JAXB.marshal(Objects.requireNonNull(value), buildFile(key).toFile());
		return game;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Game<? extends Trying<T>,T> get(Object key) {
		Path file = buildFile(Objects.requireNonNull(key).toString());
		if (Files.exists(file)) {
			return JAXB.unmarshal(file.toFile(), Game.class);
		} else {
			return null;
		}
	}

	@Override
	public Set<Entry<String, Game<? extends Trying<T>,T>>> entrySet() {
		try {
			return Files.walk(folder)
					.filter(Files::isRegularFile)
					.filter(file -> file.toString().endsWith(FILE_EXT))
					.map(Path::getFileName)
					.map(Path::toString)
					.map(name -> name.replace(FILE_EXT, ""))
					.map(this::get)
					.map(game -> new Map.Entry<String, Game<? extends Trying<T>,T>>() {

						@Override
						public String getKey() {
							return game.getId();
						}

						@Override
						public Game<? extends Trying<T>,T> getValue() {
							return game;
						}

						@Override
						public Game<? extends Trying<T>,T> setValue(Game<? extends Trying<T>,T> value) {
							throw new UnsupportedOperationException();
						}

					}).collect(Collectors.toUnmodifiableSet());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public Game<? extends Trying<T>,T> remove(Object key) {
		Game<? extends Trying<T>,T> game = get(key);
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
