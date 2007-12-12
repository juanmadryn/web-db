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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlFormComponent.java $
//$Author: Dan $
//$Revision: 39 $
//$Modtime: 10/01/04 10:08a $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * This type is the base class for all Html Form Controls
 */
public abstract class HtmlFormComponent extends HtmlComponent {
	public static final int FORCE_CASE_NONE = 0;
	public static final int FORCE_CASE_UPPER = 1;
	public static final int FORCE_CASE_LOWER = 2;

	public static final int TAB_INDEX_DEFAULT = -1;
	public static final int TAB_INDEX_NONE = -2;
	
	protected Vector _events;
	protected Vector _listeners = null;

	protected String _value;
	protected DataStoreBuffer _dsBuff = null;
	protected int _dsColNo = -1;
	protected boolean _enabled = true;
	protected String _disabledFontStartTag;
	protected String _disabledFontEndTag;
	private boolean _focus = false;
	private int _focusRow = -1;
    private boolean _select = false;

	protected int _forceCase = FORCE_CASE_NONE;
	private String _theme;

	private String _formatLocaleKey;
	private boolean _updateLocale;
	private String _displayFormat;

	/**
	 * HtmlFormComponent constructor.
	 * @param name java.lang.String
	 * @param p com.salmonllc.html.HtmlPage
	 */
	public HtmlFormComponent(String name, HtmlPage p) {
		this(name, null, p);
	}

	/**
	 * HtmlFormComponent constructor
	 * @param name java.lang.String
	 * @param theme The theme to use for loading properties.
	 * @param p com.salmonllc.html.HtmlPage
	 */
	public HtmlFormComponent(String name, String theme, HtmlPage p) {
		super(name, p);
		setTheme(theme);
	}

	protected void addEvent(ValueChangedEvent e) {
		if (_events == null)
			_events = new Vector();

		_events.addElement(e);
	}

	/**
	 * This method adds a listener the will be notified when the value in this component changes.
	 * @param l The listener to add.
	 */
	public void addValueChangedListener(ValueChangedListener l) {
		if (_listeners == null)
			_listeners = new Vector();
		//
		int listenersSize = _listeners.size();
		for (int i = 0; i < listenersSize; i++) {
			if (((ValueChangedListener) _listeners.elementAt(i)) == l)
				return;
		}
		_listeners.addElement(l);
	}

	/**
	 * This method converts the text value according to case conversion if necessary.
	 */
	protected void convertValue() {
		if (_value != null) {
			switch (_forceCase) {
				case FORCE_CASE_NONE :
					break;
				case FORCE_CASE_UPPER :
					_value = _value.toUpperCase();
					break;
				case FORCE_CASE_LOWER :
					_value = _value.toLowerCase();
					break;
			}
		}
	}

	public boolean executeEvent(int eventType) throws Exception {
		if (eventType != EVENT_OTHER)
			return true;

		if (_events == null)
			return true;

		int eventNo = 0;
		while (_events.size() > eventNo) {
			ValueChangedEvent e = (ValueChangedEvent) _events.elementAt(0);

			boolean cont = true;
			if (_listeners != null) {
				for (int i = 0; i < _listeners.size(); i++) {
					cont = ((ValueChangedListener) _listeners.elementAt(i)).valueChanged(e);
					if (!cont && e.getAcceptValueInt() == ValueChangedEvent.PROCESSING_NOTENTERED)
						return false;
				}
			}

			if (e.getAcceptValueInt() == ValueChangedEvent.PROCESSING_NOTENTERED) {
				if (e.getAcceptValue())
					setValue(e.getNewValue(), e.getRow());
				else
					removeEvent(eventNo);
			} else {
				if (e.getAcceptValueInt() == ValueChangedEvent.PROCESSING_COMMIT_CHANGE)
					setValue(e.getNewValue(), e.getRow());
				else if (e.getAcceptValueInt() == ValueChangedEvent.PROCESSING_MOVE_CHANGE_TO_TEMP) {
					if (!setTempValue(e.getNewValue(), e.getRow(), eventNo))
						eventNo++;
				} else if (e.getAcceptValueInt() == ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE)
					eventNo++;
				else
					removeEvent(eventNo);
			}

			if (!cont)
				return false;
		}

		return true;
	}

	protected int findEvent(int rowNo) {
		int queueSize = getEventCount();
		for (int i = queueSize - 1; i > -1; i--) {
			ValueChangedEvent e = getEventAt(i);
			if (e.getRow() == rowNo)
				return i;
		}

		return -1;
	}

	abstract public void generateHTML(java.io.PrintWriter p, int row) throws Exception;

