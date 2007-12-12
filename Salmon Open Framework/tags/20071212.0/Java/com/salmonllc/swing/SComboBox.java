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
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * SOFIA implementation of a JComboBox. This Combo Box can be bound to a DataStore column
 */

public class SComboBox extends JComboBox implements SComponent, ModelChangedListener {
    private SComponentHelper _helper;
    private String _dsCol;
    private DataStoreBuffer _ds;


    /**
     * Constructor for SComboBox.
     * @param items
     */
    public SComboBox(Object[] items) {
        super(items);
        _helper = new SComponentHelper(this);
    }

    /**
     * Constructor for SComboBox.
     * @param items
     */
    public SComboBox(Vector items) {
        super(items);
        _helper = new SComponentHelper(this);
    }

    /**
     * Creates a <code>JComboBox</code> with a default data model.
     * The default data model is an empty list of objects.
     * Use <code>addItem</code> to add items.  By default the first item
     * in the data model becomes selected.
     *
     * @see javax.swing.DefaultComboBoxModel
     */
    public SComboBox() {
        super();
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
     * @see com.salmonllc.sql.ModelChangedListener#modelChanged(com.salmonllc.sql.ModelChangedEvent)
     */
    public void modelChanged(ModelChangedEvent evt) {
        evalColumn();
    }

    private void evalColumn() {
        _helper.setDataDirty(true);
        try {
            if (_ds != null && _dsCol != null) {
                String value = _ds.getFormattedString(_dsCol);
                ComboBoxModel mod = getModel();
                boolean found = false;
                for (int i = 0; i < mod.getSize(); i++) {
                    Object o = mod.getElementAt(i);
                    if (o.equals(value)) {
                        mod.setSelectedItem(o);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (isEditable())
                        ((JTextComponent) getEditor().getEditorComponent()).setText(value);
                    else {
                        _helper.setDataDirty(true);
                        setSelectedItem(null);
                    }
                }
            }
        } catch (Exception e) {
            getModel().setSelectedItem(null);
        }
        _helper.setDataDirty(false);


    }


    /**
     * Returns the value of the data in the component
     */
    public String getValue() {
        try {
            if (_ds != null) {
                if (_helper.isDataDirty())
                    return getSelValue();
                else
                    return _ds.getFormattedString(_dsCol);
            }
        } catch (DataStoreException ex) {
            ex.printStackTrace();
            return null;
        }
        return getSelValue();

    }

    private String getSelValue() {
        Object o = getModel().getSelectedItem();
        if (o == null)
            return null;
        if (o instanceof SOption)
            return ((SOption) o).getKey();
        return o.toString();
    }

    public void setValue(String value) {
        setSelectedIndex(-1);
        for (int i = 0; i < getItemCount(); i++) {
            Object o = getItemAt(i);
            if (o instanceof SOption) {
                if (SComponentHelper.valuesEqual(((SOption) o).getKey(), value)) {
                    setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
        try {
            if (_ds == null)
                return null;
            else
                return new ValueChangedEvent(this, _ds.getFormattedString(_dsCol), getSelValue(), _ds, _ds.getRow(), _ds.getColumnIndex(_dsCol));
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
     * Adds an option to the combo box
     */
    public void addOption(SOption opt) {
        addItem(opt);
    }

    /**
     * Returns an array of items in the combo box
     */
    public Object[] getItems() {
        Object o[] = new Object[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            o[i] = getItemAt(i);
        }
        return o;
    }

    /**
     * Sets the selected item in the combo box display area to the object in
     * the argument.
     * If <code>anObject</code> is in the list, the display area shows
     * <code>anObject</code> selected.
     * <p>
     * If <code>anObject</code> is <i>not</i> in the list and the combo box is
     * uneditable, it will not change the current selection. For editable
     * combo boxes, the selection will change to <code>anObject</code>.
     * <p>
     * If this constitutes a change in the selected item,
     * <code>ItemListener</code>s added to the combo box will be notified with
     * one or two <code>ItemEvent</code>s.
     * If there is a current selected item, an <code>ItemEvent</code> will be
     * fired and the state change will be <code>ItemEvent.DESELECTED</code>.
     * If <code>anObject</code> is in the list and is not currently selected
     * then an <code>ItemEvent</code> will be fired and the state change will
     * be <code>ItemEvent.SELECTED</code>.
     * <p>
     * <code>ActionListener</code>s added to the combo box will be notified
     * with an <code>ActionEvent</code> when this method is called.
     *
     * @param anObject  the list object to select; use <code>null</code> to
     clear the selection
     */
    public void setSelectedItem(Object anObject) {
        ComboBoxModel mod = getModel();
        Object nullOption = null;
        for (int i = 0; i < mod.getSize(); i++) {
            Object o = mod.getElementAt(i);
            if (o.equals(anObject)) {
                mod.setSelectedItem(o);
                return;
            }
            if (o instanceof SOption && ((SOption) o).getKey() == null)
                nullOption = o;
        }
        mod.setSelectedItem(nullOption);

    }
}
