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
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlStyle.java $
//$Author: Dan $ 
//$Revision: 11 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
 
/**
 * This type can be used to add a CSS style to an HTML page
 */
public class HtmlStyle extends HtmlComponent {
	String _type;
	String _style;
	String _stylename;
	public final static String ANCHOR_TYPE="A";
	public final static String TEXT_TYPE="";
	public final static String LAYER_TYPE="#";
	public final static String TABLE_CELL_TYPE="TD";
	
/**
 * Constructs a new style to place in the page.
 * @param name of style to create.
 * @param type of style to create. e.g. A, H1, TD etc.
 * @param style this is the style attribute String. e.g. COLOR: #000000; BACKGROUND: #ffffff; ...
 */
public HtmlStyle(String name,String type,String style,HtmlPage p) {
	super(name,p);
	_type = type;
	_style = style;
	_stylename = name;
}
public void generateHTML(java.io.PrintWriter p, int rowNo) {
	if (! getVisible())
		return;

	if (_style == null || _style.trim().equals(""))
		return;
		
	  
	p.println("<STYLE>");
	String sStart=".";
	if (_type!=null && !_type.trim().equals(""))  {
	  p.print(_type);
	  if (_type.equals("#"))
		sStart="";
	 }
	if (_name!=null && !_name.trim().equals("")) 
	  p.println(sStart+_stylename);
	
	p.println("{");
	p.println(_style);
	p.println("}");
	p.println("</STYLE>");	
}
/**
 * This method returns the style in the component.
 */
public String getStyle() {
	return _style;
}
/**
 * This method returns the name of the style
 */
public String getStyleName() {
	return _stylename;
}
/**
 * This method sets the stylet that the componnt will generate.
 */
public void setStyle(String style) {
	_style = style;
}
}
