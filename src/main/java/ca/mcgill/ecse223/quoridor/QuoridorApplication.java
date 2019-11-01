package ca.mcgill.ecse223.quoridor;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.view.BoardComponent;
import ca.mcgill.ecse223.quoridor.view.GamePage;
import ca.mcgill.ecse223.quoridor.view.SetNamePage;
import ca.mcgill.ecse223.quoridor.view.WelcomePage;
import ca.mcgill.ecse223.quoridor.view.SetThinkingTimePage;

public class QuoridorApplication {
	private static Quoridor quoridor;
	private static SetThinkingTimePage timePage;
	private static GamePage game;
	private static WelcomePage lobby;
	private static SetNamePage namePage;
	public static void main(String[] args) {
		lobby = new WelcomePage();
		lobby.setVisible(true);
		//GamePage mainPage = new GamePage();
		//mainPage.setVisible(true);
//		timePage = new SetThinkingTimePage();
//		timePage.setVisible(true);
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
	
	public static void setMainPage() {
		timePage.setVisible(false);
		game = new GamePage();
		game.setVisible(true);
	}
	public static void setTimePage() {
		namePage.setVisible(false);
		timePage = new SetThinkingTimePage();
		timePage.setVisible(true);
	}
	public static void setNamePage() {
		lobby.setVisible(false);
		namePage = new SetNamePage();
		namePage.setVisible(true);
	}
	
}
