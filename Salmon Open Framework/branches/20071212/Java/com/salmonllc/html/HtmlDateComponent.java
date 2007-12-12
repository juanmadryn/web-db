package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlDateComponent.java $
//$Author: Srufle $
//$Revision: 51 $
//$Modtime: 10/26/04 3:46p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Use this class to represent an SQL DATE or TIMESTAMP value as a series of drop-down lists to support selection of year, month, day, and hour.
 */
public class HtmlDateComponent extends HtmlFormComponent {
	protected HtmlContainer _cont;
	protected HtmlDropDownList _month;
	protected HtmlDropDownList _day;
	protected HtmlDropDownList _year;
	protected HtmlDropDownList _hour;
	protected int _dsDataType;
	protected GregorianCalendar _calendar = new GregorianCalendar();
	protected int _minuteInterval;
	protected int _startYear;
	protected int _endYear;
	protected DecimalFormat _formatMinutes = new DecimalFormat("00");
	protected boolean _showHour = true;
	protected DataStore _dsTemp;
	protected int _iIndexOffset = -1;
	protected String _displayFormat;
	public final static int MONTH_NAME_FULL = 0;
	public final static int MONTH_NAME_PARTIAL = 1;
	public final static int MONTH_NAME_DIGIT = 2;
	private Integer _tabIndex;
	private String _accessKey;
	public final static String DMY_DISPLAY_FORMAT = "DMY";
	public final static String MDY_DISPLAY_FORMAT = "MDY";
	public final static String YMD_DISPLAY_FORMAT = "YMD";

