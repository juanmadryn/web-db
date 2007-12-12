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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataTableTag.java $
//$Author: Dan $
//$Revision: 50 $
//$Modtime: 11/05/04 11:03a $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.MessageLog;

/**
 * This is a tag used to implement the a Data Table.
 */

public class DataTableTag extends ContainerTag {
    private int _startRow = 0 ;
    private int _endRow = 0 ;
    private StringBuffer _content;
	private String _currentBackgroundColor = null;
	private String _currentStyle = null;
    private String _align;
    private String _bgcolor;
    private String _headingBgColor;
    private String _rowBgColor1;
    private String _rowBgColor2;
    private String _bordercolor;
    private String _border;
    private String _cellPadding;
    private String _cellSpacing;
    private String _height;
    private String _width;
    //   private String _class;
    private String _cols;
    private String _hspace;
    private String _vspace;
    private String _theme;
    
    /**
     * srufle 04-09-2002
     * not being used:
     * private String _sortBy;
     */
    private String _groupBy;
    private String _rowsPerPage;
    private String _clickSort;
    private String _maxPagingButtons;
    private int _lastBreak = -1;
    private int _nextBreak = -1;
    private int _maxCols = 0;
    private int _currentCols;
    private String _rowsPerPageSelector;
    private String _pageSelectorType;
    private String _groupByCont;
    private String _dataSource;
    private String _pageSelectRenderer;
    private String _rowsPerPageRender;

    /**
     * This method is called after the container has been initialized. It is compute the maximum number of columns in the datatable.
     */
    public void afterInit(HtmlComponent comp) {
        ((JspDataTable) comp).setMaxTDColumns(_maxCols);
        super.afterInit(comp);

    }

    /**
     * This method creates the JspDataTable object for this tag.
     */

