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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/ClassPathDialog.java $
//$Author: Srufle $
//$Revision: 5 $
//$Modtime: 4/15/03 3:11p $
/////////////////////////

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClassPathDialog extends JDialog implements ActionListener, ListSelectionListener {

    JList _list;
    JScrollPane _pane;
    String _paths;
    JButton _delete;
    JButton _moveUp;
    JButton _moveDown;

    public ClassPathDialog(Frame owner, String text, boolean filesAlso) {
        super(owner, filesAlso ? "Set Classpath" : "Set Path", true);

        int width = 620;
        int height = 400;
        Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (frameBounds.width - width) / 2;
        int y = (frameBounds.height - height) / 2;

        setBounds(x, y, width, height);

        JButton addFile = new JButton("Add File");
        addFile.addActionListener(this);
        JButton addDir = new JButton("Add Dir");
        addDir.addActionListener(this);
        JButton delete = new JButton("Delete");
        delete.setEnabled(false);
        delete.addActionListener(this);
        JButton moveUp = new JButton("Move Up");
        moveUp.setEnabled(false);
        moveUp.addActionListener(this);
        JButton moveDown = new JButton("Move Down");
        moveDown.setEnabled(false);
        moveDown.addActionListener(this);
        JButton OK = new JButton("Save");
        OK.addActionListener(this);
        JButton close = new JButton("Cancel");
        close.addActionListener(this);
        JPanel buttonBar = new JPanel(new FlowLayout());

        _delete = delete;
        _moveUp = moveUp;
        _moveDown = moveDown;

        if (filesAlso)
            buttonBar.add(addFile);

        buttonBar.add(addDir);
        buttonBar.add(delete);
        buttonBar.add(moveUp);
        buttonBar.add(moveDown);
        buttonBar.add(OK);
        buttonBar.add(close);
        buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        Vector v = new Vector();
        StringTokenizer t = new StringTokenizer(text, File.pathSeparator);
        while (t.hasMoreTokens())
            v.add(t.nextToken());

        _list = new JList(v);
        _list.addListSelectionListener(this);
        _pane = DialogComponentFactory.makeScrollPane(_list);
        Dimension size = new Dimension(width, height - 100);
        _pane.setPreferredSize(size);


        Box box = Box.createVerticalBox();
        box.add(_pane);
        box.add(Box.createRigidArea(new Dimension(10, 5)));
        box.add(buttonBar);

        getContentPane().add(box);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("Save")) {
             ListModel m = _list.getModel();
             StringBuffer b = new StringBuffer();
             for (int i = 0; i < m.getSize(); i++) {
                b.append(m.getElementAt(i));
                b.append(File.pathSeparatorChar);
             }
             _paths = b.toString();
             setVisible(false);
        }
        else if (((JButton) e.getSource()).getText().equals("Cancel")) {
            _paths = null;
            setVisible(false);
        }
        else if (((JButton) e.getSource()).getText().equals("Move Up")) {
            Vector v = new Vector();
            ListModel m = _list.getModel();
            int size = m.getSize();
            int sel = _list.getSelectedIndex();
            String val = (String) m.getElementAt(sel);
            for (int i = 0; i < size; i++) {
                if (i == (sel - 1)){
                    v.add(val);
                    v.add(m.getElementAt(i));
                }
                else if (i != sel)
                    v.add(m.getElementAt(i));
            }
            _list.setModel(new JList(v).getModel());
            _list.setSelectedIndex(sel - 1);
        }
        else if (((JButton) e.getSource()).getText().equals("Move Down")) {
            Vector v = new Vector();
            ListModel m = _list.getModel();
            int size = m.getSize();
            int sel = _list.getSelectedIndex();
            String val = (String) m.getElementAt(sel);
            for (int i = 0; i < size; i++) {
                if (i == (sel + 1)){
                    v.add(m.getElementAt(i));
                    v.add(val);
                }
                else if (i != sel)
                    v.add(m.getElementAt(i));
            }
            _list.setModel(new JList(v).getModel());
            _list.setSelectedIndex(sel + 1);
        }
        else if (((JButton) e.getSource()).getText().equals("Delete")) {
            Vector v = new Vector();
            int size = _list.getModel().getSize();
            for (int i = 0; i < size; i++) {
                if (!_list.isSelectedIndex(i))
                    v.add(_list.getModel().getElementAt(i));
            }
            _list.setModel(new JList(v).getModel());
        } else if (((JButton) e.getSource()).getText().startsWith("Add")) {
            JFileChooser c = new JFileChooser();
            if ((((JButton) e.getSource()).getText().endsWith("File"))) {
                c.setDialogTitle("Add Files");
                c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            } else {
                c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                c.setDialogTitle("Add Directories");
            }
            c.setMultiSelectionEnabled(true);
            ListModel m = _list.getModel();
            if (m.getSize() > 0) {
                String last = (String) m.getElementAt(m.getSize() - 1);
                File f = new File(last);
                File parent = f.getParentFile();
                if (parent.exists())
                    c.setCurrentDirectory(parent);
            }
            if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f[] = c.getSelectedFiles();
                Vector v = new Vector(m.getSize());
                for (int i = 0; i < m.getSize(); i++)
                    v.addElement(m.getElementAt(i));
                for (int i = 0; i < f.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < m.getSize(); j++) {
                        if (f[i].toString().equals(m.getElementAt(j))) {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        v.addElement(f[i].toString());
                }
                _list.setModel(new JList(v).getModel());
            }
        }
    }
    public String getPaths() {
        return _paths;
    }
    public void valueChanged(ListSelectionEvent e) {
        _delete.setEnabled(_list.getSelectedIndex() > -1);
        _moveUp.setEnabled(_list.getSelectedIndices().length == 1 && _list.getSelectedIndex() > 0);
        _moveDown.setEnabled(_list.getSelectedIndices().length == 1 && _list.getSelectedIndex() < (_list.getModel().getSize() - 1));
    }
}
