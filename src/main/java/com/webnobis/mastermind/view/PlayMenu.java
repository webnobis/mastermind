package com.webnobis.mastermind.view;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.DepthTest;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public interface PlayMenu {

	static void addMenu(VBox playPane, BiConsumer<ActionEvent, MenuCommand> commandListener) {
		MenuItem newPlay = new MenuItem("Neu");
		newPlay.setOnAction(event -> {
			commandListener.accept(event, MenuCommand.NEW);
			event.consume();
		});

		Menu menu = new Menu("Spiel");
		menu.getItems().add(newPlay);
		
		

		Menu menuFile = new Menu("File");
		MenuItem add = new MenuItem("Shuffle");

		menuFile.getItems().add(add);

		MenuBar menuBar = new MenuBar(menu, menuFile);
		// disable depth test fixes the unclick menu  
		menuBar.setDepthTest(DepthTest.DISABLE);
		
		playPane.getChildren().add(menuBar);
	}

}
