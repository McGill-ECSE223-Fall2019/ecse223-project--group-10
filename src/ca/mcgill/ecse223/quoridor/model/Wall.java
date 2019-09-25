/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 27 "firstdraft.ump"
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

  //Wall Associations
  private Game game;
  private Cordinate cordinate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Game aGame, Cordinate aCordinate)
  {
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create wall due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Cordinate getCordinate()
  {
    return cordinate;
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

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeWall(this);
    }
    cordinate = null;
  }

}