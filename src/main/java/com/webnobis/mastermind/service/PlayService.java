package com.webnobis.mastermind.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Source;

/**
 * File system persistent play service
 * 
 * @author steffen
 	 * @param <T>  type of findings
 */
public class PlayService<T> {

	private static final String XML_FILE_EXT = ".xml";

	private final Path rootPath;
	
	private final Function<Source<T>, Result<T>> assessmentService;

	/**
	 * Play service with root path persistent area
	 * 
	 * @param rootPath root path
	 */
	public PlayService(Path rootPath) {
		this.rootPath = Objects.requireNonNull(rootPath, "rootPath is null");
	}

	/**
	 * New unlimited persist play
	 * 
	 * @param type type class
	 * @param cols columns, it's the count of searched findings
	 * @return new play
	 * @see Play#of(Class, int)
	 * @see PlayStore#store(Play, Path)
	 */
	public Play<T> newPlay(int cols) {
		return storePlay(Play.of(type, cols));
	}

	/**
	 * New limited persist play
	 * 
	 * @param <T>  type of findings
	 * @param type type class
	 * @param cols columns, it's the count of searched findings
	 * @param rows maximum try rows, until the Play is finish, in-depending the
	 *             solution was found
	 * @return new play
	 * @see Play#of(Class, int, int)
	 * @see PlayStore#store(Play, Path)
	 */
	public <T> Play<T> newPlay(Class<T> type, int cols, int rows) {
		return storePlay(Play.of(type, cols, rows));
	}

	/**
	 * Get the persist play, if available
	 * 
	 * @param <T> type of findings
	 * @param id  play id
	 * @return play, otherwise null
	 * @see PlayStore#load(Path)
	 */
	public <T> Play<T> getPlay(String id) {
		return PlayStore.load(buildFile(id));
	}

	/**
	 * Assesses the next try and adds the result at the play and updates it, if
	 * available and not quit
	 * 
	 * @param <T>    type of findings
	 * @param id     play id
	 * @param source next try source
	 * @return updated play, otherwise null
	 * @see AssessmentService#assess(Source)
	 * @see Play#withAddedResult(com.webnobis.mastermind.model.Result)
	 * @see PlayStore#store(Play, Path)
	 * @see PlayService#quitPlay(String)
	 */
	public <T> Play<T> nextTry(String id, Source<T> source) {
		return Optional.<Play<T>>ofNullable(getPlay(id)).filter(play -> Objects.isNull(play.getSource()))
				.map(play -> play.withAddedResult(AssessmentService.assess(Objects.requireNonNull(source))))
				.orElse(null);
	}

	/**
	 * Quits the play with the solution in-depending if it's finish or solved and
	 * updates it, if available
	 * 
	 * @param <T> type of findings
	 * @param id  play id
	 * @return updated play, otherwise null
	 * @see Play#withSource(Source)
	 * @see PlayStore#store(Play, Path)
	 */
	public <T> Play<T> quitPlay(String id) {
		return Optional.<Play<T>>ofNullable(getPlay(id)).orElse(null);
	}

	/**
	 * Removes the persist play, if available
	 * 
	 * @param id play id
	 * @return true if available and removed
	 * @throws UncheckedIOException if the persist play isn't removable
	 * @see Files#deleteIfExists(Path)
	 */
	public boolean removePlay(String id) {
		try {
			return Files.deleteIfExists(buildFile(id));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Path buildFile(String id) {
		return rootPath.resolve(Objects.requireNonNull(id, "id is null").concat(XML_FILE_EXT));
	}

	private <T> Play<T> storePlay(Play<T> play) {
		PlayStore.store(play, buildFile(play.getId()));
		return play;
	}

}
