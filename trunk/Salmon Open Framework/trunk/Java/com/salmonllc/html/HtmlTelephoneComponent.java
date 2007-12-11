package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTelephoneComponent.java $
//$Author: Dan $
//$Revision: 37 $
//$Modtime: 3/17/04 2:01p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import java.util.Hashtable;

/**
 * Creates a component for editing a telephone number
 */

public class HtmlTelephoneComponent extends HtmlFormComponent
{
    protected HtmlContainer _cont;
    protected HtmlTextEdit _editAreaCode;
    protected HtmlTextEdit _editPhoneStart;
    protected HtmlTextEdit _editPhoneEnd;

    private HtmlText _txtAreaCodeStart;
    private HtmlText _txtAreaCodeEnd;
    private HtmlText _txtSeparator;

    private String _sAreaCode;
    private String _sPhoneStart;
    private String _sPhoneEnd;
    private String _sPhoneSeparator;
    private String _sAreaCodeStart;
    private String _sAreaCodeEnd;

    private boolean _bApplyStyle;
    private boolean _autoTab;

    /**
     * HtmlTelephoneComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     */
    public HtmlTelephoneComponent(String name, HtmlPage p) {
        this(name, p, "-", "(", ")", true);
    }
    /**
     * HtmlTelephoneComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param phoneSeparator character to display between the 3rd & 4th digit of the phone.
     * @param areaCodeStart	 character to display before an area code
     * @param areaCodeEnd	 character to display after an area code
     * @param bApplyStyle    boolean to elect to apply the style to the static elements of the phone component, i.e. ()-
     */
    public HtmlTelephoneComponent(String name, HtmlPage p, String phoneSeparator, String areaCodeStart, String areaCodeEnd, boolean bApplyStyle) {
        super(name, p);

		boolean netscape6or7 = (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() >= 6);
        _sAreaCodeStart = areaCodeStart;
        _sAreaCodeEnd = areaCodeEnd;
        _sPhoneSeparator = phoneSeparator;

        // 10/01/03 - BYD - Added the option to not apply a style to the static elements of a telephone #
        //          such as () and -
        //          The main reason is to avoid unsightly border when border is added to style
        //          The default is to apply the style ... for backward compatibility
        _bApplyStyle = bApplyStyle;

        _txtAreaCodeStart = new HtmlText(areaCodeStart, getPage());
        _txtAreaCodeEnd = new HtmlText(areaCodeEnd, getPage());
        _txtSeparator = new HtmlText(phoneSeparator, getPage());

        _cont = new HtmlContainer(name, p);
        // starting area code char
        if (areaCodeStart != null && !areaCodeStart.trim().equals("")) {
            _cont.add(_txtAreaCodeStart);
        }

        // area code field
        _cont.add(_editAreaCode = new HtmlTextEdit("areaCode", p));
        _editAreaCode.setGenerateNewline(false);
        _editAreaCode.setSize(netscape6or7 ? 5 : 3);
        _editAreaCode.setMaxLength(3);

        // ending area code char
        if (areaCodeEnd != null && !areaCodeEnd.trim().equals("")) {
            _cont.add(_txtAreaCodeEnd);
        }
        //first three digits of hone number
        _cont.add(_editPhoneStart = new HtmlTextEdit("phoneStart", p));
        _editPhoneStart.setGenerateNewline(false);
        _editPhoneStart.setSize(netscape6or7 ? 5 : 3);
        _editPhoneStart.setMaxLength(3);

        // separator
        if (phoneSeparator != null && !phoneSeparator.trim().equals("")) {
            _cont.add(_txtSeparator);
        }

        // last four digits
        _cont.add(_editPhoneEnd = new HtmlTextEdit("phoneEnd", p));
        _editPhoneEnd.setGenerateNewline(false);
        _editPhoneEnd.setSize(netscape6or7 ? 7 : 4);
        _editPhoneEnd.setMaxLength(4);

    }
    public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        // It is essential to call getValue() here because there may be a ValueChanged
        // event in the queue for this object, which needs to be removed, which getValue()
        // does.  The secondary calls to getValue() from the container will not
        // do this because there are no events associated with them.
        String val = getValue(row, true);
        if (val == null || val.trim().equals("")) {
            _editAreaCode.setValue(null, row);
            _editPhoneStart.setValue(null, row);
            _editPhoneEnd.setValue(null, row);
        } else {
            String sVal;
            if (_dsBuff != null) {
                if (row >= 0)
                    sVal = _dsBuff.getString(row, _dsColNo);
                else
                    sVal = _dsBuff.getString(_dsColNo);
            } else
                sVal = val;

            /* If user enters inc=valid value like "'$&@!$%^()* " val is not null but the value in the
            datastore is null. Therefore sVal is null.*/
            if (sVal != null && sVal.length() == 10) {
                _editAreaCode.setValue(sVal.substring(0, 3), row);
                _editPhoneStart.setValue(sVal.substring(3, 6), row);
                _editPhoneEnd.setValue(sVal.substring(6), row);
            }
            if (sVal != null && sVal.length() == 7) {
                _editAreaCode.setValue(null, row);
                _editPhoneStart.setValue(sVal.substring(0, 3), row);
                _editPhoneEnd.setValue(sVal.substring(3), row);
            }
            if (sVal != null && sVal.length() != 10 && sVal.length() != 7) {
                _editAreaCode.setValue(_sAreaCode);
                _editPhoneStart.setValue(_sPhoneStart);
                _editPhoneEnd.setValue(_sPhoneEnd);
            }

        }
        if (_enabled && _autoTab) {
			_editAreaCode.setOnKeyUp("return " + getFullName() + "_autoTab(this, 3, event);");
			_editPhoneStart.setOnKeyUp("return " + getFullName() + "_autoTab(this, 3, event);");	
        }
        else {
			_editAreaCode.setOnKeyUp(null);
			_editPhoneStart.setOnKeyUp(null);
        }	
        	
