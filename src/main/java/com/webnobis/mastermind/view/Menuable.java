package com.webnobis.mastermind.view;

import javafx.scene.control.MenuItem;

/**
 * Menuable to refer to the menu item
 * 
 * @author steffen
 *
 * @param <T> menu item type
 */
@FunctionalInterface
public interface Menuable<T extends MenuItem> {

	/**
	 * 
	 * @return menu item
	 */
	T getMenuItem();

}
