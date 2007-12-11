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

import com.salmonllc.html.HtmlCheckBox;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlLink;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlScriptGenerator;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.HtmlText;
import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.BeanDataStore;
import com.salmonllc.sql.DSDataRow;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.sql.DirtyDataException;
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.sql.QBEBuilder;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * An extended version of the display box with addional functionality for building list forms
 */
public class JspListFormDisplayBox extends JspFormDisplayBox implements SubmitListener {
    public static final int MODE_EDIT = 0;
    public static final int MODE_DISPLAY_MULTI_PAGE = 1;
    public static final int MODE_DISPLAY_SINGLE_PAGE = 2;

    private int _mode;

    private String _validatorName;
    private String _detailFormName;
    private String _searchFormName;
    private JspDetailFormDisplayBox _detailForm;
    private JspSearchFormDisplayBox _searchForm;
    private JspDataTable _datatable;
    private DataStoreBuffer _ds;
    private HtmlSubmitButton _addButton;
    private HtmlSubmitButton _saveButton;
    private String _deleteBucketName;
    private Object _deleteBucketValue;
    private boolean _autoCreateLink=true;
    private String _detailPageURL;
    private HtmlLink _editLink;
    private String _saveButtonAccessKey;
    private String _addButtonAccessKey;
    private String _saveButtonCaption;
    private String _addButtonCaption;
    private int _rowToEdit;
    private String _rowHighlightColor;
    private int _maxRows = -1;
    private String _maxRowsErrorMessage = "These selections will retrieve more data rows then the maximum allowed. Please enter some additional selection criteria.";
    private HtmlSubmitButton _undoButton;
    private String _dirtyDataDeleteError =
            "Can't do save. A row you deleted has been modified by another user. Press \"Undo\" and then \"Save\" again to undo the delete and allow other changes to be saved. Press \"Search\" to reload the data so this row can be deleted. ";
    private String _dirtyDataUpdateError =
            "Can't do save. The highlighted row has already been modified by another user. Press \"Undo\" and then \"Save\" again to undo the changes and save other changed rows. Press \"Search\" to reload the data so this row can be changed and saved. ";
    private DataStoreEvaluator _lookupReturnEval;
    private DataStoreExpression _lookupReturnExp;
    private DataStoreEvaluator _lookupReturnDescEval;
    private DataStoreExpression _lookupReturnDescExp;

    private String _lookupReturnExpString;
    private String _lookupReturnDescExpString;

