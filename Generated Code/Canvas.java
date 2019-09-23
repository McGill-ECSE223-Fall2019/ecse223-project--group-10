/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 9 "firstdraft.ump"
public class Canvas
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Canvas Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Canvas(Game aGame)
  {
    if (aGame == null || aGame.getGameCanvas() != null)
    {
      throw new RuntimeException("Unable to create Canvas due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
  }

  public Canvas(Menu aMainMenuForGame, Control aMainControlForGame, Board aCurBoardForGame, Record aGameRecordForGame)
  {
    game = new Game(aMainMenuForGame, aMainControlForGame, aCurBoardForGame, aGameRecordForGame, this);
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