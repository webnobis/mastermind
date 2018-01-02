package com.webnobis.mastermind.model.xml;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameConfig;
import com.webnobis.mastermind.model.Result;
import com.webnobis.mastermind.model.TryWithAssessment;

@XmlRootElement
public class XmlGame implements Game {

	@XmlElement(name = "config", type = GameConfig.class)
	private final GameConfig config;

	@XmlElements(@XmlElement(type = XmlTryWithAssessment.class))
	private final List<TryWithAssessment> tries;

	@XmlAttribute
	private final boolean finish;

	public XmlGame(GameConfig config, List<TryWithAssessment> tries) {
		this.config = config;
		this.tries = Optional.ofNullable(tries)
				.map(Collections::unmodifiableList)
				.orElse(Collections.emptyList());

		this.finish = this.tries.stream()
				.filter(assessedTry -> assessedTry.getAssessment().getAssessments().size() >= assessedTry.getTry().getIdeas().size())
				.anyMatch(assessedTry -> assessedTry.getAssessment().getAssessments().stream().allMatch(Result.CORRECT_PLACE::equals));
	}

	@OnlyForXmlBinding
	XmlGame() {
		this(null, null);
	}

	@Override
	public GameConfig getConfig() {
		return config;
	}

	@Override
	public List<TryWithAssessment> getTries() {
		return tries;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