	/*Claudio Pi (12-29-2002) Added the following 3 instance varialbes, for i18n of short and long month labels*/
	private String _monthLongNames[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	private String _monthShortNames[] = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
	private boolean _updateLocale = true;

	/**
	 * HtmlDateComponent constructor.  Hour component is divided into half-hour intervals.
	 * @param name 	Component name.
	 * @param p 	Page containing the component.
	 */
	public HtmlDateComponent(String name, HtmlPage p) {
		this(name, p, 48);
	}

	/**
	 * HtmlDateComponent constructor.
	 * @param name 			Component name.
	 * @param p 			Page containing the component.
	 * @param nbrIntervals	For the hour component, break up the day into this many intervals.
	 *  For example, 48 = half-hour intervals.  0 means do not display the hour component.
	 */
	public HtmlDateComponent(String name, HtmlPage p, int nbrIntervals) {
		this(name, p, nbrIntervals, -1, -1);

	}

	/**
	 * HtmlDateComponent constructor.
	 * @param name 			Component name.
	 * @param p 			Page containing the component.
	 * @param nbrIntervals	For the hour component, break up the day into this many intervals.
	 *  For example, 48 = half-hour intervals.  0 means do not display the hour component.
	 * @param iFromTime	    For the hour component, skip the hours before this time.
	 * @param iToTime	    For the hour component, skip the hours after this time.
	 For example iFromTime = 7 and iToTime = 19 => display times only between 7AM to 7PM
	 */
	public HtmlDateComponent(String name, HtmlPage p, int nbrIntervals, int iFromTime, int iToTime) {
		this(name, p, nbrIntervals, iFromTime, iToTime, MONTH_NAME_FULL);
	}

	/**
	 * HtmlDateComponent constructor.
	 * @param name 			Component name.
	 * @param p 			Page containing the component.
	 * @param nbrIntervals	For the hour component, break up the day into this many intervals.
	 *  For example, 48 = half-hour intervals.  0 means do not display the hour component.
	 * @param iFromTime	    For the hour component, skip the hours before this time.
	 * @param iToTime	    For the hour component, skip the hours after this time.
	 For example iFromTime = 7 and iToTime = 19 => display times only between 7AM to 7PM
	 */
	public HtmlDateComponent(String name, HtmlPage p, int nbrIntervals, int iFromTime, int iToTime, int iFlag) {
		super(name, p);
		processLocaleInfo();
		_cont = new HtmlContainer(name, p);
		String sMonth[] = new String[12];

		/*Claudio Pi (12-29-2002) following lines aren't necessary since they are replaced by the i18n instance variables
		String sMonthFull[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		String sMonthPart[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};*/
		String sMonthNumb[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		_month = new HtmlDropDownList("mm", p);
		_month.setGenerateNewline(false);
		_month.addOption(null, "");

		/*Claudio Pi (12-29-2002) hardcoded values replcaced with i18n instance variables*/
		if (iFlag == MONTH_NAME_FULL)
			sMonth = _monthLongNames; // sMonthFull;
		else if (iFlag == MONTH_NAME_PARTIAL)
			sMonth = _monthShortNames; // sMonthPart;
		else if (iFlag == MONTH_NAME_DIGIT)
			sMonth = sMonthNumb;
		else if (iFlag == MONTH_NAME_DIGIT)
			sMonth = _monthLongNames; // sMonthFull;
		for (int i = 0; i < 12; i++)
			_month.addOption(String.valueOf(i), sMonth[i]);

		_day = new HtmlDropDownList("dd", p);
		_day.setGenerateNewline(false);
		_day.addOption(null, "");
		for (int i = 1; i <= 31; i++)
			_day.addOption(String.valueOf(i), String.valueOf(i));
		_year = new HtmlDropDownList("yy", p);
		_year.setGenerateNewline(false);

		int currentYear = _calendar.get(Calendar.YEAR);
		setYearRange(currentYear, currentYear + 3);

		//Determine what order to show the drop down combo boxes for day, month, year
		_displayFormat = p.getPageProperties().getProperty(Props.DATE_COMPONENT_DISPLAY_FORMAT, MDY_DISPLAY_FORMAT);

		if (_displayFormat.equals(DMY_DISPLAY_FORMAT)) {
			_cont.add(_day);
			_cont.add(_month);
			_cont.add(_year);
		} else if (_displayFormat.equals(YMD_DISPLAY_FORMAT)) {
			_cont.add(_year);
			_cont.add(_month);
			_cont.add(_day);
		} else {
			_cont.add(_month);
			_cont.add(_day);
			_cont.add(_year);
		}

		_hour = new HtmlDropDownList("hh", p);
		_hour.setGenerateNewline(false);
		_cont.add(_hour);
		if (nbrIntervals <= 0) {
			_showHour = false;
			_hour.setVisible(false);
			_minuteInterval = 0;
		} else if (nbrIntervals <= 1440) {
			double dRemainder = 1440 % nbrIntervals;
			if (dRemainder != 0)
				com.salmonllc.util.MessageLog.writeDebugMessage("Interval in HtmlDateComponent is not " + "fraction of the day. interval = " + nbrIntervals, this);

			_showHour = true;
			_minuteInterval = (24 * 60) / nbrIntervals;
			int minutes = 0;
			_hour.addOption("", "");
			boolean bSkipHour = false;

			for (int i = 0; i < nbrIntervals; i++) {
				if (iFromTime > 0 && iToTime > 0) {
					bSkipHour = true;
					int hour = minutes / 60;
					int remainder = minutes % 60;
					if (hour < iFromTime || hour > iToTime || (hour == iToTime && remainder > 0)) {
						minutes += _minuteInterval;
						continue;
					}
				}

				_hour.addOption(String.valueOf(i), formatTime(minutes));
				minutes += _minuteInterval;
			}

			if (bSkipHour) {
				_iIndexOffset = (iFromTime * (60 / _minuteInterval)) - 1;

			}
		}
		// For simplicity we create a dummy data store and bind to it.  This allows the
		// DataStore class to worry about all details of formatting.
		DataStore ds = new DataStore();
		if (_showHour)
			ds.addColumn("table", "val", DataStore.DATATYPE_DATETIME, false, true);
		else
			ds.addColumn("table", "val", DataStore.DATATYPE_DATE, false, true);
		ds.insertRow();
		ds.gotoFirst();
		setColumn(ds, 0);
	}

	/**
	 * generates the date.
	 * @return java.lang.String
	 * @param month java.lang.String
	 * @param day java.lang.String
	 * @param year java.lang.String
	 * @param hour java.lang.String
	 */
	protected String buildValueString(String month, String day, String year, String hour) throws DataStoreException {
		String newValue;
		if ((month == null && day == null && year == null && hour == null)) {
			newValue = null;
		} else if ((month == null) || (day == null) || (year == null) || (hour == null && _hour.getVisible())) {
			newValue = (year == null ? " " : year) + "-" + (month == null ? " " : month) + "-" + (day == null ? " " : day) + "-" + (hour == null ? " " : hour);
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(Calendar.MONTH, Integer.parseInt(month));
			gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			gc.set(Calendar.YEAR, Integer.parseInt(year));
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MILLISECOND, 0);
			if (hour != null && !hour.trim().equals("")) {
				int iHour = Integer.parseInt(hour);
				int minutes = iHour * _minuteInterval;
				gc.set(Calendar.HOUR_OF_DAY, minutes / 60);
				gc.set(Calendar.MINUTE, minutes % 60);
			} else {
				gc.set(Calendar.HOUR_OF_DAY, 0);
				gc.set(Calendar.MINUTE, 0);
			}
			// Use temporary data store as a register
			if (_dsBuff != null) {
				String fmt = _dsBuff.getFormat(_dsColNo);
				if (fmt != null)
					_dsTemp.setFormat(0, fmt);
			}

			switch (_dsDataType) {
				case DataStore.DATATYPE_DATE :
					_dsTemp.setDate(0, new java.sql.Date(gc.getTime().getTime()));
					break;
				case DataStore.DATATYPE_DATETIME :
					_dsTemp.setDateTime(0, new java.sql.Timestamp(gc.getTime().getTime()));
					break;
			}
			newValue = _dsTemp.getFormattedString(0);
		}
		return newValue;
	}

	/**
	 * Clears fields within this control.
	 */
	public void clearDisplay() {
		_month.setSelectedIndex(0);
		_day.setSelectedIndex(0);
		_year.setSelectedIndex(0);
		if (_hour != null)
			_hour.setSelectedIndex(0);
	}

	/**
	 * Returns a formatted time for the specified minutes.
	 * @return java.lang.String
	 * @param minutes int
	 */
	protected String formatTime(int minutes) {
		int hour = minutes / 60;
		int remainder = minutes % 60;

		// Convert 0-based 24-hour sequence into 1-based 12-hour sequence.
		int h = ((hour + 12 - 1) % 12) + 1;

		String result = String.valueOf(h) + ":" + _formatMinutes.format(remainder);
		if (hour >= 12)
			result += " PM";
		else
			result += " AM";
		return result;
	}

	public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
		// It is essential to call getValue() here because there may be a ValueChanged
		// event in the queue for this object, which needs to be removed, which getValue()
		// does.  The secondary calls to getValue() from the container will not
		// do this because there are no events associated with them.
		String value = getValue(row, true);
		if (value == null) {
			clearDisplay();
		} else if (_dsBuff != null && !_dsBuff.isFormattedStringValid(_dsColNo, value)) {
			updatePartialDisplay(value);
		} else {
			if (_dsBuff != null) {
				String fmt = _dsBuff.getFormat(_dsColNo);
				if (fmt != null)
					_dsTemp.setFormat(0, fmt);
			}

			_dsTemp.setFormattedString(0, value);
			switch (_dsDataType) {
				case DataStore.DATATYPE_DATE :
					_calendar.setTime(_dsTemp.getDate(0, 0));
					break;
				case DataStore.DATATYPE_DATETIME :
					_calendar.setTime(_dsTemp.getDateTime(0, 0));
					break;
			}
			updateDisplay();
		}
		_cont.generateHTML(p, row);
		if (_visible && _enabled)
			p.println("");
	}

