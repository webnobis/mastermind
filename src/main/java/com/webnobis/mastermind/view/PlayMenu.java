package com.webnobis.mastermind.view;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.service.PlayService;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class PlayMenu<T> {

	private final Window parent;

	private final PlayService<T> playService;

	private final BiFunction<Window, Play<T>, Source<T>> nextTryListener;

	private final Consumer<Play<T>> playListener;

	private final AtomicReference<Play<T>> currentPlayRef;

	public PlayMenu(Window parent, PlayService<T> playService, BiFunction<Window, Play<T>, Source<T>> nextTryListener,
			Consumer<Play<T>> playListener) {
		this.parent = parent;
		this.playService = playService;
		this.nextTryListener = nextTryListener;
		this.playListener = playListener;
		currentPlayRef = new AtomicReference<>();
	}

	public MenuBar create() {
		RadioMenuItem storePlay = new RadioMenuItem("Speichern");
		storePlay.setOnAction(event -> {
			Optional.ofNullable(currentPlayRef.get()).map(Play::getId).ifPresent(id -> {
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
				Play<T> play = playService.newPlay(cols);
				playListener.accept(currentPlayRef.updateAndGet(unused -> play));
				storePlay.setSelected(false);
				event.consume();
			});
			return newPlayItem;
		}).forEach(newPlay.getItems()::add);

		MenuItem openPlay = new MenuItem("Öffnen");
		openPlay.setOnAction(event -> {
			FileChooser chooser = new FileChooser();
			Optional.ofNullable(chooser.showOpenDialog(parent)).map(File::toPath).ifPresent(file -> {
				Play<T> play = playService.getPlay(file);
				playListener.accept(currentPlayRef.updateAndGet(unused -> play));
				storePlay.setSelected(false);
			});
			event.consume();
		});

		MenuItem nextTry = new MenuItem("Nächster Versuch");
		nextTry.setOnAction(event -> {
			Optional.ofNullable(currentPlayRef.get())
					.ifPresent(play -> Optional.ofNullable(nextTryListener.apply(parent, play)).ifPresent(source -> {
						Play<T> updatedPlay = playService.nextTry(play.getId(), source);
						playListener.accept(currentPlayRef.updateAndGet(unused -> updatedPlay));
					}));
			event.consume();
		});

		MenuItem exit = new MenuItem("Beenden");
		exit.setOnAction(event -> System.exit(0));

		Menu menu = new Menu("Spiel");
		menu.getItems().addAll(newPlay, new SeparatorMenuItem(), openPlay, storePlay, new SeparatorMenuItem(), nextTry,
				new SeparatorMenuItem(), exit);

		// 1st opens a new play
		newPlay.getItems().iterator().next().fire();

		return new MenuBar(menu);
	}

}
