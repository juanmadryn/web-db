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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/SaveDialog.java $
//$Author: Dan $
//$Revision: 19 $
//$Modtime: 11/08/04 10:46a $
/////////////////////////
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class SaveDialog extends javax.swing.JDialog implements ActionListener, WindowListener {
	JTextField _fileName;
    boolean _cancel;
    String _fileText;
    JFrame _owner;

public SaveDialog (JFrame owner, String filename, String fileText) {
	super(owner,"Save Generated Code",true);
    _owner = owner;
    _fileText = fileText;

    int width = 550;
	int height = 200;
	Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (frameBounds.width - width) / 2;
	int y = (frameBounds.height - height) / 2;

	setBounds(x,y,width,height);

	Panel data = new Panel(new java.awt.FlowLayout(FlowLayout.CENTER));
    JButton file = new JButton("Select");
    file.addActionListener(this);
	data.add(DialogComponentFactory.makeLabel("File Name:"));
	data.add(_fileName = new JTextField(filename,32));
    data.add(file);

    JButton view = new JButton("View");
	view.addActionListener(this);
	JButton save = new JButton("Save");
	save.addActionListener(this);
	JButton cancel = new JButton("Cancel");
	cancel.addActionListener(this);
	JPanel buttonBar = new JPanel(new FlowLayout());
    buttonBar.add(view);
	buttonBar.add(save);
	buttonBar.add(cancel);
	buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);

	Box box = Box.createVerticalBox();
    box.add(Box.createRigidArea(new Dimension(0,20)));
    box.add(DialogComponentFactory.makeLabel("Class generated successfully. Please specifiy a file to save as."));
    box.add(Box.createRigidArea(new Dimension(0,20)));
    box.add(data);
    box.add(Box.createRigidArea(new Dimension(0,10)));
	box.add(buttonBar);
	//box.setAlignmentX(Component.CENTER_ALIGNMENT);

	getContentPane().add(box);

	addWindowListener(this);
	setVisible(true);
}
public void actionPerformed(ActionEvent e) {
    if (((JButton)e.getSource()).getText().equals("View")) {
         IDETool.displayCutPasteDialog(_fileText);
         return;
    }
    else if (((JButton)e.getSource()).getText().equals("Select")) {
        JFileChooser c = new JFileChooser();
        c.setCurrentDirectory(new File(_fileName.getText()));
        if (c.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            _fileName.setText(c.getSelectedFile().getAbsolutePath());
        return;
    }
	if (((JButton)e.getSource()).getText().equals("Cancel"))
		_cancel = true;
	else
		_cancel = false;
	setVisible(false);
}
/**
 * Returns true if the user clicked the cancel button to exit the dialog
 */
public boolean getCancel() {
	return _cancel;
}
/**
 * Returns the filename that the user specified to save the controller text under
 */
public String getFileName() {
	return _fileName.getText();
}
public void windowActivated(WindowEvent e) {
	_fileName.grabFocus();
}
public void windowClosed(WindowEvent e) {}
public void windowClosing(WindowEvent e){}
public void windowDeactivated(WindowEvent e) {}
 public void windowDeiconified(WindowEvent e) {}
public void windowIconified(WindowEvent e) {}
public void windowOpened(WindowEvent e){}
}
