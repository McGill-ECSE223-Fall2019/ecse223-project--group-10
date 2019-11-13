/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.HashMap;
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
        // line 17 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 24 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 32 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtSouthBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 38 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
        wasEventProcessed = true;
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==3&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==3&&isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==4&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 48 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 20 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtNorthBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 27 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 33 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 40 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
        wasEventProcessed = true;
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==7&&isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==7&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==6&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingNorthSouthNorthSouth();
        // line 55 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        // line 65 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        exitPawnSMPlayingEastWestEastWest();
        // line 72 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 80 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
        wasEventProcessed = true;
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 86 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
        wasEventProcessed = true;
        break;
      case MiddleEW:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==6)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 96 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 68 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtEastBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 75 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        exitPawnSMPlayingEastWestEastWest();
        // line 81 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
        wasEventProcessed = true;
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 88 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
        wasEventProcessed = true;
        break;
      case MiddleEW:
        if (getCurrentPawnColumn()==3&&isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==3&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==4&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMPlayingEastWestEastWest();
        // line 103 "../../../../../PawnStateMachine.ump"
        illegalMove();
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
  // line 115 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    if(player==currentGame.getWhitePlayer()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getRow();
		}
		return currentGame.getCurrentPosition().getBlackPosition().getTile().getRow();
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 123 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    if(player==currentGame.getWhitePlayer()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getColumn();
		}
		return currentGame.getCurrentPosition().getBlackPosition().getTile().getColumn();
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 131 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    HashMap<Integer, Boolean> wallMap = getWallMap();
		int row = getCurrentPawnRow();
		int col = getCurrentPawnColumn();
		if(isTherePlayerInDir(dir,row,col))return false;
		if(isThereWallInDir(dir,row,col))return false;
	    return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 141 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    HashMap<Integer, Boolean> wallMap = getWallMap();
		int row = getCurrentPawnRow();
		int col = getCurrentPawnColumn();
		if(isThereWallInDir(dir,row,col))return false;
		if(!isTherePlayerInDir(dir,row,col))return false;
		switch(dir){
			case North:
				row--;
				break;
			case South:
				row++;
				break;
			case East:
				col++;
				break;
			case West:
				col--;
				break;
		}
		if(isThereWallInDir(dir,row,col))return false;
    	return true;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 189 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }

  // line 191 "../../../../../PawnStateMachine.ump"
   private boolean isThereWallInDir(MoveDirection dir, int row, int col){
    HashMap<Integer, Boolean> wallMap = getWallMap();
	  switch(dir) {
	  	case North:
	  		row-=1;
	  		if(pawnSMPlayingEastWestEastWest!=PawnSMPlayingEastWestEastWest.AtWestEdge&&wallMap.containsKey((row)*9+col-1)&&wallMap.get((row)*9+col-1))return true;
			if(pawnSMPlayingEastWestEastWest!=PawnSMPlayingEastWestEastWest.AtEastEdge&&wallMap.containsKey((row)*9+col)&&wallMap.get((row)*9+col))return true;
	  		break;
	  	case South:
	  		if(pawnSMPlayingEastWestEastWest!=PawnSMPlayingEastWestEastWest.AtWestEdge&&wallMap.containsKey((row)*9+col-1)&&wallMap.get((row)*9+col-1))return true;
			if(pawnSMPlayingEastWestEastWest!=PawnSMPlayingEastWestEastWest.AtEastEdge&&wallMap.containsKey((row)*9+col)&&wallMap.get((row)*9+col))return true;
	  		break;
	  	case East:
			if(pawnSMPlayingNorthSouthNorthSouth!=PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge&&wallMap.containsKey((row)*9+col)&&!wallMap.get((row)*9+col))return true;
			if(pawnSMPlayingNorthSouthNorthSouth!=PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge&&wallMap.containsKey((row-1)*9+col)&&!wallMap.get((row-1)*9+col))return true;
	  		break;
	  	case West:
	  		col--;
			if(pawnSMPlayingNorthSouthNorthSouth!=PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge&&wallMap.containsKey((row)*9+col)&&!wallMap.get((row)*9+col))return true;
			if(pawnSMPlayingNorthSouthNorthSouth!=PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge&&wallMap.containsKey((row-1)*9+col)&&!wallMap.get((row-1)*9+col))return true;
	  		break;
	  }
	  return false;
  }

  // line 215 "../../../../../PawnStateMachine.ump"
   private boolean isTherePlayerInDir(MoveDirection dir, int row, int col){
    PlayerPosition otherPlayerPosition = player.equals(currentGame.getWhitePlayer())?currentGame.getCurrentPosition().getBlackPosition():currentGame.getCurrentPosition().getWhitePosition();
		int otherRow = otherPlayerPosition.getTile().getRow();
		int otherCol = otherPlayerPosition.getTile().getColumn();
	    switch(dir) {
	    		case North:
	    			if(col==otherCol&&row-1==otherRow)return true;
	    			break;
	    		case South:
	    			if(col==otherCol&&row+1==otherRow)return true;
	    			break;
	    		case West:
	    			if(col-1==otherCol&&row==otherRow)return true;
	    			break;
	    		case East:
	    			if(col+1==otherCol&&row==otherRow)return true;
	    			break;
	   }
	    return false;
  }

  // line 235 "../../../../../PawnStateMachine.ump"
   private boolean isWinningMove(){
    return false;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 163 "../../../../../PawnStateMachine.ump"
  HashMap<Integer,Boolean> getWallMap () 
  {
    HashMap<Integer, Boolean> wallPositions = new HashMap<Integer, Boolean>();		
		//TODO: switch to player list (more convenient if 2+)
		for (Wall wall : currentGame.getCurrentPosition().getBlackWallsOnBoard()) {
			if(wall.equals(currentGame.getWallMoveCandidate().getWallPlaced()))continue;
			WallMove wall_move = wall.getMove();
			int row = wall_move.getTargetTile().getRow();
			int col = wall_move.getTargetTile().getColumn();
			boolean dir_attr = false;
			if (wall_move.getWallDirection().equals(Direction.Horizontal))
				dir_attr = true;
			wallPositions.put(row * 9 + col, dir_attr);
		}
		for (Wall wall : currentGame.getCurrentPosition().getWhiteWallsOnBoard()) {
			if(wall.equals(currentGame.getWallMoveCandidate().getWallPlaced()))continue;
			WallMove wall_move = wall.getMove();
			int row = wall_move.getTargetTile().getRow();
			int col = wall_move.getTargetTile().getColumn();
			boolean dir_attr = false;
			if (wall_move.getWallDirection().equals(Direction.Horizontal))
				dir_attr = true;
			wallPositions.put(row * 9 + col, dir_attr);
		}
		return wallPositions;
  }

// line 239 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North ;
  }

  
}