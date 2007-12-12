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
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.swing.events.ValueChangedEvent;
import com.salmonllc.swing.events.ValueChangedListener;
import com.salmonllc.swing.table.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * SOFIA implementation of a JTable. This JTable allows for binding to columns and expressions in a DataStore, and allows the use of SComponents for editing and display.
 */

public class STable extends JTable implements SComponent {

	private DataStoreBuffer _ds;
	private SComponentHelper _helper;
	private boolean _clickSort = true;
	private boolean _selectCurrentRow = true;
    private Vector _invisibleColumns = new Vector();
	private DataStoreEvaluator _backgroundColorExpression;
	private DataStoreEvaluator _foregroundColorExpression;
	private DataStoreEvaluator _selectedForegroundColorExpression;
	private DataStoreEvaluator _selectedBackgroundColorExpression;

	public STable() {
		super();
		setAutoCreateColumnsFromModel(false);
		_helper = new SComponentHelper(this);
		setAutoSelectCurrentRow(true);
		getTableHeader().addMouseListener(new ListMouseListener());
        setSurrendersFocusOnKeystroke(true);
	}

	/**
	 * Sets the DataStore that this table will use. Call this method before adding any columns to the table
	 */
	public void setDataStore(DataStoreBuffer ds) {
		_ds = ds;
		DataStoreTableModel mod = new DataStoreTableModel(ds);
		mod.setSTable(this);
		setModel(mod);
	}

    /**
     * Adds a column from the datastore to the visual grid
     * @param name The identifier for the column
     * @param columnExpression The expression to evaluate to display on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */
    public STableColumn addDisplayColumn(String name, String columnExpression, String caption, SCheckBox cbx) throws DataStoreException {
		TableColumnModel mod = getColumnModel();
		STableColumn col = new STableColumn(name,this);
		col.setHeaderValue(caption);
		col.setModelIndex(_ds.getColumnIndex(columnExpression));
		CheckBoxCellRenderer rend = new CheckBoxCellRenderer(this, cbx);
		col.setCellRenderer(rend);
		mod.addColumn(col);
		return col;
	}

    /**
     * Adds a column from the datastore to the visual grid
     * @param columnExpression The expression to evaluate to display on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */
    public STableColumn addDisplayColumn(String columnExpression, String caption, SCheckBox cbx) throws DataStoreException {
        return addDisplayColumn(genName(),columnExpression,caption, cbx);
    }

    /**
    * Adds a column from the datastore to the visual grid. It uses an SComboBox to render the column.
    * @param name The identifier for the column
    * @param columnExpression The expression to evaluate to display on the grid
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
    public STableColumn addDisplayColumn(String name,String columnExpression, String caption, SComboBox cbx) throws DataStoreException {
		TableColumnModel mod = getColumnModel();
		STableColumn col = new STableColumn(name,this);
		col.setHeaderValue(caption);
		col.setModelIndex(_ds.getColumnIndex(columnExpression));
		ComboBoxCellRenderer rend = new ComboBoxCellRenderer(this, cbx);
		col.setCellRenderer(rend);
		mod.addColumn(col);
		return col;
	}


    /**
    * Adds a column from the datastore to the visual grid. It uses an SComboBox to render the column.
    * @param columnExpression The expression to evaluate to display on the grid
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
    public STableColumn addDisplayColumn(String columnExpression, String caption, SComboBox cbx) throws DataStoreException {
        return addDisplayColumn(genName(),columnExpression,caption,cbx);
    }



    /**
     * Adds a column from the datastore to the visual grid.
     * @param columnExpression The expression to evaluate to display on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */
    public STableColumn addDisplayColumn(String columnExpression, String caption) throws DataStoreException {
        return addDisplayColumn(genName(),new DataStoreEvaluator(_ds,columnExpression),caption);
    }

    /**
     * Adds a column from the datastore to the visual grid.
     * @param name The identifier for the column
     * @param columnExpression The expression to evaluate to display on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */
    public STableColumn addDisplayColumn(String name,String columnExpression, String caption) throws DataStoreException {
        return addDisplayColumn(name,new DataStoreEvaluator(_ds,columnExpression),caption);
    }


