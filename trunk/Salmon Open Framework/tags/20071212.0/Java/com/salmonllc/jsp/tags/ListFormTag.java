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
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/ListFormTag.java $
//$Author: Dan $
//$Revision: 17 $
//$Modtime: 10/30/02 2:58p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

/**
 * This is a tag used to implement the a ListForm.
 */

public class ListFormTag extends BaseEmptyTag
{
    public String _datadef;
    public String _buttons;
    public String _detailpage;
    public String _caption;
    private String _dataSource;

    /**
     * This method creates the JspListForm object for this tag.
     */

    public HtmlComponent createComponent()
    {
        JspListForm listForm = new JspListForm(getName(), getHelper().getController());
        //

        if (_datadef != null)
            listForm.setDataDictionary(_datadef);
        if (_buttons != null)
            listForm.setButtons(_buttons);
        if (_detailpage != null)
            listForm.setDetailPageName(_detailpage);

        if (_caption != null)
            listForm.setCaption(_caption);
        if (_dataSource != null)
            listForm.setDataSource(_dataSource);

        return listForm;
    }

    /**
     * This method returns the button string set in the JSP page
     * Creation date: (7/20/01 4:04:16 PM)
     * @return java.lang.String
     */
    public java.lang.String getButtons()
    {
        return _buttons;
    }

    /**
     * Gets the caption for the Listform
     * Creation date: (8/2/01 11:33:55 AM)
     * @return java.lang.String
     */
    public java.lang.String getCaption()
    {
        return _caption;
    }

    /**
     * Gets the data definition, XML filename, used by List form.
     * Creation date: (7/20/01 4:04:16 PM)
     * @return java.lang.String
     */
    public java.lang.String getDatadef()
    {
        return _datadef;
    }

    /**
     * Get the Data Source the component should be bound to
     */
    public java.lang.String getDatasource()
    {
        return _dataSource;
    }

    /**
     * Gets the DetailForm JSP page name
     * Creation date: (7/25/01 3:44:46 PM)
     * @return java.lang.String
     */
    public java.lang.String getDetailpage()
    {
        return _detailpage;
    }


    /**
     * Sets the button string. Buttons like Search, Advance Search are specified in this string separated by '|'.
     * Creation date: (7/20/01 4:04:16 PM)
     * @param new_buttons java.lang.String
     */
    public void setButtons(java.lang.String new_buttons)
    {
        _buttons = new_buttons;
    }

    /**
     * Sets the caption for the Listform.
     * Creation date: (8/2/01 11:33:55 AM)
     * @param new_caption java.lang.String
     */
    public void setCaption(java.lang.String new_caption)
    {
        _caption = new_caption;
    }

    /**
     * Sets the datadefinition XML file name used by Listform component.
     * Creation date: (7/20/01 4:04:16 PM)
     * @param new_datadef java.lang.String
     */
    public void setDatadef(java.lang.String new_datadef)
    {
        _datadef = new_datadef;
    }

    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(java.lang.String val)
    {
        _dataSource = val;
    }

    /**
     * Sets the name of the detailform JSP page.
     * Creation date: (7/25/01 3:44:46 PM)
     * @param new_detailPage java.lang.String
     */
    public void setDetailpage(java.lang.String new_detailPage)
    {
        _detailpage = new_detailPage;
    }
}
