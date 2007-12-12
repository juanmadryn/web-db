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
public interface DataSourceIn {
/**
 * This method will be called after the data retrieve ends.
 */
public void postRetrieve(DataStore ds) throws Exception;
/**
 * This method will be called before the data retrieve starts. It should do any preProcessing required to initiate the retrieve.
 * @return boolean true if the DataStore should be retrieved and false if not
 */
public boolean preRetrieve(DataStore ds, String selectionCriteria, boolean countOnly, DBConnection conn) throws Exception;
/**
 * This method will be called for each row the DataStore is to retrieve. The method should retrieve the data from the data source and populate the DataStoreRow passed. Return false when the retrieve is finished and true to continue retrieving.
 */
public boolean retrieveRow(DataStore ds,DataStoreRow row) throws Exception;
}
