/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 38 "Quoridor.ump"
public class Wall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { Horizontal, Vertical }
  public enum State { Highlighted, NotHighlighted }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Associations
  private Pawn wallOwner;
  private Cordinate cordinate;
  private Board board;
  private Position whiteWallPosition;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Pawn aWallOwner, Cordinate aCordinate)
  {
    boolean didAddWallOwner = setWallOwner(aWallOwner);
    if (!didAddWallOwner)
    {
      throw new RuntimeException("Unable to create wall due to wallOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Wall due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Pawn getWallOwner()
  {
    return wallOwner;
  }
  /* Code from template association_GetOne */
  public Cordinate getCordinate()
  {
    return cordinate;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }

  public boolean hasBoard()
  {
    boolean has = board != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Position getWhiteWallPosition()
  {
    return whiteWallPosition;
  }

  public boolean hasWhiteWallPosition()
  {
    boolean has = whiteWallPosition != null;
    return has;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setWallOwner(Pawn aWallOwner)
  {
    boolean wasSet = false;
    //Must provide wallOwner to wall
    if (aWallOwner == null)
    {
      return wasSet;
    }

    //wallOwner already at maximum (10)
    if (aWallOwner.numberOfWalls() >= Pawn.maximumNumberOfWalls())
    {
      return wasSet;
    }
    
    Pawn existingWallOwner = wallOwner;
    wallOwner = aWallOwner;
    if (existingWallOwner != null && !existingWallOwner.equals(aWallOwner))
    {
      boolean didRemove = existingWallOwner.removeWall(this);
      if (!didRemove)
      {
        wallOwner = existingWallOwner;
        return wasSet;
      }
    }
    wallOwner.addWall(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCordinate(Cordinate aNewCordinate)
  {
    boolean wasSet = false;
    if (aNewCordinate != null)
    {
      cordinate = aNewCordinate;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard != null && aBoard.numberOfWalls() >= Board.maximumNumberOfWalls())
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removeWall(this);
    }
    if (aBoard != null)
    {
      aBoard.addWall(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setWhiteWallPosition(Position aWhiteWallPosition)
  {
    boolean wasSet = false;
    if (aWhiteWallPosition != null && aWhiteWallPosition.numberOfWhiteWalls() >= Position.maximumNumberOfWhiteWalls())
    {
      return wasSet;
    }

    Position existingWhiteWallPosition = whiteWallPosition;
    whiteWallPosition = aWhiteWallPosition;
    if (existingWhiteWallPosition != null && !existingWhiteWallPosition.equals(aWhiteWallPosition))
    {
      existingWhiteWallPosition.removeWhiteWall(this);
    }
    if (aWhiteWallPosition != null)
    {
      aWhiteWallPosition.addWhiteWall(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Pawn placeholderWallOwner = wallOwner;
    this.wallOwner = null;
    if(placeholderWallOwner != null)
    {
      placeholderWallOwner.removeWall(this);
    }
    cordinate = null;
    if (board != null)
    {
      Board placeholderBoard = board;
      this.board = null;
      placeholderBoard.removeWall(this);
    }
    if (whiteWallPosition != null)
    {
      Position placeholderWhiteWallPosition = whiteWallPosition;
      this.whiteWallPosition = null;
      placeholderWhiteWallPosition.removeWhiteWall(this);
    }
  }

}