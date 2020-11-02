package com.webnobis.mastermind.view;

/**
 * Readable, to refer to the type
 * 
 * @author steffen
 *
 * @param <T> type
 */
@FunctionalInterface
public interface Readable<T> {

	/**
	 * 
	 * @return type
	 */
	T getType();

}
