package com.webnobis.mastermind.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.webnobis.mastermind.model.Assessment;
import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.TryWithAssessment;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GameUpdateServiceTest {

	private static BiFunction<GameWithSolution, TryWithAssessment, GameWithSolution> gameUpdateService;

	@Mocked
	private GameConfig config;

	@Mocked
	private Game game;

	@Mocked
	private Solution solution;

	@Mocked
	private GameWithSolution gameWithSolution;

	@Mocked
	private Try try1;

	@Mocked
	private Try try2;

	@Mocked
	private Assessment assessment1;

	@Mocked
	private Assessment assessment2;

	@Mocked
	private TryWithAssessment tryWithAssessment1;

	@Mocked
	private TryWithAssessment tryWithAssessment2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gameUpdateService = GameUpdateService::update;
	}

	@Before
	public void setUp() throws Exception {
		new Expectations() {
			{
				game.getConfig();
				returns(config);
				game.getTries();
				returns(Collections.singletonList(tryWithAssessment1));
			}
			{
				gameWithSolution.getGame();
				returns(game);
				gameWithSolution.getSolution();
				returns(solution);
			}
		};
	}

	@Test
	public void testUpdate() {
		new Expectations() {
			{
				try2.getIdeas();
				returns(Collections.singletonList(1));
			}
			{
				assessment2.getAssessments();
				returns(Collections.emptyList());
			}
			{
				tryWithAssessment2.getTry();
				returns(try2);
				tryWithAssessment2.getAssessment();
				returns(assessment2);
			}
		};

		GameWithSolution updated = gameUpdateService.apply(gameWithSolution, tryWithAssessment2);
		assertNotNull(updated);
		assertEquals(solution, updated.getSolution());
		assertEquals(config, updated.getGame().getConfig());
		assertEquals(Arrays.asList(tryWithAssessment1, tryWithAssessment2), updated.getGame().getTries());
	}

}
