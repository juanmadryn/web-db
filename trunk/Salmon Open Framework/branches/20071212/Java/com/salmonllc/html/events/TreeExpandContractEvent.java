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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/events/TreeExpandContractEvent.java $
//$Author: Dan $ 
//$Revision: 7 $ 
//$Modtime: 10/30/02 2:40p $ 
/////////////////////////
 
import com.salmonllc.html.treeControl.*;

/**
 * This object will be created and passed to every TreeExpand or TreeContract event method.
 * @see  TreeListener
 */
 
public class TreeExpandContractEvent extends java.awt.AWTEvent
{
	TreeBuffer _tb;	
	int _handle;
	boolean _isExpanding;
	
/**
 * This method constructs a new Event.
 */
 
public TreeExpandContractEvent(Object source,int handle, boolean exp, TreeBuffer tb) {
		super(source,0);
		_handle = handle;
		_isExpanding = exp;
		_tb = tb;
}
/**
 * This method returns the handle of the tree item that was expanded or contracted.
 */
public int getHandle() {
	return _handle;
}
/**
 * This method returns the treeBuffer for the tree control that was clicked.
 */
public TreeBuffer getTreeBuffer() {
	return _tb;
}
/**
 * This method returns true if the tree item is expanding and false if it is contracting.
 */

public boolean isExpanding() {
	return _isExpanding;
}
}
