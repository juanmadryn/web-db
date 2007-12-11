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
package com.salmonllc.util;


import javax.servlet.http.HttpServletRequest;

import com.salmonllc.properties.Props;

/**
 * This class has methods which can be used to determin the URL of the server that the application is running on
 */

public class URLGenerator {
    /**
     * This method will return the url of the the object store from the system.properties file
     */
     public static String generateObjectstoreURL(HttpServletRequest req) {
         return generateObjectstoreURL(req,null);
     }

    /**
     * This method will return the url of the the object store for a particular application
     */
    public static String generateObjectstoreURL(HttpServletRequest req, String appName) {
        Props p = null;
        if (appName == null)
             p = Props.getSystemProps();
        else
            p = Props.getProps(appName,null);
        String url = p.getProperty(Props.SYS_OBJECTSTORE_URL);
        if (url != null) {
            if (url.startsWith("http"))
                return url;
            else {
                StringBuffer buf = new StringBuffer(generateServerURL(req));
                buf.append(url);
                return buf.toString();
            }
        } else {
            StringBuffer buf = new StringBuffer();
            buf.append(generateServerURL(req));
            buf.append("/");
            buf.append(generateServletBaseURL(req));
            buf.append("/Objectstore");
            return buf.toString();
        }
    }

    /**
     * This method will return the url of the server running the servlet
     */
    public static String generateServerURL(HttpServletRequest req) {
        Props p = Props.getSystemProps();
        StringBuffer buf = new StringBuffer();
        buf.append(p.getProperty(Props.SYS_UNSECURE_SCHEME, req.getScheme()));
        buf.append("://");
        buf.append(req.getServerName());
        int port = p.getIntProperty(Props.SYS_UNSECURE_PORT);
        if (port == -1)
            port = req.getServerPort();
        if (port > 0 && port != 80 && port != 443) {
            buf.append(":");
            buf.append(new Integer(port).toString());
        }
        return buf.toString();
    }

      /**
     * This method will return the url of the server running the servlet as a local url
     */
    public static String generateLocalHostServerURL(HttpServletRequest req) {
        Props p = Props.getSystemProps();
        StringBuffer buf = new StringBuffer();
        buf.append(p.getProperty(Props.SYS_UNSECURE_SCHEME, req.getScheme()));
        buf.append("://");
        buf.append(p.getProperty(Props.SYS_LOCALHOST,"127.0.0.1"));
        int port = p.getIntProperty(Props.SYS_UNSECURE_PORT);
        if (port == -1)
            port = req.getServerPort();
        if (port > 0 && port != 80 && port != 443) {
            buf.append(":");
            buf.append(new Integer(port).toString());
        }
        return buf.toString();
    }

     /**
     * This method will return the url of the server running the servlet as a local url
     */
    public static String generateLocalHostServerURL() {
        Props p = Props.getSystemProps();
        StringBuffer buf = new StringBuffer();
        buf.append(p.getProperty(Props.SYS_UNSECURE_SCHEME, "http"));
        buf.append("://");
        buf.append(p.getProperty(Props.SYS_LOCALHOST,"127.0.0.1"));
        int port = p.getIntProperty(Props.SYS_UNSECURE_PORT);
        if (port == -1)
            port = 80;
        if (port > 0 && port != 80 && port != 443) {
            buf.append(":");
            buf.append(new Integer(port).toString());
        }
        return buf.toString();
    }

    /**
     * This method will return base url for every servlet in the web application ex:servlet in http://host/servlet/AppServer
     */
    public static String generateServletBaseURL(HttpServletRequest req) {
        String servletPath = req.getRequestURI();
        String servletName = req.getServletPath();
        if (servletName != null) {
        	int pos = servletName.lastIndexOf("/");
        	if (pos != -1)
            	servletName = servletName.substring(pos);
        }    
        else
        	servletName = "";	
		
        int pos = -1;
        if (servletPath != null)
        	pos = servletPath.indexOf(servletName);
        if (pos < 1)
            servletPath = "";
        else
            servletPath = servletPath.substring(1, pos);
        return servletPath;

    }

    /**
     * This method will return base url for every servlet in the web application ex:servlet in http://host/servlet/AppServer
     */
    public static String generateServletURL(HttpServletRequest req) {
        String servletPath = req.getRequestURI();
        String servletName = req.getServletPath();
        int pos = servletName.lastIndexOf("/");
        if (pos != -1)
            servletName = servletName.substring(pos);

        pos = servletPath.indexOf(servletName);
        if (pos <= 0)
            servletPath = "";
        else
            servletPath = servletPath.substring(1, pos);

        return servletPath + servletName;

    }

    /**
     * This method will return a secure url of the server running the servlet
     */
    public static String generateSecureServerURL(HttpServletRequest req) {
        Props p = Props.getSystemProps();
        StringBuffer buf = new StringBuffer();
        buf.append(p.getProperty(Props.SYS_SECURE_SCHEME, "https"));
        buf.append("://");
        buf.append(req.getServerName());
        int port = p.getIntProperty(Props.SYS_SECURE_PORT);
        if (port > 0 && port != 80 && port != 443) {
            buf.append(":");
            buf.append(new Integer(port).toString());
        }
        return buf.toString();
    }
}
