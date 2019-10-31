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
import ca.mcgill.ecse223.quoridor.view.WelcomePage;

public class QuoridorApplication {
	private static Quoridor quoridor;
	public static void main(String[] args) {
		createWall();
		WelcomePage welcomePage = new WelcomePage();
		welcomePage.setVisible(true);
		//GamePage mainPage = new GamePage();
		//mainPage.setVisible(true);
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
	private static void createWall() {
		quoridor = getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
		User user1 = quoridor.addUser("userName1");
		User user2 = quoridor.addUser("userName2");
		int thinkingTime = 180;
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);
		Player[] players = { player1, player2 };
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j+1, players[i]);
			}
		}
		
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(player1);
		game.setBlackPlayer(player2);
		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);
		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, player1, game);
		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j+1);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10+1);
			gamePosition.addBlackWallsInStock(wall);
		}
		game.setCurrentPosition(gamePosition);
		int row = 1;
		int col = 1;
		Direction wallDirection = Direction.Vertical;
		Player player = game.getCurrentPosition().getPlayerToMove();
		Wall toBeUsed = player.equals(game.getWhitePlayer())?game.getCurrentPosition().getWhiteWallsInStock(0):game.getCurrentPosition().getBlackWallsInStock(0);
		boolean test =game.setWallMoveCandidate(new WallMove(1, 1, player, board.getTile((row-1)*9+(col-1)), game, wallDirection, toBeUsed));
	}
	
	
}
