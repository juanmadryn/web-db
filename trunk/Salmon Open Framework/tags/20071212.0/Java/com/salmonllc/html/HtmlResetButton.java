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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlResetButton.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 6/11/03 4:27p $ 
/////////////////////////
 
import com.salmonllc.properties.*;


/**
 * This class generates an html reset button for a page.
 */
public class HtmlResetButton extends HtmlComponent {
	String _dispName = "";
	String _onClick = null;
	private String _fontTagStart = null;
	private String _fontTagEnd = null;
	private String _theme;
/**
 * Constructs a new Submit Button.
 * @param name Each component on a page must have a unique name.
 * @param dispName The text to appear on the button.
 * @param p A Props object that will be used to initialize any properties in the object.
 */
public HtmlResetButton(String name, String dispName, HtmlPage p) {
	this(name,dispName,null,p);
}
/**
 * Constructs a new Submit Button.
 * @param name Each component on a page must have a unique name.
 * @param dispName The text to appear on the button.
 * @param theme The theme to use for loading properties.
 * @param p A Props object that will be used to initialize any properties in the object.
 */
public HtmlResetButton(String name, String dispName, String theme, HtmlPage p) {
	super(name,p);
	_dispName = dispName;
	setTheme(theme);
}
public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	if (! getVisible())
		return;
		
	String out = "<INPUT TYPE=\"RESET\" name=\"" + getFullName() + "\" value=\"" + _dispName + "\"";

	if (_onClick != null)
		out += " onClick=\"" + _onClick + "\"";
		
	out += ">";
	
	if (_fontTagStart != null) 
		out = _fontTagStart + out + _fontTagEnd;

	p.println(out);
}
/**
 * This method returns the text that will be displayed on the button in the browser.
 * @return java.lang.String
 */
public String getDisplayName() {
	return _dispName;
}
/**
 * This method gets the end font tag for the component.
 */
public String getFontEndTag() {
	return _fontTagEnd;
}
/**
 * This method gets the start font tag for the component.
 */
public String getFontStartTag() {
	return _fontTagStart;
}
/**
 * This method gets the javascript that will be executed when the button is clicked.
 */
public String getOnClick() {
	return _onClick;
}
/**
 * This method returns the property theme for the component.
 * @param theme The theme to use.
 */
public String getTheme() {
	return _theme;
}
/**
 * This method sets the text that will appear on the button in the browser.
 */
public void setDisplayName(String name) {
	_dispName = name;
}
/**
 * This method sets the end font tag for the component.
 */
public void setFontEndTag(String value) {
	_fontTagEnd = value;
}
/**
 * This method sets the start font tag for the component.
 */
public void setFontStartTag(String value) {
	_fontTagStart = value;
}
/**
 * This method sets the javascript that will be executed when the button is clicked.
 */
public void setOnClick(String onClick) {
	_onClick = onClick;
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public void setTheme(String theme) {
	Props props = getPage().getPageProperties();
	_fontTagStart = props.getThemeProperty(theme,Props.FONT_BUTTON + Props.TAG_START);
	_fontTagEnd = props.getThemeProperty(theme,Props.FONT_BUTTON + Props.TAG_END);
	_theme = theme;
}
}
