package com.webnobis.mastermind.view;

import javafx.scene.layout.Pane;

@FunctionalInterface
public interface Paneable<T extends Pane> {

	T getPane();

}
