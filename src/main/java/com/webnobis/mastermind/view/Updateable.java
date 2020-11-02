package com.webnobis.mastermind.view;

/**
 * Updateable, to updates with the type
 * 
 * @author steffen
 *
 * @param <T> type
 */
@FunctionalInterface
public interface Updateable<T> {

	/**
	 * 
	 * @param type type to update
	 */
	void update(T type);

}
