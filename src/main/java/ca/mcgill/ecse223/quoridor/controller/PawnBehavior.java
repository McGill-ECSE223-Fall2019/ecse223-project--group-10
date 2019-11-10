/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import PawnBehavior.PawnSMPlayingEastWest;
import PawnBehavior.PawnSMPlayingEastWestEastWest;
import PawnBehavior.PawnSMPlayingNorthSouth;
import PawnBehavior.PawnSMPlayingNorthSouthNorthSouth;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Playing, Finished }
  private PawnSM pawnSM;
  public enum PawnSMPlayingNorthSouth { Null, NorthSouth }
  private PawnSMPlayingNorthSouth pawnSMPlayingNorthSouth;
  public enum PawnSMPlayingNorthSouthNorthSouth { Null, Setup, AtNorthEdge, AtNorthBorder, AtSouthEdge, AtSouthBorder, MiddleNS }
  private PawnSMPlayingNorthSouthNorthSouth pawnSMPlayingNorthSouthNorthSouth;
  public enum PawnSMPlayingEastWest { Null, EastWest }
  private PawnSMPlayingEastWest pawnSMPlayingEastWest;
  public enum PawnSMPlayingEastWestEastWest { Null, Setup, AtEastBorder, AtEastEdge, AtWestBorder, AtWestEdge, MiddleEW }
  private PawnSMPlayingEastWestEastWest pawnSMPlayingEastWestEastWest;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    setPawnSM(PawnSM.Playing);
    setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth.Null);
    setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth.Null);
    setPawnSMPlayingEastWest(PawnSMPlayingEastWest.Null);
    setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest.Null);
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

  public String getPawnSMPlayingNorthSouthFullName()
  {
    String answer = pawnSMPlayingNorthSouth.toString();
    return answer;
  }

  public String getPawnSMPlayingNorthSouthNorthSouthFullName()
  {
    String answer = pawnSMPlayingNorthSouthNorthSouth.toString();
    return answer;
  }

  public String getPawnSMPlayingEastWestFullName()
  {
    String answer = pawnSMPlayingEastWest.toString();
    return answer;
  }

  public String getPawnSMPlayingEastWestEastWestFullName()
  {
    String answer = pawnSMPlayingEastWestEastWest.toString();
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

  public boolean setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;
    return true;
  }

  public boolean setPawnSMPlayingNorthSouth(PawnSMPlayingNorthSouth aPawnSMPlayingNorthSouth)
  {
    pawnSMPlayingNorthSouth = aPawnSMPlayingNorthSouth;
    return true;
  }

  public boolean setPawnSMPlayingNorthSouthNorthSouth(PawnSMPlayingNorthSouthNorthSouth aPawnSMPlayingNorthSouthNorthSouth)
  {
    pawnSMPlayingNorthSouthNorthSouth = aPawnSMPlayingNorthSouthNorthSouth;
    return true;
  }

  public boolean setPawnSMPlayingEastWest(PawnSMPlayingEastWest aPawnSMPlayingEastWest)
  {
    pawnSMPlayingEastWest = aPawnSMPlayingEastWest;
    return true;
  }

  public boolean setPawnSMPlayingEastWestEastWest(PawnSMPlayingEastWestEastWest aPawnSMPlayingEastWestEastWest)
  {
    pawnSMPlayingEastWestEastWest = aPawnSMPlayingEastWestEastWest;
    return true;
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
  // line 45 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    return 0;
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 47 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    return 0;
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 49 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    return false;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 51 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    return false;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 54 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 58 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North ;
  }

  
}