package com.salmonllc.html;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.util.Util;
import java.util.Hashtable;

/**
 * This class creates a component for the editing of a social security number as a single edit field.
 */

public class HtmlSSNSingleComponent extends HtmlFormComponent
{
    private HtmlTextEdit _editSSN;
    private char _fSeparator = '-';
    private static String _separatorStr = "-";


 
    /**
     * 	This is the constructor of this class, and initializes the SSN Component.
     *	@param name	java.lang.String Name of component.
     * 	@param p HtmlPage Page containing component.
     */
public HtmlSSNSingleComponent(String name, HtmlPage p) {
	super(name, p);
	_editSSN = new HtmlTextEdit(name, p);
	_editSSN.setGenerateNewline(false);
	_editSSN.setSize(11);
	_editSSN.setMaxLength(11);
}
/**
 *	This method returns the SSN of a user with proper format (i.e. with dashes).
 *	@param s java.lang.String A string of the SSN value to be formatted.
 * 	@return java.lang.String
 */

public static String formatValue(String s)
{
    String ssn;

    if (s == null)
        {
        ssn = "";
    }
    else if (s.length() == 9)
        {
        ssn = s.substring(0, 3) + _separatorStr + s.substring(3, 5) + _separatorStr + s.substring(5);
    }
    else
        {
        ssn = s;
    }
    return ssn;
}
/**
 * 	This method generates the part of the HTML for the page used to render the SSN component.
 * 	@param p java.io.PrintWriter
 * 	@param row int
 */
 
public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
	// It is essential to call getValue() here because there may be a ValueChanged
	// event in the queue for this object, which needs to be removed, which getValue()
	// does.  The secondary calls to getValue() from the container will not
	// do this because there are no events associated with them.
	String val = getValue(row, true);
	if (val == null) {
		_editSSN.setValue(null, row);
	} else {
		String sVal;
		if (_dsBuff != null) {
			if (row >= 0)
				sVal = _dsBuff.getString(row, _dsColNo);
			else
				sVal = _dsBuff.getString(_dsColNo);
		} else {
	    	sVal = val;
		}
		_editSSN.setValue(formatValue(sVal), row);
	}
	_editSSN.generateHTML(p, row);
	if (_visible && _enabled)
		p.println("");
}
/**
 * 	This method returns the sub-component to be used for setting focus.
 * 	@return com.salmonllc.html.HtmlComponent
 */
public HtmlComponent getFocus () {
	return _editSSN;
}
/**
 *	This method returns the SSN of a user with proper format (i.e. with dashes).
 * 	@return java.lang.String
 */
 
public String getFormattedValue() {
	return formatValue(super.getValue());
}
/**
 * This method returns the seperator character for the social security number.
 */
public String getSeparator() {
	return new Character(_fSeparator).toString();
}
/**
 *	This method gets the value of the SSN# and returns it in a string format.
 * 	@return java.lang.String
 */
 
public String getValue() {
	return Util.stripChars(super.getValue(), "0123456789", null);
}
/**	
 *	This method checks to see if the user-entered value for the SSN# is valid.
 *	@return boolean if the SSN# is valid.
 */

public boolean isValid() {
	String s = getValue();
	if (s == null) {
		return false;
	} else if (s.length() == 9) {
		return Util.isNumeric(s);
	} else if (s.length() == 11) {
		if ((s.charAt(3) != _fSeparator) || (s.charAt(6) != _fSeparator) ||
			!Util.isNumeric(s.substring(0,3)) || !Util.isNumeric(s.substring(4,6)) || !Util.isNumeric(s.substring(7,11))) {
				return false;
		} else {
			setValue(s.substring(0,3) + s.substring(4,6) + s.substring(7,11));
			return true;
		}
	} else {
		return false;
	}
}
/**
 *	This method compares the contents of the SSN component with that in the corresponding row and column in the datastore to determine if a change has occurred.
 *	@param parms Hashtable
 *	@param rowNo int
 *	@return boolean 
 */

