package com.webnobis.mastermind.service.file;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.PlayState;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.PlayService;

public class FilePlayService<T extends Serializable> implements PlayService<T> {

	private static final String FILE_EXT = ".play";

	private final Path rootFolder;

	private final Supplier<String> idGenerator;

	private final IntFunction<Source<T>> sourceGenerator;

	private final Function<Source<T>, Result<T>> resultGenerator;

	public FilePlayService(Path rootFolder, Supplier<String> idGenerator, IntFunction<Source<T>> sourceGenerator,
			Function<Source<T>, Result<T>> resultGenerator) {
		this.rootFolder = rootFolder;
		this.idGenerator = idGenerator;
		this.sourceGenerator = sourceGenerator;
		this.resultGenerator = resultGenerator;
	}

	@Override
	public String newPlay(int cols, int rows) {
		String uuid = idGenerator.get();
		Path playFile = rootFolder.resolve(String.valueOf(cols)).resolve(String.valueOf(rows)).resolve(uuid.concat(FILE_EXT));
		Play<T> play = Play.of(rows, sourceGenerator.apply(cols));
		PlayFileTransformer.write(playFile, play);
		return uuid;
	}

	private Path findPlayFile(String id) {
		try {
			return Files.walk(rootFolder).filter(file -> file.getFileName().toString().startsWith(id)).findFirst()
					.orElseThrow(() -> new NoSuchElementException(id.concat(FILE_EXT)));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public PlayState<T> nextTry(String id, Source<T> source) {
		Path playFile = findPlayFile(Objects.requireNonNull(id, "id is null"));
		Play<T> play = PlayFileTransformer.read(playFile);
		play = play.ofAddedResult(resultGenerator.apply(Objects.requireNonNull(source, "source is null")));
		PlayFileTransformer.write(playFile, play);
		return PlayState.of(play);
	}

	@Override
	public Play<T> quit(String id) {
		Path playFile = findPlayFile(Objects.requireNonNull(id, "id is null"));
		return PlayFileTransformer.read(playFile);
	}

}
