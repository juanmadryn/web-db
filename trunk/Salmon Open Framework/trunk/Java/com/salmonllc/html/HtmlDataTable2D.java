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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlDataTable2D.java $
//$Author: Fred $
//$Revision: 26 $
//$Modtime: 10/02/03 12:26p $
/////////////////////////

import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.io.*;
import java.util.*;

/**
 * This component will generate an in a fashon similar to the HTMLDataTable parent. HtmlDataTables can only generate one row per band, however. The 2D version allows multiple rows per band.
 */

public class HtmlDataTable2D extends HtmlDataTable {

    private Vector2D _headingComponents2D = new Vector2D();
    private Vector2D _rowComponents2D = new Vector2D();
    private Vector2D _footerComponents2D = new Vector2D();
    private Vector2D _controlBreakFooterComponents2D = new Vector2D();
    private Vector2D _controlBreakHeadingComponents2D = new Vector2D();

    public HtmlDataTable2D(String name, DataStoreBuffer d, HtmlPage p) {
        this(name, d, null, p);
    }

    public HtmlDataTable2D(String name, DataStoreBuffer d, String theme, HtmlPage p) {
        super(name, d, theme, p);
    }

    public boolean executeEvent(int eventType) throws Exception {
        if (eventType == HtmlComponent.EVENT_OTHER) {
            for (int i = 0; i < _headingComponents2D.size(); i++) {
                TwoObjectContainer t = (TwoObjectContainer) _headingComponents2D.elementAt(i);
                if (t != null) {
                    HtmlComponent h = (HtmlComponent) t.getObject1();
                    if (h != null)
                        if (!h.executeEvent(eventType))
                            return false;
                }

            }
            for (int i = 0; i < _rowComponents2D.size(); i++) {
                TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(i);
                if (t != null) {
                    HtmlComponent h = (HtmlComponent) t.getObject1();
                    if (h != null)
                        if (!h.executeEvent(eventType))
                            return false;
                }
            }
            for (int i = 0; i < _footerComponents2D.size(); i++) {
                TwoObjectContainer t = (TwoObjectContainer) _footerComponents2D.elementAt(i);
                if (t != null) {
                    HtmlComponent h = (HtmlComponent) t.getObject1();
                    if (h != null)
                        if (!h.executeEvent(eventType))
                            return false;
                }

            }
        } else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
            boolean retVal = true;
            if (_submit == this) {
                if (_newRowsPerPage > -1) {
                    _rowsPerPage = _newRowsPerPage;
                    _firstSubmitButton = 0;
                    setFirstRowOnPage(0);
                    _newRowsPerPage = -1;
                }
                if (_newStartRow > -1)
                    setFirstRowOnPage(_newStartRow);
                if (_newFirstButton > -1)
                    _firstSubmitButton = _newFirstButton;
                if (_clickSortColumn > -1) {
                    TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(_clickSortColumn);
                    HtmlComponent sortComp = findSortComponent((HtmlComponent) t.getObject1());
                    if (sortComp != null)
                        sortOnComponent(sortComp, _clickSortColumn);
                }

            } else
                retVal = _submit.executeEvent(eventType);
            _submit = null;
            return retVal;
        }

