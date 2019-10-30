package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;

import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.view.GamePage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import java.security.InvalidAlgorithmParameterException;

public class Quoridor223Controller {
	
	/**
	 * Feature 1: Create a new game for the players
	 * @author Vanessa Ifrah
	 * @throws UnsupportedOperationException
	 */
	public static void createGame() throws UnsupportedOperationException {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game newGame = new Game(GameStatus.Running, MoveMode.WallMove, quoridor);
		
		// create players
		List<User> users = quoridor.getUsers();
		Player whitePlayer = new Player(new Time(10), users.get(0), 9, Direction.Horizontal);
		Player blackPlayer = new Player(new Time(10), users.get(1), 1, Direction.Horizontal);
		newGame.setBlackPlayer(blackPlayer);
		newGame.setWhitePlayer(whitePlayer);
	}
	
	/**
	 * Feature 2: Select an existing user
	 * @author Vanessa Ifrah
	 * @throws UnsupportedOperationException
	 */
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
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user = new User(name, quoridor);
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
		// get current Game
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		Player currentPlayer;
		if(playerName.equals("white")) {
			currentPlayer = currentGame.getWhitePlayer();
		}else {
			currentPlayer = currentGame.getBlackPlayer();
		}
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
		
		// create a new board
		Board board = new Board(quoridor);
		// add tiles
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
		
		// create walls
		for (int i = 0; i < 10; i++) {
			new Wall(0 * 10 + i + 1, quoridor.getCurrentGame().getWhitePlayer());
		}
		for (int i = 0; i < 10; i++) {
			new Wall(1 * 10 + i + 1, quoridor.getCurrentGame().getBlackPlayer());
		}
		
		// get tiles
		Tile whitePlayerTile = quoridor.getBoard().getTile(4);
		Tile blackPlayerTile = quoridor.getBoard().getTile(76);
		
		Game currentGame = quoridor.getCurrentGame();
	
		// create players' initial positions
		PlayerPosition whitePlayerPosition = new PlayerPosition(currentGame.getWhitePlayer(), whitePlayerTile);
		PlayerPosition blackPlayerPosition = new PlayerPosition(currentGame.getBlackPlayer(), blackPlayerTile);
		GamePosition gamePosition = new GamePosition(0, whitePlayerPosition, blackPlayerPosition, currentGame.getWhitePlayer(), currentGame);

