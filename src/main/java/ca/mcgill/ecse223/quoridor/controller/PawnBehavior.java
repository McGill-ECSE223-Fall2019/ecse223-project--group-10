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
  public enum PawnSM {  }
  private PawnSM pawnSM;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
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
  // line 14 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    return 0;
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 16 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    return 0;
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 18 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    return false;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 20 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    return false;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 23 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 27 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}