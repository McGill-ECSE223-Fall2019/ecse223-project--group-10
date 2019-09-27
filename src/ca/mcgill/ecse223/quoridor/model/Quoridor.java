/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 1 "Quoridor.ump"
public class Quoridor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quoridor Associations
  private List<Game> games;
  private PlayerList playerList;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Quoridor(PlayerList aPlayerList)
  {
    games = new ArrayList<Game>();
    boolean didAddPlayerList = setPlayerList(aPlayerList);
    if (!didAddPlayerList)
    {
      throw new RuntimeException("Unable to create quoridor due to playerList. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetOne */
  public PlayerList getPlayerList()
  {
    return playerList;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Quoridor existingQuoridor = aGame.getQuoridor();
    if (existingQuoridor == null)
    {
      aGame.setQuoridor(this);
    }
    else if (!this.equals(existingQuoridor))
    {
      existingQuoridor.removeGame(aGame);
      addGame(aGame);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (games.contains(aGame))
    {
      games.remove(aGame);
      aGame.setQuoridor(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setPlayerList(PlayerList aNewPlayerList)
  {
    boolean wasSet = false;
    if (aNewPlayerList == null)
    {
      //Unable to setPlayerList to null, as quoridor must always be associated to a playerList
      return wasSet;
    }
    
    Quoridor existingQuoridor = aNewPlayerList.getQuoridor();
    if (existingQuoridor != null && !equals(existingQuoridor))
    {
      //Unable to setPlayerList, the current playerList already has a quoridor, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    PlayerList anOldPlayerList = playerList;
    playerList = aNewPlayerList;
    playerList.setQuoridor(this);

    if (anOldPlayerList != null)
    {
      anOldPlayerList.setQuoridor(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (games.size() > 0)
    {
      Game aGame = games.get(games.size() - 1);
      aGame.delete();
      games.remove(aGame);
    }
    
    PlayerList existingPlayerList = playerList;
    playerList = null;
    if (existingPlayerList != null)
    {
      existingPlayerList.delete();
    }
  }

}