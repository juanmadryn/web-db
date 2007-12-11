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
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.swing.events.ValueChangedEvent;
import com.salmonllc.swing.events.ValueChangedListener;
import javax.swing.*;


/**
 * SOFIA implementation of a JSpinner. This Combo Box can be bound to a DataStore column
 */

public class SSpinner extends JSpinner implements SComponent, ModelChangedListener {
    private SComponentHelper _helper;
    private String _dsCol;
    private DataStoreBuffer _ds;

    /**
     * Constructs a spinner with an <code>Integer SpinnerNumberModel</code>
     * with initial value 0 and no minimum or maximum limits.
     */
    public SSpinner() {
        _helper = new SComponentHelper(this);
    }

    /**
     * Constructs a complete spinner with pair of next/previous buttons
     * and an editor for the <code>SpinnerModel</code>.
     */
    public SSpinner(SpinnerModel model) {
        super(model);
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
     * @see ModelChangedListener#modelChanged(ModelChangedEvent)
     */
    public void modelChanged(ModelChangedEvent evt) {
        evalColumn();
    }

    private void evalColumn() {
        _helper.setDataDirty(true);
        try {
            if (_ds != null && _dsCol != null) {
                Object value = _ds.getAny(_dsCol);
                setValue(value);
            }
        } catch (Exception e) {
            _helper.setDataDirty(false);
            SpinnerModel m = getModel();
            if (m instanceof SpinnerNumberModel)
                setValue(((SpinnerNumberModel)m).getMinimum());
            else if (m instanceof SpinnerListModel)
                setValue(((SpinnerListModel)m).getList().get(0));
            else if (m instanceof SpinnerDateModel)
                setValue(((SpinnerDateModel)m).getStart());
            fireStateChanged();
        }
        _helper.setDataDirty(false);
    }


    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
        try {
            if (_ds == null)
                return null;
            else  {
                String val = null;
                if (getValue() != null)
                    val = getValue().toString();
                return new ValueChangedEvent(this, _ds.getFormattedString(_dsCol), val, _ds, _ds.getRow(), _ds.getColumnIndex(_dsCol));
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




}
