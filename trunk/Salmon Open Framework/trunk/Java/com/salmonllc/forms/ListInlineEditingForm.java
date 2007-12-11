package com.salmonllc.forms;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/forms/ListInlineEditingForm.java $
//$Author: Srufle $
//$Revision: 17 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////


import com.salmonllc.html.*;
import com.salmonllc.html.events.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This class is used for making a list form that you can add, delete ,edit and save values directly in the listing datatable
 */
public class ListInlineEditingForm extends BaseListForm implements ValueChangedListener
{
    /** Constant definitions for flags parameter in constructor */
    /** constructor flags parameter:  Do not create the default Save button. */
    public static final int INIT_NO_SAVE_BUTTON = 1 << 2;
    /** constructor flags parameter:  Do not create the default Delete button. */
    public static final int INIT_NO_DELETE_BUTTON = 1 << 3;


    /** addColumn flags parameter: Make the field an edit field  */
    public static final int MAKE_EDIT = 1 << 9;
    /** addColumn flags parameter:  Field is required. */
    public static final int IS_REQUIRED = 1 << 10;
    //
    public static final int ERROR_ANY = 99;
    /**  Other */
    protected HtmlComponent _btnSave = null;
    protected HtmlComponent _btnDelete = null;
    /**  delete bucket */
    public static final String _bktDelete = "_bktDelete";
    protected String _mode;
    private boolean _initVerifyDone = false;
    private DataStore _dsVerify;

    /**
     * Information on components in form is stored in a vector of objects of type
     * FormComponent.
     */
    private Vector _formComponents = new Vector();

