package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Sphere;

class ResultTypePaneTest {

	@Test
	void testCreate() {
		Pane pane = ResultTypePane.create(Result.of(Source.of(null, null), ResultType.PRESENT, ResultType.EXACT));
		assertNotNull(pane);
		assertSame(2, pane.getChildren().size());
		assertTrue(pane.getChildren().stream().allMatch(node -> Sphere.class.equals(node.getClass())));
	}

}
