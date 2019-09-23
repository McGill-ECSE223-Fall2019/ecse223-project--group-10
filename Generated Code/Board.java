/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 16 "firstdraft.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private String positionFileName;
  private File positionFile;

  //Board Associations
  private Game game;
  private List<Box> boxs;
  private List<Wall> walls;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(String aPositionFileName, File aPositionFile, Game aGame, Box... allBoxs)
  {
    positionFileName = aPositionFileName;
    positionFile = aPositionFile;
    if (aGame == null || aGame.getCurBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
    boxs = new ArrayList<Box>();
    boolean didAddBoxs = setBoxs(allBoxs);
    if (!didAddBoxs)
    {
      throw new RuntimeException("Unable to create Board, must have 100 boxs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    walls = new ArrayList<Wall>();
  }

  public Board(String aPositionFileName, File aPositionFile, Menu aMainMenuForGame, Control aMainControlForGame, Record aGameRecordForGame, Canvas aGameCanvasForGame, Box... allBoxs)
  {
    positionFileName = aPositionFileName;
    positionFile = aPositionFile;
    game = new Game(aMainMenuForGame, aMainControlForGame, this, aGameRecordForGame, aGameCanvasForGame);
    boxs = new ArrayList<Box>();
    boolean didAddBoxs = setBoxs(allBoxs);
    if (!didAddBoxs)
    {
      throw new RuntimeException("Unable to create Board, must have 100 boxs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    walls = new ArrayList<Wall>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPositionFileName(String aPositionFileName)
  {
    boolean wasSet = false;
    positionFileName = aPositionFileName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionFile(File aPositionFile)
  {
    boolean wasSet = false;
    positionFile = aPositionFile;
    wasSet = true;
    return wasSet;
  }

  public String getPositionFileName()
  {
    return positionFileName;
  }

  public File getPositionFile()
  {
    return positionFile;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public Box getBox(int index)
  {
    Box aBox = boxs.get(index);
    return aBox;
  }

  public List<Box> getBoxs()
  {
    List<Box> newBoxs = Collections.unmodifiableList(boxs);
    return newBoxs;
  }

  public int numberOfBoxs()
  {
    int number = boxs.size();
    return number;
  }

  public boolean hasBoxs()
  {
    boolean has = boxs.size() > 0;
    return has;
  }

  public int indexOfBox(Box aBox)
  {
    int index = boxs.indexOf(aBox);
    return index;
  }
  /* Code from template association_GetMany */
  public Wall getWall(int index)
  {
    Wall aWall = walls.get(index);
    return aWall;
  }

  public List<Wall> getWalls()
  {
    List<Wall> newWalls = Collections.unmodifiableList(walls);
    return newWalls;
  }

  public int numberOfWalls()
  {
    int number = walls.size();
    return number;
  }

  public boolean hasWalls()
  {
    boolean has = walls.size() > 0;
    return has;
  }

  public int indexOfWall(Wall aWall)
  {
    int index = walls.indexOf(aWall);
    return index;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfBoxs()
  {
    return 100;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoxs()
  {
    return 100;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfBoxs()
  {
    return 100;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setBoxs(Box... newBoxs)
  {
    boolean wasSet = false;
    ArrayList<Box> verifiedBoxs = new ArrayList<Box>();
    for (Box aBox : newBoxs)
    {
      if (verifiedBoxs.contains(aBox))
      {
        continue;
      }
      verifiedBoxs.add(aBox);
    }

    if (verifiedBoxs.size() != newBoxs.length || verifiedBoxs.size() != requiredNumberOfBoxs())
    {
      return wasSet;
    }

    boxs.clear();
    boxs.addAll(verifiedBoxs);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 20;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    if (numberOfWalls() < maximumNumberOfWalls())
    {
      walls.add(aWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeWall(Wall aWall)
  {
    boolean wasRemoved = false;
    if (walls.contains(aWall))
    {
      walls.remove(aWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setWalls(Wall... newWalls)
  {
    boolean wasSet = false;
    ArrayList<Wall> verifiedWalls = new ArrayList<Wall>();
    for (Wall aWall : newWalls)
    {
      if (verifiedWalls.contains(aWall))
      {
        continue;
      }
      verifiedWalls.add(aWall);
    }

    if (verifiedWalls.size() != newWalls.length || verifiedWalls.size() > maximumNumberOfWalls())
    {
      return wasSet;
    }

    walls.clear();
    walls.addAll(verifiedWalls);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallAt(Wall aWall, int index)
  {  
    boolean wasAdded = false;
    if(addWall(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallAt(Wall aWall, int index)
  {
    boolean wasAdded = false;
    if(walls.contains(aWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWalls()) { index = numberOfWalls() - 1; }
      walls.remove(aWall);
      walls.add(index, aWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallAt(aWall, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    boxs.clear();
    walls.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "positionFileName" + ":" + getPositionFileName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "positionFile" + "=" + (getPositionFile() != null ? !getPositionFile().equals(this)  ? getPositionFile().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}