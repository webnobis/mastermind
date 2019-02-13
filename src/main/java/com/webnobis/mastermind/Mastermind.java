package com.webnobis.mastermind;

import java.util.Arrays;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.Solution;
import com.webnobis.mastermind.model.Trying;
import com.webnobis.mastermind.service.GameService;
import com.webnobis.mastermind.view.GameDialog;

import javafx.application.Application;
import javafx.scene.control.Dialog;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mastermind extends Application {

	public static void main(String[] args) throws Exception {
		Application.launch(Mastermind.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
/*		Game<Trying<Integer>, Integer> game = GameService.newGame(new Solution<>(Arrays.asList(7,7,5,6)));
		game = GameService.nextTrying(game, Arrays.asList(7,7,5,6));
		game = GameService.nextTrying(game, Arrays.asList(8,7,5,6));*/
		
		Game<Trying<Color>, Color> game = GameService.newGame(new Solution<>(Arrays.asList(Color.AQUAMARINE, Color.CHOCOLATE, Color.BEIGE, Color.OLIVE)));
		game = GameService.nextTrying(game, Arrays.asList(Color.YELLOW, Color.BEIGE, Color.CHOCOLATE));
		game = GameService.nextTrying(game, Arrays.asList(Color.AQUAMARINE, Color.CHOCOLATE, Color.BEIGE, Color.OLIVE));
		
//		Dialog<Game<? extends Trying<Integer>, Integer>> dialog = new GameDialog<>(game);
		Dialog<Game<? extends Trying<Color>, Color>> dialog = new GameDialog<>(GameService.verify(game));
		dialog.showAndWait();
	}

	

}
