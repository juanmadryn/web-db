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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/events/ValidateEvent.java $
//$Author: Dan $ 
//$Revision: 3 $ 
//$Modtime: 2/06/03 10:40a $ 
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.sql.*;

/**
 * This object will be created and passed to every value changed event method.
 * @see  ValueChangedListener
 */

public class ValidateEvent extends java.awt.AWTEvent {
	HtmlPage _page;
	HtmlValidatorText _val;
    String _name;
    String _fullName;
    DataStoreBuffer _ds;
    int _row;

	public ValidateEvent(HtmlPage page, HtmlValidatorText val, String name, String fullName, DataStoreBuffer ds) {
		super(val, 0);
		_page = page;
		_val = val;
		_name = name;
		_fullName = fullName;
		_ds = ds;
        _row = -1;
	}
    public ValidateEvent(HtmlPage page, HtmlValidatorText val, String name, String fullName, DataStoreBuffer ds, int row) {
        this(page,val,name,fullName,ds);
        _row=row;
    }
	/**
	 * This method returns the HtmlValidatorText component that the event is being fired for
	 */
	public HtmlValidatorText getValidatorText() {
		return _val;
	}
	/**
	 * This method returns the data store buffer for the HtmlValidatorText
	 */
	public DataStoreBuffer getDataStore() {
		return _ds;
	}
	/**
	 * This method returns the full name (name of component appended to the name of its containers) of the validation component
	 */
	public String getFullName() {
		return _fullName;
	}
	/**
	 * This method returns the name of the validation component
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
     * Returns the row being validated
     */
    public int getRow() {
        return _row;
    }
}

