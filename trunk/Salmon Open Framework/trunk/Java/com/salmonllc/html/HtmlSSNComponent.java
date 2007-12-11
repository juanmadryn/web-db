package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlSSNComponent.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 10/20/03 3:44p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import java.util.Hashtable;

/**
 * Implements a three-part entry field for a social security number.
 */
public class HtmlSSNComponent extends HtmlFormComponent {
    protected HtmlTextEdit _hteSSPart1;
    protected HtmlTextEdit _hteSSPart2;
    protected HtmlTextEdit _hteSSPart3;

    protected HtmlContainer _cont;

    public HtmlComponent _hcFocusComp;

    public String _sSSN;
    public String _sSeparator;
    private HtmlText _htSeparator1;
    private HtmlText _htSeparator2;

    private String _sPart1;
    private String _sPart2;
    private String _sPart3;

    private final int iLength1 = 3;
    private final int iLength2 = 2;
    private final int iLength3 = 4;

    private String _font;
    private String _fontStartTag;
    private String _fontEndTag;

    /**
     * HtmlSSNComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     */
    public HtmlSSNComponent(String name, HtmlPage p) {
        this(name, p, "-");
    }
    /**
     * HtmlSSNComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param separator	Character to display between the three part of the Social Security Number
     */
    public HtmlSSNComponent(String name, HtmlPage p, String separator) {
        this(name, p, "-", true);
    }
    /**
     * HtmlSSNComponent constructor comment.
     *
     * @param name			Name of component.
     * @param p				Page containing component.
     * @param separator		Character to display between the three part of the Social Security Number
     * @param bEnableFlag	Flag to set the component to editable (true) or read only (false)
     */
    public HtmlSSNComponent(String name, HtmlPage p, String separator, boolean bEnableFlag) {
        super(name, p);
        _cont = new HtmlContainer(name, p);
        _sSeparator = separator;

        _cont.add(_hteSSPart1 = new HtmlTextEdit("SSPart1", p));

        _hteSSPart1.setGenerateNewline(false);
        _hteSSPart1.setSize(iLength1);
        _hteSSPart1.setMaxLength(iLength1);

        if (_sSeparator != null && !_sSeparator.trim().equals(""))
            _cont.add(_htSeparator1 = new HtmlText(_sSeparator, getPage()));

        _cont.add(_hteSSPart2 = new HtmlTextEdit("SSPart2", p));
        _hteSSPart2.setGenerateNewline(false);
        _hteSSPart2.setSize(iLength2);
        _hteSSPart2.setMaxLength(iLength2);

        if (_sSeparator != null && !_sSeparator.trim().equals(""))
            _cont.add(_htSeparator2 = new HtmlText(_sSeparator, getPage()));

        _cont.add(_hteSSPart3 = new HtmlTextEdit("SSPart3", p));

        _hteSSPart3.setGenerateNewline(false);
        _hteSSPart3.setSize(iLength3);
        _hteSSPart3.setMaxLength(iLength3);

        setEnabled(bEnableFlag);
    }
    /**
     * HtmlSSNComponent constructor comment.
     *
     * @param name			Name of component.
     * @param p				Page containing component.
     * @param bEnableFlag	Flag to set the component to editable (true) or read only (false)
     */
    public HtmlSSNComponent(String name, HtmlPage p, boolean bEnableFlag) {
        this(name, p, "-", bEnableFlag);
    }
    public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        // It is essential to call getValue() here because there may be a ValueChanged
        // event in the queue for this object, which needs to be removed, which getValue()
        // does.  The secondary calls to getValue() from the container will not
        // do this because there are no events associated with them.
        String val = getValue(row, true);

        if (val == null || val.trim().equals("")) {
            _hteSSPart1.setValue(null, row);
            _hteSSPart2.setValue(null, row);
            _hteSSPart3.setValue(null, row);
        } else {
            String sVal;

            if (_dsBuff != null) {
                if (row >= 0)
                    sVal = _dsBuff.getString(row, _dsColNo);
                else
                    sVal = _dsBuff.getString(_dsColNo);
            } else
                sVal = val;

            if (sVal.length() == iLength1 + iLength2 + iLength3) {
                _hteSSPart1.setValue(sVal.substring(0, 3), row);
                _hteSSPart2.setValue(sVal.substring(3, 5), row);
                _hteSSPart3.setValue(sVal.substring(5), row);
            }
        }

        _cont.generateHTML(p, row);