        return true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible()) {
            _didSubmit = false;
            return;
        }

        int rowsPerPage = _rowsPerPage;
        boolean rowPerPageSel = _rowPerPageSel;
        if (getParent() instanceof HtmlInlineFrame && getPage().getUseIFrames()) {
            rowsPerPage = 100000;
            rowPerPageSel = false;
        }
        _newStartRow = -1;

        if (_center)
            p.print("<CENTER>");

        p.println("<A NAME=\"" + getFullName() + "TableStart\"></A>");

        String tableHeading = "<TABLE";

        if (_containerWidth > -1) {
            tableHeading += " WIDTH=\"" + _containerWidth;
            if (_sizeOption == SIZE_PERCENT) {
                tableHeading += "%";
            }
            tableHeading += "\"";
        }

        if (_border > -1)
            tableHeading += " BORDER=\"" + _border + "\"";

        if (_bgColor != null) {
            if (!_bgColor.equals("")) {
                tableHeading += " BGCOLOR=\"" + _bgColor + "\"";
            }
        }

        if (_cellPadding > -1)
            tableHeading += " CELLPADDING=\"" + _cellPadding + "\"";

        if (_cellPadding > -1)
            tableHeading += " CELLSPACING=\"" + _cellSpacing + "\"";


        if (_align != null) {
            if (_align.equals(ALIGN_LEFT) || _align.equals(ALIGN_RIGHT) || _align.equals(ALIGN_CENTER))
                tableHeading += " align=\"" + _align + "\"";
        }

        tableHeading += ">";

        p.println(tableHeading);

        if (_clickSort) {
            p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "_SORTITEM\" VALUE=\"-1\">");
            p.print("<SCRIPT>");
            p.print("function " + getFullName() + "_clickSort(i) {");
            p.print(getFormString() + getFullName() + "_SORTITEM.value = i;");
            p.print(getFormString() + "submit();");
            p.print("}");
            p.print("</SCRIPT>");
        }

        StringBuffer td = new StringBuffer();

        int maxSize = _headingComponents2D.getColumnCount();
        if (_rowComponents2D.getColumnCount() > maxSize)
            maxSize = _rowComponents2D.getColumnCount();
        if (_footerComponents2D.getColumnCount() > maxSize)
            maxSize = _footerComponents2D.getColumnCount();

        //do the heading components
        if (_headingComponents2D.size() > 0)
            generateHtmlForBand(_headingComponents2D, p, _defaultHeadingBackground, maxSize, _clickSort, "TD");

        //get the data
        _ds.waitForRetrieve();
        if (_firstRowOnPage >= _ds.getRowCount()) {
            _firstRowOnPage = 0;
            _firstSubmitButton = 0;
        }

        //find the first row for control breaks (if used)
        int lastBreak = -1;
        if (_breakColumns != null) {
            lastBreak = 0;
            if (_firstRowOnPage != 0) {
                lastBreak = 0;
                for (int i = _firstRowOnPage; i >= 0; i--) {
                    if (_ds.compareRows(_firstRowOnPage, i, _breakColumns) != 0) {
                        lastBreak = i + 1;
                        break;
                    }
                }
            }
        }

        //do the row components
        String backgroundColor = _defaultRowBackground2;

        //p.println("<TR>");
        for (int j = _firstRowOnPage; (j < _ds.getRowCount()) && (j < (_firstRowOnPage + rowsPerPage)); j++) {
            backgroundColor = getBackgroundColor(backgroundColor);

            //check for control breaks
            if (_breakColumns != null) {
                if (_contMessage != null)
                    _contMessage.setVisible(false);

                if (j == _firstRowOnPage) {
                    if (_controlBreakHeadingComponents2D.size() > 0) {
                        if (_contMessage != null && lastBreak != _firstRowOnPage)
                            _contMessage.setVisible(true);

                        generateHtmlForBand(_controlBreakHeadingComponents2D, p, backgroundColor, maxSize, false, lastBreak, lastBreak, "TD");
                        backgroundColor = getBackgroundColor(backgroundColor);
                    }
                } else if (_ds.compareRows(lastBreak, j, _breakColumns) != 0) {
                    if (_controlBreakFooterComponents2D.size() > 0) {
                        generateHtmlForBand(_controlBreakFooterComponents2D, p, backgroundColor, maxSize, false, lastBreak, j - 1, "TD");
                        backgroundColor = getBackgroundColor(backgroundColor);
                    }
                    if (_controlBreakHeadingComponents2D.size() > 0) {
                        generateHtmlForBand(_controlBreakHeadingComponents2D, p, backgroundColor, maxSize, false, j, j, "TD");
                        backgroundColor = getBackgroundColor(backgroundColor);
                    }
                    lastBreak = j;
                }
            }

            processPropertyExpressions(j);

            //do the rows and columns
            for (int row = 0; row < _rowComponents2D.getRowCount(); row++) {
                p.println("<TR>");
                for (int col = 0; col < _rowComponents2D.getColumnCount(); col++) {
                    int addTo = 0;
                    td.setLength(0);
                    td.append("<TD");

                    int width = getColumnWidth(col);
                    if (width > 0) {
                        td.append(" WIDTH=\"" + width);
                        if (_sizeOption == SIZE_PERCENT)
                            td.append("%");
                        td.append("\"");
                    }

                    TwoObjectContainer cont = (TwoObjectContainer) _rowComponents2D.elementAt(row, col);

                    HtmlComponent comp = null;
                    HtmlTableCellProperties props = null;

                    if (cont != null) {
                        comp = (HtmlComponent) cont.getObject1();
                        props = (HtmlTableCellProperties) cont.getObject2();
                    }

                    if (props != null) {
                        if (props.getAlign() != null)
                            if (!props.getAlign().equals(ALIGN_NONE))
                                td.append(" ALIGN=\"" + props.getAlign() + "\"");

                        if (props.getVertAlign() != null)
                            if (!props.getVertAlign().equals(VALIGN_NONE))
                                td.append(" VALIGN=\"" + props.getVertAlign() + "\"");

                        if (props.getBackgroundColor() != null)
                            if (!props.getBackgroundColor().equals(""))
                                backgroundColor = props.getBackgroundColor();

                        if (!props.getWrap())
                            td.append(" NOWRAP");

                        if (props.getColumnSpan() > 1) {
                            td.append(" COLSPAN=\"" + props.getColumnSpan() + "\"");
                            addTo = props.getColumnSpan() - 1;
                        }
                    }

                    if (backgroundColor != null)
                        if (!backgroundColor.equals(""))
                            td.append(" BGCOLOR=\"" + backgroundColor + "\"");

                    td.append('>');
                    p.println(td.toString());

                    if (comp != null)
                        comp.generateHTML(p, j);
                    else
                        p.println("&nbsp;");

                    p.println("</TD>");
                    col += addTo;
                }
                p.println("</TR>");
            }
        }

        int nextRow = _firstRowOnPage + rowsPerPage;
        if (_breakColumns != null) {
            if (nextRow >= _ds.getRowCount()) {
                if (_controlBreakFooterComponents2D.size() > 0) {
                    backgroundColor = getBackgroundColor(backgroundColor);
                    generateHtmlForBand(_controlBreakFooterComponents2D, p, backgroundColor, maxSize, false, lastBreak, _ds.getRowCount() - 1, "TD");
                }
            } else if (_ds.compareRows(lastBreak, nextRow, _breakColumns) != 0) {
                if (_controlBreakFooterComponents2D.size() > 0) {
                    backgroundColor = getBackgroundColor(backgroundColor);
                    generateHtmlForBand(_controlBreakFooterComponents2D, p, backgroundColor, maxSize, false, lastBreak, nextRow - 1, "TD");
                }
            }
        }

        if (_footerComponents2D.size() > 0 && nextRow >= _ds.getRowCount()) {
            backgroundColor = getBackgroundColor(backgroundColor);
            generateHtmlForBand(_footerComponents2D, p, backgroundColor, maxSize, false, "TD");
        }

        int rowCount = _ds.getRowCount();
        if (rowCount > rowsPerPage) {
            if (_pageSelectRenderer == null) {
                if (_pageButtonType == PAGE_BUTTON_TYPE_SUBMIT)
                    _pageSelectRenderer = DataTablePageSelectRenderer.getSubmitButtonRenderer();
                else if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE)
                    _pageSelectRenderer = DataTablePageSelectRenderer.getImageButtonRenderer();
                else
                    _pageSelectRenderer = DataTablePageSelectRenderer.getLinkRenderer();
            }
            backgroundColor = getBackgroundColor(backgroundColor);
            p.println("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + _rowComponents2D.size() + "\"" + " BGCOLOR=\"" + backgroundColor + "\">");
            int noButtons = (rowCount / rowsPerPage);
            if (rowCount % rowsPerPage > 0)
                noButtons++;
            int page = (_firstRowOnPage / rowsPerPage) + 1;
            p.println(_pageSelectRenderer.generateRowSelector(this, _theme, "Page", "of", page, _firstSubmitButton, noButtons, _maxPageButtons, _imageURL));
            p.println("</TD></TR>");
        }

        if (rowPerPageSel && rowCount > 10) {
            if (_rowsPerPageRenderer == null)
                _rowsPerPageRenderer = DataTableRowsPerPageRenderer.getDefaultRenderer();
            backgroundColor = getBackgroundColor(backgroundColor);
            p.println("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + _rowComponents2D.size() + "\"" + " BGCOLOR=\"" + backgroundColor + "\">");
            p.println(_rowsPerPageRenderer.generateRowSelector(this, _theme, "Total Rows", "Rows displayed per page", _summaryRowText, rowsPerPage, rowCount));
            p.println("</TD></TR>");
        }

        p.println("</TABLE>");

        if (_center)
            p.print("</CENTER>");

        if (_scroll) {
            _scroll = false;
        }

        _didSubmit = false;
    }

    private void generateHtmlForBand(Vector2D components, PrintWriter p, String bgColor, int maxSize, boolean clickSort, int startRow, int endRow, String cellType) throws Exception {

        processPropertyExpressions(endRow);

        StringBuffer td = new StringBuffer();
        for (int row = 0; row < components.getRowCount(); row++) {
            p.println("<TR>");
            int addTo = 0;
            for (int col = 0; col < maxSize; col++) {
                td.setLength(0);
                td.append("<");
                td.append(cellType);

                int width = getColumnWidth(col);
                if (width > 0) {
                    td.append(" WIDTH=\"" + width);
                    if (_sizeOption == SIZE_PERCENT)
                        td.append("%");
                    td.append("\"");
                }

                TwoObjectContainer cont = null;
                if (col < components.getColumnCount())
                    cont = (TwoObjectContainer) components.elementAt(row, col);

                HtmlComponent comp = null;
                HtmlTableCellProperties props = null;

                if (cont != null) {
                    comp = (HtmlComponent) cont.getObject1();
                    props = (HtmlTableCellProperties) cont.getObject2();
                }

                String backgroundColor = bgColor;
                if (props != null) {
                    if (props.getAlign() != null)
                        if (!props.getAlign().equals(ALIGN_NONE))
                            td.append(" ALIGN=\"" + props.getAlign() + "\"");

                    if (props.getVertAlign() != null)
                        if (!props.getVertAlign().equals(VALIGN_NONE))
                            td.append(" VALIGN=\"" + props.getVertAlign() + "\"");

                    if (props.getBackgroundColor() != null)
                        if (!props.getBackgroundColor().equals(""))
                            backgroundColor = props.getBackgroundColor();

                    if (!props.getWrap())
                        td.append(" NOWRAP");

                    if (props.getColumnSpan() > 1) {
                        td.append(" COLSPAN=\"" + props.getColumnSpan() + "\"");
                        addTo = props.getColumnSpan() - 1;
                    }
                }

                if (backgroundColor != null)
                    if (!backgroundColor.equals(""))
                        td.append(" BGCOLOR=\"" + backgroundColor + "\"");
                td.append('>');

                p.println(td.toString());
                if (comp != null) {
                    boolean underLine = false;
                    if (col < _rowComponents2D.getColumnCount() && row < _rowComponents2D.getRowCount())
                        if (clickSort && _rowComponents2D.elementAt(row, col) != null)
                            underLine = true;

                    if (underLine)
                        p.print("<A HREF=\"javascript:" + getFullName() + "_clickSort(" + components.indexAt(row, col) + ");\">");
                    if (startRow > -1)
                        comp.generateHTML(p, startRow, endRow);
                    else
                        comp.generateHTML(p, -1);

                    if (underLine)
                        p.print("</A>");
                } else
                    p.println("&nbsp;");
                p.print("</");
                p.print(cellType);
                p.println(">");
                col += addTo;
                addTo = 0;
            }
            p.println("</TR>");
        }

    }

    private void generateHtmlForBand(Vector2D components, PrintWriter p, String bgColor, int maxSize, boolean clickSort, String cellType) throws Exception {
        generateHtmlForBand(components, p, bgColor, maxSize, clickSort, -1, -1, cellType);
    }

    public void generateInitialHTML(PrintWriter p) throws Exception {
        if (!_visible)
            return;

        generateInitialHtmlForBand(_headingComponents2D, p);
        generateInitialHtmlForBand(_rowComponents2D, p);
        generateInitialHtmlForBand(_footerComponents2D, p);
        generateInitialHtmlForBand(_controlBreakFooterComponents2D, p);
        generateInitialHtmlForBand(_controlBreakHeadingComponents2D, p);

    }

    public void generateInitialHtmlForBand(Vector2D components, PrintWriter p) throws Exception {

        for (int i = 0; i < components.size(); i++) {
            TwoObjectContainer cont = (TwoObjectContainer) components.elementAt(i);

            if (cont != null && cont.getObject1() != null)
                ((HtmlComponent) cont.getObject1()).generateInitialHTML(p);

        }
    }

    /**
     * This method returns all the elements in the container.
     */
    public Enumeration getComponents() {
        Vector comp = new Vector();

        for (int i = 0; i < _headingComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _headingComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    comp.addElement(h);
            }
        }

        for (int i = 0; i < _rowComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    comp.addElement(h);

            }
        }

        for (int i = 0; i < _footerComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _footerComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    comp.addElement(h);

            }
        }

        for (int i = 0; i < _controlBreakHeadingComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _controlBreakHeadingComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    comp.addElement(h);

            }
        }

        for (int i = 0; i < _controlBreakFooterComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _controlBreakFooterComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    comp.addElement(h);

            }
        }

        return comp.elements();
    }

    /**
     * This method returns the heading component at the specified position or null if none is found.
     */
    public HtmlComponent getHeadingComponentAt(int row, int col) {
        TwoObjectContainer t = (TwoObjectContainer) _headingComponents2D.elementAt(row, col);
        if (t == null)
            return null;

        return (HtmlComponent) t.getObject1();

    }

    /**
     * This method returns the row component at the specified position or null if non is found.
     */
    public HtmlComponent getRowComponentAt(int row, int col) {
        TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(row, col);
        if (t == null)
            return null;

        return (HtmlComponent) t.getObject1();

    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        if (!getVisible())
            return false;

        for (int i = 0; i < _headingComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _headingComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    if (h.processParms(parms, -1))
                        _submit = h;
            }
        }

        for (int j = _firstRowOnPage; (j < _ds.getRowCount()) && (j < (_firstRowOnPage + _rowsPerPage)); j++) {
            processPropertyExpressions(j);
            for (int i = 0; i < _rowComponents2D.size(); i++) {
                TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(i);
                if (t != null) {
                    HtmlComponent h = (HtmlComponent) t.getObject1();
                    if (h != null)
                        if (h.processParms(parms, j))
                            _submit = h;
                }
            }
        }

        for (int i = 0; i < _footerComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _footerComponents2D.elementAt(i);
            if (t != null) {
                HtmlComponent h = (HtmlComponent) t.getObject1();
                if (h != null)
                    if (h.processParms(parms, -1))
                        _submit = h;
            }
        }

        for (int j = _firstRowOnPage; (j < _ds.getRowCount()) && (j < (_firstRowOnPage + _rowsPerPage)); j++) {
            for (int i = 0; i < _controlBreakHeadingComponents2D.size(); i++) {
                TwoObjectContainer t = (TwoObjectContainer) _controlBreakHeadingComponents2D.elementAt(i);
                if (t != null) {
                    HtmlComponent h = (HtmlComponent) t.getObject1();
                    if (h != null)
                        if (h.processParms(parms, j))
                            _submit = h;
                }
            }
        }

        int noButtons = _ds.getRowCount() / _rowsPerPage;
        if (_ds.getRowCount() % _rowsPerPage > 0)
            noButtons++;

        Object rpp = parms.get(getFullName() + "_rows_per_page_hidden");
        if (rpp != null) {
            String val = ((String[]) rpp)[0];
            if (val.equals("1")) {
                rpp = parms.get(getFullName() + "row_per_page_dd");
                _newRowsPerPage = new Integer(((String[]) rpp)[0]).intValue();
                _submit = this;
            } else if (!val.equals("-1")) {
                _newRowsPerPage = new Integer(val.trim()).intValue();
                _submit = this;
            }
        }


        _newStartRow = -1;
        _newFirstButton = -1;
        _clickSortColumn = -1;
        Object s1 = parms.get(getFullName() + "_SORTITEM");
        String sort = "-1";
        if (s1 != null)
            sort = ((String[]) s1)[0];

        if (!sort.equals("-1")) {
            if (_breakColumns == null) {
                _newFirstButton = 0;
                _newStartRow = 0;
            }
            _clickSortColumn = Integer.parseInt(sort);
            _submit = this;
        }
        if (parms.get(getFullName() + "_page_first") != null || parms.get(getFullName() + "_page_first.x") != null) {
            _newFirstButton = 0;
            _newStartRow = 0;
            _submit = this;
        } else if (parms.get(getFullName() + "_page_prior") != null || parms.get(getFullName() + "_page_prior.x") != null) {
            _newFirstButton = _firstSubmitButton - (_maxPageButtons == 0 ? 1 : _maxPageButtons);
            _submit = this;
            if (_newFirstButton < 0)
                _newFirstButton = 0;
            _newStartRow = _rowsPerPage * (_newFirstButton + (_maxPageButtons == 0 ? 1 : _maxPageButtons) - 1);
        } else if (parms.get(getFullName() + "_page_next") != null || parms.get(getFullName() + "_page_next.x") != null) {
            _newFirstButton = _firstSubmitButton + (_maxPageButtons == 0 ? 1 : _maxPageButtons);
            _submit = this;
            if ((_newFirstButton + _maxPageButtons) >= noButtons)
                _newFirstButton = (noButtons - (_maxPageButtons == 0 ? 1 : _maxPageButtons));
            _newStartRow = _rowsPerPage * _newFirstButton;
        } else if (parms.get(getFullName() + "_page_last") != null || parms.get(getFullName() + "_page_last.x") != null) {
            _submit = this;
            _newFirstButton = (noButtons - (_maxPageButtons == 0 ? 1 : _maxPageButtons));
            _newStartRow = _ds.getRowCount() - _rowsPerPage;
            if (_newStartRow < 0)
                _newStartRow = 0;
        } else {
            for (int i = _firstSubmitButton; (i < noButtons) && ((i - _firstSubmitButton) < _maxPageButtons); i++) {
                if (parms.get(getFullName() + "_page_" + i) != null || parms.get(getFullName() + "_page_" + i + ".x") != null) {
                    _newStartRow = _rowsPerPage * i;
                    _submit = this;
                    break;
                }
            }
        }

        if (_submit == null) {
            _scroll = false;
            return false;
        } else {
            _scroll = _scrollOnSort;
            if (_scrollOnSort)
                getPage().scrollToItem(getFullName() + "TableStart");
            _didSubmit = true;
            return true;
        }
    }

    /**
     * Removes an html component from the heading container.
     * @param comp The component to remove
     */
    public void removeHeadingComponent(HtmlComponent comp) {
        for (int i = 0; i < _headingComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _headingComponents2D.elementAt(i);
            HtmlComponent h = (HtmlComponent) t.getObject1();
            if (h == comp) {
                _headingComponents2D.setElementAt(i, null);
                return;
            }
        }
    }

    /**
     * Removes an html component from the row container.
     * @param comp The component to remove
     */
    public void removeRowComponent(HtmlComponent comp) {
        for (int i = 0; i < _rowComponents2D.size(); i++) {
            TwoObjectContainer t = (TwoObjectContainer) _rowComponents2D.elementAt(i);
            HtmlComponent h = (HtmlComponent) t.getObject1();
            if (h == comp) {
                _rowComponents2D.setElementAt(i, null);
                return;
            }
        }
    }

    /**
     * This method will remove all  Control Break Footer Components from the DataTable.
     */
    public void resetControlBreakFooterComponents() {
        _controlBreakFooterComponents2D.removeAll();
    }

    /**
     * This method will remove all Control Break Heading Components from the DataTable.
     */
    public void resetControlBreakHeadingComponents() {
        _controlBreakHeadingComponents2D.removeAll();
    }

    /**
     * This method will remove all Footer Components from the DataTable.
     */
    public void resetFooterComponents() {
        _footerComponents2D.removeAll();
    }

    /**
     * This method will remove all Heading Components from the DataTable.
     */
    public void resetHeadingComponents() {
        _headingComponents2D.removeAll();
    }

    /**
     * This method will remove all Row Components from the DataTable.
     */
    public void resetRowComponents() {
        _rowComponents2D.removeAll();
    }

    /**
     * Sets a control break footer component at a particular row position in the table.
     */
    public void setControlBreakFooterComponentAt(int row, int column, HtmlComponent comp) {
        setControlBreakFooterComponentAt(row, column, comp, null);
    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakFooterComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
        if (column < 0)
            return;

        TwoObjectContainer cont = new TwoObjectContainer(comp, props);

        if (column >= _controlBreakFooterComponents2D.getColumnCount())
            _controlBreakFooterComponents2D.addColumns((column - _controlBreakFooterComponents2D.getColumnCount()) + 1);
        if (row >= _controlBreakFooterComponents2D.getRowCount())
            _controlBreakFooterComponents2D.addRows((row - _controlBreakFooterComponents2D.getRowCount()) + 1);

        _controlBreakFooterComponents2D.setElementAt(row, column, cont);

        comp.setParent(this);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakFooterComponentAt(int column, HtmlComponent comp) {
        setControlBreakFooterComponentAt(0, column, comp, null);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakFooterComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
        setControlBreakFooterComponentAt(0, column, comp, props);

    }

    /**
     * Sets all the control break footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties you will have to use setControlBreakFooterComponentAt(int,HtmlComponent,HtmlTableCellProperties)
     */
    public void setControlBreakFooterComponents(HtmlComponent comp[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setControlBreakFooterComponentAt(row, col, comp[row][col], null);
        }
    }

    /**
     * Sets all the control break footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakFooterComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setControlBreakFooterComponentAt(row, col, comp[row][col], props[row][col]);
        }
    }

    /**
     * Sets all the control break footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakFooterComponents(HtmlComponent comp[]) {
        for (int col = 0; col < comp.length; col++)
            setControlBreakFooterComponentAt(0, col, comp[col], null);
    }

    /**
     * Sets all the control break footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakFooterComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
        for (int col = 0; col < comp.length; col++)
            setControlBreakFooterComponentAt(0, col, comp[col], props[col]);
    }

    /**
     * Sets a control break heading component at a particular row position in the table.
     */
    public void setControlBreakHeadingComponentAt(int row, int column, HtmlComponent comp) {
        setControlBreakHeadingComponentAt(row, column, comp, null);
    }

    /**
     * Sets a control break heading component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakHeadingComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
        TwoObjectContainer cont = new TwoObjectContainer(comp, props);

        if (column >= _controlBreakHeadingComponents2D.getColumnCount())
            _controlBreakHeadingComponents2D.addColumns((column - _controlBreakHeadingComponents2D.getColumnCount()) + 1);
        if (row >= _controlBreakHeadingComponents2D.getRowCount())
            _controlBreakHeadingComponents2D.addRows((row - _controlBreakHeadingComponents2D.getRowCount()) + 1);

        _controlBreakHeadingComponents2D.setElementAt(row, column, cont);

        comp.setParent(this);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakHeadingComponentAt(int column, HtmlComponent comp) {
        setControlBreakHeadingComponentAt(0, column, comp, null);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setControlBreakHeadingComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
        setControlBreakHeadingComponentAt(0, column, comp, props);

    }

    /**
     * Sets all the control break heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties you will have to use setControlBreakHeadingComponentAt(int,HtmlComponent,HtmlTableCellProperties)
     */
    public void setControlBreakHeadingComponents(HtmlComponent comp[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setControlBreakHeadingComponentAt(row, col, comp[row][col], null);
        }
    }

    /**
     * Sets all the control break heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakHeadingComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setControlBreakHeadingComponentAt(row, col, comp[row][col], props[row][col]);
        }
    }

    /**
     * Sets all the control break heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakHeadingComponents(HtmlComponent comp[]) {
        for (int col = 0; col < comp.length; col++)
            setControlBreakHeadingComponentAt(0, col, comp[col], null);
    }

    /**
     * Sets all the control break heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setControlBreakHeadingComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
        for (int col = 0; col < comp.length; col++)
            setControlBreakHeadingComponentAt(0, col, comp[col], props[col]);
    }

    /**
     * Sets a footer component at a particular row position in the table.
     */
    public void setFooterComponentAt(int row, int column, HtmlComponent comp) {
        setFooterComponentAt(row, column, comp, null);
    }

    /**
     * Sets a footer component at a particular row position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setFooterComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
        TwoObjectContainer cont = new TwoObjectContainer(comp, props);

        if (column >= _footerComponents2D.getColumnCount())
            _footerComponents2D.addColumns((column - _footerComponents2D.getColumnCount()) + 1);
        if (row >= _footerComponents2D.getRowCount())
            _footerComponents2D.addRows((row - _footerComponents2D.getRowCount()) + 1);


        _footerComponents2D.setElementAt(row, column, cont);

        if (comp != null)
            comp.setParent(this);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setFooterComponentAt(int column, HtmlComponent comp) {
        setFooterComponentAt(0, column, comp, null);

    }

    /**
     * Sets a footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setFooterComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
        setFooterComponentAt(0, column, comp, props);

    }

    /**
     * Sets all the footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties you will have to use setFooterComponentAt(int,HtmlComponent,HtmlTableCellProperties)
     */
    public void setFooterComponents(HtmlComponent comp[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setFooterComponentAt(row, col, comp[row][col], null);
        }
    }

    /**
     * Sets all the footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setFooterComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setFooterComponentAt(row, col, comp[row][col], props[row][col]);
        }
    }

    /**
     * Sets all the footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setFooterComponents(HtmlComponent comp[]) {
        for (int col = 0; col < comp.length; col++)
            setFooterComponentAt(0, col, comp[col], null);
    }

    /**
     * Sets all the footer components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setFooterComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
        for (int col = 0; col < comp.length; col++)
            setFooterComponentAt(0, col, comp[col], props[col]);
    }

    /**
     * Sets a heading component at a particular row position in the table.
     */
    public void setHeadingComponentAt(int row, int column, HtmlComponent comp) {
        setHeadingComponentAt(row, column, comp, null);
    }

    /**
     * Sets a heading component at a particular row position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setHeadingComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
        TwoObjectContainer cont = new TwoObjectContainer(comp, props);

        if (column >= _headingComponents2D.getColumnCount())
            _headingComponents2D.addColumns((column - _headingComponents2D.getColumnCount()) + 1);
        if (row >= _headingComponents2D.getRowCount())
            _headingComponents2D.addRows((row - _headingComponents2D.getRowCount()) + 1);

        _headingComponents2D.setElementAt(row, column, cont);

        if (comp != null)
            comp.setParent(this);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setHeadingComponentAt(int column, HtmlComponent comp) {
        setHeadingComponentAt(0, column, comp, null);

    }

    /**
     * Sets a heading component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setHeadingComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
        setHeadingComponentAt(0, column, comp, props);

    }

    /**
     * Sets all the heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties you wil have to use setHeadingComponentAt(int,HtmlComponent,HtmlTableCellProperties)
     */

    public void setHeadingComponents(HtmlComponent comp[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setHeadingComponentAt(row, col, comp[row][col], null);
        }
    }

    /**
     * Sets all the heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */

    public void setHeadingComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setHeadingComponentAt(row, col, comp[row][col], props[row][col]);
        }

    }

    /**
     * Sets all the heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setHeadingComponents(HtmlComponent comp[]) {
        for (int col = 0; col < comp.length; col++)
            setHeadingComponentAt(0, col, comp[col], null);
    }

    /**
     * Sets all the heading components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setHeadingComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
        for (int col = 0; col < comp.length; col++)
            setHeadingComponentAt(0, col, comp[col], props[col]);
    }

    /**
     * Sets a row component at a particular row position in the table.
     */
    public void setRowComponentAt(int row, int column, HtmlComponent comp) {
        setRowComponentAt(row, column, comp, null);
    }

    /**
     * Sets a row component at a particular row position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setRowComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
        TwoObjectContainer cont = new TwoObjectContainer(comp, props);

        if (column >= _rowComponents2D.getColumnCount())
            _rowComponents2D.addColumns((column - _rowComponents2D.getColumnCount()) + 1);
        if (row >= _rowComponents2D.getRowCount())
            _rowComponents2D.addRows((row - _rowComponents2D.getRowCount()) + 1);

        _rowComponents2D.setElementAt(row, column, cont);

        if (comp != null)
            comp.setParent(this);

    }

    /**
     * Sets a control break footer component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setRowComponentAt(int column, HtmlComponent comp) {
        setRowComponentAt(0, column, comp, null);

    }

    /**
     * Sets a row component at a particular column position in the table. The cell in the table will use properties specified in the props argument.
     */
    public void setRowComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
        setRowComponentAt(0, column, comp, props);

    }

    /**
     * Sets a row component at a particular row position in the table.
     */
    public void setRowComponents(HtmlComponent comp[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setRowComponentAt(row, col, comp[row][col], null);
        }
    }

    /**
     * Sets a row component at a particular row position in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setRowComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
        for (int row = 0; row < comp.length; row++) {
            for (int col = 0; col < comp[row].length; col++)
                setRowComponentAt(row, col, comp[row][col], props[row][col]);
        }
    }

    /**
     * Sets all the row components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setRowComponents(HtmlComponent comp[]) {
        for (int col = 0; col < comp.length; col++)
            setRowComponentAt(0, col, comp[col], null);
    }

    /**
     * Sets all the row components on a particular row in the table.
     * If you want to use HtmlTableCellProperties pass an array of HtmlTableCellProperties
     * with the ones that do not need cell props it as null.
     */
    public void setRowComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
        for (int col = 0; col < comp.length; col++)
            setRowComponentAt(0, col, comp[col], props[col]);
    }
}
