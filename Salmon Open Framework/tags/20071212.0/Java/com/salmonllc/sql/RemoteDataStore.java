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

import javax.servlet.http.*;
/**
 * This class should be extended for each data store that will be retrieved remotely via the DataServer servlet. The structure of the DataStore should be set up via addColumn methods in the constructor. Also the constructor should set the application name and the dbprofile name for the datastore.
 * @see DataStoreProxy
 */
public abstract class RemoteDataStore extends DataStore {

public RemoteDataStore() {
	super();
}

/**
 * This method gets fired before each remote request to the DataStore is made (including the create method).
 * @return true if the request should be granted and false if the request should be denied.
 * @param reqType The type of request being made.
 * @param req javax.servlet.http.HttpServletRequest The servlet request used.
 * @param sessionValid true if this request came from a valid session or false if a new one had to be created.
 * @param userID The user id making the request
 * @param password The password of the user id making the request
 * @param criteria The selection criteria passed from the request
 */
public boolean request(String reqType, HttpServletRequest req, boolean sessionValid, String userID, String password, String criteria) throws DataStoreException {
	return sessionValid;
}

}
