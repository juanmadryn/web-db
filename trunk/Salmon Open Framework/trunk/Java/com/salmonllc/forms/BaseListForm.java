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
package com.salmonllc.forms;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/forms/BaseListForm.java $
//$Author: Dan $
//$Revision: 33 $
//$Modtime: 6/11/03 4:35p $
/////////////////////////


import com.salmonllc.html.*;
import com.salmonllc.html.events.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implements Search/List form. At the top will be a display box with a table for search criteria and a "Search" button. Following will be another display box with a datatable to display the results from the search.  This box contains a "Add" button which requests the detail page (name supplied) with the parameters "mode=add", and "<primary_key>=<value>", the second repeated for as many primary keys as there are. The client of this class will add data-store columns, specifying whether they go in the search box, list box, or both. <BR> The form is implemented as a container to go within an instance of (a subclass of) HtmlPage.  Specify as many data store columns as you want using the addColumn* methods and indicate which goes in the search box and which goes in the list box. <BR> To hook key activities such as the "Add" button, implement the ListFormListener interface and call addListener. <BR> Example: <BR> <PRE> ListForm form = new ListForm(this, "UserDetailPage"); add(form); form.addColumn(_table, "user_id", "ID#", DataStoreBuffer.DATATYPE_INT, form.PRIMARY_KEY, "UserDetailPage?mode=update"); // add other columns ... </PRE>
 */
public class BaseListForm extends BaseForm implements SubmitListener {
    // Constant definitions for flags parameter addColumn methods
    /** addColumn flags parameter:  Only in search box. */
    public static final int SEARCH_ONLY = 1 << 0;
    /** addColumn flags parameter:  Only in list box. */
    public static final int LIST_ONLY = 1 << 1;
    /** addColumn flags parameter:  Primary key. */
    public static final int PRIMARY_KEY = 1 << 2;
    /** addColumn flags parameter:  Do not start a new row in search box. */
    public static final int SAME_ROW = 1 << 3;
    /** addColumn flags parameter:  Takes precedence in search.  Ignore other search criteria if this one is not empty. */
    public static final int PRECEDENCE = 1 << 4;
    /** addColumn flags parameter:  Use exact match for string value, not LIKE. */
    public static final int EXACT_MATCH = 1 << 5;
    /** addColumn flags parameter:  Do not process in retrieve (search). */
    public static final int NO_SEARCH = 1 << 6;
    /** addColumn flags parameter:  Use exact match for string value, not LIKE. */
    public static final int IGNORE_CASE = 1 << 7;
    /** addColumn flags parameter:  Places wildcard at the beginning */
    public static final int LEADING_WILDCARD = 1 << 8;
    /** addColumn flags parameter: Use in advanced search.Default to top of search box */
    public static final int ADVANCED_SEARCH = 1 << 9;


    // Constant definitions for flags parameter in constructor */
    /** constructor flags parameter:  Do not create the default Add button. */
    public static final int INIT_NO_ADD_BUTTON = 1 << 0;
    /** constructor flags parameter:  Do not create the default Search button.  */
    public static final int INIT_NO_SEARCH_BUTTON = 1 << 1;
    /** constructor flags parameter:  Create the Advanced Search Link.  */
    public static final int INIT_ADVANCED_SEARCH_LINK = 1 << 2;
    /** addColumn flags parameter: Location of advanced search link */
    public static final int INIT_ADVANCED_SEARCH_ON_SIDE = 1 << 3;


    // Constant to deal with if you add a column with a null table
    public static final String NULL_TABLE = "NULL_TABLE";

    // Components are maintained in a vector of BaseListFormComponent's:
    // sr 12-01-2000 OLD WAY	// 1 - Name, 2 - search component, 3 - list component, 4 - search component caption
    protected BaseListFormComponentVector _elements = new BaseListFormComponentVector();

    // Container compont vector
    //	protected Vector _containerComponents = new Vector();

    // Precedence components - vector of Integer indexes in to _elements
    protected Vector _precedenceList = new Vector();
    // Page Parameters
    protected Vector _pageParamsKeys = new Vector(1, 1);
    protected Vector _pageParamsValues = new Vector();

    // Other
    protected Vector _flags = new Vector();
    protected HtmlDisplayBox _boxSearch;
    protected HtmlDisplayBox _boxList;
    protected HtmlComponent _btnAddListForm = null;
    protected HtmlComponent _btnSearchListForm = null;
    protected HtmlTable _tblSearch;
    protected HtmlDataTable _tblList;
    protected int _rowSearch = -1;
    protected int _colSearch = -1;
    protected int _rowList = 0;
    // sr 11-3-2000	protected String _detailPageName;
    protected Vector _listeners = new Vector();
    protected SimpleDateFormat _dateFormat[];
//	protected SalmonDateFormat _dateFormat[];
    protected Vector _searchList = null;
    protected String _linkFont = HtmlText.FONT_LINK;
    //
    private HtmlText _htAdvSearch;
    private String _advancedSearchText;
    private String _basicSearchText;
    private HtmlLink _hlAdvSearch;
    private boolean _advancedSearch = true;
    private int _advancedSearchFlag;

    /**
     * Implements standard Search/List form.
     * Default data store is created.  Standard add button is included.
     * @param page 	Page containing this form as a component.
     * @param detailPageName Name of associated detail page.
     */
    public BaseListForm(HtmlPage page) {
        this(page, null, 0);
    }

    /**
     * Implements standard Search/List form.
     * Standard add button is included.
     * @param page HtmlPage			Page containing this form as a component.
     * @param detailPageName String	Name of corresponding detail page to use as link destination.
     * @param ds DataStore			Data store object to use; if null then create one.
     */
    public BaseListForm(HtmlPage page, DataStore ds) {
        this(page, ds, 0);
    }

