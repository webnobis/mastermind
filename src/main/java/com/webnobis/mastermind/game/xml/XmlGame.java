package com.webnobis.mastermind.game.xml;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.game.Game;

@XmlRootElement(name = "game")
public class XmlGame<E> {

	@XmlAttribute
	private final boolean easyVerify;

	@XmlAttribute
	private final boolean finish;

	@XmlElementWrapper(name = "expectation", required = false)
	@XmlElement(required = false)
	private final List<E> expected;

	@XmlElementWrapper(name = "tries")
	@XmlElement(name="try", required = false)
	private final List<XmlValidation<E>> tries;

	public XmlGame(boolean easyVerify, boolean finish, List<XmlValidation<E>> tries, List<E> expected) {
		this.easyVerify = easyVerify;
		this.finish = finish;
		this.expected = expected;
		this.tries = tries;
	}

	XmlGame() {
		this(false, false, Collections.emptyList(), Collections.emptyList());
	}

	public static <E> XmlGame<E> from(Game<E> game) {
		return new XmlGame<>(game.isEasyVerify(), game.isFinish(), IntStream.range(0, game.tries())
				.mapToObj(i -> new XmlValidation<>(i, game.getTries().get(i), game.getResults().get(i)))
				.sorted(XmlValidation.tryCountComparator)
				.collect(Collectors.toList()), (game.isFinish()) ? game.getVerifier().expected() : null);
	}

	public boolean isEasyVerify() {
		return easyVerify;
	}

	public boolean isFinish() {
		return finish;
	}

	public List<E> getExpected() {
		return expected;
	}

	public List<XmlValidation<E>> getTries() {
		return tries;
	}
}
