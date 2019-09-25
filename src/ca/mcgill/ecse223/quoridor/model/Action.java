/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/


import java.util.*;

// line 45 "Quoridor.ump"
public abstract class Action
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Action Associations
  private Player user;
  private History history;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Action(History aHistory)
  {
    boolean didAddHistory = setHistory(aHistory);
    if (!didAddHistory)
    {
      throw new RuntimeException("Unable to create actionHistory due to history. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getUser()
  {
    return user;
  }

  public boolean hasUser()
  {
    boolean has = user != null;
    return has;
  }
  /* Code from template association_GetOne */
  public History getHistory()
  {
    return history;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setUser(Player aNewUser)
  {
    boolean wasSet = false;
    user = aNewUser;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setHistory(History aHistory)
  {
    boolean wasSet = false;
    if (aHistory == null)
    {
      return wasSet;
    }

    History existingHistory = history;
    history = aHistory;
    if (existingHistory != null && !existingHistory.equals(aHistory))
    {
      existingHistory.removeActionHistory(this);
    }
    history.addActionHistory(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    user = null;
    History placeholderHistory = history;
    this.history = null;
    if(placeholderHistory != null)
    {
      placeholderHistory.removeActionHistory(this);
    }
  }

}