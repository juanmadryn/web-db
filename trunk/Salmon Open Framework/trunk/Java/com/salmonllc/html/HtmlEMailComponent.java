package com.salmonllc.html;

import com.salmonllc.html.events.ValueChangedEvent;
import java.util.Hashtable;

/**
 * Implements an entry field for an email address
 */
public class HtmlEMailComponent extends HtmlFormComponent
{
    protected HtmlTextEdit _email;
    private String _EMail;
    private int _size = 20 ;
    private int _maxlength = 50 ;
    private int _minlength = 7 ;

/**
 * EMailComponent constructor.
 *
 * @param name		Name of component.
 * @param p			Page containing component.
 * @param phoneSeparator	character to display between the 3rd & 4th digit of the phone.
 */
public HtmlEMailComponent(String name, HtmlPage p) {
	this(name, p, 7);
		
}
/**
 * EMailComponent constructor.
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 * @param minLength int
 */
public HtmlEMailComponent(String name, HtmlPage p, int minLength)
{
	super(name, p);
	_email = new HtmlTextEdit(name, p);
	_email.setGenerateNewline(false);
	_email.setSize(_size);
	_email.setMaxLength(_maxlength);
	setMinLength(minLength);
}
public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
	// It is essential to call getValue() here because there may be a ValueChanged
	// event in the queue for this object, which needs to be removed, which getValue()
	// does.  The secondary calls to getValue() from the container will not
	// do this because there are no events associated with them.
	String val = getValue(row, true);
	if (val == null) {
		_email.setValue(null, row);
	}
	else {
	  String sVal;
	  if (_dsBuff != null) {
		if (row >= 0)
		  sVal = _dsBuff.getString(row, _dsColNo);
		else
		  sVal = _dsBuff.getString(_dsColNo);
	   }
	  else
	    sVal = val;
	  _email.setValue(sVal,row);
	}
	_email.generateHTML(p, row);
	if (_visible && _enabled)
		p.println("");
}
/**
 * Returns the sub-component to be used for setting focus.
 * @return com.salmonllc.html.HtmlComponent
 */
public HtmlComponent getFocus () {
	return _email;
}
/**
 * Returns whether the e-mail entered is valid or not.
 * @return boolean Indicates whether e-mail is valid or not.
 */
	public boolean isValid() {

	  _email.setValue(_EMail);
	  int atIndex=_EMail.indexOf('@');
	  if (atIndex<0)
	    return false;
	  if (_EMail.substring(atIndex+1).indexOf('@')>=0)
	    return false;
	  String sBeforeAt=_EMail.substring(0,atIndex);
	  String sAfterAt=_EMail.substring(atIndex+1);
	  if (sBeforeAt.length()<1)
	    return false;
	  if (sAfterAt.length()<_minlength-2)
	    return false;
	  int iDotIndex=sAfterAt.indexOf('.');
	  if (iDotIndex<0)
	    return false;
	  if (sAfterAt.substring(iDotIndex+1).length()<1)
	    return false;
	  return true; 
	}
public boolean processParms(Hashtable parms, int rowNo) throws Exception {

	if (!getVisible() || !getEnabled())
		return false;
	
	// Determine the old value from both edit fields.
		
	String oldValue;
	if (_dsBuff == null) {
		oldValue = _email.getValue();
	}
	else {
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

	// Determine the new value from both edit fields.
	
	String newValue;
	String name1 = _email.getFullName();
	if (rowNo > -1)
		name1 += "_" + rowNo;
	String val[] = (String[]) parms.get(name1);
	if (val != null) {
		_EMail=newValue = val[0].trim();
		if (newValue.equals(""))
			newValue = null;
	}		
	else
		newValue = null;


		

	// Compare old and new values and possibly create a ValueChangedEvent.
	if (!valuesEqual(oldValue, newValue)) {
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),oldValue,newValue,rowNo,_dsColNo,_dsBuff);
		addEvent(e);
	}
	return false;
}
public void reset() {
	super.reset();
	_email.reset();
}
/**
 * Specifies the Style Class to be used for the EMail Component.
 * Creation date: (7/19/01 8:41:20 AM)
 * @param sClass java.lang.String A name of a class in Html to be used by this component
 */
public void setClassName(String sClass) {
	super.setClassName(sClass);
	_email.setClassName(sClass);
}
    /**
    * Sets the font end tag for disabled mode.
    * @param tag java.lang.String
    */
    public void setDisabledFontEndTag(String tag)
    {
        _email.setDisabledFontEndTag(tag);
    }
    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag)
    {
        _email.setDisabledFontStartTag(tag);
    }
/**
 * Enables or disables the ability of this component to respond to user input.
 * @param editable boolean
 */
public void setEnabled (boolean enabled) {
	super.setEnabled(enabled);
	_email.setEnabled(enabled);
}
/**
 * Sets the Max Length for the E-Mail.
 * @param maxLength int
 */
public void setMaxLength(int maxLength) {
	_email.setMaxLength(maxLength);
}
/**
 * This method sets the minimum length an email address can be default is 7
 * @param minLength int
 */
public void setMinLength(int minLength)
{
	_minlength = minLength;
}
/**
 * Specifies the parent component for this E-Mail Component.
 * Creation date: (7/19/01 8:41:20 AM)
 * @param sClass java.lang.String A name of a class in Html to be used by this component
 */
public void setParent(HtmlComponent parent) {
	super.setParent(parent);
	// This is necessary for the name to be generated correctly, else the leading
	// sequence of parent names will be lost.
	_email.setParent(parent);
	
}
/**
 * Set the size of the E-Mail Component.
 * @param size int
 */
public void setSize(int size) {
	_email.setSize(size);
}

/**
 * @returns the access key html attribute
 */
public String getAccessKey() {
	return _email.getAccessKey();
}

/**
 * @returns the read only html attribute
 */
public boolean getReadOnly() {
	return _email.getReadOnly();
}

/**
 * @returns the tab index html attribute
 */
public int getTabIndex() {
	return _email.getTabIndex();
}

/**
 * @param sets the access key html attribute
 */
public void setAccessKey(String val) {
	_email.setAccessKey(val);
}

/**
 * @param sets the read only html attribute
 */
public void setReadOnly(boolean val) {
	_email.setReadOnly(val);
}
/**
 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
 */
public void setTabIndex(int val) {
	_email.setTabIndex(val);
}
}
