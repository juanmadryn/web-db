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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/tags/SubmitTag.java $
//$Author: Dan $
//$Revision: 5 $
//$Modtime: 6/11/03 4:30p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseEmptyTag;
import com.salmonllc.wml.WmlSubmit;

/**
 * This is a tag used to create a Wml Anchor which will act as a submit.
 */

public class SubmitTag extends  BaseEmptyTag
{
    private String _dataSource;
    private String _title;
    private String _classname;

    /**
     * This method creates the WmlSubmit used by the tag.
     */

    public HtmlComponent createComponent()
    {
        WmlSubmit submit = new WmlSubmit(getName(), getHelper().getController());
        CardTag cTag=(CardTag)findAncestorWithClass(this,CardTag.class);
        submit.setCard(cTag.getCard());
        if (_title != null)
           submit.setTitle(_title);
        if (_classname != null)
            submit.setClassName(_classname);
        if (_dataSource != null)
            submit.setDataSource(_dataSource);
        return submit;
    }
    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }
    /**
     * Get the tag's title attribute
     */
    public String getTitle()
    {
        return _title;
    }
    /**
     * Get the tag's classname attribute
     */
    public String getClassname()
    {
        return _classname;
    }
    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }
    /**
     * Sets the tag's title attribute
     */
    public void setTitle(String val)
    {
        _title = val;
    }
    /**
     * Sets the tag's classname attribute
     */
    public void setClassname(String val)
    {
        _classname = val;
    }


}