	/**
	 * This method returns a Date value from the component.
	 * @return java.sql.Date
	 */
	public Date getDate() {
		String value = getValue();
		if (value == null)
			return null;

		try {
			_dsTemp.setFormattedString(0, value);
			switch (_dsDataType) {
				case DataStore.DATATYPE_DATE :
					return new Date(_dsTemp.getDate(0, 0).getTime());
				case DataStore.DATATYPE_DATETIME :
					return new Date(_dsTemp.getDateTime(0, 0).getTime());
			}
		} catch (DataStoreException ex) {
			return null;
		}

		return null;

	}

	/**
	 * This method returns a Timestamp value from the component.
	 * @return java.sql.Timestamp
	 */
	public Timestamp getDateTime() {

		String value = getValue();
		if (value == null)
			return null;

		try {
			_dsTemp.setFormattedString(0, value);

			switch (_dsDataType) {
				case DataStore.DATATYPE_DATE :
					return new Timestamp(_dsTemp.getDate(0, 0).getTime());
				case DataStore.DATATYPE_DATETIME :
					return new Timestamp(_dsTemp.getDateTime(0, 0).getTime());
			}
		} catch (DataStoreException e) {
			return null;
		}

		return null;
	}

	/**
	 * This method returns the string value of the day subcomponent.
	 * @return String
	 */
	public String getDay() {
		return _day.getValue();
	}

