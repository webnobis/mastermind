package com.webnobis.mastermind.view.menu;

import java.util.Objects;
import java.util.stream.IntStream;

import com.webnobis.mastermind.view.Menuable;
import com.webnobis.mastermind.view.Updateable;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * New menu with some variants of sub menus
 * 
 * @author steffen
 */
public class NewMenu implements Menuable<Menu> {

	private final Menu menu;

	/**
	 * Updates the updateable with the used range
	 * 
	 * @param updateable updateable
	 */
	public NewMenu(Updateable<NewMenu.Range> updateable) {
		Objects.requireNonNull(updateable);

		menu = new Menu("Neu");
		IntStream.rangeClosed(4, 6).mapToObj(cols -> {
			MenuItem item = new MenuItem(cols + " x unbegrenzt");
			item.setOnAction(event -> {
				updateable.update(new Range(cols, -1));
				event.consume();
			});
			return item;
		}).forEach(menu.getItems()::add);
	}

	@Override
	public Menu getMenuItem() {
		return menu;
	}

	/**
	 * Holder of cols and rows
	 * 
	 * @author steffen
	 *
	 */
	public class Range {

		private final int cols;
		private final int rows;

		private Range(int cols, int rows) {
			this.cols = cols;
			this.rows = rows;
		}

		/**
		 * 
		 * @return cols
		 */
		public int getCols() {
			return cols;
		}

		/**
		 * 
		 * @return rows
		 */
		public int getRows() {
			return rows;
		}
	}

}
