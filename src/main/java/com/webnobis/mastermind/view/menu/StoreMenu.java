package com.webnobis.mastermind.view.menu;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Menuable;
import com.webnobis.mastermind.view.Updateable;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Store menu
 * 
 * @author steffen
 *
 */
public class StoreMenu implements Updateable<Boolean>, Menuable<MenuItem> {

	private static final String XML_EXT = ".xml";

	private final Label storedFlag;

	private final MenuItem item;

	/**
	 * Updates the updateable with the file to use for storing.
	 * 
	 * @param updateable updateable
	 * @param idSupplier id supplier
	 * @param parent     parent
	 */
	public StoreMenu(Updateable<Path> updateable, Supplier<String> idSupplier, Window parent) {
		Objects.requireNonNull(updateable);
		Objects.requireNonNull(idSupplier);

		storedFlag = new Label();
		storedFlag.setMinWidth(Constants.INFO_LABEL.getIntValue());
		storedFlag.setPrefWidth(Constants.INFO_LABEL.getIntValue());
		item = new MenuItem("Speichern", storedFlag);
		item.setOnAction(event -> {
			FileChooser chooser = new FileChooser();
			chooser.setInitialFileName(Objects.requireNonNull(idSupplier.get()).concat(XML_EXT));
			Optional.ofNullable(chooser.showSaveDialog(Objects.requireNonNull(parent))).map(File::toPath)
					.ifPresent(updateable::update);
			event.consume();
		});
		update(false);
	}

	@Override
	public MenuItem getMenuItem() {
		return item;
	}

	/**
	 * Sets the colored flag before the menu item name: green if false, red if true
	 */
	@Override
	public void update(Boolean storeNeeded) {
		storedFlag.setBackground(createBackground(Boolean.TRUE.equals(storeNeeded) ? Color.RED : Color.GREEN));
	}

	private static Background createBackground(Color color) {
		return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
	}

}
