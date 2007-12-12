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
package com.salmonllc.sql;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSJoinDescriptor.java $
//$Author: Fred $ 
//$Revision: 12 $ 
//$Modtime: 6/11/04 8:16p $ 
/////////////////////////

import java.util.*;

/**
 * This class is public as an implementation detail. It should not be used outside the framework.
 */

public class DSJoinDescriptor implements java.io.Serializable {
	private Vector _left = new Vector(1, 1);
	private Vector _right = new Vector(1, 1);
	private boolean _outer;
    //fc 06/11/04: Added to specify join relationship type.
    private int _reltype;

	public DSJoinDescriptor(String left, String right, boolean outer) {
		super();
		setColumns(left, right);
		setOuter(outer);
        setRelationType(DataStoreBuffer.RELATION_ONE_TO_ONE);
	}

    //fc 06/11/04: Added new constructor.
    public DSJoinDescriptor(String left, String right, boolean outer,int reltype) {
        super();
        setColumns(left, right);
        setOuter(outer);
        setRelationType(reltype);
    }


	public String getLeftColumn(int no) {
		if (no < 0 || no >= _left.size())
			return null;
		return (String) _left.elementAt(no);
	}

	public int getLeftCount() {
		return _left.size();
	}

	public String getRightColumn(int no) {
		if (no < 0 || no >= _right.size())
			return null;
		return (String) _right.elementAt(no);
	}
	public int getRightCount() {
		return _right.size();
	}
	public boolean isOuter() {
		return _outer;
	}
	public void setColumns(String leftSide, String rightSide) {
		StringTokenizer t1 = new StringTokenizer(leftSide, ",", false);
		StringTokenizer t2 = new StringTokenizer(rightSide, ",", false);

		_left.removeAllElements();
		_right.removeAllElements();

		try {
			while (t1.hasMoreTokens())
				_left.addElement(t1.nextToken());

			while (t2.hasMoreTokens())
				_right.addElement(t2.nextToken());
		} catch (Exception e) {
		}

	}
	public void setOuter(boolean outer) {
		_outer = outer;
	}

    //fc 06/11/04: new method to get relationship type.
    public int getRelationType() {
        return _reltype;
    }

    //fc 06/11/04: new method to set relationship type.
    public void setRelationType(int reltype) {
        _reltype = reltype;
    }
}
