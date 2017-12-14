package com.webnobis.mastermind.service;

@FunctionalInterface
public interface Transformer<T, R> {
	
	R transform(T type);

}
