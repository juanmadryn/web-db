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

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 * @author  demian
 */
public class DatabaseDialog extends JDialog implements ActionListener, WindowListener {

    boolean _cancelClicked = true;
    JButton _ok;
    JButton _cancel;
    JFrame _owner;
    int _fieldIndex = 0;
    int _noFields = 10;
    /**
	 * @uml.property  name="_fields"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
    Field _fields[];
    Box _mainGrid;
    Hashtable _dbsettings;
    File[] _selectedFiles;

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_PW = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_DIR = 3;
    public static final int TYPE_DIR_FILE = 4;
    public static final int TYPE_CHECKBOX = 5;

    private class Field {
        public JLabel caption;
        public JComponent field;
        public String property;
        public JComponent lookup;
        public int type;
    }


public DatabaseDialog (JFrame owner, Hashtable dbsettings) {
	super(owner,"Specify Database Settings",true);
    _owner = owner;
    _dbsettings = dbsettings;

	int width = 600;
	int height = 150 + ( 30 * 2);
	Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (frameBounds.width - width) / 2;
	int y = (frameBounds.height - height) / 2;
	setBounds(x,y,width,height);

    _fields = new Field[_noFields];
    _mainGrid = Box.createVerticalBox();

    addFieldToGrid("JDBCJarFile","JDBC Jar File",TYPE_DIR_FILE);
    addFieldToGrid("JDBCDriver","JDBC Driver Class",TYPE_TEXT);
    addFieldToGrid("JDBCConnectUrl","JDBC Connect Url",TYPE_TEXT);
    addFieldToGrid("JDBCUser","JDBC Username",TYPE_TEXT);
    addFieldToGrid("JDBCPassword","JDBC Password",TYPE_TEXT);
    if (_dbsettings!=null) {
        ((JTextField)_fields[0].field).setText((String)_dbsettings.get("JDBCJarFile"));
        ((JTextField)_fields[1].field).setText((String)_dbsettings.get("JDBCDriver"));
        ((JTextField)_fields[2].field).setText((String)_dbsettings.get("JDBCConnectUrl"));
        ((JTextField)_fields[3].field).setText((String)_dbsettings.get("JDBCUser"));
        ((JTextField)_fields[4].field).setText((String)_dbsettings.get("JDBCPassword"));
    }

    _ok = new JButton("OK");
	_ok.addActionListener(this);
	_cancel = new JButton("Cancel");
	_cancel.addActionListener(this);
	JPanel buttonBar = new JPanel(new FlowLayout());
    buttonBar.add(_ok);
	buttonBar.add(_cancel);
	buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);
	Box box = Box.createVerticalBox();

    box.add(Box.createRigidArea(new Dimension(100,10)));
    box.add(_mainGrid);
    box.add(Box.createRigidArea(new Dimension(100,5)));
	box.add(buttonBar);

	getContentPane().add(box);

	addWindowListener(this);
	setVisible(true);
}
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == _cancel) {
		_cancelClicked = true;
        setVisible(false);
    }
	else if (e.getSource() == _ok) {
        Field fJDBCJarFile=_fields[0];
        String sJDBCJarFile=((JTextField)fJDBCJarFile.field).getText();
        if (sJDBCJarFile==null || sJDBCJarFile.trim().equals("")) {
            displayError("JDBC Jar File is required.");
            return;
        }
        else {
            StringTokenizer stFiles=new StringTokenizer(sJDBCJarFile,";");
            Vector vFiles=new Vector();
            while (stFiles.hasMoreTokens()) {
                String sJarFile=stFiles.nextToken();
                File fJarFile=new File(sJarFile);
                if (!fJarFile.exists()) {
                    displayError("JDBC Jar File "+fJarFile.getAbsolutePath()+" does not exist.");
                    return;
                }
                vFiles.addElement(fJarFile);
            }
            if (vFiles.size()>0) {
                _selectedFiles=new File[vFiles.size()];
                for (int i=0;i<vFiles.size();i++)
                    _selectedFiles[i]=(File)vFiles.elementAt(i);
            }
            else
                _selectedFiles=null;
        }
        Field fJDBCDriver=_fields[1];
        String sJDBCDriver = ((JTextField) fJDBCDriver.field).getText();
        if (sJDBCDriver==null || sJDBCDriver.trim().equals("")) {
            displayError("JDBC Driver is required.");
            return;
        }
        Field fJDBCConnectUrl=_fields[2];
        String sJDBCConnectUrl = ((JTextField) fJDBCConnectUrl.field).getText();
        if (sJDBCConnectUrl==null || sJDBCConnectUrl.trim().equals("")) {
            displayError("JDBC Connect Url is required.");
            return;
        }
        else {
            if (!sJDBCConnectUrl.startsWith("jdbc:")) {
                displayError("JDBC Connect Url must start with jdbc:.");
                return;
            }
        }
        Field fJDBCUser=_fields[3];
        String sJDBCUser = ((JTextField) fJDBCUser.field).getText();
        Field fJDBCPassword=_fields[4];
        String sJDBCPassword = ((JTextField) fJDBCPassword.field).getText();
        _dbsettings=new Hashtable();
        _dbsettings.put(fJDBCJarFile.property,_selectedFiles);
        _dbsettings.put(fJDBCDriver.property,sJDBCDriver);
        _dbsettings.put(fJDBCConnectUrl.property,sJDBCConnectUrl);
        _dbsettings.put(fJDBCUser.property,sJDBCUser);
        _dbsettings.put(fJDBCPassword.property,sJDBCPassword);

 		_cancelClicked = false;
        setVisible(false);
    }
    else {
        Field f = null;
        for (int i = 0; i < _noFields;i++) {
             if (_fields[i].lookup != null && _fields[i].lookup == e.getSource()) {
                   f = _fields[i];
                   break;
             }
        }
        if (f != null) {
            String fileName = ((JTextField) f.field).getText();
            JFileChooser c = new JFileChooser();
            c.setMultiSelectionEnabled(true);
            if (f.type == TYPE_FILE)
                c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            else if (f.type == TYPE_DIR)
                c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            else
                c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            c.setCurrentDirectory(new File(fileName));
            if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)  {
                _selectedFiles=c.getSelectedFiles();
                if (_selectedFiles!=null && _selectedFiles.length!=0) {
                    StringBuffer sbFiles=new StringBuffer();
                    for (int i=0;i<_selectedFiles.length;i++) {
                        sbFiles.append(_selectedFiles[i].getAbsolutePath());
                        if (i!=_selectedFiles.length-1)
                          sbFiles.append(";");
                    }
                    ((JTextField) f.field).setText(sbFiles.toString());
                }
                else {
                    if (c.getSelectedFile().exists()) {
                        ((JTextField) f.field).setText(c.getSelectedFile().getAbsolutePath());
                        _selectedFiles=new File[1];
                        _selectedFiles[0]=c.getSelectedFile();
                    }
                    else {
                        String sFile=c.getSelectedFile().getAbsolutePath();
                        int iSlashIndex=sFile.lastIndexOf(File.separator);
                        sFile=sFile.substring(0,iSlashIndex);
                        ((JTextField) f.field).setText(sFile);
                        _selectedFiles=new File[1];
                        _selectedFiles[0]=new File(sFile);
                    }
                }
/*                if (c.getSelectedFile().exists())
                    ((JTextField) f.field).setText(c.getSelectedFile().getAbsolutePath());
                else {
                    String sFile=c.getSelectedFile().getAbsolutePath();
                    int iSlashIndex=sFile.lastIndexOf(File.separator);
                    sFile=sFile.substring(0,iSlashIndex);
                    ((JTextField) f.field).setText(sFile);
                }
*/
            }
        }
    }
}
private JComponent addFieldToGrid(String property, String label, int type) {
    Field f = new Field();
    f.property = property;
    Dimension capSize = new Dimension(180,25);
    Dimension fieldSize = new Dimension(350,25);
    Dimension buttonSize = new Dimension(30,25);
    Dimension lookupSize = new Dimension(320,25);

    f.type = type;
    if (type!=TYPE_CHECKBOX)
        f.caption = DialogComponentFactory.makeLabel(label + ":");
    else
        f.caption = DialogComponentFactory.makeLabel("");
    f.caption.setMaximumSize(capSize);
    f.caption.setMinimumSize(capSize);
    Box b = Box.createHorizontalBox();
    b.add(f.caption);

    if (type == TYPE_TEXT) {
        f.field = new JTextField("");
        f.field.setMaximumSize(fieldSize);
        f.field.setMinimumSize(fieldSize);
        b.add(f.field);
        _mainGrid.add(b);
        _mainGrid.add(Box.createRigidArea( new Dimension(255,3)));
    }
    else if (type == TYPE_PW) {
        f.field = new JPasswordField("");
        f.field.setMaximumSize(fieldSize);
        f.field.setMinimumSize(fieldSize);
        b.add(f.field);
        _mainGrid.add(b);
        _mainGrid.add(Box.createRigidArea( new Dimension(255,3)));
    }
    else if (type == TYPE_FILE || type == TYPE_DIR || type == TYPE_DIR_FILE) {
        f.field = new JTextField("");
        f.field.setMaximumSize(lookupSize);
        f.field.setMinimumSize(lookupSize);

        f.lookup = new JButton("..");
        ((JButton)f.lookup).addActionListener(this);
        ((JButton)f.lookup).setMaximumSize(buttonSize);
        ((JButton)f.lookup).setMinimumSize(buttonSize);
        b.add(f.field);
        b.add(f.lookup);
        _mainGrid.add(b);
    }
    else if (type == TYPE_CHECKBOX) {
        f.field = new JCheckBox(label);
        f.field.setMaximumSize(lookupSize);
        f.field.setMinimumSize(lookupSize);
        b.add(f.field);
        _mainGrid.add(b);
    }

    _fields[_fieldIndex++] = f;
    return f.field;
}
private static void displayError(String error) {
      System.out.println("Error Display:" + error);
      JFrame f = new JFrame();
      JOptionPane.showMessageDialog(f,error,"Error",JOptionPane.ERROR_MESSAGE);
      f.dispose();
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
public Hashtable getDBSettings() {
    return _dbsettings;
}
public void windowActivated(WindowEvent e) {

}
public void windowClosed(WindowEvent e) {}
public void windowClosing(WindowEvent e){}
public void windowDeactivated(WindowEvent e) {}
public void windowDeiconified(WindowEvent e) {}
public void windowIconified(WindowEvent e) {}
public void windowOpened(WindowEvent e){}
}
