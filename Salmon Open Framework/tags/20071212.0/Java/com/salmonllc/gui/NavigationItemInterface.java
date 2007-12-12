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
package com.salmonllc.gui;

/**
 * Represents an Item in a group of a NavigationMenu. Creation date: (8/23/01 10:06:24 AM)
 * @author  : Fred Cahill
 */
public interface NavigationItemInterface {
/**
 * Returns the number of pixels of horizontal padding for this item in a group.
 * @return  int The number of pixels of horizontal padding.
 * @uml.property  name="horizPadding"
 */
	public int getHorizPadding();
/**
 * Returns the href link of this item.
 * @return  String The href link of the item.
 * @uml.property  name="hRef"
 */
	public String getHRef();
/**
 * Returns the submenu name associated with this item.
 * @return  String The submenu name of the item.
 * @uml.property  name="subMenu"
 */
	public String getSubMenu();
/**
 * Returns the target window name for the href link of this item.
 * @return  String The target window name for the href link of this item.
 * @uml.property  name="target"
 */
	public String getTarget();
/**
 * Returns the title displayed for this item.
 * @return  String The title of this item.
 * @uml.property  name="title"
 */
	public String getTitle();
/**
 * Returns whether this item is visible or not.
 * @return  boolean Indicates if item is visible or not.
 * @uml.property  name="visible"
 */
	public boolean getVisible();
/**
 * Sets the number of pixels of horizontal padding for this item
 * @param iHorizPadding  int The number of pixels of horizontal padding.
 * @uml.property  name="horizPadding"
 */
	public void setHorizPadding(int iHorizPadding);
/**
 * Sets the href link for this item
 * @param href  String The href link of the item.
 * @uml.property  name="hRef"
 */
	public void setHRef(String sHRef);
/**
 * Sets the submenu name for this item
 * @param sSubMenu  String The submenu name of the item.
 * @uml.property  name="subMenu"
 */
	public void setSubMenu(String sSubMenu);
/**
 * Sets the target window name for the href link of this item
 * @param sTarget  String The target window name of the href link of this item.
 * @uml.property  name="target"
 */
	public void setTarget(String sTarget);
/**
 * Sets the title displayed for this item
 * @param sTitle  String The title of the item.
 * @uml.property  name="title"
 */
	public void setTitle(String sTitle);
/**
 * Sets whether this item is visible or not
 * @param bVisible  boolean Indicates if item is visible or not.
 * @uml.property  name="visible"
 */
	public void setVisible(boolean bVisible);
}
