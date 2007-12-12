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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspList.java $
//$Author: Len $
//$Revision: 39 $
//$Modtime: 11/09/04 1:23p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This component will iterate through a datastore and for each row in the datastore, generate html for components contained in it. The list can render in a table (adds formatting) or not (you have to do all the formatting). If the list renders in a table, it can be difided into columns for newpaper style iteration.
 */
public class JspList extends JspContainer implements ImageGenerator {

    public static final String TYPE_NEWSPAPER = "NEWSPAPER";
    public static final String TYPE_STRAIGHT = "STRAIGHT";

    public static final String PAGE_BUTTON_STYLE_RECTANGLE = "RECTANGLE";
    public static final String PAGE_BUTTON_STYLE_OVAL = "OVAL";

    public static final int PAGE_BUTTON_TYPE_SUBMIT = 0;
    public static final int PAGE_BUTTON_TYPE_IMAGE = 1;
    public static final int PAGE_BUTTON_TYPE_TEXT = 2;

    private com.salmonllc.sql.DataStoreBuffer _ds;
    private HtmlComponent _submit;
    private int _fieldColumns;
    private String _fieldName;
    private Vector _propertyExpressions;
    private String _width;
    private String _colWidth;
    private String _fieldType = TYPE_NEWSPAPER;
    private int _border = 0 ;
    private int _cellPadding = 0 ;
    private int _cellSpacing = 0 ;
    private String _borderColor;
    private boolean _useTable = true;

    private boolean _useCache = true;
    private long _cacheKey = 0;
    private Color _textColor = Color.black;
    private Color _backgroundColor = Color.lightGray;
    private Color _topLeftBorder = Color.white;
    private Color _bottomRightBorder = Color.gray;
    private Color _transparentColor = Color.magenta;
    private String _style = PAGE_BUTTON_STYLE_RECTANGLE;
    private int _pageButtonType = PAGE_BUTTON_TYPE_SUBMIT;
    private int _imageHeight = 20;
    private String _imageURL;
    private int _rowsPerPage = 999999;
    private int _firstRowOnPage = 0;
    private int _maxPageButtons = 10;
    private int _firstSubmitButton = 0;
    private boolean _rowPerPageSel = false;
    private int _newRowsPerPage = -1;
    private int _oldRowsPerPage = _rowsPerPage;
    private int _newStartRow = -1;
    private int _newFirstButton = -1;
    private boolean _scrollOnSort;
    private boolean _scroll;
    private String _scrollAnchor;
    private String _fontString;
    private int _fontSize;
    private Font _buttonFont;

    private DataTablePageSelectRenderer _pageSelectRenderer;
    private DataTableRowsPerPageRenderer _rowsPerPageRenderer;
    private String _theme;

