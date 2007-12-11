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

import com.salmonllc.html.events.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;
import java.util.Hashtable;

/**
 * This class is used to allow for the selection of items from a list.
 */
public class HtmlListBox extends HtmlFormComponent {
	private OptionsSort _options = new OptionsSort();
	private String _fontTagEnd;
	private String _fontTagStart;
	private String _onChange;
	private String _onClick;
	private String _onFocus;
	private String _onLoseFocus;
	private boolean _multi = true;
	private int _size = 5;
	private Integer _tabIndex;
	private String _accessKey;
	private String _style;

	/**
	 * Constructs a new HtmlListBox component.
	 *
	 * @param name The name of the component
	 * @param p The page the component will be placed in.
	 */
	public HtmlListBox(String name, com.salmonllc.html.HtmlPage p) {
		this(name, null, p);
	}

	/**
	 * Constructs a new HtmlListBox component.
	 *
	 * @param name The name of the component
	 * @param theme The theme to use for loading properties.
	 * @param p The page the component will be placed in.
	 */
	public HtmlListBox(String name, String theme, com.salmonllc.html.HtmlPage p) {
		super(name, theme, p);
	}

	/**
	 * Creates a list box based on a simple table with an <BR> integer primary key column and a string column.  Typically the integer is a type value <BR> and the string is a description.  A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
	 *
	 * @param name DOCUMENT ME!
	 * @param theme DOCUMENT ME!
	 * @param page com.salmonllc.html.HtmlPage        The page hold the new component
	 * @param table - name of table to look up keys and displays from
	 * @param keyColumn - column to get key values from
	 * @param dispColumn - column to get display values from
	 */
	public HtmlListBox(String name, String theme, HtmlPage page, String table, String keyColumn, String dispColumn) {
		super(name, theme, page);

		/**
		 * srufle 04-02-2002 using initialize method now should be functionally the same
		 */
		initialize(table, keyColumn, dispColumn, null, false);

		//	try {
		//		HtmlComponentFactory f = new HtmlComponentFactory(page, null, null);
		//		DataStore ds = f.getDataStore();
		//		f.newIntegerDisplay(table, keyColumn, true);
		//		f.newStringDisplay(table, dispColumn, false);
		//		ds.setOrderBy(dispColumn);
		//		ds.retrieve();
		//		ds.waitForRetrieve();
		//		String vCol = table + "." + keyColumn;
		//		String dCol = table + "." + dispColumn;
		//		if (ds.gotoFirst()) {
		//			do {
		//				addOption(new Integer(ds.getInt(vCol)).toString(), ds.getString(dCol));
		//			} while (ds.gotoNext());
		//		}
		//	} catch (Exception e) {
		//		MessageLog.writeErrorMessage(e, this);
		//	}
	}

	/**
	 * Selects or deselects all items in the list
	 *
	 * @param selected DOCUMENT ME!
	 */
	public void setAll(boolean selected) {
		int count = getOptionCount();

		for (int i = 0; i < count; i++) {
			setOptionSelected(i, selected);
		}
	}

	/**
	 * This method sets the end font tag for the component.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setFontEndTag(String value) {
		_fontTagEnd = value;
	}

	/**
	 * This method gets the end font tag for the component.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getFontEndTag() {
		return _fontTagEnd;
	}

	/**
	 * This method sets the start font tag for the component.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setFontStartTag(String value) {
		_fontTagStart = value;
	}

	/**
	 * This method gets the start font tag for the component.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getFontStartTag() {
		return _fontTagStart;
	}

	/**
	 * This method sets the whether or not the user can select multiple rows from the component.
	 *
	 * @param multi DOCUMENT ME!
	 */
	public void setMulti(boolean multi) {
		_multi = multi;
	}

	/**
	 * This method returns whether or not the user can select multiple rows from the component.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getMulti() {
		return _multi;
	}

	/**
	 * This method sets the javascript to be executed when the value of the text in the component changes.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setOnChange(String value) {
		_onChange = value;
	}

	/**
	 * This method gets the javascript to be executed when the value of the text in the component changes.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnChange() {
		return _onChange;
	}

	/**
	 * This method sets the javascript to be executed when the component is clicked on.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setOnClick(String value) {
		_onClick = value;
	}

	/**
	 * This method gets the javascript to be executed when the component is clicked on.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnClick() {
		return _onClick;
	}

	/**
	 * This method sets the javascript to be executed when the component gains focus.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setOnFocus(String value) {
		_onFocus = value;
	}

	/**
	 * This method gets the javascript to be executed when the component gets focus.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnFocus() {
		return _onFocus;
	}

	/**
	 * This method sets the javascript to be executed when the component loses focus.
	 *
	 * @param value DOCUMENT ME!
	 */
	public void setOnLoseFocus(String value) {
		_onLoseFocus = value;
	}

