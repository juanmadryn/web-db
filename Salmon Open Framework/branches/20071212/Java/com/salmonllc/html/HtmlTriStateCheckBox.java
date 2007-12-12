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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTriStateCheckBox.java $
//$Author: Dan $ 
//$Revision: 11 $ 
//$Modtime: 6/11/03 4:27p $ 
/////////////////////////
 
import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;


/**
 * This class is used to place a check box component on a page.
 */
public class HtmlTriStateCheckBox extends HtmlFormComponent {
	private String _fontTagStart;
	private String _fontTagEnd;
	private String _checkedValue;
	private String _unCheckedValue;
	private String _mixedCheckedValue;
	private String _imageDisabledChecked;
	private String _imageDisabledUnchecked;
	private String _imageDisabledMixed;
	private String _imageChecked;
	private String _imageUnchecked;
	private String _imageMixed;
/**
 * Constructs a new HTMLTextEdit component.
 * @param name The name of the component
 * @param p The page the component will be placed in.
 * @param Unchecked Value A string representation that will be returned from getValue() when the checkbox is unchecked.
 * @param Checked Value A string representation that will be returned from getValue() when the checkbox is checked.
 * @param Mixed Checked Value A string representation that will be returned from getValue() when the checkbox is mixed checked.
 */
public HtmlTriStateCheckBox(String name, com.salmonllc.html.HtmlPage p, String checkedValue, String unCheckedValue,String mixedCheckedValue) {
	this(name,null,p,checkedValue,unCheckedValue,mixedCheckedValue);
	
}
/**
 * Constructs a new HTMLTextEdit component.
 * @param name The name of the component
 * @param theme The theme to use for loading properties.
 * @param p The page the component will be placed in.
 * @param Unchecked Value A string representation that will be returned from getValue() when the checkbox is unchecked.
 * @param Checked Value A string representation that will be returned from getValue() when the checkbox is checked.
 * @param Mixed Checked Value A string representation that will be returned from getValue() when the checkbox is mixed checked.
 */
public HtmlTriStateCheckBox(String name, String theme, com.salmonllc.html.HtmlPage p, String checkedValue, String unCheckedValue, String mixedCheckedValue) {
	super(name,theme,p);
	
	_checkedValue = checkedValue;
	_value=_unCheckedValue = unCheckedValue;
	_mixedCheckedValue = mixedCheckedValue;
	
}
 public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	if (! _visible )
		return;

	String value = getValue(rowNo,true);
	
	int checked = 0;
	if (value == null && _checkedValue == null)
		checked = 1;
	else if (value == null && _mixedCheckedValue == null)
		checked = 2;
	else if (value != null) {
		if (value.equals(_checkedValue))
			checked = 1;	
		if (value.equals(_mixedCheckedValue))
			checked = 2;	
	   }
	if (!_enabled && (_imageDisabledChecked != null) && (_imageDisabledUnchecked != null) && (_imageDisabledMixed != null)) {
		String out = "<IMG SRC=\"";
		if (checked==1)
			out += _imageDisabledChecked;
		else if (checked==2)
			out += _imageDisabledMixed;
	    else
	        out += _imageDisabledUnchecked;
		out += "\">";		
		if (_fontTagStart != null) 
			out = _fontTagStart + out + _fontTagEnd;
		p.print(out);
		return;
	}
	String name = getFullName();
	if (rowNo > -1)
		name += "_" + rowNo;

	String sJavascript="if ("+name+".value=='0')"+
	"{document."+name+"TRISTATECHECKBOX.src=fnc"+getFullName()+"_ImageChecked();"+name+".value='1';return false;}"+
	"else if ("+name+".value=='1')"+
	"{document."+name+"TRISTATECHECKBOX.src=fnc"+getFullName()+"_ImageMixed();"+name+".value='2';return false;}" +
	" else if ("+name+".value=='2')" +
	"{document."+name+"TRISTATECHECKBOX.src=fnc"+getFullName()+"_ImageUnchecked();"+name+".value='0';return false;}";
	String image=_imageUnchecked;
	if (checked==1)
	  image=_imageChecked;
	else if(checked==2)
	  image=_imageMixed;
	 
