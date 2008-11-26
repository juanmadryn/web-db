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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlCheckBox.java $
//$Author: Dan $ 
//$Revision: 19 $ 
//$Modtime: 10/17/03 1:05p $ 
/////////////////////////
 
import com.salmonllc.html.events.*;
import com.salmonllc.properties.*;
import java.util.*;


/**
 * This class is used to place a check box component on a page.
 */
public class HtmlCheckBox extends HtmlFormComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9180409437542839213L;
	private String _fontTagStart;
	private String _fontTagEnd;
	private String _onClick;
	private String _trueValue;
	private String _falseValue;
	private String _imageOn;
	private String _imageOff;
	private Integer _tabIndex;
	private String _accessKey;
/**
 * Constructs a new HTMLTextEdit component.
 * @param name The name of the component
 * @param p The page the component will be placed in.
 * @param trueValue A string representation that will be returned from getValue() when the checkbox is checked.
 * @param falseValue A string representation that will be returned from getValue() when the checkbox is not checked.
 */
public HtmlCheckBox(String name, com.salmonllc.html.HtmlPage p, String trueValue, String falseValue) {
	this(name,null,p,trueValue,falseValue);
	
}
/**
 * Constructs a new HTMLTextEdit component.
 * @param name The name of the component
 * @param theme The theme to use for loading properties.
 * @param p The page the component will be placed in.
 * @param trueValue A string representation that will be returned from getValue() when the checkbox is checked.
 * @param falseValue A string representation that will be returned from getValue() when the checkbox is not checked.
 */
public HtmlCheckBox(String name, String theme, com.salmonllc.html.HtmlPage p, String trueValue, String falseValue) {
	super(name,theme,p);
	
	_trueValue = trueValue;
	_falseValue = falseValue;
	
}
 public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	if (! _visible )
		return;

	String value = getValue(rowNo,true);
	
	boolean checked = false;
	if (value == null && _trueValue == null)
		checked = true;
	else if (value != null)
		if (value.equals(_trueValue))
			checked = true;

    String name = getFullName();
	if (rowNo > -1)
		name += "_" + rowNo;

	String tag = "<INPUT TYPE=\"CHECKBOX\" NAME=\"" + name + "\" ID=\"" + name + "\"";

     if ((! _enabled) && useDisabledAttribute())
         tag += " disabled=\"" + true + "\"";
	else if (!_enabled && (_imageOn != null) && (_imageOff != null)) {
		String out = "<IMG SRC=\"";
		if (checked)
			out += _imageOn;
		else
			out += _imageOff;
		out += "\">";		
		if (_fontTagStart != null) 
			out = _fontTagStart + out + _fontTagEnd;
		p.print(out);
		return;
	}

	if (checked)
		tag += " CHECKED";

	if (_onClick != null)
		tag += " onClick=\"" + _onClick + "\"";

	if (_class != null)
		tag += " class=\"" + _class + "\"";
         
	if (_tabIndex != null) 
		tag += " tabindex=\"" + _tabIndex + "\"";
		
	if (_accessKey != null)
		tag += " accesskey=\"" + _accessKey + "\"";
			

	tag += ">";	
			
	if (_fontTagStart != null) 
		tag = _fontTagStart + tag + _fontTagEnd;

	p.println(tag);
	writeFocusScript(p,rowNo);	
}
/**
 * This method returns the the String representation that will be returned from getValue() when the checkbox is not checked.
 */
public String getFalseValue() {
	return _falseValue;
}
/**
 * This method gets the end font tag for the component.
 */
public String getFontEndTag() {
	return _fontTagEnd;
}
/**
 * This method gets the start font tag for the component.
 */
public String getFontStartTag() {
	return _fontTagStart;
}
/**
 * This method gets the javascript to be executed when the component gets clicked.
 */
public String getOnClick() {
	return _onClick;
}
/**
 * This method returns the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public String getTrueValue() {
	return _trueValue;
}
public boolean processParms(Hashtable parms, int rowNo) throws Exception {
// fc: 10/18/02 Commented out the below lines as they are no longer required,
//        since a better approach is to check to see if the field is contained in
//        the form when submitted. see other change below
//	if (!getVisible() || !getEnabled())
//		return false;
		
	Object oldValue = _value;
	
	String name = getFullName();
	if (rowNo > -1) {
		name += "_" + rowNo;
		if (_dsBuff != null) {
			try {
				oldValue = _dsBuff.getAny(rowNo,_dsColNo);
			}
			catch (Exception e) {
				oldValue = null;
			}
		}	
	}
	else {
		if (_dsBuff != null) {
			try {
				oldValue = _dsBuff.getAny(_dsColNo);
			}
			catch (Exception e) {
				oldValue = null;
			}
		}	
	}

    //fc: 10/18/02 Added check to see if component exists in form if not
    //             then do not process. For Checkboxes if Component is not checked
    //             then parms does not contain the key. If checked it contains the key.
    //             Similarly if the Component does not exist on the form it will not be
    //             in the parms. This is why the check for getVisible & getEnabled is done.
    if (parms.containsKey(name))
       _value = _trueValue;
    else
       if (getVisible() && getEnabled())
         _value = _falseValue;
       else
         _value=oldValue==null?null:oldValue.toString();


    if (! valuesEqual(oldValue,_value)) {
         String s = null;
         if (oldValue != null)
             s = oldValue.toString();
         ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),s,_value,rowNo,_dsColNo,_dsBuff);
         addEvent(e);
    }
	return false;
}
/**
 * This method sets the the String representation that will be returned from getValue() when the checkbox is not checked.
 */
public void setFalseValue(String falseValue) {
    _falseValue=falseValue;
}
/**
 * This method sets the end font tag for the component.
 */
public void setFontEndTag(String value) {
	_fontTagEnd = value;
}
/**
 * This method sets the start font tag for the component.
 */
public void setFontStartTag(String value) {
	_fontTagStart = value;
}
/**
 * This method sets the javascript to be executed when the component is checked.
 */
public void setOnClick(String value) {
	_onClick = value;
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public void setTheme(String theme) {

	super.setTheme(theme);
	
	Props props = getPage().getPageProperties();

	_fontTagStart = props.getThemeProperty(theme,Props.FONT_TEXT_EDIT + Props.TAG_START);
	_fontTagEnd = props.getThemeProperty(theme,Props.FONT_TEXT_EDIT + Props.TAG_END);
	_imageOn = props.getThemeProperty(theme,Props.CHECKBOX_IMAGE_ON);
	_imageOff = props.getThemeProperty(theme,Props.CHECKBOX_IMAGE_OFF);
	
	
}
/**
 * This method sets the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public void setTrueValue(String trueValue) {
    _trueValue=trueValue;
}

/**
 * @returns the access key html attribute
 */
public String getAccessKey() {
	return _accessKey;
}



/**
 * @returns the tab index html attribute
 */
public int getTabIndex() {
	if (_tabIndex == null)
		return -1;
	return _tabIndex.intValue();
}

/**
 * @param sets the access key html attribute
 */
public void setAccessKey(String string) {
	_accessKey = string;
}



/**
 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
 */
public void setTabIndex(int val) {
	if (val == -1)
		_tabIndex = null;
	else	
		_tabIndex = new Integer(val);
}
}
