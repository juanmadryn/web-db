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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/events/SubmitEvent.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 6/12/03 1:06p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;

/**
 * This object will be created and passed to every submit event method.
 * @see  SubmitListener
 */

public class SubmitEvent extends java.awt.AWTEvent {
    HtmlPage _page;
    HtmlComponent _comp;
    String _name;
    String _fullName;
    int _rowNo = -1;
    Object _nextListener;
    boolean _validationFailed;

    public SubmitEvent(HtmlPage page, HtmlComponent comp, String name, String fullName) {
        super(comp, 0);
        _page = page;
        _comp = comp;
        _name = name;
        _fullName = fullName;
    }

    public SubmitEvent(HtmlPage page, HtmlComponent comp, String name, String fullName, int rowNo) {
        this(page, comp, name, fullName);
        _rowNo = rowNo;
    }

    /**
     * This method returns the component that sumbitted the page.
     */
    public HtmlComponent getComponent() {
        return _comp;
    }

    /**
     * This method returns the full name (name of component appended to the name of its containers) of the component that submitted the page.
     */
    public String getFullName() {
        return _fullName;
    }

    /**
     * This method returns the name of the component that submitted the page.
     */
    public String getName() {
        return _name;
    }

    /**
     * This method returns the page for which the submit was performed.
     */
    public HtmlPage getPage() {
        return _page;
    }

    /**
     * This method returns the row in the datastore for which the button was clicked.
     */
    public int getRow() {
        return _rowNo;
    }

    /**
     * Returns the next listener that will be notified with this submit event
     */
    public Object getNextListener() {
        return _nextListener;
    }

    /**
     * Sets the next listener that will be notified with this submit event
     */
    public void setNextListener(Object nextListener) {
        _nextListener = nextListener;
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public boolean isValidationFailed() {
        return _validationFailed;
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public void setValidationFailed(boolean validationFailed) {
        _validationFailed = validationFailed;
    }
}
