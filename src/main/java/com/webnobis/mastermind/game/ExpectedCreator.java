package com.webnobis.mastermind.game;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface ExpectedCreator<L extends List<?>> extends Supplier<L> {

	static Supplier<List<Integer>> create(int size, int from, int to) {
		return () -> {
			if (size < 1) {
				throw new IllegalArgumentException("expected size > 0");
			}
			if (to <= from) {
				throw new IllegalArgumentException("expected from < to");
			}
			return new Random(System.currentTimeMillis()).ints(size, from, to)
					.parallel()
					.boxed()
					.collect(Collectors.toList());
		};
	}

}
