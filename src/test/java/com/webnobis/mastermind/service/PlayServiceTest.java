package com.webnobis.mastermind.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayServiceTest {
	
	private PlayService<Boolean> playService;

	@BeforeEach
	void setUp() throws Exception {
		playService = PlayService.create(Collections.emptyMap());
		
		assumeTrue(playService != null);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNewPlayClassOfTInt() {
		fail("Not yet implemented");
	}

	@Test
	void testNewPlayClassOfTIntInt() {
		fail("Not yet implemented");
	}

	@Test
	void testNextTry() {
		fail("Not yet implemented");
	}

	@Test
	void testQuit() {
		fail("Not yet implemented");
	}
	
}