    /**
	 * Adds a column from the datastore to the visual grid.
	 * @param columnExpression The expression to evaluate to display on the grid
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */
	public STableColumn addDisplayColumn(DataStoreExpression columnExpression, String caption) throws DataStoreException {
		return addDisplayColumn(genName(),new DataStoreEvaluator(_ds,columnExpression),caption);
	}

    /**
	 * Adds a column from the datastore to the visual grid.
     * @param name The identifier for the column
	 * @param columnExpression The expression to evaluate to display on the grid
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */
	public STableColumn addDisplayColumn(String name,DataStoreExpression columnExpression, String caption) throws DataStoreException {
		return addDisplayColumn(name,new DataStoreEvaluator(_ds,columnExpression),caption);
	}

    /**
	 * Adds a column from the datastore to the visual grid.
     * @param name The identifier for the column
	 * @param columnExpression The expression to evaluate to display on the grid
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */
	public STableColumn addDisplayColumn(String name,DataStoreEvaluator columnExpression, String caption) throws DataStoreException {
		TableColumnModel mod = getColumnModel();
		STableColumn col = new STableColumn(name,this);
		col.setHeaderValue(caption);
                int modelIndex=-1;
                if (columnExpression.getExpression()!=null)
                    modelIndex = _ds.getColumnIndex(columnExpression.getExpression());
		col.setModelIndex(modelIndex);
		ExpressionCellRenderer rend = new ExpressionCellRenderer(this);
        if (modelIndex == -1)
            rend.setExpression(columnExpression);
        else
            rend.setExpression(columnExpression.getExpression());
		col.setCellRenderer(rend);
		mod.addColumn(col);
		return col;
	}

    /**
	 * Adds a column from the datastore to the visual grid.
	 * @param columnExpression The expression to evaluate to display on the grid
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */
	public STableColumn addDisplayColumn(DataStoreEvaluator columnExpression, String caption) throws DataStoreException {
        return addDisplayColumn(genName(),columnExpression,caption);
    }

    /**
	 * Adds a column from the datastore to the visual grid as an image.
     * @param name The identifier for the column
	 * @param imageExpression The expression to evaluate to get the URL or byte array of the image
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 */
	private STableColumn addDisplayImage(String name,DataStoreEvaluator imageExpression, String caption)  {
		TableColumnModel mod = getColumnModel();
		STableColumn col = new STableColumn(name,this);
		col.setHeaderValue(caption);
		col.setModelIndex(_ds.getColumnIndex(imageExpression.getExpression()));
		ImageCellRenderer rend = new ImageCellRenderer(imageExpression);
		col.setCellRenderer(rend);
		mod.addColumn(col);
		return col;
	}

    /**
	 * Adds a column from the datastore to the visual grid as an image.
	 * @param imageExpression The expression to evaluate to get the URL or byte array of the image
	 * @param caption The caption to display
	 * @return The Table Column object that controls this column
	 */
	private STableColumn addDisplayImage(DataStoreEvaluator imageExpression, String caption)  {
        return addDisplayImage(genName(),imageExpression,caption);
    }

