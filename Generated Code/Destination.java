/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 36 "firstdraft.ump"
public class Destination
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Destination Attributes
  private boolean horizontal;
  private int index;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Destination(boolean aHorizontal, int aIndex)
  {
    horizontal = aHorizontal;
    index = aIndex;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHorizontal(boolean aHorizontal)
  {
    boolean wasSet = false;
    horizontal = aHorizontal;
    wasSet = true;
    return wasSet;
  }

  public boolean setIndex(int aIndex)
  {
    boolean wasSet = false;
    index = aIndex;
    wasSet = true;
    return wasSet;
  }

  public boolean getHorizontal()
  {
    return horizontal;
  }

  public int getIndex()
  {
    return index;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHorizontal()
  {
    return horizontal;
  }

  public void delete()
  {}

  // line 40 "firstdraft.ump"
  public Boolean checkWin(Cordinate cord){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "horizontal" + ":" + getHorizontal()+ "," +
            "index" + ":" + getIndex()+ "]";
  }
}