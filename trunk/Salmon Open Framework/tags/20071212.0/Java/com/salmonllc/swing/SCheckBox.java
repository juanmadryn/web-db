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
 * SOFIA implementation of a check box. This check box can be bound to a DataStore column
 */
public class SCheckBox extends JCheckBox implements SComponent, ModelChangedListener {
    SComponentHelper _helper;
    String _trueValue = "1" ;
    String _falseValue = "0" ;
    private String _dsCol;
    private DataStoreBuffer _ds;

    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public SCheckBox() {
        _helper = new SComponentHelper(this);
    }

    /**
     * Creates a check box where properties are taken from the
     * Action supplied.
     *
     * @since 1.3
     */
    public SCheckBox(Action a) {
        super(a);
        _helper = new SComponentHelper(this);
    }

    /**
     * Creates an initially unselected check box with an icon.
     *
     * @param icon  the Icon image to display
     */
    public SCheckBox(Icon icon) {
        super(icon);
        _helper = new SComponentHelper(this);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Creates a check box with an icon and specifies whether
     * or not it is initially selected.
     *
     * @param icon  the Icon image to display
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public SCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
        _helper = new SComponentHelper(this);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Creates an initially unselected check box with text.
     *
     * @param text the text of the check box.
     */
    public SCheckBox(String text) {
        super(text);
        _helper = new SComponentHelper(this);
    }

    /**
     * Creates an initially unselected check box with
     * the specified text and icon.
     *
     * @param text the text of the check box.
     * @param icon  the Icon image to display
     */
    public SCheckBox(String text, Icon icon) {
        super(text, icon);
        _helper = new SComponentHelper(this);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Creates a check box with text and icon,
     * and specifies whether or not it is initially selected.
     *
     * @param text the text of the check box.
     * @param icon  the Icon image to display
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public SCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
        _helper = new SComponentHelper(this);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Creates a check box with text and specifies whether
     * or not it is initially selected.
     *
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public SCheckBox(String text, boolean selected) {
        super(text, selected);
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
                String newValue = isSelected() ? _trueValue : _falseValue;
                return new ValueChangedEvent(this,_ds.getFormattedString(_dsCol),newValue,_ds, _ds.getRow(),_ds.getColumnIndex(_dsCol));

            }
        }
        catch (Exception e) {
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
     * Binds the component to a DataStore column
     * @param dsb The DataStore to bind to
     * @param column  The column to bind to
     * @param trueValue The value that represents a true state for the checkbox
     * @param falseValue The value that represents a false state for the checkbox
     */
    public void setColumn(DataStoreBuffer dsb, String column, String trueValue, String falseValue) {
        setColumn(dsb,column);
        _trueValue=trueValue;
        _falseValue=falseValue;
        evalColumn();
    }


    /**
     * @see com.salmonllc.sql.ModelChangedListener#modelChanged(com.salmonllc.sql.ModelChangedEvent)
     */
    public void modelChanged(ModelChangedEvent evt) {
        evalColumn();
    }

    private void evalColumn() {
        try {
            if (_ds != null && _dsCol != null) {
                 _helper.setDataDirty(true);
                String value=_ds.getFormattedString(_dsCol);
                boolean selected = false;
                if (value != null)
                    if (value.equals(_trueValue))
                        selected = true;
                if (selected != isSelected())
                    setSelected(selected);
                _helper.setDataDirty(false);
            }
        } catch (Exception e) {
            setSelected(false);
        }
    }

    /**
     * Returns the value representing an unchecked box
     */
    public String getFalseValue() {
        return _falseValue;
    }

    /**
     * Sets the value representing an unchecked box
     */

    public void setFalseValue(String falseValue) {
        _falseValue = falseValue;
    }

    /**
     * Returns the value representing a checked box
     */

    public String getTrueValue() {
        return _trueValue;
    }

    /**
     * Sets the value representing a checked box
     */

    public void setTrueValue(String trueValue) {
        _trueValue = trueValue;
    }
    

}
