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
package com.salmonllc.jsp.controller;

/////////////////////////
//$Archive: /sofia/sourcecode/com/salmonllc/jsp/controller/BaseDetailController.java $
//$Author: Deepak $
//$Revision: 24 $
//$Modtime: 6/16/03 10:37a $
/////////////////////////

import com.salmonllc.forms.DetailForm;
import com.salmonllc.forms.DetailFormListener;
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlText;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;

/**
 * Licensed Material - Property of Salmon LLC
 * (C) Copyright Salmon LLC. 1999 - All Rights Reserved
 * For more information go to www.salmonllc.com
 *
 * *************************************************************************
 * DISCLAIMER:
 * The following code has been created by Salmon LLC. The code is provided
 * 'AS IS' , without warranty of any kind unless covered in another agreement
 * between your corporation and Salmon LLC.  Salmon LLC shall not be liable
 * for any damages arising out of your use of this, even if they have been
 * advised of the possibility of such damages.
 * *************************************************************************
 *
 *
 */

/**
 * Licensed Material - Property of Salmon LLC (C) Copyright Salmon LLC. 1999 - All Rights Reserved For more information go to www.salmonllc.com DISCLAIMER: The following code has been created by Salmon LLC. The code is provided 'AS IS' , without warranty of any kind unless covered in another agreement between your corporation and Salmon LLC.  Salmon LLC shall not be liable for any damages arising out of your use of this, even if they have been advised of the possibility of such damages.
 */
public class BaseDetailController extends JspController implements DetailFormListener, PageListener, SubmitListener
{
    public com.salmonllc.jsp.JspContainer _mainCont;

    // heading
    public HtmlText _messageText;

    protected DetailForm _detailForm;
    protected DataStore _ds;

    private java.util.Hashtable _hashUserComponents = new java.util.Hashtable();
    public com.salmonllc.html.HtmlComponent fieldMessageComponent;
    /**
     * This method is used if component in detail form is needed to be mapped to any other component
     * Creation date: (8/16/01 5:02:34 PM)
     * @return com.salmonllc.html.HtmlComponent
     * @param compName java.lang.String
     */
    public HtmlComponent assignComponentToUserComp(String compName) {
        if(getUserComponents() == null)
            return null;
        else
            return (HtmlComponent)getUserComponents().get(compName);
    }
    /**
     *	This method checks the HTML form component passed into it to see the form field has been filled out and is valid.
     *	@param comp HtmlFormComponent
     *	@param itemName java.lang.String
     * @return boolean true
     */

    public boolean checkComponent(HtmlFormComponent comp, String itemName)
    {
        // call isValid for all components
        String preMess = "Please enter a valid value for ";
        if (Util.isFilled(comp.getValue()))
        {
            if (!comp.isValid())
            {
                displayError(preMess + itemName, getMessageComponent());
                return false;
            }
        }

        return true;
    }
    /**
     *	This method calls the displayMessage method and passes the error message to it.
     *	@param message java.lang.String
     */
    public void displayError(String message) {
        displayMessage(message, true, null, -1);
    }
    /**
     *	This method calls the displayMessage method and passes the error message to it.
     * 	@param message java.lang.String
     * 	@param focus com.salmonllc.html.HtmlComponent
     */
    public void displayError(String message, HtmlComponent focus) {
        displayMessage(message, true, focus, -1);
    }
    /**
     *	This method calls the displayMessage method and passes the error message to it.
     * 	@param message java.lang.String
     * 	@param focus com.salmonllc.html.HtmlComponent
     *	@param row int
     */
    public void displayError(String message, HtmlComponent focus, int row) {
        displayMessage(message, true, focus, row);
    }
    /**
     *	This method calls an overloaded version of the displayMessage method and passes the message to it.
     *	@param message java.lang.String
     */
    public void displayMessage(String message) {
        displayMessage(message, false, null, -1);
    }
    /**
     *	This method calls an overloaded version of the displayMessage method and passes the message to it.
     * 	@param focus com.salmonllc.html.HtmlComponent
     */
    public void displayMessage(String message, HtmlComponent focus) {
        displayMessage(message, false, focus, -1);
    }
    /**
     *	This method is used to display a message in the descendant class/page.
     * 	@param message java.lang.String
     * 	@param focus com.salmonllc.html.HtmlComponent
     * 	@param row int
     */
    public void displayMessage(String message, boolean isError, HtmlComponent focus, int row) {

        HtmlComponent messageComp = getMessageComponent();

        if(_detailForm.getErrorMessageComp() == null)
        {
            _detailForm.setErrorMessageComp((HtmlText)messageComp);
        }

        _detailForm.getErrorMessageComp().setVisible(true);

        if (message == null) {
            _detailForm.getErrorMessageComp().setText(" ");
        } else {
            _detailForm.getErrorMessageComp().setText(message);
            _detailForm.getErrorMessageComp().setFont(isError ? HtmlText.FONT_ERROR : HtmlText.FONT_TEXT_EDIT);
        }

        if(focus != null && (focus instanceof HtmlFormComponent) )
        {
            (focus).setVisible(true);
            if (row > -1)
                ((HtmlFormComponent)focus).setFocus(row);
            else
                ((HtmlFormComponent)focus).setFocus();
        }
    }

