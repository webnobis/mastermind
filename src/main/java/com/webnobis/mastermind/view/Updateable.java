package com.webnobis.mastermind.view;

import javafx.scene.layout.Pane;

public interface Updateable<T,P extends Pane> extends Paneable<P> {

	void update(T type);

	T getType();

}
