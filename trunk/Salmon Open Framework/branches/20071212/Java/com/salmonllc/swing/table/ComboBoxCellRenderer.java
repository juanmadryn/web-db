package com.salmonllc.swing.table;

import com.salmonllc.swing.*;
import java.awt.Component;
import javax.swing.*;

    /**
	 * @author  demian
	 */
    public class ComboBoxCellRenderer extends ExpressionCellRenderer {
        SComboBox _box;

        public ComboBoxCellRenderer(STable t,SComboBox b) {
            super(t);
            _box = b;
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
           	JLabel l = (JLabel) super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
			ListModel mod = _box.getModel();
			for (int i = 0; i < mod.getSize();i++) {
				Object o = mod.getElementAt(i);
				if (o instanceof SOption) {
					if (SComponentHelper.valuesEqual(((SOption) o).getKey(),value)) {
						l.setText(((SOption) o).getDisplay());
						return l;
					}							
				}	
			}
			return l;
        }
    }
