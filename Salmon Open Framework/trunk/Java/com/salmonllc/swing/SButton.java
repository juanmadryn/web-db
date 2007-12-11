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

import javax.swing.*;

/**
 * SOFIA implementation of a JButton
 */
public class SButton extends JButton {
    /**
     * Creates a button with no set text or icon.
     */
    public SButton() {
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public SButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public SButton(Icon icon) {
        super(icon);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public SButton(String text) {
        super(text);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public SButton(String text, Icon icon) {
        super(text, icon);
        if (icon instanceof SIcon)
            ((SIcon)icon).setParent(this);

    }
}
