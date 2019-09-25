/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 1 "Quoridor.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private boolean isOver;

  //Game Associations
  private Player winner;
  private List<Pawn> pawns;
  private List<Wall> walls;
  private List<Tile> gameTiles;
  private History gameHistory;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(boolean aIsOver, History aGameHistory, Quoridor aQuoridor)
  {
    isOver = aIsOver;
    pawns = new ArrayList<Pawn>();
    walls = new ArrayList<Wall>();
    gameTiles = new ArrayList<Tile>();
    if (aGameHistory == null || aGameHistory.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aGameHistory. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameHistory = aGameHistory;
    if (aQuoridor == null || aQuoridor.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aQuoridor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    quoridor = aQuoridor;
  }

  public Game(boolean aIsOver, Quoridor aQuoridorForGameHistory, History aHistoryForQuoridor)
  {
    isOver = aIsOver;
    pawns = new ArrayList<Pawn>();
    walls = new ArrayList<Wall>();
    gameTiles = new ArrayList<Tile>();
    gameHistory = new History(this, aQuoridorForGameHistory);
    quoridor = new Quoridor(this, aHistoryForQuoridor);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsOver(boolean aIsOver)
  {
    boolean wasSet = false;
    isOver = aIsOver;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsOver()
  {
    return isOver;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOver()
  {
    return isOver;
  }
  /* Code from template association_GetOne */
  public Player getWinner()
  {
    return winner;
  }

  public boolean hasWinner()
  {
    boolean has = winner != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Pawn getPawn(int index)
  {
    Pawn aPawn = pawns.get(index);
    return aPawn;
  }

  public List<Pawn> getPawns()
  {
    List<Pawn> newPawns = Collections.unmodifiableList(pawns);
    return newPawns;
  }

  public int numberOfPawns()
  {
    int number = pawns.size();
    return number;
  }

  public boolean hasPawns()
  {
    boolean has = pawns.size() > 0;
    return has;
  }

  public int indexOfPawn(Pawn aPawn)
  {
    int index = pawns.indexOf(aPawn);
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
  /* Code from template association_GetMany */
  public Tile getGameTile(int index)
  {
    Tile aGameTile = gameTiles.get(index);
    return aGameTile;
  }

  public List<Tile> getGameTiles()
  {
    List<Tile> newGameTiles = Collections.unmodifiableList(gameTiles);
    return newGameTiles;
  }

  public int numberOfGameTiles()
  {
    int number = gameTiles.size();
    return number;
  }

  public boolean hasGameTiles()
  {
    boolean has = gameTiles.size() > 0;
    return has;
  }

  public int indexOfGameTile(Tile aGameTile)
  {
    int index = gameTiles.indexOf(aGameTile);
    return index;
  }
  /* Code from template association_GetOne */
  public History getGameHistory()
  {
    return gameHistory;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWinner(Player aNewWinner)
  {
    boolean wasSet = false;
    winner = aNewWinner;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPawnsValid()
  {
    boolean isValid = numberOfPawns() >= minimumNumberOfPawns() && numberOfPawns() <= maximumNumberOfPawns();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPawns()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPawns()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Pawn addPawn(int aX, int aY)
  {
    if (numberOfPawns() >= maximumNumberOfPawns())
    {
      return null;
    }
    else
    {
      return new Pawn(aX, aY, this);
    }
  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    if (numberOfPawns() >= maximumNumberOfPawns())
    {
      return wasAdded;
    }

    Game existingGame = aPawn.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPawns() <= minimumNumberOfPawns())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPawn.setGame(this);
    }
    else
    {
      pawns.add(aPawn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePawn(Pawn aPawn)
  {
    boolean wasRemoved = false;
    //Unable to remove aPawn, as it must always have a game
    if (this.equals(aPawn.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (2)
    if (numberOfPawns() <= minimumNumberOfPawns())
    {
      return wasRemoved;
    }
    pawns.remove(aPawn);
    wasRemoved = true;
    return wasRemoved;
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
  public Wall addWall(int aX, int aY)
  {
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return null;
    }
    else
    {
      return new Wall(aX, aY, this);
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfGameTilesValid()
  {
    boolean isValid = numberOfGameTiles() >= minimumNumberOfGameTiles() && numberOfGameTiles() <= maximumNumberOfGameTiles();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Tile addGameTile(int aX, int aY, boolean aTop, boolean aBottom, boolean aLeft, boolean aRight)
  {
    if (numberOfGameTiles() >= maximumNumberOfGameTiles())
    {
      return null;
    }
    else
    {
      return new Tile(aX, aY, aTop, aBottom, aLeft, aRight, this);
    }
  }

  public boolean addGameTile(Tile aGameTile)
  {
    boolean wasAdded = false;
    if (gameTiles.contains(aGameTile)) { return false; }
    if (numberOfGameTiles() >= maximumNumberOfGameTiles())
    {
      return wasAdded;
    }

    Game existingGame = aGameTile.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfGameTiles() <= minimumNumberOfGameTiles())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aGameTile.setGame(this);
    }
    else
    {
      gameTiles.add(aGameTile);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameTile(Tile aGameTile)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameTile, as it must always have a game
    if (this.equals(aGameTile.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (81)
    if (numberOfGameTiles() <= minimumNumberOfGameTiles())
    {
      return wasRemoved;
    }
    gameTiles.remove(aGameTile);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    winner = null;
    while (pawns.size() > 0)
    {
      Pawn aPawn = pawns.get(pawns.size() - 1);
      aPawn.delete();
      pawns.remove(aPawn);
    }
    
    while (walls.size() > 0)
    {
      Wall aWall = walls.get(walls.size() - 1);
      aWall.delete();
      walls.remove(aWall);
    }
    
    while (gameTiles.size() > 0)
    {
      Tile aGameTile = gameTiles.get(gameTiles.size() - 1);
      aGameTile.delete();
      gameTiles.remove(aGameTile);
    }
    
    History existingGameHistory = gameHistory;
    gameHistory = null;
    if (existingGameHistory != null)
    {
      existingGameHistory.delete();
    }
    Quoridor existingQuoridor = quoridor;
    quoridor = null;
    if (existingQuoridor != null)
    {
      existingQuoridor.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isOver" + ":" + getIsOver()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "winner = "+(getWinner()!=null?Integer.toHexString(System.identityHashCode(getWinner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameHistory = "+(getGameHistory()!=null?Integer.toHexString(System.identityHashCode(getGameHistory())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}