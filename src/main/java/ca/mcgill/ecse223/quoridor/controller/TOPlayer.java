/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;

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
  private Color color;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOPlayer(int aRow, int aCol, Color aColor)
  {
    row = aRow;
    col = aCol;
    color = aColor;
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

  public boolean setColor(Color aColor)
  {
    boolean wasSet = false;
    color = aColor;
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

  public Color getColor()
  {
    return color;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null");
  }
}