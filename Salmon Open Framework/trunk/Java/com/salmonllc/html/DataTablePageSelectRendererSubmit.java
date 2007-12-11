/*
 * Created on Apr 5, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.html;

import com.salmonllc.properties.Props;

/**
 * A renderer for the Page Selector for JSPDataTable, HtmlDataTable, HtmlDataTable2D and JspList, that renders as submit buttons
 */
public class DataTablePageSelectRendererSubmit extends DataTablePageSelectRenderer {
	protected String _font=Props.FONT_BUTTON;
		
	public String generateRowSelector(HtmlComponent comp, String theme, String pageLabel, String pageOfLabel, int page, int firstSubmitButton, int noButtons, int maxPagingButtons, String imageURL) {
		Props pr = comp.getPage().getPageProperties();
		String fontStart = pr.getThemeProperty(theme, _font + Props.TAG_START);
		String fontEnd = pr.getThemeProperty(theme, _font + Props.TAG_END);

		String buttonBgColor = pr.getThemeProperty(theme, Props.BUTTON_BG_COLOR);
		String buttonFontStyle = pr.getThemeProperty(theme, Props.BUTTON_FONT_STYLE);
		String styleClass = pr.getThemeProperty(theme, Props.BUTTON_STYLE_CLASS);
		String style = "";
		if (buttonBgColor != null || buttonFontStyle != null) {
			style = " style=\"";
			if (buttonBgColor != null)
				style += "background-color:" + buttonBgColor + ";";
			if (buttonFontStyle != null)
				style += buttonFontStyle;
			style += "\"";
		}

		if (styleClass != null)
			styleClass = " class=\"" + styleClass + "\"";

		if (fontStart == null) {
			fontStart = "";
			fontEnd = "";
		}
		StringBuffer sb = new StringBuffer();

		sb.append(fontStart + pageLabel + ": " + page + " " + pageOfLabel + " " + noButtons + "&nbsp;");

		if (noButtons > maxPagingButtons && firstSubmitButton > 0) {
			if (noButtons > 2)
				sb.append("<INPUT TYPE=\"SUBMIT\" name=\"" + comp.getFullName() + "_page_first\" value=\"<<\"" + style + styleClass + ">");
			sb.append("<INPUT TYPE=\"SUBMIT\" name=\"" + comp.getFullName() + "_page_prior\" value=\"<\"" + style + styleClass + ">");
		}
		for (int i = 0;(i < noButtons) && (i < maxPagingButtons); i++) {
			sb.append("<INPUT TYPE=\"SUBMIT\" name=\"" + comp.getFullName() + "_page_" + (i + firstSubmitButton) + "\" value=\"" + (firstSubmitButton + i + 1) + "\"" + style + styleClass + ">");
		}
		if ((firstSubmitButton + (maxPagingButtons == 0 ? 1 : maxPagingButtons)) < noButtons) {
			sb.append("<INPUT TYPE=\"SUBMIT\" name=\"" + comp.getFullName() + "_page_next\" value=\">\"" + style + styleClass + ">");
			if (noButtons > 2)
				sb.append("<INPUT TYPE=\"SUBMIT\" name=\"" + comp.getFullName() + "_page_last\" value=\">>\"" + style + styleClass + ">");
		}
		sb.append(fontEnd);
		return sb.toString();
	}
}