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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author  demian
 */
public class ColumnListLookupDialog extends JDialog implements ActionListener, ListSelectionListener {

	JButton _ok;
	JButton _cancel;
	JButton _addTable;
	JButton _addColumn;
	JTextArea _colList;
	JComboBox _tables;
	JList _columns;
	DataDictionary _dd;
	boolean _clickedCancel = true;

	public ColumnListLookupDialog(Frame owner, String columnList, DataDictionary dd, int buttonHeight) {
		super(owner, "Build a table/column list", true);
		_dd = dd;
		int width = 500;
		int height = 350;
		Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (frameBounds.width - width) / 2;
		int y = (frameBounds.height - height) / 2;

		setBounds(x, y, width, height);
		setResizable(false);
		setModal(true);

		//main box
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(new EmptyBorder(10, 10, 10, 10));

		//row1, column headings
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Tables/Columns in DataBase:", 200));
		box.add(Box.createRigidArea(new Dimension(50, 0)));
		box.add(DialogComponentFactory.makeLabel("Table/Columns Selected:", 200));
		main.add(box);

		JPanel b2 = new JPanel();
		b2.setLayout(new BoxLayout(b2, BoxLayout.X_AXIS));
		main.add(b2);

		JPanel b3 = new JPanel();
		b3.setLayout(new BoxLayout(b3, BoxLayout.Y_AXIS));
		b2.add(b3);

		//row2, tables box
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(_tables = DialogComponentFactory.makeComboBox(200));
		box.add(Box.createRigidArea(new Dimension(5, 0)));
		box.add(_addTable = DialogComponentFactory.makeButton("+", 40, buttonHeight));
		b3.add(box);

		//row2 right side
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeScrollPane(200, 170, _columns = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(5, 0)));
		box.add(_addColumn = DialogComponentFactory.makeButton("+", 40, buttonHeight));
		b3.add(box);

		JScrollPane p = DialogComponentFactory.makeScrollPane(200, 200, _colList = new JTextArea(50, 30));
		p.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_colList.setWrapStyleWord(true);
		_colList.setLineWrap(true);
		_colList.setText(columnList);
		b2.add(Box.createRigidArea(new Dimension(5, 0)));
		b2.add(p);

		//row 4
		_ok = new JButton("OK");
		_ok.addActionListener(this);
		_cancel = new JButton("Cancel");
		_cancel.addActionListener(this);
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		buttonBar.add(_ok = new JButton("OK"));
		buttonBar.add(_cancel = new JButton("Cancel"));
		main.add(buttonBar);

		//Add Listeners
		_ok.addActionListener(this);
		_cancel.addActionListener(this);
		_addTable.addActionListener(this);
		_addColumn.addActionListener(this);
		_tables.addActionListener(this);
		_columns.addListSelectionListener(this);

		//load the tables from the db
		loadTables();

		//Add it to the screen
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cp.add(main);
		cp.add(Box.createVerticalGlue());
		cp.add(buttonBar);
		cp.add(Box.createRigidArea(new Dimension(0, 20)));
		enableDisableButtons();
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _cancel) {
			_clickedCancel = true;
			setVisible(false);
		} else if (e.getSource() == _ok) {
			_clickedCancel = false;
			setVisible(false);
		} else if (e.getSource() == _tables) {
			String st = (String) _tables.getSelectedItem();
			loadColumns(st);
			enableDisableButtons();
		} else if (e.getSource() == _addTable) {
			String val = _colList.getText();
			if (val == null)
				val = "";
			if (val.length() != 0)
				val +=", ";
			val += _tables.getSelectedItem() + ".*";
			_colList.setText(val);		
		} else if (e.getSource() == _addColumn) {
			String val = _colList.getText();
			if (val == null)
				val = "";
			if (val.length() != 0)
				val +=", ";
			Object def[] = (Object[]) _columns.getSelectedValues();
			for (int i = 0; i < def.length; i++) {
				ColumnDefinition cd = (ColumnDefinition) def[i];
				val += cd.getTableName() + "." + cd.getColumnName();
				if (i < def.length - 1)
					val +=", ";	
			}
			_colList.setText(val);	
		}

	}

	private void enableDisableButtons() {
		_addColumn.setEnabled(_columns.getSelectedIndices().length > 0);
	}

	/**
	 * @return whether or not the user clicked cancel to close the dialog
	 */
	public boolean isClickedCancel() {
		return _clickedCancel;
	}

	public String getColumnList() {
		return _colList.getText();	
	}	
	
	private void loadColumns(String tableName) {
		if (tableName != null) {
			Vector v = _dd.getColumns(tableName);
			Vector mod = new Vector(v.size());
			for (int i = 0; i < v.size(); i++) {
				mod.add(v.elementAt(i));
			}
			_columns.setModel(new LModel(mod));
		}
	}
	private void loadTables() {
		Vector v = _dd.getTableNames();
		for (int i = 0; i < v.size(); i++) {
			_tables.addItem(v.elementAt(i));
		}
	}
	public void valueChanged(ListSelectionEvent e) {
		enableDisableButtons();
		
	}
}
