package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication.*;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.But;

public class CucumberStepDefinitions {

	private Quoridor quoridor;
	private Board board;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Game game;

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
		theGameIsNotRunning();
		createAndStartGame();
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		currentPlayer = player1;
		Quoridor223Application.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { player1, player2 };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);

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
			new WallMove(0, 1, players[playerIdx], board.getTile((wrow - 1) * 9 + wcol - 1), game, direction, wall);
			if (playerIdx == 0) {
				game.getCurrentPosition().removeWhiteWallsInStock(wall);
				game.getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				game.getCurrentPosition().removeBlackWallsInStock(wall);
				game.getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// Walls are in stock for all players
		
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
	
	
	@And ("I have a wall in my hand over the board")
	public void iHaveAWallInMyHandOverTheBoard() {
		throw new PendingException(); //GUI STEP
	}
	@Given("A wall move candidate exists with (.*) at position ((.*), (.*))")
	public void wallMoveCandidateExists(String dir, int row, int col) {
		getWallMoveCandidate(dir,row,col);
	}
	@And("The wall candidate is at the (.*) edge of the board")
	public void Thewallcandidateisattheedgeoftheboard(String side) {
		throw new PendingException();
	}
	@And("The wall candidate is not at the (.*) edge of the board")
	public void Thewallcandidateisnotattheedgeoftheboard(String side) {
		throw new PendingException();
	}
	@When("I try to move the wall (.*)")
	public void iTryToMoveTheWall(String side) {
		Quoridor223Controller.moveWall(side);
	}
	
    @Then("The wall shall be moved over the board to position ((.*), (.*))")
    public void theWallShallBeMovedOverTheBoardToPosition(int row, int col) {
    	throw new PendingException();
    }
    @And("A wall move candidate shall exist with (.*) at position ((.*), (.*))")
    public void aWallMoveCandidateShallExist(String dir, int row, int col) {
    	throw new PendingException();
    }
    @Then("I should be notified that my move is illegal")
    public void IShouldBeNotifiedThatMyMoveIsIllegal() {
    	//Feel like this is GUI
    	throw new PendingException();
    }
    @Given ("The wall move candidate with (.*) at position ((.*), (.*)) is valid")
    public void theWallMoveCandidateIsValid(String dir, int row, int col) {
    	setWall(dir, row, col);
    }
    @Given("The wall move candidate with (.*) at position ((.*), (.*)) is invalid")
    public void TheWallMoveCandidateWithIsInvalid(String dir, int row, int col) {
    	setWall(dir, row, col);
    }
    @When("I release the wall in my hand")
    public void iReleaseTheWallInMyHand() {
    	Quoridor223Controller.dropWall();
    }
    @But("A wall move is registered with (.*) at position ((.*), (.*))")
    public void aWallMoveIsRegistered() {
    	throw new PendingException();
    }
    @And("My move is completed")
    public void myMoveIsCompleted() {
    	throw new PendingException();
    }
    @And ("It is not my turn to move")
    public void itIsNotMyTurnToMove() {
    	throw new PendingException();
    }
    @Then ("I shall be notified that my wall move is invalid")
    public void iShallBeNotifiedThatMyWallMoveIsInvalid() {
    	//Feel like this is GUI
    }
    @But("No wall move is registered with (.*) at position ((.*), (.*))")
    public void noWallMoveIsRegistered(String direction, int row, int col){
    	
    }
    // ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		quoridor.delete();
		quoridor = null;
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		quoridor = Quoridor223Application.getQuoridor();
		board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private void createUsersAndPlayers(String userName1, String userName2) {
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		//@formatter:off
		/*
		 *  __________
		 * |          |
		 * |          |
		 * |x->    <-x|
		 * |          |
		 * |__________|
		 * 
		 */
		//@formatter:on
		player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
	}

	private void createAndStartGame() {
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = board.getTile(36);
		Tile player2StartPos = board.getTile(44);

		PlayerPosition player1Position = new PlayerPosition(player1, player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(player2, player2StartPos);

		game = new Game(GameStatus.Running, MoveMode.PlayerMove, player1, player2, quoridor);
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

	
//	Grab Wall Feature
	//Scenario: Start wall placement
	@Given("I have more walls on stock")
	public void iHaveMoreWallsOnStock() {
		//query methods and modifier methods get and set methods. 
		int walls = player1.getWalls().size();
		assert walls > 0;
	}
	
	@When("I try to grab a wall from my stock")
	public void iTryToGrabAWallFromMyStock() {
		Quoridor223Controller.grabWall(game);
	}
	
//	@Then("I have a wall in my hand over the board")
//	public void iHaveAWallInMyHandOverTheBoard2() { //Duplicate method
//		//GUI STEP
//	}
	
	@And("The wall in my hand should disappear from my stock")
	public void theWallInMyHandShouldDisappearFromMyStock() {
		//And is an extension #THEN2.0
	}
	
	@And("A wall move candidate shall be created at the initial position")
	public void aWallMoveCandidateShallBeCraetedAtInitialPosition() {
		//THEN3.0
	}
	
	//Scenario: No more walls in stock
	@Given("I have no more walls on stock")
	public void iHaveNoMoreWallsOnStock() {
		int walls = player1.getWalls().size();
		assert walls == 0;
	}
	
//	@When("I try to grab a wall from my stock")
//	public void iTryToGrabAWallFromMyStock2() { //Duplicate method
//		//
//	}
	
	@Then("I should be notified that I have no more walls")
	public void iShouldBeNotifiedThatIHaveNoMoreWalls() {
		//
	}
	
//	Rotate Wall Feature
	//Scenario: Flip wall from horizontal to vertical or vice versa
//	@Given("A wall move candidate exists with (.*) at position ((.*), (.*))")
//	public void aWallMoveCandidateExistswithDirAtPosition() {
//		//
//	}
	
	@When("I try to flip the wall")
	public void iTryToFlipTheWall(){
		Quoridor223Controller.rotateWall();
	}
	
	@Then("The wall shall be rotated over the board to (.*)")
	public void theWallShallBeRotatedOverTheBoardToNewdir() {
		//GUI STEP
	}
	
//	@And("A wall move candidate shall exist with (.*) at position ((.*), (.*))")
//	public void aWallMoveCandidateShallExistWithNewdirAtPosition() {
//		//
//	}


	/**
	 * Create select the wall and set it to the wall candidate in the game
	 * @param dir
	 * @param row
	 * @param col
	 */
	private void getWallMoveCandidate(String dir, int row, int col) {
		//create a new WallMove Candidate and place the corresponding tile
		Direction wallDirection = dir.equals("horizontal")?Direction.Horizontal:Direction.Vertical;
		game.setWallMoveCandidate(new WallMove(0, 1, player1, board.getTile((row-1)*9+(col-1)), game, wallDirection, game.getCurrentPosition().getBlackWallsInStock(0)));
	}
	private void setWall(String dir, int row, int col) {
		//25 is a valid position of 3 row and 8th column
		if(game.getWallMoveCandidate()!=null) {
		game.getWallMoveCandidate().setTargetTile(board.getTile((row-1)*9+(col-1)));
		}
		else {
			getWallMoveCandidate(dir, row, col);
		}
	}
}