		// Add the walls to stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		currentGame.setCurrentPosition(gamePosition);
	}
	
	//under feature 5
	/**
	 * Feature 5: Rotate Wall
	 * Perform a rotate wall operation on the wall of the respective player
	 * @author Enan Ashaduzzaman
	 * @throws UnsupportedOperationException
	 */

	public static void rotateWall() throws GameNotRunningException{
//		throw new UnsupportedOperationException();
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if the Game is running. If not, thrown an exception.
		//Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//if(!isRunning()) throw new UnsupportedOperationException("Game is not running");
		if(curGame.getWallMoveCandidate() == null) {
			return;
		}
		//check if it is the player's turn. If not, thrown an exception. 
		//check if there is no wall in my hand. If no wall, thrown an exception. 
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
	public static void grabWall() throws GameNotRunningException{
//		throw new UnsupportedOperationException();
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if the Game is running if not throw exception
		if(curGame.getWallMoveCandidate() == null) {
			return;
		}
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
	public static void moveWall(TOWall.Side side) throws GameNotRunningException, InvalidOperationException {
		//check if the Game is running if not throw exception
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if the it is player's turn if not throw exception
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new InvalidOperationException("No wall Selected");
		WallMove candidate =  curGame.getWallMoveCandidate();
		//check if newRow and newCol are within the board if not throw exception
		int newRow = candidate.getTargetTile().getRow()+ (side==TOWall.Side.Up?-1:side==TOWall.Side.Down?1:0);
		int newCol = candidate.getTargetTile().getColumn()+ (side==TOWall.Side.Left?-1:side==TOWall.Side.Right?1:0);
		if(!isWallPositionValid(newRow,newCol))throw new InvalidOperationException("Move invalid");
		//update the move candidate according to the change.
		candidate.setTargetTile(getTile(newRow,newCol));
	}

	/**
	 * Perform a drop wall Operation that drop the currently held wall
	 * Gerkin Feature 8: DropWall.feature
	 * @author Le-Li Mao
	 */
	public static void dropWall() throws GameNotRunningException,InvalidOperationException{
		//check if the Game is running if not throw exception
		if(!isRunning())throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		//check if there is wall in my hand if not throw exception
		if(curGame.getWallMoveCandidate()==null)throw new InvalidOperationException("No wall Selected");
		//validate the position

		//finalize drop by putting the move into the movelist.
		Wall wallToDrop = curGame.getWallMoveCandidate().getWallPlaced();
		GamePosition currentPosition = curGame.getCurrentPosition();
		GamePosition clone = clonePosition(currentPosition);
		boolean set =curGame.setCurrentPosition(clone);
		System.out.println(set);
		if(isWhitePlayer()) {
			clone.removeWhiteWallsInStock(wallToDrop);
			clone.addWhiteWallsOnBoard(wallToDrop);
			System.out.println("reach");
		}
		else {
			clone.removeBlackWallsInStock(wallToDrop);
			clone.addBlackWallsOnBoard(wallToDrop);
		}
		curGame.addMove(curGame.getWallMoveCandidate());
		curGame.setWallMoveCandidate(null);
		//Switch Player here
	}
	
	/**
	 * Feature 9: Save the current game position as a file in the filesystem
	 * @author Mitchell Keeley
	 * @param filename
	 * @return true if the game position was saved, otherwise returns false 
	 * @throws IOException 
	 */
	// TODO: Feature 9: Save Game
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
	// TODO: Feature 10: Load Game
	public static boolean loadPosition(String filename) {
		System.out.println("called load position");
		// check if the Game is running, if it is, throw exception
		if (isRunning()) {
			GamePage.errorPrompt("Cannot Load Game since Game is currently running");
			return false;
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
		
		GamePosition newGamePosition = getLoadGamePosition(loadFile);
		
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
	
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Le-Li's Helper and Query methods
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
	
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
	public static int getWhiteWallInStock(){
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getWhiteWallsInStock().size();
	}
	public static int getBlackWallInStock(){
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getBlackWallsInStock().size();
	}
	
	public static TOGame getListOfPlayers(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		ArrayList<String> name = new ArrayList<>();
		for(User user: quoridor.getUsers()) {
			name.add(user.getName());
		}
		TOGame listOfPlayers = new TOGame(quoridor.getCurrentGame().getWhitePlayer().getRemainingTime()
				, quoridor.getCurrentGame().getWhitePlayer().getRemainingTime(), name.get(0), name.get(1));
		return listOfPlayers;
	}
	
	public static ArrayList<TOWall> getWhiteWallOnBoard(){
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		ArrayList<TOWall> wallList = new ArrayList<TOWall>();
		for(Wall wall: curGame.getCurrentPosition().getWhiteWallsOnBoard())wallList.add(convertWall(wall));
		return wallList;
	}
	
	public static ArrayList<TOWall> getBlackWallOnBoard(){
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		ArrayList<TOWall> wallList = new ArrayList<TOWall>();
		for(Wall wall: curGame.getCurrentPosition().getBlackWallsOnBoard())wallList.add(convertWall(wall));
		return wallList;
	}

	public static TOWall getWallInHand() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if(curGame.getWallMoveCandidate()==null)return null;
		return convertWall(curGame.getWallMoveCandidate().getWallPlaced());
	}

	private static TOWall convertWall(Wall aWall) {
		int row = aWall.getMove().getTargetTile().getRow();
		int col = aWall.getMove().getTargetTile().getColumn();
		int id = aWall.getId();
		TOWall.Direction dir = aWall.getMove().getWallDirection()==Direction.Horizontal?TOWall.Direction.Horizontal:TOWall.Direction.Vertical;
		TOWall wall = new TOWall(id, row, col, dir);
		return wall;
	}
	private static GamePosition clonePosition(GamePosition oldPosition) {
		PlayerPosition newWhitePosition = clonePlayerPosition(oldPosition.getWhitePosition());
		PlayerPosition newBlackPosition = clonePlayerPosition(oldPosition.getBlackPosition());
		GamePosition newPosition = new GamePosition(oldPosition.getId()+1, newWhitePosition, newBlackPosition, oldPosition.getPlayerToMove(), oldPosition.getGame());
		for(Wall wall: oldPosition.getBlackWallsInStock())newPosition.addBlackWallsInStock(wall);
		for(Wall wall: oldPosition.getWhiteWallsInStock())newPosition.addWhiteWallsInStock(wall);
		for(Wall wall: oldPosition.getBlackWallsOnBoard())newPosition.addBlackWallsOnBoard(wall);
		for(Wall wall: oldPosition.getWhiteWallsOnBoard())newPosition.addWhiteWallsOnBoard(wall);
		return newPosition;
	}
	private static PlayerPosition clonePlayerPosition(PlayerPosition playerPos) {
		return new PlayerPosition(playerPos.getPlayer(), playerPos.getTile());
	}
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Le-Li's Helper and Query methods Ends
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////	
	
	/**
	 * GUI function to prompt the user for permission to overwrite the existing file
	 * @author Mitchell Keeley
	 * @return overwriteApproved
	 */
	public static boolean userOverwritePrompt(String filename) {
		boolean overwriteApproved = false;
		
		// use UI to prompt user to overwrite the existing file filename
		if(GamePage.userOverwritePrompt(filename) == 0) {	
			overwriteApproved = true;
		}
		
		// overwrite the existing file
		// this is done automatically when writing to the file
		
		return overwriteApproved;
	}
	
	/**
	 * A function to save the current GamePosition as a file
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws IOException
	 */
	public static boolean saveCurrentGamePositionAsFile(String filename) throws IOException {
		System.out.println("called save position");
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		
		// fetch game and gamePosition from quoridor
		//Game currentGame = quoridor.getCurrentGame();
		//GamePosition currentGamePosition = currentGame.getCurrentPosition();
		//Player whitePlayer = currentGame.getWhitePlayer();
		//Player blackPlayer = currentGame.getBlackPlayer();
		
		// for testing: create temp game and gamePosition
		Game currentGame = new Game(Game.GameStatus.Running, MoveMode.PlayerMove, quoridor);
		Board board = new Board(quoridor);
		Tile whiteTile = new Tile(5,9,board);
		Tile blackTile = new Tile(5,1,board);
		User user0 = new User("user0", quoridor);
		User user1 = new User("user1", quoridor);
		Player whitePlayer = new Player(new Time(0,15,0), user0, 1, Direction.Horizontal);
		Player blackPlayer = new Player(new Time(0,15,0), user1, 9, Direction.Horizontal);
		PlayerPosition whitePos = new PlayerPosition(whitePlayer, whiteTile);
		PlayerPosition blackPos = new PlayerPosition(blackPlayer, blackTile);	
		GamePosition currentGamePosition = new GamePosition(1,whitePos,blackPos, whitePlayer, currentGame);
		currentGame.setCurrentPosition(currentGamePosition);
		currentGame.setBlackPlayer(blackPlayer);
		currentGame.setWhitePlayer(whitePlayer);
		currentGamePosition.setGame(currentGame);
		
		// for testing, add moves to the moveList
		Move move1 = new WallMove(0, 0, whitePlayer, new Tile(5,3,board), currentGame,
				Direction.Vertical, new Wall(0,whitePlayer));
		Move move2 = new WallMove(1, 0, blackPlayer, new Tile(2,6,board), currentGame,
				Direction.Horizontal, new Wall(1,blackPlayer));
		Move move3 = new WallMove(2, 1, whitePlayer, new Tile(4,3,board), currentGame,
				Direction.Vertical, new Wall(2,whitePlayer));
		currentGame.addMove(move1);
		currentGame.addMove(move2);
		currentGame.addMove(move3);		
		
		// initialize the player information strings to write to the file
		String whitePlayerData = "W: " + tileToString(currentGamePosition.getWhitePosition().getTile());
		String blackPlayerData = "B: " + tileToString(currentGamePosition.getBlackPosition().getTile());
		//System.out.printf(whitePlayerData);
		//System.out.printf(blackPlayerData);
		
		//currentGame.addMove(new WallMove(1,1,whitePlayer,new Tile(1,2,quoridor.getBoard()),
				//currentGame,Direction.Horizontal,new Wall(0,whitePlayer)));
		//currentGame.addMove(new WallMove(2,1,blackPlayer,new Tile(5,2,quoridor.getBoard()),
				//currentGame,Direction.Horizontal,new Wall(10,blackPlayer)));
		
		// add the wall positions
		for( Move move : currentGame.getMoves()) {
			// if the move is a wallMove
			// TODO: validate that instanceof can correctly identify WallMove, may need to verify if hasDirection
			if (move instanceof WallMove){
				if(move.getPlayer().equals(whitePlayer)) {
					whitePlayerData = whitePlayerData.concat(", " + tileToString(move.getTargetTile()) + directionToString(((WallMove) move).getWallDirection()));
					//printWriter.printf("%s\n", whitePlayerData);
				}
				else if(move.getPlayer().equals(blackPlayer)) {
					blackPlayerData = blackPlayerData.concat(", " + tileToString(move.getTargetTile()) + directionToString(((WallMove) move).getWallDirection()));
					//printWriter.printf("%s\n", blackPlayerData);
				}
			}
			// else if the move is a player move
			// TODO: do nothing since the only relevent player position is the current player position (for now)
		}
		
		// initialize the printWriter
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
		
		// if the next player to move is the White Player
		if(currentGamePosition.getPlayerToMove().equals(currentGamePosition.getGame().getWhitePlayer())){
			printWriter.printf("%s\n", whitePlayerData);
			printWriter.printf("%s", blackPlayerData);
		// else the next player to move is the Black player
		}else {
			printWriter.printf("%s\n",blackPlayerData);
			printWriter.printf("%s", whitePlayerData);
		} 
		printWriter.close();
		
		return true;
		//throw new UnsupportedOperationException();
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
	public static String directionToString(Direction direction) {
		if(direction.equals(Direction.Horizontal)){
			return "h";
		}
		else {
			return "v";
		}
	}
	
	public static boolean writeToExistingFile(String filename) throws IOException {
		boolean savefileUpdated = false;
		
		// verify the file is a file, and is writable
		// prompt the user to overwrite the file
		if (userOverwritePrompt(filename) == true) {
			// save the current GamePositon as the specified file
			savefileUpdated = saveCurrentGamePositionAsFile(filename);
		}		
		
		return savefileUpdated;
		//throw new UnsupportedOperationException();
	}

	public static boolean writeToNewFile(String filename) throws IOException {
		boolean saveFileCreated = false;
		File saveFile = new File(filename);
		
		if(saveFile.createNewFile() && saveFile.isFile()) {
			saveFileCreated = saveCurrentGamePositionAsFile(filename);
		}
		
		return saveFileCreated;
		//throw new UnsupportedOperationException();
	}
	
	/**
	 * A function to load a new GamePosition
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 */
	private static GamePosition getLoadGamePosition(File filename) {
		System.out.printf("called load position");
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		//GamePosition currentGamePosition = currentGame.getCurrentPosition();
		Player whitePlayer = currentGame.getWhitePlayer();
		Player blackPlayer = currentGame.getBlackPlayer();
		
		// initialize the new GamePosition
		GamePosition newGamePosition = new GamePosition(0, null, null, null, null);

		try {
			// create a file reader
			BufferedReader fileReader = new BufferedReader(new FileReader(filename));

			// read the file to load the GamePosition
			String saveFileFirstLine = fileReader.readLine();
			String saveFileSecondLine = fileReader.readLine();
			
			// if the first line is the white player's data, set the black player as the player to move
			if(saveFileFirstLine.contains("W:")) {
				newGamePosition.setPlayerToMove(whitePlayer);
			}
			// else, set the white player as the player to move
			else {
				newGamePosition.setPlayerToMove(blackPlayer);
			}


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
