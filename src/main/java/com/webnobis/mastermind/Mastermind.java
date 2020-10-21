package com.webnobis.mastermind;

import java.util.function.Consumer;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.service.AssessmentService;
import com.webnobis.mastermind.service.PlayService;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.NextTryDialog;
import com.webnobis.mastermind.view.PlayMenu;
import com.webnobis.mastermind.view.PlayPane;
import com.webnobis.mastermind.view.ResultsToNode;
import com.webnobis.mastermind.view.SolutionGenerator;
import com.webnobis.mastermind.view.SourcesToNode;
import com.webnobis.mastermind.view.StateToNode;

import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mastermind extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(Mastermind.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		PlayService<ColorType> service = new PlayService<>(SolutionGenerator::generateColorTypes,
				AssessmentService::assess);

		VBox mainPane = new VBox();

		Consumer<Play<ColorType>> playConsumer = new PlayPane<>(stage, mainPane.getChildren()::add,
				SourcesToNode::toColorTypeNode, ResultsToNode::toResultTypeNode, StateToNode::toStateNode);

		mainPane.getChildren().add(0,
				new PlayMenu<ColorType>(stage, service, NextTryDialog::create, playConsumer).create());

		// depth test disable fixes the not working menu events
		mainPane.setDepthTest(DepthTest.DISABLE);

		Scene scene = new Scene(mainPane, mainPane.getPrefWidth(), mainPane.getPrefHeight(), true,
				SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());

		stage.setTitle("Mastermind 2.0");
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.show();
	}

}