	/**
	 * This method gets the javascript to be executed when the component loses focus.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnLoseFocus() {
		return _onLoseFocus;
	}

	/**
	 * This method returns the number of options in the component.
	 *
	 * @return DOCUMENT ME!
	 */
	public int getOptionCount() {
		return _options.size();
	}

	/**
	 * Use this method get the value of the key at index.
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOptionKey(int index) {
		if ((index < 0) || (index >= _options.size())) {
			return null;
		}

		HtmlOption opt = (HtmlOption) _options.elementAt(index);

		return opt.getKey();
	}

	/**
	 * Use this method set whether an option is selected.
	 *
	 * @param index DOCUMENT ME!
	 * @param selected DOCUMENT ME!
	 */
	public void setOptionSelected(int index, boolean selected) {
		if ((index < 0) || (index >= _options.size())) {
			return;
		}

		HtmlOption opt = (HtmlOption) _options.elementAt(index);
		opt.setSelected(selected);
	}

	/**
	 * Use this method to find out if an option is selected.
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getOptionSelected(int index) {
		if ((index < 0) || (index >= _options.size())) {
			return false;
		}

		HtmlOption opt = (HtmlOption) _options.elementAt(index);

		return opt.isSelected();
	}

	/**
	 * Use this method to set the value of the option at index.
	 *
	 * @param index DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 */
	public void setOptionValue(int index, String value) {
		if ((index < 0) || (index >= _options.size())) {
			return;
		}

		HtmlOption opt = (HtmlOption) _options.elementAt(index);
		opt.setDisplay(value);
	}

	/**
	 * Use this method get the value of the option at index.
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOptionValue(int index) {
		if ((index < 0) || (index >= _options.size())) {
			return null;
		}

		HtmlOption opt = (HtmlOption) _options.elementAt(index);

		return opt.getDisplay();
	}

	/**
	 * Returns a list of keys corresponding to the selected values
	 *
	 * @param separator DOCUMENT ME!
	 *
	 * @return java.lang.String
	 */
	public String getSelectedList(String separator) {
		String list = "";
		int count = getOptionCount();

		for (int i = 0; i < count; i++) {
			if (getOptionSelected(i)) {
				list += (separator + getOptionKey(i));
			}
		}

		if (!list.equals("")) {
			list = list.substring(separator.length());
		}

		return list;
	}

	/**
	 * This method sets the display size for the component in characters.
	 *
	 * @param size DOCUMENT ME!
	 */
	public void setSize(int size) {
		_size = size;
	}

	/**
	 * This method gets the display size for the component in characters.
	 *
	 * @return DOCUMENT ME!
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * This method sets the property theme for the component.
	 *
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {
		super.setTheme(theme);

		Props props = getPage().getPageProperties();
		_fontTagStart = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_START);
		_fontTagEnd = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_END);
	}

	/**
	 * Use this method to add new choices to the list.
	 *
	 * @param key The internal name of the item (must be unique)
	 * @param disp The value to be displayed on the list.
	 */
	public void addOption(String key, String disp) {
		addOption(key, disp, false);
	}

	/**
	 * Use this method to add new choices to the list.
	 *
	 * @param key The internal name of the item (must be unique)
	 * @param disp The value to be displayed on the list.
	 * @param selected DOCUMENT ME!
	 */
	public void addOption(String key, String disp, boolean selected) {
		HtmlOption opt = new HtmlOption(key, disp, selected);
		_options.addElement(opt);
	}

	/**
	 * Deselects all items in the list
	 */
	public void deselectAll() {
		setAll(false);
	}

