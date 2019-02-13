package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Trying;

import javafx.scene.layout.TilePane;

public class TryingPane<T> extends TilePane {
	
	private final Trying<T> trying;

	public TryingPane(Trying<T> trying) {
		super();
		this.trying = trying;
		
		trying.getPositions().stream().map(StepPosition::new).forEach(super.getChildren()::add);
	}
	
}
