package com.webnobis.mastermind.view.node;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.model.ColorType;
import com.webnobis.mastermind.model.Play;
import com.webnobis.mastermind.view.Constants;
import com.webnobis.mastermind.view.Paneable;
import com.webnobis.mastermind.view.Updateable;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * State node
 * 
 * @author steffen
 *
 */
public class StateNode implements Updateable<Play<ColorType>>, Paneable<Pane> {

	private static final int TEXTFIELD_PREF_COLS = 22;

	private final TextField id;

	private final TextField configuration;

	private final CheckBox finish;

	private final CheckBox solved;

	private final CheckBox unlimited;

	private final GridPane pane;

	/**
	 * State area of all state informations of the play
	 */
	public StateNode() {
		id = new TextField();
		id.setPrefColumnCount(TEXTFIELD_PREF_COLS);
		id.setBackground(createBackground());
		id.setEditable(false);
		configuration = new TextField();
		configuration.setPrefColumnCount(TEXTFIELD_PREF_COLS);
		configuration.setBackground(createBackground());
		configuration.setEditable(false);
		finish = new CheckBox("beendet");
		finish.setOnAction(event -> {
			finish.setSelected(!finish.isSelected());
			event.consume();
		});
		solved = new CheckBox("gelöst");
		solved.setOnAction(event -> {
			solved.setSelected(!solved.isSelected());
			event.consume();
		});
		unlimited = new CheckBox("unbegrenzt");
		unlimited.setOnAction(event -> {
			unlimited.setSelected(!unlimited.isSelected());
			event.consume();
		});

		pane = new GridPane();
		pane.setVgap(Constants.PADDING.getIntValue());
		pane.setHgap(Constants.PADDING.getIntValue());
		pane.setPadding(new Insets(Constants.PADDING.getIntValue()));
		pane.add(new Label("Id:"), 0, 0, 3, 1);
		pane.add(id, 0, 1, 3, 1);
		pane.add(new Label("Konfigruration:"), 0, 2, 3, 1);
		pane.add(configuration, 0, 3, 3, 1);
		pane.add(new Label("Status:"), 0, 4, 3, 1);
		pane.add(finish, 0, 5);
		pane.add(solved, 1, 5);
		pane.add(unlimited, 2, 5);
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void update(Play<ColorType> play) {
		Objects.requireNonNull(play);
		id.setText(play.getId());
		configuration.setText(MessageFormat.format("{0} Versuche mit {1} Farbfelder{2}",
				play.isUnlimited() ? "Unbegrenzte" : play.getRows(), play.getCols(), play.getCols() > 1 ? "n" : ""));
		finish.setSelected(play.isFinish());
		solved.setSelected(play.isSolved());
		unlimited.setSelected(play.isUnlimited());
		Optional.ofNullable(play.getSource()).ifPresent(source -> {
			pane.add(new Label("Lösung:"), 0, 6, 3, 1);
			pane.add(new SourceNode(source).getPane(), 0, 7, 3, 1);
		});
	}

	private static Background createBackground() {
		return new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
	}

}
