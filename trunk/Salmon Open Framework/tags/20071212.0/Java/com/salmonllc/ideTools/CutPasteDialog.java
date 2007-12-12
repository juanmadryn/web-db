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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/CutPasteDialog.java $
//$Author: Srufle $
//$Revision: 9 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CutPasteDialog extends JDialog implements ActionListener {

    JTextArea _text;
    JScrollPane _pane;

public CutPasteDialog (Frame owner, String text) {
	super(owner,"View Generated Code",true);

	int width = 600;
	int height = 450;
	Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (frameBounds.width - width) / 2;
	int y = (frameBounds.height - height) / 2;

	setBounds(x,y,width,height);

    JButton select = new JButton("Select All");
	select.addActionListener(this);
	JButton copy = new JButton("Copy");
	copy.addActionListener(this);
    JButton close = new JButton("Close");
	close.addActionListener(this);
	JPanel buttonBar = new JPanel(new FlowLayout());
	buttonBar.add(copy);
    buttonBar.add(select);
	buttonBar.add(close);
	buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);
    _text = new JTextArea(text);
    _text.setRows(21);
    _pane = DialogComponentFactory.makeScrollPane(_text);

    Box box = Box.createVerticalBox();
    box.add(_pane);
    box.add(Box.createRigidArea(new Dimension(100,5)));
	box.add(buttonBar);

	getContentPane().add(box);
    setVisible(true);

}
public void actionPerformed(ActionEvent e) {
    if (((JButton)e.getSource()).getText().equals("Close"))
        setVisible(false);
    else if (((JButton)e.getSource()).getText().equals("Copy")) {
        _text.copy();
        _text.grabFocus();
    }
    else {
        _text.selectAll();
        _text.grabFocus();
    }
}
}
