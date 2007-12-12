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
//$Archive: /sofia/sourcecode/com/salmonllc/forms/DetailForm.java $
//$Author: Deepak $
//$Revision: 76 $
//$Modtime: 8/11/03 9:43a $
/////////////////////////


import com.salmonllc.html.*;
import com.salmonllc.html.events.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.MessageLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Implements Detail form. A display box is created containing a table with entry fields.  Also created are submit buttons as follows:<BR> - "Delete": the current row is removed from the database. <BR> - "Save": the database is updated with the contents of the current row. <BR> The form is implemented as a container to go within an instance of (a subclass of) HtmlPage.  Specify as many data store columns as you want using the addColumn* methods. <BR> To hook key activities such as the "Delete" button, implement the DetailFormListener interface and call addListener. NOTE:  The addColumn* methods assume the new column is non-primary.  Typically the client should explicitly specify the primary keys.  For example, <BR> <PRE> DetailForm form = new DetailForm(this, "UserDetailPage"); DataStore ds = form.getDataStore(); add(form); form.addColumn(_table, "user_id", "ID#", DataStore.DATATYPE_STRING, true, true); ds.setPrimaryKey(_table + ".user_id", true); // add other columns ... </PRE>
 */
public class DetailForm extends BaseForm implements SubmitListener, ValueChangedListener, FileUploadListener {

    /** addColumn flags parameter:  Field is required. */
    public static final int IS_REQUIRED = 1 << 0;
    /** addColumn flags parameter:  Do not insert a line break, keep on same table row. */
    public static final int SAME_ROW = 1 << 1;
    /** addColumn flags parameter:  Component is read-only or display only. */
    public static final int READ_ONLY = 1 << 2;
    /** addColumn flags parameter:  Column is primary key. */
    public static final int PRIMARY_KEY = 1 << 3;
    /** addColumn flags parameter:  Do not create a datastore column. */
    public static final int NO_DATASTORE = 1 << 4;
    /** addColumn flags parameter:  Create a bucket using "column" parameter. */
    public static final int BUCKET = 1 << 5;

    /** constructor flags parameter:  Do not create the default "Save" button. */
    public static final int INIT_NO_SAVE_BUTTON = 1 << 0;
    /** constructor flags parameter:  Do not create the default "Delete" button. */
    public static final int INIT_NO_DELETE_BUTTON = 1 << 1;
    /** constructor flags parameter:  Do not process page requests. */
    public static final int INIT_NO_PROCESSING = 1 << 2;

    /** constructor flags parameter:  Do not create the default "Delete" button. */
    public static final int INIT_NO_CANCEL_BUTTON = 1 << 3;

    // Other
    private HtmlDisplayBox _box;
    protected HtmlComponent _btnDelete = null;
    protected HtmlComponent _btnSave = null;
    protected HtmlComponent _btnCancel = null;
    //
    private HtmlTable _tbl;
    private int _row = -1;
    private int _col = -1;
    private Vector _listeners = new Vector();
    protected String _mode;
    private HtmlContainer _displayContainer;
    private DataStore _dsVerify;
    private boolean _initVerifyDone = false;
    private boolean _initPageRequestDone = false;
    private Vector _primaryFormComponents = new Vector();
    private int _nameCount = 0;
    private HtmlTableCellProperties _propAlignRight;
    protected boolean _noProcessing = false;
    public static final int ERROR_DUPLICATE_ROW = 0;
    public static final int ERROR_EMPTY_FIELD = 1;
    public static final int ERROR_INVALID_ENTRY = 2;
    public static final int ERROR_ANY = 99;
    // Information on components in form is stored in a vector of objects of type
    // FormComponent.
    private Vector _formComponents = new Vector();

    //Set the directory to process the fileUploaded() method.
    private String _fileUploadDirectory = null;

    // Message Component
    public HtmlText htErrorMessageComp = null;

