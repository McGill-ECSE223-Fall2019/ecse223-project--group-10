/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 49 "Quoridor.ump"
public class Move extends Action
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Attributes
  private int oldX;
  private int oldY;
  private int newX;
  private int newY;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move(History aHistory, int aOldX, int aOldY, int aNewX, int aNewY)
  {
    super(aHistory);
    oldX = aOldX;
    oldY = aOldY;
    newX = aNewX;
    newY = aNewY;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOldX(int aOldX)
  {
    boolean wasSet = false;
    oldX = aOldX;
    wasSet = true;
    return wasSet;
  }

  public boolean setOldY(int aOldY)
  {
    boolean wasSet = false;
    oldY = aOldY;
    wasSet = true;
    return wasSet;
  }

  public boolean setNewX(int aNewX)
  {
    boolean wasSet = false;
    newX = aNewX;
    wasSet = true;
    return wasSet;
  }

  public boolean setNewY(int aNewY)
  {
    boolean wasSet = false;
    newY = aNewY;
    wasSet = true;
    return wasSet;
  }

  public int getOldX()
  {
    return oldX;
  }

  public int getOldY()
  {
    return oldY;
  }

  public int getNewX()
  {
    return newX;
  }

  public int getNewY()
  {
    return newY;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "oldX" + ":" + getOldX()+ "," +
            "oldY" + ":" + getOldY()+ "," +
            "newX" + ":" + getNewX()+ "," +
            "newY" + ":" + getNewY()+ "]";
  }
}