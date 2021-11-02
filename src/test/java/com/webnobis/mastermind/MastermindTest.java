package com.webnobis.mastermind;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

class MastermindTest {

	@Test
	void testMain() {
		Mastermind.appSupplier = () -> TestApplication.class;

		Mastermind.main(null);

		assertTrue(TestApplication.flag.get());
	}

	public static class TestApplication extends Application {

		static final AtomicBoolean flag = new AtomicBoolean();

		@SuppressWarnings("exports")
		@Override
		public void start(Stage stage) throws Exception {
			assertNotNull(stage);
			flag.set(true);
			Platform.runLater(() -> stage.close());
			stage.show();
		}

	}

}
