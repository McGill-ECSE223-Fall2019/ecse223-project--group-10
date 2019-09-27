/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 5 "Quoridor.ump"
public class PlayerList
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerList Associations
  private List<Player> gamePlayers;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerList()
  {
    gamePlayers = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Player getGamePlayer(int index)
  {
    Player aGamePlayer = gamePlayers.get(index);
    return aGamePlayer;
  }

  public List<Player> getGamePlayers()
  {
    List<Player> newGamePlayers = Collections.unmodifiableList(gamePlayers);
    return newGamePlayers;
  }

  public int numberOfGamePlayers()
  {
    int number = gamePlayers.size();
    return number;
  }

  public boolean hasGamePlayers()
  {
    boolean has = gamePlayers.size() > 0;
    return has;
  }

  public int indexOfGamePlayer(Player aGamePlayer)
  {
    int index = gamePlayers.indexOf(aGamePlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }

  public boolean hasQuoridor()
  {
    boolean has = quoridor != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGamePlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addGamePlayer(Player aGamePlayer)
  {
    boolean wasAdded = false;
    if (gamePlayers.contains(aGamePlayer)) { return false; }
    PlayerList existingPlayerList = aGamePlayer.getPlayerList();
    if (existingPlayerList == null)
    {
      aGamePlayer.setPlayerList(this);
    }
    else if (!this.equals(existingPlayerList))
    {
      existingPlayerList.removeGamePlayer(aGamePlayer);
      addGamePlayer(aGamePlayer);
    }
    else
    {
      gamePlayers.add(aGamePlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGamePlayer(Player aGamePlayer)
  {
    boolean wasRemoved = false;
    if (gamePlayers.contains(aGamePlayer))
    {
      gamePlayers.remove(aGamePlayer);
      aGamePlayer.setPlayerList(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGamePlayerAt(Player aGamePlayer, int index)
  {  
    boolean wasAdded = false;
    if(addGamePlayer(aGamePlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGamePlayers()) { index = numberOfGamePlayers() - 1; }
      gamePlayers.remove(aGamePlayer);
      gamePlayers.add(index, aGamePlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGamePlayerAt(Player aGamePlayer, int index)
  {
    boolean wasAdded = false;
    if(gamePlayers.contains(aGamePlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGamePlayers()) { index = numberOfGamePlayers() - 1; }
      gamePlayers.remove(aGamePlayer);
      gamePlayers.add(index, aGamePlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGamePlayerAt(aGamePlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setQuoridor(Quoridor aNewQuoridor)
  {
    boolean wasSet = false;
    if (quoridor != null && !quoridor.equals(aNewQuoridor) && equals(quoridor.getPlayerList()))
    {
      //Unable to setQuoridor, as existing quoridor would become an orphan
      return wasSet;
    }

    quoridor = aNewQuoridor;
    PlayerList anOldPlayerList = aNewQuoridor != null ? aNewQuoridor.getPlayerList() : null;

    if (!this.equals(anOldPlayerList))
    {
      if (anOldPlayerList != null)
      {
        anOldPlayerList.quoridor = null;
      }
      if (quoridor != null)
      {
        quoridor.setPlayerList(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (gamePlayers.size() > 0)
    {
      Player aGamePlayer = gamePlayers.get(gamePlayers.size() - 1);
      aGamePlayer.delete();
      gamePlayers.remove(aGamePlayer);
    }
    
    Quoridor existingQuoridor = quoridor;
    quoridor = null;
    if (existingQuoridor != null)
    {
      existingQuoridor.delete();
    }
  }

}