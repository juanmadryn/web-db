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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlDataTable.java $
//$Author: Dan $
//$Revision: 88 $
//$Modtime: 11/05/04 9:05a $
/////////////////////////

import com.salmonllc.forms.BaseListForm;
import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.jsp.JspForm;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This component will generate an HTML table. It serves as a container and layout manager for other components. There are several bands of components in the page. Heading Components are displayed at the top of the table and occur only once. Row Components are displayed below the heading components and are repeated for each row in the DataStoreBuffer. Footer Components are displayed at the end of the table. Control Break Header and Footer components are displayed before and after a set of control break columns change.
 */

public class HtmlDataTable extends HtmlContainer implements ImageGenerator {
	public static final int PAGE_BUTTON_TYPE_SUBMIT = 0;
	public static final int PAGE_BUTTON_TYPE_IMAGE = 1;
	public static final int PAGE_BUTTON_TYPE_TEXT = 2;
	public static final int PAGE_BUTTON_TYPE_SCROLLBAR = 3;

	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;
	public static final String ALIGN_LEFT = "LEFT";
	public static final String ALIGN_CENTER = "CENTER";
	public static final String ALIGN_RIGHT = "RIGHT";
	public static final String ALIGN_NONE = "";
	public static final String VALIGN_BASELINE = "BASELINE";
	public static final String VALIGN_BOTTOM = "BOTTOM";
	public static final String VALIGN_MIDDLE = "MIDDLE";
	public static final String VALIGN_TOP = "TOP";
	public static final String VALIGN_NONE = "";
	public static final int MAX_ROW_LIMIT = 999999;
	//

	private int _tableHeight = -1;
	private Vector _headingComponents = new Vector();
	private Vector _rowComponents = new Vector();
	private Vector _footerComponents = new Vector();
	private Vector _controlBreakFooterComponents = new Vector();
	private Vector _controlBreakHeadingComponents = new Vector();

	protected int _sizeOption = SIZE_PERCENT;
	protected int _containerWidth = 100;
	protected Vector _columnWidth = new Vector();

	protected String _align;
	protected int _border = -1;
	protected String _bgColor = "";
	protected int _cellPadding = -1;
	protected int _cellSpacing = -1;
	protected DataStoreBuffer _ds;
	protected String _defaultHeadingBackground;
	protected String _defaultRowBackground1;
	protected String _defaultRowBackground2;
	protected int _rowsPerPage = 10;
	protected int _firstRowOnPage = 0;
	protected int _maxPageButtons = 10;
	protected int _firstSubmitButton = 0;
	protected int _newStartRow = -1;
	protected int _newFirstButton = -1;
	protected boolean _scroll = false;
	protected boolean _clickSort = true;
	protected int _clickSortColumn = -1;
	protected int _sortColumn = -1;
	protected int _sortDir = DataStore.SORT_DES;
	protected int _breakColumns[];
	protected HtmlComponent _contMessage;
	protected Vector _propertyExpressions;
	protected String _theme = null;
	protected boolean _rowPerPageSel = true;
	protected int _newRowsPerPage = -1;
	protected int _baseSortColumn = -1;
	protected int _baseSortDir;
	protected boolean _didSubmit = false;
	protected boolean _scrollOnSort = true;

	public static final String PAGE_BUTTON_STYLE_RECTANGLE = "RECTANGLE";
	public static final String PAGE_BUTTON_STYLE_OVAL = "OVAL";

	protected boolean _useCache = true;
	protected long _cacheKey = 0;
	protected Font _buttonFont;
	private String _fontString;
	private int _fontSize;
	protected Color _textColor = Color.black;
	protected Color _backgroundColor = Color.lightGray;
	protected Color _topLeftBorder = Color.white;
	protected Color _bottomRightBorder = Color.gray;
	protected Color _transparentColor = Color.magenta;
	protected int _height;
	protected String _style = PAGE_BUTTON_STYLE_RECTANGLE;
	protected int _pageButtonType = PAGE_BUTTON_TYPE_SUBMIT;
	// protected boolean _genImagesForPageButtons = false;
	// protected boolean _linkForPageButtons = false;
	protected String _imageURL;

	protected String _summaryRowText = null;
	protected boolean _displayRowComponents = true;
	protected String _scrollAnchor;
	// used in addDisplay
	private int _colListVal = 0;
	// Used in addColumn
	private String _linkFont = HtmlText.FONT_LINK;
	private Hashtable _compLookUpHash = new Hashtable();
	private final static String HEADING_COMP_HASH_KEY = "Heading_comp_";
	private final static String ROW_COMP_HASH_KEY = "Row_comp_";

	private boolean _bAddPageBreak = false;
	private HtmlStyle _pageBreak;
	private int _iPageBreakIndex = 4;

	private boolean _repeatHeadingsAfterBreak = false;
	protected DataTablePageSelectRenderer _pageSelectRenderer;
	protected DataTableRowsPerPageRenderer _rowsPerPageRenderer;

	private String _defaultRowStyleClassName1;
	private String _defaultRowStyleClassName2;
	private String _defaultHeadingStyleClassName;
	private boolean _genTHeadAndBody = true;
	private HtmlRowHighlighter _rowHighlighter;

	/**
	 * Constructs a new HTMLDataTable.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param d
	 *            The DataStoreBuffer to get the data from.
	 * @param p
	 *            The page that the table will be added to.
	 */
	public HtmlDataTable(String name, DataStoreBuffer d, HtmlPage p) {
		this(name, d, null, p);
	}

	/**
	 * Constructs a new HTMLDataTable.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param d
	 *            The DataStoreBuffer to get the data from.
	 * @param theme
	 *            The theme to use for loading properties.
	 * @param p
	 *            The page that the table will be added to.
	 */
	public HtmlDataTable(String name, DataStoreBuffer d, String theme, HtmlPage p) {
		super(name, p);

		setTheme(theme);

		_ds = d;
		_imageURL = generateImageURL();
	}

	/**
	 * This method should not be used in this type of container. Instead use
	 * setComponentAt().
	 */
	public void add(HtmlComponent comp) {
		return;
	}

	/**
	 * Creates a datastore bucket and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param name
	 *            Name of bucket
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 */
	public void addBucket(String name, String heading, int type) throws Exception {
		addBucket(name, heading, type, null, null);
	}

	/**
	 * Creates a datastore bucket and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param name
	 *            Name of bucket
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 * @param format
	 *            Format used to display column in listbox (as in DataStore),
	 *            else null.
	 */
	public void addBucket(String name, String heading, int type, String href, String format) throws Exception {
		addBucket(name, heading, type, href, format, null);
	}

	/**
	 * Creates a datastore bucket and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param name
	 *            Name of bucket
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 * @param format
	 *            Format used to display column in listbox (as in DataStore),
	 *            else null.
	 * @param propList
	 *            HtmlTableCellProperties
	 */
	public void addBucket(String name, String heading, int type, String href, String format, HtmlTableCellProperties propList) throws Exception {

		DataStore dsTemp = null;
		if (_ds instanceof DataStore) {
			dsTemp = (DataStore) _ds;
		} else {
			throw new Exception("Can not use this method.Not using a DataStore. ");
		}

		// Don't add the same column twice to the data store.
		if (dsTemp.getColumnIndex(name) == -1) {
			dsTemp.addBucket(name, type);
		}

		HtmlLink hl;
		HtmlPage page = getPage();
		HtmlText ht = new HtmlText("", page);
		if (format == null) {
			// For certain data types, set format according to page properties.
			Props props = getPage().getPageProperties();
			switch (type) {
				case DataStore.DATATYPE_DATETIME :
					format = props.getProperty(Props.DATETIME_FORMAT);
					break;
				case DataStore.DATATYPE_DATE :
					format = props.getProperty(Props.DATE_FORMAT);
					break;
				case DataStore.DATATYPE_TIME :
					format = props.getProperty(Props.TIME_FORMAT);
					break;
			}
		}

		if (format != null) {
			ht.setExpression(_ds, name, format);
		} else {
			ht.setExpression(_ds, name);
		}

		if (Util.isFilled(href)) {
			hl = new HtmlLink("lnk" + name, "", page);
			hl.setHrefExpression(_ds, href);
			hl.add(ht);
			ht.setFont(_linkFont);
			addDisplay(name, heading, hl, propList);
		} else {
			addDisplay(name, heading, ht, propList);
		}

	}

	/**
	 * Creates a datastore column and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param table
	 *            Name of table for datastore
	 * @param column
	 *            Name of column for datastore
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 */
	public void addColumn(String table, String column, String heading, int type) throws Exception {
		addColumn(table, column, heading, type, null);
	}

	/**
	 * Creates a datastore column and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param table
	 *            Name of table for datastore
	 * @param column
	 *            Name of column for datastore
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 */
	public void addColumn(String table, String column, String heading, int type, String href) throws Exception {
		addColumn(table, column, heading, type, href, null);
	}

	/**
	 * Creates a datastore column and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param table
	 *            Name of table for datastore
	 * @param column
	 *            Name of column for datastore
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 * @param format
	 *            Format used to display column in listbox (as in DataStore),
	 *            else null.
	 */
	public void addColumn(String table, String column, String heading, int type, String href, String format) throws Exception {
		addColumn(table, column, heading, type, href, format, null);
	}