	/**
	 * Returns the sub-component to be used for setting focus.
	 * @return com.salmonllc.html.HtmlComponent
	 */
	public HtmlComponent getFocus() {
		return _month;
	}

	/**
	 * This method returns the string value of the hour subcomponent.
	 * @return String
	 */
	public String getHour() {
		return _hour.getValue();
	}

	/**
	 * This method returns the string value of the month subcomponent.
	 * January = 1, etc.
	 * @return String
	 */
	public String getMonth() {
		String s = _month.getValue();
		return s == null ? null : new Integer(1 + Integer.parseInt(s)).toString();
	}

	/**
	 * Gets the value of the component from the parms hashtable based upon the component name & row no.
	 */
	protected String getParmValue(Hashtable parms, HtmlComponent comp, int rowNo) {
		String name = comp.getFullName();
		String result;
		if (rowNo > -1)
			name += "_" + rowNo;
		String val[] = (String[]) parms.get(name);
		if (val != null) {
			result = val[0].trim();
			if (result.equals(""))
				result = null;
		} else
			result = null;
		return result;
	}

	/**
	 * This method returns the string value of the year subcomponent.
	 * @return String
	 */
	public String getYear() {
		return _year.getValue();
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		if (!getVisible() || !getEnabled())
			return false;

		String oldValue;
		String year, month, day;
		String hour = null;
		int r = rowNo;
		if (r < 0)
			r = _dsBuff.getRow();

		try {
			oldValue = _dsBuff.getFormattedString(r, _dsColNo);
		} catch (Exception e) {
			oldValue = getValue();
		}

		// Determine the new value from all edit fields.
		month = getParmValue(parms, _month, rowNo);
		_month.setValue(month, rowNo);
		day = getParmValue(parms, _day, rowNo);
		_day.setValue(day, rowNo);
		year = getParmValue(parms, _year, rowNo);
		_year.setValue(year, rowNo);
		if (_showHour) {
			hour = getParmValue(parms, _hour, rowNo);
			_hour.setValue(hour, rowNo);
		}
		String newValue = buildValueString(month, day, year, hour);
		// Compare old and new values and possibly create a ValueChangedEvent.
		if (!valuesEqual(oldValue, newValue)) {
			ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), oldValue, newValue, rowNo, _dsColNo, _dsBuff);
			addEvent(e);
		}
		return false;
	}

	/**
	 * This method will clear all pending events from the event queue for this component.
	 */
	public void reset() {
		super.reset();
		_cont.reset();
	}

	/**
	 * Specifies the Style Class to be used for the Date Component.
	 * Creation date: (7/19/01 8:41:20 AM)
	 * @param sClass java.lang.String A name of a class in Html to be used by this component
	 */
	public void setClassName(String sClass) {

		super.setClassName(sClass);
		if (_month != null)
			_month.setClassName(sClass);

		if (_day != null)
			_day.setClassName(sClass);

		if (_year != null)
			_year.setClassName(sClass);

		if (_hour != null)
			_hour.setClassName(sClass);

	}

	/**
	 * Use this method to bind the component to a column in a DataStore.
	 * @param ds The datastore to bind to.
	 * @param columnNo The index of the column to bind to.
	 */
	public void setColumn(DataStoreBuffer ds, int columnNo) {
		super.setColumn(ds, columnNo);
		try {
			_dsDataType = _dsBuff.getColumnDataType(columnNo);
			/*		switch (_dsDataType) {
						case _dsBuff.DATATYPE_DATETIME:
			//				_showHour = true;
							_hour.setVisible(_showHour);
							break;
						case _dsBuff.DATATYPE_DATE:
			//				_showHour = false;
							_hour.setVisible(false);
							break;
					}*/
		} catch (DataStoreException e) {
		}
		// Maintain a temporary data store in parallel with the main data store,
		// to be used as a value/formatting buffer.
		_dsTemp = new DataStore();
		_dsTemp.addColumn("table", "val", _dsDataType, false, true);
		_dsTemp.insertRow();
		_dsTemp.gotoFirst();
	}

	/**
	 * Sets the value of the component to the given date and display it.
	 * @param d	New date value.
	 */
	public void setDate(Date d) {
		_calendar.setTime(d);
		updateDisplay();

		// Updated on 07/08/02 by BYD to allow component update when not bound to DataStore
		// example of usage: search date set to passed date after re-direction from another page
		if (_dsBuff != null) {
			try {
				if (_showHour)
					_dsBuff.setDateTime(0, "table.val", new Timestamp(d.getTime()));
				else
					_dsBuff.setDate(0, "table.val", d);
			} catch (Exception e) {
				MessageLog.writeErrorMessage ("setDate", e, this);
			}
		}
	}

	/**
	 * Sets the value of the component to the given timestamp and display it.
	 * @param t	New timestamp value.
	 */
	public void setDateTime(Timestamp t) {
		_calendar.setTime(t);
		updateDisplay();

		// Updated on 07/08/02 by BYD to allow component update when not bound to DataStore
		// example of usage: search date set to passed date after re-direction from another page
		if (_dsBuff != null) {
			try {
				if (_showHour)
					_dsBuff.setDateTime(0, "table.val", t);
				else
					_dsBuff.setDate(0, "table.val", new Date(t.getTime()));
			} catch (Exception e) {
				MessageLog.writeErrorMessage ("setDateTime", e, this);
			}
		}
	}

	/**
	 * Sets the font end tag for disabled mode.
	 * @param tag java.lang.String
	 */
	public void setDisabledFontEndTag(String tag) {

		_month.setDisabledFontEndTag(tag);
		_day.setDisabledFontEndTag(tag);
		_year.setDisabledFontEndTag(tag);
		_hour.setDisabledFontEndTag(tag);
	}

	/**
	 * Sets the font tag for disabled mode.
	 * @param tag java.lang.String
	 */
	public void setDisabledFontStartTag(String tag) {

		_month.setDisabledFontStartTag(tag);
		_day.setDisabledFontStartTag(tag);
		_year.setDisabledFontStartTag(tag);
		_hour.setDisabledFontStartTag(tag);

	}

	/**
	 * Enables or disables the ability of this component to respond to user input.
	 * @param enabled boolean
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		_cont.setEnabled(enabled);
	}

	/**
	 * Specifies the parent component for this Date Component.
	 * Creation date: (7/19/01 8:41:20 AM)
	 */
	public void setParent(HtmlComponent parent) {
		super.setParent(parent);
		// This is necessary for the name to be generated correctly, else the leading
		// sequence of parent names will be lost.
		_cont.setParent(parent);
	}

	/**
	 * This method sets the property theme for the component.
	 * @param sTheme The theme to use.
	 */
	public void setTheme(String sTheme) {

		if (sTheme == null)
			return;

		if (_month != null)
			_month.setTheme(sTheme);

		if (_day != null)
			_day.setTheme(sTheme);

		if (_year != null)
			_year.setTheme(sTheme);

		if (_hour != null)
			_hour.setTheme(sTheme);

	}

	/**
	 * Changes the drop-down for the year to encompass the given range.
	 * @param startYear
	 * @param endYear
	 */
	public void setYearRange(int startYear, int endYear) {
		if ((startYear < 0) || (endYear < 0))
			// Consistency check
			return;
		_startYear = (startYear >= endYear) ? endYear : startYear;
		_endYear = (startYear < endYear) ? endYear : startYear;
		_year.resetOptions();
		_year.addOption(null, "");
		if (startYear < endYear) {
			for (int i = startYear; i <= endYear; i++) {
				String s = String.valueOf(i);
				_year.addOption(s, s);
			}
		} else {
			for (int i = startYear; i >= endYear; i--) {
				String s = String.valueOf(i);
				_year.addOption(s, s);
			}
		}
	}

	protected void updateDisplay() {
		_month.setSelectedIndex(_calendar.get(Calendar.MONTH) + 1);
		_day.setSelectedIndex(_calendar.get(Calendar.DAY_OF_MONTH));
		int year = _calendar.get(Calendar.YEAR);
		if ((year < _startYear) || (year > _endYear))
			setYearRange(year - 5, year + 5);
		_year.setValue(String.valueOf(year));
		if (_minuteInterval > 0) {
			int iIndex = (((_calendar.get(Calendar.HOUR_OF_DAY) * 60) + _calendar.get(Calendar.MINUTE)) / _minuteInterval) - _iIndexOffset;
			if (iIndex < 0)
				_hour.setSelectedIndex(0);
			else
				_hour.setSelectedIndex(iIndex);
		}
	}

	protected void updatePartialDisplay(String value) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(value, "-");

		String year = st.nextToken();
		String month = st.nextToken();
		String day = st.nextToken();
		String hour = null;
		if (st.hasMoreTokens())
			hour = st.nextToken();

		if (!year.equals(" "))
			_year.setValue(year);
		else
			_year.setValue(null);

		if (!month.equals(" "))
			_month.setValue(month);
		else
			_month.setValue(null);

		if (!day.equals(" "))
			_day.setValue(day);
		else
			_day.setValue(null);

		_hour.setValue(null);
		if (hour != null)
			if (!hour.equals(" "))
				_hour.setValue(hour);
	}

	/*Claudio Pi (12-29-2002) Added the methods updateLocale, processLocaleInfo and processLocaleArray for
	i18n of short and long month labels */

	/**
	 * Updates the month name for the current language<br>
	 * The language property file must have the following key structure<br>
	 * HtmlCalendar.month.short.0 to HtmlCalendar.month.short.11 represents the month short names from jan to dec<br>
	 * HtmlCalendar.month.long.0 to HtmlCalendar.month.long.11 represents the month long names from January to December<br>
	 * Author: Claudio Pi Gamba<br>
	 * Company: http://www.grupo-quanam.com<br>
	 * Email: claudio.pi@quanam.com.uy<br>
	 */
	public void updateLocale() {
		_updateLocale = true;
	}

	public void processLocaleInfo() {
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
			}

			if (longOK) {
				processLocaleArray(_monthLongNames, "HtmlCalendar.month.long.", appName, p, key);
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
	 * This method will set the edit focus to this component.
	 */
	public void setFocus() {
		if (_displayFormat.equals(DMY_DISPLAY_FORMAT))
			_day.setFocus();
		else if (_displayFormat.equals(YMD_DISPLAY_FORMAT))
			_year.setFocus();
		else
			_month.setFocus();

	}

	/**
	 * This method will set the edit focus to this component for a particular row in a datastore.
	 */
	public void setFocus(int row) {
		if (_displayFormat.equals(DMY_DISPLAY_FORMAT))
			_day.setFocus(row);
		else if (_displayFormat.equals(YMD_DISPLAY_FORMAT))
			_year.setFocus(row);
		else
			_month.setFocus(row);
	}

	/**
	 * Sets javascript that will fire when the user changes a value in the component
	 * @param onChange
	 */
	public void setOnChange(String onChange) {
		if (_day != null)
			_day.setOnChange(onChange);
		if (_month != null)
			_month.setOnChange(onChange);
		if (_year != null)
			_year.setOnChange(onChange);
	}

	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null)
			return -1;
		return _tabIndex.intValue();
	}

	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else
			_tabIndex = new Integer(val);
		int monthIndex=0;
		int dayIndex=0;
		int yearIndex=0;	
		if (_displayFormat.equals(DMY_DISPLAY_FORMAT)) {
			dayIndex = val;
			monthIndex = val + 1;
			yearIndex = val + 2;
		}	
		else if (_displayFormat.equals(YMD_DISPLAY_FORMAT)) {
			yearIndex = val;
			monthIndex = val + 1;
			dayIndex = val + 2;
		}
		else {
			monthIndex= val;
			dayIndex = val + 1;
			yearIndex = val + 2;
		}
			
		if (_month != null)
			_month.setTabIndex(monthIndex);
		if (_day != null)
			_day.setTabIndex(dayIndex);
		if (_year != null)
			_year.setTabIndex(yearIndex);
		if (_hour != null)
			_hour.setTabIndex(val + 3);

	}

}
