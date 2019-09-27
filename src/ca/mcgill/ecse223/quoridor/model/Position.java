/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 27 "Quoridor.ump"
public class Position
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Position Associations
  private Pawn blackPawn;
  private List<Wall> blackWalls;
  private Pawn whitePawn;
  private List<Wall> whiteWalls;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Position()
  {
    blackWalls = new ArrayList<Wall>();
    whiteWalls = new ArrayList<Wall>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Pawn getBlackPawn()
  {
    return blackPawn;
  }

  public boolean hasBlackPawn()
  {
    boolean has = blackPawn != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Wall getBlackWall(int index)
  {
    Wall aBlackWall = blackWalls.get(index);
    return aBlackWall;
  }

  public List<Wall> getBlackWalls()
  {
    List<Wall> newBlackWalls = Collections.unmodifiableList(blackWalls);
    return newBlackWalls;
  }

  public int numberOfBlackWalls()
  {
    int number = blackWalls.size();
    return number;
  }

  public boolean hasBlackWalls()
  {
    boolean has = blackWalls.size() > 0;
    return has;
  }

  public int indexOfBlackWall(Wall aBlackWall)
  {
    int index = blackWalls.indexOf(aBlackWall);
    return index;
  }
  /* Code from template association_GetOne */
  public Pawn getWhitePawn()
  {
    return whitePawn;
  }

  public boolean hasWhitePawn()
  {
    boolean has = whitePawn != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Wall getWhiteWall(int index)
  {
    Wall aWhiteWall = whiteWalls.get(index);
    return aWhiteWall;
  }

  public List<Wall> getWhiteWalls()
  {
    List<Wall> newWhiteWalls = Collections.unmodifiableList(whiteWalls);
    return newWhiteWalls;
  }

  public int numberOfWhiteWalls()
  {
    int number = whiteWalls.size();
    return number;
  }

  public boolean hasWhiteWalls()
  {
    boolean has = whiteWalls.size() > 0;
    return has;
  }

  public int indexOfWhiteWall(Wall aWhiteWall)
  {
    int index = whiteWalls.indexOf(aWhiteWall);
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
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBlackPawn(Pawn aNewBlackPawn)
  {
    boolean wasSet = false;
    blackPawn = aNewBlackPawn;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlackWalls()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfBlackWalls()
  {
    return 10;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addBlackWall(Wall aBlackWall)
  {
    boolean wasAdded = false;
    if (blackWalls.contains(aBlackWall)) { return false; }
    if (numberOfBlackWalls() < maximumNumberOfBlackWalls())
    {
      blackWalls.add(aBlackWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeBlackWall(Wall aBlackWall)
  {
    boolean wasRemoved = false;
    if (blackWalls.contains(aBlackWall))
    {
      blackWalls.remove(aBlackWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setBlackWalls(Wall... newBlackWalls)
  {
    boolean wasSet = false;
    ArrayList<Wall> verifiedBlackWalls = new ArrayList<Wall>();
    for (Wall aBlackWall : newBlackWalls)
    {
      if (verifiedBlackWalls.contains(aBlackWall))
      {
        continue;
      }
      verifiedBlackWalls.add(aBlackWall);
    }

    if (verifiedBlackWalls.size() != newBlackWalls.length || verifiedBlackWalls.size() > maximumNumberOfBlackWalls())
    {
      return wasSet;
    }

    blackWalls.clear();
    blackWalls.addAll(verifiedBlackWalls);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlackWallAt(Wall aBlackWall, int index)
  {  
    boolean wasAdded = false;
    if(addBlackWall(aBlackWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlackWalls()) { index = numberOfBlackWalls() - 1; }
      blackWalls.remove(aBlackWall);
      blackWalls.add(index, aBlackWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlackWallAt(Wall aBlackWall, int index)
  {
    boolean wasAdded = false;
    if(blackWalls.contains(aBlackWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlackWalls()) { index = numberOfBlackWalls() - 1; }
      blackWalls.remove(aBlackWall);
      blackWalls.add(index, aBlackWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlackWallAt(aBlackWall, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setWhitePawn(Pawn aNewWhitePawn)
  {
    boolean wasSet = false;
    if (aNewWhitePawn == null)
    {
      Pawn existingWhitePawn = whitePawn;
      whitePawn = null;
      
      if (existingWhitePawn != null && existingWhitePawn.getWhitePawnPosition() != null)
      {
        existingWhitePawn.setWhitePawnPosition(null);
      }
      wasSet = true;
      return wasSet;
    }

    Pawn currentWhitePawn = getWhitePawn();
    if (currentWhitePawn != null && !currentWhitePawn.equals(aNewWhitePawn))
    {
      currentWhitePawn.setWhitePawnPosition(null);
    }

    whitePawn = aNewWhitePawn;
    Position existingWhitePawnPosition = aNewWhitePawn.getWhitePawnPosition();

    if (!equals(existingWhitePawnPosition))
    {
      aNewWhitePawn.setWhitePawnPosition(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWhiteWalls()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWhiteWalls()
  {
    return 10;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addWhiteWall(Wall aWhiteWall)
  {
    boolean wasAdded = false;
    if (whiteWalls.contains(aWhiteWall)) { return false; }
    if (numberOfWhiteWalls() >= maximumNumberOfWhiteWalls())
    {
      return wasAdded;
    }

    Position existingWhiteWallPosition = aWhiteWall.getWhiteWallPosition();
    if (existingWhiteWallPosition == null)
    {
      aWhiteWall.setWhiteWallPosition(this);
    }
    else if (!this.equals(existingWhiteWallPosition))
    {
      existingWhiteWallPosition.removeWhiteWall(aWhiteWall);
      addWhiteWall(aWhiteWall);
    }
    else
    {
      whiteWalls.add(aWhiteWall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWhiteWall(Wall aWhiteWall)
  {
    boolean wasRemoved = false;
    if (whiteWalls.contains(aWhiteWall))
    {
      whiteWalls.remove(aWhiteWall);
      aWhiteWall.setWhiteWallPosition(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWhiteWallAt(Wall aWhiteWall, int index)
  {  
    boolean wasAdded = false;
    if(addWhiteWall(aWhiteWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWhiteWalls()) { index = numberOfWhiteWalls() - 1; }
      whiteWalls.remove(aWhiteWall);
      whiteWalls.add(index, aWhiteWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWhiteWallAt(Wall aWhiteWall, int index)
  {
    boolean wasAdded = false;
    if(whiteWalls.contains(aWhiteWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWhiteWalls()) { index = numberOfWhiteWalls() - 1; }
      whiteWalls.remove(aWhiteWall);
      whiteWalls.add(index, aWhiteWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWhiteWallAt(aWhiteWall, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (game != null && !game.equals(aNewGame) && equals(game.getBoardPosition()))
    {
      //Unable to setGame, as existing game would become an orphan
      return wasSet;
    }

    game = aNewGame;
    Position anOldBoardPosition = aNewGame != null ? aNewGame.getBoardPosition() : null;

    if (!this.equals(anOldBoardPosition))
    {
      if (anOldBoardPosition != null)
      {
        anOldBoardPosition.game = null;
      }
      if (game != null)
      {
        game.setBoardPosition(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    blackPawn = null;
    blackWalls.clear();
    Pawn existingWhitePawn = whitePawn;
    whitePawn = null;
    if (existingWhitePawn != null)
    {
      existingWhitePawn.delete();
      existingWhitePawn.setWhitePawnPosition(null);
    }
    while (whiteWalls.size() > 0)
    {
      Wall aWhiteWall = whiteWalls.get(whiteWalls.size() - 1);
      aWhiteWall.delete();
      whiteWalls.remove(aWhiteWall);
    }
    
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}