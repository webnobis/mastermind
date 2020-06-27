package com.webnobis.mastermind.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.webnobis.mastermind.model.Source;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Sphere;

class SourcePaneTest {

	@Test
	void testCreate() {
		Pane pane = SourcePane.create(Source.of(ColorType.HOLE, ColorType.BLUE));
		assertNotNull(pane);
		assertSame(2, pane.getChildren().size());
		assertTrue(pane.getChildren().stream().allMatch(node -> Sphere.class.equals(node.getClass())));
	}

}
