package com.salmonllc.swing;

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

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.swing.events.ValueChangedEvent;
import com.salmonllc.swing.events.ValueChangedListener;
import java.awt.*;
import java.util.Enumeration;
import javax.swing.*;

/**
 * SOFIA implementation of a Radio Button Group. It lets you bind the group to a datastore column. The button group is a non visual component. You must create the SComponent buttons individually and use this to group them and bind them to a datastore column.
 */

public class SButtonGroup extends ButtonGroup implements SComponent, ModelChangedListener {
    String _col;
    DataStoreBuffer _ds;
    SComponentHelper _helper;

    /**
     * Creates a new button group bound to a datastore columm
     * @param ds The DataStore to bind to
     * @param col The column to bind to
     */
    public SButtonGroup(DataStoreBuffer ds, String col) {
        _ds = ds;
        _col = col;
        _ds.addModelChangedListener(this);
        _helper = new SComponentHelper(this);
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
         try {
            if (_ds == null)
                return null;
            else  {
                String newValue = getText();
                return new ValueChangedEvent(this,_ds.getFormattedString(_col),newValue,_ds, _ds.getRow(),_ds.getColumnIndex(_col));

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the helper for this component.
     */
    public SComponentHelper getHelper() {
        return _helper;
    }

    /**
     * Returns the value of the selected radio button as a string
     */
    public String getText() {
        Enumeration e = getElements();
        while (e.hasMoreElements()) {
            AbstractButton b = (AbstractButton) e.nextElement();
            if (b.isSelected()) {
                if (b instanceof SRadioButton)
                    return ((SRadioButton) b).getOption().getKey();
                else if (b instanceof SGroupedToggleButton)
                    return ((SGroupedToggleButton) b).getOption().getKey();
                else
                    return b.getActionCommand();
            }
        }
        return null;
    }

    /**
     * Sets the text value for the group and selects the appropriate button
     */
    public void setText(String text) {
        Enumeration e = getElements();
        boolean found = false;
        AbstractButton nullOption = null;

        while (e.hasMoreElements()) {
            AbstractButton b = (AbstractButton) e.nextElement();
            String key = null;
            if (b instanceof SRadioButton)
                key = ((SRadioButton) b).getOption().getKey();
            else if (b instanceof SGroupedToggleButton)
                key = ((SGroupedToggleButton) b).getOption().getKey();
            else
                key = b.getActionCommand();
            if (key == null)
                nullOption = b;
            if (SComponentHelper.valuesEqual(key, text)) {
                b.setSelected(true);
                found = true;
                break;
            }
        }

        if ((!found) && nullOption != null)
            nullOption.setSelected(true);
    }
    /**
     * Part of the model changed listener implementation. Do not call directly
     */
    public void modelChanged(ModelChangedEvent evt) {
        _helper.setDataDirty(true);
        try {
            setText(_ds.getFormattedString(_col));
        } catch (Exception ex) {
            setText(null);
        }
        _helper.setDataDirty(false);
    }

    /**
     * Adds all the buttons in the panel to the group
     */
    public void add(JPanel c) {
        Component comp[] = c.getComponents();
        if (comp != null) {
            for (int i = 0; i < comp.length; i++) {
                if (comp[i] instanceof AbstractButton)
                    add((AbstractButton) comp[i]);
            }
        }
    }

    /**
     * Adds a single button to the group
     */
    public void add(AbstractButton b) {
        super.add(b);
        b.addActionListener(_helper);
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
