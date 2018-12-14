package com.webnobis.mastermind.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.service.GameService;

public class GameStoreTest {

	private static final Game<Integer> GAME = GameService.newGame(Arrays.asList(6,7,3,3,7,8));

	private Path tmpFolder;

	private Map<String, Game<Integer>> gameStore;
	
	private String id;

	@Before
	public void setUp() throws Exception {
		tmpFolder = Files.createTempDirectory(GameStoreTest.class.getSimpleName());

		gameStore = new GameStore<>(tmpFolder);
		id = GAME.getId();
	}

	@After
	public void tearDown() throws Exception {
		Files.walkFileTree(tmpFolder, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Test
	public void testPut() {
		assertTrue(gameStore.isEmpty());

		assertNull(gameStore.put(id, GAME));
		assertEquals(1, gameStore.size());

		Path file = tmpFolder.resolve(id.concat(GameStore.FILE_EXT));
		assertTrue(Files.exists(file));
		assertEquals(GAME, JAXB.unmarshal(file.toFile(), Game.class));
		
		assertEquals(GAME, gameStore.put(id, GameService.newGame(Collections.emptyList())));
	}

	@Test
	public void testEntrySet() {
		assertTrue(gameStore.isEmpty());

		Path file = tmpFolder.resolve(id.concat(GameStore.FILE_EXT));
		JAXB.marshal(GAME, file.toFile());

		Set<Entry<String, Game<Integer>>> entrySet = gameStore.entrySet();
		assertEquals(1, entrySet.size());
		entrySet.forEach(e -> {
			assertEquals(id, e.getKey());
			assertEquals(GAME, e.getValue());
		});
	}

	@Test
	public void testRemove() {
		Path file = tmpFolder.resolve(id.concat(GameStore.FILE_EXT));
		JAXB.marshal(GAME, file.toFile());

		assertFalse(gameStore.isEmpty());

		assertEquals(GAME, gameStore.remove(id));
		assertTrue(gameStore.isEmpty());

		assertFalse(Files.exists(file));
	}

}
