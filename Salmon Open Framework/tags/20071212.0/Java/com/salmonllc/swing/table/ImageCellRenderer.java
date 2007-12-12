package com.salmonllc.swing.table;

import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.swing.STable;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * @author  demian
 */
public class ImageCellRenderer implements TableCellRenderer {

    DataStoreEvaluator _mainEval;

    public ImageCellRenderer(DataStoreEvaluator mainEval) {
        _mainEval = mainEval;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Image img = evalExpression(row);
        JLabel lab = new JLabel(new ImageIcon(img));
        STable tab = (STable) table;
		
        if (! isSelected) {
            lab.setBackground(tab.getBackground(row));
            lab.setForeground(tab.getForeground(row));
        }
        else {
            lab.setBackground(tab.getSelectionBackground(row));
            lab.setForeground(tab.getSelectionForeground(row));
            lab.setOpaque(true);
        }
        return lab;
    }

    private Image evalExpression(int row) {
        Object o = null;
        try {
            if (_mainEval != null)
                o = _mainEval.evaluateRow(row);

             Image newImage = null;
             if (o instanceof String) {
                 URL location = new URL((String)o);
                 newImage = Toolkit.getDefaultToolkit().getImage(location);
             }
             else if (o instanceof byte[]) {
                 newImage = Toolkit.getDefaultToolkit().createImage((byte[]) o);
             }
             return newImage;
        }
        catch (DataStoreException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
