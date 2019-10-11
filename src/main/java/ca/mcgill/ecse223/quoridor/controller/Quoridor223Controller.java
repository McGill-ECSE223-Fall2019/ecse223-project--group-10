package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;

import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Time;
import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {

	private enum side {
		up, down, left, right
	};

	// under feature 1
	public static void createGame() {

	}

	// under feature 2
	public static void selectUser(String playerName1, String playerName2) {

	}

	// under feature 2
	public static void createUser(String name) {

	}

	/**
	 * helper method to get current player
	 * 
	 * @author Andrew Ta
	 * @param playerName
	 * @return
	 */
	public static Player getCurrentPlayer(String playerName) {
		// get current game

		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player currentPlayer;
		// get currentPlayer
		if (currentGame.getBlackPlayer().getUser().getName().equals(playerName)) {
			currentPlayer = currentGame.getBlackPlayer();
		} else {
			currentPlayer = currentGame.getWhitePlayer();
		}

		return currentPlayer;
	}

	/**
	 * Feature 3: set total thinking time
	 * 
	 * @author Andrew Ta
	 * @param thinkingTime
	 * @param playerName
	 * @throws UnsupportedOperationException
	 */
	public static void setThinkingTime(Time thinkingTime, String playerName) throws UnsupportedOperationException{
		if(!isRunning()) return; //if the game is not running, return
		
		// get current player
		Player currentPlayer = getCurrentPlayer(playerName);

		// set thinking time of that player
		currentPlayer.setRemainingTime(thinkingTime);
	}

	/**
	 * get remaining time
	 * 
	 * @author Andrew Ta
	 * @param playerName
	 * @return Time
	 * @throws UnsupportedOperationException
	 */
	public static Time getThinkingTime(String playerName) throws UnsupportedOperationException{
		if(!isRunning()) return null; //if the game is not running, return
		
		// get current player
		Player currentPlayer = getCurrentPlayer(playerName);

		return currentPlayer.getRemainingTime();
	}

	/**
	 * Feature 4: initialize board
	 * 
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

	/**
	 * return board
	 * 
	 * @author Andrew Ta
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public static Board getBoard() throws UnsupportedOperationException{
		if(!isRunning()) return null;
		
		// get quoridor object
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		
		return quoridor.getBoard();
	}

	// under feature 5
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
		int newCol = candidate.getTargetTile().getColumn()+ (side.equalsIgnoreCase("left")?-1:side.equalsIgnoreCase("right")?1:0);
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
	
	/**
	 * Save the current game position as a file in the filesystem
	 * @author Mitchell Keeley
	 * @param filename
	 * @return true if the game position was saved, otherwise returns false 
	 * @throws Throwable 
	 */
	public static boolean savePosition(String filename) throws Throwable {
		
		// when a player clicks the save game button in the save game dialog box
		// GUI: register button press of the save game button
		// GUI
		// the user types the filename into a dialog box and then clicks the save button
		// if the file is invalid, prompt the user to write a valid path
		// if the file exists already, prompt the user to agree to overwrite the file
		
		// create a new File instance
		File saveFile = new File(filename);
		
		// check that it is a valid file
		saveFile.createNewFile();
		if(!saveFile.isFile()) {
			saveFile.delete();
			throw new IOException("Invalid File");
		}
		saveFile.delete();
		
		// if the file exists
		if (saveFile.exists()) {
			// prompt user to overwrite the current file
			boolean overwrite = userOverwritePrompt();
			if (overwrite == false) {
				return false;
			}
		}
		
		// if the File doesn't exist, set it to writable and create it
		if (!saveFile.exists()) {
			saveFile.setWritable(true);
			if (saveFile.createNewFile() == false)
				throw new IOException("File cannot be created");
		}
		
		saveCurrentGamePositionAsFile(filename);
		
		return true;
	}
	
	// under feature 10
	public static boolean loadPosition(String filename) {
		// check if the Game is running, if it is, throw exception
		if (isRunning()) {
			throw new UnsupportedOperationException("Game is currently running");
		}

		File loadFile = new File(filename);
		if (!loadFile.isFile() || !loadFile.canRead()) {
			// throw new IOException("Invalid File");
			return false;
		}
		
		// get the current Game and GamePosition
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();		
		GamePosition currentGamePosition = currentGame.getCurrentPosition();
		Player playerToMove;
		
		// get the new GamePosition data from the loadFile
		PlayerPosition whitePlayerPosition = getPlayerPositionDataFromFile(filename, "whitePosition");
		PlayerPosition blackPlayerPosition = getPlayerPositionDataFromFile(filename, "blackPosition");
		if (getColorOfNextPlayerFromFile(filename).equals("white")) {
			playerToMove = currentGame.getWhitePlayer();
		}else {
			playerToMove = currentGame.getBlackPlayer();
		}
		
		// get the new Game data from the loaFile
		
		// validate the positions and moves
		
		// add the data obtained from the file to the current Game
		// TODO: add walls and moves based on the data from the file
		
		// add the data obtained from the file to the current GamePosition
		currentGamePosition = new GamePosition(currentGamePosition.getId(), whitePlayerPosition, blackPlayerPosition, playerToMove, currentGame);

		// validate the tile position for the current player
		
		/*for(PlayerPosition playerPosition : loadGamePosition.getPlayerPositions()) {
			for(Tile tilePosition : playerPosition) {
			  
				boolean isPositionValid = validatePosition(tilePosition);
				if (isPositionValid == false) {
					//throw new UnsupportedOperationException("Invalid load File");
				}
				return false;  
				
				if(tilePosition.isPlayerWhite()) {
					//set player White to tile Position
				}else if(tilePosition.isPlayerBlack()) {
					//set player Black to tile Position 
				}else if(tilePosition.isPlayerBlackWall()) {
					//set wall in tile position 
				}
						  
				//decrement player Black's wall reserve by 1 } else
				if(tilePosition.isPlayerWhiteWall()) { //set wall in tile position
				//decrement player White's wall reserve by 1
				}
			} 
		}*/

		return true;
	}

	// under feature 11
	public static void validatePosition(Tile tile) {
		// need parameter to know whether it is a pawn or a wall ?

		// validate pawn position
		// validate wall position
	}

	// under feature 12
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
	
	/**
	 * GUI function to prompt the user for permission to overwrite the existing file
	 * @author Mitchell Keeley
	 * @return overwriteApproved
	 */
	public static boolean userOverwritePrompt() {
		
		//boolean overwriteApproved;
		// use UI to prompt user to overwrite the existing file filename
		// overwriteApproved = result of user input using the ui;
		//return overwriteApproved;
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to save the current GamePosition as a file
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws Throwable 
	 */
	public static void saveCurrentGamePositionAsFile(String filename) throws Throwable {
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		GamePosition currentGamePosition = currentGame.getCurrentPosition();
		Player whitePlayer = currentGame.getWhitePlayer();
		Player blackPlayer = currentGame.getBlackPlayer();
		
		// get the player information strings to write to the file
		// TODO: verify that player1 is actually the whitePlayer
		// TODO: if so, verify the coordinates for the start positions are correct and aligned with the spec for the game coordinate system
		String whitePlayerData = "W: " + tileToString(currentGamePosition.getWhitePosition().getTile());
		//System.out.printf(whitePlayerData);
		// continue with wall list converted to string 
		String blackPlayerData = "B: " + tileToString(currentGamePosition.getBlackPosition().getTile());
		//System.out.printf(blackPlayerData);
		
		//currentGame.addMove(new WallMove(1,1,whitePlayer,new Tile(1,2,quoridor.getBoard()),
				//currentGame,Direction.Horizontal,new Wall(0,whitePlayer)));
		//currentGame.addMove(new WallMove(2,1,blackPlayer,new Tile(5,2,quoridor.getBoard()),
				//currentGame,Direction.Horizontal,new Wall(10,blackPlayer)));
		
		// add the wall positions
		// TODO: when able to add wall Moves in test, verify wall moves are also recorded
		for( Move move : currentGame.getMoves()) {
			if (move instanceof WallMove){
				if(move.getPlayer().equals(whitePlayer)) {
					whitePlayerData.concat(", " + tileToString(move.getTargetTile()) + ((WallMove) move).getWallDirection());
					//printWriter.printf("%s\n", whitePlayerData);
				}
				else if(move.getPlayer().equals(blackPlayer)) {
					blackPlayerData.concat(", " + tileToString(move.getTargetTile()) + ((WallMove) move).getWallDirection());
					//printWriter.printf("%s\n", blackPlayerData);
				}
			}
		}
		
		// initialize the printWriter
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
		
		// if the next player to move is the White Player
		if(currentGamePosition.getPlayerToMove().equals(currentGamePosition.getGame().getWhitePlayer())){
			printWriter.printf("%s\n", whitePlayerData);
			printWriter.printf("%s", blackPlayerData);
		}else {
			printWriter.printf("%s\n",blackPlayerData);
			printWriter.printf("%s", whitePlayerData);
		} 
		printWriter.close();
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to translate a tile into the correct string to write to the save file
	 * @author Mitchell Keeley
	 * @param tile
	 * @return
	 */
	public static String tileToString(Tile tile) {
		int asciiNumberOffset = 48;
		int asciiLetterOffset = 96;
		
		String tileString = "" + (char)(asciiLetterOffset + tile.getRow()) + (char)(asciiNumberOffset + tile.getColumn());
		
		return tileString;
	}
	
	/**
	 * A function to translate a Direction into the correct string to write to the save file
	 * @author Mitchell Keeley
	 * @param direction
	 * @return
	 */
	public String directionToString(Direction direction) {
		if(direction.equals(Direction.Horizontal)){
			return "h";
		}
		else {
			return "v";
		}
	}
	
	/**
	 * A function to load a new GamePosition
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 */
	private GamePosition getLoadGamePosition(File filename) {

		// initialize the new GamePosition
		GamePosition newGamePosition = new GamePosition(0, null, null, null, null);

		try {
			// create a file reader
			// BufferedReader fileReader = new BufferedReader(new FileReader(filename));

			// read the file to load the GamePosition
			// newGamePosition.nextPlayer = fileReader.readLine();
			// newGamePosition.CurrentPlayer = fileReader.readLine();

			// set the current player color by the first entry in the Current player
			// set the next player as the other color

		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}

		return newGamePosition;
	}
	
	/**
	 * A function to get the PlayerPosition for a specified player
	 * @author Mitchell Keeley
	 * @param filename
	 * @param playerPosition
	 * @return
	 */
	private static PlayerPosition getPlayerPositionDataFromFile(String filename, String playerPosition){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to get the color of the next player from the file
	 * @author Mitchell Keeley
	 * @param filename
	 * @param playerPosition
	 * @return
	 */
	private static String getColorOfNextPlayerFromFile(String filename) {
		throw new UnsupportedOperationException();
	}

}
