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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlInlineFrame.java $
//$Author: Dan $
//$Revision: 14 $
//$Modtime: 12/19/03 11:33a $
/////////////////////////

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class will generate the html an InLine Frame.
 */
public class HtmlInlineFrame extends HtmlContainer {
	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;

	int _width = -1;
	int _height = -1;
	int _border = 0;
	int _heightSizeOption = SIZE_PIXELS;
	int _widthSizeOption = SIZE_PIXELS;

	String _src="";

	/**
	 *Constructs a new InLine Frame
	 * @param name java.lang.String
	 * @param p com.salmonllc.html.HtmlPage
	 */
	public HtmlInlineFrame(String name, com.salmonllc.html.HtmlPage p) {
		super(name, p);
	}

	/**
	 * This method should not be used. Use setFrameComponent instead.
	 */
	public void add(HtmlComponent comp) {
		return;
	}

	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		generateHTML(p, -999, rowNo);
	}

	public void generateHTML(PrintWriter p, int rowStart, int rowEnd) throws Exception {
		if (_componentsVec == null)
			return;
		if (!_visible)
			return;
		if (_center)
			p.print("<CENTER>");
		if (getPage().getUseIFrames()) {
			String name = getPage().getClass().getName();
			int pos = name.lastIndexOf(".");
			if (pos > -1)
				name = name.substring(pos + 1);
			name += "-" + getName() + "FrameComponent";
			if( _src!=null && !_src.equals("") )
				name = _src;
			p.print("<IFRAME NAME=\"" + getFullName() + "\" MARGINWIDTH=\"0\" MARGINHEIGHT=\"0\" SRC=\"" + name + "\"");
			p.print(" FRAMEBORDER=\"" + _border + "\"");
			String heading = "";
			if (_width > -1) {
				heading += " WIDTH=\"" + _width;
				if (_widthSizeOption == SIZE_PERCENT)
					heading += "%";
				heading += "\"";
			}
			if (_height > -1) {
				heading += " HEIGHT=\"" + _height;
				if (_heightSizeOption == SIZE_PERCENT)
					heading += "%";
				heading += "\"";
			}
			p.print(heading);
			p.println("></IFRAME>");
		} else {
			HtmlComponent h = null;
			int componentsSize = _componentsVec.size();
			for (int i = 0; i < componentsSize; i++) {
				h = (HtmlComponent) _componentsVec.elementAt(i);
				if (h != null) {
					if (rowStart == -999)
						h.generateHTML(p, rowEnd);
					else
						h.generateHTML(p, rowStart, rowEnd);
				}
			}
		}
		if (_center)
			p.print("</CENTER>");
	}

	public void generateInitialHTML(PrintWriter p) throws Exception {
		if (!_visible || _componentsVec == null)
			return;
		if (!getPage().getUseIFrames()) {
			HtmlComponent h = null;
			int componentsSize = _componentsVec.size();
			for (int i = 0; i < componentsSize; i++) {
				h = (HtmlComponent) _componentsVec.elementAt(i);
				if (h != null)
					h.generateInitialHTML(p);
			}
		}
	}

	/**
	 * Gets the border thickness for the frame.
	 */
	public int getBorder() {
		return _border;
	}

	/**
	 * This method returns the component in the Inline Frame.
	 */
	public HtmlComponent getFrameComponent() {
		if (_componentsVec == null)
			return null;
		else {
			if (_componentsVec.size() == 0)
				return null;

			Object o = _componentsVec.elementAt(0);
			if (o == null)
				return null;
			else
				return (HtmlComponent) o;

		}

	}

	/**
	 * This method gets the minimum height of the frame.
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * This method returns the size option for the height of the iframe. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getHeightSizeOption() {
		return _heightSizeOption;
	}

	/**
	 * This method returns the width of the frame.
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * This method returns the size option for the width of the iframe. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getWidthSizeOption() {
		return _widthSizeOption;
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {

		if (getPage().getUseIFrames() && getPage().getSubPageName() != getFullName())
			return false;

		return super.processParms(parms, rowNo);
	}

	/**
	 * Sets the border width for the table.
	 */
	public void setBorder(int border) {
		_border = border;
	}

	/**
	 * This method sets the component that will appear within the frame.
	 */
	public void setFrameComponent(HtmlComponent comp) {
		if (_componentsVec == null) {
			_componentsVec = new Vector();
		} else {
			_componentsVec.removeAllElements();
		}

		getPage().registerSubPage(getName() + "FrameComponent", comp);
		_componentsVec.addElement(comp);
		comp.setParent(this);
	}

	/**
	 * This method sets the minimum height of the frame.
	 */
	public void setHeight(int height) {
		_height = height;
	}

	/**
	 * This method sets the size option for the height of the iframe. Valid values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setHeightSizeOption(int option) {
		_heightSizeOption = option;
	}

	/**
	 * This method sets the width of the frame in either pixels or percent depending on size option.
	 */
	public void setWidth(int width) {
		_width = width;
	}

	/**
	 * This method sets the size option for the width of the iframe. Valid values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setWidthSizeOption(int option) {
		_widthSizeOption = option;
	}
	/**
	 * @returns the source of the content of the inline frame
	 */
	public String getSrc() {
		return _src;
	}

	/**
	 * Sets the source URL for the inline frame
	 */
	public void setSrc(String string) {
		_src = string;
	}

}
