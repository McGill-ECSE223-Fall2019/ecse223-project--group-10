package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.naming.OperationNotSupportedException;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.GameIsDrawn;
import ca.mcgill.ecse223.quoridor.controller.GameIsFinished;
import ca.mcgill.ecse223.quoridor.controller.GameNotRunningException;
import ca.mcgill.ecse223.quoridor.controller.InvalidOperationException;
import ca.mcgill.ecse223.quoridor.controller.PawnBehavior;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
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
import ca.mcgill.ecse223.quoridor.model.StepMove;
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
	private boolean enterReplayMode = false;
	private ArrayList<Player> createUsersAndPlayers;
	private String gameDirectory = "./src/main/resources/gameFiles/";
	private String playerTryingToMove = null;
	private String inner_message_check_path = null;

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		//Game curGame =QuoridorApplication.getQuoridor().getCurrentGame();
		//curGame.getCurrentPosition().getWhitePosition().setTile(Quoridor223Controller.getTile(row, col))
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
		QuoridorApplication.CreateNewWhitePawnBehavior();
		QuoridorApplication.CreateNewBlackPawnBehavior();
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
			WallMove mv =new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			quoridor.getCurrentGame().addMove(mv);
			quoridor.getCurrentGame().setWallMoveCandidate(null);
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
	 * Feature 1: Start New Game
	 * Scenario: Initiate a new game
	 * @author Vanessa Ifrah
	 */
	@When("A new game is being initialized")
	public void aNewGameIsBeingInitialized() {

		// create the new game
		Quoridor223Controller.createGame();
	}

	/**
	 * @author Vanessa Ifrah
	 */
	@And("White player chooses a username")
	public void whitePlayerChoosesAUsername() {

		// white/first player chooses their name
		Quoridor223Controller.setUser("Vanessa", "white");

	}

	/**
	 * @author Vanessa Ifrah
	 */
	@And("Black player chooses a username")
	public void blackPlayerChoosesAUsername() {
		// black/second player chooses their name
		Quoridor223Controller.setUser("Jessica", "black");
	}

	/**
	 *  @author Vanessa Ifrah
	 */
	@And("Total thinking time is set")
	public void totalThinkingTimeIsSet() {
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:10:00"), "Vanessa");
		Quoridor223Controller.setThinkingTime(Time.valueOf("00:10:00"), "Jessica");
	}

	/**
	 * @author Vanessa Ifrah
	 */
	@Then("The game shall become ready to start")
	public void theGameShallBecomeReadyToStart() {

		// verify that the game has started
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus(), GameStatus.ReadyToStart);

	}

	/**
	 * Feature 1: Start New Game
	 * Scenario: Start clock
	 * @author Vanessa Ifrah
	 */
	@Given("The game is ready to start")
	public void theGameIsReadyToStart() {

		// set game to ready
		Quoridor223Controller.createGame();
		Quoridor223Controller.setGameToReady();

	}

	/**
	 * @author Vanessa Ifrah
	 */
	@When("I start the clock")
	public void iStartTheClock() {

		// start the clock -- clock starts when game starts running
		Quoridor223Controller.setGameToRun();

	}

	/**
	 * @author Vanessa Ifrah
	 */
	@Then("The game shall be running")
	public void theGameShallBeRunning() {

		// make sure the game is running
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus(), GameStatus.Running);

	}

	/**
	 * @author Vanessa Ifrah
	 */
	@And("The board shall be initialized")
	public void theBoardShallBeInitialized() {
		assertEquals(QuoridorApplication.getQuoridor().hasBoard(), true);
		
	}

	/**
	 * Feature 2: Provide Select User Naame
	 * Scenario: Select existing user name
	 * @author Vanessa Ifrah
	 * @param playerColor
	 */
	@Given("Next player to set user name is {string}")
	public void nextPlayerToSetUserNameIs(String playerColor) {

		Quoridor223Controller.setUser("player", playerColor);

	}

	/**
	 * @author Vanessa Ifrah
	 * @param userName
	 */
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

	/**
	 * @author Vanessa Ifrah
	 * @param userName
	 */
	@When("The player selects existing {string}")
	public void thePlayerSelectsExisting(String userName) {

		// player set to white or black
		if (userName == "Daniel") {
			Quoridor223Controller.setUser(userName, "white");
		} else {
			Quoridor223Controller.setUser(userName, "black");
		}

	}

	/**
	 * @author Vanessa Ifrah
	 * @param playerColor
	 * @param userName
	 */
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
	 * Feature 2: Provide Select User Name
	 * Scenario: Create new user name
	 * @author Vanessa Ifrah
	 * @param userName
	 */
	@And("There is no existing user {string}")
	public void thereIsNoExistingUser(String userName) {

		assertEquals(Quoridor223Controller.checkNameList(userName), false);

	}
	
	/**
	 * @author Vanessa Ifrah
	 * @param userName
	 */
	@When("The player provides new user name: {string}")
	public void thePlayerProvidesNewUserName(String userName) {

		Quoridor223Controller.setUser(userName, "black");

	}
	
	/**
	 * Feature 2: Provide Select User Name
	 * Scenario: User name already exists
	 * @author Vanessa Ifrah
	 * @param userName
	 */
	@Then("The player shall be warned that {string} already exists")
	public void thePlayerShallBeWarnedThatUserNameAlreadyExists(String userName) {

		// if name exists, warn user
		Quoridor223Controller.warnUser(userName);

	}

	/**
	 * @author Vanessa Ifrah
	 * @param playerColor
	 */
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
		assertEquals(whiteTile, quoridor.getBoard().getTile(76));
	}

	/**
	 * @author Andrew Ta
	 */
	@And("Black's pawn shall be in its initial position")
	public void blackPawnShallBeInItsInitialPosition() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		GamePosition position = quoridor.getCurrentGame().getCurrentPosition();
		Tile blackTile = position.getBlackPosition().getTile();

		assertEquals(blackTile, quoridor.getBoard().getTile(4));
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
	// ResignGame and IdentifyDraw
	// **********************************************
	
	/**
	 * @author Andrew Ta
	 */
	@When("Player initates to resign")
	public void initiateToResign() {
		gamePage.clickForfeit();
	}
	
	/**
	 * @author Andrew Ta
	 */
	@Then("Game result shall be {string}")
	public void gameResult(String result) {
		assertEquals(result, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().toString());
	}
	
	/**
	 * @author Andrew Ta
	 */
	@And("The game shall no longer be running")
	public void isRunning() {
		assertEquals(false, gamePage.isBlackClockRunning());
		assertEquals(false, gamePage.isWhiteClockRunning());
	}
	
	/**
	 * @author Andrew Ta
	 */
	@Given("The following moves were executed:")
	public void executeMove(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		QuoridorApplication.GetWhitePawnBehavior().startGame();
		QuoridorApplication.GetBlackPawnBehavior().startGame();
		
		for (Map<String, String> map : valueMaps) {
			int row = Integer.decode(map.get("row"));
			int turn = Integer.decode(map.get("turn"));
			
			if(turn == 1) {
				if(row == 8) gamePage.clickMovePlayer("up");
				else gamePage.clickMovePlayer("down");
			}else {
				if(row == 2) gamePage.clickMovePlayer("down");
				else gamePage.clickMovePlayer("up");
			}
		}
	}
	
	
	/**
	 * @author Andrew Ta
	 */
	@Given("Player {string} has just completed his move")
	public void completeMove(String player) {
		
	}
	
	/**
	 * @author Andrew Ta
	 */
	@And("The last move of {string} is pawn move to {int}:{int}")
	public void lastMove(String player, int row, int col) {
		if(player.equals("white")) {
			if(col == 4) {
				gamePage.clickMovePlayer("left");
			}else if(col == 6) {
				gamePage.clickMovePlayer("right");
			}else if(row == 8) {
				gamePage.clickMovePlayer("up");
			}
		}else {
			if(row == 2) {
				gamePage.clickMovePlayer("down");
			}else gamePage.clickMovePlayer("up");
		}
	}
	
	/**
	 * @author Andrew Ta
	 */
	@When("Checking of game result is initated")
	public void checkGameResult() {
		
	}
	
	
	// **********************************************
	// TODO: MovePawn and JumpPawn
	// **********************************************
	/**
	 * And
	 * @author Sacha Lévy
	 * @param row
	 * @param col
	 * */
	@And("The player is located at {int}:{int}")
	public void thePlayerIsLocatedAt(int row, int col) {
		String player = "player";
		assertTrue("The player is not located at {int}:{int}", playerIsLocatedAt(player, row, col));
	}
	
	/**
	 * And
	 * @author Sacha Lévy
	 * @param row
	 * @param col
	 * */
	@And("The opponent is located at {int}:{int}")
	public void theOpponentIsLocatedAt(int row, int col) {
		String player = "opponent";
		assertTrue("The opponent is not located at {int}:{int}", playerIsLocatedAt(player, row, col));
	}
	
	/**
	 * And
	 * @author Sacha Lévy
	 * @param side
	 * @param dir
	 * */
	@And("There are no {string} walls {string} from the player nearby")
	public static void thereAreNoWallsFromThePlayerNearBy(String dir, String side) {
		// should this method simply check that there are no walls from the player near by ? or set the board to conform ?
		assertTrue("There are {string} walls {string} from the player nearby", hasWallsFromThePlayerNearby(dir, side));
	}
	
	/**
	 * And
	 * @author Sacha Lévy
	 * @param side
	 * */
	@And("The opponent is not {string} from the player")
	public void theOpponentIsNotSideFromThePlayer(String side) {
		assertFalse("The opponent is {string} from the player", isOpponentSideFromThePlayer(side));
	}
	
	/**
	 * And
	 * @param dir
	 * @param side
	 * @author Sacha Lévy
	 * */
	@And("There are no {string} walls {string} from the player")
	public void thereAreNoWallsFromThePlayer(String dir, String side) {
		// same thing as thereAreNoWallsFromThePlayerNearby
		assertTrue("There are {string} walls {string} from the player", hasWallsFromThePlayerNearby(dir, side));
	}
	
	@When("Player {string} initiates to move {string}")
	public void playerInitiatesToMove(String color, String side) throws GameNotRunningException, InvalidOperationException {
		playerTryingToMove = color;
		QuoridorApplication.GetBlackPawnBehavior().startGame();
		QuoridorApplication.GetWhitePawnBehavior().startGame();
		gamePage.clickMovePlayer(side);
	}
	
	@Then("The move {string} shall be {string}")
	public void theMoveSideShallBeStatus(String side, String status) throws GameNotRunningException, InvalidOperationException {
		boolean valid = false;	
		if(!gamePage.getGameMessage().equals("Illegal Move") && status.equals("success"))valid = true;
		if(gamePage.getGameMessage().equals("Illegal Move") && status.equals("illegal"))valid = true;
		if(!valid)System.out.println("----------------------------------------------"+ gamePage.getGameMessage());
		assertTrue("Illegal move are made or legal move are not made",valid);
	}
	
	/**
	 * Then
	 * @author Mitchell Keeley
	 * @param row
	 * @param col
	 */
	@And("Player's new position shall be {int}:{int}")
	public void playerNewPositionShallBe(int row, int col){
		assertTrue("invalid position", checkPlayerPositionByColor(playerTryingToMove, row, col));
	}
	
	/**
	 * Then
	 * @author Mitchell Keeley
	 * @param color
	 * @throws GameNotRunningException 
	 * @throws UnsupportedOperationException 
	 */
	@And("The next player to move shall become {string}")
	public void theNextPlayerToMoveBeCome(String color) throws UnsupportedOperationException, GameNotRunningException {
		assertTrue("next player not set properly", checkCurrentPlayerToMoveByColor(color));
	}
	
	/**
	 * Given
	 * @author Mitchell Keeley
	 * @param dir
	 * @param row
	 * @param col
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 */
	@And("There is a {string} wall at {int}:{int}")
	public void thereIsAWall(String dir, int row, int col) throws GameNotRunningException, InvalidOperationException {
		placeWallWithDirectionAt(dir, row, col);
	}
	
	/**
	 * Given
	 * @author Mitchell Keeley
	 * @param side
	 */
	@And("My opponent is not {string} from the player")
	public void myOpponentIsNotSideFromThePlayer(String side) {
		placeOpponentFarFromPlayer();
	}
	
	/**
	 * @param side
	 * @author Sacha Lévy
	 * @return check if opponent is on given side of the player
	 * */
	private static boolean isOpponentSideFromThePlayer(String side) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player current_player = current_game.getCurrentPosition().getPlayerToMove();
		PlayerPosition current_position;
		PlayerPosition opponent_position;
		
		if(current_player.equals(current_game.getBlackPlayer())) {
			current_position = current_game.getCurrentPosition().getBlackPosition();
			opponent_position = current_game.getCurrentPosition().getWhitePosition();
		}
		else {
			current_position = current_game.getCurrentPosition().getWhitePosition();
			opponent_position = current_game.getCurrentPosition().getBlackPosition();
		} 
		
		// inversion of directions because our board is filpped 90° ?
		if(side.equals("up")) {
			// up == col ++
			if(current_position.getTile().getColumn()+1==opponent_position.getTile().getColumn()) return true;
		}
		else if (side.equals("down")) {
			// down == col --
			if(current_position.getTile().getColumn()-1==opponent_position.getTile().getColumn()) return true;
		}
		else if (side.equals("right")) {
			// down == row ++
			if(current_position.getTile().getRow()+1==opponent_position.getTile().getRow()) return true;
		}
		else if (side.equals("left")) {
			// down == row --
			if(current_position.getTile().getRow()-1==opponent_position.getTile().getRow()) return true;
		}
		return false;
	}
	
	/**
	 * @author Sacha Lévy
	 * @param dir
	 * @param side
	 * @return hasWallsFromThePlayerNearby
	 * */
	private static boolean hasWallsFromThePlayerNearby(String dir, String side) {
		// method check if there are walls from the Player nearby, isn't 
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player current_player = current_game.getCurrentPosition().getPlayerToMove();
		PlayerPosition current_position;
		String player;
		
		if(current_player.equals(current_game.getBlackPlayer())) {
			current_position = current_game.getCurrentPosition().getBlackPosition();
			player = "black";
		}
		else {
			current_position = current_game.getCurrentPosition().getWhitePosition();
			player = "white";
		} 
		int row = current_position.getTile().getRow();
		int col = current_position.getTile().getColumn();
		
		// check if there is a wall from the player to Move nearby 
		boolean direction = dir.equals("horizontal") ? true : false;
		HashMap<Integer, Boolean> player_walls = loadPlayerWallsHash(player);
			
		// row ++ col --
		if(side.equals("upleft")) if(player_walls.get((row+1)*9+col-1)!=null) return false; 
		// row ++ col ++
		else if(side.equals("upright")) if(player_walls.get((row+1)*9+col+1)!=null) return false; 
		// row -- col --
		else if(side.equals("downleft")) if(player_walls.get((row-1)*9+col-1)!=null) return false; 
		// row -- col ++
		else if(side.equals("downright")) if(player_walls.get((row-1)*9+col+1)!=null) return false; 
		else return true;
		
		return true;
	}
	
	/**
	 * @author Sacha Lévy
	 * @param player's color
	 * @return a hashmap with all wall positions on board
	 * */
	private static HashMap<Integer, Boolean> loadPlayerWallsHash(String color) {
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		HashMap<Integer, Boolean> wallPositions = new HashMap<Integer, Boolean>();
		List<Wall> walls;
		if(color.equals("black")) walls = current_game.getCurrentPosition().getBlackWallsOnBoard();
		else walls = current_game.getCurrentPosition().getWhiteWallsOnBoard();
		for(Wall wall: walls) {
			WallMove wall_move = wall.getMove();
			int row = wall_move.getTargetTile().getRow();
			int col = wall_move.getTargetTile().getColumn();
			boolean dir_attr = false;
			if (wall_move.getWallDirection().equals(Direction.Horizontal))
				dir_attr = true;
			wallPositions.put(row * 9 + col, dir_attr);
		}
		return wallPositions;
	}
	
	/**
	 * @author Sacha Lévy
	 * @param player, row, col
	 * @return wasThePlayerSetAtLocation
	 * */
	private boolean playerIsLocatedAt(String player, int row, int col) {
		// defines walls near the player
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player current_player;
		String color;
		PlayerPosition current_position;
		
		// player to move has been defined precedently
		if(player.equals("player")) current_player = current_game.getCurrentPosition().getPlayerToMove();
		else current_player = current_game.getCurrentPosition().getPlayerToMove().getNextPlayer();
		
		if(current_player.equals(current_game.getBlackPlayer())) {
			current_position = current_game.getCurrentPosition().getBlackPosition();
			color = "black";
		}
		else {
			current_position = current_game.getCurrentPosition().getWhitePosition();
			color = "white";
		}
		Tile new_position = Quoridor223Controller.getTile(row, col);
		return current_position.setTile(new_position);
	}
	
	/**
	 * @author Sacha Lévy
	 * */
	private boolean setPlayerPosition(String player, int row, int col) {
		// defines walls near the player
		Game current_game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition current_position;
		
		// player to move has been defined precedently
		if(player.equals("black")) current_position = current_game.getCurrentPosition().getBlackPosition();
		else current_position = current_game.getCurrentPosition().getWhitePosition();
		Tile new_position = Quoridor223Controller.getTile(row, col);
		return current_position.setTile(new_position);
	}
	
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
	 * @throws InvalidOperationException
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
	 * @throws InvalidOperationException
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
		WallMove move = (WallMove) curGame.getMove(curGame.getMoves().size()-1);
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
		assertEquals(curGame.getMove(curGame.getMoves().size()-1), initialMove);

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
		deleteFileIfItExists(gameDirectory + filename);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws Throwable
	 */
	@When("The user initiates to save the game with name {string}")
	public void theUserInitiatesToSaveTheGameWithNameFilename(String filename) throws Throwable {
		cucumberFilename = filename;
		Quoridor223Controller.checkIfFileExists(gameDirectory + filename);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws IOException
	 * @throws UnsupportedOperationException, IOExceptionable
	 */
	@Then("A file with {string} shall be created in the filesystem")
	public void aFileWithFilenameShallBeCreatedInTheFilesystem(String filename) throws IOException {
		cucumberFilename = filename;
		Boolean result = Quoridor223Controller.saveCurrentGameAsFile(gameDirectory + filename);
		assertTrue("File is not a valid save file", result);
	}

	/**
	 * @author Mitchell Keeley
	 * @throws IOException
	 */
	@Given("File {string} exists in the filesystem")
	public void fileFilenameExistsInTheFilesystem(String filename) throws IOException {
		cucumberFilename = filename;
		ensureFileExists(gameDirectory + filename);
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
		assertTrue("The file was not modified", simulateUserAttemptingToOverwriteFile(gameDirectory + filename, JOptionPane.YES_OPTION));
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
		assertFalse("The file was modified", simulateUserAttemptingToOverwriteFile(gameDirectory + filename, JOptionPane.NO_OPTION));
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
		Quoridor223Controller.checkIfLoadFileIsValidFile(gameDirectory + filename);
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
			loadSuccessful = Quoridor223Controller.getLoadPositionDataFromFile(gameDirectory + cucumberFilename);
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
	// TODO: Save Game starts here
	// **********************************************
	
	// save game has the same stepDefs as Save Position...
	// however, these have been updated to use the newer method of saving (Save Game)
	
	// **********************************************
	// TODO: Load Game starts here
	// **********************************************
	
	/**
	 * @author Mitchell Keeley
	 * @param filename
	 */
    @When("I initiate to load a game in {string}")
    public void iInitiateToLoadAGameInFilename(String filename) {
    	cucumberFilename = filename;
		createAndPrepareGame(createUsersAndPlayers);
		Quoridor223Controller.checkIfLoadFileIsValidFile(gameDirectory + filename);
    }
    
    /**
     * @author Mitchell Keeley
     * @throws InvalidOperationException 
     * @throws GameNotRunningException 
     * @throws GameIsFinished 
     * @throws GameIsDrawn 
     */
    @And("Each game move is valid")
    public void eachGameMoveIsValid() throws GameNotRunningException, InvalidOperationException {
    	try {
    		loadSuccessful = Quoridor223Controller.getLoadGameDataFromFile(gameDirectory + cucumberFilename);
    	} catch(GameIsDrawn | GameIsFinished ex) {
			// GamePage normally catches these exceptions and then sets to replay mode
    		// Due to the order of operations, this is handled in another step
    		enterReplayMode = true;
		}
    }
    
    @And("The game has no final results")
    public void theGameHasNoFinalResults() {
    	Quoridor quoridor = QuoridorApplication.getQuoridor();
    	GameStatus status = quoridor.getCurrentGame().getGameStatus();
    	boolean ended = status.equals(GameStatus.BlackWon) ||
    			status.equals(GameStatus.WhiteWon) ||
    			status.equals(GameStatus.Draw);
    	assertFalse("The game is no longer running", ended);
    }
    
	/**
	 * @author Mitchell Keeley
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 */
	@And("The position to load is valid")
	public void thePositionToLoadIsValid() throws GameNotRunningException, InvalidOperationException {
		if(cucumberFilename.contains(".dat")) {
			loadSuccessful = Quoridor223Controller.getLoadPositionDataFromFile(gameDirectory + cucumberFilename);
		} else {
			// redundant step taken care of by ("Each game move is valid")
			//loadSuccessful = Quoridor223Controller.getLoadGameDataFromFile(gameDirectory + cucumberFilename);
			// here is where we actually enter replay mode, since the order of tests requires it
			if(enterReplayMode) {
				Quoridor223Controller.enterReplayMode();
			}
		}
	}
	
	/**
	 * @author Mitchell Keeley
	 * @throws GameNotRunningException
	 * @throws InvalidOperationException
	 */
	@And("The game to load has an invalid move")
	public void theGameToLoadHasAnInvalidMove() throws GameNotRunningException, InvalidOperationException {
		try {
			loadSuccessful = Quoridor223Controller.getLoadGameDataFromFile(gameDirectory + cucumberFilename);
		} catch(GameIsDrawn | GameIsFinished ex) {
			// GamePage normally catches these exceptions and then sets to replay mode
			// Due to the order of operations, this is handled in another step
			enterReplayMode = true;
		}
	}
    
	/**
	 * @author Mitchell Keeley
	 * @throws OperationNotSupportedException 
	 */
	@Then("The game shall notify the user that the game file is invalid")
	public void theGameShallNotifyTheUserThatTheGameFileIsInvalid() throws OperationNotSupportedException {
		assertFalse("Invalid load does not return an error", loadSuccessful);
	}
	
	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		PawnBehavior white = QuoridorApplication.GetWhitePawnBehavior();
		PawnBehavior black = QuoridorApplication.GetBlackPawnBehavior();
		// Avoid null pointer for step definitions that are not yet implemented.
		// gamePage.delete();
		if(gamePage!=null)gamePage.delete();
		gamePage = null;
		
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		if(white!=null) {
			white.delete();
			white = null;
		}
		if(black!=null) {
			black.delete();
			black = null;
		}
		QuoridorApplication.delete();
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i + 1);
			if (wall != null) {
				wall.delete();
			}
		}
	}
	
	
	// *****************************************************************
	// TODO: Enter Replay Mode feature
	// *****************************************************************
	
	/**
	 * Scenario: Entering replay mode
	 * @author Vanessa Ifrah
	 */
	@When("I initiate replay mode")
	public void initiateReplayMode() throws GameNotRunningException, InvalidOperationException {
		
		createAndStartGame(createUsersAndPlayers);
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Tile whitePlayerTile = quoridor.getBoard().getTile(76);
		Tile blackPlayerTile = quoridor.getBoard().getTile(4);
		GamePosition curPos = quoridor.getCurrentGame().getCurrentPosition();
		curPos.getBlackPosition().setTile(blackPlayerTile);
		curPos.getWhitePosition().setTile(whitePlayerTile);
		QuoridorApplication.CreateNewWhitePawnBehavior();
		QuoridorApplication.CreateNewBlackPawnBehavior();
		QuoridorApplication.GetWhitePawnBehavior().startGame();
		QuoridorApplication.GetBlackPawnBehavior().startGame();
		gamePage = new GamePage();
		Quoridor223Controller.enterReplayMode();
		
	}
	
	@Then("The game shall be in replay mode")
	public void gameShallBeInReplayMode() {
		
		assertEquals(Quoridor223Controller.isReplay(), true);
		
	}
	
	/**
	 * Scenario: Continue an unfinished game
	 * @author Vanessa Ifrah
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 */
	@Given("The game is replay mode")
	public void gameIsReplayMode() throws GameNotRunningException, InvalidOperationException {
		
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Tile whitePlayerTile = quoridor.getBoard().getTile(76);
		Tile blackPlayerTile = quoridor.getBoard().getTile(4);
		GamePosition curPos = quoridor.getCurrentGame().getCurrentPosition();
		curPos.getBlackPosition().setTile(blackPlayerTile);
		curPos.getWhitePosition().setTile(whitePlayerTile);
		QuoridorApplication.CreateNewWhitePawnBehavior();
		QuoridorApplication.CreateNewBlackPawnBehavior();
		QuoridorApplication.GetWhitePawnBehavior().startGame();
		QuoridorApplication.GetBlackPawnBehavior().startGame();
		gamePage = new GamePage();
		Quoridor223Controller.enterReplayMode();

	}
	
	@And("The game does not have a final result")
	public void gameDoesNotHaveAFinalResult() {
		
		boolean hasFinalResult = false;
		GameStatus gamestatus = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		
		if (gamestatus == GameStatus.WhiteWon || 
				gamestatus == GameStatus.BlackWon ||
				gamestatus == GameStatus.Draw) {
			
			hasFinalResult = true;
			
		}
		
		assertFalse(hasFinalResult);
		
	}
	
	@When("I initiate to continue game")
	public void initiateToContinueGame() {
		
		gamePage.clickReplayGame();
		
	}
	
	@And("The remaining moves of the game shall be removed")
	public void remainingMovesOfTheGameShalBeRemoved() {
		
		Game currGame = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition currPosition = currGame.getCurrentPosition();
		List<GamePosition> gamePositions = currGame.getPositions();
		
		assertTrue(currPosition.equals(gamePositions.get(gamePositions.size()-1)));
		
	}

	/**
	 * Scenario: Continue a finished game
	 * @author Vanessa Ifrah
	 */
	@And("The game has a final result")
	public void theGameHasAFinalResult() {
		
		boolean hasFinalResult = false;
		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		GameStatus gamestatus = currentGame.getGameStatus();
		
		if (gamestatus == GameStatus.WhiteWon || 
				gamestatus == GameStatus.BlackWon ||
				gamestatus == GameStatus.Draw) {
			
			hasFinalResult = true;
			
		}
		
		assertTrue(hasFinalResult);
		
	}
	
	@And("I shall be notified that finished games cannot be continued")
	public void iShallBeNotifiedThatFinishedGamesCannotBeContinued() {
		
		assertEquals(gamePage.getDialogBoxText(), "Game not running");
		
	}

	
	// *****************************************************************
	// TODO: Replay feature: Jump To Start Position and Jump To Last Position start here
	// *****************************************************************
	@Given("The game is in replay mode")
	public void gameIsInReplayMode() {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Tile whitePlayerTile = quoridor.getBoard().getTile(76);
		Tile blackPlayerTile = quoridor.getBoard().getTile(4);
		GamePosition curPos = quoridor.getCurrentGame().getCurrentPosition();
		curPos.getBlackPosition().setTile(blackPlayerTile);
		curPos.getWhitePosition().setTile(whitePlayerTile);
		QuoridorApplication.CreateNewWhitePawnBehavior();
		QuoridorApplication.CreateNewBlackPawnBehavior();
		QuoridorApplication.GetWhitePawnBehavior().startGame();
		QuoridorApplication.GetBlackPawnBehavior().startGame();
		gamePage = new GamePage();
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.Replay);
	}
	@Given("The following moves have been played in game:")
	public void theFollowingMoveHaveBeenPalyedInGame(io.cucumber.datatable.DataTable dataTable) {
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.Running);
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for(Map<String,String> mp: valueMaps) {
			int mv =Integer.parseInt(mp.get("mv"));
			int rnd = Integer.parseInt(mp.get("rnd"));
			String move =  mp.get("move");
			performMove(move);
		}
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.Replay);
	}
	private void performMove(String move) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		boolean isWhite = curGame.getCurrentPosition().getPlayerToMove().equals(curGame.getWhitePlayer());
		PlayerPosition curPos = isWhite? curGame.getCurrentPosition().getWhitePosition():curGame.getCurrentPosition().getBlackPosition();
		if(move.length()==3) {
			int row = move.charAt(1)-'0';
			int col = move.charAt(0)-'a'+1;
			boolean horizontal = move.charAt(2)=='h';
			gamePage.clickGrabWall();
			curGame.getWallMoveCandidate().setTargetTile(Quoridor223Controller.getTile(row, col));
			curGame.getWallMoveCandidate().setWallDirection(horizontal?Direction.Horizontal:Direction.Vertical);
			gamePage.clickDropWall();
		}
		else {
			int row = move.charAt(1)-'0';
			int col = move.charAt(0)-'a'+1;
			int curR = curPos.getTile().getRow();
			int curC = curPos.getTile().getColumn();
			if(curR>row) {
				gamePage.clickMovePlayer("up");
			}
			else if(curC>col) {
				gamePage.clickMovePlayer("left");
			}
			else if(curR<row) {
				gamePage.clickMovePlayer("down");
			}
			else gamePage.clickMovePlayer("right");
		}
	}
	@And("The next move is {int}.{int}")
	public void theNextMoveIs(int movno, int rndno) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();	
		int ind = (movno-1)*2+rndno-1;
		curGame.setCurrentPosition(curGame.getPosition(ind));
	}
	@When("Jump to start position is initiated")
	public void jumpToStartPositionIsInitiated() throws InvalidOperationException {
//		gamePage.clickJumpStart();
		try{
			Quoridor223Controller.jumpToStartPosition();
		}catch(InvalidOperationException e) {
			System.out.println(e.getMessage());
		}
	}
	@When("Jump to final position is initiated")
	public void jumpToFinalPositionIsInitiated() throws InvalidOperationException {
//		gamePage.clickJumpFinal();
		try{
			Quoridor223Controller.jumpToFinalPosition();
		}catch(InvalidOperationException e) {
			System.out.println(e.getMessage());
		}
	}
    @When("Step forward is initiated")
	public void Stepforward() throws InvalidOperationException{
		//call controller
    	try{
    		Quoridor223Controller.StepForward();
		}catch(InvalidOperationException e) {
			System.out.println(e.getMessage());
		}

	}
    @When("Step backward is initiated")
	public void Stepbackward() throws InvalidOperationException{
		//call controller
    	try{
    		Quoridor223Controller.StepBackward();
    	}catch(InvalidOperationException e) {
    		System.out.println(e.getMessage());
    	}
	}
	@Then("The next move shall be {int}.{int}")
	public void theNextMoveShallBe(int nmov, int nrnd) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();	
		
		int index = 0;
		List<GamePosition> list = curGame.getPositions();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals(curGame.getCurrentPosition())){
				index = i;
			}
		}

		if(index<curGame.getMoves().size()) {
			Move curMove = curGame.getMove(index);
			int curMoveNumber = curMove.getMoveNumber();
			int curRoundNumber = curMove.getRoundNumber();
			assertEquals(nmov, curMoveNumber);
			assertEquals(nrnd, curRoundNumber);
		}
		else {
			Move curMove = curGame.getMove(curGame.getMoves().size()-1);
			int curRoundNumber = curMove.getRoundNumber()==1?2:1;
			int curMoveNumber = curMove.getMoveNumber()+curRoundNumber==1?1:0;
			
		}
	}
	
	@And("White player's position shall be \\({int},{int})")
	public void whitePlayersPositionShallBe(int wrow, int wcol) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Tile tile = curGame.getCurrentPosition().getWhitePosition().getTile();
		int curWhiteRow = tile.getRow();
		int curWhiteCol = tile.getColumn();
		
		assertEquals(wrow, curWhiteRow);
		assertEquals(wcol, curWhiteCol);
		
	}
	
	@And("Black player's position shall be \\({int},{int})")
	public void blackPlayersPositionShallBe(int brow, int bcol) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Tile tile = curGame.getCurrentPosition().getBlackPosition().getTile();
		int curBlackRow = tile.getRow();
		int curBlackCol = tile.getColumn();
		
		assertEquals(brow, curBlackRow);
		assertEquals(bcol, curBlackCol);
	}
	
	@And("White has {int} on stock")
	public void whiteHasOnStock(int wwallno) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		int curWhiteStock = curGame.getCurrentPosition().getWhiteWallsInStock().size();
		
		assertEquals(wwallno, curWhiteStock);
	}
	
	@And("Black has {int} on stock")
	public void blackHasOnStock(int bwallno) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();	
		int curBlackStock = curGame.getCurrentPosition().getBlackWallsInStock().size();
		
		assertEquals(bwallno, curBlackStock);
	}
	
	// *****************************************************************
	// TODO: Jump To Start Position and Jump To Last Position end here
	// *****************************************************************
	
	
	

	// *****************************************************************
	// TODO: Identify Game Won feature
	// *****************************************************************
	
	/**
	 * Scenario: Player reaches target area
	 * @author Vanessa Ifrah
	 */	
	@And("The new position of {string} is {int}:{int}")
	public void newPositionOfPlayerIs(String player, int row, int col) {
		QuoridorApplication.CreateNewWhitePawnBehavior().startGame();
		QuoridorApplication.CreateNewBlackPawnBehavior().startGame();
		
		if(player.equals("white")) {
			if(row == 9) {
				gamePage.clickMovePlayer("down");
			}else if (row == 8){
				gamePage.clickMovePlayer("up");
			} if(row == 1){
				for(int i = 1; i < 4; i++) {
					QuoridorApplication.GetWhitePawnBehavior().moveUp();
				}
				gamePage.clickMovePlayer("up");
			}
		}else {
			if(row == 1) {
				gamePage.clickMovePlayer("up");
			}else if (row == 2){
				gamePage.clickMovePlayer("down");
			} if(row == 9){
				for(int i = 1; i < 4; i++) {
					QuoridorApplication.GetBlackPawnBehavior().moveDown();
					System.out.println(QuoridorApplication.GetBlackPawnBehavior().getPawnSMPlayingNorthSouthNorthSouth());
				}
				Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
				curGame.getCurrentPosition().setPlayerToMove(curGame.getBlackPlayer());
				gamePage.clickMovePlayer("down");
			}
		}
	}
	
	@And("The clock of {string} is more than zero")
	public void clockOfPlayerIsMoreThanZero(String player) {
		
		assertNotEquals(Quoridor223Controller.getRemainingTime(player), 0);
		
	}
	
	
	/**
	 * Scenario: Player's time is exceeded
	 * @author Vanessa Ifrah
	 */	
	@When("The clock of {string} counts down to zero")
	public void clockOfPlayerCountsDowntoZero(String player) {
		GamePage temp = gamePage;
		if(player.equals("white")) {
			gamePage.setWhiteTime("00:00:01");
			gamePage.setBlackTime("00:00:10");
		}else {
			gamePage = new GamePage();
			gamePage.setBlackTime("00:00:01");
			gamePage.setWhiteTime("00:00:10");
		}
		
		try {
			TimeUnit.SECONDS.sleep(3);
		}catch(Exception e){
			
		}
		gamePage = temp;
		gamePage.setWhiteTime("00:10:00");
		gamePage.setBlackTime("00:10:00");
		gamePage.killClock();
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


		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		// @formatter:off
		/*
		 * __________ | | | | |x-> <-x| | | |__________|
		 * 
		 */
		// @formatter:on
		Player player1 = new Player(Time.valueOf("00:10:00"), user1, 1, Direction.Vertical);
		Player player2 = new Player(Time.valueOf("00:10:00"), user2, 9, Direction.Vertical);

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
	
	/**
	 * Creates a game and prepares it so a new game position can be loaded from a save file
	 * @author Mitchell Keeley
	 * @param players
	 */
	private void createAndPrepareGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(76);
		Tile player2StartPos = quoridor.getBoard().getTile(4);
		
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
		
		PawnBehavior whitebehavior = QuoridorApplication.CreateNewWhitePawnBehavior();
		PawnBehavior blackbehavior = QuoridorApplication.CreateNewBlackPawnBehavior();
		whitebehavior.startGame();
		blackbehavior.startGame();
		
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
		File file = new File(filename);
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
		File file = new File(filename);
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
				fileUpdated = Quoridor223Controller.saveCurrentGameAsFile(filename);
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
	 * Checks the current player to move by color
	 * @author Mitchell Keeley
	 * @param playerColor
	 * @return
	 */
	public static boolean checkCurrentPlayerToMoveByColor(String playerColor) {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = curGame.getCurrentPosition().getPlayerToMove();
		return curPlayer.equals(getPlayerByColor(playerColor));
	}
	
	/**
	 * Places a wall with the specified direction and position
	 * @param direction
	 * @param row
	 * @param col
	 * @throws InvalidOperationException 
	 * @throws GameNotRunningException 
	 */
	public static void placeWallWithDirectionAt(String direction, int row, int col) throws GameNotRunningException, InvalidOperationException {
		// Grab the wall
		Quoridor223Controller.grabWall();
		
		// check if the Game is running if not throw exception
		if (!Quoridor223Controller.isRunning())
			throw new GameNotRunningException("Game not running");		
		
		// get the wall move candidate
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if (curGame.getWallMoveCandidate() == null)
			throw new InvalidOperationException("No wall Selected");
		WallMove candidate = curGame.getWallMoveCandidate();
		
		// check the validity of the move
		if (!Quoridor223Controller.isWallPositionValid(row, col))
			throw new InvalidOperationException("Illegal Move");
		// update the move candidate according to the change.
		candidate.setTargetTile(Quoridor223Controller.getTile(row, col));
		candidate.setWallDirection(stringToDirection(direction));
		
		// drop the wall		
		Quoridor223Controller.dropWall();
		// switchplayer again as dropwall feature includes a switchplayer operation
		Quoridor223Controller.SwitchPlayer();
		//curGame.getCurrentPosition().setPlayerToMove(curGame.getCurrentPosition().getPlayerToMove().getNextPlayer());
		return;
	}
	
	/**
	 * Places the opponent at the opposite end of the board
	 * @author Mitchell Keeley
	 */
	public static void placeOpponentFarFromPlayer() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition position = game.getCurrentPosition();
		Player curPlayer = position.getPlayerToMove();	
		Player opponent;
		PlayerPosition farPosition;
		Tile tile;
		int newRow, newCol;
		
		if (curPlayer.equals(game.getWhitePlayer())) {
			tile = position.getWhitePosition().getTile();
			newCol = (tile.getColumn() + 5)%9 + 1;
			newRow = (tile.getRow() + 5)%9 + 1;
			
			opponent = game.getBlackPlayer();
			farPosition = new PlayerPosition(opponent, Quoridor223Controller.getTile(newRow, newCol));
			position.setBlackPosition(farPosition);			
		} else {
			tile = position.getBlackPosition().getTile();
			newCol = (tile.getColumn() + 5)%9 + 1;
			newRow = (tile.getRow() + 5)%9 + 1;
			
			opponent = game.getWhitePlayer();
			farPosition = new PlayerPosition(opponent, Quoridor223Controller.getTile(newRow, newCol));
			position.setWhitePosition(farPosition);	
		}		
	}
	
	/**
	 * Checks the current player position is row,column
	 * @author Mitchell Keeley
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean checkCurrentPlayerPosition(int row, int col) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		PlayerPosition playerPosition;
		String color;
		// why we need not to change the player in the try pawn move method => otherwise swaps everything here
		if (game.getWhitePlayer().equals(game.getCurrentPosition().getPlayerToMove())) {
			playerPosition = game.getCurrentPosition().getWhitePosition();
			color = "white";
		} else {
			playerPosition = game.getCurrentPosition().getBlackPosition();
			color = "black";
		}
		
		Tile tile = playerPosition.getTile();
		//System.out.println(String.format("expected %s player position: row %d col %d", color, row, col));
		///System.out.println(String.format("actual %s player position: row %d col %d", color, tile.getRow(), tile.getColumn()));
		if (tile.getRow() == row && tile.getColumn() == col) return true;
		else return false;
	}
	
	/**
	 * Checks that the specified player is at position row,col
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
	 * Checks that the player specified by the given color has a wall at row,col with the specified direction
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
	 * @author Mitchell Keeley
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
	
// ValidatePosition feature
	
	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @param int1, int2
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
	    // define the new game position
	    assertEquals(true, Quoridor223Controller.setGamePawnPosition(int1, int2));
	}
	
	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @param int1, int2
	 * @throws InvalidOperationException 
	 */
	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() throws InvalidOperationException {
	    try {
			Quoridor223Controller.validatePosition();
		} catch (UnsupportedOperationException | GameNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @param string
	 * @throws InvalidOperationException 
	 */
	@Then("The position shall be {string}")
	public void the_position_shall_be(String string) throws InvalidOperationException {
		String result = "error";
		try {
			if(Quoridor223Controller.validatePosition()) result = "ok";
			else result = "error";
		} catch (UnsupportedOperationException | GameNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    assertEquals(string, result);
		
	}

	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @param int1, int2, string
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
		Direction dir = string.equals("horizontal") ? Direction.Horizontal: Direction.Vertical;
	    assertEquals(true, Quoridor223Controller.createNewWallMoveCandidate(int1, int2, dir));
	    //System.out.println(Quoridor223Controller.hasWallMoveCandidate());
	}

	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @throws InvalidOperationException 
	 */
	@Then("The position shall be valid")
	public void the_position_shall_be_valid() throws InvalidOperationException {
	    assertEquals("valid", Quoridor223Controller.isPositionValid());
	}

	/**
	 * ValidatePosition.feature
	 * @author Sacha Lévy
	 * @throws InvalidOperationException 
	 */
	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() throws InvalidOperationException {
	    assertEquals("invalid", Quoridor223Controller.isPositionValid());
	}
// checkPath feature
	
	@Given("A {string} wall move candidate exists at position {int}:{int}")
	public void a_wall_move_candidate_exists_at_position(String string, Integer int1, Integer int2) {
	    Direction dir;
		if(string.equals("horizontal")) dir = Direction.Horizontal;
		else dir = Direction.Vertical;
		Quoridor223Controller.createNewWallMoveCandidate(int1, int2, dir);
	}

	@Given("The black player is located at {int}:{int}")
	public void the_black_player_is_located_at(Integer int1, Integer int2) {
		assertTrue(setPlayerPosition("black", int1, int2));
	}

	@Given("The white player is located at {int}:{int}")
	public void the_white_player_is_located_at(Integer int1, Integer int2) {
		assertTrue(setPlayerPosition("white", int1, int2));
	}

	@When("Check path existence is initiated")
	public void check_path_existence_is_initiated() throws UnsupportedOperationException, GameNotRunningException, InvalidOperationException {
		inner_message_check_path = "both";
		String inner_message = "";
		try{
			Quoridor223Controller.dropWall();
		}
		catch (InvalidOperationException e){
			inner_message = e.getMessage();
		}
		if(inner_message.equals("black is blocked")) inner_message_check_path="white";
		if(inner_message.equals("white is blocked")) inner_message_check_path="black";
		if(inner_message.equals("both are blocked")) inner_message_check_path="none";
	}

	@Then("Path is available for {string} player\\(s)")
	public void path_is_available_for_player_s(String string) throws InvalidOperationException {
		assertEquals(string, inner_message_check_path);
		//assertEquals(string, string);
	}
	
// ReportFinalResult
	@When("The game is no longer running")
	public void the_game_is_no_longer_running() throws GameNotRunningException {
		initQuoridorAndBoard();
		createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
		QuoridorApplication.CreateNewWhitePawnBehavior();
		QuoridorApplication.CreateNewBlackPawnBehavior();
		QuoridorApplication.GetWhitePawnBehavior();
		QuoridorApplication.GetBlackPawnBehavior();
		gamePage = new GamePage();
		
		Quoridor223Controller.resignGame("black");
	    assertFalse("the game is no longer running", Quoridor223Controller.isRunning());
	}

	@Then("The final result shall be displayed")
	public void the_final_result_shall_be_displayed() {
		boolean isDisplayed = false;
		// update with an actual check when the game has finished
		String final_text =  gamePage.getDialogBoxText();
		System.out.println(final_text);
		
		if(final_text!=null) isDisplayed = true;
	    assertTrue("final_result is displayed", isDisplayed);
	}

	@Then("White's clock shall not be counting down")
	public void white_s_clock_shall_not_be_counting_down() {
	    assertFalse("white clock is not running", gamePage.getWhiteClockStatus());
	}

	@Then("Black's clock shall not be counting down")
	public void black_s_clock_shall_not_be_counting_down() {
	    assertFalse("black clock is not running", gamePage.getBlackClockStatus());
	}

	@Then("White shall be unable to move")
	public void white_shall_be_unable_to_move() {
	    assertFalse("white player is unable to move", isWhitePlayerAbleToMove());
	}

	@Then("Black shall be unable to move")
	public void black_shall_be_unable_to_move() {
		assertFalse("black player is unable to move", isBlackPlayerAbleToMove());
	}
	
	private boolean isWhitePlayerAbleToMove() {
		// set the white player to be moving
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		game.getCurrentPosition().setPlayerToMove(game.getWhitePlayer());
		gamePage.clickMovePlayer("up");
		if(gamePage.getDialogBoxText().equals("Game not running")) return false;
		return true;
	}

	private boolean isBlackPlayerAbleToMove() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		game.getCurrentPosition().setPlayerToMove(game.getBlackPlayer());
		gamePage.clickMovePlayer("up");
		if(gamePage.getDialogBoxText().equals("Game not running")) return false;
		return true;
	}
// Switch Current Player feature
	/**
	 * @author Sacha Lévy
	 * @param player
	 * @return boolean
	 * @throws InterruptedException 
	 */
	private boolean isClockRunning(String color) throws InterruptedException {
		Thread.sleep(1200);
		if(color.equals("white")) {
			return gamePage.isWhiteClockRunning();
		}
		else {
			return !gamePage.isWhiteClockRunning();
		}
		
	
	}

	/**
	 * helper method to check if given player is next to move
	 * 
	 * @param other
	 * @author Sacha Lévy
	 * @return isPlayerNextToMove
	 * */
	private boolean isNextPlayerToMove(Player other) {
		if (other.equals(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()))
			return true;
		else
			return false;
	}
	
	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string 
	 * */
	@Given("The player to move is {string}")
	public void the_player_to_move_is(String string) {
		assertEquals(true, Quoridor223Controller.setCurrentPlayerToMoveByColor(string));
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
		try {
			Thread.sleep(3000);	// when setting long clock the feature still passes
			assertEquals(true, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) {
		try {
			assertEquals(false, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@When("Player {string} completes his move")
	public void player_completes_his_move(String string) {
		gamePage.clickGrabWall();
		gamePage.clickDropWall();
		assertEquals(false,Quoridor223Controller.hasWallMoveCandidate());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String string) {
		assertEquals("It is "+Quoridor223Controller.getPlayerNameByColor(string)+"'s Turn !!", gamePage.getDialogBoxText());
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) {
		try {
			assertEquals(false, isClockRunning(string));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string 
	 * */
	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
		try {
			assertEquals(true, isClockRunning(string));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SwitchCurrentPlayer.feature
	 * @author Sacha Lévy
	 * @param string
	 * */
	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
		Player player = Quoridor223Controller.getPlayerByColor(string);
		assertEquals(true, isNextPlayerToMove(player));
	}

}