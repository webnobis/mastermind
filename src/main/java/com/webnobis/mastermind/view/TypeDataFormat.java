package com.webnobis.mastermind.view;

import java.util.Optional;

import javafx.scene.input.DataFormat;

public class TypeDataFormat<T> extends DataFormat {

	public TypeDataFormat(Class<T> type) {
		super(Optional.ofNullable(type).map(Class::getName).orElse(TypeDataFormat.class.getName()));
	}

}
