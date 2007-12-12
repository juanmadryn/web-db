package com.salmonllc.swing.table;

import com.salmonllc.swing.SComboBox;
import com.salmonllc.swing.SComponentHelper;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

    /**
	 * @author  demian
	 */
    public class ComboBoxCellEditor extends DefaultCellEditor {
        SComboBox _cbx;

        public ComboBoxCellEditor(SComboBox comboBox) {
            super(comboBox);
            _cbx = comboBox;
        }

		/**
		 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(JTable, Object, boolean, int, int)
		 */
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			SComboBox box = (SComboBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            box.setSelectedItem(value);
            if (box.isEditable()) {
                String val = (value == null ? null : value.toString());
                if (!SComponentHelper.valuesEqual(box.getValue(),val))
                    ((JTextComponent)box.getEditor().getEditorComponent()).setText(val);
            }
            else
                box.setSelectedItem(value);
			return box;
		}

        public boolean isCellEditable(EventObject anEvent) {
			return true;
		}
    }
