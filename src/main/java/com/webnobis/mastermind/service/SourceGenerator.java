package com.webnobis.mastermind.service;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Source;

/**
 * Source generator
 * 
 * @author steffen
 *
 */
public interface SourceGenerator {

	/**
	 * Creates Source with count of cols random elements from pool.<br>
	 * An empty pool results ever in an empty source.
	 * 
	 * @param <T>  type of elements
	 * @param cols cols
	 * @param pool pool of usable elements
	 * @return source with count of cols random elements
	 */
	@SuppressWarnings("unchecked")
	static <T> Source<T> generateFromPool(int cols, Collection<T> pool) {
		if (Objects.requireNonNull(pool).isEmpty()) {
			return Source.of();
		}

		Random random = new SecureRandom(UUID.randomUUID().toString().getBytes());
		List<T> list = new ArrayList<>(Objects.requireNonNull(pool));
		return Source.of(Stream.generate(() -> list.get(random.nextInt(list.size()))).limit(cols)
				.toArray(i -> (T[]) Array.newInstance(list.iterator().next().getClass(), i)));
	}

}
