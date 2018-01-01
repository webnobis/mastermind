package com.webnobis.mastermind.model.transformer;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.Try;
import com.webnobis.mastermind.model.xml.XmlTry;

public abstract class TryTransformer {

	private TryTransformer() {
	}

	public static Try transform(String xml) {
		return JAXB.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), XmlTry.class);
	}

}
