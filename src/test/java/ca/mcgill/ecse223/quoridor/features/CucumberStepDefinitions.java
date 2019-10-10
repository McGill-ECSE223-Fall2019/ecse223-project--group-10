package ca.mcgill.ecse223.quoridor.features;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import cucumber.api.PendingException;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			//Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null
			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided");
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I shall not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// GUI-related feature -- TODO for later
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	}

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */

	/**
	 * @author Le-Li Mao
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void aWallMoveCandidateExistsWithAtPosition(String orientation, int row, int col) {
		setWall(orientation,row, col);	
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void aWallMoveCandidateShallExistWithDirAtPositionNrowNcol(String orientation, int row, int col) {
		Assert.assertEquals(true, hasWallCandidate(orientation,row,col));
	}
	/**
	 * @author Le-Li Mao
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void theWallMoveCandidateWithDirAtPositionRowColIsValid(String orientation, int row, int col) {
		setWall(orientation,row, col);
	}
	/**
	 * @author Le-Li Mao
	 */
	@When("I release the wall in my hand")
	public void iReleaseTheWallInMyHand() {
		try{
			Quoridor223Controller.dropWall();
		}
		catch (Exception e){

		}
	}
	/**
	 * @author Le-Li Mao
	 */
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void aWallMoveIsRegisteredWithDirAtPositionRowCol(String direction, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		WallMove move = (WallMove) curGame.getMove(curGame.numberOfMoves()-1);
		Tile target = move.getTargetTile();
		Direction expected = direction.equalsIgnoreCase("horizontal")?Direction.Horizontal:Direction.Vertical;
		boolean match = move.getWallDirection()==expected&& target.getRow()==row && target.getColumn()==col;
		Assert.assertEquals(true,match);
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("My move shall be completed")
	public void myMoveIsCompleted() {
		throw new PendingException();
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("It shall not be my turn to move")
	public void itIsNotMyTurnToMove() {
		throw new PendingException();
	}
	/**
	 * @author Le-Li Mao
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void theWallMoveCandidateWithDirAtPositionRowColIsInvalid(String orientation, int col, int row) {
		throw new PendingException();
	}
	/**
	 * @author Le-Li Mao
	 */
	@But("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void noWallMoveIsRegisteredWithDirAtPositionRowCol(String orientation, int col, int row) {
		throw new PendingException();
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("The wall candidate is not at the {string} edge of the board")
	public void theWallCandidateIsNotAtTheSideEdgeOfTheBoard(String side) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if(candidate == null )return;
		int row = candidate.getTargetTile().getRow();
		int col = candidate.getTargetTile().getColumn();
		if(side.equalsIgnoreCase("UP")){
			Assert.assertEquals(false,1==row);
		}
		else if (side.equalsIgnoreCase("DOWN")){
			Assert.assertEquals(false,8==row);
		}
		else if (side.equalsIgnoreCase("RIHGT")){
			Assert.assertEquals(false,8==col);
		}
		else {
			Assert.assertEquals(false,1==col);
		}
	}
	/**
	 * @author Le-Li Mao
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void theWallShallBeMovedOverTheBoardToPositionNrowNcol(int col, int row) {
		Quoridor quoridor =QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		WallMove candidate = curGame.getWallMoveCandidate();
		Tile tile= candidate.getTargetTile();
		boolean reached = isCordEqual(row,col,tile.getRow(),tile.getColumn());
		Assert.assertEquals(true,reached);
	}
	/**
	 * @author Le-Li Mao
	 */
	@When("I try to move the wall {string}")
	public void iTryToMoveTheWallSide(String side) {
		try {
			Quoridor223Controller.moveWall(side);
		}
		catch (Exception e){

		}
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("The wall candidate is at the {string} edge of the board")
	public void theWallCandidateIsAtTheSideEdgeOfTheBoard(String side) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		int row = candidate.getTargetTile().getRow();
		int col = candidate.getTargetTile().getColumn();
		if(side.equalsIgnoreCase("UP")){
			Assert.assertEquals(1,row);
		}
		else if (side.equalsIgnoreCase("DOWN")){
			Assert.assertEquals(8,row);
		}
		else if (side.equalsIgnoreCase("RIHGT")){
			Assert.assertEquals(8,col);
		}
		else {
			Assert.assertEquals(1,col);
		}
	}
	/**
	 * @author Le-Li Mao
	 */
	@Then("I shall be notified that my move is illegal")
	public void iShouldBeNotifiedThatMyMoveIsIllegal() {
		//GUI
		throw new PendingException();
	}
	@Then ("I shall be notified that my wall move is invalid")
	public void iShallBeNotifiedThatMyWallMoveIsInvalid() {
		//GUI
		throw new PendingException();
	}
	@And("I shall have a wall in my hand over the board")
	public void iShallHaveAWallInMyHandOverTheBoard() {
		//GUI 
		throw new PendingException();
	}
	@And("It shall be my turn to move")
	public void itShallBeMyTurnToMove() {
		
	}
	
	/**
	 * @author Mitchell Keeley
	 */	
	@Given("No file {string} exists in the filesystem")
	public void noFileFilenameExistsInTheFilesystem(String filename) {
		File file = new File(filename);
		file.delete();
	}	
	
	/**
	 * @author Mitchell Keeley
	 */
	@When("The user initiates to save the game with name {string}")
	public void theUserInitiatesToSaveTheGameWithNameFilename(String filename) {
		//GUI
		//the user types the filename into a dialog box and then clicks the save button
		//throw new UnsupportedOperationException();
	}
	
	/**
	 * @author Mitchell Keeley
	 * @throws Throwable 
	 */
	@Then("A file with {string} shall be created in the filesystem")
	public void aFileWithFilenameShallBeCreatedInTheFilesystem(String filename) throws Throwable {
		File file = new File(filename);
		file.createNewFile();
	}
	
	/**
	 * @author Mitchell Keeley
	 * @throws Throwable 
	 */
	@Given("File {string} exists in the filesystem")
	public void fileFilenameExistsInTheFilesystem(String filename) throws Throwable {
		File file = new File(filename);
		file.createNewFile();
	}
	
	/**
	 * @author Mitchell Keeley
	 */
	@And("The user confirms to overwrite existing file")
	public void theUserConfirmsToOverwriteExistingFile() {
		//GUI
		//The user clicks yes when prompted by the GUI to overwrite the existing file
	}
	
	/**
	 * @author Mitchell Keeley
	 * @throws Throwable 
	 */
	@Then("File with {string} shall be updated in the filesystem")
	public void fileWithFilenameShallBeUpdatedInTheFilesystem(String filename) throws Throwable {
		saveCurrentGamePositionAsFile(filename);
	}
	
	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i);
			if(wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		// @formatter:off
		/*
		 * __________ | | | | |x-> <-x| | | |__________|
		 * 
		 */

		//@formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
		
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);
		
		return playersList;
	}

	private void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

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
	 * Set a MoveCandidate based on the given parameter
	 * @param player
	 * @param dir
	 * @param row
	 * @param col
	 */
	private void getWallMoveCandidate(Player player, String dir, int row, int col) {
		//create a new WallMove Candidate and place the corresponding tile
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Direction wallDirection = dir.equals("horizontal")?Direction.Horizontal:Direction.Vertical;
		game.setWallMoveCandidate(new WallMove(0, 1, player, board.getTile((row-1)*9+(col-1)), game, wallDirection, game.getCurrentPosition().getBlackWallsInStock(0)));
	}
	/**
	 * @param dir
	 * @param row
	 * @param col
	 */
	private void setWall(String dir, int row, int col) {
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Board board = QuoridorApplication.getQuoridor().getBoard();
		if(game.getWallMoveCandidate()!=null) {
		game.getWallMoveCandidate().setTargetTile(board.getTile((row-1)*9+(col-1)));
		}
		else {
			getWallMoveCandidate(game.getCurrentPosition().getPlayerToMove(),dir, row, col);
		}
	}
	/**
	 * A function that return whether the given orientation and tile has a wall candidate
	 * @author Le-Li Mao
	 * @param dir
	 * @param row
	 * @param col
	 * @return candidateExists
	 */
	private boolean hasWallCandidate(String dir, int row, int col){
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		if(game.getWallMoveCandidate()==null)return false;
		WallMove candidate = game.getWallMoveCandidate();
		if(candidate.getTargetTile().getRow()!=row || candidate.getTargetTile().getColumn()!=col)return false;
		if(candidate.getWallDirection()!=(dir.equalsIgnoreCase("horizontal")?Direction.Horizontal:Direction.Vertical))return false;
		return true;
	}
	/**
	 * A function that return whether the given tile equal to another given tile row/col
	 * @author Le-Li Mao
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	private boolean isCordEqual(int row1,int col1, int row2, int col2){
		return row1==row2 && col1==col2;
	}
	
	/**
	 * A function that checks if a file with the given file path exists
	 * @author Mitchell Keeley
	 * @param filename
	 * @return fileExists
	 */
	public boolean doesFileExistInSystem(String filename) {
		File file = new File(filename);
		
		if (file.isFile() && file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * A function to save the current GamePosition as a file
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws Throwable 
	 */
	public void saveCurrentGamePositionAsFile(String filename) throws Throwable {
		Game currentGame = QuoridorApplication.getCurrentGame();
		GamePosition currentGamePosition = QuoridorApplication.getCurrentGamePosition();
		
		// get the player information strings to write to the file
		String WhitePlayer = "W: " + tileToString(currentGamePosition.getWhitePosition().getTile());
		// continue with wall list converted to string 
		String BlackPlayer = "B: " + tileToString(currentGamePosition.getBlackPosition().getTile());
		
		// add the wall positions
		for( Move move : currentGame.getMoves()) {
			if (move instanceof WallMove){
				WhitePlayer.concat(tileToString(move.getTargetTile()) + ((WallMove) move).getWallDirection());
			}
		}
		// TODO: Finish, starting from here, verify wall position for loop
		
		
		// initialize the printWriter
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
	    
		// if the next player to move is the White Player
		if(currentGamePosition.getPlayerToMove().equals(currentGamePosition.getGame().getWhitePlayer())){
			printWriter.printf("%s\n", WhitePlayer);
			printWriter.printf("%s", BlackPlayer);
		}else {
			printWriter.printf("%s\n",BlackPlayer);
			printWriter.printf("%s", WhitePlayer);
		} 
		printWriter.close();
	}
	
	/**
	 * A function to translate tile into the correct string to write to the save file
	 * @author Mitchell Keeley
	 * @param tile
	 * @return
	 */
	public String tileToString(Tile tile) {
		int asciiNumberOffset = 48;
		int asciiLetterOffset = 96;
		
		String tileString = String.valueOf(asciiLetterOffset + tile.getRow())
				+ String.valueOf(asciiNumberOffset + tile.getColumn());
		
		return tileString;
	}
	
	public String directionToString(Direction direction) {
		if( direction.equals(Direction.Horizontal)){
			return "h";
		}
		else {
			return "v";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}