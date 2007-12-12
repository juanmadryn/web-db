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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlRadioButton.java $
//$Author: Dan $ 
//$Revision: 18 $ 
//$Modtime: 10/20/03 12:43p $ 
/////////////////////////

import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;

/**
 * This class is used for adding a single radio button to the page.  As an alternative to
 * <a href="com.salmonllc.html.HtmlRadioButtonGroup.html"><code>HtmlRadioButtonGroup</code></a>, 
 * this class is more flexible but more work is required.
 * You can put radio buttons in any desired layout, interspersed with text and
 * other controls.  To link a set of individual radio buttons (so they will behave as a group),
 * give each one the same name.
 */
public class HtmlRadioButton extends HtmlFormComponent  {
	public static final int CAPTIONS_ON_LEFT = 0;
	public static final int CAPTIONS_ON_RIGHT = 1;
	
	private String _fontTagStart = null;
	private String _fontTagEnd = null;

	private String _key;
	private String _display;
	
	private int _captionLayout = CAPTIONS_ON_LEFT;
	private String _onClick = null;
	private boolean _useProportions = false;
	
	private String _imageOn = null;
	private String _imageOff = null;

	private String _group = null;
	private Integer _tabIndex;
	private String _accessKey;
	
/**
 * Constructs a new HtmlRadioButton component.
 * @param name The name of the component
 * @param p The page the component will be placed in.
 */
public HtmlRadioButton(String name, HtmlPage p, String key, String display) {
	super(name, p);
	_key = key;
	_display = display;
	
	Props props = p.getPageProperties();
	_fontTagStart = props.getProperty(Props.FONT_DEFAULT + Props.TAG_START);
	_fontTagEnd = props.getProperty(Props.FONT_DEFAULT + Props.TAG_END);
	_imageOn = props.getProperty(Props.RADIOBUTTON_IMAGE_ON);
	_imageOff = props.getProperty(Props.RADIOBUTTON_IMAGE_OFF);


}
 public void generateHTML(java.io.PrintWriter p,int rowNo) throws Exception {
	if (! _visible)
		return;
	if ((!getEnabled()) && (!useDisabledAttribute())) {
		generateHTMLDisabled(p, rowNo);
		return;
	}

	_value = getValue(rowNo,true);

	String tag = "";
	if ( _captionLayout == CAPTIONS_ON_LEFT)
		tag += getCaptionTag() + getRadioButtonTag(rowNo,_value);
	else
		tag += getRadioButtonTag(rowNo,_value) + getCaptionTag();
		
	p.print(tag);
	writeFocusScript(p,rowNo);
}
public void generateHTMLDisabled(java.io.PrintWriter p,int rowNo) throws Exception {
	_value = getValue(rowNo,true);
	String tag = "";

	if ( _captionLayout == CAPTIONS_ON_LEFT)
		tag += getDisabledCaptionTag() + getDisabledRadioButtonTag(rowNo,_value);
	else
		tag += getDisabledRadioButtonTag(rowNo,_value) + getDisabledCaptionTag();
		
	p.print(tag);
	writeFocusScript(p,rowNo);
	
}
/**
 * Returns the caption layout of the component. Valid Values are CAPTIONS_ON_LEFT, CAPTIONS_ON_RIGHT and CAPTIONS_ON_TOP.
 */
public int getCaptionLayout() {
	return _captionLayout;
}
private String getCaptionTag() {
	if (_display == null)
		return "&nbsp;";
	String fontS = _fontTagStart;
	if (fontS == null)
		fontS = "";
	String fontE = _fontTagEnd;
	if (fontE == null)
		fontE = "";
	return fontS + _display + fontE;
}
private String getDisabledCaptionTag() {
	if (_display == null)
		return "&nbsp;";
	String fontS = _disabledFontStartTag;
	if (fontS == null)
		fontS = "";
	String fontE = _disabledFontEndTag;
	if (fontE == null)
		fontE = "";
	return fontS + _display + fontE;
}
private String getDisabledRadioButtonTag(int rowNo, String val) {
	if (_key == null)
		return "&nbsp;";
		
	boolean checked = false;
	if (val != null) {
		if (val.equals(_key)) 
			checked = true;
	}

	if (!_enabled && (_imageOn != null) && (_imageOff != null)) {
		String out = "<IMG SRC=\"";
		if (checked)
			out += _imageOn + "\"";
		else
			out += _imageOff + "\"";
		out += ">";		
		return out;
	}
	String name="";
	if (_group != null)
	 name = _group;
	else
	 name = getFullName();	
	if (rowNo > -1)
		name += "_" + rowNo;
		
	String tag = "<INPUT TYPE=\"RADIO\" NAME=\"" + name + "\" VALUE=\"" + _key + "\"";

	if (_onClick != null) 
		 tag += " ONCLICK=\"" + _onClick + "\"";
	if (checked)
		tag += " CHECKED";
	tag += ">";	
			
	if (_disabledFontStartTag != null) 
		tag = _disabledFontStartTag + tag + _disabledFontEndTag;		

	return tag;
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
 * This method gets the group Name.
 */
public String getGroup() {
	return _group;
}
/**
 * This method gets the javascript to be executed when the component gets clicked.
 */
public String getOnClick() {
	return _onClick;
}
private String getRadioButtonTag(int rowNo, String val) {
	if (_key == null)
		return "&nbsp;";
		
	boolean checked = false;
	if (val != null) {
		if (val.equals(_key)) 
			checked = true;
	}

    String name="";
    if (_group != null)
      name = _group;
    else
      name = getFullName();
    if (rowNo > -1)
        name += "_" + rowNo;

    String tag = "<INPUT TYPE=\"RADIO\" NAME=\"" + name + "\" VALUE=\"" + _key + "\"";

    if ((! _enabled) && useDisabledAttribute())
         tag+= " disabled=\"true\"";
	else if (!_enabled && (_imageOn != null) && (_imageOff != null)) {
		String out = "<IMG SRC=\"";
		if (checked)
			out += _imageOn + "\"";
		else
			out += _imageOff + "\"";
		out += ">";		
		return out;
	}

	if (_onClick != null) 
		 tag += " ONCLICK=\"" + _onClick + "\"";
	if (_class != null) 
		 tag += " CLASS=\"" + _class + "\"";
	if (checked)
		tag += " CHECKED";
	if (_tabIndex != null) 
		tag += " tabindex=\"" + _tabIndex + "\"";
	if (_accessKey != null)
		tag += " accesskey=\"" + _accessKey + "\"";
			
	tag += ">";	
			
	if (_fontTagStart != null) 
		tag = _fontTagStart + tag + _fontTagEnd;		

	return tag;
}
/**
 * Returns whether or not to use a fixed percent of available space for each button in the radio button group (Horizontal Orientation only)
 */
public boolean getUseProportions() {
	return _useProportions;
}
public boolean processParms(Hashtable parms, int rowNo) throws Exception {
	if (!getEnabled())
		return false;
	Object oldValue = _value;
	String name="";
	if (_group !=null)
	 name = _group;
	else
	 name = getFullName();
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

	if (val == null)
		_value = null;
	else 
		_value = val[0];	

	if (! valuesEqual(oldValue,_value) && _value != null) {
		String s = null;
		if (oldValue != null)
			s = oldValue.toString();
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),s,_value,rowNo,_dsColNo,_dsBuff);
		addEvent(e);
	}
	
	
	return false;
}
/**
 * Sets the caption layout of the component. Valid Values are CAPTIONS_ON_LEFT, CAPTIONS_ON_RIGHT and CAPTIONS_ON_TOP.
 */
public void setCaptionLayout(int layout) {
	_captionLayout = layout;
}
/**
 * This method sets the layout property for the component
 */
public void setCaptionLayout(String layout) {
	setCaptionLayout(layout == null || !layout.toUpperCase().equals("RIGHT") ? CAPTIONS_ON_LEFT : CAPTIONS_ON_RIGHT);
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
 * This method sets the group.
 */
public void setGroup(String value) {
	_group = value;
}
/**
 * This method sets the javascript to be executed when the component is checked.
 */
public void setOnClick(String value) {
	_onClick = value;
}
/**
 * This method is used to set whether the each button in the group will occupy a fixed percent available space (Horizontal Orientation only)
 */
public void setUseProportions(boolean use) {
	_useProportions = use;
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
