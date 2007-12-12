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
package com.salmonllc.forms;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/forms/CriteriaString.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
/**
 * Implements a string for SQL-command criteria.  The point of this class is to
 * manage connectors such as "and".
 */
public class CriteriaString {
	private String _stAnd;
	private String _buf;
/**
 * CriteriaString constructor comment.
 */
public CriteriaString() {
	super();
	reset();
}
/**
 * Adds a sub-criteria preceded by "and" if necessary.
 * @param s java.lang.String
 */
public void and (String s) {
	if ((s != null) && (s.length() > 0)) {
		String ss = s.trim().substring(0, 3).toUpperCase();
		if (!ss.equals("AND"))
			_buf += _stAnd;
		_buf += s;
		_stAnd = " and ";
	}
}
/**
 * Returns length of internal string buffer.
 * @return int
 */
public int length () {
	return _buf.length();
}
/**
 * Resets internal state.
 */
public void reset () {
	_stAnd = "";
	_buf = "";
}
/**
 * Convert to String.
 * @return java.lang.String
 */
public String toString () {
	if (_buf.length() == 0)
		return null;
	else
		return _buf;
}
}