    private class SkipRowExpression implements DataStoreExpression {
        public Object evaluateExpression(DataStoreBuffer dsBuf, int row) throws DataStoreException {
            if (dsBuf.getRowStatus(row) == DataStoreBuffer.STATUS_NEW)
                return Boolean.TRUE;
            if (_deleteBucketName != null && _deleteBucketValue != null) {
                Object val = dsBuf.getAny(row, _deleteBucketName);
                if (val != null && val.equals(_deleteBucketValue))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
    /**
	 * @author  demian
	 */
    private class DataStoreSortListener implements ModelChangedListener {
        DSDataRow _oldRow;

        public void modelChanged(ModelChangedEvent evt) {
            if (evt.getType() == ModelChangedEvent.TYPE_DATA_SORTING) {
                if (evt.getDataStore().getRow() > -1) {
                    try {
                        _oldRow = evt.getDataStore().getDataStoreRow(evt.getDataStore().getRow(), DataStoreBuffer.BUFFER_STANDARD).getDSDataRow();
                    } catch (DataStoreException e) {
                    }
                }
            } else if (evt.getType() == ModelChangedEvent.TYPE_DATA_SORTED) {
                if (_oldRow != null) {
                    DataStoreBuffer ds = evt.getDataStore();
                    for (int i = 0; i < ds.getRowCount(); i++) {
                        try {
                            if (ds.getDataStoreRow(i, DataStoreBuffer.BUFFER_STANDARD).getDSDataRow() == _oldRow) {
                                ds.gotoRow(i);
                                if (_rowToEdit > -1)
                                    _rowToEdit = i;
                                break;
                            }
                        } catch (DataStoreException e) {
                        }
                    }

                }
            }
        }
    }

    private class SelectRowExpression implements DataStoreExpression {
        public Object evaluateExpression(DataStoreBuffer dsBuf, int row) throws DataStoreException {
            if (_mode == MODE_DISPLAY_MULTI_PAGE)
                return null;
            else if (_mode == MODE_EDIT) {
                if (_validator == null)
                    return null;
                else
                    return (_validator.isErrorOnLastPass() && row == dsBuf.getRow()) ? _rowHighlightColor : null;
            } else {
                if (row == dsBuf.getRow())
                    return _rowHighlightColor;
                else
                    return null;
            }
        }
    }

    private class DirtyDataUndoAction implements SubmitListener {
        int _row = -1;
        int _buffer = -1;

        public void setRowAndBuffer(int buffer, int row) {
            _buffer = buffer;
            _row = row;
        }
        public boolean submitPerformed(SubmitEvent e) throws Exception {
            if (_buffer == DataStoreBuffer.BUFFER_DELETED) {
                int row = _ds.unDeleteRow(_row);
                _ds.undoChanges(row);
                if (_deleteBucketName != null)
                    _ds.setAny(row, _deleteBucketName, null);
                jumpToPage(row);
            } else
                _ds.undoChanges(_row);
            return true;
        }
    }

    private DirtyDataUndoAction _ddAction = new DirtyDataUndoAction();

    private class LocalPageListener implements PageListener {
        public void pageRequested(PageEvent p) throws Exception {
            if (!getPage().isReferredByCurrentPage()) {
                HttpServletRequest r = getPage().getCurrentRequest();
                String refresh = r.getParameter("refresh");
                if (refresh != null && refresh.equalsIgnoreCase("true"))
                    doSearch();
            }
        }
        public void pageRequestEnd(PageEvent p) throws Exception {
        }
        public void pageSubmitEnd(PageEvent p) {
        }
        public void pageSubmitted(PageEvent p) {
        }
    }

    public JspListFormDisplayBox(String name, int mode, HtmlPage page) {
        this(name, mode, null, page);

    }

    public JspListFormDisplayBox(String name, int mode, String theme, HtmlPage page) {
        super(name, theme, page);
        _mode = mode;

        if (mode == MODE_EDIT) {
            _addButton = new HtmlSubmitButton(name + "addButton", "Add", theme, page);
            _saveButton = new HtmlSubmitButton(name + "saveButton", "Save", theme, page);
            _addButton.addSubmitListener(this);
            _saveButton.addSubmitListener(this);
            _buttons.add(_saveButton);
            _buttons.add(_addButton);
            add(_addButton, TYPE_COMP);
            add(_saveButton, TYPE_COMP);
        } else if (mode == MODE_DISPLAY_MULTI_PAGE) {
            _addButton = new HtmlSubmitButton(name + "addButton", "Add", theme, page);
            _addButton.addSubmitListener(this);
            _buttons.add(_addButton);
            add(_addButton, TYPE_COMP);
            getPage().addPageListener(new LocalPageListener());
        }

        _undoButton = new MessageButton(getPage(), "dirtyDataUndo", _undoButtonCap, _ddAction);
        _messageButtons.add(_undoButton);
        setUpButtons();
    }

    /**
     *Called by the tag handler to set the name of the validator. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
     */
    public void setValidatorName(String name) {
        _validatorName = name;
    }

    /**
     *Called by the tag handler to set the name of the ListForm. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
     */
    public void setDetailFormName(String name) {
        _detailFormName = name;
    }

    /**
     *Called by the tag handler to set the name of the ListForm. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
     */
    public void setSearchFormName(String name) {
        _searchFormName = name;
    }
    /**
     * Binds various components to the component based on their names passed to the constructor. CriteriaBuilder, CriteriaValidator and ListForm. This method is called by the framework and should not be called directly.
     */
    public void autoBindComponents() throws Exception {
        JspController cont = (JspController) getPage();

        if (getDataSource() != null) {
            _ds = cont.getDataSource(getDataSource());
            Enumeration enumera = getComponents();
            while (enumera.hasMoreElements()) {
                Object o = enumera.nextElement();
                if (o instanceof JspDataTable) {
                    _datatable = (JspDataTable) o;
                    break;
                }
            }
        } else {
            Enumeration enumera = getComponents();
            while (enumera.hasMoreElements()) {
                Object o = enumera.nextElement();
                if (o instanceof JspDataTable) {
                    _datatable = (JspDataTable) o;
                    _ds = _datatable.getDataStoreBuffer();
                    break;
                }
            }
        }

        //add an expression to set the style for the selected row
        if (_datatable != null) {
            for (int i = 0; i < _datatable.getComponentCount(); i++) {
                Object o = _datatable.getComponent(i);
                if (_datatable.getComponentType(i) == JspContainer.TYPE_ROW && o instanceof JspTableRow) {
                    _datatable.addPropertyExpression(o, "backgroundColor", new SelectRowExpression());
                    Enumeration enumera = ((JspContainer) o).getComponents();
                    while (enumera.hasMoreElements()) {
                        o = enumera.nextElement();
                        if (o instanceof JspTableCell)
                            _datatable.addPropertyExpression(o, "backgroundColor", new SelectRowExpression());

                    }
                }
            }
        }

        if (_validatorName != null)
            _validator = (HtmlValidatorText) cont.getComponent(_validatorName);
        else if (_mode == MODE_EDIT) {
            //see if there is a validator for this datastore
            Enumeration enumera = cont.getComponents();
            while (enumera.hasMoreElements()) {
                Object o = enumera.nextElement();
                if (o instanceof HtmlValidatorText) {
                    HtmlValidatorText test = (HtmlValidatorText) o;
                    if (test.getDataStore() == _ds) {
                        _validator = test;
                        break;
                    }
                }
            }
        }
        if (_validator == null && _ds != null && _mode == MODE_EDIT) {
            boolean hasRules = false;
            for (int i = 0; i < _ds.getColumnCount(); i++) {
                if (_ds.getValidationRulesForColumn(i) != null) {
                    hasRules = true;
                    break;
                }
            }
            if (hasRules) {
                _validator = new HtmlValidatorText(getName() + "validator", cont);
                _validator.setBreaksBefore(0);
                _validator.setBreaksAfter(2);
                _validator.setDataStore(_ds);
                _validator.importRules(_ds);
                _validator.setUseJavaScript(false);
                _validator.setMultipleErrorsOK(false);
                _validator.setAddFocusLinksToErrors(false);
                _validatorBuiltInternally = true;
                add(_validator, TYPE_COMP);
            }
        }

        if (_validator != null && _saveButton != null)
            _validator.addSubmitToListenTo(_saveButton);

        //find or create the delete check box
        if (_mode == MODE_EDIT && _deleteBucketName == null) {
            if (_datatable != null && _ds != null) {
                HtmlCheckBox cbx = findFirstUnboundCheckBox(_datatable);
                if (cbx != null) {
                    String name = getName() + "deleteCbx";
                    _ds.addBucket(name, DataStore.DATATYPE_INT);
                    cbx.setColumn(_ds, name);
                    cbx.setTrueValue("1");
                    cbx.setFalseValue(null);
                    _deleteBucketName = name;
                    _deleteBucketValue = new Integer(1);
                    if (_validator != null) {
                        try {
                            _validator.setSkipRowExpression(new SkipRowExpression());
                        } catch (DataStoreException e) {
                            MessageLog.writeErrorMessage("error setting skip row expression for validator", e, this);
                        }
                    }
                }
            }
        }

        //find the first text component in the table and change it to a link
        if ((_mode == MODE_DISPLAY_MULTI_PAGE || _mode == MODE_DISPLAY_SINGLE_PAGE) && _autoCreateLink && _datatable != null) {
            HtmlText t = findFirstTextComponent(_datatable);
            if (t != null) {
                if (!(t.getParent() instanceof JspLink)) {
                    String name = t.getName();
                    HtmlLink l = new HtmlLink(getFullName() + "zoomlink", "none", getPage());
                    HtmlText t2 = new HtmlText(getFullName() + "zoomtext", t.getText(), t.getStyle(), getPage());
                    t2.setFont(t.getFont());
                    t2.setFont(HtmlText.FONT_LINK);
                    DataStoreEvaluator eval = t.getExpressionEvaluator();
                    if (eval != null) {
                        DataStoreExpression exp = eval.getDataStoreExpression();
                        if (exp != null)
                            t2.setExpression(eval.getDataStore(), exp);
                        else
                            t2.setExpression(eval.getDataStore(), eval.getExpression());

                    }
                    l.addSubmitListener(this);
                    l.add(t2);
                    ((JspController) getPage()).replaceComponent(name, l);
                    _editLink = l;
                }
            }
        }

        //if there is a detail form on the page, find it and use it
        if (_detailFormName != null)
            _detailForm = (JspDetailFormDisplayBox) cont.getComponent(_detailFormName);
        else {
            Enumeration enumera = cont.getComponents();
            while (enumera.hasMoreElements()) {
                Object o = enumera.nextElement();
                if (o instanceof JspDetailFormDisplayBox) {
                    _detailForm = (JspDetailFormDisplayBox) o;
                    break;
                }
            }
        }

        //if there is a search form on the page, find it and use it
        if (_searchFormName != null)
            _searchForm = (JspSearchFormDisplayBox) cont.getComponent(_searchFormName);
        else {
            Enumeration enumera = cont.getComponents();
            while (enumera.hasMoreElements()) {
                Object o = enumera.nextElement();
                if (o instanceof JspSearchFormDisplayBox) {
                    _searchForm = (JspSearchFormDisplayBox) o;
                    break;
                }
            }
        }

        //for sorting, add a listener that keeps the row the same after a sort
        if (_ds != null && _mode == MODE_DISPLAY_SINGLE_PAGE) {
            _ds.addModelChangedListener(new DataStoreSortListener());
        }

        //setup the lookup return expression
        setupLookupReturnEvaluator();
        setupLookupDescReturnEvaluator();

    }
    /**
     * Sets the save button visible or not
     */
    public void setSaveButtonVisible(boolean visible) {
        if (_saveButton != null)
            _saveButton.setVisible(visible);
    }
    /**
     * Sets the text to display on the save button
     */
    public void setSaveButtonCaption(String caption) {
        if (_saveButton != null)
            _saveButton.setDisplayName(caption);
    }

    /**
     * Sets the add button visible or not
     */
    public void setAddButtonVisible(boolean visible) {
        if (_addButton != null)
            _addButton.setVisible(visible);
    }

    /**
     * Sets the add button visible or not
     */
    public void setAddButtonCaption(String caption) {
        if (_addButton != null)
            _addButton.setDisplayName(caption);
    }

    /**
     * Returns the save button visible or not
     */
    public boolean isSaveButtonVisible() {
        if (_saveButton == null)
            return false;
        else
            return _saveButton.getVisible();
    }
    /**
     * Returns the text to display on the save button
     */
    public String getSaveButtonCaption() {
        if (_saveButton == null)
            return null;
        else
            return _saveButton.getDisplayName();
    }

    /**
     * Return the add button visible or not
     */
    public boolean isAddButtonVisible() {
        if (_addButton == null)
            return false;
        else
            return _addButton.getVisible();
    }

    /**
     * Returns the add button visible or not
     */
    public String getAddButtonCaption() {
        if (_addButton == null)
            return null;
        else
            return _addButton.getDisplayName();
    }

    /**
     * Returns the font style for the save and add buttons
     */
    public String getButtonFontStyle() {
        if (_saveButton == null)
            return null;
        else
            return _saveButton.getButtonFontStyle();
    }
    /**
     * Returns the background color for the save and add buttons
     */
    public String getButtonBgColor() {
        if (_saveButton == null)
            return null;
        else
            return _saveButton.getButtonBgColor();
    }

    /**
     * Adds a button to the display box
     */
    public void addButton(HtmlSubmitButton b) {
        _buttons.add(b);
        add(b, TYPE_COMP);
    }

    /**
     * Updates the display box button labels for the current language<br>
     * The language property file must have the following key structure<br>
     * FormDisplayBox.add represents the caption for the add button.<br>
     * FormDisplayBox.save represents the caption for the save button.<br>
     * FormDisplayBox.undo represents the caption for the ok button.<br>
     * ListFormDisplayBox.dirtyDataDeleteError represents the error message that occurs when a dirty data exception occurs on a deleted row<br>
     * ListFormDisplayBox.dirtyDataUpdateError represents the error message that occurs when a dirty data exception occurs on an updated row<br>
     * ListFormDisplayBox.maxRowsExceededError represents the error message that occurs when trying to load more data then is allowed<br>
     */
    public void updateLocale() {
        _updateLocale = true;
    }

    protected void processLocaleInfo() {
        if (_updateLocale) {
            _updateLocale = false;
            LanguagePreferences p = getPage().getLanguagePreferences();
            String appName = getPage().getApplicationName();
            String descr = null;
            String key = "FormDisplayBox.add";
            descr = LanguageResourceFinder.getResource(appName, key, p);
            if (descr != null)
                _addButtonCaption = descr;

            descr = null;
            key = "FormDisplayBox.save";
            descr = LanguageResourceFinder.getResource(appName, key, p);
            if (descr != null)
                _saveButtonCaption = descr;

            descr = null;
            key = "ListFormDisplayBox.dirtyDataDeleteError";
            descr = LanguageResourceFinder.getResource(appName, key, p);
            if (descr != null)
                _dirtyDataDeleteError = descr;

            descr = null;
            key = "ListFormDisplayBox.dirtyDataUpdateError";
            descr = LanguageResourceFinder.getResource(appName, key, p);
            if (descr != null)
                _dirtyDataUpdateError = descr;

            descr = null;
            key = "ListFormDisplayBox.maxRowsExceededError";
            descr = LanguageResourceFinder.getResource(appName, key, p);
            if (descr != null)
                _maxRowsErrorMessage = descr;

            super.processLocaleInfo();

        }
    }

    /**
     * @return the location for displaying the buttons
     */
    public int getButtonDisplayLocation() {
        return _buttonDisplayLocation;
    }

    /**
     * Sets the display location for a button. Valid values are BUTTON_DISPLAY_IN_HEADER and BUTTON_DISPLAY_BOX_BELOW_TABLE
     */
    public void setButtonDisplayLocation(int loc) {
        _buttonDisplayLocation = loc;
    }

    /**
     * framework method, do not call directly
     */
    public boolean submitPerformed(SubmitEvent e) throws Exception {
        if (e.getComponent() == _addButton)
            doAdd();
        else if (e.getComponent() == _saveButton)
            doSave();
        else if (e.getComponent() == _editLink) {
            if (isFromPopupLookup() || isFromLookup()) {
                returnValueToLookup(e.getRow());
            } else {
                _rowToEdit = e.getRow();
                doEdit();
            }
        }
        return true;
    }

    /*Claudio Pi - 7/1/2004 Encapsulated the return to lookup method so it can be called in other situtations*/
    public void returnValueToLookup(int row) throws DataStoreException, IOException {

        String lookupRet = null;
        String lookupDescRet = null;
        if (_lookupReturnEval != null)
            lookupRet = _lookupReturnEval.evaluateRowFormat(row);
        if (_lookupReturnDescEval != null)
            lookupDescRet = _lookupReturnDescEval.evaluateRowFormat(row);


        if (lookupDescRet==null)
            lookupDescRet="";
        else
            lookupDescRet=escapeSingleQuote(lookupDescRet);
        if (isFromPopupLookup()) {
            JspController thisCont=getController();
            HtmlScriptGenerator gen=new HtmlScriptGenerator(thisCont);
            String script =gen.generateReturnValueToLookupScript(lookupRet,lookupDescRet);
            getPage().writeScript(script);
        } else {
            getPage().getCurrentResponse().sendRedirect(getLookupReturnToURL(lookupRet,lookupDescRet));
        }
    }

    /**
     * This method gets fired when the user clicks an edit link. Subclasses can override it to customize behavior
     */
    public void doEdit() throws SQLException, DataStoreException, Exception {
        if (_mode == MODE_DISPLAY_MULTI_PAGE) {
            try {
                StringBuffer filterParm = new StringBuffer();
                if (_ds instanceof DataStore) {
                    DataStore ds = (DataStore) _ds;
                    String tableList[] = _ds.getTableList(false);
                    for (int i = 0; i < tableList.length; i++) {
                        String where = ds.buildCriteriaStringForRow(getRowToEdit(), tableList[i]);
                        if (where != null && where.length() > 0) {
                            filterParm.append("&id=");
                            filterParm.append(tableList[i]);
                            filterParm.append(":");
                            filterParm.append(Util.urlEncode(where, true));
                        }
                    }
                } else if (_ds instanceof BeanDataStore) {
                    BeanDataStore ds = (BeanDataStore) _ds;
                    String criteriaString = ds.buildCriteriaStringForRow(getRowToEdit());
                    if (criteriaString != null && criteriaString.length() > 0) {
                        filterParm.append("&id=");
                        filterParm.append(Util.urlEncode(criteriaString, true));
                    }
                }
                String returnTo = getURI();
                getPage().getCurrentResponse().sendRedirect(_detailPageURL + "?mode=edit" + filterParm.toString() + "&listPage=" + returnTo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (_mode == MODE_DISPLAY_SINGLE_PAGE) {
            if (_detailForm != null)
                _detailForm.tryEdit();
            else if (_ds != null)
                _ds.gotoRow(getRowToEdit());
        }
    }
    /**
     * This method gets fired when the user clicks the add button. Subclasses can override it to customize behavior
     */
    public void doAdd() throws SQLException, DataStoreException, Exception{
        if (_mode == MODE_EDIT) {
            setVisible(true);
            DataStoreBuffer ds = getDataStore();
            if (ds != null) {
                ds.insertRow();
                if (getDataTable() != null) {
                    JspDataTable tab = getDataTable();
                    if (!tab.isRowOnPage(ds.getRowCount() - 1))
                        tab.setPage(tab.getPage(ds.getRowCount() - 1));

                    HtmlFormComponent comp = findFirstFormComponent(tab);
                    if (comp != null)
                        comp.setFocus(ds.getRowCount() - 1);
                }
            }
        } else if (_mode == MODE_DISPLAY_MULTI_PAGE) {
            if (_detailPageURL != null) {
                try {
                    String returnTo = getURI();
                    getPage().getCurrentResponse().sendRedirect(_detailPageURL + "?mode=add&listPage=" + returnTo);

                } catch (IOException e) {
                    MessageLog.writeErrorMessage("doAdd()", e, this);
                }
            }
        } else if (_mode == MODE_DISPLAY_SINGLE_PAGE && _detailForm != null) {
            setVisible(true);
            _detailForm.tryAdd();
        }
    }

    /**
     * This method gets fired when the user clicks the save button. Subclasses can override it to customize behavior
     */
    public void doSave() throws Exception {
        if (getDataStore() != null) {
            DataStoreBuffer dsb = getDataStore();
            for (int i = dsb.getRowCount() - 1; i >= 0; i--) {
                if (_deleteBucketName != null && _deleteBucketValue != null) {
                    Object o;
                    try {
                        o = dsb.getAny(i, _deleteBucketName);
                        if (_deleteBucketValue.equals(o))
                            dsb.deleteRow(i);
                        else if (dsb.getRowStatus(i) == DataStoreBuffer.STATUS_NEW)
                            dsb.deleteRow(i);
                    } catch (DataStoreException e) {
                    }

                } else if (dsb.getRowStatus(i) == DataStoreBuffer.STATUS_NEW)
                    dsb.deleteRow(i);

            }
            try {
                doDataStoreUpdate();
                _datatable.setPage(0);
            } catch (DirtyDataException ex) {
                if (_validator != null) {
                    int row = ex.getRow();
                    String message = null;
                    if (ex.getBuffer() == DataStoreBuffer.BUFFER_DELETED) {
                        dsb.clearSelectedRow();
                        message = _dirtyDataDeleteError;
                    } else {
                        jumpToPage(row);
                        message = _dirtyDataUpdateError;
                    }
                    _ddAction.setRowAndBuffer(ex.getBuffer(), row);
                    _validator.setErrorMessage(message, null, row, _undoButton);
                }
            } catch (DataStoreException ex) {
                if (_validator != null)
                    _validator.addErrorMessage(ex.getMessage(), ((JspController) getPage()).getBoundComponent(_ds, ex.getColumn()), ex.getRow());
            } catch (Exception ex) {
                if (_validator != null)
                    _validator.addErrorMessage(ex.getMessage());
            }

        }
    }

    public void doSearch() throws SQLException, DataStoreException, Exception {
        QBEBuilder search = null;
        if (_searchForm != null)
            search = _searchForm.getCriteriaBuilder();
        DataStoreBuffer ds = getDataStore();
        if (search != null && ds != null) {
            if (_maxRows != -1) {
                try {
                    if (doDataStoreEstimateRowsRetrieved(search.generateSQLFilter(ds)) > _maxRows)
                        throw new SQLException(_maxRowsErrorMessage);
                } catch (Exception e) {
                    throw new DataStoreException(e.getMessage(), e);
                }
            }
            doDataStoreRetrieve(search.generateSQLFilter(ds));
            ds.gotoFirst();
        }
        if (getDataTable() != null)
            getDataTable().setPage(0);
        setVisible(true);
        if (_mode == MODE_DISPLAY_SINGLE_PAGE && _detailForm != null) {
            if (_detailForm.getDataStore() != _ds && _ds.getRowCount() > 0) {
                _detailForm.getDataStore().reset();
                _rowToEdit = 0;
                doEdit();
                _detailForm.setVisible(_detailForm.getDataStore().getRowCount() == 1);
            } else {
                if (_ds.getRowCount() > 0)
                    doEdit();
                else {
                    _detailForm.setVisible(false);
                    _detailForm.getDataStore().reset();
                }
            }

        }
    }

    /**
     * returns the criteria validator the component is using to validate entered criteria
     */
    public HtmlValidatorText getValidator() {
        return _validator;
    }

    /**
     * @return
     */
    public JspDataTable getDataTable() {
        return _datatable;
    }

    /**
     * @return
     */
    public DataStoreBuffer getDataStore() {
        return _ds;
    }

    /**
     * Set the access key for the add button
     */
    public void setAddButtonAccessKey(String key) {
        _addButton.setAccessKey(key);
    }
    /**
     * Set the access key for the save button
     */
    public void setSaveButtonAccessKey(String key) {
        _saveButton.setAccessKey(key);
    }

    /**
     * Set to true to automatically create a link to zoom to a detail row (MODE_DISPLAY_MULTIPAGE, MODE_DISPLAY_SINGLE_PAGE only)
     */
    public void setAutoCreateLink(boolean autoCreate) {
        _autoCreateLink = autoCreate;
    }

    /**
     * Sets the url for the JSP page representing the detail page for this list page (MODE_DISPLAY_MULTIPAGE only)
     */
    public void setDetailPageURL(String url) {
        url = translateSiteMapURL(url);
        _detailPageURL = url;
    }

    /**
     * Sets the url for the JSP page representing the detail page for this list page by looking up the page in the site map(MODE_DISPLAY_MULTIPAGE only)
     */
    public void setDetailPageFromSiteMap(String logicalName) {
        _detailPageURL = ((JspController) getPage()).getSiteMapURL(logicalName);
    }

    /**
     * Sets the theme for the component
     */
    public void setTheme(String theme) {
        super.setTheme(theme);
        Props props = getPage().getPageProperties();
        _saveButtonCaption = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_SAVE_BUTTON_CAPTION);
        _saveButtonAccessKey = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_SAVE_BUTTON_ACCESS_KEY);
        _addButtonCaption = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION);
        _addButtonAccessKey = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY);
        _rowHighlightColor = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_ROW_HIGHLIGHT_COLOR);
        String st = props.getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_BUTTON_LOCATION);
        if (st != null) {
            if (st.equalsIgnoreCase("BELOW_TABLE"))
                setButtonDisplayLocation(BUTTON_DISPLAY_BELOW_TABLE);
            else if (st.equalsIgnoreCase("IN_HEADER"))
                setButtonDisplayLocation(BUTTON_DISPLAY_IN_HEADER);
            else if (st.equalsIgnoreCase("IN_HEADER_AND_BELOW_TABLE"))
                setButtonDisplayLocation(BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE);
        }
        setUpButtons();
    }

