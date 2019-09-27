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
  private Board board;
  private List<Move> moves;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(boolean aIsOver, Board aBoard, Pawn... allPawns)
  {
    isOver = aIsOver;
    pawns = new ArrayList<Pawn>();
    boolean didAddPawns = setPawns(allPawns);
    if (!didAddPawns)
    {
      throw new RuntimeException("Unable to create Game, must have 2 pawns. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    walls = new ArrayList<Wall>();
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create game due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    moves = new ArrayList<Move>();
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
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Move getMove(int index)
  {
    Move aMove = moves.get(index);
    return aMove;
  }

  public List<Move> getMoves()
  {
    List<Move> newMoves = Collections.unmodifiableList(moves);
    return newMoves;
  }

  public int numberOfMoves()
  {
    int number = moves.size();
    return number;
  }

  public boolean hasMoves()
  {
    boolean has = moves.size() > 0;
    return has;
  }

  public int indexOfMove(Move aMove)
  {
    int index = moves.indexOf(aMove);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }

  public boolean hasQuoridor()
  {
    boolean has = quoridor != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWinner(Player aNewWinner)
  {
    boolean wasSet = false;
    winner = aNewWinner;
    wasSet = true;
    return wasSet;
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
      else if (aPawn.getGame() != null && !this.equals(aPawn.getGame()))
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
      setGame(orphan, null);
    }
    pawns.clear();
    for (Pawn aPawn : newPawns)
    {
      setGame(aPawn, this);
      pawns.add(aPawn);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setGame(Pawn aPawn, Game aGame)
  {
    try
    {
      java.lang.reflect.Field mentorField = aPawn.getClass().getDeclaredField("game");
      mentorField.setAccessible(true);
      mentorField.set(aPawn, aGame);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aGame to aPawn", e);
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

    Game existingGame = aWall.getGame();
    if (existingGame == null)
    {
      aWall.setGame(this);
    }
    else if (!this.equals(existingGame))
    {
      existingGame.removeWall(aWall);
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
      aWall.setGame(null);
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setBoard(Board aNewBoard)
  {
    boolean wasSet = false;
    if (aNewBoard == null)
    {
      //Unable to setBoard to null, as game must always be associated to a board
      return wasSet;
    }
    
    Game existingGame = aNewBoard.getGame();
    if (existingGame != null && !equals(existingGame))
    {
      //Unable to setBoard, the current board already has a game, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Board anOldBoard = board;
    board = aNewBoard;
    board.setGame(this);

    if (anOldBoard != null)
    {
      anOldBoard.setGame(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addMove(Move aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    Game existingGame = aMove.getGame();
    if (existingGame == null)
    {
      aMove.setGame(this);
    }
    else if (!this.equals(existingGame))
    {
      existingGame.removeMove(aMove);
      addMove(aMove);
    }
    else
    {
      moves.add(aMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMove(Move aMove)
  {
    boolean wasRemoved = false;
    if (moves.contains(aMove))
    {
      moves.remove(aMove);
      aMove.setGame(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveAt(Move aMove, int index)
  {  
    boolean wasAdded = false;
    if(addMove(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMoveAt(Move aMove, int index)
  {
    boolean wasAdded = false;
    if(moves.contains(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMoveAt(aMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      existingQuoridor.removeGame(this);
    }
    if (aQuoridor != null)
    {
      aQuoridor.addGame(this);
    }
    wasSet = true;
    return wasSet;
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
    
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
    while (moves.size() > 0)
    {
      Move aMove = moves.get(moves.size() - 1);
      aMove.delete();
      moves.remove(aMove);
    }
    
    if (quoridor != null)
    {
      Quoridor placeholderQuoridor = quoridor;
      this.quoridor = null;
      placeholderQuoridor.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isOver" + ":" + getIsOver()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "winner = "+(getWinner()!=null?Integer.toHexString(System.identityHashCode(getWinner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}