/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 55 "Quoridor.ump"
public class AddWall extends Move
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { Horizontal, Vertical }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddWall Associations
  private Cordinate wallCord;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AddWall(Cordinate aWallCord)
  {
    super();
    if (!setWallCord(aWallCord))
    {
      throw new RuntimeException("Unable to create AddWall due to aWallCord. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cordinate getWallCord()
  {
    return wallCord;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setWallCord(Cordinate aNewWallCord)
  {
    boolean wasSet = false;
    if (aNewWallCord != null)
    {
      wallCord = aNewWallCord;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    wallCord = null;
    super.delete();
  }

}