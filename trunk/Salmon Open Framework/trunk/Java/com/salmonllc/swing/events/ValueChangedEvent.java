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
package com.salmonllc.swing.events;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/swing/events/ValueChangedEvent.java $
//$Author: Dan $ 
//$Revision: 5 $ 
//$Modtime: 6/11/03 4:29p $ 
/////////////////////////

import com.salmonllc.sql.*;

/**
 * This object will be created and passed to every value changed event method.
 * @see  ValueChangedListener
 */

public class ValueChangedEvent extends java.awt.AWTEvent {
	String _oldValue;
	String _newValue;
	int _row;
	int _column;
	boolean _acceptValue = true;
    DataStoreBuffer _ds;

    public static final int PROCESSING_DISCARD_CHANGE=1;
    public static final int PROCESSING_MOVE_CHANGE_TO_TEMP=2;
    public static final int PROCESSING_COMMIT_CHANGE=3;

    public int _processingInstruction = PROCESSING_COMMIT_CHANGE;

	/**
	 * Constructs a new event
	 */
	public ValueChangedEvent(Object comp, String oldValue, String newValue, DataStoreBuffer ds, int row, int column) {
		super(comp, 0);
		_oldValue = oldValue;
		_newValue = newValue;
		_row = row;
		_column = column;
		_ds = ds;
        try {
            _processingInstruction = PROCESSING_COMMIT_CHANGE;
        	if (newValue != null && newValue.length() > 0) {
	            if (! _ds.isFormattedStringValid(column,newValue)) {
    	            _processingInstruction = PROCESSING_MOVE_CHANGE_TO_TEMP;
        	    }
        	}    
        } catch (DataStoreException ex) {}
	}

    /**
     * Use this method to inform the framework how to treat the change
     * @param inst Valid values are:
     *  PROCESSING_DISCARD_CHANGE  - discard the change
     *  PROCESSING_MOVE_CHANGE_TO_TEMP - Move the change to the temp area of the DataStore
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
	 * This method returns the data store buffer that had a value changed.
	 */
	public DataStoreBuffer getDataStore() {
		return _ds;
	}
	/**
	 * This method returns the new value for the component
	 */
	public String getNewValue() {
		return _newValue;
	}
	/**
	 * This method returns the old value for the component
	 */
	public String getOldValue() {
		return _oldValue;
	}
	/**
	 * This method returns the row number in the data store that had a value changed.
	 */
	public int getRow() {
		return _row;
	}

    /**
     * Sets the value for new value
     * @param newValue
     */
    public void setNewValue(String newValue) {
        _newValue = newValue;
    }

}
