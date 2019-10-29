/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.*;

// line 9 "../../../../../QuoridorTransferObject.ump"
public class TOWall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Direction { Horizontal, Vertical }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, TOWall> towallsById = new HashMap<Integer, TOWall>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOWall Attributes
  private int id;
  private int row;
  private int col;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOWall(int aId, int aRow, int aCol)
  {
    row = aRow;
    col = aCol;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      towallsById.remove(anOldId);
    }
    towallsById.put(aId, this);
    return wasSet;
  }

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setCol(int aCol)
  {
    boolean wasSet = false;
    col = aCol;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static TOWall getWithId(int aId)
  {
    return towallsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
  }

  public void delete()
  {
    towallsById.remove(getId());
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "]";
  }
}