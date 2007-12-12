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

import com.salmonllc.properties.Props;
import com.salmonllc.sitemap.SiteMap;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.ModelChangedEvent;
import com.salmonllc.sql.ModelChangedListener;
import com.salmonllc.swing.SButton;
import com.salmonllc.swing.SCheckBox;
import com.salmonllc.swing.SComboBox;
import com.salmonllc.swing.SComponentHelper;
import com.salmonllc.swing.SOption;
import com.salmonllc.swing.STable;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * IDE Tool to edit a project site map
 */
public class SiteMapDialog extends JDialog implements ModelChangedListener {
	private SiteMapModel _mod;
	private SiteMapActionModel _actMod;
	private ActionDialog _actDialog;

	private STable _tab;
	private STable _actTab;
	private SButton _add;
	private SButton _delete;
	private SButton _save;
	private SButton _export;
	private SButton _lookup;
	private SButton _actions;
	private SButton _cancel;
	private SComboBox _entryNames;
	private SiteMapDialog _this = this;
	private String _exportClassName = "";

	private static final String COL_NAME = "name";
    private static final String COL_COTXT="context";
	private static final String COL_URI = "uri";
	private static final String COL_SECURE = "secure";
	private static final String COL_FORWARD = "forward";
	private static final String COL_POPUP = "popup";
	private static final String COL_ACTION_NAME = "actionName";
	private static final String COL_ACTION_ENTRY = "actionEntry";

	/**
	 * @author  demian
	 */
	private class ActionDialog extends JDialog implements ActionListener {
		private SButton _addAction;
		private SButton _delAction;
		private SButton _okAction;
		private SButton _cancelAction;
		private boolean _okClicked = false;

		public ActionDialog(Frame owner) {
			super(owner, "Site Map Actions", true);
			setModal(true);
			setResizable(false);
			int width = 500;
			int height = 300;
			Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (frameBounds.width - width) / 2;
			int y = (frameBounds.height - height) / 2;
			setBounds(x, y, width, height);
			Container cp = getContentPane();
			cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

			_actTab = new STable();
			_actTab.setDataStore(_actMod);
			try {
				_actTab.addEditColumn(COL_ACTION_NAME, "Name");
				_actTab.addEditColumn(COL_ACTION_ENTRY, "Entry", _entryNames);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			JScrollPane sp = new JScrollPane(_actTab);
			cp.add(sp);
			cp.add(Box.createRigidArea(new Dimension(0, 20)));

			JPanel buttonBar = new JPanel();
			buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
			buttonBar.add(_addAction = new SButton("Add"));
			buttonBar.add(_delAction = new SButton("Delete"));
			buttonBar.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonBar.add(_okAction = new SButton("OK"));
			buttonBar.add(_cancelAction = new SButton("Cancel"));
			cp.add(buttonBar);
			cp.add(Box.createRigidArea(new Dimension(0, 20)));
			_addAction.addActionListener(new AddAction(_actMod,_actTab ));
			_delAction.addActionListener(new DeleteAction(_actMod));
			_cancelAction.addActionListener(new CancelAction(this));
			_okAction.addActionListener(this);
		}

		public void display() {
			_okClicked = false;
			setVisible(true);
			_actTab.revalidate();
			_actTab.repaint();
		}
		public boolean getOKClicked() {
			return _okClicked;
		}
		public void actionPerformed(ActionEvent e) {
			SComponentHelper.acceptCurrentValue();
			for (int i = 0; i < _actMod.getRowCount(); i++) {
				DataStoreException ex[] = _actMod.validateRow(i, null);
				if (ex.length > 0) {
					IDETool.displayError(ex[0].getMessage(), false);
					_actMod.gotoRow(i);
					_actTab.editCellAt(i, _actMod.getColumnIndex(ex[0].getColumn()) -1);
					return;
				}
			}
			
			_okClicked = true;
			setVisible(false);
		}	
	}

	private class SiteMapActionModel extends DataStoreBuffer {
		public SiteMapActionModel() {
			addBucket(COL_NAME, DataStoreBuffer.DATATYPE_STRING);
			addBucket(COL_ACTION_NAME, DataStoreBuffer.DATATYPE_STRING);
			addBucket(COL_ACTION_ENTRY, DataStoreBuffer.DATATYPE_STRING);
			
			try {
				addExpressionRule(COL_ACTION_NAME, COL_ACTION_NAME + " != null", "Action name Cannot Be Left Blank!");
				addExpressionRule(COL_ACTION_ENTRY, COL_ACTION_ENTRY + " != null", "Action Entry Cannot Be Left Blank!");
			} catch (DataStoreException e) {
				e.printStackTrace();
			}
		}

	}

