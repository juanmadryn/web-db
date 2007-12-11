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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/RowHighlighterTag.java $
//$Author: Dan $ 
//$Revision: 2 $ 
//$Modtime: 10/21/04 2:35p $ 
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlRowHighlighter;
import com.salmonllc.jsp.JspDataTable;

/**
 * This tag creates an Row Highlighter
 */

public class RowHighlighterTag extends BaseEmptyTag {
	private String _highlightcolor;
	private String _multiple;
	private String _truevalue;
	private String _falsevalue;
	private String _datasource;
	private String _theme;

	public HtmlComponent createComponent() {
		HtmlRowHighlighter comp = new HtmlRowHighlighter(getName(), getHelper().getController());
		comp.setTheme(_theme);
		if (_highlightcolor != null)
			comp.setHighlightColor(_highlightcolor);
		if (_multiple != null)
			comp.setMultiple(BaseTagHelper.stringToBoolean(_multiple));
		if (_truevalue != null)
			comp.setTrueValue(_truevalue);
		if (_falsevalue != null)
			comp.setFalseValue(_falsevalue);
		if (_datasource != null)
			comp.setDataSource(_datasource);
		
		return comp;
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.tags.BaseEmptyTag#afterInit(com.salmonllc.html.HtmlComponent)
	 */
	public void afterInit(HtmlComponent comp) {
		super.afterInit(comp);
		HtmlComponent parent=comp.getParent();
		while (parent != null) {
			if (parent instanceof JspDataTable) { 
				((JspDataTable)parent).setRowHighlighter((HtmlRowHighlighter)comp);
				return;
			}	
			parent=parent.getParent();	
		}	
	}
	
	public void release() {
		super.release();
		_highlightcolor = null;
		_multiple = null;
		_truevalue = null;
		_falsevalue = null;
		_datasource = null;
		_theme=null;
	}

	/**
	 * @param datasource
	 *            The datasource to set.
	 */
	public void setDatasource(String datasource) {
		_datasource = datasource;
	}
	/**
	 * @param falsevalue
	 *            The falsevalue to set.
	 */
	public void setFalsevalue(String falsevalue) {
		_falsevalue = falsevalue;
	}
	/**
	 * @param highlightcolor
	 *            The highlightcolor to set.
	 */
	public void setHighlightcolor(String highlightcolor) {
		_highlightcolor = highlightcolor;
	}
	/**
	 * @param multiple
	 *            The multiple to set.
	 */
	public void setMultiple(String multiple) {
		_multiple = multiple;
	}
	/**
	 * @param truevalue
	 *            The truevalue to set.
	 */
	public void setTruevalue(String truevalue) {
		_truevalue = truevalue;
	}
	/**
	 * @param theme The theme to set.
	 */
	public void setTheme(String theme) {
		_theme = theme;
	}
}