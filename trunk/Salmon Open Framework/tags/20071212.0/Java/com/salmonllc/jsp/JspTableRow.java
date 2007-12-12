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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspTableRow.java $
//$Author: Dan $ 
//$Revision: 18 $ 
//$Modtime: 10/21/04 2:40p $ 
/////////////////////////

import com.salmonllc.html.*;

/**
 * This class is used to represent on row &ltTR&gt; in an JSP Table
 */
public class JspTableRow extends JspContainer { 
	private String _align = HtmlTable.ALIGN_NONE;
	private String _vAlign = HtmlTable.VALIGN_NONE;
	private String _bgcolor = null;
	private String _style = null;
	private String _id = null;
	private String _onmouseover = null;
	private String _onmouseout = null;
	private String _onmousedown = null;
	private String _onclick = null;
	private String _inLineStyle = null;

	
	public JspTableRow(String name, com.salmonllc.html.HtmlPage p) {
		super(name, p);
	}
	public void generateHTML(TagWriter t, String content, String defaultBackgroundColor, String defaultStyleClass, int rowNo) throws java.io.IOException {
		if (!getVisible())
			return;
		
		JspDataTable tab=getParentDataTable();
		HtmlRowHighlighter rowHighligher=null;
		if (tab != null)
			rowHighligher=tab.getRowHighlighter();
			
		StringBuffer sb = new StringBuffer();

		sb.append("<TR");

		if (getAlign() != null) {
			if (!getAlign().equals(HtmlTable.ALIGN_NONE))
				sb.append(" ALIGN=\"" + getAlign() + "\"");
		}
		if (getVertAlign() != null) {
			if (!getVertAlign().equals(HtmlTable.VALIGN_NONE))
				sb.append(" VALIGN=\"" + getVertAlign() + "\"");
		}
		
		if (getStyle() != null) 
			sb.append(" CLASS=\"" + getStyle() + "\"");
		else if (defaultStyleClass != null) 
			sb.append(" CLASS=\"" + defaultStyleClass + "\"");	
			
		if (getInLineStyle() != null)
			sb.append(" STYLE=\"" + getInLineStyle() + "\"");
		
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

				
		if (getOnmouseover() != null) {
			sb.append(" onMouseOver=\"" + getOnmouseover() + "\"");
		}
		if (getOnmouseout() != null) {
			sb.append(" onMouseOut=\"" + getOnmouseout() + "\"");
		}
		
		String onMouseDown="";
		if (getOnmousedown() != null) 
			onMouseDown+=getOnmousedown();
		if (rowHighligher != null)
			onMouseDown+=rowHighligher.generateJavaScriptForTrOnMouseDown(rowNo,this);
		if (onMouseDown.length()>0) 
			sb.append(" onMouseDown=\"" + onMouseDown + "\"");

		String onClick="";
		if (getOnclick() != null) 
			onClick+=getOnclick();
		if (rowHighligher != null)
			onClick+=rowHighligher.generateJavaScriptForTrOnClick();
		if (onClick.length() > 0) 
			sb.append(" onClick=\"" + onClick + "\""); 
		

		sb.append(">");

		t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
		t.print(content, TagWriter.TYPE_CONTENT);
		t.print("</TR>", TagWriter.TYPE_END_TAG);

	}

	/**
	 * Returns the alignment of the row
	 */
	public String getAlign() {
		return _align;
	}
	/**
	 * Returns the background color of the row
	 */
	public String getBackgroundColor() {
		return _bgcolor;
	}
	/**
	 * Returns the style of the row
	 */
	public String getStyle() {
		return _style;
	}
	/**
	 * Returns the vertical alignment of the row
	 */
	public String getVertAlign() {
		return _vAlign;
	}
	/**
	 * Sets the alignment of the row
	 */
	public void setAlign(String value) {
		_align = value;
	}
	/**
	 * Sets the background color of the row
	 */

	public void setBackgroundColor(String color) {
		_bgcolor = color;
	}
	/**
	 * Sets the style of the row
	 */

	public void setStyle(String style) {
		_style = style;
	}
	/**
	 * Sets the vertical alignment of the row
	 */

	public void setVertAlign(String value) {
		_vAlign = value;
	}

	public void setId(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	/**
	 * @return
	 */
	public String getOnclick() {
		return _onclick;
	}

	/**
	 * @return
	 */
	public String getOnmouseout() {
		return _onmouseout;
	}

	/**
	 * @return
	 */
	public String getOnmouseover() {
		return _onmouseover;
	}

	/**
	 * @param string
	 */
	public void setOnclick(String string) {
		_onclick = string;
	}

	/**
	 * @param string
	 */
	public void setOnmouseout(String string) {
		_onmouseout = string;
	}

	/**
	 * @param string
	 */
	public void setOnmouseover(String string) {
		_onmouseover = string;
	}
	/**
	 * Gets the in line style of the row
	 */
	 	public String getInLineStyle() {
	 		return _inLineStyle;
	 	}
	 
	/**
	 * Sets the in line style of the row
 	 */
 	public void setInLineStyle(String string) {
 		_inLineStyle = string;
	}
	/**
	 * @return Returns the onmousedown.
	 */
	public String getOnmousedown() {
		return _onmousedown;
	}
	/**
	 * @param onmousedown The onmousedown to set.
	 */
	public void setOnmousedown(String onmousedown) {
		_onmousedown = onmousedown;
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