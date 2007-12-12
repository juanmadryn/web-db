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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlSubmitButton.java $
//$Author: Dan $
//$Revision: 24 $
//$Modtime: 1/22/04 3:44p $
/////////////////////////

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class generates an html submit button.
 */
public class HtmlSubmitButton extends HtmlComponent {
    String _dispName = "";
    String _onClick = "";

    Vector _listeners;
    private String _fontTagStart = null;
    private String _fontTagEnd = null;
    private int _rowNo = -1;
    private String _theme;
    private String _iFrameUrl = null;
    private HtmlInlineFrame _iFrame = null;
	private String	_dispNameLocaleKey = null;
	private boolean	_updateLocale = false;
    private boolean _enabled=true;
    private String _buttonBgColor=null;
    private String _buttonFontStyle=null;
	private Integer _tabIndex;
	private String _accessKey;

    /**
     * Constructs a new Submit Button.
     * @param name Each component on a page must have a unique name.
     * @param dispName The text to appear on the button.
     * @param p A Props object that will be used to initialize any properties in the object.
     */
    public HtmlSubmitButton(String name, String dispName, HtmlPage p) {
        this(name, dispName, null, p);

    }

    /**
     * Constructs a new Submit Button.
     * @param name Each component on a page must have a unique name.
     * @param dispName The text to appear on the button.
     * @param theme The theme to use for loading properties
     * @param p A Props object that will be used to initialize any properties in the object.
     */
    public HtmlSubmitButton(String name, String dispName, String theme, HtmlPage p) {
        super(name, p);
        _dispName = dispName;
        setTheme(theme);
    }

    /**
     * This method adds a listener the will be notified when this button causes the page to be submitted.
     * @param l The listener to add.
     */
    public void addSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector();

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }

        _listeners.addElement(l);
    }

    /**
     * This method inserts a listener at the beginning of the the listener list. It will be notified first when this button causes the page to be submitted.
     * @param l The listener to add.
     */
    public void insertSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector();

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }

        _listeners.insertElementAt(l,0);
    }
    
    public boolean executeEvent(int type) throws Exception {
        if (type != EVENT_SUBMIT)
            return true;

        if (_listeners == null)
            return true;

        SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);

        for (int i = 0; i < _listeners.size(); i++) {
            SubmitListener l = (SubmitListener) _listeners.elementAt(i);
            e.setNextListener( _listeners.size() > (i + 1) ?_listeners.elementAt(i + 1) : null);
            if (!l.submitPerformed(e))
                return false;
        }

        return true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

		processLocaleInfo();
		
        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;

        String out = "";
        String jScript = null;
        if (_iFrame != null && _iFrameUrl == null) {
            String uName = getPage().getClass().getName();
            int pos = uName.lastIndexOf(".");
            if (pos > -1)
                uName = uName.substring(pos + 1);
            uName += "-" + _iFrame.getName() + "FrameComponent";
            _iFrameUrl = uName;
        }

        String style = null;
        if (_buttonBgColor != null || _buttonFontStyle != null) {
            style = " style=\"";
            if (_buttonBgColor != null)
                style += "background-color:" + _buttonBgColor + ";";
            if (_buttonFontStyle != null)
                style += _buttonFontStyle;
            style += "\"";
        }

		String onClick = _onClick;
		String valScript = HtmlValidatorText.generateOnClickJavaScriptForButtons(_onClick,_listeners,getFullName());
		if (valScript != null) {
			out += valScript;
			onClick = "return " + HtmlValidatorText.generateOnClickJavaScriptFunctionName(getFullName()) + ";";
		}	
			
		
        if (_iFrame == null || ((jScript = getPage().getSubmitJavaScript(this, _iFrameUrl, _iFrame)) == null)) {
            out += "<INPUT TYPE=\"SUBMIT\" name=\"" + name + "\" value=\"" + _dispName + "\"";
            if (onClick != null && !onClick.trim().equals(""))
                out += " onClick=\"" + onClick + "\"";
            if (_class != null && !_class.trim().equals(""))
                out += " class=\"" + _class + "\"";
            if ((! _enabled) && useDisabledAttribute())
                out += " disabled=\"true\"";
			if (_tabIndex != null) 
				out += " tabindex=\"" + _tabIndex + "\"";
			if (_accessKey != null)
				out += " accesskey=\"" + _accessKey + "\"";
            if (style != null)
                out += style;
            out += ">";
        } else {
            out += "<INPUT TYPE=\"BUTTON\" name=\"" + name + "\" value=\"" + _dispName + "\" onClick=\"" + jScript + "\"";
            if (_class != null && !_class.trim().equals(""))
                out += " class=\"" + _class + "\"";
            if ((! _enabled) && useDisabledAttribute())
                out += " disabled=\"true\"";
            if (style != null)
                out += style;
			if (_tabIndex != null) 
				out += " tabindex=\"" + _tabIndex + "\"";
			if (_accessKey != null)
				out += " accesskey=\"" + _accessKey + "\"";
                
            out += ">";
        }

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

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;

        if (parms.get(name) != null) {
            _rowNo = rowNo;
            return true;
        }

        return false;
    }

    /**
     * This method removes a listener from the list that will be notified if this button causes the page to be submitted.
     * @param l The listener to remove.
     */
    public void removeSubmitListener(SubmitListener l) {
        if (_listeners == null)
            return;

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l) {
                _listeners.removeElementAt(i);
                return;
            }
        }
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
     * Use this method if you want the button to submit only a layer of the page and not the whole page
     * @param iFrame The IFrame component that should be loaded
     */
    public void setIFrameSubmit(HtmlInlineFrame iFrame) {
        _iFrame = iFrame;
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
        _buttonBgColor = props.getThemeProperty(theme,Props.BUTTON_BG_COLOR);
        _buttonFontStyle = props.getThemeProperty(theme,Props.BUTTON_FONT_STYLE);
		setClassName(props.getThemeProperty(theme, Props.BUTTON_STYLE_CLASS));
        _theme = theme;
    }
    

	/**
	 * Returns the Locale key used for the text of this component
	 */
	public String getDisplayNameLocaleKey() {
		return _dispNameLocaleKey;
	}


	/**
	 * Returns the Locale key used for text
	 */
	public void setDisplayNameLocaleKey(String textLocaleKey) {
		_dispNameLocaleKey = textLocaleKey;
		_updateLocale = true;
	}

	/**
	 * Updates the Display Name for the current local
	 */
	public void updateLocale() {
		_updateLocale = true;	
	}	
	
	private void processLocaleInfo() {
		if (_updateLocale) {
			_updateLocale = false;
			LanguagePreferences p = getPage().getLanguagePreferences();
			if (_dispNameLocaleKey != null) {
				String newDisplayName = LanguageResourceFinder.getResource(getPage().getApplicationName(),_dispNameLocaleKey,p);
				if (newDisplayName != null)
					setDisplayName(newDisplayName);
			}		
		}		
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

}
