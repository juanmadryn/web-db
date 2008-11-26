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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspDataTable.java $
//$Author: Dan $
//$Revision: 65 $
//$Modtime: 11/05/04 11:06a $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This object is used to render tabular data in a JSP Page
 */
public class JspDataTable extends JspContainer implements ImageGenerator {
    public static final String PAGE_BUTTON_STYLE_RECTANGLE = "RECTANGLE";
    public static final String PAGE_BUTTON_STYLE_OVAL = "OVAL";

    public static final int PAGE_BUTTON_TYPE_SUBMIT = 0;
    public static final int PAGE_BUTTON_TYPE_IMAGE = 1;
    public static final int PAGE_BUTTON_TYPE_TEXT = 2;
    public static final int PAGE_BUTTON_TYPE_SCROLLBAR = 3;

    private DataStoreBuffer _ds;
    private HtmlComponent _submit;

    private int _nextTDHeaderNo;
    private int _nextTDRowNo;
    private int _nextTDFooterNo;
    private int _nextTDGroupHeaderNo;
    private int _nextTDGroupFooterNo;

    private int _nextTRHeaderNo;
    private int _nextTRRowNo;
    private int _nextTRFooterNo;
    private int _nextTRGroupHeaderNo;
    private int _nextTRGroupFooterNo;

    private String _bgcolor;
    private String _bordercolor;
    private String _defaultHeadingBackground;
    private String _defaultRowBackground1;
    private String _defaultRowBackground2;
    private String _theme;

    private int _breakColumns[];
    private HtmlComponent _clickSortComponent = null;
    private HtmlComponent _sortComponent = null;
    private int _sortDir = DataStore.SORT_DES;
    private int _sortColumn = -1;
    private int _baseSortColumn = -1;
    private int _baseSortDir;
    private Vector _propertyExpressions;
    private String _align = null;
    private int _border = -1;
    private int _cellPadding = -1;
    private int _cellSpacing = -1;
    private int _cols = -1;
    private int _hSpace = -1;
    private int _vSpace = -1;
    private String _height;
    private String _width;

    private boolean _scrollOnSort;
    private boolean _didSubmit;
    private boolean _scroll;

    private int _maxTDCols = 0;

    private String _groupBy;
    private boolean _clickSort;
    private boolean _color1 = true;

    private int _rowsPerPage = 10;
    private int _firstRowOnPage = 0;
    private int _maxPageButtons = 10;
    private int _firstSubmitButton = 0;
    private boolean _rowPerPageSel = true;

    private int _newRowsPerPage = -1;
    private int _oldRowsPerPage = _rowsPerPage;
    private int _newStartRow = -1;
    private int _newFirstButton = -1;

    private boolean _useCache = true;
    private long _cacheKey = 0;
    private Font _buttonFont;
    private String _fontString;
    private int _fontSize;
    private Color _textColor = Color.black;
    private Color _backgroundColor = Color.lightGray;
    private Color _topLeftBorder = Color.white;
    private Color _bottomRightBorder = Color.gray;
    private Color _transparentColor = Color.magenta;
    private String _style = PAGE_BUTTON_STYLE_RECTANGLE;
    private int _pageButtonType = PAGE_BUTTON_TYPE_SUBMIT;
    private int _imageHeight = 20;
    private String _imageURL;
    private String _scrollAnchor;
    private DataTablePageSelectRenderer _pageSelectRenderer;
    private DataTableRowsPerPageRenderer _rowsPerPageRenderer;

	private String _defaultRowStyleClassName1;
	private String _defaultRowStyleClassName2;
	private String _defaultHeadingStyleClassName;
	
    /*Claudio Pi (12-29-2002) Added, the following 5 instance varialbes for i18n of datatable labels*/
    private String _pageLabel = "Page";
    private String _pageOfLabel = "of";
    private String _rowsPerPageLabel = "Rows displayed per page";
    private String _totalRowsLabel = "Total rows";
    private boolean _updateLocale = true;
    private boolean _genTHeadAndBody = true;
    private HtmlRowHighlighter _rowHighlighter=null;
    private boolean _alwaysShowGroupFooter=false;
	    
    private int _lastRenderedRow=-1;
    
