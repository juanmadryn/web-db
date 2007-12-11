package com.salmonllc.swing;

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

import javax.swing.*;

/**
 * SOFIA implementation of a Radio Button. It takes an SOption with a key and display value. This component can't be bound directly to a datastore. It is instead bound via the SButtonGroup object.
 */
public class SRadioButton extends JRadioButton {

    private SOption _opt;
    /***
     * Creates a new Radio Button with an empty option
     */
    public SRadioButton () {
        super(null,null,false);
        _opt=new SOption();
    }
    /***
     * Creates a new Radio Button with the specified option
     */
    public SRadioButton (SOption opt) {
        super(opt.getDisplay());
        _opt = opt;
    }
    /***
     * Creates a new Radio Button with the specified option
     */
    public SRadioButton(String key, String display) {
        super(display);
        _opt = new SOption(key,display);
    }
    /***
     * Returns the option from the Radio Button
     */
    public SOption getOption() {
        return _opt;
    }
    /***
     * Sets the option for the Radio Button
     */
    public void setOption(SOption sOpt) {
        _opt=sOpt;
        setText(_opt.getDisplay());
    }
}
