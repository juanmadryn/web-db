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

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;

/**
 * Displays a box that allows the user to select the face, style and weight for a font
 */
public class HtmlFontChooser extends HtmlFormComponent {

    String _fontList[] = {"Arial", "Courier", "Helvetica", "TimesRoman", "Verdana"};
    String _pointSizeList[][] = {{"8", "9", "10", "11", "12", "13", "14", "15", "16", "18", "20", "24", "36"},
                                 {"8pt", "9pt", "10pt", "11pt", "12pt", "13pt", "14pt", "15pt", "16pt", "18pt", "20pt", "24pt", "36pt"}};
    String _relativeSizeList[][] = {{"-3", "-2", "-1", "0", "+1", "+2", "+3", "+4"}, {"Smallest", "Very Small", "Small", "Normal", "Large", "Very Large", "Extremely Large", "Largest"}};

    boolean _usePointSize = true;

    private String _font;
    private String _fontStartTag;
    private String _fontEndTag;
    private String _theme;

    /**
     * Constructor for HtmlColorChooser.
     * @param name
     * @param p
     */
    public HtmlFontChooser(String name, HtmlPage p) {
        super(name, p);
    }


    /**
     * @see com.salmonllc.html.HtmlComponent#generateHTML(PrintWriter, int)
     */
    public void generateHTML(PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;

        _value = getValue(rowNo, true);

        String face = null;
        String style = null;
        String size = null;

        if (_value != null) {
            StringTokenizer tok = new StringTokenizer(_value, ",");
            face = tok.nextToken();
            style = tok.nextToken();
            size = tok.nextToken().toLowerCase();
        }

        StringBuffer sb = new StringBuffer();

        //Font Face
        sb.append("<table border=\"0\"><tr><td>");
        sb.append(_fontStartTag);
        sb.append("Font Face:");
        sb.append(_fontEndTag);
        sb.append("</td><td>");
        sb.append("<select name=\"");
        sb.append(getFullName());
        sb.append("face\">");
        sb.append("<option value=\"\"");
        if (face == null)
            sb.append(" selected></option>");
        else
            sb.append("></option>");

        for (int i = 0; i < _fontList.length; i++) {
            sb.append("<option value=\"");
            sb.append(_fontList[i]);
            sb.append("\"");
            if (face != null && face.equalsIgnoreCase(_fontList[i]))
                sb.append(" selected ");
            sb.append(">");
            sb.append(_fontList[i]);
            sb.append("</option>");

        }
        sb.append("</select>");

        //Font Size
        sb.append("<td>");
        sb.append(_fontStartTag);
        sb.append("Size:");
        sb.append(_fontEndTag);
        sb.append("</td><td>");
        sb.append("<select name=\"");
        sb.append(getFullName());
        sb.append("size\">");
        String[][] list = _relativeSizeList;
        if (_usePointSize)
            list = _pointSizeList;
        else {
            if (!size.startsWith("+") && !size.startsWith("-"))
                size = "+" + size;
        }
        for (int i = 0; i < list[0].length; i++) {
            sb.append("<option value=\"");
            sb.append(list[0][i]);
            sb.append("\"");
            if (size != null && (size.equals(list[0][i]) || size.equals(list[1][i])))
                sb.append(" selected ");
            sb.append(">");
            sb.append(list[1][i]);
            sb.append("</option>");

        }
        sb.append("</select>");

        //bold/italic
        sb.append("<td>");
        sb.append(_fontStartTag);
        sb.append("Bold:");
        sb.append(_fontEndTag);
        sb.append("</td><td>");
        sb.append("<input type=\"checkbox\" name=\"");
        sb.append(getFullName());
        sb.append("bold\"");
        if (style != null && style.indexOf("BOLD") > -1)
            sb.append(" checked");
        sb.append(">");

        sb.append("<td>");
        sb.append(_fontStartTag);
        sb.append("Italic:");
        sb.append(_fontEndTag);
        sb.append("</td><td>");
        sb.append("<input type=\"checkbox\" name=\"");
        sb.append(getFullName());
        sb.append("italic\"");
        if (style != null && style.indexOf("ITALIC") > -1)
            sb.append(" checked");
        sb.append(">");
        sb.append("</td></tr></table>");
        p.println(sb.toString());
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

        String font[] = (String[]) parms.get(name + "face");
        String size[] = (String[]) parms.get(name + "size");
        String bold[] = (String[]) parms.get(name + "bold");
        String italic[] = (String[]) parms.get(name + "italic");

        if (font == null || font[0].equals(""))
            _value = null;
        else {
            _value = font[0] + ",";
            if (bold == null && italic == null)
                _value += "PLAIN,";
            else if (bold != null && italic == null)
                _value += "BOLD,";
            else if (bold == null && italic != null)
                _value += "ITALIC,";
            else
                _value += "BOLD|ITALIC,";

            if (size == null)
                _value += _usePointSize ? "10" : "0";
            else
                _value += size[0];
        }
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
     * This method returns the property theme for the component.
     * @return
     */
    public String getTheme() {
        return _theme;
    }


    /**
     * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
     */
    public void setFont(String font) {
        _font = font;
        setTheme(_theme);
    }


    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontEndTag = value;
    }


    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontStartTag = value;
    }


    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {
        Props props = getPage().getPageProperties();

        if (_font != null) {
            _fontStartTag = props.getThemeProperty(theme, _font + Props.TAG_START);
            _fontEndTag = props.getThemeProperty(theme, _font + Props.TAG_END);
        } else {
            _fontStartTag = props.getThemeProperty(theme, HtmlText.FONT_DEFAULT + Props.TAG_START);
            _fontEndTag = props.getThemeProperty(theme, HtmlText.FONT_DEFAULT + Props.TAG_END);
        }

        _theme = theme;
    }


    /**
     * Returns the usePointSize flag. True will mean the size is in points, false means it will be relative.
     * @return boolean
     */
    public boolean isUsePointSize() {
        return _usePointSize;
    }

    /**
     * Sets the usePointSize flag. True will mean the size is in points, false means it will be relative.
     * @param usePointSize The usePointSize to set
     */
    public void setUsePointSize(boolean usePointSize) {
        _usePointSize = usePointSize;
    }

    public String getValueAsStyle(String color) {
        String value = getValue();
        String face = null;
        String style = null;
        String size = null;

        if (value != null) {
            StringTokenizer tok = new StringTokenizer(_value, ",");
            face = tok.nextToken();
            if (face.equals(""))
                return null;
            style = tok.nextToken();
            size = tok.nextToken();
            StringBuffer ret = new StringBuffer();
            ret.append("font-family: ");
            ret.append(face);
            ret.append("; font-size: ");
            ret.append(size.toString());
            ret.append(";text-decoration: none;");
            if (style.indexOf("BOLD") > -1)
                ret.append("font-weight:bold;");
            if (style.indexOf("ITALIC") > -1)
                ret.append("font-style:italic;>");
            if (color != null) {
                ret.append("color:");
                ret.append(color);
                ret.append(";");
            }
            return ret.toString();
        }
        return null;
    }

    /**
     * Returns the value in the component as the start of a generated font tag
     */
    public String getValueAsFontStartTag(String color) {
        String value = getValue();

        String face = null;
        String style = null;
        String size = null;

        if (value != null) {
            StringTokenizer tok = new StringTokenizer(value, ",");
            face = tok.nextToken();
            if (face.equals(""))
                return null;
            style = tok.nextToken();
            size = tok.nextToken();
            if (size.endsWith("PT") || size.endsWith("pt"))
                size = size.substring(0, size.length() - 2);
            StringBuffer ret = new StringBuffer();
            ret.append("<FONT face=\"");
            ret.append(face);
            ret.append("\"");
            if (_usePointSize) {
                ret.append(" STYLE=\"FONT-SIZE:");
                ret.append(size);
                ret.append("pt;\"");
            } else {
                ret.append("\" SIZE=\"");
                ret.append(size);
                ret.append("\"");
            }
            if (color != null) {
                ret.append(" COLOR=\"");
                ret.append(color);
                ret.append("\"");
            }
            ret.append(">");
            if (style.indexOf("BOLD") > -1)
                ret.append("<b>");
            if (style.indexOf("ITALIC") > -1)
                ret.append("<i>");
            return ret.toString();

        }
        return null;

    }

    /**
     * Returns the value in the component as the start of a generated font tag
     */
    public String getValueAsFontEndTag() {
        String value = getValue();
        String style = null;
        if (value != null) {
            StringTokenizer tok = new StringTokenizer(value, ",");
            tok.nextToken();
            style = tok.nextToken();
            StringBuffer ret = new StringBuffer();
            if (style.indexOf("ITALIC") > -1)
                ret.append("</i>");
            if (style.indexOf("BOLD") > -1)
                ret.append("</b>");
            ret.append("</FONT>");
            return ret.toString();

        }
        return null;

    }

    /**
     * Sets the value of this component from the attributes in a FONT tag
     */
    public void setFontValue(String fontTag) {
        if (fontTag == null) {
            setValue(null);
            return;
        }
        String face = getTagAttribute(fontTag, "face");
        String size = getTagAttribute(fontTag, "size");
        if (size == null) {
            size = getTagAttribute(fontTag, "style").toUpperCase();
            int pos = size.indexOf("FONT-SIZE:");
            if (pos > -1) {
                int pos2 = size.indexOf(";", pos);
                if (pos2 > -1) {
                    size = size.substring(pos + 10, pos2);
                    _usePointSize = true;
                }
            }
        } else
            _usePointSize = false;

        fontTag = fontTag.toLowerCase();
        boolean italic = (fontTag.indexOf("<i>") > -1);
        boolean bold = (fontTag.indexOf("<b>") > -1);
        String style = "PLAIN";

        if (bold && !italic)
            style += "BOLD";
        else if (!bold && italic)
            style += "ITALIC";
        else if (bold && italic)
            style += "BOLD|ITALIC";

        String value = face + "," + style + "," + size;
        setValue(value);

    }

    /**
     * Sets the value of this component from the attributes in a CSS Style
     */
    public void setStyleValue(String style) {
        if (style == null) {
            setValue(null);
            return;
        }
        String face = getStyleAttribute(style, "font-family");
        String size = getStyleAttribute(style, "font-size");
        if (size != null && size.endsWith("px"))
            size = size.substring(0, size.length() - 2);
        String weight = getStyleAttribute(style, "font-weight");
        String fontStyle = getStyleAttribute(style, "font-style");
        _usePointSize = true;

        boolean bold = (weight != null && weight.equalsIgnoreCase("bold"));
        boolean italic = (fontStyle != null && style.equalsIgnoreCase("italic"));

        String s = "PLAIN";
        if (bold && !italic)
            s += "BOLD";
        else if (!bold && italic)
            s += "ITALIC";
        else if (bold && italic)
            s += "BOLD|ITALIC";

        String value = face + "," + s + "," + size;
        setValue(value);

    }

    /**
     * Gets an attribute from a style entry
     */
    public static String getStyleAttribute(String style, String attribute) {
        if (style == null)
            return null;

        String test1 = style.toUpperCase();
        String test2 = attribute.toUpperCase();
        int pos1 = test1.indexOf(test2 + ":");
        if (pos1 == -1)
            return null;
        int pos2 = test1.indexOf(";", pos1);
        if (pos2 == -1)
            return null;
        return style.substring(pos1 + attribute.length() + 1, pos2).trim();
    }

    /**
     * Specify a font and and attribute and this method will return the value
     */
    public static String getTagAttribute(String tag, String attribute) {
        Token tok = new Token();
        boolean next = false;
        while (nextToken(tag, tok)) {
            String val = tok.value.toString();
            if (next)
                return val;
            if (val.equalsIgnoreCase(attribute))
                next = true;
        }
        return null;
    }

    private static boolean nextToken(String st, Token tok) {
        tok.value.setLength(0);
        boolean quoteMode = false;
        if (tok.index >= st.length())
            return false;
        while ((tok.index < st.length())) {
            char c = st.charAt(tok.index);
            if (quoteMode) {
                tok.index++;
                if (c == '"')
                    return true;
                else
                    tok.value.append(c);
            } else if (c == '"') {
                quoteMode = true;
                tok.index++;
            } else {
                if (c == '<')
                    tok.index++;
                else if (c == ' ' || c == '=' || c == '>') {
                    if (tok.value.length() != 0)
                        return true;
                    tok.index++;
                } else {
                    tok.value.append(c);
                    tok.index++;
                }
            }
        }
        return false;
    }

    private static class Token {
        StringBuffer value = new StringBuffer();
        int index;
    }
}
