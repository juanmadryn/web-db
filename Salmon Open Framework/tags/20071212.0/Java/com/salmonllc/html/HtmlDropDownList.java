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
import com.salmonllc.properties.*;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.util.*;


/**
 * This class is used for text input in a page in the form of a drop down list box of options.
 */
public class HtmlDropDownList extends HtmlFormComponent implements PageListener
{
    private Integer _tabIndex;

    /*Claudio Pi (4-01-2003) Changed to allow options sorting*/
    private OptionsSort _optionsVec                       = new OptionsSort();
    private String      _criteria;
    private String      _dispColumn;
    private String      _fontTagEnd;
    private String      _fontTagStart;
    private String      _keyColumn;
    private String      _onChange;
    private String      _onClick;
    private String      _onFocus;
    private String      _onLoseFocus;
    private String      _style;
    private String      _table;
    private boolean     _inputVersion;
    private boolean     _reloadDropDownInEveryPageRequest;
    private boolean     _toUpper;
    private boolean     _trimResults;

    /**
     * Constructs a new HTMLDropDownList component.
     *
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlDropDownList(String name, com.salmonllc.html.HtmlPage p)
    {
        this(name, null, p);
    }

    /**
     * Constructs a new HTMLDropDownList component.
     *
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page the component will be placed in.
     */
    public HtmlDropDownList(String name, String theme, com.salmonllc.html.HtmlPage p)
    {
        super(name, theme, p);
    }

    /**
     * Creates a drop-down list based on a table with an integer primary key column (typically an id) and a string column. A simplifying assumption is that each of the following is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param name DOCUMENT ME!
     * @param theme DOCUMENT ME!
     * @param page com.salmonllc.html.HtmlPage        The page hold the new component
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     */
    public HtmlDropDownList(String name, String theme, HtmlPage page, String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion)
    {
        super(name, theme, page);
        initialize(table, keyColumn, dispColumn, criteria, inputVersion);
    }

    /**
     * Creates a drop-down list based on a table with an integer primary key column (typically an id) and a string column. A simplifying assumption is that each of the following is the same: - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param name DOCUMENT ME!
     * @param theme DOCUMENT ME!
     * @param page com.salmonllc.html.HtmlPage        The page hold the new component
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param inputVersion - optional value that allows for a null to be placed at the top
     */
    public HtmlDropDownList(String name, String theme, HtmlPage page, String table, String keyColumn, String dispColumn, boolean inputVersion)
    {
        super(name, theme, page);
        initialize(table, keyColumn, dispColumn, null, inputVersion);
    }

    /**
     * Sets the criteria
     *
     * @param criteria
     */
    public void setCriteria(String criteria)
    {
        this._criteria = criteria;
    }

    /**
     * Returns the criteria
     *
     * @return
     */
    public String getCriteria()
    {
        return _criteria;
    }

    /**
     * This method sets the end font tag for the component.
     *
     * @param value DOCUMENT ME!
     */
    public void setFontEndTag(String value)
    {
        _fontTagEnd = value;
    }

    /**
     * This method gets the end font tag for the component.
     *
     * @return DOCUMENT ME!
     */
    public String getFontEndTag()
    {
        return _fontTagEnd;
    }

    /**
     * This method sets the start font tag for the component.
     *
     * @param value DOCUMENT ME!
     */
    public void setFontStartTag(String value)
    {
        _fontTagStart = value;
    }

    /**
     * This method gets the start font tag for the component.
     *
     * @return DOCUMENT ME!
     */
    public String getFontStartTag()
    {
        return _fontTagStart;
    }

    public void setInputVersion(boolean _inputVersion)
    {
        this._inputVersion = _inputVersion;
    }

    public boolean isInputVersion()
    {
        return _inputVersion;
    }

    /**
     * This method sets the javascript to be executed when the value of the text in the component changes.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnChange(String value)
    {
        _onChange = value;
    }

    /**
     * This method gets the javascript to be executed when the value of the text in the component changes.
     *
     * @return DOCUMENT ME!
     */
    public String getOnChange()
    {
        return _onChange;
    }

