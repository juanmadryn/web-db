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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/CalendarTag.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 5/14/04 12:12p $ 
/////////////////////////


import com.salmonllc.html.HtmlCalendar;
import com.salmonllc.html.HtmlComponent;

/**
 * This tag creates an Html Style
 */

public class CalendarTag extends BaseEmptyTag {
	public static final int SIZE_LARGE = 1;
	public static final int SIZE_SMALL = 2;
	
	private String _headingBgColor;
	private String _headingFgColor;
	private String _weekBgColor;
	private String _weekFgColor;
	private String _dayBgColor;
	private String _dayFgDeemphisis;
	private String _dayFgNormal;
	private String _dayFgCurrent;
	private String _fontFace;
	private String _fontSize;
	private String _displaySize;
	private String _currentMonth;
	private String _currentYear;
	private String _width;
	private String _border;
	private String _theme;

	
public HtmlComponent createComponent() {
   HtmlCalendar _calendar = new HtmlCalendar(getName(),getHelper().getController());   
	
if (_headingBgColor != null)
   _calendar.setHeadingBackgroundColor(_headingBgColor);
if (_headingFgColor != null)
   _calendar.setHeadingForegroundColor(_headingFgColor);
if (_weekBgColor != null)
   _calendar.setWeekBackgroundColor(_weekBgColor);
if (_weekFgColor != null)
   _calendar.setWeekForegroundColor(_weekFgColor);
if (_dayBgColor != null)
   _calendar.setDayBackgroundColor(_dayBgColor);
if (_dayFgDeemphisis != null)
   _calendar.setDayForegroundDeemphisisColor(_dayFgDeemphisis);
if (_dayFgNormal != null)
   _calendar.setDayForegroundNormalColor(_dayFgNormal);
if (_dayFgCurrent != null)
   _calendar.setDayForegroundCurrentColor(_dayFgCurrent);
if (_fontFace != null)
   _calendar.setFontFace(_fontFace);
   
if (_displaySize != null && _displaySize.toUpperCase().equals("LARGE"))
   {
	   _calendar.setDisplaySize(SIZE_LARGE);
	   if (_fontSize != null)
   		 _calendar.setFontSizeLarge(BaseTagHelper.stringToInt(_fontSize));
   }

if (_displaySize != null && _displaySize.toUpperCase().equals("SMALL"))
   {
	   _calendar.setDisplaySize(SIZE_SMALL);
 	   if (_fontSize != null)
   			_calendar.setFontSizeSmall(BaseTagHelper.stringToInt(_fontSize));
   }
   
if (_currentMonth != null)
   _calendar.setCalendarMonth(BaseTagHelper.stringToInt(_currentMonth));
if (_currentYear != null)
   _calendar.setCalendarYear(BaseTagHelper.stringToInt(_currentYear));
if (_width != null) {
   _calendar.setWidth(BaseTagHelper.stringToInt(_width));
   if (_width.endsWith("%"))
   		_calendar.setSizeOption(HtmlCalendar.SIZE_PERCENT);
   else
   		_calendar.setSizeOption(HtmlCalendar.SIZE_PIXELS);
}  
if (_border != null)
   _calendar.setBorder(BaseTagHelper.stringToInt(_border));
if (_theme != null)
   _calendar.setTheme(_theme);
	
	
	
	
	return _calendar;
}
/**
 * Returns the Border attribute
 */

public String getBorder() {
	return _border;
}
/**
 * Returns the Current Month attribute
 */

public String getCurrentmonth() {
	return _currentMonth;
}
/**
 * Returns the Current Year attribute
 */

public String getCurrentyear() {
	return _currentYear;
}
/**
 * Returns the DayBackgroundcolor attribute
 */

public String getDaybgcolor() {
	return _dayBgColor;
}
/**
 * Returns the DayForegroundCurrent attribute
 */

public String getDayfgcurrent() {
	return _dayFgCurrent;
}
/**
 * Returns the DayForegroundDeemphasis attribute
 */

public String getDayfgdeemphasis() {
	return _dayFgDeemphisis;
}
/**
 * Returns the DayForegroundNormal attribute
 */

public String getDayfgnormal() {
	return _dayFgNormal;
}
/**
 * Returns the Display Size attribute
 */

public String getdisplaysize() {
	return _displaySize;
}
/**
 * Returns the Font Face attribute
 */

public String getFontface() {
	return _fontFace;
}
/**
 * Returns the Font Size attribute
 */

public String getFontsize() {
	return _fontSize;
}
/**
 * Returns the Headingbackgroundcolor attribute
 */

public String getHeadingbgcolor() {
	return _headingBgColor;
}
/**
 * Returns the HeadingForegroundcolor attribute
 */

public String getHeadingfgcolor() {
	return _headingFgColor;
}
/**
 * Returns the Theme attribute
 */

public String getTheme() {
	return _theme;
}
/**
 * Returns the WeekBackgroundcolor attribute
 */

public String getWeekbgcolor() {
	return _weekBgColor;
}
/**
 * Returns the WeekForegroundcolor attribute
 */

public String getWeekfgcolor() {
	return _weekFgColor;
}
/**
 * Returns the Width attribute
 */

public String getWidth() {
	return _width;
}
public void release() {
    super.release();
	
 _headingBgColor = null;
 _headingFgColor = null;
 _weekBgColor = null;
 _weekFgColor = null;
 _dayBgColor = null;
 _dayFgDeemphisis = null;
 _dayFgNormal = null;
 _dayFgCurrent = null;
 _fontFace = null;
 _fontSize = null;
 _displaySize = null;
 _currentMonth = null;
 _currentYear = null;
 _width = null;
 _border = null;
 _theme = null;

}
/**
 * Sets the Border attribute
 */

public void setBorder(String val) {
	_border = val;
}
/**
 * Sets the Current Month attribute
 */

public void setCurrentmonth(String val) {
	_currentMonth = val;
}
/**
 * Sets the Current Year attribute
 */

public void setCurrentyear(String val) {
	_currentYear = val;
}
/**
 * Sets the Day Background Color attribute
 */

public void setDaybgcolor(String val) {
	_dayBgColor = val;
}
/**
 * Sets the Day Foreground Current attribute
 */

public void setDayfgcurrentcolor(String val) {
	_dayFgCurrent = val;
}
/**
 * Sets the Day Foreground Deemphasis attribute
 */

public void setDayfgdeemphasiscolor(String val) {
	_dayFgDeemphisis = val;
}
/**
 * Sets the Day Foreground Normal attribute
 */

public void setDayfgnormalcolor(String val) {
	_dayFgNormal = val;
}
/**
 * Sets the Display Size attribute
 */

public void setDisplaysize(String val) {
	_displaySize = val;
}
/**
 * Sets the Font Face attribute
 */

public void setFontface(String val) {
	_fontFace = val;
}
/**
 * Sets the  Font Size attribute
 */

public void setFontsize(String val) {
	_fontSize = val;
}
/**
 * Sets the Heading Background Color attribute
 */

public void setHeadingbgcolor(String val) {
	_headingBgColor = val;
}
/**
 * Sets the Heading Foreground Color attribute
 */

public void setHeadingfgcolor(String val) {
	_headingFgColor = val;
}
/**
 * Sets the Theme attribute
 */

public void setTheme(String val) {
	_theme = val;
}
/**
 * Sets the Week Background Color attribute
 */

public void setWeekbgcolor(String val) {
	_weekBgColor = val;
}
/**
 * Sets the Week Foreground Color attribute
 */

public void setWeekfgcolor(String val) {
	_weekFgColor = val;
}
/**
 * Sets the Width attribute
 */

public void setWidth(String val) {
	_width = val;
}
}
