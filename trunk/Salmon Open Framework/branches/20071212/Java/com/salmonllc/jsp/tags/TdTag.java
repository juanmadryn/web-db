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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/TdTag.java $
//$Author: Dan $
//$Revision: 33 $
//$Modtime: 10/21/04 12:24p $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.*;
import com.salmonllc.util.MessageLog;

/**
 * This handler implements the table definition (TD) tag
 */
public class TdTag extends ContainerTag {
	private String _align;
	private String _valign;
	private String _bgcolor;
	private String _noWrap;
	private String _colSpan;
	private String _rowSpan;
	private String _width;
	private String _height;
	private String _onMouseOut;
	private String _onMouseOver;
	private String _onClick;
	private String _content;
	private String _generatedName = null;
	//private String _class = null;
	// The following are inherited from BaseBodyTag
	// private String _visible;
	//
	private String _clickSort;
	private int _colSpanNum = 1;
	private int _rowSpanNum = 1;

	/**
	 * This method creates the JSPTableCell used by the tag.
	 */

	public HtmlComponent createComponent() {
		BaseTagHelper helper = getHelper();
		DataTableTag dtt = helper.getDataTableTag(true);
		if (dtt != null) {
			JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
		}


		JspTableCell cell = new JspTableCell(getName(), helper.getController());

		cell.setAlign(_align);
		cell.setBackgroundColor(_bgcolor);
		cell.setHeight(_height);
		cell.setOnClick(_onClick);
		cell.setOnMouseOut(_onMouseOut);
		cell.setOnMouseOver(_onMouseOver);
		cell.setRowSpan(_rowSpanNum);
		cell.setColSpan(_colSpanNum);
		cell.setVertAlign(_valign);
		cell.setWidth(_width);
		cell.setStyle(getClassname());

		if (_noWrap == null)
			cell.setWrap(true);
		else if (_noWrap.toUpperCase().equals("false"))
			cell.setWrap(true);
		else
			cell.setWrap(false);

		if (_clickSort != null)
			cell.setClickSort(BaseTagHelper.stringToBoolean(_clickSort,true));
		
		if (dtt != null) {
			TagContext tc = helper.getTagContext();
			if (!(tc.isPrintingVars() || tc.isGenerateCode())) {
				if (helper.getComponentType() == JspContainer.TYPE_HEADER) {
					if (!getName().equals(_generatedName)) {
						java.util.Hashtable t = helper.getController().getComponentTable();
						t.put(_generatedName, cell);
					}
				} else if (helper.getComponentType() == JspContainer.TYPE_ROW) {
					java.util.Hashtable t = helper.getController().getComponentTable();
					JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
					String headerName = dt.getHeaderTDNameForRow(_generatedName);
					JspTableCell headerCell = (JspTableCell) t.get(headerName);
					if (headerCell != null) {
						headerCell.setCellToSort(cell);
						if (!headerCell.getName().equals(headerName))
							t.remove(headerName);
					}
				}
			}
		}

		return cell;
	}

	
	private void generateName() {
		BaseTagHelper helper = getHelper();
		DataTableTag dtt = helper.getDataTableTag(true);
		if (dtt != null) {
			JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
			_generatedName = dt.generateNextTDName(_colSpanNum, helper.getComponentType());
			int colCount = BaseTagHelper.stringToInt(_colSpan);
			if (colCount < 0)
				colCount = 1;
			dtt.incrementColCounter(colCount);
		}
		TableTag tt = helper.getTableTag(true);
		if (tt != null) {
			JspTable t = (JspTable) tt.getHelper().getComponent();
			_generatedName = t.generateNextTDName(_colSpanNum);
		}	
	}	
	
	/**
	 * This method generates the html used by the tag.
	 */

