package com.webnobis.mastermind;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.ResultsScrollPane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
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
		
//		Game<Trying<Color>, Color> game = GameService.newGame(new Solution<>(Arrays.asList(Color.AQUAMARINE, Color.CHOCOLATE, Color.BEIGE, Color.OLIVE)));
//		game = GameService.nextTrying(game, Arrays.asList(Color.YELLOW, Color.BEIGE, Color.CHOCOLATE));
//		game = GameService.nextTrying(game, Arrays.asList(Color.AQUAMARINE, Color.CHOCOLATE, Color.BEIGE, Color.OLIVE));
//		
////		Dialog<Game<? extends Trying<Integer>, Integer>> dialog = new GameDialog<>(game);
//		Dialog<Game<? extends Trying<Color>, Color>> dialog = new GameDialog<>(GameService.verify(game));
//		dialog.showAndWait();
		
		
		DialogPane dp = new DialogPane();
		ScrollPane scrollPane = ResultsScrollPane.create(Stream.generate(() -> Result.of(Source.of(ColorType.RED, ColorType.HOLE, ColorType.RED, ColorType.YELLOW), ResultType.EXACT, ResultType.EXACT, ResultType.PRESENT)).limit(10).collect(Collectors.toList()));
		scrollPane.setPrefSize(200, 200);
		dp.setContent(scrollPane);
		//dp.setPrefWidth(dp.getContent().minWidth(0));
		//dp.setOnScrollFinished(System.out::println);
		scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				System.out.println(arg1 + "#" + arg2);
				dp.resize(dp.getWidth() + 1, dp.getHeight());
			}
		});
		
		Dialog<Object> d = new Dialog<>();
		d.setResizable(true);
		d.setDialogPane(dp);
		d.showAndWait();
	}

	

}
