package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlPasswordEdit.java $
//$Author: Dan $
//$Revision: 22 $
//$Modtime: 10/28/03 1:22p $
/////////////////////////

import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;


/**
 * This class is used for password style text input in a page.
 */
public class HtmlPasswordEdit extends HtmlFormComponent {
    private String _fontTagStart;
    private String _fontTagEnd;
    private int _maxLength = 20;
    private int _size = 10;
	private Boolean _readOnly;
	private Integer _tabIndex;
	private String _accessKey;

    /**
     * Constructs a new HTMLPasswordEdit component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlPasswordEdit(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);
    }

    /**
     * Constructs a new HTMLPasswordEdit component.
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page the component will be placed in.
     */
    public HtmlPasswordEdit(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, theme, p);
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;

        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;

        String tag = "<INPUT TYPE=\"PASSWORD\" NAME=\"" + name + "\"";

        if (!getEnabled()) {
            if (useDisabledAttribute())
                tag += " DISABLED=\"true\"";
            else {
                String value = getValue(rowNo, true);
                String out = "";
                if (value == null)
                    value = "";
                int n = value.length();
                while (n-- > 0)
                    out += "*";
                n = _size - value.length();
                while (n-- > 0)
                    out += "&nbsp;";
                if ((value.length() == 0) && (_size <= 0))
                    out = "&nbsp;";
                if (_disabledFontStartTag != null)
                    p.print(_disabledFontStartTag + out + _disabledFontEndTag);
                else
                    p.print(out);
                return;
            }
        }

        if (_maxLength > -1)
            tag += " MAXLENGTH=\"" + _maxLength + "\"";

        if (_size > -1) {
            int size = _size;
            if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE && getPage().getBrowserVersion() == 4)
                size = (int) (size * .60);
            tag += " SIZE=\"" + size + "\"";
        }

        String value = getValue(rowNo, true);

        if (value != null)
            tag += " VALUE=\"" + value + "\"";

        if (_class != null)
            tag += " class=\"" + _class + "\"";

		if (_tabIndex != null) 
			tag += " tabindex=\"" + _tabIndex + "\"";

		if (_accessKey != null)
			tag += " accesskey=\"" + _accessKey + "\"";
			
		if (_readOnly != null)
			tag += " readonly=\"" + _readOnly + "\"";
			
        tag += ">";

        if (_fontTagStart != null)
            tag = _fontTagStart + tag + _fontTagEnd;

        p.println(tag);
        writeFocusScript(p, rowNo);
    }

    /**
     * This method gets the end font tag for the component.
     */
    public String getFontEndTag() {
        return _fontTagEnd;
    }

    /**
     * This method gets the start font tag for the component.
     */
    public String getFontStartTag() {
        return _fontTagStart;
    }

    /**
     * This method gets the maximum length for the text in the component.
     */
    public int getMaxLength() {
        return _maxLength;
    }

    /**
     * This method gets the display size for the component in characters.
     */
    public int getSize() {
        return _size;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        if (!getEnabled())
            return false;
        Object oldValue = _value;

        String name = getFullName();
        if (rowNo > -1) {
            name += "_" + rowNo;
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(rowNo, _dsColNo);
        } else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(_dsColNo);
        }

        String val[] = (String[]) parms.get(name);

        if (val != null) {
            _value = val[0].trim();
            if (_value.equals(""))
                _value = null;
        } else
            _value = null;

        if (!valuesEqual(oldValue, _value)) {
            String s = null;
            if (oldValue != null)
                s = oldValue.toString();
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }

        return false;

    }

    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontTagEnd = value;
    }

    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontTagStart = value;
    }

    /**
     * This method sets the maximum length for the text in the component.
     */
    public void setMaxLength(int maxLength) {
        _maxLength = maxLength;
    }

    /**
     * This method sets the display size for the component in characters.
     */
    public void setSize(int size) {
        _size = size;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        super.setTheme(theme);
        Props props = getPage().getPageProperties();
        _fontTagStart = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_START);
        _fontTagEnd = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_END);

    }
    
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
	}

	/**
	 * @returns the read only html attribute
	 */
	public boolean getReadOnly() {
		if (_readOnly == null)
			return false;
		return _readOnly.booleanValue();
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
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String string) {
		_accessKey = string;
	}

	/**
	 * @param sets the read only html attribute
	 */
	public void setReadOnly(boolean val) {
		if (val == false)
			_readOnly=null;
		else	
			_readOnly = Boolean.TRUE;
	}

	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else	
			_tabIndex = new Integer(val);
	}

}
