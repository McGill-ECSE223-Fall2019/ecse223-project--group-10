/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 32 "Quoridor.ump"
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

  //Wall Attributes
  private int x;
  private int y;

  //Wall Associations
  private Pawn wallOwner;
  private Game game;
  private Position position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(int aX, int aY, Pawn aWallOwner)
  {
    x = aX;
    y = aY;
    boolean didAddWallOwner = setWallOwner(aWallOwner);
    if (!didAddWallOwner)
    {
      throw new RuntimeException("Unable to create wall due to wallOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }
  /* Code from template association_GetOne */
  public Pawn getWallOwner()
  {
    return wallOwner;
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
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame != null && aGame.numberOfWalls() >= Game.maximumNumberOfWalls())
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeWall(this);
    }
    if (aGame != null)
    {
      aGame.addWall(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setPosition(Position aPosition)
  {
    boolean wasSet = false;
    if (aPosition != null && aPosition.numberOfWalls() >= Position.maximumNumberOfWalls())
    {
      return wasSet;
    }

    Position existingPosition = position;
    position = aPosition;
    if (existingPosition != null && !existingPosition.equals(aPosition))
    {
      existingPosition.removeWall(this);
    }
    if (aPosition != null)
    {
      aPosition.addWall(this);
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
    if (game != null)
    {
      Game placeholderGame = game;
      this.game = null;
      placeholderGame.removeWall(this);
    }
    if (position != null)
    {
      Position placeholderPosition = position;
      this.position = null;
      placeholderPosition.removeWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wallOwner = "+(getWallOwner()!=null?Integer.toHexString(System.identityHashCode(getWallOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "position = "+(getPosition()!=null?Integer.toHexString(System.identityHashCode(getPosition())):"null");
  }
}