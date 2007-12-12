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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlDisplayBox.java $
//$Author: Dan $ 
//$Revision: 21 $ 
//$Modtime: 1/22/04 11:18a $ 
/////////////////////////

import com.salmonllc.properties.*;
import java.io.*;
import java.util.*;

/**
 * This container can be used to create a display box in a page. The display box has a heading caption, components in the heading band and one component inside the box.
 */
public class HtmlDisplayBox extends HtmlContainer {
	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;
	//
	String _headingCaption;
	HtmlContainer _box;
	//
	private String _font;
	private String _fontStartTag;
	private String _fontEndTag;
	private String _headingBackgroundColor;
	private String _backgroundColor;
	private int _border = 0;
	private String _headingClassname;
	private String _bodyClassname;
	//
	private int _sizeOption = SIZE_PERCENT;
	private int _containerWidth = 100;
	//
	private boolean _suppressHeading = false;

	private int _cellPadding = -1;
	private int _cellSpacing = -1;

	private String _theme = null;

	/**
	 * Constructs an HtmlDisplayBox using the default theme.
	 * @param name The name of the component
	 * @param p The page that it goies in.
	 */
	public HtmlDisplayBox(String name, HtmlPage p) {
		this(name, null, p);

	}
	/**
	 * Constructs an HtmlDisplayBox using the default theme.
	 * @param name The name of the component
	 * @param theme The theme to use for loading properties.
	 * @param p The page that it goes in.
	 */
	public HtmlDisplayBox(String name, String theme, HtmlPage p) {
		super(name, p);

		_font = Props.FONT_DISPLAY_BOX_HEADING;

		setTheme(theme);

	}
	/**
	 * This method will add a component (usually a button) to the heading of the box.
	 */
	public void addHeadingComponent(HtmlComponent h) {
		add(h);
	}
	public boolean executeEvent(int eventType) throws Exception {
		if (eventType == HtmlComponent.EVENT_OTHER) {
			if (_componentsVec != null) {
				HtmlComponent h = null;
				int componentsSize = _componentsVec.size();
				for (int i = 0; i < componentsSize; i++) {
					h = (HtmlComponent) _componentsVec.elementAt(i);
					if (!h.executeEvent(eventType))
						return false;
				}
			}
			if (_box != null) {
				if (!_box.executeEvent(eventType))
					return false;
			}
		} else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
			boolean retVal = _submit.executeEvent(eventType);
			_submit = null;
			return retVal;
		}
		return true;
	}
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		if (!_visible)
			return;
		if (_center)
			p.print("<CENTER>");
		String boxColor = "";
		if (_suppressHeading == false) {
			int cellPadding = _cellPadding > -1 ? _cellPadding : 0;
			int cellSpacing = _cellSpacing > -1 ? _cellSpacing : 0;

			//do the heading
			String heading = "<TABLE BORDER=\"" + _border + "\" CELLSPACING=\"" + cellSpacing + "\" CELLPADDING=\"" + cellPadding + "\"";
			if (_containerWidth > -1) {
				heading += " WIDTH=\"" + _containerWidth;
				if (_sizeOption == SIZE_PERCENT)
					heading += "%";
				heading += "\"";
			}
			heading += ">";
			p.println(heading);
			String headingColor = "";
			if (_headingBackgroundColor != null)
				headingColor = " BGCOLOR=\"" + _headingBackgroundColor + "\"";
			if (_backgroundColor != null)
				boxColor = " BGCOLOR=\"" + _backgroundColor + "\"";

			String headingStyle = "";
			if (_headingClassname != null)
				headingStyle = " class=\"" + _headingClassname + "\"";
			p.println("<TR><TD" + headingColor + " " + headingStyle + "><TABLE WIDTH=\"100%\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
			p.println("<TR><TD" + headingColor + " ALIGN=\"LEFT\">" + _fontStartTag + _headingCaption + _fontEndTag + "</TD><TD" + headingColor + " ALIGN=\"RIGHT\" NOWRAP>");
			if (_componentsVec != null) {
				HtmlComponent h = null;
				int componentsSize = _componentsVec.size();
				for (int i = 0; i < componentsSize; i++) {
					h = (HtmlComponent) _componentsVec.elementAt(i);
					if (h != null)
						h.generateHTML(p, rowNo);
				}
			}
			p.println("</TD></TR></TABLE></TD></TR>");
		}
		//do the box
		String bodyStyle = "";
		if (_bodyClassname != null)
			bodyStyle = " class=\"" + _bodyClassname + "\"";
		p.println("<TR " + bodyStyle + "><TD" + boxColor + ">");
		
		if (_box != null)
			_box.generateHTML(p, rowNo);
		p.println("</TD></TR></TABLE>");
		if (_center)
			p.print("<CENTER>");
	}
	public void generateInitialHTML(PrintWriter p) throws Exception {
		if (!_visible)
			return;
		if (_componentsVec != null) {
			HtmlComponent h = null;
			int componentsSize = _componentsVec.size();
			for (int i = 0; i < componentsSize; i++) {
				h = (HtmlComponent) _componentsVec.elementAt(i);
				if (h != null)
					h.generateInitialHTML(p);
			}
		}
		if (_box != null)
			_box.generateInitialHTML(p);
	}
	/**
	 * This method gets the border width for component
	 */
	public int getBorder() {
		return _border;
	}
	/**
	 * This method will get the component that will be displayed in the display box
	 */
	public HtmlContainer getBoxComponent() {
		return _box;
	}
	/**
	 * Gets the cell padding for the table.
	 */
	public int getCellPadding() {
		return _cellPadding;
	}
	/**
	 * Gets the cell spacing for the table.
	 */
	public int getCellSpacing() {
		return _cellSpacing;
	}
	/**
	 * This method will return a list of all components in the container.
	 */
	public Enumeration getComponents() {
		Vector v = new Vector();
		if (_componentsVec != null) {
			int componentsSize = _componentsVec.size();
			for (int i = 0; i < componentsSize; i++)
				v.addElement(_componentsVec.elementAt(i));
		}
		if (_box != null)
			v.addElement(_box);
		return v.elements();
	}
	/**
	 * This method will return the font type used by this control.
	 */
	public String getFont() {
		return _font;
	}
	/**
	 * This method gets the background color for the heading on the component
	 */
	public String getHeadingBackgroundColor() {
		return _headingBackgroundColor;
	}
	/**
	 * This method returns the heading caption for the component.
	 */
	public String getHeadingCaption() {
		return _headingCaption;
	}
	/**
	 * This method returns the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getSizeOption() {
		return _sizeOption;
	}
	/**
	 * This method returns the property theme for the component.
	 * @param theme The theme to use.
	 */
	public String getTheme() {
		return _theme;
	}
	/**
	 * This method returns the width of the table.
	 */
	public int getWidth() {
		return _containerWidth;
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		if (_componentsVec != null) {
			int componentsSize = _componentsVec.size();
			for (int i = 0; i < componentsSize; i++) {
				if (((HtmlComponent) _componentsVec.elementAt(i)).processParms(parms, rowNo))
					_submit = (HtmlComponent) _componentsVec.elementAt(i);
			}
		}
		if (_box != null) {
			if (_box.processParms(parms, rowNo))
				_submit = _box;
		}
		if (_submit != null)
			return true;
		else
			return false;
	}
	/**
	 * This method will remove a component (usually a button) from the heading of the box.
	 */
	public void removeHeadingComponent(HtmlComponent h) {
		remove(h);
	}
	/**
	 * This method sets the border width for component
	 */
	public void setBorder(int border) {
		_border = border;
	}
	/**
	 * This method will set the component that will be displayed in the display box
	 */
	public void setBoxComponent(HtmlContainer cont) {
		_box = cont;
		cont.setParent(this);
	}
	/**
	 * Sets the cell padding for the table.
	 */
	public void setCellPadding(int value) {
		_cellPadding = value;
	}
	/**
	 * Sets the cell spacing for the table.
	 */
	public void setCellSpacing(int value) {
		_cellSpacing = value;
	}
	/**
	 * This method will load the font start and end tags from the page properties object.
	 */
	public void setFont(String font) {
		_font = font;
		Props p = getPage().getPageProperties();
		_fontStartTag = p.getThemeProperty(_theme, font + Props.TAG_START);
		_fontEndTag = p.getThemeProperty(_theme, font + Props.TAG_END);
	}
	/**
	 * This method sets the end font tag for the component.
	 */
	public void setFontEndTag(String value) {
		_fontEndTag = value;
	}
	/**
	 * This method sets the start font tag for the component.
	 */
	public void setFontStartTag(String value) {
		_fontStartTag = value;
	}
	/**
	 * This method sets the background color for the heading on the component
	 */
	public void setHeadingBackgroundColor(String color) {
		_headingBackgroundColor = color;
	}
	/**
	 * This method sets the text for the heading on the component
	 */
	public void setHeadingCaption(String text) {
		_headingCaption = text;
	}
	/**
	 * This method sets the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setSizeOption(int option) {
		_sizeOption = option;
	}
	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {

		Props props = getPage().getPageProperties();

		_fontStartTag = props.getThemeProperty(theme, _font + Props.TAG_START);
		_fontEndTag = props.getThemeProperty(theme, _font + Props.TAG_END);
		_headingBackgroundColor = props.getThemeProperty(theme, Props.DISPLAY_BOX_HEADING_BACKGROUND_COLOR);
		_backgroundColor = props.getThemeProperty(theme, Props.DISPLAY_BOX_BACKGROUND_COLOR);
		_border = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_BORDER);
		_cellPadding = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_CELLPADDING);
		_cellSpacing = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_CELLSPACING);

		_theme = theme;
	}
	/**
	 * This method sets the minimum width of the table in either pixels or percent depending on size option.
	 */
	public void setWidth(int width) {
		_containerWidth = width;
	}
	/**
	 * This method sets the border width for component
	 */
	public void suppressHeading(boolean supHead) {
		_suppressHeading = supHead;
	}
	/**
	 * @returns the style sheet class name used for the heading
	 */
	public String getHeadingClassname() {
		return _headingClassname;
	}

	/**
	 * @param sets the style sheet class name for the heading
	 */
	public void setHeadingClassname(String string) {
		_headingClassname = string;
	}
	
	/**
	 * @returns the style sheet class name used for the body
	 */
	public String getBodyClassname() {
		return _bodyClassname;
	}

	/**
	 * @param sets the style sheet class name for the body
	 */
	public void setBodyClassname(String string) {
		_bodyClassname = string;
	}

}
