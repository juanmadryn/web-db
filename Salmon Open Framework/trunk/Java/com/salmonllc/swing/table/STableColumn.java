package com.salmonllc.swing.table;

import com.salmonllc.personalization.Nameable;
import com.salmonllc.swing.STable;
import javax.swing.table.TableColumn;

/**
 * SOFIA implementation of a table column.
 */

public class STableColumn extends TableColumn implements Nameable {
    STable _tab;

    public STableColumn(String name, STable tab) {
         _tab = tab;
         setIdentifier(name);
    }

    /**                                              \
     * get the identifier for the column in the table
     * @return
     */
    public String getName() {
        Object o = getIdentifier();
        if (o instanceof String)
            return (String)o;
        return null;
    }

    /**
     * Returns the STable that the column is associated with
     */
    public STable getTable() {
        return _tab;
    }

    /**
     * Returns true if the column is editable
     */
    public boolean isEditable() {
        DataStoreTableModel tabMod = (DataStoreTableModel) _tab.getModel();
        return tabMod.isColumnEditable(getModelIndex());
    }

    /**
     * Sets whether or not the column is editable
     */
    public void setEditable(boolean editable) {
        if (getModelIndex() != -1) {
            DataStoreTableModel tabMod = (DataStoreTableModel) _tab.getModel();
            tabMod.setColumnEditable(getModelIndex(),editable);
        }
    }

    /**
     * Sets whether or not the column is visible
     */
    public void setVisible(boolean visible) {
        if (visible)
            _tab.showColumn(getName());
        else
            _tab.hideColumn(getName());
    }

    /**
     * Sets the width of the column
     */
    public void setWidth(int width) {
        super.setWidth(width);
        setPreferredWidth(width);
    }

    /**
     * Sets the position of the column in the grid
     */
    public void setPosition(int newPos) {
        _tab.moveColumn(getName(),newPos);
    }
}
