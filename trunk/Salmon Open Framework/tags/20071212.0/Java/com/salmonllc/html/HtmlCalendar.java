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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlCalendar.java $
//$Author: Dan $
//$Revision: 23 $
//$Modtime: 11/05/04 10:02a $
/////////////////////////

import com.salmonllc.html.events.*;
import com.salmonllc.jsp.JspController;
import com.salmonllc.properties.*;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This component will generate Html resembling a calendar. The user can page
 * through months and select days.
 */
public class HtmlCalendar extends HtmlComponent {

	public static final int SIZE_LARGE = 1;
	public static final int SIZE_SMALL = 2;

	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;

	private String _headingBackgroundColor;
	private String _headingForegroundColor;
	private String _weekBackgroundColor;
	private String _weekForegroundColor;
	private String _dayBackgroundColor;
	private String _dayForegroundDeemphisis;
	private String _dayForegroundNormal;
	private String _dayForegroundCurrent;
	private String _fontFace;
	private int _largeFontSize;
	private int _smallFontSize;
	private int _currentMonth;
	private int _currentYear;
	private int _displaySize = SIZE_LARGE;
	private int _width = 1;
	private int _sizeOption = SIZE_PERCENT;
	private int _border = 0;
	private int _firstDayofWeek=0;

	private String _dayLongNames[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private String _dayShortNames[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

	private String _monthLongNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private String _monthShortNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

	private boolean _updateLocale = true;
	private Vector _listeners;
	private java.awt.AWTEvent _calEvent;

	private Vector _dayHighlights = new Vector();
	private int _nextDay = 0;
	private String _theme = null;
	boolean _scrollTo;
	private String _headingStyleClass;
	private String _weekStyleClass;
	private String _dayNormalStyleClassName;
	private String _dayDeemphisisClassName;
	private String _dayCurrentStyleClassName;
	private String[] _fontSizes = {"xx-small", "xx-small", "x-small", "small", "medium", "large", "larger", "x-large", "xx-large"};
	private int _fontSizeUnit;
	
	public static final int FONT_SIZE_RELATIVE=0;
	public static final int FONT_SIZE_IN_POINTS=1;
	public static final int FONT_SIZE_IN_PIXELS=2;

	/**
	 * Constructs a new HtmlCalendar
	 * 
	 * @param name
	 *            The name of the calendar in the page
	 * @param p
	 *            The page the calendar will be in
	 */
	public HtmlCalendar(String name, HtmlPage p) {
		this(name, null, p);
	}

	/**
	 * Constructs a new HtmlCalendar
	 * 
	 * @param name
	 *            The name of the calendar in the page
	 * @param theme
	 *            The theme to use for loading properties.
	 * @param p
	 *            The page the calendar will be in
	 */
	public HtmlCalendar(String name, String theme, HtmlPage p) {
		super(name, p);

		GregorianCalendar currentDate = new GregorianCalendar();
		_currentYear = currentDate.get(Calendar.YEAR);
		_currentMonth = currentDate.get(Calendar.MONTH);

		setTheme(theme);
	}

	/**
	 * This method adds a listener the will be notified when this component
	 * causes the page its in to be submitted.
	 */
	public void addCalendarListener(CalendarListener l) {
		if (_listeners == null)
			_listeners = new Vector();

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l)
				return;
		}

		_listeners.addElement(l);
	}

	/**
	 * This method adds a new day to highlight in the calendar. That day will be
	 * displayed in the highlight font. Note: Month is 0 based, 0 = Jan, 1=Feb
	 * etc..
	 */
	public void addDayHighlight(int year, int month, int day) {
		GregorianCalendar g = new GregorianCalendar(year, month, day);
		addDayHighlight(g);
	}

