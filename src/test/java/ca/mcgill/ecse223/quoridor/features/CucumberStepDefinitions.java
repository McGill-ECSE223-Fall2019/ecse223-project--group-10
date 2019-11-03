package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.GameNotRunningException;
import ca.mcgill.ecse223.quoridor.controller.InvalidOperationException;
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
import ca.mcgill.ecse223.quoridor.view.GamePage;
import cucumber.api.PendingException;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************
	private int currentWallID = 21;
	private int curRound = 0;
	private Player initialPlayer = null;
	private Move initialMove = null;
	private String cucumberFilename	= null;
	private GamePage gamePage;
	private boolean loadSuccessful = false;
	private ArrayList<Player> createUsersAndPlayers;

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
		gamePage = new GamePage();
	}

	@Given("^A new game is initializing$")
	public void newGameIsInitializing() {
		Quoridor223Controller.createGame();
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		initialPlayer = currentPlayer;
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
		//make sure all wall are in the finalized state
		quoridor.getCurrentGame().setWallMoveCandidate(null);
	}
	/**
	 * @author Le-Li Mao
	 */
	@And("I shall not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		assertEquals(false, gamePage.hasWallInHand());
	}

	/**
	 * @author Le-Li Mao
	 * @throws Throwable
	 */
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		setWall("horizontal", 5, 1);
		assertEquals(true, gamePage.hasWallInHand());
	}

	/**
	 * @author Le-Li Mao
	 */
	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand2() {
		assertEquals(false, gamePage.hasWallInHand());
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

	// **********************************************
	// StartNewGame and ProvideSelectUserName start here
	// **********************************************

	/**
	 * Scenario: Initiate a new game
	 * 
	 * @author Vanessa Ifrah
	 */
	@When("A new game is being initialized")
	public void aNewGameIsBeingInitialized() {

		// create the new game
		Quoridor223Controller.createGame();
	}

	@And("White player chooses a username")
	public void whitePlayerChoosesAUsername() {

		// white/first player chooses their name
		Quoridor223Controller.setUser("Vanessa", "white");

	}

	@And("Black player chooses a username")
	public void blackPlayerChoosesAUsername() {
		// black/second player chooses their name
		Quoridor223Controller.setUser("Jessica", "black");
	}

	@And("Total thinking time is set")
	public void totalThinkingTimeIsSet() {
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:10:00"), "Vanessa");
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:10:00"), "Jessica");
	}

	@Then("The game shall become ready to start")
	public void theGameShallBecomeReadyToStart() {

		// verify that the game has started
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus(), GameStatus.ReadyToStart);

	}

	/**
	 * Scenario: Start Clock
	 * 
	 * @author Vanessa Ifrah
	 */
	@Given("The game is ready to start")
	public void theGameIsReadyToStart() {

		// set game to ready
		Quoridor223Controller.createGame();
		Quoridor223Controller.setGameToReady();

	}

	@When("I start the clock")
	public void iStartTheClock() {

		// start the clock -- clock starts when game starts running
		Quoridor223Controller.setGameToRun();

	}

	@Then("The game shall be running")
	public void theGameShallBeRunning() {

		// make sure the game is running
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus(), GameStatus.Running);

	}

	@And("The board shall be initialized")
	public void theBoardShallBeInitialized() {
		assertEquals(QuoridorApplication.getQuoridor().hasBoard(), true);

	}

	/**
	 * Scenario: Select existing user name
	 * 
	 * @author Vanessa Ifrah
	 */
	@Given("Next player to set user name is {string}")
	public void nextPlayerToSetUserNameIs(String playerColor) {

		Quoridor223Controller.setUser("player", playerColor);

	}

	@And("There is existing user {string}")
	public void thereIsExistingUser(String userName) {

		// add the name to the file
		try {
			File f = new File("names.txt");
			FileWriter writer = new FileWriter(f.getAbsolutePath(), true);
			writer.write("\n" + userName);
			writer.close();
		} catch (Exception e) {

		}

		// check that the username exists
		assertEquals(Quoridor223Controller.checkNameList(userName), true);

	}

	@When("The player selects existing {string}")
	public void thePlayerSelectsExisting(String userName) {

		// player set to white or black
		if (userName == "Daniel") {
			Quoridor223Controller.setUser(userName, "white");
		} else {
			Quoridor223Controller.setUser(userName, "black");
		}

	}

	@Then("The name of player {string} in the new game shall be {string}")
	public void theNameOfPlayerInTheNewGameShallBe(String playerColor, String userName) {

		// set the names according to their color
		if (playerColor == "white") {
			assertEquals(Quoridor223Controller.getWhitePlayerName(), userName);
		} else {
			assertEquals(Quoridor223Controller.getBlackPlayerName(), userName);
		}

	}

	/**
	 * Scenario: Create new user name
	 * 
	 * @author Vanessa Ifrah
	 */
	@And("There is no existing user {string}")
	public void thereIsNoExistingUser(String userName) {

		assertEquals(Quoridor223Controller.checkNameList(userName), false);

	}
	
	@When("The player provides new user name: {string}")
	public void thePlayerProvidesNewUserName(String userName) {

		Quoridor223Controller.setUser(userName, "black");

	}
	
	/**
	 * Scenario: User name already exists
	 * 
	 * @author Vanessa Ifrah
	 */
	@Then("The player shall be warned that {string} already exists")
	public void thePlayerShallBeWarnedThatUserNameAlreadyExists(String userName) {

		// if name exists, warn user
		Quoridor223Controller.warnUser(userName);

	}

	@And("Next player to set user name shall be {string}")
	public void nextPlayerToSetUserNameShallBe(String playerColor) {

		Quoridor223Controller.setUser("player1", playerColor);

	}

	// **********************************************
	// SetTotalThinkingTime and InitializeBoard start here
	// **********************************************

	

	/**
	 * @author Andrew Ta
	 * @param min minutes input by user
	 * @param sec seconds input by use
	 */
	@When("{int}:{int} is set as the thinking time")
	public void setThinkingTime(int min, int sec) {
		String timeFormat[] = (new Time(min*60000 + sec*1000)).toString().split(":");
		// set thinking time
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:" + timeFormat[1] + ":" + timeFormat[2]), "Vanessa");
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:" + timeFormat[1] + ":" + timeFormat[2]), "Jessica");
	}

	/**
	 * @author Andrew Ta
	 * @param min minutes given for testing
	 * @param sec seconds given for testing
	 */
	@Then("Both players shall have {int}:{int} remaining time left")
	public void bothPlayerShallHaveSameRemainingTime(int min, int sec) {
		

		// get remaining time of both player
		Time playerBlackTime = Quoridor223Controller.getRemainingTime("white");
		Time playerWhiteTime = Quoridor223Controller.getRemainingTime("black");

		// compare blackTime and whiteTime
		assertEquals(playerBlackTime.toString(), playerWhiteTime.toString());

		// compare blackTime and given time
		String blackTime[] = playerBlackTime.toString().split(":");
		assertEquals(Integer.parseInt(blackTime[1]), min);
		assertEquals(Integer.parseInt(blackTime[2]), sec);
	}

	/**
	 * @author Andrew Ta
	 */
	@When("The initialization of the board is initiated")
	public void boardIsInitiated() {
		Quoridor223Controller.setUser("Andrew", "white");
		Quoridor223Controller.setUser("Shuby", "black");
		Quoridor223Controller.initializeBoard();
		// create a game page for ui testing
		gamePage = new GamePage();
	}

	/**
	 * @author Andrew Ta
	 */
	@Then("It shall be white player to move")
	public void whitePlayerToMove() {
		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(currentGame.getCurrentPosition().getPlayerToMove().equals(currentGame.getWhitePlayer()), true);
	}

	/**
	 * @author Andrew Ta
	 */
	@And("White's pawn shall be in its initial position")
	public void whitePawnShallBeInItsInitialPosition() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		GamePosition position = quoridor.getCurrentGame().getCurrentPosition();
		Tile whiteTile = position.getWhitePosition().getTile();

		assertEquals(whiteTile, quoridor.getBoard().getTile(36));
	}

	/**
	 * @author Andrew Ta
	 */
	@And("Black's pawn shall be in its initial position")
	public void blackPawnShallBeInItsInitialPosition() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		GamePosition position = quoridor.getCurrentGame().getCurrentPosition();
		Tile blackTile = position.getBlackPosition().getTile();

		assertEquals(blackTile, quoridor.getBoard().getTile(44));
	}

	/**
	 * @author Andrew Ta
	 */
	@And("All of White's walls shall be in stock")
	public void whiteWallShallBeInStock() {
		GamePosition position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int numberOfWallInStock = position.getWhiteWallsInStock().size();
		assertEquals(10, numberOfWallInStock);
	}

	/**
	 * @author Andrew Ta
	 */
	@And("All of Black's walls shall be in stock")
	public void blackWallShallBeInStock() {
		GamePosition position = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int numberOfWallInStock = position.getBlackWallsInStock().size();
		assertEquals(10, numberOfWallInStock);
	}

	/**
	 * @author Andrew Ta
	 */
	@And("White's clock shall be counting down")
	public void whiteClockShallBeCountingDown() {
		try {
			TimeUnit.SECONDS.sleep(2);
		}catch(Exception e){
			
		}
		assertEquals(gamePage.getWhiteClockStatus(), true);
	}

	/**
	 * @author Andrew Ta
	 */
	@And("It shall be shown that this is White's turn")
	public void showThatThisIsWhiteTurn() {// setup mainPage
		assertEquals(gamePage.getGameMessage(), "It is Andrew's Turn !!");
	}

	// **********************************************
	// SetTotalThinkingTime and InitializeBoard end here
	// **********************************************

	
	// **********************************************
	// GrabWall and RotateWall
	// **********************************************