	private class SiteMapModel extends DataStoreBuffer {
		String _appName;
		public SiteMapModel(String appName) {
			_appName = appName;
            
			addBucket(COL_URI, DataStoreBuffer.DATATYPE_STRING);
			addBucket(COL_NAME, DataStoreBuffer.DATATYPE_STRING);
			addBucket(COL_SECURE, DataStoreBuffer.DATATYPE_INT);
			addBucket(COL_FORWARD, DataStoreBuffer.DATATYPE_INT);
			addBucket(COL_POPUP, DataStoreBuffer.DATATYPE_STRING);
            addBucket(COL_COTXT, DataStoreBuffer.DATATYPE_STRING);
			try {
				addExpressionRule(COL_NAME, COL_NAME + " != null", "Name Cannot Be Left Blank!");
				addExpressionRule(COL_URI, COL_URI + " != null", "URI Cannot Be Left Blank!");
			} catch (DataStoreException e) {
				e.printStackTrace();
			}
		}

		public void retrieve(SiteMap map) {
			reset();
			Enumeration en = map.getEntryNames();
			while (en.hasMoreElements()) {
				String name = (String) en.nextElement();
				insertRow();
				try {
					setString(COL_NAME, name);
                	setString(COL_URI, map.getURI(name));
					setInt(COL_SECURE, map.isSecure(name) ? 1 : 0);
					setInt(COL_FORWARD, map.useForward(name) ? 1 : 0);
					setString(COL_POPUP, map.getPopupFeatures(name));
                    setString(COL_COTXT,map.getContext(name));
					String actions[] = map.getActionEntries(name);
					for (int i = 0; i < actions.length; i++) {
						String entry = map.getActionEntry(name, actions[i]);
						_actMod.insertRow();
						_actMod.setString(COL_NAME, name);
						_actMod.setString(COL_ACTION_NAME, actions[i]);
						_actMod.setString(COL_ACTION_ENTRY, entry);
					}
				} catch (Exception e) {
				}
			}
		}

		public SiteMap update(boolean doUpdate) throws DataStoreException, IOException {
			SiteMap map = new SiteMap(_appName);
			for (int i = 0; i < getRowCount(); i++) {
				gotoRow(i);
				map.putEntry(getString(COL_NAME), getString(COL_URI), getString(COL_POPUP), getInt(COL_FORWARD) == 1 ? true : false, getInt(COL_SECURE) == 1 ? true : false,getString(COL_COTXT));
			}
			_actMod.filter(null);
			for (int i = 0; i < _actMod.getRowCount(); i++) {
				_actMod.gotoRow(i);
				map.addActionEntry(_actMod.getString(COL_NAME), _actMod.getString(COL_ACTION_NAME), _actMod.getString(COL_ACTION_ENTRY));
			}
			if (doUpdate)
				map.update();
			return map;
		}
	}

	/**
	 * @author  demian
	 */
	private class AddAction implements ActionListener {
		DataStoreBuffer _ds;
		STable _table;
		public AddAction (DataStoreBuffer ds, STable tab) {
			_ds = ds;
			_table = tab;	
		}	

		public void actionPerformed(ActionEvent e) {
			_ds.insertRow();
			if (_ds == _actMod) {
				try {
					_actMod.setString(COL_NAME,_mod.getString(COL_NAME));
				} catch (DataStoreException e1) {
					e1.printStackTrace();
				}
}
			_table.editCellAt(_ds.getRow(), 0);	
		}
	}

	private class CancelAction implements ActionListener {
		JDialog _diag;
		public CancelAction(JDialog d) {
			_diag = d;
		}	
		public void actionPerformed(ActionEvent e) {
			_diag.setVisible(false);
		}
	}

	private class AddActionsAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object oldValues[] = null;
			try {
				String name = _mod.getString(COL_NAME);
				_actDialog.setTitle("Actions for:" + name);
				_actMod.filter(COL_NAME + " == null");
				_actMod.filter(COL_NAME + " == '" + name + "'");
				oldValues = new Object[_actMod.getRowCount()];
				for (int i = 0; i < _actMod.getRowCount(); i++) {
					String row[] = new String[3];
					row[0] = _actMod.getString(i, 0);
					row[1] = _actMod.getString(i, 1);
					row[2] = _actMod.getString(i, 2);
					oldValues[i] = row;
				}
				_actMod.sort(COL_ACTION_NAME, DataStoreBuffer.SORT_ASC);
				_entryNames.removeAllItems();
				for (int i = 0; i < _mod.getRowCount(); i++) 
					_entryNames.addItem(new SOption(_mod.getString(i, COL_NAME),_mod.getString(i, COL_NAME)));
	
			} catch (DataStoreException e1) {
				e1.printStackTrace();
			}

