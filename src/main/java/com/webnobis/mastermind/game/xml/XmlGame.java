package com.webnobis.mastermind.game.xml;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.game.Game;
import com.webnobis.mastermind.model.OnlyForXmlBinding;

@Deprecated
@XmlRootElement(name = "game")
public class XmlGame<E> {

	@XmlAttribute
	private final String id;

	@XmlAttribute
	private final String type;

	@XmlAttribute
	private final boolean finish;

	@XmlElementWrapper(name = "expectation", required = false)
	@XmlElement(required = false)
	private final List<E> expected;

	@XmlElementWrapper(name = "tries")
	@XmlElement(name = "try", required = false)
	private final List<XmlValidation<E>> tries;

	private XmlGame(String id, boolean finish, List<XmlValidation<E>> tries, List<E> expected) {
		this.id = id;
		this.type = Stream.concat(tries.stream().flatMap(v -> v.getTest().stream()),
				Optional.ofNullable(expected).orElse(Collections.emptyList()).stream())
				.findFirst()
				.map(e -> e.getClass().getName())
				.orElse("unknown");
		this.finish = finish;
		this.expected = expected;
		this.tries = tries;
	}

	@OnlyForXmlBinding
	XmlGame() {
		this(null, false, new ArrayList<>(), new ArrayList<>());
	}

	public static <E> XmlGame<E> from(Game<E> game) {
		return new XmlGame<>(game.getId(), game.isFinish(), IntStream.range(0, game.tries())
				.mapToObj(i -> new XmlValidation<>(i, game.getTries().get(i), game.getResults().get(i)))
				.sorted(XmlValidation.tryCountComparator)
				.collect(Collectors.toList()), (game.isFinish()) ? game.getVerifier().expected() : null);
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
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

	@Override
	public String toString() {
		try (CharArrayWriter out = new CharArrayWriter()) {
			JAXB.marshal(this, out);
			out.flush();
			return out.toString();
		}
	}

}
