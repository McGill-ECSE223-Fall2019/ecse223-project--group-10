/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 47 "firstdraft.ump"
public class Destination
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { Horizontal, Vertical }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Destination Attributes
  private int index;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Destination(int aIndex)
  {
    index = aIndex;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIndex(int aIndex)
  {
    boolean wasSet = false;
    index = aIndex;
    wasSet = true;
    return wasSet;
  }

  public int getIndex()
  {
    return index;
  }

  public void delete()
  {}

  // line 51 "firstdraft.ump"
  public Boolean checkWin(Cordinate cord){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "index" + ":" + getIndex()+ "]";
  }
}