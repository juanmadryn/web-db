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

/**
 * This interface should be implemented for DataStores that use non standard data sources to retrieve their data.
 */
public interface DataSourceOut {
/**
 * This method will be called for each row that was deleted in the datastore. The method must delete the row from the database.Return true to keep processing rows and false to stop.
 */
public boolean deleteRow(DataStore ds,DataStoreRow row, DBConnection conn) throws Exception;
/**
 * This method will be called for each row that was inserted in the datastore. The method must insert the row into the database. Return true to keep processing rows and false to stop.
 */
public boolean insertRow(DataStore ds,DataStoreRow row, DBConnection conn) throws Exception;
/**
 * This method will be after the data update loop starts.
 * @return boolean true if the DataStore was updated properly and false if not.
 */
public void postUpdate(DataStore ds, DBConnection conn, boolean handleTrans, boolean updateSucceeded) throws Exception;
/**
 * This method will be called before the data update loop starts.
 * @return boolean true if the DataStore should be updated and false if not
 */
public boolean preUpdate(DataStore ds, DBConnection conn, boolean handleTrans) throws Exception;
/**
 * This method will be called for each row that was modified in the datastore. The method must take the values in the datastore and update the database.Return true to keep processing rows and false to stop.
 */
public boolean updateRow(DataStore ds,DataStoreRow row, DBConnection conn) throws Exception;
}
