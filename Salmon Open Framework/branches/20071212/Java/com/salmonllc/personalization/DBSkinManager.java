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
package com.salmonllc.personalization;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

import java.sql.*;
import java.util.Vector;


/**
 * This class loads and saves skins to a RDBMS table. It uses the following keys from the property file to determine how to load the skin
 *   SKIN_TABLE="SkinTable"
 *   SKIN_DB_PROFILE="SkinDBProfile"
 *   SKIN_NAME_COLUMN="SkinNameColumn"
 *   SKIN_KEY_COLUMN="SkinKeyColumn"
 *   SKIN_VALUE_COLUMN="SkinValueColumn"
 */

public class DBSkinManager extends SkinManager {

    String _appName = null;
    String _table = null;
    String _profile = null;
    String _nameCol = null;
    String _keyCol = null;
    String _valueCol = null;

    void init(String appName) {
        Props p = Props.getProps(appName, null);
        _appName = appName;
        _table = p.getProperty(Props.SKIN_TABLE);
        _profile = p.getProperty(Props.SKIN_DB_PROFILE);
        _nameCol = p.getProperty(Props.SKIN_NAME_COLUMN);
        _keyCol = p.getProperty(Props.SKIN_KEY_COLUMN);
        _valueCol = p.getProperty(Props.SKIN_VALUE_COLUMN);
    }

    /*Loads the data for a skin with the specified name and append its attributes to the current skin*/
    public void load(String name, Skin skin) {
        DBConnection conn = null;
        try {
            conn = DBConnection.getConnection(_appName, _profile);
            Statement st = conn.createStatement();
            String sql = "SELECT " + _keyCol + "," + _valueCol + " FROM " + _table + " WHERE " + _nameCol + "= '" + name + "'" ;
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                String att = r.getString(1);
                String val = r.getString(2);
                boolean classAtt = false;
                boolean instAtt = false;
                if (att.startsWith("class."))
                    classAtt = true;
                else if (att.startsWith("instance."))
                    instAtt = true;

                if (classAtt || instAtt) {
                    int ndx = att.lastIndexOf(".");
                    int ndx2 = att.indexOf(".");
                    if (ndx == ndx2)
                        continue;
                    String attName = att.substring(ndx + 1);
                    String attClass = att.substring(ndx2 + 1, ndx);
                    if (classAtt)
                        skin.setClassAttribute(attClass, attName, val);
                    else
                        skin.setInstanceAttribute(attClass, attName, val);
                } else {
                    skin.setProperty(att, val);
                }
            }
            r.close();
            st.close();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("load()", e, this);
        } finally {
            if (conn != null)
                conn.freeConnection();
        }

    }

    /*Saves a skin under the specified name, if it exists it is overwritten*/
    public void save(String name, Skin skin) {
        DBConnection conn = null;
        try {
            conn = DBConnection.getConnection(_appName, _profile);
            conn.beginTransaction();
            Statement st = conn.createStatement();
            st.executeQuery("DELETE FROM " + _table + " WHERE " + _nameCol + "= '" + name + "'");
            PreparedStatement st2 = conn.prepareStatement("INSERT INTO " + _table + "(" + _nameCol + "," + _keyCol + "," + _valueCol + ") VALUES ('" + name + "',?,?)");
            Attribute att[] = skin.getAllAttributes();
            for (int i = 0; i < att.length; i++) {
                st2.setString(1, att[i].getAttribute());
                st2.setString(2, att[i].getValue());
                st2.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            MessageLog.writeErrorMessage("save()", e, this);
        } finally {
            if (conn != null)
                conn.freeConnection();
        }
    }

    /*Gets an array of available skin names*/
    public String[] getSkinNames() {
        Vector v = new Vector();
        DBConnection conn = null;
        try {
            conn = DBConnection.getConnection(_appName, _profile);
            Statement st = conn.createStatement();
            String sql = "SELECT distinct " + _nameCol + " FROM " + _table + " ORDER BY " + _nameCol;
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                v.add(r.getString(1));
            }
            r.close();
            st.close();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("load()", e, this);
        } finally {
            if (conn != null)
                conn.freeConnection();
        }
        String ret[] = new String[v.size()];
        v.copyInto(ret);
        return ret;
    }

}
