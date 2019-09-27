/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.sql.Time;
import java.util.*;

// line 33 "Quoridor.ump"
public class Pawn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pawn Attributes
  private Time remainingTime;
  private int wallStock;

  //Pawn Associations
  private Cordinate cordinate;
  private Board board;
  private Position whitePawnPosition;
  private List<Wall> walls;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Time aRemainingTime, int aWallStock, Cordinate aCordinate)
  {
    remainingTime = aRemainingTime;
    wallStock = aWallStock;
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Pawn due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    walls = new ArrayList<Wall>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRemainingTime(Time aRemainingTime)
  {
    boolean wasSet = false;
    remainingTime = aRemainingTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallStock(int aWallStock)
  {
    boolean wasSet = false;
    wallStock = aWallStock;
    wasSet = true;
    return wasSet;
  }

  public Time getRemainingTime()
  {
    return remainingTime;
  }

  public int getWallStock()
  {
    return wallStock;
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
  public Position getWhitePawnPosition()
  {
    return whitePawnPosition;
  }

  public boolean hasWhitePawnPosition()
  {
    boolean has = whitePawnPosition != null;
    return has;
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setWhitePawnPosition(Position aNewWhitePawnPosition)
  {
    boolean wasSet = false;
    if (aNewWhitePawnPosition == null)
    {
      Position existingWhitePawnPosition = whitePawnPosition;
      whitePawnPosition = null;
      
      if (existingWhitePawnPosition != null && existingWhitePawnPosition.getWhitePawn() != null)
      {
        existingWhitePawnPosition.setWhitePawn(null);
      }
      wasSet = true;
      return wasSet;
    }

    Position currentWhitePawnPosition = getWhitePawnPosition();
    if (currentWhitePawnPosition != null && !currentWhitePawnPosition.equals(aNewWhitePawnPosition))
    {
      currentWhitePawnPosition.setWhitePawn(null);
    }

    whitePawnPosition = aNewWhitePawnPosition;
    Pawn existingWhitePawn = aNewWhitePawnPosition.getWhitePawn();

    if (!equals(existingWhitePawn))
    {
      aNewWhitePawnPosition.setWhitePawn(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 10;
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

    Pawn existingWallOwner = aWall.getWallOwner();
    boolean isNewWallOwner = existingWallOwner != null && !this.equals(existingWallOwner);
    if (isNewWallOwner)
    {
      aWall.setWallOwner(this);
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
    //Unable to remove aWall, as it must always have a wallOwner
    if (!this.equals(aWall.getWallOwner()))
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
    cordinate = null;
    if (board != null)
    {
        Board placeholderBoard = board;
        this.board = null;
        placeholderBoard.delete();
    }
    if (whitePawnPosition != null)
    {
      whitePawnPosition.setWhitePawn(null);
    }
    for(int i=walls.size(); i > 0; i--)
    {
      Wall aWall = walls.get(i - 1);
      aWall.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "wallStock" + ":" + getWallStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "remainingTime" + "=" + (getRemainingTime() != null ? !getRemainingTime().equals(this)  ? getRemainingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "whitePawnPosition = "+(getWhitePawnPosition()!=null?Integer.toHexString(System.identityHashCode(getWhitePawnPosition())):"null");
  }
}