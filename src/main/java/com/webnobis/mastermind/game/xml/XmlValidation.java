package com.webnobis.mastermind.game.xml;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.webnobis.mastermind.game.Result;

public class XmlValidation<E> {
	
	public static final Comparator<XmlValidation<?>> tryCountComparator = new Comparator<XmlValidation<?>>() {

		@Override
		public int compare(XmlValidation<?> v1, XmlValidation<?> v2) {
			return (v1.tryCount < v2.tryCount)? -1: (v1.tryCount > v2.tryCount)? 1: 0;
		}
	};
	
	@XmlAttribute
	private final int tryCount;

	@XmlElement(name="test")
	private final List<E> test;
	
	@XmlElement(name="result", required = false)
	private final List<Result> result;

	public XmlValidation(int tryCount, List<E> test, List<Result> result) {
		this.tryCount = tryCount;
		this.test = test;
		this.result = result;
	}

	XmlValidation() {
		this(0, Collections.emptyList(), Collections.emptyList());
	}

	public int getTryCount() {
		return tryCount;
	}

	public List<E> getTest() {
		return test;
	}

	public List<Result> getResult() {
		return result;
	}
}
