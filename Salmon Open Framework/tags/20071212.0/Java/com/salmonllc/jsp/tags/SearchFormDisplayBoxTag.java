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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/SearchFormDisplayBoxTag.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 12/22/03 4:59p $
/////////////////////////

import java.lang.reflect.Constructor;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspSearchFormDisplayBox;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * This is a tag used to create a display box (similar to a window).
 */

public class SearchFormDisplayBoxTag extends ContainerTag {
	private String _caption;
	private String _backgroundColor;
	private String _headingBackgroundColor;
	private String _border;
	private String _cellPadding;
	private String _cellSpacing;
	private String _theme;
	private String _width;
	private String _font;
	private String _dataSource;
	private String _buttonDisplayLocation;
	private String _buttonBgColor;
	private String _buttonFontStyle;
	private String _addButtonCaption;
	private String _addButtonVisible;
	private String _searchButtonCaption;
	private String _searchButtonVisible;
	private String _qbeBuilder;
	private String _qbeValidator;
	private String _listForm;
	private String _supressHeader;
	private String _searchFormClass;
	private String _searchButtonAccessKey;
	private String _addButtonAccessKey;
	private String _cancelButtonCaption;
	private String _cancelButtonVisible;
	private String _cancelButtonAccessKey;

	/**
	 * This method creates the JSPSearchFormDisplayBox used by the tag.
	 */

	public HtmlComponent createComponent() {
		JspSearchFormDisplayBox box = null;
		if (_searchFormClass != null)
			box = buildComponent(_searchFormClass);
		else {
			Props p = getHelper().getController().getPageProperties();
			String className = p.getThemeProperty(_theme, Props.SEARCH_FORM_DISPLAY_BOX_CLASS);
			if (className != null)
				box = buildComponent(className);
		}

		if (box == null)
			box = new JspSearchFormDisplayBox(getName(), getHelper().getController());

		box.setQBEBuilderName(_qbeBuilder);
		box.setQBEValidatorName(_qbeValidator);
		box.setListFormName(_listForm);
		if (!BaseTagHelper.isEmpty(_theme))
			box.setTheme(_theme);
		if (!BaseTagHelper.isEmpty(_border))
			box.setBorder(BaseTagHelper.stringToInt(_border));
		if (!BaseTagHelper.isEmpty(_cellPadding))
			box.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
		if (!BaseTagHelper.isEmpty(_cellSpacing))
			box.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
		if (!BaseTagHelper.isEmpty(_width))
			box.setWidth(_width);
		if (!BaseTagHelper.isEmpty(_headingBackgroundColor))
			box.setHeadingBackgroundColor(_headingBackgroundColor);
		if (!BaseTagHelper.isEmpty(_backgroundColor))
			box.setBackgroundColor(_backgroundColor);
		if (!BaseTagHelper.isEmpty(_font))
			box.setHeaderFont(_font);
		if (_dataSource != null)
			box.setDataSource(_dataSource);
		if (getClassname() != null)
			box.setClassName(getClassname());
		if (_buttonDisplayLocation != null) {
			if (_buttonDisplayLocation.equalsIgnoreCase("IN_HEADER_AND_BELOW_TABLE"))
				box.setButtonDisplayLocation(JspSearchFormDisplayBox.BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE);
			else if (_buttonDisplayLocation.equalsIgnoreCase("IN_HEADER"))
				box.setButtonDisplayLocation(JspSearchFormDisplayBox.BUTTON_DISPLAY_IN_HEADER);
			else
				box.setButtonDisplayLocation(JspSearchFormDisplayBox.BUTTON_DISPLAY_BELOW_TABLE);
		}
		if (_buttonBgColor != null)
			box.setButtonBgColor(_buttonBgColor);
		if (_buttonFontStyle != null)
			box.setButtonFontStyle(_buttonFontStyle);
		if (_addButtonCaption != null)
			box.setAddButtonCaption(_addButtonCaption);
		if (_searchButtonCaption != null)
			box.setSearchButtonCaption(_searchButtonCaption);
		if (_addButtonVisible != null)
			box.setAddButtonVisible(BaseTagHelper.stringToBoolean(_addButtonVisible, true));
		if (_searchButtonVisible != null)
			box.setSearchButtonVisible(BaseTagHelper.stringToBoolean(_searchButtonVisible, true));
		if (_supressHeader != null)
			box.suppressHeading(BaseTagHelper.stringToBoolean(_supressHeader));
		if (_searchButtonAccessKey != null)
			box.setSearchButtonAccessKey(_searchButtonAccessKey);
		if (_addButtonAccessKey != null)
			box.setAddButtonAccessKey(_addButtonAccessKey);
		box.setHeadingCaption(_caption);

		if (_cancelButtonAccessKey != null)
			box.setCancelButtonAccessKey(_cancelButtonAccessKey);
		if (_cancelButtonVisible != null)
			box.setCancelButtonVisible(BaseTagHelper.stringToBoolean(_cancelButtonVisible, true));
		if (_cancelButtonCaption != null)
			box.setCancelButtonCaption(_cancelButtonCaption);
		return box;
	}

