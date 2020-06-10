package com.webnobis.mastermind.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SourceTest {

	private Source<Boolean> source;

	@BeforeEach
	void setUp() throws Exception {
		source = Source.of();
	}

	@Test
	void testOf() {
		Source<Integer> newSource = Source.of(IntStream.generate(() -> Integer.MAX_VALUE).limit(Source.LIMIT + 1)
				.boxed().toArray(i -> new Integer[i]));

		assertNotNull(newSource);
		assertNotNull(newSource.getSources());
		assertSame(Source.LIMIT, newSource.getSources().size());
	}

	@Test
	void testGetSources() {
		assertTrue(source.getSources().isEmpty());
	}

}
