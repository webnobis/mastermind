package com.webnobis.mastermind.model.transformer;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.xml.XmlGameConfig;

public abstract class GameConfigTransformer {

	private GameConfigTransformer() {
	}
	
	public static GameConfig transform(String xml) {
		return JAXB.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), XmlGameConfig.class);
	}

}