        if (_visible && _enabled)
            p.println("");
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocusPart1() {
        return _hteSSPart1;
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocusPart2() {
        return _hteSSPart2;
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocusPart3() {
        return _hteSSPart3;
    }
    /**
     * This method will return the font type used by this control.See the Constants at the top of the class for valid font types.
     */
    public String getFont() {
        return _font;
    }
    /**
     * This method returns the formatted SSN provided that the component
     * contains a valid value otherwise it returns an empty string
     *
     * Date: 01/10/2001
     *
     * @return java.lang.String
     */
    public String getFormattedValue() {
        String sFormattedSSN = "";

        if (isValid()) {
            sFormattedSSN = _sPart1 + _sSeparator + _sPart2 + _sSeparator + _sPart3;
        }
        return sFormattedSSN;
    }
    /**
     * Gets the separator char.
     * @return java.lang.String
     */
    public java.lang.String getSsnSeparator() {
        return _sSeparator;
    }
    /**
     * This method returns the SSN provided that the component
     * contains a valid value otherwise it returns an empty string
     *
     * Date: 01/08/2001
     *
     * @return java.lang.String
     */
    public String getValue() {
        _sSSN = "";

        if (isValid())
            _sSSN = _sPart1 + _sPart2 + _sPart3;

        return _sSSN;
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
     * Returns whether the SSN entered is valid or not.
     * @return boolean Indicates whether SSN is valid or not.
     */
    public boolean isValid() {
        boolean bNumeric = true;

        boolean bNumeric1 = true;
        boolean bNumeric2 = true;
        boolean bNumeric3 = true;

        String sPart1 = _sPart1;
        String sPart2 = _sPart2;
        String sPart3 = _sPart3;

        _hteSSPart1.setValue(_sPart1);
        _hteSSPart2.setValue(_sPart2);
        _hteSSPart3.setValue(_sPart3);

        // Validate part1 of the SSN - 3 characters
        int iValidLength = iLength1;
        int iSSNLength = sPart1.length();

        if (iSSNLength != iValidLength) {
            _hcFocusComp = getFocusPart1();
            bNumeric1 = false;
        } else {
            for (int i = 0; i < iSSNLength; i++) {
                char c = sPart1.charAt(i);

                if (!Character.isDigit(c)) {
                    _hcFocusComp = getFocusPart1();
                    bNumeric1 = false;
                    break;
                }
            }
        }

        // Validate part2 of the SSN - 2 characters
        iValidLength = iLength2;
        iSSNLength = sPart2.length();

        if (iSSNLength != iValidLength) {
            if (bNumeric1)
                _hcFocusComp = getFocusPart2();
            bNumeric2 = false;
        } else {
            for (int i = 0; i < iSSNLength; i++) {
                char c = sPart2.charAt(i);

                if (!Character.isDigit(c)) {
                    if (bNumeric1)
                        _hcFocusComp = getFocusPart2();
                    bNumeric2 = false;
                    break;
                }
            }
        }

        // Validate part3 of the SSN - 4 characters
        iValidLength = iLength3;
        iSSNLength = sPart3.length();

        if (iSSNLength != iValidLength) {
            if (bNumeric1 && bNumeric2)
                _hcFocusComp = getFocusPart3();
            bNumeric3 = false;
        } else {
            for (int i = 0; i < iSSNLength; i++) {
                char c = sPart3.charAt(i);

                if (!Character.isDigit(c)) {
                    if (bNumeric1 && bNumeric2)
                        _hcFocusComp = getFocusPart3();
                    bNumeric3 = false;
                    break;
                }
            }
        }

        bNumeric = bNumeric1 && bNumeric2 && bNumeric3;

        return bNumeric;
    }
    public boolean processParms(Hashtable htParms, int iRowNo) throws Exception {
        if (!getVisible() || !getEnabled())
            return false;

        // Determine the old value from both edit fields.

        String sOldValue;

        if (_dsBuff == null) {
            sOldValue = _hteSSPart1.getValue() + _hteSSPart2.getValue() + _hteSSPart3.getValue();
        } else {
            String s;
            if (iRowNo > -1)
                s = _dsBuff.getString(iRowNo, _dsColNo);
            else
                s = _dsBuff.getString(_dsColNo);

            sOldValue = (s != null) ? s : null;

            if (s == null)
                sOldValue = null;
            else
                sOldValue = s;
        }

        // Determine the new value from both edit fields.

        String sNewValue;

        String sName1 = _hteSSPart1.getFullName();
        if (iRowNo > -1)
            sName1 += "_" + iRowNo;

        String[] asValues = (String[]) htParms.get(sName1);
        if (asValues != null) {
            _sPart1 = sNewValue = asValues[0].trim();
            if (sNewValue.equals(""))
                sNewValue = null;
        } else
            sNewValue = null;

        String sName2 = _hteSSPart2.getFullName();

        if (iRowNo > -1)
            sName2 += "_" + iRowNo;

        asValues = (String[]) htParms.get(sName2);

        if (asValues != null) {
            String s = _sPart2 = asValues[0].trim();

            if (!s.equals("")) {
                if (sNewValue == null)
                    sNewValue = "";
                sNewValue += s;
            }
        }

        String sName3 = _hteSSPart3.getFullName();

        if (iRowNo > -1)
            sName3 += "_" + iRowNo;

        asValues = (String[]) htParms.get(sName3);

        if (asValues != null) {
            String s = _sPart3 = asValues[0].trim();

            if (!s.equals("")) {
                if (sNewValue == null)
                    sNewValue = "";
                sNewValue += s;
            }
        }

        // Compare old and new values and possibly create a ValueChangedEvent.
        if (!valuesEqual(sOldValue, sNewValue)) {
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), sOldValue, sNewValue, iRowNo, _dsColNo, _dsBuff);
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
     * Specifies the Style Class to be used for the SSN Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
    public void setClassName(String sClass) {

        super.setClassName(sClass);

        if (sClass != null) {
            _hteSSPart1.setClassName(sClass);
            _hteSSPart2.setClassName(sClass);
            _hteSSPart3.setClassName(sClass);
            _htSeparator1.setClassName(sClass);
            _htSeparator2.setClassName(sClass);
        }
    }
/**
* Sets the font end tag for disabled mode.
* @param tag java.lang.String
*/
public void setDisabledFontEndTag(String tag)
{

    _hteSSPart1.setDisabledFontEndTag(tag);
    _hteSSPart2.setDisabledFontEndTag(tag);
    _hteSSPart3.setDisabledFontEndTag(tag);

    _htSeparator1.setFontEndTag(tag);
    _htSeparator2.setFontEndTag(tag);
}
/**
 * Sets the font tag for disabled mode.
 * @param tag java.lang.String
 */
public void setDisabledFontStartTag(String tag)
{

    _hteSSPart1.setDisabledFontStartTag(tag);
    _hteSSPart2.setDisabledFontStartTag(tag);
    _hteSSPart3.setDisabledFontStartTag(tag);

    _htSeparator1.setFontStartTag(tag);
    _htSeparator2.setFontStartTag(tag);
}
    /**
     * Enables or disables the ability of this component to respond to user input.
     * @param editable boolean
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _cont.setEnabled(enabled);
    }
    /**
     * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
     */
    public void setFont(String font) {
        _font = font;

        Props props = getPage().getPageProperties();

        if (_font != null) {
            _fontStartTag = props.getThemeProperty(null, _font + Props.TAG_START);
            _fontEndTag = props.getThemeProperty(null, _font + Props.TAG_END);
        } else {
            _fontStartTag = props.getThemeProperty(null, HtmlText.FONT_DEFAULT + Props.TAG_START);
            _fontEndTag = props.getThemeProperty(null, HtmlText.FONT_DEFAULT + Props.TAG_END);
        }

        _hteSSPart1.setFontStartTag(_fontStartTag);
        _hteSSPart1.setFontEndTag(_fontEndTag);

        _hteSSPart2.setFontStartTag(_fontStartTag);
        _hteSSPart2.setFontEndTag(_fontEndTag);

        _hteSSPart3.setFontStartTag(_fontStartTag);
        _hteSSPart3.setFontEndTag(_fontEndTag);

        if (_htSeparator1 != null) {
            _htSeparator1.setFontStartTag(_fontStartTag);
            _htSeparator1.setFontEndTag(_fontEndTag);
        }
        if (_htSeparator2 != null) {
            _htSeparator2.setFontStartTag(_fontStartTag);
            _htSeparator2.setFontEndTag(_fontEndTag);
        }
    }
    /**
     * Specifies the parent component for this SSN Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
    public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        // This is necessary for the name to be generated correctly, else the leading
        // sequence of parent names will be lost.
        _cont.setParent(parent);
    }
    /**
     * Replaces the separator between the ssn numbers
     * @param newSsnSeparator java.lang.String
     */
    public void setSsnSeparator(java.lang.String newSsnSeparator) {
        _sSeparator = newSsnSeparator;

        // create new text comp
        HtmlText txtNewSeparator1 = new HtmlText(newSsnSeparator, getPage());
        HtmlText txtNewSeparator2 = new HtmlText(newSsnSeparator, getPage());

        // replace old area code end comp with new one
        _cont.replaceComponent(txtNewSeparator1, _htSeparator1);
        _cont.replaceComponent(txtNewSeparator2, _htSeparator2);

        //	save ref of new comp
        _htSeparator1 = txtNewSeparator1;
        _htSeparator2 = txtNewSeparator2;
    }
    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        super.setTheme(theme);

        if (theme != null) {
            _hteSSPart1.setTheme(theme);
            _hteSSPart2.setTheme(theme);
            _hteSSPart3.setTheme(theme);
            _htSeparator1.setTheme(theme);
            _htSeparator2.setTheme(theme);
        }
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
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _hteSSPart1.getAccessKey();
	}

	/**
	 * @returns the read only html attribute
	 */
	public boolean getReadOnly() {
		return _hteSSPart1.getReadOnly();
	}

	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		return _hteSSPart1.getTabIndex();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String val) {
		_hteSSPart1.setAccessKey(val);
	}

	/**
	 * @param sets the read only html attribute
	 */
	public void setReadOnly(boolean val) {
		_hteSSPart1.setReadOnly(val);
		_hteSSPart2.setReadOnly(val);
		_hteSSPart3.setReadOnly(val);
	}
	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		_hteSSPart1.setTabIndex(val);
		_hteSSPart2.setTabIndex(val + 1);
		_hteSSPart3.setTabIndex(val + 2);
	}
}
