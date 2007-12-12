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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/PropertiesDialog.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 9/24/04 1:04p $
/////////////////////////

import com.salmonllc.properties.Props;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 * @author  demian
 */
public class PropertiesDialog extends JDialog implements ActionListener, WindowListener {

    boolean _cancelClicked = true;
    JButton _save;
    JButton _cancel;
    JFrame _owner;
    Props _props;
    int _fieldIndex = 0;
    int _noFields = 17;
    /**
	 * @uml.property  name="_fields"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
    Field _fields[];
    Box _mainGrid;
    Hashtable _changedValues = new Hashtable();

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_PW = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_DIR = 3;
    public static final int TYPE_DD = 4;
    public static final int TYPE_TF = 5;


    private class Field {
        public JLabel caption;
        public JComponent field;
        public String property;
        public JComponent lookup;
        public int type;
    }

    public PropertiesDialog(JFrame owner, Props p) {
        super(owner, "Set Properties", true);
        _owner = owner;
        _props = p;

        if (IDETool.getProject() != null)
            setTitle("Set Properties (Project:" + IDETool.getProject() + ")");

        Vector browserProps = new Vector();
        Enumeration en = p.getKeys();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            if (key.startsWith(Props.IDE_BROWSER_PATH + ".")) {
                browserProps.add(key);
                _noFields++;
            }
        }

        int width = 600;
        int height = 516 + (28 * browserProps.size());
        Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (frameBounds.width - width) / 2;
        int y = (frameBounds.height - height) / 2;
        setBounds(x, y, width, height);

        _fields = new Field[_noFields];
        _mainGrid = Box.createVerticalBox();

        addFieldToGrid(IDETool.getProjectProperty(Props.IDE_WEB_APP), "Default Web Application", TYPE_TEXT);
        addFieldToGrid(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP), "Default Framework Application", TYPE_TEXT);
        addFieldToGrid(IDETool.getProjectProperty(Props.IDE_DEFAULT_RUN_URL), "Default URL or JSP to Run", TYPE_FILE);
		addFieldToGrid(Props.IDE_USE_APP_PROPERTIES, "Use Prop Files from Application", TYPE_TF);
		addFieldToGrid(IDETool.getProjectProperty(Props.IDE_APP_PROPS_PATH), "Properties File Path", TYPE_DIR);

        addFieldToGrid(Props.IDE_SERVER_TYPE, "Server Type", TYPE_DD);
        addFieldToGrid(Props.IDE_DEFAULT_HOST, "Test Server Host:Port", TYPE_TEXT);

        addFieldToGrid(Props.IDE_BROWSER_PATH, "Default Browser Path", TYPE_FILE);
        for (int i = 0; i < browserProps.size(); i++) {
            String key = (String) browserProps.elementAt(i);
            String label = key.substring(Props.IDE_BROWSER_PATH.length() + 1) + " Browser Path";
            addFieldToGrid(key, label, TYPE_FILE);
        }
        addFieldToGrid(Props.IDE_DREAMWEAVER_PATH, "Dreamweaver Path", TYPE_FILE);
        addFieldToGrid(IDETool.getProjectProperty(Props.IDE_DEFAULT_SOURCE_PATH), "Source Code Path", TYPE_DIR);
        addFieldToGrid(Props.IDE_FRAMEWORK_RESOURCES_PATH, "Resources Path", TYPE_DIR);
        addFieldToGrid(Props.IDE_FRAMEWORK_SOURCE_PATH, "Framework Source Path", TYPE_DIR);
        addFieldToGrid(Props.IDE_INTELLIJ_PATH, "IntelliJ Path", TYPE_DIR);
        addFieldToGrid(Props.IDE_TOMCAT_PATH, "Tomcat Path", TYPE_DIR);
        addFieldToGrid(Props.IDE_TOMCAT_MANAGER_APP, "Tomcat Manager Web App", TYPE_TEXT);
        addFieldToGrid(Props.IDE_SERVER_MANAGER_ID, "Tomcat Manager User ID", TYPE_TEXT);
        addFieldToGrid(Props.IDE_SERVER_MANAGER_PW, "Tomcat Manager Password", TYPE_PW);

        _save = new JButton("Save");
        _save.addActionListener(this);
        _cancel = new JButton("Cancel");
        _cancel.addActionListener(this);
        JPanel buttonBar = new JPanel(new FlowLayout());
        buttonBar.add(_save);
        buttonBar.add(_cancel);
        buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box box = Box.createVerticalBox();

        box.add(Box.createRigidArea(new Dimension(100, 10)));
        box.add(_mainGrid);
        box.add(Box.createRigidArea(new Dimension(100, 5)));
        box.add(buttonBar);

        getContentPane().add(box);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _cancel) {
            _cancelClicked = true;
            setVisible(false);
        } else if (e.getSource() == _save) {
            for (int i = 0; i < _noFields; i++) {
                Field f = _fields[i];
                String origProp = _props.getProperty(f.property);
                String currentProp = null;
                if (f.field instanceof JTextField)
                    currentProp = ((JTextField) f.field).getText();
                else if (f.field instanceof JComboBox)
                    currentProp = (String) ((JComboBox) f.field).getSelectedItem();
                if (currentProp != null && currentProp.trim().length() == 0)
                    currentProp = null;
                if (!equals(origProp, currentProp)) {
                    if (currentProp == null)
                        currentProp = "";
                    _changedValues.put(f.property, currentProp);
                }
            }
            _cancelClicked = false;
            setVisible(false);
        } else {
            Field f = null;
            for (int i = 0; i < _noFields; i++) {
                if (_fields[i].lookup != null && _fields[i].lookup == e.getSource()) {
                    f = _fields[i];
                    break;
                }
            }
            if (f != null) {
                String fileName = ((JTextField) f.field).getText();
                JFileChooser c = new JFileChooser();
                if (f.type == TYPE_FILE)
                    c.setFileSelectionMode(JFileChooser.FILES_ONLY);
                else
                    c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                c.setCurrentDirectory(new File(fileName));
                if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                    ((JTextField) f.field).setText(c.getSelectedFile().getAbsolutePath());
            }
        }
    }

    private void addFieldToGrid(String property, String label, int type) {
        Field f = new Field();
        f.property = property;
        Dimension capSize = new Dimension(180, 25);
        Dimension fieldSize = new Dimension(350, 25);
        Dimension buttonSize = new Dimension(30, 25);
        Dimension lookupSize = new Dimension(320, 25);

        f.caption = DialogComponentFactory.makeLabel(label + ":");
        f.caption.setMaximumSize(capSize);
        f.caption.setMinimumSize(capSize);
        f.type = type;
        Box b = Box.createHorizontalBox();
        b.add(f.caption);

        if (type == TYPE_TEXT) {
            f.field = new JTextField(_props.getProperty(property));
            f.field.setMaximumSize(fieldSize);
            f.field.setMinimumSize(fieldSize);
            b.add(f.field);
            _mainGrid.add(b);
            _mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
        } else if (type == TYPE_PW) {
            f.field = new JPasswordField(_props.getProperty(property));
            f.field.setMaximumSize(fieldSize);
            f.field.setMinimumSize(fieldSize);
            b.add(f.field);
            _mainGrid.add(b);
            _mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
        } else if (type == TYPE_FILE || type == TYPE_DIR) {
            f.field = new JTextField(_props.getProperty(property));
            f.field.setMaximumSize(lookupSize);
            f.field.setMinimumSize(lookupSize);

            JButton btn = new JButton("..");
            btn.addActionListener(this);
            btn.setMaximumSize(buttonSize);
            btn.setMinimumSize(buttonSize);
            f.lookup = btn;
            b.add(f.field);
            b.add(f.lookup);
            _mainGrid.add(b);
        } else if (type == TYPE_DD && property == Props.IDE_SERVER_TYPE) {
            String items[] = {IDETool.SERVER_TOMCAT40, IDETool.SERVER_TOMCAT41, IDETool.SERVER_TOMCAT50};
            JComboBox box = new JComboBox(items);
            String prop = _props.getProperty(property);
            if (prop != null)
                box.setSelectedItem(prop);
            f.field = box;
            f.field.setMaximumSize(fieldSize);
            f.field.setMinimumSize(fieldSize);
            b.add(f.field);
            _mainGrid.add(b);
            _mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
        }else if (type == TYPE_TF) {
			String items[] = {"false","true"};
			JComboBox box = new JComboBox(items);
			String prop = _props.getProperty(property);
			if (prop != null)
				box.setSelectedItem(prop);
			f.field = box;
			f.field.setMaximumSize(fieldSize);
			f.field.setMinimumSize(fieldSize);
			b.add(f.field);
			_mainGrid.add(b);
			_mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
		} 

        _fields[_fieldIndex++] = f;
    }

    private boolean equals(String val1, String val2) {
        if (val1 == null && val2 == null)
            return true;
        else if (val1 == null && val2 != null)
            return false;
        else if (val2 == null && val1 != null)
            return false;
        else
            return val1.equals(val2);
    }

    /**
     * Returns true if the user clicked the cancel button to exit the dialog
     */
    public boolean getCancel() {
        return _cancelClicked;
    }

    /**
     * Returns a Hashtable of items changed by the user
     */
    public Hashtable getChangedValues() {
        return _changedValues;
    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}
