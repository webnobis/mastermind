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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XmlGameConfig other = (XmlGameConfig) obj;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlGameConfig [min=" + min + ", max=" + max + ", size=" + size + "]";
	}

}
