/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 18 "Quoridor.ump"
public class Quoridor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quoridor Associations
  private Game game;
  private History history;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Quoridor(Game aGame, History aHistory)
  {
    if (aGame == null || aGame.getQuoridor() != null)
    {
      throw new RuntimeException("Unable to create Quoridor due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
    if (aHistory == null || aHistory.getQuoridor() != null)
    {
      throw new RuntimeException("Unable to create Quoridor due to aHistory. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    history = aHistory;
    players = new ArrayList<Player>();
  }

  public Quoridor(boolean aIsOverForGame, History aGameHistoryForGame, Game aGameForHistory)
  {
    game = new Game(aIsOverForGame, aGameHistoryForGame, this);
    history = new History(aGameForHistory, this);
    players = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public History getHistory()
  {
    return history;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(String aUserName, int aPlayerScore, int aPlayerWin, int aPlayerLoss, int aPlayerTie, int aWallStock)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aUserName, aPlayerScore, aPlayerWin, aPlayerLoss, aPlayerTie, aWallStock, this);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Quoridor existingQuoridor = aPlayer.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);

    if (isNewQuoridor && existingQuoridor.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewQuoridor)
    {
      aPlayer.setQuoridor(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a quoridor
    if (this.equals(aPlayer.getQuoridor()))
    {
      return wasRemoved;
    }

    //quoridor already at minimum (2)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    History existingHistory = history;
    history = null;
    if (existingHistory != null)
    {
      existingHistory.delete();
    }
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
  }

}