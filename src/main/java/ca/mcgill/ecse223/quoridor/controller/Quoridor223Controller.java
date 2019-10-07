package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application;
import ca.mcgill.ecse223.quoridor.model.*;
import java.sql.Time;
import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {
	private enum side{up,down,left,right};
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
	 * @param player
	 * @param Row
	 * @param Col
	 */
	public void moveWall(int row, int col, side s) throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		Game curGame = Quoridor223Application.getCurrentGame();
		if(!isRunning())throw new UnsupportedOperationException("Game is not running");
		//check if the it is player's turn if not throw exception
		
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new UnsupportedOperationException("No Wall selected");
		//check if newRow and newCol are within the board if not throw exception
		int nRow = row +(s==side.up?-1:s==side.down?1:0);
		int nCol = col +(s==side.left?1:s==side.right?-1:0);
		if(!isWallValid(nRow,nCol))throw new UnsupportedOperationException("Move is illegal.");
		//update the move candidate according to the change.
		
	}
	
	/**
	 * @param player
	 * @param newRow
	 * @param newCol
	 */
	public void dropWall() throws UnsupportedOperationException{
		//check if the Game is running if not throw exception
		Game curGame = Quoridor223Application.getCurrentGame();
		if(!isRunning())throw new UnsupportedOperationException("Game is not running");
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new UnsupportedOperationException("No Wall selected");
		//check if the Row and Col are valid for the given wall parameter if not throw exception
		int	row = curGame.getWallMoveCandidate().getTargetTile().getRow();
		int	col = curGame.getWallMoveCandidate().getTargetTile().getColumn();
		if(!isWallValid(row,col))throw new UnsupportedOperationException("Position is not valid");
		//finalize drop by putting the move into the movelist and update the gamePosition.
		
		//set my hand as empty and switch turn
		curGame.setWallMoveCandidate(null);
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
	private boolean isWallValid(int row, int col) {
		return (row>0 && col>0 && row<9 && col <9);
	}
}
	
	

