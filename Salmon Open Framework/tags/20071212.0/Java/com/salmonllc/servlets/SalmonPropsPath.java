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

package com.salmonllc.servlets;

import com.salmonllc.properties.Props;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * This class can be used to set the salmon properties path without the -D command line switch
 * To use it, add this servlet to the web.xml file of one of your web applications. Then add the init property
 * salmon.props.path and use it to specify the properties.
 *  <servlet>
 *  <servlet-name>SalmonPropsPath</servlet-name>
 *  <servlet-class>com.salmonllc.servlets.SalmonPropsPath</servlet-class>
 *  <load-on-startup>1</load-on-startup>
 *       <init-param>
 *             <param-name>salmon.props.path</param-name>
 *             <param-value>   PATH_DIRECTORY  </param-value>
 *        </init-param>
 * </servlet>
 */
public class SalmonPropsPath extends HttpServlet {

    public void init(ServletConfig sc) throws ServletException {
        String salmonPropsPath = sc.getInitParameter(Props.SYS_PROPS_DIR_PROPERTY);
        Props.setPropsPath(salmonPropsPath, true);
        super.init(sc);
    }


}
