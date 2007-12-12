//** Copyright Statement ***************************************************
// Licensed Material - Property of Salmon LLC
// (C) Copyright Salmon LLC. 1999 - All Rights Reserved
// For more information go to www.salmonllc.com
//
// *************************************************************************
// DISCLAIMER:
// The following code has been created by Salmon LLC. The code is provided
// 'AS IS' , without warranty of any kind unless covered in another agreement
// between your corporation and Salmon LLC.  Salmon LLC shall not be liable
// for any damages arising out of your use of this, even if they have been
// advised of the possibility of such damages.
//** End Copyright Statement ***************************************************
package com.salmonllc.wml.tags;

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/tags/OptionTag.java $
//$Author: Srufle $
//$Revision: 5 $
//$Modtime: 4/15/03 2:25p $
////////////////////////////////////////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.ContainerTag;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import com.salmonllc.wml.WmlOption;

/**
 * The option tag represents the options of a WML Select tag.
 */
public class OptionTag extends ContainerTag
{
    private String _value;
    private String _table;
    private String _keycolumn;
    private String _displaycolumn;
    private String _nulloption;
    private String _toupper;
    private String _title;
    private String _onpick;
    private String _criteria;
    private String _class;
    private String _box;
    private String _datasource;
    private WmlOption _wo;

    public String getCriteria() {
        return _criteria;
    }

    public void setCriteria(String _criteria) {
        this._criteria = _criteria;
    }

    /**
     * There is no component for an option. It adds the option to it's parent
     * @return HtmlComponent
     */
    public HtmlComponent createComponent()
    {
        String table = null;
        SelectTag st=(SelectTag)findAncestorWithClass(this,SelectTag.class);
        table = getTable();

            if (!Util.isNull(table) && !Util.isEmpty(table))
            {
                /**
                 *  initialize(
                 *  String table,
                 *  String keyColumn,
                 *  String dispColumn,
                 *  String criteria,
                 *  boolean inputVersion,
                 *  boolean trimResults,
                 *  boolean toUpper)
                 **
                 * we are passing no criteria if you need this functionality
                 * you should probably make a custom application tag
                 */
                st.getWmlSelect().initialize(table,
                        getKeycolumn(),
                        getDisplaycolumn(),
                        _criteria,
                        Util.stringToBoolean(getNulloption(), false),
                        true,
                        Util.stringToBoolean(getToupper(), false));
                st.getWmlSelect().setHrefOnPick(_onpick);
                st.getWmlSelect().setDataSource(_datasource);

            }
            else
            {
                st.getWmlSelect().addOption(_wo=new WmlOption(getName(),_value,_title,_onpick,getHelper().getController()));
                _wo.setClassName(_class);
            }

        return null;
    }

    /**
     * This method generates the html used by the tag.
     */


    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode()
    {
        try
        {
                _box = getBodyContentData(true);
                if (_wo!=null)
                  _wo.setBodyContent(_box);
            return false;
        }

        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return false;
        }
    }


    /**
     * Get the tag's key attribute
     * @return
     */

    public String getValue()
    {
        return _value;
    }

    /**
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _value = null;
        _table = null;
        _keycolumn = null;
        _displaycolumn = null;
        _nulloption = null;
        _toupper = null;
        _onpick = null;
        _title = null;
        _wo = null;
        _class = null;
        _box = null;
        _criteria = null;
        _datasource = null;
    }


    /**
     * Set the tag's value attribute
     * @param key
     */

    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * Get the tag's table attribute
     * @return
     */
    public String getTable()
    {
        return _table;
    }

    /**
     * Set the tag's table attribute
     * @param table
     */
    public void setTable(String table)
    {
        _table = table;
    }

    /**
     * Get the tag's keycolumn attribute
     * @return
     */
    public String getKeycolumn()
    {
        return _keycolumn;
    }

    /**
     * Set the tag's keycolumn attribute
     * @param keycolumn
     */
    public void setKeycolumn(String keycolumn)
    {
        _keycolumn = keycolumn;
    }

    /**
     * Get the tag's displaycolumn attribute
     * @return
     */
    public String getDisplaycolumn()
    {
        return _displaycolumn;
    }

    /**
     * Set the tag's displaycolumn attribute
     * @param displaycolumn
     */
    public void setDisplaycolumn(String displaycolumn)
    {
        _displaycolumn = displaycolumn;
    }

    /**
     * Get the tag's nulloption attribute
     * @return
     */
    public String getNulloption()
    {
        return _nulloption;
    }

    /**
     * Set the tag's nulloption attribute
     * @param nulloption
     */
    public void setNulloption(String nulloption)
    {
        _nulloption = nulloption;
    }

    /**
     *  Get the tag's toupper attribute
     * @return
     */
    public String getToupper()
    {
        return _toupper;
    }

    /**
     * Set the tag's toupper attribute
     * @param toupper
     */
    public void setToupper(String toupper)
    {
        _toupper = toupper;
    }

    /**
     *  Get the tag's title attribute
     * @return
     */
    public String geTitle() {
        return _title;
    }

    /**
     * Set the tag's title attribute
     * @param title
     */
    public void setTitle(String title) {
        this._title = title;
    }

    /**
     *  Get the tag's onpick attribute
     * @return
     */
    public String getOnpick() {
        return _onpick;
    }

    /**
     * Set the tag's onpick attribute
     * @param onpick
     */
    public void setOnpick(String onpick) {
        this._onpick = onpick;
    }

    /**
     *  Get the tag's classname attribute
     * @return
     */
    public String getClassname() {
        return _class;
    }

    /**
     * Set the tag's classname attribute
     * @param _class
     */
    public void setClassname(String _class) {
        this._class = _class;
    }

    /**
     *  Get the tag's datasource attribute
     * @return
     */
    public String getDatasource() {
        return _datasource;
    }

    /**
     * Set the tag's datasource attribute
     * @param datasource
     */
    public void setDatasource(String datasource) {
        this._datasource = datasource;
    }
}
