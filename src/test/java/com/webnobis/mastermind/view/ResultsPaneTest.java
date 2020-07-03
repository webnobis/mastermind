package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

class ResultsPaneTest {

	@Test
	void testCreate() {
		Pane pane = ResultsPane.create(Collections.singletonList(Result.of(Source.of(ColorType.HOLE), ResultType.PRESENT)));
		assertNotNull(pane);
		assertSame(1, pane.getChildren().size());
		assertTrue(pane.getChildren().stream().allMatch(node -> HBox.class.equals(node.getClass())));
	}

}
