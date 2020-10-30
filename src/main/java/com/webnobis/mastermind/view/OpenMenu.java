package com.webnobis.mastermind.view;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class OpenMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	public OpenMenu(Updateable<Path> updateable, Window parent) {
		Objects.requireNonNull(updateable);

		item = new MenuItem("Öffnen");
		item.setOnAction(event -> {
			Optional.ofNullable(new FileChooser().showOpenDialog(Objects.requireNonNull(parent))).map(File::toPath)
					.ifPresent(updateable::update);
			event.consume();
		});
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

}
