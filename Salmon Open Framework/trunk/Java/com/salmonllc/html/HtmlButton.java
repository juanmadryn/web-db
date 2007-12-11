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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlButton.java $
//$Author: Dan $
//$Revision: 17 $
//$Modtime: 9/30/04 5:50p $
/////////////////////////


import com.salmonllc.properties.*;


/**
 * This class will generate an html button. The button will execute some javascript when clicked.
 */
public class HtmlButton extends HtmlComponent {
    String _dispName = "";
    String _onClick = "";
    private String _fontTagStart = null;
    private String _fontTagEnd = null;
    private String _theme;
    private boolean _enabled = true;
    private String _buttonBgColor = null;
    private String _buttonFontStyle = null;
	private Integer _tabIndex;
	private String _accessKey;
	private String _onDoubleClick=null;
	private String _onMouseDown=null;
	

	/**
	 * @return Returns the onDoubleClick.
	 */
	public String getOnDoubleClick() {
		return _onDoubleClick;
	}
	/**
	 * @param onDoubleClick The onDoubleClick to set.
	 */
	public void setOnDoubleClick(String onDoubleClick) {
		_onDoubleClick = onDoubleClick;
	}
    /**
     * Constructs a new Button.
     * @param name Each component on a page must have a unique name.
     * @param dispName The text to appear on the button.
     * @param onClick The javascript to execute when the button is clicked.
     * @param p A Props object that will be used to initialize any properties in the object.
     */
    public HtmlButton(String name, String dispName, String onClick, HtmlPage p) {
        this(name, dispName, onClick, null, p);
    }

    /**
     * Constructs a new Button.
     * @param name Each component on a page must have a unique name.
     * @param dispName The text to appear on the button.
     * @param onClick The javascript to execute when the button is clicked.
     * @param theme The theme to use for loading properties.
     * @param p A Props object that will be used to initialize any properties in the object.
     */
    public HtmlButton(String name, String dispName, String onClick, String theme, HtmlPage p) {
        super(name, p);
        _dispName = dispName;
        _onClick = onClick;
        setTheme(theme);
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

        String style = null;
        if (_buttonBgColor != null || _buttonFontStyle != null) {
            style = " style=\"";
            if (_buttonBgColor != null)
                style += "background-color:" + _buttonBgColor + ";";
            if (_buttonFontStyle != null)
                style += _buttonFontStyle;
            style += "\"";
        }
        String out = "<INPUT TYPE=\"BUTTON\" name=\"" + getFullName() + "\" value=\"" + _dispName + "\"" ;
        if (_onClick != null)
        	out+=" onClick=\"" + _onClick + "\"";
		if (_class != null && !_class.trim().equals(""))
            out += " class=\"" + _class + "\"";
        if ((!_enabled) && useDisabledAttribute())
            out += " disabled=\"" + true + "\"";
		if (_tabIndex != null) 
			out += " tabindex=\"" + _tabIndex + "\"";
		if (_accessKey != null)
			out += " accesskey=\"" + _accessKey + "\"";    
        if (style != null)
            out += style;
        if (_onDoubleClick != null)
        	out += " ondblclick=\"" + _onDoubleClick + "\"";
        if (_onMouseDown != null)
        	out += " onMouseDown=\"" + _onMouseDown + "\"";
        out += ">";

        if (_fontTagStart != null)
            out = _fontTagStart + out + _fontTagEnd;

        p.println(out);
    }

    /**
     * This method returns the text that will be displayed on the button in the browser.
     * @return java.lang.String
     */
    public String getDisplayName() {
        return _dispName;
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
     * This method gets the javascript that will be executed when the button is clicked.
     */
    public String getOnClick() {
        return _onClick;
    }

    /**
     * This method returns the property theme for the component.
     */
    public String getTheme() {
        return _theme;
    }

    /**
     * This method sets the text that will appear on the button in the browser.
     */
    public void setDisplayName(String name) {
        _dispName = name;
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
     * This method sets the javascript that will be executed when the button is clicked.
     */
    public void setOnClick(String onClick) {
        _onClick = onClick;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {
        Props props = getPage().getPageProperties();
        _fontTagStart = props.getThemeProperty(theme, Props.FONT_BUTTON + Props.TAG_START);
        _fontTagEnd = props.getThemeProperty(theme, Props.FONT_BUTTON + Props.TAG_END);
        _buttonBgColor = props.getThemeProperty(theme, Props.BUTTON_BG_COLOR);
        _buttonFontStyle = props.getThemeProperty(theme, Props.BUTTON_FONT_STYLE);
		_class = props.getThemeProperty(theme, Props.BUTTON_STYLE_CLASS);
        _theme = theme;
    }

    public boolean getEnabled() {
        return _enabled;
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
    }

    /**
     * Returns the background color for the button
     */
    public String getButtonBgColor() {
        return _buttonBgColor;
    }

    /**
     * Sets the background color for the button
     */
    public void setButtonBgColor(String buttonBgColor) {
        _buttonBgColor = buttonBgColor;
    }

    /**
     * Gets the foreground font style for a button
     */
    public String getButtonFontStyle() {
        return _buttonFontStyle;
    }

    /**
     * Sets the foreground font style for a button
     */

    public void setButtonFontStyle(String buttonFontStyle) {
        _buttonFontStyle = buttonFontStyle;
    }
    
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
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
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else	
			_tabIndex = new Integer(val);
	}

	/**
	 * @return Returns the onMouseDown.
	 */
	public String getOnMouseDown() {
		return _onMouseDown;
	}
	/**
	 * @param onMouseDown The onMouseDown to set.
	 */
	public void setOnMouseDown(String onMouseDown) {
		_onMouseDown = onMouseDown;
	}
}
