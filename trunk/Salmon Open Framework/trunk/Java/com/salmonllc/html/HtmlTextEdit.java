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

import com.salmonllc.html.events.ValueChangedEvent;

import com.salmonllc.properties.Props;

import com.salmonllc.util.Util;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTextEdit.java $
//$Author: Srufle $
//$Revision: 29 $
//$Modtime: 8/06/04 3:11p $
/////////////////////////
import java.util.Hashtable;


/**
 * This class is used for text input field.
 */
public class HtmlTextEdit extends HtmlFormComponent
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1428892875398469720L;
	private Boolean _readOnly;
    private Integer _tabIndex;
    private String  _accessKey;
    private String  _fontTagEnd;
    private String  _fontTagStart;
    private String _onChange;
    private String _onFocus;
    private String _onKeyUp;
    private String _onLoseFocus;
    private String _style;
    private int    _maxLength = 10;
    private int    _size      = 10;

    /**
     * Constructs a new HTMLTextEdit component.
     *
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlTextEdit(String name, com.salmonllc.html.HtmlPage p)
    {
        this(name, null, p);
    }

    /**
     * Constructs a new HTMLTextEdit component.
     *
     * @param name The name of the component
     * @param theme The theme to use for loading properties
     * @param p The page the component will be placed in.
     */
    public HtmlTextEdit(String name, String theme, com.salmonllc.html.HtmlPage p)
    {
        super(name, p);
        setTheme(theme);
    }

    /**
     * DOCUMENT ME!
     *
     * @param string the access key html attribute
     */
    public void setAccessKey(String string)
    {
        _accessKey = string;
    }

    /**
     * DOCUMENT ME!
     *
     * @return the access key html attribute
     */
    public String getAccessKey()
    {
        return _accessKey;
    }

    /**
     * This method sets the end font tag for the component.
     *
     * @param value DOCUMENT ME!
     */
    public void setFontEndTag(String value)
    {
        _fontTagEnd = value;
    }

    /**
     * This method gets the end font tag for the component.
     *
     * @return DOCUMENT ME!
     */
    public String getFontEndTag()
    {
        return _fontTagEnd;
    }

    /**
     * This method sets the start font tag for the component.
     *
     * @param value DOCUMENT ME!
     */
    public void setFontStartTag(String value)
    {
        _fontTagStart = value;
    }

    /**
     * This method gets the start font tag for the component.
     *
     * @return DOCUMENT ME!
     */
    public String getFontStartTag()
    {
        return _fontTagStart;
    }

    /**
     * This method sets the maximum length for the text in the component.
     *
     * @param maxLength DOCUMENT ME!
     */
    public void setMaxLength(int maxLength)
    {
        _maxLength = maxLength;
    }

    /**
     * This method gets the maximum length for the text in the component.
     *
     * @return DOCUMENT ME!
     */
    public int getMaxLength()
    {
        return _maxLength;
    }

    /**
     * This method sets the javascript to be executed when the value of the text in the component changes.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnChange(String value)
    {
        _onChange = value;
    }

    /**
     * This method gets the javascript to be executed when the value of the text in the component changes.
     *
     * @return DOCUMENT ME!
     */
    public String getOnChange()
    {
        return _onChange;
    }

    /**
     * This method sets the javascript to be executed when the component gains focus.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnFocus(String value)
    {
        _onFocus = value;
    }

    /**
     * This method gets the javascript to be executed when the component gets focus.
     *
     * @return DOCUMENT ME!
     */
    public String getOnFocus()
    {
        return _onFocus;
    }

    /**
     * This method sets the javascript to be executed when a keyboard key is release.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnKeyUp(String value)
    {
        _onKeyUp = value;
    }

    /**
     * This method sets the javascript to be executed when the component loses focus.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnLoseFocus(String value)
    {
        _onLoseFocus = value;
    }

    /**
     * This method gets the javascript to be executed when the component loses focus.
     *
     * @return DOCUMENT ME!
     */
    public String getOnLoseFocus()
    {
        return _onLoseFocus;
    }

    /**
     * DOCUMENT ME!
     *
     * @param val the read only html attribute
     */
    public void setReadOnly(boolean val)
    {
        if (val == false)
        {
            _readOnly = null;
        }
        else
        {
            _readOnly = Boolean.TRUE;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return the read only html attribute
     */
    public boolean getReadOnly()
    {
        if (_readOnly == null)
        {
            return false;
        }

        return _readOnly.booleanValue();
    }

    /**
     * This method sets the display size for the component in characters.
     *
     * @param size DOCUMENT ME!
     */
    public void setSize(int size)
    {
        _size = size;
    }

    /**
     * This method gets the display size for the component in characters.
     *
     * @return DOCUMENT ME!
     */
    public int getSize()
    {
        return _size;
    }

    /**
     * DOCUMENT ME!
     *
     * @param string
     */
    public void setStyle(String string)
    {
        _style = string;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public String getStyle()
    {
        return _style;
    }

    /**
     * DOCUMENT ME!
     *
     * @param val the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
     */
    public void setTabIndex(int val)
    {
        if (val == -1)
        {
            _tabIndex = null;
        }
        else
        {
            _tabIndex = new Integer(val);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return the tab index html attribute
     */
    public int getTabIndex()
    {
        if (_tabIndex == null)
        {
            return -1;
        }

        return _tabIndex.intValue();
    }

    /**
     * This method sets the property theme for the component.
     *
     * @param theme The theme to use.
     */
    public void setTheme(String theme)
    {
        super.setTheme(theme);

        Props props = getPage().getPageProperties();
        _fontTagStart = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_START);
        _fontTagEnd   = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_END);
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo)
                      throws Exception
    {
        if (!_visible)
        {
            return;
        }

        processLocaleInfo();

        String name = getFullName();

        if (rowNo > -1)
        {
            name += ("_" + rowNo);
        }

        String tag = "<INPUT TYPE=\"TEXT\" NAME=\"" + name + "\" ID=\"" + getParent().getName()+getName()+"_" + rowNo + "\"";        

        if (_onKeyUp != null)
        {
            tag += (" onKeyUp=\"" + _onKeyUp + "\"");
        }

        if (!getEnabled())
        {
            if (useDisabledAttribute())
            {
                tag += " DISABLED=\"true\"";
            }
            else
            {
                String out = getValue(rowNo, true);

                if (out != null)
                {
                    out = fixSpecialHTMLCharacters(out);
                }
                else
                {
                    out = "";
                }

                // Code added in an attempt to remove unsightly '&nbsp;' in the HtmlTelephonecomponent and
                // HtmlSSNComponent.
                // The premise is that the '&nbsp;' was introduced to provide for the case when the field
                // is disabled and when the string is empty or null
                // The IF statement is introduced to do as much without impacting non-null and non-empty strings
                if ((out == null) || ((out != null) && (out.equals(""))))
                {
                    out += "&nbsp;";
                }

                if (_disabledFontStartTag != null)
                {
                    p.print(_disabledFontStartTag + out + _disabledFontEndTag);
                }
                else
                {
                    p.print(out);
                }

                return;
            }
        }

        if (_maxLength > -1)
        {
            tag += (" MAXLENGTH=\"" + _maxLength + "\"");
        }

        if (_size > -1)
        {
            int size = _size;

            if ((getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() == 4))
            {
                size = (int) (size * .60);
            }

            if (size < 1)
            {
                size = 1;
            }

            tag += (" SIZE=\"" + size + "\"");
        }

        String value = getValue(rowNo, true);

        if (value != null)
        {
            tag += (" VALUE=\"" + fixSpecialHTMLCharacters(value) + "\"");
        }

        if (_onChange != null)
        {
            tag += (" onChange=\"" + _onChange + "\"");
        }

        if (_onFocus != null)
        {
            tag += (" onFocus=\"" + _onFocus + "\"");
        }

        if (_onLoseFocus != null)
        {
            tag += (" onBlur=\"" + _onLoseFocus + "\"");
        }

        if (_class != null)
        {
            tag += (" class=\"" + _class + "\"");
        }

        if (_tabIndex != null)
        {
            tag += (" tabindex=\"" + _tabIndex + "\"");
        }

        if (Util.isFilled(getStyle()))
        {
            tag += (" style=\"" + getStyle() + "\"");
        }

        if (_accessKey != null)
        {
            tag += (" accesskey=\"" + _accessKey + "\"");
        }

        if (_readOnly != null)
        {
            tag += (" readonly=\"" + _readOnly + "\"");
        }

        tag += ">";

        if (_fontTagStart != null)
        {
            //tag = _fontTagStart + tag + _fontTagEnd;
        }

        if (_generateNewline)
        {
            p.println(tag);
        }
        else
        {
            p.print(tag);
        }

        writeFocusScript(p, rowNo);
    }

    public boolean processParms(Hashtable<String, Object>parms, int rowNo)
                         throws Exception
    {
        // fc: 07/17/02 Commented out the below lines as they are no longer required,
        //        since a better approach is to check to see if the field is contained in
        //        the form when submitted. see other change below
        //        if (!getVisible() || !getEnabled())
        //            return false;
        String oldValue = _value;

        String name = getFullName();

        if (rowNo > -1)
        {
            name += ("_" + rowNo);

            if (_dsBuff != null)
            {
                oldValue = _dsBuff.getFormattedString(rowNo, _dsColNo);
            }
        }
        else
        {
            if (_dsBuff != null)
            {
                oldValue = _dsBuff.getFormattedString(_dsColNo);
            }
        }

        //fc: 07/17/02 Added check to see if component exists in form if not
        //             then do not process.
        if (parms.containsKey(name))
        {
            String val[] = (String[]) parms.get(name);

            if (val != null)
            {
                _value = val[0].trim();

                if (_value.equals(""))
                {
                    _value = null;
                }
            }
            else
            {
                _value = null;
            }

            convertValue();

            if (!valuesEqual(oldValue, _value))
            {
                ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), oldValue, _value, rowNo, _dsColNo, _dsBuff);
                addEvent(e);
            }
        }

        return false;
    }
}