    /**
     * Implements standard Search/List form.
     * @param page				Page containing this form as a component.
     * @param detailPageName	Name of corresponding detail page to use as link destination.
     * @param ds				Data store object to use; if null then create one.
     * @param flags 			Bitwise-OR combination of INIT_NO_SEARCH_BUTTON, etc.
     */
    public BaseListForm(HtmlPage page, DataStore ds, int flags) {
        super("", page, ds);

        // Make an advanced search link just above display box
        if ((flags & INIT_ADVANCED_SEARCH_LINK) != 0) {
            _advancedSearchText = "Advanced Search";
            _basicSearchText = "Basic Search";
            //
            _htAdvSearch = new HtmlText(_advancedSearchText, page);
            _hlAdvSearch = new HtmlLink("SearchType", "", null, page);
            _hlAdvSearch.add(_htAdvSearch);
            _hlAdvSearch.addSubmitListener(this);
            add(_hlAdvSearch);
        }

        // Define Search display box
        add(_boxSearch = new HtmlDisplayBox("boxSearch", page));
        _boxSearch.setWidth(-1);
        _boxSearch.setHeadingCaption("Search criteria");
        if ((flags & INIT_NO_SEARCH_BUTTON) == 0) {
            HtmlSubmitButton b = new HtmlSubmitButton("btnSearch", "Search", page);
            b.addSubmitListener(this);
            _boxSearch.addHeadingComponent(_btnSearchListForm = b);
        }

        // Make an advanced search link just next to search button
        if ((flags & INIT_ADVANCED_SEARCH_ON_SIDE) != 0) {
            _advancedSearchFlag = INIT_ADVANCED_SEARCH_ON_SIDE; // Need to check the flag in setSearchImage() method.
            _advancedSearchText = "Advanced Search";
            _basicSearchText = "Basic Search";
            //
            _htAdvSearch = new HtmlText(_advancedSearchText, page);
            _hlAdvSearch = new HtmlLink("SearchType", "", null, page);
            _hlAdvSearch.add(_htAdvSearch);
            _hlAdvSearch.addSubmitListener(this);
            _boxSearch.addHeadingComponent(_hlAdvSearch);
        }

        _tblSearch = new HtmlTable("tblSearch", page);
        _boxSearch.setBoxComponent(_tblSearch);
        // Search box stays hidden until a search column is added.
        _boxSearch.setVisible(false);
        add(_line_break);

        // Define List display box
        add(_boxList = new HtmlDisplayBox("boxList", page));
        _boxList.setHeadingCaption("Search results");

        // Add button should stay in parent class so you do not have to duplicate the logic in children forms.
        // The SubmitPerformed of the child classes should handle the different behavior.
        if ((flags & INIT_NO_ADD_BUTTON) == 0) {
            HtmlSubmitButton b = new HtmlSubmitButton("btnAdd", "Add", page);
            _boxList.addHeadingComponent(b);
            b.addSubmitListener(this);
            _btnAddListForm = b;
        }

        //
        _tblList = new HtmlDataTable("tblList", _ds, page);
        _boxList.setBoxComponent(_tblList);


        // As a favor to client classes, add page as listener if appropriate
        // sr 11-3-2000	if (page instanceof ListFormListener)
        // sr 11-3-2000		addListener((ListFormListener)page);



        // Stupid date format parsing
        Props p = page.getPageProperties();
        String s;
        Vector v = new Vector();
        if ((s = p.getProperty(Props.DATE_FORMAT)) != null)
            v.addElement(new SimpleDateFormat(s));
        if ((s = p.getProperty(Props.DATETIME_FORMAT)) != null)
            v.addElement(new SimpleDateFormat(s));
        v.addElement(new SimpleDateFormat());
        v.addElement(new SimpleDateFormat("MMM dd, yyyy"));
        v.addElement(new SimpleDateFormat("MMM dd yyyy"));
        v.addElement(new SimpleDateFormat("MMM dd, yyyy hh:mm aaa"));
        _dateFormat = new SimpleDateFormat[v.size()];
        for (int i = 0; i < v.size(); i++)
            _dateFormat[i] = (SimpleDateFormat) v.elementAt(i);
    }

    /**
     * Creates a datastore bucket and corresponding display components for the search and list
     * boxes.
     * @param name		Name of bucket
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     */
    public void addBucket(String name, String caption, int type, int flags) throws Exception {
        addBucket(name, caption, type, flags, null, null);
    }

