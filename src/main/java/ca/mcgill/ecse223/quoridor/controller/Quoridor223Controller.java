package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application;
import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application.Side;
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
	 * Feature 3
	 * @author Andrew Ta
	 * @param thinkingTime
	 * @param playerName
	 * @throws UnsupportedOperationException
	 */
	public void setThinkingTime(Time thinkingTime, String playerName) throws UnsupportedOperationException{
		if(!isRunning()) return; //if the game is not running, return
		
		// get current game
		Game currentGame = Quoridor223Application.getCurrentGame();
		
		Player currentPlayer;
		// get currentPlayer
		if(currentGame.getBlackPlayer().getUser().getName().equals(playerName)) {
			currentPlayer = currentGame.getBlackPlayer();
		}else {
			currentPlayer = currentGame.getWhitePlayer();
		}
		
		// set thinking time of that player
		currentPlayer.setRemainingTime(thinkingTime);
	}
	
	/**
	 * Feature 4
	 * @author Andrew Ta
	 * @throws UnsupportedOperationException
	 */
	public void initializeBoard() throws UnsupportedOperationException {
		if(!isRunning()) return;
		
		// get quoridor object
		Quoridor quoridor = Quoridor223Application.getQuoridor();
		Board board;
		
		// if there is no board, create a new board
		if(quoridor.getBoard() == null) {
			board = new Board(quoridor);
			quoridor.setBoard(board);
		}
	}
	
	//under feature 5
	public static void rotateWall() {
		
	}
	//under feature 6
	public void grabWall()  throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		//check if the it is player's turn if not throw exception
		//check if there is no wall in my hand if not throw exception
		//check if there is wall leftover
		//if Player has more wall
		//move the wall into my hand
		//Remove wall from stock
		//create a wallmoveCandidate in the game model.
		//else if player have no wall
		//Notify the user that they don't have any wall.	
	}
	
	/**
	 * @param side
	 * @throws UnsupportedOperationException
	 */
	public static void moveWall(String side) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		//check if the Game is running if not throw exception
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		//check if newRow and newCol are within the board if not throw exception
		//update the move candidate according to the change.
	}
	
	/**
	 * @throws UnsupportedOperationException
	 */
	public static void dropWall() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		//check if the Game is running if not throw exception
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		//finalize drop by putting the move into the movelist and update the gamePosition.
		//set my hand as empty and switch turn
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
	private static boolean isRunning() {
		Game current = Quoridor223Application.getCurrentGame();
		if(current == null || current.getGameStatus()!=Game.GameStatus.Running)return false;
		return true;
	}
	private static boolean isWallValid(int row, int col) {
		return (row>0 && col>0 && row<9 && col <9);
	}
}
	
	

