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

import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.VectorSort;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

/**
 * @author  demian
 */
public class ModelDialog extends JDialog implements ActionListener, ListSelectionListener, CaretListener {

	JButton _ok;
	JButton _cancel;
	JButton _validateButton;
	JCheckBox _qbe;
	boolean _qbeChecked = false;
	JPanel _main;
	JPanel _qbePanel;
	JList _qbeElements;
	JPanel _standardPanel;
	JTextField _className;
	JTextField _qbeName;
	JTextField _qbeCols;
	JComboBox _tables;
	JComboBox _bucketType;
	JComboBox _qbeType;
	JList _colList;
	JList _colsInModel;
	JList _colsInSort;
	JList _aliases;
	JList _joins;
	JList _buckets;
	Vector _validations = new Vector();
	JTextField _colName;
	JTextField _colFormat;
	JTextField _aliasName;
	JTextField _bucketName;
	JTextField _bucketFormat;
	JButton _addCol;
	JButton _delCol;
	JButton _addQBE;
	JButton _delQBE;
	JButton _clearQBE;
	JButton _addOrder;
	JButton _delOrder;
	JButton _addAlias;
	JButton _delAlias;
	JButton _addJoin;
	JButton _delJoin;
	JButton _addBucket;
	JButton _delBucket;
	JButton _qbeColLookup;
	JCheckBox _updateable;
	JCheckBox _sortAscending;
	DataDictionary _dd;
	Frame _owner;
	boolean _clickedCancel = true;
	int _buttonHeight = 28;
	boolean _remotable = false;
	public static final int _qbeTypeInts[] =
		{
			QBEBuilder.CRITERIA_TYPE_COMPLEX,
			QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE,
			QBEBuilder.CRITERIA_TYPE_STARTS_WITH,
			QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE,
			QBEBuilder.CRITERIA_TYPE_CONTAINS,
			QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE,
			QBEBuilder.CRITERIA_TYPE_LTE,
			QBEBuilder.CRITERIA_TYPE_LT,
			QBEBuilder.CRITERIA_TYPE_GTE,
			QBEBuilder.CRITERIA_TYPE_GT,
			QBEBuilder.CRITERIA_TYPE_EQUALS,
			QBEBuilder.CRITERIA_TYPE_NOT_EQUALS,
			QBEBuilder.CRITERIA_TYPE_IN,
			QBEBuilder.CRITERIA_TYPE_CUSTOM };

	public class QBEType {
		public int type;
		public QBEType(int type) {
			this.type = type;
		}
		public String toString() {
			if (type == QBEBuilder.CRITERIA_TYPE_COMPLEX)
				return "Complex";
			if (type == QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE)
				return "Equals Ignore Case";
			if (type == QBEBuilder.CRITERIA_TYPE_STARTS_WITH)
				return "Starts With";
			if (type == QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE)
				return "Starts With Ignore Case";
			if (type == QBEBuilder.CRITERIA_TYPE_CONTAINS)
				return "Contains";
			if (type == QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE)
				return "Contains Ignore Case";
			if (type == QBEBuilder.CRITERIA_TYPE_LTE)
				return "Less Than or Equal";
			if (type == QBEBuilder.CRITERIA_TYPE_LT)
				return "Less Than";
			if (type == QBEBuilder.CRITERIA_TYPE_GTE)
				return "Greater Than or Equal";
			if (type == QBEBuilder.CRITERIA_TYPE_GT)
				return "Greater Than";
			if (type == QBEBuilder.CRITERIA_TYPE_EQUALS)
				return "Equal";
			if (type == QBEBuilder.CRITERIA_TYPE_NOT_EQUALS)
				return "Not Equal";
			if (type == QBEBuilder.CRITERIA_TYPE_IN)
				return "In";	
			if (type == QBEBuilder.CRITERIA_TYPE_CUSTOM)
				return "Custom";					
			return null;
		}
	}

	public class DataType {
		public int type;
		public DataType(int type) {
			this.type = type;
		}
		public String toString() {
			if (type == DataStore.DATATYPE_BYTEARRAY)
				return "binary";
			else if (type == DataStore.DATATYPE_DATE)
				return "date";
			else if (type == DataStore.DATATYPE_DATETIME)
				return "datetime";
			else if (type == DataStore.DATATYPE_DOUBLE)
				return "double";
			else if (type == DataStore.DATATYPE_FLOAT)
				return "float";
			else if (type == DataStore.DATATYPE_INT)
				return "int";
			else if (type == DataStore.DATATYPE_LONG)
				return "long";
			else if (type == DataStore.DATATYPE_SHORT)
				return "short";
			else if (type == DataStore.DATATYPE_STRING)
				return "string";
			else if (type == DataStore.DATATYPE_TIME)
				return "time";
			else
				return "unknown";
		}
	}

	/**
	 * @author  demian
	 */
	public class QBECriteriaDef {
		public String name;
		public String colList;
		public QBEType type;
		public QBECriteriaDef(String name, QBEType type, String colList) {
			this.name = name;
			this.type = type;
			this.colList = colList;
		}
		public String toString() {
			return name + "  |  Type: " + type + "   |   Column List: " + colList;
		}
		public boolean equals(QBECriteriaDef def) {
			return def.name.equals(this.name);
		}
	}
	/**
	 * @author  demian
	 */
	public class ColumnDef {
		public ColumnDefinition def;
		public String name;
		public String format;
		public String alias;
		public boolean updateable;
		private int DISPLAY_SHORT = 0;
		private int DISPLAY_LONG = 1;
		private int DISPLAY_FULL = 2;
		private int _display = DISPLAY_SHORT;

