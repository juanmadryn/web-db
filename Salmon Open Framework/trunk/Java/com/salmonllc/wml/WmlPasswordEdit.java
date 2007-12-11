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
package com.salmonllc.wml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlPasswordEdit.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 4/15/03 1:56a $ 
/////////////////////////
 
import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;


/**
 * This class is used for password style text input in a page.
 */
public class WmlPasswordEdit extends WmlFormComponent{
	private int _maxLength = 0;
	private int _size = 10;
    private String _format;
    private boolean _emptyok=false;
    private int _tabindex=-1;
    private String _title;

/**
 * Constructs a new WMLPasswordEdit component.
 * @param name The name of the component
 * @param p The page the component will be placed in.
 */
public WmlPasswordEdit(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p);
}
    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
 public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	if (! _visible )
		return;
	
	if (!getEnabled()) {
		String value = getValue(rowNo,true);
		String out = "";
		if (value == null)
			value = "";
		int n = value.length();
		while (n-- > 0)
			out += "*";
		n = _size - value.length();
		while (n-- > 0)
			out += "&nbsp;";
		if ((value.length() == 0) && (_size <= 0))
			out = "&nbsp;";
		p.print(out);
		return;
	}
	String name = getName();
	if (rowNo > -1)
		name += "_" + rowNo;
	
	String tag = "<input type=\"password\" id=\"" + name + "\" name=\"" + name +"\"";

	if (_maxLength > 0)
		tag += " maxlength=\"" + _maxLength + "\"";

	if (_size > 0) {
		int size = _size;
		tag += " size=\"" + size + "\"";
	}
		
	String value = getValue(rowNo,true);
		
	if (value != null)
		tag += " value=\"" + value + "\"";

	if (_class != null)
		tag += " class=\"" + _class + "\"";

     if (_format != null)
         tag += " format=\"" + _format + "\"";

     if (_emptyok)
         tag += " emptyok=\"True\"";

     if (_tabindex > -1)
         tag += " tabindex=\"" + _tabindex + "\"";

     if (_title != null)
         tag += " title=\"" + _title + "\"";

	tag += "/>";
			

	p.println(tag);
}
/**
 * This method gets the maximum length for the text in the component.
 */
public int getMaxLength() {
	return _maxLength;
}
/**
 * This method gets the display size for the component in characters.
 */
public int getSize() {
	return _size;
}

/**
 * This method gets the format mask for the input.
 */
public String getFormat() {
	return _format;
}
    /**
     * This method gets the title for the input.
     */
    public String getTitle() {
        return _title;
    }
    /**
     * This method gets the tabindex of the input.
     */
    public int getTabIndex() {
        return _tabindex;
    }
    /**
     * This method gets the emptyok flag of the input.
     */
    public boolean getEmptyOk() {
        return _emptyok;
    }
    /**
     *Processes the submitted parameters. This method is called by the framework and should not be called directly
     */
public boolean processParms(Hashtable parms, int rowNo) throws Exception {
	if (!getEnabled())
		return false;
	Object oldValue = _value;
	
	String name = getName();
	if (rowNo > -1) { 
//		name += "_" + rowNo;
		if (_dsBuff != null) 
			oldValue = _dsBuff.getAny(rowNo,_dsColNo);
	}
	else {
		if (_dsBuff != null) 
			oldValue = _dsBuff.getAny(_dsColNo);
	}		
		
	String val[] = (String[]) parms.get(name);

	if (val != null) {
		_value = val[0].trim();
		if (_value.equals(""))
			_value = null;
	}		
	else
		_value = null;	

	if (! valuesEqual(oldValue,_value)) {
		String s = null;
		if (oldValue != null)
			s = oldValue.toString();
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getName(),s,_value,rowNo,_dsColNo,_dsBuff);
		addEvent(e);
	}

	return false;

}
/**
 * This method sets the maximum length for the text in the component.
 */
public void setMaxLength(int maxLength) {
	_maxLength = maxLength;
}
/**
 * This method sets the display size for the component in characters.
 */
public void setSize(int size) {
	_size = size;
}
    /**
     * This method sets the format mask for input.
     */
    public void setFormat(String format) {
        _format = format;
    }
    /**
     * This method sets the emptyok flag for input.
     */
    public void setEmptyOk(boolean emptyok) {
        _emptyok = emptyok;
    }
    /**
     * This method sets the tabindex for input.
     */
    public void setTabIndex(int tabindex) {
        _tabindex = tabindex;
    }
    /**
     * This method sets the title for input.
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * This method gets the postfield form of this component.
     */
    public String getPostForm() {
        if (getVisible() && getEnabled())
          return "<postfield name=\""+getName()+"\" value=\"$("+getName()+")\"/>";
        return "";
    }

}