	/**
	 * This method returns the number of the column in the data store that this component is bound to.
	 */
	public int getColumnNumber() {
		return _dsColNo;
	}

	/**
	 * This method returns the datastore that this component is bound to
	 */
	public DataStoreBuffer getBoundDataStore() {
		return _dsBuff;
	}

	/**
	 * Returns the end font tag for disabled mode.
	 * @return java.lang.String
	 */
	public String getDisabledFontEndTag() {
		return _disabledFontEndTag;
	}

	/**
	 * Returns the font start tag for disabled mode.
	 * @return java.lang.String
	 */
	public String getDisabledFontStartTag() {
		return _disabledFontStartTag;
	}

	/**
	 * Returns true if the component is enabled to respond to user input.
	 * @return boolean
	 */
	public boolean getEnabled() {
		return _enabled;
	}

	protected ValueChangedEvent getEventAt(int index) {
		return (ValueChangedEvent) _events.elementAt(index);
	}

	protected int getEventCount() {
		if (_events == null)
			return 0;
		return _events.size();
	}

	/**
	 * This method returns the property theme for the component.
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * This method gets the value of the data in the component.
	 */
	public String getValue() {
		return getValue(-1);
	}

	/**
	 * This method gets the value of the data in the component at the specified row number.
	 */
	public String getValue(int rowNo) {

		return getValue(rowNo, false);

	}

	/**
	 * Gets the value in the datastore bound to this component
	 */
	protected String getDataStoreValue(int rowNo) {
		if (_dsBuff != null) {
			try {
				if (rowNo > -1)
					return(_dsBuff.getFormattedString(rowNo, _dsColNo));
				else
					return(_dsBuff.getFormattedString(_dsColNo));
			} catch (Exception e) {
			}
		}
		return null;
	}	
	/**
	 * This method gets the value of the data in the component at the specified row number.
	 */
	protected String getValue(int rowNo, boolean remove) {

		String retVal = _value;
		Object temp;

		if (_dsBuff != null) {
			try {
				if (rowNo > -1)
					retVal = _dsBuff.getFormattedString(rowNo, _dsColNo);
				else
					retVal = _dsBuff.getFormattedString(_dsColNo);
			} catch (Exception e) {
			}
		}

		int queueSize = getEventCount();
		for (int i = queueSize - 1; i > -1; i--) {
			ValueChangedEvent e = getEventAt(i);
			if (e.getRow() == rowNo) {
				temp = e.getNewValue();
				if (temp == null)
					retVal = null;
				else
					retVal = temp.toString();
				if (remove)
					removeEvent(i);
				break;
			}
		}

		return retVal;
	}

	public boolean isValid() {
		return true;
	}

	protected void removeEvent(int index) {
		_events.removeElementAt(index);
	}

