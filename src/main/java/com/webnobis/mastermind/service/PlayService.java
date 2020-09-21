package com.webnobis.mastermind.service;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

	private final IntFunction<Source<T>> solutionGenerator;

	private final BiFunction<Source<T>, Source<T>, Result<T>> assessmentService;

	private final Map<String, Play<T>> playCache;

	/**
	 * Play service with root path persistent area, solution generator and
	 * assessment service
	 * 
	 * @param rootPath          root path
	 * @param solutionGenerator solution generator
	 * @param assessmentService assessment service
	 */
	public PlayService(IntFunction<Source<T>> solutionGenerator,
			BiFunction<Source<T>, Source<T>, Result<T>> assessmentService) {
		this.solutionGenerator = Objects.requireNonNull(solutionGenerator, "solutionGenerator is null");
		this.assessmentService = Objects.requireNonNull(assessmentService, "assessmentService is null");
		playCache = new HashMap<>();
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
		Play<T> play = Play.of(cols, solutionGenerator.apply(cols));
		playCache.put(play.getId(), play);
		return play.withoutSource();
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
		Play<T> play = Play.of(cols, rows, solutionGenerator.apply(cols));
		playCache.put(play.getId(), play);
		return play.withoutSource();
	}

	/**
	 * Get the persist play, if available
	 * 
	 * @param file file containing a XML play
	 * @return play, otherwise null
	 * @see PlayStore#load(Path)
	 */
	public Play<T> getPlay(Path file) {
		Play<T> play = PlayStore.load(file);
		playCache.put(play.getId(), play);
		return play.withoutSource();
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
		Play<T> play = Optional.ofNullable(id).map(playCache::get)
				.map(foundPlay -> foundPlay.withAddedResult(assessmentService.apply(foundPlay.getSource(),
						Objects.requireNonNull(trySource, "trySource is null"))))
				.orElseThrow(() -> new NoSuchElementException(id));
		playCache.put(play.getId(), play);
		return play.withoutSource();
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
		return Optional.ofNullable(id).map(playCache::get).orElseThrow(() -> new NoSuchElementException(id));
	}

	/**
	 * Stores the play, if available
	 * 
	 * @param id   play id
	 * @param file file which should containing the XML play
	 * @return true if available and stored
	 * @throws UncheckedIOException if the play couldn't be stored
	 * @see Files#deleteIfExists(Path)
	 */
	public boolean storePlay(String id, Path file) {
		return Optional.ofNullable(id).map(playCache::get).map(play -> PlayStore.store(play, file)).orElse(false);
	}

	/**
	 * Removes the persist play, if available
	 * 
	 * @param id   play id
	 * @param file the file
	 * @return true if available and removed
	 * @throws UncheckedIOException if the persist play isn't removable
	 * @see Files#deleteIfExists(Path)
	 */
	public boolean removePlay(Path file) {
		return Optional.ofNullable(getPlay(file)).map(Play::getId).map(playCache::remove)
				.map(play -> PlayStore.delete(file)).orElse(false);
	}

}