    /**
     * Creates a datastore bucket and corresponding display components for the search and list
     * boxes.
     * @param name		Name of bucket
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param href		HREF to use in hotlink from this column in list box, else null.
     * @param format	Format used to display column in listbox (as in DataStore), else null.
     */
    public void addBucket(String name, String caption, int type, int flags, String href, String format) throws Exception {
        addBucket(name, caption, type, flags, href, format, null, null, null);
    }

/**
 * Creates a datastore bucket and corresponding display components for the search and list
 * boxes.
 * @param name		Name of bucket
 * @param caption	Caption for search box and/or list box, or null
 * @param type		Type of datastore column, using DataStore.DATATYPE_* values
 * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
 * @param href		HREF to use in hotlink from this column in list box, else null.
 * @param format	Format used to display column in listbox (as in DataStore), else null.
 * @param propCaption HtmlTableCellProperties
 * @param propSearch HtmlTableCellProperties
 * @param propList HtmlTableCellProperties
 */
public void addBucket(String name, String caption, int type, int flags, String href, String format, HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch, HtmlTableCellProperties propList) throws Exception
{
    if (_ds.getColumnIndex(name) == -1)
        _ds.addBucket(name, type);
    if ((flags & LIST_ONLY) == 0)
        addSearchDisplay(name, caption, makeComponent(name, type), flags | NO_SEARCH, propCaption, propSearch);
    if ((flags & SEARCH_ONLY) == 0)
        {
        HtmlLink hl;
        HtmlPage page = getPage();
        HtmlText ht = new HtmlText("", page);
        if (format == null)
            {
            // For certain data types, set format according to page properties.
            Props props = getPage().getPageProperties();
            switch (type)
                {
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
        if (format != null)
            ht.setExpression(_ds, name, format);
        else
            ht.setExpression(_ds, name);
        if (Util.isFilled(href))
            {
            hl = new HtmlLink("lnk" + name, "", page);
            hl.setHrefExpression(_ds, href);
            hl.add(ht);
            ht.setFont(_linkFont);
            addListDisplay(name, caption, hl, propList);
        }
        else
            addListDisplay(name, caption, ht, propList);
    }
}

    /**
     * Creates a datastore column and corresponding display components for the search and list
     * boxes.
     * @param table		Name of table for datastore
     * @param column 	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     */
    public void addColumn(String table, String column, String caption, int type,
                          int flags) throws Exception {
        addColumn(table, column, caption, type, flags, null);
    }

    /**
     * Creates a datastore column and corresponding display components for the search and list
     * boxes.
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param href		HREF to use in hotlink from this column in list box, else null.
     */
    public void addColumn(String table, String column, String caption, int type,
                          int flags, String href) throws Exception {
        addColumn(table, column, caption, type, flags, href, null);
    }

    /**
     * Creates a datastore column and corresponding display components for the search and list
     * boxes.
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param href		HREF to use in hotlink from this column in list box, else null.
     * @param format	Format used to display column in listbox (as in DataStore), else null.
     */
    public void addColumn(String table, String column, String caption, int type, int flags, String href, String format) throws Exception {
        addColumn(table, column, caption, type, flags, href, format, null, null, null);
    }

/**
 * Creates a datastore column and corresponding display components for the search and list
 * boxes.
 * @param table		Name of table for datastore. If table == null then table will be forced to "NULL_TABLE"
 * @param column	Name of column for datastore
 * @param caption	Caption for search box and/or list box, or null
 * @param type		Type of datastore column, using DataStore.DATATYPE_* values
 * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
 * @param href		HREF to use in hotlink from this column in list box, else null.
 * @param format	Format used to display column in listbox (as in DataStore), else null.
 * @param propCaption HtmlTableCellProperties
 * @param propSearch HtmlTableCellProperties
 * @param propList HtmlTableCellProperties
 */
public void addColumn(String table, String column, String caption, int type, int flags, String href, String format, HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch, HtmlTableCellProperties propList) throws Exception
{
    if (table == null)
        {
        table = NULL_TABLE;
    }
    String fullColName = table + "." + column;

    // Don't add the same column twice to the data store.
    if (_ds.getColumnIndex(table + "." + column) == -1)
        _ds.addColumn(table, column, type, (flags & PRIMARY_KEY) != 0, false);
    if ((flags & LIST_ONLY) == 0)
        addSearchDisplay(fullColName, caption, makeComponent(table + "_" + column, type), flags, propCaption, propSearch);
    if ((flags & SEARCH_ONLY) == 0)
        {
        HtmlLink hl;
        HtmlPage page = getPage();
        HtmlText ht = new HtmlText(table + "_" + column, page);
        if (format == null)
            {
            // For certain data types, set format according to page properties.
            Props props = getPage().getPageProperties();
            switch (type)
                {
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
        if (format != null)
            ht.setExpression(_ds, fullColName, format);
        else
            ht.setExpression(_ds, fullColName);
        if (Util.isFilled(href))
            {
            hl = new HtmlLink("lnk" + column, "", page);
            hl.setHrefExpression(_ds, href);
            hl.add(ht);
            ht.setFont(_linkFont);
            addListDisplay(fullColName, caption, hl, propList);
        }
        else
            addListDisplay(fullColName, caption, ht, propList);
    }
}

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.

     */
    public void addComposite(HtmlComposite composite, String caption, int flags) throws Exception {
        // go through components
        // if they are bound create DataStore Columns
        // and find out what type each column is
        Enumeration enumera = composite.getComponents();
        String table = null;
        String column = null;
        HtmlComponent comp = null;
        int dataType = -1;
        while (enumera.hasMoreElements()) {
            comp = (HtmlComponent) enumera.nextElement();
            if (composite.getIsComponentBound(comp)) {
                dataType = composite.getComponentDataType(comp);
                table = composite.getComponentTable(comp);
                column = composite.getComponentColumn(comp);

                // Don't add the same column twice to the data store.
                if (_ds.getColumnIndex(table + "." + column) == -1) {
                    _ds.addColumn(table, column, dataType, (flags & PRIMARY_KEY) != 0, false);
                }
            }
        }
        //
        if ((flags & LIST_ONLY) == 0) {
            addSearchDisplay(composite.getName(), caption, composite, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            addListDisplay(composite.getName(), caption, composite);
        }
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type date-time.  The search display is a pair of
     * entry fields corresponding to a range of dates with captions "from"
     * and "to".  If the "to" date is empty then the search criteria is
     * >= the "from" date; if the "from" date is empty then the criteria is
     * <= the "to" date.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param format	Format used to display column in listbox (as in DataStore), else null.
     */
    public void addDateRange(String table, String column, String caption, int flags, String format) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_DATETIME, (flags & PRIMARY_KEY) != 0, false);
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        HtmlPage page = getPage();
        HtmlText ht;
        if ((flags & LIST_ONLY) == 0)
            addSearchDisplay(fullColName, caption, new DateRange(table + "_" + column, page), flags);
        if ((flags & SEARCH_ONLY) == 0) {
            ht = new HtmlText(table + "_" + column, page);
            ht.setExpression(_ds, fullColName, format);
            addListDisplay(fullColName, caption, ht);
        }
    }

    /**
     * Adds a given component to heading of list box.
     *
     * @deprecated	Use getListBox().addHeadingComponent().
     */
    public void addListBoxHeadingComponent(HtmlComponent c) {
        if (_boxList != null)
            _boxList.addHeadingComponent(c);
    }

    /**
     * Adds a component to the List display.
     *
     * @param fullColName	Datastore column name in form table.column
     * @param caption		Listbox caption, or null
     * @param component HtmlComponent	The component to add
     */
    public void addListDisplay(String fullColName, String caption, HtmlComponent component)
            throws Exception {
        addListDisplay(fullColName, caption, component, null);
    }

    /**
     * Adds a component to the List display.
     *
     * @param fullColName	Datastore column name in form table.column
     * @param caption		Listbox caption, or null
     * @param component HtmlComponent	The component to add
     * @param properties HtmlTableCellProperties	Table cell properties
     */
    public void addListDisplay(String fullColName, String caption, HtmlComponent component, HtmlTableCellProperties prop) throws Exception {

        //if (caption != null)
        //{
        //_tblList.setHeadingComponentAt(_rowList, new HtmlText(caption, HtmlText.FONT_TABLE_HEADING, getPage()), prop);
        //}

        //if (prop != null)
        //{
        //_tblList.setRowComponentAt(_rowList++, component, prop);
        //}
        //else
        //{
        //_tblList.setRowComponentAt(_rowList++, component);
        //}
        _tblList.addDisplay(fullColName, caption, component, prop);
        ((BaseListFormComponent) _elements.elementAt(findOrAdd(fullColName))).setListComponent(component);
    }

    /**
     * Adds a ListFormListener.
     *
     * @param listener ListFormListener
     */
    public void addListener(ListFormListener listener) {
        // Prevent listener from being added twice
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++)
            if (((ListFormListener) _listeners.elementAt(i)) == listener)
                return;
        _listeners.addElement(listener);
    }

    /**
     * Adds to a list of Parameters to pass on during an add operation.
     * @param pageParam		Parameter to pass on during an add operation
     */
    public void addParameterKey(String pageParam) {
        //	check to see if the param already exsists
        // yes: just return
        // no: add it
        if (_pageParamsKeys.indexOf(pageParam) != -1) {
            return;
        } else {
            // add  pageParam to the _pageParamsKeys Vector
            _pageParamsKeys.addElement(pageParam);
        }
    }

    /**
     * Adds a given component to heading of search box.
     *
     * @deprecated	Use getSearchBox().addHeadingComponent().
     */
    public void addSearchBoxHeadingComponent(HtmlComponent c) {
        if (_boxSearch != null)
            _boxSearch.addHeadingComponent(c);
    }

    /**
     * Creates an entry component for the search box and returns it.  The data store is not
     * affected.
     * @param name		Name to use for component.
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     */
    public HtmlFormComponent addSearchComponent(String name, String caption, int type, int flags) throws Exception {
        HtmlFormComponent ret = makeComponent(name, type);
        addSearchDisplay(null, caption, ret, flags);
        return ret;
    }

/**
 * Retrieve from database table into datastore according to search criteria.
 */
public void doRetrieve()
{
    try
        {
        int listenersSize = _listeners.size();

        for (int i = 0; i < listenersSize; i++)
            if (!((ListFormListener) _listeners.elementAt(i)).preListRetrieve())
                return;

        // if the datastore has no columns then a retrieve will not work.
        if (_ds.getColumnCount() == 0)
            {
            //	return early otherwise an exception will be thrown
            return;
        }
        String criteria = getSearchCriteria();
        _ds.retrieve(criteria);
        _ds.waitForRetrieve();
        for (int i = 0; i < listenersSize; i++)
            if (!((ListFormListener) _listeners.elementAt(i)).postListRetrieve())
                return;
    }
    catch (Exception e)
        {
        MessageLog.writeErrorMessage("doRetrieve", e, this);
    }
}

    /**
     * Gets the object container matching the name.  If not existing, create a new
     * one and add to list.
     * @return	Index of container in list.
     * @param name java.lang.String
     */
    protected int findOrAdd(String name) {
        BaseListFormComponent blfc;
        int i;
        int elementsSize = _elements.size();
        for (i = 0; i < elementsSize; i++) {
            blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc.getName() != null && blfc.getName().equals(name)) {
                //return i;
            }
        }
        // create a new instance and put in element vector
        blfc = new BaseListFormComponent();
        blfc.setName(name);
        _elements.addElement(blfc);
        //
        return i;
    }

    /**
     * Get advanced caption of the Advanced Search Link.
     *
     */
    public String getAdvancedSearchCaption() {
        return _advancedSearchText;
    }

    /**
     * Returns a list of all search components.
     * @return Enumeration
     */
    public Enumeration getBaseListFormComponents() {
        // WARNING:  Do not attempt to optimize this by storing the result in an
        // instance variable and reusing it, since an Enumeration is supposed to be used
        // one time only.
        Vector v = new Vector();
        int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc != null)
                v.addElement(blfc);
        }
        return v.elements();
    }

