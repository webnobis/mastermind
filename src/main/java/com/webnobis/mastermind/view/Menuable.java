package com.webnobis.mastermind.view;

import javafx.scene.control.MenuItem;

@FunctionalInterface
public interface Menuable<T extends MenuItem> {

	T getMenuItem();

}
