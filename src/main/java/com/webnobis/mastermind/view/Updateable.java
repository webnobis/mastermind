package com.webnobis.mastermind.view;

import javafx.scene.layout.Pane;

public interface Updateable<P extends Pane, T> {

	P getPane();

	void update(T type);

}
