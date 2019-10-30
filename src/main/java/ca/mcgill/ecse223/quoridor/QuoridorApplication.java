package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.GamePage;
import ca.mcgill.ecse223.quoridor.view.SetThinkingTimePage;

public class QuoridorApplication {
	private static Quoridor quoridor;
	private static SetThinkingTimePage timePage;
	private static GamePage game;
	public static void main(String[] args) {
		timePage = new SetThinkingTimePage();
		timePage.setVisible(true);
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
	
	public static void setMainPage() {
		if(timePage.getPageStatus()) {
			timePage.setVisible(false);
			game = new GamePage();
			game.setVisible(true);
		}
	}
	
}
