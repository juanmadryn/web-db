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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/BoxTag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 11/15/02 3:01p $
/////////////////////////

import com.salmonllc.util.MessageLog;

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * This is a tag used to create a display box (similar to a window).
 */

public class BoxTag extends ContainerTag {
    private String _lineWidth;
    private String _lineColor;
    private String _box;
    private String _theme;
    private String _bgColor;
    private String _width;
    private String _margin;

    /**
     * This method creates the JSPBox used by the tag.
     */

    public HtmlComponent createComponent() {
        JspBox box = new JspBox(getName(), getHelper().getController(),_theme);
        if (!BaseTagHelper.isEmpty(_lineWidth))
            box.setLineWidth(_lineWidth);
        if (!BaseTagHelper.isEmpty(_lineColor))
            box.setLineColor(_lineColor);
        if (!BaseTagHelper.isEmpty(_bgColor))
            box.setBgColor(_bgColor);
        if (!BaseTagHelper.isEmpty(_width))
            box.setWidth(_width);
        if (!BaseTagHelper.isEmpty(_margin))
            box.setMargin(_margin);

        return box;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException {
        JspBox box = (JspBox) getHelper().getController().getComponent(getName());
        if (box == null)
            return;
        TagWriter w = getTagWriter();
        w.setWriter(p);
        box.generateHTML(w, _box);
    }

    /**
     * Get the tag's background color attribute
     */
    public String getBgcolor() {
        return _bgColor;
    }

    /**
     * Get the tag's Line Width attribute
     */
    public String getLinewidth() {
        return _lineWidth;
    }

    /**
     * Get the tag's width attribute
     */
    public String getWidth() {
        return _width;
    }

    /**
     * Get the tag's Line Color attribute
     */
    public String getLinecolor() {
        return _lineColor;
    }


    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_CUSTOM;
    }

    /**
     * Get the tag's theme attribute
     */
    public String getTheme() {
        return _theme;
    }


    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode() {
        try {
            _box = getBodyContentData(true);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
        }
        return false;
    }

    /**
     * Initializes the tag
     */
    public void initMode() {
        _box = null;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release() {
        super.release();
        _lineColor = null;
        _lineWidth = null;
        _box = null;
        _bgColor = null;
        _theme = null;
        _width = null;
        _margin = null;
    }

    /**
     * Sets the tag's background color attribute
     */
    public void setBgcolor(String backgroundColor) {
        _bgColor = backgroundColor;
    }

    /**
     * Sets the tag's border attribute
     */
    public void setLinewidth(String width) {
        _lineWidth = width;
    }
    /**
     * Sets the tag's width
     */
    public void setWidth(String width) {
        _width = width;
    }

    /**
     * Sets the tag's Caption attribute
     */
    public void setLinecolor(String color) {
        _lineColor = color;
    }


    /**
     * Sets the tag's theme attribute
     */

    public void setTheme(String newTheme) {
        _theme = newTheme;
    }

     /**
     * Gets the space between the line and the contents
     */
    public String getMargin() {
        return _margin;
    }

    /**
     * Sets the space between the line and the contents
     */
    public void setMargin(String margin) {
        _margin = margin;
    }
}
