package com.webnobis.mastermind.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Game<E> {

	String getId();

	boolean isEasyVerify();

	Verifier<E> getVerifier();

	List<List<E>> getTries();

	List<List<Result>> getResults();

	default List<Result> nextTry(List<E> test) {
		List<Result> result = (isEasyVerify()) ? getVerifier().verifyEasy(test) : getVerifier().verifyHard(test);
		if (getTries().add(test) && getResults().add(result)) {
			return result;
		} else {
			throw new IllegalStateException("current try couldn't store");
		}
	}

	default boolean isFinish() {
		return getResults().stream().anyMatch(getVerifier()::isFinish);
	}

	default int tries() {
		return getTries().size();
	}

	static <E> Game<E> create(Supplier<E> expectedSupplier, boolean easyVerify) {
		return create(Stream.generate(expectedSupplier).limit(100).collect(Collectors.toList()), easyVerify);
	}

	static <E> Game<E> create(List<E> expected, boolean easyVerify) {
		return new Game<E>() {

			private final String id = UUID.randomUUID().toString();

			private final Verifier<E> verifier = () -> Collections.unmodifiableList(expected);

			private final List<List<E>> tries = new ArrayList<>();

			private final List<List<Result>> results = new ArrayList<>();

			@Override
			public String getId() {
				return id;
			}

			@Override
			public boolean isEasyVerify() {
				return easyVerify;
			}

			@Override
			public Verifier<E> getVerifier() {
				return verifier;
			}

			@Override
			public List<List<E>> getTries() {
				return tries;
			}

			@Override
			public List<List<Result>> getResults() {
				return results;
			}

			@Override
			public int hashCode() {
				return Objects.hash(id);
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == this) {
					return true;
				}
				if (obj == null || !this.getClass().equals(obj.getClass())) {
					return false;
				}
				return Objects.equals(id, ((Game<?>) obj).getId());
			}

			@Override
			public String toString() {
				return String.format("%s(%s): %s tries, finish=%s", Game.class.getSimpleName(), id, tries(), isFinish());
			}

		};
	}

}
