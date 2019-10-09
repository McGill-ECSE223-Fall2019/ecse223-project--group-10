package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application;
import ca.mcgill.ecse223.quoridor.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	public void savePosition(String filename) throws UnsupportedOperationException, IOException{
		
		//check if the Game is running, if not, throw exception
		Game curGame = Quoridor223Application.getCurrentGame();
		if(!isRunning()) {
			throw new UnsupportedOperationException("Game is not running");
		}
		
		// create a new File instance
		File saveFile = new File(filename);
				
		// check if the File <filename> exists and is a normal file
		if(!saveFile.exists() && saveFile.isFile()) {
		    // make the File writable and then create the new file
			saveFile.setWritable(true);
			if(saveFile.createNewFile() == false) throw new IOException("File cannot be created") ;
		}
		// if the file exists
		else if(saveFile.exists() && saveFile.isFile()) {
			// prompt user to overwrite the current file
			boolean overwrite = userOverwritePrompt(filename);
			if(overwrite == false) {
				return;
			}
		}
		// if file is invalid
		else {
			throw new UnsupportedOperationException("Invalid File");
		}
		
		// check if the file is writable
		if(saveFile.canWrite() == false) throw new SecurityException("Cannot modify File");
		
		// if the file is valid and writable, write the current game position to the file
		/*try {
			FileWriter fileWriter = new FileWriter(filename,false);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.printf("%s\n", GamePosition.nextPlayer);
		    printWriter.printf("%s", GamePosition.currentPlayer);
		    printWriter.close();
		} catch (Exception E) {
			E.printStackTrace();			
		}*/
	}
	
	//under feature 10
	public boolean loadPosition(String filename) {
		//check if the Game is running, if it is, throw exception
		Game curGame = Quoridor223Application.getCurrentGame();
		if(isRunning()) {
			throw new UnsupportedOperationException("Game is currently running");
		}
		
		File loadFile = new File(filename);
		if (!loadFile.isFile() || !loadFile.canRead()) {
			//throw new IOException("Invalid File");
			return false;
		}
		
		// get the new GamePosition from the loadFile
		GamePosition loadGamePosition = getLoadGamePosition(loadFile);
		
		// validate the tile position for the current player
		/*for(PlayerPosition playerPosition : loadGamePosition.getPlayerPositions()) {
			for(Tile tilePosition : playerPosition) {
					
				boolean isPositionValid = validatePosition(tilePosition);
				if (isPositionValid == false) {
					//throw new UnsupportedOperationException("Invalid load File");
					return false;
				}
				if( tilePosition.isPlayerWhite()) {
					//set player White to tile Position
				}
				else if(tilePosition.isPlayerBlack()) {
					//set player Black to tile Position
				}
				else if(tilePosition.isPlayerBlackWall()) {
					//set wall in tile position
					//decrement player Black's wall reserve by 1
				}
				else if(tilePosition.isPlayerWhiteWall()) {
					//set wall in tile position
					//decrement player White's wall reserve by 1
				}
			}
		}*/
		
		
		//load the new GamePosition
		Quoridor223Application.loadNewGamePosition(loadGamePosition);
		return true;
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
	
	private boolean userOverwritePrompt(String filename) {
		// use UI to prompt user to overwrite the existing file filename
		boolean overwriteApproved = false;
		
		// overwriteApproved = result of user input using the ui;
		
		return overwriteApproved;
	}
	
	private GamePosition getLoadGamePosition(File filename) {
		
		// initialize the new GamePosition
		GamePosition newGamePosition = new GamePosition(0, null, null, null, null);
		
		try {
			// create a file reader
			//BufferedReader fileReader = new BufferedReader(new FileReader(filename));
					
			// read the file to load the GamePosition
			//newGamePosition.nextPlayer = fileReader.readLine();
			//newGamePosition.CurrentPlayer = fileReader.readLine();
			
			// set the current player color by the first entry in the Current player
			// set the next player as the other color
			
		} catch(Exception e) {
			throw new UnsupportedOperationException(e);
		}
		
		return newGamePosition;
	}
}
	
	

