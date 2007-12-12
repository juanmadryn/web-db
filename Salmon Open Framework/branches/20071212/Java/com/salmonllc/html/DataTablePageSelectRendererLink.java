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
 * A renderer for the Page Selector for JSPDataTable, HtmlDataTable, HtmlDataTable2D and JspList, that renders as underline links
 */
public class DataTablePageSelectRendererLink extends DataTablePageSelectRenderer {
	protected String _font=Props.FONT_BUTTON;
		
	public String generateRowSelector(HtmlComponent comp, String theme, String pageLabel, String pageOfLabel, int page, int firstSubmitButton, int noButtons, int maxPagingButtons, String imageURL) {
			Props pr = comp.getPage().getPageProperties();
			String fontStart = pr.getThemeProperty(theme, Props.FONT_BUTTON + Props.TAG_START);
			String fontEnd = pr.getThemeProperty(theme, Props.FONT_BUTTON + Props.TAG_END);
			if (fontStart == null) {
				fontStart = "";
				fontEnd = "";
			}
			String linkStart = pr.getThemeProperty(theme, Props.FONT_LINK + Props.TAG_START);
			String linkEnd = pr.getThemeProperty(theme, Props.FONT_LINK + Props.TAG_END);
			StringBuffer sb = new StringBuffer();

			sb.append(fontStart + pageLabel + ": " + page + " " + pageOfLabel + " " + noButtons + "&nbsp;");
			genLinkJavascript(comp, sb);

			if (noButtons > maxPagingButtons && firstSubmitButton > 0) {
				if (noButtons > 2)
					sb.append("<A HREF=\"javascript:" + comp.getFullName() + "_selectPage('first');\">" + linkStart + "&lt;&lt;" + linkEnd + "</A>&nbsp;"); //FIRST
				sb.append("<A HREF=\"javascript:" + comp.getFullName() + "_selectPage('prior');\">" + linkStart + "&lt;" + linkEnd + "</A>&nbsp;"); //PRIOR
			}
			for (int i = 0; (i < noButtons) && (i < maxPagingButtons); i++) {
				int button = i + firstSubmitButton + 1;
				if (button == page)
					sb.append(linkStart + button + linkEnd + "&nbsp;");
				else
					sb.append("<A HREF=\"javascript:" + comp.getFullName() + "_selectPage('" + (i + firstSubmitButton) + "');\">" + linkStart + (i + firstSubmitButton + 1) + linkEnd + "</A>&nbsp;");
			}
			if ((firstSubmitButton + (maxPagingButtons == 0 ? 1 : maxPagingButtons)) < noButtons) {
				sb.append("<A HREF=\"javascript:" + comp.getFullName() + "_selectPage('next');\">" + linkStart + "&gt;" + linkEnd + "</A>&nbsp;");
				if (noButtons > 2)
					sb.append("<A HREF=\"javascript:" + comp.getFullName() + "_selectPage('last');\">" + linkStart + "&gt;&gt;" + linkEnd + "</A>&nbsp;");
			}
			sb.append(fontEnd);
			return sb.toString();
		}
}