    public HtmlComponent createComponent() {
        JspDataTable tab = new JspDataTable(getName(), getHelper().getController());
        if (!BaseTagHelper.isEmpty(_theme))
            tab.setTheme(_theme);
        tab.setAlign(_align);
        if (!BaseTagHelper.isEmpty(_border))
            tab.setBorder(BaseTagHelper.stringToInt(_border));
        if (!BaseTagHelper.isEmpty(_cellPadding))
            tab.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
        if (!BaseTagHelper.isEmpty(_cellSpacing))
            tab.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
        if (!BaseTagHelper.isEmpty(_height))
            tab.setHeight(_height);
        if (!BaseTagHelper.isEmpty(_width))
            tab.setWidth(_width);
        if (!BaseTagHelper.isEmpty(_cols))
            tab.setCols(BaseTagHelper.stringToInt(_cols));
        if (!BaseTagHelper.isEmpty(_hspace))
            tab.setHSpace(BaseTagHelper.stringToInt(_hspace));
        if (!BaseTagHelper.isEmpty(_vspace))
            tab.setVSpace(BaseTagHelper.stringToInt(_vspace));
        if (!BaseTagHelper.isEmpty(_rowsPerPage))
            tab.setRowsPerPage(BaseTagHelper.stringToInt(_rowsPerPage));
        if (!BaseTagHelper.isEmpty(_clickSort))
            tab.setClickSort(BaseTagHelper.stringToBoolean(_clickSort));
        if (!BaseTagHelper.isEmpty(_groupBy))
            tab.setGroupBy(_groupBy);
        if (!BaseTagHelper.isEmpty(_rowsPerPageSelector))
            tab.setSelRowsPerPage(BaseTagHelper.stringToBoolean(_rowsPerPageSelector, true));
        if (!BaseTagHelper.isEmpty(_bgcolor))
            tab.setBackgroundColor(_bgcolor);
        if (!BaseTagHelper.isEmpty(_rowBgColor1))
            tab.setRowBackgroundColor1(_rowBgColor1);
        if (!BaseTagHelper.isEmpty(_rowBgColor2))
            tab.setRowBackgroundColor2(_rowBgColor2);
        if (!BaseTagHelper.isEmpty(_headingBgColor))
            tab.setHeadingBackgroundColor(_headingBgColor);
        if (!BaseTagHelper.isEmpty(getClassname()))
            tab.setClassName(getClassname());

        if (!BaseTagHelper.isEmpty(_pageSelectorType)) {
            _pageSelectorType = _pageSelectorType.toUpperCase();
            if (_pageSelectorType.equals("TEXT"))
                tab.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_TEXT);
            else if (_pageSelectorType.equals("IMAGE"))
                tab.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_IMAGE);
            else if (_pageSelectorType.equals("SCROLLBAR"))
                tab.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_SCROLLBAR);
            else
                tab.setPageButtonType(JspDataTable.PAGE_BUTTON_TYPE_SUBMIT);
        }

        if (! BaseTagHelper.isEmpty(_pageSelectRenderer))
            tab.setPageSelectRenderer(_pageSelectRenderer);

        if (! BaseTagHelper.isEmpty(_rowsPerPageRender))
            tab.setRowsPerPageRenderer(_rowsPerPageRender);

        if (!BaseTagHelper.isEmpty(_dataSource))
            tab.setDataSource(_dataSource);

        _maxCols = 0;
        _currentCols = 0;
        return tab;
    }

    private void flipColor() {
        JspDataTable tab = ((JspDataTable) getHelper().getComponent());
        _currentBackgroundColor = tab.getCurrentRowBackgroundColor();
        _currentStyle = tab.getCurrentRowStyleClassName();
        tab.flipColor();
    }

    /**
     * This method generates the Html for the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException {
        JspDataTable tab = (JspDataTable) getHelper().getController().getComponent(getName());
        TagWriter w = getTagWriter();
        w.setWriter(p);
        tab.generateHTML(w, _content.toString());
    }

    /**
     * Gets the alignment attribute for the tag
     */
    public java.lang.String getAlign() {
        return _align;
    }

    /**
     * Gets the bgcolor attribute for the tag
     */

    public String getBgcolor() {
        return _bgcolor;
    }

    /**
     * Gets the border attribute for the tag
     */

    public String getBorder() {
        return _border;
    }

    /**
     * gets the background color property for the tag
     */

    public String getBordercolor() {
        return _bordercolor;
    }

    /**
     * Gets the cell padding attribute for the tag
     */

    public String getCellpadding() {
        return _cellPadding;
    }

    /**
     * Gets the cell spacing attribute for the tag
     */

    public String getCellspacing() {
        return _cellSpacing;
    }

    /**
     * Gets the clicksort attribute for the tag
     */

    public String getClicksort() {
        return _clickSort;
    }

    /**
     * Gets the cols attribute for the tag
     */

    public String getCols() {
        return _cols;
    }

    /**
     * Gets the background color attribute for the tag
     */

    public String getCurrentBackgroundColor() {
        return _currentBackgroundColor;
    }

	/**
	 * Gets the background color attribute for the tag
	 */

	public String getCurrentStyle() {
		return _currentStyle;
	}
    /**
     * Get the Data Source the component should be bound to
     */

    public String getDatasource() {
        return _dataSource;
    }

    /**
     * This method returns the current end row for the datatable
     */
    public int getEndRow() {
        return _endRow;
    }

    /**
     * Gets the group by attribute for the tag
     */

    public String getGroupby() {
        return _groupBy;
    }

    /**
     * Gets the group by continued attribute for the tag
     */

    public String getGroupbycont() {
        return _groupByCont;
    }

    /**
     * Gets the heading background color attribute for the tag
     */

    public String getHeadingbgcolor() {
        return _headingBgColor;
    }

    /**
     * Gets the height attribute for the tag
     */

    public String getHeight() {
        return _height;
    }

    /**
     * Gets the horizontal space attribute for the tag
     */

    public String getHspace() {
        return _hspace;
    }

    /**
     * Gets the maximum number of paging buttons for the datatable
     */

    public java.lang.String getMaxpagingbuttons() {
        return _maxPagingButtons;
    }

    /**
     * Gets the the type of page selector for the tag
     */

    public String getPageselectortype() {
        return _pageSelectorType;
    }

    /**
     * Gets the row background color stripe 1  attribute for the tag
     */

    public String getRowbgcolor1() {
        return _rowBgColor1;
    }

    /**
     * Gets the row background color stripe 2 attribute for the tag
     */

    public String getRowbgcolor2() {
        return _rowBgColor2;
    }

    /**
     * Gets the rows per page  attribute for the tag
     */

    public String getRowsperpage() {
        return _rowsPerPage;
    }

    /**
     * Gets the rows per page selector attribute for the tag
     */

    public String getRowsperpageselector() {
        return _rowsPerPageSelector;
    }

    /**
     * This method returns the current start row for the datatable
     */
    public int getStartRow() {
        return _startRow;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_CUSTOM;
    }

    /**
     * Gets the theme  attribute for the tag
     */

    public String getTheme() {
        return _theme;
    }

    /**
     * Gets the vertical space attribute for the tag
     */

    public String getVspace() {
        return _vspace;
    }

    /**
     * Gets the width attribute for the tag
     */

    public String getWidth() {
        return _width;
    }

    /**
     * Override this method for tags that contain NestedTags within to return true
     */
    public boolean hasNestedTags() {
        return true;
    }

    /**
     * Called by the td tag to count the number of columns in the datatable
     */
    void incrementColCounter(int count) {
        _currentCols += count;
    }

    /**
     * This method will step through a row of the data table each time it is called and controls the flow through the sub tags
     */

    public boolean incrementMode() {
        boolean retVal = false;
        try {
            JspDataTable comp = (JspDataTable) getHelper().getComponent();
            DataStoreBuffer ds = comp.getDataStoreBuffer();
            //If the rowcount on the latest retrieve is less than rowsperpage  set page to zero
            //So datatabel is not on invalid page from the last retrieve
            if (ds != null) {
                int rowCount = ds.getRowCount();
                int firstRow = comp.getFirstRowOnPage();
                //fc 06/11/03: Made changes to fix a bug when last row is deleted which is the first row on the current page
                // that it does not go to the previous page.
                if (rowCount <= firstRow) {
                    comp.setPage(comp.getPage(rowCount-1));
                    ds.gotoRow(rowCount-1);
                    _startRow = _endRow = (rowCount-comp.getRowsPerPage())<0?0:rowCount-comp.getRowsPerPage();
                    comp.setFirstRowOnPage(_startRow);
                    _lastBreak = -1;
                }
            }
            	
            if (getMode() == MODE_HEADER) {
            	JspController cont = getHelper().getController();
           		writeTHeadAndTBody("<THEAD id=\""+ comp.getFullName() + "-thead\">");
                writeContent();
                writeTHeadAndTBody("</THEAD>");
                if (ds != null && ds.getRowCount() > 0) {
                	if ((comp.getPageButtonType() == JspDataTable.PAGE_BUTTON_TYPE_SCROLLBAR) && !(cont.getBrowserType()==HtmlPage.BROWSER_MICROSOFT))
                		writeTHeadAndTBody("<TBODY id=\"" + comp.getFullName() + "-tbody\" style=\"max-height:" + comp.getHeight() + ";overflow: scroll\">");                	
                	else	
                		writeTHeadAndTBody("<TBODY>");
                    if (isNextRowControlBreak(comp, ds))
                        setMode(MODE_GROUP_HEADER);
                    else
                        setMode(MODE_ROW);
                    comp.processPropertyExpressions(_endRow);
                } else {
                     setMode(MODE_FOOTER);
                }
                flipColor();
                retVal = true;
            } else if (getMode() == MODE_FOOTER) {
            	writeTHeadAndTBody("<TFOOT>");
            	if (writeContent())
                    flipColor();
                retVal = false;
            } else if (getMode() == MODE_GROUP_HEADER) {
                writeContent();
                retVal = true;
                flipColor();
                comp.resetCounters();
                setMode(MODE_ROW);
            } else if (getMode() == MODE_GROUP_FOOTER) {
                if (writeContent())
                    flipColor();
                retVal = true;
                comp.resetCounters();
                int lastRow = comp.getFirstRowOnPage() + comp.getRowsPerPage() - 1;
                if (ds == null || _endRow >= (ds.getRowCount() - 1) || _endRow >= lastRow) {
                    setMode(MODE_FOOTER);
                    _startRow = 0;
                    _endRow = ds.getRowCount() - 1;
                } else {
                    setMode(MODE_GROUP_HEADER);
                    _endRow++;
                    _startRow = _endRow;
                }
                comp.processPropertyExpressions(_endRow);
            } else if (getMode() == MODE_ROW) {
                flipColor();
                comp.resetCounters();
                int lastRow = comp.getFirstRowOnPage() + comp.getRowsPerPage() - 1;

                if (ds == null || _endRow >= (ds.getRowCount() - 1) || _endRow >= lastRow) {
                	if ((_endRow >= lastRow) && alwaysShowGroupFooter(comp,ds)) {
                        setMode(MODE_GROUP_FOOTER);
                        _startRow = _lastBreak;
                        _endRow = _nextBreak;
                	}	
                    else if (isNextRowControlBreak(comp, ds)) {
                        setMode(MODE_GROUP_FOOTER);
                        _startRow = _lastBreak;
                        _endRow = ds.getRowCount() - 1;
                    } else {
                        writeContent();
                        writeTHeadAndTBody("</TBODY>");
                        setMode(MODE_FOOTER);
                        _startRow = 0;
                        _endRow = ds.getRowCount() - 1;
                    }
                    retVal = true;
                } else {
                    writeContent();
                    retVal = true;
                    if (isNextRowControlBreak(comp, ds)) {
                        setMode(MODE_GROUP_FOOTER);
                        _startRow = _lastBreak;
                        _lastBreak = _endRow + 1;
                    } else {
                        _endRow++;
                        _startRow = _endRow;
                    }
                    comp.processPropertyExpressions(_endRow);
                }
            }

            comp.setLastRenderedRow(_startRow);
            return retVal;
        } catch (Exception e) {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return false;
        }
    }

    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode() {
        JspDataTable tab = ((JspDataTable) getHelper().getComponent());
        if (tab.getRowHighlighter() != null)
        	tab.getRowHighlighter().resetForGenerate();
        setMode(MODE_HEADER);

        _lastBreak = -1;
        _nextBreak = -1;
        _startRow = tab.getFirstRowOnPage();
        _endRow = _startRow;
        if (_content != null)
            _content.setLength(0);
        else
            _content = new StringBuffer();

        tab.resetCounters();
        tab.initColor();
        tab.setLastRenderedRow(_startRow);
        _currentBackgroundColor = tab.getHeadingBackgroundColor();
        _currentStyle = tab.getHeadingStyleClassName();
    }

    private boolean alwaysShowGroupFooter(JspDataTable tab, DataStoreBuffer ds) {
        int breakCols[] = tab.getBreakColumns();
        if (breakCols == null)
            return false;
        else if (tab.getAlwaysShowGroupFooter()) {
            if (_lastBreak == -1) {
                int row = _endRow;
                while (row > -1 && ds.compareRows(row, _endRow, breakCols) == 0)
                    row--;
                _lastBreak = row + 1;
            }
            int row = _endRow;
            while (row < ds.getRowCount() && ds.compareRows(row, _endRow, breakCols) == 0)
                row++;
            _nextBreak = row;
            return true;
        }	
        else
        	return false;
    }	
    
    private boolean isNextRowControlBreak(JspDataTable tab, DataStoreBuffer ds) {
        int breakCols[] = tab.getBreakColumns();
        if (breakCols == null)
            return false;

        if (_lastBreak == -1) {
            int row = _endRow;
            while (row > -1 && ds.compareRows(row, _endRow, breakCols) == 0)
                row--;

            _lastBreak = row + 1;
            showGroupByCont(_lastBreak < _startRow);
            return true;
        }

        int nextRow = _endRow + 1;
        if (nextRow >= ds.getRowCount()) {
            showGroupByCont(false);
            return true;
        }
        boolean ret = (ds.compareRows(_lastBreak, nextRow, breakCols) != 0);
        if (ret)
            showGroupByCont(false);

        return ret;
    }

    /**
     * Releases any resources used by the tag.
     */

    public void release() {
        super.release();
        if (_content != null)
            _content.setLength(0);
        _currentBackgroundColor = null;
        _currentStyle=null;

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
        /**
         * srufle 04-09-2002
         * not being used:
         * _sortBy = null;
         */

        _groupBy = null;
        _rowsPerPage = null;
        _clickSort = null;
        _maxPagingButtons = null;
        _lastBreak = -1;
        _rowsPerPageSelector = null;
        _pageSelectorType = null;
        _groupByCont = null;
        _rowsPerPageRender = null;
        _pageSelectRenderer = null;

    }

    /**
     * Clears the column counter
     */
    void resetColCounter() {
        if (_currentCols > _maxCols)
            _maxCols = _currentCols;

        _currentCols = 0;

    }

    /**
     * Sets the alignment property for the tag
     */
    public void setAlign(String newAlign) {
        _align = newAlign;
    }

    /**
     * Sets the background color property for the tag
     */

    public void setBgcolor(String val) {
        _bgcolor = val;
    }

    /**
     * Sets the border property for the tag
     */

    public void setBorder(String val) {
        _border = val;
    }

    /**
     * Sets the background color property for the tag
     */

    public void setBordercolor(String val) {
        _bordercolor = val;
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
     * Sets the click sort property for the tag
     */

    public void setClicksort(String val) {
        _clickSort = val;
    }

    /**
     * Sets the numer of columns property for the tag
     */

    public void setCols(String val) {
        _cols = val;
    }

    /**
     * Set the Data Source the component should be bound to
     */

    public void setDatasource(String val) {
        _dataSource = val;
    }

    /**
     * Sets the group by attribute for the tag
     */
    public void setGroupby(String val) {
        _groupBy = val;
    }

    /**
     * Sets the group by continued property for the tag
     */

    public void setGroupbycont(String val) {
        _groupByCont = val;
    }

    /**
     * Sets the heading background color property for the tag
     */

    public void setHeadingbgcolor(String val) {
        _headingBgColor = val;
    }

    /**
     * Sets the height property for the tag
     */

    public void setHeight(String val) {
        _height = val;
    }

    /**
     * Sets the horizontal space property for the tag
     */

    public void setHspace(String val) {
        _hspace = val;
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
     * Sets the row background color stripe 1 property for the tag
     */

    public void setRowbgcolor1(String val) {
        _rowBgColor1 = val;
    }

    /**
     * Sets the row background color stripe 2 property for the tag
     */

    public void setRowbgcolor2(String val) {
        _rowBgColor2 = val;
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
     * Sets the vertical space property for the tag
     */

    public void setVspace(String val) {
        _vspace = val;
    }

    /**
     * Sets the width property for the tag
     */
    public void setWidth(String val) {
        _width = val;
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

    private void showGroupByCont(boolean show) {
        if (_groupByCont != null) {
            HtmlComponent comp = getHelper().getController().getComponent(_groupByCont);
            comp.setVisible(show);
        }
    }

    /**
     * Appends the content of the sub tags to a string buffer to be written later.
     */
    private boolean writeContent() throws IOException {
        String c = getBodyContentData(true);
        _content.append(c);
        return (c.length() > 0);
    }
    
    /**
     * Used for THEAD, TBODY and TFOOT tags. They mess up the Dreamweaver translation so we only print them to the browser
     */
    private void writeTHeadAndTBody(String content) throws IOException {
    	JspDataTable comp=(JspDataTable)getHelper().getComponent();
    	if (comp != null && comp.getGenTHeadAndBody()) {
    		if (!getTagWriter().getDreamWeaverConv())
    			_content.append(content);
    	}	
    }
}