    /**
     * This method sets the javascript to be executed when the component is clicked on.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnClick(String value)
    {
        _onClick = value;
    }

    /**
     * This method gets the javascript to be executed when the component is clicked on.
     *
     * @return DOCUMENT ME!
     */
    public String getOnClick()
    {
        return _onClick;
    }

    /**
     * This method sets the javascript to be executed when the component gains focus.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnFocus(String value)
    {
        _onFocus = value;
    }

    /**
     * This method gets the javascript to be executed when the component gets focus.
     *
     * @return DOCUMENT ME!
     */
    public String getOnFocus()
    {
        return _onFocus;
    }

    /**
     * This method sets the javascript to be executed when the component loses focus.
     *
     * @param value DOCUMENT ME!
     */
    public void setOnLoseFocus(String value)
    {
        _onLoseFocus = value;
    }

    /**
     * This method gets the javascript to be executed when the component loses focus.
     *
     * @return DOCUMENT ME!
     */
    public String getOnLoseFocus()
    {
        return _onLoseFocus;
    }

    /**
     * This method returns the number of options in the component.
     *
     * @return DOCUMENT ME!
     */
    public int getOptionCount()
    {
        return _optionsVec.size();
    }

    /**
     * Use this method to set the key of the option at index.
     *
     * @param index DOCUMENT ME!
     * @param key DOCUMENT ME!
     */
    public void setOptionKey(int index, String key)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return;
        }

        HtmlOption opt = (HtmlOption) _optionsVec.elementAt(index);
        opt.setKey(key);
    }

    /**
     * Use this method get the value of the key at index.
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getOptionKey(int index)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return null;
        }

        HtmlOption opt = (HtmlOption) _optionsVec.elementAt(index);

        return (String) opt.getKey();
    }

    /**
     * Use this method to set the value of the option at index.
     *
     * @param index DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public void setOptionValue(int index, String value)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return;
        }

        HtmlOption opt = (HtmlOption) _optionsVec.elementAt(index);
        opt.setDisplay(value);
    }

    /**
     * Use this method get the value of the option at index.
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getOptionValue(int index)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return null;
        }

        HtmlOption opt = (HtmlOption) _optionsVec.elementAt(index);

        return (String) opt.getDisplay();
    }

    /**
     * This method adds the options in the list.  This method can be used in a property expression. To use this in a property expression ; 1- Create a DataStoreExpression class which will extend DataStoreExpression. in the evaluateExpression() method handle the row value and create a list of HtmlOption objects and return all of them in a ArrayList. 2- In the controller add the property expression
     * to the datatable. Example; MyDSExpressionThatExtendsDataStoreExpression dsSourceExp = new MyDSExpressionThatExtendsDataStoreExpression(); _myDataTable.addPropertyExpression(_ddlMyDropDownList, "Options", dsSourceExp); //TODO Add some examples for this method.
     *
     * @param options
     */
    public void setOptions(ArrayList options)
    {
        if (options == null)
        {
            return;
        }

        resetOptions();

        int iSize = options.size();

        for (int i = 0; i < iSize; i++)
        {
            HtmlOption option = (HtmlOption) options.get(i);
            addOption(option);
        }
    }

    public OptionsSort getOptionsVector() throws CloneNotSupportedException
    {
        OptionsSort retVal = new OptionsSort();
        int         iSize = _optionsVec.size();
        HtmlOption  opt   = null;

        for (int i = 0; i < iSize; i++)
        {
            opt = (HtmlOption) ((HtmlOption) _optionsVec.elementAt(i)).clone();
            retVal.add(opt);
        }

        return retVal;
    }

    /**
     * This methods returns the ReloadDropDownInEveryPageRequest attribute
     *
     * @return
     */
    public boolean getReloadDropDownInEveryPageRequest()
    {
        return _reloadDropDownInEveryPageRequest;
    }

    /**
     * Sets the value of the selected index.
     *
     * @param index DOCUMENT ME!
     */
    public void setSelectedIndex(int index)
    {
        setSelectedIndex(index, -1);
    }

    /**
     * Sets the value of the selected index.
     *
     * @param index DOCUMENT ME!
     * @param row DOCUMENT ME!
     */
    public void setSelectedIndex(int index, int row)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return;
        }

        HtmlOption opt = (HtmlOption) _optionsVec.elementAt(index);

        if (opt.getKey() == null)
        {
            setValue(null, row);
        }
        else
        {
            setValue(opt.getKey().toString(), row);
        }
    }

    /**
     * Returns the index of the selected option.
     *
     * @return DOCUMENT ME!
     */
    public int getSelectedIndex()
    {
        String option = getValue();

        return findOptionIndexOf(option);
    }

    /**
     * Returns the index of the selected option.
     *
     * @param rowNo DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getSelectedIndex(int rowNo)
    {
        String option = getValue(rowNo);

        return findOptionIndexOf(option);
    }

    /**
     * This method sets the property style (allows for the inlining of style attributes like color:#666666;) for the component.
     *
     * @param style - The style text to use.
     */
    public void setStyle(String style)
    {
        _style = style;
    }

    /**
     * This method gets the property style (allows for the inlining of style attributes like color:#666666;) for the component.
     *
     * @return DOCUMENT ME!
     */
    public String getStyle()
    {
        return _style;
    }

    /**
     * sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
     *
     * @param val
     */
    public void setTabIndex(int val)
    {
        if (val == -1)
        {
            _tabIndex = null;
        }
        else
        {
            _tabIndex = new Integer(val);
        }
    }

    /**
     * Returns the tab index html attribute
     *
     * @return int
     */
    public int getTabIndex()
    {
        if (_tabIndex == null)
        {
            return -1;
        }

        return _tabIndex.intValue();
    }

    /**
     * This method sets the property theme for the component.
     *
     * @param theme The theme to use.
     */
    public void setTheme(String theme)
    {
        super.setTheme(theme);

        Props props = getPage().getPageProperties();

        _fontTagStart = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_START);
        _fontTagEnd   = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_END);
    }

    /**
     * Flag to convert the options to the upper case
     *
     * @param toUpper
     */
    public void setToUpper(boolean toUpper)
    {
        this._toUpper = toUpper;
    }

    /**
     * Flag to convert the options to the upper case
     *
     * @return
     */
    public boolean isToUpper()
    {
        return _toUpper;
    }

    /**
     * Flag to trim the options
     *
     * @param trimResults
     */
    public void setTrimResults(boolean trimResults)
    {
        this._trimResults = trimResults;
    }

    /**
     * Flag to trim the options
     *
     * @return
     */
    public boolean isTrimResults()
    {
        return _trimResults;
    }

    /**
     * Use this method to add new choices to the list.
     *
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     */
    public void addOption(String key, String disp)
    {
        HtmlOption opt = new HtmlOption(key, disp);
        _optionsVec.addElement(opt);
    }

    /**
     * Use this method to add new choices to the list.
     *
     * @param opt - This allows for full control of the option before adding
     */
    public void addOption(HtmlOption opt)
    {
        _optionsVec.addElement(opt);
    }

    /**
     * This method returns the index of the option with the specified key.
     *
     * @param key DOCUMENT ME!
     *
     * @return The index of the key or -1 if not found.
     */
    public int findOption2IndexOf(String key)
    {
        int        optionsSize = _optionsVec.size();
        HtmlOption opt = null;

        for (int i = 0; i < optionsSize; i++)
        {
            opt = (HtmlOption) _optionsVec.elementAt(i);

            if (key == null)
            {
                if (opt.getDisplay() == null)
                {
                    return i;
                }
            }
            else if (key.equals(opt.getDisplay()))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * This method returns the index of the option with the specified key.
     *
     * @param key DOCUMENT ME!
     *
     * @return The index of the key or -1 if not found.
     */
    public int findOptionIndexOf(String key)
    {
        int        optionsSize = _optionsVec.size();
        HtmlOption opt = null;

        for (int i = 0; i < optionsSize; i++)
        {
            opt = (HtmlOption) _optionsVec.elementAt(i);

            if (key == null)
            {
                if (opt.getKey() == null)
                {
                    return i;
                }
            }
            else if (key.equals(opt.getKey()))
            {
                return i;
            }
        }

        return -1;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo)
                      throws Exception
    {
        if (!_visible)
        {
            return;
        }

        String name = getFullName();

        if (rowNo > -1)
        {
            name += ("_" + rowNo);
        }

        StringBuffer tag = new StringBuffer("<SELECT NAME=\"" + name + "\"");

        if (!_enabled)
        {
            if (useDisabledAttribute())
            {
                tag.append(" disabled=\"true\"");
            }
            else
            {
                String out = getOptionValue(findOptionIndexOf(getValue(rowNo, true)));

                if (out == null)
                {
                    out = "";
                }

                out += "&nbsp;";

                if (_disabledFontStartTag != null)
                {
                    p.print(_disabledFontStartTag + out + _disabledFontEndTag);
                }
                else
                {
                    p.print(out);
                }

                return;
            }
        }

        if (_onChange != null)
        {
            tag.append(" onChange=\"" + _onChange + "\"");
        }

        if (_onFocus != null)
        {
            tag.append(" onFocus=\"" + _onFocus + "\"");
        }

        if (_onLoseFocus != null)
        {
            tag.append(" onBlur=\"" + _onLoseFocus + "\"");
        }

        if (_onClick != null)
        {
            tag.append(" onClick=\"" + _onClick + "\"");
        }

        if (getClassName() != null)
        {
            tag.append(" class=\"" + getClassName() + "\"");
        }

        if (Util.isFilled(getStyle()))
        {
            tag.append(" style=\"" + getStyle() + "\"");
        }

        if (_tabIndex != null)
        {
            tag.append(" tabindex=\"" + _tabIndex + "\"");
        }

        tag.append(">");

        String value = getValue(rowNo, true);

        int    selectedIndex = findOptionIndexOf(value);

        //
        int        optionsSize = _optionsVec.size();
        HtmlOption opt = null;

        for (int i = 0; i < optionsSize; i++)
        {
            opt = (HtmlOption) _optionsVec.elementAt(i);

            tag.append("<OPTION");

            if (Util.isFilled(opt.getClassname()))
            {
                tag.append(" CLASS=\"");
                tag.append(opt.getClassname());
                tag.append("\"");
            }

            if (Util.isFilled(opt.getStyle()))
            {
                tag.append(" STYLE=\"");
                tag.append(opt.getStyle());
                tag.append("\"");
            }

            if (opt.getKey() == null)
            {
                tag.append(" VALUE=\"\"");
            }
            else
            {
                tag.append(" VALUE=\"");
                tag.append(opt.getKey());
                tag.append("\"");
            }

            if (selectedIndex == i)
            {
                tag.append(" SELECTED");
            }

            tag.append(">" + (String) opt.getDisplay() + "</OPTION>");
        }

        tag.append("</SELECT>");

        if (_fontTagStart != null)
        {
            tag.insert(0, _fontTagStart);
            tag.append(_fontTagEnd);
        }

        if (_generateNewline)
        {
            p.println(tag.toString());
        }
        else
        {
            p.print(tag.toString());
        }

        writeFocusScript(p, rowNo);
    }

    /**
     * Creates a drop-down list based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     */
    public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion)
    {
        initialize(table, keyColumn, dispColumn, criteria, inputVersion, false);
    }

    /**
     * Creates a drop-down list based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     * @param trimResults - optional value that trims the rtesults before adding the options
     */
    public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion, boolean trimResults)
    {
        initialize(table, keyColumn, dispColumn, criteria, inputVersion, trimResults, false);
    }

    /**
     * Creates a drop-down list based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     * @param trimResults - optional value that trims the rtesults before adding the options
     * @param toUpper - optional value that makes the option's kays and display values all upper case
     */
    public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion, boolean trimResults, boolean toUpper)
    {
        initialize(table, keyColumn, dispColumn, criteria, inputVersion, trimResults, toUpper, false);
    }

    /**
     * Creates a drop-down list based on a table with an <BR> integer primary key column (typically an id) and a string column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     * @param trimResults - optional value that trims the rtesults before adding the options
     * @param toUpper - optional value that makes the option's kays and display values all upper case
     * @param reloadOptionsEveryPageRequest - If this is set and the options are bound to the database, than options will be refereshed in every page request. This will increase trhe database trafic.
     */
    public void initialize(String table, String keyColumn, String dispColumn, String criteria, boolean inputVersion, boolean trimResults, boolean toUpper, boolean reloadOptionsEveryPageRequest)
    {
        resetOptions();

        if (inputVersion)
        {
            addOption(null, "");
        }

        _table                            = table;
        _keyColumn                        = keyColumn;
        _dispColumn                       = dispColumn;
        _reloadDropDownInEveryPageRequest = reloadOptionsEveryPageRequest;
        _criteria                         = criteria;
        _trimResults                      = trimResults;
        _toUpper                          = toUpper;
        _inputVersion                     = inputVersion;

        populateDropdownOptions();
    }

    public void pageRequestEnd(PageEvent p) throws Exception
    {
    }

    /**
     * This method refreshes the dropdown if the attribute is set.
     *
     * @param p
     *
     * @throws Exception
     */
    public void pageRequested(PageEvent p) throws Exception
    {
        if (getReloadDropDownInEveryPageRequest() && !getPage().isReferredByCurrentPage())
        {
            resetOptions();

            if (isInputVersion())
            {
                addOption(null, "");
            }

            populateDropdownOptions();
        }
    }

    public void pageSubmitEnd(PageEvent p)
    {
    }

    public void pageSubmitted(PageEvent p)
    {
    }

    /**
     * This method populates the dropdown list options
     */
    public void populateDropdownOptions()
    {
        DBConnection connection = null;

        try
        {
            connection = DBConnection.getConnection(getPage().getApplicationName());

            java.sql.Statement s     = connection.createStatement();
            String             query = null;

            //fc 06/24/04 Added the following code contributed by ilev.

            /** added by ilev 2004/03 */
            String alias = "";

            if (_keyColumn.equals(_dispColumn))
            {
                alias = " AS DISPCOL";
            }

            /**
             * end added
             */
            if (getCriteria() != null)
            {
                //fc 06/24/04 Added the following code contributed by ilev.

                /**
                 * alias added by ilev 2004/03
                 */
                query = "SELECT " + _keyColumn + "," + _dispColumn + alias + " FROM " + _table + " WHERE " + getCriteria() + " ORDER BY ";

                if (isToUpper())
                {
                    query = query + "UPPER(" + _dispColumn + ")";
                }
                else
                {
                    query += _dispColumn;
                }
            }
            else
            {
                //fc 06/24/04 Added the following code contributed by ilev.

                /**
                 * alias added by ilev 2004/03
                 */
                query = "SELECT " + _keyColumn + "," + _dispColumn + alias + " FROM " + _table + " ORDER BY ";

                if (isToUpper())
                {
                    query = query + "UPPER(" + _dispColumn + ")";
                }
                else
                {
                    query += _dispColumn;
                }
            }

            java.sql.ResultSet r = s.executeQuery(query);

            int                cols    = r.getMetaData().getColumnCount();
            String             key;
            String             display;

            if (r.next())
            {
                do
                {
                    key     = "";
                    display = "";

                    if (isTrimResults())
                    {
                        key = r.getObject(1).toString().trim();

                        for (int i = 2; i <= cols; i++)
                        {
                            display = display + r.getObject(i).toString().trim() + " ";
                        }

                        addOption(key, display);
                    }
                    else
                    {
                        key = r.getObject(1).toString();

                        for (int i = 2; i <= cols; i++)
                        {
                            display = display + r.getObject(i).toString() + " ";
                        }

                        addOption(key, display);
                    }
                }
                while (r.next());
            }

            r.close();
            s.close();
        }
        catch (java.sql.SQLException se)
        {
            MessageLog.writeErrorMessage("initialize", se, null);
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("initialize", e, this);
        }
        finally
        {
            if (connection != null)
            {
                connection.freeConnection();
            }
        }
    }

    public boolean processParms(Hashtable parms, int rowNo)
                         throws Exception
    {
        // fc: 07/17/02 Commented out the below lines as they are no longer required,
        //        since a better approach is to check to see if the field is contained in
        //        the form when submitted. see other change below
        //        if (!getVisible() || !getEnabled())
        //            return false;
        Object oldValue = _value;

        String name = getFullName();

        if (rowNo > -1)
        {
            name += ("_" + rowNo);

            if (_dsBuff != null)
            {
                try
                {
                    oldValue = _dsBuff.getAny(rowNo, _dsColNo);
                }
                catch (Exception e)
                {
                    oldValue = null;
                }
            }
        }
        else
        {
            if (_dsBuff != null)
            {
                try
                {
                    oldValue = _dsBuff.getAny(_dsColNo);
                }
                catch (Exception e)
                {
                    oldValue = null;
                }
            }
        }

        //fc: 07/17/02 Added check to see if component exists in form if not
        //             then do not process.
        if (parms.containsKey(name))
        {
            String val[] = (String[]) parms.get(name);

            if (val == null)
            {
                _value = null;
            }
            else
            {
                _value = val[0];

                if (_value.equals(""))
                {
                    _value = null;
                }
            }

            if (!valuesEqual(oldValue, _value))
            {
                String s = null;

                if (oldValue != null)
                {
                    s = oldValue.toString();
                }

                ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
                addEvent(e);
            }
        }

        return false;
    }

    /**
     * Use this method to remove an option from the list.
     *
     * @param index The index of the option to remove.
     */
    public void removeOption(int index)
    {
        if ((index < 0) || (index >= _optionsVec.size()))
        {
            return;
        }

        _optionsVec.removeElementAt(index);
    }

    public void removeOption(String optionText)
    {
        HtmlOption temp     = null;
        String     textTemp = null;

        for (int i = 0; i < _optionsVec.size(); i++)
        {
            temp     = (HtmlOption) _optionsVec.elementAt(i);
            textTemp = (String) temp.getDisplay();

            if (optionText.equals(textTemp))
            {
                _optionsVec.removeElementAt(i);

                break;
            }
        }
    }

    /**
     * This method removes all options from the component.
     */
    public void resetOptions()
    {
        _optionsVec.removeAllElements();
    }

    /**
	 * This method sets the ReloadDropDownInEveryPageRequest attribute
	 * @param  reloadDropDownInEveryPageRequest
	 * @uml.property  name="_reloadDropDownInEveryPageRequest"
	 */
    public void set_reloadDropDownInEveryPageRequest(boolean reloadDropDownInEveryPageRequest)
    {
        this._reloadDropDownInEveryPageRequest = reloadDropDownInEveryPageRequest;
    }

    /**
     * Claudio Pi (4-01-2003) This method sorts the dropdown list based on its options values in the default order direction.
     */
    public void sort()
    {
        _optionsVec.sort();
    }

    /**
     * Claudio Pi (4-01-2003) This method sorts the dropdown list based on its options values in the given order direction.
     *
     * @param dir direction in which the options will be sorted (0=Ascending or 1=Descending order)
     */
    public void sort(int dir)
    {
        _optionsVec.setSortDir(dir);
        _optionsVec.sort();
    }
}
