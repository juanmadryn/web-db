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
 * SOFIA implementation of a Toggle Button. This toggle button should be used in conjunction with an SButton group and functionally is similar to a radio button where one button in the group can be selected at a time. Binding to a datastore is accomplished through the SButtonGroup component.
 */
public class SGroupedToggleButton extends JToggleButton {

    private SOption _opt;
    /***
     * Creates a new Grouped Toggle Button with the specified option
     */
    public SGroupedToggleButton (SOption opt) {
        super(opt.getDisplay());
        _opt = opt;
    }
    /***
     * Creates a new Grouped Toggle Button with the specified option
     */
    public SGroupedToggleButton(String key, String display) {
        super(display);
        _opt = new SOption(key,display);
    }

     /***
     * Creates a new Grouped Toggle Button with the specified option
     */
    public SGroupedToggleButton(String key, String display, Icon icon) {
        this(key,display);
        setIcon(icon);
    }

    /***
     * Creates a new Grouped Toggle Button with the specified option
     */
    public SGroupedToggleButton(String key, Icon icon) {
        super(icon);
        _opt = new SOption(key,null);
    }

    /***
     * Creates a new Grouped Toggle Button with the specified option
     */
    public SGroupedToggleButton(SOption  opt,Icon icon) {
        super(icon);
        _opt = opt;
    }

    /***
     * Returns the option from the Toggle Button
     */
    public SOption getOption() {
        return _opt;
    }
}
