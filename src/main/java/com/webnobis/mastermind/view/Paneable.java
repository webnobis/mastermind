package com.webnobis.mastermind.view;

import javafx.scene.layout.Pane;

/**
 * Paneable, to refer to the pane
 * 
 * @author steffen
 *
 * @param <T> pane type
 */
@FunctionalInterface
public interface Paneable<T extends Pane> {

	/**
	 * 
	 * @return pane
	 */
	T getPane();

}
