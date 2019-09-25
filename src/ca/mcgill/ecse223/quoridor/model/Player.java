/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 9 "Quoridor.ump"
public class Player
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Player> playersByUserName = new HashMap<String, Player>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String userName;
  private int playerScore;
  private int playerWin;
  private int playerLoss;
  private int playerTie;
  private int wallStock;

  //Player Associations
  private Pawn currentPawn;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aUserName, int aPlayerScore, int aPlayerWin, int aPlayerLoss, int aPlayerTie, int aWallStock, Quoridor aQuoridor)
  {
    playerScore = aPlayerScore;
    playerWin = aPlayerWin;
    playerLoss = aPlayerLoss;
    playerTie = aPlayerTie;
    wallStock = aWallStock;
    if (!setUserName(aUserName))
    {
      throw new RuntimeException("Cannot create due to duplicate userName. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create player due to quoridor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setPlayerScore(int aPlayerScore)
  {
    boolean wasSet = false;
    playerScore = aPlayerScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerWin(int aPlayerWin)
  {
    boolean wasSet = false;
    playerWin = aPlayerWin;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerLoss(int aPlayerLoss)
  {
    boolean wasSet = false;
    playerLoss = aPlayerLoss;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerTie(int aPlayerTie)
  {
    boolean wasSet = false;
    playerTie = aPlayerTie;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallStock(int aWallStock)
  {
    boolean wasSet = false;
    wallStock = aWallStock;
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

  public int getPlayerScore()
  {
    return playerScore;
  }

  public int getPlayerWin()
  {
    return playerWin;
  }

  public int getPlayerLoss()
  {
    return playerLoss;
  }

  public int getPlayerTie()
  {
    return playerTie;
  }

  public int getWallStock()
  {
    return wallStock;
  }
  /* Code from template association_GetOne */
  public Pawn getCurrentPawn()
  {
    return currentPawn;
  }

  public boolean hasCurrentPawn()
  {
    boolean has = currentPawn != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentPawn(Pawn aNewCurrentPawn)
  {
    boolean wasSet = false;
    currentPawn = aNewCurrentPawn;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    //Must provide quoridor to player
    if (aQuoridor == null)
    {
      return wasSet;
    }

    //quoridor already at maximum (2)
    if (aQuoridor.numberOfPlayers() >= Quoridor.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      boolean didRemove = existingQuoridor.removePlayer(this);
      if (!didRemove)
      {
        quoridor = existingQuoridor;
        return wasSet;
      }
    }
    quoridor.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    playersByUserName.remove(getUserName());
    currentPawn = null;
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "playerScore" + ":" + getPlayerScore()+ "," +
            "playerWin" + ":" + getPlayerWin()+ "," +
            "playerLoss" + ":" + getPlayerLoss()+ "," +
            "playerTie" + ":" + getPlayerTie()+ "," +
            "wallStock" + ":" + getWallStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentPawn = "+(getCurrentPawn()!=null?Integer.toHexString(System.identityHashCode(getCurrentPawn())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}