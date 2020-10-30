package com.webnobis.mastermind.view;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import com.webnobis.mastermind.model.Play;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class StateNode implements Updateable<Play<ColorType>>, Paneable<Pane> {

	private final Label id;

	private final Label configuration;

	private final CheckBox finish;

	private final CheckBox solved;

	private final CheckBox unlimited;

	private final GridPane pane;

	public StateNode() {
		id = new Label();
		configuration = new Label();
		finish = new CheckBox("beendet");
		solved = new CheckBox("gelöst");
		unlimited = new CheckBox("unbegrenzt");

		pane = new GridPane();
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

}