	/**
	 * This method adds a new day to highlight to the calendar. That day will be
	 * displayed in the highlight font.
	 */
	public void addDayHighlight(java.sql.Date d) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(d);
		addDayHighlight(g);
	}

	/**
	 * This method adds a new day to highlight to the calendar.
	 */
	public void addDayHighlight(GregorianCalendar g) {
		int max = _dayHighlights.size();
		GregorianCalendar g2 = null;
		for (int i = 0; i < max; i++) {
			g2 = (GregorianCalendar) _dayHighlights.elementAt(i);
			if (g.equals(g2))
				return;
			else if (g2.after(g)) {
				_dayHighlights.insertElementAt(g, i);
				return;
			}
		}

		_dayHighlights.addElement(g);
	}

	/**
	 * This method clears out the list of days to highlight.
	 */
	public void clearDayHighLights() {
		_dayHighlights.removeAllElements();
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @return boolean
	 * @param g
	 *            java.util.GregorianCalendar
	 */
	private boolean dayHighlighted(GregorianCalendar g) {
		GregorianCalendar g2 = null;
		for (; _nextDay < _dayHighlights.size(); _nextDay++) {
			g2 = (GregorianCalendar) _dayHighlights.elementAt(_nextDay);
			if (g2.equals(g))
				return true;
			else if (g2.after(g))
				return false;
		}
		return false;
	}

	public boolean executeEvent(int type) throws Exception {
		if (type != EVENT_SUBMIT)
			return true;
		if (_listeners != null) {
			CalendarListener l = null;
			for (int i = 0; i < _listeners.size(); i++) {
				l = (CalendarListener) _listeners.elementAt(i);
				if (_calEvent instanceof CalendarMonthChangeEvent) {
					if (!l.monthChanged((CalendarMonthChangeEvent) _calEvent))
						return false;
				} else {
					l.dateSelected((CalendarDateSelectedEvent) _calEvent);
				}
			}
		}
		if (_calEvent instanceof CalendarMonthChangeEvent) {
			_currentMonth = ((CalendarMonthChangeEvent) _calEvent).getNewMonth();
			_currentYear = ((CalendarMonthChangeEvent) _calEvent).getNewYear();
		}
		return true;
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
		processLocaleInfo();
		String days[] = _displaySize == SIZE_LARGE ? _dayLongNames : _dayShortNames;
		String months[] = _displaySize == SIZE_LARGE ? _monthLongNames : _monthShortNames;
		int sz = _displaySize == SIZE_LARGE ? _largeFontSize : _smallFontSize;
		String size = "";
		if (_fontSizeUnit == FONT_SIZE_IN_POINTS)
			size=sz+"pt";
		else if (_fontSizeUnit == FONT_SIZE_IN_PIXELS)
			size=sz+"px";
		else
			size=_fontSizes[sz];

		//HiddenComponent & javascript
		p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "\">");
		p.println("<SCRIPT>");
		p.println("  function " + getFullName() + "_changeMonth(months) {");
		p.println(getFormString() + getFullName() + ".value='changeMonth:' + months;");
		p.println(getFormString() + "submit();");
		p.println(" }");
		p.println("  function " + getFullName() + "_selectDate(day,year) {");
		p.println(getFormString() + getFullName() + ".value='selectDate:' + day + '-' + year;");
		p.println(getFormString() + "submit();");
		p.println(" }");
		p.println("</SCRIPT>");

		p.println("<A NAME=\"" + getFullName() + "CalStart\"></a>");

		//Table Heading
		p.print("<TABLE BORDER=" + _border + " CELLSPACING=0 CELLPADDING=0 COLS=1 ");
		if (_width > -1) {
			p.print("WIDTH=\"" + _width);
			if (_sizeOption == SIZE_PERCENT)
				p.print("%");
			p.print("\"");
		}
		p.println(">");

		/*
		 * String hFontStart = " <FONT"; if (_fontFace != null) hFontStart += "
		 * FACE=\"" + _fontFace + "\""; hFontStart += " SIZE=\"" + size + "\"";
		 * if (_headingForegroundColor != null) hFontStart += " COLOR=\"" +
		 * _headingForegroundColor + "\""; hFontStart += "> <B>"; String
		 * hFontEnd = " </B> </FONT>";
		 */

		String hFontStart = "<SPAN style=\"";
		if (_fontFace != null)
			hFontStart += "font-family:" + _fontFace + ";text-decoration:none;font-size:" + size + ";";
		if (_headingForegroundColor != null)
			hFontStart += "color:" + _headingForegroundColor + ";";
		hFontStart += "\"><B>";
		String hFontEnd = "</B></SPAN>";

		p.print("<TR><TD>");

		p.print("<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=2 COLS=3 WIDTH=\"100%\"");
		if (_headingBackgroundColor != null)
			p.print(" BGCOLOR=\"" + _headingBackgroundColor + "\"");
		p.print("> <TR");
		if (_headingStyleClass != null)
			p.print(" CLASS=\"" + _headingStyleClass + "\"");
		p.println(">");

		p.println("<TD WIDTH=\"20%\" ALIGN=\"LEFT\">");
		p.println("<A HREF=\"javascript:" + getFullName() + "_changeMonth(-12);\">");
		p.print(hFontStart + "&lt;&lt;" + hFontEnd);
		p.println("</A>");
		p.println("<A HREF=\"javascript:" + getFullName() + "_changeMonth(-1);\">");
		p.print(hFontStart + "&lt;" + hFontEnd);
		p.println("</A></TD>");

		p.println("<TD ALIGN=\"CENTER\" WIDTH=\"00%\">" + hFontStart);
		p.print(months[_currentMonth] + " " + _currentYear + hFontEnd + "</TD>");

		p.println("<TD ALIGN=\"RIGHT\" WIDTH=\"20%\" >");
		p.println("<A HREF=\"javascript:" + getFullName() + "_changeMonth(1);\">");
		p.print(hFontStart + "&gt;" + hFontEnd);
		p.println("</A>");
		p.println("<A HREF=\"javascript:" + getFullName() + "_changeMonth(12);\">");
		p.print(hFontStart + "&gt;&gt;" + hFontEnd);
		p.println("</A>");

		p.println("</TD>");
		p.println("</TR></TABLE>");

		//calendar
		//weeks
		p.print("<TABLE BORDER=0 CELLSPACING=1 CELLPADDING=1 COLS=7 WIDTH=\"100%\"");
		p.println(">");

		p.print("<TR");
		if (_weekStyleClass != null)
			p.print(" CLASS=\"" + _weekStyleClass + "\"");
		p.println(">");
        int numDays=0;      
        int i=getFirstDayOfWeek();
        while(numDays<7){ 
			p.print("<TD ALIGN=\"CENTER\"");
			if (_weekBackgroundColor != null)
				p.print(" BGCOLOR=\"" + _weekBackgroundColor + "\"");
			p.print("><SPAN style=\"");
			if (_fontFace != null)
				p.print("font-family:" + _fontFace + ";font-size:" + size + ";");
			if (_weekForegroundColor != null)
				p.print("color:" + _headingForegroundColor + ";");
			p.print("\">");

			if (_displaySize == SIZE_SMALL)
				p.println("<B>");
			p.print(days[i]);
			if (_displaySize == SIZE_SMALL)
				p.println("</B>");
			p.println("</SPAN></TD>");
            if (i<6) 
            	i++;
            else 
            	i=0;
            numDays++;
			
		}
		p.println("</TR>");

		//days
		int subtract=getFirstDayOfWeek();
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar date = getFirstDayOnCalendar();
		date.add(Calendar.DATE, subtract);
		_nextDay = 0;
		
		for (i = 0; i < 6; i++) {
			p.println("<TR>");
			for (int j = 0; j < 7; j++) {
				p.print("<TD ALIGN=\"CENTER\"");

				if (_dayBackgroundColor != null)
					p.print(" BGCOLOR=\"" + _dayBackgroundColor + "\"");
				p.print(">");
				p.print("<A HREF=\"javascript:" + getFullName() + "_selectDate(" + date.get(Calendar.DAY_OF_YEAR) + "," + date.get(Calendar.YEAR) + ");\">");

				p.print("<SPAN ");
				String fontStyle = " style=\"";
				if (_fontFace != null)
					fontStyle += "text-decoration:none;font-family:" + _fontFace + ";font-size:" + size + ";";
				if (date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) == today.get(Calendar.YEAR) && _dayForegroundCurrent != null)
					fontStyle += "color:" + _dayForegroundCurrent + ";";
				else if (date.get(Calendar.MONTH) != _currentMonth && _dayForegroundDeemphisis != null)
					fontStyle += "color:" + _dayForegroundDeemphisis + ";";
				else if (_dayForegroundNormal != null)
					fontStyle += "color:" + _dayForegroundNormal + ";";
				fontStyle += "\"";

				//p.print("<FONT");
				//if (_fontFace != null)
				//	p.print(" FACE=\"" + _fontFace + "\"");
				//p.print(" SIZE=\"" + size + "\"");
				//if (date.get(Calendar.DAY_OF_YEAR) ==
				// today.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) ==
				// today.get(Calendar.YEAR) && _dayForegroundCurrent != null)
				//	p.print(" COLOR=\"" + _dayForegroundCurrent + "\"");
				//else if (date.get(Calendar.MONTH) != _currentMonth &&
				// _dayForegroundDeemphisis != null)
				//	p.print(" COLOR=\"" + _dayForegroundDeemphisis + "\"");
				//else if (_dayForegroundNormal != null)
				//	p.print(" COLOR=\"" + _dayForegroundNormal + "\"");
				//p.print(">");

				//String spanStart = "";
				//if (date.get(Calendar.DAY_OF_YEAR) ==
				// today.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) ==
				// today.get(Calendar.YEAR) && _dayCurrentStyleClassName !=
				// null)
				//	spanStart = "<SPAN CLASS=\"" + _dayCurrentStyleClassName +
				// "\">";
				//else if (date.get(Calendar.MONTH) != _currentMonth &&
				// _dayDeemphisisClassName != null)
				//	spanStart = "<SPAN CLASS=\"" + _dayDeemphisisClassName +
				// "\">";
				//else if (_dayNormalStyleClassName != null)
				//	spanStart = "<SPAN CLASS=\"" + _dayNormalStyleClassName +
				// "\">";
				//String spanEnd = (spanStart.length() == 0 ? "" : "</SPAN>");

				if (date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) == today.get(Calendar.YEAR) && _dayCurrentStyleClassName != null)
					p.print("class=\"" + _dayCurrentStyleClassName + "\"");
				else if (date.get(Calendar.MONTH) != _currentMonth && _dayDeemphisisClassName != null)
					p.print("class=\"" + _dayDeemphisisClassName + "\"");
				else if (_dayNormalStyleClassName != null)
					p.print("class=\"" + _dayNormalStyleClassName + "\"");
				else
					p.print(fontStyle);
				p.print(">");

				String boldStart = "";
				String boldEnd = "";
				if (dayHighlighted(date)) {
					boldStart = "<B>";
					boldEnd = "</B>";
				}

				p.print(boldStart + date.get(Calendar.DAY_OF_MONTH) + boldEnd);
				date.add(Calendar.DATE, 1);
				p.print("</SPAN></A></TD>");
			}
			p.println("</TR>");
		}

		//end of tables
		p.println("</TABLE>");
		p.println("</TD></TR></TABLE>");

		if (_scrollTo) {
			//		getPage().scrollToItem(getFullName() + "CalStart");
			_scrollTo = false;
		}

	}

	/**
	 * This method gets the border width of the calendar.
	 */
	public int getBorder() {
		return _border;
	}

	/**
	 * This method returns the current month displayed in the calendar.
	 */
	public int getCalendarMonth() {
		return _currentMonth;
	}

	/**
	 * This method returns the current year displayed in the calendar.
	 */
	public int getCalendarYear() {
		return _currentYear;
	}

	/**
	 * This method gets the background color for the days of the month.
	 */
	public String getDayBackgroundColor() {
		return _dayBackgroundColor;
	}

	/**
	 * This method gets the text color for the day that is today's date.
	 */
	public String getDayForegroundCurrentColor() {
		return _dayForegroundCurrent;
	}

	/**
	 * This method gets the text color for days on the calendar that aren't in
	 * the current month.
	 */
	public String getDayForegroundDeemphisisColor() {
		return _dayForegroundDeemphisis;
	}

	/**
	 * This method gets the text color for days on the calendar that are in the
	 * current month.
	 */
	public String getDayForegroundNormalColor() {
		return _dayForegroundNormal;
	}

	/**
	 * This returns the display size of the calendar. Valid options are
	 * SIZE_LARGE and SIZE_SMALL.
	 */
	public int getDisplaySize() {
		return _displaySize;
	}

	/**
	 * This method returns the first day displayed on the calendar.
	 * 
	 * @return java.util.GregorianCalendar
	 */
	public GregorianCalendar getFirstDayOnCalendar() {

		GregorianCalendar g = new GregorianCalendar(_currentYear, _currentMonth, 1);
		int dow = g.get(Calendar.DAY_OF_WEEK);

		if (dow != 1)
			g.add(Calendar.DATE, ((dow - 1) * -1));

		return g;
	}

	/**
	 * This method gets the font face for everything in the calendar.
	 */
	public String getFontFace() {
		return _fontFace;
	}

	/**
	 * This method gets the font size used if the calender display size is
	 * SIZE_LARGE;
	 */
	public int getFontSizeLarge() {
		return _largeFontSize;
	}

	/**
	 * This method gets the font size used if the calender display size is
	 * SIZE_SMALL;
	 */
	public int getFontSizeSmall() {
		return _smallFontSize;
	}

	/**
	 * This method gets the background color for the calendar heading.
	 */
	public String getHeadingBackgroundColor() {
		return _headingBackgroundColor;
	}

	/**
	 * This method gets the text color for the calendar heading.
	 */
	public String getHeadingForegroundColor() {
		return _headingForegroundColor;
	}

	/**
	 * This method returns the last day displayed on the calendar.
	 */
	public GregorianCalendar getLastDayOnCalendar() {
		GregorianCalendar g = getFirstDayOnCalendar();
		g.add(Calendar.DATE, 41);
		return g;
	}

	/**
	 * This method returns the size option for the Calendar. Valid return values
	 * are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getSizeOption() {
		return _sizeOption;
	}

	/**
	 * This method returns the property theme for the component.
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * This method gets the background color for the days of the week headings.
	 */
	public String getWeekBackgroundColor() {
		return _weekBackgroundColor;
	}

	/**
	 * This method gets the text color for the days of the week headings.
	 */
	public String getWeekForegroundColor() {
		return _weekForegroundColor;
	}

	/**
	 * This method gets the width of the calendar.
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * This method should be implemented in any subclasses of this component
	 * interested in processing parameters from an html post operation. The
	 * component should interogate the HttpServletRequest for any parameters it
	 * is interested in and change its internal state to reflect the parms.
	 * 
	 * @return true if this component is the one that submitted the page and
	 *         false if not.
	 * @param parms
	 *            a HashTable containing all the parameters for the servlet.
	 */
	public boolean processParms(Hashtable parms, int rowNo) throws Exception {

		Object parm = parms.get(getFullName());
		if (parm == null)
			return false;

		String[] command = (String[]) parm;

		if (command[0] == null || command[0].trim().equals(""))
			return false;

		
		String st[] = getLookupComponent();
		if (command[0].startsWith("changeMonth:")) {
			int monthInc = Integer.parseInt(command[0].substring(12));
			GregorianCalendar g = new GregorianCalendar(_currentYear, _currentMonth, 1);
			g.add(Calendar.MONTH, monthInc);
			CalendarMonthChangeEvent e = new CalendarMonthChangeEvent(this, _currentMonth, _currentYear, g.get(Calendar.MONTH), g.get(Calendar.YEAR));
			_calEvent = e;
			if (st != null) {
				_scrollTo = true;
				getPage().scrollToItem(getFullName() + "CalStart");
			}
		} else if (command[0].startsWith("selectDate:")) {
			String newDate = command[0].substring(11);
			int pos = newDate.indexOf("-");
			String day = newDate.substring(0, pos);
			String year = newDate.substring(pos + 1);

			int dayInt = Integer.parseInt(day);
			int yearInt = Integer.parseInt(year);

			GregorianCalendar g = new GregorianCalendar();
			g.set(Calendar.YEAR, yearInt);
			g.set(Calendar.DAY_OF_YEAR, dayInt);

			java.sql.Date d = new java.sql.Date(g.getTime().getTime());
			CalendarDateSelectedEvent e = new CalendarDateSelectedEvent(this, g, d);

			_calEvent = e;

			if (st == null) {
				_scrollTo = true;
				getPage().scrollToItem(getFullName() + "CalStart");
			} else {
				HtmlScriptGenerator gen = new HtmlScriptGenerator(getPage());
				String format = st[1];
				String dtString = null;
				if (format != null) {
					SimpleDateFormat df = new SimpleDateFormat(format);
					dtString = df.format(d);
				} else {
					dtString = d.toString();
				}
				getPage().writeScript(gen.generateReturnValueToLookupScript(dtString,""));
			}
		}

		return true;

	}

	/**
	 * This method removes a listener from the list that will be notified if
	 * this component causes the page to be submitted.
	 * 
	 * @param l
	 *            The listener to remove.
	 */
	public void removeCalendarListener(CalendarListener l) {
		if (_listeners == null)
			return;

		for (int i = 0; i < _listeners.size(); i++) {
			if (((CalendarListener) _listeners.elementAt(i)) == l) {
				_listeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * This method sets the border width of the calendar.
	 */
	public void setBorder(int border) {
		_border = border;
	}

	/**
	 * This method sets the current month displayed in the calendar.
	 */
	public void setCalendarMonth(int month) {
		_currentMonth = month;
	}

	/**
	 * This method sets the current year displayed in the calendar.
	 */
	public void setCalendarYear(int year) {
		_currentYear = year;
	}

	/**
	 * This method sets the background color for the days of the month.
	 */
	public void setDayBackgroundColor(String color) {
		_dayBackgroundColor = color;
	}

	/**
	 * This method sets the text color for day that is today's date.
	 */
	public void setDayForegroundCurrentColor(String color) {
		_dayForegroundCurrent = color;
	}

	/**
	 * This method sets the text color for days on the calendar that aren't in
	 * the current month.
	 */
	public void setDayForegroundDeemphisisColor(String color) {
		_dayForegroundDeemphisis = color;
	}

	/**
	 * This method sets the text color for days on the calendar that are in the
	 * current month.
	 */
	public void setDayForegroundNormalColor(String color) {
		_dayForegroundNormal = color;
	}

	/**
	 * This sets the display size of the calendar. Valid options are SIZE_LARGE
	 * and SIZE_SMALL.
	 */
	public void setDisplaySize(int size) {
		_displaySize = size;
	}

	/**
	 * This method sets the font face for all text in the calendar.
	 */
	public void setFontFace(String face) {
		_fontFace = face;
	}

	/**
	 * This method sets the font size used if the calender display size is
	 * SIZE_LARGE;
	 */
	public void setFontSizeLarge(int size) {
		_largeFontSize = size;

	}

	/**
	 * This method sets the font size used if the calender display size is
	 * SIZE_SMALL;
	 */
	public void setFontSizeSmall(int size) {
		_smallFontSize = size;

	}

	/**
	 * This method sets the background color for the calendar heading.
	 */
	public void setHeadingBackgroundColor(String color) {
		_headingBackgroundColor = color;
	}

	/**
	 * This method sets the text color for the calendar heading.
	 */
	public void setHeadingForegroundColor(String color) {
		_headingForegroundColor = color;
	}

	/**
	 * This method sets the size option Calendar. Valid return values are
	 * SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setSizeOption(int option) {
		_sizeOption = option;
	}

	/**
	 * This method sets the property theme for the component.
	 * 
	 * @param theme
	 *            The theme to use.
	 */
	public void setTheme(String theme) {

		Props pr = getPage().getPageProperties();

		_headingBackgroundColor = pr.getThemeProperty(theme, Props.CAL_HEADING_BACKGROUND_COLOR);
		_headingForegroundColor = pr.getThemeProperty(theme, Props.CAL_HEADING_FOREGROUND_COLOR);
		_weekBackgroundColor = pr.getThemeProperty(theme, Props.CAL_WEEK_BACKGROUND_COLOR);
		_weekForegroundColor = pr.getThemeProperty(theme, Props.CAL_WEEK_FOREGROUND_COLOR);
		_dayBackgroundColor = pr.getThemeProperty(theme, Props.CAL_DAY_BACKGROUND_COLOR);
		_dayForegroundDeemphisis = pr.getThemeProperty(theme, Props.CAL_DAY_FOREGROUND_DEEMPHISIS);
		_dayForegroundNormal = pr.getThemeProperty(theme, Props.CAL_DAY_FOREGROUND_NORMAL);
		_dayForegroundCurrent = pr.getThemeProperty(theme, Props.CAL_DAY_FOREGROUND_CURRENT);
		_fontFace = pr.getThemeProperty(theme, Props.CAL_FONT_FACE);

		String ft = pr.getThemeProperty(theme, Props.CAL_FONT_LARGE);
		if (ft != null) {
			if (ft.endsWith("pt")) {
				_fontSizeUnit = FONT_SIZE_IN_POINTS;
				_largeFontSize = Integer.parseInt(ft.substring(0, ft.length() - 2));
			}
			else if (ft.endsWith("px")) {
				_fontSizeUnit = FONT_SIZE_IN_PIXELS;
				_largeFontSize = Integer.parseInt(ft.substring(0, ft.length() - 2));
			}
			else {
				_largeFontSize = Integer.parseInt(ft);
			}
		}

		ft = pr.getThemeProperty(theme, Props.CAL_FONT_SMALL);
		if (ft != null) {
			if (ft.endsWith("pt")) {
				_fontSizeUnit=FONT_SIZE_IN_POINTS;
				_smallFontSize = Integer.parseInt(ft.substring(0, ft.length() - 2));
			} 
			else if (ft.endsWith("px")) {
				_fontSizeUnit=FONT_SIZE_IN_PIXELS;
				_smallFontSize = Integer.parseInt(ft.substring(0, ft.length() - 2));
			} 
			else {
				_smallFontSize = Integer.parseInt(ft);
			}
		}

		_theme = theme;
	}

	/**
	 * This method sets the background color for the days of the week headings.
	 */
	public void setWeekBackgroundColor(String color) {
		_weekBackgroundColor = color;
	}

	/**
	 * This method sets the text color for the days of the week headings.
	 */
	public void setWeekForegroundColor(String color) {
		_weekForegroundColor = color;
	}

	/**
	 * This method sets the width of the calendar.
	 */
	public void setWidth(int width) {
		_width = width;
	}

	/**
	 * returns the short name for the month passed
	 */
	public String getMonthShortName(int monthIndex) {
		return _monthShortNames[monthIndex];
	}

	/**
	 * returns the long name for the month passed
	 */
	public String getMonthLongName(int monthIndex) {
		return _monthLongNames[monthIndex];
	}

	/**
	 * Sets the short name for the month number passed
	 */
	public void setMonthShortName(int monthIndex, String val) {
		_monthShortNames[monthIndex] = val;
	}

	/**
	 * Sets the long name for the month number passed
	 */
	public void setMonthLongName(int monthIndex, String val) {
		_monthLongNames[monthIndex] = val;
	}

	/**
	 * Set day long name for the day number passed
	 */
	public void setDayLongName(int dayIndex, String val) {
		_dayLongNames[dayIndex] = val;
	}

	/**
	 * Set day long name for the day number passed
	 */
	public void setDayShortName(int dayIndex, String val) {
		_dayShortNames[dayIndex] = val;
	}

	/**
	 * Updates the month and day names for the current language The language
	 * property file must have the following key structure
	 * HtmlCalendar.month.short.0 to HtmlCalendar.month.short.11 represents the
	 * month short names from jan to dec HtmlCalendar.month.long.0 to
	 * HtmlCalendar.month.long.11 represents the month long names from January
	 * to December HtmlCalendar.day.short.0 to HtmlCalendar.day.short.6
	 * represents the day short names from Sun to Sat HtmlCalendar.day.long.0 to
	 * HtmlCalendar.day.long.6 represents the day long names from Sunday to
	 * Saturday
	 */
	public void updateLocale() {
		_updateLocale = true;
	}

	private void processLocaleInfo() {
		if (_updateLocale) {
			_updateLocale = false;
			LanguagePreferences p = getPage().getLanguagePreferences();
			String appName = getPage().getApplicationName();
			StringBuffer key = new StringBuffer("HtmlCalendar.month.short.0");
			boolean shortOK = (LanguageResourceFinder.getResource(appName, key.toString(), p) != null);
			key.setLength(0);
			key.append("HtmlCalendar.month.long.0");
			boolean longOK = (LanguageResourceFinder.getResource(appName, key.toString(), p) != null);

			if (shortOK) {
				processLocaleArray(_monthShortNames, "HtmlCalendar.month.short.", appName, p, key);
				processLocaleArray(_dayShortNames, "HtmlCalendar.day.short.", appName, p, key);
			}

			if (longOK) {
				processLocaleArray(_monthLongNames, "HtmlCalendar.month.long.", appName, p, key);
				processLocaleArray(_dayLongNames, "HtmlCalendar.day.long.", appName, p, key);
			}
		}
	}

	private void processLocaleArray(String[] ar, String key, String appName, LanguagePreferences p, StringBuffer sb) {
		for (int i = 0; i < ar.length; i++) {
			sb.setLength(0);
			sb.append(key);
			sb.append(new Integer(i).toString());
			String val = LanguageResourceFinder.getResource(appName, sb.toString(), p);
			if (val != null)
				ar[i] = val;
		}
	}
	/**
	 * @return
	 */
	public String getHeadingStyleClassName() {
		return _headingStyleClass;
	}

	/**
	 * @param string
	 */
	public void setHeadingStyleClassName(String string) {
		_headingStyleClass = string;
	}

	/**
	 * @return
	 */
	public String getWeekStyleClassName() {
		return _weekStyleClass;
	}

	/**
	 * @param string
	 */
	public void setWeekStyleClassName(String string) {
		_weekStyleClass = string;
	}

	/**
	 * @return
	 */
	public String getDayCurrentStyleClassName() {
		return _dayCurrentStyleClassName;
	}

	/**
	 * @return
	 */
	public String getDayDeemphisisStyleClassName() {
		return _dayDeemphisisClassName;
	}

	/**
	 * @return
	 */
	public String getDayNormalStyleClassName() {
		return _dayNormalStyleClassName;
	}

	/**
	 * @param string
	 */
	public void setDayCurrentStyleClassName(String string) {
		_dayCurrentStyleClassName = string;
	}

	/**
	 * @param string
	 */
	public void setDayDeemphisisStyleClassName(String string) {
		_dayDeemphisisClassName = string;
	}

	/**
	 * @param string
	 */
	public void setDayNormalStyleClassName(String string) {
		_dayNormalStyleClassName = string;
	}

	private String[] getLookupComponent() {
		String lookupComponent = null;
		String lookupFormat = null;
		String formString = null;
		String popup = null;
		String callingController = getPage().getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER);
		if (callingController != null) {
			try {
				JspController thisCont = (JspController) getPage();
				JspController otherCont = (JspController) thisCont.getSession().getAttribute(callingController);
				if (otherCont != null) {
					HtmlLookUpComponent luComp = (HtmlLookUpComponent) otherCont.getComponent(thisCont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_COMPONENT));
					String luName = luComp.getEditField().getFullName();
					int luRow = Integer.parseInt(thisCont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_ROW));
					if (luRow > -1)
						luName += "_" + luRow;
					lookupComponent = luName;
					if (luComp.getUsePopup()) {
						popup = "win";
						if (luComp.getUseDiv())
							popup = "div";
					}

					lookupFormat = luComp.getEditField().getDisplayFormat();
					formString = luComp.getEditField().getFormString();
				}
			} catch (Exception ex) {
			}
		}
		if (lookupComponent == null)
			return null;
		String st[] = new String[4];
		st[0] = lookupComponent;
		st[1] = lookupFormat;
		st[2] = formString;
		st[3] = popup;
		return st;
	}

	/**
	 * Valid values are FONT_SIZE_IN_POINTS,FONT_SIZE_IN_PIXELS,FONT_SIZE_RELATIVE
	 */
	public int getFontSizeUnits() {
		return _fontSizeUnit;
	}

	/**
	 * Valid values are FONT_SIZE_IN_POINTS,FONT_SIZE_IN_PIXELS,FONT_SIZE_RELATIVE
	 */
	public void setFontSizeUnits(int i) {
		_fontSizeUnit = i;
	}
	
	/**
	 * Returns the first day of the week displayed on the calendar (0=Sunday,1=Monday)
	 */
    public int getFirstDayOfWeek(){
        return(_firstDayofWeek);
    }

	/**
	 * Sets the first day of the week displayed on the calendar (0=Sunday,1=Monday)
	 */
    public void setFirstDayOfWeek(int firstDayofWeek){
        if (firstDayofWeek>=0 && _firstDayofWeek<=1)
        	_firstDayofWeek=firstDayofWeek;        
    }

}