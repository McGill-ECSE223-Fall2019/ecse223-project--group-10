/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


package quoridor;
// line 9 "firstdraft.ump"
public class Menu
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Menu Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Menu(Game aGame)
  {
    if (aGame == null || aGame.getMainMenu() != null)
    {
      throw new RuntimeException("Unable to create Menu due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
  }

  public Menu(Control aMainControlForGame, Board aCurBoardForGame, Record aGameRecordForGame)
  {
    game = new Game(this, aMainControlForGame, aCurBoardForGame, aGameRecordForGame);
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