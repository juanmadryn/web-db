//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.sql;


/**
 * Event object that gets passed to ModelChangedListeners when data in a model changes.
 */
public class ModelChangedEvent extends java.awt.AWTEvent{

	public static final int TYPE_ROW_FOCUS_CHANGED = 0;
	public static final int TYPE_DATA_CHANGED = 1;
	public static final int TYPE_ROW_INSERTED_OR_DELETED = 2;
	public static final int TYPE_DATA_SORTED = 3;
	public static final int TYPE_DATA_FILTERED = 4;
	public static final int TYPE_DATA_LOADED = 5;
	public static final int TYPE_RESET = 6;
	public static final int TYPE_DATA_SORTING = 7;
	
	private DataStoreBuffer _ds;
	private int _type;
	private int _oldRow;
	private int _newRow;
	private Object _oldData;
	private Object _newData;
	private int _colNo = -1;
	
	/**
	 * Creates a row focused changed event
	 */
	public ModelChangedEvent(DataStoreBuffer ds, int oldRow, int newRow) {
		super(ds,0);
		_ds = ds;
		_type = TYPE_ROW_FOCUS_CHANGED;
		_oldRow=oldRow;
		_newRow=newRow;
	}

	/**
	 * Creates a data changed event
	 */
	public ModelChangedEvent(DataStoreBuffer ds, int colNo, Object oldData, Object newData) {
		super(ds,0);
		_ds = ds;
		_type = TYPE_DATA_CHANGED;
		_oldData = oldData;
		_newData = newData;
		_colNo = colNo;
	}
	
	/**
	 * For an insert or delete row event
	 */
	public ModelChangedEvent(DataStoreBuffer ds, int newRow) {
		super(ds,0);
		_ds = ds;	
		_type = TYPE_ROW_INSERTED_OR_DELETED;
		_newRow = newRow;
	}	

	/**
	 * For an sort,filter or load event
	 */
	public ModelChangedEvent(int type,DataStoreBuffer ds) {
		super(ds,0);
		_ds = ds;	
		_type = type;
	}	
	
	
	/**
	 * Returns the DataStore that generated the event.
	 * @return DataStoreBuffer
	 */
	public DataStoreBuffer getDataStore() {
		return _ds;
	}

	/**
	 * For Row Focus Changed Event, Returns the new row.
	 * @return int
	 */
	public int getNewRow() {
		return _newRow;
	}

	/**
	 * For Row Focus Changed Event, Returns the old row.
	 * @return int
	 */
	public int getOldRow() {
		return _oldRow;
	}

	/**
	 * Returns the type of event.
	 * @return int
	 */
	public int getType() {
		return _type;
	}

	/**
	 * Returns the new data for a data changed event.
	 * @return Object
	 */
	public Object getNewData() {
		return _newData;
	}

	/**
	 * Returns the old data for a data changed event.
	 * @return Object
	 */
	public Object getOldData() {
		return _oldData;
	}

	/**
	 * Returns the col number changed for a data changed event.
	 * @return int
	 */
	public int getColNo() {
		return _colNo;
	}
	
	/**
	 * Returns true if the event caused the whole table to change or false if only a part changed
	 */
	public boolean isAllDataChanged() {
		return _type >= 3;	
	}	

}
