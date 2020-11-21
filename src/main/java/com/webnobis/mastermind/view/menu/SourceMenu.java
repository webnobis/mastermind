package com.webnobis.mastermind.view.menu;

import com.webnobis.mastermind.view.Menuable;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Window;

/**
 * Source reference menu
 * 
 * @author steffen
 *
 */
public class SourceMenu implements Menuable<MenuItem> {

	static final String SOURCE_IMAGE = ClassLoader.getSystemResource("mastermindspielwikipedia.png").toString();

	/**
	 * Source
	 */
	public static final String SOURCE = "https://de.wikipedia.org/wiki/Mastermind_(Spiel)";

	/**
	 * Gets an screen shot of the inspiration source web page {@link #SOURCE}
	 */
	@Override
	public MenuItem getMenuItem() {
		MenuItem sourceMenu = new MenuItem("Quelle (Wikipedia)");
		sourceMenu.setOnAction(event -> {
			Dialog<Void> dialog = new Dialog<>();
			dialog.initModality(Modality.NONE);
			dialog.setTitle(SOURCE);

			Rectangle2D size = Screen.getPrimary().getBounds();
			ImageView imgView = new ImageView(SOURCE_IMAGE);

			DialogPane dialogPane = dialog.getDialogPane();
			int width = Math.min((int) (imgView.getImage().getWidth() * 1.05), (int) size.getWidth());
			int height = Math.min((int) imgView.getImage().getHeight(), (int) (size.getHeight() * 0.8));
			dialogPane.setPrefSize(width, height);
			dialogPane.setContent(new ScrollPane(imgView));
			dialogPane.getButtonTypes().add(ButtonType.CLOSE);

			Window window = dialogPane.getScene().getWindow();
			window.sizeToScene();
			window.centerOnScreen();

			dialog.showAndWait();
			event.consume();
		});
		return sourceMenu;
	}

}
