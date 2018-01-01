package com.webnobis.mastermind.model.transformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXB;

import com.webnobis.mastermind.model.Game;
import com.webnobis.mastermind.model.GameWithSolution;
import com.webnobis.mastermind.model.Solution;

public abstract class GameAndSolutionTransformer {

	private GameAndSolutionTransformer() {
	}

	public String transform(Game game) {
		return toXml(game);
	}

	public String transform(GameWithSolution gameWithSolution) {
		return toXml(gameWithSolution);
	}

	public String transform(Solution solution) {
		return toXml(solution);
	}

	private String toXml(Object object) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			JAXB.marshal(object, out);
			return out.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
