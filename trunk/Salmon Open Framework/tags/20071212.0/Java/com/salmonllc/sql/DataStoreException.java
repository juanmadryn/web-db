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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreException.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 5/01/03 11:30a $
/////////////////////////

/**
 * This Exception is thrown by methods in the DataStore / DataStore Buffer class when an error occurs.
 */
public class DataStoreException extends Exception {
    private String _SQLStatement = null;
    private int _row = -1;
    private int _buffer = 0;
    private String _column = null;
    private Throwable _rootCause;
    private int _dsNo;

    /**
     * @return  The root cause of the exception if there is one or null if not
     */

    public Throwable getRootCause() {
        return _rootCause;
    }


    /**
     * Constructs an Exception with no specified detail message.
     */
    public DataStoreException() {
        super();
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     */
    public DataStoreException(String s) {
        super(s);
    }

     /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     */
    public DataStoreException(String s, Throwable root) {
        super(s);
        _rootCause = root;
    }

     /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     */
    public DataStoreException(String s, Throwable root, int dsNo) {
        super(s);
        _rootCause = root;
        _dsNo=dsNo;
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     * @param SQLStatement The SQL Statement that caused the error.
     * @param row The dataStore row number that caused the error.
     * @param row The dataStore buffer that caused the error.
     */
    public DataStoreException(String s, String SQLStatement, int row, int buffer) {
        this(s);
        _SQLStatement = SQLStatement;
        _row = row;
        _buffer = buffer;
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     * @param row The dataStore row number that caused the error.
     * @param column The column that caused the error.
     */
    public DataStoreException(String s, int row, String column) {
        this(s);
        _row = row;
        _column = column;
    }

    /**
     * Returns the SQL Statement that caused the error.
     * @return int
     */
    public int getBuffer() {
        return _buffer;
    }

    /**
     * Returns the row number that caused the error.
     */
    public int getRow() {
        return _row;
    }

    /**
     * Returns the column that caused the error.
     */
    public String getColumn() {
        return _column;
    }

    /**
     * Returns the SQLStatement that caused the error.
     */
    public String getSqlStatement() {
        return _SQLStatement;
    }

    /**
     * For a proxy datastore updating multiple datastores, the one that caused the error
     */
    public int getDataStoreNo() {
        return _dsNo;
    }

    /**
     * Can be called by the code generating the exception to set the DataStore number that caused the error. For proxy datastores that do multiple updates
     */
    public void setDataStoreNo(int dsNo) {
        _dsNo = dsNo;
    }

    /**
     * Sets the row number causing the error
     */
    public void setRowNo(int rowNo) {
        _row = rowNo;
    }

     /**
     * Sets the column causing the error
     */
    public void setColumn(String column) {
        _column = column;
    }

}
