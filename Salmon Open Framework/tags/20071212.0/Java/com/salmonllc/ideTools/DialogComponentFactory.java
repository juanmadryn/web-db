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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/DialogComponentFactory.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 3/25/04 5:44p $
/////////////////////////
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import com.salmonllc.swing.SComboBox;

import java.awt.*;

public class DialogComponentFactory {
    private static int PREFERREDHEIGHT = 25;

    /**
     * Makes a button of the specified width and height
     */
     public static JButton makeButton(String text, int width, int height) {
        JButton b = new JButton(text);
        if (height == -1 || width == -1) {
            Dimension d = b.getPreferredSize();
            if (height == -1)
                height = (int) d.getHeight();
            if (width == -1)
                width = (int) d.getWidth();
        }
        b.setMaximumSize(new Dimension(width,height));
        b.setPreferredSize(new Dimension(width,height));
        b.setMinimumSize(new Dimension(width,height));

        b.setVerticalAlignment(JButton.TOP);
        b.setVerticalTextPosition(JButton.TOP);
        b.setHorizontalAlignment(JButton.LEFT);
        b.setHorizontalTextPosition(JButton.LEFT);
        return b;
    }
    /**
     * Returns a Combo Box Pane with the correct settings
     */
    public static JComboBox makeComboBox() {
         UIDefaults def = UIManager.getDefaults();
         if (IDETool.getDefaultScrollBarColor() != null)
            def.put("ScrollBar.thumb",IDETool.getDefaultScrollBarColor());
         JComboBox b = new JComboBox();

         return b;
    }
	/**
	 * Makes a text field of the specified width
	 */
	public static JComboBox makeComboBox(int width) {
		JComboBox f = makeComboBox();
		f.setMaximumSize(new Dimension(width,PREFERREDHEIGHT));
		f.setPreferredSize(new Dimension(width,PREFERREDHEIGHT));
		f.setMinimumSize(new Dimension(width,PREFERREDHEIGHT));
		return f;
	}
	
	/**
	 * Makes a text field of the specified width
	 */
	public static SComboBox makeSComboBox(int width) {
		SComboBox f = new SComboBox();
		f.setMaximumSize(new Dimension(width,PREFERREDHEIGHT));
		f.setPreferredSize(new Dimension(width,PREFERREDHEIGHT));
		f.setMinimumSize(new Dimension(width,PREFERREDHEIGHT));
		return f;
	}
    /**
     * Returns a JLabel with the correct text and color
     */
    public static JLabel makeLabel(String text) {
         JLabel lab = new JLabel(text);
         lab.setForeground(IDETool.DEFAULT_FONT_COLOR);
         lab.setFont(IDETool.getDefaultFont());
         return lab;
    }
    /**
     * Makes a label of the specified the specified width
     */
    public static JLabel makeLabel(String text, int width) {
        JLabel l = makeLabel(text);
		l.setMaximumSize(new Dimension(width,PREFERREDHEIGHT));
		l.setPreferredSize(new Dimension(width,PREFERREDHEIGHT));
		l.setMinimumSize(new Dimension(width,PREFERREDHEIGHT));
        return l;
    }
    

	/**
	 * Make list with the correct correct settings
	 */
	public static JList makeList(ListModel m) {
         UIDefaults def = UIManager.getDefaults();
         if (IDETool.getDefaultScrollBarColor() != null)
            def.put("ScrollBar.thumb",IDETool.getDefaultScrollBarColor());
         def.put("List.background",ColorUIResource.white);
         JList l = new JList(m);

         return l;
    }
    /**
     * Makes a scroll pane of the specified width and height with the passed component
     */
    public static JScrollPane makeScrollPane(int width, int height, Component list) {
        JScrollPane p = new JScrollPane(list);
        p.setMaximumSize(new Dimension(width,height));
        p.setPreferredSize(new Dimension(width,height));
        p.setMinimumSize(new Dimension(width,height));
        return p;
    }
    /**
     * Returns a Scroll Pane with the correct settings
     */
    public static JScrollPane makeScrollPane(JComponent comp) {
         UIDefaults def = UIManager.getDefaults();
         if (IDETool.getDefaultScrollBarColor() != null)
         def.put("ScrollBar.thumb",IDETool.getDefaultScrollBarColor());
         JScrollPane p = new JScrollPane(comp);

         return p;
    }
    /**
     * Makes a text field of the specified width
     */
    public static JTextField makeTextField(int width) {
        JTextField f = new JTextField(255);
        f.setMaximumSize(new Dimension(width,PREFERREDHEIGHT));
        f.setPreferredSize(new Dimension(width,PREFERREDHEIGHT));
        f.setMinimumSize(new Dimension(width,PREFERREDHEIGHT));
        return f;
    }
}
