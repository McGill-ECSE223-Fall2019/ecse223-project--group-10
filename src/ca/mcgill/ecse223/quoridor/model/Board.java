/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 23 "Quoridor.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Pawn> pawns;
  private List<Wall> walls;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Pawn... allPawns)
  {
    pawns = new ArrayList<Pawn>();
    boolean didAddPawns = setPawns(allPawns);
    if (!didAddPawns)
    {
      throw new RuntimeException("Unable to create Board, must have 2 pawns. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    walls = new ArrayList<Wall>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }

  public boolean hasGame()
  {
    boolean has = game != null;
    return has;
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
  /* Code from template association_SetNToOptionalOne */
  public boolean setPawns(Pawn... newPawns)
  {
    boolean wasSet = false;
    ArrayList<Pawn> checkNewPawns = new ArrayList<Pawn>();
    for (Pawn aPawn : newPawns)
    {
      if (checkNewPawns.contains(aPawn))
      {
        return wasSet;
      }
      else if (aPawn.getBoard() != null && !this.equals(aPawn.getBoard()))
      {
        return wasSet;
      }
      checkNewPawns.add(aPawn);
    }

    if (checkNewPawns.size() != minimumNumberOfPawns())
    {
      return wasSet;
    }

    pawns.removeAll(checkNewPawns);
    
    for (Pawn orphan : pawns)
    {
      setBoard(orphan, null);
    }
    pawns.clear();
    for (Pawn aPawn : newPawns)
    {
      setBoard(aPawn, this);
      pawns.add(aPawn);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setBoard(Pawn aPawn, Board aBoard)
  {
    try
    {
      java.lang.reflect.Field mentorField = aPawn.getClass().getDeclaredField("board");
      mentorField.setAccessible(true);
      mentorField.set(aPawn, aBoard);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aBoard to aPawn", e);
    }
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
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return wasAdded;
    }

    Board existingBoard = aWall.getBoard();
    if (existingBoard == null)
    {
      aWall.setBoard(this);
    }
    else if (!this.equals(existingBoard))
    {
      existingBoard.removeWall(aWall);
      addWall(aWall);
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
    if (walls.contains(aWall))
    {
      walls.remove(aWall);
      aWall.setBoard(null);
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (game != null && !game.equals(aNewGame) && equals(game.getGameBoard()))
    {
      //Unable to setGame, as existing game would become an orphan
      return wasSet;
    }

    game = aNewGame;
    Board anOldGameBoard = aNewGame != null ? aNewGame.getGameBoard() : null;

    if (!this.equals(anOldGameBoard))
    {
      if (anOldGameBoard != null)
      {
        anOldGameBoard.game = null;
      }
      if (game != null)
      {
        game.setGameBoard(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
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
    
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}