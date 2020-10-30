package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.function.IntSupplier;

import com.webnobis.mastermind.model.Source;

import javafx.scene.control.MenuItem;

public class NextTryMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	public NextTryMenu(Updateable<Source<ColorType>> updateable, IntSupplier colsSupplier) {
		Objects.requireNonNull(updateable);
		Objects.requireNonNull(colsSupplier);

		item = new MenuItem("Nächster Versuch");
		item.setOnAction(event -> {
			new NextTryDialog(Objects.requireNonNull(colsSupplier.getAsInt()), updateable);
			event.consume();
		});
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

}
