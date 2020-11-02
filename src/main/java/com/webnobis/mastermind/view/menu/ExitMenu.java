package com.webnobis.mastermind.view.menu;

import com.webnobis.mastermind.view.Menuable;

import javafx.scene.control.MenuItem;

/**
 * Exit menu
 * 
 * @author steffen
 *
 */
public class ExitMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	/**
	 * Exits the Mastermind game
	 */
	public ExitMenu() {
		item = new MenuItem("Verlassen");
		item.setOnAction(event -> System.exit(0));
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

}
