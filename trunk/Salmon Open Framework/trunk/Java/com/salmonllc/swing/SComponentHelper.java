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

import com.salmonllc.swing.events.ValueChangedListener;
import com.salmonllc.swing.events.ValueChangedEvent;
import com.salmonllc.swing.table.STableColumn;
import com.salmonllc.personalization.Skin;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.ModelChangedEvent;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Because the SOFIA Swing components extend from standard Swing components, they cannot have a common ancestor. Instead they implement the SComponent interface. This class serves as a base class for all the SComponents in the absence of a real one. It contains helper functions that the various Scomponents need.
 */
public class SComponentHelper implements FocusListener, KeyListener, MouseListener, DocumentListener, ActionListener, ItemListener, ListSelectionListener, ChangeListener {
    private Object _comp;
    private Vector _valueChangedListeners;
    private boolean _dataDirty;
    private JPopupMenu _menu;
    private int _maxLength = 9999999;

    public class MaxLengthInput extends PlainDocument {
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null || getLength() + str.length() <= _maxLength)
                super.insertString(offs, str, a);
            else {
                int remainder = _maxLength - getLength();
                if (remainder > 0)
                    super.insertString(offs, str.substring(0, remainder), a);
            }
        }
    }

    private class SelectAllAction extends AbstractAction {
        private JTextComponent _compToChange;

        public SelectAllAction(JTextComponent compToChange) {
            _compToChange = compToChange;
        }

        public void actionPerformed(ActionEvent e) {
            _compToChange.selectAll();
        }
    }

    private class ClearAction extends AbstractAction {
        private JTextComponent _compToChange;

        public ClearAction(JTextComponent compToChange) {
            _compToChange = compToChange;
        }

        public void actionPerformed(ActionEvent e) {
            _compToChange.setText("");
        }
    }

    /**
     * Creates a new SComponentHelper for a button group
     */
    public SComponentHelper(SButtonGroup comp) {
         _comp = comp;
    }

    /**
     * Creates a new SComponentHelper for a standard JComponent
     */
    public SComponentHelper(JComponent comp) {
        _comp = comp;
        comp.addFocusListener(this);
        comp.addKeyListener(this);
        comp.addMouseListener(this);
        if (comp instanceof JToggleButton) {
            ((JToggleButton) comp).addItemListener(this);
        } else if (comp instanceof JComboBox) {
            ((JComboBox) comp).addActionListener(this);
        } else if (comp instanceof JList) {
            ((JList) comp).addListSelectionListener(this);
        }
        else if (comp instanceof JTextComponent) {
            ((JTextComponent) comp).setDocument(new MaxLengthInput());
            ((JTextComponent) comp).getDocument().addDocumentListener(this);
            addKeyboardMappings(comp);
            addPopupMenu(comp);
            if (comp instanceof JTextArea)
                ((JTextArea)comp).setLineWrap(true);
        }
        else if (comp instanceof  JSpinner) {
            ((JSpinner)comp).addChangeListener(this);
        }
        else if (comp instanceof  JSlider) {
            ((JSlider)comp).addChangeListener(this);
        }
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     */
    public void keyPressed(KeyEvent e) {
        PopupManager.getPopupManager().hideWindow();
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     */
    public void keyReleased(KeyEvent e) {
    }


      /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent e) {
          if (! _dataDirty) {
            ValueChangedEvent evt = ((SComponent) _comp).generateValueChangedEvent();
            if (evt != null)
                  notifyValueChangedListeners(evt);
          }
    }

    /**
     * @see FocusListener#focusGained(FocusEvent)
     */
    public void focusGained(FocusEvent e) {
        Object o = e.getSource();
        if (o instanceof STextField) {
			((STextField) o).selectAll();
        }
    }

    private void acceptValue(Component comp) {
        Component parent = comp.getParent();
        if (parent instanceof JTable) {
            if (((JTable) parent).isEditing() && ! isMenuVisible()) {
                DefaultCellEditor ed = (DefaultCellEditor) ((JTable) parent).getCellEditor();
                ed.stopCellEditing();
            }
        }
	    else if (_comp instanceof SComponent) {
            if (_dataDirty) {
                ValueChangedEvent evt = ((SComponent) _comp).generateValueChangedEvent();
                if (evt != null)
                    notifyValueChangedListeners(evt);
            }
        }
    }

    /**
     * Components update the model when they lose focus. Just in case you want to update the model on a component with focus programatically like if the user presses a hot key, call accept value on a component to get it to move the value to the model.
     */
    public void acceptValue() {
        acceptValue((JComponent)_comp);
    }

    /**
     * Call acceptValue on the item that currently has focus.
     * @see #acceptValue()
     */
    public static void acceptCurrentValue() {
        Object focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focusOwner instanceof SComponent)
           ((SComponent) focusOwner).getHelper().acceptValue();
    }
    /**
     * @see FocusListener#focusLost(FocusEvent)
     */
    public void focusLost(FocusEvent e) {
        Component comp = (Component) e.getSource();
        Component prev = e.getOppositeComponent();

        if (comp instanceof STable) {
            STable tab = (STable)comp;
            if (prev == null || !(prev.getParent() == comp)) {
                if (tab.isEditing()) {
                    Component editingComp = tab.getEditorComponent();
                    if (editingComp instanceof SComponent) {
                        if (((SComponent)editingComp).getHelper().isMenuVisible())
                            return;
                    }
                    DefaultCellEditor ed = (DefaultCellEditor) ((JTable) comp).getCellEditor();
                    ed.stopCellEditing();
                    return;
                }
            }
        }
        else if (comp instanceof JComboBox) {
            if (comp.getParent() instanceof JTable)
                return;
        }
        if (comp != null)
            acceptValue(comp);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e) {
        PopupManager.getPopupManager().hideWindow();
        if (e.getModifiers() == MouseEvent.META_MASK) {
            if (_comp instanceof STable) {
                STable tab = (STable)_comp;
                int row = tab.rowAtPoint(e.getPoint());
                int col = tab.columnAtPoint(e.getPoint());
                if (tab.isEditing())
                    tab.getCellEditor().stopCellEditing();
                tab.changeSelection(row,col,false,false);
                CellEditor edit = tab.getCellEditor(row,col);
                if (edit != null && edit instanceof DefaultCellEditor) {
                    Component comp = ((DefaultCellEditor)edit).getComponent();
                    if (comp instanceof SComponent) {
                        SComponentHelper helper = ((SComponent)comp).getHelper();
                        JPopupMenu menu = helper.getMenu();
                        if (menu != null) {
                            if (comp instanceof JComponent) {
                                PopupManager.getPopupManager().showPopupMenu(menu,(JComponent)_comp, e.getX(), e.getY());
                                tab.editCellAt(row,col);
                                ((JComponent)tab.getEditorComponent()).grabFocus();

                            }
                        }
                    }

                }
            }
            else if (_menu != null) {
                if (_comp instanceof JComponent) {
        	        ((JComponent)_comp).grabFocus();
                    _menu.show(((JComponent)_comp), e.getX(), e.getY());
                }
            }
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * @see DocumentListener#changedUpdate(DocumentEvent)
     */
    public void changedUpdate(DocumentEvent e) {
        _dataDirty = true;
    }

    /**
     * @see DocumentListener#insertUpdate(DocumentEvent)
     */
    public void insertUpdate(DocumentEvent e) {
        _dataDirty = true;
    }

    /**
     * @see DocumentListener#removeUpdate(DocumentEvent)
     */
    public void removeUpdate(DocumentEvent e) {
        _dataDirty = true;
    }

    /**
     * This method adds a listener the will be notified when the value in this component changes.
     * @param l The listener to add.
     */
    public void addValueChangedListener(ValueChangedListener l) {
        if (_valueChangedListeners == null)
            _valueChangedListeners = new Vector();
        //
        int listenersSize = _valueChangedListeners.size();
        for (int i = 0; i < listenersSize; i++) {
            if (((ValueChangedListener) _valueChangedListeners.elementAt(i)) == l)
                return;
        }
        _valueChangedListeners.addElement(l);
    }


    /**
     * This method removes a listener from the list that will be notified if the text in the component changes.
     * @param l The listener to remove.
     */
    public void removeValueChangedListener(ValueChangedListener l) {
        if (_valueChangedListeners == null)
            return;

        for (int i = 0; i < _valueChangedListeners.size(); i++) {
            if (((com.salmonllc.html.events.ValueChangedListener) _valueChangedListeners.elementAt(i)) == l) {
                _valueChangedListeners.removeElementAt(i);
                return;
            }
        }
    }

    /**
     * Called from the framework when value changed listeners for the component need to be fired
     * @param evt
     */
    public void notifyValueChangedListeners(ValueChangedEvent evt) {
        if (evt.getNewValue() != null && evt.getNewValue().length() == 0)
            evt.setNewValue(null);

        if (_valueChangedListeners != null)
            for (int i = 0; i < _valueChangedListeners.size(); i++) {
                ValueChangedListener l = (ValueChangedListener) _valueChangedListeners.elementAt(i);
                if (!l.valueChanged(evt))
                    break;
            }

        DataStoreBuffer ds = evt.getDataStore();
        int row = evt.getRow();
        int col = evt.getColumn();

        try {
			if (evt.getAcceptValueInt() == ValueChangedEvent.PROCESSING_MOVE_CHANGE_TO_TEMP) {
                _dataDirty = false;
                ds.setTempValue(row, col, evt.getNewValue());
            } else if (evt.getAcceptValueInt() == ValueChangedEvent.PROCESSING_COMMIT_CHANGE) {
                _dataDirty = false;
                ds.setFormattedString(row, col, evt.getNewValue());
            } else if (evt.getAcceptValueInt() == ValueChangedEvent.PROCESSING_DISCARD_CHANGE) {
                ModelChangedEvent e = new ModelChangedEvent(evt.getDataStore(),evt.getColumn(),evt.getOldValue(),evt.getNewValue());
                e.getDataStore().notifyListeners(e);
            }

        } catch (DataStoreException e) {
            e.printStackTrace();
        }

    }

    /**
     * Test to see if the data in the component is dirty (changed, but not moved to the model)
     */
    public boolean isDataDirty() {
        return _dataDirty;
    }

    /**
     * Sets the data dirty flag for the component (data is changed, but not moved to the model)
     */
    public void setDataDirty(boolean dataDirty) {
        _dataDirty = dataDirty;
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        if (! _dataDirty) {
            ValueChangedEvent evt = ((SComponent) _comp).generateValueChangedEvent();
            if (evt != null)
                notifyValueChangedListeners(evt);
        }
    }

    /**
     * Adds keyboard mappings to the component. Sets shift+delete, shift+insert, control+insert to cut/paste/copy and sets the tab to a traversal key for a text area.
     * @param comp
     */
    public void addKeyboardMappings(JComponent comp) {
        InputMap m = comp.getInputMap();
        m.put(KeyStroke.getKeyStroke("shift DELETE"), DefaultEditorKit.cutAction);
        m.put(KeyStroke.getKeyStroke("shift INSERT"), DefaultEditorKit.pasteAction);
        m.put(KeyStroke.getKeyStroke("control INSERT"), DefaultEditorKit.pasteAction);
        if (comp instanceof STextArea) {
            Set forward  = new HashSet();
            Set backward  = new HashSet();
            forward.add(KeyStroke.getKeyStroke("TAB"));
            backward.add(KeyStroke.getKeyStroke("shift TAB"));
            comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);
            comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);
        }
    }

    /**
     * Removes the keyboard mappings from a component added with addKeyboardMappings
     */
    public void removeKeyboardMappings(JComponent comp) {
        InputMap m = comp.getInputMap();
        m.remove(KeyStroke.getKeyStroke("shift DELETE"));
        m.remove(KeyStroke.getKeyStroke("shift INSERT"));
        m.remove(KeyStroke.getKeyStroke("control INSERT"));
    }

    /**
     * Adds a right click cut/copy/paste menu to the component
     */
    public void addPopupMenu(JComponent comp) {
    	if (comp instanceof STextField || comp instanceof STextArea) {
	        ActionMap am = comp.getActionMap();
    	    _menu = new JPopupMenu();
	        JMenuItem cut = new JMenuItem("Cut");
    	    cut.addActionListener(am.get(DefaultEditorKit.cutAction));
	        JMenuItem copy = new JMenuItem("Copy");
    	    copy.addActionListener(am.get(DefaultEditorKit.copyAction));
	        JMenuItem paste = new JMenuItem("Paste");
    	    paste.addActionListener(am.get(DefaultEditorKit.pasteAction));
        	_menu.add(cut);
	        _menu.add(copy);
    	    _menu.add(paste);

            JMenuItem select = new JMenuItem("Select All");
            select.addActionListener(new SelectAllAction((JTextComponent) comp));
            _menu.add(select);

            JMenuItem clear= new JMenuItem("Clear");
            clear.addActionListener(new ClearAction((JTextComponent) comp));
            _menu.add(clear);
        }

    }

    /**
     * Removes a right click menu from the component
     */
    public void removePopupMenu() {
        _menu = null;
    }

    /**
     * Utility method that compares two values to see if they are equal
     */
    public static boolean valuesEqual(Object newValue, Object oldValue) {
        if (newValue == null && oldValue != null)
            return false;
        else if (newValue != null && oldValue == null)
            return false;
        else if (newValue != null && oldValue != null)
            if (!newValue.toString().trim().equals(oldValue.toString().trim()))
                return false;

        return true;
    }

    /**
     * Components may have a right click cut/copy/paste menu. This method indicates that whether or not it is currently visible
     */
    public boolean isMenuVisible() {
    	if (_menu == null)
    		return false;
    	return _menu.isVisible();
    }

    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    public void valueChanged(ListSelectionEvent e) {
         if (! _dataDirty) {
            ValueChangedEvent evt = ((SComponent) _comp).generateValueChangedEvent();
            if (evt != null)
                notifyValueChangedListeners(evt);
        }
    }

    /**
     * Returns the right click popup menu for this component
     */
    public JPopupMenu getMenu() {
        return _menu;
    }

	/**
	 * Enables or disables all components in the container
	 */
	public static void setAllComponentsEnabled(Container cont,boolean enabled) {
		cont.setEnabled(enabled);
		Component[] comp = 	cont.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof Container) 
				setAllComponentsEnabled((Container) comp[i],enabled);
			else
				comp[i].setEnabled(enabled);
		}
	}
    /**
     * Sets all components in the container to focusable, skipping ones that are currently set to false. This method is mainly used as a work around for a bug in JDK 1.4 with respect to applets. Without explicitly calling setFocusable(true) on each component, they do not respond to the tab key.
     */
    public static void setAllComponentsToFocusable(Container cont) {
    	setAllComponentsToFocusable(cont,true);
    }

    /**
     * Sets all components in the container to focusable, and will skip ones that are currently set to false depending on the argument passed. This method is mainly used as a work around for a bug in JDK 1.4 with respect to applets. Without explicitly calling setFocusable(true) on each component, they do not respond to the tab key.
     */
    public static void setAllComponentsToFocusable(Container cont,boolean skipFalse) {
    	setComponentToFocusable(cont, skipFalse);
		Component[] comp = 	cont.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof Container)
				if (! (comp[i] instanceof JTable))
					setAllComponentsToFocusable((Container) comp[i],skipFalse);
			else
					setComponentToFocusable(comp[i],skipFalse);

		}
    }

    private static void setComponentToFocusable(Component comp, boolean skipFalse) {
        if (skipFalse && ! comp.isFocusable())
            return;
    	if (comp instanceof JLabel)
    		return;
		else if (comp instanceof JPanel)
			return;
		else if (comp instanceof JScrollBar)
			return;
		else if (comp instanceof JScrollPane)
			return;
		else if (comp instanceof JWindow)
			return;
		else if (comp instanceof JViewport)
			return;
		else if (comp instanceof CellRendererPane)
			return;
		else if (comp instanceof BasicArrowButton)
			return;
		else if (comp instanceof SComboBox)
			return;
		else if (comp instanceof javax.swing.Box)
			return;
		comp.setFocusable(true);
    }

    /**
     * Sets the wait mouse cursor for this component
     */
    public static void setWaitCursor(JComponent comp) {
        setCursor(comp,new Cursor(Cursor.WAIT_CURSOR));
    }

    /**
     * Sets the default mouse cursor for this component
     */
    public static void setDefaultCursor(JComponent comp) {
        setCursor(comp,new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Sets the mouse cursor for a component
     */
    public static void setCursor(JComponent comp,Cursor cur) {
		comp.setCursor(cur);
		Component parent = comp.getParent();
		while (parent != null) {
			parent = parent.getParent();
			if (parent != null)
				parent.setCursor(cur);
		}
	}

    /**
     * Finds the parent frame of the component or null if there isn't one
     */
     public static JFrame getParentFrame(JComponent comp) {
        Component p = comp.getParent();
        while (p != null) {
            if (p instanceof JFrame)
                return (JFrame) p;
            else
               p = p.getParent();
        }
        return null;
    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e  a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e) {
        if (! _dataDirty) {
            ValueChangedEvent evt = ((SComponent) _comp).generateValueChangedEvent();
            if (evt != null)
                notifyValueChangedListeners(evt);
        }
    }

    int getMaxLength() {
        return _maxLength;
    }

    void setMaxLength(int maxLength) {
        _maxLength = maxLength;
    }

    	/**
	 * Applies a SOFIA skin to the look and feel defaults for the application
	 */
	public static void applySkin(Skin sk, Container p) {
			try {
			String metalTheme = sk.getSwingMetalTheme();
			if (metalTheme != null) {
				Class c = Class.forName(metalTheme);
				MetalTheme theme = (MetalTheme)c.newInstance();
                if (theme instanceof com.salmonllc.personalization.SMetalTheme)
                    sk.applyAttributes(theme);
                MetalLookAndFeel.setCurrentTheme(theme);
			}
			String lfd = sk.getSwingLookAndFeel();
			if (lfd == null)
				lfd = "default";
			UIManager.LookAndFeelInfo info[] = UIManager.getInstalledLookAndFeels();
			boolean found = false;
			for (int i = 0; i < info.length;i++) {
				if ( lfd.equals(info[i].getClassName()) ) {
					found=true;
					break;
				}
				else if (lfd.equals(info[i].getName())) {
					lfd = info[i].getClassName();
					found=true;
					break;
				}
			}

			if (! found)
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			else
				UIManager.setLookAndFeel(lfd);

             applyAttributes(p,sk);


		} catch (Exception e) {
		}

	}

    private static void applyAttributes(Object comp, Skin sk) {
        sk.applyAttributes(comp);
        if (comp instanceof JComponent)
            ((JComponent) comp).updateUI();
        if (comp instanceof STable) {
            JTableHeader header = ((STable)comp).getTableHeader();
            header.updateUI();
            STableColumn col[] = ((STable)comp).getColumns();
            for (int i = 0; i < col.length; i++) {
                applyAttributes(col[i],sk);
            }
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container)comp).getComponents();
            for (int i = 0; i < comps.length; i++)
                applyAttributes(comps[i],sk);
        }
    }
}
