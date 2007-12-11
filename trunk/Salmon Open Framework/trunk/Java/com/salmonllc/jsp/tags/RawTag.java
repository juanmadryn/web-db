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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/RawTag.java $
//$Author: Dan $
//$Revision: 23 $
//$Modtime: 7/16/03 8:07p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This tag is used to place a piece of Html in a page that can be manipluated by the controller
 */

public class RawTag extends ContainerTag {

    private String _fileName;
    /**
     * srufle 04-09-2002
     * not being used:
     * private String _box;
     */
    private String _dataSource;

    public HtmlComponent createComponent() {
        JspRaw raw = new JspRaw(getName(), null, getHelper().getController());
        if (getVisible() != null)
            raw.setVisible(new Boolean(getVisible()).booleanValue());
        if (_fileName != null)
            raw.setFileName(_fileName);
        if (_dataSource != null)
            raw.setDataSource(_dataSource);

        return raw;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException {
        JspRaw raw = (JspRaw) getHelper().getController().getComponent(getName());
        if (raw == null)
            return;

        HtmlComponent comp = getHelper().getComponent();
        int startRow = -1;

        DataTableTag dt = getHelper().getDataTableTag();
        if (dt != null) {
            startRow = dt.getStartRow();
        } else {
            ListTag l = getHelper().getListTag();
            if (l != null) {
                startRow = l.getRow();
            }
        }
        if (comp != null) {
            raw.generateHTML(new PrintWriter(p), startRow);
        }

    }

    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource() {
        return _dataSource;
    }

    /**
     * Returns the file name used attribute
     */
    public String getFilename() {
        return _fileName;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_INVISIBLE;
    }

    public boolean incrementMode() {
        clearBodyContent();
        return false;

    }

    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode() {
      //commented this out because it was causing a problem with tag pooling in Tomcat 4.1
      // clearBodyContent();
    }

    public void release() {
        super.release();

    }

    /**
     * Set the Data Source the component should be bound to
     */

    public void setDatasource(String val) {
        _dataSource = val;
    }

    /**
     * Sets the file name attribute
     */

    public void setFilename(String val) {
        _fileName = val;
    }

    /**
     * This method can be used by components that need to change a component after all the sub tags have been initialized.
     */
    public void afterInit(HtmlComponent comp) {
        super.afterInit(comp);
        String html = getBodyContentData(true);
        ((JspRaw) comp).setHtml(html);
    }

    /**
     *  @return true so the body content always gets written
     */
    public boolean alwaysWriteContent() {
        return true;
    }

}
