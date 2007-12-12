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
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

/**
 * SOFIA implementation of a Swing ImageIcon. This icon allows binding to DataStore columns for either an image URL or an image byte array.
 */
public class SIcon extends ImageIcon implements ModelChangedListener, SComponent {
    private DataStoreEvaluator _eval;
    private Object _currentValue;
    private JComponent _parent;

    /**
     * Creates a new SIcon
     */
    public SIcon() {
        _currentValue=null;
    }


    /**
     * Binds the component to a DataStore expression
     * @param buf The DataStore to bind to
     * @param expression The DataStore expression to compute. It can return a url string or a byte array.
     * @throws DataStoreException if the expression is invalid
     */
    public void setExpression(DataStoreBuffer buf, String expression) throws DataStoreException {
        _eval=new DataStoreEvaluator(buf,expression);
        buf.addModelChangedListener(this);
        evalExpression();
    }

     /**
     * Binds the component to a DataStore expression. It can return a url string or a byte array.
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
        Object o = null;
		try {
            if (_eval != null)
			    o = _eval.evaluateRow();

            if (! SComponentHelper.valuesEqual(o,_currentValue)) {
                Image newImage = null;
                if (o instanceof String) {
                    URL location = new URL((String)o);
                    newImage = Toolkit.getDefaultToolkit().getImage(location);
                }
                else if (o instanceof byte[]) {
                    newImage = Toolkit.getDefaultToolkit().createImage((byte[]) o);
                }
                if (newImage != null) {
                    setImage(newImage);
                    loadImage(newImage);
                    if (_parent != null) {
                        _parent.revalidate();
                        _parent.repaint();
                    }
                }
            }
		}
		catch (DataStoreException ex) {
            ex.printStackTrace();
		} catch (MalformedURLException ex) {
            ex.printStackTrace();
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
     * Returns the parent component (may be null)
     */
    public JComponent getParent() {
        return _parent;
    }

    /**
     * Sets the parent component
     */
    public void setParent(JComponent parent) {
        _parent = parent;
    }

}