    /**
    * Adds a column from the datastore to the visual grid as an image.
    * @param imageExpression The expression to evaluate to get the URL or byte array of the image
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
   public STableColumn addDisplayImage(String imageExpression, String caption) throws DataStoreException {
       return addDisplayImage(new DataStoreEvaluator(_ds,imageExpression),caption);
   }

    /**
    * Adds a column from the datastore to the visual grid as an image.
    * @param name The name of the column
    * @param imageExpression The expression to evaluate to get the URL or byte array of the image
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
   public STableColumn addDisplayImage(String name,String imageExpression, String caption) throws DataStoreException {
       return addDisplayImage(name,new DataStoreEvaluator(_ds,imageExpression),caption);
   }

    /**
    * Adds a column from the datastore to the visual grid as an image.
    * @param imageExpression The expression to evaluate to get the URL or byte array of the image
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
   public STableColumn addDisplayImage(DataStoreExpression imageExpression, String caption) throws DataStoreException {
       return addDisplayImage(new DataStoreEvaluator(_ds,imageExpression),caption);
   }

    /**
    * Adds a column from the datastore to the visual grid as an image.
    * @param name The name of the column
    * @param imageExpression The expression to evaluate to get the URL or byte array of the image
    * @param caption The caption to display
    * @return The Table Column object that controls this column
    * @throws DataStoreException if the expression is invalid
    */
   public STableColumn addDisplayImage(String name,DataStoreExpression imageExpression, String caption) throws DataStoreException {
       return addDisplayImage(name,new DataStoreEvaluator(_ds,imageExpression),caption);
   }

    /**
     * Adds a column from the datastore to the visual grid. The column can be edited.
     * @param name The internal identifier used in the STable
	 * @param column The name of the database column to display and edit on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */
    public STableColumn addEditColumn(String name, String column, String caption) throws DataStoreException {
		STableColumn col = addDisplayColumn(name, column, caption);
		STextField f = new STextField();
		col.setCellEditor(new SComponentCellEditor(f));
		if (col.getModelIndex() != -1)
			 ((DataStoreTableModel) getModel()).setColumnEditable(col.getModelIndex(), true);
		return col;
	}

    /**
     * Adds a column from the datastore to the visual grid. The column can be edited.
	 * @param column The name of the database column to display and edit on the grid
     * @param caption The caption to display
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */

    public STableColumn addEditColumn(String column, String caption) throws DataStoreException {
        return addEditColumn(column,column, caption);
    }

    /**
     * Adds a column from the datastore to the visual grid. The column can be edited in an SComboBox.
     * @param name The internal identifier used in the STable
     * @param column The name of the database column to display and edit on the grid
     * @param caption The caption to display
     * @param combo The combo box to use as an editor
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */

    public STableColumn addEditColumn(String name, String column, String caption, SComboBox combo) throws DataStoreException {
        STableColumn col = addDisplayColumn(name,column, caption,combo);
        combo.setLightWeightPopupEnabled(false);
        col.setCellEditor(new ComboBoxCellEditor(combo));
        if (col.getModelIndex() != -1)
             ((DataStoreTableModel) getModel()).setColumnEditable(col.getModelIndex(), true);
        return col;
    }

    /**
     * Adds a column from the datastore to the visual grid. The column can be edited in an SComboBox.
     * @param column The name of the database column to display and edit on the grid
     * @param caption The caption to display
     * @param combo The combo box to use as an editor
     * @return The Table Column object that controls this column
     * @throws DataStoreException if the expression is invalid
     */

    public STableColumn addEditColumn(String column, String caption, SComboBox combo) throws DataStoreException {
        return addEditColumn(column, column, caption, combo);
    }

	/**
	 * Adds a column from the datastore to the visual grid. The column can be edited using an SCheckBox.
     * @param name The internal identifier used in the STable
     * @param column The name of the database column to display and edit on the grid
	 * @param caption The caption to display
	 * @param cbx The combo box to use as an editor
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */

	public STableColumn addEditColumn(String name,String column, String caption, SCheckBox cbx) throws DataStoreException {
		STableColumn col = addDisplayColumn(name,column, caption, cbx);
		col.setCellEditor(new CheckBoxCellEditor(cbx));
		cbx.setBackground(getBackground());
		if (col.getModelIndex() != -1)
			 ((DataStoreTableModel) getModel()).setColumnEditable(col.getModelIndex(), true);
		return col;
	}


    /**
	 * Adds a column from the datastore to the visual grid. The column can be edited using an SCheckBox.
	 * @param column The column display and edit on the grid
	 * @param caption The caption to display
	 * @param cbx The combo box to use as an editor
	 * @return The Table Column object that controls this column
	 * @throws DataStoreException if the expression is invalid
	 */

