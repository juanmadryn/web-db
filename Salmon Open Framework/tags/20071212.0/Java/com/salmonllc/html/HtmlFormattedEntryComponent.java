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

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import java.util.Hashtable;

/**
 * This object creates text edit boxes to accept formated input like invoice number, Tax id etc... In order to create a series of text boxes to accept "###-####-####-##" format, enter an array of integers, {3,4,3,2}, in the contstructor, "lengthsOfFields". This will create 4 text edit boxes with each size 3,4,4 and 2 characters.
 */
public class HtmlFormattedEntryComponent extends HtmlFormComponent
{
	/**
	 * @uml.property  name="_hte"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	protected HtmlTextEdit _hte[];

	protected HtmlContainer _cont;

	public HtmlComponent _hcFocusComp;

	public String _sStr;
	public String _sSeparator;
	private HtmlText _htSeparator1;
	
	private String _sPart[];

	private int _iLength[];
	
	private String _font;
	private String _fontStartTag;
	private String _fontEndTag;
/**
 * TaxIdComponent constructor comment.
 *
 * @param name             Name of component.
 * @param lengthsOfFields  An array of integers for the length of fields.
 * @param p                Page containing component.
 * @param separator        Character to display between the parts of the component
 * @param bEnableFlag      Flag to set the component to editable (true) or read only (false)
 */
public HtmlFormattedEntryComponent(String name, int[] lengthsOfFields, HtmlPage p, String separator, boolean bEnableFlag)
{
	super(name, p);
	_cont = new HtmlContainer(name, p);
	_sSeparator = separator;

	_iLength = lengthsOfFields;
	_sPart   = new String[_iLength.length];	
	_hte     = new HtmlTextEdit[_iLength.length];
	
	for (int i=0 ; i < _iLength.length ; i++){
		_hte[i] = new HtmlTextEdit("hte"+i, p);
		_hte[i].setGenerateNewline(false);
		_hte[i].setSize(lengthsOfFields[i]);
		_hte[i].setMaxLength(lengthsOfFields[i]);
		_cont.add(_hte[i]);
	
		if (_sSeparator != null && !_sSeparator.trim().equals("") && i<_iLength.length-1)  //Do not add Seperator after the last text edit box
			_cont.add(new HtmlText(_sSeparator,getPage()));
	}
	
	setEnabled(bEnableFlag);
}
public void generateHTML(java.io.PrintWriter p, int row) throws Exception
{
	// It is essential to call getValue() here because there may be a ValueChanged
	// event in the queue for this object, which needs to be removed, which getValue()
	// does.  The secondary calls to getValue() from the container will not
	// do this because there are no events associated with them.
	String val = getValue(row, true);
	 
	if (val == null || val.trim().equals(""))
	{
		for (int i=0 ; i < _iLength.length ; i++)
			_hte[i].setValue(null, row);
	}
	else
	{
		String sVal;
	  
		if (_dsBuff != null)
		{
			if (row >= 0)
				sVal = _dsBuff.getString(row, _dsColNo);
			else
				sVal = _dsBuff.getString(_dsColNo);
		}
		else
			sVal = val;

		int iTotalStrLen = 0;
		
		for (int i=0 ; i < _iLength.length ; i++)
			iTotalStrLen += _iLength[i];
			
		if (sVal.length() >= iTotalStrLen){
			int iBegIdex = 0;
			for (int i=0 ; i < _iLength.length ; i++){
				_hte[i].setValue(sVal.substring(iBegIdex, iBegIdex+_iLength[i]), row);
				iBegIdex += _iLength[i];
			}
		}
	}
	
	_cont.generateHTML(p, row);
	
	if (_visible && _enabled)
		p.println("");
}
/**
 * Returns the sub-component to be used for setting focus.
 * @return com.salmonllc.html.HtmlComponent
 */
public HtmlComponent getFocusComponent ()
{
	return _hcFocusComp;
}
/**
 * Returns the sub-component to be used for setting focus.
 * @return com.salmonllc.html.HtmlComponent
 */
public HtmlComponent getFocusPart (int iPartNo)
{
	if (_hte!=null && iPartNo < _hte.length && _hte[iPartNo]!=null)
		return _hte[iPartNo];
	else
		return _hte[0];
}
/**
 * This method will return the font type used by this control.See the Constants at the top of the class for valid font types.
 */
public String getFont() {
	return _font;
}
/**
 * This method returns the formatted string provided that the component
 * contains a valid value otherwise it returns an empty string
 */
public String getFormattedValue()
{
	String sFormattedStr = "";

	if (isValid())
	{
		for (int i=0 ; i < _iLength.length ; i++)
			sFormattedStr += _sPart[i] + _sSeparator;
	}

	//Strip the last seperator
	if(sFormattedStr.length()>1)
		sFormattedStr = sFormattedStr.substring(0, sFormattedStr.length()-1);
	
	return sFormattedStr;
}
/**
 * Gets the separator char.
 * @return java.lang.String
 */
public java.lang.String getSeparator() {
	return _sSeparator;
}
/**
 * This method returns the string provided that the component
 * contains a valid value otherwise it returns an empty string
 */
public String getValue()
{
	_sStr = "";
	
	if (isValid())
		for(int i=0 ; i<_iLength.length ; i++)
			_sStr += _sPart[i];
		
	return _sStr;
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public boolean getVisible()
{

	return _cont.getVisible();
}
/**
 * This methot checks if all rewuired fields entered. If not, sets the focus component to the component which generated the error.
 */
public boolean isValid()
{
	boolean bRetVal = true;
	//verify if all fields are entered	
	for (int i=0 ; i < _iLength.length ; i++)
		_hte[i].setValue(_sPart[i]);
				 
	
	for (int i=0 ; i < _iLength.length ; i++){
		int iValidLength = _iLength[i];
		int iCurrLength = _sPart[i].length();

		if (iCurrLength != iValidLength)
		{
			_hcFocusComp = getFocusPart(i);
			bRetVal = false;
		}
	} 
	return bRetVal; 
}
public boolean processParms(Hashtable htParms, int iRowNo) throws Exception 
{
	if (!getVisible() || !getEnabled())
		return false;

	// Determine the old value from both edit fields.

	String sOldValue = null;
	
	if (_dsBuff == null) {
		for(int i=0 ; i<_iLength.length ; i++)
			sOldValue += _hte[i].getValue();
	}else {
		String s;
		if (iRowNo > -1)
			s = _dsBuff.getString(iRowNo, _dsColNo);
		else
			s = _dsBuff.getString(_dsColNo);

		sOldValue = (s != null) ? s : null;
		
		if (s == null)
			sOldValue = null;
		else
			sOldValue = s;
	}

	// Determine the new value from both edit fields.

	String sNewValue = null;
	for(int i=0 ; i<_iLength.length ; i++){
		String sName = _hte[i].getFullName();
	
		if (iRowNo > -1)
			sName += "_" + iRowNo;
	
		String[] asValues = (String[]) htParms.get(sName);
			
		if (asValues != null)
		{
			String s = _sPart[i] = asValues[0].trim();
		
			if (!s.equals(""))
			{
				if (sNewValue == null)
					sNewValue = "";
				sNewValue += s;
			}
		}		
	}
	
	
	// Compare old and new values and possibly create a ValueChangedEvent.
	if (!valuesEqual(sOldValue, sNewValue)) 
	{
		ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), sOldValue, sNewValue, iRowNo, _dsColNo, _dsBuff);
		addEvent(e);
	}
	return false;
}
/**
 * This method will clear all pending events from the event queue for this component. 
 */
