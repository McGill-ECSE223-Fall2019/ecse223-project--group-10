/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 60 "Quoridor.ump"
public class Jump extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Jump Associations
  private Cordinate jumpTo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Jump(Cordinate aJumpTo)
  {
    super();
    if (!setJumpTo(aJumpTo))
    {
      throw new RuntimeException("Unable to create Jump due to aJumpTo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cordinate getJumpTo()
  {
    return jumpTo;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setJumpTo(Cordinate aNewJumpTo)
  {
    boolean wasSet = false;
    if (aNewJumpTo != null)
    {
      jumpTo = aNewJumpTo;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    jumpTo = null;
    super.delete();
  }

}