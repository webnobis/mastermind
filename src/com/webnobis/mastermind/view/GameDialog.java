package com.webnobis.mastermind.view;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Trying;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class GameDialog<T> extends Dialog<Game<? extends Trying<T>, T>> {

	private Game<? extends Trying<T>, T> game;

	public GameDialog(Game<? extends Trying<T>, T> game) {
		super();
		this.game = game;
		
		TilePane pane = new TilePane(3.0,3.0);
		pane.setPrefColumns(1);
		//pane.setBorder(new Border(new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
		game.getTryings().stream().map(StepPane::new).forEach(pane.getChildren()::add);
		
		DialogPane dialogPane = super.getDialogPane();
		dialogPane.setContent(pane);
		
		super.setTitle("Mastermind");
		super.setResultConverter(button -> null);
	}
	
}