    /**
     * Constructor for DetailForm..
     * This variant creates a default data store.
     * @param page com.salmonllc.html.HtmlPage	Page containing the form.
     */
    public DetailForm(HtmlPage page) {
        this(page, null, 0);
    }
    /**
     * Constructor for DetailForm..
     * @param page com.salmonllc.html.HtmlPage	Page containing the form.
     * @param ds DataStore						DataStore to use; if null then create one.
     */
    public DetailForm(HtmlPage page, DataStore ds) {
        this(page, ds, 0);
    }
    /**
     * Constructor for DetailForm..
     * @param page 		Page containing the form.
     * @param ds 		DataStore to use; if null then create one.
     * @param flags		Bitwise-OR combination of INIT_NO_SAVE_BUTTON, etc.
     */
    public DetailForm(HtmlPage page, DataStore ds, int flags) {
        super("", page, ds);
        page.addPageListener(this);

        htErrorMessageComp = new HtmlText("", page);
		htErrorMessageComp.setVisible(false);
        add(htErrorMessageComp);

        // Define Detail display box
        add(_box = new HtmlDisplayBox("box", page));
        _box.setWidth(-1);
        _box.setHeadingCaption("Detail");


        // Create buttons regardless of flags, to avoid checking for null everywhere.

        _btnCancel = new HtmlSubmitButton("btnCancel", "Cancel", page);
        if ((flags & INIT_NO_CANCEL_BUTTON) == 0) {
            HtmlSubmitButton cancelBut = new HtmlSubmitButton("btnCancel", "Cancel", page);
            cancelBut.addSubmitListener(this);
            _box.addHeadingComponent(_btnCancel = cancelBut);
        }


        _btnDelete = new HtmlSubmitButton("btnDelete", "Delete", page);
        if ((flags & INIT_NO_DELETE_BUTTON) == 0) {
            HtmlSubmitButton delBut = new HtmlSubmitButton("btnDelete", "Delete", page);
            delBut.addSubmitListener(this);
            _box.addHeadingComponent(_btnDelete = delBut);
        }
        _btnSave = new HtmlSubmitButton("btnSave", "Save", page);
        if ((flags & INIT_NO_SAVE_BUTTON) == 0) {
            HtmlSubmitButton savBut = new HtmlSubmitButton("btnSave", "Save", page);
            savBut.addSubmitListener(this);
            _box.addHeadingComponent(_btnSave = savBut);
        }
        _box.setBoxComponent(_displayContainer = new HtmlContainer("", page));
        // Name = dft = Detail Form Table
        _displayContainer.add(_tbl = new HtmlTable("dft", page));
        add(_line_break);
        // As a favor to client classes, add page as listener if appropriate
        if (page instanceof DetailFormListener)
            addListener((DetailFormListener) page);
        // Initialize datastore to use for primary-key verification
        _dsVerify = new DataStore(page.getApplicationName());
        // Other
        (_propAlignRight = new HtmlTableCellProperties()).setAlign(HtmlTable.ALIGN_RIGHT);
        _noProcessing = (flags & INIT_NO_PROCESSING) != 0;
    }
    /**
     * Adds a bucket to the datastore and bind a given entry field, and add it to table.
     * @param bucket 			Name of bucket to add.
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
     * @param flags int			Flags.
     * @param component 		Use the given component for entry or display; if null then
     *	create a text entry field.
     * @param propCaption		Properties for caption table cell.
     * @param prop				Properties for data table cell.
     */
    public HtmlComponent addBucket(String bucket, String caption, int type,
                                   int flags, HtmlComponent component, HtmlTableCellProperties propCaption,
                                   HtmlTableCellProperties prop) throws Exception {

        return addColumn(bucket, null, caption, type, flags | BUCKET, component, propCaption,
                prop);
    }
    /**
     * Adds given caption to table, followed by ':'.
     * @param caption java.lang.String
     */
    protected HtmlText addCaption(String caption, int flags, HtmlTableCellProperties prop) {
        HtmlText result = null;
        if (caption != null) {
            String s = "";
            if (caption.length() > 0) {
                s += caption;
                if ((flags & IS_REQUIRED) != 0)
                    s += "*";
                if (!s.endsWith(":") && !s.endsWith("?"))
                    s += ":";
            }
            if (prop != null) {
                _tbl.setComponentAt(_row, ++_col, result = new HtmlText(s, HtmlText.FONT_COLUMN_CAPTION, getPage()), prop);
                /* BUGBUG:  see HtmlTable.generateHtml
                _col += prop.getColumnSpan() - 1;
                */
                if (prop.getColumnSpan() > 1)
                    _col += prop.getColumnSpan();
            } else
                _tbl.setComponentAt(_row, ++_col, result = new HtmlText(s, HtmlText.FONT_COLUMN_CAPTION, getPage()));
        }
        return result;
    }
    /**
     * Adds a column to the datastore and create a corresponding entry or display field in the table,
     * bound to the datastore.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
     * @param flags int			Flags
     */
    public HtmlComponent addColumn(String table, String column, String caption, int type,
                                   int flags) throws Exception {
        return addColumn(table, column, caption, type, flags, null, null, null);
    }
    /**
     * Adds a column to the datastore and bind a given entry field, and add it to table.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
     * @param flags int			Flags.
     * @param component 		Use the given component for the entry or display field.
     */
    public HtmlComponent addColumn(String table, String column, String caption, int type,
                                   int flags, HtmlComponent component) throws Exception {
        return addColumn(table, column, caption, type, flags, component, null, null);
    }
    /**
     * Adds a column to the datastore and bind a given entry field, and add it to table.
     * @param table String		Name of table or bucket to add, or null if no DataStore binding.
     * @param column String		Name of column to add.
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
     * @param flags int			Flags.
     * @param component 		Use the given component for entry or display; if null then
     *	create a text entry field.
     * @param propCaption		Properties for caption table cell.
     * @param prop				Properties for data table cell.
     */
    public HtmlComponent addColumn(String table, String column, String caption, int type,
                                   int flags, HtmlComponent component, HtmlTableCellProperties propCaption,
                                   HtmlTableCellProperties prop) throws Exception {

        String format = null;
        String fullColName;		// Full name of DataStore column
        boolean noDS = ((flags & NO_DATASTORE) != 0) || (table == null);
        boolean doFormat = false;
        String componentName;

        if ((flags & BUCKET) != 0) {
            fullColName = table;
            if (!noDS && (_ds.getColumnIndex(fullColName) == -1)) {
                _ds.addBucket(fullColName, type);
                doFormat = true;
            }
            componentName = fullColName;
        } else {
            fullColName = table + "." + column;
            if (!noDS && (_ds.getColumnIndex(fullColName) == -1)) {
                _ds.addColumn(table, column, type, (flags & PRIMARY_KEY) != 0, true);
                doFormat = true;
            }
            componentName = makeName(table, column);
        }
        if (doFormat) {
            // For certain data types, set format according to page properties.
            Props props = getPage().getPageProperties();
            switch (type) {
                case DataStore.DATATYPE_DATETIME:
                    format = props.getProperty(Props.DATETIME_FORMAT);
                    break;
                case DataStore.DATATYPE_DATE:
                    format = props.getProperty(Props.DATE_FORMAT);
                    break;
                case DataStore.DATATYPE_TIME:
                    format = props.getProperty(Props.TIME_FORMAT);
                    break;
            }
            if (format != null)
                _ds.setFormat(fullColName, format);
        }
        if (((flags & SAME_ROW) == 0) || (_row == -1)) {
            _row++;
            _col = -1;
        }
        HtmlText htCaption = addCaption(caption, flags, propCaption);
        if (component == null) {
            if ((flags & READ_ONLY) != 0) {
                HtmlText ht = new HtmlText(componentName, getPage());
                if (table != null) {
                    if (format != null)
                        ht.setExpression(_ds, fullColName, format);
                    else
                        ht.setExpression(_ds, fullColName);
                }
                component = ht;
            } else {
                HtmlTextEdit hte = new HtmlTextEdit(componentName, getPage());
                hte.setMaxLength(25);
                switch (type) {
                    case DataStore.DATATYPE_STRING:
                        hte.setSize(20);
                        hte.setMaxLength(50);
                        break;
                    case DataStore.DATATYPE_DATETIME:
                        hte.setSize(20);
                        break;
                    case DataStore.DATATYPE_DATE:
                        hte.setSize(15);
                        break;
                    case DataStore.DATATYPE_TIME:
                        hte.setSize(15);
                        break;
                    case DataStore.DATATYPE_INT:
                    case DataStore.DATATYPE_FLOAT:
                    case DataStore.DATATYPE_DOUBLE:
                        hte.setSize(5);
                        break;
                    default:
                        hte.setSize(5);
                        break;
                }
                if (table != null)
                    hte.setColumn(_ds, fullColName);
                component = hte;
            }
        } else if ((component instanceof HtmlFormComponent) && !noDS)
            ((HtmlFormComponent) component).setColumn(_ds, fullColName);
        if (prop != null) {
            _tbl.setComponentAt(_row, ++_col, component, prop);
            /* BUGBUG:  see HtmlTable.generateHtml
            _col += prop.getColumnSpan() - 1;
            */
            _col += prop.getColumnSpan();
        } else
            _tbl.setComponentAt(_row, ++_col, component);
        addFormComponent(htCaption, component, flags);
        return component;
    }
    /**
     * Adds a column to the datastore and create a corresponding entry or display field in the table,
     * bound to the datastore.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
     * @param flags int			Flags
     */
    public HtmlComponent addColumn(String table, String column, String caption, String defaultValue, int type,
                                   int flags) throws Exception {
        return addColumn(table, column, caption, defaultValue, type, flags, null, null, null);
    }
/**
 * Adds a column to the datastore and bind a given entry field, and add it to table.
 * @param table String		Name of table or bucket to add, or null if no DataStore binding.
 * @param column String		Name of column to add.
 * @param caption String	Text of caption to use preceding entry field.  If null, none.
 * @param type int			Type of datastore column--use DataStore.DATATYPE_* values.
 * @param flags int			Flags.
 * @param component 		Use the given component for entry or display; if null then
 *	create a text entry field.
 * @param propCaption		Properties for caption table cell.
 * @param prop				Properties for data table cell.
 */
public HtmlComponent addColumn(String table, String column, String caption, String defaultValue, int type, int flags, HtmlComponent component, HtmlTableCellProperties propCaption, HtmlTableCellProperties prop) throws Exception
{

    String format = null;
    String fullColName; // Full name of DataStore column
    boolean noDS = ((flags & NO_DATASTORE) != 0) || (table == null);
    boolean doFormat = false;
    String componentName;

    if ((flags & BUCKET) != 0)
        {
        fullColName = table;
        if (!noDS && (_ds.getColumnIndex(fullColName) == -1))
            {
            _ds.addBucket(fullColName, type);
            doFormat = true;
        }
        componentName = fullColName;
    }
    else
        {
        fullColName = table + "." + column;
        if (!noDS && (_ds.getColumnIndex(fullColName) == -1))
            {
            _ds.addColumn(table, column, type, (flags & PRIMARY_KEY) != 0, true);
            doFormat = true;
        }
        else
            {
            format = _ds.getFormat(fullColName);
        }
        componentName = makeName(table, column);
    }
    if (doFormat)
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
        if (format != null)
            _ds.setFormat(fullColName, format);
    }
    if (((flags & SAME_ROW) == 0) || (_row == -1))
        {
        _row++;
        _col = -1;
    }
    HtmlText htCaption = addCaption(caption, flags, propCaption);
    if (component == null)
        {
        if ((flags & READ_ONLY) != 0)
            {
            HtmlText ht = new HtmlText(componentName, getPage());
            if (table != null)
                {
                if (format != null)
                    ht.setExpression(_ds, fullColName, format);
                else
                    ht.setExpression(_ds, fullColName);
            }
            component = ht;
        }
        else
            {
            HtmlTextEdit hte = new HtmlTextEdit(componentName, getPage());
            hte.setValue(defaultValue);
            hte.setMaxLength(25);
            switch (type)
                {
                case DataStore.DATATYPE_STRING :
                    hte.setSize(20);
                    hte.setMaxLength(50);
                    break;
                case DataStore.DATATYPE_DATETIME :
                    hte.setSize(20);
                    break;
                case DataStore.DATATYPE_DATE :
                    hte.setSize(15);
                    break;
                case DataStore.DATATYPE_TIME :
                    hte.setSize(15);
                    break;
                case DataStore.DATATYPE_INT :
                case DataStore.DATATYPE_FLOAT :
                case DataStore.DATATYPE_DOUBLE :
                    hte.setSize(5);
                    break;
                default :
                    hte.setSize(5);
                    break;
            }
            if (table != null)
                hte.setColumn(_ds, fullColName);

            component = hte;
        }
    }
    else if ((component instanceof HtmlFormComponent) && !noDS)
         ((HtmlFormComponent) component).setColumn(_ds, fullColName);
    if (prop != null)
        {
        _tbl.setComponentAt(_row, ++_col, component, prop);
        /* BUGBUG:  see HtmlTable.generateHtml
        _col += prop.getColumnSpan() - 1;
        */
        _col += prop.getColumnSpan();
    }
    else
        _tbl.setComponentAt(_row, ++_col, component);
    addFormComponent(htCaption, component, flags);
    return component;
}
/**
 * Add a component to current position in table, without any data store processing.
 * @param caption String			Caption to precede table cell, or null
 * @param component HtmlComponent	Component to add
 * @param flags int					Flags
 */
