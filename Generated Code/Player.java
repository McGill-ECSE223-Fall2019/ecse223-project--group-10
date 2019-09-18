/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 19 "firstdraft.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean isHighLighted;

  //Player Associations
  private Game game;
  private Cordinate cordinate;
  private Destination destination;
  private Color color;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aIsHighLighted, Game aGame, Cordinate aCordinate, Destination aDestination, Color aColor)
  {
    isHighLighted = aIsHighLighted;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Player due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setDestination(aDestination))
    {
      throw new RuntimeException("Unable to create Player due to aDestination. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setColor(aColor))
    {
      throw new RuntimeException("Unable to create Player due to aColor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsHighLighted(boolean aIsHighLighted)
  {
    boolean wasSet = false;
    isHighLighted = aIsHighLighted;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsHighLighted()
  {
    return isHighLighted;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsHighLighted()
  {
    return isHighLighted;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Cordinate getCordinate()
  {
    return cordinate;
  }
  /* Code from template association_GetOne */
  public Destination getDestination()
  {
    return destination;
  }
  /* Code from template association_GetOne */
  public Color getColor()
  {
    return color;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayer() >= Game.maximumNumberOfPlayer())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDestination(Destination aNewDestination)
  {
    boolean wasSet = false;
    if (aNewDestination != null)
    {
      destination = aNewDestination;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setColor(Color aNewColor)
  {
    boolean wasSet = false;
    if (aNewColor != null)
    {
      color = aNewColor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    cordinate = null;
    destination = null;
    color = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isHighLighted" + ":" + getIsHighLighted()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "destination = "+(getDestination()!=null?Integer.toHexString(System.identityHashCode(getDestination())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "color = "+(getColor()!=null?Integer.toHexString(System.identityHashCode(getColor())):"null");
  }
}