    protected void setUpButtons() {
        super.setUpButtons();
        if (_saveButton != null) {
            if (_saveButtonCaption != null)
                _saveButton.setDisplayName(_saveButtonCaption);
            if (_saveButtonAccessKey != null)
                _saveButton.setAccessKey(_saveButtonAccessKey);
        }
        if (_addButton != null) {
            if (_addButtonCaption != null)
                _addButton.setDisplayName(_addButtonCaption);
            if (_addButtonAccessKey != null)
                _addButton.setAccessKey(_addButtonAccessKey);
        }

    }

    /**
     * Returns the last row selected when an edit link was clicked
     */
    public int getRowToEdit() {
        return _rowToEdit;
    }

    /**
     * Sets the the row in the table to edit
     */
    public void setRowToEdit(int row) {
        _rowToEdit = row;
    }

    /**
     * @return the background color for the selected row
     */
    public String getRowHighlightColor() {
        return _rowHighlightColor;
    }

    /**
     * Sets the background color for the selected row
     */
    public void setRowHighlightColor(String string) {
        _rowHighlightColor = string;
    }

    private void jumpToPage(int row) {
        if (_datatable != null) {
            if (!_datatable.isRowOnPage(row))
                _datatable.setPage(_datatable.getPage(row));
        }
        if (_ds != null)
            _ds.gotoRow(row);
    }

