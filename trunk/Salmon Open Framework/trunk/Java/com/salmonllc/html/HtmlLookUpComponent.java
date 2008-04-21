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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlLookUpComponent.java $
//$Author: Dan $
//$Revision: 39 $
//$Modtime: 11/01/04 2:46p $
/////////////////////////

import com.salmonllc.html.events.*;
import com.salmonllc.jsp.Constants;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.jsp.JspList;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.util.MessageLog;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a lookup component. It has an edit field and a zoom component to zoom to a list of possible values.
 */
public class HtmlLookUpComponent extends HtmlComposite implements PageListener, SubmitListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 657603079345128064L;

	protected DataStoreBuffer _ds = null;

	private DataStoreBuffer _descDs;

	/** reference to the edit box in the lookup comp */
	private Object _editHandle;

	private HtmlSubmitImage _browseImage;

	private HtmlLink _browsePopupImageLink;

	private HtmlImage _browsePopupImage;

	private Object _browsePopupImageHandle;

	private int _popupWidth = 300;

	private int _popupHeight = 300;

	private String _font;

	private String _fontStartTag;

	private String _fontEndTag;

	private boolean _showDescription = true;

	private boolean _editDescription = false;

	private DataStoreEvaluator _descriptionEval;

	private int _descriptionColumn = -1;

	private String _descriptionDataSource;

	/** reference to the browse image lookup comp */
	private Object _browseImageHandle;

	protected String _lookUpPageURL;

	/** reference to the hidden description lookup comp */
	private HtmlHiddenField _hiddenDescriptionHandle;

	/**
	 * reference to a hidden field containing the key, used if _editDescription
	 * is true
	 */
	private HtmlHiddenField _hiddenKeyHandle;
	protected int _rowNo = -1;

	protected String _extraParms;

	private boolean _usePopup = false;
	private boolean _useDiv=false;
	private String _divBorderStyle=null;
	private boolean _divScrolling=false;

	/* Claudio Pi - 5/25/04 Added for modal popup windows */
	private boolean _useModal = false;

	/* Claudio Pi - 6/28/04 Added popup position */
	private int _popupLeft = 0;

	private int _popupTop = 0;

	private String _popupPosition = Constants.POPUP_POSITION_CUSTOM;

	public static final String PARAM_LISTFORM_SEARCH_FILTER_STRING = "ListFormSearchFilterString";

	public static final String PARAM_LOOKUP_COMPONENT = "LookupComponentParam";

	public static final String PARAM_LOOKUP_CONTROLLER = "LookupComponentControllerParam";

	public static final String PARAM_LOOKUP_ROW = "LookupComponentRowParam";

	public static final String PARAM_LOOKUP_VALUE = "LookupComponentRowValue";

	private String _popupAttributes;
	
	private Vector<FormComponentInfo> _popupURLLineValues;
	private class FormComponentInfo {
		String compName;
		String attName;
		boolean isInDataTable;
	}	


	/**
	 * LookUp constructor.
	 * 
	 * @param name -
	 *            name of component
	 * @param p -
	 *            page the component will be associated with
	 */
	public HtmlLookUpComponent(String name, HtmlPage p) {
		this(name, null, p);

	}

	/**
	 * LookUp constructor.
	 * 
	 * @param name -
	 *            name of component
	 * @param lookup_page -
	 *            the page that will provide the lookup values
	 * @param p -
	 *            page the component will be associated with
	 */
	public HtmlLookUpComponent(String name, String lookup_page, HtmlPage p) {
		this(name, lookup_page, "../../Objectstore/Images/Browse.GIF", p);
	}

	/**
	 * LookUp constructor.
	 * 
	 * @param name -
	 *            name of component
	 * @param lookup_page -
	 *            the page that will provide the lookup values
	 * @param ds -
	 *            DataStore that is being used with this component
	 * @param tableName -
	 *            table name to bind to
	 * @param columnName -
	 *            column name to bind to
	 * @param dataType -
	 *            data type of the main edit field ( use DataStore.DATATYPE_*)
	 * @param pKey -
	 *            flag that signifies if the column is a primary key column
	 * @param p -
	 *            page the component will be associated with
	 */
	public HtmlLookUpComponent(String name, String lookup_page, DataStore ds, String tableName, String columnName, int dataType, boolean pKey, HtmlPage p) { //	 HtmlLookUpComponent(String
		// name,
		// String
		// lookup_page, String browseImage, DataStore ds,
		// String tableName, String columnName, int dataType,
		// boolean pKey, HtmlPage p)
		this(name, lookup_page, "../../Objectstore/Images/Browse.GIF", ds, tableName, columnName, dataType, pKey, p);
	}

	/**
	 * LookUp constructor.
	 * 
	 * @param name -
	 *            name of component
	 * @param lookup_page -
	 *            the page that will provide the lookup values
	 * @param browseImage -
	 *            the browse image to use. default is a magnifying glass
	 * @param p -
	 *            page the component will be associated with
	 */
	public HtmlLookUpComponent(String name, String lookup_page, String browseImage, HtmlPage p) {
		//	 HtmlLookUpComponent(String name, String lookup_page, String
		// browseImage, DataStore ds, String tableName, String columnName, int
		// dataType, boolean pKey, HtmlPage p)
		this(name, lookup_page, browseImage, null, null, null, DataStore.DATATYPE_INT, false, p);
	}

	/**
	 * LookUp constructor.
	 * 
	 * @param name -
	 *            name of component
	 * @param lookup_page -
	 *            the page that will provide the lookup values
	 * @param browseImage -
	 *            the browse image to use. default is a magnifying glass
	 * @param ds -
	 *            DataStore that is being used with this component
	 * @param tableName -
	 *            table name to bind to
	 * @param columnName -
	 *            column name to bind to
	 * @param dataType -
	 *            data type of the main edit field ( use DataStore.DATATYPE_*)
	 * @param pKey -
	 *            flag that signifies if the column is a primary key column
	 * @param p -
	 *            page the component will be associated with
	 */
	public HtmlLookUpComponent(String name, String lookup_page, String browseImage, DataStore ds, String tableName, String columnName, int dataType, boolean pKey, HtmlPage p) {
		super(name, p);

		_hiddenDescriptionHandle = new HtmlHiddenField("hiddenDescr", null, p);
		addCompositeComponent(_hiddenDescriptionHandle, DataStore.DATATYPE_STRING, false, null, null);
		_hiddenKeyHandle = new HtmlHiddenField("hiddenKey", null, p);
		addCompositeComponent(_hiddenKeyHandle, DataStore.DATATYPE_STRING, false, null, null);
		_hiddenKeyHandle.setVisible(false);

		setTheme(null);
		p.addPageListener(this);

		HtmlTextEdit edit = null;

		if (ds == null) {
			edit = new HtmlTextEdit("_edit", p);
			_editHandle = addCompositeComponent(edit, dataType, false, null, null);
		} else {
			_ds = ds;
			/** Don't add the same column twice to the data store. */
			if (_ds.getColumnIndex(tableName + "." + columnName) == -1) {
				if (_ds instanceof DataStore)
					((DataStore) _ds).addColumn(tableName, columnName, dataType, pKey, true);
			}
			edit = new HtmlTextEdit(tableName + "_" + columnName, p);
			edit.setColumn(_ds, tableName + "." + columnName);

			_editHandle = addCompositeComponent(edit, dataType, true, tableName, columnName);
		}

		_browseImage = new HtmlSubmitImage("browseImage", browseImage, p);
		_browseImage.addSubmitListener(this);
		_browseImageHandle = addCompositeComponent(_browseImage, DATATYPE_ANY, false, null, null);

		_browsePopupImage = new HtmlImage("browsPopupImage", browseImage, p);
		_browsePopupImageLink = new HtmlLink("browsePopupImageLink", null, p);
		_browsePopupImageLink.add(_browsePopupImage);
		_browseImageHandle = addCompositeComponent(_browsePopupImageLink, DATATYPE_ANY, false, null, null);
		_browsePopupImageLink.setVisible(false);

		setLookUpPageURL(lookup_page);
		setDescriptionFont(null);
	}

	/**
	 * Do not use this method in this class.
	 */
	public void add(HtmlComponent comp) {
		try {
			throw new Exception(" DO NOT USE: HtmlLookUpComponent.add. Use addLookupComp instead");
		} catch (Exception e) {
			MessageLog.writeErrorMessage(" DO NOT USE: HtmlLookUpComponent.add. Use addLookupComp instead", e, this);
		}
	}

	/**
	 * This method returns the browse image
	 * 
	 * @return HtmlTextEdit
	 */
	public HtmlSubmitImage getBrowseImage() {
		try {

			/** get the index of the browse image so we can replace it */
			int browseIndex = _componentsVec.indexOf(_browseImageHandle);
			if (browseIndex != -1) {
				return (HtmlSubmitImage) _componentsVec.elementAt(browseIndex);
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("getBrowseImage", e, this);
		}
		return null;
	}

	/**
	 * This method returns the edit filed
	 * 
	 * @return HtmlTextEdit
	 */
	public HtmlTextEdit getEditField() {
		try {

			/** get the index of the edit field so we can replace it */
			int editIndex = _componentsVec.indexOf(_editHandle);
			if (editIndex != -1) {
				return (HtmlTextEdit) _componentsVec.elementAt(editIndex);
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("getEditField", e, this);
		}
		return null;
	}

	/**
	 * This method returns the hidden description filed
	 * 
	 * @return HtmlTextEdit
	 */
	public HtmlHiddenField getHiddenDescrField() {
		try {

			/** get the index of the edit field so we can replace it */
			int editIndex = _componentsVec.indexOf(_hiddenDescriptionHandle);
			if (editIndex != -1) {
				return (HtmlHiddenField) _componentsVec.elementAt(editIndex);
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("getHiddenDescrField", e, this);
		}
		return null;
	}

	/**
	 * This method retuns the lookUp URL
	 * 
	 * @return String
	 */
	public String getLookUpPageURL() {
		return _lookUpPageURL;
	}

	/**
	 * This method returns the value of the text in the component.
	 * 
	 * @return String
	 */
	public String getValue() {
		return getEditField().getValue();
	}

	/**
	 * This method returns the description of the text in the component.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return getHiddenDescrField().getValue();
	}

	public void pageRequested(PageEvent p) throws Exception {
		HttpServletRequest req = p.getPage().getCurrentRequest();
		String returnVal[] = req.getParameterValues(getFullName());
		if (returnVal != null) {
			if (_editDescription)
				_hiddenKeyHandle.setValue(returnVal[0], _rowNo);
			else
				getEditField().setValue(returnVal[0], _rowNo);
		}
		HtmlPage pg = getPage();
		if (pg instanceof JspController)
			((JspController) pg).setRemoveFromQueryString(getFullName());

		if (returnVal != null) {
			returnVal = req.getParameterValues("descReturn");
			if (returnVal != null) {
				if (_editDescription)
					getEditField().setValue(returnVal[0], _rowNo);
				if (_descDs != null && _descriptionColumn != -1) {
					if (_descDs.getColumnDataType(_descriptionColumn) == DataStoreBuffer.DATATYPE_STRING) {
						if (_rowNo == -1)
							_rowNo = _descDs.getRow();
						_descDs.setString(_rowNo, _descriptionColumn, returnVal[0]);
					}
				}
				if (pg instanceof JspController)
					((JspController) pg).setRemoveFromQueryString("descReturn");
			}
		}

	}

	public void pageRequestEnd(PageEvent p) throws Exception {
	}

	public void pageSubmitEnd(PageEvent p) {
	}

	public void pageSubmitted(PageEvent p) {
	}

	/**
	 * Replaces the default browse image with one you specify
	 * 
	 * @param comp -
	 *            component that is replacing the browse image
	 * @param dataType -
	 *            data type of the component being replaced ( use
	 *            DataStore.DATATYPE_*)
	 * @param bound -
	 *            flag that signifies if the component is bound to a datastore
	 *            column
	 * @param table -
	 *            table component is bound to
	 * @param column -
	 *            column component is bound to
	 */
	public void replaceBrowseImage(HtmlComponent comp, int dataType, boolean bound, String table, String column) {
		try {

			/** get the index of the edit field so we can replace it */
			if (_usePopup) {
				int browseIndex = _componentsVec.indexOf(_browsePopupImageHandle);
				if (browseIndex != -1) {
					replaceCompositeComponent(comp, _browsePopupImageHandle, dataType, bound, table, column);
					_componentsVec.setElementAt(comp, browseIndex);
					comp.setParent(this);
					_browsePopupImageHandle = comp;
				}
			} else {
				int browseIndex = _componentsVec.indexOf(_browseImageHandle);
				if (browseIndex != -1) {
					replaceCompositeComponent(comp, _browseImageHandle, dataType, bound, table, column);
					_componentsVec.setElementAt(comp, browseIndex);
					comp.setParent(this);
					_browseImageHandle = comp;
				}

			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("replaceBrowseImage", e, this);
		}
	}

	/**
	 * Replaces the default edit field with one you specify
	 * 
	 * @param comp -
	 *            component that is replacing the edit field
	 * @param dataType -
	 *            data type of the component being replaced ( use
	 *            DataStore.DATATYPE_*)
	 * @param bound -
	 *            flag that signifies if the component is bound to a datastore
	 *            column
	 * @param table -
	 *            table component is bound to
	 * @param column -
	 *            column component is bound to
	 */
	public void replaceEdit(HtmlComponent comp, int dataType, boolean bound, String table, String column) {
		try {

			/** get the index of the edit field so we can replace it */
			int editIndex = _componentsVec.indexOf(_editHandle);
			if (editIndex != -1) {
				replaceCompositeComponent(comp, _editHandle, dataType, bound, table, column);
				comp.setParent(this);
				_editHandle = comp;
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("replaceEdit", e, this);
		}
	}

	/**
	 * Sets the column in the DataStore that this field binds to
	 */

	public void setColumn(DataStoreBuffer ds, int columnNo) {
		if (!_editDescription)
			getEditField().setColumn(ds, columnNo);
		else
			_hiddenKeyHandle.setColumn(ds, columnNo);
		_ds = ds;
	}

	/**
	 * Sets the column in the DataStore that this field binds to
	 */

	public void setColumn(DataStoreBuffer ds, String columnName) {
		if (!_editDescription)
			getEditField().setColumn(ds, columnName);
		else
			_hiddenKeyHandle.setColumn(ds, columnName);
		_ds = ds;
	}

	/**
	 * Sets the flag for ability to respond to user input (true = does respond).
	 * 
	 * @param enabled
	 *            boolean
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (_editHandle instanceof HtmlFormComponent)
			((HtmlFormComponent)_editHandle).setEnabled(enabled);
		if (_usePopup) 
			_browsePopupImageLink.setVisible(enabled);
		else	
			_browseImage.setVisible(enabled);
	}

	/**
	 * This method will set the focus to the edit component.
	 */
	public void setFocus() {
		if (_editHandle instanceof HtmlFormComponent)
			((HtmlFormComponent)_editHandle).setFocus();
	}
	
	/**
	 * This method will set the focus to the edit component.
	 */
	public void setFocus(int row) {
		if (_editHandle instanceof HtmlFormComponent)
			((HtmlFormComponent)_editHandle).setFocus(row);
	}
	
	/**
	 * This method will set the focus to the edit component.
	 */
	public void setFocus(int row, boolean select) {
		if (_editHandle instanceof HtmlFormComponent)
			((HtmlFormComponent)_editHandle).setFocus(row, select);
	}
	
	/**
	 * This method will append the extra parms string to the url when the user
	 * clicks the submit image.
	 */
	public void setExtraParms(String extraParms) {
		_extraParms = extraParms;
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @param newValue
	 *            String
	 */
	public void setLookUpPageURL(String newValue) {
		_lookUpPageURL = translateSiteMapURL(newValue);
	}

	/**
	 * Sets the value in the edit field.
	 */
	public void setValue(String newValue) {
		getEditField().setValue(newValue);
	}

	public boolean submitPerformed(SubmitEvent e) throws Exception {
		/** redirect to lookupPageURL?returnto=parentPage&comp=nameofComponent */
		/** open new page */
		_rowNo = e.getRow();
		String rowURL = "";
		if (_rowNo != -1)
			rowURL = "&rowNo=" + _rowNo;

		HttpServletResponse resp = e.getPage().getCurrentResponse();
		String URL = _lookUpPageURL;
		if (_lookUpPageURL != null && _lookUpPageURL.indexOf("?") == -1)
			URL += "?returnTo=" + e.getPage().getPageName() + "&comp=" + getFullName() + rowURL;
		else
			URL += "&returnTo=" + e.getPage().getPageName() + "&comp=" + getFullName() + rowURL;

		String sCurrentValue = null;

		//Try to get the text that is already entered to the text edit box. We
		// will use this value in the list form to filter the results.
		if (_rowNo != -1)
			sCurrentValue = getEditField().getValue(_rowNo);
		else
			sCurrentValue = getEditField().getValue();

		if (sCurrentValue != null && sCurrentValue.length() > 0)
			URL += "&" + PARAM_LISTFORM_SEARCH_FILTER_STRING + "=" + sCurrentValue;

		if (_extraParms != null)
			URL += "&" + _extraParms;
		resp.sendRedirect(URL);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlComponent#generateHTML(java.io.PrintWriter,
	 *      int, int)
	 */
	public void generateHTML(PrintWriter p, int rowStart, int rowEnd) throws Exception {
		generatePopupHtml(rowStart);
		boolean editReadOnly = setEditReadOnly(rowStart);
		super.generateHTML(p, rowStart, rowEnd);
		getEditField().setReadOnly(editReadOnly);
		generateDivHtml(p, rowStart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlComponent#generateHTML(java.io.PrintWriter,
	 *      int)
	 */
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		generatePopupHtml(rowNo);
		boolean editReadOnly = setEditReadOnly(rowNo);
		super.generateHTML(p, rowNo);
		getEditField().setReadOnly(editReadOnly);
		generateDivHtml(p, rowNo);

	}

	private boolean setEditReadOnly(int rowNo) {
		boolean ret = getEditField().getReadOnly();
		if (_editDescription) 
			getEditField().setOnChange(getFormString() + _hiddenKeyHandle.getFullName() + (rowNo != -1 ? "_" + rowNo : "") + ".value=''");
		return ret;
	}
	
	private void generateDivHtml(PrintWriter p, int rowNo) throws DataStoreException {
		if (!_showDescription)
			return;
		if (_editDescription)
			return;
		p.print(_fontStartTag);
		p.print("<span id=\"div");
		p.print(getFullName());
		if (rowNo != -1)
			p.print("_" + rowNo);
		p.print("\">");
		if (_descriptionEval != null) {
			if (rowNo == -1)
				rowNo = _descriptionEval.getDataStore().getRow();
			Object o = _descriptionEval.evaluateRow(rowNo);
			if (o != null) {
				p.print(o);
				_hiddenDescriptionHandle.setValue(o.toString());
			} else {
				_hiddenDescriptionHandle.setValue(null);
			}
		} else {
			//Take the description from the hidden field value
			p.print(_hiddenDescriptionHandle.getValue() == null ? "" : _hiddenDescriptionHandle.getValue());
		}
		p.print("</span>");
		p.print(_fontEndTag);
	}
	
	private String generatePopupHtml(int row) {
		if (_showDescription && _editHandle instanceof HtmlTextEdit) {
			String rowNo = row == -1 ? "" : "_" + row;
			((HtmlTextEdit) _editHandle).setOnChange("var theSpan=document.getElementById(\'div" + getFullName() + rowNo + "'); if (theSpan) theSpan.innerHTML=''; " + getHiddenDescrFieldFullName(row) + ".value='';");
		}

		if (_usePopup) {
			if (_visible && _enabled) {
				StringBuffer jsPopupVars = new StringBuffer();

				if (_editHandle != null) {
					jsPopupVars.append("obj=");
					jsPopupVars.append(this.getFormString());
					jsPopupVars.append(((HtmlComponent) _editHandle).getFullName());
					jsPopupVars.append(((row == -1) ? "" : ("_" + row)));
					jsPopupVars.append(";");
				}

				String urlParms = PARAM_LOOKUP_CONTROLLER + "=$jsp$" + ((JspController) this.getPage()).getSessionKey() + "&" + PARAM_LOOKUP_COMPONENT + "=" + this.getName() + "&" + PARAM_LOOKUP_ROW + "=" + row;

				jsPopupVars.append("url='");
				String lookupPageURL=getLookUpPageURL();
				jsPopupVars.append(lookupPageURL);
				if (lookupPageURL != null && lookupPageURL.indexOf("?") == -1)
					jsPopupVars.append("?");
				else
					jsPopupVars.append("&");
				jsPopupVars.append(urlParms);
				jsPopupVars.append("&" + PARAM_LOOKUP_VALUE + "='+escape(obj.value)");
				//pass any values from other components on the page if necessary
				if (_popupURLLineValues != null) {
					for (int i=0; i< _popupURLLineValues.size(); i++) {
						FormComponentInfo inf = (FormComponentInfo) _popupURLLineValues.elementAt(i);
						jsPopupVars.append("+'&");
						jsPopupVars.append(inf.attName);
						jsPopupVars.append("='+escape(");
						jsPopupVars.append(inf.compName);
						if (inf.isInDataTable)
							jsPopupVars.append("_" + row);
						jsPopupVars.append(".value)");
					}	
				}	
				//add extra parms to the component
                if (_extraParms!=null) jsPopupVars.append("+" + _extraParms );
				jsPopupVars.append(";");
				HtmlScriptGenerator gen=new HtmlScriptGenerator((JspController) getPage());
                if (_useDiv)
					jsPopupVars.append(gen.generatePopupDivScript("url", getPopupPosition(), getPopupTop(), getPopupLeft(), getPopupWidth(), getPopupHeight(), _useModal, _divBorderStyle, (_divScrolling ? "auto" : "no"), (HtmlComponent) _editHandle, row));
				else
					jsPopupVars.append(gen.generateOpenPopupScript("url", getPopupPosition(), getPopupTop(), getPopupLeft(), getPopupWidth(), getPopupHeight(), _useModal, _popupAttributes, (HtmlComponent) _editHandle, row));
				
				_browsePopupImageLink.setHref("javascript:" + jsPopupVars);
				return jsPopupVars.toString();
			}

		}
		return null;
	}
	/**
	 * @return
	 */
	public boolean getUsePopup() {
		return _usePopup;
	}

	/**
	 * @param b
	 */
	public void setUsePopup(boolean b, boolean useModal) {
		_usePopup = b;
		/* Claudio Pi - 5/25/04 Added for modal popup windows */
		_useModal = useModal;

		if (_usePopup) {
			_browseImage.setVisible(false);
			_browsePopupImageLink.setVisible(true);
		} else {
			_browseImage.setVisible(true);
			_browsePopupImageLink.setVisible(false);
		}
	}

	/**
	 * @return
	 */
	public int getPopupHeight() {
		return _popupHeight;
	}

	/**
	 * @return
	 */
	public int getPopupWidth() {
		return _popupWidth;
	}

	/**
	 * @param i
	 */
	public void setPopupHeight(int i) {
		_popupHeight = i;
	}

	/**
	 * @param i
	 */
	public void setPopupWidth(int i) {
		_popupWidth = i;
	}

	public void setDisplayFormat(String format) {
		getEditField().setDisplayFormat(format);
	}

	public void setDisplayFormatLocaleKey(String key) {
		getEditField().setDisplayFormatLocaleKey(key);

	}

	/**
	 * This method can be used by controllers of lookup popup windows to find
	 * the current value in the lookup component
	 */
	public static String getParentLookupValue(JspController cont) {
		String value = cont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_VALUE);
		if (value != null && value.trim().length() == 0)
			value = null;

		return value;
	}

	/**
	 * This method can be used by controllers of lookup popup windows to find
	 * the display format the lookup component
	 */
	public static String getParentLookupFormat(JspController cont) {
		String value = null;
		String callingController = cont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER);
		if (callingController != null) {
			try {
				JspController otherCont = (JspController) cont.getSession().getAttribute(callingController);
				if (otherCont != null) {
					HtmlLookUpComponent luComp = (HtmlLookUpComponent) otherCont.getComponent(cont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_COMPONENT));
					value = luComp.getEditField().getDisplayFormat();
				}
			} catch (Exception ex) {
			}
		}
		return value;
	}

	/**
	 * This method can be used by controllers of lookup popup windows to find
	 * the max length of the lookup component
	 */
	public static int  getParentLookupMaxLength(JspController cont) {
		int  value = 0;
		String callingController = cont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER);
		if (callingController != null) {
			try {
				JspController otherCont = (JspController) cont.getSession().getAttribute(callingController);
				if (otherCont != null) {
					HtmlLookUpComponent luComp = (HtmlLookUpComponent) otherCont.getComponent(cont.getParameter(HtmlLookUpComponent.PARAM_LOOKUP_COMPONENT));
					value = luComp.getEditField().getMaxLength();
				}
			} catch (Exception ex) {
			}
		}
		return value;
	}
	/**
	 * This method will load the font start and end tags from the page
	 * properties object.See the Constants at the top of the class for valid
	 * values to pass to this method.
	 */
	public void setDescriptionFont(String font) {
		_font = font;
		Props props = getPage().getPageProperties();

		if (_font != null) {
			_fontStartTag = props.getProperty(_font + Props.TAG_START);
			_fontEndTag = props.getProperty(_font + Props.TAG_END);
		} else {
			_fontStartTag = props.getProperty(HtmlText.FONT_DEFAULT + Props.TAG_START);
			_fontEndTag = props.getProperty(HtmlText.FONT_DEFAULT + Props.TAG_END);
		}

	}

	/**
	 * @return true if a description should be shown next to the field
	 */
	public boolean getShowDescription() {
		return _showDescription;
	}

	/**
	 * set to true if a description should be shown next to the field
	 */
	public void setShowDescription(boolean b) {
		_showDescription = b;
	}

	/*
	 * Sets an expression for the description of the field being looked up
	 */
	public void setDescriptionExpression(DataStoreBuffer ds, String exp) throws DataStoreException {
		int index = ds.getColumnIndex(exp);

		if (!_editDescription) {
			_descriptionEval = new DataStoreEvaluator(ds, exp);
			if (index != -1)
				_descriptionColumn = index;
			_showDescription = true;
			_descDs = ds;
		} else if (index != -1)
			getEditField().setColumn(ds, index);

	}
	/*
	 * Sets an expression for the description of the field being looked up
	 */
	public void setDescriptionExpression(DataStoreBuffer ds, DataStoreExpression exp) throws DataStoreException {
		if (!_editDescription) {
			_descriptionEval = new DataStoreEvaluator(ds, exp);
			_showDescription = true;
			_descDs = ds;
		}
	}

	/**
	 * @return the datasource string for the description column of the lookup
	 */
	public String getDescriptionDataSource() {
		return _descriptionDataSource;
	}

	/**
	 * @sets the datasource string for the description column of the lookup
	 */
	public void setDescriptionDataSource(String string) {
		_descriptionDataSource = string;
	}

	public int getPopupLeft() {
		return _popupLeft;
	}

	public void setPopupLeft(int _popupLeft) {
		this._popupLeft = _popupLeft;
	}

	public int getPopupTop() {
		return _popupTop;
	}

	public void setPopupTop(int _popupTop) {
		this._popupTop = _popupTop;
	}

	public String getPopupPosition() {
		return _popupPosition;
	}

	public void setPopupPosition(String _popupPosition) {
		this._popupPosition = _popupPosition;
	}

	public String getEditFieldFullName(int rowNum) {
		String editFieldFullName = null;
		String formString = null;
		if (getPage() != null) {
			editFieldFullName = getEditField().getFullName();
			if (rowNum > -1)
				editFieldFullName += "_" + rowNum;
			formString = getEditField().getFormString();
		}

		if (editFieldFullName == null)
			return null;
		else
			return formString + editFieldFullName;
	}

	public String getHiddenDescrFieldFullName(int rowNum) {
		String hiddenDescrFullName = null;
		String formString = null;
		if (getPage() != null) {
			hiddenDescrFullName = getHiddenDescrField().getFullName();
			if (rowNum > -1)
				hiddenDescrFullName += "_" + rowNum;
			formString = getEditField().getFormString();
		}

		if (hiddenDescrFullName == null)
			return null;
		else
			return formString + hiddenDescrFullName;
	}

	public String getHiddenKeyFieldFullName(int rowNum) {
		String hiddenKeyFullName = null;
		String formString = null;
		if (getPage() != null) {
			hiddenKeyFullName = _hiddenKeyHandle.getFullName();
			if (rowNum > -1)
				hiddenKeyFullName += "_" + rowNum;
			formString = getEditField().getFormString();
		}

		if (hiddenKeyFullName == null)
			return null;
		else
			return formString + hiddenKeyFullName;
	}
	public String getDivFullName(int rowNum) {
		HtmlLookUpComponent luComp = this;
		String luName = luComp.getFullName();
		if (rowNum > -1)
			luName += "_" + rowNum;
		return "div" + luName;
	}

	/**
	 * @return Returns the popupAttributes if this lookup uses a popup window
	 *         (see javascript window.open for list of available attributes)".
	 */
	public String getPopupAttributes() {
		return _popupAttributes;
	}
	/**
	 * @param popupAttributes
	 *            The popupAttributes to set if this lookup uses a popup window
	 *            (see javascript window.open for list of available
	 *            attributes)"..
	 */
	public void setPopupAttributes(String popupAttributes) {
		_popupAttributes = popupAttributes;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlComponent#setTheme(java.lang.String)
	 */
	public void setTheme(String theme) {
		Props p = getPage().getPageProperties();
		_popupAttributes = p.getThemeProperty(theme, Props.LOOKUP_COMPONENT_POPUPATTRIBUTES);

		_lookUpPageURL = p.getThemeProperty(theme, Props.LOOKUP_COMPONENT_URL);
		_usePopup = p.getThemeBooleanProperty(theme, Props.LOOKUP_COMPONENT_USEPOPUP, false);
		_useModal = p.getThemeBooleanProperty(theme, Props.LOOKUP_COMPONENT_POPUPMODAL, false);
		_popupWidth = p.getThemeIntProperty(theme, Props.LOOKUP_COMPONENT_POPUPWIDTH, 300);
		_popupHeight = p.getThemeIntProperty(theme, Props.LOOKUP_COMPONENT_POPUPHEIGHT, 300);
		_popupTop = p.getThemeIntProperty(theme, Props.LOOKUP_COMPONENT_POPUPTOP, 0);
		_popupLeft = p.getThemeIntProperty(theme, Props.LOOKUP_COMPONENT_POPUPLEFT, 0);
		_popupPosition = p.getThemeProperty(theme, Props.LOOKUP_COMPONENT_POPUPPOSITION, Constants.POPUP_POSITION_CUSTOM);
		_useDiv=p.getThemeBooleanProperty(theme, Props.LOOKUP_COMPONENT_POPUPDIV,false);
		_divBorderStyle=p.getThemeProperty(theme,Props.LOOKUP_COMPONENT_POPUPDIVBORDERSTYLE);
		setEditDescription(p.getThemeBooleanProperty(theme, Props.LOOKUP_COMPONENT_EDITDESCRIPTION));
		super.setTheme(theme);

	}
	/**
	 * @return Returns whether or not the description is editable. If the
	 *         description is editable, the key field will not be displayed, but
	 *         will be set by the lookup.
	 */
	public boolean getEditDescription() {
		return _editDescription;
	}
	/**
	 * @param editDescription
	 *            Sets whether or not the description is editable. If the
	 *            description is editable, the key field will not be displayed,
	 *            but will be set by the lookup.
	 */
	public void setEditDescription(boolean editDescription) {
		_editDescription = editDescription;
		_hiddenKeyHandle.setVisible(editDescription);
	}


	/**
	 * @return Returns the useDiv flag to indicate if a popup should open in a div.
	 */
	public boolean getUseDiv() {
		return _useDiv;
	}
	/**
	 * @param useDiv The useDiv to set.
	 */
	public void setUseDiv(boolean useDiv) {
		_useDiv = useDiv;
	}
	/**
	 * @return Returns the divBorderStyle.
	 */
	public String getDivBorderStyle() {
		return _divBorderStyle;
	}
	/**
	 * @param divBorderStyle For popups that open in divs sets the border style (CSS) for the div border default  is: 1px solid black.
	 */
	public void setDivBorderStyle(String divBorderStyle) {
		_divBorderStyle = divBorderStyle;
	}
	/**
	 * @return Returns the divScrolling.
	 */
	public boolean getDivScrolling() {
		return _divScrolling;
	}
	/**
	 * @param divScrolling For popus that open in divs, whether or not a scrollbar will be present for overflow
	 */
	public void setDivScrolling(boolean divScrolling) {
		_divScrolling = divScrolling;
	}
	
	/**
	 * Add a form component value to the url line of a popup request
	 * @param comp The component to get the value from
	 * @param requestParmName The name of the parameter to add to the URL line
	 */
	public void addFormComponentValueToPopup(HtmlComponent comp, String requestParmName) {
		if (comp == null)
			return;
		if (_popupURLLineValues == null)
			_popupURLLineValues=new Vector<FormComponentInfo>();

		boolean isInDataTable=false;
		HtmlComponent parent=comp.getParent();
		while (parent != null) {
			if (parent instanceof JspDataTable || parent instanceof JspList || parent instanceof HtmlDataTable) {
				isInDataTable=true;
				break;
			}	
			parent=parent.getParent();
		}	
			
		if (comp instanceof HtmlLookUpComponent)
			comp = ((HtmlLookUpComponent)comp).getEditField();
		String name=comp.getFormString() + comp.getFullName();
		FormComponentInfo inf=new FormComponentInfo();
		inf.attName=requestParmName;
		inf.compName=name;
		inf.isInDataTable=isInDataTable;
		_popupURLLineValues.add(inf);
	}	
}