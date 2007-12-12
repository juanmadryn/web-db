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

import com.salmonllc.sql.*;
import com.salmonllc.swing.events.ValueChangedEvent;
import java.util.Vector;
import javax.swing.*;

/**
 * SOFIA implementation of a Swing Label. This label allows binding to a DataStore column for the value and binding to DataStore validations to display error icons.
 */
public class SLabel extends JLabel implements ModelChangedListener, SComponent {
    private DataStoreEvaluator _eval;
    private Vector _exp;
    private static String _colExp="$col$";
    private DataStoreBuffer _valDs;
    private Icon _errorIcon;
    private String _errorMsg;

    /**
     * Creates a <code>SLabel</code> instance with
     * no image and with an empty string for the title.
     * The label is centered vertically
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge
     * of the label's display area.
     */
    public SLabel() {
    }

    /**
     * Creates a <code>SLabel</code> instance with the specified image.
     * The label is centered vertically and horizontally
     * in its display area.
     *
     * @param image  The image to be displayed by the label.
     */
    public SLabel(Icon image) {
        super(image);
        if (image instanceof SIcon)
            ((SIcon)image).setParent(this);
    }

    /**
     * Creates a <code>SLabel</code> instance with the specified
     * image and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public SLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        if (image instanceof SIcon)
            ((SIcon)image).setParent(this);

    }

    /**
     * Creates a <code>SLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     *
     * @param text  The text to be displayed by the label.
     */
    public SLabel(String text) {
        super(text);
    }

    /**
     * Creates a <code>SLabel</code> instance with the specified
     * text and horizontal alignment.
     * The label is centered vertically in its display area.
     *
     * @param text  The text to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public SLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    /**
     * Creates a <code>SLabel</code> instance with the specified
     * text, image, and horizontal alignment.
     * The label is centered vertically in its display area.
     * The text is on the trailing edge of the image.
     *
     * @param text  The text to be displayed by the label.
     * @param icon  The image to be displayed by the label.
     * @param horizontalAlignment  One of the following constants
     *           defined in <code>SwingConstants</code>:
     *           <code>LEFT</code>,
     *           <code>CENTER</code>,
     *           <code>RIGHT</code>,
     *           <code>LEADING</code> or
     *           <code>TRAILING</code>.
     */
    public SLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Binds the component to a DataStore expression
     * @param buf The DataStore to bind to
     * @param expression The DataStore expression to compute
     * @throws DataStoreException if the expression is invalid
     */
    public void setExpression(DataStoreBuffer buf, String expression) throws DataStoreException {
        _eval=new DataStoreEvaluator(buf,expression);
        buf.addModelChangedListener(this);
        evalExpression();
    }

     /**
     * Binds the component to a DataStore expression
     * @param buf The DataStore to bind to
     * @param expression The DataStore expression to compute
     * @throws DataStoreException if the expression is invalid
     */
    public void setExpression(DataStoreBuffer buf, DataStoreExpression expression) throws DataStoreException {
        _eval=new DataStoreEvaluator(buf,expression);
        buf.addModelChangedListener(this);
        evalExpression();
    }

    /**
     * Part of the model changed listener interface, don't call this method directly
     */
	public void modelChanged(ModelChangedEvent evt) {
		evalExpression();
	}

	private void evalExpression() {
		try {
            if (_eval != null)
			    setText(_eval.evaluateRowFormat());
		}
		catch (DataStoreException ex) {
            setText("");
            ex.printStackTrace();
		}

        try {
            if (_exp != null) {
                Icon icon = null;
                String toolTip = null;

                for (int i = 0; i < _exp.size(); i++) {
                    Object o[] = (Object[])_exp.elementAt(i);
                    if (o[1] == _colExp) {
                        int colIndex = _valDs.getColumnIndex((String)o[0]);
                        if (colIndex != -1) {
                            DataStoreException ex[] = _valDs.validateColumn(_valDs.getRow(),colIndex,null);
                            if (ex.length > 0) {
                                icon = (Icon) o[2];
                                toolTip = ex[0].getMessage();
                                break;
                            }
                        }
                    }
                    else if (o[0] != null) {
                        Object ret = ((DataStoreEvaluator)o[0]).evaluateRow();
                        if ((ret instanceof Boolean) && !((Boolean)ret).booleanValue()) {
                             icon = (Icon) o[2];
                             toolTip = (String) o[1];
                             break;
                        }
                    }
                }
                _errorIcon = icon;
                _errorMsg = toolTip;
                setIcon(icon);
                setToolTipText(toolTip);
            }
		}
		catch (DataStoreException ex) {
            ex.printStackTrace();
		}
	}

    /**
     * Returns the value of the data in the component
     */
    public String getValue() {
   		try {
            if (_eval != null)
			    return(_eval.evaluateRowFormat());
            else
                return getText();
		}
		catch (DataStoreException ex) {
            ex.printStackTrace();
            return null;
		}
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public ValueChangedEvent generateValueChangedEvent() {
        return null;
    }

    /**
     * This method is used by the framework and should not be called directly
     */
    public SComponentHelper getHelper() {
        return null;
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
            if (_exp == null)
                _exp = new Vector();
            _exp.add(o);
            exp.getDataStore().addModelChangedListener(this);
    }

    /**
     * Displays the icon and message for each cell where the expression evaluates to false
     * @throws DataStoreException
     */
    public void addValidationExpression(DataStoreBuffer ds, String exp, String message, Icon icon) throws DataStoreException {
        DataStoreEvaluator eval = new DataStoreEvaluator(ds,exp);
        this.addValidateExpression(eval,message,icon);
    }

    /**
     * Displays the icon and message expression for each cell where the expression evaluates to false
     * @throws DataStoreException
     */
    public void addValidateExpression(DataStoreBuffer ds,DataStoreExpression exp, String message, Icon icon) throws DataStoreException {
        DataStoreEvaluator eval = new DataStoreEvaluator(ds,exp);
        this.addValidateExpression(eval,message,icon);
    }

    /**
     *  Uses the rules embedded in the datastore for a particular column to indicate whether or not to display an icon and and error message tooltip
     */
    public void addValidateColumn(DataStoreBuffer dsb, String validateColumn, Icon icon) {
        Object o[] = new Object[3];
        o[0]=validateColumn;
        o[1]=_colExp;
        o[2]=icon;
        if (_exp == null)
            _exp = new Vector();
        _exp.add(o);
        _valDs = dsb;
        dsb.addModelChangedListener(this);
    }
    
	/**
	 * Returns the Icon for the last error or null if there isn't any.
	 * @return Icon
	 */
	public Icon getErrorIcon() {
		return _errorIcon;
	}

	/**
	 * Returns the errorMessage for the last error or null if there isn't any.
	 * @return String
	 */
	public String getErrorMessage() {
		return _errorMsg;
	}

}
