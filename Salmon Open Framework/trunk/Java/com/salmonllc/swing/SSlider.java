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
 * SOFIA implementation of a JSlider. This Combo Box can be bound to a DataStore column
 */

public class SSlider extends JSlider implements SComponent, ModelChangedListener {
    private SComponentHelper _helper;
    private String _dsCol;
    private DataStoreBuffer _ds;

    /**
     * Constructs a new Slider
     */
    public SSlider() {
        _helper = new SComponentHelper(this);
    }

    /**
     * Creates a horizontal slider using the specified
     * BoundedRangeModel.
     */
    public SSlider(BoundedRangeModel brm) {
        super(brm);
        _helper = new SComponentHelper(this);

    }

    /**
     * Creates a horizontal slider using the specified min and max
     * with an initial value equal to the average of the min plus max.
     */
    public SSlider(int min, int max) {
        super(min, max);
        _helper = new SComponentHelper(this);

    }

    /**
     * Creates a horizontal slider using the specified min, max and value.
     */
    public SSlider(int min, int max, int value) {
        super(min, max, value);
        _helper = new SComponentHelper(this);

    }

    /**
     * Creates a slider using the specified orientation with the
     * range 0 to 100 and an initial value of 50.
     */
    public SSlider(int orientation) {
        super(orientation);
        _helper = new SComponentHelper(this);

    }

    /**
     * Creates a slider with the specified orientation and the
     * specified minimum, maximum, and initial values.
     *
     * @exception IllegalArgumentException if orientation is not one of VERTICAL, HORIZONTAL
     *
     * @see #setOrientation
     * @see #setMinimum
     * @see #setMaximum
     * @see #setValue
     */
    public SSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
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
                if (_ds.getAny(_dsCol) == null)
                    throw new Exception();
                setValue(_ds.getInt(_dsCol));
            }
        } catch (Exception e) {
            _helper.setDataDirty(false);
            setValue(getMinimum());
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
                String val = new Integer(getValue()).toString();
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
