/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 1 "firstdraft.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private String positionFileName;
  private File positionFile;

  //Game Associations
  private Board curBoard;
  private List<Player> players;
  private List<Wall> walls;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(String aPositionFileName, File aPositionFile, Board aCurBoard)
  {
    positionFileName = aPositionFileName;
    positionFile = aPositionFile;
    if (aCurBoard == null || aCurBoard.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aCurBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    curBoard = aCurBoard;
    players = new ArrayList<Player>();
    walls = new ArrayList<Wall>();
  }

  public Game(String aPositionFileName, File aPositionFile, Grid... allGridsForCurBoard)
  {
    positionFileName = aPositionFileName;
    positionFile = aPositionFile;
    curBoard = new Board(this, allGridsForCurBoard);
    players = new ArrayList<Player>();
    walls = new ArrayList<Wall>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPositionFileName(String aPositionFileName)
  {
    boolean wasSet = false;
    positionFileName = aPositionFileName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionFile(File aPositionFile)
  {
    boolean wasSet = false;
    positionFile = aPositionFile;
    wasSet = true;
    return wasSet;
  }

  public String getPositionFileName()
  {
    return positionFileName;
  }

  public File getPositionFile()
  {
    return positionFile;
  }
  /* Code from template association_GetOne */
  public Board getCurBoard()
  {
    return curBoard;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Wall getWall(int index)
  {
    Wall aWall = walls.get(index);
    return aWall;
  }

  public List<Wall> getWalls()
  {
    List<Wall> newWalls = Collections.unmodifiableList(walls);
    return newWalls;
  }

  public int numberOfWalls()
  {
    int number = walls.size();
    return number;
  }

  public boolean hasWalls()
  {
    boolean has = walls.size() > 0;
    return has;
  }

  public int indexOfWall(Wall aWall)
  {
    int index = walls.indexOf(aWall);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(String aUserName, int aUserScore, int aUserWin, int aUserLoss, int aUserTie, int aPlayerWallStock, Cordinate aCordinate, Destination aDestination)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aUserName, aUserScore, aUserWin, aUserLoss, aUserTie, aPlayerWallStock, this, aCordinate, aDestination);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
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
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
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
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 20;
  }
  /* Code from template association_AddOptionalNToOne */
  public Wall addWall(Cordinate aCordinate)
  {
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return null;
    }
    else
    {
      return new Wall(this, aCordinate);
    }
  }

  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return wasAdded;
    }

    Game existingGame = aWall.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aWall.setGame(this);
    }
    else
    {
      walls.add(aWall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWall(Wall aWall)
  {
    boolean wasRemoved = false;
    //Unable to remove aWall, as it must always have a game
    if (!this.equals(aWall.getGame()))
    {
      walls.remove(aWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallAt(Wall aWall, int index)
  {  
    boolean wasAdded = false;
    if(addWall(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallAt(Wall aWall, int index)
  {
    boolean wasAdded = false;
    if(walls.contains(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallAt(aWall, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Board existingCurBoard = curBoard;
    curBoard = null;
    if (existingCurBoard != null)
    {
      existingCurBoard.delete();
    }
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (walls.size() > 0)
    {
      Wall aWall = walls.get(walls.size() - 1);
      aWall.delete();
      walls.remove(aWall);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "positionFileName" + ":" + getPositionFileName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "positionFile" + "=" + (getPositionFile() != null ? !getPositionFile().equals(this)  ? getPositionFile().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "curBoard = "+(getCurBoard()!=null?Integer.toHexString(System.identityHashCode(getCurBoard())):"null");
  }
}