/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 45 "Quoridor.ump"
public abstract class Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Associations
  private Pawn user;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move()
  {}

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Pawn getUser()
  {
    return user;
  }

  public boolean hasUser()
  {
    boolean has = user != null;
    return has;
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
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setUser(Pawn aNewUser)
  {
    boolean wasSet = false;
    user = aNewUser;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeMove(this);
    }
    if (aGame != null)
    {
      aGame.addMove(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    user = null;
    if (game != null)
    {
      Game placeholderGame = game;
      this.game = null;
      placeholderGame.removeMove(this);
    }
  }

}