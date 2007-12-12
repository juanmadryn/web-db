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

package com.salmonllc.swing.table;
import com.salmonllc.sql.*;
import com.salmonllc.swing.SOption;
import com.salmonllc.swing.STable;
import com.salmonllc.swing.events.ValueChangedEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;


/**
 * A Table Model for a JTable that allows for binding to a DataStore
 */
public class DataStoreTableModel extends AbstractTableModel implements ModelChangedListener, ListSelectionListener {

	DataStoreBuffer _ds;
	String _colNames[];
    boolean _isEditable[];
    STable _tab;
    boolean _clickSort=true;

	/**
	 * Constructor for DataStoreTableModel.
	 */
	public DataStoreTableModel(DataStoreBuffer ds, String colNames[]) {
		super();
		_ds = ds ;
		_colNames = colNames;
        _isEditable = new boolean[ds.getColumnCount()];
        _ds.addModelChangedListener(this);
	}
    /**
	 * Constructor for DataStoreTableModel.
	 */
    public DataStoreTableModel(DataStoreBuffer ds) {
		super();
		_ds = ds ;
		_colNames = new String[ds.getColumnCount()];
        _isEditable = new boolean[ds.getColumnCount()];
        try {
            for (int i = 0; i < ds.getColumnCount();i++) {
                _colNames[i] = ds.getColumnName(i);
            }
        } catch (Exception e) {}
        _ds.addModelChangedListener(this);
	}
	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return _ds.getRowCount();
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return _ds.getColumnCount();
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
            if (columnIndex == -1)
                return null;
            else
			    return _ds.getFormattedString(rowIndex,columnIndex);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		return _colNames[columnIndex];
	}


	/**
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)  {
        ValueChangedEvent e= generateValueChangedEvent(aValue,rowIndex,columnIndex);
        _tab.getHelper().notifyValueChangedListeners(e); 
	}

     /**
     * Part of the model changed listener interface, don't call this method directly
     */
    public void modelChanged(ModelChangedEvent evt) {
        if (evt.getType() != ModelChangedEvent.TYPE_ROW_FOCUS_CHANGED) {
            if (evt.isAllDataChanged()) {
                fireTableDataChanged();
                if (_tab.getAutoSelectCurrentRow() && _ds.getRow() != -1)
                     _tab.selectRow(_ds.getRow());
            }
            else {
                fireTableRowsUpdated(evt.getNewRow(),evt.getNewRow());
                _tab.revalidate();
                _tab.repaint();
            }
        }
        else if (_tab.getAutoSelectCurrentRow()) {
            _tab.selectRow(evt.getNewRow());
        }
    }

    /**
     *  Returns false.  This is the default implementation for all cells.
     *
     *  @param  rowIndex  the row being queried
     *  @param  columnIndex the column being queried
     *  @return false
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == -1)
            return false;
        else
            return _isEditable[columnIndex];
    }

    /**
     *  Indicates whether or not a column is editable
     */
    public void setColumnEditable(int columnIndex, boolean editable) {
        _isEditable[columnIndex] = editable;
    }

       /**
     *  Indicates whether or not a column is editable
     */
    public boolean isColumnEditable(int columnIndex) {
        if (columnIndex == -1 || columnIndex >= _isEditable.length)
            return false;
        return _isEditable[columnIndex];
    }
    /**
     * Sets the STable this model is for
     */
    public void setSTable(STable tab) {
        _tab = tab;
        _tab.getSelectionModel().addListSelectionListener(this);
    }


    ValueChangedEvent generateValueChangedEvent(Object val, int row,int col) {
        try {
            String value = null;
            if (val != null) {
                if (val instanceof SOption)
                    value = ((SOption) val).getKey();
                else
                    value = val.toString();
            }
            return new ValueChangedEvent(_tab,_ds.getFormattedString(row,col),value,_ds, row,col);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the DataStore
     */
    public DataStoreBuffer getDataStore() {
        return _ds;
    }
    
    /**
     * Part of the list selection interface
     */
    public void valueChanged(ListSelectionEvent e) {
    	if (_tab.getAutoSelectCurrentRow())
	        _ds.gotoRow(_tab.getSelectedRow());
    }

}
