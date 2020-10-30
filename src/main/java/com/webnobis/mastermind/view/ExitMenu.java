package com.webnobis.mastermind.view;

import javafx.scene.control.MenuItem;

public class ExitMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	public ExitMenu() {
		item = new MenuItem("Beenden");
		item.setOnAction(event -> System.exit(0));
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

}
