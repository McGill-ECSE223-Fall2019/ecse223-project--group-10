/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 17 "Quoridor.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Tile> gameTiles;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Tile... allGameTiles)
  {
    gameTiles = new ArrayList<Tile>();
    boolean didAddGameTiles = setGameTiles(allGameTiles);
    if (!didAddGameTiles)
    {
      throw new RuntimeException("Unable to create Board, must have 81 gameTiles. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Tile getGameTile(int index)
  {
    Tile aGameTile = gameTiles.get(index);
    return aGameTile;
  }

  public List<Tile> getGameTiles()
  {
    List<Tile> newGameTiles = Collections.unmodifiableList(gameTiles);
    return newGameTiles;
  }

  public int numberOfGameTiles()
  {
    int number = gameTiles.size();
    return number;
  }

  public boolean hasGameTiles()
  {
    boolean has = gameTiles.size() > 0;
    return has;
  }

  public int indexOfGameTile(Tile aGameTile)
  {
    int index = gameTiles.indexOf(aGameTile);
    return index;
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
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGameTiles()
  {
    return 81;
  }
  /* Code from template association_SetNToOptionalOne */
  public boolean setGameTiles(Tile... newGameTiles)
  {
    boolean wasSet = false;
    ArrayList<Tile> checkNewGameTiles = new ArrayList<Tile>();
    for (Tile aGameTile : newGameTiles)
    {
      if (checkNewGameTiles.contains(aGameTile))
      {
        return wasSet;
      }
      else if (aGameTile.getBoard() != null && !this.equals(aGameTile.getBoard()))
      {
        return wasSet;
      }
      checkNewGameTiles.add(aGameTile);
    }

    if (checkNewGameTiles.size() != minimumNumberOfGameTiles())
    {
      return wasSet;
    }

    gameTiles.removeAll(checkNewGameTiles);
    
    for (Tile orphan : gameTiles)
    {
      setBoard(orphan, null);
    }
    gameTiles.clear();
    for (Tile aGameTile : newGameTiles)
    {
      setBoard(aGameTile, this);
      gameTiles.add(aGameTile);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setBoard(Tile aGameTile, Board aBoard)
  {
    try
    {
      java.lang.reflect.Field mentorField = aGameTile.getClass().getDeclaredField("board");
      mentorField.setAccessible(true);
      mentorField.set(aGameTile, aBoard);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aBoard to aGameTile", e);
    }
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (game != null && !game.equals(aNewGame) && equals(game.getBoard()))
    {
      //Unable to setGame, as existing game would become an orphan
      return wasSet;
    }

    game = aNewGame;
    Board anOldBoard = aNewGame != null ? aNewGame.getBoard() : null;

    if (!this.equals(anOldBoard))
    {
      if (anOldBoard != null)
      {
        anOldBoard.game = null;
      }
      if (game != null)
      {
        game.setBoard(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (gameTiles.size() > 0)
    {
      Tile aGameTile = gameTiles.get(gameTiles.size() - 1);
      aGameTile.delete();
      gameTiles.remove(aGameTile);
    }
    
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}