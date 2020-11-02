package com.webnobis.mastermind.view.menu;

import java.util.Objects;
import java.util.function.IntSupplier;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Source;
import com.webnobis.mastermind.view.Menuable;
import com.webnobis.mastermind.view.NextTryDialog;
import com.webnobis.mastermind.view.Updateable;

import javafx.scene.control.MenuItem;

/**
 * Next try menu
 * 
 * @author steffen
 *
 */
public class NextTryMenu implements Menuable<MenuItem> {

	private final MenuItem item;

	/**
	 * Updates the updateable with the next try dialog resulting source.
	 * 
	 * @param updateable   updateable
	 * @param colsSupplier cols supplier
	 * @see NextTryDialog#NextTryDialog(int, Updateable)
	 */
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
