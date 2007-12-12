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
import java.awt.Component;
import java.awt.Container;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

/**
 * This component will watch for changes in a model, after each model change it will search through a specifed panel and find and list any errors found in any SLabel components in the panel.
 */
public class SErrorList extends JScrollPane implements ModelChangedListener {

    JPanel _pan;
    DataStoreBuffer _ds;
    JList _list;


    /**
     * Create a new Error List Component
     */
    public SErrorList() {
        _list = new JList(new DefaultListModel());
        _list.setCellRenderer(new ListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return (JLabel) value;
            }
        });
        getViewport().add(_list);
        setVisible(false);
    }


    /**
     * Sets the panel and datastore this component should check for errors
     */
    public void setDataStoreAndPanel(DataStoreBuffer ds, JPanel p) {
        _pan = p;
        _ds = ds;
        ds.addModelChangedListener(this);
    }

    /**
     * @see com.salmonllc.sql.ModelChangedListener#modelChanged(ModelChangedEvent)
     */
    public void modelChanged(ModelChangedEvent evt) {
        ((DefaultListModel) _list.getModel()).removeAllElements();
        getErrors(_pan);
        ListModel m = _list.getModel();
        _list.setVisible(m.getSize() > 0);
        setVisible(m.getSize() > 0);
    }


    private void getErrors(Container cont) {
        int count = cont.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component comp = cont.getComponent(i);
            if (comp instanceof SLabel) {
                Icon errorIcon = ((SLabel) comp).getErrorIcon();
                String errorText = ((SLabel) comp).getErrorMessage();
                if (errorText != null) {
                    JLabel l = new JLabel(errorText, errorIcon, SwingConstants.LEFT);
                    ((DefaultListModel) _list.getModel()).addElement(l);
                }
            } else if (comp instanceof Container) {
                getErrors(((Container) comp));
            }
        }
    }
}
