package com.webnobis.mastermind.service.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.PlayService;

class FilePlayServiceTest {
	
	private static final String ID = "It is the only one id";

	private static final IntFunction<Source<TestValue>> sourceGenerator = size -> Source
			.of(IntStream.range(0, size).mapToObj(i -> new TestValue(i, i % 2 > 0)).toArray(TestValue[]::new));
	
	private static final Function<Source<TestValue>, Result<TestValue>> resultGenerator = source -> Result.of(source, ResultType.EXACT, ResultType.PRESENT);

	private Path tmpRootFolder;

	private PlayService<TestValue> playService;

	@BeforeEach
	void setUp() throws Exception {
		tmpRootFolder = Files.createTempDirectory(FilePlayServiceTest.class.getSimpleName());
		
		playService = new FilePlayService<>(tmpRootFolder, () -> ID, sourceGenerator, resultGenerator);
	}

	@AfterEach
	void tearDown() throws Exception {
		List<Path> paths = Files.walk(tmpRootFolder).collect(Collectors.toList());
		Collections.reverse(paths);
		paths.forEach(t -> {
			try {
				Files.delete(t);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}

	@Test
	void testNewPlay() throws IOException {
		int cols = 15;
		int rows = 30;
		
		String id = playService.newPlay(cols, rows);
		assertEquals(ID, id);
		
		Source<TestValue> source = sourceGenerator.apply(cols);
		Play<TestValue> play = Files.walk(tmpRootFolder).filter(Files::isRegularFile).findFirst().map(file -> {
		try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
			return (Play<TestValue>) in.readObject();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		}).orElseThrow();
		assertEquals(source, play.getSource());
		assertTrue(play.getResults().isEmpty());
	}

	@Test
	void testNextTry() {
		fail("Not yet implemented");
	}

	@Test
	void testQuit() {
		fail("Not yet implemented");
	}

	private static class TestValue implements Serializable {

		private static final long serialVersionUID = 1L;

		private final int i;

		private final boolean b;

		TestValue(int i, boolean b) {
			this.i = i;
			this.b = b;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (b ? 1231 : 1237);
			result = prime * result + i;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			TestValue other = (TestValue) obj;
			if (b != other.b) {
				return false;
			}
			if (i != other.i) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "TestValue [i=" + i + ", b=" + b + "]";
		}
	}

}
