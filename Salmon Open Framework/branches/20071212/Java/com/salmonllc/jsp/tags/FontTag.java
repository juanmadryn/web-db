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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/FontTag.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 4/15/03 11:06a $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.properties.Props;

/**
 * This (body) tag is to enable making generic controllers. The contents of the body is the value.
 */

public class FontTag extends BaseBodyTag
{
    String _content;
    String _type;

    /**
     * This method creates a null component.
     */
    public com.salmonllc.html.HtmlComponent createComponent()
    {
        return null;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        TagWriter w = getTagWriter();
        w.setWriter(p);

        JspController cont = getHelper().getController();
        Props pr = cont.getPageProperties();

        String startTag = pr.getProperty(_type + Props.TAG_START, "");
        String endTag = pr.getProperty(_type + Props.TAG_END, "");

        w.print(startTag, TagWriter.TYPE_BEGIN_TAG);
        w.print(_content, TagWriter.TYPE_CONTENT);
        w.print(endTag, TagWriter.TYPE_END_TAG);

    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }

    public boolean incrementMode()
    {
        _content = getBodyContentData(true);
        return false;
    }

    /**
     * This method sets the type for the font
     */
    public void release()
    {
        _type = null;
        _content = null;
    }

    /**
     * This method sets the type for the font
     */
    public void setType(String type)
    {
        _type = type;
    }
}