	/**
	 * This method returns the index of the option with the specified key.
	 *
	 * @param key DOCUMENT ME!
	 *
	 * @return The index of the key or -1 if not found.
	 */
	public int findOptionIndexOf(String key) {
		int optionsSize = _options.size();
		HtmlOption opt = null;

		for (int i = 0; i < optionsSize; i++) {
			opt = (HtmlOption) _options.elementAt(i);

			if (key.equals(opt.getKey())) {
				return i;
			}
		}

		return -1;
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
		if (!_visible) {
			return;
		}

		String name = getFullName();

		if (rowNo > -1) {
			name += ("_" + rowNo);
		}

		String value = getValue(rowNo, true);

		if (_dsBuff != null) {
			if (value == null) {
				setInternalValue(null);
			} else {
				setInternalValue(value.toString());
			}
		}

		String tag = "<SELECT NAME=\"" + name + "\"";
		tag += (" SIZE=\"" + _size + "\"");

		if (_multi) {
			tag += " MULTIPLE";
		}

		if (_onChange != null) {
			tag += (" onChange=\"" + _onChange + "\"");
		}

		if (_onFocus != null) {
			tag += (" onFocus=\"" + _onFocus + "\"");
		}

		if (_onLoseFocus != null) {
			tag += (" onBlur=\"" + _onLoseFocus + "\"");
		}

		if (_onClick != null) {
			tag += (" onClick=\"" + _onClick + "\"");
		}

		if (_class != null) {
			tag += (" class=\"" + _class + "\"");
		}

		if (_style != null) {
			tag += (" style=\"" + _style + "\"");
		}

		if ((!_enabled) && useDisabledAttribute()) {
			tag += " disabled=\"true\"";
		}

		if (_tabIndex != null)
			tag += " tabindex=\"" + _tabIndex + "\"";

		if (_accessKey != null)
			tag += " accesskey=\"" + _accessKey + "\"";

		tag += ">";

		StringBuffer b = new StringBuffer();
		int optionsSize = _options.size();

		for (int i = 0; i < optionsSize; i++) {
			HtmlOption opt = (HtmlOption) _options.elementAt(i);
			b.append("<OPTION VALUE=\"");
			b.append(opt.getKey());
			b.append("_");
			b.append(i);
			b.append("\"");

			if (opt.isSelected()) {
				b.append(" SELECTED");
			}

			if (opt.getDisplay() == null) {
				b.append("></OPTION>");
			} else {
				b.append(">");
				b.append(opt.getDisplay());
				b.append("</OPTION>");
			}
		}

		tag += b.toString();
		tag += "</SELECT>";

		if (_fontTagStart != null) {
			tag = _fontTagStart + tag + _fontTagEnd;
		}

		p.println(tag);
		writeFocusScript(p, rowNo);
	}

