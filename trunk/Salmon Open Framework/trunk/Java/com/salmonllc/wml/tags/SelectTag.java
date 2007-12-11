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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/tags/SelectTag.java $
//$Author: Srufle $
//$Revision: 4 $
//$Modtime: 4/15/03 10:27a $
////////////////////////////////////////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.jsp.tags.ContainerTag;
import com.salmonllc.wml.WmlSelect;

/**
 * This tag approximately corresponds to the WML tag "SELECT"
 */

public class SelectTag extends ContainerTag
{

    private String _enabled;


    private String _multiple;

    private String _value;

    private String _dataSource;
    private String _class;
    private String _tabindex;
    private String _title;
    private String _iname;
    private String _ivalue;
    private WmlSelect _sel;


    /**
     * Creates the component to be used by the tag.
     */
    public HtmlComponent createComponent()
    {

            WmlSelect sel = new WmlSelect(getName(), getHelper().getController());
           sel.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
            sel.setMultiple(BaseTagHelper.stringToBoolean(_multiple, false));
            sel.setValue(_value);
            sel.setIname(_iname);
            sel.setIvalue(_ivalue);
            sel.setTabIndex(BaseTagHelper.stringToInt(_tabindex));
            if (_dataSource != null)
                sel.setDataSource(_dataSource);
            sel.setClassName(_class);
            CardTag cTag=(CardTag)findAncestorWithClass(this,CardTag.class);
            cTag.getCard().addInputComponent(sel);
            _sel=sel;
            return sel;
    }

    /**
     * get the WmlSelect object associated with this select tag
     */
    public WmlSelect getWmlSelect() {
        return _sel;
    }

    /**
     * get the tag's classname attribute
     */
    public String getClassname()
    {
        return _class;
    }


    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }


    /**
     * get the tag's enabled attribute
     */
    public String getEnabled()
    {
        return _enabled;
    }




    /**
     * Set the tag's multiple attribute
     */
    public String getMultiple()
    {
        return _multiple;
    }



    /**
     * Get the tag's value attribute
     */
    public String getValue()
    {
        return _value;
    }




    /**
     * Get the tag's tabindex attribute
     */
    public String getTabindex() {
        return _tabindex;
    }

    /**
     * Get the tag's title attribute
     */
    public String getTitle() {
        return _title;
    }

    /**
     * Get the tag's iname attribute
     */
    public String getIname() {
        return _iname;
    }

    /**
     * Get the tag's ivalue attribute
     */
    public String getIvalue() {
        return _ivalue;
    }

    /**
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _multiple = null;
        _value = null;
        _dataSource = null;
        _class = null;
        _tabindex = null;
        _title = null;
        _iname = null;
        _ivalue = null;
        _sel = null;
        _enabled = null;
    }


    /**
     * Set the tag's classname attribute
     */
    public void setClassname(String sClass)
    {
        _class = sClass;
    }


    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }


    /**
     * Set the tag's enabled attribute
     */
    public void setEnabled(String val)
    {
        _enabled = val;
    }




    /**
     * Set the tag's multiple attribute
     */
    public void setMultiple(String multiple)
    {
        _multiple = multiple;
    }





    /**
     * Set the tag's value attribute
     */

    public void setValue(String value)
    {
        _value = value;
    }


    /**
     * Set the tag's tabindex attribute
     */
    public void setTabindex(String _tabindex) {
        this._tabindex = _tabindex;
    }

    /**
     * Set the tag's title attribute
     */
    public void setTitle(String _title) {
        this._title = _title;
    }

    /**
     * Set the tag's iname attribute
     */
    public void setIname(String _iname) {
        this._iname = _iname;
    }

    /**
     * Set the tag's ivalue attribute
     */
    public void setIvalue(String _ivalue) {
        this._ivalue = _ivalue;
    }
}
