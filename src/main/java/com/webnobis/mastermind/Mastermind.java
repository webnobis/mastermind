package com.webnobis.mastermind;

import java.util.Collections;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.service.GameService;

public class Mastermind {

	public static void main(String[] args) throws Exception {
		JAXB.marshal(buildGame(), System.out);
	}

	static Game<Boolean> buildGame() {
		return GameService.newGame(Collections.singletonList(true));
	}

}