public boolean processParms(Hashtable parms, int rowNo) throws Exception {

	if (!getVisible() || !getEnabled())
		return false;
	
	String oldValue;
	if (_dsBuff == null) {
		oldValue = _editSSN.getValue();
	} else {
		String s;
		if (rowNo > -1)
			s = _dsBuff.getString(rowNo, _dsColNo);
		else
			s= _dsBuff.getString(_dsColNo);
		if (s == null)
			oldValue = null;
		else
			oldValue = s;	
	}

	String newValue;
	String name1 = _editSSN.getFullName();
	if (rowNo > -1)
		name1 += "_" + rowNo;
	String val[] = (String[]) parms.get(name1);
	if (val != null) {
		newValue = val[0].trim();
		if (newValue.equals(""))
			newValue = null;
		else if (newValue.length() == 11)
			newValue = newValue.substring(0,3) + newValue.substring(4,6) + newValue.substring(7,11);
	} else {
		newValue = null;
	}

	// Compare old and new values and possibly create a ValueChangedEvent.
	if (!valuesEqual(oldValue, newValue)) {
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),oldValue,newValue,rowNo,_dsColNo,_dsBuff);
		addEvent(e);
	}
	return false;
}
/**
 * 	This method will clear all pending events from the event queue for this component. 
 */
public void reset() {
	super.reset();
	_editSSN.reset();
}
/**
 *	This method is used to associate a specific set of properties from the properties file with the SSN component to change its presentation from the default one.
 *	@param theme java.lang.String
 */

public void setClassName(String sClass) {
	super.setClassName(sClass);
	if (sClass != null) 
		_editSSN.setClassName(sClass);
}
    /**
      * Sets the font end tag for disabled mode.
      * @param tag java.lang.String
      */
    public void setDisabledFontEndTag(String tag)
    {
        _editSSN.setDisabledFontEndTag(tag);

    }
    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag)
    {

        _editSSN.setDisabledFontStartTag(tag);

    }
/**
 * 	This method enables or disables the ability of this component to respond to user input.
 * 	@param editable boolean
 */
public void setEnabled (boolean enabled) {
	super.setEnabled(enabled);
	_editSSN.setEnabled(enabled);
}
/**
 *	This method sets the parent of the SSN component, i.e. the component containing the SSN component, so that the component can be named properly.  
 *	@param parent HtmlComponent 
 */

public void setParent(HtmlComponent parent) {
	super.setParent(parent);
	// This is necessary for the name to be generated correctly, else the leading
	// sequence of parent names will be lost.
	_editSSN.setParent(parent);
	
}
/**
 * This method sets the seperator character for the ssn
 */
public void setSeparator(String newSeparator)
{
    _fSeparator = newSeparator.charAt(0);
    _separatorStr = newSeparator;
}
/**
 *	This method is used to associate a specific set of properties from the properties file with the SSN component to change its presentation from the default one.
 *	@param theme java.lang.String
 */

public void setTheme(String theme) {
	super.setTheme(theme);
	if (theme != null) 
		_editSSN.setTheme(theme);
}

/**
 * @returns the access key html attribute
 */
public String getAccessKey() {
	return _editSSN.getAccessKey();
}

/**
 * @returns the read only html attribute
 */
public boolean getReadOnly() {
	return _editSSN.getReadOnly();
}

/**
 * @returns the tab index html attribute
 */
public int getTabIndex() {
	return _editSSN.getTabIndex();
}

/**
 * @param sets the access key html attribute
 */
public void setAccessKey(String val) {
	_editSSN.setAccessKey(val);
}

/**
 * @param sets the read only html attribute
 */
public void setReadOnly(boolean val) {
	_editSSN.setReadOnly(val);
}
/**
 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
 */
public void setTabIndex(int val) {
	_editSSN.setTabIndex(val);
}
}
