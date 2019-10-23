package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.BoardComponent;
import ca.mcgill.ecse223.quoridor.view.GamePage;

public class QuoridorApplication {
	private static Quoridor quoridor;
	public static void main(String[] args) {
		GamePage mainPage = new GamePage();
		mainPage.setVisible(true);
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
	
}
