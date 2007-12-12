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
package com.salmonllc.wml.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/tags/DoTag.java $
//$Author: Srufle $
//$Revision: 6 $
//$Modtime: 4/15/03 3:11p $
/////////////////////////

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.jsp.tags.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.wml.WmlDo;

/**
 * This is a tag used to create a Do Tag in WML.
 */

public class DoTag extends ContainerTag
{
    private String _box;
    private String _href;
    private String _class;
    private String _dataSource;
    private String _sendreferer;
    private String _method;
    private String _acceptcharset;
    private String _hrefparameters;
    private String _postfields;
    private String _type;
    private String _label;
    private String _optional;
    private String _doprev;


    /**
     * Get the tag's postfields attribute
     */
    public String getPostfields() {
        return _postfields;
    }

    /**
     * Set the tag's postfields attribute
     */
    public void setPostfields(String _postfields) {
        this._postfields = _postfields;
    }

    /**
     * Get the tag's type attribute
     */
    public String getType() {
        return _type;
    }

    /**
     * Set the tag's type attribute
     */
    public void setType(String _type) {
        this._type = _type;
    }

    /**
     * Get the tag's label attribute
     */
    public String getLabel() {
        return _label;
    }

    /**
     * Set the tag's label attribute
     */
    public void setLabel(String _label) {
        this._label = _label;
    }

    /**
     * Get the tag's optional attribute
     */
    public String getOptional() {
        return _optional;
    }

    /**
     * Set the tag's optional attribute
     */
    public void setOptional(String _optional) {
        this._optional = _optional;
    }

    /**
     * Get the tag's doprev attribute
     */
    public String getDoprev() {
        return _doprev;
    }

    /**
     * Set the tag's doprev attribute
     */
    public void setDoprev(String _doprev) {
        this._doprev = _doprev;
    }

    /**
     * This method creates the WmlDo used by the tag.
     */

    public HtmlComponent createComponent()
    {
        WmlDo wDo = new WmlDo(getName(), _href, getHelper().getController());
        CardTag cTag=(CardTag)findAncestorWithClass(this,CardTag.class);
        wDo.setCard(cTag.getCard());
        if (_sendreferer != null)
           wDo.setSendReferer(new Boolean(_sendreferer).booleanValue());
        if (getClassname() != null)
            wDo.setClassName(getClassname());
        if (_dataSource != null)
            wDo.setDataSource(_dataSource);
        if (_method != null)
            wDo.setMethod(_method);
        if (_acceptcharset != null)
            wDo.setAcceptCharset(_acceptcharset);
        if (_hrefparameters != null)
           wDo.setHrefParameters(new Boolean(_hrefparameters).booleanValue());
        if (_type != null)
            wDo.setType(_type);
        if (_label != null)
            wDo.setLabel(_label);
        if (_optional != null)
           wDo.setOptional(new Boolean(_optional).booleanValue());
        if (_doprev != null)
           wDo.setDoPrev(new Boolean(_doprev).booleanValue());
        if (_postfields != null)
           wDo.setPostFields(new Boolean(_postfields).booleanValue());
        return wDo;
    }
    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        WmlDo wDo = (WmlDo) getHelper().getController().getComponent(getName());
        if (wDo == null)
            return;
        HtmlComponent comp = getHelper().getComponent();
        int startRow = -1;

        DataTableTag dt = getHelper().getDataTableTag();
        if (dt != null)
        {
            startRow = dt.getStartRow();
        }
        else
        {
            ListTag l = getHelper().getListTag();
            if (l != null)
            {
                startRow = l.getRow();
            }
        }
        if (comp != null)
        {
            TagWriter w = getTagWriter();
            w.setWriter(p);
            wDo.generateHTML(w, _box, startRow);
        }
    }
    /**
     * Get the tag's method attribute
     */
    public String getMethod()
    {
        return _method;
    }
    /**
     * Get the tag's acceptcharset attribute
     */
    public String getAcceptcharset()
    {
        return _acceptcharset;
    }
    /**
     * Get the tag's hrefparameters attribute
     */
    public String getHrefparameters()
    {
        return _hrefparameters;
    }
    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }
    /**
     * Get the tag's Href attribute
     */
    public String getHref()
    {
        return _href;
    }
    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }
    /**
     * Get the tag's sendreferer attribute
     */
    public String getSendreferer()
    {
        return _sendreferer;
    }
    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode()
    {
        try
        {
            _box = getBodyContentData(true);
            return false;
        }

        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
            return false;
        }
    }
    /**
     * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
     */
    public void initMode()
    {
        _box = null;
    }
    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _href = null;
        _box = null;
        _method = null;
        _sendreferer = null;
        _acceptcharset = null;
        _hrefparameters = null;
        _type = null;
        _label = null;
        _optional = null;
        _doprev = null;
        _postfields=null;
        _class = null;
        _dataSource = null;
    }
    /**
     * Set the method attribute.
     */
    public void setMethod(String val)
    {
        _method = val;
    }
    /**
     * Set the hrefparameters attribute.
     */
    public void setHrefparameters(String val)
    {
        _hrefparameters = val;
    }
    /**
     * Set the acceptcharset attribute.
     */
    public void setAcceptcharset(String val)
    {
        _acceptcharset = val;
    }
    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }
    /**
     * Sets the tag's href  attribute
     */
    public void setHref(String href)
    {
        _href = href;
    }
    /**
     * Sets the tag's sendreferer attribute
     */
    public void setSendreferer(String val)
    {
        _sendreferer = val;
    }
}
