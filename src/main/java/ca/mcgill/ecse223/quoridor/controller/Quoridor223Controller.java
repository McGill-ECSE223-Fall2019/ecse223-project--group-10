package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;

import ca.mcgill.ecse223.quoridor.model.*;
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
	 * helper method to get current player
	 * @author Andrew Ta
	 * @param playerName
	 * @return
	 */
	public static Player getCurrentPlayer(String playerName) {
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
	 * Feature 3: set total thinking time
	 * @author Andrew Ta
	 * @param thinkingTime
	 * @param playerName
	 * @throws UnsupportedOperationException
	 */
	public void setThinkingTime(Time thinkingTime, String playerName) throws UnsupportedOperationException{
		if(!isRunning()) return; //if the game is not running, return
		
		// get current player
		Player currentPlayer = getCurrentPlayer(playerName);
		
		// set thinking time of that player
		currentPlayer.setRemainingTime(thinkingTime);
	}
	
	/**
	 * get remaining time
	 * @author Andrew Ta
	 * @param playerName
	 * @return Time
	 * @throws UnsupportedOperationException
	 */
	public Time getThinkingTime(String playerName) throws UnsupportedOperationException{
		if(!isRunning()) return null; //if the game is not running, return
		
		// get current player
		Player currentPlayer = getCurrentPlayer(playerName);
		
		return currentPlayer.getRemainingTime();	
	}
	
	/**
	 * Feature 4: initialize board
	 * @author Andrew Ta
	 * @throws UnsupportedOperationException
	 */
	public void initializeBoard() throws UnsupportedOperationException {
		if(!isRunning()) return;
		
		// get quoridor object
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board;
		
		// if there is no board, create a new board
		if(quoridor.hasBoard()) {
			board = new Board(quoridor);
			quoridor.setBoard(board);
		}
	}
	
	/**
	 * return board
	 * @author Andrew Ta
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public Board getBoard() throws UnsupportedOperationException{
		if(!isRunning()) return null;
		
		// get quoridor object
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		
		if(quoridor.hasBoard()) {
			return quoridor.getBoard();
		}else {
			return null;
		}
	}
	
	//under feature 5
	public static void rotateWall() throws UnsupportedOperationException {
		//check if the Game is running. If not, thrown an exception.
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if(!isRunning()) throw new UnsupportedOperationException("Game is not running");
		
	
	}
		//check if it is the player's turn. If not, thrown an excpetion. 
		//check if there is no wall in my hand. If no wall, thrown an excpetion. 
		//if there is a wall in my hand
			//rotate walls with the "A" and "D" keys.
			//get coordinates for the wall position
			//
		//else if
		//Notify player there is no wall in hand.

	
	//under feature 6
	public static void grabWall(Game game)  throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if(!isRunning()) throw new UnsupportedOperationException("Game is not running");
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
	}

	/**
	 * Perform a move operation on a currently selected wall
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
		int newCol = candidate.getTargetTile().getRow()+ (side.equalsIgnoreCase("left")?-1:side.equalsIgnoreCase("right")?1:0);
		if(!isWallPositionValid(newRow,newCol))throw new InvalidOperationException("Move invalid");
		//update the move candidate according to the change.
		candidate.setTargetTile(getTile(newRow,newCol));
	}
	
	/**
	 * Perform a drop wall Operation that drop the currently held wall
	 * @author Le-Li Mao
	 * @throws UnsupportedOperationException
	 */
	public static void dropWall() throws GameNotRunningException, InvalidOperationException {
		//check if the Game is running if not throw exception
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if the it is player's turn if not throw exception
		//HOW to do this??
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new InvalidOperationException("No wall Selected");
		//finalize drop by putting the move into the movelist and update the gamePosition TODO.
		curGame.addMove(curGame.getWallMoveCandidate());
		curGame.setWallMoveCandidate(null);

		//set my hand as empty and switch turn TODO
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
}
	
	

