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

package com.salmonllc.swing;


import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.swing.events.ValueChangedEvent;
import com.salmonllc.swing.events.ValueChangedListener;
import javax.swing.*;
import javax.swing.text.*;


/**
 * SOFIA implementation of a JTextField. This Text Field can be bound to a DataStore column.
 */

public class STextField extends JTextField implements ModelChangedListener, SComponent {

    private DataStoreBuffer _ds;
    private String _dsCol;
    private SComponentHelper _helper;


    /**
     * Constructs a new <code>TextField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     */
    public STextField() {
        super();
        _helper = new SComponentHelper(this);
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public STextField(int columns) {
        super(columns);
        _helper = new SComponentHelper(this);
    }


    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text. A default model is created and the number of
     * columns is 0.
     *
     * @param text the text to be displayed, or <code>null</code>
     */
    public STextField(String text) {
        super(text);
        _helper = new SComponentHelper(this);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text and columns.  A default model is created.
     *
     * @param text the text to be displayed, or <code>null</code>
     * @param columns  the number of columns to use to calculate
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public STextField(String text, int columns) {
        super(text, columns);
        _helper = new SComponentHelper(this);
    }

    /**
     * Binds the component to a DataStore column
     * @param dsb The DataStore to bind to
     * @param column  The column to bind to
     */
    public void setColumn(DataStoreBuffer dsb, String column) {
        _ds = dsb;
        _dsCol = column;
        _ds.addModelChangedListener(this);
        evalColumn();
    }

    /**
     * Overrides the setDocument Method such that the documentlistener can be added.
     * @param doc The Document model to use
     */
    public void setDocument(Document doc) {
        super.setDocument(doc);
        doc.removeDocumentListener(_helper);
        doc.addDocumentListener(_helper);
    }
    
    /**
     * @see com.salmonllc.sql.ModelChangedListener#modelChanged(ModelChangedEvent)
     */
    public void modelChanged(ModelChangedEvent evt) {
        evalColumn();
    }

    private void evalColumn() {
        try {
            if (_ds != null && _dsCol != null) {
                setText(_ds.getFormattedString(_dsCol));
                _helper.setDataDirty(false);
            }
        } catch (Exception e) {
            setText(null);
        }
    }


    /**
     * Returns the value of the data in the component for the current row in the DataStore.
     */
    public String getValue() {
        try {
            if (_ds != null) {
                if (_helper.isDataDirty())
                    return getText();
                else
                    return _ds.getFormattedString(_dsCol);
            }
        } catch (DataStoreException ex) {
            ex.printStackTrace();
            return null;
        }
        return getText();
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
        try {
            if (_ds == null || _ds.getRow() < 0)
                return null;
            else {
                String newValue = getText();
                return new ValueChangedEvent(this, _ds.getFormattedString(_dsCol), newValue, _ds, _ds.getRow(), _ds.getColumnIndex(_dsCol));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public SComponentHelper getHelper() {
        return _helper;
    }

    /**
     * This method adds a listener the will be notified when the value in this component changes.
     * @param l The listener to add.
     */
    public void addValueChangedListener(ValueChangedListener l) {
        _helper.addValueChangedListener(l);
    }

    /**
     * This method removes a listener from the list that will be notified if the text in the component changes.
     * @param l The listener to remove.
     */
    public void removeValueChangedListener(ValueChangedListener l) {
        _helper.removeValueChangedListener(l);
    }

    /**
     * Returns the maximum number of characters you are allowed to type in this box
     */
    public int getMaxLength() {
        return _helper.getMaxLength();
    }

    /**
     * Sets the maximum number of characters you are allowed to type in this box
     */
    public void setMaxLength(int maxLength) {
        _helper.setMaxLength(maxLength);
    }
}