    /**
     * Sets the error message for a dirty data exception
     * @param _deleteErrorMessage The message to display if a dirty data exception occurs on a deleted row
     * @param _updateErrorMessage The message to display if a dirty data exception occurs on a updated row
     */
    public void setDirtyDataErrorMessages(String _deleteErrorMessage, String _updateErrorMessage) {
        _dirtyDataDeleteError = _deleteErrorMessage;
        _dirtyDataUpdateError = _updateErrorMessage;
    }

    /**
     * Returns true if the data in the list has been modified
     */
    public boolean isDataModified() {
        if (_mode == MODE_EDIT) {
            for (int i = 0; i < _ds.getRowCount(); i++) {
                int stat = _ds.getRowStatus(i);
                if (stat == DataStoreBuffer.STATUS_MODIFIED)
                    return true;
                else if (stat == DataStoreBuffer.STATUS_NEW_MODIFIED)
                    return true;
            }
        } else if (_mode == MODE_DISPLAY_SINGLE_PAGE) {
            if (_detailForm != null)
                return _detailForm.isDataModified();
        }

        return false;
    }
    /**
     * returns the maximum number of rows the list is allowed to retrieve (-1 for unlimited)
     */
    public int getMaxRows() {
        return _maxRows;
    }

    /**
     * sets the maximum number of rows the list is allowed to retrieve (-1 for unlimited)
     */
    public void setMaxRows(int i) {
        _maxRows = i;
    }

