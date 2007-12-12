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

import java.util.Vector;
import javax.servlet.jsp.tagext.TagSupport;
 
/**
 * @author  demian
 */
class JspElement {
	private JspElement _next;
	private String _text;
	private TagSupport _handler;
	private int _type;
	private String _error;
	private String _name;
	private Vector _attributes;
	private String _prefix;
	private JspElement _closingTag;
	
	public static final int TAG_EMPTY = 0;
	public static final int TAG_BODY_START = 1;
	public static final int TAG_BODY_END = 2;
	public static final int TAG_HTML = 3;
	public static final int TAG_ERROR = 4;
	public static final int TAG_NULL = -1;
public JspElement(String name,String prefix,String text, int type, TagSupport handler, String error) {
	_name = name;
	_text = text;
	_type = type;
	_handler = handler;
	_error = error;
	_prefix = prefix;
}
public JspElement(String name,String prefix,String text, int type, TagSupport handler, String error, Vector attributes) {
	this(name,prefix,text,type,handler,error);
	_attributes = attributes;
}
public Vector getAttributes() {
	return _attributes;
}
public JspElement getClosingTag() {
	return _closingTag;
}
public String getError() {
	return _error;
}
public TagSupport getHandler() {
	return _handler;
}
public String getName() {
	return _name;
}
public JspElement getNext() {
	return _next;
}
public String getPrefix() {
	return _prefix;
}
public String getText() {
	return _text;
}
public int getType() {
	return _type;
}
public void setClosingTag(JspElement tag) {
	_closingTag = tag;
}
public void setError(String error) {
	_error = error;
	_type = TAG_ERROR;
}
public void setNext(JspElement next) {
	_next = next;
}
public String toString() {
	return _text + "\n";
}
}
