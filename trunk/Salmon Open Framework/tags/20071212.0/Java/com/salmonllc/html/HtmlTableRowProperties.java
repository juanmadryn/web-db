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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTableRowProperties.java $
//$Author: Dan $ 
//$Revision: 8 $ 
//$Modtime: 6/11/03 4:27p $ 
/////////////////////////
 
/**
 * This is used in conjunction with HtmlTable to set the properties for a particular row
 */
public class HtmlTableRowProperties implements java.io.Serializable {
	private String _align = HtmlTable.ALIGN_NONE;
	private String _vAlign = HtmlTable.VALIGN_NONE;
	private String _bgColor = null;
	private String _rowClass = null;
	private boolean _wrap = true;
	private boolean _visible = true;
	
/**
 * Constructs a new empty object
 */
public HtmlTableRowProperties() {
	super();
}
/**
 * Constructs a new object with all the properties set
 * @param align Valid values are HtmlTable.ALIGN_LEFT,HtmlTable.ALIGN_CENTER,HtmlTable.ALIGN_RIGHT,HtmlTable.ALIGN_NONE
 * @param vAlign Valid values are HtmlTable.VALIGN_BASELINE,HtmlTable.VALIGN_BOTTOM,HtmlTable.VALIGN_MIDDLE and HtmlTable.VALIGN_TOP.
 * @param backGroundColor The background color for the cell.
 * @param wrap True if the contents of the cell should word wrap.
 */
public HtmlTableRowProperties(String align, String vAlign, String backGroundColor, boolean wrap) {
	super();
	_align = align;
	_vAlign = vAlign;
	_bgColor = backGroundColor;
	_wrap = wrap;
}
public String getAlign() {
	return _align;
}
public String getBackgroundColor() {
	return _bgColor;
}
public String getRowClass() {
	return _rowClass;
}
public String getVertAlign() {
	return _vAlign;
}
public boolean getVisible() {
	return _visible;
}
public boolean getWrapStyle() {
	return _wrap;
}
public void setAlign(String value) {
	_align = value;
}
public void setBackgroundColor(String color) {
	_bgColor = color;
}
public void setRowClass(String rowClass) {
	_rowClass = rowClass;
}
public void setVertAlign(String value) {
	_vAlign = value;
}
public void setVisible(boolean visible) {
	_visible = visible;
}
public void setWrapStyle(boolean value) {
	_wrap = value;
}
}
