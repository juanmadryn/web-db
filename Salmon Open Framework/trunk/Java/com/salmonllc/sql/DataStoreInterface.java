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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreInterface.java $
//$Author: Dan $ 
//$Revision: 11 $ 
//$Modtime: 11/01/04 4:21p $ 
/////////////////////////

import java.sql.*;
import java.util.*;

/**
 * This interface is implemented by both the DataStore and DataStoreProxy and is useful for components that need to retrieve data but need to user the DataStore and DataStoreProxy interchangably
 */
public interface DataStoreInterface  extends DataStoreQBEInterface {
/**
 * This method adds a bucket to the DataStoreBuffer. The bucket will be filled in with nulls when the datastore is retrieved.
 * @param internalname The name of the bucket to add to the datastore.
 * @param type The type of the column to add to the datastore. This must be one of the "TYPE" constants in the class.
 */
public void addBucket(String internalname, int type);
/**
 * This method indicates whether all the data in the result set that is to be returned by the last retrieve statement has in fact been retrieved.
 * @return true if all the data has been retrieved and false if the retrieve is still in progress.
 */
public boolean allDataRetrieved();
/**
 * This method was created in VisualAge.
 */
public void cancelRetrieve();
/**
 * This method will compare the contents of two rows using the specified column list. It will return < 0 if row1 is less then row2, 0 if they are equal and > 0 if row1 is greater than row2
 * @param row1 the first row to check
 * @param row2 the next row to check
 * @param cols An array of column numbers to use for the comparison
 */
public int compareRows(int row1, int row2, int[] cols) ;
/**
 * This method will compare the contents of two rows using the specified column list. It will return < 0 if row1 is less then row2, 0 if they are equal and > 0 if row1 is greater than row2
 * @param row1 the first row to check
 * @param row2 the next row to check
 * @param cols An array of column names to use for the comparison
 */
public int compareRows(int row1, int row2, String[] cols) ;
/**
 * This deletes the current row in the DataStoreBuffer. This will generate a delete statment that will remove the row from the database when the update method is called.
 * @return true if the row is deleted and false if not.
 */
public boolean deleteRow() ;
/**
 * This deletes the row in the DataStoreBuffer. This will generate a delete statment that will remove the row from the database when the update method is called.
 * @param row The number of the row to delete.
 * @return True if the row is deleted and false if not.
 */
public boolean deleteRow(int row);
/**
 * This method will destroy the remote data store on the data server. All resources on the server will be reclaimed.
 */
public void destroy() throws Exception;
/**
 * Use this method to get the amount of rows that will be retrieved when a data store retrieve is executed. 
 */
public int estimateRowsRetrieved() throws Exception ;
/**
 * Use this method to get the amount of rows that will be retrieved when a data store retrieve is executed. 
 * @param criteria The selection criteria to use for the select.	
 */
public int estimateRowsRetrieved(String criteria) throws Exception;
/**
 * Exports the rows of the data store in the format specified.
 * @param format: Can be EXPORT_XML, EXPORT_HTML or EXPORT_TAB_DELIMITED;
 * @param includeHeaders: True if the output should contain column headings.
 * @param p: The PrintWriter to send the output to.
 */
public void export(int format, boolean includeHeaders, java.io.PrintWriter p);
/**
 * This method will remove all rows from the buffer that don't match the criteria and place them in the datastore's filter buffer.
 * The method is passed a String containing the filter expression that will be used. 
 * Examples:<BR>
 *		  <pre>
 *        ds.filter("table1.column1 == 'xxxx'); <BR>
 * 		  ds.filter("table1.column1.substring(2,3) == table1.column2");<BR>
 * 		  ds.filter("table1.column1.substring(2,3) == table1.column2 || table1.column3.startsWith('xxx')");<BR><BR>
 *		  </pre>
 * @param filter The criteria to filter on. Enter a value of null to clear the filter.
 * @see DataStoreEvaluator
 */

public void filter(String filter) throws DataStoreException ;
/**
 * This method finds the next row in the result set that matches the criteria entered and is within the start and end range.
 * @start The first row in the DataStoreBuffer to search.
 * @end The last row in the DataStoreBuffer to search (Make start > end to search backwards).
 * @return Returns the first row matching the criteria within the range or -1 if none are found.
 * @see DataStore#setFindExpression
 */
public int find(int start,int end) throws DataStoreException ;
/**
 * This method finds the firs row in the result set that matches the criteria entered and makes it the current row.
 * @return Returns false if no rows are found that match the criteria.
 * @see DataStoreBuffer#setFindExpression
 */
public boolean findFirst() throws DataStoreException ;
/**
 * This method finds the last row in the result set that matches the criteria entered and makes it the current row.
 * @return Returns false if no rows are found that match the criteria.
 * @see DataStoreBuffer#setFindExpression
 */
public  boolean findLast() throws DataStoreException ;
/**
 * This method finds the next row in the result set that matches the criteria entered and makes it the current row.
 * @return Returns false if no rows are found that match the criteria.
 * @see DataStoreBuffer#setFindExpression
 */
public boolean findNext() throws DataStoreException ;
/**
 * This method finds the next row in the result set that matches the criteria entered and makes it the current row.
 * @return Returns false if no rows are found that match the criteria.
 * @see DataStoreBuffer#setFindExpression
 */
public boolean findPrior() throws DataStoreException;

/**
 * This method return a Object value from the current row of the data store buffer.
 * @return The Object value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public Object getAny(int column) throws DataStoreException;
/**
 * This method return a Object value from the data store buffer.
 * @return The Object value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public Object getAny(int row, int column) throws DataStoreException;
/**
 * This method return a Object value from the data store buffer.
 * @return The Object value
 * @param row The row number in the data store buffer.
 * @param column The column name (in the form table.column) in the data store buffer.
 */
public Object getAny(int row, String column) throws DataStoreException;
/**
 * This method return a Object value from the current row of the data store buffer.
 * @return The Object value
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public Object getAny(String column) throws DataStoreException;
/**
 * This method return a byte array value from the current row in the data store buffer.
 * @return The byte array
 * @param column The column number in the data store buffer.
 */
public byte[] getByteArray(int column) throws DataStoreException;
/**
 * This method return a byte array value from the data store buffer.
 * @return The byte array
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public byte[] getByteArray(int row, int column) throws DataStoreException;
/**
 * This method return a byte array value from the data store buffer.
 * @return The byte array
 * @param row The row number in the data store buffer.
 * @param column The column name (in the form table.column) in the data store buffer.
 */
public byte[] getByteArray(int row, String column) throws DataStoreException;
/**
 * This method return a byte array value from the current row in the data store buffer.
 * @return The byte array
 * @param row The row number in the data store buffer.
 * @param column The column name (in the form table.column) in the data store buffer.
 */
public byte[] getByteArray(String column) throws DataStoreException;
/**
 * Use this method to get whether or not the datastore will do a concurrency check when rows are updated and deleted.
 */
public boolean getCheckConcurrency();
/**
 * This method returns the number of columns in the datastore.
 */
public int getColumnCount() ;
/**
 * This method returns the Data Type for a particular column.
 * @param column The column number in the data store buffer.
 */
 
public int getColumnDataType(int column) throws DataStoreException ;
/**
 * This method returns the Data Type for a particular column.
 * @param column The column name in the data store buffer.
 */
 
public int getColumnDataType(String column) throws DataStoreException ;

/**
 * This method returns a list with the names of all the columns in the data store.
 */
public String[] getColumnList() ;
/**
 * This method returns the name of the column in the data store given its index.
 */
public String getColumnName(int col) throws DataStoreException ;
/**
 * This method is used to get whether a column should be used in the update, delete concurrency check.
 */
public boolean getConcurrencyCheckColumn(int col) throws DataStoreException ;
/**
 * This method is used to get whether a column should be used in the update, delete concurrency check.
 */
public boolean getConcurrencyCheckColumn(String col) throws DataStoreException ;
/**
 * This method is used to get selection criteria filtering for the result set of the datastore.
 */
public String getCriteria() ;
/**
 * This method gets the specified row from the DataStore.
 * @param rowNo The number of the row to get.
 * @param buffer The datastore buffer to look in. Valid values are BUFFER_STANDARD, BUFFER_DELETED, BUFFER_FILTERED.
 */
public DataStoreRow getDataStoreRow(int rowNo, int buffer) throws DataStoreException;
/**
 * This method return a date value from current row of the data store buffer.
 * @return The DateTime Value
  * @param column The column number in the data store buffer.
 */
public java.sql.Date getDate(int column) throws DataStoreException;
/**
 * This method return a date value from the data store buffer.
 * @return The DateTime Value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public java.sql.Date getDate(int row, int column) throws DataStoreException;
/**
 * This method return a date value from the data store buffer.
 * @return The DateTime Value
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public java.sql.Date getDate(int row, String column) throws DataStoreException;
/**
 * This method return a date value from current row of the data store buffer.
 * @return The DateTime Value
  * @param column The column name in the data store buffer.
 */
public java.sql.Date getDate(String column) throws DataStoreException;
/**
 * This method return a date value from current row of the data store buffer.
 * @return The DateTime Value
  * @param column The column number in the data store buffer.
 */
public Timestamp getDateTime(int column) throws DataStoreException;
/**
 * This method return a date value from the data store buffer.
 * @return The DateTime Value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public Timestamp getDateTime(int row, int column) throws DataStoreException;
/**
 * This method return a date value from the data store buffer.
 * @return The DateTime Value
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public Timestamp getDateTime(int row, String column) throws DataStoreException;
/**
 * This method return a datetime value from current row of the data store buffer.
 * @return The DateTime Value
  * @param column The column name in the data store buffer.
 */
public java.sql.Timestamp getDateTime(String column) throws DataStoreException;
/**
 * This method returns the default table for the datastore
 * @param table The default table for this DataStore
 */
public String getDefaultTable() ;
/**
 * This method returns the current number of rows that will be deleted when the datastores update method is called.
 * @return int
 */
public int getDeletedCount() ;
/**
 * This method will return whether the distinct flag in the data store is set. The flag indicates that the distinct keyword should be placed at the beginning of a select statement.
 * @uml.property  name="distinct"
 */
public boolean getDistinct() ;
/**
 * This method return a double value from the current row in data store buffer.
 * @return The double value.
 * @param column The column number in the in the data store buffer.
 */
public double getDouble(int column) throws DataStoreException;
/**
 * This method return a double value from the data store buffer.
 * @return The double value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the in the data store buffer.
 */
public double getDouble(int row,int column) throws DataStoreException;
/**
 * This method return a double value from the data store buffer.
 * @return The double value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the form table.column in the data store buffer
 */
public double getDouble(int row,String column) throws DataStoreException;
/**
 * This method return a double value from the current row in data store buffer.
 * @return The double value.
 * @param column The column name in the form table.column in the data store buffer
 */
public double getDouble(String column) throws DataStoreException;
/**
 * This method returns the current number of rows in the DataStores filter buffer. 
 * @return the number of rows in the filter buffer.
 */
public int getFilteredCount() ;
/**
 * This method return a float value from the current row in the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public float getFloat(int column) throws DataStoreException;
/**
 * This method return a float value from the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public float getFloat(int row,int column) throws DataStoreException;
/**
 * This method return a float value from the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public float getFloat(int row,String column) throws DataStoreException;
/**
 * This method return a float value from the current row in the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public float getFloat(String column) throws DataStoreException;
/**
 * This method returns a the format string for a date, time, datetime or numeric type column.
 * @param col The column number to get the format for.
 * @see DataStore#getFormattedString
 * @see DataStore#setFormattedString
 */
public String getFormat(int col) throws DataStoreException;
/**
 * This method returns a the format string for a date, time, datetime or numeric type column.
 * @param col The column name to get the format for.
 * @see DataStore#getFormattedString
 * @see DataStore#setFormattedString
 */
public String getFormat(String col) throws DataStoreException ;
/**
 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
 * @see DataStore#setFormat
 */	
public String getFormattedString(int column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
 * @see DataStore#setFormat
 */	
public String getFormattedString(int row, int column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
 * @see DataStore#setFormat
 */	
public String getFormattedString(int row, String column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
 * @see DataStore#setFormat
 */	
public String getFormattedString(String column) throws DataStoreException;
/**
 * This method returns the column list in the group by clause.
 */
public String getGroupBy();
/**
 * This method returns the having clause for the datastore.
 */
public String getHaving() ;
/**
 * This method return a integer value from the current row in the data store buffer.
 * @return The integer value.
 * @param column The column number in the data store buffer
 */
public int getInt(int column) throws DataStoreException;
/**
 * This method return a integer value from the data store buffer.
 * @return The integer value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer
 */
public int getInt(int row,int column) throws DataStoreException;
/**
 * This method return a integer value from the data store buffer.
 * @return The integer value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the form table.column in the data store buffer
 */
public int getInt(int row,String column) throws DataStoreException;
/**
 * This method return a integer value from the current row in data store buffer.
 * @return The integer value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the form table.column in the data store buffer
 */
public int getInt(String column) throws DataStoreException;
/**
 * This method returns the number of columns in a particular join.
 */
public int getJoinColumnCount(int joinNo) ;
/**
 * This method returns the number of joins in the datastore.
 */
public int getJoinCount() ;
/**
 * This method returns a column on the left side of the join.
 */
public String getJoinLeftColumn(int joinNo, int colNo);
/**
 * This method returns the true if a particular join is outer.
 */
public boolean getJoinOuter(int joinNo);
//fc 06/11/04: Added this method signature to the interface to get join relationship type.
/**
 * This method returns the relation type of the join. (RELATION_ONE_TO_ONE, RELATION_ONE_TO_MANY, RELATION_MANY_TO_ONE)
 */
public int getJoinRelationType(int joinNo);
/**
 * This method returns a column on the right side of the join.
 */
public String getJoinRightColumn(int joinNo, int colNo);
/**
 * This method return a long value from the current row in the data store buffer.
 * @return The long value.
 * @param column The column number in the data store buffer.
 */
public long getLong(int column) throws DataStoreException;
/**
 * This method return a long value from the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public long getLong(int row,int column) throws DataStoreException;
/**
 * This method return a long value from the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public long getLong(int row,String column) throws DataStoreException;
/**
 * This method return a long value from the data store buffer.
 * @return The long value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public long getLong(String column) throws DataStoreException;
/**
 * This method will return the maximum number of rows that the datastore will retrieve. If the max is set to -1, the datastore will retrieve all rows in the result set. Otherwise it will stop retrieving when the max is reached.
 */
public int getMaxRows() ;
/**
 * This method returns the order by clause for the datastore.
 * @uml.property  name="orderBy"
 */
public String getOrderBy() ;
/**
 * This method creates a properties object containing the definition of the data store.
 * @uml.property  name="properties"
 */
public Properties getProperties() ;
/**
 * Used to get the remote id for the datastore
 * @uml.property  name="remoteID"
 */
public String getRemoteID() ;
/**
 * This method returns the current row in the result set.
 * @return The current row in the result set or -1 if the result set is empty.
 */
public int getRow() ;
/**
 * This method returns the current number of rows in the DataStores data buffer. If a retrieve is in progress this value will change as the rows are retrieved.
 * @return the number of rows in the buffer.
 */
public int getRowCount() ;
/**
 * This method returns the status flag of the current row.
 * @return  -1 if the row is not in the buffer or the status if it is (Valid values: STATUS_NOT_MODIFIED, STATUS_MODIFIED, STATUS_NEW, STATUS_NEW_MODIFIED).
 * @uml.property  name="rowStatus"
 */
public int getRowStatus() ;
/**
 * This method returns the status flag of the specified row.
 * @param row The row in the datastore buffer.
 * @return -1 if the row is not in the buffer or the status if it is. (Valid values: STATUS_NOT_MODIFIED, STATUS_MODIFIED, STATUS_NEW, STATUS_NEW_MODIFIED).
 */
public int getRowStatus(int row) ;
/**
 * This method return a short value from the current row in the data store buffer.
 * @return The short value.
 * @param column The column number in the data store buffer.
 */
public short getShort(int column) throws DataStoreException;
/**
 * This method return a short value from the data store buffer.
 * @return The short value.
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public short getShort(int row,int column) throws DataStoreException;
/**
 * This method return a short value from the data store buffer.
 * @return The short value.
 * @param row The row number in the data store buffer.
 * @param column The column name in the form table.column in the data store buffer
 */
public short getShort(int row,String column) throws DataStoreException;
/**
 * This method return a short value from the current row in the data store buffer.
 * @return The short value.
 * @param column The column name in the form table.column in the data store buffer
 */
public short getShort(String column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer.
 * @return The String Value
 * @param row The row number in the data store buffer
 * @param column The column number in the data store buffer
 */
public String getString(int column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer.
 * @return The String Value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public String getString(int row, int column) throws DataStoreException;
/**
 * This method return a String value from the data store buffer.
 * @return The String value
 * @param row The row number in the data store buffer.
 * @param column The column name (in the form table.column) in the data store buffer.
 */
public String getString(int row, String column) throws DataStoreException;
/**
 * This method return a string value from the data store buffer.
 * @return The String Value
 * @param row The row number in the data store buffer.
 * @param column The column name (in the form table.column) in the data store buffer.
 */
public String getString(String column) throws DataStoreException;

/**
 * This method returns an array of all the tables referenced in the datastore.
 * @param updateable True if the table list should only include updateable tables and false if it should include all.
 */
public String [] getTableList(boolean updateable) ;
/**
 * This method return a time value from current row of the data store buffer.
 * @return The time Value
  * @param column The column number in the data store buffer.
 */
public java.sql.Time getTime(int column) throws DataStoreException;
/**
 * This method return a time value from the data store buffer.
 * @return The time Value
 * @param row The row number in the data store buffer.
 * @param column The column number in the data store buffer.
 */
public java.sql.Time getTime(int row, int column) throws DataStoreException;
/**
 * This method return a time value from the data store buffer.
 * @return The Time Value
 * @param row The row number in the data store buffer.
 * @param column The column name in the data store buffer.
 */
public java.sql.Time getTime(int row, String column) throws DataStoreException;
/**
 * This method return a time value from current row of the data store buffer.
 * @return The Time Value
  * @param column The column name in the data store buffer.
 */
public java.sql.Time getTime(String column) throws DataStoreException;
/**
 * Use this method to get whether the DataStore will trim (remove trailing spaces) from columns retrieved from the database;
 */
public boolean getTrimStrings() ;
/**
 * Gets the update method for the datastore. 
 * @see DataStore#setUpdateMethod
 */
public int getUpdateMethod() ;
/**
 * This method is used to get whether a column should use bind variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and BIND_DEFAULT (Use default for datastore)
 */
public int getUseBindColumn(int col) throws DataStoreException ;
/**
 * This method is used to get whether a column should use bind variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and BIND_DEFAULT (Use default for datastore)
 */
public int getUseBindColumn(String col) throws DataStoreException ;
/**
 * Use this method to get whether or not the datastore will use bind variables as the default for updating or inserting columns.
 */
public boolean getUseBindForUpdate() ;
/**
 * This method makes the current row the first one in the result set.
 * @return Returns fals if the result set is empty.
 */
public boolean gotoFirst();
/**
 * This method makes the current row the last one in the result set.
 * @return Returns fals if the result set is empty.
 */
public boolean gotoLast();
/**
 * This method increments the current row in the result set.
 * @return Returns false if the result set is empty or the current row is the last.
 */
public boolean gotoNext();
/**
 * This method decrements the current row in the result set.
 * @return Returns false if the result set is empty or the current row is the first.
 */
public boolean gotoPrior();
/**
 * This method makes a specific row the current one in the result set.
 * @return Returns false if the result set is empty or the row passed is beyond the bounds of the result set.
 * @param row The row in the result set to make current.	
 */
public boolean gotoRow(int row);
/**
 * Inserts a blank row at the end of the DataStoreBuffer. Returns the number of the row added.
 */
public int insertRow() ;
/**
�* Inserts a blank row at the specified position of the DataStore's result set buffer. Returns the number of the row added.
�*/
public int insertRow(int atPosition) ;
/**
 * This method will return true if the passed string value if a valid entry for the column based on the String format specified for the column via the setFormat method.
 * @see DataStoreBuffer#setFormat
 */
public boolean isFormattedStringValid(int column, String value) throws DataStoreException;
/**
 * This method will return true if the passed string value if a valid entry for the column based on the String format specified for the column via the setFormat method.
 * @see DataStoreBuffer#setFormat
 */
public boolean isFormattedStringValid(String column, String value) throws DataStoreException;
/**
 * This method returns whether a column is part of the primary key
 */
public boolean isPrimaryKey(int col) throws DataStoreException ;
/**
 * This method returns whether a column is part of the primary key
 */
public boolean isPrimaryKey(String col) throws DataStoreException ;
/**
 * This method returns whether a column is updateable
 */
public boolean isUpdateable(int col) throws DataStoreException ;
/**
 * This method returns whether a column is updateable
 */
public boolean isUpdateable(String col) throws DataStoreException ;
/**
 * This method will ping the server for this particular data store. Pinging from time to time will prevent the server session from expiring.
 * @return true if the ping succeeds and false if not.
 */
public boolean ping() throws Exception ;
/**
 * This removes the current row from the DataStore buffer. This will not generate a delete statment that will remove the row from the database when the update method is called.
 * @return True if the row is remove and false if not.
 */
public boolean removeRow() ;
/**
 * This removes the row from the DataStore buffer. This will not generate a delete statment that will remove the row from the database when the update method is called.
 * @param row The number of the row to remove.
 * @return True if the row is remove and false if not.
 */
public boolean removeRow(int row);
/**
 * This method will clear all rows in the dataStore.
 */
public void reset() ;
/**
 * This method will clear the row status flags and clear the deleted buffer in the DataStoreBuffer. 
 */
public void resetStatus() ;
/**
 * Executes the sql statement and retrieves to data. The data is retrieved in a new thread so the beginning of the result set can be accessed before all the data has been retrieved.
 * You do not need to pass a database connection to this version of retrieve, but in order to use it the DataStore must be created with a constructor that passes an application (not the no args constructor).
 */
public void retrieve() throws java.sql.SQLException, DataStoreException  ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setAny(int row,int column, Object value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setAny(int column, Object value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setAny(int row, String column, Object value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setAny(String column, Object value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setByteArray(int column, byte[] value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setByteArray(int row,int column, byte[] value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setByteArray(int row, String column, byte[] value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setByteArray(String column, byte[] value) throws DataStoreException ;
/**
 * This method sets the specified row in the DataStore. The DataStoreRow must be compatible with the internal structure of the datastore (eg created with the getDataStoreRow method from the same datastore). To create a blank row in the datastore call the insertRow method and then getDataStoreRow.
 * @param rowNo The number of the row to get.
 * @param buffer The datastore buffer to look in. Valid values are BUFFER_STANDARD, BUFFER_DELETED, BUFFER_FILTERED.
 * @param row The row to set
 */
public void setDataStoreRow(int rowNo, int buffer, DataStoreRow row) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDate(int row,int column, java.sql.Date value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDate(int row, String column, java.sql.Date value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDate(int column, java.sql.Date value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setDate(String column, java.sql.Date value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDateTime(int row,int column, java.sql.Timestamp value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDateTime(int row, String column, java.sql.Timestamp value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDateTime(int column, java.sql.Timestamp value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setDateTime(String column, java.sql.Timestamp value) throws DataStoreException;
/**
 * This method will set the distinct flag in the data store. 
 * @param distinct  if the flag is set to true the generated select statement will begin with a select distinct.
 * @uml.property  name="distinct"
 */
public void setDistinct(boolean distinct) ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDouble(int column, double value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDouble(int row,int column, double value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDouble(int row, String column, double value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setDouble(String column, double value) throws DataStoreException ;
/**
 * This method will set the expression used to find rows in the datastore. It is used in conjunction with the findNext, findPrior, findFirst, findLast and find methods.
 * @param exp The expression to use in the find.
 * @see DataStore#findNext
 * @see DataStore#findPrior
 * @see DataStore#findFirst
 * @see DataStore#findLast
 * @see DataStore#find
 * @see DataStore#filter
 * @see DataStoreEvaluator
 */
public void setFindExpression(String exp) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setFloat(int column, float value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setFloat(int row,int column, float value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setFloat(int row, String column, float value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setFloat(String column, float value) throws DataStoreException ;
/**
 * This method sets a the format string for a date, time, datetime or numeric type column.
 * @param col The column number to set the format for.
 * @param format The format to use in parsing or formatting the value
 * @see DataStore#getFormattedString
 * @see DataStore#setFormattedString
 */
public void setFormat(int col, String format) throws DataStoreException ;
/**
 * This method sets a the format string for a date, time, datetime or numeric type column.<BR><BR>
 * Date Formats<BR><BR>
 *  Symbol   Meaning                 Presentation        Example<BR>
 *  ------   -------                 ------------        -------<BR>
 *  G        era designator          (Text)              AD<BR>
 *  y        year                    (Number)            1996<BR>
 *  M        month in year           (Text & Number)     July & 07<BR>
 *  d        day in month            (Number)            10<BR>
 *  h        hour in am/pm (1~12)    (Number)            12<BR>
 *  H        hour in day (0~23)      (Number)            0<BR>
 *  m        minute in hour          (Number)            30<BR>
 *  s        second in minute        (Number)            55<BR>
 *  S        millisecond             (Number)            978<BR>
 *  E        day in week             (Text)              Tuesday<BR>
 *  D        day in year             (Number)            189<BR>
 *  F        day of week in month    (Number)            2 (2nd Wed in July)<BR>
 *  w        week in year            (Number)            27<BR>
 *  W        week in month           (Number)            2<BR>
 *  a        am/pm marker            (Text)              PM<BR>
 *  k        hour in day (1~24)      (Number)            24<BR>
 *  K        hour in am/pm (0~11)    (Number)            0<BR>
 *  z        time zone               (Text)              Pacific Standard Time<BR>
 *  '        escape for text         (Delimiter)<BR>
 *  ''       single quote            (Literal)           '<BR><BR>
 * Numeric Formats<BR><BR>
 *  Symbol Meaning<BR>
 *  ------ -------<BR>
 *  0      a digit<BR>
 *  #      a digit, zero shows as absent<BR>
 *  .      placeholder for decimal separator<BR>
 *  ,      placeholder for grouping separator.<BR>
 *  ;      separates formats.<BR>
 *  -      default negative prefix.<BR>
 *  %      multiply by 100 and show as percentage<BR>
 *  ?      multiply by 1000 and show as per mille<BR>
 *  �      currency sign; replaced by currency symbol; if<BR>
 *         doubled, replaced by international currency symbol.<BR>
 *         If present in a pattern, the monetary decimal separator<BR>
 *         is used instead of the decimal separator.<BR>
 *  X      any other characters can be used in the prefix or suffix<BR>
 *  '      used to quote special characters in a prefix or suffix.<BR>
 * @param col The column name to set the format for.
 * @param format The format to use in parsing or formatting the value
 * @see DataStore#getFormattedString
 * @see DataStore#setFormattedString
 */
public void setFormat(String col, String format) throws DataStoreException ;
/**
 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
 * @see DataStore#setFormat
 */
public void setFormattedString(int row, int column, String value) throws DataStoreException;
/**
 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
 * @see DataStore#setFormat
 */
public void setFormattedString(int column, String value) throws DataStoreException;
/**
 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
 * @see DataStore#setFormat
 */
public void setFormattedString(int row, String column, String value) throws DataStoreException;
/**
 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
 * @see DataStore#setFormat
 */
public void setFormattedString(String column, String value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setInt(int row,int column, int value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setInt(int row, String column, int value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setInt(String column, int value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setLong(int row,int column, long value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setLong(int column, long value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setLong(int row,String column, long value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setLong(String column, long value) throws DataStoreException ;
/**
 * Sets the order by clause of the DataStore's SQL Statement
 * @param orderBy  The columns to use to sort the result set.
 * @uml.property  name="orderBy"
 */
public void setOrderBy(String orderBy) ;
/**
 * This method is used to indicate whether a column is part of the primary key
 */
public void setPrimaryKey(int col, boolean pkey) throws DataStoreException;
/**
 * This method is used to indicate whether a column is part of the primary key
 */
public void setPrimaryKey(String col, boolean pkey) throws DataStoreException;
/**
 * This method builds the datastore from the information in the properties object.
 * @uml.property  name="properties"
 */
public void setProperties(Properties p);
/**
 * Used to set the remote id for the datastore
 * @uml.property  name="remoteID"
 */
public void setRemoteID(String remoteID) ;
/**
 * This method sets the status flag of the current row.
 * @param status  (STATUS_NOT_MODIFIED,STATUS_MODIFIED,STATUS_NEW,STATUS_NEW_MODIFIED)
 * @return  True if the row is in the buffer and false if not
 * @uml.property  name="rowStatus"
 */
public boolean setRowStatus(int status) ;
/**
 * This method sets the status flag of the specified row.
 * @param row The row in the datastore buffer.
 * @param status (STATUS_NOT_MODIFIED,STATUS_MODIFIED,STATUS_NEW,STATUS_NEW_MODIFIED)
 * @return True if the row is in the buffer and false if not
 */
public boolean setRowStatus(int row,int status) ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setShort(int row,int column, short value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setShort(int row,String column, short value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setShort(int column, short value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setShort(String column, short value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setString(int row,int column, String value) throws DataStoreException;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setString(int column, String value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setString(int row, String column, String value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setString(String column, String value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setTime(int row,int column, java.sql.Time value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setTime(int row, String column, java.sql.Time value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column.
 */
public void setTime(int column, java.sql.Time value) throws DataStoreException ;
/**
 * This method sets a value in the data store's internal buffer at the current row.
 * @param row The row number for the value to set.
 * @param column The column for the value to set.
 * @param value The data to place in the column
 */
public void setTime(String column, java.sql.Time value) throws DataStoreException ;
/**
 * This method will sort the rows in the data store on an array of columns.
 * @param col[] A array of column numbers to sort on. 
 * @param dir[] An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
 */
public void sort(int[] col, int dir[]) throws DataStoreException ;
/**
 * This method will sort the rows in the data store on an array of columns.
 * @param obj[] A array of objects to sort on. The array can contain Strings (column name), Integers (column numbers) or DataStoreEvaluators (expressions)
 * @param dir[] An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
 */
public void sort(Object[] obj, int dir[]) throws DataStoreException ;
/**
 * This method will sort the rows in the data store on an array of columns.
 * @param col[] A String array of column names to sort on.
 * @param dir[] An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
 */
public void sort(String col[], int dir[]) throws DataStoreException  ;
/**
 * This method will sort the rows in the data store on a particular column.
 * @param col The column number of the column to sort on.
 * @param dir The direction to sort on. Either SORT_ASC or SORT_DESC.
 */
public void sort(int col, int dir) throws DataStoreException ;
/**
 * This method will sort the rows in the data store using an expression.
 * @param eval A datastore evaluator containing the expression.
 * @param dir An integer indicating the sort direction valid values are SORT_ASC or SORT_DESC.
 */
public void sort(DataStoreEvaluator eval, int dir) throws DataStoreException ;
/**
 * This method will sort the rows in the data store on a particular column.
 * @param col The name of the column to sort on.
 * @param dir The direction to sort on. Either SORT_ASC or SORT_DESC.
 */
public void sort(String col, int dir) throws DataStoreException ;
/**
 * This method will take a row from the datastores deleted buffer and move it back to the standard buffer.
 * @param row The number of the row to undelete. Note: this is the row number of the row in the deleted buffer not the standard buffer.
 * @return The number that the deleted row was moved to in the standard buffer or -1 if an error occurs.
 */
public int unDeleteRow(int row) ;
/**
 * This method will cause the database to reflect the changes made in the data store's buffer.
 * @exception com.salmonllc.sql.DataStoreException If a SQLError occurs while the datastore is updating.
 */
public void update()  throws DataStoreException, java.sql.SQLException;
/**
 * This method will block until a datastore cancel command is finished
 */
public void waitForCancel() ;
/**
 * This method will block until all the data from the last retrieve method call has been loaded into the DataStore's internal buffer.
 */
public void waitForRetrieve();




}