    /**
     * Creation date: (10/26/01 5:17:41 AM)
     * @return com.salmonllc.html.HtmlComponent
     */
    protected com.salmonllc.html.HtmlComponent getMessageComponent() {
        return fieldMessageComponent;
    }
    /**
     * Insert the method's description here.
     * Creation date: (8/2/01 10:16:09 AM)
     * @return java.lang.String
     */
    protected String getPrimaryKey(DataStore _ds) throws com.salmonllc.sql.DataStoreException
    {
        int colCount = _ds.getColumnCount();
        String s = null;
        for (int i = 0; i < colCount; i++)
        {
            if (!_ds.isPrimaryKey(i))
                continue;
            s = _ds.getColumnName(i);
        }

        return s;
    }
    /**
     * Insert the method's description here.
     * Creation date: (8/2/01 10:16:09 AM)
     * @return java.lang.String
     */
    protected String getPrimaryKeyName(DataStore _ds) throws com.salmonllc.sql.DataStoreException
    {
        int colCount = _ds.getColumnCount();
        String s = null;
        for (int i = 0; i < colCount; i++)
        {
            if (!_ds.isPrimaryKey(i))
                continue;
            s = _ds.getColumnName(i);
        }

        if(s != null && s.indexOf(".") != -1)
            s = s.substring(s.indexOf(".") + 1);

        return s;
    }
    /**
     * Gets all the User Components specified in Detail Form as a hastable
     * Creation date: (8/16/01 4:24:49 PM)
     * @return java.util.Hashtable
     */
    public java.util.Hashtable getUserComponents() {
        return _hashUserComponents;
    }
    /**
     *	This method creates the list form for maintaining brokers
     *
     */
    public void initialize() throws Exception
    {
        super.initialize();
        try
        {
            _messageText = new HtmlText("", this);
            this.add(_messageText);
            addPageListener(this);
        }
        catch(Exception e)
        {
            writeMessage("Exception occured in initailize method of class " + this.getClass().getName());
        }
    }
    /**
     * Called when an error is encountered.
     * @return int	If true, continue processing the event.  Same convention as in
     *	ValueChangedEvent.valueChanged(), SubmitEvent.submitPerformed(), etc.
     */
    public boolean onDetailError(int code, java.lang.String message, com.salmonllc.html.HtmlComponent component)
    {
        displayError(message, component);
        return false;
    }
    /**
     *	This method verifies page access by returning TRUE.
     *	@return boolean
     */

