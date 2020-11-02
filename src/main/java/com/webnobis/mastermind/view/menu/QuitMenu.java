package com.webnobis.mastermind.view.menu;

import java.util.Objects;

import com.webnobis.mastermind.view.Menuable;
import com.webnobis.mastermind.view.Updateable;

import javafx.scene.control.MenuItem;

/**
 * Quit menu
 * 
 * @author steffen
 *
 */
public class QuitMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	/**
	 * Calls the updateable, to quit the current play.
	 * 
	 * @param updateable updateable
	 */
	public QuitMenu(Updateable<Void> updateable) {
		Objects.requireNonNull(updateable);

		item = new MenuItem("Beenden");
		item.setOnAction(event -> {
			updateable.update(null);
			event.consume();
		});
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

}
