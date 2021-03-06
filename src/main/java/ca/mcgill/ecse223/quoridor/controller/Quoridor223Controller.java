package ca.mcgill.ecse223.quoridor.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.JumpMove;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.view.GamePage;

public class Quoridor223Controller {

	/**
	 * Feature 1: Create a new game for the players
	 * 
	 * @author Vanessa Ifrah
	 */
	public static void createGame() {
		
		// create Quoridor game and get users
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game newGame = new Game(GameStatus.Initializing, false, MoveMode.WallMove, quoridor);

	}

	/**
	 * @author Vanessa Ifrah
	 */
	public static void setGameToReady() {
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		curGame.setGameStatus(GameStatus.ReadyToStart);
	}

	/**
	 * @author Vanessa Ifrah
	 */
	public static void setGameToRun() {
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		curGame.setGameStatus(GameStatus.Running);
	}
	
	public static void setGameToDraw() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		curGame.setGameStatus(GameStatus.Draw);
	}
	

	/**
	 * Feature 2: Setting a user with a new username or with an existing one
	 * 
	 * @author Vanessa Ifrah
	 * @param name
	 * @param color
	 */
	public static void setUser(String name, String color) {

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		User user = quoridor.addUser(name);

		// create player
		Player player;

		// set player name according to their color
		if (color.equals("white")) {
			player = new Player(Time.valueOf("00:10:00"), user, 1, Direction.Vertical);
			curGame.setWhitePlayer(player);
		} else {
			player = new Player(Time.valueOf("00:10:00"), user, 9, Direction.Vertical);
			curGame.setBlackPlayer(player);
		}
	}

	/**
	 * @author Vanessa Ifrah
	 * 
	 * @param playerName1
	 * @param playerName2
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public static List<User> selectUser(String playerName1, String playerName2) throws UnsupportedOperationException {

		// load the user
		if (!User.hasWithName(playerName1))
			createUser(playerName1);
		if (!User.hasWithName(playerName2))
			createUser(playerName2);
		ArrayList<User> list = new ArrayList<User>();
		list.add(User.getWithName(playerName1));
		list.add(User.getWithName(playerName2));
		return list;
	}
	
	/**
	 * @author Vanessa Ifrah
	 * @param name
	 * @return
	 */
	public static boolean checkNameList(String name) {

		boolean result = true;
		ArrayList<String> usernames = new ArrayList<String>();
		try {
			File f = new File("names.txt");
			FileReader reader = new FileReader(f.getAbsolutePath());
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				usernames.add(line);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!usernames.contains(name)) {
			result = false;
		}

		return result;
	}
	
	/**
	 * @author Vanessa Ifrah
	 * @param name
	 * @return error
	 */
	public static boolean warnUser(String name) {
		
		boolean error = false;
		
		// check if name already exists (duplicate name)
		if (Quoridor223Controller.checkNameList(name)) {
			error = true;
		}
		
		return error;

	}
	
	/**
	 * @author Vanessa Ifrah
	 * @param playerName
	 * @throws UnsupportedOperationException
	 */
	public static void createUser(String playerName) throws UnsupportedOperationException {

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		quoridor.addUser(playerName);

	}
	
	/**
	 * @author Vanessa Ifrah
	 */
	public static boolean identifyWin() {

		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = curGame.getCurrentPosition().getPlayerToMove();
		if(curPlayer.equals(curGame.getWhitePlayer())) {
			Tile tile = curGame.getCurrentPosition().getWhitePosition().getTile();
			int curWhiteRow = tile.getRow();
			if(curWhiteRow == 1) {
				curGame.setGameStatus(GameStatus.WhiteWon);
				return true;
			}
		} else if (curPlayer.equals(curGame.getBlackPlayer())) {
			Tile tile = curGame.getCurrentPosition().getBlackPosition().getTile();
			int curBlackRow = tile.getRow();
			if(curBlackRow == 9) {
				curGame.setGameStatus(GameStatus.BlackWon);
				return true;
			}
		}
		return false;
	}

	/**
	 * helper method to get player by name
	 * 
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
	 * set the remaining time of a player
	 * 
	 * @author Andrew Ta
	 * @param thinkingTime remaining time provided
	 * @param playerName   name of the player
	 */
	public static void setThinkingTime(Time thinkingTime, String playerName) {
		// get current Game
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = getPlayerByName(playerName);
		// set thinking time of that player
		currentPlayer.setRemainingTime(thinkingTime);
		if(quoridor.getCurrentGame().getGameStatus() == GameStatus.ReadyToStart||quoridor.getCurrentGame().getGameStatus() == GameStatus.Initializing) {
			setGameToReady();
		}

	}

	/**
	 * Get Remaining Time of Black and White Player
	 * querry method to get remaining time of a player
	 * 
	 * @author Andrew Ta
	 * @param playerName name of the player
	 */
	public static Time getRemainingTime(String playerColor) {
		Player currentPlayer;
		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if(playerColor.equals("white")) {
			currentPlayer = currentGame.getWhitePlayer();
		}else {
			currentPlayer = currentGame.getBlackPlayer();
		}

		return currentPlayer.getRemainingTime();
	}

	/**
	 * Feature 4: Initialize Board
	 * create a new board with 81 tiles
	 * create 10 walls and put them on the stock for each player 
	 * put each player in its initial position
	 * set white player as the first one to move
	 * 
	 * @author Andrew Ta
	 */
	public static void initializeBoard() {
		// get quoridor object
		Quoridor quoridor = QuoridorApplication.getQuoridor();

		// create a new board if not yet created
		Board board;
		if (!quoridor.hasBoard()) {
			board = new Board(quoridor);
			// add tiles
			for (int i = 1; i <= 9; i++) { // rows
				for (int j = 1; j <= 9; j++) { // columns
					board.addTile(i, j);
				}
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
		Tile whitePlayerTile = quoridor.getBoard().getTile(76);
		Tile blackPlayerTile = quoridor.getBoard().getTile(4);
		
		//Initialize the Pawn Behavior
		
		// create players' initial positions
		Game currentGame = quoridor.getCurrentGame();
		// set current position to a new game position
		
		PlayerPosition whitePlayerPosition = new PlayerPosition(currentGame.getWhitePlayer(), whitePlayerTile);
		PlayerPosition blackPlayerPosition = new PlayerPosition(currentGame.getBlackPlayer(), blackPlayerTile);
		GamePosition gamePosition = new GamePosition(0, whitePlayerPosition, blackPlayerPosition,
				currentGame.getWhitePlayer(), currentGame);
		
		// Add the walls to stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 1);
			gamePosition.addWhiteWallsInStock(wall);
		}

		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10 + 1);
			gamePosition.addBlackWallsInStock(wall);
		}

		currentGame.setCurrentPosition(gamePosition);

		// set next player
		currentGame.getWhitePlayer().setNextPlayer(currentGame.getBlackPlayer());
		PawnBehavior whitebehavior = QuoridorApplication.CreateNewWhitePawnBehavior();
		PawnBehavior blackbehavior = QuoridorApplication.CreateNewBlackPawnBehavior();
		whitebehavior.startGame();
		blackbehavior.startGame();
	}
	
	public static void setUpNewGame(String time1, String time2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		curGame.delete();
		quoridor.setCurrentGame(curGame);
		
		List<User> users = QuoridorApplication.getQuoridor().getUsers();
		Player white = new Player(Time.valueOf(time1), users.get(0), 1, Direction.Vertical);
		curGame.setWhitePlayer(white);
		Player black = new Player(Time.valueOf(time2), users.get(1), 9, Direction.Vertical);
		curGame.setBlackPlayer(black);
		
		initializeBoard();
		curGame.setGameStatus(GameStatus.Running);
	}

	/**
	 * Feature 5: Rotate Wall Perform a rotate wall operation on the wall of the
	 * respective player
	 * @author Enan Ashaduzzaman
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void rotateWall() throws GameNotRunningException, InvalidOperationException {
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if (curGame.getWallMoveCandidate() == null) {
			throw new InvalidOperationException("No wall Selected");
		}
		Direction wallDir = curGame.getWallMoveCandidate().getWallDirection();
		if (wallDir == Direction.Horizontal) {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Vertical);
		} else {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}
	}

	/**
	 * Feature 6: Grab Wall Perform a grab wall operation on the stock of the
	 * respective player
	 * 
	 * @author Enan Ashaduzzaman
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void grabWall() throws GameNotRunningException, InvalidOperationException {
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Board curBoard = QuoridorApplication.getQuoridor().getBoard();

		int moveLength = curGame.getMoves().size();
		
	    int moveNumber = 1;
	    int roundNumber = 1;
	    if(moveLength!=0){
	    	Move lastMove = curGame.getMove(moveLength-1);
	    	roundNumber = lastMove.getRoundNumber()%2+1;
	    	moveNumber = lastMove.getMoveNumber();
	    	if(roundNumber==1)moveNumber++;
	    }
		
		if (curGame.getWallMoveCandidate() != null) {
			throw new InvalidOperationException("Already a wall Selected");
		}
		Player curPlayer = curGame.getCurrentPosition().getPlayerToMove();
		
		if (curPlayer.equals(curGame.getWhitePlayer())) {
			if (curGame.getCurrentPosition().getWhiteWallsInStock().isEmpty()) {
				throw new InvalidOperationException("No walls in stock");
			} else {
				Wall curWall = curGame.getCurrentPosition().getWhiteWallsInStock(0);
				curGame.getCurrentPosition().removeWhiteWallsInStock(curWall);
				WallMove curWallMove = new WallMove(moveNumber, roundNumber, curPlayer, curBoard.getTile(67), curGame,
						Direction.Horizontal, curWall);
				curGame.setWallMoveCandidate(curWallMove);
			}
		} else if (curPlayer.equals(curGame.getBlackPlayer())) {
			if (curGame.getCurrentPosition().getBlackWallsInStock().isEmpty()) {
				throw new InvalidOperationException("No walls in stock");
			} else {
				Wall curWall = curGame.getCurrentPosition().getBlackWallsInStock(0);
				curGame.getCurrentPosition().removeBlackWallsInStock(curWall);
				WallMove curWallMove = new WallMove(moveNumber, roundNumber, curPlayer, curBoard.getTile(4), curGame,
						Direction.Horizontal, curWall);
				curGame.setWallMoveCandidate(curWallMove);
			}
		}
	}
	
	/**
	 * @author Enan Ashaduzzaman
	 * @throws GameNotRunningException
	 */
	public static void cancelGrabWall() throws GameNotRunningException {
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = curGame.getCurrentPosition().getPlayerToMove();
		
		if (curPlayer.equals(curGame.getWhitePlayer())) {
			WallMove curWallMove = curGame.getWallMoveCandidate();
			Wall wallInHand = curWallMove.getWallPlaced();
			curGame.getCurrentPosition().addWhiteWallsInStock(wallInHand);
			curGame.removeMove(curWallMove);
			curGame.setWallMoveCandidate(null);
		} else if (curPlayer.equals(curGame.getBlackPlayer())) {
			WallMove curWallMove = curGame.getWallMoveCandidate();
			Wall wallInHand = curWallMove.getWallPlaced();
			curGame.getCurrentPosition().addBlackWallsInStock(wallInHand);
			curGame.removeMove(curWallMove);
			curGame.setWallMoveCandidate(null);
		}
	}
	/**
	 * Perform a move operation on a currently selected wall Gherkin Feature 7:
	 * MoveWall.feature
	 * 
	 * @author Le-Li Mao
	 * @param side
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void moveWall(TOWall.Side side) throws GameNotRunningException, InvalidOperationException {
		// check if the Game is running if not throw exception
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		// check if the it is player's turn if not throw exception
		// check if there is wall in my hand if not throw exception
		if (curGame.getWallMoveCandidate() == null)
			throw new InvalidOperationException("No wall Selected");
		WallMove candidate = curGame.getWallMoveCandidate();
		// check if newRow and newCol are within the board if not throw exception
		int newRow = candidate.getTargetTile().getRow()
				+ (side == TOWall.Side.Up ? -1 : side == TOWall.Side.Down ? 1 : 0);
		int newCol = candidate.getTargetTile().getColumn()
				+ (side == TOWall.Side.Left ? -1 : side == TOWall.Side.Right ? 1 : 0);
		if (!isWallPositionValid(newRow, newCol))
			throw new InvalidOperationException("Illegal Move");
		// update the move candidate according to the change.
		candidate.setTargetTile(getTile(newRow, newCol));
	}

	/**
	 * Perform a drop wall Operation that drop the currently held wall Gerkin
	 * Feature 8: DropWall.feature
	 * @author Le-Li Mao
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void dropWall() throws GameNotRunningException, InvalidOperationException {
		// check if the Game is running if not throw exception
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		// check if there is wall in my hand if not throw exception
		if (curGame.getWallMoveCandidate() == null)
			throw new InvalidOperationException("No wall Selected");
		// validate the position
		if (!validatePosition()) {
			throw new InvalidOperationException("Invalid Move");
		}
		// put the path as an external command in the dropWall feature
		hasPath();
		// finalize drop by putting the move into the movelist.
		Wall wallToDrop = curGame.getWallMoveCandidate().getWallPlaced();
		GamePosition currentPosition = curGame.getCurrentPosition();
		GamePosition clone = clonePosition(currentPosition);

		if (isWhitePlayer()) {
			currentPosition.addWhiteWallsInStock(wallToDrop);
			clone.addWhiteWallsOnBoard(wallToDrop);
		} else {
			currentPosition.addBlackWallsInStock(wallToDrop);
			clone.addBlackWallsOnBoard(wallToDrop);
		}
		curGame.setCurrentPosition(clone);
		curGame.addMove(curGame.getWallMoveCandidate());
		curGame.setWallMoveCandidate(null);
		// Switch Player here
		SwitchPlayer();
	}

	/**
	 * Feature 9: Save the current position as a file in the filesystem
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 * @return true if the position was saved, otherwise returns false
	 * @throws IOException
	 */
	// TODO: Feature 9: Save Position
	public static boolean savePosition(String filename) throws IOException {
		// GUI: register button press of the save game button
		// ie: this method is called when a player clicks the save game button in the
		// save game dialog box
		// the user types the filename into a dialog box and then clicks the save button
		// if the file is invalid, prompt the user to write a valid path
		// if the file exists already, prompt the user to agree to overwrite the file
		boolean doesFileExist = false;
		boolean savedPosition = false;
		String relPath = "./src/main/resources/gameFiles/" + filename;
		
		//check if file exists
		doesFileExist = checkIfFileExists(relPath);

		// if the File exists
		if (doesFileExist) {
			savedPosition = writeToExistingFile(relPath);
		} else {
			savedPosition = writeToNewFile(relPath);
		}

		return savedPosition;
	}

	/**
	 * Feature 10: Load a new position from a file in the filesystem
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 * @throws IOException 
	 */
	// TODO: Feature 10: Load Position
	public static boolean loadPosition(String filename) throws IOException {
		//System.out.println("called load position");
		
		boolean loadedPosition = false;
		String relPath = "./src/main/resources/gameFiles/" + filename;

		if(checkIfLoadFileIsValidFile(relPath)) {
			// add all the move data from the loadFile to the game
			loadedPosition = getLoadPositionDataFromFile(relPath);
		}
		return loadedPosition;
	}
	
	/**
	 * Feature 23: Save the current game as a file in the filesystem
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 * @return true if the game was saved, otherwise returns false
	 * @throws IOException
	 */
	// TODO: Feature 23: Save Game
	public static boolean saveGame(String filename) throws IOException {
	
		boolean doesFileExist = false;
		boolean savedGame = false;
		String relPath = "./src/main/resources/gameFiles/" + filename;
		
		//check if file exists
		doesFileExist = checkIfFileExists(relPath);

		// if the File exists
		if (doesFileExist) {
			savedGame = saveGameToExistingFile(relPath);
		} else {
			savedGame = saveGameToNewFile(relPath);
		}
		return savedGame;
	}
	
	/**
	 * Feature 24: Load a new game from a file in the filesystem
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 * @return
	 * @throws IOException 
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 * @throws GameIsFinished 
	 * @throws GameIsDrawn 
	 */
	// TODO: Feature 24: Load Game
	public static boolean loadGame(String filename) throws IOException, GameNotRunningException, InvalidOperationException, GameIsDrawn, GameIsFinished {
		//System.out.println("called load position");
		
		boolean loadedGame = false;
		String relPath = "./src/main/resources/gameFiles/" + filename;

		if(checkIfLoadFileIsValidFile(relPath)) {
			// add all the move data from the loadFile to the game
			loadedGame = getLoadGameDataFromFile(relPath);
		}
		return loadedGame;
	}

	/**
	 * Feature 11: ValidatePosition, validate a wall position by checking overlapping walls and player position
	 * 
	 * @author Sacha Lévy
	 * @throws GameNotRunningException
	 * @throws UnsupportedOperationException
	 * @throws InvalidOperationException 
	 */
	public static boolean validatePosition() throws UnsupportedOperationException, GameNotRunningException, InvalidOperationException {
		if (!isRunning()) throw new GameNotRunningException("Game not running");
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		//TODO: better combine isWallCandidateOverlapping and doWallsOverlap, ie minimize search time
		//TODO: implement path checking when dropping walls
		//TODO: implement more specific error messages
		if (doWallsOverlap()) return false;
		if (current_game.hasWallMoveCandidate()) {
			if (!isWallCandidatePositionValid()) return false;
			if (isWallMoveCandidateOverlapping()) return false;
			//if (!hasPath()) return false;
		} else {
			// TODO: implement the pawn behavior checking within the validate position, uniform method
			if (!isPlayerPositionValid()) return false;
			if (isPlayerPositionOverlapping()) return false;
		}
		return true;
	}
	
	/**
	 * Feature 12: SwitchPlayer, switch the players after a move is completed (dropWall for the moment)
	 * 
	 * @author Sacha Lévy
	 * @throws GameNotRunningException
	 * @throws UnsupportedOperationException
	 */
	public static void SwitchPlayer() throws UnsupportedOperationException, GameNotRunningException {
		GamePosition current_position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Player current_player = current_position.getPlayerToMove();
		current_position.setPlayerToMove(current_player.getNextPlayer());
		current_player.getNextPlayer().setNextPlayer(current_player);
	}
	
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	//////////Iteration 5 Controller Methods Starts Here//////////
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////

	/**
	 * Feature 19: Resign Game
	 * This method will be called if a player wants to resign the game. Game status
	 * will be set correspondingly.
	 * 
	 * @author Andrew Ta
	 * @param playerName  name of player who wishes to resign the game
	 * @throws GameNotRunningException
	 * @throws GameIsFinished 
	 */
	public static void resignGame(String playerName) throws GameNotRunningException, GameIsFinished{
		if(!isRunning()) throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = getPlayerByName(playerName);
		if(curPlayer.equals(curGame.getBlackPlayer())) {
			curGame.setIsFinished(true);
			curGame.setGameStatus(GameStatus.WhiteWon);
		}else {
			curGame.setIsFinished(true);
			curGame.setGameStatus(GameStatus.BlackWon);
		}
		throw new GameIsFinished("Game Finished: "+curGame.getCurrentPosition().getPlayerToMove().getNextPlayer().getUser().getName() + " won. Congratulation!");
	}
	
	/**
	 * Feeture 17: Identify if game draw
	 * This method checks for a threefold repetion of moves. It there is one, 
	 * game status will be set to draw.
	 * 
	 * @author Andrew Ta
	 * @return returns a boolean to determine a game is draw or not
	 * @throws GameNotRunningException
	 */
	public static boolean identifyDraw() throws GameNotRunningException{
		if(!isRunning()) throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		List<Move> historyMoves = curGame.getMoves();
		
		if(historyMoves.size() < 9) return false;
		
		int n = historyMoves.size();
		if(checkMove(historyMoves.get(n-1), historyMoves.get(n-5)) 
			&& checkMove(historyMoves.get(n-5), historyMoves.get(n-9))
			&& checkMove(historyMoves.get(n-4), historyMoves.get(n-8))){
			curGame.setGameStatus(GameStatus.Draw);
			return true;
		};
		return false;
	}


	/**
	 * helper method to check if two tiles are the same
	 * @author Andrew Ta
	 * @param tile1 the first tile
	 * @param tile2 the second tile
	 * @return a boolean value
	 */
	private static boolean checkMove(Move move1, Move move2) {
		if((move1 instanceof WallMove ) || (move2 instanceof WallMove)) return false;
		if(move1.getTargetTile().equals(move2.getTargetTile())) return true;
		return false;
	}
	
	/**
	 * Performs the jump to the start position of the game
	 * @author Enan Ashaduzzaman
	 * @throws InvalidOperationException
	 */
	public static void jumpToStartPosition() throws InvalidOperationException {
		if (!isReplay())
			throw new InvalidOperationException("Game is not in replay mode");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition startPosition = curGame.getPosition(0);
		curGame.setCurrentPosition(startPosition);
	}

	/**
	 * @author Vanessa Ifrah
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void enterReplayMode() throws GameNotRunningException, InvalidOperationException {
		if (!isReplayPossible()) throw new InvalidOperationException("Unable to replay");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		
		if(curGame.getWallMoveCandidate()!=null) {
			throw new InvalidOperationException("Cannot enter Replay Mode with wall in hand");
		}
		
		curGame.setGameStatus(GameStatus.Replay);
		
	}
	
	/**
	 * @author Le-Li Mao
	 * @throws InvalidOperationException
	 */
	public static void exitReplayMode() throws InvalidOperationException{
		if (!isReplay())throw new InvalidOperationException("Not in replay mode.");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		int size = curGame.getPositions().size()-1;
		if(curGame.getIsFinished()) {
			curGame.setCurrentPosition(curGame.getPosition(size));
			throw new InvalidOperationException("Game Finished: Can't Continue Game");
		}
		curGame.setGameStatus(GameStatus.Running);
		int ind = findPositionIndex();
		for(int i = ind; i<size;i++) {
			Move move = curGame.getMove(ind);
			move.delete();
			curGame.removeMove(move);
			GamePosition pos = curGame.getPosition(ind+1);
			pos.delete();
		}
		QuoridorApplication.CreateNewWhitePawnBehavior().startGame();
		QuoridorApplication.CreateNewBlackPawnBehavior().startGame();
		
	}
	/**
	 * Performs the jump to the final position
	 * @author Enan Ashaduzzaman
	 * @throws InvalidOperationException
	 */
	public static void jumpToFinalPosition() throws InvalidOperationException {
		if (!isReplay())
			throw new InvalidOperationException("Game is not in replay mode");
	
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		int finalIndex = curGame.getPositions().size() - 1;
		GamePosition finalPosition = curGame.getPosition(finalIndex);
		curGame.setCurrentPosition(finalPosition);
	}
	
	/**
	 * Perform a step forward in the replay mode
	 * @author Le-Li Mao
	 * @throws InvalidOperationException
	 */
	public static void StepForward() throws InvalidOperationException {
		if (!isReplay())throw new InvalidOperationException("Game is not in replay mode");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		int ind = findPositionIndex();
		if(ind==curGame.getPositions().size()-1)throw new InvalidOperationException("Already at the last step");
		curGame.setCurrentPosition(curGame.getPosition(ind+1));
	}

	/**
	 * Perform a step backward in the replay mode
	 * @author Le-Li Mao
	 * @throws InvalidOperationException
	 */
	public static void StepBackward() throws InvalidOperationException {
		if (!isReplay())throw new InvalidOperationException("Game is not in replay mode");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		int ind = findPositionIndex();
		if(ind==0)throw new InvalidOperationException("Already at the first step");
		curGame.setCurrentPosition(curGame.getPosition(ind-1));
	}
	
	/**
	 * @author Sacha Lévy 
	 * @author Le-li Mao
	 * perform check to validate a wall move have path to allow player to pass through
	 * @throws InvalidOperationException
	 */
	public static void hasPath() throws InvalidOperationException {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		int[][] wallMap = new int[9][9];
		final int up = 1;
		final int left = 2; 
		final int down = 4;
		final int right = 8;
		for(int i=0; i<9; i++) {		// rows
			for(int j=0; j<9; j++) {	// cols
				// fill with edge cases: borders of the map
				if(i==0)wallMap[i][j]|=up;
				if(i==8)wallMap[i][j]|=down;
				if(j==0)wallMap[i][j]|=left;
				if(j==8)wallMap[i][j]|=right;
			}
		}
		// add the wall candidate to the walls on board to check if it is blocking the path
		WallMove move_candidate = current_game.getWallMoveCandidate();
		addWall(wallMap,move_candidate);
		
		for(Move move: current_game.getMoves()) {
			if(move instanceof WallMove) {
				addWall(wallMap,(WallMove) move);
			}
		}
		int whiteRow = current_game.getCurrentPosition().getWhitePosition().getTile().getRow()-1;
		int whiteCol = current_game.getCurrentPosition().getWhitePosition().getTile().getColumn()-1;
		int blackRow = current_game.getCurrentPosition().getBlackPosition().getTile().getRow()-1;
		int blackCol = current_game.getCurrentPosition().getBlackPosition().getTile().getColumn()-1;
		
		boolean black = checkMazeMap(8, wallMap, blackRow, blackCol);
		boolean white = checkMazeMap(0, wallMap, whiteRow, whiteCol);
		if (!white&&!black) {
			throw new InvalidOperationException("both are blocked");
		}
		else if(!white) {
			throw new InvalidOperationException("white is blocked");
		}
		else if (!black) {
			throw new InvalidOperationException("black is blocked");
		}
	}
	
	/**
	 * Add the wall by updating the matrix created to simulate a maze environment
	 * @author Sacha Lévy 
	 * @author Le-li Mao
	 * @param mazeMap
	 * @param wall
	 */
	private static void addWall(int[][] mazeMap, WallMove wall) {
		int row = wall.getTargetTile().getRow()-1;
		int col = wall.getTargetTile().getColumn()-1;
		final int up = 1;
		final int left = 2; 
		final int down = 4;
		final int right = 8;
		if(wall.getWallDirection()==Direction.Horizontal) {
			// the wall is horizontal
			mazeMap[row][col]|=down;
			mazeMap[row][col+1]|=down;
			mazeMap[row+1][col]|=up;
			mazeMap[row+1][col+1]|=up;
		}
		else {
			// the wall is vertical
			mazeMap[row][col]|=right;
			mazeMap[row+1][col]|=right;
			mazeMap[row][col+1]|=left;
			mazeMap[row+1][col+1]|=left;
		}
	}
	
	// exhaustive process since only need to check if can cut the board in two
	/**
	 * @author Sacha Lévy 
	 * @author Le-li Mao
	 * @param goal_row
	 * @param mazeMap
	 * @param i
	 * @param j
	 * @return whether the path exist
	 */
	public static boolean checkMazeMap(int goal_row, int[][] mazeMap, int i, int j) {
		boolean[][] visited = new boolean[9][9];
		final int up = 1;
		final int left = 2; 
		final int down = 4;
		final int right = 8;
		Queue<int[]> list= new LinkedList<>();
		list.add(new int[] {i,j});
		while(!list.isEmpty()) {
			int[]cord = list.remove();
			i = cord[0];
			j = cord[1];
			visited[i][j]=true;
			if(i==goal_row)return true;
			if((mazeMap[i][j]&up)==0 && isCordValid(visited,i-1,j))list.add(new int[] {i-1,j});
			if((mazeMap[i][j]&down)==0 && isCordValid(visited,i+1, j))list.add(new int[] {i+1,j});
			if((mazeMap[i][j]&left)==0 && isCordValid(visited,i,j-1))list.add(new int[] {i,j-1});
			if((mazeMap[i][j]&right)==0 && isCordValid(visited,i, j+1))list.add(new int[] {i,j+1});
		}
		return false;
	}
	
	/**
	 * check if a coordinate is visited or out of bound
	 * @author Sacha Lévy 
	 * @author Le-li Mao
	 * @param visited
	 * @param i
	 * @param j
	 * @return is coordinate valid
	 */
	private static boolean isCordValid(boolean[][] visited, int i, int j) {
		if(i<0||j<0||i>8||j>8)return false;
		if(visited[i][j])return false;
		return true;
	}
	
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Sacha's Helper and Query methods Begins////////
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
	
	/**
	 * helper method to get validity of board position for cucumber stepDef
	 * 
	 * @author Sacha Lévy
	 * @return isValid
	 * @throws InvalidOperationException 
	 * */
	public static String isPositionValid() throws InvalidOperationException {
		String isValid = "invalid";
		try {
			if (validatePosition()) isValid = "valid";
			else isValid = "invalid";
		} catch (UnsupportedOperationException | GameNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return isValid;
	} 

	/**
	 * query method to get name of a player based on its color
	 * i.e. username for black/white players
	 * 
	 * @param color
	 * @author Sacha Lévy
	 * @return String
	 * */
	public static String getPlayerNameByColor(String color) {
		Player player = getPlayerByColor(color);
		return player.getUser().getName();
	}
	
	/**
	 * method to set the position of a Pawn on board
	 * helper method for cucumber step definition ValidatePosition
	 * 
	 * @param int1, int2
	 * @author Sacha Lévy
	 * @return boolean
	 * */
	public static boolean setGamePawnPosition(Integer int1, Integer int2) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition current_position = current_game.getCurrentPosition();
		Board current_board = QuoridorApplication.getQuoridor().getBoard();
		// arbitrarily get the black position
		PlayerPosition current_player_pos = current_position.getBlackPosition();
		Tile current_black_tile = getTile(int1, int2);
		return current_player_pos.setTile(current_black_tile);
	}
	
	/**
	 * method to simulate displacement of a wall on board to be checked by validatePosition
	 * helper method for cucumber step definition ValidatePosition
	 * 
	 * @param int1, int2, dir
	 * @author Sacha Lévy
	 * @return wasWallSetAsNewWallCandidate
	 * */
	public static boolean createNewWallMoveCandidate(Integer int1, Integer int2, Direction dir) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition current_position = current_game.getCurrentPosition();
		Board current_board = QuoridorApplication.getQuoridor().getBoard();
		
		if(current_game.hasWallMoveCandidate()) {
			// then drop the preceeding wall
			Wall wallToDrop = current_game.getWallMoveCandidate().getWallPlaced();
			GamePosition clone = clonePosition(current_position);
			
			current_game.setCurrentPosition(clone);
			if (isWhitePlayer()) {
				clone.addWhiteWallsOnBoard(wallToDrop);
			} else {
				clone.addBlackWallsOnBoard(wallToDrop);
			}
			current_game.addMove(current_game.getWallMoveCandidate());
			current_game.setWallMoveCandidate(null);
		}
		
		int moveLength = current_game.getMoves().size();
		int moveNum;
		int roundNum;

		if (moveLength > 0) {
			moveNum = current_game.getMoves().get(moveLength - 1).getMoveNumber();
			roundNum = current_game.getMoves().get(moveLength - 1).getRoundNumber();
		} else {
			moveNum = 0;
			roundNum = 0;
		}
		// work with black walls
		Wall curWall = current_game.getCurrentPosition().getBlackWallsInStock(0);
		current_game.getCurrentPosition().removeBlackWallsInStock(curWall);
		Tile target_tile = getTile(int1, int2);
		WallMove curWallMove = new WallMove(moveNum, roundNum + 1, current_game.getBlackPlayer(), target_tile, current_game, dir, curWall);
		return current_game.setWallMoveCandidate(curWallMove);		
	}
	/**
	 * query method to get a player based on its color
	 * 
	 * @param color
	 * @author Sacha Lévy
	 * @return playerByColor
	 * */
	public static Player getPlayerByColor(String color) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		if(color.equals("black")) return current_game.getBlackPlayer();
		else return current_game.getWhitePlayer();
	}
	
	/**
	 * query method to get the current player moving
	 * 
	 * @author Sacha Lévy
	 * @return currentPlayer
	 * */
	public static Player getPlayerMoving() {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		return current_game.getCurrentPosition().getPlayerToMove();
	}
	
	/**
	 * helper method to set player to move based on its color
	 * 
	 * @param color
	 * @author Sacha Lévy
	 * @return wasThePlayerSet
	 * */
	public static boolean setCurrentPlayerToMoveByColor(String color) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player player_to_move = getPlayerByColor(color);
		Player next_player;
		if(player_to_move.equals(current_game.getBlackPlayer())) next_player = current_game.getWhitePlayer();
		else next_player = current_game.getBlackPlayer();
		player_to_move.setNextPlayer(next_player);
		return current_game.getCurrentPosition().setPlayerToMove(player_to_move);
	}

	/**
	 * @param newRow, newCol
	 * @author Sacha Lévy
	 * @return isPawnMoveLegal
	 * */
	public static boolean isPawnMoveLegal(int newRow, int newCol) throws InvalidOperationException{
		Tile other_position;
		Tile current_position;
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		if(isWhitePlayer()) {
			other_position = current_game.getCurrentPosition().getBlackPosition().getTile();
			current_position = current_game.getCurrentPosition().getWhitePosition().getTile();
		}
		else {
			other_position = current_game.getCurrentPosition().getWhitePosition().getTile();
			current_position = current_game.getCurrentPosition().getBlackPosition().getTile();
		} 
		// if the two pawns are over each other
		if (other_position.getRow()==newRow && other_position.getColumn()==newCol) return false;
		int curRow = current_position.getRow();
		int curCol = current_position.getColumn();
		
		// get the current position of the player & determine which wall has been crossed
		Map<Integer, Boolean> wallPositions = loadWallPositionsMap();
		for (int cur_key : wallPositions.keySet()) {
			// if same row/col means move was vertical/horizontal
			if(curRow==newRow && wallPositions.get(cur_key).equals(Direction.Vertical) && cur_key==curRow*9+curCol) return false;
			if(curCol==newCol && wallPositions.get(cur_key).equals(Direction.Horizontal) && cur_key==curRow*9+curCol) return false;
		}
		
		return true;
	}
	/**
	 * check if there is a WallMoveCandidate
	 * 
	 * @author Sacha Lévy
	 */
	public static boolean hasWallMoveCandidate() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		return game.hasWallMoveCandidate();
	}

	/**
	 * check if in present gamePosition black & white players are overlapping
	 * i.e. share the same tile (assumes the players would have made the move effective)
	 * 
	 * @author Sacha Lévy
	 */
	public static boolean isPlayerPositionOverlapping() {
		GamePosition game_position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Tile blackPosition = game_position.getBlackPosition().getTile();
		Tile whitePosition = game_position.getWhitePosition().getTile();
		if (whitePosition.equals(blackPosition))
			return true;
		else
			return false;
	}
	/**
	 * check if the player position exists on board
	 * 
	 * @author Sacha Lévy
	 * */
	private static boolean isPlayerPositionValid(){
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition current_position = current_game.getCurrentPosition();
		PlayerPosition player_position;
		if(isWhitePlayer()) player_position = current_position.getWhitePosition();
		else player_position = current_position.getBlackPosition();
		int row = player_position.getTile().getRow();
		int col = player_position.getTile().getColumn();
		return row>=1&&row<=9&&col>=1&&col<=9;
	}

	/**
	 * check if wall move candidate overlaps with any walls on board, i.e. there already is a wall at this position
	 * check for adjacent walls with same directions, therefore creating an overlap
	 * 
	 * @author Sacha Lévy
	 * @throws InvalidOperationException 
	 */
	public static boolean isWallMoveCandidateOverlapping(){
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();	
		// wall position hash map:
		// keys : row*9 + col
		// values : horizontal wall is true, vertical wall is false
		// board is 9 by 9
		HashMap<Integer, Boolean> wallPositions = new HashMap<Integer, Boolean>();
		try {
			wallPositions = loadWallPositionsMap();
		} catch (InvalidOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WallMove move_candidate = current_game.getWallMoveCandidate();
		int candidate_row = move_candidate.getTargetTile().getRow();
		int candidate_col = move_candidate.getTargetTile().getColumn();
		int cand_key = candidate_row * 9 + candidate_col;
		boolean cand_dir = move_candidate.getWallDirection().equals(Direction.Horizontal) ? true : false;
		int adj_wall0 = cand_dir ? (candidate_row) * 9 + candidate_col - 1 : (candidate_row - 1) * 9 + candidate_col;
		int adj_wall1 = cand_dir ? (candidate_row) * 9 + candidate_col + 1 : (candidate_row + 1) * 9 + candidate_col;
		if(wallPositions.containsKey(cand_key))return true;
		if(wallPositions.containsKey(adj_wall0)&&wallPositions.get(adj_wall0)==cand_dir)return true;
		if(wallPositions.containsKey(adj_wall1)&&wallPositions.get(adj_wall1)==cand_dir)return true;
		return false;
	}
	
	/**
	 * check if the walls on board overlapp
	 * 
	 * @author Sacha Lévy
	 * @return doWallsOnBoardOverlap
	 * */
	public static boolean doWallsOverlap() {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();	
		HashMap<Integer, Boolean> wallPositions = new HashMap<Integer, Boolean>();
		for(Wall wall: current_game.getCurrentPosition().getBlackWallsOnBoard()) {
			if(wall.getMove().equals(current_game.getWallMoveCandidate()))continue;
			int row = wall.getMove().getTargetTile().getRow();
			int col = wall.getMove().getTargetTile().getColumn();
			if(wallPositions.containsKey(row*9+col))return true;
			int row1 = row;
			int row2 = row;
			int col1 = col;
			int col2 = col;
			boolean dir = true;
			if(wall.getMove().getWallDirection()==Direction.Horizontal) {
				col1+=1;
				col2-=1;
			}
			else {
				row1+=1;
				row2-=1;
				dir =false;
			}
			if(wallPositions.containsKey(row1*9+col1)&&wallPositions.get(row1*9+col1)==dir)return true;
			if(wallPositions.containsKey(row2*9+col2)&&wallPositions.get(row2*9+col2)==dir)return true;
			wallPositions.put(row*9+col,dir);
		}
		for(Wall wall: current_game.getCurrentPosition().getWhiteWallsOnBoard()) {
			if(wall.getMove().equals(current_game.getWallMoveCandidate()))continue;
			int row = wall.getMove().getTargetTile().getRow();
			int col = wall.getMove().getTargetTile().getColumn();
			if(wallPositions.containsKey(row*9+col))return true;
			int row1 = row;
			int row2 = row;
			int col1 = col;
			int col2 = col;
			boolean dir = true;
			if(wall.getMove().getWallDirection()==Direction.Horizontal) {
				col1+=1;
				col2-=1;
			}
			else {
				row1+=1;
				row2-=1;
				dir =false;
			}
			if(wallPositions.containsKey(row1*9+col1)&&wallPositions.get(row1*9+col1)==dir)return true;
			if(wallPositions.containsKey(row2*9+col2)&&wallPositions.get(row2*9+col2)==dir)return true;
			wallPositions.put(row*9+col,dir);
		}
		return false;
	}
	
	
	/**
	 * create a hashmap for the walls on board to check
	 * 
	 * @author Sacha Lévy
	 * @return wallPositions
	 * */
	public static HashMap<Integer, Boolean> loadWallPositionsMap() throws InvalidOperationException{
		HashMap<Integer, Boolean> wallPositions = new HashMap<Integer, Boolean>();
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		//TODO: switch to player list (more convenient if 2+)
		for (Wall wall : current_game.getCurrentPosition().getBlackWallsOnBoard()) {
			if(current_game.hasWallMoveCandidate()) {
				if(wall.equals(current_game.getWallMoveCandidate().getWallPlaced()))continue;
			}
			WallMove wall_move = wall.getMove();
			int row = wall_move.getTargetTile().getRow();
			int col = wall_move.getTargetTile().getColumn();
			boolean dir_attr = false;
			if (wall_move.getWallDirection().equals(Direction.Horizontal))
				dir_attr = true;
			if (wallPositions.containsKey(row*9+col)) throw new InvalidOperationException("Cannot add to 2walls on the same position");
			wallPositions.put(row * 9 + col, dir_attr);
		}
		for (Wall wall : current_game.getCurrentPosition().getWhiteWallsOnBoard()) {
			if(current_game.hasWallMoveCandidate()) {
				if(wall.equals(current_game.getWallMoveCandidate().getWallPlaced()))continue;
			}
			WallMove wall_move = wall.getMove();
			int row = wall_move.getTargetTile().getRow();
			int col = wall_move.getTargetTile().getColumn();
			boolean dir_attr = false;
			if (wall_move.getWallDirection().equals(Direction.Horizontal))
				dir_attr = true;
			if (wallPositions.containsKey(row*9+col)) throw new InvalidOperationException("Cannot add to 2walls on the same position");
			wallPositions.put(row * 9 + col, dir_attr);
		}
		return wallPositions;
	}

	/**
	 * check if the wall position exists on the board
	 * 
	 * @author Sacha Lévy
	 * @return isWallCandidatePositionValid
	 * */
	private static boolean isWallCandidatePositionValid() throws UnsupportedOperationException{
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (!current_game.hasWallMoveCandidate()) {
			throw new UnsupportedOperationException("Game does not have a wall move candidate.");
		}
		Tile target_tile = current_game.getWallMoveCandidate().getTargetTile();
		return isWallPositionValid(target_tile.getRow(), target_tile.getColumn());
	}
	
	/////////////////////////////////////////////////////
	//////////// Move Pawn and Jump Pawn/////////////////
	/////////////////////////////////////////////////////
	
	/**
	 * @author Enan Ashaduzzaman
	 * 
	 * @param side
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	public static void movePawn(TOPlayer.Side side) throws GameNotRunningException, InvalidOperationException,
		GameIsDrawn, GameIsFinished{
		//call specific behaviour and call the specific move that is received by the player
		if (!isRunning())
			throw new GameNotRunningException("Game not running");
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		
		if (curGame.getWallMoveCandidate() != null) {
			throw new InvalidOperationException("Cannot move pawn since there is a wall in hand.");
		}
		Player curPlayer = curGame.getCurrentPosition().getPlayerToMove();
	
		 
		if (curPlayer.equals(curGame.getWhitePlayer())) {
			PawnBehavior whiteBehavior = QuoridorApplication.GetWhitePawnBehavior();
			if (side == TOPlayer.Side.Up) {
				if(!whiteBehavior.moveUp()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Down) {
				if(!whiteBehavior.moveDown()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Left) {
				if(!whiteBehavior.moveLeft()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Right) {
				if(!whiteBehavior.moveRight()) {
					throw new InvalidOperationException("Illegal Move");
				};
			} else if (side == TOPlayer.Side.DownLeft) {
				if(!whiteBehavior.moveDownLeft()) {
					throw new InvalidOperationException("Illegal Move");
				};
			} else if (side == TOPlayer.Side.DownRight) {
				if(!whiteBehavior.moveDownRight()) {
					throw new InvalidOperationException("Illegal Move");
				};
			} else if (side == TOPlayer.Side.UpLeft) {
				if(!whiteBehavior.moveUpLeft()) {;
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.UpRight) {
				if(!whiteBehavior.moveUpRight()) {
					throw new InvalidOperationException("Illegal Move");
				};
			} 
		} else if (curPlayer.equals(curGame.getBlackPlayer())) {
			PawnBehavior blackBehavior = QuoridorApplication.GetBlackPawnBehavior();
			if (side == TOPlayer.Side.Up) {
				if(!blackBehavior.moveUp()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Down) {
				if(!blackBehavior.moveDown()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Left) {
				if(!blackBehavior.moveLeft()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.Right) {
				if(!blackBehavior.moveRight()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.DownLeft) {
				if(!blackBehavior.moveDownLeft()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.DownRight) {
				if(!blackBehavior.moveDownRight()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.UpLeft) {
				if(!blackBehavior.moveUpLeft()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} else if (side == TOPlayer.Side.UpRight) {
				if(!blackBehavior.moveUpRight()) {
					throw new InvalidOperationException("Illegal Move");
				}
			} 
		}

		if(identifyDraw()) {
			curGame.setIsFinished(true);
			throw new GameIsDrawn("Game Finished: Game Draw!");
		}
		if(identifyWin()) {
			curGame.setIsFinished(true);
			throw new GameIsFinished("Game Finished: "+ curGame.getCurrentPosition().getPlayerToMove().getUser().getName() + " won. Congratulations!");
		}
		SwitchPlayer();
	}
	
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	//////////// Helper and Query methods////////////////
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * @return  whiteplayer remaining time
	 * */
	public static Time getWhiteRemainingTime() {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (!current_game.hasWhitePlayer())
			return null;
		Player white_player = current_game.getWhitePlayer();
		return white_player.getRemainingTime();
	}

	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * @param whitePlayerNewName
	 * */
	public static void setWhitePlayerName(String new_name) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		current_game.getWhitePlayer().getUser().setName(new_name);
	}

	/**
	 * Query methods for the UI
	 * 
	 * */
	public static void setBlackPlayerName(String new_name) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		current_game.getBlackPlayer().getUser().setName(new_name);
	}
	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static String getBlackPlayerName() {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		return current_game.getBlackPlayer().getUser().getName();
	}
	
	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static String getWhitePlayerName() {

		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		return current_game.getWhitePlayer().getUser().getName();
	}
	
	/**
	 * setter method fro cucumber step definition
	 * 
	 * @author Sacha Lévy
	 * */
	public static void setCurrentPlayerToMove(Player player) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		current_game.getCurrentPosition().setPlayerToMove(player);
	}
	
	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static String getPlayerName(Player player) {
		return player.getUser().getName();
	}
	
	/**
	 * helper method to get name of player
	 * 
	 * @author Sacha Lévy
	 * */
	public static void setWhitePlayerTime(Time new_time) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		current_game.getWhitePlayer().setRemainingTime(new_time);
	}

	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static void setBlackPlayerTime(Time new_time) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		current_game.getBlackPlayer().setRemainingTime(new_time);
	}

	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static Time getBlackRemainingTime() {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		if(!current_game.hasBlackPlayer()) return null;
		Player black_player = current_game.getBlackPlayer();
		return black_player.getRemainingTime();
	}

	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static String getPlayerMovingName() {
		GamePosition current_position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		return current_position.getPlayerToMove().getUser().getName();
	}

	/**
	 * Query methods for the UI
	 * 
	 * @author Sacha Lévy
	 * */
	public static String getPlayerToMoveName() {
		GamePosition current_position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		return current_position.getPlayerToMove().getUser().getName();
	}
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Sacha's Helper and Query methods Ends
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////


