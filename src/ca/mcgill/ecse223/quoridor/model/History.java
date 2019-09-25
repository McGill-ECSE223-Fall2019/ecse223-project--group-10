/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 42 "Quoridor.ump"
public class History
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //History Associations
  private List<Action> actionHistory;
  private Game game;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public History(Game aGame, Quoridor aQuoridor)
  {
    actionHistory = new ArrayList<Action>();
    if (aGame == null || aGame.getGameHistory() != null)
    {
      throw new RuntimeException("Unable to create History due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
    if (aQuoridor == null || aQuoridor.getHistory() != null)
    {
      throw new RuntimeException("Unable to create History due to aQuoridor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    quoridor = aQuoridor;
  }

  public History(boolean aIsOverForGame, Quoridor aQuoridorForGame, Game aGameForQuoridor)
  {
    actionHistory = new ArrayList<Action>();
    game = new Game(aIsOverForGame, this, aQuoridorForGame);
    quoridor = new Quoridor(aGameForQuoridor, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Action getActionHistory(int index)
  {
    Action aActionHistory = actionHistory.get(index);
    return aActionHistory;
  }

  public List<Action> getActionHistory()
  {
    List<Action> newActionHistory = Collections.unmodifiableList(actionHistory);
    return newActionHistory;
  }

  public int numberOfActionHistory()
  {
    int number = actionHistory.size();
    return number;
  }

  public boolean hasActionHistory()
  {
    boolean has = actionHistory.size() > 0;
    return has;
  }

  public int indexOfActionHistory(Action aActionHistory)
  {
    int index = actionHistory.indexOf(aActionHistory);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfActionHistory()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addActionHistory(Action aActionHistory)
  {
    boolean wasAdded = false;
    if (actionHistory.contains(aActionHistory)) { return false; }
    History existingHistory = aActionHistory.getHistory();
    boolean isNewHistory = existingHistory != null && !this.equals(existingHistory);
    if (isNewHistory)
    {
      aActionHistory.setHistory(this);
    }
    else
    {
      actionHistory.add(aActionHistory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeActionHistory(Action aActionHistory)
  {
    boolean wasRemoved = false;
    //Unable to remove aActionHistory, as it must always have a history
    if (!this.equals(aActionHistory.getHistory()))
    {
      actionHistory.remove(aActionHistory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addActionHistoryAt(Action aActionHistory, int index)
  {  
    boolean wasAdded = false;
    if(addActionHistory(aActionHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActionHistory()) { index = numberOfActionHistory() - 1; }
      actionHistory.remove(aActionHistory);
      actionHistory.add(index, aActionHistory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveActionHistoryAt(Action aActionHistory, int index)
  {
    boolean wasAdded = false;
    if(actionHistory.contains(aActionHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActionHistory()) { index = numberOfActionHistory() - 1; }
      actionHistory.remove(aActionHistory);
      actionHistory.add(index, aActionHistory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addActionHistoryAt(aActionHistory, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (actionHistory.size() > 0)
    {
      Action aActionHistory = actionHistory.get(actionHistory.size() - 1);
      aActionHistory.delete();
      actionHistory.remove(aActionHistory);
    }
    
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    Quoridor existingQuoridor = quoridor;
    quoridor = null;
    if (existingQuoridor != null)
    {
      existingQuoridor.delete();
    }
  }

}