package com.webnobis.mastermind;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.ResultType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.ColorType;
import com.webnobis.mastermind.view.PlayPane;
import com.webnobis.mastermind.view.ResultsToPane;
import com.webnobis.mastermind.view.SourcesToPane;
import com.webnobis.mastermind.view.StateToPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
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
		new PlayPane<ColorType>(dp::setContent, SourcesToPane::toColorTypePane, ResultsToPane::toColorTypePane, StateToPane::toPane).accept(Play.of(7, Source.of(ColorType.BLUE)).withAddedResult(Result.of(Source.of(ColorType.RED, ColorType.ORANGE, ColorType.ORANGE, ColorType.ORANGE), ResultType.PRESENT)));
//		Pane pane = ResultsPane.create(Stream.generate(() -> Result.of(Source.of(ColorType.RED, ColorType.HOLE, ColorType.RED, ColorType.YELLOW), ResultType.EXACT, ResultType.EXACT, ResultType.PRESENT)).limit(10).collect(Collectors.toList()));
//		scrollPane.setPrefSize(200, 200);
//		dp.setContent(updateable.getPane());
//		updateable.getPane().setPrefSize(400, 1000);
		//dp.setPrefWidth(dp.getContent().minWidth(0));
		//dp.setOnScrollFinished(System.out::println);
//		scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//				System.out.println(arg1 + "#" + arg2);
//				dp.resize(dp.getWidth() + 1, dp.getHeight());
//			}
//		});
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);

//		VBox vbox = new VBox(new Text("Hi"), new Button("Ok."));
//		vbox.setAlignment(Pos.CENTER);
//		vbox.setPadding(new Insets(15));

		dialogStage.setScene(new Scene(dp));
		dialogStage.show();
		}

	

}
