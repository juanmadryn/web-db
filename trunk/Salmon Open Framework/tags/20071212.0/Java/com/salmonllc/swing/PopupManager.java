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

import java.awt.*;
import javax.swing.*;

/**
 * This class is used by other components in the framework to display popups windows
 */
public class PopupManager{
    private static PopupManager _man;
    private Popup _curWindow;
    private JPopupMenu _curMenu;
    private static Color _errorWindowBackground = null;
    private static Color _errorWindowForeground = null;

    private PopupManager() {
        super();
    }

    /**
     * Returns a singleton popup manager
     */
    public static PopupManager getPopupManager() {
        if (_man == null)
            _man = new PopupManager();
        return _man;
    }

    /**
     * Hides the current visible window
     */
    public void hideWindow() {
        if (_curWindow != null) {
            _curWindow.hide();
        }
        if (_curMenu != null)
            _curMenu.setVisible(false);
    }

    /**
     * Displays an error bubble below the specified component
     * @param comp The component to display the bubble near
     * @param message The message to display
     */
    public void showErrorWindow(Component comp, String message) {
            hideWindow();
            Point screenLocation = comp.getLocationOnScreen();
            Rectangle compBounds = comp.getBounds();
            Point location = new Point();
            Rectangle screenBounds = comp.getGraphicsConfiguration().getBounds();
            JToolTip tip = new JToolTip();
            tip.setTipText(message);
            if (_errorWindowBackground != null)
                tip.setBackground(_errorWindowBackground);
            if (_errorWindowForeground != null)
                tip.setForeground(_errorWindowForeground);
            Dimension size = tip.getPreferredSize();
            location.x = screenLocation.x + 5;
            if ((location.x + size.width) > (screenBounds.x + screenBounds.width - 20))
                location.x = (screenBounds.x + screenBounds.width - 20) - size.width;

            location.y = screenLocation.y + compBounds.height + 5;
            if ((location.y + size.height) > (screenBounds.y + screenBounds.height - 30))
                location.y = screenLocation.y - size.height - 2;

            PopupFactory factory = PopupFactory.getSharedInstance();
            _curWindow = factory.getPopup(comp, tip, location.x, location.y);
            _curWindow.show();

            comp.requestFocus();
    }

	/**
	  * Displays an error bubble below the specified component
	  * @param comp The component to display the bubble near
	  * @param message The message to display
	  */
	 public void showPopupPanel(Component comp, JPanel panel) {
			 hideWindow();
			 Point screenLocation = comp.getLocationOnScreen();
			 Rectangle compBounds = comp.getBounds();
			 Point location = new Point();
			 Rectangle screenBounds = comp.getGraphicsConfiguration().getBounds();
			 Dimension size = panel.getPreferredSize();
			 location.x = screenLocation.x;
			 if ((location.x + size.width) > (screenBounds.x + screenBounds.width - 20))
				 location.x = (screenBounds.x + screenBounds.width - 20) - size.width;

			 location.y = screenLocation.y + compBounds.height;
			 if ((location.y + size.height) > (screenBounds.y + screenBounds.height - 30))
				 location.y = screenLocation.y - size.height;

			 PopupFactory factory = PopupFactory.getSharedInstance();
			 _curWindow = factory.getPopup(comp, panel, location.x, location.y);
			 _curWindow.show();

			 comp.requestFocus();
	 }

    /**
     * Sets the colors for error window popups
     * @param background the background color
     * @param forground the text color
     */
    public static void setErrorWindowColors(Color background, Color forground ) {
        _errorWindowBackground = background;
        _errorWindowForeground = forground;
    }

    /**
     *  Displays a popup menu, but hides any popup currently showing
     */
    public void showPopupMenu(JPopupMenu menu, JComponent comp, int x, int y) {
       //     hideWindow();
            _curMenu = menu;
            menu.setSelected(null);
            menu.show(comp,x, y);
            menu.setVisible(true);
    }
}
