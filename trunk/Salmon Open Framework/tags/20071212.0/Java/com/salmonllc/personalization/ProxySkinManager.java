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

import com.salmonllc.sql.Base64Encoder;

import java.util.Vector;
import java.util.Properties;
import java.io.*;
import java.net.URLConnection;
import java.net.URL;


/**
 * This class loads skins using the PersonalizationServer servlet to get the data. It can be used to load server side skins to applets and Java applications.
 */

public class ProxySkinManager extends SkinManager {

    String _serverURL;
    String _sessionID;
    String _proxyUserID;
    String _proxyPw;

    void init(String st) {
    }

    /**
     * Creates a new ProxySkinManager
     * @param serverURL the URL of the personalization server servlet
     * @param sessionID the users session id
     */
    public ProxySkinManager(String serverURL, String sessionID) {
        _serverURL = serverURL;
        _sessionID = sessionID;
    }

    /**
     * Creates a new ProxySkinManager that can go through a proxy server
     */
    public ProxySkinManager(String serverURL, String sessionID, String proxyHost, String proxyPort, String proxyUserID, String proxyPw) {
        _proxyPw = proxyPw;
        _proxyUserID = proxyUserID;
        _serverURL = serverURL;
        _sessionID = sessionID;
        if (proxyHost != null) {
            Properties p = System.getProperties();
            p.put("proxySet", "true");
            p.put("proxyHost", proxyHost);
            p.put("proxyPort", proxyPort);
        }
    }

    /*Loads the data for a skin with the specified name and append its attributes to the current skin*/
    public void load(String name, Skin skin) {
        try {
            BufferedReader in = openConnection(_serverURL, _sessionID, "LOAD", name);
            String line = in.readLine();
            int pos = 0;
            while (line != null) {
                if (line.length() > 0 && line.charAt(0) != '#') {
                    pos = line.indexOf('=');
                    if (pos > -1) {
                        String att = line.substring(0, pos).trim();
                        String val = line.substring(pos + 1).trim();
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
                }
                line = in.readLine();
            }
            in.close();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Saves a skin under the specified name, if it exists it is overwritten*/
    public void save(String name, Skin skin) {
        //for security purposes we don't update skins remotely
    }

    /*Gets an array of available skin names*/
    public String[] getSkinNames() {
        Vector v = new Vector();
        BufferedReader in = null;
        try {
            in = openConnection(_serverURL, _sessionID, "LIST", null);
            String line = in.readLine();
            while (line != null) {
                v.add(line);
                line = in.readLine();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ret[] = new String[v.size()];
        v.copyInto(ret);
        return ret;
    }

    private BufferedReader openConnection(String url, String sessionId, String operation, String skinName) throws Exception {
        if (operation != null)
            url += "?OP=" + operation;
        if (skinName != null)
            url += (operation == null ? "?" : "&") + "SKINNAME=" + skinName;

        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        String session = sessionId;
        if (session != null)
            conn.setRequestProperty("Cookie", "sesessionid=" + session + ";session=" + session + ";sessionid=" + session + ";JSESSIONID=" + session + ";jsessionid=" + session);

        if (_proxyUserID != null) {
            String authString = _proxyUserID + ":" + _proxyPw;
            String auth = "Basic " + new Base64Encoder().encode(authString);
            conn.setRequestProperty("Proxy-Authorization", auth);
        }

        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }


}
