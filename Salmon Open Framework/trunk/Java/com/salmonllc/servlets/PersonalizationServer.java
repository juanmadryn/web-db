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
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/PersonalizationServer.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 8/24/04 4:27p $
/////////////////////////

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.salmonllc.util.URLGenerator;
import com.salmonllc.util.MessageLog;
import com.salmonllc.jsp.JspServlet;
import com.salmonllc.personalization.SkinManager;
import com.salmonllc.personalization.Skin;
import com.salmonllc.personalization.Attribute;

/**
 * This Servlet is used to provide skin information to the ProxySkinManager.
 */
public class PersonalizationServer extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	res.setStatus(HttpServletResponse.SC_OK);
        String skinName = req.getParameter("SKINNAME");
        String operation = req.getParameter("OP");
        if (operation == null)
            operation = "LIST";

        MessageLog.writeInfoMessage("Personalization Server invoked: Operation=" + operation + " Skin Name=" + skinName,this);

        String app = URLGenerator.generateServletBaseURL(req);
        int pos = app.indexOf("/");
        if (pos > -1)
            app = app.substring(0, pos);
        SkinManager man = SkinManager.getSkinManager(app);

        PrintWriter pw = res.getWriter();
        if (operation.equals("LIST")) {
            String[] s = man.getSkinNames();
            for (int i = 0; i < s.length; i++)
                pw.println(s[i]);
            pw.close();
            return;
        }

        Skin sk = null;
        if (skinName == null) {
            String key = "%$DEFAULT_SKIN$%";
            HttpSession sess = req.getSession(false);
            if (sess != null) {
                sk = (Skin) sess.getAttribute(app + key);
                if (sk == null)
                   sk = (Skin) sess.getAttribute(key);
            }
            if (sk == null)
                sk = new Skin();
        }
        else if (skinName.equals("TEMP")) {
            String key = "%$TEMP_SKIN$%";
            HttpSession sess = req.getSession(false);
            if (sess != null) {
                sk = (Skin) sess.getAttribute(key);
                key = "%$DEFAULT_SKIN$%";
                if (sk == null)
                    sk = (Skin) sess.getAttribute(app + key);
                    if (sk == null)
                        sk = (Skin) sess.getAttribute(key);
            }
            if (sk == null)
                sk = new Skin();
        }
        else {
            sk = new Skin();
            man.load(skinName, sk);
        }

        Attribute att[] = sk.getAllAttributes();
        for (int i = 0; i < att.length; i++)
            pw.println(att[i].getAttribute() + "=" + att[i].getValue());
        pw.close();


    }

    /**
     * Adds a special skin to the session called TEMP that can be used to apply a skin to a test applet
     */
    public static void setTempSkin(HttpSession sess, Skin sk) {
        sess.setAttribute("%$TEMP_SKIN$%",sk);
    }
}
