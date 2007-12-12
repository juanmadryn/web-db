package com.salmonllc.localizer;

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

import java.sql.ResultSet;
import java.sql.Statement;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

/**
 * The DB Localizer is a concrete implementation of the Localizer class. This will load the localization keys from a database table
 **/

public class DBLocalizer extends Localizer {

	/**
	 * Constructs a new Localizer
	 */
	
	public DBLocalizer () {
		super(false);	
	}

    /**
     * Constructs a new Localizer
     * @param translateEscapes true to translate escape sequences to their propery unicode representation
     */
    public DBLocalizer(boolean translateEscapes) {
        super(false,translateEscapes);
    }
	/**
	 * Loads the data from a database table. It uses the following properties from the application or system.property file<br>
	 * LocaleTable=The table where the localization data comes from<br>
	 * LocaleLangColumn=The column in the table that specifies the language<br>
	 * LocaleKeyColumn=The column in the table that specifies the resource key<br>
	 * LocaleResourceColumn=The column in the table that specifies the resource key<br>
	 * LocaleAppColumn=The column in the table that specifies which application a key is for (optional: If left out it won't use app name as part of the selection)<br>
	 * LocaleDBProfile=The database profile to use to load the data (optional: if left out it will use the default prrofile for the application<br>
	 * The method returns true if it succeeds and false if not.
	 */
	protected boolean loadData(String appName, String language) {
		
		DBConnection conn = null;
		try {
			Props p = Props.getProps(appName,null);
			String table = p.getProperty(Props.LOCALE_TABLE);
			String lang = p.getProperty(Props.LOCALE_LANG_COLUMN);
			String key = p.getProperty(Props.LOCALE_KEY_COLUMN);
			String resource = p.getProperty(Props.LOCALE_RESOURCE_COLUMN);
			String app = p.getProperty(Props.LOCALE_APP_COLUMN);
			String prof = p.getProperty(Props.LOCALE_DB_PROFILE);
			String sql = "SELECT " + key + "," + resource + " FROM " + table + " WHERE " + lang + "='" + language + "'";
			if (app != null)
				sql += " AND " + app + " ='" + app + "'";
			sql += " ORDER BY " + key + " ASC";			
			
			conn = DBConnection.getConnection(appName,prof);
			Statement st = conn.createStatement();
			ResultSet r = st.executeQuery(sql);
			while (r.next()) 
				addValue(r.getString(1),r.getString(2));
			r.close();
			st.close();	
		}
		catch (Exception e) {
			MessageLog.writeErrorMessage("loadData",e,this);
		}	
		finally {
			if (conn != null)
				conn.freeConnection();	
		}			
		return (getSize() > 0);
	}
}
