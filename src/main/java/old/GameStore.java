package old;

import com.webnobis.mastermind.game.Game;

public interface GameStore {
	
	<E> String store(Game<E> game);
	
	<E> Game<E> get(String id);
	
	void remove(String id);
	
	static GameStore get() {
		return GlobalGameStore.INSTANCE;
	}

}