    /**
     * Implements standard Search/List form.
     * Default data store is created.  Standard add button is included.
     * @param page 	Page containing this form as a component.
     */
    public ListInlineEditingForm(HtmlPage page)
    {
        this(page, null, 0);
    }
    /**
     * Implements standard Search/List form.
     * Standard add button is included.
     * @param page HtmlPage			Page containing this form as a component.
     * @param ds DataStore			Data store object to use; if null then create one.
     */
    public ListInlineEditingForm(HtmlPage page, DataStore ds)
    {
        this(page, ds, 0);
    }
    /**
     * Implements standard Search/List form.
     * @param page HtmlPage			Page containing this form as a component.
     * @param ds DataStore			Data store object to use; if null then create one.
     * @param flags 			Bitwise-OR combination of INIT_NO_SAVE_BUTTON, etc.
     */
    public ListInlineEditingForm(HtmlPage page, DataStore ds, int flags)
    {
        super(page, ds, flags);
        _mode = "edit";
        /** Add Save button to  List display box */
        if ((flags & INIT_NO_SAVE_BUTTON) == 0)
        {
            HtmlSubmitButton b = new HtmlSubmitButton("btnSave", "Save", page);
            _boxList.addHeadingComponent(b);
            b.addSubmitListener(this);
            _btnSave = b;
        }
        if ((flags & INIT_NO_DELETE_BUTTON) == 0)
        {
            HtmlSubmitButton b = new HtmlSubmitButton("btnDelete", "Delete", page);
            _boxList.addHeadingComponent(b);
            b.addSubmitListener(this);
            _btnDelete = b;
        }

        /** Initialize datastore to use for primary-key verification */
        _dsVerify = new DataStore(page.getApplicationName());


        /** As a favor to client classes, add page as listener if appropriate */
        if (page instanceof ListInLineEditingFormListener)
            addListener((ListInLineEditingFormListener) page);
    }
    /**
     * Creates a datastore bucket and corresponding display components for the search and list
     * boxes.
     * @param name		Name of bucket
     * @param caption	Caption for search box and/or list box, or null
     * @param type		Type of datastore column, using DataStore.DATATYPE_* values
     * @param flags		Bitwise-OR combination of PRIMARY_KEY, etc.  0 = default.
     */
    public void addBucket(String name, String caption, int type, int flags) throws Exception
    {
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
    public void addBucket(String name, String caption, int type, int flags, String href, String format) throws Exception
    {
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
            HtmlLink hl = null;
            HtmlPage page = getPage();
            HtmlText ht = null;
            HtmlTextEdit hte = null;
            if (format == null)
            {
                /** For certain data types, set format according to page properties. */
                Props props = getPage().getPageProperties();
                switch (type)
                {
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
            }
            if ((flags & MAKE_EDIT) != 0)
            {
                hte = (HtmlTextEdit) makeComponent(name, type);
                hte.setColumn(_ds, name);

                addListDisplay(name, caption, hte, propList);
                addFormComponent(caption, hte, flags);
            }
            else
            {
                /** create a text comp */
                ht = new HtmlText(name, page);

                /** check the format */
                if (format != null)
                {
                    ht.setExpression(_ds, name, format);
                }
                else
                {
                    ht.setExpression(_ds, name);
                }

                /** add text comp to a link container if applicable */
                if (Util.isFilled(href))
                {
                    hl = new HtmlLink("lnk" + name, "", page);
                    hl.setHrefExpression(_ds, href);
                    hl.add(ht);
                    ht.setFont(_linkFont);
                    addListDisplay(name, caption, hl, propList);
                }
                else
                {
                    addListDisplay(name, caption, ht, propList);
                }
            }
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
                          int flags) throws Exception
    {
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
                          int flags, String href) throws Exception
    {
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
    public void addColumn(String table, String column, String caption, int type, int flags, String href, String format) throws Exception
    {
        addColumn(table, column, caption, type, flags, href, format, null, null, null);
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

        /** `Don't add the same column twice to the data store. */
        if (_ds.getColumnIndex(table + "." + column) == -1)
        {
            /** changed to make all columns editable */
            if ((flags & PRIMARY_KEY) != 0)
            {
                _ds.addColumn(table, column, type, true, true);
            }
            else
            {
                _ds.addColumn(table, column, type, false, true);
            }
        }


        if ((flags & LIST_ONLY) == 0)
        {
            addSearchDisplay(fullColName, caption, makeComponent(table + "_" + column, type), flags, propCaption, propSearch);
        }
        if ((flags & SEARCH_ONLY) == 0)
        {
            HtmlLink hl = null;
            HtmlPage page = getPage();
            HtmlText ht = null;
            HtmlTextEdit hte = null;
            if (format == null)
            {
                /** For certain data types, set format according to page properties. */
                Props props = getPage().getPageProperties();
                switch (type)
                {
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
            }
            if ((flags & MAKE_EDIT) != 0)
            {
                hte = (HtmlTextEdit) makeComponent(table + "_" + column, type);
                hte.setColumn(_ds, fullColName);

                /** when you add a list display you will also have to call addFormComponent(caption, hte, flags); */
                addListDisplay(fullColName, caption, hte, propList);
                addFormComponent(caption, hte, flags);
            }
            else
            {
                /** create a text comp */
                ht = new HtmlText(table + "_" + column, page);

                /** check the format */
                if (format != null)
                {
                    ht.setExpression(_ds, fullColName, format);
                }
                else
                {
                    ht.setExpression(_ds, fullColName);
                }

                /** add text comp to a link container if applicable */
                if (Util.isFilled(href))
                {
                    hl = new HtmlLink("lnk" + column, "", page);
                    hl.setHrefExpression(_ds, href);
                    hl.add(ht);
                    ht.setFont(_linkFont);
                    addListDisplay(fullColName, caption, hl, propList);
                }
                else
                {
                    addListDisplay(fullColName, caption, ht, propList);
                }
            }
        }
    }
    /**
     * Adds a check box to each row so you can delete each checked row when you press the delete button.
     */
    public void addDeleteCheckBox()
    {
        try
        {
            if (_ds.getColumnIndex(_bktDelete) == -1)
            {
                _ds.addBucket(_bktDelete, DataStore.DATATYPE_STRING);
            }
            /** create a check box */
            HtmlCheckBox chkDelete = new HtmlCheckBox("chkDelete", getPage(), "1", "0");
            chkDelete.setColumn(_ds, _bktDelete);

            /** we may want to pass in an alignment prop */
            addListDisplay(_bktDelete, "Delete", chkDelete, null);
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("addDeleteCheckBox", e, this);
        }
    }
    /**
     * Adds a custom component to the List portion of the form.
     * @param caption java.lang.String
     * @param component com.salmonllc.html.HtmlComponent
     * @param flags int
     */
    protected void addFormComponent(String caption, HtmlComponent component, int flags)
    {

        HtmlText captionTextComp = new HtmlText(caption, getPage());
        _formComponents.addElement(new FormComponent(captionTextComp, component, flags));
        addValueChangedListener(component);
    }
    /**
     * Adds a ListInLineEditingFormListener.
     * @param listener ListInLineEditingFormListener
     */
    public void addListener(ListInLineEditingFormListener listener)
    {
        /** Prevent listener from being added twice */
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++)
            if (((ListInLineEditingFormListener) _listeners.elementAt(i)) == listener)
                return;
        _listeners.addElement(listener);
    }
    /**
     * Recursively adds ValueChangedListener.
     * @param c com.salmonllc.html.HtmlComponent
     */
    protected void addValueChangedListener(HtmlComponent c)
    {
        if (c instanceof HtmlFormComponent)
        {
            ((HtmlFormComponent) c).addValueChangedListener(this);
        }
        else
        {
            if (c instanceof HtmlContainer)
            {
                Enumeration e = ((HtmlContainer) c).getComponents();
                while (e.hasMoreElements())
                {
                    addValueChangedListener((HtmlComponent) e.nextElement());
                }
            }
        }
    }
    /**
     * Finds a component in the List Form Components
     * @return HtmlComponent
     * @param component HtmlComponent
     */
    protected HtmlComponent findFormComponent(HtmlComponent component)
    {
        int rowCompSize = _tblList.getRowComponentsSize();
        HtmlComponent comp = null;
        for (int i = 0; i < rowCompSize; i++)
        {
            comp = (HtmlComponent) _tblList.getRowComponentAt(i);
            if ((comp == component)
                    || ((comp instanceof HtmlContainer
                    && hasComponent((HtmlContainer) comp, component))))
                return comp;
        }
        return null;
    }
    /**
     * Recursively determins whether a container contains a component.
     * @return boolean
     * @param cont com.salmonllc.html.HtmlContainer
     * @param component com.salmonllc.html.HtmlComponent
     */
    protected boolean hasComponent(HtmlContainer cont, HtmlComponent component)
    {
        Enumeration e = cont.getComponents();
        HtmlComponent c = null;
        while (e.hasMoreElements())
        {
            c = (HtmlComponent) e.nextElement();
            if (c == component)
                return true;
            else if (c instanceof HtmlContainer)
            {
                if (hasComponent((HtmlContainer) c, component))
                    return true;
            }
        }
        return false;
    }
    /**
     * Process a user error condition.
     * @param code int
     * @param caption java.lang.String
     * @param component com.salmonllc.html.HtmlComponent
     * @param row int
     * @return boolean	If true, continue event processing.
     */
    protected boolean processError(int code, String caption, HtmlComponent component, int row)
    {
        int listenersSize = _listeners.size();
        if (listenersSize == 0)
            return true;
        String text = null;
        switch (code)
        {
            case DetailFormListener.ERROR_DUPLICATE_ROW:
                text = "Row in database already exists with same primary key on row " + (row + 1);
                break;
            case DetailFormListener.ERROR_EMPTY_FIELD:
                if ((component instanceof HtmlDropDownList) || (component instanceof HtmlRadioButtonGroup))
                    text = "Please select a value on row " + (row + 1);
                else
                    text = "Please enter a value on row " + (row + 1);
                break;
            case DetailFormListener.ERROR_INVALID_ENTRY:
                text = "Please enter a valid value on row " + (row + 1);
                break;
        }
        if (text == null)
            text = caption;
        else if (caption != null)
            text += " for " + caption;
        if (text != null)
            text += ".";
        for (int i = 0; i < listenersSize; i++)
            if (!((ListInLineEditingFormListener) _listeners.elementAt(i)).onListError(code, text, component, row))
                return false;
        return true;
    }
    /**
     * Replaces default delete button (if any) with a submit image
     * @param img - HtmlSubmitImage to use for the delete button.
     */
    public void setDeleteImage(HtmlSubmitImage img)
    {
        /** Delete Button */
        if (_btnDelete != null)
        {
            _boxList.removeHeadingComponent(_btnDelete);
        }
        img.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnDelete = img);
    }
    /**
     * Replaces default delete button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setDeleteImage(String imageUrl)
    {
        /** Delete Button */
        if (_btnDelete != null)
        {
            _boxList.removeHeadingComponent(_btnDelete);
        }
        HtmlSubmitImage i = new HtmlSubmitImage("btnDelete", imageUrl, getPage());
        i.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnDelete = i);
    }
    /**
     * Replaces default save button (if any) with a submit image
     * @param img - SumbitImage to use for the save button.
     */
    public void setSaveImage(HtmlSubmitImage img)
    {
        /** Save Button */
        if (_btnSave != null)
        {
            _boxList.removeHeadingComponent(_btnSave);
        }
        img.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnSave = img);
    }
    /**
     * Replaces default save button (if any) with an image button
     * @param imageUrl 	URL of image.
     */
    public void setSaveImage(String imageUrl)
    {
        /** Save Button */
        if (_btnSave != null)
        {
            _boxList.removeHeadingComponent(_btnSave);
        }
        HtmlSubmitImage i = new HtmlSubmitImage("btnSave", imageUrl, getPage());
        i.addSubmitListener(this);
        _boxList.addHeadingComponent(_btnSave = i);
    }
    /**
     * Sets the vivibility of the default Delete button.
     * @param visible boolean
     */
    public void showDeleteButton(boolean visible)
    {
        _btnDelete.setVisible(visible);
    }
    /**
     * Sets the vivibility of the default Save button.
     * @param visible boolean
     */
    public void showSaveButton(boolean visible)
    {
        _btnSave.setVisible(visible);
    }
    /**
     * Inherited abstract method.
     * @return boolean
     * @param e com.salmonllc.html.events.SubmitEvent
     */
    public boolean submitPerformed(SubmitEvent e) throws Exception
    {
        super.submitPerformed(e);

        MessageLog.writeDebugMessage(" submitPerformed(SubmitEvent e)", this);
        HtmlComponent c = e.getComponent();

        int listenersSize = _listeners.size();
        if (c == _btnAddListForm)
        {
            /** do preListAdd */

            for (int i = 0; i < listenersSize; i++)
            {
                if (!((ListFormListener) _listeners.elementAt(i)).preListAdd())
                {
                    return false;
                }
            }
            /** add a row to the data store */
            _ds.insertRow();
            _mode = "add";

            /** do postListAdd */
            for (int i = 0; i < listenersSize; i++)
            {
                ((ListFormListener) _listeners.elementAt(i)).postListAdd();
            }
            return true;
        }
        if (c == _btnDelete)
        {

            /** first delete any rows that are flagged */
            for (int i = 0; i < listenersSize; i++)
            {
                if (!((ListInLineEditingFormListener) _listeners.elementAt(i)).preListDelete())
                {
                    return false;
                }
            }
            /** you have to go from the bottom up for it to work */
            int rowCount = _ds.getRowCount() - 1;
            for (int i = rowCount; i >= 0; i--)
            {
                String data = _ds.getString(i, _bktDelete);
                int status = _ds.getRowStatus(i);
                /** Delete row if necessary */
                if (data != null)
                {
                    if (data.equals("1"))
                    {
                        switch (status)
                        {
                            case DataStore.STATUS_NOT_MODIFIED:
                            case DataStore.STATUS_MODIFIED:
                                if (!_ds.deleteRow(i))
                                {
                                    return false;
                                }
                                break;
                            case DataStore.STATUS_NEW:
                            case DataStore.STATUS_NEW_MODIFIED:
                                _ds.removeRow(i);
                                break;
                        }
                    }
                }
            }
            _ds.update();
            for (int i = 0; i < listenersSize; i++)
            {
                ((ListInLineEditingFormListener) _listeners.elementAt(i)).postListDelete();
            }
            doRetrieve();
            return true;
        }
        if (c == _btnSave)
        {

            /** Verification passed, go ahead with update */
            for (int i = 0; i < listenersSize; i++)
            {
                if (!((ListInLineEditingFormListener) _listeners.elementAt(i)).preListSave())
                {
                    return false;
                }
            }

            /** you have to go from the bottom up for it to work */
            int rowCount = _ds.getRowCount() - 1;
            for (int i = rowCount; i >= 0; i--)
            {
                int status = _ds.getRowStatus(i);
                switch (status)
                {
                    case DataStore.STATUS_NEW_MODIFIED:
                    case DataStore.STATUS_MODIFIED:
                        if (!validateRow(i))
                        {
                            return false;
                        }
                }
            }
            _ds.update();
            _mode = "edit";
            for (int i = 0; i < listenersSize; i++)
            {
                ((ListInLineEditingFormListener) _listeners.elementAt(i)).postListSave();
            }
            return true;
        }
        return true;
    }
    /**
     * Perform validation recursively on a component.
     * @return boolean
     * @param comp com.salmonllc.html.HtmlComponent
     * @param row int
     */
    protected boolean validateComponent(HtmlComponent comp, int row)
    {
        if (comp instanceof HtmlFormComponent)
        {
            HtmlFormComponent hfc = (HtmlFormComponent) comp;
            if (hfc.getValue() == null)
            {
                if (!processError(DetailFormListener.ERROR_EMPTY_FIELD, null, hfc, row))
                    return false;
            }
            if (!hfc.isValid())
            {
                if (!processError(DetailFormListener.ERROR_INVALID_ENTRY, null, hfc, row))
                    return false;
            }
        }
        else if (comp instanceof HtmlContainer)
        {
            Enumeration e = ((HtmlContainer) comp).getComponents();
            while (e.hasMoreElements())
                if (!validateComponent((HtmlComponent) e.nextElement(), row))
                    return false;
        }
        return true;
    }
    /**
     * Validates entry fields according to current mode.
     * @param row int
     * @return 	True if validation was successful.
     */
    public boolean validateRow(int row) throws Exception
    {
        String mode = null;
        if (_mode == null)
        {
            mode = "";
        }
        else
        {
            mode = _mode;
        }
        //
        if (mode.equals("add"))
        {
            /** Verify row with duplicate primary key combination does not exist */
            CriteriaString cr = new CriteriaString();

            int colCount = _ds.getColumnCount();
            String name = null;

            for (int colIndex = 0; colIndex < colCount; colIndex++)
            {
                if (!_ds.isPrimaryKey(colIndex))
                    continue;
                name = _ds.getColumnName(colIndex);
                switch (_ds.getColumnDataType(colIndex))
                {
                    case DataStore.DATATYPE_STRING:
                        cr.and(name + "='" + _ds.getString(row, colIndex) + "'");
                        break;
                    case DataStore.DATATYPE_INT:
                        cr.and(name + "=" + _ds.getInt(row, colIndex));
                        break;
                    case DataStore.DATATYPE_DOUBLE:
                        cr.and(name + "=" + _ds.getDouble(row, colIndex));
                        break;
                    case DataStore.DATATYPE_DATETIME:
                        cr.and(name + "='" + _ds.getDateTime(row, colIndex) + "'");
                        break;
                    default :
                        throw new FormException("Unknown datatype");
                }
                if (!_initVerifyDone)
                {
                    /** Copy an arbirary column into _dsVerify so we can do a retrieve on it. */
                    int dot = name.indexOf('.');
                    /** Check for table.column form even thought it should always be OK */
                    if (dot != -1)
                    {
                        _dsVerify.addColumn(name.substring(0, dot), name.substring(dot + 1), _ds.getColumnDataType(colIndex), _ds.isPrimaryKey(colIndex), false);
                        _initVerifyDone = true;
                    }
                }
            }
            String s = cr.toString();
            /**
             * problem: with multiple rows we have to validate each new row
             * when this was in detailform there was only one row
             */

            if ((s != null) && _initVerifyDone)
            {
                _dsVerify.retrieve(s);
                if (_dsVerify.gotoNext())
                {
                    if (!processError(DetailFormListener.ERROR_DUPLICATE_ROW, null, null, row))
                        return false;
                }
            }
        }

        /** Verify all required fields */
        if (mode.equals("edit"))
        {

            int formComponentsSize = _formComponents.size();
            FormComponent fc = null;
            HtmlComponent c = null;
            for (int i = 0; i < formComponentsSize; i++)
            {
                fc = (FormComponent) _formComponents.elementAt(i);
                if ((fc.getFlags() & IS_REQUIRED) == 0)
                    continue;
                c = fc.getFormComponent();
                if ((c != null) && !validateComponent((HtmlComponent) c, row))
                {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Tests that data entered in the edit components passes simple validation.
     * @return boolean
     * @param e com.salmonllc.html.events.ValueChangedEvent
     */
    public boolean valueChanged(ValueChangedEvent e) throws Exception
    {
        DataStoreBuffer ds = e.getDataStore();
        if (ds == null)
        {
            return true;
        }
        if (!ds.isFormattedStringValid(e.getColumn(), e.getNewValue()))
        {
            String msg = "Invalid value entered";
            HtmlComponent comp = findFormComponent(e.getComponent());
            if (comp != null)
                msg += " for column " + (e.getColumn() + 1) + " row " + (e.getRow() + 1);
            if (!processError(ERROR_ANY, msg, e.getComponent(), e.getRow()))
            {
                e.setAcceptValue(ValueChangedEvent.PROCESSING_NOTENTERED);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }
}
