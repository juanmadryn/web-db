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
package com.salmonllc.fatwire;

import com.salmonllc.jsp.*;

/**
 * Stub object so the rest of the framework will compile. Replace with the real one if using update engine.
 */
public class UEDataStore extends com.salmonllc.sql.DataStoreBuffer {
private String _dbProfile = null;



public UEDataStore() {}
public UEDataStore(JspController cont, String contentClassName, String[] colList) {}			  
public UEDataStore(JspController cont, String contentClassName , String sDbProfile ) throws Exception{}
public UEDataStore(JspController cont, String contentClassName, String colList[] , String sDbProfile) throws Exception{}    
public void buildBuffer(JspController cont, String contentClassName, String[] colList) throws Exception {
}   
/**
 * @return Returns the dbProfile.
 */
public String getDbProfile ()
{
	return _dbProfile;
}
/**
 * @param dbProfile The dbProfile to set.
 */
public void setDbProfile (String dbProfile)
{
	_dbProfile = dbProfile;
}

public void retrieve() {}
public void setMaxRows(int max) {

}
}
