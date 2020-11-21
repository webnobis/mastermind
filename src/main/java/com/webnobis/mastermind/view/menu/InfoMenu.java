package com.webnobis.mastermind.view.menu;

import com.webnobis.mastermind.view.Menuable;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Info menu, containing the info menu items
 * 
 * @author steffen
 *
 */
public class InfoMenu implements Menuable<Menu> {

	/**
	 * Author
	 */
	public static final String AUTHOR = "Steffen Nobis";

	/**
	 * Shows the informations about the play.
	 * 
	 * @see #AUTHOR
	 * @see SourceMenu#SOURCE
	 */
	@Override
	public Menu getMenuItem() {
		Menu ideaMenu = new Menu("Spiel-Idee", null, new MenuItem("Umgesetzt von ".concat(AUTHOR)),
				new SourceMenu().getMenuItem());
		return new Menu("Info", null, ideaMenu);
	}

}
