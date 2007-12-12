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
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;

/**
 * SOFIA implementation of a JList. This List can be bound to a DataStore column.
 */

public class SList extends JList implements SComponent, ModelChangedListener {
    private SComponentHelper _helper;
    private String _dsCol;
    private DataStoreBuffer _ds;

    /**
     * Constructs a <code>JList</code> with an empty model.
     */
    public SList() {
        super(new DefaultListModel());
        _helper = new SComponentHelper(this);
    }



    /**
     * Constructs a <code>JList</code> that displays the elements in
     * the specified array.  This constructor just delegates to the
     * <code>ListModel</code> constructor.
     *
     * @param  listData  the array of Objects to be loaded into the data model
     */
    public SList(final Object[] listData) {
        super(new DefaultListModel());
        for (int i = 0; i < listData.length; i++)
            addItem(listData[i]);
        _helper = new SComponentHelper(this);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in
     * the specified <code>Vector</code>.  This constructor just
     * delegates to the <code>ListModel</code> constructor.
     *
     * @param  listData  the <code>Vector</code> to be loaded into the
     *		data model
     */
    public SList(final Vector listData) {
        super(new DefaultListModel());
        for (int i = 0; i < listData.size(); i++)
            addItem(listData.elementAt(i));
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
                String value=_ds.getFormattedString(_dsCol);
                getSelectionModel().clearSelection();

                if (getSelectionMode() != ListSelectionModel.SINGLE_SELECTION && value != null) {
                    int nullIndex = findSelectValue(null);
                    Vector sel = new Vector();
                    StringTokenizer st = new StringTokenizer(value,",");
                    while (st.hasMoreTokens()) {
                        String item = st.nextToken();
                        int ndx = findSelectValue(item);
                        if (ndx > -1)
                            sel.addElement(new Integer(ndx));
                        else if (nullIndex != -1)
                            sel.addElement(new Integer(nullIndex));
                    }
                    if (sel.size() > 0) {
                        int ndx[] = new int[sel.size()];
                        for (int i = 0; i < sel.size();i++)
                            ndx[i] = ((Integer)sel.elementAt(i)).intValue();
                        setSelectedIndices(ndx);
                    }
                }
                else {
                   int ndx = findSelectValue(value);
                   if (ndx > -1)
                      setSelectedIndex(ndx);
                }
            }
        } catch (Exception e) {
            getSelectionModel().clearSelection();
        }
        _helper.setDataDirty(false);


    }

    private int findSelectValue(String value) {
        ListModel mod = getModel();
        for (int i = 0; i < mod.getSize();i++) {
            Object o = mod.getElementAt(i);
            if (o.equals(value))
                return i;
        }
        return -1;
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
        int ndx[] = getSelectedIndices();

        if (ndx == null)
            return null;
        if (ndx.length == 0)
            return null;

        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < ndx.length;i++) {
            Object o = getModel().getElementAt(ndx[i]);
            String val = "null";
            if (o != null) {
                if (o instanceof SOption) {
                    val = ((SOption)o).getKey();
                    if (val == null)
                        val = "null";
                }
                else
                    val = o.toString();
            }
            ret.append(val);
            ret.append(",");
        }
        ret.setLength(ret.length() - 1);
        String r = ret.toString();
        if (r.equals("null"))
            return null;
        else
            return r;
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
        try {
            if (_ds == null)
                return null;
            else
                return new ValueChangedEvent(this,_ds.getFormattedString(_dsCol),getSelValue(),_ds, _ds.getRow(),_ds.getColumnIndex(_dsCol));
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
    * Adds an item to the List
     */
    public void addItem(Object opt) {
      ((DefaultListModel) getModel()).addElement(opt);
   }

   /**
    * Gets all the items from the list
    */
   public Object[] getItems() {
       ListModel m = getModel();
		Object o[] = new Object[m.getSize()];
		for (int i = 0; i < m.getSize();i++) {
			o[i] = m.getElementAt(i);
		}
		return o;
	}
}
