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
//$Archive: /JADE/SourceCode/com/salmonllc/html/events/CalendarMonthChangeEvent.java $
//$Author: Dan $ 
//$Revision: 7 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
 
import com.salmonllc.html.*;
import java.util.*;

/**
 * This object will be created and passed to every Calendar Month Change event method.
 */
 
public class CalendarMonthChangeEvent extends java.awt.AWTEvent {
	int _oldMonth;
	int _oldYear;
	int _newMonth;
	int _newYear;
	HtmlCalendar _source;
/**
 * This method constructs a new Event.
 */
 
public CalendarMonthChangeEvent(HtmlCalendar source,int oldMonth,int oldYear, int newMonth, int newYear) {
	super(source,0);
	_oldMonth = oldMonth;
	_oldYear = oldYear;
	_newYear = newYear;
	_newMonth = newMonth;
	_source = source;
}
/**
 * This method returns the calendar that generated the event.
 */
public HtmlCalendar getCalendar() {
	return _source;
}
/**
 * This method returns the first day to be displayed on the new month of the calendat
 * @return java.util.GregorianCalendar
 */
public GregorianCalendar getFirstDayOnCalendar() {

	GregorianCalendar g = new GregorianCalendar(_newYear,_newMonth,1);
	int dow = g.get(Calendar.DAY_OF_WEEK);
	
	if (dow != 1) 
		g.add(Calendar.DATE, ((dow - 1) * -1));	

	return g;
}
/**
 * This method returns the last day displayed on the new month in the calendar.
 */
public GregorianCalendar getLastDayOnCalendar() {
	GregorianCalendar g = getFirstDayOnCalendar();
	g.add(Calendar.DATE,41);
	return g;
}
/**
 * This method returns the selected year of the calendar after the change.
 */
public int getNewMonth() {
	return _newMonth;
}
/**
 * This method returns the selected year of the calendar after the change.
 */
public int getNewYear() {
	return _newYear;
}
/**
 * This method returns the selected month of the calendar before the change.
 */
public int getOldMonth() {
	return _oldMonth;
}
/**
 * This method returns the selected year of the calendar before the change.
 */
public int getOldYear() {
	return _oldYear;
}
}
