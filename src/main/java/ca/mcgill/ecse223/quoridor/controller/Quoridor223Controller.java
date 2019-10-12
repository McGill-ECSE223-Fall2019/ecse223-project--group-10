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
import java.util.List;

import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {
	
	/**
	 * Create a new game for the players
	 * @author Vanessa Ifrah
	 * @throws UnsupportedOperationException
	 */
	public static void createGame() throws UnsupportedOperationException {
		//check if the game is being initialized
		//throw an exception if not
	
	}
	
	/**
	 * Feature 2: Select an existing user
	 * @author Vanessa Ifrah
	 * @throws UnsupportedOperationException
	 */
	//under feature 2
	public static void selectUser(String playerName1, String playerName2) throws UnsupportedOperationException {
		// load the user
		// if not throw an exception
	}
	
	/**
	 * Feature 2: Creating a new user with new username
	 * @author Vanessa Ifrah
	 * @throws UnsupportedOperationException
	 */
	public static void createUser(String name) throws UnsupportedOperationException{
		// create a new user
		// throw an exception if user not created
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
		if (currentGame.getBlackPlayer().getUser().getName().equals(playerName)) {
			currentPlayer = currentGame.getBlackPlayer();
		} else {
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
		
		// remains to implement switch player method in the game
	}
	
	//under feature 5
	/**
	 * Feature 5: Rotate Wall
	 * Perform a rotate wall operation on the wall of the respective player
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
	 * Feature 6: Grab Wall
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
	 * Gherkin Feature 7: MoveWall.feature
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
	 * Gerkin Feature 8: DropWall.feature
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
	
	/**
	 * Feature 9: Save the current game position as a file in the filesystem
	 * @author Mitchell Keeley
	 * @param filename
	 * @return true if the game position was saved, otherwise returns false 
	 * @throws IOException 
	 */
	public static boolean savePosition(String filename) throws IOException {
		// GUI: register button press of the save game button
		// ie: this method is called when a player clicks the save game button in the save game dialog box
		// the user types the filename into a dialog box and then clicks the save button
		// if the file is invalid, prompt the user to write a valid path
		// if the file exists already, prompt the user to agree to overwrite the file
		boolean savedPosition;
		
		// create a new File instance
		File saveFile = new File(filename);
				
		// if the File doesn't exist
		if (!saveFile.exists()) {
			savedPosition = writeToNewFile(filename);
		}
		
		// if the file exists
		else {
			savedPosition = writeToExistingFile(filename);
		}
		
		return savedPosition;
	}
	
	// under feature 10
	/**
	 * Feature 10: Load game
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 */
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
	/**
	 * Feature 11: Validate Position
	 * @author Sacha Lévy
	 * @param row
	 * @param col
	 * @throws UnsupportedOperationException, GameNotRunningException 
	 */
	// definition overriding first definition applied for testing the validity of wall moves
	public static boolean validatePosition(Tile tile) throws UnsupportedOperationException, GameNotRunningException{
		if (!isRunning()) {throw new GameNotRunningException("Game not running");}
		Quoridor curr_quoridor = QuoridorApplication.getQuoridor();
		int curr_row = tile.getRow();
		int curr_col = tile.getColumn();
		// check if there is an overlap between the tiles on board and the next move
		for(Tile tmp_tile: curr_quoridor.getBoard().getTiles()) {
			if(tmp_tile.getRow() == (curr_row) || tmp_tile.getColumn() == (curr_col));
			else return false;
		}
		return true;
	}
		
	//under feature 12
	/**
	 * Feature 12: Switch Player
	 * Switch player continuous operation
	 * @throws UnsupportedOperationException, GameNotRunningException
	 * @author Sacha Lévy
	 */
	public void SwitchPlayer() throws UnsupportedOperationException, GameNotRunningException{
		// method constantly running while in-game
		while(true) {
			if (!isRunning()) {throw new GameNotRunningException("Game not running");}
			// get the current player
			Game curr_game = QuoridorApplication.getQuoridor().getCurrentGame();
			Player current_player;
			Player other_player;
			if(curr_game.getBlackPlayer().equals(curr_game.getCurrentPosition().getPlayerToMove())){
				// player moving is the black player
				current_player = curr_game.getBlackPlayer();
				other_player = curr_game.getWhitePlayer();
			}
			else {
				// player moving is the white player 
				current_player = curr_game.getWhitePlayer();
				other_player = curr_game.getBlackPlayer();
			}
			// check if the clock of the current player is not running
			if (isClockRunning(current_player) == false) {
				// set the player to move as the other player
				curr_game.getCurrentPosition().setPlayerToMove(other_player);
				updateGUI(current_player, other_player);
			}
		}
	}
	
	/**
	 * Update the GUI when performing the Switch Player operation
	 * @param player
	 * @param other
	 * @author Sacha Lévy
	 */
	private void updateGUI(Player other, Player player) throws UnsupportedOperationException{
		// load to the GUI a dialog box indicating his turn to the player
		// unshade the current player to move buttons
		// shade the other player's buttons
	}
	
	/**
	 * Check if the clock of the given player is running
	 * @param player
	 * @return boolean
	 * @author Sacha Lévy
	 */
	private boolean isClockRunning(Player player)throws UnsupportedOperationException{
		Time tmp_time = player.getRemainingTime();
		if (tmp_time.equals(player.getRemainingTime())) return true;
		else return false;
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
	
	public static boolean writeToExistingFile(String filename) throws IOException {
		boolean savefileUpdated = false;
		
		// verify the file is a file, and is writable
		// prompt the user to overwrite the file
		if (userOverwritePrompt() == true) {
			// save the current GamePositon as the specified file
			savefileUpdated = saveCurrentGamePositionAsFile(filename);
		}		
		
		//return savefileUpdated;
		throw new UnsupportedOperationException();
	}

	public static boolean writeToNewFile(String filename) throws IOException {
		boolean saveFileCreated = false;
		File saveFile = new File(filename);
		
		if(saveFile.createNewFile() && saveFile.isFile()) {
			saveFileCreated = saveCurrentGamePositionAsFile(filename);
		}
		
		//return saveFileCreated;
		throw new UnsupportedOperationException();
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
	 * @throws IOException
	 */
	public static boolean saveCurrentGamePositionAsFile(String filename) throws IOException {
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
		
		// return true;
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
		// check first line of file
		// if it contains "W:" and doesn't contain "B:"
		// if so, set the next player as the white player
		// check the next line for if it contains "B:" and doesn't contain "W:"
		// if so, set the next player as the black player
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to validate the file information being passed to the new GamePosition
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 */
	public static GamePosition validateFile(String filename) {
		// validate the file
		// will implement later but will validate the contents of the file
		// to verify it fits the correct data format
		// and that when a new GamePosition is created with that data
		// all the information (player and wall positions, nextPlayer)
		// doesn't add any invalid information to the GamePosition
		// returns the valid GamePosition updated with all the valid data
		
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to set the current player to move by color
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @return
	 */
	public static boolean setCurrentPlayerToMoveByColor(String playerColor) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		return game.getCurrentPosition().setPlayerToMove(getPlayerByColor(playerColor));
	}
	
	/**
	 * A function to set the player position by color
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean setPlayerPositionByColor(String playerColor, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = quoridor.getBoard();
		Game game = quoridor.getCurrentGame();
		
		if(playerColor.equals("white")	) {
			return game.getCurrentPosition().setWhitePosition(new PlayerPosition(getPlayerByColor(playerColor), new Tile(row, col, board)));
		}
		else {
			return game.getCurrentPosition().setBlackPosition(new PlayerPosition(getPlayerByColor(playerColor), new Tile(row, col, board)));
		}
	}
	
	/**
	 * A function to add a wall position to the player specified by the given color
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param direction
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean addPlayerWallPositionByColor(String playerColor, String direction, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		List<Move> moveList = game.getMoves();
		
		// create new wall move
		// add the wall move to the moveList
		// GUI: place the wall on the board		
		throw new UnsupportedOperationException();
	}

	/**
	 * A function to get the player specified by the color
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @return
	 */
	public static Player getPlayerByColor(String playerColor) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		
		if(playerColor.equals("white")) {
			return game.getWhitePlayer();
		}
		else {
			return game.getBlackPlayer();
		}
	}
	
	/**
	 * A function to throw an error to be handled by the GUI when loading an invalid file
	 * @author Mitchell Keeley
	 */
	public static void whenInvalidLoadGamePosition() {
		// throw error to be caught by the GUI to prompt user to load a different file
		throw new UnsupportedOperationException();
	}

}
