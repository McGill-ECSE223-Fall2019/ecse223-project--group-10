package ca.mcgill.ecse223.quoridor.QuoridorApplication;

import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class Quoridor223Application {
	private static Quoridor quoridor;
	private static Game currentGame;
	private static GamePosition currentGamePosition;

	public enum Side {
		up, down, left, right
	};

	public static void main(String[] args) {

	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static GamePosition getCurrentGamePosition() {
		return currentGamePosition;
	}

	public static void loadNewGamePosition(GamePosition newGamePosition) {
		currentGamePosition = newGamePosition;
		return;
	}
}
