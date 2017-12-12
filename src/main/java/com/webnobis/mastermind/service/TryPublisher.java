package com.webnobis.mastermind.service;

import com.webnobis.mastermind.model.Try;

@FunctionalInterface
public interface TryPublisher {

	void publish(String id, Try nextTry);

}
