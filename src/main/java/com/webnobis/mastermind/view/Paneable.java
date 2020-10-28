package com.webnobis.mastermind.view;

import javafx.scene.layout.Pane;

@FunctionalInterface
public interface Paneable<P extends Pane> {

	P getPane();

}
