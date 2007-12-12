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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DisplayBoxTag.java $
//$Author: Dan $
//$Revision: 30 $
//$Modtime: 7/29/04 11:26a $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspDisplayBox;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.util.MessageLog;

/**
 * This is a tag used to create a display box (similar to a window).
 */

public class DisplayBoxTag extends ContainerTag
{
    private String _caption;
    private String _header;
    private String _box;
    private String _backgroundColor;
    private String _headingBackgroundColor;
    private String _border;
    private String _cellPadding;
    private String _cellSpacing;
    private String _theme;
    private String _width;
    private String _font;
    private String _dataSource;
    private String _displayBoxClass;
    /**
     * This method creates the JSPDisplayBox used by the tag.
     */

    public HtmlComponent createComponent()
    {
        JspDisplayBox box = new JspDisplayBox(getName(), getHelper().getController());
        if (!BaseTagHelper.isEmpty(_theme))
            box.setTheme(_theme);
        if (!BaseTagHelper.isEmpty(_border))
            box.setBorder(BaseTagHelper.stringToInt(_border));
        if (!BaseTagHelper.isEmpty(_cellPadding))
            box.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
        if (!BaseTagHelper.isEmpty(_cellSpacing))
            box.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
        if (!BaseTagHelper.isEmpty(_width))
            box.setWidth(_width);
        if (!BaseTagHelper.isEmpty(_headingBackgroundColor))
            box.setHeadingBackgroundColor(_headingBackgroundColor);
        if (!BaseTagHelper.isEmpty(_backgroundColor))
            box.setBackgroundColor(_backgroundColor);
        if (!BaseTagHelper.isEmpty(_font))
            box.setHeaderFont(_font);
        if (_dataSource != null)
            box.setDataSource(_dataSource);

        if (getClassname() != null)
            box.setClassName(getClassname());

        box.setHeadingCaption(_caption);
        return box;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        JspDisplayBox box = (JspDisplayBox) getHelper().getController().getComponent(getName());
        if (box == null)
            return;
        TagWriter w = getTagWriter();
        w.setWriter(p);
        box.generateHTML(w, _header, _box);
    }

    /**
     * Get the tag's background color attribute
     */
    public String getBgcolor()
    {
        return _backgroundColor;
    }

    /**
     * Get the tag's border attribute
     */
    public String getBorder()
    {
        return _border;
    }

    /**
     * Get the tag's caption attribute
     */
    public String getCaption()
    {
        return _caption;
    }

    /**
     * Get the tag's cell padding attribute
     */
    public String getCellpadding()
    {
        return _cellPadding;
    }

    /**
     * Get the tag's cell spacing attribute
     */
    public String getCellspacing()
    {
        return _cellSpacing;
    }

    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }

    /**
     * Get the tag's heading background color attribute.
     */
    public String getHeaderbgcolor()
    {
        return _headingBackgroundColor;
    }

    /**
     * Get the tag's heading font color attribute.
     */
    public String getHeaderfont()
    {
        return _font;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }

    /**
     * Get the tag's theme attribute
     */
    public String getTheme()
    {
        return _theme;
    }

    /**
     * Get the tag's width attribute
     */
    public String getWidth()
    {
        return _width;
    }

    /**
     * Override this method for tags that contain NestedTags within to return true
     */
    public boolean hasNestedTags()
    {
        return true;
    }

    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode()
    {
        boolean retVal = false;
        try
        {
            if (getMode() == MODE_HEADER)
            {
                _header = getBodyContentData(true);
                setMode(MODE_BOX);
                retVal = true;
            }
            else
            {
                _box = getBodyContentData(true);
                retVal = false;
            }

            return retVal;
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return false;
        }
    }

    /**
     * Sets the mode to MODE_HEADER and gets the object ready to render html
     */
    public void initMode()
    {
        setMode(MODE_HEADER);
        _header = null;
        _box = null;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _caption = null;
        _header = null;
        _box = null;
        _backgroundColor = null;
        _headingBackgroundColor = null;
        _font = null;


    }

    /**
     * Sets the tag's background color attribute
     */
    public void setBgcolor(String backgroundColor)
    {
        _backgroundColor = backgroundColor;
    }

    /**
     * Sets the tag's border attribute
     */
    public void setBorder(String newBorder)
    {
        _border = newBorder;
    }

    /**
     * Sets the tag's Caption attribute
     */
    public void setCaption(String caption)
    {
        _caption = caption;
    }

    /**
     * Sets the tag's cell padding attribute
     */

    public void setCellpadding(String newCellPadding)
    {
        _cellPadding = newCellPadding;
    }

    /**
     * Sets the tag's cell spacing attribute
     */

    public void setCellspacing(String newCellSpacing)
    {
        _cellSpacing = newCellSpacing;
    }

    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }

    /**
     * Set's the tag's heading background color attribute
     */
    public void setHeaderbgcolor(String backgroundColor)
    {
        _headingBackgroundColor = backgroundColor;
    }

    /**
     * Set's the tag's heading font attribute
     */
    public void setHeaderfont(String font)
    {
        _font = font;
    }

    /**
     * Sets the tag's theme attribute
     */

    public void setTheme(String newTheme)
    {
        _theme = newTheme;
    }

    /**
     * Sets the tag's width attribute
     */

    public void setWidth(String newWidth)
    {
        _width = newWidth;
    }
}
