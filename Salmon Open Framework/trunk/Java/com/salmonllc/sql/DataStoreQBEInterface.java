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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreQBEInterface.java $
//$Author: Dan $ 
//$Revision: 2 $ 
//$Modtime: 11/01/04 4:21p $ 
/////////////////////////


/**
 * This interface is for DataStores that should work with the QBEBuilder
 */
public interface DataStoreQBEInterface {

	/**
	 * This method returns the number of aliases used by the datastore.
	 */
	public int getAliasCount();
	/**
	 * This method returns the database name of the column in the data store
	 * given its index.
	 */
	public String getColumnDatabaseName(int col) throws DataStoreException;
	/**
	 * This method returns the name of the database table that the column is
	 * for.
	 */
	public String getColumnTableName(int col) throws DataStoreException;
	/**
	 * Returns the name of the database engine being used
	 */
	public String getDBMS();
	
	/**
	 * Returns a list of column definitions for a particular table in the database that the datastore is using. Note, the datastore must have an app name for this method to work.
	 */	
	public ColumnDefinition[] getColumnsForTable(String table);

	/**
	 * This method returns the name of one of the aliases used by the datastore. Use the method getAliasCount() to find out how many tables or aliases are used by the datastore.
	 * @return The table name.
	 */
	public String getAlias(int tableNo) throws DataStoreException ;

	/**
	 * This method returns the name of one of the tables used by the datastore. Use the method getAliasCount() to find out how many tables or aliases are used by the datastore.
	 * @return The table name.
	 */
	public String getTable(int tableNo) throws DataStoreException ;
	
	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved in a new thread so the beginning of the result set can be accessed before all the data has been retrieved.
	 * @param criteria Additional selection criteria to use to limit the result set
	 */
	public void retrieve(String criteria) throws java.sql.SQLException, DataStoreException  ;

	/**
	 * Use this method to get the amount of rows that will be retrieved when a data store retrieve is executed. 
	 * @param criteria The selection criteria to use for the select.	
	 */
	public int estimateRowsRetrieved(String criteria) throws Exception;

	/**
	 * This method returns the number of columns in the datastore.
	 */
	public int getColumnCount() ;
	
	/**
	 * This method returns the index of the column in the data store given its name.
	 * @return The column number or -1 if the column name is not found.
	 * @param column The name of the column to search for.
	 */
	public int getColumnIndex(String column) ;
}

