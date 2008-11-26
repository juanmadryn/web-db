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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspTableCell.java $
//$Author: Dan $
//$Revision: 19 $
//$Modtime: 10/21/04 2:44p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlRowHighlighter;
import com.salmonllc.html.HtmlTable;
/**
 * This class is used to represent on cell &ltTD&gt; in an JSP Table
 */
public class JspTableCell extends JspContainer {
	private String _align = HtmlTable.ALIGN_NONE;
	private String _vAlign = HtmlTable.VALIGN_NONE;
	private String _bgcolor = null;
	private boolean _wrap = true;
	private int _colSpan = 1;
	private int _rowSpan = 1;
	//
	private String _cellWidth = null;
	private String _cellHeight = null;
	//
	private String _style = null;
	private String _onMouseOut = null;
	private String _onMouseOver = null;
	private String _onClick = null;
	private JspTableCell _sortTd = null;
	private String _clickSortAnchorClass = null;
	private boolean _clickSort = true;

	public JspTableCell(String name, com.salmonllc.html.HtmlPage p) {
		super(name, p);
	}
	public void generateHTML(TagWriter writer, String content, String defaultBackgroundColor, String clickSortTable, String defaultStyle, int rowNo) throws java.io.IOException {
		if (!getVisible())
			return;		
		JspDataTable tab = getParentDataTable();
		
		StringBuffer sb = new StringBuffer();

		sb.append("<TD");

		boolean underline = (clickSortTable != null && _sortTd != null && !writer.getDreamWeaverConv() && _clickSort);

		if (getAlign() != null)
			if (!getAlign().equals(HtmlTable.ALIGN_NONE))
				sb.append(" ALIGN=\"" + getAlign() + "\"");

		if (getVertAlign() != null)
			if (!getVertAlign().equals(HtmlTable.VALIGN_NONE))
				sb.append(" VALIGN=\"" + getVertAlign() + "\"");		
		String bgColor = "";
		if (getBackgroundColor() != null) {
			if (!getBackgroundColor().equals("")) 
				bgColor = getBackgroundColor();
		} else if (defaultBackgroundColor != null) {
			if (!defaultBackgroundColor.equals("")) 
				bgColor = defaultBackgroundColor;
		}
		
		if (tab != null && tab.getRowHighlighter() != null)
			sb.append(" BGCOLOR=\"" + tab.getRowHighlighter().getBgColorForRow(rowNo,bgColor,this) + "\"");
		else
			sb.append(" BGCOLOR=\"" + bgColor + "\"");
		
		if (getColSpan() != 1)
			sb.append(" COLSPAN=\"" + getColSpan() + "\"");

		if (getRowSpan() != 1)
			sb.append(" ROWSPAN=\"" + getRowSpan() + "\"");

		if (getHeight() != null)
			sb.append(" HEIGHT=\"" + getHeight() + "\"");

		if (getOnClick() != null)
			sb.append(" ONCLICK=\"" + getOnClick() + "\"");

		if (getOnMouseOver() != null)
			sb.append(" ONMOUSEOVER=\"" + getOnMouseOver() + "\"");

		if (getOnMouseOut() != null)
			sb.append(" ONMOUSEOUT=\"" + getOnMouseOut() + "\"");

		if (getWidth() != null)
			sb.append(" WIDTH=\"" + getWidth() + "\"");

		if (!getWrap())
			sb.append(" NOWRAP");

		if (_style != null)
			sb.append(" CLASS=\"" + getStyle() + "\"");
		else if (defaultStyle != null)
			sb.append(" CLASS=\"" + defaultStyle + "\"");
		
		
		sb.append(" ID=\"" + getName() + "\"");
		
		sb.append(">");

	
		if (tab != null && tab.getRowHighlighter() != null) {
			HtmlComponent comp=getParent();
			if (comp instanceof JspContainer) {
				JspContainer cont=(JspContainer) comp;
				int ndx = cont.getComponentIndex(this);
				int type = cont.getComponentType(ndx);
				if (type == TYPE_ROW) {
					int trNo=-1;
					if (comp instanceof JspTableRow) {
						int cnt=tab.getComponentCount();
						for (int i=0; i < cnt; i++) {
							HtmlComponent test=tab.getComponent(i);
							if (test instanceof JspTableRow && tab.getComponentType(i)==TYPE_ROW)
								trNo++;
							if (test == comp)
								break;
						}	
					if (trNo == -1)
						trNo=0;
					}
					HtmlRowHighlighter rh = tab.getRowHighlighter();
					if (!writer.getDreamWeaverConv())
						sb.append(rh.generateHtmlForTd(rowNo, trNo, bgColor));
				}
			}	
		}

		if (underline) {
			sb.append("<A HREF=\"javascript:" + clickSortTable + "ClickSort('" + _sortTd.getName() + "');\"");
			if (_clickSortAnchorClass != null) {
				sb.append(" CLASS=\"" + _clickSortAnchorClass + "\"");
			}
			sb.append(">");
		}

		writer.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
		writer.print(content, TagWriter.TYPE_CONTENT);

		sb.setLength(0);
		if (underline)
			sb.append("</A>");
		sb.append("</TD>");

		writer.print(sb.toString(), TagWriter.TYPE_END_TAG);

	}
	/**
	 * This method gets the alignment for a table cell
	 */
	public String getAlign() {
		return _align;
	}
	/**
	 * This method gets the background color for a table cell
	 */
	public String getBackgroundColor() {
		return _bgcolor;
	}
	/**
	 * This method gets the column span for a table cell
	 */
	public int getColSpan() {
		return _colSpan;
	}
	/**
	 * This method gets the height of the table cell.
	 */
	public String getHeight() {
		return _cellHeight;
	}
	/**
	 * Use this method to get the javascript that will be executed when the user
	 * clicks on one of the components in the link.
	 */
	public String getOnClick() {
		return _onClick;
	}
	/**
	 * Use this method to get the javascript that will be executed when the
	 * mouse passes over out of all the components
	 */
	public String getOnMouseOut() {
		return _onMouseOut;
	}
	/**
	 * Use this method to get the javascript that will be executed when the
	 * mouse passes over any component in the link
	 */
	public String getOnMouseOver() {
		return _onMouseOver;
	}
	/**
	 * This method gets the column span for a table cell
	 */
	public int getRowSpan() {
		return _rowSpan;
	}
	/**
	 * This method returns the style for a table cell
	 */
	public String getStyle() {
		return _style;
	}
	/**
	 * This method gets the vertival alignment for a table cell
	 */
	public String getVertAlign() {
		return _vAlign;
	}
	/**
	 * This method gets the width of the table cell.
	 */
	public String getWidth() {
		return _cellWidth;
	}
	/**
	 * This method gets whether or not the items in the cell will word wrap
	 */
	public boolean getWrap() {
		return _wrap;
	}
	/**
	 * This method sets the alignment for a table cell
	 */
	public void setAlign(String value) {
		_align = value;
	}
	/**
	 * This method sets the background color for a table cell
	 */
	public void setBackgroundColor(String color) {
		_bgcolor = color;
	}
	/**
	 * This method sets which JspTableCell will be sorted when the user clicks
	 * on the text in this cell. Generally it is only called from other
	 * framework classes and is only relavant in the JSPDataTable
	 */

