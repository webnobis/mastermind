package com.webnobis.mastermind.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="game")
public interface Game<E> {

	@XmlElement(name="easyVerify")
	boolean isEasyVerify();

	Verifier<E> getVerifier();

	@XmlElement(name="tries")
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
		return create(Stream.generate(expectedSupplier).collect(Collectors.toList()), easyVerify);
	}

	static <E> Game<E> create(List<E> expected, boolean easyVerify) {
		return new Game<E>() {

			private final Verifier<E> verifier = () -> expected;

			@XmlElement(name="tries")
			private final List<List<E>> tries = new ArrayList<>();

			private final List<List<Result>> results = new ArrayList<>();

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

		};
	}

}
