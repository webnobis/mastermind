package com.webnobis.mastermind;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.AssessmentService;
import com.webnobis.mastermind.service.PlayService;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PlayMenu;
import com.webnobis.mastermind.view.PlayPane;
import com.webnobis.mastermind.view.ResultsToNode;
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
		VBox playPane = new VBox();

		PlayMenu.addMenu(stage, playPane,
				new PlayService<ColorType>(i -> Source.of(ColorType.BLUE), AssessmentService::assess), () -> Play.of(3, Source.of(ColorType.VIOLET)).getId(), play -> {
					System.out.println(play);
				});

		new PlayPane<ColorType>(playPane.getChildren()::add, SourcesToNode::toColorTypeNode,
				ResultsToNode::toResultTypeNode, StateToNode::toStateNode)
						.accept(Play.of(7, Source.of(ColorType.BLUE))
								.withAddedResult(Result.of(
										Source.of(ColorType.RED, ColorType.ORANGE, ColorType.ORANGE, ColorType.ORANGE),
										ResultType.PRESENT))
								.withAddedResult(Result.of(
										Source.of(ColorType.BLUE, ColorType.ORANGE, ColorType.ORANGE, ColorType.ORANGE),
										ResultType.PRESENT, ResultType.EXACT))
								.withAddedResult(
										Result.of(
												Source.of(ColorType.RED, ColorType.GREEN, ColorType.ORANGE,
														ColorType.ORANGE),
												ResultType.PRESENT, ResultType.PRESENT, ResultType.PRESENT,
												ResultType.PRESENT)));

		// depth test disable fixes the not working menu events
		playPane.setDepthTest(DepthTest.DISABLE);

		Scene scene = new Scene(playPane, playPane.getPrefWidth(), playPane.getPrefHeight(), true,
				SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());

		stage.setTitle("Mastermind 2.0");
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.show();
	}

}
