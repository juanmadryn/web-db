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
//$Archive: /JADE/SourceCode/com/salmonllc/wml/tags/TextTag.java $
//$Author: Dan $
//$Revision: 2 $
//$Modtime: 10/30/02 2:59p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.tags.BaseEmptyTag;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.wml.WmlText;

/**
 * This tag creates an HTMLText component
 */

public class TextTag extends BaseEmptyTag
{
    private String _text;
    private String _dataSource;
    private String _fixwml;
    private String _bold;
    private String _big;
    private String _italic;
    private String _small;
    private String _strong;
    private String _underline;

    /**
     * Creates the HtmlText component used by the tag
     */

    public HtmlComponent createComponent()
    {
        WmlText wt = null;
                wt = new WmlText(getName(), _text, getHelper().getController());
                wt.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
                wt.setBold(BaseTagHelper.stringToBoolean(_bold, false));
                wt.setBig(BaseTagHelper.stringToBoolean(_big, false));
                wt.setItalic(BaseTagHelper.stringToBoolean(_italic, false));
                wt.setSmall(BaseTagHelper.stringToBoolean(_small, false));
                wt.setStrong(BaseTagHelper.stringToBoolean(_strong, false));
                wt.setUnderline(BaseTagHelper.stringToBoolean(_underline, false));
                if (_dataSource != null)
                    wt.setDataSource(_dataSource);

                // setting whether to fixwml characters
                if (_fixwml != null)
                    wt.setFixSpecialWmlCharacters(BaseTagHelper.stringToBoolean(getFixwml(), true));
                return wt;
    }


    /**
     * Get the Data Source the component should be bound to
     */

    public String getDatasource()
    {
        return _dataSource;
    }


    /**
     * Gets the text property for the tag
     */

    public String getText()
    {
        return _text;
    }

    /**
     * Gets the bold property for the tag
     */

    public String getBold()
    {
        return _bold;
    }
    /**
     * Gets the big property for the tag
     */

    public String getBig()
    {
        return _big;
    }
    /**
     * Gets the italic property for the tag
     */

    public String getItalic()
    {
        return _italic;
    }
    /**
     * Gets the small property for the tag
     */

    public String getSmall()
    {
        return _small;
    }
    /**
     * Gets the strong property for the tag
     */

    public String getStrong()
    {
        return _strong;
    }
    /**
     * Gets the underline property for the tag
     */

    public String getUnderline()
    {
        return _underline;
    }

    public void release()
    {
        super.release();
        _text = null;
        _fixwml = null;
        _dataSource = null;
        _bold = null;
        _big = null;
        _italic = null;
        _small = null;
        _strong = null;
        _underline = null;
    }

    /**
     * Specify whether special html characters (<,>,&,; etc..) should be converted to Wml Escape Sequences before being generated.
     */
    public void setFixwml(String new_fixwml)
    {
        _fixwml = new_fixwml;
    }


    /**
     * Set the Data Source the component should be bound to
     */

    public void setDatasource(String newValue)
    {
        _dataSource = newValue;
    }

    /**
     * Sets the font property for the tag
     */


    /**
     * Sets the text property for the tag
     */

    public void setText(String newValue)
    {
        _text = newValue;
    }


    /**
     * Gets the fixwml property for the tag
     */

    public String getFixwml()
    {
        return _fixwml;
    }
    /**
     * Sets the bold property for the tag
     */

    public void setBold(String newValue)
    {
        _bold = newValue;
    }
    /**
     * Sets the big property for the tag
     */

    public void setBig(String newValue)
    {
        _big = newValue;
    }
    /**
     * Sets the italic property for the tag
     */

    public void setItalic(String newValue)
    {
        _italic = newValue;
    }
    /**
     * Sets the small property for the tag
     */

    public void setSmall(String newValue)
    {
        _small = newValue;
    }
    /**
     * Sets the strong property for the tag
     */

    public void setStrong(String newValue)
    {
        _strong = newValue;
    }
    /**
     * Sets the underline property for the tag
     */

    public void setUnderline(String newValue)
    {
        _underline = newValue;
    }

}
