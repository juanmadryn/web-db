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


/**
 * A renderer for the Page Selector for JSPDataTable, HtmlDataTable, HtmlDataTable2D and JspList
 */

public abstract class DataTablePageSelectRenderer {
	public abstract String generateRowSelector(HtmlComponent comp, String theme, String pageLabel, String pageOfLabel, int page, int firstSubmitButton, int noButtons, int maxPagingButtons, String imageURL);

	public static DataTablePageSelectRenderer getSubmitButtonRenderer() {
		return new DataTablePageSelectRendererSubmit();
	}

	public static DataTablePageSelectRenderer getImageButtonRenderer() {
		return new DataTablePageSelectRendererImage();
	}

	public static DataTablePageSelectRenderer getLinkRenderer() {
		return new DataTablePageSelectRendererLink();
	}

	protected void genLinkJavascript(HtmlComponent comp, StringBuffer sb) {
		sb.append("<INPUT TYPE=\"HIDDEN\" NAME=\"" + comp.getFullName() + "_page_selector\" value=\"\">");
		sb.append("<SCRIPT LANGUAGE=\"javascript\">");
		sb.append("function " + comp.getFullName() + "_selectPage (value) {");
		sb.append(comp.getFormString() + comp.getFullName() + "_page_selector.value=value;");
		sb.append(comp.getFormString() + "submit();");
		sb.append("}");
		sb.append("</SCRIPT>");

	}
}
