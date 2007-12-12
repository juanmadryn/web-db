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

import com.salmonllc.properties.Props;

/**
 * The default render for data table rows per page bar
 *
 */
public class DataTableRowsPerPageRendererDefault extends DataTableRowsPerPageRenderer{
	protected String _font=Props.FONT_BUTTON;
	
	public String generateRowSelector(HtmlComponent comp, String theme, String totalRowsLabel, String rowsPerPageLabel, String summaryRowText, int rowsPerPage, int rowCount) {
		Props pr = comp.getPage().getPageProperties();
		String fontStart = pr.getThemeProperty(theme, _font + Props.TAG_START);
		String fontEnd = pr.getThemeProperty(theme, _font + Props.TAG_END);
		if (fontStart == null) {
			fontStart = "";
			fontEnd = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<INPUT TYPE=\"HIDDEN\" name=\"" + comp.getFullName() + "_rows_per_page_hidden\" value=\"-1\">");
		sb.append("<SCRIPT>");
		sb.append("function " + comp.getFullName() + "_setRowsPerPage(rpp) {");
		sb.append(comp.getFormString() + comp.getFullName() + "_rows_per_page_hidden.value = rpp;");
		sb.append(comp.getFormString() + "submit();");
		sb.append("}");
		sb.append("</SCRIPT>");
		sb.append(fontStart);
		sb.append(totalRowsLabel + ": " + rowCount + "&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(rowsPerPageLabel + ":&nbsp;");
		sb.append("<SELECT name=\"" + comp.getFullName() + "row_per_page_dd\" onChange=\"javascript:" + comp.getFullName() + "_setRowsPerPage(options[selectedIndex].text);\">");
		if (rowsPerPage != 10 && rowsPerPage != 20 && rowsPerPage != 50 && rowsPerPage != 100 && rowsPerPage != 500 && rowsPerPage != 1000)
			sb.append("<OPTION SELECTED>" + rowsPerPage);
		sb.append("<OPTION" + (rowsPerPage == 10 ? " SELECTED" : "") + "> 10");
		sb.append("<OPTION" + (rowsPerPage == 20 ? " SELECTED" : "") + "> 20");
		sb.append("<OPTION" + (rowsPerPage == 50 ? " SELECTED" : "") + "> 50");
		sb.append("<OPTION" + (rowsPerPage == 100 ? " SELECTED" : "") + "> 100");
		sb.append("<OPTION" + (rowsPerPage == 500 ? " SELECTED" : "") + "> 500");
		sb.append("<OPTION" + (rowsPerPage == 1000 ? " SELECTED" : "") + "> 1000");
		sb.append("</SELECT>");
		sb.append(summaryRowText == null ? "" : "&nbsp;&nbsp;&nbsp;&nbsp;" + summaryRowText);
		sb.append(fontEnd);
		return sb.toString();
	}
}
