package com.webnobis.mastermind.view;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public interface StateToNode {

	static Node toStateNode(String label, Boolean flag) {
		return Optional.ofNullable(flag).<Node>map(selected -> {
			RadioButton state = new RadioButton(label);
			state.setSelected(selected);
			state.setOnAction(event -> state.setSelected(selected));
			return state;
		}).orElseGet(() -> {
			TextField text = new TextField(label);
			text.setEditable(false);
			Tooltip info = new Tooltip();
			info.setText("Bitte Drag-and-Drop zum Kopieren des Inhalts nutzen.");
			text.setTooltip(info);
			text.setOnDragDetected(event -> {
				Dragboard db = text.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putString(text.getText());
				db.setContent(content);
				Clipboard clipboard = Clipboard.getSystemClipboard();
				clipboard.setContent(content);
				event.consume();
			});
			return text;
		});
	}

}