	/**
	 * This method generates the html used by the tag.
	 */

	public void generateComponentHTML(JspWriter p) throws Exception {
		String content = getBodyContentData(true);
		JspSearchFormDisplayBox box = (JspSearchFormDisplayBox) getHelper().getController().getComponent(getName());
		if (box == null)
			return;
		TagWriter w = getTagWriter();
		w.setWriter(p);
		box.generateHTML(w, content);
	}

	/**
	 * Get the tag's background color attribute
	 */
	public String getBgcolor() {
		return _backgroundColor;
	}

	/**
	 * Get the tag's border attribute
	 */
	public String getBorder() {
		return _border;
	}

	/**
	 * Get the tag's caption attribute
	 */
	public String getCaption() {
		return _caption;
	}

	/**
	 * Get the tag's cell padding attribute
	 */
	public String getCellpadding() {
		return _cellPadding;
	}

	/**
	 * Get the tag's cell spacing attribute
	 */
	public String getCellspacing() {
		return _cellSpacing;
	}

	/**
	 * Get the Data Source the component should be bound to
	 */
	public String getDatasource() {
		return _dataSource;
	}

	/**
	 * Get the tag's heading background color attribute.
	 */
	public String getHeaderbgcolor() {
		return _headingBackgroundColor;
	}

	/**
	 * Get the tag's heading font color attribute.
	 */
	public String getHeaderfont() {
		return _font;
	}

	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 */
	public int getTagConvertType() {
		return CONV_CUSTOM;
	}

	/**
	 * Get the tag's theme attribute
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * Get the tag's width attribute
	 */
	public String getWidth() {
		return _width;
	}

	/**
	 * Override this method for tags that contain NestedTags within to return true
	 */
	public boolean hasNestedTags() {
		return false;
	}

	/**
	 * Part of the tag library specification. Clears all resources used by the tag.
	 */
	public void release() {
		super.release();
		_caption = null;
		_backgroundColor = null;
		_headingBackgroundColor = null;
		_font = null;
		_buttonDisplayLocation = null;
		_buttonBgColor = null;
		_buttonFontStyle = null;
		_addButtonCaption = null;
		_addButtonVisible = null;
		_searchButtonCaption = null;
		_searchButtonVisible = null;
		_listForm = null;
		_qbeBuilder = null;
		_qbeValidator = null;
		_supressHeader = null;
		_searchFormClass = null;
		_searchButtonAccessKey = null;
		_addButtonAccessKey = null;
		_cancelButtonCaption = null;
		_cancelButtonVisible = null;
		_cancelButtonAccessKey = null;
	}

	/**
	 * Sets the tag's background color attribute
	 */
	public void setBgcolor(String backgroundColor) {
		_backgroundColor = backgroundColor;
	}

	/**
	 * Sets the tag's border attribute
	 */
	public void setBorder(String newBorder) {
		_border = newBorder;
	}

	/**
	 * Sets the tag's Caption attribute
	 */
	public void setCaption(String caption) {
		_caption = caption;
	}

	/**
	 * Sets the tag's cell padding attribute
	 */

