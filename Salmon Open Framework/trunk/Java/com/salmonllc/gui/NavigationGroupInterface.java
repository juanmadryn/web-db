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
 * An interface of methods used to communicate to a group of items in a NavigationMenu Creation date: (8/23/01 10:03:52 AM)
 * @author  : Fred Cahill
 */

public interface NavigationGroupInterface {
/**
 * Adds an item to a group in a navigation menu
 * @param submenu String The name of a submenu if item is a submenu otherwise pass null
 * @param title String The text to appear for the item.
 * @param href String The href link of an item.
 * @param target String The target window for the href link of an item.
 * @param horizPadding int The number of pixels to pad the item by in the menu.
 * @return NavigationItemInterface The item which was added.
 */
		public NavigationItemInterface addItem(String submenu,String title,String href,String target,int horizPadding);
/**
 * Collapses an expanded group.
 */
	public void collapse();
/**
 * Contracts an expanded group.
 */
	public void contract();
/**
 * Expands a collapsed group.
 */
	public void expand();
/**
 * Returns the HRef Link Associated with this Group.
 * @return  String The Href of this group.
 * @uml.property  name="hRef"
 */
	public String getHRef();
/**
 * Returns the Image Associated with this Group.
 * @return  String The Image of this group.
 * @uml.property  name="image"
 */
	public String getImage();
/**
 * Returns the items within this Group.
 * @return NavigationItemInterface[] Array of items within this group.
 */
	public NavigationItemInterface[] getItems();
/**
 * Returns the number of pixels of vertical padding for this Group in the menu.
 * @return  int The number of pixels of vertical padding.
 * @uml.property  name="vertPadding"
 */
	public int getVertPadding();
/**
 * Returns whether this Group is visible or not.
 * @return  boolean Indicates if group is visible or not.
 * @uml.property  name="visible"
 */
	public boolean getVisible();
/**
 * Inserts an item to a group in a navigation menu at specified location
 * @param submenu String The name of a submenu if item is a submenu otherwise pass null
 * @param title String The text to appear for the item.
 * @param href String The href link of an item.
 * @param target String The target window for the href link of an item.
 * @param horizPadding int The number of pixels to pad the item by in the menu.
 * @param iLocation int The index of the Item to insert before.
 * @return NavigationItemInterface The item which was inserted.
 */
		public NavigationItemInterface insertItemAt(String submenu,String title,String href,String target,int horizPadding,int iLocation);
/**
 * Inserts an item to a group in a navigation menu at specified location
 * @param submenu String The name of a submenu if item is a submenu otherwise pass null
 * @param title String The text to appear for the item.
 * @param href String The href link of an item.
 * @param target String The target window for the href link of an item.
 * @param horizPadding int The number of pixels to pad the item by in the menu.
 * @param niiInsertBefore com.salmonllc.gui.NavigationItemInterface The Item to insert before.
 * @return NavigationItemInterface The item which was inserted.
 */
		public NavigationItemInterface insertItemAt(String submenu,String title,String href,String target,int horizPadding,NavigationItemInterface niiInsertBefore);
/**
 * Sets the href link for this group
 * @param href  String The href link of the group.
 * @uml.property  name="hRef"
 */
	public void setHRef(String sHRef);
/**
 * Sets the image for this group
 * @param sImage  String The Image of the group.
 * @uml.property  name="image"
 */
	public void setImage(String sImage);
/**
 * Sets the number of pixels of vertical padding for this group
 * @param iVertPadding  int The number of pixels of vertical padding.
 * @uml.property  name="vertPadding"
 */
	public void setVertPadding(int iVertPadding);
/**
 * Sets whether this group is visible or not
 * @param bVisible  boolean Indicates if group is visible or not.
 * @uml.property  name="visible"
 */
	public void setVisible(boolean bVisible);
}
