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
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DetailFormTag.java $
//$Author: Dan $
//$Revision: 16 $
//$Modtime: 10/30/02 2:40p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

/**
 * This is a tag used to implement the a Detailform.
 */

public class DetailFormTag extends BaseEmptyTag
{
    public String _datadef;
    public String _savebutton;
    public String _deletebutton;
    public String _cancelbutton;
    public String _caption;
    private String _dataSource;

    /**
     * This method creates the JspDetailForm object for this tag.
     */

    public HtmlComponent createComponent()
    {
        JspDetailForm detailForm = new JspDetailForm(getName(), getHelper().getController());
        //

        if (_datadef != null)
            detailForm.setDataDictionary(_datadef);
        if (_savebutton != null)
            detailForm.setSaveButton(_savebutton);
        if (_deletebutton != null)
            detailForm.setDeleteButton(_deletebutton);
        if (_cancelbutton != null)
            detailForm.setCancelButton(_cancelbutton);
        if (_caption != null)
            detailForm.setCaption(_caption);
        if (_dataSource != null)
            detailForm.setDataSource(_dataSource);
        return detailForm;
    }

    /**
     * Gets the cancel button specified in the JSP page
     * Creation date: (8/2/01 11:33:55 AM)
     * @return java.lang.String
     */
    public java.lang.String getCancelbutton()
    {
        return _cancelbutton;
    }

    /**
     * Gets the caption for Detail form page
     * Creation date: (8/2/01 11:33:55 AM)
     * @return java.lang.String
     */
    public java.lang.String getCaption()
    {
        return _caption;
    }

    /**
     * Gets the data definition, XML file, used by detail form component.
     * Creation date: (7/20/01 4:04:16 PM)
     * @return java.lang.String
     */
    public java.lang.String getDatadef()
    {
        return _datadef;
    }

    /**
     * Get the Data Source the component should be bound to.
     */
    public java.lang.String getDatasource()
    {
        return _dataSource;
    }

    /**
     * Gets the handle to the delete button specified in the JSP page
     * Creation date: (8/2/01 11:32:58 AM)
     * @return java.lang.String
     */
    public java.lang.String getDeletebutton()
    {
        return _deletebutton;
    }

    /**
     * Gets the handle to the save button specified in the JSP page
     * Creation date: (8/2/01 11:32:58 AM)
     * @return java.lang.String
     */
    public java.lang.String getSavebutton()
    {
        return _savebutton;
    }



    /**
     * Sets the handle to the cancel button specified in the JSP page
     * Creation date: (8/2/01 11:33:55 AM)
     * @param new_cancelbutton java.lang.String
     */
    public void setCancelbutton(java.lang.String new_cancelbutton)
    {
        _cancelbutton = new_cancelbutton;
    }

    /**
     * Sets the caption for the detail form used by detail form component.
     * Creation date: (8/2/01 11:33:55 AM)
     * @param new_caption java.lang.String
     */
    public void setCaption(java.lang.String new_caption)
    {
        _caption = new_caption;
    }

    /**
     * Sets the data definition, XML filename, used by detail form component.
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
     * Sets the handle to the delete button specified in the JSP page
     * Creation date: (8/2/01 11:32:58 AM)
     * @param new_deletebutton java.lang.String
     */
    public void setDeletebutton(java.lang.String new_deletebutton)
    {
        _deletebutton = new_deletebutton;
    }

    /**
     * Sets the handle to the save button specified in the JSP page
     * Creation date: (8/2/01 11:32:58 AM)
     * @param new_savebutton java.lang.String
     */
    public void setSavebutton(java.lang.String new_savebutton)
    {
        _savebutton = new_savebutton;
    }
}
