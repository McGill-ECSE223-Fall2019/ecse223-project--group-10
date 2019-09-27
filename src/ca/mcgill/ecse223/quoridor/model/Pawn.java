/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.sql.Time;
import java.util.*;

// line 27 "Quoridor.ump"
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
  private Game game;
  private List<Wall> walls;
  private Position position;

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
  public Game getGame()
  {
    return game;
  }

  public boolean hasGame()
  {
    boolean has = game != null;
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
  /* Code from template association_GetOne */
  public Position getPosition()
  {
    return position;
  }

  public boolean hasPosition()
  {
    boolean has = position != null;
    return has;
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
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setPosition(Position aPosition)
  {
    boolean wasSet = false;
    if (aPosition != null && aPosition.numberOfPawns() >= Position.maximumNumberOfPawns())
    {
      return wasSet;
    }

    Position existingPosition = position;
    position = aPosition;
    if (existingPosition != null && !existingPosition.equals(aPosition))
    {
      existingPosition.removePawn(this);
    }
    if (aPosition != null)
    {
      aPosition.addPawn(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    cordinate = null;
    if (game != null)
    {
        Game placeholderGame = game;
        this.game = null;
        placeholderGame.delete();
    }
    for(int i=walls.size(); i > 0; i--)
    {
      Wall aWall = walls.get(i - 1);
      aWall.delete();
    }
    if (position != null)
    {
      Position placeholderPosition = position;
      this.position = null;
      placeholderPosition.removePawn(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "wallStock" + ":" + getWallStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "remainingTime" + "=" + (getRemainingTime() != null ? !getRemainingTime().equals(this)  ? getRemainingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "position = "+(getPosition()!=null?Integer.toHexString(System.identityHashCode(getPosition())):"null");
  }
}