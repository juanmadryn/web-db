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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/tags/InputTag.java $
//$Author: Srufle $
//$Revision: 5 $
//$Modtime: 4/15/03 10:25a $
////////////////////////////////////////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseEmptyTag;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.wml.WmlPasswordEdit;
import com.salmonllc.wml.WmlTextEdit;

/**
 * This tag approximately corresponds to the WML tag "INPUT"
 */

public class InputTag extends BaseEmptyTag
{

    private String _enabled;


    private String _maxLength;
    private String _size;

    private String _type;

    private String _value;

    private String _dataSource;
    private String _class;
    private String _format;
    private String _emptyok;
    private String _tabindex;
    private String _title;



    /**
     * Creates the component to be used by the tag.
     */
    public HtmlComponent createComponent()
    {
        if (_type == null)
            return null;

        String type = _type.toUpperCase();
        if (type.equals("PASSWORD"))
        {
            WmlPasswordEdit pass = new WmlPasswordEdit(getName(), getHelper().getController());
            pass.setSize(BaseTagHelper.stringToInt(_size,0));
            pass.setMaxLength(BaseTagHelper.stringToInt(_maxLength,0));
            pass.setValue(_value);
            pass.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
            if (_dataSource != null)
                pass.setDataSource(_dataSource);
            pass.setClassName(_class);
            pass.setFormat(_format);
            pass.setEmptyOk(BaseTagHelper.stringToBoolean(_emptyok, false));
            pass.setTabIndex(BaseTagHelper.stringToInt(_tabindex));
            pass.setTitle(_title);
            CardTag cTag=(CardTag)findAncestorWithClass(this,CardTag.class);
            cTag.getCard().addInputComponent(pass);
            return pass;
        }
        else if (type.equals("TEXT"))
        {
            WmlTextEdit edit = new WmlTextEdit(getName(), getHelper().getController());
            edit.setSize(BaseTagHelper.stringToInt(_size));
            edit.setMaxLength(BaseTagHelper.stringToInt(_maxLength));
            edit.setValue(_value);
            edit.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
            if (_dataSource != null)
                edit.setDataSource(_dataSource);

            edit.setClassName(_class);
            edit.setFormat(_format);
            edit.setEmptyOk(BaseTagHelper.stringToBoolean(_emptyok, false));
            edit.setTabIndex(BaseTagHelper.stringToInt(_tabindex));
            edit.setTitle(_title);
            CardTag cTag=(CardTag)findAncestorWithClass(this,CardTag.class);
            cTag.getCard().addInputComponent(edit);
            return edit;
        }
      return null;
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
     * Returns the length of the field
     */
    public String getLength()
    {
        return _maxLength;
    }

    /**
     * Set the tag's maxlength attribute
     */
    public String getMaxlength()
    {
        return _maxLength;
    }




    /**
     * Get the tag's type attribute
     */
    public String getType()
    {
        return _type;
    }

    /**
     * Get the tag's value attribute
     */
    public String getValue()
    {
        return _value;
    }


    /**
     * Get the tag's format attribute
     */
    public String getFormat()
    {
        return _format;
    }

    /**
     * Get the tag's emptyok attribute
     */
    public String getEmptyok()
    {
        return _emptyok;
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
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _maxLength = null;
        _size = null;
        _type = null;
        _value = null;
        _emptyok = null;
        _format = null;
        _dataSource = null;
        _tabindex = null;
        _class = null;
        _dataSource = null;
        _enabled = null;
        _size = null;
        _title = null;
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
     * Sets the length of the field
     */
    public void setLength(String newLength)
    {
        _maxLength = newLength;
    }

    /**
     * Set the tag's maxLength attribute
     */
    public void setMaxlength(String maxLength)
    {
        _maxLength = maxLength;
    }




    /**
     * Set the tag's size attribute
     */
    public void setSize(String size)
    {
        _size = size;
    }


    /**
     * Set the tag's type attribute
     */

    public void setType(String type)
    {
        _type = type;
    }

    /**
     * Set the tag's value attribute
     */

    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * Set the tag's format attribute
     */

    public void setFormat(String _format) {
        this._format = _format;
    }

    /**
     * Set the tag's emptyok attribute
     */
    public void setEmptyok(String _emptyok) {
        this._emptyok = _emptyok;
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

}