/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Le-Li's Helper and Query methods
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////

	/**
	 * Check if the game is running
	 * @author Le-Li Mao
	 * @return game the is game running
	 */
	public static boolean isRunning() {
		Game current = QuoridorApplication.getQuoridor().getCurrentGame();
		if (current == null || current.getGameStatus()!=Game.GameStatus.Running)
			return false;

		return true;
	}

	/**
	 * Check if wall row and column is valid
	 * @author Le-Li Mao
	 * @param row
	 * @param col
	 * @return Is position valid 
	 */
	public static boolean isWallPositionValid(int row, int col) {
		return (row > 0 && col > 0 && row < 9 && col < 9);
	}

	/**
	 * Get the tile based on row and col
	 * @author Le-Li Mao
	 * @param row
	 * @param col
	 * @return tile of the given row and column
	 */
	public static Tile getTile(int row, int col) {
		Board board = QuoridorApplication.getQuoridor().getBoard();
		return board.getTile((row - 1) * 9 + (col - 1));
	}

	/**
	 * Check if the current player to move white player
	 * @author Le-Li Mao
	 * @return is current player white player
	 */
	private static boolean isWhitePlayer() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getPlayerToMove().equals(curGame.getWhitePlayer());
	}

	/**
	 * Get the name of the current player
	 * @author Le-Li Mao
	 * @return the game of the current player
	 */
	public static String getCurrentPlayerName() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return isWhitePlayer() ? curGame.getWhitePlayer().getUser().getName()
				: curGame.getBlackPlayer().getUser().getName();
	}

	/**
	 * Get the number of white wall on in stock
	 * @author Le-Li Mao
	 * @return the number of wall in stock for white player
	 */
	public static int getWhiteWallInStock() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getWhiteWallsInStock().size();
	}
	
	/**
	 * Get the number of black wall on in stock
	 * @author Le-Li Mao
	 * @return the number of wall in stock for black player
	 */
	public static int getBlackWallInStock() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		return curGame.getCurrentPosition().getBlackWallsInStock().size();
	}

	/**
	 * Get the white wall on board's transfer objects
	 * @author Le-Li Mao
	 * @return a list of wall transfer object on the board belong to white player
	 */
	public static ArrayList<TOWall> getWhiteWallOnBoard() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		ArrayList<TOWall> wallList = new ArrayList<TOWall>();
		for (Wall wall : curGame.getCurrentPosition().getWhiteWallsOnBoard())
			wallList.add(convertWall(wall));
		return wallList;
	}

	/**
	 * Get the black wall on board's transfer objects
	 * @author Le-Li Mao
	 * @return a list of wall transfer objects on the board belong to black player
	 */
	public static ArrayList<TOWall> getBlackWallOnBoard() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		ArrayList<TOWall> wallList = new ArrayList<TOWall>();
		for (Wall wall : curGame.getCurrentPosition().getBlackWallsOnBoard())
			wallList.add(convertWall(wall));
		return wallList;
	}

	/**
	 * Get the wall transfer object of the wall in hand
	 * @author Le-Li Mao
	 * @return number of wall in stock
	 */
	public static TOWall getWallInHand() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if (curGame.getWallMoveCandidate() == null)return null;
		return convertWall(curGame.getWallMoveCandidate().getWallPlaced());
	}
	
	/**
	 * Get a list of player object on the board
	 * @author Le-Li Mao
	 * @return a list of player transfer objects on the board
	 */
	public static ArrayList<TOPlayer> getPlayers() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition position = curGame.getCurrentPosition().getBlackPosition();
		int row = position.getTile().getRow();
		int col = position.getTile().getColumn();
		ArrayList<TOPlayer> players = new ArrayList<TOPlayer>();
		players.add(new TOPlayer(row, col, TOPlayer.Color.Black));
		position = curGame.getCurrentPosition().getWhitePosition();
		row = position.getTile().getRow();
		col = position.getTile().getColumn();
		players.add(new TOPlayer(row, col, TOPlayer.Color.White));
		return players;
	}
	/**
	 *  @author Le-Li Mao
	 * @param aWall
	 * @return the converted wall transfer object
	 */
	private static TOWall convertWall(Wall aWall) {
		int row = aWall.getMove().getTargetTile().getRow();
		int col = aWall.getMove().getTargetTile().getColumn();
		int id = aWall.getId();
		TOWall.Direction dir = aWall.getMove().getWallDirection() == Direction.Horizontal ? TOWall.Direction.Horizontal
				: TOWall.Direction.Vertical;
		TOWall wall = new TOWall(id, row, col, dir);
		return wall;
	}

	/**
	 * Clone the game position
	 * @author Le-Li Mao
	 * @param oldPosition
	 * @return a copy of the new position
	 */
	private static GamePosition clonePosition(GamePosition oldPosition) {
		PlayerPosition newWhitePosition = clonePlayerPosition(oldPosition.getWhitePosition());
		PlayerPosition newBlackPosition = clonePlayerPosition(oldPosition.getBlackPosition());
		GamePosition newPosition = new GamePosition(oldPosition.getId() + 1, newWhitePosition, newBlackPosition,
				oldPosition.getPlayerToMove(), oldPosition.getGame());
		for (Wall wall : oldPosition.getBlackWallsInStock())
			newPosition.addBlackWallsInStock(wall);
		for (Wall wall : oldPosition.getWhiteWallsInStock())
			newPosition.addWhiteWallsInStock(wall);
		for (Wall wall : oldPosition.getBlackWallsOnBoard())
			newPosition.addBlackWallsOnBoard(wall);
		for (Wall wall : oldPosition.getWhiteWallsOnBoard())
			newPosition.addWhiteWallsOnBoard(wall);
		return newPosition;
	}

	/**
	 * Clone the player position for updating the player position
	 * @author Le-Li Mao
	 * @param playerPos
	 * @return the cloned player position
	 */
	private static PlayerPosition clonePlayerPosition(PlayerPosition playerPos) {
		return new PlayerPosition(playerPos.getPlayer(), playerPos.getTile());
	}
	
	private static int findPositionIndex() {
		GamePosition current = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		List<GamePosition> posList = QuoridorApplication.getQuoridor().getCurrentGame().getPositions();
		int i = 0;
		for(GamePosition cur: posList) {
			if(cur.equals(current))return i;
			i++;
		}
		return -1;
	}
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//////Le-Li's Helper and Query methods Ends
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////	
	
	/////////////////////////////////////////////////
	// TODO: Mitchell's helper and query methods
	/////////////////////////////////////////////////	
	
	/**
	 * Checks if file exists in the file system
	 * @author Mitchell Keeley
	 * @param filename
	 * @return file.exists()
	 */
	public static boolean checkIfFileExists(String filename) {
		File file = new File(filename);
		return file.exists();
	}
	
	/**
	 * Prompt the user for permission to overwrite the existing file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return overwriteApproved
	 */
	public static boolean userOverwritePrompt(String filename) {
		boolean overwriteApproved = false;

		// use UI to prompt user to overwrite the existing file filename
		if (GamePage.userOverwritePrompt(filename) == 0) {
			overwriteApproved = true;
		}

		// overwrite the existing file
		// this is done automatically when writing to the file

		return overwriteApproved;
	}

	/**
	 * Save the current position as a file
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws IOException
	 */
	public static boolean saveCurrentGamePositionAsFile(String filename) throws IOException {
		//System.out.println("called save position");
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame;
		GamePosition currentGamePosition;
		Player whitePlayer;
		Player blackPlayer;
		
		// fetch game, gamePosition and players from quoridor
		currentGame = quoridor.getCurrentGame();
		currentGamePosition = currentGame.getCurrentPosition();
		whitePlayer = currentGame.getWhitePlayer();
		blackPlayer = currentGame.getBlackPlayer();
		
		// initialize the player information strings to write to the file
		String whitePlayerData = "W: " + tileToString(currentGamePosition.getWhitePosition().getTile());
		String blackPlayerData = "B: " + tileToString(currentGamePosition.getBlackPosition().getTile());
		
		// add the wall positions
		for (Move move : currentGame.getMoves()) {
			// if the move is a wallMove
			if (move instanceof WallMove) {
				if (move.getPlayer().equals(whitePlayer)) {
					whitePlayerData = whitePlayerData.concat(", " + tileToString(move.getTargetTile())
							+ directionToString(((WallMove) move).getWallDirection()));
					// printWriter.printf("%s\n", whitePlayerData);
				} else if (move.getPlayer().equals(blackPlayer)) {
					blackPlayerData = blackPlayerData.concat(", " + tileToString(move.getTargetTile())
							+ directionToString(((WallMove) move).getWallDirection()));
					// printWriter.printf("%s\n", blackPlayerData);
				}
			}
		}
		
		// initialize the printWriter
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename));

		// if the next player to move is the White Player
		if (currentGamePosition.getPlayerToMove().equals(currentGamePosition.getGame().getWhitePlayer())) {
			printWriter.printf("%s\n", whitePlayerData);
			printWriter.printf("%s", blackPlayerData);
			// else the next player to move is the Black player
		} else {
			printWriter.printf("%s\n", blackPlayerData);
			printWriter.printf("%s", whitePlayerData);
		}
		printWriter.close();

		return true;
	}
	
	/**
	 * Save the current game as a file
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws IOException
	 */
	public static boolean saveCurrentGameAsFile(String filename) throws IOException {
		//System.out.println("called save position");
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		
		String saveGameData = "";
		List<Move> moveList = currentGame.getMoves();
		
		for (Move move : moveList) {
			// Note: model is wrong, so move and round number are opposite
			// getMove corresponds to "round" and getRound is either "move 1" or "move 2" of that "round"
			int roundNum = move.getMoveNumber();
			int moveNum = move.getRoundNumber();
			
			if (move instanceof WallMove) {
				if (moveNum == 1) {
					saveGameData = saveGameData.concat(roundNum + ". " + tileToString(move.getTargetTile())
						+ directionToString(((WallMove) move).getWallDirection()));
				} else {
					saveGameData = saveGameData.concat(" " + tileToString(move.getTargetTile())
					+ directionToString(((WallMove) move).getWallDirection()) + "\n");
				}
			}
			
			if (move instanceof StepMove || move instanceof JumpMove) {
				if (moveNum == 1) {
					saveGameData = saveGameData.concat(roundNum + ". " + tileToString(move.getTargetTile()));
				} else {
					saveGameData = saveGameData.concat(" " + tileToString(move.getTargetTile()) + "\n");
				}
			}
		}
		
		// initialize the printWriter
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
		printWriter.printf("%s", saveGameData);
		printWriter.close();

		return true;
	}

	/**
	 * Translate a tile into a save compatible string
	 * file
	 * @author Mitchell Keeley
	 * @param tile
	 * @return tileAsString
	 */
	public static String tileToString(Tile tile) {
		int asciiNumberOffset = 48;
		int asciiLetterOffset = 96;

		String tileString = "" + (char) (asciiLetterOffset + tile.getColumn())
				+ (char) (asciiNumberOffset + tile.getRow());

		return tileString;
	}

	/**
	 * Translate a Direction into a save compatible string
	 * @author Mitchell Keeley
	 * @param direction
	 * @return
	 */
	public static String directionToString(Direction direction) {
		if (direction.equals(Direction.Horizontal)) {
			return "h";
		} else {
			return "v";
		}
	}
	
	/**
	 * Translate a character into the corresponding Direction
	 * @author Mitchell Keeley
	 * @param letter
	 * @return Direction
	 */
	public static Direction charToDirection(char letter) {
		if (letter == 'h') {
			return Direction.Horizontal;
		} else {
			return Direction.Vertical;
		}
	}
	
	/**
	 * Write to an existing save file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return success or failure of operation
	 * @throws IOException
	 */
	public static boolean writeToExistingFile(String filename) throws IOException {
		boolean savefileUpdated = false;

		// verify the file is a file, and is writable
		// prompt the user to overwrite the file
		if (userOverwritePrompt(filename) == true) {
			// save the current GamePositon as the specified file
			savefileUpdated = saveCurrentGamePositionAsFile(filename);
		}

		return savefileUpdated;
	}
	
	/**
	 * Create and write to a new save file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return success or failure of operation
	 * @throws IOException
	 */
	public static boolean writeToNewFile(String filename) throws IOException {
		boolean saveFileCreated = false;
		File saveFile = new File(filename);
		
		if (saveFile.createNewFile() && saveFile.isFile()) {
			saveFileCreated = saveCurrentGamePositionAsFile(filename);
		}

		return saveFileCreated;
	}
	
	/**
	 * Check if the load file is a valid file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return fileValidity
	 */
	public static boolean checkIfLoadFileIsValidFile(String filename) {
		boolean fileValidity = false;		
		
		File loadFile = new File(filename);
		if (loadFile.isFile() && loadFile.canRead()) {
			fileValidity = true;
		}
		
		return fileValidity;
	}
	
	/**
	 * Save game to an existing save file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return success or failure of operation
	 * @throws IOException
	 */
	public static boolean saveGameToExistingFile(String filename) throws IOException {
		boolean savefileUpdated = false;

		// verify the file is a file, and is writable
		// prompt the user to overwrite the file
		if (userOverwritePrompt(filename) == true) {
			// save the current GamePositon as the specified file
			savefileUpdated = saveCurrentGameAsFile(filename);
		}

		return savefileUpdated;
	}
	
	/**
	 * Save game to a newly created save file
	 * @author Mitchell Keeley
	 * @param filename
	 * @return success or failure of operation
	 * @throws IOException
	 */
	public static boolean saveGameToNewFile(String filename) throws IOException {
		boolean saveFileCreated = false;
		File saveFile = new File(filename);
		
		if (saveFile.createNewFile() && saveFile.isFile()) {
			saveFileCreated = saveCurrentGameAsFile(filename);
		}

		return saveFileCreated;
	}
	
	/**
	 * Get the load position data from the specified file into the game
	 * @author Mitchell Keeley
	 * @param loadFile
	 * @return boolean result of operation
	 */
	public static boolean getLoadPositionDataFromFile(String loadFile) {		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		GamePosition currentGamePosition = currentGame.getCurrentPosition();
		Player whitePlayer = currentGame.getWhitePlayer();
		Player blackPlayer = currentGame.getBlackPlayer();
		Tile whiteTile;
		Tile blackTile;
		String saveFileFirstLine;
		String saveFileSecondLine;
		String[] whiteMoveData;
		String[] blackMoveData;
		
		int letterOffset = 9;
		int indexOfWhiteWallsPlaced = currentGamePosition.numberOfWhiteWallsOnBoard() -1;
		int indexOfBlackWallsPlaced = currentGamePosition.numberOfBlackWallsOnBoard() -1;
		
		try {
			// create a file reader
			BufferedReader fileReader = new BufferedReader(new FileReader(loadFile));

			// read the file
			saveFileFirstLine = fileReader.readLine();
			saveFileSecondLine = fileReader.readLine();
						
			// clean up resources
			fileReader.close();

		} catch (Exception e) {
			return false;
		}
		
		// clear the game moves, and place all the walls back in the player stocks to allow a new game to be loaded
		int numOfMoves = currentGame.getMoves().size()-1;
		while(numOfMoves >= 0) {
			Move move = currentGame.getMove(numOfMoves);
			if (move.getPlayer().equals(whitePlayer) && currentGamePosition.hasWhiteWallsOnBoard()) {
				Wall currrentWhiteWall = currentGamePosition.getWhiteWallsOnBoard(indexOfWhiteWallsPlaced);
				currentGamePosition.removeWhiteWallsOnBoard(currrentWhiteWall);
				currentGamePosition.addWhiteWallsInStock(currrentWhiteWall);
				indexOfWhiteWallsPlaced--;
			}
			if (move.getPlayer().equals(blackPlayer) && currentGamePosition.hasBlackWallsOnBoard()) {
				Wall currentBlackWall = currentGamePosition.getBlackWallsOnBoard(indexOfBlackWallsPlaced);
				currentGamePosition.removeBlackWallsOnBoard(currentBlackWall);
				currentGamePosition.addBlackWallsInStock(currentBlackWall);
				indexOfBlackWallsPlaced--;
			}
			currentGame.removeMove(move);
			move.delete();
			//currentGame.removeMove(move);
			numOfMoves--;
		}
		
		// if the first line of the file contains "W:"
		if(saveFileFirstLine.contains("W:")){
			/*whiteTile = new Tile(Character.getNumericValue(saveFileFirstLine.charAt(3))-letterOffset, 
					Character.getNumericValue(saveFileFirstLine.charAt(4)), board);
			blackTile = new Tile(Character.getNumericValue(saveFileSecondLine.charAt(3))-letterOffset, 
					Character.getNumericValue(saveFileSecondLine.charAt(4)), board);*/
			
			whiteTile = getTile(Character.getNumericValue(saveFileFirstLine.charAt(4)),
					Character.getNumericValue(saveFileFirstLine.charAt(3))-letterOffset);
			blackTile = getTile(Character.getNumericValue(saveFileSecondLine.charAt(4)),
					Character.getNumericValue(saveFileSecondLine.charAt(3))-letterOffset);
			
			whiteMoveData = saveFileFirstLine.split("(W: \\w\\w,\\s)|(B: \\w\\w,\\s)|(, )");
			blackMoveData = saveFileSecondLine.split("(W: \\w\\w,\\s)|(B: \\w\\w,\\s)|(, )");
			
			// check the black pawn position
			currentGamePosition.getBlackPosition().setTile(blackTile);
			currentGamePosition.setPlayerToMove(blackPlayer);
			if(!isPlayerPositionValid()) return false;
			if(isPlayerPositionOverlapping()) return false;
			
			// check the white pawn position
			currentGamePosition.getWhitePosition().setTile(whiteTile);
			currentGamePosition.setPlayerToMove(whitePlayer);
			if(!isPlayerPositionValid()) return false;
			if(isPlayerPositionOverlapping()) return false;
			
		} else {
			/*blackTile = new Tile(Character.getNumericValue(saveFileFirstLine.charAt(3))-letterOffset, 
					Character.getNumericValue(saveFileFirstLine.charAt(4)), board);
			whiteTile = new Tile(Character.getNumericValue(saveFileSecondLine.charAt(3))-letterOffset, 
					Character.getNumericValue(saveFileSecondLine.charAt(4)), board);*/
			
			blackTile = getTile((Character.getNumericValue(saveFileFirstLine.charAt(4))),
					Character.getNumericValue(saveFileFirstLine.charAt(3))-letterOffset);
			whiteTile = getTile((Character.getNumericValue(saveFileSecondLine.charAt(4))),
					Character.getNumericValue(saveFileSecondLine.charAt(3))-letterOffset);
			
			blackMoveData = saveFileFirstLine.split("(W: \\w\\w,\\s?)|(B: \\w\\w,\\s?)|(,\\s?)");
			whiteMoveData = saveFileSecondLine.split("(W: \\w\\w,\\s?)|(B: \\w\\w,\\s?)|(,\\s?)");
			
			// check the white pawn position
			currentGamePosition.getWhitePosition().setTile(whiteTile);
			currentGamePosition.setPlayerToMove(whitePlayer);
			if(!isPlayerPositionValid()) return false;
			if(isPlayerPositionOverlapping()) return false;
			
			// check the black pawn position
			currentGamePosition.getBlackPosition().setTile(blackTile);
			currentGamePosition.setPlayerToMove(blackPlayer);
			if(!isPlayerPositionValid()) return false;
			if(isPlayerPositionOverlapping()) return false;
		}
		
		// to remove null element at the beginning
		whiteMoveData = Arrays.copyOfRange(whiteMoveData, 1, whiteMoveData.length);
		blackMoveData = Arrays.copyOfRange(blackMoveData, 1, blackMoveData.length);
		
		// add all the moves for the white player
		indexOfWhiteWallsPlaced = 0;
		for (String position : whiteMoveData){
			// if position correspond to a wall position
			if (position.length() == 3){
				//System.out.println(position);
				
				// get the new Tile
				Tile newTile;
				try {
					newTile = getTile((Character.getNumericValue(position.charAt(1))),
							Character.getNumericValue(position.charAt(0))-letterOffset);
				} catch (IndexOutOfBoundsException e) {
					return false;
				}
				
				// initialize the current wall to be placed in the game
				Wall currentWall = currentGamePosition.getWhiteWallsInStock(9-indexOfWhiteWallsPlaced);
				currentWall.setMove(null);
				currentGamePosition.removeWhiteWallsInStock(currentWall);
				
				// create the new Wall move, (for white, the move# = #wallsPlaced*2 and round# = #wallsPlaced)
				WallMove newMove = new WallMove(indexOfWhiteWallsPlaced*2,indexOfWhiteWallsPlaced,whitePlayer,newTile,
						currentGame, charToDirection(position.charAt(2)), currentWall);
				
				// validate the new Wall move and add it to the game
				currentGame.setWallMoveCandidate(newMove);
				if(!isWallCandidatePositionValid()) return false;
				if(isWallMoveCandidateOverlapping()) return false;
				currentGame.setWallMoveCandidate(null);
				currentGame.addMove(newMove);
				currentGamePosition.addWhiteWallsOnBoard(currentWall);
				indexOfWhiteWallsPlaced++;
			}
		}
		
		// add all the moves for the black player
		indexOfBlackWallsPlaced = 0;
		for (String position : blackMoveData){
			if (position.length() == 3){
				//System.out.println(position);
				
				// get the new Tile
				Tile newTile;
				try {
					newTile = getTile((Character.getNumericValue(position.charAt(1))),
							Character.getNumericValue(position.charAt(0))-letterOffset);
				} catch (IndexOutOfBoundsException e) {
					return false;
				}
				
				// initialize the current wall to be placed in the game
				Wall currentWall = currentGamePosition.getBlackWallsInStock(9-indexOfBlackWallsPlaced);
				currentWall.setMove(null);
				currentGamePosition.removeBlackWallsInStock(currentWall);
				
				// create the new Wall move, (for black, the move# = #wallsPlaced*2+1 and round# = #wallsPlaced)
				WallMove newMove = new WallMove(indexOfBlackWallsPlaced*2+1,indexOfBlackWallsPlaced,blackPlayer,newTile,
						currentGame, charToDirection(position.charAt(2)), currentWall);
				
				// validate the new Wall move and add it to the game
				currentGame.setWallMoveCandidate(newMove);
				if(!isWallCandidatePositionValid()) return false;
				if(isWallMoveCandidateOverlapping()) return false;
				currentGame.setWallMoveCandidate(null);
				currentGame.addMove(newMove);
				currentGamePosition.addBlackWallsOnBoard(currentWall);
				indexOfBlackWallsPlaced++;
			}
		}
		return true;
	}
	
	/**
	 * Using the move data from the load file, attempt the pawn move
	 * @author Mitchell Keeley
	 * @param position
	 * @param playerPos
	 * @param colorBehavior
	 * @return boolean result of operation
	 * @throws InvalidOperationException 
	 */
	public static boolean loadGameTryPawnMove(String position, PlayerPosition playerPos, PawnBehavior colorBehavior) throws InvalidOperationException{
		
		// Position guide: letter is column, number is row, ex: b6
		// also need to account for letterOffset
		int letterOffset = 9;
		
		// UpLeft
		if(Character.getNumericValue(position.charAt(1)) < playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset < playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveUpLeft()) {
				return false;
			}
		}
		
		// Up
		else if(Character.getNumericValue(position.charAt(1)) < playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset == playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveUp()) {
				return false;
			}
		}
		
		// UpRight
		else if(Character.getNumericValue(position.charAt(1)) < playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset > playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveUpRight()) {
				return false;
			}
		}
		
		// Right
		else if(Character.getNumericValue(position.charAt(1)) == playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset > playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveRight()) {
				return false;
			};
		}
		
		// DownRight
		else if(Character.getNumericValue(position.charAt(1)) > playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset > playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveDownRight()) {
				return false;
			}
		}
		
		// Down
		else if(Character.getNumericValue(position.charAt(1)) > playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset == playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveDown()) {
				return false;
			}
		}
		
		// DownLeft
		else if(Character.getNumericValue(position.charAt(1)) > playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset < playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveDownLeft()) {
				return false;
			}
		}
		
		// Left
		else if(Character.getNumericValue(position.charAt(1)) == playerPos.getTile().getRow() && 
				Character.getNumericValue(position.charAt(0))-letterOffset < playerPos.getTile().getColumn()) {
			if(!colorBehavior.moveLeft()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Using the data from the load game file, attempt the wall move
	 * @author Mitchell Keeley
	 * @param position
	 * @param player
	 * @return boolean result of the operation
	 */
	public static boolean loadGameTryWallMove(String position, Player player) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		//GamePosition currentGamePosition = currentGame.getCurrentPosition();
		GamePosition currentGamePosition = clonePosition(currentGame.getCurrentPosition());
		currentGame.setCurrentPosition(currentGamePosition);
		Player whitePlayer = currentGame.getWhitePlayer();
		Player blackPlayer = currentGame.getBlackPlayer();
		
		int letterOffset = 9;
		int indexOfWhiteWallsPlaced = currentGamePosition.numberOfWhiteWallsOnBoard();
		int indexOfBlackWallsPlaced = currentGamePosition.numberOfBlackWallsOnBoard();
		
		// to account for the backwards definitions of moveNum and roundNum
		int moveLength= currentGame.getMoves().size();
	    int nMoveNumber = 1;
	    int nRoundNumber = 1;
	    if(moveLength!=0){
	    	Move lastMove = currentGame.getMove(moveLength-1);
	    	nRoundNumber = lastMove.getRoundNumber()%2+1;
	    	nMoveNumber = lastMove.getMoveNumber();
	    	if(nRoundNumber==1)nMoveNumber++;
	    }
		
	    // for when placing a white wall
		if (player.equals(whitePlayer)) {
			// get the new Tile
			Tile newTile;
			try {
				newTile = getTile((Character.getNumericValue(position.charAt(1))),
						Character.getNumericValue(position.charAt(0))-letterOffset);
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
						
			// initialize the current wall to be placed in the game
			Wall currentWall = currentGamePosition.getWhiteWallsInStock(9-indexOfWhiteWallsPlaced);
			currentWall.setMove(null);
			currentGamePosition.removeWhiteWallsInStock(currentWall);
			
			// create the new Wall move, (for white, the move# = #wallsPlaced*2 and round# = #wallsPlaced)
			WallMove newMove = new WallMove(nMoveNumber,nRoundNumber,whitePlayer,newTile,
					currentGame, charToDirection(position.charAt(2)), currentWall);
			
			// validate the new Wall move and add it to the game
			currentGame.setWallMoveCandidate(newMove);
			if(!isWallCandidatePositionValid()) return false;
			if(isWallMoveCandidateOverlapping()) return false;
			try {
				hasPath();
			} catch (InvalidOperationException e) {
				e.printStackTrace();
				return false;
			}
			currentGame.setWallMoveCandidate(null);
			currentGame.addMove(newMove);
			currentGamePosition.addWhiteWallsOnBoard(currentWall);
			indexOfWhiteWallsPlaced++;
		}
		
		// for when placing a black wall
		if (player.equals(blackPlayer)) {
			// get the new Tile
			Tile newTile;
			try {
				newTile = getTile((Character.getNumericValue(position.charAt(1))),
						Character.getNumericValue(position.charAt(0))-letterOffset);
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
			
			// initialize the current wall to be placed in the game
			Wall currentWall = currentGamePosition.getBlackWallsInStock(9-indexOfBlackWallsPlaced);
			currentWall.setMove(null);
			currentGamePosition.removeBlackWallsInStock(currentWall);
			
			// create the new Wall move, (for black, the move# = #wallsPlaced*2+1 and round# = #wallsPlaced)
			WallMove newMove = new WallMove(nMoveNumber,nRoundNumber,blackPlayer,newTile,
					currentGame, charToDirection(position.charAt(2)), currentWall);
			
			// validate the new Wall move and add it to the game
			currentGame.setWallMoveCandidate(newMove);
			if(!isWallCandidatePositionValid()) return false;
			if(isWallMoveCandidateOverlapping()) return false;
			try {
				hasPath();
			} catch (InvalidOperationException e) {
				e.printStackTrace();
				return false;
			}
			currentGame.setWallMoveCandidate(null);
			currentGame.addMove(newMove);
			currentGamePosition.addBlackWallsOnBoard(currentWall);
			indexOfBlackWallsPlaced++;
		}
		return true;
	}
	
	/**
	 * Get the load game data from the specified file into the game
	 * @author Mitchell Keeley
	 * @param loadFile
	 * @return boolean result of the operation
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 * @throws GameIsDrawn 
	 * @throws GameIsFinished 
	 */
	public static boolean getLoadGameDataFromFile(String loadFile) throws GameNotRunningException, InvalidOperationException, GameIsDrawn, GameIsFinished {		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		GamePosition currentGamePosition = currentGame.getCurrentPosition();
		PlayerPosition whitePos = currentGamePosition.getWhitePosition();
		PlayerPosition blackPos = currentGamePosition.getBlackPosition();
		Player whitePlayer = currentGame.getWhitePlayer();
		Player blackPlayer = currentGame.getBlackPlayer();
		
		int indexOfWhiteWallsPlaced = currentGamePosition.numberOfWhiteWallsOnBoard() -1;
		int indexOfBlackWallsPlaced = currentGamePosition.numberOfBlackWallsOnBoard() -1;
		
		// reset the player positions
		Tile player1StartPos = quoridor.getBoard().getTile(76);
		Tile player2StartPos = quoridor.getBoard().getTile(4);
		whitePos.setTile(player1StartPos);
		blackPos.setTile(player2StartPos);
		currentGamePosition.setPlayerToMove(whitePlayer);
		
		// clear the game moves, and place all the walls back in the player stocks to allow a new game to be loaded
		int numOfMoves = currentGame.getMoves().size()-1;
		while(numOfMoves >= 0) {
			Move move = currentGame.getMove(numOfMoves);
			if (move instanceof WallMove && move.getPlayer().equals(whitePlayer) && currentGamePosition.hasWhiteWallsOnBoard()) {
				Wall currrentWhiteWall = currentGamePosition.getWhiteWallsOnBoard(indexOfWhiteWallsPlaced);
				currentGamePosition.removeWhiteWallsOnBoard(currrentWhiteWall);
				currentGamePosition.addWhiteWallsInStock(currrentWhiteWall);
				indexOfWhiteWallsPlaced--;
			}
			if (move instanceof WallMove && move.getPlayer().equals(blackPlayer) && currentGamePosition.hasBlackWallsOnBoard()) {
				Wall currentBlackWall = currentGamePosition.getBlackWallsOnBoard(indexOfBlackWallsPlaced);
				currentGamePosition.removeBlackWallsOnBoard(currentBlackWall);
				currentGamePosition.addBlackWallsInStock(currentBlackWall);
				indexOfBlackWallsPlaced--;
			}
			currentGame.removeMove(move);
			move.delete();
			numOfMoves--;
		}
		
		// read the data lines from the file
		List<String> dataLines = new ArrayList<String>();
		try {
			// create a file reader
			BufferedReader fileReader = new BufferedReader(new FileReader(loadFile));
			
			// read the first line
			String dataString = fileReader.readLine();
			
			while(dataString != null) {
				dataLines.add(dataString);
				dataString = fileReader.readLine();
			}
									
			// clean up resources
			fileReader.close();

		} catch (Exception e) {
				return false;
		}
		
		// process each line of data
		String dataArr[] = null;
		
		// Ensure the game is running before making moves
		currentGame.setGameStatus(GameStatus.Running);
		boolean playedBlackMove = true;
				
		for(String line : dataLines) {
			dataArr = line.split("\\s");
			
			if(dataArr == null || dataArr.length < 2 || dataArr.length > 3 ) {
				return false;
			}
			
			// Update the behaviors and playerPositions
			PawnBehavior whiteBehavior = QuoridorApplication.GetWhitePawnBehavior();
			PawnBehavior blackBehavior = QuoridorApplication.GetBlackPawnBehavior();
			quoridor = QuoridorApplication.getQuoridor();
			currentGame = quoridor.getCurrentGame();
			currentGamePosition = currentGame.getCurrentPosition();
			whitePos = currentGamePosition.getWhitePosition();
			blackPos = currentGamePosition.getBlackPosition();
			
			// Process white player's move
			if(playedBlackMove) {
				playedBlackMove = false;
			} else {
				// was no black move before next white move
				return false;
			}
			
			if(dataArr[1].length() == 2) {
				if(!loadGameTryPawnMove(dataArr[1], whitePos, whiteBehavior)) {
					return false;
				}
			}
			// if it's a wallMove
			else {
				if(!loadGameTryWallMove(dataArr[1], whitePlayer)) {
					return false;
				}
			}
			
			// Check that the white game move was correctly registered
			List<Move> moveList = currentGame.getMoves();
			if(moveList.size() > 0) {
				if(moveList.get(moveList.size()-1) instanceof WallMove) {
					WallMove tmpMove = (WallMove) moveList.get(moveList.size()-1);
					String tmpString = tileToString(tmpMove.getTargetTile())
							+ directionToString(tmpMove.getWallDirection());
					if(!tmpString.equals(dataArr[1])){
						return false;
					}
				} else {
					Move tmpMove = moveList.get(moveList.size()-1);
					String tmpString = tileToString(tmpMove.getTargetTile());
					if(!tmpString.equals(dataArr[1])){
						return false;
					}
				}
			} else {
				return false;
			}
				
			// Check game status based on white move, only if pawn move
			//if(dataArr[1].length() == 2) {
				if(identifyDraw()) {
					currentGame.setGameStatus(GameStatus.Draw);
					throw new GameIsDrawn("Game Draw!");
				}
				if(identifyWin()) {
					currentGame.setGameStatus(GameStatus.WhiteWon);
					throw new GameIsFinished(currentGame.getCurrentPosition().getPlayerToMove().getUser().getName() + " won. Congratulation!");
				}
				SwitchPlayer();
			//}
			
			// Update the behaviors and playerPositions
			whiteBehavior = QuoridorApplication.GetWhitePawnBehavior();
			blackBehavior = QuoridorApplication.GetBlackPawnBehavior();
			quoridor = QuoridorApplication.getQuoridor();
			currentGame = quoridor.getCurrentGame();
			currentGamePosition = currentGame.getCurrentPosition();
			whitePos = currentGamePosition.getWhitePosition();
			blackPos = currentGamePosition.getBlackPosition();
			
			// Process black player's move, if it exists
			if(dataArr.length == 3) {
				if(dataArr[2].length() == 2) {
					if(!loadGameTryPawnMove(dataArr[2], blackPos, blackBehavior)) {
						return false;
					}
				}
				// if it's a wallMove
				else {
					if(!loadGameTryWallMove(dataArr[2], blackPlayer)) {
						return false;
					}
				}
				
				// Check that the black game move was correctly registered
				moveList = currentGame.getMoves();
				if(moveList.get(moveList.size()-1) instanceof WallMove) {
					WallMove tmpMove = (WallMove) moveList.get(moveList.size()-1);
					String tmpString = tileToString(tmpMove.getTargetTile())
							+ directionToString(tmpMove.getWallDirection());
					if(!tmpString.equals(dataArr[2])){
						return false;
					}
				} else {
					Move tmpMove = moveList.get(moveList.size()-1);
					String tmpString = tileToString(tmpMove.getTargetTile());
					if(!tmpString.equals(dataArr[2])){
						return false;
					}
				}
				
				// Check game status based on black move
				//if(dataArr[2].length() == 2) {
					if(identifyDraw()) {
						currentGame.setGameStatus(GameStatus.Draw);
						throw new GameIsDrawn("Game Draw!");
					}
					if(identifyWin()) {
						currentGame.setGameStatus(GameStatus.BlackWon);
						throw new GameIsFinished(currentGame.getCurrentPosition().getPlayerToMove().getUser().getName() + " won. Congratulation!");
					}
					SwitchPlayer();
					playedBlackMove = true;
				//}
			}
		}
		currentGame.setGameStatus(GameStatus.ReadyToStart);
		return true;		
	}
	
	/////////////////////////////////////////////////
	// TODO: End of Mitchell's helper and query methods
	/////////////////////////////////////////////////

	/**
	 * @author Le-Li Mao
	 * @return GameData
	 */
	public static TOGame getListOfPlayers() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player playerToMove = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();
		ArrayList<String> name = new ArrayList<>();
		for(User user : quoridor.getUsers()) {
			name.add(user.getName());
		}
		String playerToMoveName;
		if(playerToMove.getUser().getName().equals(name.get(0))) {
			playerToMoveName = name.get(0);
		}else {
			playerToMoveName = name.get(1);
		}
		TOGame listOfPlayers = new TOGame(quoridor.getCurrentGame().getWhitePlayer().getRemainingTime(),
				quoridor.getCurrentGame().getBlackPlayer().getRemainingTime(), name.get(0), name.get(1),
				playerToMoveName);
		return listOfPlayers;
	}
	// @sacha: helpers for cucumber step definitions
	public static String currentStatePlayers() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game currentGame = quoridor.getCurrentGame();
		String return_statement = "current player  moving : "+getPlayerToMoveName() +"\n"+ "Next player moving:" + currentGame.getCurrentPosition().getPlayerToMove().getNextPlayer().getUser().getName();
		return return_statement;
	}
	
	/**
	 * Check if the game is in replay mode
	 * @author Enan Ashaduzzaman
	 * @return game the game is replay mode
	 */
	public static boolean isReplay() {
		Game current = QuoridorApplication.getQuoridor().getCurrentGame();
		if (current != null && current.getGameStatus()==Game.GameStatus.Replay)
			return true;
		return false;
	}
	
	/**
	 * Check if the game is allowed to go into replay mode
	 * @author Enan Ashaduzzaman
	 * @return is replay possible
	 */
	public static boolean isReplayPossible() {
		Game current = QuoridorApplication.getQuoridor().getCurrentGame();
		if (current.getGameStatus()==Game.GameStatus.Running || current.getGameStatus()==Game.GameStatus.BlackWon || current.getGameStatus()==Game.GameStatus.WhiteWon || current.getGameStatus()==Game.GameStatus.Draw)
			return true;

		return false;
	}	

}
