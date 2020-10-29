package com.webnobis.mastermind;

import com.webnobis.mastermind.view.NextTryDialog;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuSphereMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox pane = new VBox(new Label("777"));
//		
//		Sphere sphere = new Sphere(50);
//		sphere.setDepthTest(DepthTest.ENABLE);
//
//		pane.getChildren().add(createMenu());
//		pane.getChildren().add(sphere);
//		pane.setDepthTest(DepthTest.DISABLE);
//		
//		Scene scene = new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight(), true,
//				SceneAntialiasing.BALANCED);
//		scene.setCamera(new PerspectiveCamera());
//
		stage.setTitle("Mastermind 2.0");
		stage.centerOnScreen();
//		stage.setScene(scene);
//		stage.show();
//
		new NextTryDialog(4, System.out::println);
	}

	private MenuBar createMenu() {
		MenuItem item = new MenuItem("Star");
		Menu menu = new Menu("123");
		menu.getItems().add(item);
		MenuBar bar = new MenuBar(menu);
		return bar;
	}

}
