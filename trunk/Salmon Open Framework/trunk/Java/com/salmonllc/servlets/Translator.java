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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/Translator.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 8/24/04 4:28p $
/////////////////////////

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.salmonllc.jsp.JspServlet;
import com.salmonllc.jsp.engine.JspConverter;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * This Servlet is used to translate custom tags for Dreamweaver into valid html.
 */
public class Translator extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	res.setStatus(HttpServletResponse.SC_OK);
        try {
            String input = req.getParameter("InputFile");
            String header = req.getParameter("HeaderLine");
            if (header == null)
            	header = "";
            if (input != null) {
                FileInputStream in = new FileInputStream(input);
                byte b[] = new byte[in.available() + header.length()];
                in.read(b);
                in.close();
                String st = header + new String(b);
                String docBase=File.separator;
                int pos = input.lastIndexOf(File.separatorChar);
                if (pos != -1)
                	docBase=input.substring(0,pos);
                req.setAttribute(JspServlet.SALMON_SERVLET_KEY,this);	
                JspConverter.getInstanceVariables(new ByteArrayInputStream(st.getBytes()), this, req, res, JspConverter.CONV_HTML,docBase);
            }
        } catch (Exception e) {
            MessageLog.writeErrorMessage("Error in doGet of translator.", e, this);
        }


    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	res.setStatus(HttpServletResponse.SC_OK);
        try {
            JspConverter.convertDocument(req.getInputStream(), this, req, res, JspConverter.CONV_DREAMWEAVER);

            Props p = Props.getSystemProps();
            String info = "Dreamweaver Document Translated.";
            String input = p.getProperty(Props.JSP_ENGINE_DEBUG_INPUT_FILE);
            if (input == null)
                info += " \n     Input not written to any file. Set the property \"" + Props.JSP_ENGINE_DEBUG_INPUT_FILE + "\" in system.properties to specify a debug file for saving the Dreamweaver input.";
            else
                info += " \n     Input written to: \"" + input + "\".";
            String output = p.getProperty(Props.JSP_ENGINE_DEBUG_OUTPUT_FILE);
            if (output == null)
                info += " \n     Output written back to Dreamweaver stream only. Set the property \"" + Props.JSP_ENGINE_DEBUG_OUTPUT_FILE + "\" in system.properties to specify a debug file to save the Dreamweaver output.";
            else
                info += " \n     Output written to: \"" + output + "\".";

            MessageLog.writeInfoMessage(info, this);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("Dreamweaver Document Failed to Translate.", e, this);
        }


    }
}
