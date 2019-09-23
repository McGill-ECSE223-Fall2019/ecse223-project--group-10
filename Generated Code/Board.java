/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 23 "firstdraft.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private Game game;
  private List<Grid> grids;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Game aGame, Grid... allGrids)
  {
    if (aGame == null || aGame.getCurBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
    grids = new ArrayList<Grid>();
    boolean didAddGrids = setGrids(allGrids);
    if (!didAddGrids)
    {
      throw new RuntimeException("Unable to create Board, must have 100 grids. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Board(String aPositionFileNameForGame, File aPositionFileForGame, Grid... allGrids)
  {
    game = new Game(aPositionFileNameForGame, aPositionFileForGame, this);
    grids = new ArrayList<Grid>();
    boolean didAddGrids = setGrids(allGrids);
    if (!didAddGrids)
    {
      throw new RuntimeException("Unable to create Board, must have 100 grids. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetMany */
  public Grid getGrid(int index)
  {
    Grid aGrid = grids.get(index);
    return aGrid;
  }

  public List<Grid> getGrids()
  {
    List<Grid> newGrids = Collections.unmodifiableList(grids);
    return newGrids;
  }

  public int numberOfGrids()
  {
    int number = grids.size();
    return number;
  }

  public boolean hasGrids()
  {
    boolean has = grids.size() > 0;
    return has;
  }

  public int indexOfGrid(Grid aGrid)
  {
    int index = grids.indexOf(aGrid);
    return index;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfGrids()
  {
    return 100;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGrids()
  {
    return 100;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGrids()
  {
    return 100;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setGrids(Grid... newGrids)
  {
    boolean wasSet = false;
    ArrayList<Grid> verifiedGrids = new ArrayList<Grid>();
    for (Grid aGrid : newGrids)
    {
      if (verifiedGrids.contains(aGrid))
      {
        continue;
      }
      verifiedGrids.add(aGrid);
    }

    if (verifiedGrids.size() != newGrids.length || verifiedGrids.size() != requiredNumberOfGrids())
    {
      return wasSet;
    }

    grids.clear();
    grids.addAll(verifiedGrids);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    grids.clear();
  }

}