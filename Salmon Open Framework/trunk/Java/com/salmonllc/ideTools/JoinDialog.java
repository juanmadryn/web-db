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
package com.salmonllc.ideTools;

import com.salmonllc.sql.ColumnDefinition;
import com.salmonllc.sql.DataDictionary;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author  demian
 */
public class JoinDialog extends JDialog implements ActionListener,ListSelectionListener {

    /**
	 * @uml.property  name="_aliases"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
    ModelDialog.AliasDef[] _aliases;
    JButton _ok;
    JButton _cancel;
    JButton _add;
    JButton _del;
    JComboBox _leftTables;
    JComboBox _rightTables;
    JList _leftColumns;
    JList _rightColumns;
    JList _joins;
    JCheckBox _outer;
    DataDictionary _dd;
    boolean _clickedCancel = true;

    public class JoinTermDef {
        String left;
        String right;
        boolean outer;
        public JoinTermDef (String left,String right,boolean outer) {
              this.left = left;
              this.right = right;
              this.outer = outer;
        }
        public String toString() {
            return left + (outer ? "*=" : "=") + right;
        }
    }

    public JoinDialog (Frame owner, ModelDialog.AliasDef[] aliases, DataDictionary dd, int buttonHeight) {
        super(owner,"Create a join",true);
        _dd = dd;
        int width = 620;
        int height = 450;
        Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (frameBounds.width - width) / 2;
        int y = (frameBounds.height - height) / 2;

        setBounds(x,y,width,height);
        setResizable(false);
        setModal(true);

        //main box
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(10,10,10,10) );

        //row 1, headings
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.add(DialogComponentFactory.makeLabel("Tables/Columns Left Side:",300));
        box.add(DialogComponentFactory.makeLabel("Tables/Columns Right Side:",300));
        box.add(Box.createHorizontalGlue());
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,5)));

        //row 2, drop downs
        box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.add(_leftTables = DialogComponentFactory.makeComboBox(290));
        box.add(Box.createRigidArea(new Dimension(10,0)));
        box.add(_rightTables = DialogComponentFactory.makeComboBox(290));
        box.add(Box.createHorizontalGlue());
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,5)));

        //row3, column lists
        box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.add(_leftColumns = DialogComponentFactory.makeList(new LModel()));
        box.add(DialogComponentFactory.makeScrollPane(290,150,_leftColumns = DialogComponentFactory.makeList(new LModel())));
        box.add(Box.createRigidArea(new Dimension(10,0)));
        box.add(DialogComponentFactory.makeScrollPane(290,150,_rightColumns = DialogComponentFactory.makeList(new LModel())));
        box.add(Box.createHorizontalGlue());
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,5)));

        //row4, Add and Delete Buttons
        box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.add(DialogComponentFactory.makeLabel("Column Pairs in Join:", 370));
        box.add(DialogComponentFactory.makeLabel("Outer:", 50));
        box.add(_outer = new JCheckBox());
        box.add(Box.createRigidArea(new Dimension(30,0)));
        box.add(_add = DialogComponentFactory.makeButton("Add", 60,buttonHeight));
        box.add(_del = DialogComponentFactory.makeButton("Del", 60,buttonHeight));
        box.add(Box.createHorizontalGlue());
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,5)));

        //row5, Join Display
        box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.add(DialogComponentFactory.makeScrollPane(600,100,_joins = DialogComponentFactory.makeList(new LModel())));
        main.add(box);


        //button bar
        _ok = new JButton("OK");
        _ok.addActionListener(this);
        _cancel = new JButton("Cancel");
        _cancel.addActionListener(this);
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new BoxLayout(buttonBar,BoxLayout.X_AXIS));
        buttonBar.add(_ok = new JButton("OK"));
        buttonBar.add(_cancel = new JButton("Cancel"));
        main.add(buttonBar);

        //Add Listeners
        _ok.addActionListener(this);
        _cancel.addActionListener(this);
        _leftTables.addActionListener(this);
        _rightTables.addActionListener(this);
        _add.addActionListener(this);
        _del.addActionListener(this);
        _leftColumns.addListSelectionListener(this);
        _rightColumns.addListSelectionListener(this);
        _joins.addListSelectionListener(this);
        _leftColumns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _rightColumns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Fill Drop Downs
        loadTables(aliases,_leftTables);
        loadTables(aliases,_rightTables);

        //Add it to the screen
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
        cp.add(main);
        cp.add(Box.createVerticalGlue());
        cp.add(buttonBar);
        cp.add(Box.createRigidArea(new Dimension(0,20)));
        enableDisableButtons();
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==_leftTables) {
            ModelDialog.AliasDef d = (ModelDialog.AliasDef)_leftTables.getSelectedItem();
            loadColumns(d.table,_leftColumns);
            enableDisableButtons();
        }
        else if (e.getSource()==_rightTables) {
            ModelDialog.AliasDef d = (ModelDialog.AliasDef)_rightTables.getSelectedItem();
            loadColumns(d.table,_rightColumns);
            enableDisableButtons();
        }
        else if (e.getSource() == _cancel) {
            _clickedCancel = true;
            setVisible(false);
        }
        else if (e.getSource() == _ok) {
            _clickedCancel = false;
            setVisible(false);
        }
        else if (e.getSource() == _add)
            addJoin();
        else if (e.getSource() == _del)
            deleteJoin();

    }
    public void addJoin() {
        ModelDialog.AliasDef leftTable = (ModelDialog.AliasDef) _leftTables.getSelectedItem();
        ModelDialog.AliasDef rightTable = (ModelDialog.AliasDef) _rightTables.getSelectedItem();
        ColumnDefinition leftColumn = (ColumnDefinition) _leftColumns.getSelectedValue();
        ColumnDefinition rightColumn = (ColumnDefinition) _rightColumns.getSelectedValue();
        boolean outer = _outer.isSelected();
        LModel joinModel = (LModel)_joins.getModel();
        for (int i = 0; i < joinModel.getSize();i++)
            ((JoinTermDef)joinModel.getElementAt(i)).outer = outer;
        JoinTermDef d = new JoinTermDef((leftTable.alias == null ? leftTable.table : leftTable.alias) + "." + leftColumn.getColumnName()                ,
                                      (rightTable.alias == null ? rightTable.table : rightTable.alias) + "." + rightColumn.getColumnName(),
                                      outer);

        joinModel.addElement(d);
        enableDisableButtons();
    }
     private void deleteJoin() {
        LModel mod = (LModel) _joins.getModel();
        int sel[] = _joins.getSelectedIndices();
        for (int i = sel.length - 1; i > -1; i--)
            mod.remove(sel[i]);
        _joins.getSelectionModel().clearSelection();
        enableDisableButtons();
    }
    private void enableDisableButtons() {
         _ok.setEnabled(_joins.getModel().getSize() > 0);
         _del.setEnabled(_joins.getModel().getSize() > 0 && _joins.getSelectedIndices().length > 0);
         _add.setEnabled(_leftColumns.getSelectedIndex() > -1 && _rightColumns.getSelectedIndex() > -1);
    }
    /**
     * Returns the join terms specified by the user or an empty array if the user clicked cancel
     */
    public JoinTermDef[] getResult() {
        if (_clickedCancel)
            return new JoinTermDef[0];
        LModel l = (LModel) _joins.getModel();
        JoinTermDef ret[] = new JoinTermDef[l.getSize()];
        for (int i = 0; i < l.getSize();i++)
            ret[i] = (JoinTermDef) l.getElementAt(i);
        return ret;
    }
    private void loadColumns(String tableName, JList list) {
        if (tableName != null){
            Vector v = _dd.getColumns(tableName);
            Vector mod = new Vector(v.size());
            for (int i = 0; i < v.size();i++) {
                ColumnDefinition def = (ColumnDefinition) v.elementAt(i);
                mod.add(def);
            }
            list.setModel(new LModel(mod));
        }
    }
    private void loadTables(ModelDialog.AliasDef[] defs, JComboBox box) {
        box.removeAllItems();
        for (int i = 0; i < defs.length; i++)
            box.addItem(defs[i]);
    }
    public void valueChanged(ListSelectionEvent e) {
        enableDisableButtons();
    }
}
