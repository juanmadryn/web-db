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
package com.salmonllc.html.treeControl;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/treeControl/TreeEnumerator.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 10/30/02 2:58p $
/////////////////////////

import java.util.*;

/**
 * Enumerates through all the items on a tree
 */
public class TreeEnumerator implements java.util.Enumeration {
    Vector _list;
    int _index = 0;

    /**
     * Creates a new enumerator
     */

    public TreeEnumerator(Vector items) {
        _list = items;
    }

    /**
     * Returns the handle of the current element
     */

    public int getHandle() {
        return (_index - 1);
    }

    /**
     * Returns true if there are more elements in the enumeration.
     */

    public boolean hasMoreElements() {
        while (true) {
            if (_index >= _list.size())
                return false;

            if (_list.elementAt(_index) instanceof TreeNode)
                return true;

            _index++;
        }
    }

    /**
     * Returns the next element in the enumeration. It returns the key component for the tree node.
     */

    public Object nextElement() {
        TreeNode t = (TreeNode) _list.elementAt(_index);
        _index++;
        return t.getKey();
    }
}
