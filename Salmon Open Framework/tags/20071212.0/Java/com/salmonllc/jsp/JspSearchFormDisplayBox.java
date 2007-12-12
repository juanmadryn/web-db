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

import java.sql.SQLException;
import java.util.Enumeration;

import com.salmonllc.html.*;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.QBEBuilder;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * An extended version of the display box with addional functionality for building search forms
 *
 */
public class JspSearchFormDisplayBox extends JspFormDisplayBox implements SubmitListener {

	private QBEBuilder _criteriaBuilder;

	private String _criteriaBuilderName, _criteriaValidatorName, _listFormName;
	private HtmlSubmitButton _searchButton, _addButton, _cancelLookupButton;
	private JspListFormDisplayBox _listForm;
	private String _searchButtonAccessKey, _addButtonAccessKey, _searchButtonCaption, _addButtonCaption, _cancelLookupAccessKey, _cancelLookupButtonCaption;
	private String _dataModifiedQuestion;// = "Data has been modified, and doing a new search will cause the changes to be lost. Continue? ";
	private HtmlContainer _okCancelButtons;
	private HtmlSubmitButton _okButton, _cancelButton;
	private boolean _cancelLookupButtonVisible = true;
    private HtmlHiddenField _dataModifiedValue;

	public JspSearchFormDisplayBox(String name, HtmlPage page) {
		this(name, null, page);
	}

    public void trySearch() {
        if (_listForm != null && _listForm.isDataModified())
            if (_validator.getUseAlertsForErrors()) {
                addConfirmScript(_dataModifiedQuestion, _dataModifiedValue);
            }
            else {
                _validator.setErrorMessage(_dataModifiedQuestion, null, -1, _okCancelButtons);
            }
        else
            try {
                doSearch();
            } catch (Exception e1) {
                _validator.setErrorMessage(e1.getMessage());
            }
    }