	/**
	 * This method removes a listener from the list that will be notified if the text in the component changes.
	 * @param l The listener to remove.
	 */
	public void removeValueChangedListener(ValueChangedListener l) {
		if (_listeners == null)
			return;

		for (int i = 0; i < _listeners.size(); i++) {
			if (((ValueChangedListener) _listeners.elementAt(i)) == l) {
				_listeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * This method will clear all pending events from the event queue for this component.
	 */
	public void reset() {
		if (_events != null) {
			_events.setSize(0);
		}
	}

	/**
	 * Use this method to bind the component to a column in a DataStore.
	 * @param ds The datastore to bind to.
	 * @param columnNo The index of the column to bind to.
	 */
	public void setColumn(DataStoreBuffer ds, int columnNo) {
		if (columnNo < 0 || columnNo >= ds.getColumnCount())
		{
			String msg = "Column not in range columnNo=" + columnNo;
			msg += "\n ds and column not set";
			MessageLog.writeErrorMessage(msg, null, this);
			return;
		}
		

		_dsBuff = ds;
		_dsColNo = columnNo;
	}

	/**
	 * Use this method to bind the component to a column in a DataStore.
	 * @param ds The datastore to bind to.
	 * @param columnName The name of the column to bind to.
	 */
	public void setColumn(DataStoreBuffer ds, String columnName) {
		int colIndex = ds.getColumnIndex(columnName);
		if (colIndex < 0)
		{
			MessageLog.writeErrorMessage("UNMATCHED columnName=" + columnName, null, this);
		}
		setColumn(ds, colIndex);
	}

	/**
	 * Sets the font end tag for disabled mode.
	 * @param tag java.lang.String
	 */
	public void setDisabledFontEndTag(String tag) {
		_disabledFontEndTag = tag;
	}

	/**
	 * Sets the font tag for disabled mode.
	 * @param tag java.lang.String
	 */
	public void setDisabledFontStartTag(String tag) {
		_disabledFontStartTag = tag;
	}

	/**
	 * Enables or disables the ability of this component to respond to user input.
	 * @param enabled boolean
	 */
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	/**
	 * This method will set the edit focus to this component.
	 */
	public void setFocus() {
		_focus = true;
		_focusRow = -1;
        _select=false;
	}

    /**
     * This method will set the edit focus to this component.
     * If select is true it additionally highlights the input area of the component.
     */
    public void setFocus(boolean select) {
        _focus = true;
        _focusRow = -1;
        _select=select;
    }

    /**
     * This method will set the edit focus to this component for a particular row in a datastore.
     * If select is true it additionally highlights the input area of the component.
     */
    public void setFocus(int row, boolean select) {
        _focus = true;
        _focusRow = row;
        _select=select;

    }

	/**
	 * This method will set the edit focus to this component for a particular row in a datastore.
	 */
	public void setFocus(int row) {
		_focus = true;
		_focusRow = row;
        _select=false;
	}


	/**
	 * This method sets case-conversion mode for incoming text.
	 * @param mode int One of FORCE_CASE_NONE, FORCE_CASE_UPPER, FORCE_CASE_LOWER.
	 */
	public void setForceCase(int mode) {
		switch (mode) {
			case FORCE_CASE_NONE :
			case FORCE_CASE_UPPER :
			case FORCE_CASE_LOWER :
				break;
			default :
				// Ignore invalid values
				return;
		}
		_forceCase = mode;
	}

	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {
		Props props = getPage().getPageProperties();
		_disabledFontStartTag = props.getThemeProperty(theme, Props.FONT_DEFAULT + Props.TAG_START);
		_disabledFontEndTag = props.getThemeProperty(theme, Props.FONT_DEFAULT + Props.TAG_END);
		String s = props.getThemeProperty(theme, Props.TEXTEDIT_FORCE_CASE);
		if (s != null) {
			s = s.toLowerCase();
			if (s.equals("upper"))
				_forceCase = FORCE_CASE_UPPER;
			else if (s.equals("lower"))
				_forceCase = FORCE_CASE_LOWER;
		}
		_theme = theme;
	}

	/**
	 * This method sets the value of the component.
	 */
	public void setValue(String value) {
		setValue(value, -1);
	}

	/**
	 * This method sets the value of the component at the specified row number.
	 */
	public void setValue(String value, int rowNo) {
		_value = value;

		if (_dsBuff != null) {
			try {
				if (rowNo > -1)
					_dsBuff.setFormattedString(rowNo, _dsColNo, value);
				else
					_dsBuff.setFormattedString(_dsColNo, value);
			} catch (Exception e) {
				try {
					if (rowNo > -1)
						_dsBuff.setAny(rowNo, _dsColNo, null);
					else
						_dsBuff.setAny(_dsColNo, null);
				} catch (Exception ex) {
				}
			}
		}

		int queueSize = getEventCount();
		for (int i = queueSize - 1; i > -1; i--) {
			ValueChangedEvent e = getEventAt(i);
			if (e.getRow() == rowNo)
				removeEvent(i);
		}
	}

	protected boolean setTempValue(String value, int rowNo, int eventNo) {
		_value = value;
		if (_dsBuff != null) {
			try {
				if (rowNo > -1)
					_dsBuff.setTempValue(rowNo, _dsColNo, value);
				else
					_dsBuff.setTempValue(_dsColNo, value);
				removeEvent(eventNo);
				return true;
			} catch (Exception e) {
				MessageLog.writeErrorMessage("setTempValue", e, this);
			}
		}
		return false;
	}
	protected boolean valuesEqual(Object newValue, Object oldValue) {
		if (newValue == null && oldValue != null)
			return false;
		else if (newValue != null && oldValue == null)
			return false;
		else if (newValue != null && oldValue != null)
			if (!newValue.toString().trim().equals(oldValue.toString().trim()))
				return false;

		return true;
	}

	protected void writeFocusScript(java.io.PrintWriter p, int row) {
		if (!_focus)
			return;

		if (!_enabled || !_visible) {
			_focus = false;
			return;
		}

        String compToFocusName=null;
		if (_focusRow == -1) {
            compToFocusName=getFormString() + getFullName() ;
		} else if (row == _focusRow) {
            compToFocusName=getFormString() + getFullName() + "_" + _focusRow ;
		}

        if (compToFocusName!=null) {
            getPage().writeScript(compToFocusName + ".focus();");
            _focus = false;
            if (_select) {
                getPage().writeScript(compToFocusName + ".select();");
                _select=false;
            }
        }

	}

	/**
	 * This method returns the name of the component.
	 */
	public String toString() {
		return "[name=" + super.toString() + ", value=" + getValue() + "]";
	}

	/**
	* Returns the Locale key used for display format
	*/
	public String getDisplayFormatLocaleKey() {
		return _formatLocaleKey;
	}

	/**
	 * Sets the Locale key used for display format
	 */
	public void setDisplayFormatLocaleKey(String formatLocaleKey) {
		_formatLocaleKey = formatLocaleKey;
		_updateLocale = true;

	}

	/**
	* Returns the Display format used by this component
	*/
	public String getDisplayFormat() {
		return _displayFormat;
	}

	/**
	 * Sets the Display format used by this component. This only works if the component is bound to a Datastore column.
	 */
	public void setDisplayFormat(String format) {
		if (format != null) {
			//Retrieve format string from page properties           
			Props props = getPage().getPageProperties();
			String newFormat = props.getThemeProperty(_theme, format);
			if (newFormat != null)
				format = newFormat;
		}
		_displayFormat = format;
		_updateLocale = true;

	}

	public boolean isEnabled() {
		return _enabled;
	}

	/**
	 * Updates the text and format for the current local
	 */
	public void updateLocale() {
		_updateLocale = true;
	}

	protected void processLocaleInfo() {
		if (_updateLocale) {
			_updateLocale = false;
			LanguagePreferences p = getPage().getLanguagePreferences();

			if (_displayFormat != null && _dsBuff != null && _dsColNo != -1) {
				try {
					_dsBuff.setFormat(_dsColNo, _displayFormat);
				} catch (DataStoreException e) {
					MessageLog.writeErrorMessage("processLocaleInfo()", e, this);
				}
			}

			if (_formatLocaleKey != null && _dsBuff != null && _dsColNo != -1) {
				String newFormat = LanguageResourceFinder.getResource(getPage().getApplicationName(), _formatLocaleKey, p);
				if (newFormat != null) {
					try {
						_dsBuff.setFormat(_dsColNo, newFormat);
					} catch (DataStoreException e) {
						MessageLog.writeErrorMessage("processLocaleInfo()", e, this);
					}
				}
			}
		}
	}

	/**
	 * Enables or disables the ability of this component to respond to user input.
	 * @param enabled boolean
	 * @param useDisableAttribute whether to use the HTML disable attribute or not
	 */
	public void setEnabled(boolean enabled, boolean useDisableAttribute) {
		_enabled = enabled;
		setUseDisabledAttribute((useDisableAttribute) ? DISABLED_ATTRIBUTE_USE_ON_SUPPORTED_BROWSERS : DISABLED_ATTRIBUTE_USE_NEVER);
	}

	/**
	 * Generates a script for form components to automatically tab to the next field
	 * @param pw
	 */
	protected void generateAutoTabJavaScript(PrintWriter pw) {
		pw.println("<SCRIPT LANGUAGE=\"JavaScript\">");
		pw.println("<!-- Original:  Cyanide_7 (leo7278@hotmail.com) -->");
		pw.println("<!-- Web Site:  http://members.xoom.com/cyanide_7 -->");
		pw.println("<!-- This script and many more are available free online at -->");
		pw.println("<!-- The JavaScript Source!! http://javascript.internet.com -->");
		pw.println("function " + getFullName() + "_autoTab(input,len, e) {");
		pw.println("	var isNN = (navigator.appName.indexOf(\"Netscape\")!=-1);");
		pw.println("	var keyCode = (isNN) ? e.which : e.keyCode; ");
		pw.println("	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];");
		pw.println("	if(input.value.length >= len && !containsElement(filter,keyCode)) {");
		pw.println("		input.value = input.value.slice(0, len);");
		pw.println("		input.form[(getIndex(input)+1) % input.form.length].focus();");
		pw.println("	}");
		pw.println("			function containsElement(arr, ele) {");
		pw.println("				var found = false, index = 0;");
		pw.println("				while(!found && index < arr.length)");
		pw.println("				if(arr[index] == ele)");
		pw.println("					found = true;");
		pw.println("				else");
		pw.println("					index++;");
		pw.println("				return found;");
		pw.println("			}");
		pw.println("");
		pw.println("			function getIndex(input) {");
		pw.println("				var index = -1, i = 0, found = false;");
		pw.println("				while (i < input.form.length && index == -1)");
		pw.println("					if (input.form[i] == input)");
		pw.println("						index = i;");
		pw.println("					else ");
		pw.println("						i++;");
		pw.println("				return index;");
		pw.println("			}");
		pw.println("			return true;");
		pw.println("}");
		pw.println("</script>");

	}

	/**
	 * Returns the name of this component would be referenced by if used in javascript code
	 */
	public String getJavaScriptName() {
		return getFormString() + getFullName();	
	}	
}