	/**
	 * Creates a datastore column and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param table
	 *            Name of table for datastore. If table == null then table will
	 *            be forced to "NULL_TABLE"
	 * @param column
	 *            Name of column for datastore
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 * @param format
	 *            Format used to display column in listbox (as in DataStore),
	 *            else null.
	 * @param propList
	 *            HtmlTableCellProperties
	 */
	public void addColumn(String table, String column, String heading, int type, String href, String format, HtmlTableCellProperties propList) throws Exception {
		addColumn(table, column, heading, type, href, format, propList, propList);
	}

	/**
	 * Creates a datastore column and corresponding display components for the
	 * search and list boxes.
	 * 
	 * @param table
	 *            Name of table for datastore. If table == null then table will
	 *            be forced to "NULL_TABLE"
	 * @param column
	 *            Name of column for datastore
	 * @param heading
	 *            heading for search box and/or list box, or null
	 * @param type
	 *            Type of datastore column, using DataStore.DATATYPE_* values
	 * @param href
	 *            HREF to use in hotlink from this column in list box, else
	 *            null.
	 * @param format
	 *            Format used to display column in listbox (as in DataStore),
	 *            else null.
	 * @param propHeading
	 *            HtmlTableCellProperties
	 * @param propList
	 *            HtmlTableCellProperties
	 */
	public void addColumn(String table, String column, String heading, int type, String href, String format, HtmlTableCellProperties propHeading, HtmlTableCellProperties propList) throws Exception {

		// If table == null then table will be forced to "NULL_TABLE"
		if (table == null) {
			table = BaseListForm.NULL_TABLE;
		}
		String fullColName = table + "." + column;

		DataStore dsTemp = null;
		if (_ds instanceof DataStore) {
			dsTemp = (DataStore) _ds;
		} else {
			throw new Exception("Can not use this method.Not using a DataStore. ");
		}

		// Don't add the same column twice to the data store.
		if (dsTemp.getColumnIndex(table + "." + column) == -1) {
			dsTemp.addColumn(table, column, type, false, false);
		}

		HtmlLink hl;
		HtmlPage page = getPage();
		HtmlText ht = new HtmlText(table + "_" + column, page);
		if (format == null) {
			// For certain data types, set format according to page properties.
			Props props = getPage().getPageProperties();
			switch (type) {
				case DataStore.DATATYPE_DATETIME :
					format = props.getProperty(Props.DATETIME_FORMAT);
					break;
				case DataStore.DATATYPE_DATE :
					format = props.getProperty(Props.DATE_FORMAT);
					break;
				case DataStore.DATATYPE_TIME :
					format = props.getProperty(Props.TIME_FORMAT);
					break;
			}
		}
		if (format != null) {
			ht.setExpression(dsTemp, fullColName, format);
		} else {
			ht.setExpression(dsTemp, fullColName);
		}

		// if an href is given make the component a link
		if (Util.isFilled(href)) {
			hl = new HtmlLink("lnk" + column, "", page);
			hl.setHrefExpression(dsTemp, href);
			hl.add(ht);
			ht.setFont(_linkFont);
			addDisplay(fullColName, heading, hl, propHeading, propList);
		} else {
			addDisplay(fullColName, heading, ht, propHeading, propList);
		}

	}

	/**
	 * Adds a component to the List display.
	 * 
	 * @param fullColName
	 *            Datastore column name in form table.column
	 * @param heading
	 *            DataTable Heading, or null
	 * @param component
	 *            HtmlComponent The component to add
	 * @param prop
	 *            HtmlTableCellProperties Table cell properties
	 */
	public void addDisplay(String fullColName, String heading, HtmlComponent component, HtmlTableCellProperties prop) throws Exception {
		addDisplay(fullColName, heading, component, prop, prop);
	}

	/**
	 * Adds a component to the List display.
	 * 
	 * @param fullColName
	 *            Datastore column name in form table.column
	 * @param heading
	 *            DataTable Heading, or null
	 * @param component
	 *            HtmlComponent The component to add
	 * @param propHeading
	 *            HtmlTableCellProperties the table-cell properties for the
	 *            column heading
	 * @param propComponent
	 *            HtmlTableCellProperties the table-cell properties for the list
	 *            data
	 */
	public void addDisplay(String fullColName, String heading, HtmlComponent component, HtmlTableCellProperties propHeading, HtmlTableCellProperties propComponent) throws Exception {

		// Add heading comp to HashTable with key Heading_comp+fullColName
		HtmlText headComp = null;
		if (heading != null) {
			headComp = new HtmlText(heading, HtmlText.FONT_TABLE_HEADING, getPage());
			setHeadingComponentAt(_colListVal, headComp, propHeading);
			_compLookUpHash.put(HEADING_COMP_HASH_KEY + fullColName, headComp);
		}

		if (propComponent != null) {
			setRowComponentAt(_colListVal++, component, propComponent);

		} else {
			setRowComponentAt(_colListVal++, component);
		}
		// Add row comp to HashTable with key Row_comp+fullColName
		// then later the get by name routine can just use the name value pair
		_compLookUpHash.put(ROW_COMP_HASH_KEY + fullColName, component);

	}

	/**
	 * This method will add a property expression to the HtmlDataTable. The
	 * property expression will automatically set a property value on the
	 * specified component with the results of the passed expression as each row
	 * in the table is processed. This can be used for example to make a
	 * component invisible or visible depending on the value in the datastore
	 * for a particular row: ex:
	 * addPropertyExpression(comp,"visible","bucket==1") will call the
	 * setVisible method on the component comp passing the results of the
	 * expression.
	 * 
	 * @param comp
	 *            The component to set the property for
	 * @param propertyName
	 *            The name of the property to set. The component must have a
	 *            corresponding setProperty method or this method will throw a
	 *            NoSuchMethodException
	 * @param expEval
	 *            DataStoreEvaluator The datastore evaluator that evaluates the
	 *            expression
	 * @exception java.lang.NoSuchMethodException
	 *                The exception description.
	 * @exception com.salmonllc.sql.DataStoreException
	 *                The exception description.
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
	 * This method will add a property expression to the HtmlDataTable. The
	 * property expression will automatically set a property value on the
	 * specified component with the results of the passed expression as each row
	 * in the table is processed. This can be used for example to make a
	 * component invisible or visible depending on the value in the datastore
	 * for a particular row: ex:
	 * addPropertyExpression(comp,"visible","bucket==1") will call the
	 * setVisible method on the component comp passing the results of the
	 * expression.
	 * 
	 * @param comp
	 *            The component to set the property for
	 * @param propertyName
	 *            The name of the property to set. The component must have a
	 *            corresponding setProperty method or this method will throw a
	 *            NoSuchMethodException
	 * @param propExpression
	 *            com.salmonllc.html.PropertyExpression The instance of
	 *            PropertyExpression that should do the evaluating.
	 * @exception java.lang.NoSuchMethodException
	 *                The exception description.
	 * @exception com.salmonllc.sql.DataStoreException
	 *                The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreExpression propExpression) throws NoSuchMethodException, DataStoreException {
		DataStoreEvaluator dse = new DataStoreEvaluator(_ds, propExpression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * This method will add a property expression to the HtmlDataTable. The
	 * property expression will automatically set a property value on the
	 * specified component with the results of the passed expression as each row
	 * in the table is processed. This can be used for example to make a
	 * component invisible or visible depending on the value in the datastore
	 * for a particular row: ex:
	 * addPropertyExpression(comp,"visible","bucket==1") will call the
	 * setVisible method on the component comp passing the results of the
	 * expression.
	 * 
	 * @param comp
	 *            The component to set the property for
	 * @param propertyName
	 *            The name of the property to set. The component must have a
	 *            corresponding setProperty method or this method will throw a
	 *            NoSuchMethodException
	 * @param expression
	 *            java.lang.String The datastore expression to evaluate
	 * @exception java.lang.NoSuchMethodException
	 *                The exception description.
	 * @exception com.salmonllc.sql.DataStoreException
	 *                The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, String expression) throws NoSuchMethodException, DataStoreException {
		DataStoreEvaluator dse = new DataStoreEvaluator(_ds, expression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * This method will remove all property expressions from the table.
	 */
	public void clearPropertyExpressions() {
		if (_propertyExpressions != null)
			_propertyExpressions.setSize(0);
	}

	public boolean executeEvent(int eventType) throws Exception {
		TwoObjectContainer t = null;
		//
		if (eventType == HtmlComponent.EVENT_OTHER) {
			HtmlComponent h = null;
			int headingComponentsSize = _headingComponents.size();
			for (int i = 0; i < headingComponentsSize; i++) {
				t = (TwoObjectContainer) _headingComponents.elementAt(i);
				if (t != null) {
					h = (HtmlComponent) t.getObject1();
					if (h != null)
						if (!h.executeEvent(eventType))
							return false;
				}
			}
			int rowComponentsSize = _rowComponents.size();
			for (int i = 0; i < rowComponentsSize; i++) {
				t = (TwoObjectContainer) _rowComponents.elementAt(i);
				if (t != null) {
					h = (HtmlComponent) t.getObject1();
					if (h != null)
						if (!h.executeEvent(eventType))
							return false;
				}
			}
			int footerComponentsSize = _footerComponents.size();
			for (int i = 0; i < footerComponentsSize; i++) {
				t = (TwoObjectContainer) _footerComponents.elementAt(i);
				if (t != null) {
					h = (HtmlComponent) t.getObject1();
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
					t = (TwoObjectContainer) _rowComponents.elementAt(_clickSortColumn);
					HtmlComponent sortComp = findSortComponent((HtmlComponent) t.getObject1());
					if (sortComp != null)
						sortOnComponent(sortComp, _clickSortColumn);
				}
				if (_scroll) {
					if (_scrollAnchor != null)
						getPage().scrollToItem(_scrollAnchor);
					else {
						boolean didScroll = false;
						JspForm form = JspForm.findParentForm(this);
						if (form != null) {
							if (form.isScrollPositionSet()) {
								form.scrollToLastPosition();
								didScroll = true;
							}
						}
						if (!didScroll)
							getPage().scrollToItem(getFullName() + "TableStart");
					}
					_scroll = false;
				}
			} else
				retVal = _submit.executeEvent(eventType);
			_submit = null;
			return retVal;
		}
		return true;
	}

