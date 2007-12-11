package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlZipCodeComponent.java $
//$Author: Dan $
//$Revision: 11 $
//$Modtime: 10/20/03 4:14p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import java.util.Hashtable;
/**
 * This class creates an edit for a zip code
 */
public class HtmlZipCodeComponent extends HtmlFormComponent
{
    protected HtmlTextEdit _editZip;
    private boolean _extendedZip = false;
    private String _zip;
    private String _zip5;
    private String _zip4;

    /**
     * @param name		Name of component.
     * @param p			Page containing component.
     */
    public HtmlZipCodeComponent(String name, HtmlPage p) {
        this(name, p, false);

    }
    /** @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     * @param extendedZip boolean
     */
    public HtmlZipCodeComponent(String name, HtmlPage p, boolean extendedZip) {
        super(name, p);
        _extendedZip = extendedZip;
        _editZip = new HtmlTextEdit(name, p);
        _editZip.setGenerateNewline(false);
        _editZip.setSize(_extendedZip ? 10 : 5);
        _editZip.setMaxLength(_extendedZip ? 10 : 5);
    }
    public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        // It is essential to call getValue() here because there may be a ValueChanged
        // event in the queue for this object, which needs to be removed, which getValue()
        // does.  The secondary calls to getValue() from the container will not
        // do this because there are no events associated with them.
        String val = getValue(row, true);
        if (val == null) {
            _editZip.setValue(null, row);
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
            _editZip.setValue(sVal, row);
        }
        _editZip.generateHTML(p, row);
        if (_visible && _enabled)
            p.println("");
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocus() {
        return _editZip;
    }
    public boolean isNumeric(String s, int length) {
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
    /**
     * Returns true if the zip code is valid
     */
    public boolean isValid() {
        _editZip.setValue(_zip);
        int length = _zip.length();
        if (length == 5) {
            _zip5 = _zip.substring(0, 5);
            _zip4 = "";
            return isNumeric(_zip5, 5);
        } else if (length == 9) {
            _zip5 = _zip.substring(0, 5);
            _zip4 = _zip.substring(5);
            return isNumeric(_zip5, 5) && isNumeric(_zip4, 4);
        } else if (length == 10 && _zip.indexOf('-') == 5) {
            _zip5 = _zip.substring(0, 5);
            _zip4 = _zip.substring(6);
            return isNumeric(_zip5, 5) && isNumeric(_zip4, 4);
        } else {
            return false;
        }
    }
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {

        if (!getVisible() || !getEnabled())
            return false;

        String oldValue;
        if (_dsBuff == null) {
            oldValue = _editZip.getValue();
        } else {
            String s;
            if (rowNo > -1)
                s = _dsBuff.getString(rowNo, _dsColNo);
            else
                s = _dsBuff.getString(_dsColNo);
            if (s == null)
                oldValue = null;
            else
                oldValue = s;
        }

        String newValue;
        String name1 = _editZip.getFullName();
        if (rowNo > -1)
            name1 += "_" + rowNo;
        String val[] = (String[]) parms.get(name1);
        if (val != null) {
            _zip = newValue = val[0].trim();
            if (newValue.equals(""))
                newValue = null;
        } else {
            newValue = null;
        }

        // Compare old and new values and possibly create a ValueChangedEvent.
        if (!valuesEqual(oldValue, newValue)) {
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), oldValue, newValue, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }
        return false;
    }
    public void reset() {
        super.reset();
        _editZip.reset();
    }
    public void setClassName(String sClass) {
        super.setClassName(sClass);
        if (_editZip != null)
            _editZip.setClassName(sClass);

    }
    /**
    * Sets the font end tag for disabled mode.
    * @param tag java.lang.String
    */
    public void setDisabledFontEndTag(String tag)
    {
        _editZip.setDisabledFontEndTag(tag);
    }
    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag)
    {
        _editZip.setDisabledFontStartTag(tag);
    }
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _editZip.setEnabled(enabled);
    }
    public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        // This is necessary for the name to be generated correctly, else the leading
        // sequence of parent names will be lost.
        _editZip.setParent(parent);

    }
    public void setTheme(String sTheme) {
        if (sTheme == null)
            return;

        if (_editZip != null)
            _editZip.setTheme(sTheme);

    }
    
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _editZip.getAccessKey();
	}

	/**
	 * @returns the read only html attribute
	 */
	public boolean getReadOnly() {
		return _editZip.getReadOnly();
	}

	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		return _editZip.getTabIndex();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String val) {
		_editZip.setAccessKey(val);

	}

	/**
	 * @param sets the read only html attribute
	 */
	public void setReadOnly(boolean val) {
		_editZip.setReadOnly(val);
	}	
	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		_editZip.setTabIndex(val);
	}
}
