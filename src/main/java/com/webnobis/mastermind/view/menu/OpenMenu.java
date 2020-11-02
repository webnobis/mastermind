package com.webnobis.mastermind.view.menu;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.view.Menuable;
import com.webnobis.mastermind.view.Updateable;

import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Open menu
 * 
 * @author steffen
 *
 */
public class OpenMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	/**
	 * Updates the updateable with the opened file.
	 * 
	 * @param updateable updateable
	 * @param parent     parent
	 * @see FileChooser#showOpenDialog(Window)
	 */
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