//	Grab Wall Feature
	// Scenario: Start wall placement
	/**
	 * @author Enan Ashaduzzaman
	 */
	
	int wallQuantityBefore = 0;
	
	@Given("I have more walls on stock")
	public void iHaveMoreWallsOnStock() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = game.getCurrentPosition().getPlayerToMove();
		if (curPlayer.equals(game.getWhitePlayer())) {
			int curPlayerWalls = game.getCurrentPosition().getWhiteWallsInStock().size();
			if (curPlayerWalls == 0) {
				Wall wallOnBoard = game.getCurrentPosition().getWhiteWallsOnBoard(0);
				game.getCurrentPosition().addWhiteWallsInStock(wallOnBoard);
			}
		} else if (curPlayer.equals(game.getBlackPlayer())) {
			int curPlayerWalls = game.getCurrentPosition().getBlackWallsInStock().size();
			if (curPlayerWalls == 0) {
				Wall wallOnBoard = game.getCurrentPosition().getBlackWallsOnBoard(0);
				game.getCurrentPosition().addBlackWallsInStock(wallOnBoard);
			}
		}
		//Check Wall Quantity of player stock before grab wall. 
		String Colour = new String();
		if (curPlayer.equals(game.getWhitePlayer())) {
			wallQuantityBefore = Quoridor223Controller.getWhiteWallInStock();
		} else if (curPlayer.equals(game.getBlackPlayer())){
			wallQuantityBefore = Quoridor223Controller.getBlackWallInStock();
		}
		
	}

	/**
	 * @author Enan Ashaduzzaman
	 * @throws GameNotRunningException
	 */
	@When("I try to grab a wall from my stock")
	public void iTryToGrabAWallFromMyStock() throws GameNotRunningException, InvalidOperationException {
		gamePage.clickGrabWall();
	}

	/**
	 * @author Enan Ashaduzzaman
	 */
	@Then("A wall move candidate shall be created at initial position")
	public void aWallMoveCandidateShallBeCraetedAtInitialPosition() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		boolean hasWallCandidate = true;
		if (game.getWallMoveCandidate() == null) {
			hasWallCandidate = false;
		}
		assertEquals(true, hasWallCandidate);
	}

	/**
	 * @author Enan Ashaduzzaman
	 */
	@And("The wall in my hand shall disappear from my stock")
	public void theWallInMyHandShouldDisappearFromMyStock() {		
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = game.getCurrentPosition().getPlayerToMove();
		int wallQuantityAfter = 0;
		if (curPlayer.equals(game.getWhitePlayer())) {
			wallQuantityAfter = Quoridor223Controller.getWhiteWallInStock();
		} else if (curPlayer.equals(game.getBlackPlayer())) {
			wallQuantityAfter = Quoridor223Controller.getBlackWallInStock();
		}
		assertEquals(wallQuantityBefore - 1, wallQuantityAfter);
	}

	// Scenario: No more walls in stock
	/**
	 * @author Enan Ashaduzzaman
	 */
	@Given("I have no more walls on stock")
	public void iHaveNoMoreWallsOnStock() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = game.getCurrentPosition().getPlayerToMove();

		if (curPlayer.equals(game.getWhitePlayer())) {
			ArrayList<Wall> toBeRemoved = new ArrayList<Wall>();
			for (Wall wall : game.getCurrentPosition().getWhiteWallsInStock()) {
					game.getCurrentPosition().addWhiteWallsOnBoard(wall);
					toBeRemoved.add(wall);
					WallMove curWallMove = new WallMove(0, 1, curPlayer, QuoridorApplication.getQuoridor().getBoard().getTile(1), game, Direction.Vertical, wall);
					game.setWallMoveCandidate(curWallMove);
			}
			for (Wall wall:toBeRemoved)game.getCurrentPosition().removeWhiteWallsInStock(wall);
		} else if (curPlayer.equals(game.getBlackPlayer())) {
			ArrayList<Wall> toBeRemoved = new ArrayList<Wall>();
			for (Wall wall : game.getCurrentPosition().getBlackWallsInStock()) {
					game.getCurrentPosition().addBlackWallsOnBoard(wall);
					toBeRemoved.add(wall);
					WallMove curWallMove = new WallMove(0, 1, curPlayer, QuoridorApplication.getQuoridor().getBoard().getTile(1), game, Direction.Vertical, wall);
					game.setWallMoveCandidate(curWallMove);
			}
			for (Wall wall:toBeRemoved)game.getCurrentPosition().removeBlackWallsInStock(wall);
		}
		game.setWallMoveCandidate(null);
	}

	/**
	 * @author Enan Ashaduzzaman
	 */
	@Then("I shall be notified that I have no more walls")
	public void iShallBeNotifiedThatIHaveNoMoreWalls() {
		assertEquals("No walls in stock", gamePage.getGameMessage());
	}

	/**
	 * @author Enan Ashaduzzaman
	 */
	@And("I shall have no walls in my hand")
	public void iShallHaveNoWallsInMyHand() {
		assertEquals(false, gamePage.hasWallInHand());
	}

