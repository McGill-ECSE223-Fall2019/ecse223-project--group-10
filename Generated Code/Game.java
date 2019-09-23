/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 1 "firstdraft.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Menu mainMenu;
  private Control mainControl;
  private Board curBoard;
  private Record gameRecord;
  private Canvas gameCanvas;
  private List<User> users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Menu aMainMenu, Control aMainControl, Board aCurBoard, Record aGameRecord, Canvas aGameCanvas)
  {
    if (aMainMenu == null || aMainMenu.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aMainMenu. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    mainMenu = aMainMenu;
    if (aMainControl == null || aMainControl.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aMainControl. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    mainControl = aMainControl;
    if (aCurBoard == null || aCurBoard.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aCurBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    curBoard = aCurBoard;
    if (aGameRecord == null || aGameRecord.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aGameRecord. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameRecord = aGameRecord;
    if (aGameCanvas == null || aGameCanvas.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aGameCanvas. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameCanvas = aGameCanvas;
    users = new ArrayList<User>();
  }

  public Game(String aPositionFileNameForCurBoard, File aPositionFileForCurBoard, Box... allBoxsForCurBoard)
  {
    mainMenu = new Menu(this);
    mainControl = new Control(this);
    curBoard = new Board(aPositionFileNameForCurBoard, aPositionFileForCurBoard, this, allBoxsForCurBoard);
    gameRecord = new Record(this);
    gameCanvas = new Canvas(this);
    users = new ArrayList<User>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Menu getMainMenu()
  {
    return mainMenu;
  }
  /* Code from template association_GetOne */
  public Control getMainControl()
  {
    return mainControl;
  }
  /* Code from template association_GetOne */
  public Board getCurBoard()
  {
    return curBoard;
  }
  /* Code from template association_GetOne */
  public Record getGameRecord()
  {
    return gameRecord;
  }
  /* Code from template association_GetOne */
  public Canvas getGameCanvas()
  {
    return gameCanvas;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUsers()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOne */
  public User addUser(String aUserName, int aUserScore, int aUserWin, int aUserLoss, int aUserTie, Player aPlayer)
  {
    if (numberOfUsers() >= maximumNumberOfUsers())
    {
      return null;
    }
    else
    {
      return new User(aUserName, aUserScore, aUserWin, aUserLoss, aUserTie, this, aPlayer);
    }
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    if (numberOfUsers() >= maximumNumberOfUsers())
    {
      return wasAdded;
    }

    Game existingGame = aUser.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aUser.setGame(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a game
    if (!this.equals(aUser.getGame()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Menu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.delete();
    }
    Control existingMainControl = mainControl;
    mainControl = null;
    if (existingMainControl != null)
    {
      existingMainControl.delete();
    }
    Board existingCurBoard = curBoard;
    curBoard = null;
    if (existingCurBoard != null)
    {
      existingCurBoard.delete();
    }
    Record existingGameRecord = gameRecord;
    gameRecord = null;
    if (existingGameRecord != null)
    {
      existingGameRecord.delete();
    }
    Canvas existingGameCanvas = gameCanvas;
    gameCanvas = null;
    if (existingGameCanvas != null)
    {
      existingGameCanvas.delete();
    }
    for(int i=users.size(); i > 0; i--)
    {
      User aUser = users.get(i - 1);
      aUser.delete();
    }
  }

}