public void addComponent(String caption, HtmlComponent component, int flags) throws Exception
{
    addComponent(caption, component, flags, null, null);
}
    /**
     * Add a component to current position in table, without any data store processing.
     * @param caption	Caption to precede table cell, or null
     * @param component Component to add
     * @param flags		Flags
     * @param propCaption	Table cell properties for caption, or null
     * @param prop 		Table cell properties for data, or null
     */
    public void addComponent(String caption, HtmlComponent component, int flags,
                             HtmlTableCellProperties propCaption, HtmlTableCellProperties prop) throws Exception {
        addColumn(null, null, caption, 0, flags, component, propCaption, prop);
    }
    /**
     * Add a custom form component to the detail form. The regular addColumn just adds HtmlTextEdit's
     * @param caption java.lang.String
     * @param component com.salmonllc.html.HtmlComponent
     * @param flags int
     * @param name java.lang.String
     */
    protected void addFormComponent(HtmlText caption, HtmlComponent component, int flags) {
        _formComponents.addElement(new FormComponent(caption, component, flags));
        addValueChangedListener(component);
//       if(component instanceof HtmlFileUpload)
//         ((HtmlFileUpload)component).addFileUploadListener(this);
    }
    /**
     * Adds a column to the datastore, using integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlDropDownList addIntegerDropDown(String table, String column, String caption,
                                               int flags, int values[], String displayValues[]) throws Exception {
        return (HtmlDropDownList) addColumn(table, column, caption, DataStore.DATATYPE_INT, flags,
                newIntegerDropDown(makeName(table, column), values, displayValues), null, null);
    }
    /**
     * Adds a column to the datastore, using integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param propCaption	Table cell properties for caption, or null
     * @param prop 		Table cell properties for data, or null
     */
    public HtmlDropDownList addIntegerDropDown(String table, String column, String caption,
                                               int flags, int values[], String displayValues[], HtmlTableCellProperties propCaption,
                                               HtmlTableCellProperties prop) throws Exception {
        HtmlDropDownList ddl = newIntegerDropDown(makeName(table, column), values, displayValues);
        if ((flags & READ_ONLY) != 0)
            ddl.setEnabled(false);
        return (HtmlDropDownList) addColumn(table, column, caption, DataStore.DATATYPE_INT, flags,
                ddl, propCaption, prop);
    }
    /**
     * Adds a column to the datastore, using integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlDropDownList addIntegerDropDown(String table, String column, String caption,
                                               int flags, int values[], String displayValues[], boolean isMandatory) throws Exception {

        HtmlDropDownList ddl = (HtmlDropDownList) addColumn(table, column, caption, DataStore.DATATYPE_INT, flags,
                newIntegerDropDown(makeName(table, column), values, displayValues), null, null);

        if (isMandatory)
            ddl.removeOption("");
        return ddl;
    }
    /**
     * Adds a column to the datastore, using an integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlRadioButtonGroup addIntegerRadioButtonGroup(String table, String column, String caption, int flags, int values[], String displayValues[]) throws Exception {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(makeName(table, column), getPage());
        // This allows less work having to be done in the loop every iteration
        String value = null;
        String displayValue = null;
        int valLength = values.length;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valLength; i++) {
            value = new Integer(values[i]).toString();
            displayValue = "Value " + i;
            if (i < displayValuesLength) {
                displayValue = displayValues[i];
            }
            rbg.addOption(value, displayValue);
        }
        //
        if ((flags & READ_ONLY) != 0)
            rbg.setEnabled(false);
        return (HtmlRadioButtonGroup) addColumn(table, column, caption, DataStore.DATATYPE_INT, flags, rbg, null, null);
    }
    /**
     * Adds a column to the datastore, using an integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param propCaption		Properties for caption table cell.
     * @param prop				Properties for data table cell.
     */
    public HtmlRadioButtonGroup addIntegerRadioButtonGroup(String table, String column, String caption, int flags, int values[], String displayValues[], HtmlTableCellProperties propCaption, HtmlTableCellProperties prop) throws Exception {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(makeName(table, column), getPage());

        // This allows less work having to be done in the loop every iteration
        String value = null;
        String displayValue = null;
        int valLength = values.length;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valLength; i++) {
            value = new Integer(values[i]).toString();
            displayValue = "Value " + i;
            if (i < displayValuesLength) {
                displayValue = displayValues[i];
            }
            rbg.addOption(value, displayValue);
        }
        if ((flags & READ_ONLY) != 0)
            rbg.setEnabled(false);
        return (HtmlRadioButtonGroup) addColumn(table, column, caption, DataStore.DATATYPE_INT, flags, rbg, propCaption, prop);
    }
    /**
     * Adds a DetailFormListener.
     * @param listener com.salmonllc.forms.DetailFormListener
     */
    public void addListener(DetailFormListener listener) {
        // Make sure listener is not added twice
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++)
            if (((DetailFormListener) _listeners.elementAt(i)) == listener)
                return;
        _listeners.addElement(listener);
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
    public HtmlDropDownList addPreInitDropDown(String table, String column, String caption,
                                               int flags, String initName, String descName, HtmlTableCellProperties propCaption,
                                               HtmlTableCellProperties prop) throws Exception {
        return addPreInitDropDown(table, column, caption, flags, initName, initName, descName, propCaption, prop);
    }
    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		 Name of table for datastore
     * @param column	 Name of column for datastore
     * @param caption	 Caption for search box and/or list box, or null
     * @param flags		 Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	 Name of table used to initialized.
     * @param initColumn Name of primary integer column of initName table.
     * @param descName	 Name of description column in initialization table.
     */
    public HtmlDropDownList addPreInitDropDown(String table, String column, String caption,
                                               int flags, String initName, String initColumn, String descName, HtmlTableCellProperties propCaption,
                                               HtmlTableCellProperties prop) throws Exception {
        HtmlDropDownList ddl;
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initName, initColumn, DataStoreBuffer.DATATYPE_INT, true, false);
        ds.addColumn(initName, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
		  ds.setOrderBy(initName + "." + descName);

        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        int values[] = new int[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getInt(initName + "." + initColumn);
            displayValues[n] = ds.getString(initName + "." + descName);
            n++;
        }
        ddl = newIntegerDropDown(table + "_" + column, values, displayValues);
        if ((flags & READ_ONLY) != 0)
            ddl.setEnabled(false);
        addColumn(table, column, caption, DataStoreBuffer.DATATYPE_INT, flags, ddl, propCaption, prop);
        return ddl;
    }
    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		 Name of table for datastore
     * @param column	 Name of column for datastore
     * @param caption	 Caption for search box and/or list box, or null
     * @param flags		 Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	 Name of table used to initialized.
     * @param initColumn Name of primary integer column of initName table.
     * @param descName	 Name of description column in initialization table.
     */
    public HtmlRadioButtonGroup addPreInitRadioButtonGroup(String table, String column, String caption,
                                                           int flags, String initName, String initColumn, String descName, HtmlTableCellProperties propCaption,
                                                           HtmlTableCellProperties prop) throws Exception {

        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initName, initColumn, DataStoreBuffer.DATATYPE_INT, true, false);
        ds.addColumn(initName, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initName + "." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        int values[] = new int[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getInt(initName + "." + initColumn);
            displayValues[n] = ds.getString(initName + "." + descName);
            n++;
        }
        return addIntegerRadioButtonGroup(table, column, caption, flags, values, displayValues, propCaption, prop);
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
    public HtmlDropDownList addPreInitStringDropDown(String table, String column, String caption,
                                                     int flags, String initName, String descName) throws Exception {
        return addPreInitStringDropDown(table, column, caption, flags, initName, initName, descName, null, null);
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
    public HtmlDropDownList addPreInitStringDropDown(String table, String column, String caption,
                                                     int flags, String initName, String descName, HtmlTableCellProperties propCaption,
                                                     HtmlTableCellProperties prop) throws Exception {
        return addPreInitStringDropDown(table, column, caption, flags, initName, initName, descName, propCaption, prop);
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
    public HtmlDropDownList addPreInitStringDropDown(String table, String column, String caption,
                                                     int flags, String tableName, String initName, String descName) throws Exception {
        return addPreInitStringDropDown(table, column, caption, flags, tableName, initName, descName, null, null);
    }
    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		 Name of table for datastore
     * @param column	 Name of column for datastore
     * @param caption	 Caption for search box and/or list box, or null
     * @param flags		 Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	 Name of table used to initialized.
     * @param initColumn Name of primary integer column of initName table.
     * @param descName	 Name of description column in initialization table.
     */
    public HtmlDropDownList addPreInitStringDropDown(String table, String column, String caption,
                                                     int flags, String initName, String initColumn, String descName, HtmlTableCellProperties propCaption,
                                                     HtmlTableCellProperties prop) throws Exception {
        HtmlDropDownList ddl;
        HtmlPage page = getPage();

        // Load secondary data store.
        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initName, initColumn, DataStoreBuffer.DATATYPE_STRING, true, false);
        ds.addColumn(initName, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
		  ds.setOrderBy(initName + "." + descName);

        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        String values[] = new String[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getString(initName + "." + initColumn);
            displayValues[n] = ds.getString(initName + "." + descName);
            n++;
        }
        ddl = newStringDropDown(table + "_" + column, values, displayValues);
        if ((flags & READ_ONLY) != 0)
            ddl.setEnabled(false);
        addColumn(table, column, caption, DataStoreBuffer.DATATYPE_STRING, flags, ddl, propCaption, prop);
        return ddl;
    }
    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		 Name of table for datastore
     * @param column	 Name of column for datastore
     * @param caption	 Caption for search box and/or list box, or null
     * @param flags		 Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	 Name of table used to initialized.
     * @param initColumn Name of primary integer column of initName table.
     * @param descName	 Name of description column in initialization table.
     */
    public HtmlRadioButtonGroup addPreInitStringRadioButtonGroup(String table, String column, String caption,
                                                                 int flags, String initName, String initColumn, String descName) throws Exception {

        HtmlPage page = getPage();

        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initName, initColumn, DataStoreBuffer.DATATYPE_STRING, true, false);
        ds.addColumn(initName, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initName + "." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        String values[] = new String[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getString(initName + "." + initColumn);
            displayValues[n] = ds.getString(initName + "." + descName);
            n++;
        }
        return addStringRadioButtonGroup(table, column, caption, flags, values, displayValues, null, null);
    }
    /**
     * Adds a column to the data store and list box, search box, or both.
     * The column is of type integer and the display is a drop-down list which is
     * preinitialized from a second table structured in a particular way.
     *
     * @param table		 Name of table for datastore
     * @param column	 Name of column for datastore
     * @param caption	 Caption for search box and/or list box, or null
     * @param flags		 Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     * @param initName 	 Name of table used to initialized.
     * @param initColumn Name of primary integer column of initName table.
     * @param descName	 Name of description column in initialization table.
     */
    public HtmlRadioButtonGroup addPreInitStringRadioButtonGroup(String table, String column, String caption,
                                                                 int flags, String initName, String initColumn, String descName, HtmlTableCellProperties propCaption,
                                                                 HtmlTableCellProperties prop) throws Exception {

        HtmlPage page = getPage();

        DataStore ds = new DataStore(page.getApplicationName());
        ds.addColumn(initName, initColumn, DataStoreBuffer.DATATYPE_STRING, true, false);
        ds.addColumn(initName, descName, DataStoreBuffer.DATATYPE_STRING, false, false);
	     ds.setOrderBy(initName + "." + descName);
        ds.retrieve();
        ds.waitForRetrieve();
        int n = ds.getRowCount();
        if (n < 1)
            throw new FormException("Row count < 1");
        String values[] = new String[n];
        String displayValues[] = new String[n];
        n = 0;
        while (ds.gotoNext()) {
            values[n] = ds.getString(initName + "." + initColumn);
            displayValues[n] = ds.getString(initName + "." + descName);
            n++;
        }
        return addStringRadioButtonGroup(table, column, caption, flags, values, displayValues, propCaption, prop);
    }
    /**
     * Adds a column to the datastore, using integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlDropDownList addStringDropDown(String table, String column, String caption,
                                              int flags, String values[], String displayValues[]) throws Exception {
        return (HtmlDropDownList) addColumn(table, column, caption, DataStore.DATATYPE_STRING, flags,
                newStringDropDown(makeName(table, column), values, displayValues), null, null);
    }
    /**
     * Adds a column to the datastore, using integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlDropDownList addStringDropDown(String table, String column, String caption,
                                              int flags, String values[], String displayValues[], boolean isMandatory) throws Exception {
        HtmlDropDownList ddl = (HtmlDropDownList) addColumn(table, column, caption, DataStore.DATATYPE_STRING, flags,
                newStringDropDown(makeName(table, column), values, displayValues), null, null);
        if (isMandatory)
            ddl.removeOption("");
        return ddl;
    }
    /**
     * Adds a column to the datastore, using an integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     */
    public HtmlRadioButtonGroup addStringRadioButtonGroup(String table, String column, String caption, int flags, String values[], String displayValues[]) throws Exception {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(makeName(table, column), getPage());
        // This allows less work having to be done in the loop every iteration
        String value = null;
        String displayValue = null;
        int valLength = values.length;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valLength; i++) {
            value = values[i];
            displayValue = "Value " + i;
            if (i < displayValuesLength) {
                displayValue = displayValues[i];
            }
            rbg.addOption(value, displayValue);
        }
        //
        if ((flags & READ_ONLY) != 0)
            rbg.setEnabled(false);
        return (HtmlRadioButtonGroup) addColumn(table, column, caption, DataStore.DATATYPE_STRING, flags, rbg, null, null);
    }
    /**
     * Adds a column to the datastore, using an integer-type radio button group.
     * @param table String		Name of table to add
     * @param column String		Name of column to add
     * @param caption String	Text of caption to use preceding entry field.  If null, none.
     * @param flags int			Flags
     * @param int[] values 		A list of values for the column.
     * @param String[] dispValues A list of corresponding display values for each of the values for the column.
     * @param propCaption		Properties for caption table cell.
     * @param prop				Properties for data table cell.
     */
    public HtmlRadioButtonGroup addStringRadioButtonGroup(String table, String column, String caption, int flags, String values[], String displayValues[], HtmlTableCellProperties propCaption, HtmlTableCellProperties prop) throws Exception {
        HtmlRadioButtonGroup rbg = new HtmlRadioButtonGroup(makeName(table, column), getPage());

        // This allows less work having to be done in the loop every iteration
        String value = null;
        String displayValue = null;
        int valLength = values.length;
        int displayValuesLength = displayValues.length;
        //
        for (int i = 0; i < valLength; i++) {
            value = values[i];
            displayValue = "Value " + i;
            if (i < displayValuesLength) {
                displayValue = displayValues[i];
            }
            rbg.addOption(value, displayValue);
        }
        if ((flags & READ_ONLY) != 0)
            rbg.setEnabled(false);
        return (HtmlRadioButtonGroup) addColumn(table, column, caption, DataStore.DATATYPE_STRING, flags, rbg, propCaption, prop);
    }
    /**
     * Recursively adds ValueChangedListener.
     * @param c com.salmonllc.html.HtmlComponent
     */
    protected void addValueChangedListener(HtmlComponent c) {
        if (c instanceof HtmlFormComponent)
            ((HtmlFormComponent) c).addValueChangedListener(this);
        else if (c instanceof HtmlContainer) {
            Enumeration e = ((HtmlContainer) c).getComponents();
            while (e.hasMoreElements())
                addValueChangedListener((HtmlComponent) e.nextElement());
        }
    }
    /**
     * Used to find a previously added component.
     * @return FormComponent
     * @param name java.lang.String
     */
    protected FormComponent findFormComponent(HtmlComponent component) {
        int formCompSize = _formComponents.size();
        FormComponent fc = null;
        for (int i = 0; i < formCompSize; i++) {
            fc = (FormComponent) _formComponents.elementAt(i);
            if ((fc.getFormComponent() == component) //
                    || ((fc.getFormComponent() instanceof HtmlContainer //
                    && hasComponent((HtmlContainer) fc.getFormComponent(), component))))
                return fc;
        }
        return null;
    }
    /**
     * Get the cation component associated with the passed in HtmlComponent.
     * @return FormComponent
     * @param name java.lang.String
     */
    public HtmlText getCaption(HtmlComponent component) {
        FormComponent fc = findFormComponent(component);
        if (fc != null)
            return fc.getCaptionTextComp();
        else
            return null;
    }
    /**
     * Used to get the current column index.
     * @return int
     */
    public int getCol() {
        return _col;
    }
    /**
     * Return the internal DataStore object.
     * @return com.salmonllc.sql.DataStore
     */
    public DataStore getDataStore() {
        return _ds;
    }
    /**
     * Returns the display box component.
     * @return com.salmonllc.html.HtmlDisplayBox
     */
    public HtmlDisplayBox getDisplayBox() {
        return _box;
    }
    /**
     * Returns container component which is main part of display box.
     * @return com.salmonllc.html.HtmlContainer
     */
    public HtmlContainer getDisplayContainer() {
        return _displayContainer;
    }
/**
 * Insert the method's description here.
 * Creation date: (5/7/02 5:29:03 PM)
 * @return com.salmonllc.html.HtmlText
 */
public com.salmonllc.html.HtmlText getErrorMessageComp() {
	return htErrorMessageComp;
}
    /**
     * Used to get the current row index.
     * @return int
     */
    public int getRow() {
        return _row;
    }
    /**
     * Returns the display table component.
     * @return com.salmonllc.html.HtmlTable
     */
    public HtmlTable getTable() {
        return _tbl;
    }
    /**
     * Recursively determins whether a container contains a component.
     * @return boolean
     * @param cont com.salmonllc.html.HtmlContainer
     * @param component com.salmonllc.html.HtmlComponent
     */
    protected boolean hasComponent(HtmlContainer cont, HtmlComponent component) {
        Enumeration e = cont.getComponents();
        HtmlComponent c = null;
        while (e.hasMoreElements()) {
            c = (HtmlComponent) e.nextElement();
            if (c == component)
                return true;
            else if (c instanceof HtmlContainer) {
                if (hasComponent((HtmlContainer) c, component))
                    return true;
            }
        }
        return false;
    }
/**
 * Constructs a component name from table, column names.
 * @return java.lang.String
 * @param table java.lang.String
 * @param column java.lang.String
 */
public String makeName(String table, String column)
{
    if (table != null)
        {
        if (column != null)
            return table + "_" + column;
        else
            return table;
    }
    else if (column != null)
        {
        return column;
    }
    else
        return "comp" + _nameCount++;
}
/**
 * This event will get fired each time a page is requested by the browser.
 */
public void pageRequested(PageEvent p) throws Exception
{
    super.pageRequested(p);
    if (_noProcessing || !getVisible())
        return;
    HtmlPage page = getPage();
    if (!_initPageRequestDone)
        {
        // Accumulate list of form components bound to primary keys in data store
        Enumeration e = _tbl.getComponents();
        Object o = null;
        HtmlFormComponent hfc = null;
        while (e.hasMoreElements())
            {
            o = e.nextElement();
            if (o instanceof HtmlFormComponent)
                {
                hfc = (HtmlFormComponent) o;
                int colnum;
                if (((colnum = hfc.getColumnNumber()) != -1) && _ds.isPrimaryKey(colnum))
                    _primaryFormComponents.addElement(hfc);
            }
        }
        _initPageRequestDone = true;
    }
    // Set up the display
    if (!page.isReferredByCurrentPage())
        {
        String mode = page.getParameter("mode");
        if (mode == null)
            return;
        else
            _mode = mode;
        if (_mode.equals("add"))
            {
            _btnDelete.setVisible(false);
            _btnSave.setVisible(true);
            _btnCancel.setVisible(true);
            setEnabled(true);
        }
        else if (_mode.equals("view"))
            {
            _btnDelete.setVisible(false);
            _btnSave.setVisible(false);
            _btnCancel.setVisible(false);
            setEnabled(false);
        }
        else if (_mode.equals("update"))
            {
            _btnDelete.setVisible(true);
            _btnSave.setVisible(true);
            _btnCancel.setVisible(true);
            setEnabled(true);
            // Disable form components associated with primary keys, else there will
            // be an SQL error if the user tries to change them then save.
            int primaryFormCompsSize = _primaryFormComponents.size();
            for (int i = 0; i < primaryFormCompsSize; i++)
                 ((HtmlFormComponent) _primaryFormComponents.elementAt(i)).setEnabled(false);
        }
        else
            {
            return;
        }
    }
    // Call pre-processing listeners
    int listenersSize = _listeners.size();
    for (int i = 0; i < listenersSize; i++)
        if (!((DetailFormListener) _listeners.elementAt(i)).preDetailRequest())
            return;
    if (!page.isReferredByCurrentPage())
        {
        // sr 10-15-2000 if we used  _mode.intern()=="add" it might be faster
        // as per page 106 performance tuning book by o'reilly
        if (_mode.equals("add"))
            {
            _ds.reset();
            _ds.insertRow();
        }
        else if (_mode.equals("update") || _mode.equals("view"))
            {
            // Process page parameters associated with primary keys to identify
            // row to retrieve.
            CriteriaString cr = new CriteriaString();
            int colCount = _ds.getColumnCount();
            String name = null;
            String s = null;
            for (int i = 0; i < colCount; i++)
                {
                if (!_ds.isPrimaryKey(i))
                    continue;
                name = _ds.getColumnName(i);
                int dot = name.indexOf('.');
                // Check for table.column form even thought it should always be OK
                if (dot == -1)
                    continue;
                s = page.getParameter(name.substring(dot + 1));
                if (s == null)
                    {
                    // No matching primary-key parameter, so continue and hope the retrieve
                    // will work anyway.
                    continue;
                }
                switch (_ds.getColumnDataType(i))
                    {
                    case DataStore.DATATYPE_INT :
                    case DataStore.DATATYPE_SHORT :
                    case DataStore.DATATYPE_LONG :
                    case DataStore.DATATYPE_FLOAT :
                    case DataStore.DATATYPE_DOUBLE :
                        cr.and(name + "=" + s);
                        break;
                    case DataStore.DATATYPE_STRING :
                    case DataStore.DATATYPE_DATETIME :
                        cr.and(name + "='" + s + "'");
                        break;
                    default :
                        throw new FormException("Unknown datatype: " + _ds.getColumnDataType(i) + "\nColumn:" + _ds.getColumnName(i));
                }
            }
            // we are getting here early. we have no columns set up so we throw an exception
            // this seems to have happened in a jsp.

            // the fix
            if (_ds.getColumnCount() <= 0)
                {
                return;
            }
            _ds.retrieve(cr.toString());
            _ds.gotoFirst();
        }
        else
            {
            throw new FormException("unknown mode: " + _mode);
        }
    }
    //
    //  listenersSize already initialized from above
    for (int i = 0; i < listenersSize; i++)
        if (!((DetailFormListener) _listeners.elementAt(i)).postDetailRequest())
            return;
}
    /**
     * Process a user error condition.
     * @param code int
     * @param caption java.lang.String
     * @param component com.salmonllc.html.HtmlComponent
     * @return int	If true, continue event processing.
     */
    protected boolean processError(int code, String caption, HtmlComponent component) {
        int listenersSize = _listeners.size();
        if (listenersSize == 0)
            return true;
        String text = null;
        switch (code) {
            case DetailFormListener.ERROR_DUPLICATE_ROW:
                text = "Row in database already exists with same primary key";
                break;
            case DetailFormListener.ERROR_EMPTY_FIELD:
                if ((component instanceof HtmlDropDownList) || (component instanceof HtmlRadioButtonGroup))
                    text = "Please select a value";
                else
                    text = "Please enter a value";
                break;
            case DetailFormListener.ERROR_INVALID_ENTRY:
                text = "Please enter a valid value";
                break;
        }
        if (text == null)
        {
            text = caption;
        }
        else if (caption != null)
        {
            text += " for " + caption;
        }

        if (text != null)
            text += ".";
        for (int i = 0; i < listenersSize; i++)
            if (!((DetailFormListener) _listeners.elementAt(i)).onDetailError(code, text, component))
                return false;
        return true;
    }
    /**
     * Sets display name for Delete button.
     *
     * @param String name
     */
    public void setDeleteButtonDisplay(String name) throws Exception {
        if (_btnDelete != null) {
            if (_btnDelete instanceof HtmlSubmitButton) {
                ((HtmlSubmitButton) _btnDelete).setDisplayName(name);
            } else {
                throw new FormException("_btnSaveis not a submit button");
            }
        }
    }
    /**
     * Replaces default search button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setDeleteImage(HtmlSubmitImage img) {
        // Delete Button
        if (_btnDelete != null) {
            _box.removeHeadingComponent(_btnDelete);
        }
        img.addSubmitListener(this);
        _box.addHeadingComponent(_btnDelete = img);
    }
    /**
     * Replaces default search button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setDeleteImage(String imageUrl) {
        if (_btnDelete != null)
            _box.removeHeadingComponent(_btnDelete);
        HtmlSubmitImage i = new HtmlSubmitImage("btnDelete", imageUrl, getPage());
        i.addSubmitListener(this);
        _box.addHeadingComponent(_btnDelete = i);
    }
/**
 * Insert the method's description here.
 * Creation date: (5/7/02 5:29:03 PM)
 * @param newErrorMessageComp com.salmonllc.html.HtmlText
 */
public void setErrorMessageComp(com.salmonllc.html.HtmlText newErrorMessageComp) {
	htErrorMessageComp = newErrorMessageComp;
}
    /**
     * This method sets the text for the heading on the component
     */
    public void setHeadingCaption(String text) {
        _box.setHeadingCaption(text);
    }
    /**
     * Sets display name for Save button.
     * @param text java.lang.String
     */
    public void setSaveButtonDisplay(String name) throws Exception {
        if (_btnSave != null) {
            if (_btnSave instanceof HtmlSubmitButton) {
                ((HtmlSubmitButton) _btnSave).setDisplayName(name);
            } else {
                throw new FormException("_btnSave is not a submit button");
            }
        }
    }
    /**
     * Replaces default add button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setSaveImage(HtmlSubmitImage img) {
        // Save Button
        if (_btnSave != null) {
            _box.removeHeadingComponent(_btnSave);
        }
        img.addSubmitListener(this);
        _box.addHeadingComponent(_btnSave = img);
    }

   /**
     * Sets the directory to upload the file.
     * @param String sDirectory 	directory name.
     */
   public void setFileUploadDirectory(String sDirectory){
      _fileUploadDirectory = sDirectory;
   }
    /**
     * Replaces default add button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setSaveImage(String imageUrl) {
        if (_btnSave != null)
            _box.removeHeadingComponent(_btnSave);
        HtmlSubmitImage i = new HtmlSubmitImage("btnSave", imageUrl, getPage());
        i.addSubmitListener(this);
        _box.addHeadingComponent(_btnSave = i);
    }
    /**
     * Sets the visiblility of the default Delete button.
     * @param visible boolean
     */
    public void showDeleteButton(boolean visible) {
        if (_btnDelete != null) {
            _btnDelete.setVisible(visible);
        }
    }
    /**
     * Sets the visiblility of the default Save button.
     * @param visible boolean
     */
    public void showSaveButton(boolean visible) {
        if (_btnSave != null) {
            _btnSave.setVisible(visible);
        }
    }
/**
 * Inherited abstract method.
 * @return boolean
 * @param e com.salmonllc.html.events.SubmitEvent
 */
public boolean submitPerformed(SubmitEvent e) throws Exception
{
    //
    MessageLog.writeDebugMessage("  submitPerformed (SubmitEvent e)", this);
    HtmlComponent c = e.getComponent();
    int listenersSize = _listeners.size();
    if (c == _btnSave)
        {

        // Verification passed, go ahead with update.

        for (int i = 0; i < listenersSize; i++)
            if (!((DetailFormListener) _listeners.elementAt(i)).preDetailSave())
                return false;
        if (!validate())
            return false;
        _ds.update();
        for (int i = 0; i < listenersSize; i++)
             ((DetailFormListener) _listeners.elementAt(i)).postDetailSave();
    }
    else if (c == _btnDelete)
        {
        for (int i = 0; i < listenersSize; i++)
            if (!((DetailFormListener) _listeners.elementAt(i)).preDetailDelete())
                return false;
        _ds.deleteRow();
        _ds.update();
        for (int i = 0; i < listenersSize; i++)
             ((DetailFormListener) _listeners.elementAt(i)).postDetailDelete();
    }
    else if (c == _btnCancel)
    {
        int formComponentsSize = _formComponents.size();
        HtmlComponent htmlComp = null;

        for (int i = 0; i < formComponentsSize; i++)
            {
            htmlComp = ((FormComponent) _formComponents.elementAt(i)).getFormComponent();
            if (htmlComp instanceof HtmlFormComponent)
                {
                ((HtmlFormComponent) htmlComp).setValue(null);
            }
        }
    }

    return true;
}
    /**
     * Validates entry fields according to current mode.
     * @return 	True if validation was successful.
     */
    public boolean validate() throws Exception {
        String mode = _mode == null ? "" : _mode;
        if (mode.equals("add")) {
            // Verify row with duplicate primary key combination does not exist.
            CriteriaString cr = new CriteriaString();
            //
            int colCount = _ds.getColumnCount();
            String name = null;
            //
            for (int i = 0; i < colCount; i++) {
                if (!_ds.isPrimaryKey(i))
                    continue;
                name = _ds.getColumnName(i);
                switch (_ds.getColumnDataType(i)) {
                    case DataStore.DATATYPE_STRING:
                        cr.and(name + "='" + _ds.getString(i) + "'");
                        break;
                    case DataStore.DATATYPE_INT:
                        cr.and(name + "=" + _ds.getInt(i));
                        break;
                    case DataStore.DATATYPE_LONG:
                        cr.and(name + "=" + _ds.getLong(i));
                        break;
                    case DataStore.DATATYPE_SHORT:
                        cr.and(name + "=" + _ds.getShort(i));
                        break;
                    case DataStore.DATATYPE_DOUBLE:
                        cr.and(name + "=" + _ds.getDouble(i));
                        break;
                    case DataStore.DATATYPE_DATETIME:
                        cr.and(name + "='" + _ds.getDateTime(i) + "'");
                        break;
                    default :
                        throw new FormException("Unknown datatype");
                }
                if (!_initVerifyDone) {
                    // Copy an arbirary column into _dsVerify so we can do a retrieve on it.
                    int dot = name.lastIndexOf('.');
                    // Check for table.column form even thought it should always be OK
                    if (dot != -1) {
                        _dsVerify.addColumn(name.substring(0, dot), name.substring(dot + 1), _ds.getColumnDataType(i), _ds.isPrimaryKey(i), false);
                        _initVerifyDone = true;
                    }
                }
            }
            String s = cr.toString();
            if ((s != null) && _initVerifyDone) {
                _dsVerify.retrieve(s);
                if (_dsVerify.gotoNext()) {
                    if (!processError(DetailFormListener.ERROR_DUPLICATE_ROW, null, null))
                        return false;
                }
            }
        }
        // Verify all required fields
        if (!mode.equals("view")) {
            int formComponentsSize = _formComponents.size();
            FormComponent fc = null;
            HtmlComponent c = null;
            String caption = null;
            for (int i = 0; i < formComponentsSize; i++) {
                fc = (FormComponent) _formComponents.elementAt(i);
                if ((fc.getFlags() & IS_REQUIRED) == 0)
                    continue;
                c = fc.getFormComponent();
                if (fc.getCaptionTextComp() != null) {
                    caption = fc.getCaptionString();
                    if (caption.endsWith(":"))
                        caption = caption.substring(0, caption.length() - 1);
                    if (caption.startsWith("*"))
                        caption = caption.substring(1);
                    if (caption.endsWith("*"))
                        caption = caption.substring(0, caption.length() - 1);
                } else
                    caption = null;
                if ((c != null) && !validateComponent((HtmlComponent) c, caption))
                    return false;
            }
        }
        return true;
    }
    /**
     * Perform validation recursively on a component.
     * @return boolean
     * @param comp com.salmonllc.html.HtmlComponent
     * @param caption java.lang.String
     */
    protected boolean validateComponent(HtmlComponent comp, String name) {
        if (comp instanceof HtmlFormComponent) {
            HtmlFormComponent hfc = (HtmlFormComponent) comp;
            if (hfc.getValue() == null) {
                if (!processError(DetailFormListener.ERROR_EMPTY_FIELD, name, hfc))
                    return false;
            }
            if (!hfc.isValid()) {
                if (!processError(DetailFormListener.ERROR_INVALID_ENTRY, name, hfc))
                    return false;
            }
        } else if (comp instanceof HtmlContainer) {
            Enumeration e = ((HtmlContainer) comp).getComponents();
            while (e.hasMoreElements())
                if (!validateComponent((HtmlComponent) e.nextElement(), name))
                    return false;
        }
        return true;
    }
    /**
     * This method checks the entered value to see if it passes validation test.
     * @return boolean
     * @param e com.salmonllc.html.events.ValueChangedEvent
     */
    public boolean valueChanged(ValueChangedEvent e) throws Exception {
        DataStoreBuffer ds = e.getDataStore();
        if (ds == null)
            return true;
        if (!ds.isFormattedStringValid(e.getColumn(), e.getNewValue())) {
            String msg = "Invalid value entered";
            FormComponent fc = findFormComponent(e.getComponent());
            if (fc != null)
                msg += " for " + fc.getCaptionString();
            if (!processError(ERROR_ANY, msg, e.getComponent())) {
                e.setAcceptValue(ValueChangedEvent.PROCESSING_NOTENTERED);
                return false;
            } else
                return true;
        } else
            return true;
    }

    public void fileUploaded(FileUploadEvent e){
        if (_fileUploadDirectory == null )
           return;
        try {

           byte[] bContent  = e.getContent();
           String sFileName = e.getShortFileName();

           if (bContent==null || bContent.length == 0) {
               return ;
           }
           File f = null;
           FileOutputStream fos = null;
           if (sFileName != null)
           {
              f = new File(_fileUploadDirectory + File.separator +  sFileName);

              if (f.exists() )
                 f.delete();


              fos = new FileOutputStream(f);
              fos.write(bContent);
              fos.flush();
              fos.close();
           }
       }
       catch (Exception ex) {
           System.out.println("Exception at Upload a file." + ex.getMessage() );
           ex.printStackTrace();
           return ;
       }
    }

    /**
     * Replaces default cancel button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setCancelImage(HtmlSubmitImage img) {
        // Cancel Button
        if (_btnCancel != null) {
            _box.removeHeadingComponent(_btnCancel);
        }
        img.addSubmitListener(this);
        _box.addHeadingComponent(_btnCancel = img);
    }

/**
 * Replaces default cancel button (if any) with an image button
 * @param imageUrl 	URL of image.
 */
public void setCancelImage(String imageUrl)
{
    if (_btnCancel != null)
        _box.removeHeadingComponent(_btnCancel);
    HtmlSubmitImage i = new HtmlSubmitImage("btnCancel", imageUrl, getPage());
    i.addSubmitListener(this);
    _box.addHeadingComponent(_btnCancel = i);
}
}