public void reset()
{
	super.reset();
	_cont.reset();
	
	setValue("");
	
}
/**
 * Enables or disables the ability of this component to respond to user input.
 * @param editable boolean
 */
public void setEnabled (boolean enabled)
{
	super.setEnabled(enabled);
	_cont.setEnabled(enabled);
}
/**
 * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
 */
public void setFont(String font)
{
	_font = font;

	Props props = getPage().getPageProperties();

	if (_font != null)
		{
		_fontStartTag = props.getThemeProperty(null, _font + Props.TAG_START);
		_fontEndTag = props.getThemeProperty(null, _font + Props.TAG_END);
	} else
		{
		_fontStartTag = props.getThemeProperty(null, HtmlText.FONT_DEFAULT + Props.TAG_START);
		_fontEndTag = props.getThemeProperty(null, HtmlText.FONT_DEFAULT + Props.TAG_END);
	}

	for(int i=0 ; i< _iLength.length ; i++){
		_hte[i].setFontStartTag(_fontStartTag);
		_hte[i].setFontEndTag(_fontEndTag);
	}
	
	if (_htSeparator1 != null)
		{
		_htSeparator1.setFontStartTag(_fontStartTag);
		_htSeparator1.setFontEndTag(_fontEndTag);
	}
	
}
public void setParent(HtmlComponent parent)
{
	super.setParent(parent);
	// This is necessary for the name to be generated correctly, else the leading
	// sequence of parent names will be lost.
	_cont.setParent(parent);
}
/**
 * Replaces the separator between the ssn numbers
 * @param newSeparator java.lang.String
 */
public void setSeparator(java.lang.String newSeparator)
{
	_sSeparator = newSeparator;

	// create new text comp
	HtmlText txtNewSeparator1 = new HtmlText(newSeparator, getPage());
	
	// replace old area code end comp with new one
	_cont.replaceComponent(txtNewSeparator1, _htSeparator1);
	
	//	save ref of new comp
	_htSeparator1 = txtNewSeparator1;
	
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public void setTheme(String theme)
{

	super.setTheme(theme);

	if (theme != null){	
		for(int i=0; i< _iLength.length; i++){
			_hte[i].setTheme(theme);
		}

		if(_htSeparator1!=null)
			_htSeparator1.setTheme(theme);
	}
}
/**
 * This method sets the property theme for the component.
 * @param theme The theme to use.
 */
public void setVisible(boolean visible)
{

	_cont.setVisible(visible);
}

/**
 * @returns the access key html attribute
 */
public String getAccessKey() {
	return _hte[0].getAccessKey();
}

/**
 * @returns the read only html attribute
 */
public boolean getReadOnly() {
	return _hte[0].getReadOnly();
}

/**
 * @returns the tab index html attribute
 */
public int getTabIndex() {
	return _hte[0].getTabIndex();
}

/**
 * @param sets the access key html attribute
 */
public void setAccessKey(String val) {
	_hte[0].setAccessKey(val);
}

/**
 * @param sets the read only html attribute
 */
public void setReadOnly(boolean val) {
	for (int i = 0; i < _hte.length; i++)
		_hte[i].setReadOnly(val);
}
/**
 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
 */
public void setTabIndex(int val) {
	for (int i = 0; i < _hte.length; i++)
		_hte[i].setTabIndex(val + i);
}
}
