/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 35 "Quoridor.ump"
public class Wall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { Horizontal, Vertical }
  public enum State { Highlighted, NotHighlighted }
  public enum Owner { Black, White, Yellow, Red }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private int x;
  private int y;

  //Wall Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(int aX, int aY, Game aGame)
  {
    x = aX;
    y = aY;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create wall due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to wall
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (20)
    if (aGame.numberOfWalls() >= Game.maximumNumberOfWalls())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeWall(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addWall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}