    public boolean pageAccess() {
        return (true);
    }
    /**
     * Called before datastore retrieval.
     */
    public void pageRequested(PageEvent p) throws Exception
    {
        request(p);
    }
    /**
     * 	This method/event will get fired each time a page is requested by the browser.
     *	@param p PageEvent
     *	@throws Exception
     */
    public void pageRequestEnd(PageEvent p) throws Exception
    {
        //writeMessage(getClassName() + ".pageRequestEnd()");
        //displayMessage(null);
    }
    /**
     * 	This method occurs each time a page is sumbitted.
     *	@param p PageEvent
     */
    public void pageSubmitEnd(PageEvent p)
    {
        //writeMessage(getClassName() + ".pageSubmitEnd()");
    }
    /**
     *	This method is called after a page has been submitted.  It sets a URL for the user to be re-directed to after the page has been submitted.
     *	@param p PageEvent
     */

    public void pageSubmitted(PageEvent p)
    {

    }
    /**
     * Called before Delete button is processed.
     * @return boolean	True if processing is to continue.
     */
    public boolean postDetailDelete() throws java.lang.Exception {
        return true;
    }
    /**
     * Called after pageRequested is processed.
     * @return boolean	Currently undefined.
     */
    public boolean postDetailRequest() throws java.lang.Exception
    {

        return false;
    }
    /**
     * Called before Save button is processed on Detail page.
     * @return boolean	True if continue to save, else do not continue.
     */
    public boolean postDetailSave() throws java.lang.Exception
    {

        return true;
    }
    /**
     * Called before Delete button is processed.
     * @return boolean	True if processing is to continue.
     */
    public boolean preDetailDelete() throws java.lang.Exception {
        return true;
    }
    /**
     * Called before pageRequested() is processed.
     * @return boolean	True if continue processing, else stop processing.
     */
    public boolean preDetailRequest() throws java.lang.Exception
    {

        return true;
    }
    /**
     * Called before Save button is processed on Detail page.
     * @return boolean	True if continue to save, else do not continue.
     */
    public boolean preDetailSave() throws java.lang.Exception
    {
        java.util.Hashtable hashComp = getUserComponents();
        java.util.Enumeration enumera = hashComp.elements();

        while(enumera.hasMoreElements())
        {
            HtmlComponent comp = (HtmlComponent)enumera.nextElement();

            if(comp instanceof HtmlFormComponent)
            {
                if(!checkComponent((HtmlFormComponent)comp, comp.getName()))
                {
                    break;
                }
            }
        }
        return true;
    }
    /**
     *	This method is overridden in the descendant class/page.  It is used each time a user re-visits a page after the initial page generation by the initPage and createPage methods.
     *	@param p PageEvent
     *	@throws Exception
     */

    public void request(PageEvent p) throws Exception
    {
    }
    /**
     * Sets the message component
     * Creation date: (10/26/01 5:17:41 AM)
     * @param newMessageComponent com.salmonllc.html.HtmlComponent
     */
    public void setMessageComponent(com.salmonllc.html.HtmlComponent newMessageComponent) {
        fieldMessageComponent = newMessageComponent;
    }
    /**
     * Creation date: (8/16/01 4:24:49 PM)
     * @param newUserComponents java.util.Hashtable
     */
    public void setUserComponents(java.util.Hashtable newUserComponents) {
        _hashUserComponents = newUserComponents;
    }
    /**
     *	This method is used to write a debug message to the Message Log in the descendant class/page.
     *	The message written to the log is the String value passed into this method.
     *	@param message java.lang.String
     */

    public void writeMessage(String message) {
        MessageLog.writeDebugMessage("" + message, this);
    }

    /**
     * Gets all the User Components specified in Detail Form as a hastable
     * Creation date: (8/16/01 4:24:49 PM)
     * @return java.util.Hashtable
     */
    public HtmlComponent getHtmlComponent(String name) {
        if(name != null)
            return (HtmlComponent)getUserComponents().get(name);
        return null;
    }

    public boolean submitPerformed(SubmitEvent e) throws Exception
    {
        return true;
    }
}