    /**
     * sets the error message that will be displayed when the maximum number of rows would be exceeded
     */
    public void setMaxRowsErrorMessage(String string) {
        _maxRowsErrorMessage = string;
    }

    private String getURI() {
        return Util.urlEncode(((JspController) getPage()).getCurrentRequest().getRequestURI(), true);
    }

    public void generateHTML(TagWriter t, String boxBody) throws Exception {
        boolean oldVis[] = null;
        if (isFromLookup() || isFromPopupLookup()) {
            oldVis = getButtonsVisible();
            if (_addButton != null)
                _addButton.setVisible(false);
            if (_saveButton != null)
                _saveButton.setVisible(false);
        }
        super.generateHTML(t, boxBody);
        if (oldVis != null)
            setButtonVisible(oldVis);

    }

    /**
     * If this component is being called from a lookup, the datastore expression to return to compute to return to the calling lookup
     */
    public void setLookupReturnExpression(String exp) throws DataStoreException {
        _lookupReturnExpString = exp;
        _lookupReturnExp = null;
        setupLookupReturnEvaluator();

    }

    /**
     * If this component is being called from a lookup, the datastore expression to return to compute to return to the calling lookup
     */
    public void setLookupReturnExpression(DataStoreExpression exp) throws DataStoreException {
        _lookupReturnExpString = null;
        _lookupReturnExp = exp;
        setupLookupReturnEvaluator();

    }

