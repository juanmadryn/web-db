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

import com.salmonllc.sql.DataDictionary;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.sql.ValidationRule;
import com.salmonllc.swing.SComboBox;
import com.salmonllc.swing.SComponentHelper;
import com.salmonllc.swing.SOption;
import com.salmonllc.swing.STable;
import com.salmonllc.swing.STextField;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author  demian
 */
public class ValidationDialog extends JDialog implements ActionListener, ModelChangedListener {

	/**
	 * @uml.property  name="_rules"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	ValidationRuleDefinition[] _rules;
	STable _rulesTable;
	DataStoreBuffer _rulesDs;
	JButton _ok;
	JButton _cancel;
	JButton _addRule;
	JButton _deleteRule;
	Box _expressionBox;
	Box _minValBox;
	Box _maxValBox;
	Box _lookupTableBox;
	Box _lookupDescBox;
	Box _lookupExpBox;
	Box _lookupBucketBox;
	JPanel _detailGrid;

	DataDictionary _dd;
	boolean _clickedCancel = true;
	private String _ruleDesc[] = { "Expression", "JavaScript", "Lookup", "Range", "Regular Expression", "Required", "Type Check" };
	private int _ruleType[] = { ValidationRule.TYPE_EXPRESSION, ValidationRule.TYPE_JAVASCRIPT, ValidationRule.TYPE_LOOKUP, ValidationRule.TYPE_RANGE, ValidationRule.TYPE_REGULAR_EXPRESSION, ValidationRule.TYPE_REQUIRED, ValidationRule.TYPE_TYPE_CHECK };

	public static final String COL_RULE_TYPE = "RuleType";
	public static final String COL_COL_NAME = "ColumnName";
	public static final String COL_ERROR_MESSAGE = "ErrorMessage";
	public static final String COL_RULE_EXP = "RuleExpression";
	public static final String COL_MIN_VAL = "MinValue";
	public static final String COL_MAX_VAL = "MaxValue";
	public static final String COL_LOOKUP_TABLE = "LookupTable";
	public static final String COL_LOOKUP_EXPRESSION = "LookupExpression";
	public static final String COL_LOOKUP_DESC_COL = "LookupDescCol";
	public static final String COL_LOOKUP_DESC_BUCKET = "LookupDescBucket";

	private class RuleResolver implements DataStoreExpression {
		public Object evaluateExpression(DataStoreBuffer dsBuf, int row) throws DataStoreException {
			int i = dsBuf.getInt(row, COL_RULE_TYPE);
			for (int j = 0; j < _ruleType.length; j++) {
				if (_ruleType[j] == i)
					return _ruleDesc[j];
			}
			return null;
		}
	}

	public ValidationDialog(Frame owner, ValidationRuleDefinition[] def, String colNames[], DataDictionary dd, int buttonHeight) {
		super(owner, "Validations", true);
		_dd = dd;
		int width = 700;
		int height = 400;
		Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (frameBounds.width - width) / 2;
		int y = (frameBounds.height - height) / 2;

		setBounds(x, y, width, height);
		setResizable(false);
		setModal(true);

		_rules = def;

		//build a datastore to hold the rules
		_rulesDs = new DataStoreBuffer();
		_rulesDs.addBucket(COL_RULE_TYPE, DataStoreBuffer.DATATYPE_INT);
		_rulesDs.addBucket(COL_COL_NAME, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_ERROR_MESSAGE, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_RULE_EXP, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_MIN_VAL, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_MAX_VAL, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_LOOKUP_TABLE, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_LOOKUP_EXPRESSION, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_LOOKUP_DESC_COL, DataStoreBuffer.DATATYPE_STRING);
		_rulesDs.addBucket(COL_LOOKUP_DESC_BUCKET, DataStoreBuffer.DATATYPE_STRING);

		_rulesDs.insertRow();

		//main box
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(new EmptyBorder(10, 10, 10, 10));

		//row1, column headings
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(DialogComponentFactory.makeLabel("Validations in DataStore:", 345));
		box.add(Box.createHorizontalGlue());
		box.add(DialogComponentFactory.makeLabel("Selected Validation:", 218));
		box.add(_addRule = DialogComponentFactory.makeButton("Add", 55, buttonHeight));
		box.add(_deleteRule = DialogComponentFactory.makeButton("Del", 55, buttonHeight));

		main.add(box);

		//row2, screens
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		_rulesTable = new STable();
		_rulesTable.setDataStore(_rulesDs);
		try {
			_rulesTable.addDisplayColumn(new RuleResolver(), "Type");
			_rulesTable.addDisplayColumn(COL_COL_NAME, "Column Name");
			_rulesTable.addDisplayColumn(COL_ERROR_MESSAGE, "Error Message");
		} catch (DataStoreException e) {
			e.printStackTrace();
		}
		box.add(DialogComponentFactory.makeScrollPane(345, 240, _rulesTable));
		box.add(Box.createRigidArea(new Dimension(5, 0)));

		//detail screen
		JPanel box2 = new JPanel();
		box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
		box.add(box2);
		box2.add(Box.createRigidArea(new Dimension(0, 10)));
		_detailGrid = box2;

		//rule types
		SComboBox ruleTypesEdit = new SComboBox();
		ruleTypesEdit.setColumn(_rulesDs, COL_RULE_TYPE);
		for (int i = 0; i < _ruleDesc.length; i++)
			ruleTypesEdit.addItem(new SOption(new Integer(_ruleType[i]).toString(), _ruleDesc[i]));
		addFieldToGrid("Rule Type", ruleTypesEdit, box2);

		//column list
		SComboBox ruleColumnEdit = new SComboBox();
		ruleColumnEdit.setColumn(_rulesDs, COL_COL_NAME);

		for (int i = 0; i < colNames.length; i++)
			ruleColumnEdit.addItem(colNames[i]);
		addFieldToGrid("Column", ruleColumnEdit, box2);

		//error message
		STextField ruleErrorEdit = new STextField();
		ruleErrorEdit.setColumn(_rulesDs, COL_ERROR_MESSAGE);
		addFieldToGrid("Error Message", ruleErrorEdit, box2);

		//expression
		STextField ruleExp = new STextField();
		ruleExp.setColumn(_rulesDs, COL_RULE_EXP);
		_expressionBox = addFieldToGrid("Expression", ruleExp, box2);

		//minimum value and maximum value
		STextField minValExp = new STextField();
		minValExp.setColumn(_rulesDs, COL_MIN_VAL);
		_minValBox = addFieldToGrid("Min Value", minValExp, box2);
		STextField maxValExp = new STextField();
		maxValExp.setColumn(_rulesDs, COL_MAX_VAL);
		_maxValBox = addFieldToGrid("Max Value", maxValExp, box2);

		//lookup fields
		STextField lookupTable = new STextField();
		lookupTable.setColumn(_rulesDs, COL_LOOKUP_TABLE);
		_lookupTableBox = addFieldToGrid("Lookup Table", lookupTable, box2);

		STextField lookupWhereExpression = new STextField();
		lookupWhereExpression.setColumn(_rulesDs, COL_LOOKUP_EXPRESSION);
		_lookupExpBox = addFieldToGrid("Where Exp", lookupWhereExpression, box2);

		STextField lookupDescCol = new STextField();
		lookupDescCol.setColumn(_rulesDs, COL_LOOKUP_DESC_COL);
		_lookupDescBox = addFieldToGrid("Desc Source Col", lookupDescCol, box2);

		SComboBox lookupDescBucket = new SComboBox();
		lookupDescBucket.setColumn(_rulesDs, COL_LOOKUP_DESC_BUCKET);
		_lookupBucketBox = addFieldToGrid("Desc Dest Bucket", lookupDescBucket, box2);

		lookupDescBucket.addItem(new SOption(null, ""));
		for (int i = 0; i < colNames.length; i++)
			lookupDescBucket.addItem(colNames[i]);

		box2.add(Box.createVerticalGlue());

		main.add(box);
		main.add(Box.createRigidArea(new Dimension(0, 5)));

		//row 3
		_ok = new JButton("OK");
		_ok.addActionListener(this);
		_cancel = new JButton("Cancel");
		_cancel.addActionListener(this);
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		buttonBar.add(_ok = new JButton("OK"));
		buttonBar.add(_cancel = new JButton("Cancel"));
		main.add(buttonBar);

		//populate rule ds
		_rulesDs.reset();
		for (int i = 0; i < def.length; i++) {
			_rulesDs.insertRow();
			try {
				_rulesDs.setInt(COL_RULE_TYPE, def[i].rule.getRuleType());
				_rulesDs.setString(COL_COL_NAME, def[i].columnName);
				_rulesDs.setString(COL_ERROR_MESSAGE, def[i].rule.getErrorMessage());
				_rulesDs.setString(COL_RULE_EXP, def[i].rule.getExpression());
				Object o = def[i].rule.getMinValue();
				_rulesDs.setString(COL_MIN_VAL, o == null ? null : o.toString());
				o = def[i].rule.getMaxValue();
				_rulesDs.setString(COL_MAX_VAL, o == null ? null : o.toString());
				_rulesDs.setString(COL_LOOKUP_TABLE, def[i].rule.getLookupExpression());
				_rulesDs.setString(COL_LOOKUP_DESC_COL, def[i].rule.getDesciptionColumn());
				_rulesDs.setString(COL_LOOKUP_DESC_BUCKET, def[i].rule.getDescriptionBucket());
				_rulesDs.setString(COL_LOOKUP_EXPRESSION, def[i].rule.getLookupExpression());

			} catch (DataStoreException e1) {
				e1.printStackTrace();
			}
		}
		_rulesDs.resetStatus();

		//Add Listeners
		_ok.addActionListener(this);
		_cancel.addActionListener(this);
		_addRule.addActionListener(this);
		_deleteRule.addActionListener(this);

		//Add it to the screen
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cp.add(main);
		cp.add(Box.createVerticalGlue());
		cp.add(buttonBar);
		cp.add(Box.createRigidArea(new Dimension(0, 20)));
		enableDisableButtons();
		_rulesDs.addModelChangedListener(this);
		if (_rulesDs.getRowCount() == 0)
			_rulesDs.insertRow();
		else
			_rulesDs.gotoFirst();
		modelChanged(new ModelChangedEvent(_rulesDs,1));
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _cancel) {
			_clickedCancel = true;
			setVisible(false);
		} else if (e.getSource() == _ok) {
			_clickedCancel = false;
			setVisible(false);
		} else if (e.getSource() == _addRule) {
			_rulesDs.insertRow();
		} else if (e.getSource() == _deleteRule) {
			_rulesDs.deleteRow();
		}

	}

	private void enableDisableButtons() {
		_deleteRule.setEnabled(_rulesDs.getRowCount() > 0);
	}

	private Box addFieldToGrid(String label, JComponent comp, JPanel mainGrid) {
		Dimension capSize = new Dimension(100, 25);
		Dimension fieldSize = new Dimension(225, 25);

		JLabel l = DialogComponentFactory.makeLabel(label + ":");
		l.setMaximumSize(capSize);
		l.setMinimumSize(capSize);

		Box b = Box.createHorizontalBox();
		b.add(l);
		comp.setMaximumSize(fieldSize);
		comp.setMinimumSize(fieldSize);
		b.add(comp);

		Box b2 = Box.createVerticalBox();
		b2.add(b);
		b2.add(Box.createRigidArea(new Dimension(0, 5)));
		mainGrid.add(b2);
		return b2;

	}

	public void modelChanged(ModelChangedEvent evt) {
		boolean enabled = _rulesDs.getRowCount() > 0;
		SComponentHelper.setAllComponentsEnabled(_detailGrid, enabled);
		if (_rulesDs.getRow() == -1 || _rulesDs.getRow() >= _rulesDs.getRowCount())
			return;
		int type;
		try {
			type = _rulesDs.getInt(COL_RULE_TYPE);
			_expressionBox.setVisible(type == ValidationRule.TYPE_EXPRESSION || type == ValidationRule.TYPE_REGULAR_EXPRESSION || type == ValidationRule.TYPE_JAVASCRIPT);
			_maxValBox.setVisible(type == ValidationRule.TYPE_RANGE);
			_minValBox.setVisible(type == ValidationRule.TYPE_RANGE);
			_lookupBucketBox.setVisible(type == ValidationRule.TYPE_LOOKUP);
			_lookupDescBox.setVisible(type == ValidationRule.TYPE_LOOKUP);
			_lookupExpBox.setVisible(type == ValidationRule.TYPE_LOOKUP);
			_lookupTableBox.setVisible(type == ValidationRule.TYPE_LOOKUP);

		} catch (DataStoreException e) {
			e.printStackTrace();
		}
		enableDisableButtons();
	}

	public ValidationRuleDefinition[] getValidationRules() {
		ArrayList list = new ArrayList();
		for (int i = 0; i < _rulesDs.getRowCount(); i++) {
			try {
				if (_rulesDs.getAny(i, COL_RULE_TYPE) != null && _rulesDs.getAny(i, COL_COL_NAME) != null) {
					int type = _rulesDs.getInt(i, COL_RULE_TYPE);
					String colName = _rulesDs.getString(i, COL_COL_NAME);
					String errorMessage = _rulesDs.getString(i, COL_ERROR_MESSAGE);
					ValidationRule rule = null;
					if (type == ValidationRule.TYPE_EXPRESSION || type == ValidationRule.TYPE_REGULAR_EXPRESSION || type == ValidationRule.TYPE_JAVASCRIPT)
						rule = new ValidationRule(_rulesDs.getString(i, COL_RULE_EXP), errorMessage, type);
					else if (type == ValidationRule.TYPE_TYPE_CHECK || type == ValidationRule.TYPE_REQUIRED)
						rule = new ValidationRule(errorMessage, type);
					else if (type == ValidationRule.TYPE_RANGE)
						rule = new ValidationRule(_rulesDs.getString(i, COL_MIN_VAL), _rulesDs.getString(i, COL_MAX_VAL), errorMessage);
					else if (type == ValidationRule.TYPE_LOOKUP)
						rule = new ValidationRule(_rulesDs.getString(i, COL_LOOKUP_TABLE), _rulesDs.getString(i, COL_LOOKUP_EXPRESSION), _rulesDs.getString(i, COL_LOOKUP_DESC_COL), _rulesDs.getString(i, COL_LOOKUP_DESC_BUCKET), errorMessage);

					if (rule != null) {
						ValidationRuleDefinition def = new ValidationRuleDefinition();
						def.columnName = colName;
						def.rule = rule;
						list.add(def);
					}
				}
			} catch (DataStoreException e) {
				e.printStackTrace();
			}
		}
		ValidationRuleDefinition[] ret = new ValidationRuleDefinition[list.size()];
		list.toArray(ret);
		return ret;
	}
	/**
	 * @return whether or not the user clicked cancel to close the dialog
	 */
	public boolean isClickedCancel() {
		return _clickedCancel;
	}

}