	/**
	 * Creates a list box based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
	 *
	 * @param table - name of table to look up keys and displays from
	 * @param keyColumn - column to get key values from
	 * @param dispColumn - column to get display values from
	 * @param criteria - optional selection criteria
	 * @param inputVersion - optional value that allows for a null to be placed at the top
	 */
	public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion) {
		initialize(table, keyColumn, dispColumn, criteria, inputVersion, false);
	}

	/**
	 * Creates a list box based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
	 *
	 * @param table - name of table to look up keys and displays from
	 * @param keyColumn - column to get key values from
	 * @param dispColumn - column to get display values from
	 * @param criteria - optional selection criteria
	 * @param inputVersion - optional value that allows for a null to be placed at the top
	 * @param trimResults - optional value that trims the rtesults before adding the options
	 */
	public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion, boolean trimResults) {
		initialize(table, keyColumn, dispColumn, criteria, inputVersion, trimResults, false);
	}

	/**
	 * Creates a list box based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
	 *
	 * @param table - name of table to look up keys and displays from
	 * @param keyColumn - column to get key values from
	 * @param dispColumn - column to get display values from
	 * @param criteria - optional selection criteria
	 * @param inputVersion - optional value that allows for a null to be placed at the top
	 * @param trimResults - optional value that trims the rtesults before adding the options
	 * @param toUpper - optional value that makes the option's kays and display values all upper case
	 */
	public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion, boolean trimResults, boolean toUpper) {
		resetOptions();

		if (inputVersion) {
			addOption(null, "");
		}

		DBConnection connection = null;

		try {
			connection = DBConnection.getConnection(getPage().getApplicationName());

			java.sql.Statement s = connection.createStatement();
			String query = null;

			if (criteria != null) {
				query = "SELECT " + keyColumn + "," + dispColumn + " FROM " + table + " WHERE " + criteria + " ORDER BY ";

				if (toUpper) {
					query = query + "UPPER(" + dispColumn + ")";
				} else {
					query += dispColumn;
				}
			} else {
				query = "SELECT " + keyColumn + "," + dispColumn + " FROM " + table + " ORDER BY ";

				if (toUpper) {
					query = query + "UPPER(" + dispColumn + ")";
				} else {
					query += dispColumn;
				}
			}

			java.sql.ResultSet r = s.executeQuery(query);

			int cols = r.getMetaData().getColumnCount();
			String key, display;
			if (r.next()) {
				do {
					key = "";
					display = "";
					if (trimResults) {
						key = r.getObject(1).toString().trim();
						for (int i = 2; i <= cols; i++) 
							display = display + r.getObject(i).toString().trim() + " ";
						
						addOption(key, display);
					} else {
						key = r.getObject(1).toString();
						for (int i = 2; i <= cols; i++) 
							display = display + r.getObject(i).toString() + " ";
						addOption(key, display);
					}
				} while (r.next());
			}

			r.close();
			s.close();
		} catch (java.sql.SQLException se) {
			MessageLog.writeErrorMessage("initialize", se, null);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("initialize", e, this);
		} finally {
			if (connection != null) {
				connection.freeConnection();
			}
		}
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		Object oldValue = _value;
		String name = getFullName();

		if (rowNo > -1) {
			name += ("_" + rowNo);

			if (_dsBuff != null) {
				oldValue = _dsBuff.getAny(rowNo, _dsColNo);
			}
		} else {
			if (_dsBuff != null) {
				oldValue = _dsBuff.getAny(_dsColNo);
			}
		}

		String val[] = (String[]) parms.get(name);
		int optionsSize = _options.size();
		HtmlOption opt = null;

		for (int i = 0; i < optionsSize; i++) {
			opt = (HtmlOption) _options.elementAt(i);
			opt.setSelected(false);
		}

		if (val != null) {
			int index = -1;

			for (int i = 0; i < val.length; i++) {
				index = Integer.parseInt(val[i].substring(val[i].lastIndexOf("_") + 1));
				if (index < _options.size()) {
					opt = (HtmlOption) _options.elementAt(index);
					opt.setSelected(true);
				}
			}
		}

		if (!_multi)
			_value = getInternalValue();
		else
			_value = getSelectedList(",");

		if (!valuesEqual(oldValue, _value)) {
			String s = null;

			if (oldValue != null) {
				s = oldValue.toString();
			}

			ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
			addEvent(e);
		}

		return false;
	}

	/**
	 * Use this method to remove an option from the list.
	 *
	 * @param index The index of the option to remove.
	 */
	public void removeOption(int index) {
		if ((index < 0) || (index >= _options.size())) {
			return;
		}

		_options.removeElementAt(index);
	}

	/**
	 * This method removes all options from the component.
	 */
	public void resetOptions() {
		_options.removeAllElements();
	}

	/**
	 * Selects all items in the list
	 */
	public void selectAll() {
		setAll(true);
	}

	/**
	 * Claudio Pi (4-01-2003) This method sorts the dropdown list based on its options values in the default order direction.
	 */
	public void sort() {
		_options.sort();
	}

	/**
	 * Claudio Pi (4-01-2003) This method sorts the dropdown list based on its options values in the given order direction.
	 *
	 * @param dir direction in which the options will be sorted (0=Ascending or 1=Descending order)
	 */
	public void sort(int dir) {
		_options.setSortDir(dir);
		_options.sort();
	}

	/**
	 * This method sets the first selected option in the list box to the one with the key.
	 *
	 * @param value DOCUMENT ME!
	 */
	private void setInternalValue(String value) {

		int optionsSize = _options.size();
		HtmlOption opt = null;
		String key = null;
		boolean selected = false;

		//
		for (int i = 0; i < optionsSize; i++) {
			opt = (HtmlOption) _options.elementAt(i);
			key = opt.getKey();
			selected = false;

			if (key != null) {
				if (key.equals(value)) {
					selected = true;
				}
			}

			opt.setSelected(selected);
		}
	}

	/**
	 * This method returns the key of the first selected option in the list box..
	 *
	 * @return DOCUMENT ME!
	 */
	private String getInternalValue() {
		int optionsSize = _options.size();
		HtmlOption opt = null;

		for (int i = 0; i < optionsSize; i++) {
			opt = (HtmlOption) _options.elementAt(i);

			if (opt.isSelected()) {
				return opt.getKey();
			}
		}

		return null;
	}
	/**
	 * @return the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
	}

	/**
	 * @return the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null)
			return -1;
		return _tabIndex.intValue();
	}

	/**
	 * @param string sets the access key html attribute
	 */
	public void setAccessKey(String string) {
		_accessKey = string;
	}

	/**
	 * @param val sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else
			_tabIndex = new Integer(val);
	}

	/**
	 * This method sets the property style (allows for the inlining of style attributes like color:#666666;) for the component.
	 *
	 * @param style - The style text to use.
	 */
	public void setStyle(String style) {
		_style = style;
	}

	/**
	 * This method gets the property style (allows for the inlining of style attributes like color:#666666;) for the component.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getStyle() {
		return _style;
	}

}