	String sImagescript="<SCRIPT LANGUAGE='JavaScript1.1'>\n"+getImagesScript()+"\n</SCRIPT>\n";
	String tag = "<INPUT TYPE=HIDDEN NAME=\""+name+"\" VALUE=\""+checked+"\"><A HREF=\"\" ONCLICK=\""+sJavascript+"\"><IMG NAME=\"" + name + "TRISTATECHECKBOX\" SRC='"+image+"' BORDER=0></A>";

			
	if (_fontTagStart != null) 
		tag = _fontTagStart + tag + _fontTagEnd;

	p.println(sImagescript+tag);
//	writeFocusScript(p,rowNo);	
}
/**
 * This method returns the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public String getCheckedValue() {
	return _checkedValue;
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
 * This method returns the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public String getMixedCheckedValue() {
	return _mixedCheckedValue;
}
/**
 * This method returns the the String representation that will be returned from getValue() when the checkbox is not checked.
 */
public String getUncheckedValue() {
	return _unCheckedValue;
}
public boolean processParms(Hashtable parms, int rowNo) throws Exception {
	if (!getVisible() || !getEnabled())
		return false;
		
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
	
	String val[] = (String[]) parms.get(name);

	_value=_unCheckedValue;
	if (val != null) {
		int iVal=new Integer(val[0]).intValue();
		if (iVal==1)
		  _value = _checkedValue;
		else if (iVal==2)
		  _value = _mixedCheckedValue;
	  }

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
public void setCheckedValue(String checkedValue) {
	_checkedValue=checkedValue;
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
 * This method sets the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public void setMixedCheckedValue(String mixedCheckedValue) {
	_mixedCheckedValue=mixedCheckedValue;
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
	_imageDisabledChecked = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_DISABLED_IMAGE_CHECKED);
	_imageDisabledUnchecked = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_DISABLED_IMAGE_UNCHECKED);
	_imageDisabledMixed = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_DISABLED_IMAGE_MIXED);
	_imageChecked = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_IMAGE_CHECKED);
	_imageUnchecked = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_IMAGE_UNCHECKED);
	_imageMixed = props.getThemeProperty(theme,Props.TRISTATECHECKBOX_IMAGE_MIXED);
	
	
}
/**
 * This method sets the the String representation that will be returned from getValue() when the checkbox is checked.
 */
public void setUncheckedValue(String uncheckedValue) {
	_unCheckedValue=uncheckedValue;
}

private String getImagesScript() {
	String sScript="";
	if (_imageChecked!=null && !_imageChecked.equals("")) {
	  sScript+="\nvar "+getFullName()+"_ImageChecked=new Image();\n";
	  sScript+=getFullName()+"_ImageChecked.src=\""+_imageChecked+"\";\n";
	  sScript+="function fnc"+getFullName()+"_ImageChecked() {\n";
	  sScript+="if ("+getFullName()+"_ImageChecked==null) \n";
	  sScript+="return \""+_imageChecked+"\";\n";
	  sScript+="else \n";
	  sScript+="return "+getFullName()+"_ImageChecked.src;\n";
	  sScript+="};\n";
	}
	if (_imageUnchecked!=null && !_imageUnchecked.equals("")) {
	  sScript+="\nvar "+getFullName()+"_ImageUnchecked=new Image();\n";
	  sScript+=getFullName()+"_ImageUnchecked.src=\""+_imageUnchecked+"\";\n";
	  sScript+="function fnc"+getFullName()+"_ImageUnchecked() {\n";
	  sScript+="if ("+getFullName()+"_ImageUnchecked==null) \n";
	  sScript+="return \""+_imageUnchecked+"\";\n";
	  sScript+="else \n";
	  sScript+="return "+getFullName()+"_ImageUnchecked.src;\n";
	  sScript+="};\n";
	}
	if (_imageMixed!=null && !_imageMixed.equals("")) {
	  sScript+="\nvar "+getFullName()+"_ImageMixed=new Image();\n";
	  sScript+=getFullName()+"_ImageMixed.src=\""+_imageMixed+"\";\n";
	  sScript+="function fnc"+getFullName()+"_ImageMixed() {\n";
	  sScript+="if ("+getFullName()+"_ImageMixed==null) \n";
	  sScript+="return \""+_imageMixed+"\";\n";
	  sScript+="else \n";
	  sScript+="return "+getFullName()+"_ImageMixed.src;\n";
	  sScript+="};\n";
	}
	return sScript;
}
}
