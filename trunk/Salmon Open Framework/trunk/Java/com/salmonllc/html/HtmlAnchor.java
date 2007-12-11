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
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlAnchor.java $
//$Author: Dan $ 
//$Revision: 10 $ 
//$Modtime: 10/30/02 2:38p $ 
/////////////////////////
 
import java.io.*;

/**
 * This container will construct an anchor tag around the components within it.
 */
public class HtmlAnchor extends HtmlContainer {

/**
 * Creates a new HtmlAnchor
 * @param name The name of the anchor
 * @param p The page the anchor will go in.
 */
public HtmlAnchor(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p);
}

 public void generateHTML(PrintWriter p, int rowNo) throws Exception {
	p.println("<A NAME=\"" + getFullName() + "\">");
	super.generateHTML(p, rowNo);
	p.println("</A>");
}
}
