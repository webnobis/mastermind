package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Verification;

import javafx.scene.layout.TilePane;

public class VerificationPane extends TilePane {
	
	private final Verification verification;

	public VerificationPane(Verification verification) {
		super();
		this.verification = verification;
		
		verification.getResults().stream().map(StepPosition::new).forEach(super.getChildren()::add);
	}
	
	

}
