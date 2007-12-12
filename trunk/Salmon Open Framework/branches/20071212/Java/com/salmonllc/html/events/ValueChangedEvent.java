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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/events/ValueChangedEvent.java $
//$Author: Dan $ 
//$Revision: 12 $ 
//$Modtime: 11/03/04 7:04a $ 
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.sql.*;

/**
 * This object will be created and passed to every value changed event method.
 * @see  ValueChangedListener
 */

public class ValueChangedEvent extends java.awt.AWTEvent {
	HtmlPage _page;
	HtmlComponent _comp;
	String _name;
	String _fullName;
	String _oldValue;
	String _newValue;
	int _row;
	int _column;
	DataStoreBuffer _ds;
	boolean _acceptValue = true;

    public static final int PROCESSING_NOTENTERED=-1;
    public static final int PROCESSING_DISCARD_CHANGE=1;
    public static final int PROCESSING_KEEP_CHANGE_IN_QUEUE=2;
    public static final int PROCESSING_COMMIT_CHANGE=3;
    public static final int PROCESSING_MOVE_CHANGE_TO_TEMP=4;

    int _processingInstruction = PROCESSING_NOTENTERED;

	/**
	 * This method was created in VisualAge.
	 * @param page com.salmonllc.html.HtmlPage
	 * @param comp com.salmonllc.html.HtmlComponent
	 * @param name java.lang.String
	 * @param fullName java.lang.String
	 */
	public ValueChangedEvent(HtmlPage page, HtmlComponent comp, String name, String fullName, String oldValue, String newValue, int row, int column, DataStoreBuffer ds) {
		super(comp, 0);
		_page = page;
		_comp = comp;
		_name = name;
		_fullName = fullName;
		_oldValue = oldValue;
		_newValue = newValue;
		_row = row;
		_column = column;
		_ds = ds;
	}
	/**
	 * Returns whether or not the changes to the column will be accepted.
	 */
	public boolean getAcceptValue() {
		return _acceptValue;
	}
    /**
     * Set this value to false to reject the data value and true to accept it.
     * @deprecated you should now use the {@link #setAcceptValue(int inst)} method instead
     */
    public void setAcceptValue(boolean accept) {
        _acceptValue = accept;
    }
    /**
     * Use this method to inform the framework how to treat the change
     * @param inst Valid values are:
     *  PROCESSING_DISCARD_CHANGE  - discard the change
     *  PROCESSING_KEEP_CHANGE_IN_QUEUE - don't copy the change to the datastore, but keep it temporarily so it appears on the next page request
     *  PROCESSING_COMMIT_CHANGE - copy the change to the datastore
     */
    public void setAcceptValue(int inst){
       _processingInstruction = inst;
    }

    /**
     * Returns the accept value as an integer
     * @see #setAcceptValue(int inst)
     */
    public int getAcceptValueInt() {
        return _processingInstruction;
    }
	/**
	 * This method returns the column number in the data store that had a value changed.
	 */
	public int getColumn() {
		return _column;
	}
	/**
	 * This method returns the component that the value was changed for.
	 */
	public HtmlComponent getComponent() {
		return _comp;
	}
	/**
	 * This method returns the data store buffer that had a value changed.
	 */
	public DataStoreBuffer getDataStore() {
		return _ds;
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
	 * This method returns the new value for the component
	 */
	public String getNewValue() {
		return _newValue;
	}
	/**
	 * This method sets the new value for the component
	 */
	public void setNewValue(String newValue) {
		_newValue = newValue;
	}
	/**
	 * This method returns the old value for the component
	 */
	public String getOldValue() {
		return _oldValue;
	}
	/**
	 * This method returns the page for which the submit was performed.
	 */
	public HtmlPage getPage() {
		return _page;
	}
	/**
	 * This method returns the row number in the data store that had a value changed.
	 */
	public int getRow() {
		return _row;
	}

	/**
	 * Returns the component that submitted the page
	 */
	public HtmlComponent getSubmitComponent() {
		return _page.getSubmitComponent();
	}
}