    /**
     * If this component is being called from a lookup, the datastore expression to return to compute a description to return to the calling lookup
     */
    public void setLookupDescReturnExpression(DataStoreExpression exp) throws DataStoreException {
        _lookupReturnDescExpString = null;
        _lookupReturnDescExp = exp;
        setupLookupDescReturnEvaluator();

    }

    /**
     * If this component is being called from a lookup, the datastore expression to return to compute a description to return to the calling lookup
     */
    public void setLookupDescReturnExpression(String exp) throws DataStoreException {
        _lookupReturnDescExpString = exp;
        _lookupReturnDescExp = null;
        setupLookupDescReturnEvaluator();

    }

    private void setupLookupDescReturnEvaluator() throws DataStoreException {
        if (_ds == null)
            return;
        if (_lookupReturnDescExp != null)
            _lookupReturnDescEval = new DataStoreEvaluator(_ds, _lookupReturnDescExp);
        else if (_lookupReturnDescExpString != null)
            _lookupReturnDescEval = new DataStoreEvaluator(_ds, _lookupReturnDescExpString);
    }


    private void setupLookupReturnEvaluator() throws DataStoreException {
        if (_ds == null)
            return;
        if (_lookupReturnExp != null)
            _lookupReturnEval = new DataStoreEvaluator(_ds, _lookupReturnExp);
        else if (_lookupReturnExpString != null)
            _lookupReturnEval = new DataStoreEvaluator(_ds, _lookupReturnExpString);
    }

