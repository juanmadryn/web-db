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
package com.salmonllc.forms;

import com.salmonllc.html.HtmlText;
import com.salmonllc.html.HtmlTextEdit;

/**
 * Encapsulates a range of dates with two entry fields.
 */
public class DateRange extends com.salmonllc.html.HtmlContainer {
	private HtmlTextEdit _hteFrom;
	private HtmlTextEdit _hteTo;
/**
 * DateRange constructor comment.
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 */
public DateRange(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p);
	HtmlTextEdit hte;
	add(new HtmlText("From:", HtmlText.FONT_COLUMN_CAPTION, p));
	hte = _hteFrom = new HtmlTextEdit(name + "_from", p);
	hte.setMaxLength(25);
	hte.setSize(15);
	add(hte);
	add(new HtmlText("To:", HtmlText.FONT_COLUMN_CAPTION, p));
	hte = _hteTo = new HtmlTextEdit(name + "_to", p);
	hte.setMaxLength(25);
	hte.setSize(15);
	add(hte);
}
/**
 * This method was gets the from date string .
 * @return java.lang.String
 */
public String getFromDate () {
	String s = _hteFrom.getValue();
	if (s != null)
		if (s.trim().length() == 0)
			s = null;
	return s;
}
/**
 * This method was gets the to date string .
 * @return java.lang.String
 */
public String getToDate () {
	String s = _hteTo.getValue();
	if (s != null)
		if (s.trim().length() == 0)
			s = null;
	return s;
}
}