	protected HtmlComponent findSortComponent(HtmlComponent sortComp) {
		if (sortComp instanceof HtmlFormComponent) {
			if (((HtmlFormComponent) sortComp).getColumnNumber() != -1)
				return sortComp;
		} else if (sortComp instanceof HtmlText) {
			if (((HtmlText) sortComp).getExpressionEvaluator() != null)
				return sortComp;
		} else if (sortComp instanceof HtmlContainer) {
			Enumeration e = ((HtmlContainer) sortComp).getComponents();
			while (e.hasMoreElements()) {
				sortComp = (HtmlComponent) e.nextElement();
				return findSortComponent(sortComp);
			}
		}
		return null;
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {

		if (_pageBreak != null)
			_pageBreak.generateHTML(p, rowNo);

		if (!getVisible()) {
			_didSubmit = false;
			return;
		}

		if (_rowHighlighter != null) {
			HtmlScriptGenerator gen=new HtmlScriptGenerator(getPage());
			gen.generateSelectRowScript();
			_rowHighlighter.resetForGenerate();
		}	

		int rowsPerPage = _rowsPerPage;
		boolean rowPerPageSel = _rowPerPageSel;
		if (getParent() instanceof HtmlInlineFrame && getPage().getUseIFrames() || (_pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR)) {
			rowsPerPage = 100000;
			rowPerPageSel = false;
		}
		_newStartRow = -1;

		// Start Table
		generateHtmlTableStart(p);
		// SR 10-15-2000
		int headingComponentsSize = _headingComponents.size();
		int rowComponentsSize = _rowComponents.size();
		int footerComponentsSize = _footerComponents.size();
		// LT/FC 11/22/00: maxComponentsSize represents true number of columns;
		// needed for COLSPAN at end of table
		int maxComponentsSize = Math.max(Math.max(headingComponentsSize, rowComponentsSize), footerComponentsSize);

		//
		int maxSize = headingComponentsSize;
		//
		if (rowComponentsSize > maxSize)
			maxSize = rowComponentsSize;
		if (footerComponentsSize > maxSize)
			maxSize = footerComponentsSize;

		if (headingComponentsSize > 0) {
			if (_genTHeadAndBody)
				p.println("<THEAD id=\"" + getFullName() + "-thead\">");
			generateHtmlForBand(_headingComponents, p, _defaultHeadingBackground, _defaultHeadingStyleClassName, maxSize, _clickSort, "TD");
			if (_genTHeadAndBody)
				p.println("</THEAD>");
		}

		//get the data
		_ds.waitForRetrieve();
		int rowCount = _ds.getRowCount();
		if (_firstRowOnPage >= rowCount) {
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
		String rowStyle = _defaultRowStyleClassName2;

		if (_genTHeadAndBody) {
			if ((getPageButtonType() == JspDataTable.PAGE_BUTTON_TYPE_SCROLLBAR) && !(getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT))
				p.println("<TBODY id=\"" + getFullName() + "-tbody\" style=\"max-height:" + getTableHeight() + "px;overflow: scroll\">");
			else
				p.println("<TBODY>");
		}
		for (int rowIndex = _firstRowOnPage; (rowIndex < rowCount) && (rowIndex < (_firstRowOnPage + rowsPerPage)); rowIndex++) {
			if (_displayRowComponents) {
				backgroundColor = getBackgroundColor(backgroundColor);
				rowStyle = getStyle(rowStyle);
			}
			//check for control breaks
			if (_breakColumns != null) {
				if (_contMessage != null) {
					_contMessage.setVisible(false);
				}

				if (rowIndex == _firstRowOnPage) {
					if (_controlBreakHeadingComponents.size() > 0) {
						if (_contMessage != null && lastBreak != _firstRowOnPage) {
							_contMessage.setVisible(true);
						}

						generateHtmlForBand(_controlBreakHeadingComponents, p, backgroundColor, rowStyle, maxSize, false, lastBreak, lastBreak, "TD");
						backgroundColor = getBackgroundColor(backgroundColor);
						rowStyle = getStyle(rowStyle);
					}
				} else {
					if (_ds.compareRows(lastBreak, rowIndex, _breakColumns) != 0) {
						if (_controlBreakFooterComponents.size() > 0) {
							generateHtmlForBand(_controlBreakFooterComponents, p, backgroundColor, rowStyle, maxSize, false, lastBreak, rowIndex - 1, "TD");
							backgroundColor = getBackgroundColor(backgroundColor);
							rowStyle = getStyle(rowStyle);
						}
						if (_controlBreakHeadingComponents.size() > 0) {
							generateHtmlForBand(_controlBreakHeadingComponents, p, backgroundColor, rowStyle, maxSize, false, rowIndex, rowIndex, "TD");
							backgroundColor = getBackgroundColor(backgroundColor);
							rowStyle = getStyle(rowStyle);
						}
						lastBreak = rowIndex;
					}
				} // end first row on page check
			} //end check for control breaks
			processPropertyExpressions(rowIndex);

			//do the columns
			if (_displayRowComponents) {
				//Begening of new row
				//Add the pagebreak if reuested... TT2001_11_15
				generateHtmlForBand(_rowComponents, p, backgroundColor, rowStyle, maxSize, false, rowIndex, rowIndex, "TD", _bAddPageBreak);
			}

			Thread.yield();
		}
		int nextRow = _firstRowOnPage + rowsPerPage;
		if (_breakColumns != null) {
			if (nextRow >= rowCount) {
				if (_controlBreakFooterComponents.size() > 0) {
					backgroundColor = getBackgroundColor(backgroundColor);
					rowStyle = getStyle(rowStyle);
					generateHtmlForBand(_controlBreakFooterComponents, p, backgroundColor, rowStyle, maxSize, false, lastBreak, rowCount - 1, "TD");
				}
			} else if (_ds.compareRows(lastBreak, nextRow, _breakColumns) != 0) {
				if (_controlBreakFooterComponents.size() > 0) {
					backgroundColor = getBackgroundColor(backgroundColor);
					rowStyle = getStyle(rowStyle);
					generateHtmlForBand(_controlBreakFooterComponents, p, backgroundColor, rowStyle, maxSize, false, lastBreak, nextRow - 1, "TD");
				}
			}
		}
		if (_genTHeadAndBody)
			p.println("</TBODY><TFOOT>");
		if (footerComponentsSize > 0 && nextRow >= rowCount) {
			backgroundColor = getBackgroundColor(backgroundColor);
			rowStyle = getStyle(rowStyle);
			generateHtmlForBand(_footerComponents, p, backgroundColor, rowStyle, maxSize, false, "TD");
		}

		if (rowCount > rowsPerPage) {
			if (_pageSelectRenderer == null) {
				if (_pageButtonType == PAGE_BUTTON_TYPE_SUBMIT)
					_pageSelectRenderer = DataTablePageSelectRenderer.getSubmitButtonRenderer();
				else if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE)
					_pageSelectRenderer = DataTablePageSelectRenderer.getImageButtonRenderer();
				else if (_pageButtonType != PAGE_BUTTON_TYPE_SCROLLBAR)
					_pageSelectRenderer = DataTablePageSelectRenderer.getLinkRenderer();
			}
			backgroundColor = getBackgroundColor(backgroundColor);
			rowStyle = getStyle(rowStyle);
			String styleString = "";
			if (backgroundColor != null)
				styleString += "  BGCOLOR=\"" + backgroundColor + "\"";
			if (rowStyle != null)
				styleString += " CLASS=\"" + rowStyle + "\"";

			p.println("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + maxComponentsSize + "\"" + styleString + ">");
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
			rowStyle = getStyle(rowStyle);
			String styleString = "";
			if (backgroundColor != null)
				styleString += "  BGCOLOR=\"" + backgroundColor + "\"";
			if (rowStyle != null)
				styleString += " CLASS=\"" + rowStyle + "\"";
			p.println("<TR><TD VALIGN=\"MIDDLE\" COLSPAN=\"" + maxComponentsSize + "\"" + styleString + ">");
			p.println(_rowsPerPageRenderer.generateRowSelector(this, _theme, "Total Rows", "Rows displayed per page", _summaryRowText, rowsPerPage, rowCount));
			p.println("</TD></TR>");
		}
		if (_genTHeadAndBody)
			p.println("</TFOOT>");
		p.println("</TABLE>");
		boolean writeScrollDiv = _pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR && getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT && getPage().getBrowserVersion() > 4;
		if (writeScrollDiv)
			p.println("</DIV>");
		if (_center)
			p.print("</CENTER>");
		if (_scroll) {
			_scroll = false;
		}
		_didSubmit = false;
	}

	/**
	 * Author: Bruno Y. Decaudin Last Modifed: 12/06/00 - 4:30pm
	 */
	private void generateHtmlForBand(Vector components, PrintWriter p, String rowBgColor, String rowStyle, int maxSize, boolean clickSort, int startRow, int endRow, String cellType) throws Exception {
		generateHtmlForBand(components, p, rowBgColor, rowStyle, maxSize, clickSort, startRow, endRow, cellType, false);
	}

	/**
	 * Author: Bruno Y. Decaudin Last Modifed: 12/06/00 - 4:30pm
	 */
	private void generateHtmlForBand(Vector components, PrintWriter p, String rowBgColor, String rowStyle, int maxSize, boolean clickSort, int startRow, int endRow, String cellType, boolean bAddPageBreak) throws Exception {

		processPropertyExpressions(endRow);
		StringBuffer td = new StringBuffer();

		if (bAddPageBreak && _iPageBreakIndex > 0) {
			if ((endRow % _iPageBreakIndex) == 0 && endRow > 0) {
				p.print("<TR><TD colspan=\"" + maxSize + "\"><DIV class=pagebreak>&nbsp;</DIV></TR></TD>");
				if (components != _headingComponents && _repeatHeadingsAfterBreak)
					generateHtmlForBand(_headingComponents, p, _defaultHeadingBackground, _defaultHeadingStyleClassName, maxSize, _clickSort, "TD");
			}
		}

		boolean rowComponents=(components==_rowComponents);
		String javascriptRow="";
		String rowHighlightColor=rowBgColor;
		if (rowComponents && _rowHighlighter != null) {
			String script=_rowHighlighter.generateJavaScriptForTrOnMouseDown(startRow,null);
			if (script.length() > 0)
				javascriptRow+=" onmousedown=\"" + script + "\"";
			script=_rowHighlighter.generateJavaScriptForTrOnClick();
			if (script.length() > 0)
				javascriptRow+=" onclick=\"" + script + "\"";
			rowHighlightColor=_rowHighlighter.getBgColorForRow(startRow,rowBgColor,null);
		}
		
		String styleString = "";
		if (rowHighlightColor != null)
			styleString += "  BGCOLOR=\"" + rowHighlightColor + "\"";
		if (rowStyle != null)
			styleString += " CLASS=\"" + rowStyle + "\"";
					
		p.println("<TR" + styleString + javascriptRow + ">");

		//
		int addTo = 0;
		// sr 10-15-2000
		int width = -1;
		TwoObjectContainer cont = null;
		HtmlComponent comp = null;
		HtmlTableCellProperties props = null;
		int componentsSize = components.size();
		int rowComponentsSize = _rowComponents.size();
		//
		for (int i = 0; i < maxSize; i++) {
			td.setLength(0);
			td.append("<");
			td.append(cellType);
			width = getColumnWidth(i);

			// WIDTH
			if (width > 0) {
				td.append(" WIDTH=\"" + width);
				if (_sizeOption == SIZE_PERCENT)
					td.append("%");
				td.append("\"");
			}

			//
			cont = null;
			if (i < componentsSize) {
				cont = (TwoObjectContainer) components.elementAt(i);
			}

			// clear previous values
			comp = null;
			props = null;
			//
			if (cont != null) {
				comp = (HtmlComponent) cont.getObject1();
				props = (HtmlTableCellProperties) cont.getObject2();
			}

			//
			String cellBgColor = null;
			if (props != null) {
				// ALIGN -- ALIGN_NONE = ""
				if (!Util.isNull(props.getAlign()) && !Util.isEmpty(props.getAlign())) {
					td.append(" ALIGN=\"" + props.getAlign() + "\"");
				}

				// VALIGN -- VALIGN_NONE = ""
				if (!Util.isNull(props.getVertAlign()) && !Util.isEmpty(props.getVertAlign())) {
					td.append(" VALIGN=\"" + props.getVertAlign() + "\"");
				}

				// WIDTH
				if (width == -1 && props.getCellWidth() != -1) {
					td.append(" WIDTH=\"" + props.getCellWidth());
					// get column size option
					if (props.getCellWidthSizeOption() == SIZE_PERCENT)
						td.append("%");
					td.append("\"");
				}

				// HEIGHT
				if (props.getCellHeight() != -1) {
					td.append(" HEIGHT=\"" + props.getCellHeight());
					// get column size option
					if (props.getCellHeightSizeOption() == SIZE_PERCENT)
						td.append("%");
					td.append("\"");
				}

				// BGCOLOR
				if (!Util.isNull(props.getBackgroundColor()) && !Util.isEmpty(props.getBackgroundColor())) {
					cellBgColor = props.getBackgroundColor();
					td.append(" BGCOLOR=\"" + cellBgColor + "\"");
				}

				//	NOWRAP
				if (!props.getWrap()) {
					td.append(" NOWRAP");
				}

				// COLSPAN
				if (props.getColumnSpan() > 1) {
					td.append(" COLSPAN=\"" + props.getColumnSpan() + "\"");
					addTo = props.getColumnSpan() - 1;
				}
				
	
			}
			td.append('>');
			
			if (_rowHighlighter != null && rowComponents) {
				String html=_rowHighlighter.generateHtmlForTd(startRow,0,rowBgColor);
				if (html.length() > 0) {
					td.append(" ");
					td.append(html);
				}	
			}
			


			//
			p.println(td.toString());
			if (comp != null) {
				boolean underLine = false;
				if (i < rowComponentsSize) {
					if (clickSort && _rowComponents.elementAt(i) != null) {
						//-----------------------------------------------------------------------------------------------
						// Author: Bruno Y. Decaudin
						// Date: 12/06/00 - 4:30pm
						// Feedback Item#890: table cell background incorrectly
						// displayed in Netscape (non-issue in IE)
						// This is the second part of the fix. Part one of the
						// fix takes place in the HtmlText component
						// in both 'generateHTML' methods.
						// I added the following three lines to provide a way of
						// preventing an empty label from being
						// replaced by a '&nbsp;' and hence from being
						// associated with a JavaScript clicksort.
						// If the component is an HtmlText and its content is an
						// empty string then 'underline' will be set
						// to false and the JAvaScript code will not be
						// generated
						if (i < _headingComponents.size()) {
							TwoObjectContainer tocHeading = (TwoObjectContainer) _headingComponents.elementAt(i);
							if ((tocHeading.getObject1() instanceof HtmlText) && (((HtmlText) tocHeading.getObject1()).getText().trim().equals("")))
								underLine = false;
							else
								underLine = true;
						}
						//-----------------------------------------------------------------------------------------------
					}
				}

				//
				if (underLine) {
					p.print("<A HREF=\"javascript:" + getFullName() + "_clickSort(" + i + ");\">");
				}

				//
				if (comp == null)
					p.println("&nbsp;");
				else {
					if (startRow > -1) {
						//sr 11-15-2000 originally the only thing in here :
						// comp.generateHTML(p, startRow, endRow);
						if (startRow != endRow) {
							comp.generateHTML(p, startRow, endRow);
						} else {
							comp.generateHTML(p, startRow);
						}
					} else {
						comp.generateHTML(p, -1);
					}
				}

				//
				if (underLine) {
					p.print("</A>");
				}
			} else {
				p.println("&nbsp;");
			}

			// Close the cell
			p.print("\n</");
			p.print(cellType);
			p.println(">");
			i += addTo;
			addTo = 0;
		}

		// close the row
		p.println("</TR>");
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @param components
	 *            java.util.Vector
	 */
	private void generateHtmlForBand(Vector components, PrintWriter p, String bgColor, String style, int maxSize, boolean clickSort, String cellType) throws Exception {
		generateHtmlForBand(components, p, bgColor, style, maxSize, clickSort, -1, -1, cellType);
	}

	private void generateHtmlTableStart(PrintWriter p) throws Exception {
		// SR 11-14-2000 p.println(Util.addHtmlComment("Start Data Table"));

		// CENTER
		if (_center) {
			p.print("<CENTER>");
		}

		// Create Table start
		p.println("<A NAME=\"" + getFullName() + "TableStart\"></A>");
		String tableHeading = "";
		boolean writeScrollDiv = _pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR && getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT && getPage().getBrowserVersion() > 4;
		if (writeScrollDiv) {
			tableHeading += "<style>thead#" + getFullName() + "-thead td {position:relative;top: expression(document.getElementById(\"" + getFullName() + "-div\").scrollTop-2);}</style>";
			tableHeading += "<div id=\"" + getFullName() + "-div\" style=\"height:" + _tableHeight + "px;overflow: auto\">";
		}
		tableHeading += "<TABLE";
		if (_containerWidth > -1) {
			tableHeading += " WIDTH=\"" + _containerWidth;
			if (_sizeOption == SIZE_PERCENT) {
				tableHeading += "%";
			}
			tableHeading += "\"";
		}

		// BORDER
		if (_border > -1) {
			tableHeading += " BORDER=\"" + _border + "\"";
		}

		// BGCOLOR
		if (!Util.isNull(_bgColor) && !Util.isEmpty(_bgColor)) {
			tableHeading += " BGCOLOR=\"" + _bgColor + "\"";
		}

		// CELLPADDING
		if (_cellPadding > -1) {
			tableHeading += " CELLPADDING=\"" + _cellPadding + "\"";
		}

		// CELLSPACING
		if (_cellPadding > -1) {
			tableHeading += " CELLSPACING=\"" + _cellSpacing + "\"";
		}

		// ALIGN
		if (_align != null) {
			if (_align.equals(ALIGN_LEFT) || _align.equals(ALIGN_RIGHT) || _align.equals(ALIGN_CENTER)) {
				tableHeading += " ALIGN=\"" + _align + "\"";
			}
		}

		//
		tableHeading += ">";
		p.println(tableHeading);
		if (_clickSort) {
			p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "_SORTITEM\" VALUE=\"-1\">");
			p.print("<SCRIPT>");
			p.print("function " + getFullName() + "_clickSort(i) {");
			p.print(getFormString() + getFullName() + "_SORTITEM.value = i;");
			JspForm form = JspForm.findParentForm(this);
			if (form != null)
				p.print(form.getSubmitScript());
			else
				p.print(getFormString() + "submit();");
			p.print("}");
			p.print("</SCRIPT>");
		}
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
			i = fact.createOvalButton(_height, getPagingButtonFont(), source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
		else
			i = fact.createRectangleButton(_height, getPagingButtonFont(), source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);

		GifEncoder g = new GifEncoder(i, out, true, _transparentColor);
		g.encode();

	}

	public void generateInitialHTML(PrintWriter p) throws Exception {
		if (!_visible)
			return;

		generateInitialHtmlForBand(_headingComponents, p);
		generateInitialHtmlForBand(_rowComponents, p);
		generateInitialHtmlForBand(_footerComponents, p);
		generateInitialHtmlForBand(_controlBreakFooterComponents, p);
		generateInitialHtmlForBand(_controlBreakHeadingComponents, p);

	}

	private void generateInitialHtmlForBand(Vector components, PrintWriter p) throws Exception {
		// sr 10-15-2000
		int componentsSize = components.size();
		TwoObjectContainer cont = null;
		//
		for (int i = 0; i < componentsSize; i++) {
			cont = (TwoObjectContainer) components.elementAt(i);
			if (cont != null && cont.getObject1() != null)
				((HtmlComponent) cont.getObject1()).generateInitialHTML(p);
		}
	}

	/**
	 * Returns the alignment property for the table.
	 * 
	 * @return align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and
	 *         ALIGN_NONE.
	 */
	public String getAlign() {
		return _align;
	}

	/**
	 * Gets the background color for the table.
	 */
	public String getBackgroundColor() {
		return _bgColor;
	}

	protected String getBackgroundColor(String currentColor) {
		if (currentColor == null)
			return _defaultRowBackground1;
		else if (currentColor.equals(_defaultRowBackground1))
			return _defaultRowBackground2;
		else
			return _defaultRowBackground1;

	}

	protected String getStyle(String currentStyle) {
		if (currentStyle == null)
			return _defaultRowStyleClassName1;
		else if (currentStyle.equals(_defaultRowStyleClassName1))
			return _defaultRowStyleClassName2;
		else
			return _defaultRowStyleClassName1;

	}
	/**
	 * Gets the border thickness for the table.
	 */
	public int getBorder() {
		return _border;
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
	 * Returns true if heading components should be underline links that sort
	 * the list.
	 */
	public boolean getClickSort() {
		return _clickSort;
	}

	/**
	 * This method gets the minimum width for a particular column in the table
	 * or -1 if the column width has not been set
	 */
	public int getColumnWidth(int column) {
		if (column < 0)
			return -1;

		if (column >= _columnWidth.size())
			return -1;

		Object o = _columnWidth.elementAt(column);

		if (o == null)
			return -1;

		return ((Integer) o).intValue();
	}

	/**
	 * This method returns all the elements in the container.
	 */
	public Enumeration getComponents() {
		Vector comp = new Vector();
		//
		int headingComponentsSize = _headingComponents.size();
		int rowComponentsSize = _rowComponents.size();
		int footerComponentsSize = _footerComponents.size();
		int controlBreakHeadingComponentsSize = _controlBreakHeadingComponents.size();
		int controlBreakFooterComponentsSize = _controlBreakFooterComponents.size();
		//
		TwoObjectContainer t = null;
		HtmlComponent h = null;
		//

		for (int i = 0; i < headingComponentsSize; i++) {
			t = (TwoObjectContainer) _headingComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					comp.addElement(h);
			}
		}
		for (int i = 0; i < rowComponentsSize; i++) {
			t = (TwoObjectContainer) _rowComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					comp.addElement(h);
			}
		}
		for (int i = 0; i < footerComponentsSize; i++) {
			t = (TwoObjectContainer) _footerComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					comp.addElement(h);
			}
		}
		for (int i = 0; i < controlBreakHeadingComponentsSize; i++) {
			t = (TwoObjectContainer) _controlBreakHeadingComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					comp.addElement(h);
			}
		}
		for (int i = 0; i < controlBreakFooterComponentsSize; i++) {
			t = (TwoObjectContainer) _controlBreakFooterComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					comp.addElement(h);
			}
		}
		return comp.elements();
	}

	/**
	 * Returns the internal data store buffer used by the component.
	 */
	public DataStoreBuffer getDataStoreBuffer() {
		return _ds;
	}

	/**
	 * This method returns true if the datatable did the last submit of the page
	 * through a sort or clicking on a paging button.
	 */
	public boolean getDidSubmit() {
		return _didSubmit;
	}

	/**
	 * This method gets the first row displayed on the current page.
	 */
	public int getFirstRowOnPage() {
		return _firstRowOnPage;
	}

	/**
	 * This method returns all the elements in the container.
	 */
	public int getFooterComponentsSize() {
		return _footerComponents.size();
	}

	/**
	 * Gets whether the or not paging buttons will be generated images or
	 * regular submit buttons.
	 * 
	 * @deprecated Use getPageButtonType()
	 */
	public boolean getGenImagesForPagingButtons() {
		return _pageButtonType == PAGE_BUTTON_TYPE_IMAGE;
	}

	/**
	 * This method returns the heading component at the specified position or
	 * null if non is found.
	 */
	public HtmlComponent getHeadingComponentAt(int pos) {
		if (pos < 0 || pos >= _headingComponents.size())
			return null;

		TwoObjectContainer t = (TwoObjectContainer) _headingComponents.elementAt(pos);
		if (t == null)
			return null;

		return (HtmlComponent) t.getObject1();

	}

	/**
	 * This method returns the heading component at that is the same as the name
	 * specified or null if none is found.
	 */
	public HtmlComponent getHeadingComponentAt(String name) {
		return (HtmlComponent) _compLookUpHash.get(HEADING_COMP_HASH_KEY + name);
	}

	/**
	 * This method returns all the elements in the container.
	 */
	public int getHeadingComponentsSize() {
		return _headingComponents.size();
	}

	/**
	 * This method returns the font that is being used for links created with
	 * addColumn.
	 */
	public String getLinkFont() {
		return _linkFont;
	}

	/**
	 * This method gets the maximum number of paging buttons to display.
	 */
	public int getMaxPageButtons() {
		return _maxPageButtons;
	}

	/**
	 * This method returns the page the row is on.
	 */
	public int getPage(int row) {
		return row / _rowsPerPage;
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
	 * This method gets the Display Style for a generated page button image.
	 * Valid Values are PAGE_BUTTON_STYLE_RECTANGLE and PAGE_BUTTON_STYLE_OVAL
	 */
	public String getPagingButtonDisplayStyle() {
		return _style;
	}

	/**
	 * This method returns the font used for page button images
	 */
	public Font getPagingButtonFont() {
		if (_buttonFont == null)
			_buttonFont = new Font(_fontString, 0, _fontSize);
		return _buttonFont;
	}

	/**
	 * This method returns the height used for page button images
	 */
	public int getPagingButtonHeight() {
		return _height;
	}

	/**
	 * This method returns the text color of page button images
	 */
	public Color getPagingButtonTextColor() {
		return _textColor;
	}

	/**
	 * This method returns the row component at the specified position or null
	 * if non is found.
	 */
	public HtmlComponent getRowComponentAt(int pos) {
		if (pos < 0 || pos >= _rowComponents.size())
			return null;

		TwoObjectContainer t = (TwoObjectContainer) _rowComponents.elementAt(pos);
		if (t == null)
			return null;

		return (HtmlComponent) t.getObject1();

	}

	/**
	 * This method returns the row component at that is the same as the name
	 * specified or null if none is found.
	 */
	public HtmlComponent getRowComponentAt(String name) {
		return (HtmlComponent) _compLookUpHash.get(ROW_COMP_HASH_KEY + name);

	}

	/**
	 * This method returns all the elements in the container.
	 */
	public int getRowComponentsSize() {
		return _rowComponents.size();
	}

	/**
	 * This method gets the number of rows to display on one page of output. A
	 * value <= 0 will indicate no limit to the number of rows displayed.
	 */
	public int getRowsPerPage() {
		if (_rowsPerPage == MAX_ROW_LIMIT)
			return 0;
		else
			return _rowsPerPage;
	}

	/**
	 * This method gets the anchor to scroll to when a paging button on the
	 * table is clicked. Set it to null for the top of the table.
	 */
	public String getScrollAnchor() {
		return _scrollAnchor;
	}

	/**
	 * Gets whether the datastore will build scrolling javascript when the user
	 * clicks on a column heading to sort or a paging button.
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
	 * This method returns the size option for the table and each cell in it.
	 * Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getSizeOption() {
		return _sizeOption;
	}

	/**
	 * This method returns the number of the last column that the user clicked
	 * on to sort the list.
	 */
	public int getSortColumn() {
		return _sortColumn;
	}

	/**
	 * This method returns the direction of the sort set when the user last
	 * clicked a column heading to sort the list. Valid values are
	 * DataStoreBuffer.SORT_ASC or DataStoreBuffer.SORT_DES.
	 */
	public int getSortDirection() {
		return _sortDir;
	}

	/**
	 * This method returns the property theme for the component.
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * If the image is dynamically generated, this method will indicate whether
	 * or not the Objectstore should cache it.
	 */
	public boolean getUseCache() {
		return _useCache;
	}

	/**
	 * Gets whether the or not paging buttons will be links or submit.
	 * 
	 * @deprecated use getPageButtonType
	 */
	public boolean getUseLinksForPagingButtons() {
		return _pageButtonType == PAGE_BUTTON_TYPE_TEXT;
	}

	/**
	 * This method returns the width of the table.
	 */
	public int getWidth() {
		return _containerWidth;
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
		//
		int headingComponentsSize = _headingComponents.size();
		int rowComponentsSize = _rowComponents.size();
		int footerComponentsSize = _footerComponents.size();
		int controlBreakHeadingComponentsSize = _controlBreakHeadingComponents.size();
		//
		TwoObjectContainer t = null;
		HtmlComponent h = null;
		int rowCount = _ds.getRowCount();
		//
		for (int i = 0; i < headingComponentsSize; i++) {
			t = (TwoObjectContainer) _headingComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					if (h.processParms(parms, -1))
						_submit = h;
			}
		}
		//
		for (int j = _firstRowOnPage; (j < rowCount) && (j < (_firstRowOnPage + _rowsPerPage)); j++) {
			processPropertyExpressions(j);
			for (int i = 0; i < rowComponentsSize; i++) {
				t = (TwoObjectContainer) _rowComponents.elementAt(i);
				if (t != null) {
					h = (HtmlComponent) t.getObject1();
					if (h != null)
						if (h.processParms(parms, j))
							_submit = h;
				}
			}
		}
		//
		for (int i = 0; i < footerComponentsSize; i++) {
			t = (TwoObjectContainer) _footerComponents.elementAt(i);
			if (t != null) {
				h = (HtmlComponent) t.getObject1();
				if (h != null)
					if (h.processParms(parms, -1))
						_submit = h;
			}
		}
		//
		for (int j = _firstRowOnPage; (j < rowCount) && (j < (_firstRowOnPage + _rowsPerPage)); j++) {
			for (int i = 0; i < controlBreakHeadingComponentsSize; i++) {
				t = (TwoObjectContainer) _controlBreakHeadingComponents.elementAt(i);
				if (t != null) {
					h = (HtmlComponent) t.getObject1();
					if (h != null)
						if (h.processParms(parms, j))
							_submit = h;
				}
			}
		}
		//
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
				_newRowsPerPage = new Integer(val.trim()).intValue();
				_submit = this;
			}
		}

		//
		_newStartRow = -1;
		_newFirstButton = -1;
		_clickSortColumn = -1;
		Object s1 = parms.get(getFullName() + "_SORTITEM");
		String sort = "-1";
		if (s1 != null)
			sort = ((String[]) s1)[0];
		//
		if (!sort.equals("-1")) {
			if (_breakColumns == null) {
				_newFirstButton = 0;
				_newStartRow = 0;
			}
			_clickSortColumn = Integer.parseInt(sort);
			_submit = this;
		}

		String[] pageSubmitA = (String[]) parms.get(getFullName() + "_page_selector");
		String pageSubmit = "";
		if (pageSubmitA != null)
			pageSubmit = pageSubmitA[0];

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

	protected void processPropertyExpressions(int row) {
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
	 * Removes all the components from the DataTable
	 */
	public void removeAll() {
		_headingComponents.removeAllElements();
		_rowComponents.removeAllElements();
		_footerComponents.removeAllElements();
		_controlBreakFooterComponents.removeAllElements();
		_controlBreakHeadingComponents.removeAllElements();
	}

	/**
	 * Removes an html component from the row container.
	 * 
	 * @param comp
	 *            The component to remove
	 */
	public void removeRowComponent(HtmlComponent comp) {
		int rowComponentsSize = _rowComponents.size();
		//
		TwoObjectContainer t = null;
		HtmlComponent h = null;
		//
		for (int i = 0; i < rowComponentsSize; i++) {
			t = (TwoObjectContainer) _rowComponents.elementAt(i);
			h = (HtmlComponent) t.getObject1();
			if (h == comp) {
				_rowComponents.setElementAt(null, i);
				return;
			}
		}
	}

	public void reset() {
		super.reset();
		_firstRowOnPage = 0;
		_firstSubmitButton = 0;
	}

	/**
	 * This method will remove all Control Break Footer Components from the
	 * DataTable.
	 */
	public void resetControlBreakFooterComponents() {
		_controlBreakFooterComponents.removeAllElements();
	}

	/**
	 * This method will remove all Control Break Heading Components from the
	 * DataTable.
	 */
	public void resetControlBreakHeadingComponents() {
		_controlBreakHeadingComponents.removeAllElements();
	}

	/**
	 * This method will remove all Footer Components from the DataTable.
	 */
	public void resetFooterComponents() {
		_footerComponents.removeAllElements();
	}

	/**
	 * This method will remove all Heading Components from the DataTable.
	 */
	public void resetHeadingComponents() {
		_headingComponents.removeAllElements();
	}

	/**
	 * This method will remove all Row Components from the DataTable.
	 */
	public void resetRowComponents() {
		_rowComponents.removeAllElements();
	}

	/**
	 * Sets the alignment property for the table.
	 * 
	 * @param align
	 *            Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and
	 *            ALIGN_NONE.
	 */

	public void setAlign(String align) {
		_align = align;
	}

	/**
	 * Sets the background color for the table.
	 */
	public void setBackgroundColor(String value) {
		_bgColor = value;
	}

	/**
	 * This method will set the base click sort column for the datastore. This
	 * column and direction will be appended to the sort column that the user
	 * clicked on.
	 * 
	 * @param column
	 *            The column to sort on
	 * @param dir
	 *            int DataStoreBuffer.SORT_ASC or DataStoreBuffer.SORT_DES
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
	 * Use this method to set the columns that the Data Table will control break
	 * on.
	 * 
	 * @param cols
	 *            An array of column numbers to break on.
	 */
	public void setBreakColumns(int[] cols) {
		_breakColumns = cols;
	}

	/**
	 * Use this method to set the columns that the Data Table will control break
	 * on.
	 * 
	 * @param cols
	 *            An array of column names to break on.
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
	 * Use this method to set the columns that the Data Table will control break
	 * on.
	 * 
	 * @param col
	 *            The column number to break on.
	 */
	public void setBreakColumns(int col) {
		_breakColumns = new int[1];
		_breakColumns[0] = col;
	}

	/**
	 * Use this method to set the columns that the Data Table will control break
	 * on.
	 * 
	 * @param col
	 *            A column name to break on.
	 */
	public void setBreakColumns(String col) {
		int colN = _ds.getColumnIndex(col);
		if (colN == -1) {
			MessageLog.writeInfoMessage("***Column Name not Found Setting Break Columns:" + col + "***", this);
		}
		_breakColumns = new int[1];
		_breakColumns[0] = colN;
	}

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
	 * Set to true if heading components should be underline links that sort the
	 * list.
	 */
	public void setClickSort(boolean clickSort) {
		_clickSort = clickSort;
	}

	/**
	 * Sets the column width for a particular column in the table.
	 */
	public void setColumnWidth(int column, int width) {
		if (column < 0)
			return;

		if (column >= _columnWidth.size())
			_columnWidth.setSize(column + 1);

		_columnWidth.setElementAt(new Integer(width), column);
	}

	/**
	 * Use this method to set a continuation message for an HtmlDataTable with
	 * Control Break Columns. The component will be set to visible only for
	 * control break headings where the first row on the page is not the first
	 * row in the group.
	 */
	public void setContMessage(HtmlComponent comp) {
		_contMessage = comp;
	}

	/**
	 * Sets a control break footer component at a particular row position in the
	 * table.
	 */
	public void setControlBreakFooterComponentAt(int column, HtmlComponent comp) {
		setControlBreakFooterComponentAt(column, comp, null);
	}

	/**
	 * Sets a control break footer component at a particular column position in
	 * the table. The cell in the table will use properties specified in the
	 * props argument.
	 */
	public void setControlBreakFooterComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;

		if (column >= _controlBreakFooterComponents.size())
			_controlBreakFooterComponents.setSize(column + 1);

		TwoObjectContainer cont = new TwoObjectContainer(comp, props);

		_controlBreakFooterComponents.setElementAt(cont, column);

		comp.setParent(this);

	}

	/**
	 * Sets all the control break footer components on a particular row in the
	 * table. If you want to use HtmlTableCellProperties you will have to use
	 * setControlBreakFooterComponentAt(int,HtmlComponent,HtmlTableCellProperties)
	 */
	public void setControlBreakFooterComponents(HtmlComponent comp[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setControlBreakFooterComponentAt(colIndex, comp[colIndex], null);
		}
	}

	/**
	 * Sets all the control break footer components on a particular row in the
	 * table. If you want to use HtmlTableCellProperties pass an array of
	 * HtmlTableCellProperties with the ones that do not need cell props it as
	 * null.
	 */
	public void setControlBreakFooterComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setControlBreakFooterComponentAt(colIndex, comp[colIndex], props[colIndex]);
		}
	}

	/**
	 * Sets a control break heading component at a particular row position in
	 * the table.
	 */
	public void setControlBreakHeadingComponentAt(int column, HtmlComponent comp) {
		setControlBreakHeadingComponentAt(column, comp, null);
	}

	/**
	 * Sets a control break heading component at a particular column position in
	 * the table. The cell in the table will use properties specified in the
	 * props argument.
	 */
	public void setControlBreakHeadingComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;

		if (column >= _controlBreakHeadingComponents.size())
			_controlBreakHeadingComponents.setSize(column + 1);

		TwoObjectContainer cont = new TwoObjectContainer(comp, props);

		_controlBreakHeadingComponents.setElementAt(cont, column);

		comp.setParent(this);

	}

	/**
	 * Sets all the control break heading components on a particular row in the
	 * table. If you want to use HtmlTableCellProperties you will have to use
	 * setControlBreakHeadingComponentAt(int,HtmlComponent,HtmlTableCellProperties)
	 */
	public void setControlBreakHeadingComponents(HtmlComponent comp[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setControlBreakHeadingComponentAt(colIndex, comp[colIndex], null);
		}
	}

	/**
	 * Sets all the control break heading components on a particular row in the
	 * table. If you want to use HtmlTableCellProperties pass an array of
	 * HtmlTableCellProperties with the ones that do not need cell props it as
	 * null.
	 */
	public void setControlBreakHeadingComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setControlBreakHeadingComponentAt(colIndex, comp[colIndex], props[colIndex]);
		}
	}

	/**
	 * Sets the internal data store used by the component.
	 */
	public void setDataStore(DataStoreBuffer d) {
		_ds = d;
	}

	/**
	 * Use this method if you don't want the datatable to display the band with
	 * row components.
	 */
	public void setDisplayRowComponents(boolean display) {
		_displayRowComponents = display;
	}

	/**
	 * This method sets the first row displayed on the current page.
	 */
	public void setFirstRowOnPage(int firstRowOnPage) {
		_firstRowOnPage = firstRowOnPage;
	}

	/**
	 * Sets a footer component at a particular row position in the table.
	 */
	public void setFooterComponentAt(int column, HtmlComponent comp) {
		setFooterComponentAt(column, comp, null);
	}

	/**
	 * Sets a footer component at a particular row position in the table. The
	 * cell in the table will use properties specified in the props argument.
	 */
	public void setFooterComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;

		if (column >= _footerComponents.size())
			_footerComponents.setSize(column + 1);

		TwoObjectContainer cont = new TwoObjectContainer(comp, props);

		_footerComponents.setElementAt(cont, column);

		if (comp != null)
			comp.setParent(this);

	}

	/**
	 * Sets all the footer components on a particular row in the table. If you
	 * want to use HtmlTableCellProperties you will have to use
	 * setFooterComponentAt(int,HtmlComponent,HtmlTableCellProperties)
	 */
	public void setFooterComponents(HtmlComponent comp[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setFooterComponentAt(colIndex, comp[colIndex], null);
		}
	}

	/**
	 * Sets all the footer components on a particular row in the table. If you
	 * want to use HtmlTableCellProperties pass an array of
	 * HtmlTableCellProperties with the ones that do not need cell props it as
	 * null.
	 */
	public void setFooterComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setFooterComponentAt(colIndex, comp[colIndex], props[colIndex]);
		}
	}

	/**
	 * Sets whether the or not paging buttons will be generated images or
	 * regular submit buttons.
	 * 
	 * @deprecated Use setPageButonType
	 */
	public void setGenImagesForPagingButtons(boolean gen) {
		if (gen)
			setPageButtonType(PAGE_BUTTON_TYPE_IMAGE);
		else
			setPageButtonType(PAGE_BUTTON_TYPE_SUBMIT);
	}

	/**
	 * Sets a heading component at a particular row position in the table.
	 */
	public void setHeadingComponentAt(int column, HtmlComponent comp) {
		setHeadingComponentAt(column, comp, null);
	}

	/**
	 * Sets a heading component at a particular row position in the table. The
	 * cell in the table will use properties specified in the props argument.
	 */
	public void setHeadingComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;

		if (column >= _headingComponents.size())
			_headingComponents.setSize(column + 1);

		TwoObjectContainer cont = new TwoObjectContainer(comp, props);
		// sr 04-19-2001_compLookUpHash.put(HEADING_COMP_HASH_KEY+
		// comp.getName(), comp);

		_headingComponents.setElementAt(cont, column);

		if (comp != null)
			comp.setParent(this);

	}

	/**
	 * Sets all the heading components on a particular row in the table. If you
	 * want to use HtmlTableCellProperties you wil have to use
	 * setHeadingComponentAt(int,HtmlComponent,HtmlTableCellProperties)
	 */

	public void setHeadingComponents(HtmlComponent comp[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setHeadingComponentAt(colIndex, comp[colIndex], null);
		}
	}

	/**
	 * Sets all the heading components on a particular row in the table. If you
	 * want to use HtmlTableCellProperties pass an array of
	 * HtmlTableCellProperties with the ones that do not need cell props it as
	 * null.
	 */

	public void setHeadingComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setHeadingComponentAt(colIndex, comp[colIndex], props[colIndex]);
		}
	}

	/**
	 * This method set which font will be used for links created with addColumn.
	 */
	public void setLinkFont(String font) {
		_linkFont = font;
	}

	/**
	 * This method sets the maximum number of paging buttons to display.
	 */
	public void setMaxPageButtons(int max) {
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
	 * Adds a page break to this table for printing purposes (IE 5 and up only).
	 */
	public void setPageBreak(boolean bAddPageBreak) {

		int iBrowserType = getPage().getBrowserType();

		//PageBreak works with IE 5 and up...
		if (iBrowserType != HtmlPageBase.BROWSER_MICROSOFT || (iBrowserType == HtmlPageBase.BROWSER_MICROSOFT && getPage().getBrowserVersion() < 5))
			return;

		_bAddPageBreak = bAddPageBreak;

		if (bAddPageBreak) {
			if (_pageBreak == null) {
				_pageBreak = new HtmlStyle("", ".pagebreak", "page-break-before: always", getPage());
				//	getPage().add(_pageBreak);
			}
		}
	}

	/**
	 * add a page break style to the rows depening on the index Creation date:
	 * (11/15/01 1:40:29 PM)
	 * 
	 * @param idx
	 *            int
	 */
	public void setPageBreakIndex(int idx) {
		_iPageBreakIndex = idx;
	}

	/**
	 * This method sets the background color for the page button generated image
	 */
	public void setPagingButtonBackgroundColor(Color c) {
		_backgroundColor = c;
	}

	/**
	 * This method sets the bottom and right border color for the page button
	 * generated image
	 */
	public void setPagingButtonBottomRightColor(Color c) {
		_bottomRightBorder = c;
	}

	/**
	 * This method sets the Display Style for a generated image button. Valid
	 * Values are PAGE_BUTTON_STYLE_RECTANGLE and PAGE_BUTTON_STYLE_OVAL
	 */
	public void setPagingButtonDisplayStyle(String style) {
		_style = style;
	}

	/**
	 * This method sets the font used for dynamically generated page button
	 * images.
	 */
	public void setPagingButtonFont(Font f) {
		_buttonFont = f;
	}

	/**
	 * This method sets the height used for page button images
	 */
	public void setPagingButtonHeight(int height) {
		_height = height;
	}

	/**
	 * Sets the background color for the heading band
	 */
	public void setHeadingBackgroundColor(String value) {
		_defaultHeadingBackground = value;
	}

	/**
	 * Sets the background color for rows in the data table. Rows can alternate
	 * in color and this one sets the second color.
	 */
	public void setRowBackgroundColor2(String value) {
		_defaultRowBackground2 = value;
	}

	/**
	 * Sets the background color for rows in the data table. Rows can alternate
	 * in color and this one sets the second color.
	 */
	public void setRowBackgroundColor1(String value) {
		_defaultRowBackground1 = value;
	}

	/**
	 * Sets a row component at a particular row position in the table.
	 */
	public void setRowComponentAt(int column, HtmlComponent comp) {
		setRowComponentAt(column, comp, null);
	}

	/**
	 * Sets a row component at a particular row position in the table. The cell
	 * in the table will use properties specified in the props argument.
	 */
	public void setRowComponentAt(int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;

		if (column >= _rowComponents.size())
			_rowComponents.setSize(column + 1);

		TwoObjectContainer cont = new TwoObjectContainer(comp, props);

		_rowComponents.setElementAt(cont, column);

		if (comp != null) {
			comp.setParent(this);
			if (_rowHighlighter == null) {
				if (comp instanceof HtmlRowHighlighter)
					_rowHighlighter = (HtmlRowHighlighter) comp;
				else if (comp instanceof HtmlContainer)
					_rowHighlighter = findHighlighterInContainer((HtmlContainer) comp);
			}
		}
	}
	
	private HtmlRowHighlighter findHighlighterInContainer(HtmlContainer cont) {
		Enumeration enumera = cont.getComponents();
		while (enumera.hasMoreElements()) {
			HtmlComponent comp = (HtmlComponent) enumera.nextElement();
			if (comp instanceof HtmlRowHighlighter)
				return (HtmlRowHighlighter) comp;
			else if (comp instanceof HtmlContainer) {
				HtmlRowHighlighter r = findHighlighterInContainer((HtmlContainer) comp);
				if (r != null)
					return r;
			}
		}
		return null;
	}
	/**
	 * Sets a row component at a particular row position in the table.
	 */
	public void setRowComponents(HtmlComponent comp[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setRowComponentAt(colIndex, comp[colIndex], null);
		}

	}

	/**
	 * Sets a row component at a particular row position in the table. If you
	 * want to use HtmlTableCellProperties pass an array of
	 * HtmlTableCellProperties with the ones that do not need cell props it as
	 * null.
	 */
	public void setRowComponents(HtmlComponent comp[], HtmlTableCellProperties props[]) {
		for (int colIndex = 0; colIndex < comp.length; colIndex++) {
			setRowComponentAt(colIndex, comp[colIndex], props[colIndex]);
		}
	}

	/**
	 * This method sets the number of rows to display on one page of output. A
	 * value <= 0 will indicate no limit to the number of rows displayed.
	 */
	public void setRowsPerPage(int rowsPerPage) {
		if (rowsPerPage <= 0)
			_rowsPerPage = MAX_ROW_LIMIT;
		else
			_rowsPerPage = rowsPerPage;
	}

	/**
	 * This method sets the anchor to scroll to when a paging button on the
	 * table is clicked. Set it to null for the top of the table.
	 */
	public void setScrollAnchor(String anchor) {
		_scrollAnchor = anchor;
	}

	/**
	 * Sets whether the datastore will build scrolling javascript when the user
	 * clicks on a column heading to sort or a paging button.
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
	 * This method sets the size option for the table and each cell in it. Valid
	 * return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setSizeOption(int option) {
		_sizeOption = option;
	}

	/**
	 * This method sets the size option for the table and each cell in it. Valid
	 * return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public void setSummaryRowText(String summaryRowText) {
		_summaryRowText = summaryRowText;
	}

	/**
	 * This method sets the property theme for the component.
	 * 
	 * @param theme
	 *            The theme to use.
	 */
	public void setTheme(String theme) {

		Props prop = getPage().getPageProperties();

		_border = prop.getThemeIntProperty(theme, Props.DATA_TABLE_BORDER);
		_bgColor = prop.getThemeProperty(theme, Props.DATA_TABLE_BACKGROUND_COLOR);
		_cellPadding = prop.getThemeIntProperty(theme, Props.DATA_TABLE_CELLPADDING);
		_cellSpacing = prop.getThemeIntProperty(theme, Props.DATA_TABLE_CELLSPACING);

		_defaultHeadingBackground = prop.getThemeProperty(theme, Props.DATA_TABLE_HEADING_BACKGROUND_COLOR);
		_defaultRowBackground1 = prop.getThemeProperty(theme, Props.DATA_TABLE_ROW_BACKGROUND_COLOR_1);
		_defaultRowBackground2 = prop.getThemeProperty(theme, Props.DATA_TABLE_ROW_BACKGROUND_COLOR_2);

		_rowsPerPage = prop.getThemeIntProperty(theme, Props.DATA_TABLE_ROWS_PER_PAGE);
		if (_rowsPerPage <= 0)
			_rowsPerPage = 20;

		_maxPageButtons = prop.getThemeIntProperty(theme, Props.DATA_TABLE_MAX_PAGE_BUTTONS);
		if (_maxPageButtons <= 0)
			_maxPageButtons = 10;

		String cs = prop.getThemeProperty(theme, Props.DATA_TABLE_CLICK_SORT);
		_clickSort = (cs == null ? false : cs.toUpperCase().equals("TRUE"));

		_scrollOnSort = prop.getThemeBooleanProperty(theme, Props.DATA_TABLE_SCROLL_ON_CLICK_SORT, true);

		//properties for paging buttons
		_fontString = prop.getThemeProperty(theme, Props.SUBMIT_IMAGE_FONT_FACE);
		if (_fontString == null)
			_fontString = "Helvetica";
		_fontSize = prop.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_SIZE);
		if (_fontSize == -1)
			_fontSize = 12;

		_height = prop.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);

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

	/**
	 * If the image is dynamically generated, this method will indicate whether
	 * or not the Objectstore should cache it.
	 */
	public void setUseCache(boolean useCache) {
		_useCache = useCache;
	}

	/**
	 * Sets whether the or not paging buttons will be links or submit.
	 * 
	 * @deprecated Use setPageButtonType()
	 */
	public void setUseLinksForPagingButtons(boolean gen) {
		if (gen)
			setPageButtonType(PAGE_BUTTON_TYPE_TEXT);
		else
			setPageButtonType(PAGE_BUTTON_TYPE_SUBMIT);
	}

	/**
	 * This method sets the minimum width of the table in either pixels or
	 * percent depending on size option.
	 */
	public void setWidth(int width) {
		_containerWidth = width;
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @param comp
	 *            com.salmonllc.html.HtmlComponent
	 */
	protected void sortOnComponent(HtmlComponent comp, int colNo) throws Exception {
		if (colNo == _sortColumn) {
			if (_sortDir == DataStore.SORT_ASC)
				_sortDir = DataStore.SORT_DES;
			else
				_sortDir = DataStore.SORT_ASC;
		} else
			_sortDir = DataStore.SORT_ASC;
		_sortColumn = colNo;
		int col = 0, dir = 0;
		if (comp instanceof HtmlFormComponent) {
			int dsCol = ((HtmlFormComponent) comp).getColumnNumber();
			col = dsCol;
			dir = _sortDir;
		} else if (comp instanceof HtmlText) {
			DataStoreEvaluator eval = ((HtmlText) comp).getExpressionEvaluator();
			String exp = eval.getExpression();
			int index = -1;
			if (exp != null)
				index = _ds.getColumnIndex(exp);
			if (index > -1) {
				col = index;
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

	/**
	 * A true false value indicating whether the heading band should be
	 * reprinted after a page break
	 */

	public boolean getRepeatHeadingsAfterBreak() {
		return _repeatHeadingsAfterBreak;
	}

	/**
	 * A true false value indicating whether the heading band should be
	 * reprinted after a page break
	 */
	public void setRepeatHeadingsAfterBreak(boolean repeatHeadingsAfterBreak) {
		_repeatHeadingsAfterBreak = repeatHeadingsAfterBreak;
	}

	/**
	 * Sets the type of paging buttons for the data table. Valid Types are
	 * PAGE_BUTTON_TYPE_SUBMIT, PAGE_BUTTON_TYPE_IMAGE, PAGE_BUTTON_TYPE_TEXT,
	 * PAGE_BUTTON_TYPE_SCROLLBAR
	 */

	public void setPageButtonType(int type) {
		_pageButtonType = type;
		if (_pageButtonType == PAGE_BUTTON_TYPE_IMAGE)
			getPage().registerImageGenerator(getFullName(), this);
		else if (_pageButtonType == PAGE_BUTTON_TYPE_SCROLLBAR)
			setRowsPerPage(-1);
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
	 * Sets a new render to draw the HTML for the table page selector, given the
	 * class name for the renderer
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
	 * Gets the type of paging buttons for the data table. Valid Types are
	 * PAGE_BUTTON_TYPE_SUBMIT, PAGE_BUTTON_TYPE_IMAGE, PAGE_BUTTON_TYPE_TEXT
	 */

	public int getPageButtonType() {
		return _pageButtonType;
	}

	/**
	 * Gets a vector containing the components in the data table heading.
	 */
	protected Vector getHeadingComponents() {
		return _headingComponents;
	}

	/**
	 * Gets a vector containing the components in the data table footer.
	 */
	protected Vector getFooterComponents() {
		return _footerComponents;
	}

	/**
	 * Gets a vector containing the components in a data table row.
	 */
	protected Vector getRowComponents() {
		return _rowComponents;
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
	 * @param string
	 *            sets the default style sheet class for table rows
	 */
	public void setRowStyleClassName1(String string) {
		_defaultRowStyleClassName1 = string;
	}

	/**
	 * @param string
	 *            sets the default style sheet class for table alternate rows
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
	 * @param string
	 *            sets the default style sheet class for heading rows
	 */
	public void setHeadingStyleClassName(String string) {
		_defaultHeadingStyleClassName = string;
	}
	/**
	 * @return Returns true if the component will generate THEAD, TBODY and
	 *         TFOOT tags around the sections of the table.
	 */
	public boolean getGenTHeadAndBody() {
		return _genTHeadAndBody;
	}
	/**
	 * @param true
	 *            if the component will generate THEAD, TBODY and TFOOT tags
	 *            around the sections of the table.
	 */
	public void setGenTHeadAndBody(boolean genTHeadAndBody) {
		_genTHeadAndBody = genTHeadAndBody;
	}
	/**
	 * @return Returns the table height (used for tables with
	 *         pageButtonType=PAGE_BUTTON_TYPE_SCROLLBAR).
	 */
	public int getTableHeight() {
		return _tableHeight;
	}
	/**
	 * @param Sets
	 *            the table height (used for tables with
	 *            pageButtonType=PAGE_BUTTON_TYPE_SCROLLBAR).
	 */
	public void setTableHeight(int tableHeight) {
		_tableHeight = tableHeight;
	}
}