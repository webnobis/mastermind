package com.webnobis.mastermind.view;

import java.util.Objects;

import javafx.scene.control.MenuItem;

public class QuitMenu implements Menuable<MenuItem> {

	private final MenuItem item;

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
