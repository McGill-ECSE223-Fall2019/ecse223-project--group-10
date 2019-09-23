/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 21 "firstdraft.ump"
public class Box
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Box Attributes
  private boolean isHighLighted;

  //Box Associations
  private Cordinate cordinate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Box(boolean aIsHighLighted, Cordinate aCordinate)
  {
    isHighLighted = aIsHighLighted;
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Box due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsHighLighted(boolean aIsHighLighted)
  {
    boolean wasSet = false;
    isHighLighted = aIsHighLighted;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsHighLighted()
  {
    return isHighLighted;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsHighLighted()
  {
    return isHighLighted;
  }
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


  public String toString()
  {
    return super.toString() + "["+
            "isHighLighted" + ":" + getIsHighLighted()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null");
  }
}