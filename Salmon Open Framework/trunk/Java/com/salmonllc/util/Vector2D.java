//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// 
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.util;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/util/Vector2D.java $
//$Author: Dan $ 
//$Revision: 15 $ 
//$Modtime: 10/30/02 2:59p $ 
/////////////////////////

import java.util.*;

/**
 * This class is a two dimensional Vector. It contains rows and columns that can expand as necessary.
 */
public class Vector2D implements java.io.Serializable
{
	Vector _rows = new Vector();
	int _columnCapacity = 5;
	int _columnCount;
	int _columnSize;
	int _rowSize;
/**
 * Constructs an empty 2 Dimensional vector.
 */
public Vector2D() {
	super();
}
/**
 * This method adds a specified number of columns to the vector
 * @param The Number of colums to add.
 */
public void addColumns(int noColumns)
{
	if ((noColumns + _columnCount) >= _columnCapacity)
	{
		_columnCapacity = noColumns + _columnCount + _columnCapacity;
	}
	for (int i = 0; i < _rows.size(); i++)
	{
		Object[] tgt = new Object[_columnCapacity];
		Object[] src = (Object[]) _rows.elementAt(i);
		System.arraycopy(src, 0, tgt, 0, _columnCount);
		_rows.setElementAt(tgt, i);
	}
	_columnCount += noColumns;
}
/**
 * This method adds a specified number of rows to the vector
 * @param The Number of rows to add.
 */

public void addRows(int noRows)
{
	for (int i = 0; i < noRows; i++)
	{
		Object[] o = new Object[_columnCapacity];
		_rows.addElement(o);
	}
}
/**
 * This method returns the object at row and column..
 */
public Object elementAt(int index) {
	if (index < 0 || index >= size()) 
		return null;

	int row = index / _columnCount;
	int column = index % _columnCount;
	
	Object[] o = (Object[]) _rows.elementAt(row);
	return o[column];
		
}
/**
 * This method returns the object at row and column..
 */
public Object elementAt(int row, int column) {
	if (row < 0 || row >= _rows.size()) 
		return null;
	else if (column	 < 0 || column >= _columnCount)
		return null;

	Object[] o = (Object[]) _rows.elementAt(row);
	return o[column];
		
}
public void exportData(boolean includeHeaders, java.io.PrintWriter p)
{
	StringBuffer work = new StringBuffer();
	Object data = null;
	//
	work.append("<TABLE BORDER = \"1\">");
	// include headers
	if (includeHeaders)
	{
		work.append("<TR>");
		work.append("<TH>");
		work.append("ROW_NUM");
		work.append("</TH>");
		for (int i = 0; i < getColumnCount(); i++)
		{
			work.append("<TH>");
			work.append("COL_" + i);
			work.append("</TH>");
		}
		work.append("</TR>\n");
	}
	for (int rows = 0; rows < getRowCount(); rows++)
	{
		work.append("<TR>");
		work.append("<TD>ROW_" + rows);
		work.append("</TD>");
		for (int cols = 0; cols < getColumnCount(); cols++)
		{
			work.append("<TD>");
			data = elementAt(rows, cols);
			if (data == null)
			{
				work.append("&nbsp;");
			}
			else
			{
				work.append(data.toString());
			}
			work.append("</TD>");
		}
		work.append("</TR>\n");
	}
	work.append("</TABLE>");
	p.println(work.toString());
	p.flush();
}
/**
 * This method returns the number of columns in the 2D Vector
 */
public int getColumnCount() {
	return _columnCount;
}
/**
 * This method returns the number of columns that are occupied with data in the 2D Vector
 */
public int getColumnSize()
{
	return _columnSize + 1;
}
/**
 * This method returns the number of columns in the 2DVector
 */
public int getRowCount() {
	return _rows.size();
}
/**
 * This method returns the number of rows that are occupied with data in the 2D Vector
 */
public int getRowSize()
{
	return _rowSize + 1;
}
/**
 * This method returns the index of the item at row and column or -1 if the element doesn't exist.
 */
public int indexAt(int row, int column) {
	if (row < 0 || row >= _rows.size()) 
		return -1;
	else if (column	 < 0 || column >= _columnCount)
		return -1;

	return (row * _columnCount)	+ column;
	
}
/**
 * This method inserts a row immediately before the specified row
 */
public void insertRow(int row) {
	if (row < 0)
		return;

	Object[] o = new Object[_columnCapacity];

	if (row > _rows.size())
		_rows.addElement(o);
	else
		_rows.insertElementAt(o,row);

}
/**
 * This method inserts a row immediately before the specified row
 */
public void removeAll()
{
	_rows.removeAllElements();
	_columnCapacity = 5;
	_columnCount = 0;
	_columnSize = 0;
	_rowSize = 0;
}
/**
 * This method removes a row from the 2DVector
 * @param row The row to remove.
 */
public void removeRow(int row)
{
	if (row < 0 || row > _rows.size())
		return;
	_rows.removeElementAt(row);
	_rowSize = _rowSize - 1;
}
/**
 * This method sets the value of a cell in the 2D Vector
 * @param row The row position
 * @param column The row position
 * @param in The object to set.
 */
public void setElementAt(int row, int column, Object in)
{
	if (row < 0 || row >= _rows.size())
		return;
	else
		if (column < 0 || column >= _columnCount)
			return;
	Object[] o = (Object[]) _rows.elementAt(row);
	o[column] = in;

	// this is to get only the occupied rows and columns
	if (_rowSize < row)
		_rowSize = row;
	if (_columnSize < column)
		_columnSize = column;
}
/**
 * This method sets the value of a cell in the 2D Vector
 * @param in The object to set.
 * @param index The position to put it at
 */
public void setElementAt(int index,Object in){

	if (index < 0 || index >= size()) 
		return;

	int row = index / _columnCount;
	int column = index % _columnCount;
	
	Object[] o = (Object[]) _rows.elementAt(row);
	o[column] = in;

	// this is to get only the occupied rows and columns
	if (_rowSize < row)
		_rowSize = row;
	if (_columnSize < column)
		_columnSize = column;

}
/**
 * This method was created in VisualAge.
 * @return int
 */
public int size() {
	return (_rows.size() * _columnCount);
}
/**
 * Returns a string representation of the 2D vector.
 */
public String toString()
{

	StringBuffer work = new StringBuffer();
	Object data = null;
	boolean commaFlag = false;
	for (int rows = 0; rows < getRowCount(); rows++)
	{
		work.append("[");
		for (int cols = 0; cols < getColumnCount(); cols++)
		{
			if (commaFlag == true)
			{
				work.append(",");
			}

			data = elementAt(rows, cols);
			if (data == null)
			{
				work.append("NULL");
			}
			else
			{
				work.append(data.toString());
			}
			commaFlag = true;
		}
		work.append("]\n");
		commaFlag = false;
	}
	return work.toString();
}
}
