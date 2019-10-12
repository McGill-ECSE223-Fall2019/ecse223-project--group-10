package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;

import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

import java.sql.Time;
import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {
	
	//under feature 1
	public static void createGame() {
	
	}
	//under feature 2
	public static void selectUser(String playerName1, String playerName2) {
		
	}
	//under feature 2
	public static void createUser(String name) {
		
	}
	
	/**
	 * helper method to get player by name
	 * @author Andrew Ta
	 * @param playerName
	 */
	public static Player getPlayerByName(String playerName) {
		// get current game
		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player currentPlayer;
		// get currentPlayer
		if(currentGame.getBlackPlayer().getUser().getName().equals(playerName)) {
			currentPlayer = currentGame.getBlackPlayer();
		}else {
			currentPlayer = currentGame.getWhitePlayer();
		}
		
		return currentPlayer;
	}
	
	/**
	 * Feature 3: Set Total Thinking Time
	 * @author Andrew Ta
	 * @param thinkingTime
	 * @param playerName
	 * @throws UnsupportedOperationException
	 */
	public static void setThinkingTime(Time thinkingTime, String playerName) throws UnsupportedOperationException{
		// get current player
		Player currentPlayer = getPlayerByName(playerName);
		
		// set thinking time of that player
		currentPlayer.setRemainingTime(thinkingTime);
	}
	
	/**
	 * Get Remaining Time of A Player
	 * @author Andrew Ta
	 * @param playerName
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws GameNotRunningException
	 */
	public static Time getRemainingTime(String playerName) throws UnsupportedOperationException, GameNotRunningException{
		if(!isRunning()) throw new GameNotRunningException("Game is not running."); //if the game is not running, return
		
		// get current player
		Player currentPlayer = getPlayerByName(playerName);
		
		return currentPlayer.getRemainingTime();	
	}
	
	/**
	 * Feature 4: Initialize Board
	 * @author Andrew Ta
	 * @throws UnsupportedOperationException
	 */
	public static void initializeBoard() throws UnsupportedOperationException{
		// get quoridor object
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		
		// add tiles
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
		
		// add user and create players
		User user1 = quoridor.addUser("user1");
		User user2 = quoridor.addUser("user2");
		Player player1 = new Player(new Time(100), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(100), user2, 1, Direction.Horizontal);
		
		// create walls
		for (int i = 0; i < 10; i++) {
			new Wall(0 * 10 + i, player1);
		}
		for (int i = 0; i < 10; i++) {
			new Wall(1 * 10 + i, player2);
		}
		
		// create players' positions
		Tile player1StartPos = quoridor.getBoard().getTile(44);
		Tile player2StartPos = quoridor.getBoard().getTile(36);
		
		// create a game
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, player1, player2, quoridor);
		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);
		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, player1, game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}

	
	//under feature 5
	/**
	 * Perform a rotate wall operation on the wall od the respective player
	 * @author Enan Ashaduzzaman
	 * @throws UnsupportedOperationException
	 */
	public static void rotateWall() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
		//check if the Game is running. If not, thrown an exception.
		//Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//if(!isRunning()) throw new UnsupportedOperationException("Game is not running");
		//check if it is the player's turn. If not, thrown an excpetion. 
		//check if there is no wall in my hand. If no wall, thrown an excpetion. 
		//if there is a wall in my hand
			//rotate walls with the "R" keys.
			//get coordinates for the wall position
			//
		//else if
		//Notify player there is no wall in hand.
	}
	
	
	
	//under feature 6
	/**
	 * Perform a grab wall operation on the stock of the respective player
	 * @author Enan Ashaduzzaman
	 * @throws UnsupportedOperationException
	 */
	public static void grabWall() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		//check if the Game is running if not throw exception
		//Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//if(!isRunning()) throw new UnsupportedOperationException("Game is not running");
		//check if the it is player's turn if not throw exception
		//check if there is no wall in my hand if not throw exception
		//if(curGame.getWallMoveCandidate()==null)
		//check if there is wall leftover
		//if Player has more wall
			//move the wall into player's hand
			//Remove one wall from stock
			//create a wallmoveCandidate in the game model.
		//move the wall into my hand
		//Remove wall from stock
		//create a wallmoveCandidate in the game model.

		//else if player have no wall
		//Notify the user that they don't have any wall.	
		
		//get quoridor, game
		//see current position and current game
		//check if current player has a wall in hand
		//if there is a wall in stock, create wall move candidate
	}

	/**
	 * Perform a move operation on a currently selected wall
	 * Gherkin Feature: MoveWall.feature
	 * @author Le-Li Mao
	 * @param side
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void moveWall(String side) throws GameNotRunningException, InvalidOperationException {
		//check if the Game is running if not throw exception
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new InvalidOperationException("No wall Selected");
		WallMove candidate =  curGame.getWallMoveCandidate();
		//check if newRow and newCol are within the board if not throw exception
		int newRow = candidate.getTargetTile().getRow()+ (side.equalsIgnoreCase("up")?-1:side.equalsIgnoreCase("down")?1:0);
		int newCol = candidate.getTargetTile().getColumn()+ (side.equalsIgnoreCase("left")?-1:side.equalsIgnoreCase("right")?1:0);
		if(!isWallPositionValid(newRow,newCol))throw new InvalidOperationException("Move invalid");
		//update the move candidate according to the change.
		candidate.setTargetTile(getTile(newRow,newCol));
	}
	
	/**
	 * Perform a drop wall Operation that drop the currently held wall
	 * Gerkin Feature: DropWall.feature
	 * @author Le-Li Mao
	 */
	public static Wall dropWall(){
		//check if the Game is running if not throw exception
		if(!isRunning())return null;
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)return null;
		//finalize drop by putting the move into the movelist.
		Wall wallToDrop = curGame.getWallMoveCandidate().getWallPlaced();
		curGame.addMove(curGame.getWallMoveCandidate());
		curGame.setWallMoveCandidate(null);
		if(isWhitePlayer())curGame.getCurrentPosition().addWhiteWallsOnBoard(wallToDrop);
		else curGame.getCurrentPosition().addBlackWallsOnBoard(wallToDrop);
		Player next = curGame.getCurrentPosition().getPlayerToMove().getNextPlayer();
		curGame.getCurrentPosition().setPlayerToMove(next);
		if(!isPlacementValid())return null;
		//set my hand as empty, update the gamePosition and switch turn yet to be implemented
		return wallToDrop;
	}
	
	//under feature 9
	public static void savePosition() {
		
	}
	//under feature 10
	public static void loadPosition() {
	
	}
	
	//under feature 11
	public static void validatePosition(Tile tile) {
		// need parameter to know whether it is a pawn or a wall ?
		
		// validate pawn position
		// validate wall position
	}
	//under feature 12
	public static void UpdateBoard() {
		
	}

	/**
	 * @author Le-Li Mao
	 * @return gameIsRunning
	 */
	private static boolean isRunning() {
		Game current = QuoridorApplication.getQuoridor().getCurrentGame();
		if(current == null || current.getGameStatus()!=Game.GameStatus.Running)return false;
		return true;
	}

	/**
	 * Check if wall is valid
	 * @author Le-Li Mao
	 * @param row
	 * @param col
	 * @return
	 */
	private static boolean isWallPositionValid(int row, int col) {

		return (row>0 && col>0 && row<9 && col <9);
	}
	private static Tile getTile(int row, int col){
		Board board = QuoridorApplication.getQuoridor().getBoard();
		return board.getTile((row-1)*9+(col-1));
	}
	private static boolean isWhitePlayer() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getPlayerToMove().equals(curGame.getWhitePlayer());
	}
	private static boolean isPlacementValid() {
		//will implement this later
		return true;
	}
	private static Move getPreviousMove() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if(curGame.numberOfMoves()==0)return null;
		return curGame.getMove(0);
	}
}
	
	

