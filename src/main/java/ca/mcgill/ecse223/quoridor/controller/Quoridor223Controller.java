package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application;
import ca.mcgill.ecse223.quoridor.QuoridorApplication.Quoridor223Application.Side;
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
		Game currentGame = Quoridor223Application.getCurrentGame();
				
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
		Quoridor quoridor = Quoridor223Application.getQuoridor();
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
		Quoridor quoridor = Quoridor223Application.getQuoridor();
		
		if(quoridor.hasBoard()) {
			return quoridor.getBoard();
		}else {
			return null;
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
	
	

