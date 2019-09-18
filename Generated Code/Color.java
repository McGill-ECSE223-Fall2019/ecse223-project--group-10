/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 37 "firstdraft.ump"
public class Color
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Color Attributes
  private int r;
  private int g;
  private int b;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Color(int aR, int aG, int aB)
  {
    r = aR;
    g = aG;
    b = aB;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setR(int aR)
  {
    boolean wasSet = false;
    r = aR;
    wasSet = true;
    return wasSet;
  }

  public boolean setG(int aG)
  {
    boolean wasSet = false;
    g = aG;
    wasSet = true;
    return wasSet;
  }

  public boolean setB(int aB)
  {
    boolean wasSet = false;
    b = aB;
    wasSet = true;
    return wasSet;
  }

  public int getR()
  {
    return r;
  }

  public int getG()
  {
    return g;
  }

  public int getB()
  {
    return b;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "r" + ":" + getR()+ "," +
            "g" + ":" + getG()+ "," +
            "b" + ":" + getB()+ "]";
  }
}