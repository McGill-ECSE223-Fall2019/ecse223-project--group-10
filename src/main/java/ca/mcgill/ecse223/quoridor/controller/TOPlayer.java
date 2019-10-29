/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;

// line 15 "../../../../../QuoridorTransferObject.ump"
public class TOPlayer
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { White, Black }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOPlayer Attributes
  private int row;
  private int col;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOPlayer(int aRow, int aCol)
  {
    row = aRow;
    col = aCol;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "]";
  }
}