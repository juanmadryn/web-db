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

import com.salmonllc.html.*;
import com.salmonllc.html.events.*;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * An extended version of the display box with addional functionality for building detail forms
 *
 */
public class JspDetailFormDisplayBox extends JspFormDisplayBox implements SubmitListener {
	public static final int MODE_LIST_ON_PAGE = 0;
	public static final int MODE_LIST_OFF_PAGE = 1;
	private String _validatorName;
	private DataStoreBuffer _ds;
	private String _dataStoreName;
	private HtmlSubmitButton _addButton, _saveButton, _cancelButton, _deleteButton;
	private String _saveButtonAccessKey, _addButtonAccessKey, _cancelButtonAccessKey, _deleteButtonAccessKey, _saveButtonCaption, _addButtonCaption, _cancelButtonCaption, _deleteButtonCaption;
	private JspListFormDisplayBox _listForm;
	private String _listFormName;
	private int _mode;
	private HtmlSubmitButton _okToEditButton, _okToDeleteButton, _okToCancelButton, _okToAddButton, _cancelMessageButton;
	private HtmlContainer _okToEdit, _okToDelete, _okToCancel, _okToAdd;
	private String _okToAddQuestion;// = "Cuidado, añadir un nuevo item causará que los cambios no guardados se pierdan. ¿Desea continuar de todas maneras?";
	private String _okToEditQuestion;// = "Cuidado, editar este item causará que los cambios no guardados se pierdan. ¿Desea continuar de todas maneras?"; //"Warning, editing this item will cause unsaved changes to be lost. Continue?";
	private String _okToDeleteQuestion;// = "Se dispone a eliminar un item. ¿Esta seguro?";
	private String _okToCancelQuestion;
	private String _dirtyDataError;// = "No se puede guardar. Esta fila ha sido modificada o eliminada por otro usuario. Por favor, refresque los datos e intente la operación nuevamente.";//"Can't do save. The row has already been modified or deleted by another user. Please reload the data and try your changes again. ";
	private boolean _confirmDelete = true;
	private DataStoreRow _listSelectedRow;
	private String _returnTo;
	private JspDetailFormDisplayBox _this = this;
	private boolean _displayOnly=false;
	private boolean _reloadRowAfterSave=true;
    private HtmlHiddenField _okToEditValue;
    private HtmlHiddenField _okToDeleteValue;
    private HtmlHiddenField _okToCancelValue;
    private HtmlHiddenField _okToAddValue;

