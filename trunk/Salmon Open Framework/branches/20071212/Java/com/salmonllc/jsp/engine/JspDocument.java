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

package com.salmonllc.jsp.engine;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/engine/JspDocument.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 10/30/02 2:58p $
/////////////////////////

/**
 * Contains a linked list of JSPElements that together represent a JSP document
 */
class JspDocument {
	JspElement _last;
	JspElement _first;
public JspDocument() {
	super();
}
/**
 * Append an element to the end of the list
 */
public void append(JspElement e) {
	if (_first == null) {
		_first = e;
		_last = e;
	}	
	else {
		_last.setNext(e);
		_last = e;
	}
	e.setNext(null);
}
/**
 * Get the first element in the document
 */
public JspElement getFirst() {
	return _first;
}
/**
 * Get the last element in the document
 */
public JspElement getLast() {
	return _last;
}
/**
 * Insert an element at the begining of the list.
 */
public void insert(JspElement e) {
	if (_first == null) {
		_first = e;
		_last = e;
		e.setNext(null);
	}	
	else {
		e.setNext(_first);
		_first = e;
	}
}
}
