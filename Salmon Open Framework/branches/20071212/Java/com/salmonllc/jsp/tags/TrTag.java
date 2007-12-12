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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/TrTag.java $
//$Author: Dan $
//$Revision: 25 $
//$Modtime: 10/21/04 1:29p $
/////////////////////////

import com.salmonllc.util.MessageLog;

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * This handler implements the table definition (TD) tag
 */
public class TrTag extends ContainerTag {
	private String _align;
	private String _valign;
	private String _bgcolor;
	private String _content;
	private String _generatedName = null;
	// private String _class = null;


	/**
	 * This method creates the JSPTableRow used by the tag.
	 */

	public HtmlComponent createComponent() {
		JspTableRow row = new JspTableRow(getName(), getHelper().getController());

		row.setAlign(_align);
		row.setBackgroundColor(_bgcolor);
		row.setVertAlign(_valign);
		row.setStyle(getClassname());

		return row;
	}

	/**
	 * This method generates the html used by the tag.
	 */

	public void generateComponentHTML(JspWriter p) throws IOException {
		String backgroundColor = null;
		String styleClass = null;
		int rowNo=-1;
		
		DataTableTag dtt = getHelper().getDataTableTag(true);

		if (dtt != null) {
			JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
			backgroundColor = dtt.getCurrentBackgroundColor();
			styleClass = dtt.getCurrentStyle();
			rowNo=dtt.getStartRow();
		}
		TableTag tt = getHelper().getTableTag(true);
		if (tt != null) {
			JspTable t = (JspTable) tt.getHelper().getComponent();
			backgroundColor = tt.getCurrentBackgroundColor();
			styleClass = tt.getCurrentRowStyle();
		}

		JspTableRow row = (JspTableRow) getHelper().getController().getComponent(getName());
		if (row == null)
			return;

		TagWriter w = getTagWriter();
		w.setWriter(p);
		row.generateHTML(w, _content, backgroundColor, styleClass, rowNo);
	}

	/**
	 * Gets the align property for the tag
	 */

	public String getAlign() {
		return _align;
	}

	/**
	 * Gets the background color property for the tag
	 */
	public String getBgcolor() {
		return _bgcolor;
	}

	/**
	 * Gets the generated name property for the tag
	 */

	public String getGeneratedName() {
		return _generatedName;
	}

	/**
	 * Gets the name for the tag
	 */

	public String getName() {
		String name = super.getName();
		if (name == null)
			return _generatedName;
		else
			return name;
	}

	/**
	 * Gets the vertical align property for the tag
	 */

	public String getValign() {
		return _valign;
	}

	public boolean incrementMode() {
		try {
			_content = getBodyContentData(true);
			return false;
		} catch (Exception e) {
			MessageLog.writeErrorMessage("incrementMode()", e, this);
			return false;
		}
	}

	/**
	 * Gets the object ready to render html
	 */
	public void initMode() {
		_content = null;
	}

	public void generateName() {
		if (super.getName() != null)
			return;
		DataTableTag dtt = getHelper().getDataTableTag(true);
		if (dtt != null) {
			JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
			_generatedName = dt.generateNextTRName(getHelper().getComponentType());
			dtt.resetColCounter();
		}
		TableTag tt = getHelper().getTableTag(true);
		if (tt != null) {
			JspTable t = (JspTable) tt.getHelper().getComponent();
			_generatedName = t.generateNextTRName();
		}
	}
	/**
	 * Releases any resources used by the tag.
	 */

	public void release() {
		super.release();
		_align = null;
		_valign = null;
		_bgcolor = null;
		_generatedName = null;

	}

	/**
	 * Sets the align property for the tag
	 */

	public void setAlign(String value) {
		_align = value;
	}

	/**
	 * Sets the background color property for the tag
	 */

	public void setBgcolor(String color) {
		_bgcolor = color;
	}

	/**
	 * Sets the vertical align property for the tag
	 */

	public void setValign(String value) {
		_valign = value;
	}

	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 */
	public int getTagConvertType() {
		return CONV_CUSTOM;
	}


	public int doStartTag() throws JspException {
		generateName();
		return super.doStartTag();
	}

}