	public void generateComponentHTML(JspWriter p) throws IOException {
		String backgroundColor = null;
		String clickSortTable = null;
		String styleClass=null;
		DataTableTag dtt = getHelper().getDataTableTag(true);
		int rowNo=-1;
		if (dtt != null) {
			JspDataTable dt = (JspDataTable) dtt.getHelper().getComponent();
			backgroundColor = dtt.getCurrentBackgroundColor();
			styleClass = dtt.getCurrentStyle();
			if (dt.getClickSort())
				clickSortTable = dt.getFullName();
			rowNo=dtt.getStartRow();
		}
		TableTag tt = getHelper().getTableTag(true);
		if (tt != null) {
			JspTable t = (JspTable) tt.getHelper().getComponent();
			backgroundColor = tt.getCurrentBackgroundColor();
			styleClass = tt.getCurrentRowStyle();
			clickSortTable = t.getFullName();
		}

		JspTableCell cell = (JspTableCell) getHelper().getController().getComponent(getName());
		if (cell == null)
			return;

		TagWriter w = getTagWriter();
		w.setWriter(p);
		cell.generateHTML(w, _content, backgroundColor, clickSortTable, styleClass, rowNo);
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
	 * Use this method to get the javascript that will be executed when the user clicks on one of the components in the link.
	 */
	public String getClicksort() {
		return _clickSort;
	}

	/**
	 * This method gets the column span for a table cell
	 */
	public String getColspan() {
		return _colSpan;
	}

	/**
	 * Returns the generated name for the tag. TD's and TR's don't require a name attribute and so the system will generate one.
	 */

	public String getGeneratedName() {
		return _generatedName;
	}

	/**
	 * This method gets the height of the table cell.
	 */
	public String getHeight() {
		return _height;
	}

	/**
	 * Gets the name property for the tag
	 */

	public String getName() {
		String name = super.getName();
		if (name == null)
			return _generatedName;
		else
			return name;
	}

	/**
	 * Gets the nowrap property for the tag
	 */

	public String getNowrap() {
		return _noWrap;
	}

	/**
	 * Use this method to get the javascript that will be executed when the user clicks on one of the components in the link.
	 */
	public String getOnclick() {
		return _onClick;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over out of all the components
	 */
	public String getOnmouseout() {
		return _onMouseOut;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over any component in the link
	 */
	public String getOnmouseover() {
		return _onMouseOver;
	}

	/**
	 * This method gets the column span for a table cell
	 */
	public String getRowspan() {
		return _rowSpan;
	}

	/**
	 * Gets the vertical align property for the tag
	 */

	public String getValign() {
		return _valign;
	}

	/**
	 * This method gets the width of the table cell.
	 */
	public String getWidth() {
		return _width;
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

	/**
	 * Releases any resources used by the tag.
	 */

	public void release() {
		super.release();
		_align = null;
		_valign = null;
		_bgcolor = null;
		_noWrap = null;
		_colSpan = null;
		_rowSpan = null;
		_width = null;
		_height = null;
		_onMouseOut = null;
		_onMouseOver = null;
		_onClick = null;
		_content = null;
		_generatedName = null;
		_rowSpanNum = 1;
		_colSpanNum = 1;
		//    _class = null;
		_clickSort = null;

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
	 * Use this method to set the javascript that will be executed when the user clicks on one of the components in the link.
	 */
	public void setClicksort(String val) {
		_clickSort = val;
	}

	/**
	 * This method sets the column span for a table cell
	 * @param colSpan the number of columns to span
	 */
	public void setColspan(String colSpan) {

		try {
			_colSpanNum = Integer.parseInt(colSpan);
		} catch (Exception e) {
			_colSpanNum = 1;
		}

		_colSpan = colSpan;
	}

	/**
	 * This method sets the height of the table cell. Overerides table column height.
	 * @param cellHeight the height of the cell
	 */
	public void setHeight(String cellHeight) {
		_height = cellHeight;
	}

	/**
	 * Sets the noWrap property property for the tag
	 */

	public void setNowrap(String value) {
		_noWrap = value;
	}

	/**
	 * Use this method to set the javascript that will be executed when the user clicks on one of the components in the link.
	 */
	public void setOnclick(String onClick) {
		_onClick = onClick;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
	 */
	public void setOnmouseout(String onMouseOut) {
		_onMouseOut = onMouseOut;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
	 */
	public void setOnmouseover(String onMouseOver) {
		_onMouseOver = onMouseOver;
	}

	/**
	 * This method sets the column span for a table cell
	 * @param rowSpan the number of columns to span
	 */
	public void setRowspan(String rowSpan) {
		try {
			_rowSpanNum = Integer.parseInt(rowSpan);
		} catch (Exception e) {
			_rowSpanNum = 1;
		}

		_rowSpan = rowSpan;
	}

	/**
	 * Sets the vertical align property for the tag
	 */

	public void setValign(String value) {
		_valign = value;
	}

	/**
	 * This method sets the width of the table cell. Overerides table column width.
	 * @param cellWidth the width of the cell
	 */
	public void setWidth(String cellWidth) {
		_width = cellWidth;
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
