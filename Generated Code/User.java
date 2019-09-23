/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 61 "firstdraft.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String userName;
  private int userScore;
  private int userWin;
  private int userLoss;
  private int userTie;

  //User Associations
  private Game game;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUserName, int aUserScore, int aUserWin, int aUserLoss, int aUserTie, Game aGame, Player aPlayer)
  {
    userName = aUserName;
    userScore = aUserScore;
    userWin = aUserWin;
    userLoss = aUserLoss;
    userTie = aUserTie;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create user due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setPlayer(aPlayer))
    {
      throw new RuntimeException("Unable to create User due to aPlayer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserScore(int aUserScore)
  {
    boolean wasSet = false;
    userScore = aUserScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserWin(int aUserWin)
  {
    boolean wasSet = false;
    userWin = aUserWin;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserLoss(int aUserLoss)
  {
    boolean wasSet = false;
    userLoss = aUserLoss;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserTie(int aUserTie)
  {
    boolean wasSet = false;
    userTie = aUserTie;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }

  public int getUserScore()
  {
    return userScore;
  }

  public int getUserWin()
  {
    return userWin;
  }

  public int getUserLoss()
  {
    return userLoss;
  }

  public int getUserTie()
  {
    return userTie;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to user
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfUsers() >= Game.maximumNumberOfUsers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeUser(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addUser(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer != null)
    {
      player = aNewPlayer;
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
      placeholderGame.removeUser(this);
    }
    player = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "userScore" + ":" + getUserScore()+ "," +
            "userWin" + ":" + getUserWin()+ "," +
            "userLoss" + ":" + getUserLoss()+ "," +
            "userTie" + ":" + getUserTie()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}