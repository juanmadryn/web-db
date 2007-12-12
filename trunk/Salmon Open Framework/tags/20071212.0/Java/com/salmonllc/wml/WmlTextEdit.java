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
package com.salmonllc.wml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlTextEdit.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 6/11/03 4:30p $
/////////////////////////

import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;

/**
 * This class is used for text input field.
 */
public class WmlTextEdit extends WmlFormComponent {
    private int _maxLength = 0;
    private int _size = 10;
    private String _format;
    private boolean _emptyok=false;
    private int _tabindex=-1;
    private String _title;

    /**
     * Constructs a new WmlTextEdit component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public WmlTextEdit(String name, com.salmonllc.html.HtmlPage p) {
        super(name, p);
    }


    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;

        if (!getEnabled()) {
            String out = getValue(rowNo, true);
            if (out != null) 
                out = fixSpecialHTMLCharacters(out);
            else
                out = "";

            // Code added in an attempt to remove unsightly '&nbsp;' in the HtmlTelephonecomponent and
            // HtmlSSNComponent.
            // The premise is that the '&nbsp;' was introduced to provide for the case when the field
            // is disabled and when the string is empty or null
            // The IF statement is introduced to do as much without impacting non-null and non-empty strings
            if ((out == null) || ((out != null) && (out.equals(""))))
                out += "&nbsp;";

            if (_disabledFontStartTag != null)
                p.print(_disabledFontStartTag + out + _disabledFontEndTag);
            else
                p.print(out);
            return;
        }

        String name = getName();
        if (rowNo > -1)
            name += "_" + rowNo;
        String tag = "<input type=\"text\" id=\"" + name + "\" name=\"" + name +"\"";

        if (_maxLength > 0)
            tag += " maxlength=\"" + _maxLength + "\"";

        if (_size > 0) {
            int size = _size;
            tag += " size=\"" + size + "\"";
        }


        String value = getValue(rowNo, true);

        if (value != null)
            tag += " value=\"" + fixSpecialHTMLCharacters(value) + "\"";


        if (_class != null)
            tag += " class=\"" + _class + "\"";

        if (_format != null)
            tag += " format=\"" + _format + "\"";

        if (_emptyok)
            tag += " emptyok=\"True\"";

        if (_tabindex > -1)
            tag += " tabindex=\"" + _tabindex + "\"";

        if (_title != null)
            tag += " title=\"" + _title + "\"";

        tag += "/>";


        p.print(tag);
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

    /**
     * This method gets the format mask for the input.
     */
    public String getFormat() {
        return _format;
    }
        /**
         * This method gets the title for the input.
         */
        public String getTitle() {
            return _title;
        }
        /**
         * This method gets the tabindex of the input.
         */
        public int getTabIndex() {
            return _tabindex;
        }
        /**
         * This method gets the emptyok flag of the input.
         */
        public boolean getEmptyOk() {
            return _emptyok;
        }

    /**
     *Processes the submitted parameters. This method is called by the framework and should not be called directly
     */
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        if (!getVisible() || !getEnabled())
            return false;

        String oldValue = _value;

        String name = getName();
        if (rowNo > -1) {
//            name += "_" + rowNo;
            if (_dsBuff != null)
                oldValue = _dsBuff.getFormattedString(rowNo, _dsColNo);
        } else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getFormattedString(_dsColNo);
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
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getName(), oldValue, _value, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }

        return false;
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

    /**
     * This method sets the format mask for input.
     */
    public void setFormat(String format) {
        _format = format;
    }
    /**
     * This method sets the emptyok flag for input.
     */
    public void setEmptyOk(boolean emptyok) {
        _emptyok = emptyok;
    }
    /**
     * This method sets the tabindex for input.
     */
    public void setTabIndex(int tabindex) {
        _tabindex = tabindex;
    }
    /**
     * This method sets the title for input.
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * This method gets the postfield form of this component.
     */
    public String getPostForm() {
        if (getVisible() && getEnabled())
          return "<postfield name=\""+getName()+"\" value=\"$("+getName()+")\"/>";
        return "";
    }

}
