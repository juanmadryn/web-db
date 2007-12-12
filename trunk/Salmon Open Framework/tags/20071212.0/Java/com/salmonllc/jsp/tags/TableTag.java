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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/TableTag.java $
//$Author: Dan $
//$Revision: 25 $
//$Modtime: 1/22/04 12:36p $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspTable;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.util.MessageLog;

/**
 * This is a tag used to implement the a Html Table.
 */

public class TableTag extends ContainerTag
{
    private int _startRow = 0;
    private int _endRow = 0;
    private String _content;

	private String _currentBackgroundColor = null;
	private String _currentRowStyle = null;
    private String _align;
    private String _bgcolor;
    private String _bordercolor;
    private String _border;
    private String _cellPadding;
    private String _cellSpacing;
    private String _height;
    private String _width;
    private String _cols;
    private String _hspace;
    private String _vspace;
    private String _theme;
    private String _dataSource;
    //   private String _class;

    /**
     * This method creates the JspDataTable object for this tag.
     */

    public HtmlComponent createComponent()
    {
        JspTable tab = new JspTable(getName(), getHelper().getController());
        //
        tab.setAlign(_align);
        //
        if (_theme != null)
            tab.setTheme(_theme);
        if (_bgcolor != null)
            tab.setBackgroundColor(_bgcolor);
        if (_bordercolor != null)
            tab.setBorderColor(_bordercolor);
        if (_border != null)
            tab.setBorder(BaseTagHelper.stringToInt(_border));
        if (_cellPadding != null)
            tab.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
        if (_cellSpacing != null)
            tab.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
        if (_height != null)
            tab.setHeight(_height);
        if (_width != null)
            tab.setWidth(_width);
        if (_cols != null)
            tab.setCols(BaseTagHelper.stringToInt(_cols));
        if (_hspace != null)
            tab.setHSpace(BaseTagHelper.stringToInt(_hspace));
        if (_vspace != null)
            tab.setVSpace(BaseTagHelper.stringToInt(_vspace));
        if (getVisible() != null)
            tab.setVisible(new Boolean(getVisible()).booleanValue());
        if (getEnabled() != null)
            tab.setEnabled(new Boolean(getEnabled()).booleanValue());
        if (_dataSource != null)
            tab.setDataSource(_dataSource);
        if (getClassname() != null)
            tab.setClassName(getClassname());

        return tab;

    }

    /**
     * This method generates the Html for the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        JspTable tab = (JspTable) getHelper().getController().getComponent(getName());
        TagWriter w = getTagWriter();
        w.setWriter(p);
        tab.generateHTML(w, _content.toString());
    }

    /**
     * gets the align property for the tag
     */
    public java.lang.String getAlign()
    {
        return _align;
    }

    /**
     * gets the background color property for the tag
     */

    public String getBgcolor()
    {
        return _bgcolor;
    }

    /**
     * gets the border property for the tag
     */

    public String getBorder()
    {
        return _border;
    }

    /**
     * gets the background color property for the tag
     */

    public String getBordercolor()
    {
        return _bordercolor;
    }

    /**
     * gets the cell padding property for the tag
     */

    public String getCellpadding()
    {
        return _cellPadding;
    }

    /**
     * gets the cell spacing property for the tag
     */

    public String getCellspacing()
    {
        return _cellSpacing;
    }

    /**
     * gets the cols property for the tag
     */

    public String getCols()
    {
        return _cols;
    }

	/**
	 * gets the current background color for the tag
	 */

	public String getCurrentBackgroundColor()
	{
		return _currentBackgroundColor;
	}
	
	/**
	 * gets the current style class for the rows in the table
	 */

	public String getCurrentRowStyle()
	{
		return _currentRowStyle;
	}

    /**
     * Get the Data Source the component should be bound to
     */

    public String getDatasource()
    {
        return _dataSource;
    }

    /**
     * This method returns the current start row for the datatable
     */
    public int getEndRow()
    {
        return _endRow;
    }

    /**
     * gets the height property for the tag
     */

    public String getHeight()
    {
        return _height;
    }

    /**
     * gets the horizontal space property for the tag
     */

    public String getHspace()
    {
        return _hspace;
    }

    /**
     * This method returns the current start row for the datatable
     */
    public int getStartRow()
    {
        return _startRow;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }

    /**
     * gets the theme property for the tag
     */

    public String getTheme()
    {
        return _theme;
    }

    /**
     * gets the vertical space property for the tag
     */

    public String getVspace()
    {
        return _vspace;
    }

    /**
     * gets the width property for the tag
     */

    public String getWidth()
    {
        return _width;
    }

    /**
     * This method will step through a row of the Html table each time it is called and controls the flow through the sub tags
     */

    public boolean incrementMode()
    {
        boolean retVal = false;
        try
        {
            _content = getBodyContentData(true);
            return retVal;

        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return retVal;
        }
    }

    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode()
    {
//	setMode(MODE_HEADER);
        _startRow = 0;
        _endRow = 0;
        _content = null;
        JspTable tab = ((JspTable) getHelper().getComponent());
        tab.resetCounters();
        _currentBackgroundColor = tab.getBackgroundColor();
        _currentRowStyle = tab.getRowStyleClassName();
    }

    /**
     * Releases any resources used by the tag.
     */

    public void release()
    {
        super.release();
        _content = null;
        _currentBackgroundColor = null;
		_currentRowStyle=null;
        _align = null;
        _bgcolor = null;
        _bordercolor = null;
        _border = null;
        _cellPadding = null;
        _cellSpacing = null;
        _height = null;
        _width = null;
        _cols = null;
        _hspace = null;
        _vspace = null;
        _theme = null;


    }

    /**
     * Sets the align property for the tag
     */

    public void setAlign(String newAlign)
    {
        _align = newAlign;
    }

    /**
     * Sets the background color property for the tag
     */

    public void setBgcolor(String val)
    {
        _bgcolor = val;
    }

    /**
     * Sets the border color property for the tag
     */

    public void setBorder(String val)
    {
        _border = val;
    }

    /**
     * Sets the background color property for the tag
     */

    public void setBordercolor(String val)
    {
        _bordercolor = val;
    }

    /**
     * Sets the cell padding property for the tag
     */

    public void setCellpadding(String val)
    {
        _cellPadding = val;
    }

    /**
     * Sets the cell spacing property for the tag
     */

    public void setCellspacing(String val)
    {
        _cellSpacing = val;
    }

    /**
     * Sets the columns property for the tag
     */

    public void setCols(String val)
    {
        _cols = val;
    }

    /**
     * Set the Data Source the component should be bound to
     */

    public void setDatasource(String val)
    {
        _dataSource = val;
    }

    /**
     * Sets the height property for the tag
     */

    public void setHeight(String val)
    {
        _height = val;
    }

    /**
     * Sets the horizontal space property for the tag
     */

    public void setHspace(String val)
    {
        _hspace = val;
    }

    /**
     * Sets the theme property for the tag
     */

    public void setTheme(String val)
    {
        _theme = val;
    }

    /**
     * Sets the vspace property for the tag
     */

    public void setVspace(String val)
    {
        _vspace = val;
    }

    /**
     * Sets the width property for the tag
     */

    public void setWidth(String val)
    {
        _width = val;
    }
}
