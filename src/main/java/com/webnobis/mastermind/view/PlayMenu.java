package com.webnobis.mastermind.view;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.PlayService;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface PlayMenu {

	static void addMenu(Window parent, VBox playPane, PlayService<ColorType> playService, Supplier<String> currentPlayIdSupplier,
			Consumer<Play<ColorType>> playListener) {
		RadioMenuItem storePlay = new RadioMenuItem("Speichern");
		storePlay.setOnAction(event -> {
			Optional.ofNullable(currentPlayIdSupplier.get()).ifPresent(id -> {
				FileChooser chooser = new FileChooser();
				chooser.setInitialFileName(id.concat(".xml"));
				storePlay.setSelected(Optional.ofNullable(chooser.showSaveDialog(parent)).map(File::toPath)
						.map(file -> playService.storePlay(id, file)).orElse(false));
			});
			event.consume();
		});

		Menu newPlay = new Menu("Neu");
		ToggleGroup newGroup = new ToggleGroup();
		IntStream.rangeClosed(3, 6).mapToObj(cols -> {
			RadioMenuItem newPlayItem = new RadioMenuItem(cols + " x unbegrenzt");
			newPlayItem.setToggleGroup(newGroup);
			newPlayItem.setOnAction(event -> {
				playListener.accept(playService.newPlay(cols));
				storePlay.setSelected(false);
				event.consume();
			});
			return newPlayItem;
		}).forEach(newPlay.getItems()::add);

		MenuItem openPlay = new MenuItem("Öffnen");
		openPlay.setOnAction(event -> {
			FileChooser chooser = new FileChooser();
			Optional.ofNullable(chooser.showOpenDialog(parent)).map(File::toPath).ifPresent(file -> {
				playListener.accept(playService.getPlay(file));
				storePlay.setSelected(false);
			});
			event.consume();
		});

		MenuItem nextTry = new MenuItem("Nächster Versuch");
		openPlay.setOnAction(event -> {
			Optional.ofNullable(currentPlayIdSupplier.get()).ifPresent(id -> {
				DialogPane tryDialog = new DialogPane();
				tryDialog.setContent(new Button("Versuch"));
				Stage stage = new Stage();
				stage.setScene(new Scene(tryDialog));
				stage.showAndWait();
				playListener.accept(playService.nextTry(id, Source.of(ColorType.YELLOW)));
			});
			event.consume();
		});

		Menu menu = new Menu("Spiel");
		menu.getItems().addAll(newPlay, new SeparatorMenuItem(), openPlay, storePlay, new SeparatorMenuItem(), nextTry, new SeparatorMenuItem());

		MenuBar menuBar = new MenuBar(menu);

		playPane.getChildren().add(menuBar);
	}

}
