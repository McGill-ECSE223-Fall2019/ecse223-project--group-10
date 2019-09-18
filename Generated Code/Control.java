/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 11 "firstdraft.ump"
public class Control
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Control Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Control(Game aGame)
  {
    if (aGame == null || aGame.getMainControl() != null)
    {
      throw new RuntimeException("Unable to create Control due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
  }

  public Control(Menu aMainMenuForGame, Board aCurBoardForGame, Record aGameRecordForGame)
  {
    game = new Game(aMainMenuForGame, this, aCurBoardForGame, aGameRecordForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}