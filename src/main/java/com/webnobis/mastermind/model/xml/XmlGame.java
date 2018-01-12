package com.webnobis.mastermind.model.xml;

import java.util.ArrayList;
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

@XmlRootElement(name = "game")
public class XmlGame implements Game {

	@XmlElement(type = XmlGameConfig.class)
	private final GameConfig config;

	@XmlElements(@XmlElement(type = XmlTryWithAssessment.class))
	private final List<TryWithAssessment> tries = new ArrayList<>();

	@XmlAttribute
	private final boolean finish;

	public XmlGame(GameConfig config, List<TryWithAssessment> tries) {
		this.config = config;
		Optional.ofNullable(tries)
				.ifPresent(this.tries::addAll);

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		result = prime * result + (finish ? 1231 : 1237);
		result = prime * result + ((tries == null) ? 0 : tries.hashCode());
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
		XmlGame other = (XmlGame) obj;
		if (config == null) {
			if (other.config != null)
				return false;
		} else if (!config.equals(other.config))
			return false;
		if (finish != other.finish)
			return false;
		if (tries == null) {
			if (other.tries != null)
				return false;
		} else if (!tries.equals(other.tries))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XmlGame [config=" + config + ", tries=" + tries + ", finish=" + finish + "]";
	}

}
