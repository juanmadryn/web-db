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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlRowSelector.java $
//$Author: Dan $ 
//$Revision: 16 $ 
//$Modtime: 6/11/03 4:39p $ 
/////////////////////////
 
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * This type can be used to add a row selector to an html table.  The row selector is either radio buttons that allows the selection of one row or check boxes that allow multiple selections.
 */
public class HtmlRowSelector extends HtmlFormComponent {
	private boolean _selectMultiple = false;
	private String _selectedValue = "1";
	private String _unSelectedValue = "0";
	private HtmlCheckBox _cbx;
	private String _imageOn;
	private String _imageOff;
	private String _onClick;
/**
 * Constructs a Html row selector object for an HtmlDataTable
 */
public HtmlRowSelector(String name, DataStoreBuffer ds, String dataStoreColumn, HtmlPage p) {
	this(name,ds,dataStoreColumn,null,p);
}
/**
 * Constructs a Html row selector object for an HtmlDataTable
 */
public HtmlRowSelector(String name, DataStoreBuffer ds, String dataStoreColumn, String theme, HtmlPage p) {
	super(name,p);
	setColumn(ds,dataStoreColumn);
	setTheme(theme);
}
public boolean executeEvent(int eventType) throws Exception {
	if (_cbx != null)
		return _cbx.executeEvent(eventType);
	else {
		if (_events != null) 
			if (_events.size() > 0 && _dsBuff != null) 
				for (int i = 0;i<_dsBuff.getRowCount();i++) 
					_dsBuff.setAny(i,_dsColNo,_unSelectedValue);
					
		return super.executeEvent(eventType);
	}	
}
 public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {

	if (_cbx != null)
		_cbx.generateHTML(p,rowNo);
	else {
		boolean checked = false;
		_value = getValue(rowNo);
		
		if (_value != null) {
		   if (_value.equals(_selectedValue)) 
				checked = true;
		}	
			
		if (!getEnabled() && (_imageOn != null) && (_imageOff != null)) {
			String out = "<IMG SRC=\"";
			if (checked)
				out += _imageOn + "\"";
			else
				out += _imageOff + "\"";
			out += ">";
			p.println(out);
			return;
		}
		
		String name = getFullName();	
		String tag = "<INPUT TYPE=\"RADIO\" NAME=\"" + name + "\" VALUE=\"" + rowNo + "\"";

		if (_onClick!=null && !_onClick.trim().equals(""))
		  tag+=" ONCLICK=\""+_onClick+"\"";
		if (checked)
			tag += " CHECKED";

		tag += ">";	
			
		p.println(tag);
	}	
}
/**
 * This method returns whether the component can select multiple rows or not.
 */
public boolean getMultiple() {
	return _selectMultiple;
}
/**
 * This method gets the javascript to be executed when the component gets clicked.
 */
public String getOnClick() {
	return _onClick;
}
/**
 * This method gets the value to use if the row is selected.
 */
public String getSelectedValue() {
	return _selectedValue;
}
/**
 * This method gets the value to use if the row is not selected.
 */
public String getUnSelectedValue() {
	return _unSelectedValue;
}
public boolean processParms(java.util.Hashtable parms , int row) throws Exception {
	if (_cbx != null)
		return _cbx.processParms(parms,row);
	else {
		if (!getEnabled())
			return false;
		Object oldValue = _value;

		String name = getFullName();
		if (row > -1) {
			if (_dsBuff != null) 
				oldValue = _dsBuff.getAny(row,_dsColNo);
		}
		else {
			if (_dsBuff != null) 
				oldValue = _dsBuff.getAny(_dsColNo);
		}
	
		String val[] = (String[]) parms.get(name);

		int selectedRow = -1;
		if (val != null)
			selectedRow = Integer.parseInt(val[0]);	

		if (selectedRow == row) 
			_value = _selectedValue;
		else
			_value = _unSelectedValue;
				
		if (! valuesEqual(oldValue,_value) && _value.equals(_selectedValue)) {
			String s = null;
			if (oldValue != null)
				s = oldValue.toString();
			ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),s,_value,row,_dsColNo,_dsBuff);
			addEvent(e);
		}	
			
		return false;
	}	
}
/**
 * This method sets whether the component can select multiple rows or not.
 */
public void setMultiple(boolean multiple) {
	if (multiple) {
		_cbx = new HtmlCheckBox(getName(),getPage(),_selectedValue,_unSelectedValue);
		_cbx.setColumn(_dsBuff,_dsColNo);
	}
	else {
		_cbx = null;
	}		
	
	_selectMultiple = multiple;
}
/**
 * This method sets the javascript to be executed when the component is checked.
 */
public void setOnClick(String value) {
	_onClick = value;
}
/**
 * This method sets the value to use if the row is selected.
 */
public void setSelectedValue(String value) {
	_selectedValue = value;
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public void setTheme(String theme) {

	super.setTheme(theme);
	
	Props props = getPage().getPageProperties();
	_imageOn = props.getThemeProperty(theme,Props.RADIOBUTTON_IMAGE_ON);
	_imageOff = props.getThemeProperty(theme,Props.RADIOBUTTON_IMAGE_OFF);

}
/**
 * This method sets the value to use if the row is not selected.
 */
public void setUnSelectedValue(String value) {
	_unSelectedValue = value;
}
}
