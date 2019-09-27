/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 16 "Quoridor.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private boolean isOver;

  //Game Associations
  private Player winner;
  private Board gameBoard;
  private List<Move> moves;
  private Position boardPosition;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(boolean aIsOver, Board aGameBoard, Position aBoardPosition)
  {
    isOver = aIsOver;
    boolean didAddGameBoard = setGameBoard(aGameBoard);
    if (!didAddGameBoard)
    {
      throw new RuntimeException("Unable to create game due to gameBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    moves = new ArrayList<Move>();
    boolean didAddBoardPosition = setBoardPosition(aBoardPosition);
    if (!didAddBoardPosition)
    {
      throw new RuntimeException("Unable to create game due to boardPosition. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public Board getGameBoard()
  {
    return gameBoard;
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
  public Position getBoardPosition()
  {
    return boardPosition;
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setGameBoard(Board aNewGameBoard)
  {
    boolean wasSet = false;
    if (aNewGameBoard == null)
    {
      //Unable to setGameBoard to null, as game must always be associated to a gameBoard
      return wasSet;
    }
    
    Game existingGame = aNewGameBoard.getGame();
    if (existingGame != null && !equals(existingGame))
    {
      //Unable to setGameBoard, the current gameBoard already has a game, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Board anOldGameBoard = gameBoard;
    gameBoard = aNewGameBoard;
    gameBoard.setGame(this);

    if (anOldGameBoard != null)
    {
      anOldGameBoard.setGame(null);
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setBoardPosition(Position aNewBoardPosition)
  {
    boolean wasSet = false;
    if (aNewBoardPosition == null)
    {
      //Unable to setBoardPosition to null, as game must always be associated to a boardPosition
      return wasSet;
    }
    
    Game existingGame = aNewBoardPosition.getGame();
    if (existingGame != null && !equals(existingGame))
    {
      //Unable to setBoardPosition, the current boardPosition already has a game, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Position anOldBoardPosition = boardPosition;
    boardPosition = aNewBoardPosition;
    boardPosition.setGame(this);

    if (anOldBoardPosition != null)
    {
      anOldBoardPosition.setGame(null);
    }
    wasSet = true;
    return wasSet;
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
    Board existingGameBoard = gameBoard;
    gameBoard = null;
    if (existingGameBoard != null)
    {
      existingGameBoard.delete();
    }
    while (moves.size() > 0)
    {
      Move aMove = moves.get(moves.size() - 1);
      aMove.delete();
      moves.remove(aMove);
    }
    
    Position existingBoardPosition = boardPosition;
    boardPosition = null;
    if (existingBoardPosition != null)
    {
      existingBoardPosition.delete();
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
            "  " + "gameBoard = "+(getGameBoard()!=null?Integer.toHexString(System.identityHashCode(getGameBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "boardPosition = "+(getBoardPosition()!=null?Integer.toHexString(System.identityHashCode(getBoardPosition())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}