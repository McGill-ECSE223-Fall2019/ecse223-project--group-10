/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4657.40ea68fec modeling language!*/



// line 24 "firstdraft.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean isHighLighted;
  private int playerWallStock;

  //Player Associations
  private Cordinate cordinate;
  private Destination destination;
  private Color color;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aIsHighLighted, int aPlayerWallStock, Cordinate aCordinate, Destination aDestination, Color aColor)
  {
    isHighLighted = aIsHighLighted;
    playerWallStock = aPlayerWallStock;
    if (!setCordinate(aCordinate))
    {
      throw new RuntimeException("Unable to create Player due to aCordinate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setDestination(aDestination))
    {
      throw new RuntimeException("Unable to create Player due to aDestination. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setColor(aColor))
    {
      throw new RuntimeException("Unable to create Player due to aColor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setPlayerWallStock(int aPlayerWallStock)
  {
    boolean wasSet = false;
    playerWallStock = aPlayerWallStock;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsHighLighted()
  {
    return isHighLighted;
  }

  public int getPlayerWallStock()
  {
    return playerWallStock;
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
  /* Code from template association_GetOne */
  public Destination getDestination()
  {
    return destination;
  }
  /* Code from template association_GetOne */
  public Color getColor()
  {
    return color;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDestination(Destination aNewDestination)
  {
    boolean wasSet = false;
    if (aNewDestination != null)
    {
      destination = aNewDestination;
      wasSet = true;
    }
    return wasSet;
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

  public void delete()
  {
    cordinate = null;
    destination = null;
    color = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isHighLighted" + ":" + getIsHighLighted()+ "," +
            "playerWallStock" + ":" + getPlayerWallStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cordinate = "+(getCordinate()!=null?Integer.toHexString(System.identityHashCode(getCordinate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "destination = "+(getDestination()!=null?Integer.toHexString(System.identityHashCode(getDestination())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "color = "+(getColor()!=null?Integer.toHexString(System.identityHashCode(getColor())):"null");
  }
}