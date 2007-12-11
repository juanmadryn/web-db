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

import com.salmonllc.html.HtmlComponent;

import com.salmonllc.jsp.JspForm;
import com.salmonllc.jsp.TagWriter;

import com.salmonllc.util.MessageLog;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/FormTag.java $
//$Author: Srufle $
//$Revision: 9 $
//$Modtime: 6/11/04 2:09p $
/////////////////////////
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;


/**
 * This is a tag used to create a form tag.
 */
public class FormTag extends ContainerTag
{
    private String _action;
    private String _autoscroll;
    private String _content;
    private String _encType;
    private String _hiddenFields;
    private String _method;
    private String _onReset;
    private String _onSubmit;
    private String _target;

    /**
     * Sets the action for this form (The URL invoked when the form is posted)
     *
     * @param action
     */
    public void setAction(String action)
    {
        _action = action;
    }

    /**
     * Sets whether the form should auto scroll
     *
     * @param string
     */
    public void setAutoscroll(String string)
    {
        _autoscroll = string;
    }

    /**
     * Sets the mime type encoding of the data sent "application/x-www-form-urlencoded" (the default), is usually used if the METHOD attribute has the value POST. "multipart/form-data" is used when the form contains a file upload element (INPUT TYPE="FILE").
     *
     * @param encType
     */
    public void setEnctype(String encType)
    {
        _encType = encType;
    }

    /**
     * Sets the method for this form (GET or POST)
     *
     * @param method
     */
    public void setMethod(String method)
    {
        _method = method;
    }

    /**
     * Sets the javascript code that executes if the user resets the form
     *
     * @param onReset
     */
    public void setOnreset(String onReset)
    {
        _onReset = onReset;
    }

    /**
     * Sets the javascript code that executes if the user submits the form
     *
     * @param onSubmit
     */
    public void setOnsubmit(String onSubmit)
    {
        _onSubmit = onSubmit;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     *
     * @return
     */
    public int getTagConvertType()
    {
        return CONV_CUSTOM;
    }

    /**
     * Sets the window that will display the results of the submit
     *
     * @param target
     */
    public void setTarget(String target)
    {
        _target = target;
    }

    /**
     * This method creates the JspForm used by the tag.
     *
     * @return
     */
    public HtmlComponent createComponent()
    {
        JspForm form = new JspForm(getName(), getHelper().getController());

        if (!BaseTagHelper.isEmpty(_action))
        {
            form.setAction(_action);
        }

        if (!BaseTagHelper.isEmpty(_method))
        {
            form.setMethod(_method);
        }

        if (!BaseTagHelper.isEmpty(_onReset))
        {
            form.setOnReset(_onReset);
        }

        if (!BaseTagHelper.isEmpty(_onSubmit))
        {
            form.setOnSubmit(_onSubmit);
        }

        if (!BaseTagHelper.isEmpty(_target))
        {
            form.setTarget(_target);
        }

        if (!BaseTagHelper.isEmpty(_encType))
        {
            form.setEncType(_encType);
        }

        if (!BaseTagHelper.isEmpty(getVisible()))
        {
            form.setVisible(BaseTagHelper.stringToBoolean(getVisible()));
        }

        if (!BaseTagHelper.isEmpty(getEnabled()))
        {
            form.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
        }

        if (!BaseTagHelper.isEmpty(_autoscroll))
        {
            form.setAutoScrollEnabled(BaseTagHelper.stringToBoolean(_autoscroll));
        }

        return form;
    }

    /**
     * This is part of the JSP tag specification
     *
     * @return
     *
     * @throws JspException
     */
    public int doEndTag() throws JspException
    {
        getHelper().getTagContext().setRefIndexPrinted(false);

        return super.doEndTag();
    }

    /**
     * This is part of the JSP tag specification
     *
     * @return
     *
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        getHelper().getTagContext().setRefIndexPrinted(false);

        return super.doStartTag();
    }

    /**
     * This method generates the html used by the tag.
     *
     * @param p
     *
     * @throws IOException
     */
    public void generateComponentHTML(JspWriter p) throws IOException
    {
        JspForm form = (JspForm) getHelper().getController().getComponent(getName());

        if (form == null)
        {
            return;
        }

        TagWriter w = getTagWriter();
        w.setWriter(p);

        form.generateHTML(w, _content);
    }

    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output
     * stream.
     *
     * @return
     */
    public boolean incrementMode()
    {
        try
        {
            _content = getBodyContentData(true);

            if ((_hiddenFields != null) && (_hiddenFields.length() > 0))
            {
                _content = _hiddenFields + _content;
            }
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
        }

        return false;
    }

    /**
     * Initializes the tag
     */
    public void initMode()
    {
        _content      = null;
        _hiddenFields = generateFormHiddenFieldHtml();
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _action       = null;
        _method       = null;
        _onReset      = null;
        _onSubmit     = null;
        _target       = null;
        _encType      = null;
        _content      = null;
        _hiddenFields = null;
        _autoscroll   = null;
    }
}
