package com.webnobis.mastermind;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PlayPane;
import com.webnobis.mastermind.view.ResultsToNode;
import com.webnobis.mastermind.view.SourcesToNode;
import com.webnobis.mastermind.view.StateToNode;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Mastermind extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(Mastermind.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		DialogPane dp = new DialogPane();

		new PlayPane<ColorType>(dp::setContent, SourcesToNode::toColorTypeNode, ResultsToNode::toResultTypeNode,
				StateToNode::toStateNode)
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

		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.setTitle("Mastermind 2.0");

		Scene scene = new Scene(dp, dp.getPrefWidth(), dp.getPrefHeight(), true, SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());
		dialogStage.setScene(scene);
		dialogStage.show();
	}

}