    /**
     * JspList constructor comment.
     */
    public JspList(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);
    }

    /**
     * JspList constructor comment.
     */
    public JspList(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        setTheme(theme);
        _imageURL = generateImageURL();
    }

    /**
     * This method will add a property expression to the HtmlDataTable. The property expression will automatically set a property value with the results of the passed expression.
     * @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param expEval java.lang.String The datastore evaluator that evaluates the expression
     * @exception java.lang.NoSuchMethodException The exception description.
     * @exception com.salmonllc.sql.DataStoreException The exception description.
     */
    public void addPropertyExpression(Object comp, String propertyName, DataStoreEvaluator expEval) throws NoSuchMethodException, DataStoreException {
        Class c = comp.getClass();
        Method m[] = c.getMethods();
        Method exe = null;
        String name = "set" + propertyName;
        Class parms[] = null;
        for (int i = 0; i < m.length; i++) {
            if (name.equalsIgnoreCase(m[i].getName())) {
                parms = m[i].getParameterTypes();
                if (parms.length == 1) {
                    exe = m[i];
                    break;
                }
            }
        }
        if (exe == null)
            throw new NoSuchMethodException("Couldn't find a set method for property:" + propertyName);
        ThreeObjectContainer t = new ThreeObjectContainer(comp, exe, expEval);
        if (_propertyExpressions == null)
            _propertyExpressions = new Vector();
        _propertyExpressions.addElement(t);
    }

    /**
     * This method will add a property expression to the HtmlDataTable.
     * The propExpression will be evaluated by the  evaluatePropertyExpression method
     * @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param propExpression com.salmonllc.html.PropertyExpression The instance of PropertyExpression that should do the evaluating.
     * @exception java.lang.NoSuchMethodException The exception description.
     * @exception com.salmonllc.sql.DataStoreException The exception description.
     */
    public void addPropertyExpression(Object comp, String propertyName, DataStoreExpression propExpression) throws NoSuchMethodException, DataStoreException {
        DataStoreEvaluator dse = new DataStoreEvaluator(_ds, propExpression);
        addPropertyExpression(comp, propertyName, dse);
    }

    /**
     * This method will add a property expression to the HtmlDataTable. The property expression will automatically set a property value with the results of the passed expression.
     * @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param expression java.lang.String The datastore expression to evaluate
     * @exception java.lang.NoSuchMethodException The exception description.
     * @exception com.salmonllc.sql.DataStoreException The exception description.
     */
    public void addPropertyExpression(Object comp, String propertyName, String expression) throws NoSuchMethodException, DataStoreException {
        DataStoreEvaluator dse = new DataStoreEvaluator(_ds, expression);
        addPropertyExpression(comp, propertyName, dse);
    }

    /**
     * This method will remove all property expressions from the list
     */
    public void clearPropertyExpressions() {
        _propertyExpressions.setSize(0);
    }

    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(TagWriter t, Vector content) throws java.io.IOException {

        if (content == null)
            return;

        if (getColumns() <= 0)
            setColumns(1);
        boolean useTable = _useTable;
        if (getColumns() > 1)
            useTable = true;

        StringBuffer buffer = new StringBuffer();
        if (!t.getDreamWeaverConv() && !(getPage().getContentType() != null && getPage().getContentType().endsWith(".wml"))) {
            buffer.append("<a name=\"" + getName() + "TableStart\"></a>");
        }

        if (useTable) {
            buffer.append("<table");
            if (getPage().getContentType() != null && getPage().getContentType().endsWith(".wml"))
                buffer.append(" columns=\"" + _fieldColumns + "\"");
            if (_cellPadding != -1) {
                buffer.append(" cellpadding=\"");
                buffer.append(new Integer(_cellPadding).toString());
                buffer.append("\"");
            }
            if (_cellSpacing != -1) {
                buffer.append(" cellspacing=\"");
                buffer.append(new Integer(_cellSpacing).toString());
                buffer.append("\"");
            }
            if (_border != -1) {
                buffer.append(" border=\"");
                buffer.append(new Integer(_border).toString());
                buffer.append("\"");
            }

            if (_borderColor != null) {
                buffer.append(" bordercolor=\"");
                buffer.append(_borderColor);
                buffer.append("\"");
            }
            if (_width != null) {
                buffer.append(" width=\"");
                buffer.append(_width);
                buffer.append("\"");
            }
            buffer.append(">");
            t.print(buffer.toString(), TagWriter.TYPE_BEGIN_TAG);
        } else {
            if (!t.getDreamWeaverConv()&& !(getPage().getContentType() != null && getPage().getContentType().endsWith(".wml")))
                t.print("<a name=\"" + getName() + "TableStart\"></a>", TagWriter.TYPE_BEGIN_TAG);
            else
                t.print("", TagWriter.TYPE_BEGIN_TAG);
        }


        int columns = getColumns();
        int noOfRows = (int) Math.ceil((double) content.size() / (double) columns);


        String width = _colWidth;
        if (width == null)
            width = new Integer(100 / columns).toString() + "%";

        for (int rows = 0; rows < noOfRows; rows++) {
            buffer.setLength(0);
            if (useTable)
                buffer.append("<tr>");
            for (int cols = 0; cols < columns; cols++) {
                if (useTable) {
                    if ((getPage()).getContentType() != null && (getPage()).getContentType().endsWith(".wml"))
                        buffer.append("<td>");
                    else
                        buffer.append("<td valign=\"top\" width=\"" + width + "\">");
                }

                t.print(buffer.toString(), TagWriter.TYPE_INSERTED_CONTENT);
                if (_fieldType.equals(TYPE_NEWSPAPER))
                    t.print(getValidContent(content, (cols * noOfRows) + rows), TagWriter.TYPE_CONTENT);
                else
                    t.print(getValidContent(content, (rows * columns) + cols), TagWriter.TYPE_CONTENT);
                buffer.setLength(0);

                if (useTable)
                    buffer.append("</td>");
            }
            if (useTable)
                buffer.append("</tr>");
            t.print(buffer.toString(), TagWriter.TYPE_INSERTED_CONTENT);
        }

        //paging buttons
        buffer.setLength(0);
        int rowCount = _ds == null ? 0 : _ds.getRowCount();
        int rowsPerPage = _rowsPerPage;
        if (rowCount > rowsPerPage) {
            if (_pageSelectRenderer == null) {
                if (_pageButtonType == PAGE_BUTTON_TYPE_SUBMIT)
                    _pageSelectRenderer = DataTablePageSelectRenderer.getSubmitButtonRenderer();
                else if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE)
                    _pageSelectRenderer = DataTablePageSelectRenderer.getImageButtonRenderer();
                else
                    _pageSelectRenderer = DataTablePageSelectRenderer.getLinkRenderer();
            }
            if (useTable)
                buffer.append("<tr><td valign=\"MIDDLE\" colspan=\"" + columns + "\">");
            int noButtons = (rowCount / rowsPerPage);
            if (rowCount % rowsPerPage > 0)
                noButtons++;
            int page = (_firstRowOnPage / rowsPerPage) + 1;
            buffer.append(_pageSelectRenderer.generateRowSelector(this, _theme, "Page", "of", page, _firstSubmitButton, noButtons, _maxPageButtons, _imageURL));
            buffer.append("</td></tr>");
        }

        if (_rowPerPageSel && rowCount > _oldRowsPerPage) {
            if (_rowsPerPageRenderer == null)
                _rowsPerPageRenderer = DataTableRowsPerPageRenderer.getDefaultRenderer();
            if (useTable)
                buffer.append("<tr><td valign=\"MIDDLE\" colspan=\"" + columns + "\">");
            buffer.append(_rowsPerPageRenderer.generateRowSelector(this, _theme, "Total Items", "Items per page", null, rowsPerPage, rowCount));
            buffer.append("</td></tr>");
        }
        if (useTable)
            buffer.append("</table>");

        t.print(buffer.toString(), TagWriter.TYPE_END_TAG);
        makeEnabledAndVisible();
    }

    /**
     * Gets the border thickness for the table.
     */
    public int getBorder() {
        return _border;
    }

    /**
     * Gets the border color for the table.
     */
    public String getBorderColor() {
        return _borderColor;
    }
    /**
     * Gets the cell padding for the table.
     */
    public int getCellPadding() {
        return _cellPadding;
    }

    /**
     * Gets the cell spacing for the table.
     */
    public int getCellSpacing() {
        return _cellSpacing;
    }

    /**
     * Returns the number of columns used in the list
     */
    public int getColumns() {
        return _fieldColumns;
    }

    /**
     * Returns the DataStoreBuffer used by the list
     */
    public com.salmonllc.sql.DataStoreBuffer getDataStore() {
        return _ds;
    }

    /**
     * Returns the name of the list
     */
    public java.lang.String getName() {
        return _fieldName;
    }

    /**
     * Returns the type of the list, used for sorting columns: TYPE_NEWSPAPER or TYPE_STRAIGHT
     */
    public java.lang.String getType() {
        return _fieldType;
    }

    /**
     * gets whether or not the component will us a table to generate it's html
     */
    public boolean getUseTable() {
        return _useTable;
    }

    private String getValidContent(Vector content, int position) {

        if (position > content.size() - 1) {
            return "";
        }
        return (String) content.elementAt(position);
    }

    private void makeEnabledAndVisible() {
        if (_propertyExpressions == null)
            return;
        //sr 10-15-2000
        int propertyExpressionsSize = _propertyExpressions.size();
        ThreeObjectContainer c = null;
        Object comp = null;
        Method meth = null;
        //
        for (int i = 0; i < propertyExpressionsSize; i++) {
            c = (ThreeObjectContainer) _propertyExpressions.elementAt(i);
            comp = c.getObject1();
            meth = (Method) c.getObject2();
            Object[] res = new Object[1];
            res[0] = new Boolean(true);
            //
            try {
                if (meth.getName().equals("setVisible") || meth.getName().equals("setEnabled"))
                    meth.invoke(comp, res);
            } catch (Exception e) {
                MessageLog.writeErrorMessage("makeEnabledAndVisible()", e, this);
            }
        }
    }

    /**
     * This method will process the parms from a post for every component in the container.
     */
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        String compName = null;
        try {

            if (!getVisible())
                return false;


            int rowCount = _ds.getRowCount();
            for (int j = 0; j < rowCount; j++) {
                for (int i = 0; i < getComponentCount(); i++) {
                    if (getComponentType(i) != TYPE_ROW) {
                        HtmlComponent h = getComponent(i);
                        if (h != null)
                            if (h.processParms(parms, j))
                                _submit = h;
                    }
                }
            }

            int noButtons = rowCount / _rowsPerPage;
            if (rowCount % _rowsPerPage > 0)
                noButtons++;

            Object rpp = parms.get(getFullName() + "_rows_per_page_hidden");
            if (rpp != null) {
                String val = ((String[]) rpp)[0];
                if (val.equals("1")) {
                    rpp = parms.get(getFullName() + "row_per_page_dd");
                    _newRowsPerPage = new Integer(((String[]) rpp)[0]).intValue();
                    _submit = this;
                } else if (!val.equals("-1")) {
                    _newRowsPerPage = new Integer(val).intValue();
                    _submit = this;
                }
            }

            String[] pageSubmitA = (String[]) parms.get(getFullName() + "_page_selector");
            String pageSubmit = "";
            if (pageSubmitA != null)
                pageSubmit = pageSubmitA[0];

            _newStartRow = -1;
            _newFirstButton = -1;
            if (parms.get(getFullName() + "_page_first") != null || parms.get(getFullName() + "_page_first.x") != null || pageSubmit.equals("first")) {
                _newFirstButton = 0;
                _newStartRow = 0;
                _submit = this;
            } else if (parms.get(getFullName() + "_page_prior") != null || parms.get(getFullName() + "_page_prior.x") != null || pageSubmit.equals("prior")) {
                _newFirstButton = _firstSubmitButton - (_maxPageButtons == 0 ? 1 : _maxPageButtons);
                _submit = this;
                if (_newFirstButton < 0)
                    _newFirstButton = 0;
                _newStartRow = _rowsPerPage * (_newFirstButton + (_maxPageButtons == 0 ? 1 : _maxPageButtons) - 1);
            } else if (parms.get(getFullName() + "_page_next") != null || parms.get(getFullName() + "_page_next.x") != null || pageSubmit.equals("next")) {
                _newFirstButton = _firstSubmitButton + (_maxPageButtons == 0 ? 1 : _maxPageButtons);
                _submit = this;
                if ((_newFirstButton + _maxPageButtons) >= noButtons)
                    _newFirstButton = (noButtons - (_maxPageButtons == 0 ? 1 : _maxPageButtons));
                _newStartRow = _rowsPerPage * _newFirstButton;
            } else if (parms.get(getFullName() + "_page_last") != null || parms.get(getFullName() + "_page_last.x") != null || pageSubmit.equals("last")) {
                _submit = this;
                _newFirstButton = (noButtons - (_maxPageButtons == 0 ? 1 : _maxPageButtons));
                _newStartRow = _rowsPerPage * _newFirstButton;
                if (_newStartRow < 0)
                    _newStartRow = 0;
            } else if (!pageSubmit.equals("")) {
                _newStartRow = _rowsPerPage * Integer.parseInt(pageSubmit);
                _submit = this;
            } else {
                for (int i = _firstSubmitButton; (i < noButtons) && ((i - _firstSubmitButton) < _maxPageButtons); i++) {
                    if (parms.get(getFullName() + "_page_" + i) != null || parms.get(getFullName() + "_page_" + i + ".x") != null) {
                        _newStartRow = _rowsPerPage * i;
                        _submit = this;
                        break;
                    }
                }
            }

            //
            if (_submit == null) {
                _scroll = false;
                return false;
            } else {
                _scroll = _scrollOnSort;
                return true;
            }

        } catch (Exception e) {
            MessageLog.writeErrorMessage("processParms for " + compName + "\n", e, this);
            throw (e);
        }
    }

    public void processPropertyExpressions(int row) {
        if (_propertyExpressions == null)
            return;

        ThreeObjectContainer c = null;
        Object comp = null;
        Method meth = null;
        DataStoreEvaluator eval = null;

        //
        int propertyExpressionsSize = _propertyExpressions.size();
        for (int i = 0; i < propertyExpressionsSize; i++) {
            c = (ThreeObjectContainer) _propertyExpressions.elementAt(i);
            comp = c.getObject1();
            meth = (Method) c.getObject2();
            eval = (DataStoreEvaluator) c.getObject3();
            HtmlPage.executePropertyMethod(comp, meth, eval, row);
        }
    }

    /**
     * Sets the border for the table.
     */
    public void setBorder(int border) {
        _border = border;
    }

    /**
     * Sets the border color for the table.
     */
    public void setBorderColor(String value) {
        _borderColor = value;
    }

    /**
     * Sets the cell padding for the table.
     */
    public void setCellPadding(int value) {
        _cellPadding = value;
    }

    /**
     * Sets the cell spacing for the table.
     */
    public void setCellSpacing(int value) {
        _cellSpacing = value;
    }

    /**
     * Sets the number of columns used to present this list
     */
    public void setColumns(int newColumns) {
        _fieldColumns = newColumns;
    }

    /**
     * Sets the width of each column in the list (multicolumn lists only)
     */
    public void setColumnWidth(java.lang.String width) {
        _colWidth = width;
    }

    /**
     * Sets the datastore that the list will iterate through to display its data
     */
    public void setDataStore(com.salmonllc.sql.DataStoreBuffer newDataStore) {
        _ds = newDataStore;
    }

    /**
     * Sets the name of the component
     */
    public void setName(java.lang.String newName) {
        _fieldName = newName;
    }

    /**
     * Sets the type of list for sorting purposes: TYPE_NEWSPAPER or TYPE_STRAIGHT. Newspaper will sort down then across, straight will sort across then down
     */
    public void setType(String newType) {
        if (newType != null && (newType.toUpperCase().equals(TYPE_NEWSPAPER) || newType.toUpperCase().equals(TYPE_STRAIGHT)))
            _fieldType = newType;
    }

    /**
     * Sets whether or not the component will us a table to generate it's html
     */
    public void setUseTable(boolean useTable) {
        _useTable = useTable;
    }

    /**
     * Sets the width of the list
     */
    public void setWidth(java.lang.String width) {
        _width = width;
    }

    /**
     * Sets a new render to draw the HTML for the table page selector
     */
    public void setPageSelectRenderer(DataTablePageSelectRenderer r) {
        _pageSelectRenderer = r;
    }

    /**
     * Sets a new render to draw the HTML for the rows per page selector
     */
    public void setRowsPerPageRenderer(DataTableRowsPerPageRenderer r) {
        _rowsPerPageRenderer = r;
    }

    /**
     * Sets a new render to draw the HTML for the table page selector, given the class name for the renderer
     */
    public void setPageSelectRenderer(String r) {
        if (r != null) {
            if (r.equalsIgnoreCase("null") || r.equalsIgnoreCase("default")) {
                _pageSelectRenderer = null;
                return;
            }
        }

        try {
            Class c = Class.forName(r);
            _pageSelectRenderer = (DataTablePageSelectRenderer) c.newInstance();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("setRowSelectRenderer", e, this);
        }
    }

    /**
     * Sets a new render to draw the HTML for the rows per page selector
     */
    public void setRowsPerPageRenderer(String r) {
        if (r != null) {
            if (r.equalsIgnoreCase("null") || r.equalsIgnoreCase("default")) {
                _rowsPerPageRenderer = null;
                return;
            }
        }
        try {
            Class c = Class.forName(r);
            _rowsPerPageRenderer = (DataTableRowsPerPageRenderer) c.newInstance();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("setRowsPerPageRenderer", e, this);
        }
    }

    /**
     * Returns the first row on the page displayed in the datatable.
     */
    public int getFirstRowOnPage() {
        return _firstRowOnPage;
    }

    /**
     * This method returns the page the row is on.
     */
    public int getPage(int row) {
        return row / _rowsPerPage;
    }

    /**
     * Gets the type of paging buttons for the data table. Valid Types are PAGE_BUTTON_TYPE_SUBMIT, PAGE_BUTTON_TYPE_IMAGE, PAGE_BUTTON_TYPE_TEXT
     **/

    public int getPageButtonType() {
        return _pageButtonType;
    }

    /**
     * This method returns the background color of paging button images
     */
    public Color getPagingButtonBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * This method returns the bottom right border color of paging button images
     */
    public Color getPagingButtonBottomRightColor() {
        return _bottomRightBorder;
    }

    /**
     * This method gets the Display Style for a generated page button image. Valid Values are PAGE_BUTTON_STYLE_RECTANGLE and PAGE_BUTTON_STYLE_OVAL
     */
    public String getPagingButtonDisplayStyle() {
        return _style;
    }

    /**
     * This method returns the font used for page button images
     */
    public Font getPagingButtonFont() {
         if (_buttonFont == null)
            _buttonFont = new Font(_fontString,0,_fontSize);
        return _buttonFont;
    }

    /**
     * This method returns the height used for page button images
     */
    public int getPagingButtonHeight() {
        return _imageHeight;
    }

    /**
     * This method returns the text color of page button images
     */
    public Color getPagingButtonTextColor() {
        return _textColor;
    }

    /**
     * This method gets the number of rows to display on one page of output. A value <= 0 will indicate no limit to the number of rows displayed.
     */
    public int getRowsPerPage() {
        return _rowsPerPage;
    }

    /**
     * Returns true if rows per page selector is visible.
     */
    public boolean getSelRowsPerPage() {
        return _rowPerPageSel;
    }

    public boolean getUseCache() {
        return _useCache;
    }

    /**
     * This method returns true if the specified row is on the current page.
     */
    public boolean isRowOnPage(int row) {
        int pageNo = row / _rowsPerPage;
        int currentPage = _firstRowOnPage / _rowsPerPage;

        return pageNo == currentPage;
    }

    public void generateImage(String img, OutputStream out) throws IOException {
        //create an image to draw
        ImageFactory fact = new ImageFactory();
        Image i = null;

        String source = img;
        if (img.equals("first"))
            source = "<<";
        else if (img.equals("last"))
            source = ">>";
        else if (img.equals("next"))
            source = ">";
        else if (img.equals("prior"))
            source = "<";
        else
            source = new Integer((Integer.parseInt(img) + 1)).toString();

        if (_style.equals(PAGE_BUTTON_STYLE_OVAL))
            i = fact.createOvalButton(_imageHeight, getPagingButtonFont(), source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
        else
            i = fact.createRectangleButton(_imageHeight, getPagingButtonFont(), source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);

        GifEncoder g = new GifEncoder(i, out, true, _transparentColor);
        g.encode();

    }

    /**
     * This method is used by the framework to decide whether or not to generate the image or get it from cache. It should not be called directly.
     */
    public void setCacheKey(long key) {
        _cacheKey = key;
    }

    /**
     * This method sets the first row displayed on the current page.
     */
    public void setFirstRowOnPage(int firstRowOnPage) {
        _firstRowOnPage = firstRowOnPage;
    }

    /**
     * This method sets the maximum number of paging buttons to display.
     */
    public void setMaxPageButtons(int max) {
        _maxPageButtons = max;
    }

    /**
     * Sets the maximum number of paging buttons for the table.
     */
    public void setMaxPagingButtons(int max) {
        _maxPageButtons = max;
    }

    /**
     * This method will scroll to the first row on the current selected page
     */
    public void setPage(int page) {
        _firstRowOnPage = page * _rowsPerPage;
        if (_maxPageButtons == 0)
            _firstSubmitButton = page;
    }

    /**
     * Sets the type of paging buttons for the data table. Valid Types are PAGE_BUTTON_TYPE_SUBMIT, PAGE_BUTTON_TYPE_IMAGE, PAGE_BUTTON_TYPE_TEXT
     **/

    public void setPageButtonType(int type) {
        _pageButtonType = type;
        if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE) ;
        getPage().registerImageGenerator(getFullName(), this);

    }

    /**
     * This method sets the background color for the page button generated image
     */
    public void setPagingButtonBackgroundColor(Color c) {
        _backgroundColor = c;
    }

    /**
     * This method sets the bottom and right border color for the page button generated image
     */
    public void setPagingButtonBottomRightColor(Color c) {
        _bottomRightBorder = c;
    }

    /**
     * This method sets the Display Style for a generated image button. Valid Values are PAGE_BUTTON_STYLE_RECTANGLE and PAGE_BUTTON_STYLE_OVAL
     */
    public void setPagingButtonDisplayStyle(String style) {
        _style = style;
    }

    /**
     * This method sets the font used for dynamically generated page button images.
     */
    public void setPagingButtonFont(Font f) {
        _buttonFont = f;
    }

    /**
     * This method sets the height used for page button images
     */
    public void setPagingButtonHeight(int height) {
        _imageHeight = height;
    }

    /**
     * This method sets the number of rows to display on one page of output. A value <= 0 will indicate no limit to the number of rows displayed.
     */
    public void setRowsPerPage(int rowsPerPage) {
        _rowsPerPage = rowsPerPage;
        _oldRowsPerPage = rowsPerPage;
    }

    /**
     * This method sets the anchor to scroll to when a paging button on the table is clicked. Set it to null for the top of the table.
     */
    public void setScrollAnchor(String anchor) {
        _scrollAnchor = anchor;
    }

    /**
     * Sets whether the list will build scrolling javascript when the user clicks on paging button.
     */
    public void setScrollOnPaging(boolean scroll) {
        _scrollOnSort = scroll;
    }

    /**
     * Sets whether rows per page selector is visible.
     */
    public void setSelRowsPerPage(boolean sel) {
        _rowPerPageSel = sel;
    }


    public long getCacheKey() {
        return _cacheKey;
    }

    /**
     * This method gets the anchor to scroll to when a paging button on the table is clicked. Set it to null for the top of the table.
     */
    public String getScrollAnchor() {
        return _scrollAnchor;
    }

    /**
     * Gets whether the build scrolling javascript when the user clicks on a paging button.
     */
    public boolean getScrollOnPaging() {
        return _scrollOnSort;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        Props prop = getPage().getPageProperties();

        _scrollOnSort = prop.getThemeBooleanProperty(theme, Props.DATA_TABLE_SCROLL_ON_CLICK_SORT, true);
        _maxPageButtons = -1;
        prop.getThemeIntProperty(theme, Props.DATA_TABLE_MAX_PAGE_BUTTONS);
        if (_maxPageButtons <= 0)
            _maxPageButtons = 10;

        // rows per page
        _oldRowsPerPage = _rowsPerPage;

       //properties for paging buttons
        _fontString = prop.getThemeProperty(theme, Props.SUBMIT_IMAGE_FONT_FACE);
        if (_fontString == null)
            _fontString = "Helvetica";
        _fontSize = prop.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_SIZE);
        if (_fontSize == -1)
            _fontSize = 12;

        _imageHeight = prop.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);

        Color c = prop.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TEXT_COLOR);
        if (c != null)
            _textColor = c;

        c = prop.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BACKGROUND_COLOR);
        if (c != null)
            _backgroundColor = c;

        c = prop.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TOPLEFT_BORDER_COLOR);
        if (c != null)
            _topLeftBorder = c;

        c = prop.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BOTTOMRIGHT_BORDER_COLOR);
        if (c != null)
            _bottomRightBorder = c;

        c = prop.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TRANSPARENT_COLOR);
        if (c != null)
            _transparentColor = c;

        String style = prop.getThemeProperty(theme, Props.SUBMIT_IMAGE_STYLE);
        if (style != null) {
            if (style.toUpperCase().equals(PAGE_BUTTON_STYLE_OVAL))
                _style = PAGE_BUTTON_STYLE_OVAL;
            else
                _style = PAGE_BUTTON_STYLE_RECTANGLE;
        }

        String rowSelectRenderer = prop.getThemeProperty(theme, Props.DATA_TABLE_PAGE_SELECT_RENDERER);
        if (rowSelectRenderer != null)
            setPageSelectRenderer(rowSelectRenderer);

        String rowsPerPageRenderer = prop.getThemeProperty(theme, Props.DATA_TABLE_ROWS_PER_PAGE_RENDERER);
        if (rowsPerPageRenderer != null)
            setRowsPerPageRenderer(rowsPerPageRenderer);

        _theme = theme;
    }

    public boolean executeEvent(int eventType) throws Exception {
        if (eventType == HtmlComponent.EVENT_OTHER) {
            for (int i = 0; i < getComponentCount(); i++) {
                HtmlComponent h = getComponent(i);
                if (!h.executeEvent(eventType))
                    return false;
            }
        } else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
            if (_submit == this) {
                if (_newRowsPerPage > -1) {
                    _rowsPerPage = _newRowsPerPage;
                    _firstSubmitButton = 0;
                    _firstRowOnPage = 0;
                    _newRowsPerPage = -1;
                }

                if (_newStartRow > -1)
                    _firstRowOnPage = _newStartRow;
                if (_newFirstButton > -1)
                    _firstSubmitButton = _newFirstButton;

                _submit = null;
                if (_scroll) {
                    if (_scrollAnchor != null)
                        getPage().scrollToItem(_scrollAnchor);
                    else
                        getPage().scrollToItem(getName() + "TableStart");
                    _scroll = false;
                }
            } else {
                boolean retVal = _submit.executeEvent(eventType);
                _submit = null;
                return retVal;
            }
        }
        return true;
    }
}
