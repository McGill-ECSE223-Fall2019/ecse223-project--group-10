/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Playing, Finished }
  public enum PawnSMPlayingNorthSouth { Null, NorthSouth }
  public enum PawnSMPlayingNorthSouthNorthSouth { Null, Setup, AtNorthEdge, AtNorthBorder, AtSouthEdge, AtSouthBorder, MiddleNS }
  public enum PawnSMPlayingEastWest { Null, EastWest }
  public enum PawnSMPlayingEastWestEastWest { Null, Setup, AtEastEdge, AtEastBorder, AtWestEdge, AtWestBorder, MiddleEW }
  private PawnSM pawnSM;
  private PawnSMPlayingNorthSouth pawnSMPlayingNorthSouth;
  private PawnSMPlayingNorthSouthNorthSouth pawnSMPlayingNorthSouthNorthSouth;
  private PawnSMPlayingEastWest pawnSMPlayingEastWest;
  private PawnSMPlayingEastWestEastWest pawnSMPlayingEastWestEastWest;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth.Null);
    setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
    setPawnSMPlayingEastWest(PawnSMPlayingEastWest.Null);
    setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
    setPawnSM(PawnSM.Playing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMPlayingNorthSouth != PawnSMPlayingNorthSouth.Null) { answer += "." + pawnSMPlayingNorthSouth.toString(); }
    if (pawnSMPlayingNorthSouthNorthSouth != PawnSMPlayingNorthSouthNorthSouth.Null) { answer += "." + pawnSMPlayingNorthSouthNorthSouth.toString(); }
    if (pawnSMPlayingEastWest != PawnSMPlayingEastWest.Null) { answer += "." + pawnSMPlayingEastWest.toString(); }
    if (pawnSMPlayingEastWestEastWest != PawnSMPlayingEastWestEastWest.Null) { answer += "." + pawnSMPlayingEastWestEastWest.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMPlayingNorthSouth getPawnSMPlayingNorthSouth()
  {
    return pawnSMPlayingNorthSouth;
  }

  public PawnSMPlayingNorthSouthNorthSouth getPawnSMPlayingNorthSouthNorthSouth()
  {
    return pawnSMPlayingNorthSouthNorthSouth;
  }

  public PawnSMPlayingEastWest getPawnSMPlayingEastWest()
  {
    return pawnSMPlayingEastWest;
  }

  public PawnSMPlayingEastWestEastWest getPawnSMPlayingEastWestEastWest()
  {
    return pawnSMPlayingEastWestEastWest;
  }

  public boolean moveUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthEdge:
        exitPawnSMPlayingNorthSouthNorthSouth();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtNorthBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.North)&&!(isLegalJump(MoveDirection.North)))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (isLegalStep(MoveDirection.North)&&!(isLegalJump(MoveDirection.North))&&getCurrentPawnRow()==3)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()==3)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()==4)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.South)&&!(isLegalJump(MoveDirection.South)))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        exitPawnSMPlayingNorthSouthNorthSouth();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtSouthBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (isLegalStep(MoveDirection.South)&&!(isLegalJump(MoveDirection.South))&&getCurrentPawnRow()==7)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()==7)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()==6)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastEdge:
        exitPawnSMPlayingEastWestEastWest();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtEastBorder:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.East)&&!(isLegalJump(MoveDirection.East)))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (isLegalStep(MoveDirection.East)&&!(isLegalJump(MoveDirection.East))&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==6)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastEdge:
        if (isLegalStep(MoveDirection.West)&&!(isLegalJump(MoveDirection.West)))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        exitPawnSMPlayingEastWestEastWest();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
        wasEventProcessed = true;
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (isLegalStep(MoveDirection.West)&&!(isLegalJump(MoveDirection.West))&&getCurrentPawnColumn()==3)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()==3)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()==4)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitPawnSM()
  {
    switch(pawnSM)
    {
      case Playing:
        exitPawnSMPlayingNorthSouth();
        exitPawnSMPlayingEastWest();
        break;
    }
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case Playing:
        if (pawnSMPlayingNorthSouth == PawnSMPlayingNorthSouth.Null) { setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth.NorthSouth); }
        if (pawnSMPlayingEastWest == PawnSMPlayingEastWest.Null) { setPawnSMPlayingEastWest(PawnSMPlayingEastWest.EastWest); }
        break;
    }
  }

  private void exitPawnSMPlayingNorthSouth()
  {
    switch(pawnSMPlayingNorthSouth)
    {
      case NorthSouth:
        exitPawnSMPlayingNorthSouthNorthSouth();
        setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth.Null);
        break;
    }
  }

  private void setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth aPawnSMPlayingNorthSouth)
  {
    pawnSMPlayingNorthSouth = aPawnSMPlayingNorthSouth;
    if (pawnSM != PawnSM.Playing && aPawnSMPlayingNorthSouth != PawnSMPlayingNorthSouth.Null) { setPawnSM(PawnSM.Playing); }

    // entry actions and do activities
    switch(pawnSMPlayingNorthSouth)
    {
      case NorthSouth:
        if (pawnSMPlayingNorthSouthNorthSouth == PawnSMPlayingNorthSouthNorthSouth.Null) { setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Setup); }
        break;
    }
  }

  private void exitPawnSMPlayingNorthSouthNorthSouth()
  {
    switch(pawnSMPlayingNorthSouthNorthSouth)
    {
      case Setup:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
      case AtNorthEdge:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
      case AtNorthBorder:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
      case AtSouthEdge:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
      case AtSouthBorder:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
      case MiddleNS:
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
        break;
    }
  }

  private void setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth)
  {
    pawnSMPlayingNorthSouthNorthSouth = aPawnSMPlayingNorthSouthNorthSouth;
    if (pawnSMPlayingNorthSouth != PawnSMPlayingNorthSouth.NorthSouth && aPawnSMPlayingNorthSouthNorthSouth != PawnSMPlayingNorthSouthNorthSouth.Null) { setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth.NorthSouth); }
  }

  private void exitPawnSMPlayingEastWest()
  {
    switch(pawnSMPlayingEastWest)
    {
      case EastWest:
        exitPawnSMPlayingEastWestEastWest();
        setPawnSMPlayingEastWest(PawnSMPlayingEastWest.Null);
        break;
    }
  }

  private void setPawnSMPlayingEastWest(PawnSMPlayingEastWest aPawnSMPlayingEastWest)
  {
    pawnSMPlayingEastWest = aPawnSMPlayingEastWest;
    if (pawnSM != PawnSM.Playing && aPawnSMPlayingEastWest != PawnSMPlayingEastWest.Null) { setPawnSM(PawnSM.Playing); }

    // entry actions and do activities
    switch(pawnSMPlayingEastWest)
    {
      case EastWest:
        if (pawnSMPlayingEastWestEastWest == PawnSMPlayingEastWestEastWest.Null) { setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Setup); }
        break;
    }
  }

  private void exitPawnSMPlayingEastWestEastWest()
  {
    switch(pawnSMPlayingEastWestEastWest)
    {
      case Setup:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
      case AtEastEdge:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
      case AtEastBorder:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
      case AtWestEdge:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
      case AtWestBorder:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
      case MiddleEW:
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
        break;
    }
  }

  private void setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest)
  {
    pawnSMPlayingEastWestEastWest = aPawnSMPlayingEastWestEastWest;
    if (pawnSMPlayingEastWest != PawnSMPlayingEastWest.EastWest && aPawnSMPlayingEastWestEastWest != PawnSMPlayingEastWestEastWest.Null) { setPawnSMPlayingEastWest(PawnSMPlayingEastWest.EastWest); }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 91 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    return Quoridor223Controller.getCurrentPawnRow();
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 96 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    return Quoridor223Controller.getCurrentPawnColumn();
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 100 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    return false;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 103 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    return false;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 106 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 110 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North ;
  }

  
}