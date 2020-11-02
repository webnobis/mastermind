package com.webnobis.mastermind;

import com.webnobis.mastermind.view.MastermindDialog;

import javafx.application.Application;

/**
 * Main class of the graphical representation of the Mastermind game
 * 
 * @author steffen
 *
 */
public class Mastermind {

	/**
	 * Starts the graphical representation
	 * 
	 * @param args unused
	 * @see Application#launch(Class, String...)
	 * @see MastermindDialog#start(javafx.stage.Stage)
	 */
	public static void main(String[] args) {
		Application.launch(MastermindDialog.class, args);
	}

}
