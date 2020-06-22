package com.webnobis.mastermind.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.Source;

/**
 * File system persistent play service<br>
 * Each method except quit gets the play without the solution source.
 * 
 * @author steffen
 * 
 * @param <T> type of findings
 * @see PlayService#quitPlay(String)
 */
public class PlayService<T> {

	private static final String XML_FILE_EXT = ".xml";

	private final Path rootPath;

	private final IntFunction<Source<T>> solutionGenerator;

	private final BiFunction<Source<T>, Source<T>, Result<T>> assessmentService;

	/**
	 * Play service with root path persistent area, solution generator and
	 * assessment service
	 * 
	 * @param rootPath          root path
	 * @param solutionGenerator solution generator
	 * @param assessmentService assessment service
	 */
	public PlayService(Path rootPath, IntFunction<Source<T>> solutionGenerator,
			BiFunction<Source<T>, Source<T>, Result<T>> assessmentService) {
		this.rootPath = Objects.requireNonNull(rootPath, "rootPath is null");
		this.solutionGenerator = Objects.requireNonNull(solutionGenerator, "solutionGenerator is null");
		this.assessmentService = Objects.requireNonNull(assessmentService, "assessmentService is null");
	}

	/**
	 * New unlimited persist play
	 * 
	 * @param cols columns, it's the count of searched findings
	 * @return new play
	 * @see Play#of(Class, int)
	 * @see PlayStore#store(Play, Path)
	 */
	public Play<T> newPlay(int cols) {
		return storePlay(Play.of(cols, solutionGenerator.apply(cols))).withoutSource();
	}

	/**
	 * New limited persist play
	 * 
	 * @param cols columns, it's the count of searched findings
	 * @param rows maximum try rows, until the Play is finish, in-depending the
	 *             solution was found
	 * @return new play
	 * @see Play#of(Class, int, int)
	 * @see PlayStore#store(Play, Path)
	 */
	public Play<T> newPlay(int cols, int rows) {
		return storePlay(Play.of(cols, rows, solutionGenerator.apply(cols))).withoutSource();
	}

	/**
	 * Get the persist play, if available
	 * 
	 * @param id play id
	 * @return play, otherwise null
	 * @see PlayStore#load(Path)
	 */
	public Play<T> getPlay(String id) {
		return loadPlay(id).withoutSource();
	}

	/**
	 * Assesses the next try and adds the result at the play and updates it, if
	 * available and not quit
	 * 
	 * @param id        play id
	 * @param trySource next try source
	 * @return updated play, otherwise null
	 * @see AssessmentService#assess(Source, Source)
	 * @see Play#withAddedResult(com.webnobis.mastermind.model.Result)
	 * @see PlayStore#store(Play, Path)
	 * @see PlayService#quitPlay(String)
	 */
	public Play<T> nextTry(String id, Source<T> trySource) {
		return Optional
				.ofNullable(
						loadPlay(id))
				.map(play -> play.withAddedResult(assessmentService.apply(play.getSource(),
						Objects.requireNonNull(trySource, "trySource is null"))).withoutSource())
				.orElse(null);
	}

	/**
	 * Quits the play with the solution in-depending if it's finish or solved and
	 * updates it, if available
	 * 
	 * @param id play id
	 * @return updated play, otherwise null
	 * @see Play#withSource(Source)
	 * @see PlayStore#store(Play, Path)
	 */
	public Play<T> quitPlay(String id) {
		return Optional.ofNullable(loadPlay(id)).orElse(null);
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

	private Play<T> loadPlay(String id) {
		return PlayStore.<T>load(buildFile(id));
	}

	private Play<T> storePlay(Play<T> play) {
		PlayStore.store(play, buildFile(play.getId()));
		return play;
	}

}