	public void setCellToSort(JspTableCell cell) {
		_sortTd = cell;
	}
	/**
	 * This method sets the column span for a table cell
	 * 
	 * @param colSpan
	 *            the number of columns to span
	 */
	public void setColSpan(int colSpan) {
		_colSpan = colSpan;
	}
	/**
	 * This method sets the height of the table cell. Overerides table column
	 * height.
	 * 
	 * @param cellHeight
	 *            the height of the cell
	 */
	public void setHeight(String cellHeight) {
		_cellHeight = cellHeight;
	}
	/**
	 * Use this method to set the javascript that will be executed when the user
	 * clicks on one of the components in the link.
	 */
	public void setOnClick(String onClick) {
		_onClick = onClick;
	}
	/**
	 * Use this method to set the javascript that will be executed when the
	 * mouse passes over out of all the components
	 */
	public void setOnMouseOut(String onMouseOut) {
		_onMouseOut = onMouseOut;
	}
	/**
	 * Use this method to set the javascript that will be executed when the
	 * mouse passes over any component in the link
	 */
	public void setOnMouseOver(String onMouseOver) {
		_onMouseOver = onMouseOver;
	}
	/**
	 * This method sets the column span for a table cell
	 * 
	 * @param colSpan
	 *            the number of columns to span
	 */
	public void setRowSpan(int rowSpan) {
		_rowSpan = rowSpan;
	}
	/**
	 * This method was created in VisualAge.
	 * 
	 * @param style
	 *            com.salmonllc.html.HtmlStyle
	 */
	public void setStyle(String style) {
		_style = style;
	}
	/**
	 * This method sets the vertical alignment for a table cell
	 */

	public void setVertAlign(String value) {
		_vAlign = value;
	}
	/**
	 * This method sets the width of the table cell. Overerides table column
	 * width.
	 * 
	 * @param cellWidth
	 *            the width of the cell
	 */
	public void setWidth(String cellWidth) {
		_cellWidth = cellWidth;
	}
	/**
	 * This method sets whether or not the contents of the cell should word wrap
	 */

	public void setWrap(boolean value) {
		_wrap = value;
	}

	public void setClickSortAnchorClass(String sClassName) {
		_clickSortAnchorClass = sClassName;
	}
	/**
	 * @return Returns the clickSort.
	 */
	public boolean isClickSort() {
		return _clickSort;
	}
	/**
	 * @param clickSort
	 *            The clickSort to set.
	 */
	public void setClickSort(boolean clickSort) {
		_clickSort = clickSort;
	}

	/**
	 *  @return the parent datatable that this component is in
	 */
	public JspDataTable getParentDataTable() {
		HtmlComponent parent = getParent();
		while (parent != null) {
			if (parent instanceof JspDataTable)
				return (JspDataTable) parent;
			parent = parent.getParent();
		}
		return null;
	}
}