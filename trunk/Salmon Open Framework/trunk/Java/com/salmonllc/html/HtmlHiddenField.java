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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlHiddenField.java $
//$Author: Srufle $ 
//$Revision: 12 $ 
//$Modtime: 4/15/03 1:56a $ 
/////////////////////////

import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;

/**
 * This class will generate a hidden form field.
 */
public class HtmlHiddenField extends HtmlFormComponent
{

/**
 * Constructs a new Hidden Field
 * @param name Each component on a page must have a unique name.
 * @param value The value to appear on the button.
 * @param p An HtmlPage object that will be used to initialize any properties in the object.
 */
public HtmlHiddenField(String name, String value, HtmlPage p) {
	super(name,p);
	_value = value;
}
public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	String name = getFullName();
	if (rowNo > -1)
		name += "_" + rowNo;
		
	if (! getVisible())
		return;
	
	_value = getValue(rowNo);

    String val = _value == null ? "" : _value;

	String out = "<INPUT TYPE=\"HIDDEN\" name=\"" + name + "\" value=\"" + val + "\">";

	p.println(out);
}
public boolean processParms(Hashtable parms, int rowNo)  throws Exception {
	Object oldValue = _value;
	
	String name = getFullName();
	if (rowNo > -1) {
		name += "_" + rowNo;
		if (_dsBuff != null) 
			oldValue = _dsBuff.getAny(rowNo,_dsColNo);
	}
	else {
		if (_dsBuff != null) 
			oldValue = _dsBuff.getAny(_dsColNo);
	}
	
	String val[] = (String[]) parms.get(name);

	if (val != null)
		_value = val[0];
	else
		_value = null;

    if (_value != null && _value.trim().length() == 0)
        _value = null;

	if (! valuesEqual(oldValue,_value)) {
		String s = null;
		if (oldValue != null)
			s = oldValue.toString();
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),s,_value,rowNo,_dsColNo,_dsBuff);
		addEvent(e);
	}
	
		
	return false;
}
}
