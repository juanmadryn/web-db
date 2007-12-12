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
package com.salmonllc.sql;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/SQLPreviewEvent.java $
//$Author: Srufle $ 
//$Revision: 9 $ 
//$Modtime: 4/15/03 1:56a $ 
/////////////////////////
 

/**
 * This object will be created and passed to every SQLPreview method in registered SQLPreviewListener objects.
 * @see  SQLPreviewListener
 */
 
public class SQLPreviewEvent extends java.awt.AWTEvent {
	DataStore _ds;
	int _buffer;
	int _row;
	String _statement;
	DBConnection _conn;
/**
 * Constructs a new PageEvent object.
 */
public SQLPreviewEvent(DataStore ds, int buffer, int row, String statement, DBConnection conn) {
	super(ds,0);
	_ds = ds;
	_buffer = buffer;
	_row = row;
	_statement = statement;
	_conn = conn;
	
}
/**
 * This method will return the DataStore buffer that the statement was generated from.
 */
public int getBuffer() {
	return _buffer;
}
/**
 * This method will return the DBConnection object that will be used to execute the statement.
 */
public DBConnection getConnection() {
	return _conn;
}
/**
 * This method will return the DataStore that the event was fired from.
 */
public DataStore getDataStore() {
	return _ds;
}
/**
 * This method will return the DataStore row that the statement was generated from.
 */
public int getRow() {
	return _row;
}
/**
 * This method will return the SQL statement was generated.
 */
public String getStatement() {
	return _statement;
}
}
