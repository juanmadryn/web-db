package com.salmonllc.sql;

import java.io.Serializable;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/ColumnDefinition.java $
//$Author: Dan $ 
//$Revision: 16 $ 
//$Modtime: 11/19/03 3:11p $ 
/////////////////////////

/**
 * Represents on database column returned by the DataDictionary class
 */
public class ColumnDefinition implements Serializable {
	private String _tableName;
	private String _columnName;
	private String _dbDataType;
	private int _dsDataType;
	private int _precision;
	private int _scale;
	private int _length;
	private boolean _nullable;
	private boolean _isPkey;

	/**
	 * Creates a new column definition object.
	 */
	public ColumnDefinition(String dbms, String tableName, String columnName, String dbDataType, int length, boolean nullable) {
		_tableName = tableName;
		_columnName = columnName;
		_dbDataType = dbDataType;
		_dsDataType = DataType.toDSDataType(dbms, dbDataType);
		_length = length;
		_nullable = nullable;
	}

	/**
	 * Creates a new column definition object.
	 */
	public ColumnDefinition(String dbms, String tableName, String columnName, String dbDataType, int length, boolean nullable, int precision, int scale) {
		_tableName = tableName;
		_columnName = columnName;
		_dbDataType = dbDataType;
		_dsDataType = DataType.toDSDataType(dbms, dbDataType, precision, scale);
		_length = length;
		_nullable = nullable;
		_precision = precision;
		_scale = scale;
	}
	/**
	 * Creates a new column definition object.
	 */
	public ColumnDefinition(String tableName, String columnName, String dbDataType, int length, boolean nullable) {
		_tableName = tableName;
		_columnName = columnName;
		_dbDataType = dbDataType;
		_dsDataType = DataType.toDSDataType(dbDataType);
		_length = length;
		_nullable = nullable;
	}
	/**
	 * Creates a new column definition object.
	 */
	public ColumnDefinition(String tableName, String columnName, String dbDataType, int length, boolean nullable, int precision, int scale) {
		_tableName = tableName;
		_columnName = columnName;
		_dbDataType = dbDataType;
		_dsDataType = DataType.toDSDataType(dbDataType, precision, scale);
		_length = length;
		_nullable = nullable;
		_precision = precision;
		_scale = scale;
	}

	/**
	 * Creates a new column definition object.
	 */
	public ColumnDefinition(String tableName, String columnName, int dsDataType, boolean pkey) {
		_tableName = tableName;
		_columnName = columnName;
		_dsDataType = dsDataType;
		_isPkey = pkey;
	}
	/**
	 * Returns the name of the colum.
	 */
	public java.lang.String getColumnName() {
		return _columnName;
	}
	/**
	 * Returns the database datatype of the colum.
	 */
	public java.lang.String getDBDataType() {
		return _dbDataType;
	}
	/**
	 * Returns the datastore datatype of the colum.
	 */
	public int getDSDataType() {
		return _dsDataType;
	}
	/**
	 * Returns the length of the column
	 */
	public int getLength() {
		return _length;
	}
	/**
	 * Returns the precision of the column.
	 */
	public int getPrecision() {
		return _precision;
	}
	/**
	 * Returns the scale (number of decimal places) of the column.
	 */
	public int getScale() {
		return _scale;
	}
	/**
	 * Returns the tablename of the column.
	 */
	public java.lang.String getTableName() {
		return _tableName;
	}
	/**
	 * Returns true if the column can be set to null.
	 */
	public boolean isNullable() {
		return _nullable;
	}
	/**
	 * Returns true if the column is part of the table's primary key.
	 */
	public boolean isPkey() {
		return _isPkey;
	}

	public void setPkey() {
		_isPkey = true;
	}
	public String toString() {
		return _columnName;
	}

	void setTableName(String string) {
		_tableName = string;
	}

}
