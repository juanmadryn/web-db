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

package com.salmonllc.swing.table;

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.swing.STable;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A cell renderer that displays an icon and/or tooltip for a table column if a boolean expression is true for the row.
 */
public class ExpressionCellRenderer extends DefaultTableCellRenderer {
    private DataStoreBuffer _ds;
    private Vector _eval;
    private DataStoreEvaluator _mainEval;
    protected STable _tab;
    private static String _colExp="$col$";
    private String _colName = null;

    /**
     * Creates a <code>SLabel</code> instance with
     * no image and with an empty string for the title.
     * The label is centered vertically
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge
     * of the label's display area.
     */
    public ExpressionCellRenderer(STable tab) {
        super();
        _ds=((DataStoreTableModel)tab.getModel()).getDataStore();
        _eval = new Vector();
        _tab = tab;
    }

    /**
     * Sets the expression that will be computed to display this cell
     */
    public void setExpression(DataStoreEvaluator exp) {
        _mainEval = exp;
    }

    /**
     * Sets the expression that will be computed to display this cell
     */
    public void setExpression(DataStoreExpression exp) throws DataStoreException {
        setExpression(new DataStoreEvaluator(_ds,exp));
    }

    /**
     * Sets the expression that will be computed to display this cell
     */
    public void setExpression(String exp) throws DataStoreException {
        if (_ds.getColumnIndex(exp) == -1)
            setExpression(new DataStoreEvaluator(_ds,exp));
        else
            _colName = exp;
    }

     /**
     * Adds the validations for a column in the datastore to this renderer
     */
    public void addColumnValidations(String column, Icon icon)  {
        Object o[] = new Object[3];
        o[0]=column;
        o[1]=_colExp;
        o[2]=icon;
        _eval.add(o);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @throws DataStoreException
     */
    public void addValidateExpression(DataStoreEvaluator exp, String message, Icon icon) throws DataStoreException {
        Object o[] = new Object[3];
        o[0]=exp;
        o[1]=message;
        o[2]=icon;
        _eval.add(o);
    }

    /**
     * Displays the icon and message for each cell where the expression evaluates to false
     * @throws DataStoreException
     */
    public void addValidateExpression(String exp, String message, Icon icon) throws DataStoreException {
        Object o[] = new Object[3];
        o[0]=new DataStoreEvaluator(_ds,exp);
        o[1]=message;
        o[2]=icon;
        _eval.add(o);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @throws DataStoreException
     */
    public void addValidateExpression(DataStoreExpression exp, String message, Icon icon) throws DataStoreException {
        Object o[] = new Object[3];
        o[0]=new DataStoreEvaluator(_ds,exp);
        o[1]=message;
        o[2]=icon;
        _eval.add(o);
    }

	public Component getRenderer() {
		return getTableCellRendererComponent(_tab,null,false,false,0,0);	
	}	
	
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
           if (comp instanceof JLabel) {
            try {
                if (_mainEval != null) {
                    String val = _mainEval.evaluateRowFormat(row);
                    ((JLabel) comp).setText(val);
                }
                Icon icon = null;
                String toolTip = null;

                for (int i = 0; i < _eval.size(); i++) {
                    Object o[] = (Object[])_eval.elementAt(i);
                    if (o[1] == _colExp) {
                        int colIndex = _ds.getColumnIndex((String)o[0]);
                        if (colIndex != -1) {
                            DataStoreException ex[] = _ds.validateColumn(row,colIndex,null);
                            if (ex.length > 0) {
                                icon = (Icon) o[2];
                                toolTip = ex[0].getMessage();
                                break;
                            }
                        }
                    }
                    else if (o[0] != null) {
                        Object ret = ((DataStoreEvaluator)o[0]).evaluateRow(row);
                        if ((ret instanceof Boolean) && ! ((Boolean)ret).booleanValue()) {
                             icon = (Icon) o[2];
                             toolTip = (String) o[1];
                             break;
                        }
                    }
                }


                ((JLabel) comp).setIcon(icon);
                ((JLabel) comp).setToolTipText(toolTip);
                if (!isSelected) {
					((JLabel) comp).setBackground(_tab.getBackground(row));
					((JLabel) comp).setForeground(_tab.getForeground(row));
                }	
                else {
					((JLabel) comp).setBackground(_tab.getSelectionBackground(row));
					((JLabel) comp).setForeground(_tab.getSelectionForeground(row));
                }	   
            } catch (DataStoreException ex) {}
        }
        int col = _tab.getColumnModel().getColumn(column).getModelIndex();
        if (hasFocus && table.getModel().isCellEditable(row,col))
       	    table.editCellAt(row,column);
        return comp;
    }

    /**
     * Returns the DataStoreEvaluator for this cell
     */
     public DataStoreEvaluator getEvaluator() {
         return _mainEval;
     }
    /**
     * If this renderer is bound to a particular column in a datastore then this returns the name of it
     * @return
     */
    public String getColName() {
        return _colName;
    }

}
