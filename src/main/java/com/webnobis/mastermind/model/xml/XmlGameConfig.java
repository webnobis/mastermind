package com.webnobis.mastermind.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.GameConfig;

@XmlRootElement(name = "config")
public class XmlGameConfig implements GameConfig {

	@XmlAttribute
	private final int min;

	@XmlAttribute
	private final int max;

	@XmlAttribute
	private final int size;

	public XmlGameConfig(int min, int max, int size) {
		this.min = min;
		this.max = max;
		this.size = size;
	}

	@OnlyForXmlBinding
	XmlGameConfig() {
		this(0, 0, 0);
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public int getSize() {
		return size;
	}

}