    public JspDataTable(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);
    }

    public JspDataTable(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        setTheme(theme);
        _imageURL = generateImageURL();
    }

    /**
     * The propExpression will be evaluated by the  evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
     * @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param expEval DataStoreEvaluator The datastore evaluator that evaluates the expression
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
     * The propExpression will be evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
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
     * The propExpression will be evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
     @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param expression java.lang.String The datastore expression to evaluate
     * @exception java.lang.NoSuchMethodException The exception description.
     * @exception com.salmonllc.sql.DataStoreException The exception description.
     */
    public void addPropertyExpression(Object comp, String propertyName, String expression) throws NoSuchMethodException, DataStoreException {
        DataStoreEvaluator dse = new DataStoreEvaluator(_ds, expression);
        addPropertyExpression(comp, propertyName, dse);
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

                if (_clickSortComponent != null) {
                    sortOnComponent(_clickSortComponent);
                    _clickSortComponent = null;
                }
                _submit = null;
                if (_scroll) {
                    if (_scrollAnchor != null)
                        getPage().scrollToItem(_scrollAnchor);
                    else {
                    	boolean didScroll = false;
						JspForm form = JspForm.findParentForm(this);
						if (form != null) {
							if (form.isScrollPositionSet()) {
								form.scrollToLastPosition();
								didScroll=true;
							}
						}
                    	if (! didScroll)
                        	getPage().scrollToItem(getName() + "TableStart");
                    }
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

    /**
     * This method will find a component suitable for sorting (an html for component attached to a datastore) inside the passed component.
     */
    public HtmlComponent findSortComponent(HtmlComponent sortComp) {
        if (sortComp == null)
            return null;
        else if (sortComp instanceof HtmlFormComponent) {
            if (((HtmlFormComponent) sortComp).getColumnNumber() != -1)
                return sortComp;
        } else if (sortComp instanceof HtmlText) {
            if (((HtmlText) sortComp).getExpressionEvaluator() != null)
                return sortComp;
        } else if (sortComp instanceof HtmlContainer) {
            Enumeration e = ((HtmlContainer) sortComp).getComponents();
            while (e.hasMoreElements()) {
                sortComp = (HtmlComponent) e.nextElement();
                if (sortComp != null)
                    return findSortComponent(sortComp);
            }
        } else if (sortComp instanceof JspContainer) {
            int count = ((JspContainer) sortComp).getComponentCount();
            if (count == 0)
                return null;
            else {
                for (int i = 0; i < count; i++) {
                    HtmlComponent comp = findSortComponent(((JspContainer) sortComp).getComponent(i));
                    if (comp != null)
                        return comp;
                }
            }
        }
        return null;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public void flipColor() {
        _color1 = (!_color1);
    }

    /**
     * Generates the HTML for the component. This should not be called directly.
     */
    public void generateHTML(TagWriter t, String content) throws java.io.IOException {
        _didSubmit = false;
        /*Claudio Pi (12-29-2002) Added for i18n of data table labels*/
        processLocaleInfo();
        StringBuffer sb = new StringBuffer();
        if (!t.getDreamWeaverConv()) {
            sb.append("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "ClickSortHidden\">");
            sb.append("<SCRIPT language=\"javascript\">");
            sb.append(" function " + getFullName() + "ClickSort(cell){");
            sb.append(getFormString() + getFullName() + "ClickSortHidden.value=cell;");
            JspForm form = JspForm.findParentForm(this);
            if (form != null)
            	sb.append(form.getSubmitScript());
            else
          		sb.append(getFormString() + "submit();");
            sb.append(" }");
            sb.append("</SCRIPT>");
            sb.append("<A NAME=\"" + getName() + "TableStart\"></A>");
        }

        boolean writeScrollDiv=_pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR && getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT && getPage().getBrowserVersion() > 4;
        if (writeScrollDiv) {
       		sb.append("<style>thead#" + getFullName() + "-thead td {position:relative;top: expression(document.getElementById(\"" + getFullName() + "-div\").scrollTop-2);}</style>"); 
			sb.append("<div id=\"" + getFullName() + "-div\" style=\"height:" + _height +";overflow: auto\">"); 		
        }
        
        sb.append("<TABLE");

		if (_class != null)
 			 sb.append(" CLASS=\"" + _class + "\"");

        if (_border != -1)
            sb.append(" BORDER=\"" + _border + "\"");

        if (_bordercolor != null)
            sb.append(" BORDERCOLOR=\"" + _bordercolor + "\"");

        if (_cellPadding != -1)
            sb.append(" CELLPADDING=\"" + _cellPadding + "\"");

        if (_cellSpacing != -1)
            sb.append(" CELLSPACING=\"" + _cellSpacing + "\"");

        if (_bgcolor != null)
            sb.append(" BGCOLOR=\"" + _bgcolor + "\"");

        if (_height != null && _pageButtonType != PAGE_BUTTON_TYPE_SCROLLBAR)
            sb.append(" HEIGHT=\"" + _height + "\"");

        if (_width != null)
            sb.append(" WIDTH=\"" + _width + "\"");

        if (_align != null)
            sb.append(" ALIGN=\"" + _align + "\"");

        if (_cols != -1)
            sb.append(" COLS=\"" + _cols + "\"");

        if (_hSpace != -1)
            sb.append(" HSPACE=\"" + _hSpace + "\"");

        if (_vSpace != -1)
            sb.append(" VSPACE=\"" + _vSpace + "\"");

        if (getName() != null) {
           sb.append(" NAME=\"" + getName() + "\"");
           sb.append(" ID=\"" + getName() + "\"");
        }   
        
        sb.append(">");

        t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        t.print(content, TagWriter.TYPE_CONTENT);

        sb.setLength(0);

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

            flipColor();
            String backgroundColor = getCurrentRowBackgroundColor();
            String style=getCurrentRowStyleClassName();
            String styleString = "";
            if (backgroundColor != null)
            	styleString += " BGCOLOR=\"" + backgroundColor + "\"";
            if (style != null)
            	styleString += " CLASS=\"" + style + "\"";	
            sb.append("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + getColumnCount() + "\"" + styleString + ">");
            int noButtons = (rowCount / rowsPerPage);
            if (rowCount % rowsPerPage > 0)
                noButtons++;
            int page = (_firstRowOnPage / rowsPerPage) + 1;

            sb.append(_pageSelectRenderer.generateRowSelector(this,_theme,_pageLabel,_pageOfLabel,
                    page,_firstSubmitButton,noButtons,_maxPageButtons,_imageURL)
            );
            sb.append("</TD></TR>");
        }

        if (_rowPerPageSel && rowCount > _oldRowsPerPage) {
            if (_rowsPerPageRenderer == null)
                _rowsPerPageRenderer = DataTableRowsPerPageRenderer.getDefaultRenderer();
            flipColor();

            String backgroundColor = getCurrentRowBackgroundColor();
			String style=getCurrentRowStyleClassName();
					String styleString = "";
					if (backgroundColor != null)
						styleString += " BGCOLOR=\"" + backgroundColor + "\"";
					if (style != null)
						styleString += " CLASS=\"" + style + "\"";	
            sb.append("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + getColumnCount() + "\"" + styleString + ">");
            sb.append(_rowsPerPageRenderer.generateRowSelector(this,_theme,_totalRowsLabel,_rowsPerPageLabel,null,rowsPerPage,rowCount));
            sb.append("</TD></TR>");
        }
        sb.append("</TFOOT></TABLE>");
        if (writeScrollDiv)
        	sb.append("</DIV>");
        t.print(sb.toString(), TagWriter.TYPE_END_TAG);
        makeEnabledAndVisible();

        // if (_scroll) {
        //     getPage().scrollToItem(getName() + "TableStart");
        //     _scroll = false;
        // }
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
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public String generateNextTDName(int colSpan, int type) {
        String ret = "";
        if (type == TYPE_HEADER) {
            ret = getName() + "TDHeader" + _nextTDHeaderNo;
            _nextTDHeaderNo += colSpan;
        } else if (type == TYPE_FOOTER) {
            ret = getName() + "TDFooter" + _nextTDFooterNo;
            _nextTDFooterNo += colSpan;
        } else if (type == TYPE_ROW) {
            ret = getName() + "TDRow" + _nextTDRowNo;
            _nextTDRowNo += colSpan;
        } else if (type == TYPE_GROUP_HEADER) {
            ret = getName() + "TDGroupHeader" + _nextTDGroupHeaderNo;
            _nextTDGroupHeaderNo += colSpan;
        } else if (type == TYPE_GROUP_FOOTER) {
            ret = getName() + "TDGroupFooter" + _nextTDGroupFooterNo;
            _nextTDGroupFooterNo += colSpan;
        }

        return ret;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public String generateNextTRName(int type) {
        String ret = "";
        if (type == TYPE_HEADER) {
            ret = getName() + "TRHeader" + _nextTRHeaderNo;
            _nextTRHeaderNo++;
        } else if (type == TYPE_FOOTER) {
            ret = getName() + "TRFooter" + _nextTRFooterNo;
            _nextTRFooterNo++;
        } else if (type == TYPE_ROW) {
            ret = getName() + "TRRow" + _nextTRRowNo;
            _nextTRRowNo++;
        } else if (type == TYPE_GROUP_HEADER) {
            ret = getName() + "TRGroupHeader" + _nextTRGroupHeaderNo;
            _nextTRGroupHeaderNo++;
        } else if (type == TYPE_GROUP_FOOTER) {
            ret = getName() + "TRGroupFooter" + _nextTRGroupFooterNo;
            _nextTRGroupFooterNo++;
        }

        return ret;
    }

    /**
     * Gets the border thickness for the table.
     */
    public int getBorder() {
        return _border;
    }

    /**
     * Gets the background color for the table.
     */
    public String getBorderColor() {
        return _bordercolor;
    }

    /**
     * Returns an array of column numbers used for control breaks.
     */
    public int[] getBreakColumns() {
        if (_breakColumns == null && _groupBy != null) {
            JspController parent = (JspController) getPage();
            Hashtable t = parent.getComponentTable();
            StringTokenizer st = new StringTokenizer(_groupBy, ",");
            Vector res = new Vector();
            while (st.hasMoreTokens()) {
                String tok = st.nextToken();
                HtmlComponent comp = (HtmlComponent) t.get(tok);
                int col = -1;
                if (comp != null) {
                    if (comp instanceof HtmlFormComponent)
                        col = ((HtmlFormComponent) comp).getColumnNumber();
                    else if (comp instanceof HtmlText) {
                        DataStoreEvaluator eval = ((HtmlText) comp).getExpressionEvaluator();
                        if (eval != null)
                            col = _ds.getColumnIndex(eval.getExpression());
                    }
                }
                if (col != -1)
                    res.add(new Integer(col));
            }

            _breakColumns = new int[res.size()];
            for (int i = 0; i < res.size(); i++)
                _breakColumns[i] = ((Integer) res.elementAt(i)).intValue();
        }

        return _breakColumns;
    }


    public long getCacheKey() {
        return _cacheKey;
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
     * Returns whether or not the component should have headings that sort the table when the user clicks on them
     */
    public boolean getClickSort() {
        return _clickSort;
    }

    /**
     * Sets the cell padding for the table.
     */
    public int getCols() {
        return _cols;
    }

    private int getColumnCount() {
        if (_cols != -1)
            return _cols;
        else
            return _maxTDCols;
    }

	/**
	 * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
	 */
	public String getCurrentRowBackgroundColor() {
		if (_color1)
			return getRowBackgroundColor1();
		else
			return getRowBackgroundColor2();

	}
	
	/**
	 * Returns the next color that will be used to stripe the rows in the table
	 */
	public String getNextRowBackgroundColor() {
		if (!_color1)
			return getRowBackgroundColor1();
		else
			return getRowBackgroundColor2();

	}
	
	/**
	 * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
	 */
	public String getCurrentRowStyleClassName() {
		if (_color1)
			return getRowStyleClassName1();
		else
			return getRowStyleClassName2();

	}

    /**
     * Returns the DataStoreBuffer attached to this component.
     */
    public DataStoreBuffer getDataStoreBuffer() {
        return _ds;
    }

    /**
     * This method returns true if the datatable did the last submit of the page through a sort or clicking on a paging button.
     */
    public boolean getDidSubmit() {
        return _didSubmit;
    }

    /**
     * Returns the first row on the page displayed in the datatable.
     */
    public int getFirstRowOnPage() {
        return _firstRowOnPage;
    }

    /**
     * Returns the GroupBy string initialized by the JSP.
     */
    public String getGroupBy() {
        return _groupBy;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public String getHeaderTDNameForRow(String name) {
        String number = name.substring(getName().length() + 5);
        return getName() + "TDHeader" + number;
    }

    /**
     * This method returns the background color for the heading of the Data Table.
     */
    public String getHeadingBackgroundColor() {
        return _defaultHeadingBackground;
    }

    /**
     * This method returns the height for the DataTable
     */
    public String getHeight() {
        return _height;

    }

    /**
     * Gets the horizontal margin for the table.
     */
    public int getHSpace() {
        return _hSpace;
    }

    /**
     * This method gets the maximum number of paging buttons to display.
     */
    public int getMaxPageButtons() {
        return _maxPageButtons;
    }

    /**
     * Sets the maximum number of paging buttons for the table.
     */
    public int getMaxPagingButtons() {
        return _maxPageButtons;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public int getMaxTDColumns() {
        return _maxTDCols;

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
     * This method returns the first background color for the rows in the datatable
     */
    public String getRowBackgroundColor1() {
        return _defaultRowBackground1;
    }

    /**
     * This method returns the second background color for the rows in the datatable
     */

    public String getRowBackgroundColor2() {
        return _defaultRowBackground2;
    }

    /**
     * This method gets the number of rows to display on one page of output. A value <= 0 will indicate no limit to the number of rows displayed.
     */
    public int getRowsPerPage() {
        return _rowsPerPage;
    }

    /**
     * This method gets the anchor to scroll to when a paging button on the table is clicked. Set it to null for the top of the table.
     */
    public String getScrollAnchor() {
        return _scrollAnchor;
    }

    /**
     * Gets whether the datastore will build scrolling javascript when the user clicks on a column heading to sort or a paging button.
     */
    public boolean getScrollOnClickSort() {
        return _scrollOnSort;
    }

    /**
     * Returns true if rows per page selector is visible.
     */
    public boolean getSelRowsPerPage() {
        return _rowPerPageSel;
    }

    /**
     * This method returns the number of the last column that the user clicked on to sort the list.
     */
    public int getSortColumn() {
        return _sortColumn;
    }

    /**
     * This method returns the direction of the sort set when the user last clicked a column heading to sort the list. Valid values are DataStoreBuffer.SORT_ASC or DataStoreBuffer.SORT_DES.
     */
    public int getSortDirection() {
        return _sortDir;
    }


    public boolean getUseCache() {
        return _useCache;
    }

    /**
     * Gets the vertical margin for the table.
     */
    public int getVSpace() {
        return _vSpace;
    }

    /**
     * Gets the width of the DataTable
     */
    public String getWidth() {
        return _width;

    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */

    public void initColor() {
        _color1 = true;
    }

    /**
     * This method returns true if the specified row is on the current page.
     */
    public boolean isRowOnPage(int row) {
        int pageNo = row / _rowsPerPage;
        int currentPage = _firstRowOnPage / _rowsPerPage;

        return pageNo == currentPage;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        if (!getVisible())
            return false;

        if (_ds == null)
            return false;
        //fc: 05/30/02 need to reprocess property expressions before doing process parms
		processPropertyExpressions(rowNo);
        int rowCount = _ds.getRowCount();

        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponentType(i) != TYPE_ROW && getComponentType(i) != TYPE_GROUP_FOOTER && getComponentType(i) != TYPE_GROUP_HEADER) {
                HtmlComponent h = getComponent(i);
                if (h != null)
                    if (h.processParms(parms, -1))
                        _submit = h;
            }
        }

        int endRow = _firstRowOnPage + _rowsPerPage;
        if (endRow > rowCount)
            endRow = rowCount;

        for (int j = _firstRowOnPage; j < endRow; j++) {
            //fc: 10/18/02 need to reprocess property expressions before doing process parms for each row of Data Table.
            processPropertyExpressions(j);
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponentType(i) == TYPE_ROW || getComponentType(i) == TYPE_GROUP_HEADER || getComponentType(i) == TYPE_GROUP_FOOTER) {
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

        Object sortName = parms.get(getFullName() + "ClickSortHidden");
        _clickSortComponent = null;
        if (sortName != null) {
            String[] items = (String[]) sortName;
            if (items[0] != null && items[0].trim().length() > 0) {
                _clickSortComponent = ((JspController) getPage()).getComponent(items[0]);
                _submit = this;
            }
        }

        Object rpp = parms.get(getFullName() + "_rows_per_page_hidden");
        if (rpp != null) {
            String val = ((String[]) rpp)[0];
            if (val.equals("1")) {
                rpp = parms.get(getFullName() + "row_per_page_dd");
                _newRowsPerPage = new Integer(((String[]) rpp)[0]).intValue();
                _submit = this;
            }
            else if (! val.equals("-1")) {
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
            _didSubmit = true;
            return true;
        }
    }

    public void processPropertyExpressions(int row) {
        if (_propertyExpressions == null)
            return;

        ThreeObjectContainer c = null;
        Object comp = null;
        Method meth = null;
        DataStoreEvaluator eval = null;

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
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */
    public void resetCounters() {
        _nextTDFooterNo = 0;
        _nextTDHeaderNo = 0;
        _nextTDRowNo = 0;
        _nextTRFooterNo = 0;
        _nextTRHeaderNo = 0;
        _nextTRRowNo = 0;
        _nextTDGroupFooterNo = 0;
        _nextTDGroupHeaderNo = 0;
        _nextTRGroupFooterNo = 0;
        _nextTRGroupHeaderNo = 0;
    }

    /**
     * Sets the alignment for the Data Table.
     */
    public void setAlign(String newAlign) {
        _align = newAlign;
    }

    /**
     * Sets the background color for the table.
     */
    public void setBackgroundColor(String value) {
        _bgcolor = value;
    }

    /**
     * This method will set the base click sort column for the datastore. This column and direction will be appended to the sort column that the user clicked on.
     * @param column The column to sort on
     * @param dir int DataStoreBuffer.SORT_ASC or DataStoreBuffer.SORT_DES
     */
    public void setBaseClickSort(String column, int dir) {
        _baseSortColumn = _ds.getColumnIndex(column);
        _baseSortDir = dir;
    }

    /**
     * Sets the border width for the table.
     */
    public void setBorder(int border) {
        _border = border;
    }

    /**
     * Sets the background color for the table.
     */
    public void setBorderColor(String value) {
        _bordercolor = value;
    }

    /**
     * Use this method to set the columns that the Data Table will control break on.
     * @param cols An array of column numbers to break on.
     */
    public void setBreakColumns(int[] cols) {
        _breakColumns = cols;
    }

    /**
     * Use this method to set the columns that the Data Table will control break on.
     * @param cols An array of column names to break on.
     */
    public void setBreakColumns(String[] cols) {

        int[] colN = new int[cols.length];

        for (int i = 0; i < colN.length; i++) {
            colN[i] = _ds.getColumnIndex(cols[i]);
            if (colN[i] == -1) {
                MessageLog.writeInfoMessage("***Column Not Found Setting Break Columns:" + cols[i] + "***", this);
            }
        }
        _breakColumns = colN;
    }

    /**
     * Use this method to set the columns that the Data Table will control break on.
     * @param col The column number to break on.
     */
    public void setBreakColumns(int col) {
        _breakColumns = new int[1];
        _breakColumns[0] = col;
    }

    /**
     * Use this method to set the columns that the Data Table will control break on.
     * @param col A column name to break on.
     */
    public void setBreakColumns(String col) {
        int colN = _ds.getColumnIndex(col);
        if (colN == -1) {
            MessageLog.writeInfoMessage("***Column Name not Found Setting Break Columns:" + col + "***", this);
        }
        _breakColumns = new int[1];
        _breakColumns[0] = colN;
    }

    /**
     * This method is used by the framework to decide whether or not to generate the image or get it from cache. It should not be called directly.
     */
    public void setCacheKey(long key) {
        _cacheKey = key;
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
     * Sets whether or not the table headings will have underline links that sort the table
     * Creation date: (5/22/01 2:32:37 PM)
     * @param newClickSort java.lang.String
     */
    public void setClickSort(boolean newClickSort) {
        _clickSort = newClickSort;
    }

    /**
     * Sets the number of columns for the table.
     */
    public void setCols(int value) {
        _cols = value;
    }

    /**
     * Set the datastore buffer that will be used to generate the table.
     */
    public void setDataStoreBuffer(DataStoreBuffer ds) {
        _ds = ds;
    }

    /**
     * This method sets the first row displayed on the current page.
     */
    public void setFirstRowOnPage(int firstRowOnPage) {
        _firstRowOnPage = firstRowOnPage;
    }

    /**
     * Sets the groupby string for the datatable.
     */
    public void setGroupBy(java.lang.String newGroupBy) {
        _groupBy = newGroupBy;
    }

    /**
     * Sets the background color for the heading
     */
    public void setHeadingBackgroundColor(String value) {
        _defaultHeadingBackground = value;
    }

    /**
     * Sets the height for the datatable
     */
    public void setHeight(String val) {
        _height = val;

    }

    /**
     * Sets the horizontal margin for the table.
     */
    public void setHSpace(int val) {
        _hSpace = val;
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
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags.
     */
    public void setMaxTDColumns(int cols) {
        _maxTDCols = cols;

    }

    /**
     * This method will scroll to the first row on the current selected page
     */
    //public void setPage(int page) {
    //    _firstRowOnPage = page * _rowsPerPage;
    //    if (_maxPageButtons == 0)
    //        _firstSubmitButton = page;
    //}

    /**
      * This method will scroll to the first row on the current selected page
      * It also adjusts the _firstSubmitButton to correctly set up the page
      * range below the table.
      */
     public void setPage(int page) {

         // Calculate first row on page
         _firstRowOnPage = page * _rowsPerPage;

         // Set our first submit button to be the page we are on
         int rowCount = _ds.getRowCount();
         int noButtons = rowCount / _rowsPerPage;
         if (rowCount % _rowsPerPage > 0)
             noButtons++;

         // Calculate an offset to the submit button so that we don't skew
         // the range of pages. Then apply offset if necessary.  Also make sure
         // we adjust the first button at the high end of the page range
         //if necessary,
         _firstSubmitButton = page;
         int pageOffset = _firstSubmitButton % (_maxPageButtons==0?1:_maxPageButtons);

         // Takes care of case where we are at the beginning of page range
         if ( page < _maxPageButtons )
             _firstSubmitButton = 0;

         // Takes care of case where we are at the end of the page range
         else if ( page + _maxPageButtons > noButtons )
             _firstSubmitButton = noButtons - _maxPageButtons;

         // All other cases
         else
             _firstSubmitButton -= pageOffset;
     }

    /**
     * Sets the type of paging buttons for the data table. Valid Types are PAGE_BUTTON_TYPE_SUBMIT, PAGE_BUTTON_TYPE_IMAGE, PAGE_BUTTON_TYPE_TEXT
     **/

    public void setPageButtonType(int type) {
        _pageButtonType = type;
        if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE) 
        	getPage().registerImageGenerator(getFullName(), this);
        if (_pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR) 
        	setRowsPerPage(9999999);
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
     * Sets the first background color for the table
     */
    public void setRowBackgroundColor1(String value) {
        _defaultRowBackground1 = value;
    }

    /**
     * Sets the second background color for rows in the table
     */
    public void setRowBackgroundColor2(String value) {
        _defaultRowBackground2 = value;
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
     * Sets whether the datastore will build scrolling javascript when the user clicks on a column heading to sort or a paging button.
     */
    public void setScrollOnClickSort(boolean scroll) {
        _scrollOnSort = scroll;
    }

    /**
     * Sets whether rows per page selector is visible.
     */
    public void setSelRowsPerPage(boolean sel) {
        _rowPerPageSel = sel;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        Props prop = getPage().getPageProperties();

        _border = prop.getThemeIntProperty(theme, Props.DATA_TABLE_BORDER);
        _bgcolor = prop.getThemeProperty(theme, Props.DATA_TABLE_BACKGROUND_COLOR);
        _cellPadding = prop.getThemeIntProperty(theme, Props.DATA_TABLE_CELLPADDING);
        _cellSpacing = prop.getThemeIntProperty(theme, Props.DATA_TABLE_CELLSPACING);

        _defaultHeadingBackground = prop.getThemeProperty(theme, Props.DATA_TABLE_HEADING_BACKGROUND_COLOR);
        _defaultRowBackground1 = prop.getThemeProperty(theme, Props.DATA_TABLE_ROW_BACKGROUND_COLOR_1);
        _defaultRowBackground2 = prop.getThemeProperty(theme, Props.DATA_TABLE_ROW_BACKGROUND_COLOR_2);

        String cs = prop.getThemeProperty(theme, Props.DATA_TABLE_CLICK_SORT);
        _clickSort = (cs == null ? false : cs.toUpperCase().equals("TRUE"));
        _scrollOnSort = prop.getThemeBooleanProperty(theme, Props.DATA_TABLE_SCROLL_ON_CLICK_SORT, true);

        _maxPageButtons = prop.getThemeIntProperty(theme, Props.DATA_TABLE_MAX_PAGE_BUTTONS);
        if (_maxPageButtons <= 0)
            _maxPageButtons = 10;

        // rows per page
        _rowsPerPage = prop.getThemeIntProperty(theme, Props.DATA_TABLE_ROWS_PER_PAGE);
        if (_rowsPerPage <= 0)
            _rowsPerPage = 20;

        // sr (11/27/01 1:54:34 PM)
        // added to make the datatable smart about when to show the rows per page dropdown
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

       String rowSelectRenderer = prop.getThemeProperty(theme,Props.DATA_TABLE_PAGE_SELECT_RENDERER);
       if (rowSelectRenderer != null)
           setPageSelectRenderer(rowSelectRenderer);

       String rowsPerPageRenderer = prop.getThemeProperty(theme,Props.DATA_TABLE_ROWS_PER_PAGE_RENDERER);
       if (rowsPerPageRenderer != null)
           setRowsPerPageRenderer(rowsPerPageRenderer);

        _theme = theme;
    }

    /**
     * Sets the vertical margin for the table.
     */
    public void setVSpace(int val) {
        _vSpace = val;
    }

    /**
     * Sets the width of the table
     */
    public void setWidth(String val) {
        _width = val;

    }

    /**
     * This method was created in VisualAge.
     * @param comp com.salmonllc.html.HtmlComponent
     */
    private void sortOnComponent(HtmlComponent comp) throws Exception {
        comp = findSortComponent(comp);
        if (comp == _sortComponent) {
            if (_sortDir == DataStore.SORT_ASC)
                _sortDir = DataStore.SORT_DES;
            else
                _sortDir = DataStore.SORT_ASC;
        } else
            _sortDir = DataStore.SORT_ASC;
        _sortComponent = comp;
        int col = 0, dir = 0;
        if (comp instanceof HtmlFormComponent) {
            int dsCol = ((HtmlFormComponent) comp).getColumnNumber();
            col = dsCol;
            _sortColumn = col;
            dir = _sortDir;
        } else if (comp instanceof HtmlText) {
            DataStoreEvaluator eval = ((HtmlText) comp).getExpressionEvaluator();
            String exp = eval.getExpression();
            int index = -1;
            if (exp != null)
                index = _ds.getColumnIndex(exp);
            if (index > -1) {
                col = index;
                _sortColumn = col;
                dir = _sortDir;
            } else {
                if (_breakColumns != null) {
                    Object cols[] = new Object[_breakColumns.length + 1];
                    int dirs[] = new int[_breakColumns.length + 1];
                    for (int i = 0; i < _breakColumns.length; i++) {
                        cols[i] = new Integer(_breakColumns[i]);
                        dirs[i] = DataStore.SORT_ASC;
                    }
                    cols[_breakColumns.length] = eval;
                    dirs[_breakColumns.length] = _sortDir;
                    _ds.sort(cols, dirs);
                } else
                    _ds.sort(eval, _sortDir);
                return;
            }
        }
        if (_breakColumns != null) {
            int cols[] = new int[_breakColumns.length + 1];
            int dirs[] = new int[_breakColumns.length + 1];
            for (int i = 0; i < _breakColumns.length; i++) {
                cols[i] = _breakColumns[i];
                dirs[i] = DataStore.SORT_ASC;
            }
            cols[_breakColumns.length] = col;
            dirs[_breakColumns.length] = dir;
            _ds.sort(cols, dirs);
        } else {
            if (_baseSortColumn == -1)
                _ds.sort(col, dir);
            else {
                int cols[] = {col, _baseSortColumn};
                int dirs[] = {dir, _baseSortDir};
                _ds.sort(cols, dirs);
            }
        }
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
     * This method will remove all property expressions from the table
     */
    public void clearPropertyExpressions() {
    	if (_propertyExpressions != null)
	        _propertyExpressions.setSize(0);
    }

    /**
     * Updates the data table labels for the current language<br>
     * The language property file must have the following key structure<br>
     * JspDataTable.page.Page represents the "Page"  in the "Page N of N" label.<br>
     * JspDataTable.page.Of represents the "of"  in the "Page N of N" label.<br>
     * JspDataTable.rows.PerPage represents the "Rows displayed per page" label.<br>
     * JspDataTable.rows.Total represents the "Total Rows" label.<br>
     * Author: Claudio Pi Gamba<br>
     * Company: http://www.grupo-quanam.com<br>
     * Email: claudio.pi@quanam.com.uy<br>
     */
    public void updateLocale() {
        _updateLocale = true;
    }

    private void processLocaleInfo() {
        if (_updateLocale) {
            _updateLocale = false;
            LanguagePreferences p = getPage().getLanguagePreferences();
            String appName = getPage().getApplicationName();
            String descr=null;
            StringBuffer key = new StringBuffer("JspDataTable.page.Page");
            descr=LanguageResourceFinder.getResource(appName, key.toString(), p);
            if (descr!=null) {
                _pageLabel=descr;
            }
            key = new StringBuffer("JspDataTable.page.Of");
            descr=LanguageResourceFinder.getResource(appName, key.toString(), p);
            if (descr!=null) {
                _pageOfLabel=descr;
            }
            key = new StringBuffer("JspDataTable.rows.PerPage");
            descr=LanguageResourceFinder.getResource(appName, key.toString(), p);
            if (descr!=null) {
                _rowsPerPageLabel=descr;
            }
            key = new StringBuffer("JspDataTable.rows.Total");
            descr=LanguageResourceFinder.getResource(appName, key.toString(), p);
            if (descr!=null) {
                _totalRowsLabel=descr;
            }

        }
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
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setRowSelectRenderer",e,this);
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
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setRowsPerPageRenderer",e,this);
        }
    }


	/**
	 * @return the default style sheet class for table rows
	 */
	public String getRowStyleClassName1() {
		return _defaultRowStyleClassName1;
	}

	/**
	 * @return the default style sheet class for table alternate rows
	 */
	public String getRowStyleClassName2() {
		return _defaultRowStyleClassName2;
	}

	/**
	 * @param string sets the default style sheet class for table rows 
	 */
	public void setRowStyleClassName1(String string) {
		_defaultRowStyleClassName1 = string;
	}

	/**
	 * @param string sets the default style sheet class for table alternate rows
	 */
	public void setRowStyleClassName2(String string) {
		_defaultRowStyleClassName2 = string;
	}

	/**
	 * @return the default style sheet class for heading rows
	 */
	public String getHeadingStyleClassName() {
		return _defaultHeadingStyleClassName;
	}

	/**
	 * @param string sets the default style sheet class for heading rows
	 */
	public void setHeadingStyleClassName(String string) {
		_defaultHeadingStyleClassName = string;
	}

	/**
	 * framework method, do not call directly
	 */
	public int getLastRenderedRow() {
		return _lastRenderedRow;
	}

	/**
	 * framework method, do not call directly
	 */
	public void setLastRenderedRow(int lastRenderedRow) {
		_lastRenderedRow = lastRenderedRow;
	}
	/**
	 * @return Returns true if the component will generate THEAD, TBODY and TFOOT tags around the sections of the table.
	 */
	public boolean getGenTHeadAndBody() {
		return _genTHeadAndBody;
	}
	/**
	 * @param true if the component will generate THEAD, TBODY and TFOOT tags around the sections of the table.
	 */
	public void setGenTHeadAndBody(boolean genTHeadAndBody) {
		_genTHeadAndBody = genTHeadAndBody;
	}
	
	
	/**
	 * @return Returns the rowHighlighter used by this component.
	 */
	public HtmlRowHighlighter getRowHighlighter() {
		return _rowHighlighter;
	}
	/**
	 * @param Framework method, do not call directly. Instead add a rowhighlighter to the JSP.
	 */
	public void setRowHighlighter(HtmlRowHighlighter rowHighlighter) {
		_rowHighlighter = rowHighlighter;
	}
	/**
	 * @return Returns the alwaysShowGroupFooter.
	 */
	public boolean getAlwaysShowGroupFooter() {
		return _alwaysShowGroupFooter;
	}
	/**
	 * @param alwaysShowGroupFooter The alwaysShowGroupFooter to set.
	 */
	public void setAlwaysShowGroupFooter(boolean alwaysShowGroupFooter) {
		_alwaysShowGroupFooter = alwaysShowGroupFooter;
	}
	
	
	/**
	 * Framework method, do not call directly
	 */
	public void add(HtmlComponent comp, int type) {
		if (comp instanceof JspDiv && type==TYPE_GROUP_HEADER)
			_alwaysShowGroupFooter=true;
		super.add(comp, type);
	}
}

