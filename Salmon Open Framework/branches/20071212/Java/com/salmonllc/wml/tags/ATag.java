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
package com.salmonllc.wml.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/wml/tags/ATag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 11/15/02 3:04p $
/////////////////////////

import com.salmonllc.util.MessageLog;

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;
import com.salmonllc.wml.*;
import com.salmonllc.jsp.tags.ContainerTag;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.jsp.tags.DataTableTag;
import com.salmonllc.jsp.tags.ListTag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * This is a tag used to create an ATag for a WmlLink. This is an anchor in WML.
 */

public class ATag extends ContainerTag
{
    private String _box;
    private String _href;
    private String _class;
    private String _dataSource;
    private String _bracket;
    private String _bracketFont;
    private String _title;


    /**
     * This method creates the WmlLink used by the tag.
     */

    public HtmlComponent createComponent()
    {
        WmlLink link = new WmlLink(getName(), _href, getHelper().getController());
        if (_title != null)
           link.setTitle(_title);
        if (getClassname() != null)
            link.setClassName(getClassname());
        if (_dataSource != null)
            link.setDataSource(_dataSource);
        if (_bracket != null)
         {
           link.setBracket(BaseTagHelper.stringToBoolean(getBracket(),false));
           if (_bracketFont != null)
            link.setBracketFont(_bracketFont);
         }
        return link;
    }
    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        WmlLink link = (WmlLink) getHelper().getController().getComponent(getName());
        if (link == null)
            return;
        HtmlComponent comp = getHelper().getComponent();
        int startRow = -1;

        DataTableTag dt = getHelper().getDataTableTag();
        if (dt != null)
        {
            startRow = dt.getStartRow();
        }
        else
        {
            ListTag l = getHelper().getListTag();
            if (l != null)
            {
                startRow = l.getRow();
            }
        }
        if (comp != null)
        {
            TagWriter w = getTagWriter();
            w.setWriter(p);
            link.generateHTML(w, _box, startRow);
        }
    }
    /**
     * Get the tag's bracket attribute
     */
    public String getBracket()
    {
        return _bracket;
    }
    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }
    /**
     * Get the tag's Href attribute
     */
    public String getHref()
    {
        return _href;
    }
    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }
    /**
     * Get the tag's title attribute
     */
    public String getTitle()
    {
        return _title;
    }
    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode()
    {
        try
        {
            _box = getBodyContentData(true);
            return false;
        }

        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return false;
        }
    }
    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode()
    {
        _box = null;
    }
    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _href = null;
        _box = null;
        _bracket = null;
        _bracketFont = null;
        _class = null;
        _dataSource = null;
        _title = null;

    }
    /**
     * Set the Brackets  if the component should be have brackets around it.
     */
    public void setBracket(String val)
    {
        _bracket = val;
    }
    /**
     * Set the Brackets Font of the component when brackets are around it.
     */
    public void setBracketfont(String val)
    {
        _bracketFont = val;
    }
    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }
    /**
     * Sets the tag's href color attribute
     */
    public void setHref(String href)
    {
        _href = href;
    }
    /**
     * Sets the tag's title attribute
     */
    public void setTitle(String val)
    {
        _title = val;
    }
}