	public void setCellpadding(String newCellPadding) {
		_cellPadding = newCellPadding;
	}

	/**
	 * Sets the tag's cell spacing attribute
	 */

	public void setCellspacing(String newCellSpacing) {
		_cellSpacing = newCellSpacing;
	}

	/**
	 * Set the Data Source the component should be bound to
	 */
	public void setDatasource(String val) {
		_dataSource = val;
	}

	/**
	 * Set's the tag's heading background color attribute
	 */
	public void setHeaderbgcolor(String backgroundColor) {
		_headingBackgroundColor = backgroundColor;
	}

	/**
	 * Set's the tag's heading font attribute
	 */
	public void setHeaderfont(String font) {
		_font = font;
	}

	/**
	 * Sets the tag's theme attribute
	 */

	public void setTheme(String newTheme) {
		_theme = newTheme;
	}

	/**
	 * Sets the tag's width attribute
	 */

	public void setWidth(String newWidth) {
		_width = newWidth;
	}

	/**
	 * Sets the search button visible or not
	 */
	public void setSearchbuttonvisible(String visible) {
		_searchButtonVisible = visible;
	}
	/**
	 * Sets the text to display on the search button 
	 */
	public void setSearchbuttoncaption(String caption) {
		_searchButtonCaption = caption;
	}

	/**
	 * Sets the add button visible or not
	 */
	public void setAddbuttonvisible(String visible) {
		_addButtonVisible = visible;
	}

	/**
	 * Sets the add button visible or not
	 */
	public void setAddbuttoncaption(String caption) {
		_addButtonCaption = caption;
	}

	/**
	 * Sets the font style for the search and add buttons
	 */
	public void setButtonfontstyle(String style) {
		_buttonFontStyle = style;
	}
	/**
	 * Sets the background color for the search and add buttons
	 */
	public void setButtonbgcolor(String bgColor) {
		_buttonBgColor = bgColor;
	}

	/**
	 * Sets the display location for a button. Valid values are IN_HEADER and BELOW_TABLE
	 */
	public void setButtondisplaylocation(String loc) {
		_buttonDisplayLocation = loc;
	}
	/**
	 * @param string
	 */
	public void setListformdisplaybox(String string) {
		_listForm = string;
	}

	/**
	 * @param string
	 */
	public void setQbebuilder(String string) {
		_qbeBuilder = string;
	}

	/**
	 * @param string
	 */
	public void setQbevalidator(String string) {
		_qbeValidator = string;
	}

	public void setSupressheading(String string) {
		_supressHeader = string;
	}

	public void setSearchformclass(String className) {
		_searchFormClass = className;
	}

	private JspSearchFormDisplayBox buildComponent(String className) {
		try {
			Class c = Class.forName(className, true, Thread.currentThread().getContextClassLoader());

			try {
				Class[] parms = { String.class, String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), _theme, getHelper().getController()};
				return (JspSearchFormDisplayBox) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			try {
				Class[] parms = { String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), getHelper().getController()};
				return (JspSearchFormDisplayBox) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			throw new Exception("Could not find suitable constructor");
		} catch (Exception e) {
			MessageLog.writeErrorMessage(
				"Error creating JspSearchFormDisplayBox component. Make sure the component exists and has a public constructor with the arguments (String name, String theme, HtmlPage page) or (String name,  HtmlPage page)",
				e,
				this);
		}
		return null;
	}

	/**
	 * @param string
	 */
	public void setAddbuttonaccesskey(String string) {
		_addButtonAccessKey = string;
	}

	/**
	 * @param string
	 */
	public void setSearchbuttonaccesskey(String string) {
		_searchButtonAccessKey = string;
	}

	/**
	 * @param string
	 */
	public void setCancelbuttonaccesskey(String string) {
		_cancelButtonAccessKey = string;
	}

	/**
	 * @param string
	 */
	public void setCancelbuttoncaption(String string) {
		_cancelButtonCaption = string;
	}

	/**
	 * @param string
	 */
	public void setCancelbuttonvisible(String string) {
		_cancelButtonVisible = string;
	}

}