    private class SearchAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			try {
				doSearch();
			} catch (Exception e1) {
				_validator.setErrorMessage(e1.getMessage());
			}
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_dataModifiedValue.getValue() != null && _dataModifiedValue.getValue().equals("1")) {
                doSearch();
            }
            return true;
        }
    }

	public JspSearchFormDisplayBox(String name, String theme, HtmlPage page) {
		super(name, theme, page);

		_searchButton = new HtmlSubmitButton(name + "searchButton", "Search", theme, page);
		_addButton = new HtmlSubmitButton(name + "addButton", "Add", theme, page);
		_searchButton.addSubmitListener(this);
		_cancelLookupButton = new HtmlSubmitButton(name + "cancelButton", "Cancel", theme, page);
		_addButton.addSubmitListener(this);
		_cancelLookupButton.addSubmitListener(this);
		_buttons.add(_searchButton);
		_buttons.add(_addButton);
		_buttons.add(_cancelLookupButton);
		add(_searchButton, TYPE_COMP);
		add(_addButton, TYPE_COMP);
		add(_cancelLookupButton, TYPE_COMP);
		setUpButtons();

        SearchAction searchAction = new SearchAction();
		_okCancelButtons = new HtmlContainer("sfokcancelquestion", page);
		_okCancelButtons.add(_okButton = new MessageButton(getPage(), "dataModifiedOK", _okButtonCap, searchAction));
		_okCancelButtons.add(_cancelButton = new MessageButton(getPage(), "dataModifiedCancel", _cancelButtonCap, null));
		_messageButtons.add(_okButton);
		_messageButtons.add(_cancelButton);
		_cancelLookupButton.setVisible(false);

        _dataModifiedValue = new HtmlHiddenField("jsdatamodifiedvalue", "0", page);
        _dataModifiedValue.addValueChangedListener(searchAction);
        add(_dataModifiedValue, TYPE_COMP);
        _hidden.add(_dataModifiedValue);
	}

	/**
	 *Called by the tag handler to set the name of the QBEBuilder. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
	 */
	public void setQBEBuilderName(String name) {
		_criteriaBuilderName = name;
	}
	/**
	 *Called by the tag handler to set the name of the QBEValidator. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
	 */
	public void setQBEValidatorName(String name) {
		_criteriaValidatorName = name;
	}

	/**
	 *Called by the tag handler to set the name of the ListForm. The autoBindComponents will lookup the name of the component and attach it to this one or try to discover it if the name is null
	 */
	public void setListFormName(String name) {
		_listFormName = name;
	}
	/**
	 * Binds various components to the component based on their names passed to the constructor. CriteriaBuilder, CriteriaValidator and ListForm. This method is called by the framework and should not be called directly.
	 */
	public void autoBindComponents() {
		JspController cont = (JspController) getPage();

		if (_criteriaBuilderName != null)
			_criteriaBuilder = (QBEBuilder) cont.getDataSource(_criteriaBuilderName);
		else {
			//find the first QBEBuilder in the page
			Enumeration enumerar = cont.getDataSources();
			while (enumerar.hasMoreElements()) {
				Object o = enumerar.nextElement();
				if (o instanceof QBEBuilder) {
					_criteriaBuilder = (QBEBuilder) o;
					break;
				}
			}
		}
		if (_criteriaValidatorName != null)
			_validator = (HtmlValidatorText) cont.getComponent(_criteriaValidatorName);
		else {
			//see if there is a validator for this qbebuilder
			Enumeration enumerar = cont.getComponents();
			while (enumerar.hasMoreElements()) {
				Object o = enumerar.nextElement();
				if (o instanceof HtmlValidatorText) {
					HtmlValidatorText test = (HtmlValidatorText) o;
					if (test.getDataStore() == _criteriaBuilder) {
						_validator = test;
						break;
					}
				}
			}
		}
		if (_validator == null && _criteriaBuilder != null) {
			boolean hasRules = false;
			for (int i = 0; i < _criteriaBuilder.getColumnCount(); i++) {
				if (_criteriaBuilder.getValidationRulesForColumn(i) != null) {
					hasRules = true;
					break;
				}
			}
			if (hasRules) {
				_validator = new HtmlValidatorText(getName() + "validator", cont);
				_validator.setBreaksBefore(0);
				_validator.setBreaksAfter(2);
				_validator.setDataStore(_criteriaBuilder);
				_validator.importRules(_criteriaBuilder);
				_validatorBuiltInternally = true;
				add(_validator, TYPE_COMP);
			}
		}

		if (_validator != null)
			_validator.addSubmitToListenTo(_searchButton);
		else {
			_validator = new HtmlValidatorText(getName() + "validator", cont);
			_validator.setBreaksBefore(0);
			_validator.setBreaksAfter(2);
			_validatorBuiltInternally = true;
			add(_validator, TYPE_COMP);
		}

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
	}
	/**
	 * Sets the search button visible or not
	 */
	public void setSearchButtonVisible(boolean visible) {
		_searchButton.setVisible(visible);
	}
	/**
	 * Sets the text to display on the search button
	 */
	public void setSearchButtonCaption(String caption) {
		_searchButton.setDisplayName(caption);
      _searchButtonCaption = caption;
	}

	/**
	 * Sets the add button visible or not
	 */
	public void setAddButtonVisible(boolean visible) {
		_addButton.setVisible(visible);
	}

	/**
	 * Sets the add button visible or not
	 */
	public void setAddButtonCaption(String caption) {
		_addButton.setDisplayName(caption);
      _addButtonCaption = caption;
	}

	/**
	 * Returns the search button visible or not
	 */
	public boolean isSearchButtonVisible() {
		return _searchButton.getVisible();
	}
	/**
	 * Returns the text to display on the search button
	 */
	public String getSearchButtonCaption() {
		return _searchButton.getDisplayName();
	}

	/**
	 * Return the add button visible or not
	 */
	public boolean isAddButtonVisible() {
		return _addButton.getVisible();
	}

	/**
	 * Returns the add button visible or not
	 */
	public String getAddButtonCaption() {
		return _addButton.getDisplayName();
	}

	/**
	 * Returns the font style for the search and add buttons
	 */
	public String getButtonFontStyle() {
		return _searchButton.getButtonFontStyle();
	}
	/**
	 * Returns the background color for the search and add buttons
	 */
	public String getButtonBgColor() {
		return _searchButton.getButtonBgColor();
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
	  * FormDisplayBox.search represents the caption for the search button.<br>
	  * FormDisplayBox.cancel represents the caption for the cancel button.<br>
	  * FormDisplayBox.ok represents the caption for the ok button.<br>
	  * FormDisplayBox.undo represents the caption for the ok button.<br>
	  * SearchFormDisplayBox.dataModifiedQuestion represents the question asked if a search will overwrite unsaved data<br>
	  */
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
				_addButtonCaption = descr;

			descr = null;
			key = "FormDisplayBox.search";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_searchButtonCaption = descr;

			descr = null;
			key = "FormDisplayBox.cancel";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_cancelLookupButtonCaption = descr;

			descr = null;
			key = "SearchFormDisplayBox.dataModifiedQuestion";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_dataModifiedQuestion = descr;

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
		else if (e.getComponent() == _searchButton) {
            trySearch();
		} else if (e.getComponent() == _cancelLookupButton)
			if (isFromPopupLookup()) {
				HtmlScriptGenerator gen=new HtmlScriptGenerator(getPage());
				getPage().writeScript(gen.generateCancelLookupScript());
			}
			else
				getPage().getCurrentResponse().sendRedirect(getLookupReturnToURL(null,null));
		return true;
	}

	/**
	 * This method gets fired when the user clicks the add button. Subclasses can override it to customize behavior
	 */
	public void doAdd() throws DataStoreException, SQLException, Exception {
		if (_listForm != null)
			_listForm.doAdd();
	}

	/**
	 * This method gets fired when the user clicks the search button. Subclasses can override it to customize behavior
	 */
	public void doSearch() throws SQLException, DataStoreException, Exception {
		if (_listForm != null) {
			_listForm.doSearch();
		}
	}

	/**
	 * @return the criteria builder the component is using to build the search filter
	 */
	public QBEBuilder getCriteriaBuilder() {
		return _criteriaBuilder;
	}

	/**
	 * @return the criteria validator the component is using to validate entered criteria
	 */
	public HtmlValidatorText getCriteriaValidator() {
		return _validator;
	}

	/**
	 * Sets the access key for the search button
	 */
	public void setSearchButtonAccessKey(String key) {
		_searchButton.setAccessKey(key);
	}

	/**
	 * Sets the access key for the add button
	 */
	public void setAddButtonAccessKey(String key) {
		_addButton.setAccessKey(key);
	}

	/**
	 * Sets the theme for the component
	 */
	public void setTheme(String theme) {
		super.setTheme(theme);
		Props props = getPage().getPageProperties();
		_searchButtonCaption = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_SEARCH_BUTTON_CAPTION);
		_searchButtonAccessKey = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_SEARCH_BUTTON_ACCESS_KEY);
		_addButtonCaption = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION);
		_addButtonAccessKey = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY);
		_cancelLookupButtonCaption = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION);
		_cancelLookupAccessKey = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_CANCEL_BUTTON_ACCESS_KEY);

		//Juan Manuel Cortez, 14/10/2007
		_dataModifiedQuestion = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_DATA_MODIFIED_QUESTION);

		String st = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_BUTTON_LOCATION);
		if (st != null) {
			if (st.equalsIgnoreCase("BELOW_TABLE"))
				setButtonDisplayLocation(BUTTON_DISPLAY_BELOW_TABLE);
			else if (st.equalsIgnoreCase("IN_HEADER"))
				setButtonDisplayLocation(BUTTON_DISPLAY_IN_HEADER);
			else if (st.equalsIgnoreCase("IN_HEADER_AND_BELOW_TABLE"))
				setButtonDisplayLocation(BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE);
		}
		st = props.getThemeProperty(theme, Props.SEARCH_FORM_DISPLAY_BOX_BOTTOM_BUTTON_ALIGN);
		if (st != null)
			setBottomButtonAlign(st);
		setUpButtons();
	}

	protected void setUpButtons() {
		super.setUpButtons();
		if (_searchButton == null)
			return;
		if (_searchButtonCaption != null)
			_searchButton.setDisplayName(_searchButtonCaption);
		if (_searchButtonAccessKey != null)
			_searchButton.setAccessKey(_searchButtonAccessKey);
		if (_addButtonCaption != null)
			_addButton.setDisplayName(_addButtonCaption);
		if (_addButtonAccessKey != null)
			_addButton.setAccessKey(_addButtonAccessKey);
		if (_cancelLookupButtonCaption != null)
			_cancelLookupButton.setDisplayName(_cancelLookupButtonCaption);
		if (_cancelLookupAccessKey != null)
			_cancelLookupButton.setAccessKey(_cancelLookupAccessKey);

		if (_okButton != null)
			_okButton.setDisplayName(_okButtonCap);
		if (_cancelButton != null)
			_cancelButton.setDisplayName(_cancelButtonCap);
	}

	/**
	 * @return the question when asking whether to cancel an operation because the modified data will be overwritten
	 */
	public String getDataModifiedQuestion() {
		return _dataModifiedQuestion;
	}

	/**
	* sets the question when asking whether to cancel an operation because the modified data will be overwritten
	*/
	public void setDataModifiedQuestion(String string) {
		_dataModifiedQuestion = string;
	}

	/**
	* Set the access key for the lookup cancel button
	*/
	public void setCancelButtonAccessKey(String key) {
		_cancelLookupButton.setAccessKey(key);
	}

	/**
	 * Sets the cancel lookup button visible or not
	 */
	public void setCancelButtonVisible(boolean visible) {
		_cancelLookupButtonVisible = visible;
	}

	/**
	 * Sets the cancel lookup button visible or not
	 */
	public void setCancelButtonCaption(String caption) {
		if (_cancelLookupButton != null){
			_cancelLookupButton.setDisplayName(caption);
         _cancelButtonCap = caption;
      }
	}

	/**
	 * Returns the cancel lookup button caption
	 */
	public String getCancelLookupButtonCaption() {
		if (_cancelLookupButton == null)
			return null;
		else
			return _cancelLookupButton.getDisplayName();
	}

	/**
	 * Return the cancel lookup button visible or not
	 */
	public boolean isCancelButtonVisible() {
		if (_cancelLookupButton == null)
			return false;
		else
			return _cancelLookupButton.getVisible();
	}

	public void generateHTML(TagWriter t, String boxBody) throws Exception {
		processLocaleInfo();
		boolean oldVis[] = null;
		if (isFromLookup() || isFromPopupLookup()) {
			oldVis = getButtonsVisible();
			_cancelLookupButton.setVisible(_cancelLookupButtonVisible);
			_addButton.setVisible(false);
		}

		super.generateHTML(t, boxBody);

		if (oldVis != null)
			setButtonVisible(oldVis);
	}

	/**
	 * @return the DataStoreBuffer this component is using
	 */
	public DataStoreBuffer getDataStoreBuffer() {
		return _listForm.getDataStore();
	}

	/**
	 * @return the add button the component is using
	 */
	public HtmlSubmitButton getAddButton() {
		return _addButton;
	}

	/**
	 * @return the search button the component is using
	 */
	public HtmlSubmitButton getSearchButton() {
		return _searchButton;
	}
}
