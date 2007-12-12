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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ListTag.java $
//$Author: Len $
//$Revision: 25 $
//$Modtime: 11/09/04 1:28p $
/////////////////////////

import java.io.IOException;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.*;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.MessageLog;

/**
 * This is a tag used to implement the a List .
 */

public class ListTag extends ContainerTag {
    public static final int PAGE_BUTTON_TYPE_SUBMIT = 0;
    public static final int PAGE_BUTTON_TYPE_IMAGE = 1;
    public static final int PAGE_BUTTON_TYPE_TEXT = 2;

    private Vector _content;
    private int _row = 0;
    private String _dataSource;
    private String _columns;
    private String _width;
    private String _colWidth;
    private String _type;
    private String _border;
    private String _borderColor;
    private String _cellPadding;
    private String _cellSpacing;
    private String _useTable;

    private String _theme;
    private String _rowsPerPage;
    private String _rowsPerPageSelector;
    private String _pageSelectorType;
    private String _pageSelectRenderer;
    private String _rowsPerPageRender;
    private String _maxPagingButtons;
    /**
     * This method creates the JspDataTable object for this tag.
     */

    public HtmlComponent createComponent() {
        JspList list = new JspList(getName(), getHelper().getController());
        if (!BaseTagHelper.isEmpty(_theme))
            list.setTheme(_theme);
        list.setColumns(BaseTagHelper.stringToInt(getColumns()));
        list.setName(getName());
        list.setWidth(_width);
        list.setType(_type);
        list.setColumnWidth(_colWidth);
        list.setDataSource(_dataSource);
        list.setBorder(BaseTagHelper.stringToInt(_border));
        list.setBorderColor(_borderColor);
        list.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
        list.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
        list.setUseTable(BaseTagHelper.stringToBoolean(_useTable, true));
        if (!BaseTagHelper.isEmpty(_rowsPerPage))
            list.setRowsPerPage(BaseTagHelper.stringToInt(_rowsPerPage));
        if (!BaseTagHelper.isEmpty(_rowsPerPageSelector))
            list.setSelRowsPerPage(BaseTagHelper.stringToBoolean(_rowsPerPageSelector, false));
        if (!BaseTagHelper.isEmpty(_maxPagingButtons))
            list.setMaxPageButtons(BaseTagHelper.stringToInt(_maxPagingButtons));

        if (!BaseTagHelper.isEmpty(_pageSelectorType)) {
            _pageSelectorType = _pageSelectorType.toUpperCase();
            if (_pageSelectorType.equals("TEXT"))
                list.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_TEXT);
            else if (_pageSelectorType.equals("IMAGE"))
                list.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_IMAGE);
            else
                list.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_SUBMIT);
        }

        if (! BaseTagHelper.isEmpty(_pageSelectRenderer)) {
            list.setPageSelectRenderer(_pageSelectRenderer);
        }

        if (! BaseTagHelper.isEmpty(_rowsPerPageRender))
            list.setRowsPerPageRenderer(_rowsPerPageRender);
        return list;
    }

    /**
     * This method generates the Html for the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException {
        JspList list = (JspList) getHelper().getController().getComponent(getName());
        TagWriter w = getTagWriter();
        w.setWriter(p);
        list.generateHTML(w, _content);
    }

    /**
     * gets the border property for the tag
     */

    public String getBorder() {
        return _border;
    }

    /**
     * gets the border color property for the tag
     */

    public String getBordercolor() {
        return _borderColor;
    }
    /**
     * gets the cell padding property for the tag
     */

    public String getCellpadding() {
        return _cellPadding;
    }

    /**
     * gets the cell spacing property for the tag
     */

    public String getCellspacing() {
        return _cellSpacing;
    }

    /**
     * Returns the number of columns used by the list
     */
    public java.lang.String getColumns() {
        return _columns;
    }

    /**
     * Get the Data Source the component should be bound to
     */

    public String getDatasource() {
        return _dataSource;
    }

    /**
     * Returns the row that the list is currently on
     */
    public int getRow() {
        return _row;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_CUSTOM;
    }

    /**
     * Returns the type of list NEWSPAPER or STRAIGHT.
     */
    public java.lang.String getType() {
        return _type;
    }

    /**
     * This method will step through a row of the data table each time it is called and controls the flow through the sub tags
     */

    public boolean incrementMode() {
        boolean retVal = false;
        try {

            JspList comp = (JspList) getHelper().getComponent();
            int lastRow = comp.getFirstRowOnPage() + comp.getRowsPerPage();

            DataStoreBuffer ds = comp.getDataStore();
            if (ds == null && getTagWriter().getDreamWeaverConv()) {
           	   writeContent();
           	   return false;
            }	
            else if (ds != null && ds.getRowCount() > 0) {
                writeContent();
                if (ds != null) {
                    _row++;
                    comp.processPropertyExpressions(_row);
                    return (_row < ds.getRowCount() && _row < lastRow);
                }
            }
            else
                clearBodyContent();


            return retVal;

        } catch (Exception e) {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return retVal;
        }
    }

    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode() {
        JspList list = ((JspList) getHelper().getComponent());
        _row = list.getFirstRowOnPage();
        DataStoreBuffer ds = list.getDataStore();
        if (ds != null) {
           int rowCount = ds.getRowCount();
           int firstRow = list.getFirstRowOnPage();
           if (rowCount < firstRow) {
             list.setPage(0);
             ds.gotoFirst();
             _row = 0;
           }
        }
        list.processPropertyExpressions(_row);
        _content = new Vector();
    }

    /**
     * Releases any resources used by the tag.
     */

    public void release() {
        super.release();
        if (_content != null)
            _content = null;
        _width = null;
        _colWidth = null;
        _border = null;
        _borderColor = null;
        _type = null;
        _cellPadding = null;
        _cellSpacing = null;
        _theme =  null;
        _rowsPerPage =  null;
        _rowsPerPageSelector =  null;
        _pageSelectorType =  null;
        _pageSelectRenderer =  null;
        _rowsPerPageRender =  null;
        _maxPagingButtons =  null;
    }

    /**
     * Sets the border color property for the tag
     */

    public void setBorder(String val) {
        _border = val;
    }

    /**
     * Sets the border color property for the tag
     */

    public void setBordercolor(String val) {
        _borderColor = val;
    }
    /**
     * Sets the cell padding property for the tag
     */

    public void setCellpadding(String val) {
        _cellPadding = val;
    }

    /**
     * Sets the cell spacing property for the tag
     */

    public void setCellspacing(String val) {
        _cellSpacing = val;
    }

    /**
     * Sets the number of columns the tag will use
     */
    public void setColumns(java.lang.String new_columns) {
        _columns = new_columns;
    }

    /**
     * Sets the column width for each column
     */
    public void setColumnwidth(java.lang.String val) {
        _colWidth = val;
    }

    /**
     * Set the Data Source the component should be bound to
     */

    public void setDatasource(String val) {
        _dataSource = val;
    }

    /**
     * Sets the sort type for the tag NEWSPAPER or STRAIGHT
     */
    public void setType(java.lang.String new_type) {
        _type = new_type;
    }

    /**
     * Sets the cell spacing property for the tag
     */

    public void setUsetable(String val) {
        _useTable = val;
    }

    /**
     * Sets the width of the tag
     */
    public void setWidth(java.lang.String val) {
        _width = val;
    }

    /**
     * Appends the content of the sub tags to a string buffer to be written later.
     */
    private boolean writeContent() throws IOException {
        String c = getBodyContentData(true);
        if (_content != null)
	        _content.add(c);
        return (c.length() > 0);
    }

        /**
     * Sets the rows per page property for the tag
     */

    public void setRowsperpage(String val) {
        _rowsPerPage = val;
    }

    /**
     * Sets the rows per page selector for the tag
     */

    public void setRowsperpageselector(String val) {
        _rowsPerPageSelector = val;
    }

    /**
     * Sets the theme property for the tag
     */

    public void setTheme(String val) {
        _theme = val;
    }

        /**
     * Sets the mamimum number of paging buttons for the tag
     */

    public void setMaxpagingbuttons(java.lang.String newMaxPagingButtons) {
        _maxPagingButtons = newMaxPagingButtons;
    }

    /**
     * Sets the page selector property for the tag
     */

    public void setPageselectortype(String val) {
        _pageSelectorType = val;
    }

     /**
     * Page select renderer
     */
    public void setPageselectrenderer(String val) {
        _pageSelectRenderer = val;
    }

    /**
     * Rows per page renderer
     */
    public void setRowsperpagerenderer(String val) {
        _rowsPerPageRender = val;
    }
}
