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
package com.salmonllc.html;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlScript.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 10/30/02 8:21p $
/////////////////////////

/**
 * This type can be used to add javascript to your html page
 */
public class HtmlScript extends HtmlComponent {
    String _script;
    String _language;

    /**
     * Constructs a new script that can be placed on a page
     * @param script the script to insert.
     */
    public HtmlScript(String script, HtmlPage p) {
        this(script, null, p);
    }

    /**
     * Constructs a new script that can be placed on a page
     * @param script java.lang.String
     * @param language java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public HtmlScript(String script, String language, HtmlPage p) {
        super("", p);
        _script = script;
        _language = language;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) {
        if (!getVisible())
            return;

        if (_script == null)
            return;

        p.print("<SCRIPT");
        if (_language != null && !_language.trim().equals(""))
            p.print(" LANGUAGE=\"" + _language + "\"");
        p.println(">");
        p.println(_script);
        p.println("</SCRIPT>");
    }

    /**
     * This method returns the script in the component.
     */
    public String getScript() {
        return _script;
    }

    /**
     * This method sets the script that the component will generate.
     */
    public void setScript(String script) {
        _script = script;
    }
}
