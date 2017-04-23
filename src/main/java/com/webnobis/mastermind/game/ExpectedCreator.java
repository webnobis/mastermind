package com.webnobis.mastermind.game;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public interface ExpectedCreator<E> extends Supplier<List<E>> {
	
	static Supplier<List<Integer>> create(int size, int min, int max) {
		return () -> {
			Random random = new Random(System.currentTimeMillis());
			
		};
	}

}
