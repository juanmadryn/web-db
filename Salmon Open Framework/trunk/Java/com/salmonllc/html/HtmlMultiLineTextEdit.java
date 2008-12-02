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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlMultiLineTextEdit.java $
//$Author: Dan $
//$Revision: 31 $
//$Modtime: 7/30/04 10:41a $
/////////////////////////

import java.util.Hashtable;
import java.util.StringTokenizer;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import com.salmonllc.util.Util;

/**
 * This class is used for multi line text input in a page.
 */
public class HtmlMultiLineTextEdit extends HtmlFormComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2236760701108046410L;
	public static final int WRAP_OFF = 0;
    public static final int WRAP_HARD = 1;
    public static final int WRAP_SOFT = 2;

    private String _fontTagStart;
    private String _fontTagEnd;
    private int _maxLength = -1;
    private int _cols = 10;
    private int _rows = 3;
    private String _onChange;
    private String _onFocus;
    private String _onLoseFocus;
    private int _wrap = WRAP_SOFT;
    private boolean _readOnly=false;
	private Integer _tabIndex;
	private String _accessKey;
    private String _style;   

    /**
     * Constructs a new HTMLMultiLineTextEdit component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlMultiLineTextEdit(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);

    }

    /**
     * Constructs a new HTMLMultiLineTextEdit component.
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page the component will be placed in.
     */
    public HtmlMultiLineTextEdit(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, theme, p);

    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;

        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;

        String tag = "<TEXTAREA NAME=\"" + name + "\"";

        if (_readOnly) {
            tag += " readonly=\"true\"";
        }

        if (!getEnabled()) {
            if (useDisabledAttribute())
                tag += " disabled=\"true\"";
            else {

                if (_disabledFontStartTag != null)
                    p.print(_disabledFontStartTag);
                String out = getValue(rowNo, true);
                if (out != null) {
                    String buf = "";
                    StringTokenizer st = new StringTokenizer(out, " \t\n\r", true);
                    int n = 0;
                    String linebreak = "";

                    //--------------------------------------------------------------------
                    // Added by BYD on 01/24/02 to properly handle page breaks in Netscape
                    int iNbrCols = _cols;
                    if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
                        iNbrCols = (int) (iNbrCols * .70);
                    //--------------------------------------------------------------------
                    while (st.hasMoreTokens()) {
                        String next = st.nextToken();
                        if (next.equals("\r"))
                        // We need to detect the \r\n sequence and this is a cheap way
                        // to do it.  Strictly speaking, there could be isolated \r
                        // characters but the possibility is remote.
                        // We swallow the \r to avoid double line breaks.
                            continue;
                        else if (next.equals("\n")) {
                            p.print(linebreak);
                            linebreak = "<BR>";
                            p.print(fixSpecialHTMLCharacters(buf));
                            next = "";	// Else we get double line breaks
                            buf = "";
                            n = 0;
                        }
                        //----------------------------------------------------------------------
                        // changed by BYD on 01/24/02 to properly handle page breaks in Netscape
//				else if ((n + next.length()) > _cols)
                        //----------------------------------------------------------------------
                        else if ((n + next.length()) > iNbrCols) {
                            p.print(linebreak);
                            linebreak = "<BR>";
                            p.print(fixSpecialHTMLCharacters(buf));
                            buf = "";
                            n = 0;
                        }
                        buf += next;
                        n += next.length();
                    }
                    if (buf.length() > 0) {
                        p.print(linebreak);
                        p.print(fixSpecialHTMLCharacters(buf));
                    }
                }
                p.print("&nbsp;");
                if (_disabledFontStartTag != null)
                    p.print(_disabledFontEndTag);
                return;
            }
        }
            
		if (_tabIndex != null) 
			tag += " tabindex=\"" + _tabIndex + "\"";

		if (_accessKey != null)
			tag += " accesskey=\"" + _accessKey + "\"";
		
		String lengthFunctions=null;	
		if (_maxLength > -1) {
			
			tag += " MAXLENGTH=\"" + _maxLength + "\"";
			 tag += " onKeyDown=\"javascript:" + _fullName + "textCounter(" + _fullName + "," + _maxLength + ", event)\"";
			 tag += " onKeyUp=\"javascript:" + _fullName + "textCounter(" + _fullName + "," + _maxLength + ", event)\"";
			 tag += " onpaste=\"javascript:" + _fullName + "textPaste(" + _fullName + "," + _maxLength + ", event)\"";
			 lengthFunctions = " <SCRIPT language=\"javascript\">" +
			 		 "\nfunction " + _fullName + "textCounter(field,maxlimit, event) {\n" +
					 " if ((event.keyCode != 8 && event.keyCode != 46 && \n" +
					 "	event.keyCode != 37 && event.keyCode != 38 \n" +
					 "	&& event.keyCode != 39 && event.keyCode != 40) \n" +
					 "	&& field.value.length >= maxlimit ) \n" +
					 "       { \n" +
					 "         event.returnValue=false; \n" +
					 "       } \n" +
				 "}\n" +
				 "function " + _fullName + "textPaste(field,maxlimit, event)\n" +
				 "{\n" +
				 "	allowed = maxlimit - field.value.length;\n" +
				 "	window.clipboardData.setData(\"Text\", window.clipboardData.getData(\"Text\").substring(0, allowed));\n" +
				 "	event.returnValue = true;\n" +
				 "}\n" +
				 "</SCRIPT>";
		 }

        if (_cols > -1) {
            int size = _cols;
            if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
                size = (int) (size * .70);
            tag += " COLS=\"" + size + "\"";
        }

        if (_rows > -1)
            tag += " rows=\"" + _rows + "\"";

        if (_onChange != null)
            tag += " onChange=\"" + _onChange + "\"";

        if (_onFocus != null)
            tag += " onFocus=\"" + _onFocus + "\"";

        if (_onLoseFocus != null)
            tag += " onBlur=\"" + _onLoseFocus + "\"";

        if (_wrap == WRAP_OFF)
            tag += " WRAP=\"OFF\"";
        else if (_wrap == WRAP_HARD)
            tag += " WRAP=\"HARD\"";
        else
            tag += " WRAP=\"SOFT\"";

		if (_class != null)
			tag += " class=\"" + _class + "\"";
			
		if (Util.isFilled(_style ))
			tag += " style=\"" + _style + "\"";
			
        tag += ">";

        String value = fixSpecialHTMLCharacters(getValue(rowNo, true));

        if (value != null)
            tag += value;

        tag += "</TEXTAREA>";

        if (_fontTagStart != null)
            tag = _fontTagStart + tag + _fontTagEnd;

        p.println(tag);
        if (lengthFunctions != null)
        	p.println(lengthFunctions);
        	
        writeFocusScript(p, rowNo);
    }

    /**
     * This method gets the number of columns for the component in characters.
     */
    public int getColumns() {
        return _cols;
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
     * This method gets the readonly attribute of the component.
     */
    public boolean getReadOnly() {
        return _readOnly;
    }

    /**
     * This method gets the javascript to be executed when the value of the text in the component changes.
     */
    public String getOnChange() {
        return _onChange;
    }

    /**
     * This method gets the javascript to be executed when the component gets focus.
     */
    public String getOnFocus() {
        return _onFocus;
    }

    /**
     * This method gets the javascript to be executed when the component loses focus.
     */
    public String getOnLoseFocus() {
        return _onLoseFocus;
    }

    /**
     * This method gets the number of rows for the component.
     */
    public int getRows() {
        return _rows;
    }

    /**
     * This method gets the word wrap property for the component (WRAP_OFF,WRAP_SOFT,WRAP_HARD).
     */
    public int getWrap() {
        return _wrap;
    }

    public boolean processParms(Hashtable<String, Object>parms, int rowNo) throws Exception {
        if (!getVisible() || !getEnabled())
            return false;

        Object oldValue = _value;

        String name = getFullName();
        if (rowNo > -1) {
            name += "_" + rowNo;
            if (_dsBuff != null) {
                try {
                    oldValue = _dsBuff.getFormattedString(rowNo, _dsColNo);
                } catch (Exception e) {
                    oldValue = null;
                }
            }
        } else {
            if (_dsBuff != null)
                try {
                    oldValue = _dsBuff.getFormattedString(_dsColNo);
                } catch (Exception e) {
                    oldValue = null;
                }
        }

        String val[] = (String[]) parms.get(name);

        if (val != null) {
            _value = val[0].trim();
            if (_value.equals(""))
                _value = null;
        } else
            _value = null;
        convertValue();

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
     * This method sets the number of columns for the component in characters.
     */
    public void setColumns(int size) {
        _cols = size;
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
     * This method sets the readonly attribute of the component.
     */
    public void setReadOnly(boolean readonly) {
        _readOnly = readonly;
    }


    /**
     * This method sets the javascript to be executed when the value of the text in the component changes.
     */
    public void setOnChange(String value) {
        _onChange = value;
    }

    /**
     * This method sets the javascript to be executed when the component gains focus.
     */
    public void setOnFocus(String value) {
        _onFocus = value;
    }    
    
    /**
     * This method sets the javascript to be executed when the component loses focus.
     */
    public void setOnLoseFocus(String value) {
        _onLoseFocus = value;
    }

    /**
     * This method sets the number of rows for the component.
     */
    public void setRows(int rows) {
        _rows = rows;
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
     * This method sets the word wrap property for the component (WRAP_OFF,WRAP_SOFT,WRAP_HARD)
     */
    public void setWrap(int wrap) {
        _wrap = wrap;
    }

    /**
     * This method sets the word wrap property for the component using the HTML values for the attribute
     */
    public void setWrap(String wrap) {
        int iWrap = WRAP_OFF;
        if (wrap != null) {
            wrap = wrap.toUpperCase();
            iWrap = wrap.equals("HARD") ? WRAP_HARD : (wrap.equals("SOFT") ? WRAP_SOFT : WRAP_OFF);
        }
        setWrap(iWrap);
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
 * @return
 */
public String getStyle()
{
	return _style;
}

/**
 * @param string
 */
public void setStyle(String string)
{
	_style = string;
}

//Juan Manuel Cortez - 01/12/2008 - Added for highlight on focus behavior
/**
 * This method adds javascript code to be executed when the component gains focus.
 *
 * @param value DOCUMENT ME!
 */
public void addOnFocus(String value)
{
   if(_onFocus == null) 
  	 _onFocus = value;
   else
  	 _onFocus += value;
}

//Juan Manuel Cortez - 01/12/2008 - Added for highlight on focus behavior
/**
 * This method adds the javascript to be executed when the component loses focus.
 *
 * @param value DOCUMENT ME!
 */
public void addOnLoseFocus(String value)
{
	 if(_onLoseFocus == null)
		 _onLoseFocus = value;
	 else
		 _onLoseFocus += value;
}
}
