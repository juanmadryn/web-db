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


/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/swing/SOption.java $
//$Author: Dan $
//$Revision: 2 $
//$Modtime: 5/07/03 11:53a $
/////////////////////////


/**
 * This type is used for the options in the SComboBox, SCheckBox, SList, SToggleButton, etc. It contains an internal key value, but display an external display value.
 */
public class SOption {
    private String _display;
    private String _key;

    /**
     * This method is the default Constructor
     */
    public SOption() {
        super();
    }

    /**
     * Creates a new Option object
     */
    public SOption(String key, String disp) {
        super();
        setKey(key);
        setDisplay(disp);
    }


    /**
     * Sets the display value.
     */
    public void setDisplay(String display) {
        _display = display;
    }

    /**
     * Returns the display value.
     */
    public String getDisplay() {
        return _display;
    }

    /**
     * Sets the key value.
     */
    public void setKey(String key) {
        _key = key;
    }

    /**
     * Returns the key value.
     */
    public String getKey() {
        return _key;
    }


    /**
     * @see Object#toString()
     */
    public String toString() {
        return getDisplay();
    }

    /**
     * Returns true if the object.toString() passed equals the key value
     */
    public boolean equals(Object o) {
        if (o == null) {
            if (_key == null || _key.length() == 0)
                return true;
            else
                return false;
        }
        String val = null;
        if (o instanceof SOption)   {
            val = ((SOption) o).getKey();
            return compValues(val,_key);
        }
        else {
            val = o.toString().trim();
            return compValues(val,_key) || compValues(val,_display) ;
        }
    }

    private boolean compValues(String val,String val2) {
        if (val == null || val.equals("")) {
            if (val2 == null || val2.length() == 0)
                return true;
            else
                return false;
        }
        else {
            if (val2 == null || val2.equals(""))
                return false;
            else
                return val.equals(val2);
        }

    }
}