			_actDialog.display();
			SComponentHelper.acceptCurrentValue();
			
			if (!_actDialog.getOKClicked()) {
				for (int i = _actMod.getRowCount() - 1; i >= 0; i--)
					_actMod.removeRow(i);
				try {
					for (int i = 0; i < oldValues.length; i++) {
						_actMod.insertRow();
						String row[] = (String[]) oldValues[i];
						_actMod.setString(0, row[0]);
						_actMod.setString(1, row[1]);
						_actMod.setString(2, row[2]);
					}
				} catch (DataStoreException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	/**
	 * @author  demian
	 */
	private class DeleteAction implements ActionListener {
		DataStoreBuffer _ds;
		public DeleteAction (DataStoreBuffer ds) {
			_ds = ds;
		}	
		public void actionPerformed(ActionEvent e) {
			if (_ds.getRow() > -1)
				_ds.deleteRow();
		}
	}

	private class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (save())
				setVisible(false);
		}
		public boolean save() {
			for (int i = 0; i < _mod.getRowCount(); i++) {
				DataStoreException ex[] = _mod.validateRow(i, null);
				if (ex.length > 0) {
					IDETool.displayError(ex[0].getMessage(), false);
					_mod.gotoRow(i);
					_tab.editCellAt(i, _mod.getColumnIndex(ex[0].getColumn()));
					return false;
				}
			}
			try {
				_mod.update(true);
				return true;
			} catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	private class LookupAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			String defaultPath = Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_SOURCE_PATH));
			if (IDETool.getDocumentRoot() != null)
				defaultPath = IDETool.getDocumentRoot();
			c.setCurrentDirectory(new File(defaultPath));
			c.setDialogTitle("Choose File Or Folder");
			c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (c.showSaveDialog(_this) == JFileChooser.APPROVE_OPTION) {
				String defaultWebApp = Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_WEB_APP));
				String path = c.getSelectedFile().getAbsolutePath();
				int pos = path.indexOf(defaultWebApp + File.separator);
				if (pos > -1) {
					path = path.substring(pos + defaultWebApp.length());
					StringBuffer sb = new StringBuffer(path.length());
					for (int i = 0; i < path.length(); i++) {
						char ch = path.charAt(i);
						if (ch == File.separatorChar)
							sb.append("/");
						else
							sb.append(ch);
					}
					path = sb.toString();
				}
				try {
					_mod.setString(COL_URI, path);
					_tab.editCellAt(_mod.getRow(), 1);
				} catch (DataStoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class ExportAction implements ActionListener {
		private String _appName;
		public ExportAction(String appName) {
			_appName = appName;
		}
		public void actionPerformed(ActionEvent e) {
			boolean loop = true;

			if (!new SaveAction().save())
				return;

			while (loop) {
				String exportClassName = JOptionPane.showInputDialog(_this, "Please enter the class name for the generated constants:", _exportClassName, JOptionPane.QUESTION_MESSAGE);
				if (exportClassName == null)
					return;
				_exportClassName = exportClassName;
				if (exportClassName.trim().length() == 0)
					JOptionPane.showMessageDialog(_this, "Please Fill in an class name.");
				else if (exportClassName.indexOf(".") == -1)
					JOptionPane.showMessageDialog(_this, "Class name must include a package name.");
				else if (exportClassName.indexOf(" ") != -1)
					JOptionPane.showMessageDialog(_this, "Class name cannot include spaces.");
				else {
					SiteMap map;
					try {
						map = _mod.update(false);
						String interfaceString = map.generateConstants(exportClassName);
						Hashtable props = new Hashtable();
						props.put(_appName + "." + Props.IDE_SITE_MAP_INTERFACE, exportClassName);
						Props.updateSystemProperties(props);
						int pos = exportClassName.lastIndexOf(".");
						String clazz = exportClassName.substring(pos + 1);
						String packag = exportClassName.substring(0, pos);
						String fileName = IDETool.genClassFileName(packag, clazz, IDETool.getDefaultSourcePath(), IDETool.getDefaultFwApp(), IDETool.getDontAppendProject());
						if (IDETool.saveClass(fileName, interfaceString)) {
							loop = false;
							setVisible(false);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public SiteMapDialog(Frame owner) {
		super(owner, "Site Map", true);

		_entryNames = new SComboBox();
		_entryNames.setEditable(true);
		String app = Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP));
		setTitle("Site Map for " + app);
		_exportClassName = Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_SITE_MAP_INTERFACE), "");
		IDETool.setPropsPath();
        _mod = new SiteMapModel(app);
		_mod.addModelChangedListener(this);
		
		_actMod = new SiteMapActionModel();
		_actMod.addModelChangedListener(this);

		_actDialog = new ActionDialog(owner);

		setModal(true);
		setResizable(false);
		int width = 780;
		int height = 400;
		Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (frameBounds.width - width) / 2;
		int y = (frameBounds.height - height) / 2;
		setBounds(x, y, width, height);
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		_tab = new STable();
		_tab.setDataStore(_mod);
		try {
			_tab.addEditColumn(COL_URI, "URI");
			_tab.addEditColumn(COL_NAME, "Logical Name");
			_tab.addEditColumn(COL_POPUP, "Popup Features");
			SCheckBox cbx = new SCheckBox();
			cbx.setTrueValue("1");

			_tab.addEditColumn(COL_FORWARD, "Use Forward", cbx);
			_tab.addEditColumn(COL_SECURE, "Secure", cbx);
            _tab.addEditColumn(COL_COTXT,"Context");

			_tab.setColumnWidth(COL_URI, 200);
			_tab.setColumnWidth(COL_NAME, 200);
			_tab.setColumnWidth(COL_POPUP, 200);
			_tab.setColumnWidth(COL_FORWARD, 80);
			_tab.setColumnWidth(COL_SECURE, 80);
            _tab.setColumnWidth(COL_COTXT, 100);

			Icon errorIcon = null;
            try {
                errorIcon = new ImageIcon(getClass().getResource("error.gif"));
            }
            catch (Exception e) {}

			_tab.addColumnValidations(_tab.getTableColumn(1), COL_NAME, errorIcon);
			_tab.addColumnValidations(_tab.getTableColumn(0), COL_URI, errorIcon);
			
			_actTab.addColumnValidations(_actTab.getTableColumn(0), COL_ACTION_NAME, errorIcon);
			_actTab.addColumnValidations(_actTab.getTableColumn(1), COL_ACTION_ENTRY, errorIcon);
			JScrollPane sp = new JScrollPane(_tab);
			cp.add(sp);
		} catch (DataStoreException e) {
			e.printStackTrace();
		}

		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
		buttonBar.add(_add = new SButton("Add"));
		buttonBar.add(_delete = new SButton("Delete"));
		buttonBar.add(_lookup = new SButton("URI Lookup"));
		buttonBar.add(_actions = new SButton("Actions"));
		buttonBar.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonBar.add(_export = new SButton("Generate Constants"));
		buttonBar.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonBar.add(_save = new SButton("Save"));
		buttonBar.add(_cancel = new SButton("Cancel"));
		_add.addActionListener(new AddAction(_mod,_tab));
		_delete.addActionListener(new DeleteAction(_mod));
		_save.addActionListener(new SaveAction());
		_lookup.addActionListener(new LookupAction());
		_export.addActionListener(new ExportAction(app));
		_actions.addActionListener(new AddActionsAction());
		_cancel.addActionListener(new CancelAction(this));

		cp.add(Box.createRigidArea(new Dimension(0, 10)));
		cp.add(buttonBar);
		cp.add(Box.createRigidArea(new Dimension(0, 10)));

		_mod.retrieve(SiteMap.getSiteMap(app));
		_save.setEnabled(false);

		if (_mod.gotoFirst()) {
			_tab.editCellAt(0, 0);
			_tab.getEditorComponent().requestFocus();
		}
		_tab.scrollRectToVisible(new Rectangle(0,0,0,0));
	}

	/**
	 * Handles stuff when the model changes
	 */
	public void modelChanged(ModelChangedEvent evt) {
		_delete.setEnabled(_mod.getRowCount() > 0);
		_lookup.setEnabled(_mod.getRowCount() > 0);
		_export.setEnabled(_mod.getRowCount() > 0);
		_actions.setEnabled(_mod.getRowCount() > 0);

		if (evt.getType() == ModelChangedEvent.TYPE_DATA_CHANGED || evt.getType() == ModelChangedEvent.TYPE_ROW_INSERTED_OR_DELETED)
			_save.setEnabled(_mod.getRowCount() > 0);

	}
}
