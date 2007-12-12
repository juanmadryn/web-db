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
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlLine.java $
//$Author: Dan $ 
//$Revision: 7 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
 
/**
 * This type can be used to add a horizontal line to your page.
 */
public class HtmlLine extends HtmlComponent {
	public static final String ALIGN_LEFT = "LEFT";
	public static final String ALIGN_CENTER = "CENTER";
	public static final String ALIGN_RIGHT = "RIGHT";
	public static final String ALIGN_NONE = "";

	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;

	private String _align = ALIGN_NONE;
	private boolean _shade = true;
	private int _width = -1;
	private int _thickness = -1;
	private int _sizeOption = SIZE_PERCENT;
/**
 * Constructs a line object for the page
 */
public HtmlLine(HtmlPage p) {
	super("",p);
}
public void generateHTML(java.io.PrintWriter p, int rowNo) {
	if (! getVisible())
		return;

	String out = "<HR";

	if (_align != null)
		if (! _align.equals(ALIGN_NONE))
			out += " ALIGN=\"" + _align + "\"";
	
	if (! _shade)
		out += " NOSHADE";

	if (_thickness > -1) 
		out += " SIZE=\"" + _thickness + "\"";

	if (_width > -1) {
		out += " WIDTH=\"" + _width ;
		if (_sizeOption == SIZE_PERCENT)
			out += "%";
		out += "\"";
	}	
			
	out += ">";		
		
	p.println(out);
}
/**
 * Returns the alignment property for the table.
 * @return align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
 */
public String getAlign() {
	return _align;
}
/**
 * This method gets whether or not the line is shaded.
 */
public boolean getShade() {
	return _shade;
}
/**
 * This method returns the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
 */
public int getSizeOption() {
	return _sizeOption;
}
/**
 * This method returns the thickness of the line in pixels.
 */
public int getThickness() {
	return _thickness;
}
/**
 * This method returns the width (either in pixels or percent depending on the size option) of the component.
 */
public int getWidth() {
	return _width;
}
/**
 * Sets the alignment property for the table.
 * @param align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
 */

public void setAlign(String align) {
	_align = align;
}
/**
 * This method sets whether or not the line is shaded.
 */
public void setShade(boolean shade) {
	_shade = shade;
}
/**
 * This method sets the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
 */
public void setSizeOption(int option) {
	_sizeOption = option;
}
/**
 * This method sets the thickness of the line in pixels.
 */
public void setThickness(int thickness) {
	_thickness = thickness;
}
/**
 * This method sets the minimum width of the line in either pixels or percent depending on size option.
 */
public void setWidth(int width) {
	_width = width;
}
}
