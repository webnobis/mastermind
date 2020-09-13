package com.webnobis.mastermind;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PlayMenu;
import com.webnobis.mastermind.view.PlayPane;
import com.webnobis.mastermind.view.ResultsToNode;
import com.webnobis.mastermind.view.SourcesToNode;
import com.webnobis.mastermind.view.StateToNode;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Mastermind extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(Mastermind.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox playPane = new VBox();
		
		/*
		 * Menu-Items werden beim Anklicken nicht angezeigt. Das Mouse-Klicken ist deaktiviert.
		 * Das ist immer so, wenn Sphere verwendet wird, egal ob in Group, einem Extra-Pane oder so.
		 * Nur wenn Sphere nicht vorhanden ist, läßt sich das Menu mit der Mouse bedienen.
		 * Mit Tasten geht es jedoch immer: ALT + Nach unten
		 * Irgendwie scheinen Mouse-Events bei Sphere abgeschaltet zu sein...
		 */
		
		PlayMenu.addMenu(playPane, (event, command) -> {
			System.out.println(command);
		});

		new PlayPane<ColorType>(playPane.getChildren()::add, SourcesToNode::toColorTypeNode, ResultsToNode::toResultTypeNode,
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
 		dialogStage.centerOnScreen();

		Scene scene = new Scene(playPane, playPane.getPrefWidth(), playPane.getPrefHeight(), true, SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());
		dialogStage.setScene(scene);
		dialogStage.show();
	}

}
