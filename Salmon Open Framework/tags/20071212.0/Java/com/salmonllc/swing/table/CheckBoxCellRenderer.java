package com.salmonllc.swing.table;

import com.salmonllc.swing.SCheckBox;
import com.salmonllc.swing.STable;
import java.awt.Component;
import javax.swing.JTable;

    /**
	 * @author  demian
	 */
    public class CheckBoxCellRenderer extends ExpressionCellRenderer {
        SCheckBox _box;

        public CheckBoxCellRenderer(STable t,SCheckBox b) {
            super(t);
            _box = new SCheckBox(b.getText());
            _box.setTrueValue(b.getTrueValue());
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            boolean selected = false;
            if (value != null)
                if (value.toString().equals(_box.getTrueValue()))
                    selected = true;
            _box.setSelected(selected);
			if (!isSelected) {
				_box.setBackground(_tab.getBackground(row));
				_box.setForeground(_tab.getForeground(row));
			}	
			else {
				_box.setBackground(_tab.getSelectionBackground(row));
				_box.setForeground(_tab.getSelectionForeground(row));
			}	
            return _box;
        }
    }
