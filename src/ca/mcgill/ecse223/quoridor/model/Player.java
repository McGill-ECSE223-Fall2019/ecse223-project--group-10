/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 14 "firstdraft.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum State { Highlighted, NotHighlighted }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Player> playersByUserName = new HashMap<String, Player>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String userName;
  private int userScore;
  private int userWin;
  private int userLoss;
  private int userTie;
  private int playerWallStock;

  //Player Associations
  private Game game;
  private Cordinate cordinate;
  private Destination destination;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aUserName, int aUserScore, int aUserWin, int aUserLoss, int aUserTie, int aPlayerWallStock, Game aGame, Cordinate aCordinate, Destination aDestination)
  {
    userScore = aUserScore;
    userWin = aUserWin;
    userLoss = aUserLoss;
    userTie = aUserTie;
    playerWallStock = aPlayerWallStock;
    if (!setUserName(aUserName))
    {
      throw new RuntimeException("Cannot create due to duplicate userName. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Player due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setDestination(aDestination))
    {
      throw new RuntimeException("Unable to create Player due to aDestination. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    String anOldUserName = getUserName();
    if (anOldUserName != null && anOldUserName.equals(aUserName)) {
      return true;
    }
    if (hasWithUserName(aUserName)) {
      return wasSet;
    }
    userName = aUserName;
    wasSet = true;
    if (anOldUserName != null) {
      playersByUserName.remove(anOldUserName);
    }
    playersByUserName.put(aUserName, this);
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

  public boolean setPlayerWallStock(int aPlayerWallStock)
  {
    boolean wasSet = false;
    playerWallStock = aPlayerWallStock;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }
  /* Code from template attribute_GetUnique */
  public static Player getWithUserName(String aUserName)
  {
    return playersByUserName.get(aUserName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUserName(String aUserName)
  {
    return getWithUserName(aUserName) != null;
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

  public int getPlayerWallStock()
  {
    return playerWallStock;
  }
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
  /* Code from template association_GetOne */
  public Destination getDestination()
  {
    return destination;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDestination(Destination aNewDestination)
  {
    boolean wasSet = false;
    if (aNewDestination != null)
    {
      destination = aNewDestination;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    playersByUserName.remove(getUserName());
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    cordinate = null;
    destination = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "userScore" + ":" + getUserScore()+ "," +
            "userWin" + ":" + getUserWin()+ "," +
            "userLoss" + ":" + getUserLoss()+ "," +
            "userTie" + ":" + getUserTie()+ "," +
            "playerWallStock" + ":" + getPlayerWallStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "destination = "+(getDestination()!=null?Integer.toHexString(System.identityHashCode(getDestination())):"null");
  }
}