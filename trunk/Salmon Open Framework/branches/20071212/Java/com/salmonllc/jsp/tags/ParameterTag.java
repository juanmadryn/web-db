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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ParameterTag.java $
//$Author: Dan $
//$Revision: 21 $
//$Modtime: 6/16/03 5:31p $
/////////////////////////

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspParameter;
import com.salmonllc.jsp.TagWriter;

/**
 * This (body) tag is to enable making generic controllers. The contents of the body is the value.
 */

public class ParameterTag extends BaseBodyTag
{
    String _visible;

    /**
     * This method is called after the container has been initialized. It is used to update the visible and enabled attributes of the sub tags.
     */
    public void afterInit(HtmlComponent comp)
    {
        String value = getBodyContentData(true);
        ((JspParameter) comp).setValue(value);
    }

    /**
     * This method creates a JspParameter object.
     */
    public com.salmonllc.html.HtmlComponent createComponent()
    {
        JspParameter paramComp = new JspParameter(getName(), getHelper().getController());
        return paramComp;

    }

    /**
     *  @return true so the body content always gets written
     */
    public boolean alwaysWriteContent() {
        return true;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws Exception
    {
        //Only dump out the content in dreamweaver mode, otherwise the parameter will show in the html contents of the page
        if (getHelper().getTagContext().getDreamWeaverMode())
        {
            JspParameter parm = (JspParameter) getHelper().getComponent();
            p.print(TagWriter.dreamWeaverConv("", "param", parm.getValue(), "{" + getName() + "}"));
        }

    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_INVISIBLE;
    }

    public String getVisible()
    {
        return _visible;
    }

    public void setVisible(String sVisible)
    {
        _visible = sVisible;
    }

    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    protected boolean incrementMode() {
        clearBodyContent();
        return false;
    }
}
