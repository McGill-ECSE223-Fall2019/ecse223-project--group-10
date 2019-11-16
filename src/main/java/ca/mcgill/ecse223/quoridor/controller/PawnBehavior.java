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

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case Setup:
        if (getCurrentPawnRow()==1)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==2)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==9)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==8)
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
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

    switch (aPawnSMPlayingEastWestEastWest)
    {
      case Setup:
        if (getCurrentPawnColumn()==1)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==2)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==9)
        {
          exitPawnSMPlayingEastWestEastWest();
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==8)
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
        // line 21 "../../../../../PawnStateMachine.ump"
          updateMove(1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 22 "../../../../../PawnStateMachine.ump"
          updateMove(2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 29 "../../../../../PawnStateMachine.ump"
          updateMove(1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 30 "../../../../../PawnStateMachine.ump"
          updateMove(2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 47 "../../../../../PawnStateMachine.ump"
          updateMove(1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==7&&isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 66 "../../../../../PawnStateMachine.ump"
          updateMove(1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==7&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 67 "../../../../../PawnStateMachine.ump"
          updateMove(2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==6&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 68 "../../../../../PawnStateMachine.ump"
          updateMove(2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 69 "../../../../../PawnStateMachine.ump"
          updateMove(1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 70 "../../../../../PawnStateMachine.ump"
          updateMove(2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDownLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthEdge:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthBorder:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==7&&isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastEdge:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 93 "../../../../../PawnStateMachine.ump"
          updateMove(1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastBorder:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 102 "../../../../../PawnStateMachine.ump"
          updateMove(1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 120 "../../../../../PawnStateMachine.ump"
          updateMove(1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (getCurrentPawnColumn()==3&&isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 144 "../../../../../PawnStateMachine.ump"
          updateMove(1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 145 "../../../../../PawnStateMachine.ump"
          updateMove(1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDownRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthEdge:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthBorder:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==7&&isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastBorder:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 103 "../../../../../PawnStateMachine.ump"
          updateMove(1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 110 "../../../../../PawnStateMachine.ump"
          updateMove(1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 121 "../../../../../PawnStateMachine.ump"
          updateMove(1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 133 "../../../../../PawnStateMachine.ump"
          updateMove(1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==7&&isLegalDiagonalMove(MoveDirection.South))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 134 "../../../../../PawnStateMachine.ump"
          updateMove(1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 28 "../../../../../PawnStateMachine.ump"
          updateMove(-1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 38 "../../../../../PawnStateMachine.ump"
          updateMove(-1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 39 "../../../../../PawnStateMachine.ump"
          updateMove(-2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 45 "../../../../../PawnStateMachine.ump"
          updateMove(-1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 46 "../../../../../PawnStateMachine.ump"
          updateMove(-2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==3&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 55 "../../../../../PawnStateMachine.ump"
          updateMove(-2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==3&&isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 56 "../../../../../PawnStateMachine.ump"
          updateMove(-1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==4&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 57 "../../../../../PawnStateMachine.ump"
          updateMove(-2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 58 "../../../../../PawnStateMachine.ump"
          updateMove(-1,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
        // line 59 "../../../../../PawnStateMachine.ump"
          updateMove(-2,0);
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveUpRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthBorder:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==3&&isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastBorder:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 101 "../../../../../PawnStateMachine.ump"
          updateMove(-1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 109 "../../../../../PawnStateMachine.ump"
          updateMove(-1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 117 "../../../../../PawnStateMachine.ump"
          updateMove(-1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (getCurrentPawnColumn()==7&&isLegalDiagonalMove(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 131 "../../../../../PawnStateMachine.ump"
          updateMove(-1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 132 "../../../../../PawnStateMachine.ump"
          updateMove(-1,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveUpLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth = pawnSMPlayingNorthSouthNorthSouth;
    PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest = pawnSMPlayingEastWestEastWest;
    switch (aPawnSMPlayingNorthSouthNorthSouth)
    {
      case AtNorthBorder:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtSouthBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthBorder:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleNS:
        if (getCurrentPawnRow()==3&&isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.AtNorthBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingNorthSouthNorthSouth();
          setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.MiddleNS);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMPlayingEastWestEastWest)
    {
      case AtEastEdge:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 92 "../../../../../PawnStateMachine.ump"
          updateMove(-1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastBorder:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 100 "../../../../../PawnStateMachine.ump"
          updateMove(-1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 118 "../../../../../PawnStateMachine.ump"
          updateMove(-1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (getCurrentPawnColumn()==3&&isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 142 "../../../../../PawnStateMachine.ump"
          updateMove(-1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalDiagonalMove(MoveDirection.North))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 143 "../../../../../PawnStateMachine.ump"
          updateMove(-1,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
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
        // line 90 "../../../../../PawnStateMachine.ump"
          updateMove(0,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 91 "../../../../../PawnStateMachine.ump"
          updateMove(0,-2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 97 "../../../../../PawnStateMachine.ump"
          updateMove(0,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 98 "../../../../../PawnStateMachine.ump"
          updateMove(0,-2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 115 "../../../../../PawnStateMachine.ump"
          updateMove(0,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (getCurrentPawnColumn()==3&&isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 136 "../../../../../PawnStateMachine.ump"
          updateMove(0,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==3&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 137 "../../../../../PawnStateMachine.ump"
          updateMove(0,-2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==4&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 138 "../../../../../PawnStateMachine.ump"
          updateMove(0,-2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 139 "../../../../../PawnStateMachine.ump"
          updateMove(0,-1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 140 "../../../../../PawnStateMachine.ump"
          updateMove(0,-2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
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
      case AtEastBorder:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 96 "../../../../../PawnStateMachine.ump"
          updateMove(0,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 106 "../../../../../PawnStateMachine.ump"
          updateMove(0,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtWestBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 107 "../../../../../PawnStateMachine.ump"
          updateMove(0,2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestBorder:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 113 "../../../../../PawnStateMachine.ump"
          updateMove(0,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 114 "../../../../../PawnStateMachine.ump"
          updateMove(0,2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleEW:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 125 "../../../../../PawnStateMachine.ump"
          updateMove(0,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 126 "../../../../../PawnStateMachine.ump"
          updateMove(0,2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==6)
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 127 "../../../../../PawnStateMachine.ump"
          updateMove(0,2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.AtEastBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 128 "../../../../../PawnStateMachine.ump"
          updateMove(0,1);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlayingEastWestEastWest();
        // line 129 "../../../../../PawnStateMachine.ump"
          updateMove(0,2);
          setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.MiddleEW);
          wasEventProcessed = true;
          break;
        }
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
  // line 156 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    if(player==currentGame.getWhitePlayer()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getRow();
		}
		return currentGame.getCurrentPosition().getBlackPosition().getTile().getRow();
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 164 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    if(player==currentGame.getWhitePlayer()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getColumn();
		}
		return currentGame.getCurrentPosition().getBlackPosition().getTile().getColumn();
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 172 "../../../../../PawnStateMachine.ump"
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
  // line 182 "../../../../../PawnStateMachine.ump"
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
  // line 231 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }

  // line 233 "../../../../../PawnStateMachine.ump"
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

  // line 257 "../../../../../PawnStateMachine.ump"
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

  // line 278 "../../../../../PawnStateMachine.ump"
   private boolean isLegalDiagonalMove(MoveDirection dir){
    int row = getCurrentPawnRow();
	int col = getCurrentPawnColumn();
    switch(dir){
   			case North:
   				if(row==1||col==1)return false;
   				if(isTherePlayerInDir(MoveDirection.North,row,col)) {
   					if(isThereWallInDir(MoveDirection.North, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.North, row-1, col)&&(row-1)!=1)return false;
   					if(isThereWallInDir(MoveDirection.West, row-1, col))return false;
   				}
   				else if(isTherePlayerInDir(MoveDirection.West, row, col)) {
   					if(isThereWallInDir(MoveDirection.West, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.West, row, col-1)&&(col-1)!=1)return false;
   					if(isThereWallInDir(MoveDirection.North, row, col-1))return false;
   				}
   				else return false;
   				break;
   			case South:

   				if(row==9||col==9)return false;
   				if(isTherePlayerInDir(MoveDirection.South,row,col)) {
   					if(isThereWallInDir(MoveDirection.South, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.South, row+1, col)&&(row+1)!=9)return false;
   					if(isThereWallInDir(MoveDirection.East, row+1, col))return false;
   				}
   				else if(isTherePlayerInDir(MoveDirection.East, row, col)) {
   					if(isThereWallInDir(MoveDirection.East, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.East, row, col+1)&&(col+1)!=9)return false;
   					if(isThereWallInDir(MoveDirection.South, row, col+1))return false;
   				}
   				else return false;
   				break;
   			case East:
   				if(row==1||col==9)return false;
   				if(isTherePlayerInDir(MoveDirection.North,row,col)) {
   					if(isThereWallInDir(MoveDirection.North, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.North, row-1, col)&&(row-1)!=1)return false;
   					if(isThereWallInDir(MoveDirection.East, row-1, col))return false;
   				}
   				else if(isTherePlayerInDir(MoveDirection.East, row, col)) {
   					if(isThereWallInDir(MoveDirection.East, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.East, row, col+1)&&(col+1)!=9)return false;
   					if(isThereWallInDir(MoveDirection.North, row, col+1))return false;
   				}
   				else return false;
   				break;
   			case West:
   				if(row==9||col==1)return false;
   				if(isTherePlayerInDir(MoveDirection.South,row,col)) {
   					if(isThereWallInDir(MoveDirection.South, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.South, row+1, col)&&(row+1)!=9)return false;
   					if(isThereWallInDir(MoveDirection.West, row+1, col))return false;
   				}
   				else if(isTherePlayerInDir(MoveDirection.West, row, col)) {
   					if(isThereWallInDir(MoveDirection.West, row, col))return false;
   					if(!isThereWallInDir(MoveDirection.West, row, col-1)&&(col-1)!=1)return false;
   					if(isThereWallInDir(MoveDirection.South, row, col-1))return false;
   				}
   				else return false;
   				break;
   		}
   		return true;
  }

  // line 342 "../../../../../PawnStateMachine.ump"
   private void updateMove(int i, int j){
    GamePosition clone = clonePosition(currentGame.getCurrentPosition());
	    currentGame.setCurrentPosition(clone);
	    PlayerPosition playerpos;
	    if(isWhite())playerpos = clone.getWhitePosition();
	    else playerpos = clone.getBlackPosition();
	    int row = playerpos.getTile().getRow();
	    int col = playerpos.getTile().getColumn();
	    playerpos.setTile(Quoridor223Controller.getTile(row+i,col+j));
  }

  // line 352 "../../../../../PawnStateMachine.ump"
   private boolean isWinningMove(){
    if(isWhite()&&getCurrentPawnRow()==1)return true;
	   if(!isWhite()&&getCurrentPawnRow()==9)return true;
	   return false;
  }

  // line 357 "../../../../../PawnStateMachine.ump"
   private boolean isWhite(){
    return player.equals(currentGame.getWhitePlayer());
  }

  // line 360 "../../../../../PawnStateMachine.ump"
   private static  PlayerPosition clonePlayerPosition(PlayerPosition playerPos){
    return new PlayerPosition(playerPos.getPlayer(), playerPos.getTile());
  }

  // line 363 "../../../../../PawnStateMachine.ump"
   private static  GamePosition clonePosition(GamePosition oldPosition){
    PlayerPosition newWhitePosition = clonePlayerPosition(oldPosition.getWhitePosition());
		PlayerPosition newBlackPosition = clonePlayerPosition(oldPosition.getBlackPosition());
		GamePosition newPosition = new GamePosition(oldPosition.getId() + 1, newWhitePosition, newBlackPosition,
				oldPosition.getPlayerToMove(), oldPosition.getGame());
		for (Wall wall : oldPosition.getBlackWallsInStock())
			newPosition.addBlackWallsInStock(wall);
		for (Wall wall : oldPosition.getWhiteWallsInStock())
			newPosition.addWhiteWallsInStock(wall);
		for (Wall wall : oldPosition.getBlackWallsOnBoard())
			newPosition.addBlackWallsOnBoard(wall);
		for (Wall wall : oldPosition.getWhiteWallsOnBoard())
			newPosition.addWhiteWallsOnBoard(wall);
		return newPosition;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 204 "../../../../../PawnStateMachine.ump"
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

// line 379 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North ;
  }

  
}