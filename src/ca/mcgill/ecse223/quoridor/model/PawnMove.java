/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 53 "Quoridor.ump"
public class PawnMove extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnMove Associations
  private Cordinate cordinate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnMove(Cordinate aCordinate)
  {
    super();
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create PawnMove due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
    super.delete();
  }

}