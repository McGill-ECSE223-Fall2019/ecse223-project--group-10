/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 23 "Quoridor.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private int x;
  private int y;
  private boolean top;
  private boolean bottom;
  private boolean left;
  private boolean right;

  //Tile Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(int aX, int aY, boolean aTop, boolean aBottom, boolean aLeft, boolean aRight, Game aGame)
  {
    x = aX;
    y = aY;
    top = aTop;
    bottom = aBottom;
    left = aLeft;
    right = aRight;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameTile due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setTop(boolean aTop)
  {
    boolean wasSet = false;
    top = aTop;
    wasSet = true;
    return wasSet;
  }

  public boolean setBottom(boolean aBottom)
  {
    boolean wasSet = false;
    bottom = aBottom;
    wasSet = true;
    return wasSet;
  }

  public boolean setLeft(boolean aLeft)
  {
    boolean wasSet = false;
    left = aLeft;
    wasSet = true;
    return wasSet;
  }

  public boolean setRight(boolean aRight)
  {
    boolean wasSet = false;
    right = aRight;
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

  public boolean getTop()
  {
    return top;
  }

  public boolean getBottom()
  {
    return bottom;
  }

  public boolean getLeft()
  {
    return left;
  }

  public boolean getRight()
  {
    return right;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isTop()
  {
    return top;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isBottom()
  {
    return bottom;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLeft()
  {
    return left;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isRight()
  {
    return right;
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
    //Must provide game to gameTile
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (81)
    if (aGame.numberOfGameTiles() >= Game.maximumNumberOfGameTiles())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeGameTile(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addGameTile(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeGameTile(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "top" + ":" + getTop()+ "," +
            "bottom" + ":" + getBottom()+ "," +
            "left" + ":" + getLeft()+ "," +
            "right" + ":" + getRight()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}