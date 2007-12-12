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

import java.sql.*;
/**
 * This class encapsulates one row in a DataStore Buffer.
 */
public class DataStoreRow {
	DataStoreBuffer _ds;
	DSDataRow _row;
	DSDataStoreDescriptor _desc;
DataStoreRow(DataStoreBuffer ds, DSDataRow row, DSDataStoreDescriptor desc) {
	super();
	_ds = ds;
	_row = row;
	_desc = desc;
}
private void checkColNo(int colNo)  throws DataStoreException {
	if (colNo < 0 || colNo >= _desc.getColumnCount())	
		throw new DataStoreException("Specified column (" + colNo + ") is out of range.");			
}
private int getColNo(String colName) throws DataStoreException {
	int colNo = _desc.getColumnIndex(colName);
	
	if (colNo < 0 || colNo >= _desc.getColumnCount())	
		throw new DataStoreException("Specified column (" + colName + ") is out of range.");

	return colNo;	
}
/**
 * This method returns the index of the column with the specified name or -1 if not found.
 */
public int getColumnIndex(String name) {
	return _ds.getColumnIndex(name);
}
/**
 * This method returns the number of columns.
 */
public int getColumnCount() {
	return _ds.getColumnCount();
}
/**
 * This method returns a list with the names of all the columns in the data store.
 */
public String[] getColumnList() {
	return _ds.getColumnList();
}
/**
 * This method returns the name of the column at the specified index.
 */
public String getColumnName(int colNo) throws DataStoreException {
	return _ds.getColumnName(colNo);
}
/**
 * This method gets the data from a column in the datastore. 
 * @param colNo The number of the column in the data store.
 */
public Object getData(int colNo) throws DataStoreException {
	checkColNo(colNo);	
	return _row.getData(colNo);
}
/**
 * This method gets the data from a column in the datastore. If the column doesn't exist it will return null.
 * @param colName The name of the column in the data store.
 */
public Object getData(String colName) throws DataStoreException {
	return _row.getData(getColNo(colName));
}
/**
 * This method returns the datatype of the column in the datastore. 
 * @param colNo The number of the column in the data store.
 */
public int getDataType(int colNo) throws DataStoreException {
	checkColNo(colNo);	
	return _desc.getColumn(colNo).getType();		
}
/**
 * This method returns the datatype of the column in the datastore. 
 * @param colName The name of the column in the data store.
 */
public int getDataType(String colName) throws DataStoreException {
	return getDataType(getColNo(colName));
}
/**
 * This method was created in VisualAge.
 * @return com.salmonllc.sql.DSDataRow
 */
public DSDataRow getDSDataRow() {
	return _row;
}
/**
 * This method gets the original data (before any modifications were made) from a column in the datastore. 
 * @param colNo the column in the data store.
 */
public Object getOriginalData(int colNo) throws DataStoreException {
	if (colNo < 0 || colNo >= _desc.getColumnCount())	
		throw new DataStoreException("Specified column (" + colNo + ") is out of range.");			
		
	return _row.getOrigData(colNo);
}
/**
 * This method gets the original data (before any modifications were made) from a column in the datastore. 
 * @param colName the column in the data store.
 */
public Object getOriginalData(String colName) throws DataStoreException {
	return _row.getOrigData(getColNo(colName));
}
/**
 * This method gets the status of the column in the row. Returns true if the column has been modified since the original retrieve.
 */
public boolean isColumnModified(int colNo) throws DataStoreException {
	checkColNo(colNo);
	return ( _row.getColumnStatus(colNo) == DataStoreBuffer.STATUS_MODIFIED);
}
/**
 * This method gets the status of the column in the row. Returns true if the column has been modified since the original retrieve.
 */
public boolean isColumnModified(String colName) throws DataStoreException {
	return isColumnModified(getColNo(colName));
}
/**
 * Use this method to populate the row from a result set. The result set must be 100% compatible with the structure of the datastore or an exception will be thrown.
 */
public void populateFromResultSet(ResultSet r) throws Exception {
	_row.populateFromResultSet(_desc,r);
}
/**
 * This method sets the status of the row and all the columns in it to not modified.
 */
public void resetStatus() {
	_row.resetStatus();
}
/**
 * This method sets the value of the column colNo.
 * @param colNo int The column to set.
 * @param data Object The value to set the column to.
 */
public void setData(int colNo, Object data) throws DataStoreException {
	checkColNo(colNo);
	
	int type = _desc.getColumn(colNo).getType();
	
	boolean dataMatch = false;
	
	switch (type) {
		case DataStoreBuffer.DATATYPE_BYTEARRAY:
			dataMatch = data instanceof byte[];
			break;
		case DataStoreBuffer.DATATYPE_DATE:
			dataMatch = data instanceof java.sql.Date || data instanceof java.sql.Timestamp;
			break;
		case DataStoreBuffer.DATATYPE_DATETIME:
			dataMatch = data instanceof java.sql.Date || data instanceof java.sql.Timestamp;
			break;
		case DataStoreBuffer.DATATYPE_DOUBLE:
			dataMatch = data instanceof Double;
			break;
		case DataStoreBuffer.DATATYPE_FLOAT:
			dataMatch = data instanceof Float;
			break;
		case DataStoreBuffer.DATATYPE_INT:
			dataMatch = data instanceof Integer;
			break;
		case DataStoreBuffer.DATATYPE_LONG:
			dataMatch = data instanceof Long;
			break;
		case DataStoreBuffer.DATATYPE_SHORT:
			dataMatch = data instanceof Short;
			break;
		case DataStoreBuffer.DATATYPE_STRING:
			dataMatch = data instanceof String;
			break;
		case DataStoreBuffer.DATATYPE_TIME:
			dataMatch = data instanceof java.sql.Time;
			break;
	}

	if (! dataMatch && data != null)
		throw new DataStoreException("Column type mismatch. Column: " + colNo + " must be type: " + type + ".");

	_row.setValue(colNo,data,_desc)	;
}
/**
 * This method sets the value of the column colName.
 * @param colName String The column to set.
 * @param data Object The value to set the column to.
 */
public void setData(String colName, Object data) throws DataStoreException {
	setData(getColNo(colName),data);	
}

/**
 * Sets a temp value in the row
 */
public void setTempValue(int colNo, String data) throws DataStoreException {
    _row.setTempValue(colNo,data,_desc);
}

/**
 * Sets a temp value in the row
 */
public void setTempValue(String colName, String data) throws DataStoreException {
    _row.setTempValue(getColNo(colName),data,_desc);
}

/**
 * Gets a temp value from the row
 */
public String getTempValue(int colNo) throws DataStoreException {
    return _row.getTempValue(colNo);
}

/**
 * Gets a temp value from the row
 */
public String getTempValue(String colName) throws DataStoreException {
    return _row.getTempValue(getColNo(colName));
}

/**
 * returns the column status for the row
 */
int getColumnStatus(int colNo) {
    return _row.getColumnStatus(colNo);
}

void setDSDataRow(DSDataRow row) {
	_row = row;
}
/**
 * Copies matching values from this DataStore row to the destination row
 */
public void copyTo(DataStoreRow dest) {
	DSDataStoreDescriptor destDesc = dest.getDesc();
	DSDataRow sourceRow = getDSDataRow();
	DSDataRow destRow = dest.getDSDataRow();
	sourceRow.copyTo(destRow,_desc,destDesc);	
}	
DSDataStoreDescriptor getDesc() {
	return _desc;
}

}