	public STableColumn addEditColumn(String column, String caption, SCheckBox cbx) throws DataStoreException {
	    return addEditColumn(column, column, caption, cbx);
    }

	/**
	 * This method is used by the framework and should not be called directly
	 */
	public ValueChangedEvent generateValueChangedEvent() {
		int row = editingRow;
		int col = editingColumn;
		try {
			String oldVal = _ds.getFormattedString(row, col);
			String newVal = getCellEditor().getCellEditorValue().toString();
			return new ValueChangedEvent(this, oldVal, newVal, _ds, row, col);
		} catch (DataStoreException ex) {
			ex.printStackTrace();
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
	 * Returns whether or not the table can be sorted by clicking on a column
	 */
	public boolean getClickSort() {
		return _clickSort;
	}

	/**
	 * Sets whether or not the table can be sorted by clicking on a column
	 */
	public void setClickSort(boolean clickSort) {
		_clickSort = clickSort;
	}

    /**
     * Returns true if the STable row selection is bound to the current row in the DataStore. If so, the selected row in the STable will be the same as the current row in the DataStore.
     */
	public boolean getAutoSelectCurrentRow() {
		return _selectCurrentRow;
	}

    /**
     * Sets whether or not the STable row selection is bound to the current row in the DataStore. If so, the selected row in the STable will be the same as the current row in the DataStore.
     */
	public void setAutoSelectCurrentRow(boolean selectCurrentRow) {
		_selectCurrentRow = selectCurrentRow;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Selects a row (single selection only)
	 */
	public void selectRow(int row) {
		clearSelection();
        boolean oldAutoScroll = getAutoscrolls();
        setAutoscrolls(true);
        changeSelection(row,-1,false,false);
        setAutoscrolls(oldAutoScroll);
	}

	/**
	 * Sets the table's selection mode to allow only single selections, a single
	 * contiguous interval, or multiple intervals.
	 * <P>
	 * <bold>Note:</bold>
	 * <code>JTable</code> provides all the methods for handling
	 * column and row selection.  When setting states,
	 * such as <code>setSelectionMode</code>, it not only
	 * updates the mode for the row selection model but also sets similar
	 * values in the selection model of the <code>columnModel</code>.
	 * If you want to have the row and column selection models operating
	 * in different modes, set them both directly.
	 * <p>
	 * Both the row and column selection models for <code>JTable</code>
	 * default to using a <code>DefaultListSelectionModel</code>
	 * so that <code>JTable</code> works the same way as the
	 * <code>JList</code>. See the <code>setSelectionMode</code> method
	 * in <code>JList</code> for details about the modes.
	 *
	 * @see JList#setSelectionMode
	 */
	public void setSelectionMode(int selectionMode) {
		super.setSelectionMode(selectionMode);
		if (selectionMode != ListSelectionModel.SINGLE_SELECTION)
			setAutoSelectCurrentRow(false);
	}

     /**
     * Adds the validations for a column in the datastore to a table column. The icon will display if ther are any validation errors for the specified column and the tooltip will show the error message.
      * @param tabCol The column to set the expression for
      * @param columnName The name of the column in the datastore
      * @param icon the icon to show if a rule is volated
     */
    public void addColumnValidations(TableColumn tabCol,String columnName, Icon icon)  {
         ExpressionCellRenderer rend = (ExpressionCellRenderer) tabCol.getCellRenderer();
         rend.addColumnValidations(columnName,icon);
    }

     /**
     * Adds the validations for a column in the datastore to a table column. This method will bind to the column that the TableColumn is already bound to.
      * @param tabCol The column to set the expression for
      * @param icon the icon to show if a rule is volated
     */
    public void addColumnValidations(TableColumn tabCol, Icon icon)  {
         ExpressionCellRenderer rend = (ExpressionCellRenderer) tabCol.getCellRenderer();
         if (rend.getColName() != null)
            rend.addColumnValidations(rend.getColName(),icon);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @param tabCol The TableColumn object to set the expression for number in the stable
     * @param exp The expression to evaluate
     * @param message The message to display if the rule is false
     * @param icon the icon to show if a rule is volated
     * @throws DataStoreException
     */
    public void addValidateExpression(TableColumn tabCol,DataStoreEvaluator exp, String message, Icon icon) throws DataStoreException {
        ExpressionCellRenderer rend = (ExpressionCellRenderer) tabCol.getCellRenderer();
        rend.addValidateExpression(exp,message,icon);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @param tabCol The TableColumn object to set the expression for number in the stable
     * @param exp The expression to evaluate
     * @param message The message to display if the rule is false
     * @param icon the icon to show if a rule is volated
     * @throws DataStoreException
     */
    public void addValidateExpression(TableColumn tabCol,String exp, String message, Icon icon) throws DataStoreException {
        ExpressionCellRenderer rend = (ExpressionCellRenderer) tabCol.getCellRenderer();
        rend.addValidateExpression(exp,message,icon);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @param tabCol The TableColumn object to set the expression for number in the stable
     * @param exp The expression to evaluate
     * @param message The message to display if the rule is false
     * @param icon the icon to show if a rule is volated
     * @throws DataStoreException
     */
    public void addValidateExpression(TableColumn tabCol,DataStoreExpression exp, String message, Icon icon) throws DataStoreException {
        ExpressionCellRenderer rend = (ExpressionCellRenderer) tabCol.getCellRenderer();
        rend.addValidateExpression(exp,message,icon);
    }

    /**
     * Returns the table column object at the specified column index
     */
    public TableColumn getTableColumn(int tableColNo) {
         return getColumnModel().getColumn(tableColNo);
    }

    //inner class that handles click sorting
	private class ListMouseListener extends MouseAdapter {
		Object _lastSort = null;
		int _lastDir = DataStoreBuffer.SORT_ASC;

		public void mouseClicked(MouseEvent e) {
			if (!_clickSort)
				return;
			if (isEditing())
				getCellEditor().stopCellEditing();
			TableColumnModel columnModel = getColumnModel();
			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
			TableColumn col = columnModel.getColumn(viewColumn);

			int colNo = convertColumnIndexToModel(viewColumn);
			try {
				if (colNo != -1) {
					_ds.sort(colNo, getDir(new Integer(colNo)));
				} else {
					ExpressionCellRenderer rend = (ExpressionCellRenderer) col.getCellRenderer();
					DataStoreEvaluator eval = rend.getEvaluator();
					_ds.sort(eval, getDir(eval));
				}
			} catch (Exception ex) {
			}
			if (_ds.gotoFirst()) {
				getSelectionModel().clearSelection();
				getSelectionModel().setAnchorSelectionIndex(0);
				getSelectionModel().setLeadSelectionIndex(0);
			}
		}

		int getDir(Object o) {
			if (_lastSort == null) {
				_lastDir = DataStoreBuffer.SORT_ASC;
			} else if (o instanceof Integer && _lastSort instanceof Integer) {
				if (o.equals(_lastSort)) {
					if (_lastDir == DataStoreBuffer.SORT_ASC)
						_lastDir = DataStoreBuffer.SORT_DES;
					else
						_lastDir = DataStoreBuffer.SORT_ASC;
				} else
					_lastDir = DataStoreBuffer.SORT_ASC;
			} else if (o instanceof DataStoreEvaluator && _lastSort instanceof DataStoreEvaluator) {
				if (o == _lastSort) {
					if (_lastDir == DataStoreBuffer.SORT_ASC)
						_lastDir = DataStoreBuffer.SORT_DES;
					else
						_lastDir = DataStoreBuffer.SORT_ASC;
				} else
					_lastDir = DataStoreBuffer.SORT_ASC;
			} else
				_lastDir = DataStoreBuffer.SORT_ASC;

			_lastSort = o;
			return _lastDir;

		}
	};

    private String genName() {
        return "column" + getColumnCount();
    }

    /**
     * Makes a column with the specified name invisible
     * @param name The name of the column to hide
     */
    public void hideColumn(String name) {
        TableColumnModel mod = getColumnModel();
        try {
            int ndx = mod.getColumnIndex(name);
            if (ndx > -1) {
                STableColumn col = (STableColumn) mod.getColumn(ndx);
                mod.removeColumn(col);
                _invisibleColumns.add(col);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes a column visible and adds it to the end of the table
     */
    public void showColumn(String name) {
        TableColumnModel mod = getColumnModel();
        for (int i = 0; i < _invisibleColumns.size(); i++) {
            STableColumn col = (STableColumn) _invisibleColumns.elementAt(i);
            if (col != null && col.getName().equals(name)) {
                mod.addColumn(col);
                _invisibleColumns.removeElementAt(i);
                return;
            }
        }
    }

    /**
     * Move the named column to a new position on the table
     */
    public void moveColumn(String name, int newIndex) {
        TableColumnModel mod = getColumnModel();
        try {
            int ndx = mod.getColumnIndex(name);
            mod.moveColumn(ndx,newIndex);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the index of the named column or -1 if not found
     */
    public int getColumnIndex(String name) {
        TableColumnModel mod = getColumnModel();
        try {
            return mod.getColumnIndex(name);

        }
        catch (Exception e) {
            return -1;
        }
    }

    /**
     *  Sets the width for a column. Autoresize must be set to off for this to work.
     */
    public void setColumnWidth(String name, int width) {
        TableColumnModel mod = getColumnModel();
        try {
            TableColumn col = mod.getColumn(mod.getColumnIndex(name));
            col.setPreferredWidth(width);
            col.setWidth(width);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

   /**
    * Sets a column to editable, the column must have been added with an addEditColumn method for this to have an effect
    */
   public void setColumnEditable(String name, boolean editable) {
       TableColumnModel mod = getColumnModel();
       try {
           if (isEditing()) {
                DefaultCellEditor ed = (DefaultCellEditor) getCellEditor();
                ed.stopCellEditing();
           }

           STableColumn col = (STableColumn) mod.getColumn(mod.getColumnIndex(name));
           col.setEditable(editable);
       }
       catch (Exception e) {
           e.printStackTrace();
       }
   }

    /**
     * Returns true if the named column is editable
     */
    public boolean isColumnEditable(String name) {
        TableColumnModel mod = getColumnModel();
        try {
            STableColumn col = (STableColumn) mod.getColumn(mod.getColumnIndex(name));
            return col.isEditable();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns a list of all the columns in the table
     */
    public STableColumn[] getColumns() {
        TableColumnModel mod = getColumnModel();
        int count = mod.getColumnCount();
        STableColumn ret[] = new STableColumn[count];
        for (int i = 0; i < count; i++)
            ret[i] = (STableColumn) mod.getColumn(i);
        return ret;
    }
    
        /**
     * Updates the selection models of the table, depending on the state of the
     * two flags: <code>toggle</code> and <code>extend</code>. All changes
     * to the selection that are the result of keyboard or mouse events received
     * by the UI are channeled through this method so that the behavior may be
     * overridden by a subclass.
     * <p>
     * This implementation uses the following conventions:
     * <ul>
     * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>: <em>false</em>.
     *      Clear the previous selection and ensure the new cell is selected.
     * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>: <em>true</em>.
     *      Extend the previous selection to include the specified cell.
     * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>: <em>false</em>.
     *      If the specified cell is selected, deselect it. If it is not selected, select it.
     * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>: <em>true</em>.
     *      Leave the selection state as it is, but move the anchor index to the specified location.
     * </ul>
     * @param  rowIndex   affects the selection at <code>row</code>
     * @param  columnIndex  affects the selection at <code>column</code>
     * @param  toggle  see description above
     * @param  extend  if true, extend the current selection
     *
     */
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
 			SComponentHelper.acceptCurrentValue();
 			super.changeSelection(rowIndex,columnIndex,toggle,extend);
    }
    
	
	/* (non-Javadoc)
	 * @see javax.swing.JTable#editCellAt(int, int)
	 */
	public boolean editCellAt(int row, int column) {
		boolean ret = super.editCellAt(row, column);
		if (ret) {
			getEditorComponent().requestFocus();
		}		
		return ret;
	}


	/* (non-Javadoc)
	 * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
	 */
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean ret = super.editCellAt(row, column,e);
		if (ret)
			getEditorComponent().requestFocus();
		return ret;
	}


	/**
	 * Set a datastore expression for the selected background color of each row. The expression must return a String in the form "red,green,blue" 
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setSelectedBackgroundColorExpression(String expression) throws DataStoreException {
		_selectedBackgroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}	
	
	/**
	 * Set a datastore expression for the selected background color of each row. The expression must return a Color object or a String in the form "red,green,blue"
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setSelectedBackgroundColorExpression(DataStoreExpression expression) throws DataStoreException {
		_selectedBackgroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}
	
	/**
	 * Set a datastore expression for the selected foreground color of each row. The expression must return a String in the form "red,green,blue" 
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setSelectedForegroundColorExpression(String expression) throws DataStoreException {
		_selectedForegroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}	
	
	/**
	 * Set a datastore expression for the selected foreground color of each row. The expression must return a Color object or a String in the form "red,green,blue"
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setSelectedForegroundColorExpression(DataStoreExpression expression) throws DataStoreException {
		_selectedForegroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}		

	/**
	 * Set a datastore expression for the background color of each row. The expression must return a String in the form "red,green,blue" 
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setBackgroundColorExpression(String expression) throws DataStoreException {
		_backgroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}	
	
	/**
	 * Set a datastore expression for the background color of each row. The expression must return a Color object or a String in the form "red,green,blue"
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setBackgroundColorExpression(DataStoreExpression expression) throws DataStoreException {
		_backgroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}
	
	/**
	 * Set a datastore expression for the foreground color of each row. The expression must return a String in the form "red,green,blue" 
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setForegroundColorExpression(String expression) throws DataStoreException {
		_foregroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}	
	
	/**
	 * Set a datastore expression for the foreground color of each row. The expression must return a Color object or a String in the form "red,green,blue"
	 * @param expression
	 * @throws DataStoreException
	 */
	public void setForegroundColorExpression(DataStoreExpression expression) throws DataStoreException {
		_foregroundColorExpression = new DataStoreEvaluator(_ds, expression);
	}		

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getSelectionBackground()
	 */
	public Color getSelectionBackground(int row) {
		Color c = getColor(_selectedBackgroundColorExpression,row);
		if (c == null)
			return super.getSelectionBackground();
		else
			return c;	
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getSelectionForeground()
	 */
	public Color getSelectionForeground(int row) {
		Color c = getColor(_selectedForegroundColorExpression,row);
		if (c == null)
			return super.getSelectionForeground();
		else
			return c;	
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground(int row) {
		Color c = getColor(_backgroundColorExpression,row);
		if (c == null)
			return super.getBackground();
		else
			return c;	
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getForeground()
	 */
	public Color getForeground(int row) {
		Color c = getColor(_foregroundColorExpression,row);
		if (c == null)
			return super.getForeground();
		else
			return c;	
	}

	private Color getColor(DataStoreEvaluator eval, int row) {
		if (eval == null)
			return null;
		Object ret;
		try {
			ret = eval.evaluateRow(row);
		} catch (DataStoreException e1) {
			return null;
		}
		if (ret == null)
			return null;
		if (ret instanceof Color)
			return (Color) ret;
		String col = "";
		if (ret instanceof String)
			col = (String) ret;		
		if (col.indexOf(",") == -1)
			return null;
		StringTokenizer tok = new StringTokenizer(col, ",");
		try {
			int red = Integer.parseInt(tok.nextToken().trim());
			int green = Integer.parseInt(tok.nextToken().trim());
			int blue = Integer.parseInt(tok.nextToken().trim());
			return new Color(red, green, blue);
		} catch (Exception e) {
			return null;
		}
	}
}