    private class CancelAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			doCancel();
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_okToCancelValue.getValue() != null && _okToCancelValue.getValue().equals("1")) {
                doCancel();
            }
            return true;
        }
    }

	private class EditAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			doEdit();
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_okToEditValue.getValue() != null && _okToEditValue.getValue().equals("1")) {
                doEdit();
            }
            return true;
        }
    }

	private class DeleteAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			doDelete();
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_okToDeleteValue.getValue() != null && _okToDeleteValue.getValue().equals("1")) {
                doDelete();
            }
            return true;
        }
    }

	private class AddAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			doAdd();
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_okToAddValue.getValue() != null && _okToAddValue.getValue().equals("1")) {
                doAdd();
            }
            return true;
        }
    }

	private class CancelMessageAction implements SubmitListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			scrollToMe();
			return true;
		}
	}

	private class LocalPageListener implements PageListener {
		private boolean[] _oldButtonsVisible;
		private boolean[] _oldEnabled;

		public void pageRequested(PageEvent p) throws Exception {
			String displayOnly = getPage().getCurrentRequest().getParameter("displayOnly");
			String mode = getPage().getCurrentRequest().getParameter("mode");
			String listPage = getPage().getCurrentRequest().getParameter("listPage");
			_displayOnly=false;
			_oldEnabled = null;
			_oldButtonsVisible = null;
			if (_mode == MODE_LIST_OFF_PAGE) {
				if (mode == null || mode.equalsIgnoreCase("add")) {
					_oldButtonsVisible = getButtonsVisible();
					_deleteButton.setVisible(false);
				}
			}
			if (displayOnly != null && displayOnly.equalsIgnoreCase("true")) {
				_displayOnly=true;
				_oldButtonsVisible = getButtonsVisible();
				boolean cancelVis = _cancelButton.getVisible();
				for (int i = 0; i < _buttons.size(); i++)
					 ((HtmlSubmitButton) _buttons.get(i)).setVisible(false);
				if (listPage != null)
					_cancelButton.setVisible(cancelVis);
				_oldEnabled = new boolean[getComponentCount()];
				for (int i = 0; i < getComponentCount(); i++) {
					Object o = getComponent(i);
					if (o instanceof HtmlFormComponent) {
						HtmlFormComponent comp = (HtmlFormComponent) o;
						_oldEnabled[i] = comp.getEnabled();
						comp.setEnabled(false);
					}
				}
			}

			if (!getPage().isReferredByCurrentPage() && _mode == MODE_LIST_OFF_PAGE) {
				HttpServletRequest r = getPage().getCurrentRequest();
				if (listPage != null)
					_returnTo = listPage;

				if (mode == null)
					mode = "add";
				if (mode.equalsIgnoreCase("edit")) {
					String id[] = r.getParameterValues("id");
					if (_ds instanceof DataStore) {
						String tableList[] = _ds.getTableList(false);
						if (id != null) {
							StringBuffer where = new StringBuffer();
							for (int i = 0; i < id.length; i++) {
								String parm = id[i];
								int pos = parm.indexOf(":");
								if (pos > -1) {
									String table = parm.substring(0, pos);
									String crit = parm.substring(pos + 1);
									for (int j = 0; j < tableList.length; j++) {
										if (table.equalsIgnoreCase(tableList[j])) {
											if (where.length() != 0)
												where.append(" AND ");
											where.append(crit);
											break;
										}
									}
								}
							}
							if (where.length() > 0) {
								doDataStoreRetrieve(where.toString());
								if (!_ds.gotoFirst())
									_ds.insertRow();
								HtmlFormComponent comp = findFirstFormComponent(_this);
								if (comp != null)
									comp.setFocus();
							}
						}
					}
					else if (_ds instanceof BeanDataStore) {
						if (id != null) {
							doDataStoreRetrieve(id[0]);
							if (!_ds.gotoFirst())
								_ds.insertRow();
							HtmlFormComponent comp = findFirstFormComponent(_this);
							if (comp != null)
								comp.setFocus();
						}
					}
				} else {
					doAdd();
				}
			}
		}

		public void pageRequestEnd(PageEvent p) throws Exception {
			if (_oldButtonsVisible != null) {
				for (int i = 0; i < _buttons.size(); i++)
					 ((HtmlSubmitButton) _buttons.get(i)).setVisible(_oldButtonsVisible[i]);
			}
			if (_oldEnabled != null) {
				for (int i = 0; i < getComponentCount(); i++) {
						if (getComponent(i) instanceof HtmlFormComponent)
							 ((HtmlFormComponent) getComponent(i)).setEnabled(_oldEnabled[i]);
					}
			}
		}

		public void pageSubmitEnd(PageEvent p) {
		}
		public void pageSubmitted(PageEvent p) {
		}
	}

	public JspDetailFormDisplayBox(String name, HtmlPage page) {
		this(name, null, page);

	}

	public JspDetailFormDisplayBox(String name, String theme, HtmlPage page) {
		super(name, theme, page);

		_addButton = new HtmlSubmitButton(name + "addButton", "Añadir", theme, page);
		_saveButton = new HtmlSubmitButton(name + "saveButton", "Guardar", theme, page);
		_deleteButton = new HtmlSubmitButton(name + "cancelButton", "Eliminar", theme, page);
		_cancelButton = new HtmlSubmitButton(name + "deleteButton", "Cancelar", theme, page);

		_addButton.addSubmitListener(this);
		_saveButton.addSubmitListener(this);
		_deleteButton.addSubmitListener(this);
		_buttons.add(_saveButton);
		_buttons.add(_addButton);
		_buttons.add(_deleteButton);
		add(_addButton, TYPE_COMP);
		add(_saveButton, TYPE_COMP);
		add(_deleteButton, TYPE_COMP);
		_cancelButton.addSubmitListener(this);
		_buttons.add(_cancelButton);
		add(_cancelButton, TYPE_COMP);

		_okToEdit = new HtmlContainer("dfoktoeditquestion", page);
		_okToDelete = new HtmlContainer("dfoktodeletequestion", page);
		_okToCancel = new HtmlContainer("dfoktocancelquestion", page);
		_okToAdd = new HtmlContainer("dfoktoaddquestion", page);

        EditAction editAction = new EditAction();
        CancelAction cancelAction = new CancelAction();
        DeleteAction deleteAction = new DeleteAction();
        AddAction addAction = new AddAction();

		_cancelMessageButton = new MessageButton(getPage(), "dfcancelmessage", _cancelButtonCap, new CancelMessageAction());
		_okToEditButton = new MessageButton(getPage(), "dfoktoeditmessage", _okButtonCap, editAction);
		_okToCancelButton = new MessageButton(getPage(), "dfoktocancelmessage", _okButtonCap, cancelAction);
		_okToDeleteButton = new MessageButton(getPage(), "dfoktodeletemessage", _okButtonCap, deleteAction);
		_okToAddButton = new MessageButton(getPage(), "dfoktoaddmessage", _okButtonCap, addAction);

        _okToEditValue = new HtmlHiddenField("jsoktoeditvalue", "0", page);
        _okToEditValue.addValueChangedListener(editAction);
        add(_okToEditValue, TYPE_COMP);
        _hidden.add(_okToEditValue);

        _okToCancelValue = new HtmlHiddenField("jsoktocancelvalue", "0", page);
        _okToCancelValue.addValueChangedListener(cancelAction);
        add(_okToCancelValue, TYPE_COMP);
        _hidden.add(_okToCancelValue);

        _okToDeleteValue = new HtmlHiddenField("jsoktodeletevalue", "0", page);
        _okToDeleteValue.addValueChangedListener(deleteAction);
        add(_okToDeleteValue, TYPE_COMP);
        _hidden.add(_okToDeleteValue);

        _okToAddValue = new HtmlHiddenField("jsoktoaddvalue", "0", page);
        _okToAddValue.addValueChangedListener(addAction);
        add(_okToAddValue, TYPE_COMP);
        _hidden.add(_okToAddValue);

		_okToAdd.add(_okToAddButton);
    	_okToAdd.add(_cancelMessageButton);

		_okToEdit.add(_okToEditButton);
		_okToEdit.add(_cancelMessageButton);

		_okToDelete.add(_okToDeleteButton);
		_okToDelete.add(_cancelMessageButton);

		_okToCancel.add(_okToCancelButton);
		_okToCancel.add(_cancelMessageButton);

		_messageButtons.add(_okToDeleteButton);
		_messageButtons.add(_okToEditButton);
		_messageButtons.add(_okToCancelButton);
		_messageButtons.add(_okToAddButton);
		_messageButtons.add(_cancelMessageButton);

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
	public void setListFormName(String name) {
		_listFormName = name;
	}

	/**
	 *Called by the tag handler to set the name of the DataStore used by the DetailForm. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
	 */
	public void setDataStoreName(String string) {
		_dataStoreName = string;
	}
	/**
	 * Binds various components to the component based on their names passed to the constructor. CriteriaBuilder, CriteriaValidator and ListForm. This method is called by the framework and should not be called directly.
	 */
	public void autoBindComponents() throws Exception {
		JspController cont = (JspController) getPage();

		//get the datastore for this component
		if (getDataSource() != null)
			_ds = cont.getDataSource(getDataSource());
		else {
			//find the first component in the container bound to a datastore and use that instead
			Enumeration enumerar = getComponents();
			while (enumerar.hasMoreElements()) {
				Object o = enumerar.nextElement();
				if (o instanceof HtmlFormComponent) {
					DataStoreBuffer ds = ((HtmlFormComponent) o).getBoundDataStore();
					if (ds != null) {
						_ds = ds;
						break;
					}
				}
			}
		}

		//get the validator
		if (_validatorName != null)
			_validator = (HtmlValidatorText) cont.getComponent(_validatorName);
		else {
			//see if there is a validator for this datastore
			Enumeration enumerar = cont.getComponents();
			while (enumerar.hasMoreElements()) {
				Object o = enumerar.nextElement();
				if (o instanceof HtmlValidatorText) {
					HtmlValidatorText test = (HtmlValidatorText) o;
					if (test.getDataStore() == _ds) {
						_validator = test;
						break;
					}
				}
			}
		}
		//if the validator is null, build one from the validation rules in the model
		if (_validator == null && _ds != null) {
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
				_validator.setMultipleErrorsOK(true);
				_validator.setAddFocusLinksToErrors(true);
				_validatorBuiltInternally = true;
				add(_validator, TYPE_COMP);
			}
		}

		if (_validator == null) {
			_validator = new HtmlValidatorText(getName() + "validator", cont);
			_validator.setBreaksBefore(0);
			_validator.setBreaksAfter(2);
			_validatorBuiltInternally = true;
			add(_validator, TYPE_COMP);
		} else if (_saveButton != null)
			_validator.addSubmitToListenTo(_saveButton);

		//figure out what the list form is for this component
		if (_listFormName != null)
			_listForm = (JspListFormDisplayBox) cont.getComponent(_listFormName);
		else {
			Enumeration enumerar = cont.getComponents();
			while (enumerar.hasMoreElements()) {
				Object o = enumerar.nextElement();
				if (o instanceof JspListFormDisplayBox) {
					_listForm = (JspListFormDisplayBox) o;
					break;
				}
			}
		}

		if (_listForm != null)
			_mode = MODE_LIST_ON_PAGE;
		else
			_mode = MODE_LIST_OFF_PAGE;

		getPage().addPageListener(new LocalPageListener());

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
	 * Sets the delete button visible or not
	 */
	public void setDeleteButtonVisible(boolean visible) {
		if (_deleteButton != null)
			_deleteButton.setVisible(visible);
	}

	/**
	 * Sets the delete button visible or not
	 */
	public void setDeleteButtonCaption(String caption) {
		if (_deleteButton != null)
			_deleteButton.setDisplayName(caption);
	}

	/**
	 * Sets the cancel button visible or not
	 */
	public void setCancelButtonVisible(boolean visible) {
		if (_cancelButton != null)
			_cancelButton.setVisible(visible);
	}

	/**
	 * Sets the delete button visible or not
	 */
	public void setCancelButtonCaption(String caption) {
		if (_cancelButton != null)
			_cancelButton.setDisplayName(caption);
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
	 * Return the delete button visible or not
	 */
	public boolean isDeleteButtonVisible() {
		if (_deleteButton == null)
			return false;
		else
			return _deleteButton.getVisible();
	}

	/**
	 * Returns the delete button caption
	 */
	public String getDeleteButtonCaption() {
		if (_deleteButton == null)
			return null;
		else
			return _deleteButton.getDisplayName();
	}

	/**
	 * Return the cancel button visible or not
	 */
	public boolean isCancelButtonVisible() {
		if (_cancelButton == null)
			return false;
		else
			return _cancelButton.getVisible();
	}

	/**
	 * Returns the cancel button caption
	 */
	public String getCancelButtonCaption() {
		if (_cancelButton == null)
			return null;
		else
			return _cancelButton.getDisplayName();
	}
	/**
	 * Returns the font style for the buttons
	 */
	public String getButtonFontStyle() {
		if (_saveButton == null)
			return null;
		else
			return _saveButton.getButtonFontStyle();
	}
	/**
	 * Returns the background color for the buttons
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
	  * FormDisplayBox.delete represents the caption for the delete button.<br>
	  * FormDisplayBox.cancel represents the caption for the cancel button.<br>
	  * FormDisplayBox.ok represents the caption for the ok button.<br>
	  * FormDisplayBox.undo represents the caption for the ok button.<br>
	  * DetailFormDisplayBox.editQuestion represents the question asked when the user wants to edit a row and there is unsaved data on the current row.<br>
	  * DetailFormDisplayBox.cancelQuestion represents the question asked when the user wants to cancel changes in a row.<br>
	  * DetailFormDisplayBox.addQuestion represents the question asked when the user wants to add a row and there is unsaved data on the current row.<br>
	  * DetailFormDisplayBox.deleteQuestion represents the confirm delete question.
	  **/
	public void updateLocale() {
		super.updateLocale();
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
				_addButtonCaption=descr;

			descr = null;
			key = "FormDisplayBox.save";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_saveButtonCaption = descr;

			descr = null;
			key = "FormDisplayBox.cancel";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_cancelButtonCaption = descr;

			descr = null;
			key = "FormDisplayBox.delete";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_deleteButtonCaption = descr;

			descr = null;
			key = "DetailFormDisplayBox.addQuestion";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_okToAddQuestion = descr;

			descr = null;
			key = "DetailFormDisplayBox.editQuestion";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_okToEditQuestion = descr;

			descr = null;
			key = "DetailFormDisplayBox.cancelQuestion";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_okToCancelQuestion = descr;

			descr = null;
			key = "DetailFormDisplayBox.deleteQuestion";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_okToDeleteQuestion = descr;

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
			tryAdd();
		else if (e.getComponent() == _saveButton)
			doSave();
		else if (e.getComponent() == _cancelButton) {
			tryCancel();
		} else if (e.getComponent() == _deleteButton) {
			tryDelete();
		}
		return true;
	}

	/**
	 * This method gets fired when the user clicks the cancel button. Subclasses can override it to customize behavior
	 */
	public void doCancel() throws Exception {
		if (_mode == MODE_LIST_OFF_PAGE)
			returnToListPage(false);
		else if (getPageDataStoresStatus() == DataStoreBuffer.STATUS_NEW || getPageDataStoresStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED) {
			try {
				doDelete();
			} catch (Exception e) {
			}
			return;
		}
    undoChanges();
        scrollToMe();
	}

	/**
	 * This method gets fired when the user clicks the delete button. Subclasses can override it to customize behavior
	 */
	public void doDelete() throws Exception {
		_ds.deleteRow();
		doDataStoreUpdate();
		if (_mode == MODE_LIST_ON_PAGE) {
			if (_listForm != null && _listForm.getDataStore() != _ds) {
				//different datastores on list and detail
				DataStoreBuffer listDs = _listForm.getDataStore();
				if (_listSelectedRow != null) {
					for (int i = 0; i < listDs.getRowCount(); i++) {
						if (listDs.getDataStoreRow(i, DataStoreBuffer.BUFFER_STANDARD).getDSDataRow() == _listSelectedRow.getDSDataRow()) {
							listDs.removeRow(i);
							break;
						}
					}
				}
				if (listDs.getRowCount() == 0)
					setVisible(false);
				else {
					if (listDs.getRow() == -1)
						listDs.gotoRow(listDs.getRowCount() - 1);
					_listForm.setRowToEdit(listDs.getRow());
					doEdit();
				}
			} else {
				//list and detail share the same datastore
				if (_ds.getRowCount() == 0)
					setVisible(false);
				else
					scrollToMe();
			}
			syncListFormPage();
		} else {
			returnToListPage(true);
		}
	}

	/**
	 * This method gets fired when the user clicks the add button. Subclasses can override it to customize behavior
	 */
	public void doAdd() {
		if (_mode == MODE_LIST_ON_PAGE && _listForm != null) {
			DataStoreBuffer listDs = _listForm.getDataStore();
			DataStoreBuffer ds = getDataStore();
			if (listDs == ds && ds != null) {
				//same datastore on list and detail
				if (ds.getRowCount() > 0 && (ds.getRowStatus() == DataStoreBuffer.STATUS_NEW))
					return;

                if (ds.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED) ds.deleteRow();
                if (isDataModified()) undoChanges();

				ds.insertRow();
				if (_listForm.getDataTable() != null) {
					JspDataTable tab = _listForm.getDataTable();
					if (!tab.isRowOnPage(ds.getRowCount() - 1))
						tab.setPage(tab.getPage(ds.getRowCount() - 1));
				}
				HtmlFormComponent comp = findFirstFormComponent(this);
				if (comp != null)
					comp.setFocus();
				scrollToMe();

			} else if (_ds != null) {
				//different datastores on list and detail
				if (listDs != null)
					listDs.clearSelectedRow();
				_listSelectedRow = null;
				_ds.reset();
				_ds.insertRow();
				HtmlFormComponent comp = findFirstFormComponent(this);
				if (comp != null)
					comp.setFocus();
				scrollToMe();
			}
			setVisible(true);
		} else {
			_listSelectedRow = null;
			_ds.reset();
			_ds.insertRow();
			HtmlFormComponent comp = findFirstFormComponent(this);
			if (comp != null)
				comp.setFocus();
			scrollToMe();
		}
	}

	/**
	 * Tries to do an add, but firsts checks for unsaved data
	 */
	public void tryAdd() {
		if (isDataModified()) {
			scrollToMe();
            if (getValidator().getUseAlertsForErrors()) {
                addConfirmScript(_okToAddQuestion, _okToAddValue);
            }
            else {
                _validator.setErrorMessage(_okToAddQuestion, null, -1, _okToAdd);
            }
		}
        else {
    		doAdd();
        }
	}

    /**
	 * Tries to do an edit, but firsts checks for unsaved data
	 */
	public void tryEdit() throws SQLException, DataStoreException, Exception {
		if (isDataModified()) {
			scrollToMe();
            if (getValidator().getUseAlertsForErrors()) {
                addConfirmScript(_okToEditQuestion, _okToEditValue);
            }
            else {
                _validator.setErrorMessage(_okToEditQuestion, null, -1, _okToEdit);
            }
		}
        else {
			doEdit();
        }
	}

	/**
	 * Tries to do a cancel, but firsts checks for unsaved data
	 */
	public void tryCancel() throws Exception {
		if (! _displayOnly && isDataModified()) {
			scrollToMe();
            if (getValidator().getUseAlertsForErrors()) {
                addConfirmScript(_okToCancelQuestion, _okToCancelValue);
            }
            else {
                _validator.setErrorMessage(_okToCancelQuestion, null, -1, _okToCancel);
            }
		}
        else {
			doCancel();
        }
	}
	/**
	 * Tries to do an delete, but firsts checks with the user
	 */
	public void tryDelete() throws Exception {
		if (_confirmDelete && getPageDataStoresStatus() != DataStoreBuffer.STATUS_NEW) {
			scrollToMe();
            if (getValidator().getUseAlertsForErrors()) {
                addConfirmScript(_okToDeleteQuestion, _okToDeleteValue);
            }
            else {
                _validator.setErrorMessage(_okToDeleteQuestion, null, -1, _okToDelete);
            }
		}
        else {
			doDelete();
        }
	}

	/**
	 * This method gets fired when the user clicks the save button. Subclasses can override it to customize behavior
	 */
	public void doSave() throws Exception {
		if ((_listForm != null && _listForm.getDataStore() != _ds) || _mode == MODE_LIST_OFF_PAGE) {
			if (getPageDataStoresStatus() == DataStoreBuffer.STATUS_NEW) {
				doCancel();
				return;
			}
		}
		if (getDataStore() != null) {
			DataStoreBuffer dsb = getDataStore();
			try {
				doDataStoreUpdate();
			} catch (DirtyDataException ex) {
				if (_validator != null)
					_validator.addErrorMessage(_dirtyDataError);
			} catch (DataStoreException ex) {
				if (_validator != null)
					_validator.addErrorMessage(ex.getMessage(), ((JspController) getPage()).getBoundComponent(_ds, ex.getColumn()), ex.getRow());
			} catch (Exception ex) {
				if (_validator != null)
					_validator.addErrorMessage(ex.getMessage());
			}
		}

		if (_mode == MODE_LIST_ON_PAGE) {
			if (_reloadRowAfterSave) {
				if (_ds instanceof DataStore)
					((DataStore)_ds).reloadRow(_ds.getRow());
			}

			if (_listForm != null && _listForm.getDataStore() != _ds) {
				DataStoreRow source = _ds.getDataStoreRow(_ds.getRow(), DataStoreBuffer.BUFFER_STANDARD);
				if (_listSelectedRow != null)
					source.copyTo(_listSelectedRow);
				else {
					_listSelectedRow = _listForm.getDataStore().getDataStoreRow(_listForm.getDataStore().insertRow(), DataStoreBuffer.BUFFER_STANDARD);
					source.copyTo(_listSelectedRow);
				}
				DataStoreBuffer dsb = _listForm.getDataStore();
				if (dsb != null && dsb instanceof DataStore) {
					if (_reloadRowAfterSave ) {
						dsb.setRowStatus(DataStoreBuffer.STATUS_NOT_MODIFIED);
						((DataStore)dsb).reloadRow();
					}
				}
			}

			syncListFormPage();
		} else
			returnToListPage(true);
	}

	/**
	 * This method gets fired when the user clicks on a row in a list form. Subclasses can override it to customize behavior
	 */
	public void doEdit() throws SQLException, DataStoreException, Exception {
		if (_listForm != null) {
			if (_listForm.getDataStore() == _ds) {
				if (isDataModified()) {
                    if (getPageDataStoresStatus() == DataStoreBuffer.STATUS_MODIFIED)
                        undoChanges();
                    else if (getPageDataStoresStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED || getPageDataStoresStatus() == DataStoreBuffer.STATUS_NEW)
                        _ds.removeRow();
				}
				_ds.gotoRow(_listForm.getRowToEdit());
				setVisible(true);
				getPage().scrollToItem(getFullName() + "scrollToMe");
				HtmlFormComponent comp = findFirstFormComponent(this);
				if (comp != null)
					comp.setFocus();
			} else {
				DataStoreBuffer listDs = _listForm.getDataStore();
				int row = _listForm.getRowToEdit();
				if (listDs instanceof DataStore && _ds instanceof DataStore) {
					String table[] = _ds.getTableList(true);
					String where = null;
					for (int i = 0; i < table.length; i++) {
						String snippet = ((DataStore) listDs).buildCriteriaStringForRow(row, table[i]);
						if (snippet != null) {
							if (where == null)
								where = snippet;
							else
								where += " and " + snippet;
						}
					}
					doDataStoreRetrieve(where);
				} else if (listDs instanceof BeanDataStore) {
					String crit = ((BeanDataStore) listDs).buildCriteriaStringForRow(row);
					doDataStoreRetrieve(crit);
				}
				_ds.gotoFirst();
				listDs.gotoRow(row);
				_listSelectedRow = listDs.getDataStoreRow(row, DataStoreBuffer.BUFFER_STANDARD);
				getPage().scrollToItem(getFullName() + "scrollToMe");
				HtmlFormComponent comp = findFirstFormComponent(this);
				if (comp != null)
					comp.setFocus();

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
	* Set the access key for the delete button
	*/
	public void setDeleteButtonAccessKey(String key) {
		_deleteButton.setAccessKey(key);
	}
	/**
	* Set the access key for the cancel button
	*/
	public void setCancelButtonAccessKey(String key) {
		_deleteButton.setAccessKey(key);
	}

	/**
	 * Sets the theme for the component
	 */
	public void setTheme(String theme) {
		super.setTheme(theme);
		Props props = getPage().getPageProperties();
		_saveButtonCaption = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_SAVE_BUTTON_CAPTION);
		_saveButtonAccessKey = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_SAVE_BUTTON_ACCESS_KEY);
		_addButtonCaption = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION);
		_addButtonAccessKey = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY);
		_deleteButtonCaption = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_DELETE_BUTTON_CAPTION);
		_deleteButtonAccessKey = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_DELETE_BUTTON_ACCESS_KEY);
		_cancelButtonCaption = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION);
		_cancelButtonAccessKey = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_CANCEL_BUTTON_ACCESS_KEY);

		//agregado
		//Juan Manuel Cortez, 14/10/2007
		_okToCancelQuestion = props.getThemeProperty(theme, Props.FORM_DISPLAY_BOX_CANCEL_CONFIRMATION_MESSAGE);
		_okToAddQuestion = props.getThemeProperty(theme, Props.FORM_DISPLAY_BOX_ADD_CONFIRMATION_MESSAGE);
		_okToEditQuestion = props.getThemeProperty(theme, Props.FORM_DISPLAY_BOX_EDIT_CONFIRMATION_MESSAGE);
		_okToDeleteQuestion = props.getThemeProperty(theme, Props.FORM_DISPLAY_BOX_DELETE_CONFIRMATION_MESSAGE);
		_dirtyDataError = props.getThemeProperty(theme, Props.FORM_DISPLAY_BOX_DIRTY_DATA_ERROR_MESSAGE);

		String st = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_BUTTON_LOCATION);
		if (st != null) {
			if (st.equalsIgnoreCase("BELOW_TABLE"))
				setButtonDisplayLocation(BUTTON_DISPLAY_BELOW_TABLE);
			else if (st.equalsIgnoreCase("IN_HEADER"))
				setButtonDisplayLocation(BUTTON_DISPLAY_IN_HEADER);
		}
		st = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_BOTTOM_BUTTON_ALIGN);
		if (st !=null)
			setBottomButtonAlign(st);
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
		if (_cancelButton != null) {
			if (_cancelButtonCaption != null)
				_cancelButton.setDisplayName(_cancelButtonCaption);
			if (_cancelButtonAccessKey != null)
				_cancelButton.setAccessKey(_cancelButtonAccessKey);
		}
		if (_deleteButton != null) {
			if (_deleteButtonCaption != null)
				_deleteButton.setDisplayName(_deleteButtonCaption);
			if (_deleteButtonAccessKey != null)
				_deleteButton.setAccessKey(_deleteButtonAccessKey);
		}
		if (_okToAddButton != null && _okButtonCap != null)
			_okToAddButton.setDisplayName(_okButtonCap);
		if (_okToDeleteButton != null && _okButtonCap != null)
			_okToDeleteButton.setDisplayName(_okButtonCap);
		if (_okToCancelButton != null && _okButtonCap != null)
			_okToCancelButton.setDisplayName(_okButtonCap);
		if (_okToEditButton != null && _okButtonCap != null)
			_okToEditButton.setDisplayName(_okButtonCap);
		if (_cancelMessageButton != null && _cancelButtonCap != null)
			_cancelMessageButton.setDisplayName(_cancelButtonCap);
	}

	/**
	 * Returns true if the data in the detail has been modified
	 */
	public boolean isDataModified() {
		int stat = getPageDataStoresStatus();

		if (stat == DataStoreBuffer.STATUS_MODIFIED)
			return true;
		else if (stat == DataStoreBuffer.STATUS_NEW_MODIFIED)
			return true;
		else if (stat == DataStoreBuffer.STATUS_NEW)
			return true;
		else
			return false;
	}

	private void scrollToMe() {
		getPage().scrollToItem(getFullName() + "scrollToMe");
	}
	/**
	 * @return whether or not the form will confirm deletes
	 */
	public boolean getConfirmDelete() {
		return _confirmDelete;
	}

	/**
	 * sets whether or not the form will confirm deletes
	 */
	public void setConfirmDelete(boolean b) {
		_confirmDelete = b;
	}

	/**
	 * @return the question asked when tryAdd() is called with modified data in the form
	 */
	public String getOkToAddQuestion() {
		return _okToAddQuestion;
	}

	/**
	 * @return the question asked when tryCancel() is called with modified data in the form
	 */
	public String getOkToCancelQuestion() {
		return _okToCancelQuestion;
	}

	/**
	 * @return the question asked if confirmDelete is true and the user tries to delete a row
	 */
	public String getOkToDeleteQuestion() {
		return _okToDeleteQuestion;
	}

	/**
	 * @return the question asked when tryEdit() is called with modified data in the form
	 */
	public String getOkToEditQuestion() {
		return _okToEditQuestion;
	}

	/**
	 * set the question asked when tryAdd() is called with modified data in the form
	 **/
	public void setOkToAddQuestion(String string) {
		_okToAddQuestion = string;
	}

	/**
	 * set the question asked when tryCancel() is called with modified data in the form
	 **/
	public void setOkToCancelQuestion(String string) {
		_okToCancelQuestion = string;
	}

	/**
	 * sets the question asked if confirmDelete is true and the user tries to delete a row
	 */
	public void setOkToDeleteQuestion(String string) {
		_okToDeleteQuestion = string;
	}

	/**
	 * set the question asked when tryEdit() is called with modified data in the form
	 **/
	public void setOkToEditQuestion(String string) {
		_okToEditQuestion = string;
	}

	/**
	 * @return the operation mode of the component. Valid values are MODE_LIST_OFF_PAGE and MODE_LIST_ON_PAGE.
	 */
	public int getMode() {
		return _mode;
	}

	/**
	 * Sets the operational mode of the component. Valid values are MODE_LIST_OFF_PAGE and MODE_LIST_ON_PAGE.
	 */
	public void setMode(int i) {
		_mode = i;
	}

	private void syncListFormPage() {
		//check to see if the datatable now is beyond the last row. If so adjust the page
		if (_listForm != null && _listForm.getDataTable() != null) {
			DataStoreBuffer listFormDs = _listForm.getDataStore();
			JspDataTable listFormDataTable = _listForm.getDataTable();
			if (listFormDs != null && listFormDataTable != null) {
				if (listFormDs.getRow() != -1) {
					if (!listFormDataTable.isRowOnPage(listFormDs.getRow())) {
						listFormDataTable.setPage(listFormDataTable.getPage(listFormDs.getRow()));
					}
				}
			}
		}
	}
	public void returnToListPage(boolean refresh) {
		if (_mode == MODE_LIST_OFF_PAGE && _returnTo != null) {
			String ret = _returnTo;
			if (refresh)
				ret += "?refresh=true";
			try {
				getPage().getCurrentResponse().sendRedirect(ret);
			} catch (IOException e) {
				MessageLog.writeErrorMessage("returnToListPage", e, this);
			}
		}
	}

	/**
	 * Override this method in subclasses to change the way the datastore is updated
	 */
	public void doDataStoreUpdate() throws Exception {
		DataStoreBuffer ds = getDataStore();
		if (ds instanceof DataStore)
			((DataStore)ds).update();
		else if (ds instanceof BeanDataStore)
			((BeanDataStore)ds).update();
	}

	/**
	 * Override this method in subclasses to change the way the datastore is retrieved
	 */
	public void doDataStoreRetrieve(String where) throws Exception {
		if (where != null && where.length() == 0)
			where = null;
		DataStoreBuffer ds = getDataStore();
		if (ds instanceof DataStore)
			((DataStore)ds).retrieve(where);
		else if (ds instanceof BeanDataStore)
			((BeanDataStore)ds).retrieve(where);
	}
	/**
	 * @return the Add button used by this component
	 */
	public HtmlSubmitButton getAddButton() {
		return _addButton;
	}

	/**
	 * @return the Cancelbutton used by this component
	 */
	public HtmlSubmitButton getCancelButton() {
		return _cancelButton;
	}

	/**
	 * @return the Delete button used by this component
	 */
	public HtmlSubmitButton getDeleteButton() {
		return _deleteButton;
	}

	/**
	 * @return the Save button used by this component
	 */
	public HtmlSubmitButton getSaveButton() {
		return _saveButton;
	}

	/**
	 * @return true if the row will be reloaded from the database after a save
	 */
	public boolean getReloadRowAfterSave() {
		return _reloadRowAfterSave;
	}

	/**
	 * Set to true to have the row reloaded from the database after a save
	 */
	public void setReloadRowAfterSave(boolean b) {
		_reloadRowAfterSave = b;
	}

    /**
     * Adds a button to the display box at the specified position.
     */
    public void addButton(int i, HtmlSubmitButton b) {
        _buttons.add(i,b);
        add(b, TYPE_COMP);

    }

    /**
     * Restores the detail form to the state it was when it was first loaded.
     */
    public void undoChanges() {
        if (getPageDataStoresStatus() == DataStoreBuffer.STATUS_NOT_MODIFIED)
            return;
        else
            _ds.undoChanges(_ds.getRow());
    }

    /**
     * This method returns a status flag for the datastores related with this detail form.
     * Override if you have more than one datastore in your page (ie other datastores besides the one of the detail form).
     */
    public int getPageDataStoresStatus() {
        return _ds.getRowStatus();
    }

}
