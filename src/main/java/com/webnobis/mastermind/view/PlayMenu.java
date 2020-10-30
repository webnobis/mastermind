package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.service.PlayService;

import javafx.animation.PauseTransition;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Window;
import javafx.util.Duration;

public class PlayMenu implements Menuable<Menu> {

	private final Window parent;

	private final PlayService<ColorType> playService;

	private final Updateable<Play<ColorType>> updateable;

	private final AtomicReference<Play<ColorType>> playRef;

	public PlayMenu(Window parent, PlayService<ColorType> playService, Updateable<Play<ColorType>> updateable) {
		this.parent = Objects.requireNonNull(parent);
		this.playService = Objects.requireNonNull(playService);
		this.updateable = Objects.requireNonNull(updateable);
		playRef = new AtomicReference<>();
	}

	@Override
	public Menu getMenuItem() {
		AtomicBoolean storeNeededRef = new AtomicBoolean();

		NewMenu newMenu = new NewMenu(range -> {
			Play<ColorType> play = playService.newPlay(range.getCols(), range.getRows());
			playRef.set(play);
			updateable.update(play);
			storeNeededRef.set(true);
		});
		newMenu.getMenuItem().getItems().iterator().next().fire();

		OpenMenu openMenu = new OpenMenu(path -> {
			Play<ColorType> play = playService.getPlay(path);
			playRef.set(play);
			updateable.update(play);
		}, parent);

		StoreMenu storeMenu = new StoreMenu(path -> {
			storeNeededRef.set(!playService.storePlay(playRef.get().getId(), path));
		}, () -> playRef.get().getId(), parent);

		NextTryMenu nextTryMenu = new NextTryMenu(source -> {
			Play<ColorType> play = playService.nextTry(playRef.get().getId(), source);
			playRef.set(play);
			updateable.update(play);
			storeNeededRef.set(true);
		}, () -> playRef.get().getCols());

		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(event -> {
			storeMenu.update(storeNeededRef.get());
			pause.playFromStart(); // loop again
		});
		pause.play();

		Menu menu = new Menu("Spiel");
		menu.getItems().addAll(newMenu.getMenuItem(), openMenu.getMenuItem(), storeMenu.getMenuItem(),
				new SeparatorMenuItem(), nextTryMenu.getMenuItem(), new SeparatorMenuItem(),
				new ExitMenu().getMenuItem());
		return menu;
	}

}
