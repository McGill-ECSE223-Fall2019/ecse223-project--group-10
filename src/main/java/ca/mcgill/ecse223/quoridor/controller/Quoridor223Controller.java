package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application;
import ca.mcgill.ecse223.quoridor.model.*;
import java.sql.Time;
import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {
	//under feature 1
	public void createGame() {
	
	}
	//under feature 2
	public void selectUser(String playerName1, String playerName2) {
		
	}
	//under feature 2
	public void createUser(String name) {
		
	}
	//under feature 3
	public void setThinkingTime(Time thinkingTime, String playerName) {
		
	}
	//under feature 4
	public void initializeBoard() {
		
	}
	//under feature 5
	public void rotateWall() {
		//check if the Game is running. If not, thrown an exception.
		//check if it is the player's turn. If not, thrown an excpetion. 
		//check if there is no wall in my hand. If no wall, thrown an excpetion. 
		//if there is a wall in my hand
			//rotate walls with the "A", "S", "W", "D" keys.
		//else if
			//Notify player there is no wall in hand.
	}
	//under feature 6
	public void grabWall(Player player)  throws UnsupportedOperationException{
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
	 * @param player
	 * @param Row
	 * @param Col
	 */
	public void moveWall(int Row, int Col, int side) throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		//check if newRow and newCol are within the board if not throw exception
		//update the move candidate according to the change.
	}
	
	/**
	 * @param player
	 * @param newRow
	 * @param newCol
	 */
	public void dropWall() throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		//check if the Row and Col are valid for the given wall parameter if not throw exception
		//finalize drop by putting the move into the movelist and update the gamePosition.
		//set my hand as empty and switch turn
	}
	//under feature 9
	public void savePosition() {
		
	}
	//under feature 10
	public void loadPosition() {
	
	}
	
	//under feature 11
	public void validatePosition(Tile tile) {
		// need parameter to know whether it is a pawn or a wall ?
		
		// validate pawn position
		// validate wall position
	}
	//under feature 12
	public void UpdateBoard() {
		
	}
	private boolean isRunning() {
		Game current = Quoridor223Application.getCurrentGame();
		if(current == null || current.getGameStatus()!=Game.GameStatus.Running)return false;
		return true;
	}
}
	
	

