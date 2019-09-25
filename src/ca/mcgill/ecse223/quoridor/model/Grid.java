/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 11 "firstdraft.ump"
public class Grid
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum State { Highlighted, NotHighlighted }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Grid Associations
  private Cordinate cordinate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Grid(Cordinate aCordinate)
  {
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Grid due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cordinate getCordinate()
  {
    return cordinate;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCordinate(Cordinate aNewCordinate)
  {
    boolean wasSet = false;
    if (aNewCordinate != null)
    {
      cordinate = aNewCordinate;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    cordinate = null;
  }

}