    /**
     * Returns the datastore evaluator used to process a return from a lookup
     */
    protected DataStoreEvaluator getLookupReturnEval() {
        return _lookupReturnEval;
    }

    /**
     * Returns the datastore evaluator used to process a return description from a lookup
     */
    protected DataStoreEvaluator getLookupDescReturnEval() {
        return _lookupReturnDescEval;
    }
    /**
     * Returns the url for the detail page this list form will zoom to
     */
    protected String getDetailPageURL() {
        return _detailPageURL;
    }

    /**
     * Override this method in subclasses to change the way the rows retrieved will be estimated
     */
    public int doDataStoreEstimateRowsRetrieved(String criteria) throws Exception {
        DataStoreBuffer ds = getDataStore();
        if (ds instanceof DataStore)
            return ((DataStore) ds).estimateRowsRetrieved(criteria);
        else
            return 0;
    }
    /**
     * Override this method in subclasses to change the way the datastore is updated
     */
    public void doDataStoreUpdate() throws Exception {
        DataStoreBuffer ds = getDataStore();
        if (ds instanceof DataStore)
            ((DataStore) ds).update();
        else if (ds instanceof BeanDataStore)
            ((BeanDataStore) ds).update();
    }

    /**
     * Override this method in subclasses to change the way the datastore is retrieved
     */
    public void doDataStoreRetrieve(String where) throws Exception {
        if (where != null && where.length() == 0)
            where = null;
        DataStoreBuffer ds = getDataStore();
        if (ds instanceof DataStore)
            ((DataStore) ds).retrieve(where);
        else if (ds instanceof BeanDataStore)
            ((BeanDataStore) ds).retrieve(where);
    }
    /**
     * @return the save button used by this component
     */
    public HtmlSubmitButton getSaveButton() {
        return _saveButton;
    }

    /**
     * @return the add button used by this component
     */
    public HtmlSubmitButton getAddButton() {
        return _addButton;
    }

    private String escapeSingleQuote(String messageIn) {
        if (messageIn.indexOf('\'') == -1)
            return messageIn;
        StringBuffer sb = new StringBuffer(messageIn.length() + 10);
        for (int i = 0; i < messageIn.length(); i++) {
            char c = messageIn.charAt(i);
            if (c == '\'') {
                sb.append('\\');
                sb.append('\'');
            } else
                sb.append(c);
        }
        return sb.toString();
    }
}