		public ColumnDef(ColumnDefinition def, boolean displayShort) {
			this.def = def;
			_display = displayShort ? DISPLAY_SHORT : DISPLAY_LONG;
		}

		public ColumnDef(ColumnDefinition def, String name, String alias, String format, boolean updateable) {
			this.def = def;
			this.name = (name == null || name.length() == 0) ? null : name;
			this.format = (format == null || format.length() == 0) ? null : format;
			this.alias = (alias == null || alias.length() == 0) ? null : alias;
			this.updateable = updateable;
			_display = DISPLAY_FULL;

		}
		public String toString() {
			if (_display == DISPLAY_SHORT)
				return def.getColumnName();
			else if (_display == DISPLAY_LONG)
				return def.getTableName() + "." + def.getColumnName();
			else if (_display == DISPLAY_FULL) {
				String tab = (alias == null ? def.getTableName() : alias);
				String nme = (name == null ? (tab + "." + def.getColumnName()) + " " : (name + " (" + tab + "." + def.getColumnName()) + ") ");
				String fmt = (format == null ? "" : " | format:" + format);
				String upd = (updateable ? " | update:yes" : "| update:no");
				return nme + fmt + upd;
			}
			return "";

		}
		/**
		 * @return  the name
		 * @uml.property  name="name"
		 */
		public String getName() {
			if (name != null)
				return name;
			else if (alias != null)
				return alias + "." + def.getColumnName();
			else
				return def.getTableName() + "." + def.getColumnName();
		}
	}

	/**
	 * @author  demian
	 */
	public class SortDef {
		public ColumnDefinition def;
		public String alias;
		private boolean sortAscending;

		public SortDef(ColumnDefinition def, String alias, boolean sortAsc) {
			this.def = def;
			this.alias = alias;
			this.sortAscending = sortAsc;
		}
		public String toString() {
			String tab = (alias == null ? def.getTableName() : alias);
			String col = def.getColumnName();
			String dir = (sortAscending ? " ASC" : " DESC");
			return tab + "." + col + dir;
		}
		public String getDir() {
			if (sortAscending)
				return "ASC";
			else
				return "DESC";
		}
	}
	public class AliasDef {
		public String table;
		public String alias;
		public AliasDef(String table, String alias) {
			this.table = table;
			this.alias = alias;
		}
		public String toString() {
			if (alias == null)
				return table;
			else
				return alias + " (" + table + ")";
		}
	}
	/**
	 * @author  demian
	 */
	public class BucketDef {
		public String name;
		public String format;
		public DataType datatype;
		public BucketDef(String name, String format, DataType type) {
			this.name = name;
			this.format = format == null || format.length() == 0 ? null : format;
			this.datatype = type;
		}
		public String toString() {
			return name + (format == null ? "" : " | format:" + format) + " | datatype:" + datatype.toString();
		}
	}

	public class JoinDef {
		public String leftSide;
		public String rightSide;
		public boolean outer;
		public String toString() {
			return leftSide + (outer ? " *= " : " = ") + rightSide;
		}

		public void addJoinTerm(JoinDialog.JoinTermDef term) {
			if (leftSide == null) {
				leftSide = term.left;
				rightSide = term.right;
			} else {
				leftSide += "," + term.left;
				rightSide += "," + term.right;
			}
			outer = term.outer;
		}
		public void addJoinTerm(JoinTermDef term) {
			if (leftSide == null) {
				leftSide = term.left;
				rightSide = term.right;
			} else {
				leftSide += "," + term.left;
				rightSide += "," + term.right;
			}
			outer = term.outer;
		}

		public boolean usesAlias(String alias) {
			String search = alias + ".";
			return (leftSide.indexOf(search) > -1 || rightSide.indexOf(search) > -1);
		}
	}

	public class JoinTermDef {
		String left;
		String right;
		boolean outer;
		public JoinTermDef(String left, String right, boolean outer) {
			this.left = left;
			this.right = right;
			this.outer = outer;
		}
		public String toString() {
			return left + (outer ? "*=" : "=") + right;
		}
	}

	private class Sorter extends VectorSort {
		public boolean compare(Object o1, Object o2) {
			return ((String) o1).compareTo((String) o2) < 1;
		}
	}

