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

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/OptionTag.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 8/23/04 11:45a $
////////////////////////////////////////////////////////////

import com.salmonllc.util.Util;

import com.salmonllc.html.*;

/**
 * The option tag represents the options of a list box or drop down list box and
 * needs to live in an input tag.
 */
public class OptionTag extends BaseEmptyTag {
	private String _display;
	private String _key;
	private String _table;
	private String _keycolumn;
	private String _displaycolumn;
	private String _nulloption;
	private String _toupper;
	//_criteria added by ilev 2004-03-20
	private String _criteria;
	// end added by ilev

	private String _reloadDropDownInEveryPageRequest;

	/**
	 * There is no component for an option. It adds the option to it's parent
	 * 
	 * @return HtmlComponent
	 */
	public HtmlComponent createComponent() {
		String table = null;
		HtmlComponent comp = getHelper().getInputTag().getHelper().getComponent();
		table = getTable();
		if (comp instanceof HtmlDropDownList) {

			if (!Util.isNull(table) && !Util.isEmpty(table)) {
				boolean reloadOptionsEveryPageRequest = Util.stringToBoolean(getReloadDropDownInEveryPageRequest(), false);
				/**
				 * initialize( String table, String keyColumn, String
				 * dispColumn, String criteria, boolean inputVersion, boolean
				 * trimResults, boolean toUpper, boolean
				 * reloadOptionsEveryPageRequest)
				 */
				((HtmlDropDownList) comp).initialize(table, getKeycolumn(), getDisplaycolumn(), getCriteria(), Util.stringToBoolean(getNulloption(), false), true, Util.stringToBoolean(getToupper(), false), reloadOptionsEveryPageRequest);

				/*-- Add the page listener only if we need it --*/
				if (reloadOptionsEveryPageRequest)
					getHelper().getInputTag().getHelper().getController().addPageListener(((HtmlDropDownList) comp));

			} else {
				((HtmlDropDownList) comp).addOption(_key, _display);
			}

		} else if (comp instanceof HtmlListBox) {

			if (!Util.isNull(table) && !Util.isEmpty(table)) {
				((HtmlListBox) comp).initialize(table, getKeycolumn(), getDisplaycolumn(), getCriteria(), Util.stringToBoolean(getNulloption(), false), true, Util.stringToBoolean(getToupper(), false));
			} else {
				((HtmlListBox) comp).addOption(_key, _display);
			}
		} else if (comp instanceof HtmlRadioButtonGroup) {

			if (!Util.isNull(table) && !Util.isEmpty(table)) {
				((HtmlRadioButtonGroup) comp).initialize(table, getKeycolumn(), getDisplaycolumn(), getCriteria(), true, Util.stringToBoolean(getToupper(), false));
			} else {
				((HtmlRadioButtonGroup) comp).addOption(_key, _display);
			}
		}
		return null;
	}

	/**
	 * Get the tag's display attribute
	 * 
	 * @return
	 */

	public String getDisplay() {
		return _display;
	}

	/**
	 * Get the tag's key attribute
	 * 
	 * @return
	 */

	public String getKey() {
		return _key;
	}

	/**
	 * Release all resources used by the tag.
	 */
	public void release() {
		super.release();
		_display = null;
		_key = null;
		_table = null;
		_keycolumn = null;
		_displaycolumn = null;
		_nulloption = null;
		_toupper = null;
		_criteria = null;
		_reloadDropDownInEveryPageRequest = null;
	}

	/**
	 * Set the tag's display attribute
	 * 
	 * @param display
	 */

	public void setDisplay(String display) {
		_display = display;
	}

	/**
	 * Set the tag's key attribute
	 * 
	 * @param key
	 */

	public void setKey(String key) {
		_key = key;
	}

	/**
	 * Get the tag's table attribute
	 * 
	 * @return
	 */
	public String getTable() {
		return _table;
	}

	/**
	 * Set the tag's table attribute
	 * 
	 * @param table
	 */
	public void setTable(String table) {
		_table = table;
	}

	/**
	 * Get the tag's keycolumn attribute
	 * 
	 * @return
	 */
	public String getKeycolumn() {
		return _keycolumn;
	}

	/**
	 * Set the tag's keycolumn attribute
	 * 
	 * @param keycolumn
	 */
	public void setKeycolumn(String keycolumn) {
		_keycolumn = keycolumn;
	}

	/**
	 * Get the tag's displaycolumn attribute
	 * 
	 * @return
	 */
	public String getDisplaycolumn() {
		return _displaycolumn;
	}

	/**
	 * Set the tag's displaycolumn attribute
	 * 
	 * @param displaycolumn
	 */
	public void setDisplaycolumn(String displaycolumn) {
		_displaycolumn = displaycolumn;
	}

	/**
	 * Get the tag's nulloption attribute
	 * 
	 * @return
	 */
	public String getNulloption() {
		return _nulloption;
	}

	/**
	 * Set the tag's nulloption attribute
	 * 
	 * @param nulloption
	 */
	public void setNulloption(String nulloption) {
		_nulloption = nulloption;
	}

	/**
	 * Get the tag's toupper attribute
	 * 
	 * @return
	 */
	public String getToupper() {
		return _toupper;
	}

	/**
	 * Set the tag's toupper attribute
	 * 
	 * @param toupper
	 */
	public void setToupper(String toupper) {
		_toupper = toupper;
	}

	/**
	 * Get the tag's reloadDropDownInEveryPageRequest attribute
	 * 
	 * @return
	 */
	public String getReloadDropDownInEveryPageRequest() {
		return _reloadDropDownInEveryPageRequest;
	}

	/**
	 * Set the tag's reloadDropDownInEveryPageRequest attribute
	 * 
	 * @param reloadDropDownInEveryPageRequest
	 */
	public void setReloadDropDownInEveryPageRequest(String reloadDropDownInEveryPageRequest) {
		this._reloadDropDownInEveryPageRequest = reloadDropDownInEveryPageRequest;
	}

	/**
	 * added by ilev 2004-03-20 Get the tag's criteria attribute
	 * 
	 * @return
	 */
	public String getCriteria() {
		return _criteria;
	}

	/**
	 * added by ilev 2004-03-20 Set the tag's criteria attribute
	 * 
	 * @param criteria
	 */
	public void setCriteria(String criteria) {
		_criteria = criteria;
	}

}