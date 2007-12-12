package com.salmonllc.swing.table;


import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import com.salmonllc.swing.STextField;
import com.salmonllc.swing.SComboBox;

    public class SComponentCellEditor extends DefaultCellEditor {

        public SComponentCellEditor(STextField field) {
            super(field);
        }

        public SComponentCellEditor(SComboBox field) {
             super(field);
         }

		/**
		 * @see javax.swing.CellEditor#isCellEditable(EventObject)
		 */
		public boolean isCellEditable(EventObject anEvent) {
			return true;
		}
		
	

    }
