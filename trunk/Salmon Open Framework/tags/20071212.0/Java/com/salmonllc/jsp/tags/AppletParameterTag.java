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

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/AppletParameterTag.java $
//$Author: Fred $
//$Revision: 2 $
//$Modtime: 4/17/03 11:14a $
////////////////////////////////////////////////////////////

import com.salmonllc.util.Util;

import com.salmonllc.html.*;

/**
 * The option tag represents the options of a list box
 * or drop down list box and needs to live in an input tag.
 */
public class AppletParameterTag extends BaseEmptyTag
{
    private String _value;
    private String _table;
    private String _criteria;

    /**
     * There is no component for an option. It adds the option to it's parent
     * @return HtmlComponent
     */
    public HtmlComponent createComponent()
    {
        String table = null;
        HtmlComponent comp = getHelper().getAppletTag().getHelper().getComponent();
        table = getTable();
        if (comp != null)
        {
            if (!Util.isNull(table) && !Util.isEmpty(table))
            {
                ((HtmlApplet) comp).addParameters(table,
                        getName(),
                        getValue(),
                        getCriteria());
            }
            else
                ((HtmlApplet) comp).setParm(getName(), _value);
        }

        return null;
    }

    /**
     * Get the tag's value attribute
     * @return
     */

    public String getValue()
    {
        return _value;
    }


    /**
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _value=null;
        _table=null;
        _criteria=null;
    }

    /**
     * Set the tag's value attribute
     * @param value
     */

    public void setValue(String value)
    {
        _value = value;
    }


    /**
     * Get the tag's table attribute
     * @return
     */
    public String getTable()
    {
        return _table;
    }

    /**
     * Set the tag's table attribute
     * @param table
     */
    public void setTable(String table)
    {
        _table = table;
    }


    /**
     * Get the tag's criteria attribute
     * @return
     */
    public String getCriteria()
    {
        return _criteria;
    }

    /**
     * Set the tag's criteria attribute
     * @param displaycolumn
     */
    public void setCriteria(String criteria)
    {
        _criteria = criteria;
    }


}
