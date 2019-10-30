/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;

// line 9 "../../../../../QuoridorTransferObject.ump"
public class TOWall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Direction { Horizontal, Vertical }
  public enum Side { Up, Down, Left, Right }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOWall Attributes
  private int id;
  private int row;
  private int col;
  private Direction dir;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOWall(int aId, int aRow, int aCol, Direction aDir)
  {
    id = aId;
    row = aRow;
    col = aCol;
    dir = aDir;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
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

  public boolean setDir(Direction aDir)
  {
    boolean wasSet = false;
    dir = aDir;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
  }

  public Direction getDir()
  {
    return dir;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dir" + "=" + (getDir() != null ? !getDir().equals(this)  ? getDir().toString().replaceAll("  ","    ") : "this" : "null");
  }
}