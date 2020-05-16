package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Trying;
import com.webnobis.mastermind.model.VerifiedTrying;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class StepPane<T> extends GridPane {
	
	private final Trying<T> trying;

	public StepPane(Trying<T> trying) {
		super();
		this.trying = trying;
		
		Pane tryingPane = new TryingPane<>(trying);
		Pane verificationPane;
		if (VerifiedTrying.class.isAssignableFrom(trying.getClass())) {
			verificationPane = new VerificationPane(VerifiedTrying.class.cast(trying).getVerification());
		} else {
			verificationPane = new Pane();
		}
		super.setPadding(new Insets(3.0));
		super.add(tryingPane, 0, 0);
		super.add(verificationPane, 1, 0);
	}
	
	

}