    /**
     * Get the Basic caption of the Advanced Search Link.
     *
     */
    public String getBasicSearchCaption() {
        return _basicSearchText;
    }



    /**
     * Return the internal DataStore object.
     * @return com.salmonllc.sql.DataStore
     */
    public DataStore getDataStore() {
        return _ds;
    }

    /**
     * Returns display box component for list box.
     * @return com.salmonllc.html.HtmlDisplayBox
     */
    public HtmlDisplayBox getListBox() {
        return _boxList;
    }

    /**
     * Returns component in list box associated with given name, or null.
     *
     * @return com.salmonllc.html.HtmlComponent
     * @param name String 	Name in form table.column
     */
    public HtmlComponent getListComponent(String name) {

	    int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc.getName().equals(name) && blfc.getListComponent() != null) {
                return blfc.getListComponent();
            }
        }

        return null;
   
    }

    /**
     * Returns the data-table instance used for list box.
     *
     * @return HtmlDataTable
     */
    public HtmlDataTable getListTable() {
        return _tblList;
    }

    /**
     * Returns display box component for search box.
     * @return com.salmonllc.html.HtmlDisplayBox
     */
    public HtmlDisplayBox getSearchBox() {
        return _boxSearch;
    }

    /**
     * Returns component in search box associated with given name, or null.
     *
     * @return com.salmonllc.html.HtmlComponent
     * @param name java.lang.String		Name in form table.column
     */
    public HtmlComponent getSearchComponent(String name) {

        int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc.getName().equals(name) && blfc.getSearchComponent() != null) {
                return blfc.getSearchComponent();
            }
        }

        return null;
    }

    /**
     * Returns a list of all search components.
     * @return Enumeration
     */
    public Enumeration getSearchComponents() {
        // WARNING:  Do not attempt to optimize this by storing the result in an
        // instance variable and reusing it, since an Enumeration is supposed to be used
        // one time only.
        Vector v = new Vector();
        int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            HtmlComponent c = blfc.getSearchComponent();
            if (c != null)
                v.addElement(c);
        }
        return v.elements();
    }

    /**
     * Does the work of creating a component according to type.
     * @return com.salmonllc.html.HtmlFormComponent
     * @param name java.lang.String
     * @param type int
     */
    protected HtmlFormComponent makeComponent(String name, int type) {
        HtmlTextEdit hte = new HtmlTextEdit(name, getPage());
        hte.setMaxLength(25);
        switch (type) {
            case DataStore.DATATYPE_BYTEARRAY:
            case DataStore.DATATYPE_STRING:
                hte.setSize(20);
                hte.setMaxLength(50);
                break;
            case DataStore.DATATYPE_INT:
            case DataStore.DATATYPE_SHORT:
            case DataStore.DATATYPE_LONG:
                hte.setSize(10);
                break;
            case DataStore.DATATYPE_DATETIME:
            case DataStore.DATATYPE_DATE:
            case DataStore.DATATYPE_TIME:
                hte.setSize(15);
                break;
            case DataStore.DATATYPE_DOUBLE:
            case DataStore.DATATYPE_FLOAT:
                hte.setSize(10);
                break;
            default:
                hte.setSize(10);
                break;
        }
        return hte;
    }

    /**
     * Creates an integer-type radio-button group list like HtmlComponenFactory but tailored
     * to needs of the forms subclasses.
     * @return HtmlRadioButtonGroup
     * @param name java.lang.String
     * @param values int[]
     * @param displayValues java.lang.String[]
     * @param defaultVal java.lang.String
     */
    protected HtmlRadioButtonGroup newRadioButtonGroup(String name, int values[], String displayValues[], String defaultVal) {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(name, getPage());
        //
        int valuesSize = values.length;
        String displayValue = null;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valuesSize; i++) {
            if (i < displayValuesLength)
                displayValue = displayValues[i];
            else
                displayValue = "Value " + i;
            rbg.addOption(new Integer(values[i]).toString(), displayValue);
        }
        // set to default value
        if (defaultVal != null) {
            rbg.setValue(defaultVal);
        }
        return rbg;
    }

    /**
     * This event will get fired each time a page is requested by the browser.
     */
    public void pageRequested(PageEvent p) throws Exception {
        super.pageRequested(p);
        HtmlPage page = getPage();
        if (!page.isReferredByCurrentPage() && ((page.getParameter("refresh") != null) || !_boxSearch.getVisible()))
            doRetrieve();
    }

    /**
     * This method was created to parse a date string
     * and return a valid java.util.Date object .
     * @return java.util.Date
     * @param sValue java.lang.String
     */
    protected Date parseDate(String sValue) throws ParseException {
        ParseException ee = null;
        Date date = null;
        for (int i = 0; i < _dateFormat.length; i++) {
            try {
                date = _dateFormat[i].parse(sValue);
                return date;
            } catch (ParseException e) {
                ee = e;
            }
        }
        // Throw the last exception encountered
        throw ee;
    }

    /**
     * Process a search component.
     * @param i 	0-based index to _elements
     * @param cr	CriteriaString
     */
    protected void processSearchComponent(int i, CriteriaString cr) throws Exception {
        BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
        HtmlComponent c = blfc.getSearchComponent();

        // !c.getVisible(true) is checking if the component or its parent is visible
        // if not it should not be used in the search criteria
        if (c == null || !c.getVisible(true)) {
            return;
        }
        int flags;
        try {
            flags = ((Integer) _flags.elementAt(i)).intValue();
        } catch (Exception e) {
            flags = 0;
        }
        //
        if ((flags & NO_SEARCH) != 0) {
            return;
        }
        //
        String name = blfc.getName();
        // check to see if c is a container
        /// yes: act on all contained components

        if (c instanceof HtmlComposite) {
            MessageLog.writeDebugMessage(" Container: ", this);
            processSearchCompositeComponent((HtmlComposite) c, cr);
            return;
        }
        if (c instanceof DateRange) {
            DateRange dr = (DateRange) c;
            String sDateFrom = dr.getFromDate();
            String sDateTo = dr.getToDate();
            if (sDateTo == null) {
                if (sDateFrom == null)
                    return;
                cr.and(name + " >= " + _ds.formatDateTime(parseDate(sDateFrom)));
            } else if (sDateFrom == null) {
                if (sDateTo == null)
                    return;
                cr.and(name + " <= " + _ds.formatDateTime(parseDate(sDateTo)));
            } else {
                cr.and(name + " BETWEEN " + _ds.formatDateTime(parseDate(sDateFrom)) + " AND " + _ds.formatDateTime(parseDate(sDateTo)));
            }
            return;
        }
        HtmlFormComponent searchComponent;
        try {
            if (c instanceof HtmlFormComponent) {
                searchComponent = (HtmlFormComponent) c;
            } else {
                return;
            }
        } catch (ClassCastException e) {
            MessageLog.writeErrorMessage("processSearchComponent", e, this);
            return;
        }
        String sValue = searchComponent.getValue();
        if ((sValue == null) || (sValue.length() == 0))
            return;
        try {
            switch (_ds.getColumnDataType(name)) {
                case DataStore.DATATYPE_INT:
                case DataStore.DATATYPE_SHORT:
                case DataStore.DATATYPE_LONG:
                    // Assume that -1 corresponds to no selection
                    if (sValue.equals("-1") && (searchComponent instanceof HtmlDropDownList))
                        return;
                    // fall through
                case DataStore.DATATYPE_FLOAT:
                case DataStore.DATATYPE_DOUBLE:
                    cr.and(name + "=" + sValue);
                    break;
                case DataStore.DATATYPE_STRING:
                    if ((flags & EXACT_MATCH) != 0)
                        cr.and(name + "='" + _ds.fixQuote(sValue) + "'");
                    else {
                        String s = ((flags & LEADING_WILDCARD) != 0) ? "%" : "";
                        if ((flags & IGNORE_CASE) != 0)
                            cr.and("upper(" + name + ") like '" + s + _ds.fixQuote(sValue.toUpperCase()) + "%'");
                        else
                            cr.and(name + " like '" + s + _ds.fixQuote(sValue) + "%'");
                    }
                    break;
                case DataStore.DATATYPE_DATETIME:
                    cr.and(name + " = " + _ds.formatDateTime(parseDate(sValue)));
                    break;
                default :
                    throw new FormException("Unknown datatype: " + _ds.getColumnDataType(name));
            }
        } catch (DataStoreException e) {
            // For example, if the name does not match a data store column.  This is
            // OK for user-supplied names.
        }
    }

    /**
     * Process a search component.
     * @param composite com.salmonllc.html.HtmlComposite look through composite and set criteria for each component inside
     * @param cr	CriteriaString
     */
    protected void processSearchCompositeComponent(HtmlComposite composite, CriteriaString cr) throws Exception {
        java.util.Enumeration enumera = composite.getComponents();
        int flags;
        //
        HtmlComponent compositeComp = null;
        String name = null;
        while (enumera.hasMoreElements()) {
            compositeComp = (HtmlComponent) enumera.nextElement();
            // name should be table.column ex. (ENTITY.CUSTOMER)
            name = compositeComp.getName();

            // the submit image can never be in a search criteria
            if (compositeComp instanceof HtmlSubmitImage) {
                continue;
            }
            // if component we are looking at is also a composite call recursively
            if (compositeComp instanceof HtmlComposite) {
                processSearchCompositeComponent((HtmlComposite) compositeComp, cr);
            }
            // each component in a composite can hav flas associated with it
            try {
                flags = composite.getComponentFlags(compositeComp);
            } catch (Exception e) {
                flags = 0;
            }

            // cast to HtmlFormComponent so we can do .getValue on component
            HtmlFormComponent searchComponent;
            try {
                searchComponent = (HtmlFormComponent) compositeComp;
            } catch (ClassCastException e) {
                return;
            }
            String sValue = searchComponent.getValue();
            if ((sValue == null) || (sValue.length() == 0))
                return;
            switch (composite.getComponentDataType(compositeComp)) {
                case DataStore.DATATYPE_INT:
                case DataStore.DATATYPE_SHORT:
                case DataStore.DATATYPE_LONG:
                    // Assume that -1 corresponds to no selection
                    if (sValue.equals("-1") && (searchComponent instanceof HtmlDropDownList))
                        return;
                    // fall through
                case DataStore.DATATYPE_FLOAT:
                case DataStore.DATATYPE_DOUBLE:
                    cr.and(name + "=" + sValue);
                    break;
                case DataStore.DATATYPE_STRING:
                    if ((flags & EXACT_MATCH) != 0) {
                        cr.and(name + "='" + _ds.fixQuote(sValue) + "'");
                    } else {
                        String s = ((flags & LEADING_WILDCARD) != 0) ? "%" : "";
                        if ((flags & IGNORE_CASE) != 0)
                            cr.and("upper(" + name + ") like '" + s + _ds.fixQuote(sValue.toUpperCase()) + "%'");
                        else
                            cr.and(name + " like '" + s + _ds.fixQuote(sValue) + "%'");
                    }
                    break;
                case DataStore.DATATYPE_DATETIME:
                    cr.and(name + " = " + _ds.formatDateTime(parseDate(sValue)));
                    break;
                default :
                    throw new FormException("Unknown datatype: " + name);
            }
        }
    }

    /**
     * Removes param from  list of parameters
     * @param pageParam		Parameter to pass on during an add operation
     */
    public void removeParameterKey(String pageParam) {
        // get the index of the parameter
        int keyIndex = _pageParamsKeys.indexOf(pageParam);
        //
        if (keyIndex == -1) {
            return;
        } else {
            _pageParamsKeys.removeElementAt(keyIndex);
        }
    }

    /**
     * Resets keys and values to empty.
     */
    public void resetParameters() {
        _pageParamsKeys.removeAllElements();
        _pageParamsValues.removeAllElements();
    }

    /**
     * Replaces default search button (if any) with a submit image
     * @param The SumbitImage to use for the search button.
     */
    public void setAddImage(HtmlSubmitImage img) {
        // Add Button
        if (_btnAddListForm != null) {
            _boxList.removeHeadingComponent(_btnAddListForm);
        }
        img.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnAddListForm = img);
    }

    /**
     * Replaces default add button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setAddImage(String imageUrl) {
        if (_btnAddListForm != null)
            _boxList.removeHeadingComponent(_btnAddListForm);
        HtmlSubmitImage i = new HtmlSubmitImage("btnAdd", imageUrl, getPage());
        i.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnAddListForm = i);
    }

    /**
     * Set caption of the Advanced Search Link.
     *
     */
    public void setAdvancedSearchCaption(String caption) {
        _advancedSearchText = caption;
    }

    /**
     * Set basic caption of the Advanced Search Link.
     *
     */
    public void setBasicSearchCaption(String caption) {
        _basicSearchText = caption;
    }

    /**
     * Sets the font used for HtmlLink components created by the form.  Default is
     * FONT_LINK.
     * @param font java.lang.String
     */
    public void setLinkFont(String font) {
        _linkFont = font;
    }

    /**
     * Set heading caption of list box.
     *
     * @deprecated	Use getListBox().setHeadingCaption().
     */
    public void setListBoxCaption(String caption) {
        if (_boxList != null)
            _boxList.setHeadingCaption(caption);
    }

    /**
     * Adds to a list of Parameters to pass on during an add operation.
     * @param pageParam	String	Parameter to associate key with
     * @param value String		Value of key
     * @param extend boolean	should we add another value to this key
     *							(ex. if extend ==true : &key1=abc&key1=def&key1=ghi)
     *							(ex. if extend ==false : &key1=abc)
     */
    public void setParameterValue(String pageParam, String value, boolean extend) {
        // get the index of the parameter
        int keyIndex = _pageParamsKeys.indexOf(pageParam);

        /*
        Create a value list vector. This is to take care of the fact that page parameters
        can be passed with the same key multiple times with different values
        ex. &key1=abc&key1=def&key1=ghi
        */
        Vector valVec = null;
        if (!_pageParamsValues.isEmpty()) {
            valVec = (Vector) _pageParamsValues.elementAt(keyIndex);
        }
        //
        if (valVec == null) {
            valVec = new Vector();
        }
        //
        if (extend) {
            valVec.addElement(value);
        } else {
            if (valVec.isEmpty()) {
                valVec.addElement(value);
            } else {
                valVec.setElementAt(value, 0);
            }
        }
        //
        if (_pageParamsValues.isEmpty()) {
            _pageParamsValues.addElement(valVec);
        } else {
            _pageParamsValues.setElementAt(valVec, keyIndex);
        }
    }

    /**
     * Set heading caption of search box.
     *
     * @deprecated	Use getSearchBox().setHeadingCaption().
     */
    public void setSearchBoxCaption(String caption) {
        if (_boxSearch != null)
            _boxSearch.setHeadingCaption(caption);
    }

    /**
     * Replaces default search button (if any) with a submit image
     * @param The SumbitImage to use for the search button.
     */
    public void setSearchImage(HtmlSubmitImage img) {
        if (_btnSearchListForm != null)
            _boxSearch.removeHeadingComponent(_btnSearchListForm);

        img.addSubmitListener(this);
        _boxSearch.addHeadingComponent(_btnSearchListForm = img);

        if ((_advancedSearchFlag & INIT_ADVANCED_SEARCH_ON_SIDE) != 0) {
            if (_hlAdvSearch != null)
                _boxSearch.removeHeadingComponent(_hlAdvSearch);

            _advancedSearchFlag = INIT_ADVANCED_SEARCH_ON_SIDE; // Need to check the flag in setSearchImage() method.
            _advancedSearchText = "Advanced Search";
            _basicSearchText = "Basic Search";
            //
            _htAdvSearch = new HtmlText(_advancedSearchText, getPage());
            _hlAdvSearch = new HtmlLink("SearchType", "", null, getPage());
            _hlAdvSearch.add(_htAdvSearch);
            _hlAdvSearch.addSubmitListener(this);
            _boxSearch.addHeadingComponent(_hlAdvSearch);
        }

    }

    /**
     * Replaces default search button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setSearchImage(String imageUrl) {
        if (_btnSearchListForm != null)
            _boxSearch.removeHeadingComponent(_btnSearchListForm);
        HtmlSubmitImage i = new HtmlSubmitImage("btnSearch", imageUrl, getPage());
        i.addSubmitListener(this);
        _boxSearch.addHeadingComponent(_btnSearchListForm = i);
    }

    /**
     * This method toggles the visiblity of the default add button.
     * @param visible boolean
     */
    public void showAddButton(boolean visible) {
        _btnAddListForm.setVisible(visible);
    }

    /**
     * This method toggles the visiblity of the advanced search components.
     * These are added with the flag set to ADVANCED_SEARCH
     * @param show boolean
     */
    private void showAdvancedSearchComps(boolean show) {
        int elementsSize = _elements.size();
        int flags;
        //
        HtmlComponent searchComp = null;
        HtmlComponent searchCapComp = null;
        for (int i = 0; i < elementsSize; i++) {
            try {
                flags = ((Integer) _flags.elementAt(i)).intValue();
            } catch (Exception e) {
                flags = 0;
            }

            //
            if ((flags & ADVANCED_SEARCH) != 0) {
                BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
                searchCapComp = blfc.getSearchCapComponent();
                searchComp = blfc.getSearchComponent();
                //
                searchCapComp.setVisible(show);
                searchComp.setVisible(show);
            }
        }
        MessageLog.writeDebugMessage("show advanced =" + show, null);
    }

    /**
     * This method toggles the visiblity of the default search button.
     * @param visible boolean
     */
    public void showSearchButton(boolean visible) {
        _btnSearchListForm.setVisible(visible);
    }

    /**
     * Inherited abstract method.
     * @return boolean
     * @param e com.salmonllc.html.events.SubmitEvent
     */
    public boolean submitPerformed(SubmitEvent e) throws Exception {
        MessageLog.writeDebugMessage(" submitPerformed(SubmitEvent e)", this);
        HtmlComponent c = e.getComponent();
        if (c == _btnSearchListForm) {
            doRetrieve();
        }

        //
        if (e.getSource() == _hlAdvSearch) {
            // Show or Hide either advanced search components or generic search components
            if (_advancedSearch) {
                showAdvancedSearchComps(_advancedSearch);
                _advancedSearch = false;
                _htAdvSearch.setText(_basicSearchText);
            } else {
                showAdvancedSearchComps(_advancedSearch);
                _advancedSearch = true;
                _htAdvSearch.setText(_advancedSearchText);
            }
            return true;
        }
        return true;
    }

    /**
     * Creates an integer-type radio-button group list like HtmlComponenFactory but tailored
     * to needs of the forms subclasses.
     * @return HtmlRadioButtonGroup
     * @param name java.lang.String
     * @param values int[]
     * @param displayValues java.lang.String[]
     * @param defaultVal java.lang.String
     */
    protected HtmlRadioButtonGroup newRadioButtonGroup(String name, String values[], String displayValues[], String defaultVal) {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(name, getPage());
        //
        int valuesSize = values.length;
        String displayValue = null;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valuesSize; i++) {
            if (i < displayValuesLength)
                displayValue = displayValues[i];
            else
                displayValue = "Value " + i;
            rbg.addOption(values[i], displayValue);
        }
        // set to default value
        if (defaultVal != null) {
            rbg.setValue(defaultVal);
        }
        return rbg;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addIntegerDropDown(String table, String column, String caption, //
                                            int flags, int values[], String displayValues[]) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }
        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addIntegerDropDown(String table, String column, String caption, //
                                            int flags, int values[], String displayValues[], boolean isMandatory) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);

            if (isMandatory)
                ddl.removeOption("");

            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }
        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addIntegerRadioButtonGroup(String table, String column, String caption, int flags, int values[], String displayValues[]) throws Exception {
        return addIntegerRadioButtonGroup(table, column, caption, flags, values, displayValues, null);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param defaultVal java.lang.String The defalt Radio Button you want selected
     */
    public HtmlComponent addIntegerRadioButtonGroup(String table, String column, String caption, int flags, int values[], String displayValues[], String defaultVal) throws Exception {
        return addIntegerRadioButtonGroup(table, column, caption, flags, values, displayValues, defaultVal, null, null);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param defaultVal java.lang.String The defalt Radio Button you want selected
     * @param propCaption HtmlTableCellProperties
     * @param propSearch HtmlTableCellProperties
     */
    public HtmlComponent addIntegerRadioButtonGroup(String table, String column, String caption, int flags, int values[], String displayValues[], String defaultVal, HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlRadioButtonGroup rbg = null;
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            rbg = newRadioButtonGroup(table + "_" + column, values, displayValues, defaultVal);
            addSearchDisplay(fullColName, caption, rbg, flags, propCaption, propSearch);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous rbg because this one will be disabled.
            rbg = newRadioButtonGroup(table + "_" + column, values, displayValues, defaultVal);
            rbg.setColumn(_ds, fullColName);
            rbg.setEnabled(false);
            addListDisplay(fullColName, caption, rbg, propSearch);
        }
        return rbg;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	Name of table used to initialized, also name of its primary integer column.
     * @param descName	Name of description column in initialization table.
     */
    public HtmlComponent addPreInitDropDown(String table, String column, String caption, int flags, String initTable, String initName, String descName) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initTable, initName, DataStoreBuffer.DATATYPE_INT, true, false);
        ds.addColumn(initTable, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initTable +"." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        int values[] = new int[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getInt(initTable + "." + initName);
            displayValues[n] = ds.getString(initTable + "." + descName);
            n++;
        }
        if ((flags & LIST_ONLY) == 0) {
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }

        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	Name of table used to initialized, also name of its primary integer column.
     * @param descName	Name of description column in initialization table.
     */
    public HtmlComponent addPreInitRadioButtonGroup(String table, String column, String caption, int flags, String initName, String descName) throws Exception {
        return addPreInitRadioButtonGroup(table, column, caption, flags, table, initName, descName);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	Name of table used to initialized, also name of its primary integer column.
     * @param descName	Name of description column in initialization table.
     */
    public HtmlComponent addPreInitRadioButtonGroup(String table, String column, String caption, int flags, String initTable, String initName, String descName) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);

        if (table == null) {
            table = NULL_TABLE;
        }
  
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initTable, initName, DataStoreBuffer.DATATYPE_INT, true, false);
        ds.addColumn(initTable, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initTable + "."+ descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        int values[] = new int[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getInt(initTable + "." + initName);
            displayValues[n] = ds.getString(initTable + "." + descName);
            n++;
        }

        return addIntegerRadioButtonGroup(table, column, caption, flags, values, displayValues);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	Name of table used to initialized, also name of its primary integer column.
     * @param descName	Name of description column in initialization table.
     */
    public HtmlComponent addPreInitStringDropDown(String table, String column, String caption, int flags, String initTable, String initName, String descName) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initTable, initName, DataStoreBuffer.DATATYPE_STRING, true, false);
        ds.addColumn(initTable, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initTable + "." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        String values[] = new String[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getString(initTable + "." + initName);
            displayValues[n] = ds.getString(initTable + "." + descName);
            n++;
        }
        if ((flags & LIST_ONLY) == 0) {
            ddl = newStringDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newStringDropDown(table + "_" + column, values, displayValues);
            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }

        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	Name of table used to initialized, also name of its primary integer column.
     * @param descName	Name of description column in initialization table.
     */
    public HtmlComponent addPreInitStringRadioButtonGroup(String table, String column, String caption, int flags, String initTable, String initName, String descName) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);

        if (table == null) {
            table = NULL_TABLE;
        }
 
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initTable, initName, DataStoreBuffer.DATATYPE_STRING, true, false);
        ds.addColumn(initTable, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initTable + "." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        String values[] = new String[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getString(initTable + "." + initName);
            displayValues[n] = ds.getString(initTable + "." + descName);
            n++;
        }

        return addStringRadioButtonGroup(table, column, caption, flags, values, displayValues);
    }

    /**
     * Adds a component to the Search display box.
     *
     * @param name		Name to associate internally with the search component.
     * @param caption	Text of caption to put before the search component, or null.
     * @param component	The search component.
     * @param flags		Bitwise-OR combination of SAME_ROW, PRECEDENCE, etc.
     */
    public HtmlComponent addSearchDisplay(String name, String caption, HtmlComponent component,
                                          int flags) throws Exception {

        return addSearchDisplay(name, caption, component, flags, null, null);
    }

    /**
     * Adds a component to the Search display box.
     *
     * @param name		Name to associate internally with the search component.
     * @param caption	Text of caption to put before the search component, or null.
     * @param component	The search component.
     * @param flags		Bitwise-OR combination of SAME_ROW, PRECEDENCE, etc.
     * @param propCaption	Table properties for caption, or null.
     * @param propSearch	Table properties for search component, or null.
     */
    public HtmlComponent addSearchDisplay(String name, String caption, HtmlComponent component, int flags, //
                                          HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch) throws Exception {
        if (((flags & SAME_ROW) == 0) || (_rowSearch == -1)) {
            _rowSearch++;
            _colSearch = -1;
        }
        int compIndex = -1;
        //
        if (caption != null) {
            String s = caption;
            if (s.length() > 0) {
                switch (s.charAt(s.length() - 1)) {
                    case ':':
                    case '.':
                    case '?':
                        break;
                    default :
                        s += ":";
                }
            }
            HtmlComponent searchCapComp = null;
            // save what caption goes with what search comp for later
            searchCapComp = new HtmlText(s, HtmlText.FONT_COLUMN_CAPTION, getPage());
            //
            if (name != null) {
                compIndex = findOrAdd(name);
                ((BaseListFormComponent) _elements.elementAt(compIndex)).setSearchCapComponent(searchCapComp);
                if ((flags & ADVANCED_SEARCH) != 0) {
                    searchCapComp.setVisible(false);
                }
            }

            // Put Caption in search table
            if (propCaption != null) {
                _tblSearch.setComponentAt(_rowSearch, ++_colSearch, searchCapComp, propCaption);
            } else {
                _tblSearch.setComponentAt(_rowSearch, ++_colSearch, searchCapComp);
            }
        }

        //
        if (component != null) {
            // Null component means caption only, perhaps.
            if (propSearch != null) {
                _tblSearch.setComponentAt(_rowSearch, ++_colSearch, component, propSearch);
            } else {
                _tblSearch.setComponentAt(_rowSearch, ++_colSearch, component);
            }
            if (name != null) {
                compIndex = findOrAdd(name);
                //
                ((BaseListFormComponent) _elements.elementAt(compIndex)).setSearchComponent(component);

                // if this search component is for advanced searching do not show initially
                if ((flags & ADVANCED_SEARCH) != 0) {
                    component.setVisible(false);
                }
                if ((flags & PRECEDENCE) != 0) {
                    _precedenceList.addElement(new Integer(compIndex));
                }

                //
                while (_flags.size() < (compIndex + 1)) {
                    _flags.addElement(null);
                }

                //
                _flags.setElementAt(new Integer(flags), compIndex);
            }
        }
        // Search box has at least one element so make it visible.
        _boxSearch.setVisible(true);
        return component;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addStringDropDown(String table, String column, String caption, //
                                           int flags, String values[], String displayValues[]) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            ddl = newStringDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newStringDropDown(table + "_" + column, values, displayValues);
            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }

        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addStringDropDown(String table, String column, String caption, //
                                           int flags, String values[], String displayValues[], boolean isMandatory) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlDropDownList ddl = null;
        if (table == null) {
            table = NULL_TABLE;
        }
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            ddl = newStringDropDown(table + "_" + column, values, displayValues);
            addSearchDisplay(fullColName, caption, ddl, flags);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous ddl, if any, because this one will be disabled.
            ddl = newStringDropDown(table + "_" + column, values, displayValues);

            if (isMandatory)
                ddl.removeOption("");

            ddl.setColumn(_ds, fullColName);
            ddl.setEnabled(false);
            addListDisplay(fullColName, caption, ddl);
        }
        return ddl;
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlComponent addStringRadioButtonGroup(String table, String column, String caption, int flags, String values[], String displayValues[]) throws Exception {
        return addStringRadioButtonGroup(table, column, caption, flags, values, displayValues, null);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param defaultVal java.lang.String The defalt Radio Button you want selected
     */
    public HtmlComponent addStringRadioButtonGroup(String table, String column, String caption, int flags, String values[], String displayValues[], String defaultVal) throws Exception {
        return addStringRadioButtonGroup(table, column, caption, flags, values, displayValues, defaultVal, null, null);
    }

    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a radio button group.
     *
     * @param table		Name of table for datastore
     * @param column	Name of column for datastore
     * @param caption	Caption for search box and/or list box, or null
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param defaultVal java.lang.String The defalt Radio Button you want selected
     * @param propCaption HtmlTableCellProperties
     * @param propSearch HtmlTableCellProperties
     */
    public HtmlComponent addStringRadioButtonGroup(String table, String column, String caption, int flags, String values[], String displayValues[], String defaultVal, HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch) throws Exception {
        // Don't add the same column twice to the data store.
        if (_ds.getColumnIndex(table + "." + column) == -1)
            _ds.addColumn(table, column, DataStore.DATATYPE_INT, (flags & PRIMARY_KEY) != 0, false);
        HtmlRadioButtonGroup rbg = null;
        ;
        String fullColName = table + "." + column;
        if ((flags & LIST_ONLY) == 0) {
            rbg = newRadioButtonGroup(table + "_" + column, values, displayValues, defaultVal);
            addSearchDisplay(fullColName, caption, rbg, flags, propCaption, propSearch);
        }
        if ((flags & SEARCH_ONLY) == 0) {
            // We cannot reuse the previous rbg because this one will be disabled.
            rbg = newRadioButtonGroup(table + "_" + column, values, displayValues, defaultVal);
            rbg.setColumn(_ds, fullColName);
            rbg.setEnabled(false);
            addListDisplay(fullColName, caption, rbg, propSearch);
        }

        return rbg;
    }

    /**
     * Gets the object container matching the name, else null.
     * @return ThreeObjectContainer
     * @param name java.lang.String
     */
    protected BaseListFormComponent getContainer(String name) {
        int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc.getName().equals(name)) {
                return blfc;
            }
        }
        return null;
    }

    /**
     * Returns component in list box associated with given name, or null.
     *
     * @return com.salmonllc.html.HtmlComponent
     * @param name String 	Name in form table.column
     */
    public HtmlComponent getListLinkComponent(String name) {
        int elementsSize = _elements.size();
        for (int i = 0; i < elementsSize; i++) {
            BaseListFormComponent blfc = (BaseListFormComponent) _elements.elementAt(i);
            if (blfc.getName().equals(name) && (blfc.getListComponent() instanceof HtmlLink)) {
                return blfc.getListComponent();
            }
        }

        return null;
    }

/**
 * Gets the criteria that will be used for the retrieve
 */
public String getSearchCriteria()
{
    CriteriaString cr = new CriteriaString();
    try
        {
        int precedenceListSize = _precedenceList.size();
        int elementsSize = _elements.size();
        //

        // First examine the list of precedence search criteria.

        for (int i = 0; i < precedenceListSize; i++)
            processSearchComponent(((Integer) _precedenceList.elementAt(i)).intValue(), cr);
        // If there are no precedence criteria, examine all criteria.
        if (cr.length() == 0)
            {
            for (int i = 0; i < elementsSize; i++)
                {
                processSearchComponent(i, cr);
            }
        }

    }
    catch (Exception e)
        {
        MessageLog.writeErrorMessage("doRetrieve", e, this);
    }
    return cr.toString();
}
}