	public ModelDialog(Frame owner, String classToLoad, int buttonHeight) {
		super(owner, "Create a Model", true);
		_buttonHeight = buttonHeight;
		int buttonWidth = 60;
		_owner = owner;
		setModal(true);
		setResizable(false);
		//create a data dictionary object
		_dd = new DataDictionary(Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP)));
        String appName = Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP));
        IDETool.setPropsPath();
		int width = 780;
		int height = 570;
		Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (frameBounds.width - width) / 2;
		int y = (frameBounds.height - height) / 2;
		setBounds(x, y, width, height);
		JPanel box = new JPanel();

		//main box
		_main = new JPanel();
		_main.setLayout(new BoxLayout(_main, BoxLayout.Y_AXIS));
		_main.setBorder(new EmptyBorder(10, 10, 10, 10));
		_qbePanel = new JPanel();
		_qbePanel.setLayout(new BoxLayout(_qbePanel, BoxLayout.Y_AXIS));
		_standardPanel = new JPanel();
		_standardPanel.setLayout(new BoxLayout(_standardPanel, BoxLayout.Y_AXIS));

		//row 1, class name
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Class Name:", 80));
		box.add(_className = DialogComponentFactory.makeTextField(200));
		_validateButton = new JButton("Validations");
		_validateButton.addActionListener(this);

		box.add(Box.createHorizontalGlue());
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel(" QBE:"));
		box.add(_qbe = new JCheckBox());
		if (IDETool.useDefaultOSLookAndFeel())
			box.add(Box.createRigidArea(new Dimension(60, 0)));
		else
			box.add(Box.createRigidArea(new Dimension(50, 0)));
		box.add(_validateButton);
		box.add(Box.createRigidArea(new Dimension(15, 0)));
		_main.add(box);
		_main.add(Box.createRigidArea(new Dimension(0, 5)));

		//row 2, file drop down label, Columns in Data Store Label, Columns in sort label
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Tables/Columns in DataBase:", 200));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Columns in Model:", 200));
		box.add(_addCol = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delCol = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Order By:", 80));
		box.add(_addOrder = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delOrder = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));

		box.add(Box.createHorizontalGlue());
		_standardPanel.add(box);

		//row 3,  name, updatable, format
		_standardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(_tables = DialogComponentFactory.makeComboBox(200));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Name:", 47));
		box.add(_colName = DialogComponentFactory.makeTextField(44));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(DialogComponentFactory.makeLabel("Format:", 47));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(_colFormat = DialogComponentFactory.makeTextField(44));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(DialogComponentFactory.makeLabel("Updateable:", 67));
		box.add(_updateable = new JCheckBox());
		box.add(Box.createRigidArea(new Dimension(105, 0)));
		box.add(DialogComponentFactory.makeLabel("Ascending:", 80));
		box.add(_sortAscending = new JCheckBox());
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(Box.createHorizontalGlue());
		_standardPanel.add(box);

		//row 4, Columns, in model, in sort
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeScrollPane(200, 170, _colList = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeScrollPane(320, 170, _colsInModel = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeScrollPane(200, 170, _colsInSort = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createHorizontalGlue());
		_standardPanel.add(Box.createRigidArea(new Dimension(0, 3)));
		_standardPanel.add(box);

		//row 5, captions for alias, joins, buckets
		_standardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Aliases:", 80));
		box.add(_addAlias = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delAlias = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Buckets:", 200));
		box.add(_addBucket = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delBucket = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Joins:", 80));
		box.add(_addJoin = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delJoin = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));
		box.add(Box.createHorizontalGlue());
		_standardPanel.add(box);

		//row 6, alias name, bucket name, format, type
		_standardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Name:", 60));
		box.add(_aliasName = DialogComponentFactory.makeTextField(140));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Name:", 40));
		box.add(_bucketName = DialogComponentFactory.makeTextField(60));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(DialogComponentFactory.makeLabel("Format:", 45));
		box.add(_bucketFormat = DialogComponentFactory.makeTextField((60)));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(DialogComponentFactory.makeLabel("Type:", 35));
		box.add(_bucketType = DialogComponentFactory.makeComboBox((75)));
		box.add(Box.createRigidArea(new Dimension(240, 0)));
		box.add(Box.createHorizontalGlue());
		_standardPanel.add(box);

		//row 7, Alias, Joins, Buckets
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeScrollPane(200, 140, _aliases = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeScrollPane(320, 140, _buckets = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeScrollPane(200, 140, _joins = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createHorizontalGlue());
		_standardPanel.add(Box.createRigidArea(new Dimension(0, 3)));
		_standardPanel.add(box);

		//row 2 in qbe, qbe captions
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(Box.createRigidArea(new Dimension(0, 50)));
		box.add(DialogComponentFactory.makeLabel("QBE Selection Criteria:", 200));
		box.add(Box.createHorizontalGlue());
		box.add(_addQBE = DialogComponentFactory.makeButton("Add", buttonWidth, buttonHeight));
		box.add(_delQBE = DialogComponentFactory.makeButton("Del", buttonWidth, buttonHeight));
		box.add(_clearQBE = DialogComponentFactory.makeButton("Clear Selection", 120, buttonHeight));
		box.add(Box.createRigidArea(new Dimension(15, 0)));
		_qbePanel.add(box);

		//row 3 in qbe, qbe panel,  name, type, column list
		_qbePanel.add(Box.createRigidArea(new Dimension(0, 15)));
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Criteria Name:", 80));
		box.add(_qbeName = DialogComponentFactory.makeTextField(60));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Type:", 30));
		box.add(_qbeType = DialogComponentFactory.makeComboBox(140));
		box.add(Box.createRigidArea(new Dimension(10, 0)));
		box.add(DialogComponentFactory.makeLabel("Column List:", 75));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(_qbeCols = DialogComponentFactory.makeTextField(120));
		box.add(Box.createRigidArea(new Dimension(2, 0)));
		box.add(_qbeColLookup = DialogComponentFactory.makeButton("...", 40, _buttonHeight));
		box.add(Box.createRigidArea(new Dimension(15, 0)));
		box.add(Box.createHorizontalGlue());
		_qbePanel.add(box);

		//row 4 in qbe, list of QBE items
		_qbePanel.add(Box.createRigidArea(new Dimension(0, 15)));
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeScrollPane(740, 300, _qbeElements = DialogComponentFactory.makeList(new LModel())));
		box.add(Box.createRigidArea(new Dimension(15, 0)));
		_qbePanel.add(box);
		_qbePanel.add(Box.createVerticalGlue());

		//last row, button bar
		_ok = new JButton("OK");
		_ok.addActionListener(this);
		_cancel = new JButton("Cancel");
		_cancel.addActionListener(this);
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		buttonBar.add(_ok);
		buttonBar.add(_cancel);

		//add it to the screen
		_main.add(_standardPanel);
		_main.add(_qbePanel);
		_qbePanel.setVisible(false);

		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cp.add(_main);
		cp.add(Box.createVerticalGlue());
		cp.add(buttonBar);
		cp.add(Box.createRigidArea(new Dimension(0, 20)));

		//add listeners
		_colList.addListSelectionListener(this);
		_colsInModel.addListSelectionListener(this);
		_colsInSort.addListSelectionListener(this);
		_aliases.addListSelectionListener(this);
		_buckets.addListSelectionListener(this);
		_qbeElements.addListSelectionListener(this);
		_joins.addListSelectionListener(this);
		_bucketName.addCaretListener(this);
		_aliasName.addCaretListener(this);
		_qbeName.addCaretListener(this);
		_qbeCols.addCaretListener(this);
		_tables.addActionListener(this);
		_addAlias.addActionListener(this);
		_delAlias.addActionListener(this);
		_className.addCaretListener(this);
		_addOrder.addActionListener(this);
		_delOrder.addActionListener(this);
		_addCol.addActionListener(this);
		_delCol.addActionListener(this);
		_addBucket.addActionListener(this);
		_delBucket.addActionListener(this);
		_addJoin.addActionListener(this);
		_delJoin.addActionListener(this);
		_qbe.addActionListener(this);
		_addQBE.addActionListener(this);
		_delQBE.addActionListener(this);
		_clearQBE.addActionListener(this);
		_qbeColLookup.addActionListener(this);
		_qbeElements.addListSelectionListener(this);
		_qbeType.addActionListener(this);
	
		//fill drop downs and init the buttons
		enableDisableButtons();
		_updateable.setSelected(true);
		_sortAscending.setSelected(true);
		try {
			loadTables();
		} catch (Exception ex) {
			IDETool.displayError("Error Loading Tables" + ex.getMessage());
			return;
		}
		_bucketType.addItem(new DataType(DataStore.DATATYPE_BYTEARRAY));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_DATE));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_DATETIME));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_DOUBLE));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_FLOAT));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_INT));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_LONG));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_SHORT));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_STRING));
		_bucketType.addItem(new DataType(DataStore.DATATYPE_TIME));

		for (int i = 0; i < _qbeTypeInts.length; i++)
			_qbeType.addItem(new QBEType(_qbeTypeInts[i]));

		if (classToLoad != null) {
			populateFromModel(classToLoad, appName);
		}
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _qbe) {
			_qbeChecked = !_qbeChecked;
			_qbePanel.setVisible(_qbeChecked);
			_standardPanel.setVisible(!_qbeChecked);
			enableDisableButtons();
		} else if (e.getSource() == _qbeType) {
			int i = _qbeElements.getSelectedIndex();
			if (i > -1) {
				LModel mod = (LModel)_qbeElements.getModel();
				QBECriteriaDef def = (QBECriteriaDef) mod.getElementAt(i);
				def.type = (QBEType) _qbeType.getSelectedItem();
				_qbeElements.revalidate();
				_qbeElements.repaint();					
			}		
		}	 
		else if (e.getSource() == _ok) {
			_clickedCancel = false;
			setVisible(false);
		} else if (e.getSource() == _cancel) {
			_clickedCancel = true;
			setVisible(false);
		} else if (e.getSource() == _tables) {
			AliasDef def = (AliasDef) _tables.getSelectedItem();
			loadColumns(def.table);
			enableDisableButtons();
		} else if (e.getSource() == _addAlias)
			addAlias();
		else if (e.getSource() == _delAlias)
			deleteAlias();
		else if (e.getSource() == _addOrder)
			addSort();
		else if (e.getSource() == _delOrder)
			deleteSort();
		else if (e.getSource() == _addCol)
			addColumn();
		else if (e.getSource() == _delCol)
			deleteColumn();
		else if (e.getSource() == _addBucket)
			addBucket();
		else if (e.getSource() == _delBucket)
			deleteBucket();
		else if (e.getSource() == _addJoin)
			addJoin();
		else if (e.getSource() == _delJoin)
			deleteJoin();
		else if (e.getSource() == _validateButton)
			doValidations();
		else if (e.getSource() == _addQBE)
			addQBE();
		else if (e.getSource() == _delQBE)
			deleteQBE();
		else if (e.getSource() == _qbeColLookup)
			doColListLookup();	
		else if (e.getSource() == _clearQBE) 
			doClearQBE();
	}
	private void addAlias() {
		LModel aliasMod = (LModel) _aliases.getModel();
		String table = ((AliasDef) _tables.getSelectedItem()).table;
		String alias = _aliasName.getText().trim();
		if (alias.length() == 0)
			alias = null;

		AliasDef d = new AliasDef(table, alias);
		aliasMod.addElement(d);
		_aliasName.setText("");

		if (d.alias != null) {
			_tables.addItem(d);
			_tables.setSelectedItem(d);
		}
		enableDisableButtons();
	}
	private void addBucket() {
		String name = _bucketName.getText().trim();
		String format = _bucketFormat.getText().trim();
		DataType dt = (DataType) _bucketType.getSelectedItem();
		BucketDef d = new BucketDef(name, format, dt);
		((LModel) _buckets.getModel()).addElement(d);
		_bucketName.setText("");
		enableDisableButtons();

	}
	private void addColumn() {
		Object[] vals = _colList.getSelectedValues();
		AliasDef alias = (AliasDef) _tables.getSelectedItem();
		String name = vals.length > 1 ? null : _colName.getText().trim();
		String format = _colFormat.getText().trim();
		boolean updable = _updateable.isSelected();
		for (int i = 0; i < vals.length; i++) {
			ColumnDefinition col = ((ColumnDef) vals[i]).def;
			ColumnDef d = new ColumnDef(col, name, alias.alias, format, updable);
			((LModel) _colsInModel.getModel()).addElement(d);
		}
		_colName.setText("");
		enableDisableButtons();
	}
	private void addQBE() {
		QBEType type = (QBEType) _qbeType.getSelectedItem();
		QBECriteriaDef def = new QBECriteriaDef(_qbeName.getText().trim(), type, _qbeCols.getText().trim());
		LModel mod = ((LModel) _qbeElements.getModel());
		_qbeName.setText("");
		_qbeCols.setText("");
		for (int i = 0; i < mod.getSize(); i++) {
			QBECriteriaDef d1 = (QBECriteriaDef) mod.getElementAt(i);
			if (d1.equals(def)) {
				mod.setElementAt(def, i);
				enableDisableButtons();
				return;
			}
		}
		mod.addElement(def);
		_qbeElements.clearSelection();
		enableDisableButtons();
	}

	private void addJoin() {
		Vector aliases = new Vector();

		LModel mod = (LModel) _colsInModel.getModel();
		for (int i = 0; i < mod.getSize(); i++) {
			ColumnDef d = (ColumnDef) mod.getElementAt(i);
			String table = d.def.getTableName();
			String alias = d.alias;
			boolean found = false;
			for (int j = 0; j < aliases.size(); j++) {
				AliasDef def = (AliasDef) aliases.elementAt(j);
				if (equals(table, def.table) && equals(alias, def.alias)) {
					found = true;
					break;
				}
			}
			if (!found)
				aliases.add(new AliasDef(table, alias));
		}

		mod = (LModel) _aliases.getModel();
		for (int i = 0; i < mod.getSize(); i++) {
			AliasDef d = (AliasDef) mod.getElementAt(i);
			String table = d.table;
			String alias = d.alias;
			boolean found = false;
			for (int j = 0; j < aliases.size(); j++) {
				AliasDef def = (AliasDef) aliases.elementAt(j);
				if (equals(table, def.table) && equals(alias, def.alias)) {
					found = true;
					break;
				}
			}
			if (!found)
				aliases.add(new AliasDef(table, alias));
		}

		AliasDef send[] = new AliasDef[aliases.size()];
		aliases.copyInto(send);
		JoinDialog d = new JoinDialog(_owner, send, _dd, _buttonHeight);
		JoinDialog.JoinTermDef[] res = d.getResult();
		if (res.length > 0) {
			JoinDef join = new JoinDef();
			for (int i = 0; i < res.length; i++)
				join.addJoinTerm(res[i]);
			((LModel) _joins.getModel()).addElement(join);
		}
		d.dispose();
		enableDisableButtons();
	}
	private void doValidations() {
		ValidationDialog d = new ValidationDialog(_owner, getValidationRules(), getColumnAndBucketNames(), _dd, _buttonHeight);
		if (!d.isClickedCancel()) {
			_validations.removeAllElements();
			ValidationRuleDefinition def[] = d.getValidationRules();
			for (int i = 0; i < def.length; i++)
				_validations.add(def[i]);
		}
		d.dispose();
	}
	private void doColListLookup() {
		ColumnListLookupDialog d = new ColumnListLookupDialog(_owner, _qbeCols.getText(), _dd, _buttonHeight);
		if (!d.isClickedCancel()) 
			_qbeCols.setText(d.getColumnList());
		d.dispose();
	}
	private void doClearQBE() {
		_qbeElements.clearSelection();
		_qbeName.setText("");
		_qbeCols.setText("");
		_qbeType.setSelectedIndex(0);	
	}	
	private void addSort() {
		Object[] vals = _colList.getSelectedValues();
		AliasDef alias = (AliasDef) _tables.getSelectedItem();
		for (int i = 0; i < vals.length; i++) {
			ColumnDefinition col = ((ColumnDef) vals[i]).def;
			SortDef d = new SortDef(col, alias.alias, _sortAscending.isSelected());
			((LModel) _colsInSort.getModel()).addElement(d);
		}
		enableDisableButtons();
	}
	/**
	 * Called when the caret position is updated.
	 *
	 * @param e the caret event
	 */
	public void caretUpdate(CaretEvent e) {
		if (_qbeChecked) {
			int i = _qbeElements.getSelectedIndex();
			if (i > -1) {
				LModel mod = (LModel)_qbeElements.getModel();
				QBECriteriaDef def = (QBECriteriaDef) mod.getElementAt(i);
				if (e.getSource() == _qbeName) 
					def.name=_qbeName.getText();
				else if (e.getSource() == _qbeCols) 
					def.colList=_qbeCols.getText();
				_qbeElements.revalidate();
				_qbeElements.repaint();					
	
			}			
		}	
		enableDisableButtons();

	}
	private void deleteAlias() {
		LModel aliasMod = (LModel) _aliases.getModel();
		int sel[] = _aliases.getSelectedIndices();
		for (int i = sel.length - 1; i > -1; i--) {
			AliasDef def = (AliasDef) aliasMod.getElementAt(sel[i]);
			aliasMod.remove(sel[i]);
			if (def.alias != null) {
				for (int j = _tables.getItemCount() - 1; j > -1; j--) {
					AliasDef comp = (AliasDef) _tables.getItemAt(j);
					if (comp.alias == null)
						break;
					else if (comp.alias.equals(def.alias)) {
						_tables.removeItemAt(j);
						break;
					}
				}
			}
		}
		_aliases.getSelectionModel().clearSelection();
		enableDisableButtons();
	}
	private void deleteBucket() {
		LModel bucketMod = (LModel) _buckets.getModel();
		int sel[] = _buckets.getSelectedIndices();
		for (int i = sel.length - 1; i > -1; i--)
			bucketMod.remove(sel[i]);
		_buckets.getSelectionModel().clearSelection();
		enableDisableButtons();
	}

	private void deleteQBE() {
		LModel qbeMod = (LModel) _qbeElements.getModel();
		int sel[] = _qbeElements.getSelectedIndices();
		for (int i = sel.length - 1; i > -1; i--)
			qbeMod.remove(sel[i]);
		_qbeElements.getSelectionModel().clearSelection();
		doClearQBE();
		enableDisableButtons();
	}
	private void deleteColumn() {
		LModel colMod = (LModel) _colsInModel.getModel();
		int sel[] = _colsInModel.getSelectedIndices();
		for (int i = sel.length - 1; i > -1; i--)
			colMod.remove(sel[i]);
		_colsInModel.getSelectionModel().clearSelection();
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
	private void deleteSort() {
		LModel sortMod = (LModel) _colsInSort.getModel();
		int sel[] = _colsInSort.getSelectedIndices();
		for (int i = sel.length - 1; i > -1; i--)
			sortMod.remove(sel[i]);
		_colsInSort.getSelectionModel().clearSelection();
		enableDisableButtons();
	}
	private void enableDisableButtons() {
		_addCol.setEnabled(_colList.getSelectedIndices().length > 0);
		_addOrder.setEnabled(_colList.getSelectedIndices().length > 0);
		_addJoin.setEnabled(_colsInModel.getModel().getSize() > 0 || _aliases.getModel().getSize() > 0);
		_delOrder.setEnabled(_colsInSort.getSelectedIndices().length > 0 && _colsInSort.getModel().getSize() > 0);
		_delJoin.setEnabled(_joins.getSelectedIndices().length > 0 && _joins.getModel().getSize() > 0);
		_delBucket.setEnabled(_buckets.getSelectedIndices().length > 0 && _buckets.getModel().getSize() > 0);
		_addQBE.setEnabled(_qbeName.getText().trim().length() > 0 && _qbeCols.getText().trim().length() > 0 && _qbeElements.getSelectedIndex() == -1);
		_clearQBE.setEnabled(_qbeElements.getSelectedIndex() != -1);
		_delQBE.setEnabled(_qbeElements.getSelectedIndices().length > 0 && _qbeElements.getModel().getSize() > 0);
		_addBucket.setEnabled(_bucketName.getText().trim().length() > 0);

		if (_qbeChecked) {
			_ok.setEnabled(IDETool.isClassNameValid(_className.getText(), true) && (_qbeElements.getModel().getSize() > 0));
			_validateButton.setEnabled(_qbeElements.getModel().getSize() > 0);
		} else {
			_validateButton.setEnabled(_buckets.getModel().getSize() > 0 || _colsInModel.getModel().getSize() > 0);
			_ok.setEnabled(IDETool.isClassNameValid(_className.getText(), true) && (_buckets.getModel().getSize() > 0 || _colsInModel.getModel().getSize() > 0));
		}

		//add Alias button
		_addAlias.setEnabled(true);
		String check = _aliasName.getText().trim();
		AliasDef sel = (AliasDef) _tables.getSelectedItem();
		if (sel == null)
			_addAlias.setEnabled(false);
		else {
			if (check.length() == 0)
				check = sel.table;
			LModel aliasModel = (LModel) _aliases.getModel();
			for (int i = 0; i < aliasModel.getSize(); i++) {
				AliasDef comp = (AliasDef) aliasModel.getElementAt(i);
				if (comp.alias == null) {
					if (check.equals(comp.table)) {
						_addAlias.setEnabled(false);
						break;
					}
				} else if (comp.alias.equals(check)) {
					if (check.equals(comp.alias)) {
						_addAlias.setEnabled(false);
						break;
					}
				}
			}
		}

		//delete alias
		_delAlias.setEnabled(_aliases.getSelectedIndex() != -1 && _aliases.getModel().getSize() > 0);
		if (_delAlias.isEnabled()) {
			LModel cols = (LModel) _colsInModel.getModel();
			LModel sortCols = (LModel) _colsInSort.getModel();
			LModel joinCols = (LModel) _joins.getModel();
			Object[] selAlias = _aliases.getSelectedValues();
			for (int i = 0; i < selAlias.length; i++) {
				String alias = ((AliasDef) selAlias[i]).alias;
				if (alias != null) {
					for (int j = 0; j < cols.getSize(); j++) {
						ColumnDef d = (ColumnDef) cols.getElementAt(j);
						if (d.alias != null && d.alias.equals(alias)) {
							_delAlias.setEnabled(false);
							break;
						}
					}
					for (int j = 0; j < sortCols.getSize(); j++) {
						SortDef d = (SortDef) sortCols.getElementAt(j);
						if (d.alias != null && d.alias.equals(alias)) {
							_delAlias.setEnabled(false);
							break;
						}
					}
				} else {
					alias = ((AliasDef) selAlias[i]).table;
				}
				for (int j = 0; j < joinCols.getSize(); j++) {
					JoinDef d = (JoinDef) joinCols.getElementAt(j);
					if (d.usesAlias(alias)) {
						_delAlias.setEnabled(false);
						break;
					}
				}
			}
		}
	}
	private boolean equals(String val1, String val2) {
		if (val1 == null && val2 == null)
			return true;
		else if (val1 == null && val2 != null)
			return false;
		else if (val2 == null && val1 != null)
			return false;
		else
			return val1.equals(val2);
	}

	/**
	 * Returns whether or not the model is remotable
	 */
	public boolean isRemotable() {
		return _remotable;
	}
	/**
	* get list of aliases
	*/
	public AliasDef[] getAliases() {
		LModel l = (LModel) _aliases.getModel();
		AliasDef[] ret = new AliasDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (AliasDef) l.getElementAt(i);
		return ret;
	}
	/**
	* get list of buckets
	*/
	public BucketDef[] getBuckets() {
		LModel l = (LModel) _buckets.getModel();
		BucketDef[] ret = new BucketDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (BucketDef) l.getElementAt(i);
		return ret;
	}
	/**
	 * Return true if the user clicked cancel to close the dialog
	 */
	public boolean getCancelClicked() {
		return _clickedCancel;
	}
	/**
	 * Get the name of the class to save as
	 */
	public String getClassName() {
		return _className.getText().trim();
	}
	/**
	 * get list of columns
	 */
	public ColumnDef[] getColumns() {
		LModel l = (LModel) _colsInModel.getModel();
		ColumnDef[] ret = new ColumnDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (ColumnDef) l.getElementAt(i);
		return ret;
	}
	/**
	* get list of joins
	*/
	public JoinDef[] getJoins() {
		LModel l = (LModel) _joins.getModel();
		JoinDef[] ret = new JoinDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (JoinDef) l.getElementAt(i);
		return ret;
	}

	/**
	 * Get a list of validations
	 */
	public ValidationRuleDefinition[] getValidationRules() {
		ValidationRuleDefinition[] d = new ValidationRuleDefinition[_validations.size()];
		_validations.copyInto(d);
		return d;
	}

	/**
	 * Get a list of qbe criteria columns
	 * @return
	 */
	public QBECriteriaDef[] getQBECriteria() {
		LModel l = (LModel) _qbeElements.getModel();
		QBECriteriaDef[] ret = new QBECriteriaDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (QBECriteriaDef) l.getElementAt(i);
		return ret;

	}

	/**
	 * Returns true if the model is a qbe datastore
	 */
	public boolean isQBE() {
		return _qbeChecked;
	}
	/**
	 * get list of columns to sort by
	 */
	public SortDef[] getSortColumns() {
		LModel l = (LModel) _colsInSort.getModel();
		SortDef[] ret = new SortDef[l.getSize()];
		for (int i = 0; i < l.getSize(); i++)
			ret[i] = (SortDef) l.getElementAt(i);
		return ret;
	}
	private void loadColumns(String tableName) {
		if (tableName != null) {
			Vector v = _dd.getColumns(tableName);
			Vector mod = new Vector(v.size());
			for (int i = 0; i < v.size(); i++) {
				ColumnDefinition def = (ColumnDefinition) v.elementAt(i);
				mod.add(new ColumnDef(def, true));
			}
			_colList.setModel(new LModel(mod));
		}
	}
	private void loadTables() {
		Vector v = _dd.getTableNames();
		for (int i = 0; i < v.size(); i++) {
			_tables.addItem(new AliasDef((String) v.elementAt(i), null));
		}
	}
	private void populateFromModel(String model, String appName) {
		try {
			DataStoreBuffer d = DataStore.constructDataStore(model, appName);
			if (d == null) {
				IDETool.displayError("Error parsing model. Make sure the class extends com.salmonllc.sql.DataStoreBuffer and  has compiled correctly!", true);

			}
			if (d instanceof Remotable)
				_remotable = true;

			_className.setText(model);
			if (d instanceof QBEBuilder) {
				_qbeChecked = true;
				//criteria Elements
				QBEBuilder ds = (QBEBuilder) d;
				QBEBuilder.CriteriaElement ele[] = ds.getCriteriaElements();
				for (int i = 0; i < ele.length; i++) {
					//String name = ds.getColumnName(i);
					QBECriteriaDef def = new QBECriteriaDef(ele[i].bucketName, new QBEType(ele[i].type), ele[i].columnList);
					((LModel) _qbeElements.getModel()).addElement(def);
				}

				//validations
				for (int i = 0; i < ele.length; i++) {
					String name = ele[i].bucketName;
					ValidationRule rules[] = ds.getValidationRulesForColumn(i);
					if (rules != null) {
						for (int j = 0; j < rules.length; j++) {
							if (rules[j].getDsExpression() == null) {
								ValidationRuleDefinition def = new ValidationRuleDefinition();
								def.columnName = name;
								def.rule = rules[j];
								_validations.add(def);
							}
						}
					}

				}
			} else if (d instanceof DataStore) {
				_qbeChecked = false;

				DataStore ds = (DataStore) d;

				//first do the aliases
				for (int i = 0; i < ds.getAliasCount(); i++) {
					AliasDef def = new AliasDef(ds.getTable(i), ds.getAlias(i));
					((LModel) _aliases.getModel()).addElement(def);
					if (def.alias != null)
						_tables.addItem(def);
				}

				//then the columns and buckets 
				for (int i = 0; i < ds.getColumnCount(); i++) {
					if (ds.getColumnTableName(i) == null && ds.getColumnDBColumnName(i) == null) {
						DataType dt = new DataType(ds.getColumnDataType(i));
						String internalName = ds.getColumnInternalName(i);
						String format = ds.getFormat(i);
						BucketDef bd = new BucketDef(internalName, format, dt);
						((LModel) _buckets.getModel()).addElement(bd);
						continue;
					}
					String tableName = ds.getColumnTableName(i);
					String aliasName = null;
					for (int j = 0; j < ds.getAliasCount(); j++) {
						if (ds.getAlias(j) != null && ds.getAlias(j).equals(tableName)) {
							tableName = ds.getTable(j);
							aliasName = ds.getAlias(j);
						}
					}
					ColumnDefinition cDef = new ColumnDefinition(tableName, ds.getColumnDBColumnName(i), ds.getColumnDataType(i), ds.isColumnPrimaryKey(i));

					String internalName = ds.getColumnInternalName(i);
					if (internalName.equals(ds.getColumnDatabaseName(i)))
						internalName = null;

					ColumnDef def = new ColumnDef(cDef, internalName, aliasName, ds.getFormat(i), ds.isUpdateable(i));

					((LModel) _colsInModel.getModel()).addElement(def);

				}

				//order by
				String orderBy = ds.getOrderBy();
				if (orderBy != null) {
					StringTokenizer st = new StringTokenizer(orderBy, ",");
					while (st.hasMoreTokens()) {
						String tok = st.nextToken();
						String col = null;
						String tab = null;
						String dir = "A";
						String alias = null;
						int pos = tok.indexOf(" ");
						if (pos > -1) {
							dir = tok.substring(pos).trim().toUpperCase();
							col = tok.substring(0, pos).trim();
						}
						else
						{
							col = tok;
						}
						pos = col.indexOf(".");
						if (pos > -1) {
							tab = col.substring(0, pos);
							col = col.substring(pos + 1);
						}
						if (tab == null)
							tab = ds.getDefaultTable();

						for (int j = 0; j < ds.getAliasCount(); j++)
							if (ds.getAlias(j) != null && ds.getAlias(j).equals(tab))
								alias = ds.getAlias(j);

						ColumnDefinition def = new ColumnDefinition(tab, col, 0, false);
						SortDef sd = new SortDef(def, alias, dir.startsWith("A"));
						((LModel) _colsInSort.getModel()).addElement(sd);

					}
				}

				//joins
				for (int i = 0; i < ds.getJoinCount(); i++) {
					JoinDef jd = new JoinDef();
					boolean outer = ds.getJoinOuter(i);
					for (int j = 0; j < ds.getJoinColumnCount(i); j++) {
						String left = ds.getJoinLeftColumn(i, j);
						String right = ds.getJoinRightColumn(i, j);
						JoinTermDef jtd = new JoinTermDef(left, right, outer);
						jd.addJoinTerm(jtd);
					}
					((LModel) _joins.getModel()).addElement(jd);
				}

				//validations
				for (int i = 0; i < ds.getColumnCount(); i++) {
					String name = ds.getColumnInternalName(i);
					ValidationRule rules[] = ds.getValidationRulesForColumn(i);
					if (rules != null) {
						for (int j = 0; j < rules.length; j++) {
							if (rules[j].getDsExpression() == null) {
								ValidationRuleDefinition def = new ValidationRuleDefinition();
								def.columnName = name;
								def.rule = rules[j];
								_validations.add(def);
							}
						}
					}

				}
			}
			_qbe.getModel().setSelected(_qbeChecked);
			_qbePanel.setVisible(_qbeChecked);
			_standardPanel.setVisible(!_qbeChecked);
			enableDisableButtons();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("populateFromModel", e, this);
			IDETool.displayError("Error, class must extend com.salmonllc.sql.DataStoreBuffer!", true);
		
		}
	}
	/**
	 * Reset the cancel clicked status button
	 */
	public void resetCancel() {
		_clickedCancel = true;
	}
	/**
	 * Called whenever the value of the selection changes.
	 * @param e the event that characterizes the change.
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == _qbeElements) { 
			Object o[] = _qbeElements.getSelectedValues();
			if (o.length > 0) {
				QBECriteriaDef def = (QBECriteriaDef) o[0];
				_qbeName.setText(def.name);
				_qbeCols.setText(def.colList);
				ComboBoxModel mod =_qbeType.getModel();
				for (int i = 0; i < mod.getSize(); i++) {
					if (((QBEType)mod.getElementAt(i)).type == def.type.type) {
						_qbeType.setSelectedIndex(i);
						break;	
					}		
				}	
			}
		}	
		enableDisableButtons();
	}

	/**
	 * Returns the names of all the columns and buckets in the model
	 */
	public String[] getColumnAndBucketNames() {
		Sorter st = new Sorter();
		if (_qbeChecked) {
			QBECriteriaDef criteriaDef[] = getQBECriteria();
			for (int i = 0; i < criteriaDef.length; i++)
				st.add(criteriaDef[i].name);
		} else {
			ColumnDef columns[] = getColumns();
			BucketDef buckets[] = getBuckets();
			for (int i = 0; i < columns.length; i++)
				st.add(columns[i].getName());
			for (int i = 0; i < buckets.length; i++)
				st.add(buckets[i].name);
			st.sort();
		}
		String ret[] = new String[st.size()];
		st.copyInto(ret);
		return ret;

	}
}
