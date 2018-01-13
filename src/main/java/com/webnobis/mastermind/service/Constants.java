package com.webnobis.mastermind.service;

public interface Constants {

	public static final String CONTENT_TYPE = "application/xml";

	public static final String ID_TOKEN = "id";

	public static final String ALIVE_PATH = "alive";

	public static final String GAME_BUILDER_PATH = "new";

	public static final String GAME_PATH = ":id";

	public static final String TRY_PATH = ":id/try";

	public static final String RESIGN_PATH = ":id/end";

	public static final int REDIRECT_CODE = 303;

	public static final int NOT_FOUND_CODE = 404;

}
