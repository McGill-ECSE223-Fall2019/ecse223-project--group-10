/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 32 "firstdraft.ump"
public class Wall
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private boolean horizontal;
  private boolean isHighLighted;

  //Wall Associations
  private Color color;
  private Cordinate cordinate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(boolean aHorizontal, boolean aIsHighLighted, Color aColor, Cordinate aCordinate)
  {
    horizontal = aHorizontal;
    isHighLighted = aIsHighLighted;
    if (!setColor(aColor))
    {
      throw new RuntimeException("Unable to create Wall due to aColor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Wall due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setIsHighLighted(boolean aIsHighLighted)
  {
    boolean wasSet = false;
    isHighLighted = aIsHighLighted;
    wasSet = true;
    return wasSet;
  }

  public boolean getHorizontal()
  {
    return horizontal;
  }

  public boolean getIsHighLighted()
  {
    return isHighLighted;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHorizontal()
  {
    return horizontal;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsHighLighted()
  {
    return isHighLighted;
  }
  /* Code from template association_GetOne */
  public Color getColor()
  {
    return color;
  }
  /* Code from template association_GetOne */
  public Cordinate getCordinate()
  {
    return cordinate;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setColor(Color aNewColor)
  {
    boolean wasSet = false;
    if (aNewColor != null)
    {
      color = aNewColor;
      wasSet = true;
    }
    return wasSet;
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
    color = null;
    cordinate = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "horizontal" + ":" + getHorizontal()+ "," +
            "isHighLighted" + ":" + getIsHighLighted()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color = "+(getColor()!=null?Integer.toHexString(System.identityHashCode(getColor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null");
  }
}