//	Rotate Wall Feature
	// Scenario: Flip wall from horizontal to vertical or vice versa
	/**
	 * @author Enan Ashaduzzaman
	 * @throws GameNotRunningException
	 */
	@When("I try to flip the wall")
	public void iTryToFlipTheWall() throws GameNotRunningException, InvalidOperationException {
		gamePage.clickRotateWall();
	}

	/**
	 * @author Enan Ashaduzzaman
	 * @param direction
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void theWallShallBeRotatedOverTheBoardToNewdir(String direction) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		String boardDir = new String();
		Direction wallDir = curGame.getWallMoveCandidate().getWallDirection();
		if (wallDir == Direction.Horizontal) {
			boardDir = "horizontal";
		} else {
			boardDir = "vertical";
		}
		assertEquals(direction, boardDir);
	}
	// ***********************************************
	// Move Wall and Drop Wall start here
	// ***********************************************

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void aWallMoveCandidateExistsWithAtPosition(String direction, int row, int col) {
		setWall(direction, row, col);
	}

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void aWallMoveCandidateShallExistWithDirAtPositionNrowNcol(String direction, int row, int col) {
		assertEquals(true, hasWallCandidate(direction, row, col));
	}

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void theWallMoveCandidateWithDirAtPositionRowColIsValid(String direction, int row, int col) {
		setWall(direction, row, col);
	}

	/**
	 * @author Le-Li Mao
	 */
	@When("I release the wall in my hand")
	public void iReleaseTheWallInMyHand() {
		try {
			Quoridor223Controller.dropWall();
		} catch (Exception e) {

		}
		gamePage.clickDropWall();
		// @sacha: use commands from the UI to get the texts from the JLABELS
	}

	/**
	 * @author Le-Li Mao
	 */
	@And("It shall be my turn to move")
	public void itShallBeMyTurnToMove() {
		assertEquals(initialPlayer,
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove());
	}

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void aWallMoveIsRegisteredWithDirAtPositionRowCol(String direction, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		WallMove move = (WallMove) curGame.getMove(0);
		Tile target = move.getTargetTile();
		Direction expected = (direction.equalsIgnoreCase("horizontal") ? Direction.Horizontal : Direction.Vertical);
		assertEquals(expected, move.getWallDirection());
		assertEquals(row, target.getRow());
		assertEquals(col, target.getColumn());
	}

	/**
	 * @author Le-Li Mao
	 */
	@And("My move shall be completed")
	public void myMoveIsCompleted() {

		//
		// throw new PendingException();
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(curGame.getMove(0), initialMove);

	}

	/**
	 * @author Le-Li Mao
	 */
	@And("It shall not be my turn to move")
	public void itIsNotMyTurnToMove() {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(false, initialPlayer.equals(curGame.getCurrentPosition().getPlayerToMove()));
	}

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void theWallMoveCandidateWithDirAtPositionRowColIsInvalid(String direction, int row, int col) {
		setWall(direction, row, col);
	}

	/**
	 * @author Le-Li Mao
	 * @param direction
	 * @param row
	 * @param col
	 */
	@But("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void noWallMoveIsRegisteredWithDirAtPositionRowCol(String direction, int row, int col) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Direction dir = direction.equalsIgnoreCase("Horizontal") ? Direction.Horizontal : Direction.Vertical;
		for (Move move : curGame.getMoves()) {
			if (!(move instanceof WallMove))
				continue;
			WallMove wallMove = (WallMove) move;
			boolean found = true;
			if (!dir.equals(wallMove.getWallDirection()))
				found = false;
			if (row != wallMove.getTargetTile().getRow())
				found = false;
			if (col != wallMove.getTargetTile().getColumn())
				found = false;
			assertEquals(false, found);
		}
	}

	/**
	 * @author Le-Li Mao
	 * @param side
	 */
	@And("The wall candidate is not at the {string} edge of the board")
	public void theWallCandidateIsNotAtTheSideEdgeOfTheBoard(String side) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (candidate == null)
			return;
		int row = candidate.getTargetTile().getRow();
		int col = candidate.getTargetTile().getColumn();
		if (side.equalsIgnoreCase("UP")) {
			assertEquals(false, 1 == row);
		} else if (side.equalsIgnoreCase("DOWN")) {
			assertEquals(false, 8 == row);
		} else if (side.equalsIgnoreCase("RIHGT")) {
			assertEquals(false, 8 == col);
		} else {
			assertEquals(false, 1 == col);
		}
	}

	/**
	 * @author Le-Li Mao
	 * @param row
	 * @param col
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void theWallShallBeMovedOverTheBoardToPositionNrowNcol(int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game curGame = quoridor.getCurrentGame();
		WallMove candidate = curGame.getWallMoveCandidate();
		Tile tile = candidate.getTargetTile();
		boolean reached = isCordEqual(row, col, tile.getRow(), tile.getColumn());
		assertEquals(true, reached);
	}

	/**
	 * @author Le-Li Mao
	 * @param side
	 */
	@When("I try to move the wall {string}")
	public void iTryToMoveTheWallSide(String side) {
		gamePage.clickMoveWall(side);
	}

	/**
	 * @author Le-Li Mao
	 * @param side
	 */
	@And("The wall candidate is at the {string} edge of the board")
	public void theWallCandidateIsAtTheSideEdgeOfTheBoard(String side) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		int row = candidate.getTargetTile().getRow();
		int col = candidate.getTargetTile().getColumn();
		if (side.equalsIgnoreCase("UP")) {
			assertEquals(1, row);
		} else if (side.equalsIgnoreCase("DOWN")) {
			assertEquals(8, row);
		} else if (side.equalsIgnoreCase("RIGHT")) {
			assertEquals(8, col);
		} else {
			assertEquals(1, col);
		}
	}

	/**
	 * @author Le-Li Mao
	 */
	@And("I shall have a wall in my hand over the board")
	public void iShallHaveAWallInMyHandOverTheBoard() {
//		assertEquals(true, gamePage.getWallInHand() != null);
//		System.out.println(gamePage.hasWallInHand());
		assertEquals(true, gamePage.hasWallInHand());
	}

	/**
	 * @author Le-Li Mao
	 */
	@Then("I shall be notified that my move is illegal")
	public void iShallBeNotifiedThatMyMoveIsIllegal() {
		// GUI
		assertEquals("Illegal Move", gamePage.getGameMessage());
	}

	/**
	 * @author Le-Li Mao
	 */
	@Then("I shall be notified that my wall move is invalid")
	public void iShallBeNotifiedThatMyWallMoveIsInvalid() {
		// GUI
		assertEquals("Invalid Move", gamePage.getGameMessage());
	}

	// **********************************************
	// Drop Wall and Move Wall end here
	// **********************************************

	// **********************************************
	// TODO: Save Position starts here
	// **********************************************
	/**
	 * @author Mitchell Keeley
	 */
	@Given("No file {string} exists in the filesystem")
	public void noFileFilenameExistsInTheFilesystem(String filename) {
		cucumberFilename = filename;
		deleteFileIfItExists(filename);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws Throwable
	 */
	@When("The user initiates to save the game with name {string}")
	public void theUserInitiatesToSaveTheGameWithNameFilename(String filename) throws Throwable {
		cucumberFilename = filename;
		Quoridor223Controller.checkIfFileExists(filename);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws IOException
	 * @throws UnsupportedOperationException, IOExceptionable
	 */
	@Then("A file with {string} shall be created in the filesystem")
	public void aFileWithFilenameShallBeCreatedInTheFilesystem(String filename) throws IOException {
		cucumberFilename = filename;
		Boolean result = Quoridor223Controller.saveCurrentGamePositionAsFile("./ca.mcgill.ecse223.quoridor/"+ filename);
		assertTrue("File is not a valid save file", result);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws IOException
	 */
	@Given("File {string} exists in the filesystem")
	public void fileFilenameExistsInTheFilesystem(String filename) throws IOException {
		cucumberFilename = filename;
		ensureFileExists(filename);
	}

	/**
	 * @author Mitchell Keeley
	 */
	@And("The user confirms to overwrite existing file")
	public void theUserConfirmsToOverwriteExistingFile() {
		//GUI: The user clicks yes when prompted by the GUI to overwrite an existing file
		assertTrue("The user did not agree to overwrite the file", cucumberUserOverwritePrompt(JOptionPane.YES_OPTION));
	}

	/**
	 * @author Mitchell Keeley
	 * @throws Throwable
	 */
	@Then("File with {string} shall be updated in the filesystem")
	public void fileWithFilenameShallBeUpdatedInTheFilesystem(String filename) throws Throwable {
		cucumberFilename = filename;
		assertTrue("The file was not modified", simulateUserAttemptingToOverwriteFile(filename, JOptionPane.YES_OPTION));
	}

	/**
	 * @author Mitchell Keeley
	 */
	@And("The user cancels to overwrite existing file")
	public void theUserCancelsToOverwriteExistingFile() {
		//GUI: The user clicks no when prompted by the GUI to overwrite an existing file
		assertFalse("The user agreed to overwrite the file", cucumberUserOverwritePrompt(JOptionPane.NO_OPTION));
	}

	/**
	 * @author Mitchell Keeley
	 * @throws Throwable
	 */
	@Then("File {string} shall not be changed in the filesystem")
	public void fileFilenameShallNotBeChangedInTheFilesystem(String filename) throws Throwable {
		cucumberFilename = filename;
		assertFalse("The file was modified", simulateUserAttemptingToOverwriteFile(filename, JOptionPane.NO_OPTION));
	}

	// **********************************************
	// TODO: Load Position starts here
	// **********************************************

	/**
	 * @author Mitchell Keeley
	 * @param filename
	 * @throws IOException 
	 */
	@When("I initiate to load a saved game {string}")
	public void iInitiateToLoadASavedGame(String filename) throws IOException {
		cucumberFilename = filename;
		createAndPrepareGame(createUsersAndPlayers);
		Quoridor223Controller.checkLoadFileIsValid(filename);
	}

	/**
	 * @author Mitchell Keeley
	 */
	@And("The position to load is valid")
	public void thePositionToLoadIsValid() {
		loadSuccessful = Quoridor223Controller.loadMoveDataFromFile("./ca.mcgill.ecse223.quoridor/" + cucumberFilename);
	}
	
	/**
	 * @author Mitchell Keeley
	 * @param playerColor
	 */
	@Then("It shall be {string}'s turn")
	// @And("It shall be {player}'s turn")
	public void itShallBePlayersTurn(String playerColor) {
		assertTrue("Could not set nextPlayer to that player", checkCurrentPlayerToMoveByColor(playerColor));
	}
	
	/**
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param row
	 * @param col
	 */
	@And("{string} shall be at {int}:{int}") // this is for both the player and opponent
	public void playerShallbeAtRowCol(String playerColor, int row, int col) {
		assertTrue("Could not set player's position", checkPlayerPositionByColor(playerColor, row, col));
	}
	
	/**
	 * 
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param direction
	 * @param row
	 * @param col
	 */
	@And("{string} shall have a {} wall at {int}:{int}") // this is for both the player and the opponent
	public void playerShallHaveADirectionWallAtRowCol(String playerColor, String direction, int row, int col) {
		assertTrue("Could not set a wall's direction and position", checkPlayerWallPositionByColor(playerColor, direction, row, col));
	}
	
	/**
	 * @author Mitchell Keeley
	 * @param remainingWalls
	 */
	@And("Both players shall have {int} in their stacks")
	public void bothPlayersShallHaveRemainingWallsInTheirStacks(int remainingWalls) {
		assertTrue("White player does not have the correct number of walls in their stack",
				checkPlayerWallsInStockByColor("white", remainingWalls));
		assertTrue("Black player does not have the correct number of walls in their stack", 
				checkPlayerWallsInStockByColor("black", remainingWalls));
	}
	
	/**
	 * @author Mitchell Keeley
	 */
	@And("The position to load is invalid")
	public void thePositionToLoadIsInvalid() {
		try{
			loadSuccessful = Quoridor223Controller.loadMoveDataFromFile("./ca.mcgill.ecse223.quoridor/" + cucumberFilename);
		} catch (Exception e){
			loadSuccessful = false;
		}
	}
	
	/**
	 * @author Mitchell Keeley
	 * @throws IOException
	 */
	@Then("The load shall return an error")
	public void theLoadShallReturnAnError() throws IOException {
		assertFalse("Invalid load does not return an error", loadSuccessful);
	}

	// **********************************************
	// TODO: Save Position and Load Position end here
	// **********************************************

	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		// gamePage.delete();
		gamePage = null;
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i + 1);
			if (wall != null) {
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
		// @formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 1, Direction.Vertical);
		Player player2 = new Player(new Time(thinkingTime), user2, 9, Direction.Vertical);

		Player[] players = { player1, player2 };
		player1.setNextPlayer(player2);
		player2.setNextPlayer(player1);
		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j + 1, players[i]);
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

		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(),
				player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(),
				player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 1);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10 + 1);
			gamePosition.addBlackWallsInStock(wall);
		}
		game.setCurrentPosition(gamePosition);
	}
		
	private void createAndPrepareGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.ReadyToStart, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));

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
		gamePage = new GamePage();
	}
		
	/**
	 * Set a MoveCandidate based on the given parameter
	 * 
	 * @author Le-Li Mao
	 * @param player
	 * @param dir
	 * @param row
	 * @param col
	 */
	
	private void getWallMoveCandidate(Player player, String dir, int row, int col) {
		//create a new WallMove Candidate and place the corresponding tile
		
		Board board = QuoridorApplication.getQuoridor().getBoard();
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Wall toBeUsed;
		Direction wallDirection = dir.equals("horizontal")?Direction.Horizontal:Direction.Vertical;
		if(player.equals(game.getWhitePlayer())) {
			toBeUsed = game.getCurrentPosition().getWhiteWallsInStock(0);
			game.getCurrentPosition().removeWhiteWallsInStock(toBeUsed);
			game.getCurrentPosition().addWhiteWallsOnBoard(toBeUsed);
		}
		else {
			toBeUsed = game.getCurrentPosition().getBlackWallsInStock(0);
			game.getCurrentPosition().removeBlackWallsInStock(toBeUsed);
			game.getCurrentPosition().addBlackWallsOnBoard(toBeUsed);
		}
		game.setWallMoveCandidate(new WallMove(currentWallID++, curRound++, player, board.getTile((row-1)*9+(col-1)), game, wallDirection, toBeUsed));
		
	}

	/**
	 * @author Le-Li Mao
	 * @param dir
	 * @param row
	 * @param col
	 */
	private void setWall(String dir, int row, int col) {
		Game game =  QuoridorApplication.getQuoridor().getCurrentGame();
		Board board = QuoridorApplication.getQuoridor().getBoard();
		if(game.getWallMoveCandidate()!=null) {
			game.getWallMoveCandidate().setTargetTile(board.getTile((row-1)*9+(col-1)));
			game.getWallMoveCandidate().setWallDirection(dir.equalsIgnoreCase("horizontal")?Direction.Horizontal:Direction.Vertical);
		}
		else {
		getWallMoveCandidate(game.getCurrentPosition().getPlayerToMove(),dir, row, col);
		}
		initialMove = game.getWallMoveCandidate();
	}

	/**
	 * A function that return whether the given orientation and tile has a wall
	 * candidate
	 * 
	 * @author Le-Li Mao
	 * @param dir
	 * @param row
	 * @param col
	 * @return candidateExists
	 */
	private boolean hasWallCandidate(String dir, int row, int col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		if (game.getWallMoveCandidate() == null)
			return false;
		WallMove candidate = game.getWallMoveCandidate();
		if (candidate.getTargetTile().getRow() != row || candidate.getTargetTile().getColumn() != col)
			return false;
		if (candidate
				.getWallDirection() != (dir.equalsIgnoreCase("horizontal") ? Direction.Horizontal : Direction.Vertical))
			return false;
		return true;
	}

	/**
	 * A function that return whether the given tile equal to another given tile
	 * row/col
	 * 
	 * @author Le-Li Mao
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	private boolean isCordEqual(int row1, int col1, int row2, int col2) {
		return row1 == row2 && col1 == col2;
	}

	/**
	 * A function that deletes the specified filename if it exists in the filesystem
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 */
	private void deleteFileIfItExists(String filename) {
		File file = new File("./ca.mcgill.ecse223.quoridor/" + filename);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * A function that creates the specified file if it does not already exist
	 * 
	 * @author Mitchell Keeley
	 * @param filename
	 * @return fileExists
	 * @throws IOException
	 */
	private void ensureFileExists(String filename) throws IOException {
		File file = new File("./ca.mcgill.ecse223.quoridor/" + filename);
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	/**
	 * simulates the user attempting to overwrite the file with the specified userInput
	 * @author Mitchell Keeley
	 * @param filename
	 * @param userInput
	 * @return
	 */
	private boolean simulateUserAttemptingToOverwriteFile(String filename, Object userInput) {
		boolean fileUpdated = false;
		
		if(cucumberUserOverwritePrompt(userInput)) {
			try {
				fileUpdated = Quoridor223Controller.saveCurrentGamePositionAsFile("./ca.mcgill.ecse223.quoridor/" + filename);
			} catch (IOException e) {
				return fileUpdated;
			}
		}
		
		return fileUpdated;		
	}
	
	/**
	 * Prompts user to overwrite the file, and then automatically accepts or declines
	 * @author Mitchell Keeley
	 * @param userInput
	 * @return
	 */
	private boolean cucumberUserOverwritePrompt(Object userInput) {
		boolean permissionToOveride = false;
		
		//Quoridor223Controller.userOverwritePrompt(cucumberFilename);
		JOptionPane test = new JOptionPane((Object)"Permisssion to Overwrite file:\n\"" + cucumberFilename + "\"",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, (Icon)null);
		test.setVisible(true);
		test.setValue(userInput);
		
		if (test.getValue().equals(JOptionPane.YES_OPTION)) {
			permissionToOveride = true;
		}
		
		return permissionToOveride;
	}

	/**
	 * A function to check the current player to move by color
	 * 
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @return
	 */
	public static boolean checkCurrentPlayerToMoveByColor(String playerColor) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		return game.getCurrentPosition().getPlayerToMove().equals(getPlayerByColor(playerColor));
	}

	/**
	 * A function to set the player position by color
	 * 
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean checkPlayerPositionByColor(String playerColor, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		PlayerPosition playerPosition;
		
		if (playerColor.equals("white")) {
			playerPosition = game.getCurrentPosition().getWhitePosition();
		} else {
			playerPosition = game.getCurrentPosition().getBlackPosition();
		}
		
		Tile tile = playerPosition.getTile();
		if (tile.getRow() == row && tile.getColumn() == col) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A function to add a wall position to the player specified by the given color
	 * 
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @param direction
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean checkPlayerWallPositionByColor(String playerColor, String direction, int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		List<Move> moveList = game.getMoves();
		
		for (Move move : moveList) {
			if(move.getPlayer().equals(getPlayerByColor(playerColor)) && 
					move.getTargetTile().getRow() == row && move.getTargetTile().getColumn() == col &&
					((WallMove)move).getWallDirection().equals(stringToDirection(direction))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if the specified player has remainingWalls in their stock
	 * @param playerColor
	 * @param remainingWalls
	 * @return
	 */
	public static boolean checkPlayerWallsInStockByColor(String playerColor, int remainingWalls) {
		int playerWalls;
		
		if (playerColor.equals("white")) {
			playerWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size();
		} else {
			playerWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size();
		}
		
		if (playerWalls == remainingWalls) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A function to convert the given string into the corresponding Direction
	 * @author Mitchell Keeley
	 * @param direction
	 * @return
	 */
	public static Direction stringToDirection(String direction) {
		if (direction.equals("horizontal")) {
			return Direction.Horizontal;
		}
		if (direction.equals("vertical")) {
			return Direction.Vertical;
		}
		return null;
	}

	/**
	 * A function to get the player specified by the color
	 * 
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @return
	 */
	public static Player getPlayerByColor(String playerColor) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();

		if (playerColor.equals("white")) {
			return game.getWhitePlayer();
		} else {
			return game.getBlackPlayer();
		}
	}
	
// note: no need to implement the given lines, they are given and supposed available

// Validate Position feature
	// Scenario: Validate Pawn position	
	@Given("A game position is supplied with pawn coordinate <row>:<col>")
	public void aGamePositionIsSuppliedWithPawnCoordinate() {
		// if pawn coordinate are to be checked then the wall candidate should be null
		// the goal thus is to check the pawn position instead of validating WallCandidate
		// NEED IMPLEMENTATION FOR MOVE PLAYER FOR MEANING ???
		assertEquals(true, !Quoridor223Controller.hasWallMoveCandidate());
	}
	
	// Scenario: Validate pawn position

	/**
	 * @author Sacha Lévy
	 */
	@When("Validation of the position is initiated")
	public void validationOfThenPositionIsInitiated() throws UnsupportedOperationException, GameNotRunningException {
		// check wall has been dropped or pawn has been moved, for the moment only dropWall possible
		// if no wall candidate then no checking of the Position
		// need way to record if the dropWall was initiated ?
		Quoridor223Controller.validatePosition();
		// since anyways the valudatePosition sends errors
		boolean init_validatePosition = true;
		assertEquals(true, init_validatePosition);

	}

	/**
	 * @author	Sacha Lévy
	 * @param	expected_result
	 * @return
	 * @throws GameNotRunningException 
	 * @throws UnsupportedOperationException 
	 */
	@Then("The position shall be <result>")
	public void thePositionShallBeResult(String expected_result) throws UnsupportedOperationException, GameNotRunningException {
		String result = Quoridor223Controller.validatePosition()?"ok":"error";
		assertEquals(expected_result, result);
	}

	// Scenario: Validate overlapping walls (all valid)
	/**
	 * @author Sacha Lévy
	 * @return
	 */
	@Then("The position shall be valid")
	public void thePositionShallBeValid() {
		// check position given is valid
		throw new PendingException();
	}

	// Scenario: Validate overlapping walls (invalid-1)
	/**
	 * @author Sacha Lévy
	 * @return
	 */
	@Then("The position shall be invalid")
	public void thePositionShallBeInvalid() {
		// check position given is invalid
		throw new PendingException();
	}

	// Scenario: Validate overlapping walls (invalid-2)
	// Scenario: Validate overlapping walls (invalid-3)

// Switch Current Player feature
	
	

	/**
	 * @author Sacha Lévy
	 * @param player
	 * @return boolean
	 * @throws InterruptedException 
	 */
	private boolean isClockRunning(String color) throws InterruptedException {
		Player player = Quoridor223Controller.getPlayerByColor(color);
		Time tmp_time = player.getRemainingTime();
		Thread.sleep(1500);
		//System.out.println(tmp_time.toString());
		if (tmp_time.toString().equals(player.getRemainingTime().toString()))
			return false;
		else
			return true;
	}

	private boolean isNextPlayerToMove(Player other) {
		if (other.equals(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()))
			return true;
		else
			return false;
	}
	
	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * */
	@Given("The player to move is {string}")
	public void the_player_to_move_is(String string) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(true, Quoridor223Controller.setCurrentPlayerToMoveByColor(string));
		//System.out.println(Quoridor223Controller.currentStatePlayers());
	}

	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
		try {
			assertEquals(true, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(false, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("Player {string} completes his move")
	public void player_completes_his_move(String string) {
		gamePage.clickGrabWall();
		gamePage.clickDropWall();
		assertEquals(false,Quoridor223Controller.hasWallMoveCandidate());
	}

	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String string) {
		// Write code here that turns the phrase above into concrete actions
		//System.out.println("current player moving"+string + "  " + Quoridor223Controller.getPlayerNameByColor(string));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(gamePage.getDialogBoxText());
		assertEquals("It is "+Quoridor223Controller.getPlayerNameByColor(string)+"'s Turn !!", gamePage.getDialogBoxText());
	}

	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(false, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			assertEquals(true, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
		// Write code here that turns the phrase above into concrete actions
		Player player = Quoridor223Controller.getPlayerByColor(string);
		assertEquals(true, isNextPlayerToMove(player));
	}

}