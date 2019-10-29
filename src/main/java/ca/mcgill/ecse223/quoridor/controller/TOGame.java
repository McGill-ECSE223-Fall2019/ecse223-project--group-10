/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.sql.Time;

// line 3 "../../../../../TO.ump"
public class TOGame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGame Attributes
  private Time playerOneTime;
  private Time playerTwoTime;
  private String playerOne;
  private String playerTwo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGame(Time aPlayerOneTime, Time aPlayerTwoTime, String aPlayerOne, String aPlayerTwo)
  {
    playerOneTime = aPlayerOneTime;
    playerTwoTime = aPlayerTwoTime;
    playerOne = aPlayerOne;
    playerTwo = aPlayerTwo;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlayerOneTime(Time aPlayerOneTime)
  {
    boolean wasSet = false;
    playerOneTime = aPlayerOneTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerTwoTime(Time aPlayerTwoTime)
  {
    boolean wasSet = false;
    playerTwoTime = aPlayerTwoTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerOne(String aPlayerOne)
  {
    boolean wasSet = false;
    playerOne = aPlayerOne;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerTwo(String aPlayerTwo)
  {
    boolean wasSet = false;
    playerTwo = aPlayerTwo;
    wasSet = true;
    return wasSet;
  }

  public Time getPlayerOneTime()
  {
    return playerOneTime;
  }

  public Time getPlayerTwoTime()
  {
    return playerTwoTime;
  }

  public String getPlayerOne()
  {
    return playerOne;
  }

  public String getPlayerTwo()
  {
    return playerTwo;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "playerOne" + ":" + getPlayerOne()+ "," +
            "playerTwo" + ":" + getPlayerTwo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playerOneTime" + "=" + (getPlayerOneTime() != null ? !getPlayerOneTime().equals(this)  ? getPlayerOneTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "playerTwoTime" + "=" + (getPlayerTwoTime() != null ? !getPlayerTwoTime().equals(this)  ? getPlayerTwoTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}