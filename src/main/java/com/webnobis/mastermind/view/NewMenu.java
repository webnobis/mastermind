package com.webnobis.mastermind.view;

import java.util.Objects;
import java.util.stream.IntStream;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NewMenu implements Menuable<Menu> {

	private final Menu menu;

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

	public class Range {

		private final int cols;
		private final int rows;

		private Range(int cols, int rows) {
			this.cols = cols;
			this.rows = rows;
		}

		public int getCols() {
			return cols;
		}

		public int getRows() {
			return rows;
		}
	}

}
