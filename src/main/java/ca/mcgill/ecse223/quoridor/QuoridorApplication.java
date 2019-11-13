package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.controller.PawnBehavior;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.GamePage;
import ca.mcgill.ecse223.quoridor.view.SetNamePage;
import ca.mcgill.ecse223.quoridor.view.SetThinkingTimePage;
import ca.mcgill.ecse223.quoridor.view.WelcomePage;

public class QuoridorApplication {
	private static Quoridor quoridor;
	private static SetThinkingTimePage timePage;
	private static GamePage game;
	private static WelcomePage lobby;
	private static SetNamePage namePage;
	public static void main(String[] args) {

	lobby = new WelcomePage();
	lobby.setVisible(true);
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
//	public static PawnBehavior GetPawnBehavior() {
//		Quoridor quoridor = getQuoridor();
//		
//	}
	public static void setMainPage() {
		if(timePage != null) {
			timePage.setVisible(false);
		}
		game = new GamePage();
		game.setVisible(true);
		game.setResizable(false);
	}
	
	public static GamePage getMainPage() {
		return game;
	}
	
	public static void setTimePage() {
		namePage.setVisible(false);
		timePage = new SetThinkingTimePage();
		timePage.setVisible(true);
	}
	
	public static void setWelcomePage() {
		namePage.setVisible(false);
		lobby = new WelcomePage();
		lobby.setVisible(true);
	}
	
	public static void setNamePage() {
		lobby.setVisible(false);
		namePage = new SetNamePage();
		namePage.setVisible(true);
	}
	
}
