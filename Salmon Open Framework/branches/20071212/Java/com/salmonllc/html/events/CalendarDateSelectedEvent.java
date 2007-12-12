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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/events/CalendarDateSelectedEvent.java $
//$Author: Dan $ 
//$Revision: 6 $ 
//$Modtime: 10/30/02 2:40p $ 
/////////////////////////
 
import com.salmonllc.html.*;
import java.util.*;

/**
 * This object will be created and passed to every Calendar Date Selected event method.
 */
 
public class CalendarDateSelectedEvent extends java.awt.AWTEvent {
	GregorianCalendar _date;
	java.sql.Date _sqlDate;
	HtmlCalendar _source;
/**
 * This method constructs a new Event.
 */
 
public CalendarDateSelectedEvent(HtmlCalendar source,GregorianCalendar gDate, java.sql.Date sDate) {
	super(source,0);
	_source = source;
	_date = gDate;
	_sqlDate = sDate;
}
/**
 * This method returns the calendar that generated the event.
 */
public HtmlCalendar getCalendar() {
	return _source;
}
/**
 * This method returns the selected date as a gregorian calendar
 */
public GregorianCalendar getDateAsCalendar() {
	return _date;
}
/**
 * This method returns the selected date as an SQL Date field
 */
public java.sql.Date getDateAsSQLDate() {
	return _sqlDate;
}
}
