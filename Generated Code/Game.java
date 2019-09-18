/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 1 "firstdraft.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Menu mainMenu;
  private Control mainControl;
  private Board curBoard;
  private Record gameRecord;
  private List<Player> player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Menu aMainMenu, Control aMainControl, Board aCurBoard, Record aGameRecord)
  {
    if (aMainMenu == null || aMainMenu.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aMainMenu. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    mainMenu = aMainMenu;
    if (aMainControl == null || aMainControl.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aMainControl. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    mainControl = aMainControl;
    if (aCurBoard == null || aCurBoard.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aCurBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    curBoard = aCurBoard;
    if (aGameRecord == null || aGameRecord.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aGameRecord. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameRecord = aGameRecord;
    player = new ArrayList<Player>();
  }

  public Game(Box... allBoxsForCurBoard)
  {
    mainMenu = new Menu(this);
    mainControl = new Control(this);
    curBoard = new Board(this, allBoxsForCurBoard);
    gameRecord = new Record(this);
    player = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Menu getMainMenu()
  {
    return mainMenu;
  }
  /* Code from template association_GetOne */
  public Control getMainControl()
  {
    return mainControl;
  }
  /* Code from template association_GetOne */
  public Board getCurBoard()
  {
    return curBoard;
  }
  /* Code from template association_GetOne */
  public Record getGameRecord()
  {
    return gameRecord;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = player.get(index);
    return aPlayer;
  }

  public List<Player> getPlayer()
  {
    List<Player> newPlayer = Collections.unmodifiableList(player);
    return newPlayer;
  }

  public int numberOfPlayer()
  {
    int number = player.size();
    return number;
  }

  public boolean hasPlayer()
  {
    boolean has = player.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = player.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayerValid()
  {
    boolean isValid = numberOfPlayer() >= minimumNumberOfPlayer() && numberOfPlayer() <= maximumNumberOfPlayer();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayer()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayer()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(boolean aIsHighLighted, Cordinate aCordinate, Destination aDestination, Color aColor)
  {
    if (numberOfPlayer() >= maximumNumberOfPlayer())
    {
      return null;
    }
    else
    {
      return new Player(aIsHighLighted, this, aCordinate, aDestination, aColor);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (player.contains(aPlayer)) { return false; }
    if (numberOfPlayer() >= maximumNumberOfPlayer())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayer() <= minimumNumberOfPlayer())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      player.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (this.equals(aPlayer.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (2)
    if (numberOfPlayer() <= minimumNumberOfPlayer())
    {
      return wasRemoved;
    }
    player.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayer()) { index = numberOfPlayer() - 1; }
      player.remove(aPlayer);
      player.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(player.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayer()) { index = numberOfPlayer() - 1; }
      player.remove(aPlayer);
      player.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Menu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.delete();
    }
    Control existingMainControl = mainControl;
    mainControl = null;
    if (existingMainControl != null)
    {
      existingMainControl.delete();
    }
    Board existingCurBoard = curBoard;
    curBoard = null;
    if (existingCurBoard != null)
    {
      existingCurBoard.delete();
    }
    Record existingGameRecord = gameRecord;
    gameRecord = null;
    if (existingGameRecord != null)
    {
      existingGameRecord.delete();
    }
    for(int i=player.size(); i > 0; i--)
    {
      Player aPlayer = player.get(i - 1);
      aPlayer.delete();
    }
  }

}