        if (_enabled || useDisabledAttribute() || val != null)
            _cont.generateHTML(p, row);

        if (_visible && _enabled) {
            p.println("");
			if (_autoTab) 
				generateAutoTabJavaScript(p);
        }    
    }
    /**
     * 10/01/03 - BYD - Added the option to not apply a style to the static elements of a telephone #
     *          such as () and -
     *          The main reason is to avoid unsightly border when border is added to style
     *
     * Gets the Apply Style boolean
     * @return boolean
     */
    public boolean getApplyStyle() {
        return _bApplyStyle;
    }
    /**
     * 10/01/03 - BYD - Added the option to not apply a style to the static elements of a telephone #
     *          such as () and -
     *          The main reason is to avoid unsightly border when border is added to style
     *
     * Sets the Apply Style boolean for static elements of the phone component
     * @param bApplyStyle boolean
     */
    public void setApplyStyle(boolean bApplyStyle) {
        _bApplyStyle = bApplyStyle;
    }
    /**
     * Gets the Area code.
     * @return java.lang.String
     */
    public String getAreaCode() {
        return _sAreaCode != null ? _sAreaCode : "";
    }
    /**
     * Gets the Area code ending char.
     * @return java.lang.String
     */
    public java.lang.String getAreaCodeEnd() {
        return _sAreaCodeEnd;
    }
    /**
     * Gets the Area code starting char.
     * @return java.lang.String
     */
    public java.lang.String getAreaCodeStart() {
        return _sAreaCodeStart;
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocus() {
        return _editAreaCode;
    }
    /**
     * This method returns the formatted phone number provided that the component
     * contains a valid value otherwise it returns an empty string
     * @return java.lang.String
     */
    public String getFormattedValue() {
        String sFormattedPhone = "";

        if (isValid()) {
            if (_sAreaCode.length() == 3)
                sFormattedPhone = _sAreaCodeStart + _sAreaCode + _sAreaCodeEnd;

            sFormattedPhone += _sPhoneStart + _sPhoneSeparator + _sPhoneEnd;
        }

        return sFormattedPhone;
    }
    /**
     * Gets the Phone Number.
     * @return java.lang.String
     */
    public String getPhone() {
        return _sPhoneStart + _sPhoneEnd;
    }
    /**
     * Gets the separator char.
     * @return java.lang.String
     */
    public java.lang.String getPhoneSeparator() {
        return _sPhoneSeparator;
    }
    /**
     * Gets the current value of the control.
     * @return java.lang.String
     */
    public String getValue() {
        String sValue = "";
        if (_sAreaCode != null)
            sValue += _sAreaCode;
        if (_sPhoneStart != null)
            sValue += _sPhoneStart;
        if (_sPhoneEnd != null)
            sValue += _sPhoneEnd;
        return sValue;
    }
    /**
     * Returns whether component is visible or not.
     * Creation date: (7/19/01 8:41:20 AM)
     * @return boolean Indicates if component is visible or not.
     */
    public boolean getVisible() {
        return _cont.getVisible();
    }
    /**
     * Returns whether the Telephone Number entered is valid or not.
     * @return boolean Indicates whether Telephone Number is valid or not.
     */
    public boolean isValid() {
        String sAC = _sAreaCode;
        String sPhoneStart = _sPhoneStart;
        String sPhoneEnd = _sPhoneEnd;

        _editAreaCode.setValue(_sAreaCode);
        _editPhoneStart.setValue(_sPhoneStart);
        _editPhoneEnd.setValue(_sPhoneEnd);

        String sPhone = sPhoneStart + sPhoneEnd;

        int iValidLength = 7;

        // If the Area Code is not null prepend it to the phone string
        // and adjust the correct length of the phone string
        if (sAC != null && !sAC.trim().equals("")) {
            sPhone = sAC + sPhone;
            iValidLength = 10;
        }

        // Ensure that the length of the phone string is correct
        if (sPhone.length() != iValidLength)
            return false;

        // Corrected the argument of the charAt() method from 0 to i to ensure
        // that all characters be checked and not just the first one
        boolean bNumeric = true;

        for (int i = 0; i < sPhone.length(); i++) {
            char c = sPhone.charAt(i);

            if (!Character.isDigit(c)) {
                bNumeric = false;
                break;
            }
        }
        return bNumeric;
    }
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {

        if (!getVisible() || !getEnabled())
            return false;

        // Determine the old value from both edit fields.

        String oldValue = "";
        if (_dsBuff == null) {
            String sAreaCD = _editAreaCode.getValue();
            String sExchge = _editPhoneStart.getValue();
            String sLast4 = _editPhoneEnd.getValue();

            /*
            We have to create the value changed events in the same way for both old and new values.
            if the old values is null for all fields, than oldValue = "nullnullnull". If user changed only one field
            we have to still create the old and new values by combining 3 fields. In that case old value
            can be "null123null". Although the phone number is not valid, we must still create a value changed event.
            TT20010713
            */
            if (sAreaCD != null)
                oldValue += sAreaCD;
            if (sExchge != null)
                oldValue += sExchge;
            if (sLast4 != null)
                oldValue += sLast4;

        } else {
            String s;
            if (rowNo > -1)
                s = _dsBuff.getString(rowNo, _dsColNo);
            else
                s = _dsBuff.getString(_dsColNo);
            if (s == null)
                oldValue = "";
            else
                oldValue = s;
        }

        // Determine the new value from all three edit fields.

        String newValue = "", newValue1, newValue2, newValue3;

        String name1 = _editAreaCode.getFullName();
        if (rowNo > -1)
            name1 += "_" + rowNo;
        String val[] = (String[]) parms.get(name1);
        if (val != null) {
            _sAreaCode = newValue1 = val[0].trim();
            if (newValue1.equals(""))
                newValue1 = null;
        } else {
            newValue1 = null;
        }

        String name2 = _editPhoneStart.getFullName();
        if (rowNo > -1)
            name2 += "_" + rowNo;
        val = (String[]) parms.get(name2);
        if (val != null) {
            _sPhoneStart = newValue2 = val[0].trim();
            if (newValue2.equals(""))
                newValue2 = null;
        } else {
            newValue2 = null;
        }


        String name3 = _editPhoneEnd.getFullName();
        if (rowNo > -1)
            name3 += "_" + rowNo;
        val = (String[]) parms.get(name3);
        if (val != null) {
            _sPhoneEnd = newValue3 = val[0].trim();
            if (newValue3.equals(""))
                newValue3 = null;
        } else {
            newValue3 = null;
        }


        // if (newValue1 != null && newValue2 != null && newValue3 != null) TT20010713
        if (newValue1 != null)
            newValue += newValue1;
        if (newValue2 != null)
            newValue += newValue2;
        if (newValue3 != null)
            newValue += newValue3;

        // else         TT20010713
        // 	newValue = null;        TT20010713

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
     * Replaces the area code end component
     * @param newAreaCodeEnd java.lang.String
     */
    public void setAreaCodeEnd(java.lang.String newAreaCodeEnd) {
        _sAreaCodeEnd = newAreaCodeEnd;
        // create new text comp
        HtmlText txtNewAreaCodeEnd = new HtmlText(newAreaCodeEnd, getPage());

        // replace old area code end comp with new one
        _cont.replaceComponent(txtNewAreaCodeEnd, _txtAreaCodeEnd);

        //	save ref of new comp
        _txtAreaCodeEnd = txtNewAreaCodeEnd;
    }
    /**
     * Replaces the area code start component
     * @param newAreaCodeStart java.lang.String
     */
    public void setAreaCodeStart(java.lang.String newAreaCodeStart) {
        _sAreaCodeStart = newAreaCodeStart;
        // create new text comp
        HtmlText txtNewAreaCodeStart = new HtmlText(newAreaCodeStart, getPage());

        // replace old area code start comp with new one
        _cont.replaceComponent(txtNewAreaCodeStart, _txtAreaCodeStart);

        //	save ref of new comp
        _txtAreaCodeStart = txtNewAreaCodeStart;
    }
    /**
     * Specifies the Style Class to be used for the Telephone Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
    public void setClassName(String sClass) {
        super.setClassName(sClass);

        if (_bApplyStyle)
        {
            if (_txtAreaCodeStart != null)
                _txtAreaCodeStart.setClassName(sClass);
            if (_txtAreaCodeEnd != null)
                _txtAreaCodeEnd.setClassName(sClass);
            if (_txtSeparator != null)
                _txtSeparator.setClassName(sClass);
        }

        if (_editAreaCode != null)
            _editAreaCode.setClassName(sClass);
        if (_editPhoneStart != null)
            _editPhoneStart.setClassName(sClass);
        if (_editPhoneEnd != null)
            _editPhoneEnd.setClassName(sClass);
    }
/**
* Sets the font end tag for disabled mode.
* @param tag java.lang.String
*/
public void setDisabledFontEndTag(String tag)
{
    _editAreaCode.setDisabledFontEndTag(tag);
    _editPhoneStart.setDisabledFontEndTag(tag);
    _editPhoneEnd.setDisabledFontEndTag(tag);

    _txtAreaCodeStart.setFontEndTag(tag);
    _txtAreaCodeEnd.setFontEndTag(tag);
    _txtSeparator.setFontEndTag(tag);

}
/**
 * Sets the font tag for disabled mode.
 * @param tag java.lang.String
 */
public void setDisabledFontStartTag(String tag)
{

    _editAreaCode.setDisabledFontStartTag(tag);
    _editPhoneStart.setDisabledFontStartTag(tag);
    _editPhoneEnd.setDisabledFontStartTag(tag);

    _txtAreaCodeStart.setFontStartTag(tag);
    _txtAreaCodeEnd.setFontStartTag(tag);
    _txtSeparator.setFontStartTag(tag);
}
    /**
     * Enables or disables the ability of this component to respond to user input.
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _cont.setEnabled(enabled);
    }
    /**
     * This method will set the edit focus to this component.
     */
    public void setFocus() {
           _editAreaCode.setFocus();
    }
    /**
     * This method will set the edit focus to this component for a particular row in a datastore.
     */
    public void setFocus(int row) {
        _editAreaCode.setFocus(row);
    }
    /**
     * Specifies the parent component for this Telephone Component.
     * Creation date: (7/19/01 8:41:20 AM)
     */
    public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        // This is necessary for the name to be generated correctly, else the leading
        // sequence of parent names will be lost.
        _cont.setParent(parent);
    }
    /**
     * Replaces the separator between the phone numbers
     * @param newPhoneSeparator java.lang.String
     */
    public void setPhoneSeparator(java.lang.String newPhoneSeparator) {
        _sPhoneSeparator = newPhoneSeparator;

        // create new text comp
        HtmlText txtNewSeparator = new HtmlText(newPhoneSeparator, getPage());

        // replace old area code end comp with new one
        _cont.replaceComponent(txtNewSeparator, _txtSeparator);

        //	save ref of new comp
        _txtSeparator = txtNewSeparator;
    }
    /**
     * Sets the theme of the Telephone Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param theme java.lang.String The theme of the component
     */
    public void setTheme(String theme) {
        super.setTheme(theme);

        if (_txtAreaCodeStart != null)
            _txtAreaCodeStart.setTheme(theme);
        if (_txtAreaCodeEnd != null)
            _txtAreaCodeEnd.setTheme(theme);
        if (_txtSeparator != null)
            _txtSeparator.setTheme(theme);

        if (_editAreaCode != null)
            _editAreaCode.setTheme(theme);
        if (_editPhoneStart != null)
            _editPhoneStart.setTheme(theme);
        if (_editPhoneEnd != null)
            _editPhoneEnd.setTheme(theme);
    }
    /**
     * Sets the visibility of the component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param visible boolean Indicates the visibility of the component
     */
    public void setVisible(boolean visible) {
        _cont.setVisible(visible);
    }

    /**
     * Gets the middle three digits.
     * @return java.lang.String
     */
    public String getMiddleThree() {
        return _sPhoneStart != null ? _sPhoneStart : "";
    }
    /**
     * Gets the last four digits.
     * @return java.lang.String
     */
    public String getLastFour() {
        return _sPhoneEnd != null ? _sPhoneEnd : "";
    }

	

	/**
	 * @return true if the component is set to automatically tab to next field
	 */
	public boolean isAutoTab() {
		return _autoTab;
	}

	/**
	 * @return true if the component is set to automatically tab to next field
	 */
	public void setAutoTab(boolean b) {
		_autoTab = b;
	}
	
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _editAreaCode.getAccessKey();
	}

	/**
	 * @returns the read only html attribute
	 */
	public boolean getReadOnly() {
		return _editAreaCode.getReadOnly();
	}

	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		return _editAreaCode.getTabIndex();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String val) {
		_editAreaCode.setAccessKey(val);

	}

	/**
	 * @param sets the read only html attribute
	 */
	public void setReadOnly(boolean val) {
		_editAreaCode.setReadOnly(val);
		_editPhoneStart.setReadOnly(val);
		_editPhoneEnd.setReadOnly(val);
	}
	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		_editAreaCode.setTabIndex(val);
		_editPhoneStart.setTabIndex(val + 1);
		_editPhoneEnd.setTabIndex(val +2 );
	}


	/**
	 * Returns the name of this component would be referenced by if used in javascript code
	 */
	public String getJavaScriptName() {
		return getFormString() + _editAreaCode.getFullName();	
	}	
}
